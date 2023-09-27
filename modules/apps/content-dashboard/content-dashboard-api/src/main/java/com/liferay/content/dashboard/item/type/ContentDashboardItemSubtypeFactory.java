/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.item.type;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Cristina González
 */
public interface ContentDashboardItemSubtypeFactory<T> {

	public default ContentDashboardItemSubtype<T> create(long classPK)
		throws PortalException {

		return create(classPK, 0);
	}

	public ContentDashboardItemSubtype<T> create(
			long classPK, long entryClassPK)
		throws PortalException;

}