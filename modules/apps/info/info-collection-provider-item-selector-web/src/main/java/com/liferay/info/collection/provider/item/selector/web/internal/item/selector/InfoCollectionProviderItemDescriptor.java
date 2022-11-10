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

package com.liferay.info.collection.provider.item.selector.web.internal.item.selector;

import com.liferay.frontend.taglib.clay.servlet.taglib.VerticalCard;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.collection.provider.SingleFormVariationInfoCollectionProvider;
import com.liferay.info.collection.provider.item.selector.web.internal.frontend.taglib.clay.servlet.taglib.InfoCollectionProviderVerticalCard;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class InfoCollectionProviderItemDescriptor
	implements ItemSelectorViewDescriptor.ItemDescriptor {

	public InfoCollectionProviderItemDescriptor(
		HttpServletRequest httpServletRequest,
		InfoCollectionProvider<?> infoCollectionProvider,
		InfoItemServiceRegistry infoItemServiceRegistry) {

		_httpServletRequest = httpServletRequest;
		_infoCollectionProvider = infoCollectionProvider;
		_infoItemServiceRegistry = infoItemServiceRegistry;
	}

	@Override
	public String getIcon() {
		return null;
	}

	@Override
	public String getImageURL() {
		return null;
	}

	@Override
	public String getPayload() {
		return JSONUtil.put(
			"itemSubtype",
			() -> {
				if (_infoCollectionProvider instanceof
						SingleFormVariationInfoCollectionProvider) {

					SingleFormVariationInfoCollectionProvider<?>
						singleFormVariationInfoCollectionProvider =
							(SingleFormVariationInfoCollectionProvider<?>)
								_infoCollectionProvider;

					return singleFormVariationInfoCollectionProvider.
						getFormVariationKey();
				}

				return null;
			}
		).put(
			"itemType", _infoCollectionProvider.getCollectionItemClassName()
		).put(
			"key", _infoCollectionProvider.getKey()
		).put(
			"title",
			() -> {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)_httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				return _infoCollectionProvider.getLabel(
					themeDisplay.getLocale());
			}
		).toString();
	}

	@Override
	public String getSubtitle(Locale locale) {
		return null;
	}

	@Override
	public String getTitle(Locale locale) {
		return null;
	}

	@Override
	public VerticalCard getVerticalCard(
		RenderRequest renderRequest, RowChecker rowChecker) {

		return new InfoCollectionProviderVerticalCard(
			_infoCollectionProvider, _infoItemServiceRegistry, renderRequest,
			rowChecker);
	}

	private final HttpServletRequest _httpServletRequest;
	private final InfoCollectionProvider<?> _infoCollectionProvider;
	private final InfoItemServiceRegistry _infoItemServiceRegistry;

}