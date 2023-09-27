/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.display.page.item.selector.web.internal;

import com.liferay.asset.display.page.item.selector.criterion.AssetDisplayPageSelectorCriterion;
import com.liferay.asset.display.page.item.selector.web.internal.display.context.AssetDisplayPagesItemSelectorViewDisplayContext;
import com.liferay.asset.display.page.item.selector.web.internal.item.selector.AssetDisplayPageItemSelectorViewDescriptor;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
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
 * @author Jürgen Kappler
 */
@Component(service = ItemSelectorView.class)
public class AssetDisplayPagesItemSelectorView
	implements ItemSelectorView<AssetDisplayPageSelectorCriterion> {

	@Override
	public Class<AssetDisplayPageSelectorCriterion>
		getItemSelectorCriterionClass() {

		return AssetDisplayPageSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		return _language.get(locale, "display-page-templates");
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			AssetDisplayPageSelectorCriterion assetDisplayPageSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		_itemSelectorViewDescriptorRenderer.renderHTML(
			servletRequest, servletResponse, assetDisplayPageSelectorCriterion,
			portletURL, itemSelectedEventName, search,
			new AssetDisplayPageItemSelectorViewDescriptor(
				new AssetDisplayPagesItemSelectorViewDisplayContext(
					(HttpServletRequest)servletRequest,
					assetDisplayPageSelectorCriterion, portletURL)));
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new UUIDItemSelectorReturnType());

	@Reference
	private ItemSelectorViewDescriptorRenderer
		<AssetDisplayPageSelectorCriterion> _itemSelectorViewDescriptorRenderer;

	@Reference
	private Language _language;

}