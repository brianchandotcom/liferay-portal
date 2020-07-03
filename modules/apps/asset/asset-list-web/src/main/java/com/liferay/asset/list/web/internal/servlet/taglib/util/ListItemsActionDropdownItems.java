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
import com.liferay.asset.info.display.url.provider.AssetInfoEditURLProvider;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.item.InfoItemClassPKReference;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class ListItemsActionDropdownItems {

	public ListItemsActionDropdownItems(
		AssetDisplayPageFriendlyURLProvider assetDisplayPageFriendlyURLProvider,
		AssetInfoEditURLProvider assetInfoEditURLProvider,
		InfoDisplayContributorTracker infoDisplayContributorTracker,
		InfoItemServiceTracker infoItemServiceTracker,
		HttpServletRequest httpServletRequest) {

		_assetDisplayPageFriendlyURLProvider =
			assetDisplayPageFriendlyURLProvider;
		_assetInfoEditURLProvider = assetInfoEditURLProvider;
		_infoDisplayContributorTracker = infoDisplayContributorTracker;
		_infoItemServiceTracker = infoItemServiceTracker;

		_httpServletRequest = httpServletRequest;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems(
			String className, Object object)
		throws Exception {

		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class, className);

		InfoItemFieldValues infoFormValues =
			infoItemFieldValuesProvider.getInfoItemFieldValues(object);

		InfoItemClassPKReference infoItemClassPKReference =
			infoFormValues.getInfoItemClassPKReference();

		long classPK = infoItemClassPKReference.getClassPK();

		String objectClassName = className;

		if (object instanceof AssetEntry) {
			AssetEntry assetEntry = (AssetEntry)object;

			classPK = assetEntry.getClassPK();
			objectClassName = assetEntry.getClassName();
		}

		return DropdownItemListBuilder.add(
			_getViewDisplayPageActionUnsafeConsumer(objectClassName, classPK)
		).add(
			_getEditContentActionUnsafeConsumer(objectClassName, classPK)
		).build();
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getEditContentActionUnsafeConsumer(String className, long classPK) {

		String editContentURL = _getEditContentURL(className, classPK);

		return dropdownItem -> {
			dropdownItem.putData("action", "editContent");
			dropdownItem.putData("editContentURL", editContentURL);
			dropdownItem.setDisabled(Validator.isNull(editContentURL));
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "edit-content"));
		};
	}

	private String _getEditContentURL(String className, long classPK) {
		String editContentURL = _assetInfoEditURLProvider.getURL(
			className, classPK, _httpServletRequest);

		return HttpUtil.setParameter(
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
				String className, long classPK)
		throws Exception {

		String viewDisplayPageURL = _getViewDisplayPageURL(className, classPK);

		return dropdownItem -> {
			dropdownItem.putData("action", "viewDisplayPage");
			dropdownItem.putData("viewDisplayPageURL", viewDisplayPageURL);
			dropdownItem.setDisabled(Validator.isNull(viewDisplayPageURL));
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "view-display-page"));
		};
	}

	private String _getViewDisplayPageURL(String className, long classPK)
		throws Exception {

		if (_assetDisplayPageFriendlyURLProvider == null) {
			return null;
		}

		String viewDisplayPageURL =
			_assetDisplayPageFriendlyURLProvider.getFriendlyURL(
				className, classPK, _themeDisplay);

		return HttpUtil.setParameter(
			viewDisplayPageURL, "p_l_back_url", _getRedirect());
	}

	private final AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;
	private final AssetInfoEditURLProvider _assetInfoEditURLProvider;
	private final HttpServletRequest _httpServletRequest;
	private final InfoDisplayContributorTracker _infoDisplayContributorTracker;
	private final InfoItemServiceTracker _infoItemServiceTracker;
	private String _redirect;
	private final ThemeDisplay _themeDisplay;

}