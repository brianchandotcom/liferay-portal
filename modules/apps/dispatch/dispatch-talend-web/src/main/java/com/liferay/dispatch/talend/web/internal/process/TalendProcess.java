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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Igor Beslic
 */
public interface TalendProcess {

	public static final String CONTEXT_PARM_COMPANY_ID_TPL =
		"--context_param companyId=%d";

	public static final String CONTEXT_PARM_JOB_WORK_DIRECTORY_TPL =
		"--context_param jobWorkDirectory=%s";

	public static final String CONTEXT_PARM_LAST_RUN_START_DATE_TPL =
		"--context_param lastRunStartDate=%s";

	public static final String CONTEXT_PARM_NAME_VALUE_TPL =
		"--context_param %s=%s";

	public List<String> getArguments();

	public static class Builder {

		public TalendProcess build() {
			List<String> arguments = new ArrayList<>();

			arguments.add("java");
			arguments.add("-cp");
			arguments.add(_talendArchive.getClasspath());
			arguments.add(_talendArchive.getJobMainClassFQN());
			arguments.add("--context=" + _talendArchive.getContextName());
			arguments.add(
				String.format(CONTEXT_PARM_COMPANY_ID_TPL, _companyId));
			arguments.add(
				String.format(
					CONTEXT_PARM_JOB_WORK_DIRECTORY_TPL,
					_talendArchive.getJobDirectory()));

			if (_lastRunStartDate != null) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss'Z'");

				arguments.add(
					String.format(
						CONTEXT_PARM_LAST_RUN_START_DATE_TPL,
						simpleDateFormat.format(_lastRunStartDate)));
			}

			arguments.addAll(_contextParams);

			return new TalendProcess() {

				@Override
				public List<String> getArguments() {
					return _arguments;
				}

				@Override
				public String toString() {
					StringBundler sb = new StringBundler(
						(_arguments.size() * 2) - 1);

					Iterator<String> iterator = _arguments.iterator();

					while (iterator.hasNext()) {
						sb.append(iterator.next());

						if (iterator.hasNext()) {
							sb.append(StringPool.SPACE);
						}
					}

					return sb.toString();
				}

				private final List<String> _arguments =
					Collections.unmodifiableList(arguments);

			};
		}

		public Builder companyId(long companyId) {
			_companyId = companyId;

			return this;
		}

		public Builder contextParam(String name, String value) {
			_contextParams.add(
				String.format(CONTEXT_PARM_NAME_VALUE_TPL, name, value));

			return this;
		}

		public Builder lastRunStartDate(Date lastRunStartDate) {
			_lastRunStartDate = lastRunStartDate;

			return this;
		}

		public Builder talendArchive(TalendArchive talendArchive) {
			_talendArchive = talendArchive;

			return this;
		}

		private long _companyId;
		private final List<String> _contextParams = new ArrayList<>();
		private Date _lastRunStartDate;
		private TalendArchive _talendArchive;

	}

}