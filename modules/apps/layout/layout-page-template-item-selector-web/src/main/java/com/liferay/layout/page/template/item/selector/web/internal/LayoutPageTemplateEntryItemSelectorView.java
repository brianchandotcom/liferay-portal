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
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.layout.page.template.util.comparator.LayoutPageTemplateEntryNameComparator;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

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
			new LayoutPageTemplateEntryItemSelectorViewDescriptor(
				(HttpServletRequest)servletRequest,
				layoutPageTemplateEntryItemSelectorCriterion.getLayoutType(),
				portletURL));
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new LayoutPageTemplateEntryItemSelectorReturnType());

	@Reference
	private ItemSelectorViewDescriptorRenderer
		<LayoutPageTemplateEntryItemSelectorCriterion>
			_itemSelectorViewDescriptorRenderer;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.layout.page.template.item.selector.web)"
	)
	private ServletContext _servletContext;

	private class LayoutPageTemplateEntryItemSelectorViewDescriptor
		implements ItemSelectorViewDescriptor<LayoutPageTemplateEntry> {

		public LayoutPageTemplateEntryItemSelectorViewDescriptor(
			HttpServletRequest httpServletRequest, int layoutType,
			PortletURL portletURL) {

			_httpServletRequest = httpServletRequest;
			_layoutType = layoutType;
			_portletURL = portletURL;

			_portletRequest = (PortletRequest)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

			_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);
		}

		@Override
		public ItemDescriptor getItemDescriptor(
			LayoutPageTemplateEntry layoutPageTemplateEntry) {

			return null;
		}

		@Override
		public ItemSelectorReturnType getItemSelectorReturnType() {
			return new LayoutPageTemplateEntryItemSelectorReturnType();
		}

		@Override
		public String[] getOrderByKeys() {
			return new String[] {"name"};
		}

		@Override
		public SearchContainer<LayoutPageTemplateEntry> getSearchContainer() {
			SearchContainer<LayoutPageTemplateEntry> searchContainer =
				new SearchContainer<>(
					_portletRequest, _portletURL, null,
					"no-entries-were-found");

			String orderByCol = ParamUtil.getString(
				_httpServletRequest, "orderByCol", "name");

			searchContainer.setOrderByCol(orderByCol);

			String orderByType = ParamUtil.getString(
				_httpServletRequest, "orderByType", "asc");

			searchContainer.setOrderByType(orderByType);

			boolean orderByAsc = true;

			if (orderByType.equals("desc")) {
				orderByAsc = false;
			}

			searchContainer.setOrderByComparator(
				new LayoutPageTemplateEntryNameComparator(orderByAsc));

			String keywords = ParamUtil.getString(
				_httpServletRequest, "keywords");

			if (Validator.isNull(keywords)) {
				searchContainer.setResults(
					_layoutPageTemplateEntryService.
						getLayoutPageTemplateEntries(
							_themeDisplay.getScopeGroupId(), _layoutType,
							searchContainer.getStart(),
							searchContainer.getEnd(),
							searchContainer.getOrderByComparator()));
				searchContainer.setTotal(
					_layoutPageTemplateEntryService.
						getLayoutPageTemplateEntriesCount(
							_themeDisplay.getScopeGroupId(), _layoutType));
			}
			else {
				searchContainer.setResults(
					_layoutPageTemplateEntryService.
						getLayoutPageTemplateEntries(
							_themeDisplay.getScopeGroupId(), keywords,
							_layoutType, WorkflowConstants.STATUS_ANY,
							searchContainer.getStart(),
							searchContainer.getEnd(),
							searchContainer.getOrderByComparator()));
				searchContainer.setTotal(
					_layoutPageTemplateEntryService.
						getLayoutPageTemplateEntriesCount(
							_themeDisplay.getScopeGroupId(), keywords,
							_layoutType, WorkflowConstants.STATUS_ANY));
			}

			return searchContainer;
		}

		@Override
		public boolean isShowBreadcrumb() {
			return false;
		}

		@Override
		public boolean isShowManagementToolbar() {
			return true;
		}

		@Override
		public boolean isShowSearch() {
			return true;
		}

		private final HttpServletRequest _httpServletRequest;
		private final int _layoutType;
		private final PortletRequest _portletRequest;
		private final PortletURL _portletURL;
		private final ThemeDisplay _themeDisplay;

	}

}