/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.mail.model.Filter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.process.ProcessUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
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
			}
		);
	}

	@Override
	public void addUser(
		long companyId, long userId, String password, String firstName,
		String middleName, String lastName, String emailAddress) {

		execute(
			new String[] {
				SHELL_SCRIPT, "addUser", String.valueOf(userId), password,
				firstName, middleName, lastName, emailAddress
			}
		);
	}

	@Override
	public void addVacationMessage(
		long companyId, long userId, String emailAddress,
		String vacationMessage) {

		execute(
			new String[] {
				SHELL_SCRIPT, "addVacationMessage", String.valueOf(userId),
				emailAddress, vacationMessage
			}
		);
	}

	@Override
	public void deleteEmailAddress(long companyId, long userId) {
		execute(
			new String[] {
				SHELL_SCRIPT, "deleteEmailAddress", String.valueOf(userId)
			}
		);
	}

	@Override
	public void deleteUser(long companyId, long userId) {
		execute(
			new String[] {
				SHELL_SCRIPT, "deleteUser", String.valueOf(userId)
			}
		);
	}

	@Override
	public void updateBlocked(
		long companyId, long userId, List<String> blocked) {

		execute(
			new String[] {
				SHELL_SCRIPT, "updateBlocked", String.valueOf(userId),
				StringUtil.merge(blocked)
			}
		);
	}

	@Override
	public void updateEmailAddress(
		long companyId, long userId, String emailAddress) {

		execute(
			new String[] {
				SHELL_SCRIPT, "updateEmailAddress", String.valueOf(userId),
				emailAddress
			}
		);
	}

	@Override
	public void updatePassword(long companyId, long userId, String password) {
		execute(
			new String[] {
				SHELL_SCRIPT, "updatePassword", String.valueOf(userId), password
			}
		);
	}

	protected void execute(String[] cmdLine) {
		for (int i = 0; i < cmdLine.length; i++) {
			if (cmdLine[i].trim().length() == 0) {
				cmdLine[i] = StringPool.UNDERLINE;
			}
		}

		try {
			Future<?> future = ProcessUtil.execute(
				ProcessUtil.LOGGING_OUTPUT_PROCESSOR, cmdLine);

			future.get();
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ShellHook.class);

}