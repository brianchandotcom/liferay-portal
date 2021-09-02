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

package com.liferay.info.collection.provider.item.selector.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseVerticalCard;
import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.info.collection.provider.item.selector.web.internal.constants.InfoCollectionProviderItemSelectorWebKeys;
import com.liferay.info.collection.provider.item.selector.web.internal.display.context.RelatedInfoItemCollectionProviderItemSelectorDisplayContext;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;

import javax.portlet.RenderRequest;

/**
 * @author Jürgen Kappler
 */
public class RelatedInfoItemCollectionProviderVerticalCard
	extends BaseVerticalCard {

	public RelatedInfoItemCollectionProviderVerticalCard(
		RenderRequest renderRequest,
		RelatedInfoItemCollectionProvider<?, ?>
			relatedInfoItemCollectionProvider,
		RowChecker rowChecker) {

		super(null, renderRequest, rowChecker);

		_relatedInfoItemCollectionProvider = relatedInfoItemCollectionProvider;
	}

	@Override
	public String getCssClass() {
		return "card-interactive card-interactive-secondary";
	}

	@Override
	public String getIcon() {
		return "list";
	}

	@Override
	public String getInputValue() {
		return null;
	}

	@Override
	public String getStickerIcon() {
		RelatedInfoItemCollectionProviderItemSelectorDisplayContext
			relatedInfoItemCollectionProviderItemSelectorDisplayContext =
				(RelatedInfoItemCollectionProviderItemSelectorDisplayContext)
					renderRequest.getAttribute(
						InfoCollectionProviderItemSelectorWebKeys.
							RELATED_INFO_ITEM_COLLECTION_PROVIDER_ITEM_SELECTOR_DISPLAY_CONTEXT);

		if (relatedInfoItemCollectionProviderItemSelectorDisplayContext.
				supportsFilters(_relatedInfoItemCollectionProvider)) {

			return "filter";
		}

		return super.getStickerIcon();
	}

	@Override
	public String getSubtitle() {
		return ResourceActionsUtil.getModelResource(
			themeDisplay.getLocale(),
			_relatedInfoItemCollectionProvider.getCollectionItemClassName());
	}

	@Override
	public String getTitle() {
		return _relatedInfoItemCollectionProvider.getLabel(
			themeDisplay.getLocale());
	}

	@Override
	public Boolean isFlushHorizontal() {
		return true;
	}

	private final RelatedInfoItemCollectionProvider<?, ?>
		_relatedInfoItemCollectionProvider;

}