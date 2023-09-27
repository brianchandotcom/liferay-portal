/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface AssetTagFinder {

	public int countByG_N(long groupId, String name);

	public int countByG_C_N(long groupId, long classNameId, String name);

	public java.util.List<com.liferay.asset.kernel.model.AssetTag> findByG_C_N(
		long groupId, long classNameId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.asset.kernel.model.AssetTag> orderByComparator);

	public java.util.List<com.liferay.asset.kernel.model.AssetTag>
		findByG_N_S_E(
			long groupId, String name, int startPeriod, int endPeriod,
			int periodLength);

}