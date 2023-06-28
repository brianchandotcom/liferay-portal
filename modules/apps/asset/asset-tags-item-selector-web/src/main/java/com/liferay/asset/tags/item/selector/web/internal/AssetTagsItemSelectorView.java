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

package com.liferay.asset.tags.item.selector.web.internal;

import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.tags.item.selector.AssetTagsItemSelectorReturnType;
import com.liferay.asset.tags.item.selector.criterion.AssetTagsItemSelectorCriterion;
import com.liferay.asset.tags.item.selector.web.internal.display.context.AssetTagsDisplayContext;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefan Tanasie
 */
@Component(service = ItemSelectorView.class)
public class AssetTagsItemSelectorView
	implements ItemSelectorView<AssetTagsItemSelectorCriterion> {

	@Override
	public Class<? extends AssetTagsItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return AssetTagsItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		return _language.get(_portal.getResourceBundle(locale), "tags");
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			AssetTagsItemSelectorCriterion assetTagsItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)servletRequest;

		RenderRequest renderRequest =
			(RenderRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		RenderResponse renderResponse =
			(RenderResponse)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		AssetTagsDisplayContext assetTagsDisplayContext =
			new AssetTagsDisplayContext(
				httpServletRequest, portletURL, renderRequest, renderResponse,
				assetTagsItemSelectorCriterion);

		_itemSelectorViewDescriptorRenderer.renderHTML(
			httpServletRequest, servletResponse, assetTagsItemSelectorCriterion,
			portletURL, itemSelectedEventName, search,
			new AssetTagsItemSelectorViewDescriptor(
				assetTagsItemSelectorCriterion, httpServletRequest,
				assetTagsDisplayContext));
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new AssetTagsItemSelectorReturnType());

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private ItemSelectorViewDescriptorRenderer<AssetTagsItemSelectorCriterion>
		_itemSelectorViewDescriptorRenderer;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}