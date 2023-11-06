/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.display.page.item.selector.web.internal.frontend.taglib.clay.servlet.taglib;

import com.liferay.frontend.taglib.clay.servlet.taglib.HorizontalCard;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;

import javax.portlet.RenderResponse;

/**
 * @author Yurena Cabrera
 */
public class DisplayPageTemplateCollectionHorizontalCard
	implements HorizontalCard {

	public DisplayPageTemplateCollectionHorizontalCard(
		LayoutPageTemplateCollection layoutPageTemplateCollection,
		RenderResponse renderResponse) {

		_layoutPageTemplateCollection = layoutPageTemplateCollection;
		_renderResponse = renderResponse;
	}

	@Override
	public String getHref() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
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

	private final LayoutPageTemplateCollection _layoutPageTemplateCollection;
	private final RenderResponse _renderResponse;

}