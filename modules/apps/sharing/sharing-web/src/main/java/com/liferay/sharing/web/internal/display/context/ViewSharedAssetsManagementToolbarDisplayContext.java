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

package com.liferay.sharing.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class ViewSharedAssetsManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public ViewSharedAssetsManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<?> searchContainer,
		ViewSharedAssetsDisplayContext viewSharedAssetsDisplayContext) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer);

		_viewSharedAssetsDisplayContext = viewSharedAssetsDisplayContext;
	}

	@Override
	public Map<String, Object> getAdditionalProps() {
		try {
			return HashMapBuilder.<String, Object>put(
				"selectAssetTypeURL",
				String.valueOf(
					_viewSharedAssetsDisplayContext.getSelectAssetTypeURL())
			).put(
				"viewAssetTypeURL",
				String.valueOf(
					_viewSharedAssetsDisplayContext.
						getViewAssetTypePortletURL())
			).build();
		}
		catch (PortletException portletException) {
			return ReflectionUtil.throwException(portletException);
		}
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		return _viewSharedAssetsDisplayContext.getFilterDropdownItems();
	}

	@Override
	public String getSortingOrder() {
		return _viewSharedAssetsDisplayContext.getSortingOrder();
	}

	@Override
	public String getSortingURL() {
		try {
			return String.valueOf(
				_viewSharedAssetsDisplayContext.getSortingURL());
		}
		catch (PortletException portletException) {
			return ReflectionUtil.throwException(portletException);
		}
	}

	@Override
	public Boolean isSelectable() {
		return false;
	}

	@Override
	public Boolean isShowSearch() {
		return false;
	}

	private final ViewSharedAssetsDisplayContext
		_viewSharedAssetsDisplayContext;

}