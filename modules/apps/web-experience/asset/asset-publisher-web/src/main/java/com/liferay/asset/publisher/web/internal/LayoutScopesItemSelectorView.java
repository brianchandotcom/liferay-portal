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

package com.liferay.asset.publisher.web.internal;

import com.liferay.asset.publisher.constants.AssetPublisherWebKeys;
import com.liferay.asset.publisher.web.display.context.LayoutScopesItemSelectorViewDisplayContext;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.site.item.selector.criteria.SiteItemSelectorReturnType;
import com.liferay.site.item.selector.criterion.SiteItemSelectorCriterion;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {"item.selector.view.order:Integer=100"},
	service = ItemSelectorView.class
)
public class LayoutScopesItemSelectorView
	implements ItemSelectorView<SiteItemSelectorCriterion> {

	@Override
	public Class<SiteItemSelectorCriterion> getItemSelectorCriterionClass() {
		return SiteItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundle resourceBundle = _portal.getResourceBundle(locale);

		return ResourceBundleUtil.getString(resourceBundle, "pages");
	}

	@Override
	public boolean isVisible(ThemeDisplay themeDisplay) {
		Layout layout = themeDisplay.getLayout();

		int groupsCount = _groupLocalService.getGroupsCount(
			themeDisplay.getCompanyId(), Layout.class.getName(),
			layout.getGroupId());

		if (groupsCount > 0) {
			return true;
		}

		return false;
	}

	@Override
	public void renderHTML(
			ServletRequest request, ServletResponse response,
			SiteItemSelectorCriterion siteItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		LayoutScopesItemSelectorViewDisplayContext
			layoutScopesItemSelectorViewDisplayContext =
				new LayoutScopesItemSelectorViewDisplayContext(
					(HttpServletRequest)request, siteItemSelectorCriterion,
					itemSelectedEventName, portletURL);

		request.setAttribute(
			AssetPublisherWebKeys.ITEM_SELECTOR_DISPLAY_CONTEXT,
			layoutScopesItemSelectorViewDisplayContext);

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher("/view_sites.jsp");

		requestDispatcher.include(request, response);
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.asset.publisher.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.unmodifiableList(
			ListUtil.fromArray(
				new ItemSelectorReturnType[] {
					new SiteItemSelectorReturnType()
				}));

	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	private ServletContext _servletContext;

}