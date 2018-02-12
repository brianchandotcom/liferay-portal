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

package com.liferay.mail.util;

import com.liferay.mail.kernel.model.Filter;
import com.liferay.mail.kernel.util.Hook;
import com.liferay.petra.process.LoggingOutputProcessor;
import com.liferay.petra.process.ProcessUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsUtil;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @author Michael Lawrence
 */
public class ShellHook implements Hook {

	public static final String SHELL_SCRIPT = PropsUtil.get(
		PropsKeys.MAIL_HOOK_SHELL_SCRIPT);

	public void addFilters(long companyId, long userId, List<String> filters) {
	}

	@Override
	public void addForward(
		long companyId, long userId, List<Filter> filters,
		List<String> emailAddresses, boolean leaveCopy) {

		execute(
			new String[] {
				SHELL_SCRIPT, "addForward", String.valueOf(userId),
				StringUtil.merge(emailAddresses)
			});
	}

	@Override
	public void addUser(
		long companyId, long userId, String password, String firstName,
		String middleName, String lastName, String emailAddress) {

		execute(
			new String[] {
				SHELL_SCRIPT, "addUser", String.valueOf(userId), password,
				firstName, middleName, lastName, emailAddress
			});
	}

	@Override
	public void addVacationMessage(
		long companyId, long userId, String emailAddress,
		String vacationMessage) {

		execute(
			new String[] {
				SHELL_SCRIPT, "addVacationMessage", String.valueOf(userId),
				emailAddress, vacationMessage
			});
	}

	@Override
	public void deleteEmailAddress(long companyId, long userId) {
		execute(
			new String[] {
				SHELL_SCRIPT, "deleteEmailAddress", String.valueOf(userId)
			});
	}

	@Override
	public void deleteUser(long companyId, long userId) {
		execute(
			new String[] {SHELL_SCRIPT, "deleteUser", String.valueOf(userId)});
	}

	@Override
	public void updateBlocked(
		long companyId, long userId, List<String> blocked) {

		execute(
			new String[] {
				SHELL_SCRIPT, "updateBlocked", String.valueOf(userId),
				StringUtil.merge(blocked)
			});
	}

	@Override
	public void updateEmailAddress(
		long companyId, long userId, String emailAddress) {

		execute(
			new String[] {
				SHELL_SCRIPT, "updateEmailAddress", String.valueOf(userId),
				emailAddress
			});
	}

	@Override
	public void updatePassword(long companyId, long userId, String password) {
		execute(
			new String[] {
				SHELL_SCRIPT, "updatePassword", String.valueOf(userId), password
			});
	}

	protected void execute(String[] cmdLine) {
		for (int i = 0; i < cmdLine.length; i++) {
			String trimmedLine = cmdLine[i].trim();

			if (trimmedLine.length() == 0) {
				cmdLine[i] = StringPool.UNDERLINE;
			}
		}

		try {
			Future<?> future = ProcessUtil.execute(
				new LoggingOutputProcessor(
					(stdErr, line) -> {
						if (stdErr) {
							_log.error(line);
						}
						else if (_log.isInfoEnabled()) {
							_log.info(line);
						}
					}),
				cmdLine);

			future.get();
		}
		catch (Exception e) {
			_log.error("Unable to execute shell command " + cmdLine[0], e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(ShellHook.class);

}