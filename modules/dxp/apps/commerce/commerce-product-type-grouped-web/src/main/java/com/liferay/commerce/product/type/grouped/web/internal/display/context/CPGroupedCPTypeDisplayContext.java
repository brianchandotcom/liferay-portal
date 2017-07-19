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

package com.liferay.commerce.product.type.grouped.web.internal.display.context;

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPFriendlyURLEntry;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPFriendlyURLEntryLocalService;
import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.commerce.product.type.grouped.service.CPDefinitionGroupedEntryLocalService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CPGroupedCPTypeDisplayContext {

	public CPGroupedCPTypeDisplayContext(
		CPDefinition cpDefinition,
		CPAttachmentFileEntryLocalService cpAttachmentFileEntryLocalService,
		CPDefinitionGroupedEntryLocalService
			cpDefinitionGroupedEntryLocalService,
		CPDefinitionLocalService cpDefinitionLocalService,
		CPFriendlyURLEntryLocalService cpFriendlyURLEntryLocalService,
		Portal portal, CPInstanceHelper cpInstanceHelper) {

		_cpDefinition = cpDefinition;
		_cpAttachmentFileEntryLocalService = cpAttachmentFileEntryLocalService;
		_cpDefinitionGroupedEntryLocalService =
			cpDefinitionGroupedEntryLocalService;
		_cpDefinitionLocalService = cpDefinitionLocalService;
		_cpFriendlyURLEntryLocalService = cpFriendlyURLEntryLocalService;
		_portal = portal;
		_cpInstanceHelper = cpInstanceHelper;
	}

	public CPDefinition getCPDefinition() {
		return _cpDefinition;
	}

	public CPDefinitionGroupedEntry getCPDefinitionGroupedEntry(
		long cpDefinitionId, long entryCPDefinitionId) {

		return _cpDefinitionGroupedEntryLocalService.
			fetchCPDefinitionGroupedEntryByC_E(
				cpDefinitionId, entryCPDefinitionId);
	}

	public List<CPDefinition> getCPDefinitions() {
		List<CPDefinition> cpDefinitions = new ArrayList<>();

		if (_cpDefinition != null) {
			List<CPDefinitionGroupedEntry> cpDefinitionGroupedEntries =
				_cpDefinitionGroupedEntryLocalService.
					getCPDefinitionGroupedEntriesByCPDefinitionId(
						_cpDefinition.getCPDefinitionId());

			for (CPDefinitionGroupedEntry cpDefinitionGroupedEntry :
					cpDefinitionGroupedEntries) {

				CPDefinition cpDefinition =
					_cpDefinitionLocalService.fetchCPDefinition(
						cpDefinitionGroupedEntry.getEntryCPDefinitionId());

				if (cpDefinition != null) {
					cpDefinitions.add(cpDefinition);
				}
			}

			return cpDefinitions;
		}

		return cpDefinitions;
	}

	public String getCPDefinitionURL(
			CPDefinition cpDefinition, ThemeDisplay themeDisplay)
		throws PortalException {

		long classNameId = _portal.getClassNameId(CPDefinition.class);

		CPFriendlyURLEntry cpFriendlyURLEntry =
			_cpFriendlyURLEntryLocalService.fetchCPFriendlyURLEntry(
				cpDefinition.getGroupId(), cpDefinition.getCompanyId(),
				classNameId, cpDefinition.getCPDefinitionId(),
				themeDisplay.getLanguageId(), true);

		String cpDefinitionURL =
			themeDisplay.getPortalURL() + CPConstants.SEPARATOR_PRODUCT_URL +
				cpFriendlyURLEntry.getUrlTitle();

		return cpDefinitionURL;
	}

	public CPAttachmentFileEntry getDefaultImage() throws PortalException {
		long classNameId = _portal.getClassNameId(CPDefinition.class);

		List<CPAttachmentFileEntry> cpAttachmentFileEntries =
			_cpAttachmentFileEntryLocalService.getCPAttachmentFileEntries(
				classNameId, _cpDefinition.getCPDefinitionId(),
				CPConstants.ATTACHMENT_FILE_ENTRY_TYPE_IMAGE,
				WorkflowConstants.STATUS_APPROVED, 0, 1);

		if (cpAttachmentFileEntries.isEmpty()) {
			return null;
		}

		return cpAttachmentFileEntries.get(0);
	}

	public List<CPAttachmentFileEntry> getImages() throws PortalException {
		long classNameId = _portal.getClassNameId(CPDefinition.class);

		return _cpAttachmentFileEntryLocalService.getCPAttachmentFileEntries(
			classNameId, _cpDefinition.getCPDefinitionId(),
			CPConstants.ATTACHMENT_FILE_ENTRY_TYPE_IMAGE,
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	public String getImageURL(FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		return DLUtil.getDownloadURL(
			fileEntry, fileEntry.getFileVersion(), themeDisplay,
			StringPool.BLANK);
	}

	public String renderOptions(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		CPDefinition cpDefinition = getCPDefinition();

		if (cpDefinition == null) {
			return StringPool.BLANK;
		}

		return _cpInstanceHelper.render(
			cpDefinition.getCPDefinitionId(), renderRequest, renderResponse);
	}

	private final CPAttachmentFileEntryLocalService
		_cpAttachmentFileEntryLocalService;
	private final CPDefinition _cpDefinition;
	private final CPDefinitionGroupedEntryLocalService
		_cpDefinitionGroupedEntryLocalService;
	private final CPDefinitionLocalService _cpDefinitionLocalService;
	private final CPFriendlyURLEntryLocalService
		_cpFriendlyURLEntryLocalService;
	private final CPInstanceHelper _cpInstanceHelper;
	private final Portal _portal;

}