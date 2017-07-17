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

package com.liferay.commerce.product.type.simple.internal.display.context;

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Marco Leo
 */
public class CPSimpleCPTypeDisplayContext {

	public CPSimpleCPTypeDisplayContext(
		CPDefinition cpDefinition,
		CPAttachmentFileEntryLocalService cpAttachmentFileEntryLocalService,
		Portal portal, CPInstanceHelper cpInstanceHelper) {

		_cpDefinition = cpDefinition;
		_cpAttachmentFileEntryLocalService = cpAttachmentFileEntryLocalService;
		_portal = portal;
		_cpInstanceHelper = cpInstanceHelper;
	}

	public CPDefinition getCPDefinition() {
		return _cpDefinition;
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
			fileEntry, fileEntry.getFileVersion(), themeDisplay, "");
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
	private final CPInstanceHelper _cpInstanceHelper;
	private final Portal _portal;

}