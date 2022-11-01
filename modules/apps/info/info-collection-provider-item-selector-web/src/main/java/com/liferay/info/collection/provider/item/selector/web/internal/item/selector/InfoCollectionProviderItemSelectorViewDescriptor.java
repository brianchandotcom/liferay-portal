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

package com.liferay.info.collection.provider.item.selector.web.internal.item.selector;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.KeyValuePairComparator;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class InfoCollectionProviderItemSelectorViewDescriptor
	implements ItemSelectorViewDescriptor<InfoCollectionProvider<?>> {

	public InfoCollectionProviderItemSelectorViewDescriptor(
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		List<InfoCollectionProvider<?>> infoCollectionProviders,
		InfoItemServiceRegistry infoItemServiceRegistry) {

		_httpServletRequest = httpServletRequest;
		_portletURL = portletURL;
		_infoCollectionProviders = infoCollectionProviders;
		_infoItemServiceRegistry = infoItemServiceRegistry;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String[] getDisplayViews() {
		return new String[] {"icon"};
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {
		return LabelItemListBuilder.add(
			() -> Validator.isNotNull(_getSelectedItemType()),
			labelItem -> {
				labelItem.putData(
					"removeLabelURL",
					PortletURLBuilder.create(
						_portletURL
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
	public List<DropdownItem> getFilterNavigationDropdownItems() {
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
	public ItemDescriptor getItemDescriptor(
		InfoCollectionProvider<?> infoCollectionProvider) {

		return new InfoCollectionProviderItemDescriptor(
			_httpServletRequest, infoCollectionProvider,
			_infoItemServiceRegistry);
	}

	@Override
	public ItemSelectorReturnType getItemSelectorReturnType() {
		return new InfoListProviderItemSelectorReturnType();
	}

	public SearchContainer<InfoCollectionProvider<?>> getSearchContainer() {
		PortletRequest portletRequest =
			(PortletRequest)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		SearchContainer<InfoCollectionProvider<?>> searchContainer =
			new SearchContainer<>(
				portletRequest, _portletURL, null,
				"there-are-no-info-collection-providers");

		List<InfoCollectionProvider<?>> infoCollectionProviders =
			new ArrayList<>(_infoCollectionProviders);

		String itemType = ParamUtil.getString(_httpServletRequest, "itemType");

		if (Validator.isNotNull(itemType)) {
			infoCollectionProviders = ListUtil.filter(
				infoCollectionProviders,
				infoCollectionProvider -> {
					if (Objects.equals(
							infoCollectionProvider.getCollectionItemClassName(),
							itemType)) {

						return true;
					}

					return false;
				});
		}

		String keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		if (Validator.isNotNull(keywords)) {
			infoCollectionProviders = ListUtil.filter(
				infoCollectionProviders,
				infoCollectionProvider -> {
					String label = StringUtil.toLowerCase(
						infoCollectionProvider.getLabel(
							_themeDisplay.getLocale()));

					if (label.contains(StringUtil.toLowerCase(keywords))) {
						return true;
					}

					return false;
				});
		}

		searchContainer.setResultsAndTotal(infoCollectionProviders);

		return searchContainer;
	}

	@Override
	public boolean isShowSearch() {
		return true;
	}

	private List<DropdownItem> _getFilterTypeDropdownItems() {
		Stream<InfoCollectionProvider<?>> stream =
			_infoCollectionProviders.stream();

		List<KeyValuePair> keyValuePairs = stream.map(
			relatedInfoItemCollectionProvider -> {
				String collectionItemClassName =
					relatedInfoItemCollectionProvider.
						getCollectionItemClassName();

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
								_portletURL, "itemType", keyValuePair.getKey());
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

		_selectedItemType = ParamUtil.getString(
			_httpServletRequest, "itemType");

		return _selectedItemType;
	}

	private final HttpServletRequest _httpServletRequest;
	private final List<InfoCollectionProvider<?>> _infoCollectionProviders;
	private final InfoItemServiceRegistry _infoItemServiceRegistry;
	private final PortletURL _portletURL;
	private String _selectedItemType;
	private final ThemeDisplay _themeDisplay;

}