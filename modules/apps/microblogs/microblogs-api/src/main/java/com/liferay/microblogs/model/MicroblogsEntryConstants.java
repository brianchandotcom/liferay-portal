/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.microblogs.model;

/**
 * @author     Jonathan Lee
 * @deprecated As of Athanasius (7.3.x), replaced by {@link
 *             com.liferay.microblogs.constants.MicroblogsEntryConstants}
 */
@Deprecated
public class MicroblogsEntryConstants {

	public static final int NOTIFICATION_TYPE_REPLY = 0;

	public static final int NOTIFICATION_TYPE_REPLY_TO_REPLIED = 1;

	public static final int NOTIFICATION_TYPE_REPLY_TO_TAGGED = 2;

	public static final int NOTIFICATION_TYPE_TAG = 3;

	public static final int NOTIFICATION_TYPE_UNKNOWN = -1;

	public static final int TYPE_EVERYONE = 0;

	public static final int TYPE_REPLY = 1;

	public static final int TYPE_REPOST = 2;

}