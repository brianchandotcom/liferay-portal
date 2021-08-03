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

package com.liferay.content.dashboard.web.internal.item.selector;

import com.liferay.content.dashboard.web.internal.display.context.ContentDashboardItemSubtypeItemSelectorViewDisplayContext;
import com.liferay.content.dashboard.web.internal.display.context.ContentDashboardItemSubtypeItemSelectorViewManagementToolbarDisplayContext;
import com.liferay.content.dashboard.web.internal.item.selector.criteria.content.dashboard.type.criterion.ContentDashboardItemSubtypeItemSelectorCriterion;
import com.liferay.content.dashboard.web.internal.item.selector.provider.ContentDashboardItemSubtypeItemSelectorProvider;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemSubtype;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = ItemSelectorView.class)
public class ContentDashboardItemSubtypeItemSelectorView
	implements ItemSelectorView
		<ContentDashboardItemSubtypeItemSelectorCriterion> {

	@Override
	public Class<ContentDashboardItemSubtypeItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return ContentDashboardItemSubtypeItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		return ResourceBundleUtil.getString(resourceBundle, "subtype");
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			ContentDashboardItemSubtypeItemSelectorCriterion
				contentDashboardItemSubtypeItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(
				"/view_content_dashboard_item_types.jsp");

		PortletRequest portletRequest =
			(PortletRequest)servletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);
		PortletResponse portletResponse =
			(PortletResponse)servletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		SearchContainer<? extends ContentDashboardItemSubtype> searchContainer =
			_contentDashboardItemSubtypeItemSelectorProvider.getSearchContainer(
				portletRequest, portletResponse, portletURL);

		servletRequest.setAttribute(
			ContentDashboardItemSubtypeItemSelectorViewDisplayContext.class.
				getName(),
			new ContentDashboardItemSubtypeItemSelectorViewDisplayContext(
				searchContainer));

		servletRequest.setAttribute(
			ContentDashboardItemSubtypeItemSelectorViewManagementToolbarDisplayContext.class.
				getName(),
			new ContentDashboardItemSubtypeItemSelectorViewManagementToolbarDisplayContext(
				_portal.getHttpServletRequest(portletRequest),
				_portal.getLiferayPortletRequest(portletRequest),
				_portal.getLiferayPortletResponse(portletResponse),
				searchContainer));

		requestDispatcher.include(servletRequest, servletResponse);
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new UUIDItemSelectorReturnType());

	@Reference
	private ContentDashboardItemSubtypeItemSelectorProvider
		_contentDashboardItemSubtypeItemSelectorProvider;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.content.dashboard.web)"
	)
	private ServletContext _servletContext;

	@Reference
	private UserLocalService _userLocalService;

}