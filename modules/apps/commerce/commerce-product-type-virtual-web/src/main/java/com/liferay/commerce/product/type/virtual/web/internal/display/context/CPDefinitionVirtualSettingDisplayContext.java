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

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.product.display.context.BaseCPDefinitionsDisplayContext;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.portlet.action.ActionHelper;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.virtual.constants.VirtualCPTypeConstants;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.web.internal.portlet.action.helper.CPDefinitionVirtualSettingActionHelper;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
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
		DLAppService dlAppService, JournalArticleService journalArticleService,
		CPDefinitionVirtualSettingActionHelper
			cpDefinitionVirtualSettingActionHelper,
		ItemSelector itemSelector) {

		super(actionHelper, httpServletRequest);

		_dlAppService = dlAppService;
		_journalArticleService = journalArticleService;
		_cpDefinitionVirtualSettingActionHelper =
			cpDefinitionVirtualSettingActionHelper;
		_itemSelector = itemSelector;
	}

	public int[] getActivationStatuses() {
		return VirtualCPTypeConstants.ACTIVATION_STATUSES;
	}

	public String getActivationStatusLabel(int status) {
		return CommerceOrderConstants.getOrderStatusLabel(status);
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

	public CPInstance getCPInstance() throws PortalException {
		if (_cpInstance != null) {
			return _cpInstance;
		}

		_cpInstance = actionHelper.getCPInstance(
			cpRequestHelper.getRenderRequest());

		return _cpInstance;
	}

	public long getCPInstanceId() throws PortalException {
		long cpInstanceId = 0;

		CPInstance cpInstance = getCPInstance();

		if (cpInstance != null) {
			cpInstanceId = cpInstance.getCPInstanceId();
		}

		return cpInstanceId;
	}

	public String getDownloadFileEntryURL() throws PortalException {
		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			getCPDefinitionVirtualSetting();

		if (cpDefinitionVirtualSetting == null) {
			return null;
		}

		FileEntry fileEntry = _dlAppService.getFileEntry(
			cpDefinitionVirtualSetting.getFileEntryId());

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return DLUtil.getDownloadURL(
			fileEntry, fileEntry.getLatestFileVersion(), themeDisplay,
			StringPool.BLANK, true, true);
	}

	public String getDownloadSampleFileEntryURL() throws PortalException {
		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			getCPDefinitionVirtualSetting();

		if (cpDefinitionVirtualSetting == null) {
			return null;
		}

		long fileEntryId = cpDefinitionVirtualSetting.getSampleFileEntryId();

		FileEntry fileEntry = _dlAppService.getFileEntry(fileEntryId);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return DLUtil.getDownloadURL(
			fileEntry, fileEntry.getLatestFileVersion(), themeDisplay,
			StringPool.BLANK, true, true);
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
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		if (cpType != null) {
			return cpType.getName();
		}

		return super.getScreenNavigationCategoryKey();
	}

	public String getTermsOfUseJournalArticleBrowserURL() throws Exception {
		return PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				httpServletRequest, JournalArticle.class.getName(),
				PortletProvider.Action.BROWSE)
		).setParameter(
			"eventName", "selectJournalArticle"
		).setParameter(
			"groupId", getScopeGroupId()
		).setParameter(
			"selectedGroupIds", StringUtil.merge(_getSelectedGroupIds())
		).setParameter(
			"showNonindexable", Boolean.TRUE
		).setParameter(
			"showScheduled", Boolean.TRUE
		).setParameter(
			"typeSelection", JournalArticle.class.getName()
		).setPortletMode(
			PortletMode.VIEW
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	private long[] _getSelectedGroupIds() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return new long[] {getScopeGroupId(), themeDisplay.getCompanyGroupId()};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionVirtualSettingDisplayContext.class);

	private CPDefinitionVirtualSetting _cpDefinitionVirtualSetting;
	private final CPDefinitionVirtualSettingActionHelper
		_cpDefinitionVirtualSettingActionHelper;
	private CPInstance _cpInstance;
	private final DLAppService _dlAppService;
	private final ItemSelector _itemSelector;
	private final JournalArticleService _journalArticleService;

}