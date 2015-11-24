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

package com.liferay.jenkins.load.balance;

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

		int hostNameCount = getHostNameCount(project, hostNamePrefix);

		File baseDir = new File(
			project.getProperty("jenkins.shared.dir") + "/" + hostNamePrefix);

		File semaphoreFile = new File(baseDir, hostNamePrefix + ".semaphore");

		waitForTurn(semaphoreFile, hostNameCount);

		JenkinsResultsParserUtil.write(semaphoreFile, myHostName);

		List<String> coolDownList = getCoolDownList(
			new File(baseDir, "timeout"));

		List<String> hostNameList = new ArrayList<>(hostNameCount);
		int maxResult = Integer.MIN_VALUE;
		int x = -1;

		try {
			List<FutureTask<Integer>> taskList = new ArrayList<>(hostNameCount);

			startParallelTasks(
				hostNameList, hostNamePrefix, hostNameCount, project, taskList,
				coolDownList);

			List<Integer> badIndicies = new ArrayList<>(taskList.size());
			List<Integer> maxIndicies = new ArrayList<>(taskList.size());

			for (int i = 0; i < taskList.size(); i++) {
				FutureTask<Integer> task = taskList.get(i);

				Integer result = task.get();

				System.out.println(hostNameList.get(i) + " : " + result);

				if (result == null) {
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

			if (maxIndicies.size() > 0) {
				x = maxIndicies.get(getRandomValue(0, maxIndicies.size() - 1));
			}
			else {
				while (true) {
					x = getRandomValue(0, hostNameCount - 1);

					if (badIndicies.contains(x)) {
						continue;
					}

					break;
				}
			}

			JenkinsResultsParserUtil.write(
				new File(baseDir, "timeout/" + hostNameList.get(x)),
				Long.toString(System.currentTimeMillis()));

			System.out.println(
				"Most available master: " + hostNameList.get(x) + " with " +
					maxResult + " available slaves.");
		}
		finally {
			JenkinsResultsParserUtil.write(semaphoreFile, "");
		}

		return hostNameList.get(x);
	}

	protected static List<String> getCoolDownList(File dir) throws Exception {
		List<String> coolDownList = new ArrayList<>();

		if (!dir.exists()) {
			return coolDownList;
		}

		for (File file : dir.listFiles()) {
			long lastUsed = Long.parseLong(JenkinsResultsParserUtil.read(file));

			if (System.currentTimeMillis() < (lastUsed + coolDownPeriod)) {
				coolDownList.add(file.getName());
			}
		}

		return coolDownList;
	}

	protected static int getHostNameCount(
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
		List<String> hostNameList, String hostNamePrefix, int hostNameCount,
		Project project, List<FutureTask<Integer>> taskList,
		List<String> coolDownList) throws Exception {

		ExecutorService executor = Executors.newFixedThreadPool(hostNameCount);

		for (int i = 1; i <= hostNameCount; i++) {
			String targetHostName = hostNamePrefix + "-" + i;

			if (coolDownList.contains(targetHostName)) {
				continue;
			}

			hostNameList.add(targetHostName);

			IdleSlaveCounterCallable callable = new IdleSlaveCounterCallable(
				project.getProperty(
					"jenkins.local.url[" + targetHostName + "]"));

			FutureTask<Integer> futureTask = new FutureTask<>(callable);

			executor.execute(futureTask);

			taskList.add(futureTask);
		}

		executor.shutdown();
	}

	protected static void waitForTurn(File file, int hostNameCount)
		throws Exception {

		while (true) {
			if (!file.exists()) {
				JenkinsResultsParserUtil.write(file, "");
				return;
			}

			long age = System.currentTimeMillis() - file.lastModified();
			String content = JenkinsResultsParserUtil.read(file);
			
			if ((content.length() > 0) && (age < maxAge)) {
				Thread.sleep(1000);

				continue;
			}
			
			if ((content.length()) == 0 && (age < executionRate)) {
				Thread.sleep(
					executionRate - age + 
					getRandomValue(1, hostNameCount) * 100);
				
				continue;
			}

			return;
		}
	}

	protected static long coolDownPeriod = 60 * 1000;
	protected static long executionRate = 15 * 1000;
	protected static long maxAge = 30 * 1000;
	protected static final String myHostName;
	protected static final Pattern urlPattern = Pattern.compile(
		"http://(?<hostNamePrefix>.+-\\d?).liferay.com");

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
			JSONObject queueJsonObject = null;

			try {
				jsonObject = JenkinsResultsParserUtil.toJSONObject(
					JenkinsResultsParserUtil.getLocalURL(
						url + "/computer/api/json?pretty&tree=computer" +
						"[displayName,idle,offline]"),
					false, 5000);
				queueJsonObject = JenkinsResultsParserUtil.toJSONObject(
					JenkinsResultsParserUtil.getLocalURL(
						url + "/queue/api/json?pretty"),
					false, 5000);
			}
			catch (Exception e) {
				System.out.println(
					"WARNING : Exception occurred while attempting to read: " +
						url);
				return null;
			}

			JSONArray jsonArray = jsonObject.getJSONArray("computer");

			int idle = 0;

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject idleJsonObject = jsonArray.getJSONObject(i);

				if (idleJsonObject.getBoolean("idle") &&
					!idleJsonObject.getBoolean("offline")) {

					String displayName = idleJsonObject.getString(
						"displayName");

					if (!displayName.equals("master")) {
						idle++;
					}
				}
			}

			if (queueJsonObject.has("items")) {
				JSONArray itemsJsonArray = queueJsonObject.getJSONArray(
					"items");
				int queueLength = itemsJsonArray.length();

				if (queueLength > 0) {
					return -1 * queueLength;
				}
			}

			return idle;
		}

		protected IdleSlaveCounterCallable(String statusUrl) {
			url = statusUrl;
		}

		protected String url;

	}

}