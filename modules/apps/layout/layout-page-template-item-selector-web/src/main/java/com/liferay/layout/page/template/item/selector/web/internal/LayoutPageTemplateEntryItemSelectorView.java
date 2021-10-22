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

package com.liferay.layout.page.template.item.selector.web.internal;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.layout.page.template.item.selector.LayoutPageTemplateEntryItemSelectorReturnType;
import com.liferay.layout.page.template.item.selector.criterion.LayoutPageTemplateEntryItemSelectorCriterion;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(immediate = true, service = ItemSelectorView.class)
public class LayoutPageTemplateEntryItemSelectorView
	implements ItemSelectorView<LayoutPageTemplateEntryItemSelectorCriterion> {

	@Override
	public Class<LayoutPageTemplateEntryItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return LayoutPageTemplateEntryItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, LayoutPageTemplateEntryItemSelectorView.class);

		return ResourceBundleUtil.getString(resourceBundle, "page-template");
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			LayoutPageTemplateEntryItemSelectorCriterion
				layoutPageTemplateEntryItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		_itemSelectorViewDescriptorRenderer.renderHTML(
			servletRequest, servletResponse,
			layoutPageTemplateEntryItemSelectorCriterion, portletURL,
			itemSelectedEventName, search,
			new ItemSelectorViewDescriptor<LayoutPageTemplateEntry>() {

				@Override
				public ItemDescriptor getItemDescriptor(
					LayoutPageTemplateEntry layoutPageTemplateEntry) {

					return null;
				}

				@Override
				public ItemSelectorReturnType getItemSelectorReturnType() {
					return null;
				}

				@Override
				public SearchContainer<LayoutPageTemplateEntry>
						getSearchContainer()
					throws PortalException {

					return null;
				}

			});
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new LayoutPageTemplateEntryItemSelectorReturnType());

	@Reference
	private ItemSelectorViewDescriptorRenderer
		<LayoutPageTemplateEntryItemSelectorCriterion>
			_itemSelectorViewDescriptorRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.layout.page.template.item.selector.web)"
	)
	private ServletContext _servletContext;

}