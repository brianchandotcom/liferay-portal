/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.trash.test.util;

import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Cristina González
 */
public interface WhenIsIndexableBaseModel {

	public String getSearchKeywords();

	public int searchBaseModelsCount(Class<?> clazz, long groupId)
		throws Exception;

	public int searchTrashEntriesCount(
			String keywords, ServiceContext serviceContext)
		throws Exception;

}