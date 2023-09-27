/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.manager;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Eudaldo Alonso
 */
public interface AssetLinkManager {

	public long[] getDirectLinksIds(long entryId);

	public long[] getRelatedDirectLinksIds(long entryId);

	public void updateLinks(long[] assetLinkEntryIds, long entryId, long userId)
		throws PortalException;

}