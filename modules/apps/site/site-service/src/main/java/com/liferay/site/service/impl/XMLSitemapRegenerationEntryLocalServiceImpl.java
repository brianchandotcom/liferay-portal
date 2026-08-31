/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.site.model.XMLSitemapRegenerationEntry;
import com.liferay.site.service.base.XMLSitemapRegenerationEntryLocalServiceBaseImpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(
	property = "model.class.name=com.liferay.site.model.XMLSitemapRegenerationEntry",
	service = AopService.class
)
public class XMLSitemapRegenerationEntryLocalServiceImpl
	extends XMLSitemapRegenerationEntryLocalServiceBaseImpl {

	@Override
	public XMLSitemapRegenerationEntry addXMLSitemapRegenerationEntry(
		String assetTypeKey, long companyId, long groupId) {

		XMLSitemapRegenerationEntry xmlSitemapRegenerationEntry =
			xmlSitemapRegenerationEntryPersistence.fetchByG_C_A_First(
				groupId, companyId, assetTypeKey, null);

		if (xmlSitemapRegenerationEntry != null) {
			return xmlSitemapRegenerationEntry;
		}

		xmlSitemapRegenerationEntry =
			xmlSitemapRegenerationEntryPersistence.create(
				counterLocalService.increment());

		xmlSitemapRegenerationEntry.setGroupId(groupId);
		xmlSitemapRegenerationEntry.setCompanyId(companyId);
		xmlSitemapRegenerationEntry.setAssetTypeKey(assetTypeKey);

		return xmlSitemapRegenerationEntryPersistence.update(
			xmlSitemapRegenerationEntry);
	}

	@Override
	public void deleteXMLSitemapRegenerationEntries(long companyId) {
		xmlSitemapRegenerationEntryPersistence.removeByCompanyId(companyId);
	}

	@Override
	public List<XMLSitemapRegenerationEntry> getXMLSitemapRegenerationEntries(
		long companyId) {

		return xmlSitemapRegenerationEntryPersistence.findByCompanyId(
			companyId);
	}

	@Override
	public int getXMLSitemapRegenerationEntriesCount(long companyId) {
		return xmlSitemapRegenerationEntryPersistence.countByCompanyId(
			companyId);
	}

}