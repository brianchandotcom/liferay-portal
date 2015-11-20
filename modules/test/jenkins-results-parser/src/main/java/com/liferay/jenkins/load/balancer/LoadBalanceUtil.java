/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.jenkins.load.balancer;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.File;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.Project;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public class LoadBalanceUtil {

	public static String getMostAvailableMasterURL(Project project)
		throws Exception {

		String baseInvocationUrl = project.getProperty("base.invocation.url");

		String hostNamePrefix = getHostNamePrefix(baseInvocationUrl);

		if (hostNamePrefix.equals(baseInvocationUrl)) {
			return baseInvocationUrl;
		}

		int maxHostNames = calculateMaxHostNames(project, hostNamePrefix);

		File file = new File(
			project.getProperty(
				"jenkins.shared.dir") + "/" + hostNamePrefix + ".semaphore");

		waitForTurn(file, maxHostNames);

		JenkinsResultsParserUtil.write(file, myHostName);

		try {
			List<String> hostNameList = new ArrayList<>(maxHostNames);
			List<FutureTask<Integer>> taskList = new ArrayList<>(maxHostNames);

			startParallelTasks(
				hostNameList, hostNamePrefix, maxHostNames, project, taskList);

			List<Integer> badIndicies = new ArrayList<>(taskList.size());
			List<Integer> maxIndicies = new ArrayList<>(taskList.size());
			int maxResult = 0;

			for (int i = 0; i < taskList.size(); i++) {
				FutureTask<Integer> task = taskList.get(i);

				Integer result = task.get();

				if (result == -1) {
					badIndicies.add(i);
					continue;
				}

				if (result > maxResult) {
					maxResult = result;
					maxIndicies.clear();
				}

				if (result >= maxResult) {
					maxIndicies.add(i);
				}
			}

			if (badIndicies.size() == maxHostNames) {
				throw new IllegalStateException(
					"SEVERE: All hosts failed to respond.");
			}

			int x = -1;

			if (maxIndicies.size() > 0) {
				x = maxIndicies.get(getRandomValue(0, maxIndicies.size() - 1));
			}
			else {
				while (true) {
					x = getRandomValue(0, maxHostNames - 1);

					if (badIndicies.contains(x)) {
						continue;
					}

					break;
				}
			}

			return hostNameList.get(x);
		}
		finally {
			JenkinsResultsParserUtil.write(file, "");
		}
	}

	public static void main(String[] args) {
		try {
			System.out.println(
				"Most available master: " +
					getMostAvailableMasterURL(getProject()));
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	protected static int calculateMaxHostNames(
		Project project, String hostNamePrefix) {

		int i = 1;
		while (true) {
			String propertyValue = project.getProperty(
				"jenkins.local.url[" + hostNamePrefix + "-" + i + "]");

			if ((propertyValue != null) && (propertyValue.length() > 0)) {
				i++;

				continue;
			}

			return i - 1;
		}
	}

	protected static String getHostNamePrefix(String baseInvocationUrl) {
		Matcher matcher = urlPattern.matcher(baseInvocationUrl);

		if (!matcher.find()) {
			return baseInvocationUrl;
		}

		return matcher.group("hostNamePrefix");
	}

	protected static int getRandomValue(int start, int end) {
		int size = Math.abs(end - start);

		double randomDouble = Math.random();

		return start + (int)Math.round(size * randomDouble);
	}

	protected static void startParallelTasks(
		List<String> hostNameList, String hostNamePrefix, int maxHostNames,
		Project project, List<FutureTask<Integer>> taskList) throws Exception {

		ExecutorService executor = Executors.newFixedThreadPool(maxHostNames);

		for (int i = 1; i <= maxHostNames; i++) {
			String targetHostName = hostNamePrefix + "-" + i;

			hostNameList.add(targetHostName);

			IdleSlaveCounterCallable callable =
				new IdleSlaveCounterCallable(
					project.getProperty(
						"jenkins.local.url[" + targetHostName + "]") +
							"/computer/api/json");

			FutureTask<Integer> futureTask = new FutureTask<>(callable);

			executor.execute(futureTask);

			taskList.add(futureTask);
		}

		executor.shutdown();
	}

	protected static void waitForTurn(File file, int maxHostNames)
		throws Exception {

		boolean bypass = false;

		while (true) {
			if (!file.exists()) {
				JenkinsResultsParserUtil.write(file, "");
				bypass = true;
			}

			long age = System.currentTimeMillis() - file.lastModified();
			String content = JenkinsResultsParserUtil.read(file);

			if (!bypass &&
				((age < _MIN_RUN_INTERVAL) ||
				 ((content.length() > 0) && (age < _MAX_AGE)))) {

				long sleepPeriod =
					_MIN_RUN_INTERVAL - age + (getRandomValue(0, maxHostNames) *
						10);

				System.out.println("Waiting " + sleepPeriod + " milliseconds.");

				Thread.sleep(sleepPeriod);

				continue;
			}

			return;
		}
	}

	protected static final String myHostName;
	protected static final Pattern urlPattern = Pattern.compile(
		"http://(?<hostNamePrefix>[\\S&&[^-]]+-\\d+).liferay.com");

	static {
		InetAddress inetAddress = null;
		String inetHostName = null;

		try {
			inetAddress = InetAddress.getLocalHost();
			inetHostName = inetAddress.getHostName();
		}
		catch (UnknownHostException uhe) {
			inetHostName = "UNKNOWN";
		}

		myHostName = inetHostName;
	}

	protected static class IdleSlaveCounterCallable
		implements Callable<Integer> {

		@Override
		public Integer call() throws Exception {
			JSONObject jsonObject = null;

			try {
				jsonObject = JenkinsResultsParserUtil.toJSONObject(url, false);
			}
			catch (Exception e) {
				System.out.println(
					"WARNING : Exception occurred while attempting to read: " +
						url);
				return -1;
			}

			JSONArray jsonArray = jsonObject.getJSONArray("computer");

			int idle = 0;

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject idleJsonObject = jsonArray.getJSONObject(i);

				if (idleJsonObject.getBoolean("idle")) {
					idle++;
				}
			}

			return idle;
		}

		protected IdleSlaveCounterCallable(String statusUrl) {
			url = statusUrl;
		}

		protected final String url;

	}

	private static Project getProject() {
		Project project = new Project();

		project.setProperty("base.invocation.url", "http://test-1.liferay.com");
		project.setProperty("jenkins.shared.dir", "mnt/mfs-ssd1-10.10/jenkins");
		project.setProperty("jenkins.local.url[test-1-1]", "http://test-1-1");
		project.setProperty("jenkins.local.url[test-1-2]", "http://test-1-2");
		project.setProperty("jenkins.local.url[test-1-3]", "http://test-1-3");
		project.setProperty("jenkins.local.url[test-1-4]", "http://test-1-4");
		project.setProperty("jenkins.local.url[test-1-5]", "http://test-1-5");
		project.setProperty("jenkins.local.url[test-1-6]", "http://test-1-6");
		project.setProperty("jenkins.local.url[test-1-7]", "http://test-1-7");
		project.setProperty("jenkins.local.url[test-1-8]", "http://test-1-8");
		project.setProperty("jenkins.local.url[test-1-9]", "http://test-1-9");
		project.setProperty("jenkins.local.url[test-1-10]", "http://test-1-10");
		project.setProperty("jenkins.local.url[test-1-11]", "http://test-1-11");
		project.setProperty("jenkins.local.url[test-1-12]", "http://test-1-12");
		project.setProperty("jenkins.local.url[test-1-13]", "http://test-1-13");
		project.setProperty("jenkins.local.url[test-1-14]", "http://test-1-14");
		project.setProperty("jenkins.local.url[test-1-15]", "http://test-1-15");
		project.setProperty("jenkins.local.url[test-1-16]", "http://test-1-16");
		project.setProperty("jenkins.local.url[test-1-17]", "http://test-1-17");
		project.setProperty("jenkins.local.url[test-1-18]", "http://test-1-18");
		project.setProperty("jenkins.local.url[test-1-19]", "http://test-1-19");
		project.setProperty("jenkins.local.url[test-1-20]", "http://test-1-20");

		return project;
	}

	private static final long _MAX_AGE = 30 * 1000;

	private static final long _MIN_RUN_INTERVAL = 15 * 1000;

}