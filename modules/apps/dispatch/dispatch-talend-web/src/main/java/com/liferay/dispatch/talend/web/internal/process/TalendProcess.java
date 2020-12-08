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

package com.liferay.dispatch.talend.web.internal.process;

import com.liferay.dispatch.talend.web.internal.archive.TalendArchive;
import com.liferay.petra.process.ProcessConfig;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.util.PortalClassPathUtil;

import java.io.File;

import java.net.URL;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Igor Beslic
 */
public class TalendProcess {

	public String[] getMainMethodArguments() {
		return _mainMethodArguments.toArray(new String[0]);
	}

	public ProcessConfig getProcessConfig() {
		return _processConfig;
	}

	public static class Builder {

		public TalendProcess build() {
			return new TalendProcess(this);
		}

		public Builder companyId(long companyId) {
			_contextParams.add(
				"--context_param companyId=".concat(String.valueOf(companyId)));

			return this;
		}

		public Builder contextParam(String name, String value) {
			if (Objects.equals(name, "JAVA_OPTS")) {
				_jvmOptions = StringUtil.split(value, CharPool.SPACE);

				return this;
			}

			_contextParams.add(
				StringBundler.concat(
					"--context_param ", name, StringPool.EQUAL, value));

			return this;
		}

		public Builder lastRunStartDate(Date lastRunStartDate) {
			if (lastRunStartDate != null) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss'Z'");

				_contextParams.add(
					"--context_param lastRunStartDate=".concat(
						simpleDateFormat.format(lastRunStartDate)));
			}

			return this;
		}

		public Builder talendArchive(TalendArchive talendArchive) {
			_talendArchive = talendArchive;

			_contextParams.add(
				"--context=".concat(_talendArchive.getContextName()));
			_contextParams.add(
				"--context_param jobWorkDirectory=".concat(
					_talendArchive.getJobDirectory()));

			return this;
		}

		private ProcessConfig _buildProcessConfig() {
			ProcessConfig.Builder processConfigBuilder =
				new ProcessConfig.Builder();

			if (_jvmOptions != null) {
				processConfigBuilder.setArguments(_jvmOptions);
			}

			ProcessConfig portalProcessConfig =
				PortalClassPathUtil.getPortalProcessConfig();

			processConfigBuilder.setBootstrapClassPath(
				portalProcessConfig.getBootstrapClassPath());

			processConfigBuilder.setProcessLogConsumer(
				portalProcessConfig.getProcessLogConsumer());

			processConfigBuilder.setRuntimeClassPath(
				StringBundler.concat(
					_talendArchive.getClasspath(), File.pathSeparator,
					_BUNDLE_FILE_PATH));

			return processConfigBuilder.build();
		}

		private static final String _BUNDLE_FILE_PATH;

		static {
			ProtectionDomain protectionDomain =
				Builder.class.getProtectionDomain();

			CodeSource codeSource = protectionDomain.getCodeSource();

			URL url = codeSource.getLocation();

			_BUNDLE_FILE_PATH = url.getPath();
		}

		private final List<String> _contextParams = new ArrayList<>();
		private List<String> _jvmOptions;
		private TalendArchive _talendArchive;

	}

	private TalendProcess(Builder builder) {
		_mainMethodArguments = builder._contextParams;
		_processConfig = builder._buildProcessConfig();
	}

	private final List<String> _mainMethodArguments;
	private final ProcessConfig _processConfig;

}