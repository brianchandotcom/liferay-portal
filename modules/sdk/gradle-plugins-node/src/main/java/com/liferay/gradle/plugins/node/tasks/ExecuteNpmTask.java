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

package com.liferay.gradle.plugins.node.tasks;

import com.liferay.gradle.plugins.node.util.FileUtil;
import com.liferay.gradle.plugins.node.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;

import java.util.List;
import java.util.concurrent.Callable;

import org.gradle.api.logging.Logger;

/**
 * @author Andrea Di Giorgi
 */
public class ExecuteNpmTask extends ExecuteNodeScriptTask {

	public ExecuteNpmTask() {
		setCacheDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File nodeDir = getNodeDir();

					if (nodeDir == null) {
						return null;
					}

					return new File(getNodeDir(), ".cache");
				}

			});

		setCommand(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					if (getNodeDir() == null) {
						return "npm";
					}

					return "node";
				}

			});

		setScriptFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File nodeDir = getNodeDir();

					if (nodeDir == null) {
						return null;
					}

					return new File(
						getNodeDir(), "lib/node_modules/npm/bin/npm-cli.js");
				}

			});
	}

	public File getCacheDir() {
		return GradleUtil.toFile(getProject(), _cacheDir);
	}

	public boolean isInheritProxy() {
		return _inheritProxy;
	}

	public void setCacheDir(Object cacheDir) {
		_cacheDir = cacheDir;
	}

	public void setInheritProxy(boolean inheritProxy) {
		_inheritProxy = inheritProxy;
	}

	protected void addProxyArg(List<String> args, String key, String protocol) {
		Logger logger = getLogger();

		if (args.contains(key)) {
			if (logger.isInfoEnabled()) {
				logger.info(
					"{} proxy on {} is already set", protocol.toUpperCase(),
					this);
			}

			return;
		}

		String host = System.getProperty(protocol + ".proxyHost");
		String port = System.getProperty(protocol + ".proxyPort");

		if (Validator.isNotNull(host) && Validator.isNotNull(port)) {
			String url = protocol + "://" + host + ":" + port;

			args.add(key);
			args.add(url);

			if (logger.isInfoEnabled()) {
				logger.info(
					"{} proxy on {} set to {}", protocol.toUpperCase(), this,
					url);
			}
		}
	}

	@Override
	protected List<String> getCompleteArgs() {
		List<String> completeArgs = super.getCompleteArgs();

		File cacheDir = getCacheDir();

		if (cacheDir != null) {
			completeArgs.add("--cache");
			completeArgs.add(FileUtil.getAbsolutePath(cacheDir));
		}

		String logLevel = null;

		Logger logger = getLogger();

		if (logger.isTraceEnabled()) {
			logLevel = "silly";
		}
		else if (logger.isDebugEnabled()) {
			logLevel = "verbose";
		}
		else if (logger.isInfoEnabled()) {
			logLevel = "info";
		}
		else if (logger.isWarnEnabled()) {
			logLevel = "warn";
		}
		else if (logger.isErrorEnabled()) {
			logLevel = "error";
		}

		if (logLevel != null) {
			completeArgs.add("--loglevel");
			completeArgs.add(logLevel);
		}

		if (isInheritProxy()) {
			addProxyArg(completeArgs, "--proxy", "http");
			addProxyArg(completeArgs, "--https-proxy", "https");
		}

		return completeArgs;
	}

	private Object _cacheDir;
	private boolean _inheritProxy = true;

}