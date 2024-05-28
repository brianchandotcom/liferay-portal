/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.benchmarks;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.tools.benchmarks.task.BenchmarksTask;
import com.liferay.portal.tools.benchmarks.task.LoginBenchmarksTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author Tina Tian
 */
public class BenchmarksTest {

	public static void main(String[] args) throws Exception {
		for (String arg : args) {
			int pos = arg.indexOf('=');

			if (pos <= 0) {
				throw new IllegalArgumentException("Bad argument " + arg);
			}

			String key = arg.substring(2, pos);
			String value = arg.substring(pos + 1);

			System.setProperty(key.trim(), value.trim());
		}

		BenchmarksTest benchmarksTest = new BenchmarksTest();

		benchmarksTest.login();
	}

	public BenchmarksTest() {
		_excludedCompanies = StringUtil.split(
			System.getProperty(
				"benchmarks.test.excluded.companies", "liferay.com"));
		_driverClassName = System.getProperty(
			"benchmarks.test.jdbc.driverClassName", "com.mysql.cj.jdbc.Driver");
		_jdbcURL = System.getProperty(
			"benchmarks.test.jdbc.url",
			StringBundler.concat(
				"jdbc:mysql://localhost/lportal?characterEncoding=UTF-8&",
				"dontTrackOpenResources=true&holdResultsOpenOverStatementClose",
				"=true&serverTimezone=GMT&useFastDateParsing=false&",
				"useUnicode=true"));
		_jdbcUserName = System.getProperty(
			"benchmarks.test.jdbc.username", StringPool.BLANK);
		_jdbcPassword = System.getProperty(
			"benchmarks.test.jdbc.password", StringPool.BLANK);
		_runCount = GetterUtil.getInteger(
			System.getProperty("benchmarks.test.run.count", "1"));
		_skipWarmUp = Boolean.parseBoolean(
			System.getProperty("benchmarks.test.skip.warm.up", "false"));
		_threadCount = GetterUtil.getInteger(
			System.getProperty("benchmarks.test.thread.count", "1"));
		_userPassword = System.getProperty(
			"benchmarks.test.user.password", "test");

		StringBundler sb = new StringBundler(16);

		sb.append("\nCurrent properties:\n	Excluded Companies:");
		sb.append(_excludedCompanies);
		sb.append("\n	JDBC Driver Class Name: ");
		sb.append(_driverClassName);
		sb.append("\n	JDBC URL: ");
		sb.append(_jdbcURL);
		sb.append("\n	JDBC User Name: ");
		sb.append(_jdbcUserName);
		sb.append("\n	JDBC Password: ");
		sb.append(_jdbcPassword);
		sb.append("\n	Run Count: ");
		sb.append(_runCount);
		sb.append("\n	Thread Count: ");
		sb.append(_threadCount);
		sb.append("\n	User Password: ");
		sb.append(_userPassword);

		System.out.println(sb);
	}

	public void login() throws Exception {
		StringBundler sb = new StringBundler(5);

		sb.append("select hostname, emailAddress from Company as company, ");
		sb.append("VirtualHost as virtualHost, User_ as user where ");
		sb.append(_getExcludedCompaniesSQL());
		sb.append("user.type_ = 1 and user.companyId = company.companyId and ");
		sb.append("virtualHost.companyId = company.companyId;");

		_runBenchmarksTest(
			"Login", sb.toString(),
			resultSet -> new String[] {
				resultSet.getString("hostname"),
				resultSet.getString("emailAddress")
			},
			testData -> new LoginBenchmarksTask(
				testData[0], 8080, testData[1], _userPassword));
	}

	private String _getExcludedCompaniesSQL() {
		if (_excludedCompanies.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler();

		sb.append("company.webId not in (");

		for (int i = 0; i < _excludedCompanies.size(); i++) {
			sb.append("'");
			sb.append(_excludedCompanies.get(i));
			sb.append("'");

			if (i < (_excludedCompanies.size() - 1)) {
				sb.append(",");
			}
		}

		sb.append(") and ");

		return sb.toString();
	}

	private String[][] _loadData(
			String loadDataSQL,
			UnsafeFunction<ResultSet, String[], Exception>
				loadDataUnsafeFunction)
		throws Exception {

		Class.forName(_driverClassName);

		List<String[]> data = new ArrayList<>();

		Properties properties = new Properties();

		properties.put("password", _jdbcPassword);
		properties.put("user", _jdbcUserName);

		try (Connection connection = DriverManager.getConnection(
				_jdbcURL, properties);
			PreparedStatement preparedStatement = connection.prepareStatement(
				loadDataSQL);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				data.add(loadDataUnsafeFunction.apply(resultSet));
			}
		}

		return data.toArray(new String[0][0]);
	}

	private void _runBenchmarksTest(
			String testName, String loadDataSQL,
			UnsafeFunction<ResultSet, String[], Exception>
				loadDataUnsafeFunction,
			Function<String[], BenchmarksTask> benchmarkTaskFunction)
		throws Exception {

		System.out.println(
			StringBundler.concat(
				"\n", testName, " test data load SQL : ", loadDataSQL));

		String[][] data = _loadData(loadDataSQL, loadDataUnsafeFunction);

		if (!_skipWarmUp) {
			System.out.println(
				"\nStart warming up data for " + testName + " test ...");

			_runBenchmarksTest(
				testName, data, 1, data.length, index -> index,
				benchmarkTaskFunction);

			System.out.println(
				"\nWarm up data for " + testName + " test finished!");
		}
		else {
			System.out.println(
				"\nSkip warming up data for " + testName + " test!");
		}

		System.out.println("\nStart running " + testName + " test ...");

		_runBenchmarksTest(
			testName, data, _threadCount, _runCount,
			index -> index % data.length, benchmarkTaskFunction);

		System.out.println("\n" + testName + "test run finished!");
	}

	private void _runBenchmarksTest(
			String testName, String[][] data, int threadCount, int runCount,
			Function<Integer, Integer> indexFunction,
			Function<String[], BenchmarksTask> benchmarksTaskFunction)
		throws Exception {

		ExecutorService executorService = new ThreadPoolExecutor(
			threadCount, threadCount, 0, TimeUnit.SECONDS,
			new LinkedBlockingDeque<>());

		List<Future<Void>> futures = new ArrayList<>();

		Statistics statistics = new Statistics(testName, runCount);

		statistics.start();

		for (int i = 0; i < runCount; i++) {
			String[] testData = data[indexFunction.apply(i)];

			futures.add(
				executorService.submit(
					() -> {
						BenchmarksTask benchmarksTask =
							benchmarksTaskFunction.apply(testData);

						statistics.record(benchmarksTask.execute());

						return null;
					}));
		}

		for (Future<Void> future : futures) {
			future.get();
		}

		statistics.finish();
	}

	private final String _driverClassName;
	private final List<String> _excludedCompanies;
	private final String _jdbcPassword;
	private final String _jdbcURL;
	private final String _jdbcUserName;
	private final int _runCount;
	private final boolean _skipWarmUp;
	private final int _threadCount;
	private final String _userPassword;

}