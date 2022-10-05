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
import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.info.collection.provider.SingleFormVariationInfoCollectionProvider;
import com.liferay.info.collection.provider.item.selector.web.internal.frontend.taglib.clay.servlet.taglib.RelatedInfoItemCollectionProviderVerticalCard;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Diego Hu
 */
public class RelatedInfoItemCollectionProviderItemDescriptor
	implements ItemSelectorViewDescriptor.ItemDescriptor {

	public RelatedInfoItemCollectionProviderItemDescriptor(
		HttpServletRequest httpServletRequest,
		RelatedInfoItemCollectionProvider<?, ?>
			relatedInfoItemCollectionProvider) {

		_httpServletRequest = httpServletRequest;
		_relatedInfoItemCollectionProvider = relatedInfoItemCollectionProvider;
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
				if (_relatedInfoItemCollectionProvider instanceof
						SingleFormVariationInfoCollectionProvider) {

					SingleFormVariationInfoCollectionProvider<?>
						singleFormVariationInfoCollectionProvider =
							(SingleFormVariationInfoCollectionProvider<?>)
								_relatedInfoItemCollectionProvider;

					return singleFormVariationInfoCollectionProvider.
						getFormVariationKey();
				}

				return null;
			}
		).put(
			"itemType",
			_relatedInfoItemCollectionProvider.getCollectionItemClassName()
		).put(
			"key", _relatedInfoItemCollectionProvider.getKey()
		).put(
			"sourceItemType",
			_relatedInfoItemCollectionProvider.getSourceItemClassName()
		).put(
			"title",
			() -> {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)_httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				return _relatedInfoItemCollectionProvider.getLabel(
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

		return new RelatedInfoItemCollectionProviderVerticalCard(
			renderRequest, _relatedInfoItemCollectionProvider, rowChecker);
	}

	private final HttpServletRequest _httpServletRequest;
	private final RelatedInfoItemCollectionProvider<?, ?>
		_relatedInfoItemCollectionProvider;

}