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

package com.liferay.info.collection.provider.item.selector.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.KeyValuePairComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class InfoCollectionProviderItemSelectorManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public InfoCollectionProviderItemSelectorManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		InfoCollectionProviderItemSelectorDisplayContext
			infoCollectionProviderItemSelectorDisplayContext,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<?> searchContainer) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer);

		_infoCollectionProviderItemSelectorDisplayContext =
			infoCollectionProviderItemSelectorDisplayContext;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String getClearResultsURL() {
		return PortletURLBuilder.create(
			getPortletURL()
		).setKeywords(
			StringPool.BLANK
		).setParameter(
			"itemType", (String)null
		).buildString();
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					_getFilterTypeDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(
						_themeDisplay.getLocale(), "filter-by-item-type"));
			}
		).build();
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {
		return LabelItemListBuilder.add(
			() -> Validator.isNotNull(_getSelectedItemType()),
			labelItem -> {
				labelItem.putData(
					"removeLabelURL",
					PortletURLBuilder.create(
						PortletURLUtil.clone(
							currentURLObj, liferayPortletResponse)
					).setParameter(
						"itemType", (String)null
					).buildString());
				labelItem.setDismissible(true);

				String modelResource = ResourceActionsUtil.getModelResource(
					_themeDisplay.getLocale(), _getSelectedItemType());

				labelItem.setLabel(
					LanguageUtil.get(_themeDisplay.getLocale(), "item-type") +
						": " + modelResource);
			}
		).build();
	}

	@Override
	public String getSearchActionURL() {
		return String.valueOf(getPortletURL());
	}

	@Override
	public Boolean isSelectable() {
		return false;
	}

	@Override
	protected String getDefaultDisplayStyle() {
		return "icon";
	}

	@Override
	protected String[] getDisplayViews() {
		return new String[] {"descriptive", "icon", "list"};
	}

	private List<DropdownItem> _getFilterTypeDropdownItems() {
		List<InfoCollectionProvider<?>> infoCollectionProviders =
			_infoCollectionProviderItemSelectorDisplayContext.
				getInfoCollectionProviders();

		Stream<InfoCollectionProvider<?>> stream =
			infoCollectionProviders.stream();

		List<KeyValuePair> keyValuePairs = stream.map(
			infoCollectionProvider -> {
				String collectionItemClassName =
					infoCollectionProvider.getCollectionItemClassName();

				return new KeyValuePair(
					collectionItemClassName,
					ResourceActionsUtil.getModelResource(
						_themeDisplay.getLocale(), collectionItemClassName));
			}
		).distinct(
		).sorted(
			new KeyValuePairComparator(false, true)
		).collect(
			Collectors.toList()
		);

		return new DropdownItemList() {
			{
				for (KeyValuePair keyValuePair : keyValuePairs) {
					add(
						dropdownItem -> {
							if (Objects.equals(
									keyValuePair.getKey(),
									_getSelectedItemType())) {

								dropdownItem.setActive(true);
							}

							dropdownItem.setHref(
								getPortletURL(), "itemType",
								keyValuePair.getKey());
							dropdownItem.setLabel(keyValuePair.getValue());
						});
				}
			}
		};
	}

	private String _getSelectedItemType() {
		if (_selectedItemType != null) {
			return _selectedItemType;
		}

		_selectedItemType = ParamUtil.getString(httpServletRequest, "itemType");

		return _selectedItemType;
	}

	private final InfoCollectionProviderItemSelectorDisplayContext
		_infoCollectionProviderItemSelectorDisplayContext;
	private String _selectedItemType;
	private final ThemeDisplay _themeDisplay;

}