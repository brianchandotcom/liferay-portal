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

package com.liferay.asset.list.item.selector.web.internal;

import com.liferay.asset.list.constants.AssetListPortletKeys;
import com.liferay.asset.list.item.selector.web.internal.display.context.AssetListEntryItemSelectorDisplayContext;
import com.liferay.info.collection.provider.item.selector.criterion.InfoCollectionProviderItemSelectorCriterion;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.search.InfoSearchClassMapperRegistry;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.item.selector.PortletItemSelectorView;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.portal.kernel.language.Language;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "item.selector.view.order:Integer=100",
	service = ItemSelectorView.class
)
public class AssetListPortletItemSelectorView
	implements PortletItemSelectorView
		<InfoCollectionProviderItemSelectorCriterion> {

	@Override
	public Class<? extends InfoCollectionProviderItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return InfoCollectionProviderItemSelectorCriterion.class;
	}

	@Override
	public List<String> getPortletIds() {
		return Collections.singletonList(AssetListPortletKeys.ASSET_LIST);
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		return _language.get(locale, "collections");
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			InfoCollectionProviderItemSelectorCriterion
				infoCollectionProviderItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		_itemSelectorViewDescriptorRenderer.renderHTML(
			servletRequest, servletResponse,
			infoCollectionProviderItemSelectorCriterion, portletURL,
			itemSelectedEventName, search,
			new AssetListItemSelectorViewDescriptor(
				new AssetListEntryItemSelectorDisplayContext(
					(HttpServletRequest)servletRequest,
					_infoItemServiceRegistry, _infoSearchClassMapperRegistry,
					_language, portletURL,
					infoCollectionProviderItemSelectorCriterion)));
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new InfoListItemSelectorReturnType());

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private InfoSearchClassMapperRegistry _infoSearchClassMapperRegistry;

	@Reference
	private ItemSelectorViewDescriptorRenderer
		<InfoCollectionProviderItemSelectorCriterion>
			_itemSelectorViewDescriptorRenderer;

	@Reference
	private Language _language;

}