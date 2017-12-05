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

package com.liferay.document.library.item.selector.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.document.library.item.selector.web.internal.DLItemSelectorView;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.item.selector.ItemSelectorReturnTypeResolverHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class DLItemSelectorViewDisplayContext<T extends ItemSelectorCriterion> {

	public DLItemSelectorViewDisplayContext(
		T itemSelectorCriterion, DLItemSelectorView<T> dlItemSelectorView,
		ItemSelectorReturnTypeResolverHandler
			itemSelectorReturnTypeResolverHandler,
		String itemSelectedEventName, boolean search, PortletURL portletURL,
		AssetVocabularyService assetVocabularyService,
		ClassNameLocalService classNameLocalService) {

		_itemSelectorCriterion = itemSelectorCriterion;
		_dlItemSelectorView = dlItemSelectorView;
		_itemSelectorReturnTypeResolverHandler =
			itemSelectorReturnTypeResolverHandler;
		_itemSelectedEventName = itemSelectedEventName;
		_search = search;
		_portletURL = portletURL;
		_assetVocabularyService = assetVocabularyService;
		_classNameLocalService = classNameLocalService;
	}

	public String[] getExtensions() {
		return _dlItemSelectorView.getExtensions();
	}

	public long getFolderId(HttpServletRequest request) {
		return ParamUtil.getLong(
			request, "folderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public T getItemSelectorCriterion() {
		return _itemSelectorCriterion;
	}

	public ItemSelectorReturnTypeResolver getItemSelectorReturnTypeResolver() {
		return _itemSelectorReturnTypeResolverHandler.
			getItemSelectorReturnTypeResolver(
				_itemSelectorCriterion, _dlItemSelectorView, FileEntry.class);
	}

	public String[] getMimeTypes() {
		return _dlItemSelectorView.getMimeTypes();
	}

	public PortletURL getPortletURL(
			HttpServletRequest request,
			LiferayPortletResponse liferayPortletResponse)
		throws PortletException {

		PortletURL portletURL = PortletURLUtil.clone(
			_portletURL, liferayPortletResponse);

		portletURL.setParameter(
			"folderId", String.valueOf(getFolderId(request)));
		portletURL.setParameter(
			"selectedTab", String.valueOf(getTitle(request.getLocale())));

		return portletURL;
	}

	public String getTitle(Locale locale) {
		return _dlItemSelectorView.getTitle(locale);
	}

	public PortletURL getUploadURL(
			HttpServletRequest request,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<AssetVocabulary> assetVocabularies =
			_assetVocabularyService.getGroupVocabularies(
				themeDisplay.getScopeGroupId());

		if (!assetVocabularies.isEmpty()) {
			long classNameId = _classNameLocalService.getClassNameId(
				DLFileEntryConstants.getClassName());

			for (AssetVocabulary assetVocabulary : assetVocabularies) {
				if (assetVocabulary.isRequired(
						classNameId,
						DLFileEntryTypeConstants.
							FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT)) {

					return null;
				}
			}
		}

		PortletURL portletURL = liferayPortletResponse.createActionURL(
			PortletKeys.DOCUMENT_LIBRARY);

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "/document_library/upload_file_entry");
		portletURL.setParameter(
			"folderId", String.valueOf(getFolderId(request)));

		return portletURL;
	}

	public boolean isSearch() {
		return _search;
	}

	private final AssetVocabularyService _assetVocabularyService;
	private final ClassNameLocalService _classNameLocalService;
	private final DLItemSelectorView<T> _dlItemSelectorView;
	private final String _itemSelectedEventName;
	private final T _itemSelectorCriterion;
	private final ItemSelectorReturnTypeResolverHandler
		_itemSelectorReturnTypeResolverHandler;
	private final PortletURL _portletURL;
	private final boolean _search;

}