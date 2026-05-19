/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.internal.storage.helper;

import com.liferay.document.library.kernel.store.DLStore;
import com.liferay.document.library.kernel.store.DLStoreRequest;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CompanyConstants;

import java.io.InputStream;

import java.nio.charset.StandardCharsets;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(service = SitemapStorageHelper.class)
public class SitemapStorageHelper {

	public void deleteSitemaps(long companyId, long groupId)
		throws PortalException {

		_dlStore.deleteDirectory(
			companyId, CompanyConstants.SYSTEM, _getDirName(groupId));
	}

	public void deleteSitemaps(
			long companyId, long groupId, String assetTypeKey)
		throws PortalException {

		_dlStore.deleteDirectory(
			companyId, CompanyConstants.SYSTEM,
			_getDirName(groupId, assetTypeKey));
	}

	public InputStream getSitemapInputStream(long companyId, long groupId)
		throws PortalException {

		return _dlStore.getFileAsStream(
			companyId, CompanyConstants.SYSTEM,
			_getSitemapIndexFileName(groupId), Store.VERSION_DEFAULT);
	}

	public InputStream getSitemapInputStream(
			long companyId, long groupId, String assetTypeKey, int page)
		throws PortalException {

		return _dlStore.getFileAsStream(
			companyId, CompanyConstants.SYSTEM,
			_getSitemapFileName(groupId, assetTypeKey, page),
			Store.VERSION_DEFAULT);
	}

	public boolean hasSitemapFile(long companyId, long groupId)
		throws PortalException {

		return _dlStore.hasFile(
			companyId, CompanyConstants.SYSTEM,
			_getSitemapIndexFileName(groupId), Store.VERSION_DEFAULT);
	}

	public boolean hasSitemapFile(
			long companyId, long groupId, String assetTypeKey, int page)
		throws PortalException {

		return _dlStore.hasFile(
			companyId, CompanyConstants.SYSTEM,
			_getSitemapFileName(groupId, assetTypeKey, page),
			Store.VERSION_DEFAULT);
	}

	public void storeSitemapFile(long companyId, long groupId, String xml)
		throws PortalException {

		_storeSitemapFile(companyId, _getSitemapIndexFileName(groupId), xml);
	}

	public void storeSitemapFile(
			long companyId, long groupId, String assetTypeKey, int page,
			String xml)
		throws PortalException {

		_storeSitemapFile(
			companyId, _getSitemapFileName(groupId, assetTypeKey, page), xml);
	}

	private String _getDirName(long groupId) {
		return StringBundler.concat(
			_DIR_NAME_PREFIX, StringPool.SLASH, groupId);
	}

	private String _getDirName(long groupId, String assetTypeKey) {
		return StringBundler.concat(
			_DIR_NAME_PREFIX, StringPool.SLASH, groupId, StringPool.SLASH,
			assetTypeKey);
	}

	private String _getSitemapFileName(
		long groupId, String assetTypeKey, int page) {

		return StringBundler.concat(
			_DIR_NAME_PREFIX, StringPool.SLASH, groupId, StringPool.SLASH,
			assetTypeKey, StringPool.SLASH, page, ".xml");
	}

	private String _getSitemapIndexFileName(long groupId) {
		return StringBundler.concat(
			_DIR_NAME_PREFIX, StringPool.SLASH, groupId, "/sitemap-index.xml");
	}

	private void _storeSitemapFile(long companyId, String fileName, String xml)
		throws PortalException {

		byte[] bytes = xml.getBytes(StandardCharsets.UTF_8);

		DLStoreRequest dlStoreRequest = DLStoreRequest.builder(
			companyId, CompanyConstants.SYSTEM, fileName
		).size(
			bytes.length
		).versionLabel(
			Store.VERSION_DEFAULT
		).build();

		_dlStore.addFile(dlStoreRequest, bytes);
	}

	private static final String _DIR_NAME_PREFIX = "sitemaps";

	@Reference
	private DLStore _dlStore;

}