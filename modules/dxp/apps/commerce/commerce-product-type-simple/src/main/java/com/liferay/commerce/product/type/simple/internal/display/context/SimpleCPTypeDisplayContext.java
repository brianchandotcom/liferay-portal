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
import com.liferay.commerce.product.content.web.configuration.CPContentPortletInstanceConfiguration;
import com.liferay.commerce.product.display.context.util.CPRequestHelper;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class SimpleCPTypeDisplayContext {

	public SimpleCPTypeDisplayContext(
			CPDefinition cpDefinition,
			CPAttachmentFileEntryLocalService cpAttachmentFileEntryLocalService,
			HttpServletRequest httpServletRequest, Portal portal,
			CPInstanceHelper cpInstanceHelper)
		throws ConfigurationException {

		_cpDefinition = cpDefinition;
		_cpAttachmentFileEntryLocalService = cpAttachmentFileEntryLocalService;
		_httpServletRequest = httpServletRequest;
		_portal = portal;
		_cpInstanceHelper = cpInstanceHelper;

		CPRequestHelper cpRequestHelper = new CPRequestHelper(
			httpServletRequest);

		_liferayPortletRequest = cpRequestHelper.getLiferayPortletRequest();
		_liferayPortletResponse = cpRequestHelper.getLiferayPortletResponse();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_cpContentPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				CPContentPortletInstanceConfiguration.class);
	}

	public CPDefinition getCPDefinition() {
		return _cpDefinition;
	}

	public int getCPTypeIndex() {
		List<String> cpTypes = Arrays.asList(
			_cpContentPortletInstanceConfiguration.cpTypes());

		return cpTypes.indexOf(_cpDefinition.getProductTypeName());
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

	public String getDisplayStyle() {
		int cpTypeIndex = getCPTypeIndex();

		if (cpTypeIndex <= 0) {
			return StringPool.BLANK;
		}

		return _cpContentPortletInstanceConfiguration.
			displayStyles()[cpTypeIndex];
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId > 0) {
			return _displayStyleGroupId;
		}

		int cpTypeIndex = getCPTypeIndex();

		if (cpTypeIndex >= 0) {
			_displayStyleGroupId =
				_cpContentPortletInstanceConfiguration.
					displayStyleGroupIds()[cpTypeIndex];
		}

		if (_displayStyleGroupId <= 0) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			_displayStyleGroupId = themeDisplay.getScopeGroupId();
		}

		return _displayStyleGroupId;
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

	public SearchContainer<CPDefinition> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		_searchContainer = new SearchContainer<>(
			_liferayPortletRequest, portletURL, null, null);

		_searchContainer.setEmptyResultsMessage("no-products-were-found");

		List<CPDefinition> cpDefinitions = new ArrayList<>();

		cpDefinitions.add(_cpDefinition);

		_searchContainer.setTotal(1);
		_searchContainer.setResults(cpDefinitions);

		return _searchContainer;
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
	private final CPContentPortletInstanceConfiguration
		_cpContentPortletInstanceConfiguration;
	private final CPDefinition _cpDefinition;
	private final CPInstanceHelper _cpInstanceHelper;
	private long _displayStyleGroupId;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final Portal _portal;
	private SearchContainer<CPDefinition> _searchContainer;

}