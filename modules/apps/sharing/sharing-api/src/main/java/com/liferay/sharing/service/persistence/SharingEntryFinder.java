/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface SharingEntryFinder {

	public int countByUserId(long userId, long classNameId);

	public java.util.List<com.liferay.sharing.model.SharingEntry> findByUserId(
		long userId, long classNameId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.sharing.model.SharingEntry> orderByComparator);

}