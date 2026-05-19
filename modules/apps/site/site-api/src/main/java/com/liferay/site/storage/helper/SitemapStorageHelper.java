/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.storage.helper;

import com.liferay.portal.kernel.exception.PortalException;

import java.io.InputStream;

/**
 * @author Cheryl Tang
 */
public interface SitemapStorageHelper {

	public void deleteSitemaps(long companyId, long groupId)
		throws PortalException;

	public void deleteSitemaps(
			long companyId, long groupId, String assetTypeKey)
		throws PortalException;

	public InputStream getSitemapInputStream(long companyId, long groupId)
		throws PortalException;

	public InputStream getSitemapInputStream(
			long companyId, long groupId, String assetTypeKey, int page)
		throws PortalException;

	public boolean hasSitemapFile(long companyId, long groupId)
		throws PortalException;

	public boolean hasSitemapFile(
			long companyId, long groupId, String assetTypeKey, int page)
		throws PortalException;

	public void storeSitemapFile(long companyId, long groupId, String xml)
		throws PortalException;

	public void storeSitemapFile(
			long companyId, long groupId, String assetTypeKey, int page,
			String xml)
		throws PortalException;

}