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

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.definitions.web.configuration.AttachmentsConfiguration;
import com.liferay.commerce.product.definitions.web.display.context.BaseCPDefinitionsSearchContainerDisplayContext;
import com.liferay.commerce.product.definitions.web.internal.util.CPDefinitionsPortletUtil;
import com.liferay.commerce.product.definitions.web.portlet.action.ActionHelper;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.document.library.display.context.DLMimeTypeDisplayContext;
import com.liferay.frontend.taglib.servlet.taglib.ManagementBarFilterItem;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
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
			AttachmentsConfiguration attachmentsConfiguration,
			CPAttachmentFileEntryService cpAttachmentFileEntryService,
			CPDefinitionOptionRelService cpDefinitionOptionRelService,
			CPInstanceHelper cpInstanceHelper,
			DLMimeTypeDisplayContext dlMimeTypeDisplayContext,
			HttpServletRequest httpServletRequest, ItemSelector itemSelector,
			Portal portal, WorkflowDefinitionLinkLocalService
				workflowDefinitionLinkLocalService)
		throws PortalException {

		super(
			actionHelper, httpServletRequest,
			CPAttachmentFileEntry.class.getSimpleName());

		setDefaultOrderByCol("priority");
		setDefaultOrderByType("asc");

		_attachmentsConfiguration = attachmentsConfiguration;
		_cpAttachmentFileEntryService = cpAttachmentFileEntryService;
		_cpDefinitionOptionRelService = cpDefinitionOptionRelService;
		_cpInstanceHelper = cpInstanceHelper;
		_dlMimeTypeDisplayContext = dlMimeTypeDisplayContext;
		_itemSelector = itemSelector;
		_portal = portal;
		_workflowDefinitionLinkLocalService =
			workflowDefinitionLinkLocalService;

		_type = ParamUtil.get(
			httpServletRequest, "type",
			CPConstants.ATTACHMENT_FILE_ENTRY_TYPE_IMAGE);
	}

	public String getAttachmentItemSelectorUrl() {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				cpRequestHelper.getRenderRequest());

		FileItemSelectorCriterion fileItemSelectorCriterion =
			new FileItemSelectorCriterion();

		fileItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new FileEntryItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "addCPAttachmentFileEntry",
			fileItemSelectorCriterion);

		return itemSelectorURL.toString();
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

	public List<CPDefinitionOptionRel> getCPDefinitionOptionRels()
		throws PortalException {

		CPDefinition cpDefinition = getCPDefinition();

		if (cpDefinition == null) {
			return Collections.emptyList();
		}

		return cpDefinition.getCPDefinitionOptionRels();
	}

	public String getCssClassFileMimeType(FileEntry fileEntry) {
		if (fileEntry == null) {
			return StringPool.BLANK;
		}

		return _dlMimeTypeDisplayContext.getCssClassFileMimeType(
			fileEntry.getMimeType());
	}

	public String getFileEntryName() throws PortalException {
		CPAttachmentFileEntry cpAttachmentFileEntry =
			getCPAttachmentFileEntry();

		FileEntry fileEntry = cpAttachmentFileEntry.getFileEntry();

		if (fileEntry == null) {
			return StringPool.BLANK;
		}

		return fileEntry.getFileName();
	}

	public String[] getImageExtensions() {
		return _attachmentsConfiguration.imageExtensions();
	}

	public String getImageItemSelectorUrl() {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				cpRequestHelper.getRenderRequest());

		ImageItemSelectorCriterion imageItemSelectorCriterion =
			new ImageItemSelectorCriterion();

		imageItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new FileEntryItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "addCPAttachmentFileEntry",
			imageItemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	public long getImageMaxSize() {
		return _attachmentsConfiguration.imageMaxSize();
	}

	@Override
	public List<ManagementBarFilterItem> getManagementBarStatusFilterItems()
		throws PortalException, PortletException {

		List<ManagementBarFilterItem> managementBarFilterItems =
			super.getManagementBarStatusFilterItems();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		int workflowDefinitionLinksCount =
			_workflowDefinitionLinkLocalService.getWorkflowDefinitionLinksCount(
				themeDisplay.getCompanyId(), WorkflowConstants.DEFAULT_GROUP_ID,
				CPAttachmentFileEntry.class.getName());

		if (workflowDefinitionLinksCount > 0) {
			managementBarFilterItems.add(
				getManagementBarFilterItem(WorkflowConstants.STATUS_PENDING));
			managementBarFilterItems.add(
				getManagementBarFilterItem(WorkflowConstants.STATUS_DENIED));
		}

		return managementBarFilterItems;
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		String toolbarItem = ParamUtil.getString(
			httpServletRequest, "toolbarItem");
		int type = ParamUtil.getInteger(httpServletRequest, "type");

		portletURL.setParameter(
			"mvcRenderCommandName", "viewAttachmentFileEntries");
		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(getCPDefinitionId()));
		portletURL.setParameter("toolbarItem", toolbarItem);
		portletURL.setParameter("type", String.valueOf(type));

		return portletURL;
	}

	@Override
	public SearchContainer<CPAttachmentFileEntry> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		long classNameId = _portal.getClassNameId(CPDefinition.class);

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage("no-attachments-were-found");

		OrderByComparator<CPAttachmentFileEntry> orderByComparator =
			CPDefinitionsPortletUtil.getCPAttachmentFileEntryOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		if (!isSearch()) {
			int total =
				_cpAttachmentFileEntryService.getCPAttachmentFileEntriesCount(
					classNameId, getCPDefinitionId(), _type, getStatus());

			searchContainer.setTotal(total);

			List<CPAttachmentFileEntry> results =
				_cpAttachmentFileEntryService.getCPAttachmentFileEntries(
					classNameId, getCPDefinitionId(), _type, getStatus(),
					searchContainer.getStart(), searchContainer.getEnd(),
					orderByComparator);

			searchContainer.setResults(results);
		}

		return searchContainer;
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

	public Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			parseCPAttachmentFileEntry(long cpAttachmentFileEntryId)
		throws PortalException {

		if (cpAttachmentFileEntryId <= 0) {
			return Collections.emptyMap();
		}

		CPAttachmentFileEntry cpAttachmentFileEntry =
			_cpAttachmentFileEntryService.fetchCPAttachmentFileEntry(
				cpAttachmentFileEntryId);

		if (cpAttachmentFileEntry == null) {
			return Collections.emptyMap();
		}

		return _cpInstanceHelper.parseJSONString(
			cpAttachmentFileEntry.getJson());
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

	private final AttachmentsConfiguration _attachmentsConfiguration;
	private CPAttachmentFileEntry _cpAttachmentFileEntry;
	private final CPAttachmentFileEntryService _cpAttachmentFileEntryService;
	private final CPDefinitionOptionRelService _cpDefinitionOptionRelService;
	private final CPInstanceHelper _cpInstanceHelper;
	private final DLMimeTypeDisplayContext _dlMimeTypeDisplayContext;
	private final ItemSelector _itemSelector;
	private final Portal _portal;
	private final WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;
	private final int _type;

}