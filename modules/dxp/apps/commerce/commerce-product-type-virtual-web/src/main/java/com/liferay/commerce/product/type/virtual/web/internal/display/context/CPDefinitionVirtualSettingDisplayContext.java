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
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.web.internal.portlet.action.CPDefinitionVirtualSettingActionHelper;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;

import javax.portlet.PortletMode;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionVirtualSettingDisplayContext
	extends BaseCPDefinitionsDisplayContext {

	public CPDefinitionVirtualSettingDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			DLAppService dlAppService,
			JournalArticleService journalArticleService,
			CPDefinitionVirtualSettingActionHelper
				cpDefinitionVirtualSettingActionHelper,
			ItemSelector itemSelector)
		throws PortalException {

		super(actionHelper, httpServletRequest);

		_dlAppService = dlAppService;
		_journalArticleService = journalArticleService;
		_cpDefinitionVirtualSettingActionHelper =
			cpDefinitionVirtualSettingActionHelper;
		_itemSelector = itemSelector;
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

	public String getDownloadFileEntryURL() throws PortalException {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			getCPDefinitionVirtualSetting();

		if (cpDefinitionVirtualSetting == null) {
			return null;
		}

		long fileEntryId = cpDefinitionVirtualSetting.getFileEntryId();

		FileEntry fileEntry = _dlAppService.getFileEntry(fileEntryId);

		String downloadUrl = DLUtil.getDownloadURL(
			fileEntry, fileEntry.getLatestFileVersion(), themeDisplay,
			StringPool.BLANK, true, true);

		return downloadUrl;
	}

	public String getDownloadSampleFileEntryURL() throws PortalException {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			getCPDefinitionVirtualSetting();

		if (cpDefinitionVirtualSetting == null) {
			return null;
		}

		long fileEntryId = cpDefinitionVirtualSetting.getSampleFileEntryId();

		FileEntry fileEntry = _dlAppService.getFileEntry(fileEntryId);

		String downloadUrl = DLUtil.getDownloadURL(
			fileEntry, fileEntry.getLatestFileVersion(), themeDisplay,
			StringPool.BLANK, true, true);

		return downloadUrl;
	}

	public FileEntry getFileEntry() throws PortalException {
		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			getCPDefinitionVirtualSetting();

		if (cpDefinitionVirtualSetting != null) {
			long fileEntryId = cpDefinitionVirtualSetting.getFileEntryId();

			if (fileEntryId > 0) {
				return _dlAppService.getFileEntry(fileEntryId);
			}
		}

		return null;
	}

	public String getFileEntryItemSelectorURL() {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				cpRequestHelper.getRenderRequest());

		FileItemSelectorCriterion fileItemSelectorCriterion =
			new FileItemSelectorCriterion();

		fileItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new FileEntryItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "uploadCPDefinitionVirtualSetting",
			fileItemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	public JournalArticle getJournalArticle() throws PortalException {
		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			getCPDefinitionVirtualSetting();

		if (cpDefinitionVirtualSetting != null) {
			long journalArticleResourcePK =
				cpDefinitionVirtualSetting.
					getTermsOfUseJournalArticleResourcePrimKey();

			if (journalArticleResourcePK > 0) {
				return _journalArticleService.getLatestArticle(
					journalArticleResourcePK);
			}
		}

		return null;
	}

	public FileEntry getSampleFileEntry() throws PortalException {
		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			getCPDefinitionVirtualSetting();

		if (cpDefinitionVirtualSetting != null) {
			long fileEntryId =
				cpDefinitionVirtualSetting.getSampleFileEntryId();

			if (fileEntryId > 0) {
				return _dlAppService.getFileEntry(fileEntryId);
			}
		}

		return null;
	}

	@Override
	public String getScreenNavigationCategoryKey() {
		CPType cpType = null;

		try {
			cpType = getCPType();
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		if (cpType != null) {
			return cpType.getName();
		}

		return super.getScreenNavigationCategoryKey();
	}

	public String getTermsOfUseJournalArticleBrowserURL() throws Exception {
		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, JournalArticle.class.getName(),
			PortletProvider.Action.BROWSE);

		portletURL.setParameter("groupId", String.valueOf(getScopeGroupId()));
		portletURL.setParameter(
			"selectedGroupIds", StringUtil.merge(getSelectedGroupIds()));
		portletURL.setParameter(
			"typeSelection", JournalArticle.class.getName());
		portletURL.setParameter(
			"showNonindexable", String.valueOf(Boolean.TRUE));
		portletURL.setParameter("showScheduled", String.valueOf(Boolean.TRUE));
		portletURL.setParameter("eventName", "selectJournalArticle");
		portletURL.setPortletMode(PortletMode.VIEW);
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	protected long[] getSelectedGroupIds() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long[] selectedGroupIds =
			{getScopeGroupId(), themeDisplay.getCompanyGroupId()};

		return selectedGroupIds;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionVirtualSettingDisplayContext.class);

	private CPDefinitionVirtualSetting _cpDefinitionVirtualSetting;
	private final CPDefinitionVirtualSettingActionHelper
		_cpDefinitionVirtualSettingActionHelper;
	private final DLAppService _dlAppService;
	private final ItemSelector _itemSelector;
	private final JournalArticleService _journalArticleService;

}