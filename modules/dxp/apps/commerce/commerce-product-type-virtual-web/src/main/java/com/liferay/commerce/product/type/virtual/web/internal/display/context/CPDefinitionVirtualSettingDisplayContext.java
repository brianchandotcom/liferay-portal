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

package com.liferay.commerce.product.type.virtual.web.internal.display.context;

import com.liferay.commerce.product.definitions.web.display.context.BaseCPDefinitionsDisplayContext;
import com.liferay.commerce.product.definitions.web.portlet.action.ActionHelper;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.service.CPDefinitionVirtualSettingService;
import com.liferay.commerce.product.type.virtual.web.internal.portlet.action.CPDefinitionVirtualSettingActionHelper;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletMode;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionVirtualSettingDisplayContext
	extends BaseCPDefinitionsDisplayContext<FileEntry> {

	public CPDefinitionVirtualSettingDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CPDefinitionVirtualSettingService cpDefinitionVirtualSettingService,
			DLAppService dlAppService,
			JournalArticleService journalArticleService,
			CPDefinitionVirtualSettingActionHelper
				cpDefinitionVirtualSettingActionHelper,
			ItemSelector itemSelector)
		throws PortalException {

		super(actionHelper, httpServletRequest, "CPDefinitionVirtualSetting");

		_cpDefinitionVirtualSettingService = cpDefinitionVirtualSettingService;
		_dlAppService = dlAppService;
		_journalArticleService = journalArticleService;
		_cpDefinitionVirtualSettingActionHelper =
			cpDefinitionVirtualSettingActionHelper;
		_itemSelector = itemSelector;
	}

	public String getAssetBrowserURL() throws Exception {
		PortletURL assetBrowserURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, JournalArticle.class.getName(),
			PortletProvider.Action.BROWSE);

		assetBrowserURL.setParameter(
			"groupId", String.valueOf(getScopeGroupId()));
		assetBrowserURL.setParameter(
			"selectedGroupIds", String.valueOf(getScopeGroupId()));
		assetBrowserURL.setParameter(
			"typeSelection", JournalArticle.class.getName());
		assetBrowserURL.setParameter(
			"showNonindexable", String.valueOf(Boolean.TRUE));
		assetBrowserURL.setParameter(
			"showScheduled", String.valueOf(Boolean.TRUE));
		assetBrowserURL.setParameter("eventName", "selectJournalArticle");
		assetBrowserURL.setPortletMode(PortletMode.VIEW);
		assetBrowserURL.setWindowState(LiferayWindowState.POP_UP);

		return assetBrowserURL.toString();
	}

	public CPDefinitionVirtualSetting getCPDefinitionVirtualSetting()
		throws PortalException {

		if (_cpDefinitionVirtualSetting != null) {
			return _cpDefinitionVirtualSetting;
		}

		_cpDefinitionVirtualSetting =
			_cpDefinitionVirtualSettingActionHelper.
				getCPDefinitionVirtualSetting(
					cpRequestHelper.getRenderRequest());

		return _cpDefinitionVirtualSetting;
	}

	public long getCPDefinitionVirtualSettingFileEntryId()
		throws PortalException {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			getCPDefinitionVirtualSetting();

		if (cpDefinitionVirtualSetting == null) {
			return 0;
		}

		return cpDefinitionVirtualSetting.getFileEntryId();
	}

	public long getCPDefinitionVirtualSettingJournalArticleId()
		throws PortalException {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			getCPDefinitionVirtualSetting();

		if (cpDefinitionVirtualSetting == null) {
			return 0;
		}

		return cpDefinitionVirtualSetting.getTermsOfUseJournalArticleId();
	}

	public long getCPDefinitionVirtualSettingSampleFileEntryId()
		throws PortalException {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			getCPDefinitionVirtualSetting();

		if (cpDefinitionVirtualSetting == null) {
			return 0;
		}

		return cpDefinitionVirtualSetting.getSampleFileEntryId();
	}

	public String getDownloadFileEntryURL() throws PortalException {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		FileEntry fileEntry = _dlAppService.getFileEntry(
			getCPDefinitionVirtualSettingFileEntryId());

		String url = DLUtil.getDownloadURL(
			fileEntry, fileEntry.getLatestFileVersion(), themeDisplay,
			StringPool.BLANK, true, true);

		return url;
	}

	public String getItemSelectorURL() {
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
			requestBackedPortletURLFactory, "uploadCPDefinitionVirtualSetting",
			fileItemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	public SearchContainer<JournalArticle> getJournalArticleSearchContainer()
		throws PortalException {

		SearchContainer<JournalArticle> searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		List<JournalArticle> results = new ArrayList<>();

		if (getCPDefinitionVirtualSetting() != null) {
			JournalArticle journalArticle =
				_journalArticleService.getLatestArticle(
					getCPDefinitionVirtualSettingJournalArticleId());

			results.add(journalArticle);
		}

		int total = results.size();

		searchContainer.setResults(results);
		searchContainer.setTotal(total);

		return searchContainer;
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "editProductDefinitionVirtualSetting");

		return portletURL;
	}

	public SearchContainer<FileEntry> getSampleFileEntrySearchContainer()
		throws PortalException {

		SearchContainer<FileEntry> searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		List<FileEntry> results = new ArrayList<>();

		if (getCPDefinitionVirtualSetting() != null) {
			FileEntry fileEntry = _dlAppService.getFileEntry(
				getCPDefinitionVirtualSettingSampleFileEntryId());

			results.add(fileEntry);
		}

		int total = results.size();

		searchContainer.setResults(results);
		searchContainer.setTotal(total);

		return searchContainer;
	}

	@Override
	public SearchContainer<FileEntry> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		SearchContainer<FileEntry> searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		List<FileEntry> results = new ArrayList<>();

		if (getCPDefinitionVirtualSetting() != null) {
			FileEntry fileEntry = _dlAppService.getFileEntry(
				getCPDefinitionVirtualSettingFileEntryId());

			results.add(fileEntry);
		}

		int total = results.size();

		searchContainer.setResults(results);
		searchContainer.setTotal(total);

		this.searchContainer = searchContainer;

		return this.searchContainer;
	}

	private CPDefinitionVirtualSetting _cpDefinitionVirtualSetting;
	private final CPDefinitionVirtualSettingActionHelper
		_cpDefinitionVirtualSettingActionHelper;
	private final CPDefinitionVirtualSettingService
		_cpDefinitionVirtualSettingService;
	private final DLAppService _dlAppService;
	private final ItemSelector _itemSelector;
	private final JournalArticleService _journalArticleService;

}