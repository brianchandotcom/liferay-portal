/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;
import com.liferay.users.admin.kernel.file.uploads.UserFileUploadsSettings;

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

	public static final int TYPE_DEFAULT_SERVICE_ACCOUNT = 2;

	public static final int TYPE_GUEST = 0;

	public static final int TYPE_ON_DEMAND_USER = 4;

	public static final int TYPE_REGULAR = 1;

	public static final int TYPE_SERVICE_ACCOUNT = 3;

	public static final long USER_ID_DEFAULT = 0;

	public static final String USERS_EMAIL_ADDRESS_AUTO_SUFFIX = PropsUtil.get(
		PropsKeys.USERS_EMAIL_ADDRESS_AUTO_SUFFIX);

	public static String getPortraitURL(
		String imagePath, boolean male, long portraitId, String userUuid) {

		StringBundler sb = new StringBundler(8);

		sb.append(imagePath);

		boolean contactMaleEnabled = PrefsPropsUtil.getBoolean(
			CompanyThreadLocal.getCompanyId(),
			PropsKeys.
				FIELD_ENABLE_COM_LIFERAY_PORTAL_KERNEL_MODEL_CONTACT_MALE);

		if (contactMaleEnabled) {
			if (male) {
				sb.append("/user_male_portrait");
			}
			else {
				sb.append("/user_female_portrait");
			}
		}
		else {
			sb.append("/user_portrait");
		}

		sb.append("?img_id=");
		sb.append(portraitId);

		if (_userFileUploadsSettings.isImageCheckToken()) {
			sb.append("&img_id_token=");
			sb.append(URLCodec.encodeURL(DigesterUtil.digest(userUuid)));
		}

		sb.append("&t=");
		sb.append(WebServerServletTokenUtil.getToken(portraitId));

		return sb.toString();
	}

	private static volatile UserFileUploadsSettings _userFileUploadsSettings =
		ServiceProxyFactory.newServiceTrackedInstance(
			UserFileUploadsSettings.class, UserConstants.class,
			"_userFileUploadsSettings", false);

}