/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.display.page.item.selector.web.internal.frontend.taglib.clay.servlet.taglib;

import com.liferay.asset.display.page.item.selector.web.internal.display.context.AssetDisplayPagesItemSelectorCustomViewDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.HorizontalCard;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;

/**
 * @author Yurena Cabrera
 */
public class LayoutPageTemplateCollectionHorizontalCard
	implements HorizontalCard {

	public LayoutPageTemplateCollectionHorizontalCard(
		AssetDisplayPagesItemSelectorCustomViewDisplayContext
			assetDisplayPagesItemSelectorCustomViewDisplayContext,
		LayoutPageTemplateCollection layoutPageTemplateCollection) {

		_assetDisplayPagesItemSelectorCustomViewDisplayContext =
			assetDisplayPagesItemSelectorCustomViewDisplayContext;
		_layoutPageTemplateCollection = layoutPageTemplateCollection;
	}

	@Override
	public String getHref() {
		return PortletURLBuilder.create(
			_assetDisplayPagesItemSelectorCustomViewDisplayContext.
				getPortletURL()
		).setParameter(
			"groupId", _layoutPageTemplateCollection.getGroupId()
		).setParameter(
			"layoutPageTemplateCollectionId",
			_layoutPageTemplateCollection.getLayoutPageTemplateCollectionId()
		).buildString();
	}

	@Override
	public String getIcon() {
		return "folder";
	}

	@Override
	public String getTitle() {
		return _layoutPageTemplateCollection.getName();
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	private final AssetDisplayPagesItemSelectorCustomViewDisplayContext
		_assetDisplayPagesItemSelectorCustomViewDisplayContext;
	private final LayoutPageTemplateCollection _layoutPageTemplateCollection;

}