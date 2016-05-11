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

package com.liferay.petra.doulos.processor;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;
import org.json.JSONObject;

/**
 * @author Brian Wing Shun Chan
 * @author Peter Shin
 */
public abstract class BaseShellDoulosRequestProcessor
	extends BaseDoulosRequestProcessor {

	public BaseShellDoulosRequestProcessor() {
		_destroyed = false;

		ConcurrentLinkedHashMap.Builder<String, ShellStatus> builder =
			new ConcurrentLinkedHashMap.Builder<String, ShellStatus>();

		builder.maximumWeightedCapacity(getShellStatusesSize());

		_shellStatuses = builder.build();

		_thread.start();
	}

	@Override
	public void destroy() {
		_destroy = true;

		while (!_destroyed) {
			try {
				if (_log.isInfoEnabled()) {
					_log.info("Waiting for background thread to destroy");
				}

				Thread.sleep(getThreadDestroyInterval());
			}
			catch (InterruptedException ie) {
				_log.error(ie, ie);
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info("Background thread is destroyed");
		}

		super.destroy();
	}

	protected abstract boolean isValid(JSONObject payloadJSONObject);

	@Override
	public void process(
			String method, String pathInfo, Map<String, String[]> parameterMap,
			JSONObject payloadJSONObject, JSONObject responseJSONObject)
		throws Exception {

		if (!isValid(payloadJSONObject)) {
			if (_log.isInfoEnabled()) {
				_log.info("Skip invalid payload");
			}

			return;
		}

		ShellStatus shellStatus = queue(payloadJSONObject);

		populateResponseJSONObject(responseJSONObject, shellStatus);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Status " + shellStatus.status + " for " + shellStatus.key);
		}
	}

	protected void populateResponseJSONObject(
		JSONObject responseJSONObject, ShellStatus shellStatus) {

		responseJSONObject.put("exception", shellStatus.exception);
		responseJSONObject.put("exitValue", shellStatus.exitValue);
		responseJSONObject.put("shellCommands", shellStatus.shellCommands);
		responseJSONObject.put("output", shellStatus.output);
		responseJSONObject.put("status", shellStatus.status);
	}

	protected abstract ShellStatus createShellStatus(
		JSONObject payloadJSONObject);

	protected abstract List<String> getShellCommands(ShellStatus shellStatus);

	protected void execute(ShellStatus shellStatus) throws Exception {
		shellStatus.status = "executing";

		List<String> shellCommandsList = getShellCommands(shellStatus);

		shellCommandsList.add(0, "/bin/bash");
		shellCommandsList.add(1, "-x");
		shellCommandsList.add(2, "-c");

		String[] shellCommands = shellCommandsList.toArray(
			new String[shellCommandsList.size()]);

		shellStatus.shellCommands = StringUtils.join(shellCommands, "\n");

		ProcessBuilder processBuilder = new ProcessBuilder(shellCommands);

		processBuilder.redirectErrorStream(true);

		Process process = processBuilder.start();

		StringBuilder sb = new StringBuilder();

		String line = null;

		BufferedReader bufferedReader = new BufferedReader(
			new InputStreamReader(process.getInputStream()));

		while ((line = bufferedReader.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}

		bufferedReader.close();

		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Wait for process to finish");
			}

			process.waitFor();

			shellStatus.exitValue = String.valueOf(process.exitValue());
			shellStatus.output = sb.toString();
			shellStatus.status = "finished";
		}
		catch (Exception e) {
			Writer writer = new StringWriter();

			PrintWriter printWriter = new PrintWriter(writer);

			e.printStackTrace(printWriter);

			shellStatus.exception = writer.toString();

			shellStatus.status = "exception";
		}
	}

	protected long getExpiredTime() {
		return _EXPIRED_TIME;
	}

	protected abstract String getKey(JSONObject payloadJSONObject);

	protected int getThreadDestroyInterval() {
		return _THREAD_DESTROY_INTERVAL;
	}

	protected int getThreadExecuteInterval() {
		return _THREAD_EXECUTE_INTERVAL;
	}

	protected long getShellStatusesSize() {
		return _SHELL_STATUSES_SIZE;
	}

	protected ShellStatus queue(JSONObject payloadJSONObject) {
		ShellStatus shellStatus = null;

		String key = getKey(payloadJSONObject);

		synchronized (this) {
			shellStatus = _shellStatuses.get(key);

			if (shellStatus != null) {
				long expiredTime = getExpiredTime();

				if ((expiredTime > 0) &&
					(shellStatus.time < getExpiredTime())) {

					_shellStatuses.remove(key);

					shellStatus = null;
				}
			}

			if (shellStatus == null) {
				if (_log.isInfoEnabled()) {
					_log.info("Adding " + key + " to queue");
				}

				shellStatus = createShellStatus(payloadJSONObject);

				_shellStatuses.put(key, shellStatus);

				_queue.add(shellStatus);
			}
		}

		return shellStatus;
	}

	protected class ShellStatus {

		public ShellStatus(String key) {
			this.key = key;
		}

		public String key = "";
		public String exitValue = "";
		public String output = "";
		public String status = "queued";
		public String exception = "";
		public String shellCommands = "";
		public long time = System.currentTimeMillis();

	}

	private static final int _EXPIRED_TIME = 0;

	private static final int _THREAD_DESTROY_INTERVAL = 10 * 1000;

	private static final int _THREAD_EXECUTE_INTERVAL = 3 * 1000;

	private static final long _SHELL_STATUSES_SIZE = 1000;

	private static final Log _log = LogFactory.getLog(
		BaseShellDoulosRequestProcessor.class);

	private boolean _destroy;
	private boolean _destroyed;
	private final Queue<ShellStatus> _queue = new LinkedBlockingQueue<>();

	private final Thread _thread = new Thread() {

		@Override
		public void run() {
			while (true) {
				if (_destroy) {
					break;
				}

				ShellStatus shellStatus = _queue.poll();

				if (shellStatus == null) {
					try {
						Thread.sleep(getThreadExecuteInterval());
					}
					catch (InterruptedException ie) {
						_log.error(
							"Terminating background thread due to unexpected " +
								"interruption",
							ie);

						break;
					}

					continue;
				}

				System.out.println("Queue size " + _queue.size());

				try {
					if (_log.isInfoEnabled()) {
						_log.info("Executing " + shellStatus.key);
					}

					execute(shellStatus);
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}

			_destroyed = true;
		}

	};

	private final Map<String, ShellStatus> _shellStatuses;

}