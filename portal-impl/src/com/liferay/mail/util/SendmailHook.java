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
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.process.ProcessUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsUtil;

import java.io.File;
import java.io.FileReader;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @author Brian Wing Shun Chan
 */
public class SendmailHook implements Hook {

	@Override
	public void addForward(
		long companyId, long userId, List<Filter> filters,
		List<String> emailAddresses, boolean leaveCopy) {

		try {
			if (emailAddresses != null) {
				String home = PropsUtil.get(PropsKeys.MAIL_HOOK_SENDMAIL_HOME);

				File file = new File(home + "/" + userId + "/.forward");

				if (emailAddresses.size() > 0) {
					StringBundler sb = new StringBundler(
						emailAddresses.size() * 2);

					for (int i = 0; i < emailAddresses.size(); i++) {
						String emailAddress = emailAddresses.get(i);

						sb.append(emailAddress);
						sb.append("\n");
					}

					FileUtil.write(file, sb.toString());
				}
				else {
					file.delete();
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void addUser(
		long companyId, long userId, String password, String firstName,
		String middleName, String lastName, String emailAddress) {

		// Get add user command

		String addUserCmd = PropsUtil.get(
			PropsKeys.MAIL_HOOK_SENDMAIL_ADD_USER);

		// Replace userId

		addUserCmd = StringUtil.replace(
			addUserCmd, "%1%", String.valueOf(userId));

		try {
			Future<?> future = ProcessUtil.execute(
				ProcessUtil.LOGGING_OUTPUT_PROCESSOR, addUserCmd);

			future.get();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		updatePassword(companyId, userId, password);
		updateEmailAddress(companyId, userId, emailAddress);
	}

	@Override
	public void addVacationMessage(
		long companyId, long userId, String emailAddress,
		String vacationMessage) {
	}

	@Override
	public void deleteEmailAddress(long companyId, long userId) {
		updateEmailAddress(companyId, userId, "");
	}

	@Override
	public void deleteUser(long companyId, long userId) {
		deleteEmailAddress(companyId, userId);

		// Get delete user command

		String deleteUserCmd = PropsUtil.get(
			PropsKeys.MAIL_HOOK_SENDMAIL_DELETE_USER);

		// Replace userId

		deleteUserCmd = StringUtil.replace(
			deleteUserCmd, "%1%", String.valueOf(userId));

		try {
			Future<?> future = ProcessUtil.execute(
				ProcessUtil.LOGGING_OUTPUT_PROCESSOR, deleteUserCmd);

			future.get();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void updateBlocked(
		long companyId, long userId, List<String> blocked) {

		String home = PropsUtil.get(PropsKeys.MAIL_HOOK_SENDMAIL_HOME);

		File file = new File(home + "/" + userId + "/.procmailrc");

		if ((blocked == null) || (blocked.size() == 0)) {
			file.delete();

			return;
		}

		StringBundler sb = new StringBundler(blocked.size() * 9 + 3);

		sb.append("ORGMAIL /var/spool/mail/$LOGNAME\n");
		sb.append("MAILDIR $HOME/\n");
		sb.append("SENDMAIL /usr/smin/sendmail\n");

		for (int i = 0; i < blocked.size(); i++) {
			String emailAddress = blocked.get(i);

			sb.append("\n");
			sb.append(":0\n");
			sb.append("* ^From.*");
			sb.append(emailAddress);
			sb.append("\n");
			sb.append("{\n");
			sb.append(":0\n");
			sb.append("/dev/null\n");
			sb.append("}\n");
		}

		try {
			FileUtil.write(file, sb.toString());
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void updateEmailAddress(
		long companyId, long userId, String emailAddress) {

		try {
			String virtusertable = PropsUtil.get(
				PropsKeys.MAIL_HOOK_SENDMAIL_VIRTUSERTABLE);

			FileReader fileReader = new FileReader(virtusertable);
			UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(fileReader);

			StringBundler sb = new StringBundler();

			for (String s = unsyncBufferedReader.readLine(); s != null;
					s = unsyncBufferedReader.readLine()) {

				if (!s.endsWith(" " + userId)) {
					sb.append(s);
					sb.append('\n');
				}
			}

			if ((emailAddress != null) && !emailAddress.equals("")) {
				sb.append(emailAddress);
				sb.append(" ");
				sb.append(userId);
				sb.append('\n');
			}

			unsyncBufferedReader.close();
			fileReader.close();

			FileUtil.write(virtusertable, sb.toString());

			String virtusertableRefreshCmd = PropsUtil.get(
				PropsKeys.MAIL_HOOK_SENDMAIL_VIRTUSERTABLE_REFRESH);

			Future<?> future = ProcessUtil.execute(
				ProcessUtil.LOGGING_OUTPUT_PROCESSOR, virtusertableRefreshCmd);

			future.get();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void updatePassword(long companyId, long userId, String password) {

		// Get change password command

		String changePasswordCmd = PropsUtil.get(
			PropsKeys.MAIL_HOOK_SENDMAIL_CHANGE_PASSWORD);

		// Replace userId

		changePasswordCmd = StringUtil.replace(
			changePasswordCmd, "%1%", String.valueOf(userId));

		// Replace password

		changePasswordCmd = StringUtil.replace(
			changePasswordCmd, "%2%", password);

		try {
			Future<?> future = ProcessUtil.execute(
				ProcessUtil.LOGGING_OUTPUT_PROCESSOR, changePasswordCmd);

			future.get();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SendmailHook.class);

}