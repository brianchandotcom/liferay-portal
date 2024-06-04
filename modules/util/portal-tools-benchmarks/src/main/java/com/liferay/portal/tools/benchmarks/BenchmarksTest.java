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

	public BenchmarksTest() {
		_excludedCompanies = StringUtil.split(
			System.getProperty(
				"benchmarks.test.excluded.companies", "liferay.com"));
		_jdbcDriverClassName = System.getProperty(
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
		sb.append(_jdbcDriverClassName);
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

	public void executeLoginBenchmarksTask() throws Exception {
		System.out.println("Running Login test ...");

		_executeBenchmarksTask(
			testData -> new LoginBenchmarksTask(
				testData[0], testData[1], _userPassword, 8080),
			_loadData(
				_createLoadDataSQL(),
				resultSet -> new String[] {
					resultSet.getString("emailAddress"),
					resultSet.getString("hostname")
				}));
	}

	private String _createLoadDataSQL() {
		StringBundler sb = new StringBundler();

		sb.append("select hostname, emailAddress from Company as company, ");
		sb.append("VirtualHost as virtualHost, User_ as user where ");

		if (!_excludedCompanies.isEmpty()) {
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
		}

		sb.append("user.type_ = 1 and user.companyId = company.companyId and ");
		sb.append("virtualHost.companyId = company.companyId;");

		return sb.toString();
	}

	private void _executeBenchmarksTask(
			Function<String[], BenchmarksTask> benchmarkTaskFunction,
			String[][] data)
		throws Exception {

		if (!_skipWarmUp) {
			System.out.println("\nStart warming up data ...");

			_executeBenchmarksTask(
				benchmarkTaskFunction, data, index -> index, data.length, 1);

			System.out.println("\nWarming up finished");
		}
		else {
			System.out.println("\nSkip warming up data ...");
		}

		System.out.println("\nStart running test ...");

		_executeBenchmarksTask(
			benchmarkTaskFunction, data, index -> index % data.length,
			_runCount, _threadCount);

		System.out.println("\nTest run finished");
	}

	private void _executeBenchmarksTask(
			Function<String[], BenchmarksTask> benchmarksTaskFunction,
			String[][] data, Function<Integer, Integer> indexFunction,
			int runCount, int threadCount)
		throws Exception {

		ExecutorService executorService = new ThreadPoolExecutor(
			threadCount, threadCount, 0, TimeUnit.SECONDS,
			new LinkedBlockingDeque<>());

		List<Future<Void>> futures = new ArrayList<>();

		Statistics statistics = new Statistics(runCount);

		for (int i = 0; i < runCount; i++) {
			String[] testData = data[indexFunction.apply(i)];

			futures.add(
				executorService.submit(
					() -> {
						BenchmarksTask benchmarksTask =
							benchmarksTaskFunction.apply(testData);

						statistics.addResults(benchmarksTask.execute());

						return null;
					}));
		}

		for (Future<Void> future : futures) {
			future.get();
		}

		statistics.printStatistics();

		executorService.shutdown();
	}

	private String[][] _loadData(
			String loadDataSQL,
			UnsafeFunction<ResultSet, String[], Exception>
				loadDataUnsafeFunction)
		throws Exception {

		System.out.println("\nTest data load SQL : " + loadDataSQL);

		Class.forName(_jdbcDriverClassName);

		List<String[]> data = new ArrayList<>();

		try (Connection connection = DriverManager.getConnection(
				_jdbcURL, _jdbcUserName, _jdbcPassword);
			PreparedStatement preparedStatement = connection.prepareStatement(
				loadDataSQL);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				data.add(loadDataUnsafeFunction.apply(resultSet));
			}
		}

		return data.toArray(new String[0][0]);
	}

	private final List<String> _excludedCompanies;
	private final String _jdbcDriverClassName;
	private final String _jdbcPassword;
	private final String _jdbcURL;
	private final String _jdbcUserName;
	private final int _runCount;
	private final boolean _skipWarmUp;
	private final int _threadCount;
	private final String _userPassword;

}