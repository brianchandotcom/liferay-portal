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

package com.liferay.portal.model;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.webserver.WebServerServletTokenUtil;

/**
 * @author Amos Fong
 */
public class UserConstants {

	public static final int FULL_NAME_MAX_LENGTH = 75;

	public static final String LIST_VIEW_FLAT_ORGANIZATIONS =
		"flat-organizations";

	public static final String LIST_VIEW_FLAT_USER_GROUPS = "flat-user-groups";

	public static final String LIST_VIEW_FLAT_USERS = "flat-users";

	public static final String LIST_VIEW_TREE = "tree";

	public static final String USERS_EMAIL_ADDRESS_AUTO_SUFFIX = PropsUtil.get(
		PropsKeys.USERS_EMAIL_ADDRESS_AUTO_SUFFIX);

	/**
	 * @deprecated As of 7.0.0 replaced by {@link
	 * #getPortraitURL(String, boolean, long, long, String)}
	 */
	public static String getPortraitURL(
		String imagePath, boolean male, long portraitId) {

		if (portraitId <= 0) {
			return getPortraitURL(imagePath, male, 0, 0, StringPool.BLANK);
		}

		try {
			User user = UserLocalServiceUtil.fetchUserByPortraitId(portraitId);

			if (user == null) {
				return getPortraitURL(imagePath, male, 0, 0, StringPool.BLANK);
			}

			return getPortraitURL(
				imagePath, male, portraitId, user.getCompanyId(),
				user.getScreenName());
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e);
			}
		}

		return StringPool.BLANK;
	}

	public static String getPortraitURL(
		String imagePath, boolean male, long portraitId, long companyId,
		String screenName) {

		StringBundler sb = new StringBundler(9);

		sb.append(imagePath);
		sb.append("/user_");

		if (male) {
			sb.append("male");
		}
		else {
			sb.append("female");
		}

		if ((companyId > 0) && Validator.isNotNull(screenName)) {
			sb.append("_portrait?screenName=");
			sb.append(screenName);
			sb.append("&companyId=");
			sb.append(companyId);
			sb.append("&t=");
		}
		else {
			sb.append("_portrait?t=");
		}

		sb.append(WebServerServletTokenUtil.getToken(portraitId));

		return sb.toString();
	}

	private static Log _log = LogFactoryUtil.getLog(UserConstants.class);

}