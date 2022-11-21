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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.item.selector.ItemSelector;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.sharing.filter.SharedAssetsFilterItem;
import com.liferay.sharing.web.internal.filter.SharedAssetsFilterItemRegistry;
import com.liferay.sharing.web.internal.item.selector.SharedAssetsFilterItemItemSelectorCriterion;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class ViewSharedAssetsManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public ViewSharedAssetsManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest, ItemSelector itemSelector,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<?> searchContainer,
		SharedAssetsFilterItemRegistry sharedAssetsFilterItemRegistry,
		ViewSharedAssetsDisplayContext viewSharedAssetsDisplayContext) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer);

		_itemSelector = itemSelector;
		_sharedAssetsFilterItemRegistry = sharedAssetsFilterItemRegistry;
		_viewSharedAssetsDisplayContext = viewSharedAssetsDisplayContext;
	}

	@Override
	public Map<String, Object> getAdditionalProps() {
		try {
			return HashMapBuilder.<String, Object>put(
				"selectAssetTypeURL", String.valueOf(_getSelectAssetTypeURL())
			).put(
				"viewAssetTypeURL",
				String.valueOf(_getViewAssetTypePortletURL())
			).build();
		}
		catch (PortletException portletException) {
			return ReflectionUtil.throwException(portletException);
		}
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					_getFilterNavigationDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(
						httpServletRequest, "filter-by-navigation"));
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(_getOrderByDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(httpServletRequest, "order-by"));
			}
		).build();
	}

	@Override
	public String getSortingOrder() {
		return _viewSharedAssetsDisplayContext.getSortingOrder();
	}

	@Override
	public String getSortingURL() {
		try {
			return PortletURLBuilder.create(
				_getCurrentSortingURL()
			).setParameter(
				"orderByType",
				() -> {
					if (Objects.equals(getSortingOrder(), "asc")) {
						return "desc";
					}

					return "asc";
				}
			).buildString();
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

	private String _getClassNameLabel(String className) {
		if (Validator.isNotNull(className)) {
			SharedAssetsFilterItem sharedAssetsFilterItem =
				_sharedAssetsFilterItemRegistry.getSharedAssetsFilterItem(
					className);

			if (sharedAssetsFilterItem != null) {
				return sharedAssetsFilterItem.getLabel(
					liferayPortletRequest.getLocale());
			}
		}

		return LanguageUtil.get(httpServletRequest, "asset-types");
	}

	private PortletURL _getCurrentSortingURL() throws PortletException {
		return PortletURLUtil.clone(currentURLObj, liferayPortletResponse);
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		String className = ParamUtil.getString(httpServletRequest, "className");

		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setActive(Validator.isNull(className));

				dropdownItem.setHref(
					PortletURLBuilder.create(
						PortletURLUtil.clone(
							currentURLObj, liferayPortletResponse)
					).setParameter(
						"className", (String)null
					).buildPortletURL());

				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "all"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.putData("action", "openAssetTypesSelector");
				dropdownItem.setActive(Validator.isNotNull(className));
				dropdownItem.setLabel(_getClassNameLabel(className));
			}
		).build();
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setActive(
					Objects.equals(getOrderByCol(), "sharedDate"));

				dropdownItem.setHref(
					_getCurrentSortingURL(), "orderByCol", "sharedDate");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "shared-date"));
			}
		).build();
	}

	private PortletURL _getSelectAssetTypeURL() {
		return _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(liferayPortletRequest),
			liferayPortletResponse.getNamespace() + "selectAssetType",
			new SharedAssetsFilterItemItemSelectorCriterion());
	}

	private PortletURL _getViewAssetTypePortletURL() throws PortletException {
		return PortletURLBuilder.create(
			PortletURLUtil.clone(currentURLObj, liferayPortletResponse)
		).setParameter(
			"className", (String)null
		).buildPortletURL();
	}

	private final ItemSelector _itemSelector;
	private final SharedAssetsFilterItemRegistry
		_sharedAssetsFilterItemRegistry;
	private final ViewSharedAssetsDisplayContext
		_viewSharedAssetsDisplayContext;

}