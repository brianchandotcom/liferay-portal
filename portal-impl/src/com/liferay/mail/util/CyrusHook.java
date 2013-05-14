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
import com.liferay.mail.service.CyrusServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.process.ProcessUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsUtil;

import java.io.File;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @author Brian Wing Shun Chan
 */
public class CyrusHook implements Hook {

	@Override
	public void addForward(
		long companyId, long userId, List<Filter> filters,
		List<String> emailAddresses, boolean leaveCopy) {

		try {
			if (emailAddresses != null) {
				String home = PropsUtil.get(PropsKeys.MAIL_HOOK_CYRUS_HOME);

				File file = new File(home + "/" + userId + ".procmail.forward");

				if ((filters.size() > 0) || (emailAddresses.size() > 0) ||
					leaveCopy) {

					StringBundler sb = new StringBundler();

					for (int i = 0; i < filters.size(); i++) {
						Filter filter = filters.get(i);

						sb.append(":0\n");
						sb.append("* ^(From|Cc|To).*");
						sb.append(filter.getEmailAddress());
						sb.append("\n");
						sb.append("| $DELIVER -e -a $USER -m user.$USER.");
						sb.append(filter.getFolder());
						sb.append("\n\n");
					}

					if (leaveCopy) {
						sb.append(":0 c\n");
						sb.append("| $DELIVER -e -a $USER -m user.$USER\n\n");
					}

					if (emailAddresses.size() > 0) {
						sb.append(":0\n");
						sb.append("!");

						for (String emailAddress : emailAddresses) {
							sb.append(" ");
							sb.append(emailAddress);
						}
					}

					String content = sb.toString();

					while (content.endsWith("\n")) {
						content = content.substring(0, content.length() - 1);
					}

					FileUtil.write(file, content);
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

		try {
			CyrusServiceUtil.addUser(userId, emailAddress, password);

			// Expect

			String addUserCmd = PropsUtil.get(
				PropsKeys.MAIL_HOOK_CYRUS_ADD_USER);

			addUserCmd = StringUtil.replace(
				addUserCmd, "%1%", String.valueOf(userId));

			Future<?> future = ProcessUtil.execute(
				ProcessUtil.LOGGING_OUTPUT_PROCESSOR, addUserCmd);

			future.get();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void addVacationMessage(
		long companyId, long userId, String emailAddress,
		String vacationMessage) {

		try {
			String home = PropsUtil.get(PropsKeys.MAIL_HOOK_CYRUS_HOME);

			// Remove vacation cache

			new File(home + "/" + userId + ".vacation.cache").delete();

			// Update vacation message

			File vacation = new File(home + "/" + userId + ".vacation");

			if (Validator.isNull(vacationMessage)) {
				vacation.delete();
			}
			else {
				FileUtil.write(vacation, emailAddress + "\n" + vacationMessage);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void deleteEmailAddress(long companyId, long userId) {
		try {
			CyrusServiceUtil.deleteEmailAddress(companyId, userId);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void deleteUser(long companyId, long userId) {
		try {
			CyrusServiceUtil.deleteUser(userId);

			// Expect

			String deleteUserCmd = PropsUtil.get(
				PropsKeys.MAIL_HOOK_CYRUS_DELETE_USER);

			deleteUserCmd = StringUtil.replace(
				deleteUserCmd, "%1%", String.valueOf(userId));

			Future<?> future = ProcessUtil.execute(
				ProcessUtil.LOGGING_OUTPUT_PROCESSOR, deleteUserCmd);

			future.get();

			// Procmail

			String home = PropsUtil.get(PropsKeys.MAIL_HOOK_CYRUS_HOME);

			File file = new File(home + "/" + userId + ".procmail.blocked");

			if (file.exists()) {
				file.delete();
			}

			file = new File(home + "/" + userId + ".procmail.forward");

			if (file.exists()) {
				file.delete();
			}

			file = new File(home + "/" + userId + ".vacation");

			if (file.exists()) {
				file.delete();
			}

			file = new File(home + "/" + userId + ".vacation.cache");

			if (file.exists()) {
				file.delete();
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void updateBlocked(
		long companyId, long userId, List<String> blocked) {

		String home = PropsUtil.get(PropsKeys.MAIL_HOOK_CYRUS_HOME);

		File file = new File(home + "/" + userId + ".procmail.blocked");

		if ((blocked == null) || (blocked.size() == 0)) {
			file.delete();

			return;
		}

		StringBundler sb = new StringBundler(blocked.size() * 9);

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
			CyrusServiceUtil.updateEmailAddress(
				companyId, userId, emailAddress);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void updatePassword(long companyId, long userId, String password) {
		try {
			CyrusServiceUtil.updatePassword(companyId, userId, password);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CyrusHook.class);

}