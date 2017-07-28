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

package com.liferay.commerce.product.content.web.display.context;

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.content.web.configuration.CPContentConfigurationHelper;
import com.liferay.commerce.product.content.web.configuration.CPContentPortletInstanceConfiguration;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class CPTypeDisplayContext {

	public CPTypeDisplayContext(
			CPAttachmentFileEntryService cpAttachmentFileEntryService,
			CPContentConfigurationHelper cpContentConfigurationHelper,
			CPDefinition cpDefinition, CPInstanceHelper cpInstanceHelper,
			HttpServletRequest httpServletRequest, Portal portal)
		throws ConfigurationException {

		this.cpAttachmentFileEntryService = cpAttachmentFileEntryService;
		this.cpContentConfigurationHelper = cpContentConfigurationHelper;
		this.cpDefinition = cpDefinition;
		this.cpInstanceHelper = cpInstanceHelper;
		this.httpServletRequest = httpServletRequest;
		this.portal = portal;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		cpContentPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				CPContentPortletInstanceConfiguration.class);
	}

	public CPDefinition getCPDefinition() {
		return cpDefinition;
	}

	public long getCPDefinitionId() {
		return cpDefinition.getCPDefinitionId();
	}

	public CPAttachmentFileEntry getDefaultImage() throws PortalException {
		long classNameId = portal.getClassNameId(CPDefinition.class);

		List<CPAttachmentFileEntry> cpAttachmentFileEntries =
			cpAttachmentFileEntryService.getCPAttachmentFileEntries(
				classNameId, cpDefinition.getCPDefinitionId(),
				CPConstants.ATTACHMENT_FILE_ENTRY_TYPE_IMAGE,
				WorkflowConstants.STATUS_APPROVED, 0, 1);

		if (cpAttachmentFileEntries.isEmpty()) {
			return null;
		}

		return cpAttachmentFileEntries.get(0);
	}

	public String getDisplayStyle() {
		return cpContentConfigurationHelper.getCPTypeDisplayStyle(
			cpContentPortletInstanceConfiguration,
			cpDefinition.getProductTypeName());
	}

	public long getDisplayStyleGroupId() {
		return cpContentConfigurationHelper.getCPTypeDisplayStyleGroupId(
			cpContentPortletInstanceConfiguration,
			cpDefinition.getProductTypeName());
	}

	public List<CPAttachmentFileEntry> getImages() throws PortalException {
		long classNameId = portal.getClassNameId(CPDefinition.class);

		return cpAttachmentFileEntryService.getCPAttachmentFileEntries(
			classNameId, cpDefinition.getCPDefinitionId(),
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

		return cpInstanceHelper.render(
			cpDefinition.getCPDefinitionId(), renderRequest, renderResponse);
	}

	protected final CPAttachmentFileEntryService cpAttachmentFileEntryService;
	protected final CPContentConfigurationHelper cpContentConfigurationHelper;
	protected final CPContentPortletInstanceConfiguration
		cpContentPortletInstanceConfiguration;
	protected final CPDefinition cpDefinition;
	protected final CPInstanceHelper cpInstanceHelper;
	protected final HttpServletRequest httpServletRequest;
	protected final Portal portal;

}