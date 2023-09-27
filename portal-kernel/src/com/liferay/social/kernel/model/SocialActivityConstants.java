/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.kernel.model;

/**
 * @author Zsolt Berentey
 * @author Brian Wing Shun Chan
 */
public class SocialActivityConstants {

	public static final int TYPE_ADD_ATTACHMENT = 10006;

	public static final int TYPE_ADD_COMMENT = 10005;

	public static final int TYPE_ADD_VOTE = 10004;

	public static final int TYPE_DELETE = 10000;

	public static final int TYPE_MOVE_ATTACHMENT_TO_TRASH = 10009;

	public static final int TYPE_MOVE_TO_TRASH = 10007;

	public static final int TYPE_RESTORE_ATTACHMENT_FROM_TRASH = 10010;

	public static final int TYPE_RESTORE_FROM_TRASH = 10008;

	public static final int TYPE_REVOKE_VOTE = 10011;

	public static final int TYPE_SUBSCRIBE = 10002;

	public static final int TYPE_UNSUBSCRIBE = 10003;

	/**
	 * @see com.liferay.portlet.social.service.impl.SocialActivityLocalServiceImpl#isLogActivity(
	 *      SocialActivity)
	 */
	public static final int TYPE_VIEW = 10001;

}