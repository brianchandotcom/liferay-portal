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

package com.liferay.asset.list.web.internal.servlet.taglib.util;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.info.display.url.provider.InfoEditURLProvider;
import com.liferay.info.display.url.provider.InfoEditURLProviderTracker;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.search.InfoSearchClassMapperTracker;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class ListItemsActionDropdownItems {

	public ListItemsActionDropdownItems(
		AssetDisplayPageFriendlyURLProvider assetDisplayPageFriendlyURLProvider,
		DLAppService dlAppService,
		InfoEditURLProviderTracker infoEditURLProviderTracker,
		InfoItemServiceTracker infoItemServiceTracker,
		InfoSearchClassMapperTracker infoSearchClassMapperTracker,
		HttpServletRequest httpServletRequest) {

		_assetDisplayPageFriendlyURLProvider =
			assetDisplayPageFriendlyURLProvider;
		_dlAppService = dlAppService;
		_infoEditURLProviderTracker = infoEditURLProviderTracker;
		_infoItemServiceTracker = infoItemServiceTracker;
		_infoSearchClassMapperTracker = infoSearchClassMapperTracker;

		_httpServletRequest = httpServletRequest;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems(
			String className, Object object)
		throws Exception {

		return DropdownItemListBuilder.add(
			_getViewDisplayPageActionUnsafeConsumer(className, object)
		).add(
			_getEditContentActionUnsafeConsumer(className, object)
		).build();
	}

	public String getViewDisplayPageURL(String className, Object object)
		throws Exception {

		if (_assetDisplayPageFriendlyURLProvider == null) {
			return null;
		}

		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class, className);

		InfoItemFieldValues infoItemFieldValues =
			infoItemFieldValuesProvider.getInfoItemFieldValues(object);

		InfoItemReference infoItemReference =
			infoItemFieldValues.getInfoItemReference();

		long classPK = infoItemReference.getClassPK();

		if (object instanceof AssetEntry) {
			AssetEntry assetEntry = (AssetEntry)object;

			classPK = assetEntry.getClassPK();
			className = assetEntry.getClassName();
		}

		String viewDisplayPageURL =
			_assetDisplayPageFriendlyURLProvider.getFriendlyURL(
				_infoSearchClassMapperTracker.getClassName(className), classPK,
				_themeDisplay);

		return HttpComponentsUtil.setParameter(
			viewDisplayPageURL, "p_l_back_url", _getRedirect());
	}

	private Object _getAssetEntryObject(AssetEntry assetEntry)
		throws Exception {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				assetEntry.getClassName());

		if (assetRendererFactory == null) {
			return null;
		}

		AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(
			assetEntry.getClassPK());

		if (assetRenderer == null) {
			return null;
		}

		return assetRenderer.getAssetObject();
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getEditContentActionUnsafeConsumer(String className, Object object)
		throws Exception {

		String editContentURL = _getEditContentURL(className, object);

		return dropdownItem -> {
			dropdownItem.putData("action", "editContent");
			dropdownItem.putData("editContentURL", editContentURL);
			dropdownItem.setDisabled(Validator.isNull(editContentURL));
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "edit-content"));
		};
	}

	private String _getEditContentURL(String className, Object object)
		throws Exception {

		if (object instanceof AssetEntry) {
			AssetEntry assetEntry = (AssetEntry)object;

			className = assetEntry.getClassName();
			object = _getAssetEntryObject(assetEntry);
		}

		if (Objects.equals(className, DLFileEntryConstants.getClassName())) {
			className = FileEntry.class.getName();

			LiferayFileEntry liferayFileEntry = (LiferayFileEntry)object;

			object = _dlAppService.getFileEntry(
				liferayFileEntry.getFileEntryId());
		}

		InfoEditURLProvider<Object> infoEditURLProvider =
			_infoEditURLProviderTracker.getInfoEditURLProvider(className);

		if (infoEditURLProvider == null) {
			return null;
		}

		String editContentURL = infoEditURLProvider.getURL(
			object, _httpServletRequest);

		return HttpComponentsUtil.setParameter(
			editContentURL, "redirect", _getRedirect());
	}

	private String _getRedirect() {
		if (Validator.isNotNull(_redirect)) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		return _redirect;
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getViewDisplayPageActionUnsafeConsumer(
				String className, Object object)
		throws Exception {

		String viewDisplayPageURL = getViewDisplayPageURL(className, object);

		return dropdownItem -> {
			dropdownItem.putData("action", "viewDisplayPage");
			dropdownItem.putData("viewDisplayPageURL", viewDisplayPageURL);
			dropdownItem.setDisabled(Validator.isNull(viewDisplayPageURL));
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "view-display-page"));
		};
	}

	private final AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;
	private final DLAppService _dlAppService;
	private final HttpServletRequest _httpServletRequest;
	private final InfoEditURLProviderTracker _infoEditURLProviderTracker;
	private final InfoItemServiceTracker _infoItemServiceTracker;
	private final InfoSearchClassMapperTracker _infoSearchClassMapperTracker;
	private String _redirect;
	private final ThemeDisplay _themeDisplay;

}