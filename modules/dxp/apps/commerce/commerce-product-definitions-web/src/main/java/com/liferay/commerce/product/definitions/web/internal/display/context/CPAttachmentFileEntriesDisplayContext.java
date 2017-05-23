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

package com.liferay.commerce.product.definitions.web.internal.display.context;

import com.liferay.commerce.product.definitions.web.configuration.AttachmentConfiguration;
import com.liferay.commerce.product.definitions.web.display.context.BaseCPDefinitionsSearchContainerDisplayContext;
import com.liferay.commerce.product.definitions.web.portlet.action.ActionHelper;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class CPAttachmentFileEntriesDisplayContext extends
	BaseCPDefinitionsSearchContainerDisplayContext<CPAttachmentFileEntry> {

	public CPAttachmentFileEntriesDisplayContext(
			ActionHelper actionHelper,
			AttachmentConfiguration attachmentConfiguration,
			CPAttachmentFileEntryService cpAttachmentFileEntryService,
			CPDefinitionOptionRelService cpDefinitionOptionRelService,
			CPInstanceHelper cpInstanceHelper,
			HttpServletRequest httpServletRequest, ItemSelector itemSelector)
		throws PortalException {

		super(actionHelper, httpServletRequest, "CPAttachmentFileEntry");

		setDefaultOrderByCol("priority");
		setDefaultOrderByType("asc");

		_attachmentConfiguration = attachmentConfiguration;
		_cpAttachmentFileEntryService = cpAttachmentFileEntryService;
		_cpDefinitionOptionRelService = cpDefinitionOptionRelService;
		_cpInstanceHelper = cpInstanceHelper;
		_itemSelector = itemSelector;
	}

	public CPAttachmentFileEntry getCPAttachmentFileEntry()
		throws PortalException {

		if (_cpAttachmentFileEntry != null) {
			return _cpAttachmentFileEntry;
		}

		_cpAttachmentFileEntry = actionHelper.getCPAttachmentFileEntry(
			cpRequestHelper.getRenderRequest());

		return _cpAttachmentFileEntry;
	}

	public long getCPAttachmentFileEntryId() throws PortalException {
		CPAttachmentFileEntry cpAttachmentFileEntry =
			getCPAttachmentFileEntry();

		if (cpAttachmentFileEntry == null) {
			return 0;
		}

		return cpAttachmentFileEntry.getCPAttachmentFileEntryId();
	}

	public String[] getImageExtensions() {
		return _attachmentConfiguration.imageExtensions();
	}

	public long getImageMaxSize() {
		return _attachmentConfiguration.imageMaxSize();
	}

	public String getItemSelectorUrl() {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				cpRequestHelper.getRenderRequest());

		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			new ArrayList<>();

		desiredItemSelectorReturnTypes.add(
			new FileEntryItemSelectorReturnType());

		FileItemSelectorCriterion fileItemSelectorCriterion =
			new FileItemSelectorCriterion();

		fileItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			desiredItemSelectorReturnTypes);

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "addCPAttachmentFileEntry",
			fileItemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	@Override
	public SearchContainer<CPAttachmentFileEntry> getSearchContainer()
		throws PortalException {

		return null;
	}

	public boolean hasOptions() throws PortalException {
		int skuContributorCPDefinitionOptionRelCount =
			_cpDefinitionOptionRelService.
				getSkuContributorCPDefinitionOptionRelCount(
					getCPDefinitionId());

		if (skuContributorCPDefinitionOptionRelCount > 0) {
			return true;
		}

		return false;
	}

	public String renderOptions(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		CPAttachmentFileEntry cpAttachmentFileEntry =
			getCPAttachmentFileEntry();

		String json = null;

		if (cpAttachmentFileEntry != null) {
			json = cpAttachmentFileEntry.getJson();
		}

		return _cpInstanceHelper.render(
			getCPDefinitionId(), json, false, renderRequest, renderResponse);
	}

	private final AttachmentConfiguration _attachmentConfiguration;
	private CPAttachmentFileEntry _cpAttachmentFileEntry;
	private final CPAttachmentFileEntryService _cpAttachmentFileEntryService;
	private final CPDefinitionOptionRelService _cpDefinitionOptionRelService;
	private final CPInstanceHelper _cpInstanceHelper;
	private final ItemSelector _itemSelector;

}