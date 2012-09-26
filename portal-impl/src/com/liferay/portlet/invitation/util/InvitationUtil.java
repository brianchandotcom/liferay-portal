/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.invitation.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.ContentUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 */
public class InvitationUtil {

	public static String getEmailMessageBody(PortletPreferences preferences) {
		String emailMessageBody = preferences.getValue(
			"emailMessageBody", StringPool.BLANK);

		if (Validator.isNotNull(emailMessageBody)) {
			return emailMessageBody;
		}
		else {
			return ContentUtil.get(
				PropsUtil.get(PropsKeys.INVITATION_EMAIL_MESSAGE_BODY));
		}
	}

	public static int getEmailMessageMaxRecipients() {
		return GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.INVITATION_EMAIL_MAX_RECIPIENTS));
	}

	public static String getEmailMessageSubject(
		PortletPreferences preferences) {

		String emailMessageSubject = preferences.getValue(
			"emailMessageSubject", StringPool.BLANK);

		if (Validator.isNotNull(emailMessageSubject)) {
			return emailMessageSubject;
		}
		else {
			return ContentUtil.get(
				PropsUtil.get(PropsKeys.INVITATION_EMAIL_MESSAGE_SUBJECT));
		}
	}

}