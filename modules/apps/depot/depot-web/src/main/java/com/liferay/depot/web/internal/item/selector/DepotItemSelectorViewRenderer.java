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

package com.liferay.depot.web.internal.item.selector;

import com.liferay.depot.web.internal.application.controller.DepotApplicationController;
import com.liferay.depot.web.internal.constants.DepotAdminWebKeys;
import com.liferay.depot.web.internal.display.context.DepotApplicationDisplayContext;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewRenderer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.PortalIncludeUtil;

import java.io.IOException;

import java.util.List;
import java.util.stream.Stream;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * @author Alicia García
 */
public class DepotItemSelectorViewRenderer implements ItemSelectorViewRenderer {

	public DepotItemSelectorViewRenderer(
		DepotApplicationController depotApplicationController,
		ItemSelectorViewRenderer itemSelectorViewRenderer, Portal portal,
		List<String> portletIds, ServletContext servletContext) {

		_depotApplicationController = depotApplicationController;
		_itemSelectorViewRenderer = itemSelectorViewRenderer;
		_portal = portal;
		_portletIds = portletIds;
		_servletContext = servletContext;
	}

	@Override
	public String getItemSelectedEventName() {
		return _itemSelectorViewRenderer.getItemSelectedEventName();
	}

	@Override
	public ItemSelectorCriterion getItemSelectorCriterion() {
		return _itemSelectorViewRenderer.getItemSelectorCriterion();
	}

	@Override
	public ItemSelectorView<ItemSelectorCriterion> getItemSelectorView() {
		return _itemSelectorViewRenderer.getItemSelectorView();
	}

	@Override
	public PortletURL getPortletURL() {
		return _itemSelectorViewRenderer.getPortletURL();
	}

	@Override
	public void renderHTML(PageContext pageContext)
		throws IOException, ServletException {

		PortalIncludeUtil.include(
			pageContext,
			(httpServletRequest, httpServletResponse) -> {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				Group scopeGroup = themeDisplay.getScopeGroup();

				if (scopeGroup.getType() != GroupConstants.TYPE_DEPOT) {
					_itemSelectorViewRenderer.renderHTML(pageContext);

					return;
				}

				_dispatchPortletItemSelectorView(
					scopeGroup.getGroupId(), httpServletRequest,
					httpServletResponse, pageContext);
			});
	}

	private void _dispatchPortletItemSelectorView(
			long groupId, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, PageContext pageContext)
		throws IOException, ServletException {

		String portletId = _getPortletId(groupId);

		if (Validator.isNotNull(portletId)) {
			_itemSelectorViewRenderer.renderHTML(pageContext);

			return;
		}

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(
				"/item/selector/application_disabled.jsp");

		DepotApplicationDisplayContext depotApplicationDisplayContext =
			new DepotApplicationDisplayContext(httpServletRequest, _portal);

		depotApplicationDisplayContext.setPortletId(_portletIds.get(0));
		depotApplicationDisplayContext.setPortletURL(
			_itemSelectorViewRenderer.getPortletURL());

		httpServletRequest.setAttribute(
			DepotAdminWebKeys.DEPOT_APPLICATION_DISPLAY_CONTEXT,
			depotApplicationDisplayContext);

		requestDispatcher.include(httpServletRequest, httpServletResponse);
	}

	private String _getPortletId(long groupId) {
		Stream<String> stream = _portletIds.stream();

		return stream.filter(
			portletId -> _depotApplicationController.isEnabled(
				portletId, groupId)
		).findFirst(
		).orElse(
			StringPool.BLANK
		);
	}

	private final DepotApplicationController _depotApplicationController;
	private final ItemSelectorViewRenderer _itemSelectorViewRenderer;
	private final Portal _portal;
	private final List<String> _portletIds;
	private final ServletContext _servletContext;

}