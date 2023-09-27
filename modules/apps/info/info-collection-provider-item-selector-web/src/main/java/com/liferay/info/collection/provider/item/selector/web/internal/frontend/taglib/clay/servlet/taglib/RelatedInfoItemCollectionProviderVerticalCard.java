/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.collection.provider.item.selector.web.internal.frontend.taglib.clay.servlet.taglib;

import com.liferay.frontend.taglib.clay.servlet.taglib.BaseVerticalCard;
import com.liferay.info.collection.provider.FilteredInfoCollectionProvider;
import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
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
		if (_relatedInfoItemCollectionProvider instanceof
				FilteredInfoCollectionProvider) {

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