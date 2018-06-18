/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.product.internal.catalog;

import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPFriendlyURLEntryLocalService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.Portal;

import java.util.Locale;
import java.util.Map;

/**
 * @author Andrea Di Giorgi
 */
public class DatabaseCPCatalogEntryImpl implements CPCatalogEntry {

	public DatabaseCPCatalogEntryImpl(
			CPDefinition cpDefinition,
			CPDefinitionLocalService cpDefinitionLocalService,
			CPFriendlyURLEntryLocalService cpFriendlyURLEntryLocalService,
			Locale locale, Portal portal)
		throws PortalException {

		_cpDefinition = cpDefinition;

		_defaultImageFileURL = _getDefaultImageFileURL(
			cpDefinition, cpDefinitionLocalService);
		_languageId = LanguageUtil.getLanguageId(locale);

		_url = _getURL(
			cpDefinition, cpFriendlyURLEntryLocalService, _languageId, portal);
	}

	@Override
	public long getCPDefinitionId() {
		return _cpDefinition.getCPDefinitionId();
	}

	@Override
	public String getDefaultImageFileUrl() {
		return _defaultImageFileURL;
	}

	@Override
	public double getDepth() {
		return _cpDefinition.getDepth();
	}

	@Override
	public String getDescription() {
		return _cpDefinition.getDescription(_languageId);
	}

	@Override
	public double getHeight() {
		return _cpDefinition.getHeight();
	}

	@Override
	public String getName() {
		return _cpDefinition.getName(_languageId);
	}

	@Override
	public String getProductTypeName() {
		return _cpDefinition.getProductTypeName();
	}

	@Override
	public String getShortDescription() {
		return _cpDefinition.getShortDescription(_languageId);
	}

	@Override
	public String getSku() {
		return null;
	}

	@Override
	public String getUrl() {
		return _url;
	}

	@Override
	public boolean isIgnoreSKUCombinations() {
		return _cpDefinition.isIgnoreSKUCombinations();
	}

	private static String _getDefaultImageFileURL(
			CPDefinition cpDefinition,
			CPDefinitionLocalService cpDefinitionLocalService)
		throws PortalException {

		CPAttachmentFileEntry cpAttachmentFileEntry =
			cpDefinitionLocalService.getDefaultImage(
				cpDefinition.getCPDefinitionId());

		if (cpAttachmentFileEntry == null) {
			return null;
		}

		FileEntry fileEntry = cpAttachmentFileEntry.getFileEntry();

		return DLUtil.getDownloadURL(
			fileEntry, fileEntry.getFileVersion(), null, null);
	}

	private static String _getURL(
		CPDefinition cpDefinition,
		CPFriendlyURLEntryLocalService cpFriendlyURLEntryLocalService,
		String languageId, Portal portal) {

		long classNameId = portal.getClassNameId(CPDefinition.class);

		Map<String, String> languageIdToUrlTitleMap =
			cpFriendlyURLEntryLocalService.getLanguageIdToUrlTitleMap(
				cpDefinition.getGroupId(), classNameId,
				cpDefinition.getCPDefinitionId());

		return languageIdToUrlTitleMap.get(languageId);
	}

	private final CPDefinition _cpDefinition;
	private final String _defaultImageFileURL;
	private final String _languageId;
	private final String _url;

}