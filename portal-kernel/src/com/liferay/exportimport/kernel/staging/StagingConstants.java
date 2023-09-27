/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.staging;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

/**
 * @author     Raymond Augé
 * @deprecated As of Athanasius (7.3.x), replaced by {@link
 *             com.liferay.exportimport.kernel.staging.constants.StagingConstants}
 */
@Deprecated
public class StagingConstants {

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		PropsUtil.get("lock.expiration.time." + Staging.class.getName()));

	public static final String RANGE_FROM_LAST_PUBLISH_DATE_CHANGESET_NAME =
		"RANGE_FROM_LAST_PUBLISH_DATE_CHANGESET_NAME";

	public static final String STAGED_PORTLET = "staged-portlet_";

	public static final String STAGED_PREFIX = "staged--";

	public static final int TYPE_LOCAL_STAGING = 1;

	public static final int TYPE_NOT_STAGED = 0;

	public static final int TYPE_REMOTE_STAGING = 2;

}