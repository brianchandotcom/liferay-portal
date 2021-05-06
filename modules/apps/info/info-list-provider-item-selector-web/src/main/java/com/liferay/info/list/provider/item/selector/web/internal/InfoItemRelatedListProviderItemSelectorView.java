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

package com.liferay.info.list.provider.item.selector.web.internal;

import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.list.provider.InfoItemRelatedListProvider;
import com.liferay.info.list.provider.item.selector.criterion.InfoItemRelatedListProviderItemSelectorCriterion;
import com.liferay.info.list.provider.item.selector.criterion.InfoItemRelatedListProviderItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.ArrayList;
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
 * @author Víctor Galán
 */
@Component(service = ItemSelectorView.class)
public class InfoItemRelatedListProviderItemSelectorView
	implements ItemSelectorView
		<InfoItemRelatedListProviderItemSelectorCriterion> {

	@Override
	public Class<? extends InfoItemRelatedListProviderItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return InfoItemRelatedListProviderItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return _language.get(
			resourceBundle, "related-items-collection-provider");
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			InfoItemRelatedListProviderItemSelectorCriterion
				infoItemRelatedListProviderItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		_itemSelectorViewDescriptorRenderer.renderHTML(
			servletRequest, servletResponse,
			infoItemRelatedListProviderItemSelectorCriterion, portletURL,
			itemSelectedEventName, search,
			new InfoItemRelatedItemsProviderItemSelectorViewDescriptor(
				(HttpServletRequest)servletRequest,
				infoItemRelatedListProviderItemSelectorCriterion, portletURL));
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new InfoItemRelatedListProviderItemSelectorReturnType());

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private ItemSelectorViewDescriptorRenderer
		<InfoItemRelatedListProviderItemSelectorCriterion>
			_itemSelectorViewDescriptorRenderer;

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.info.list.provider.item.selector.web)"
	)
	private ServletContext _servletContext;

	private class InfoItemRelatedItemsProviderItemSelectorViewDescriptor
		implements ItemSelectorViewDescriptor
			<InfoItemRelatedListProvider<?, ?>> {

		public InfoItemRelatedItemsProviderItemSelectorViewDescriptor(
			HttpServletRequest httpServletRequest,
			InfoItemRelatedListProviderItemSelectorCriterion
				infoItemRelatedListProviderItemSelectorCriterion,
			PortletURL portletURL) {

			_httpServletRequest = httpServletRequest;
			_infoItemRelatedListProviderItemSelectorCriterion =
				infoItemRelatedListProviderItemSelectorCriterion;
			_portletURL = portletURL;
		}

		@Override
		public ItemDescriptor getItemDescriptor(
			InfoItemRelatedListProvider<?, ?> infoItemRelatedItemsProvider) {

			return new ItemDescriptor() {

				@Override
				public String getIcon() {
					return "list";
				}

				@Override
				public String getImageURL() {
					return StringPool.BLANK;
				}

				@Override
				public String getPayload() {
					Class<?> relatedItemClass =
						infoItemRelatedItemsProvider.getRelatedItemClass();
					Class<?> sourceItemClass =
						infoItemRelatedItemsProvider.getSourceItemClass();
					ThemeDisplay themeDisplay =
						(ThemeDisplay)_httpServletRequest.getAttribute(
							WebKeys.THEME_DISPLAY);

					return JSONUtil.put(
						"itemType", relatedItemClass.getName()
					).put(
						"key", infoItemRelatedItemsProvider.getKey()
					).put(
						"sourceItemType", sourceItemClass.getName()
					).put(
						"title",
						infoItemRelatedItemsProvider.getLabel(
							themeDisplay.getLocale())
					).toString();
				}

				@Override
				public String getSubtitle(Locale locale) {
					Class<?> sourceItemClass =
						infoItemRelatedItemsProvider.getSourceItemClass();

					return ResourceActionsUtil.getModelResource(
						locale, sourceItemClass.getName());
				}

				@Override
				public String getTitle(Locale locale) {
					return infoItemRelatedItemsProvider.getLabel(locale);
				}

			};
		}

		@Override
		public ItemSelectorReturnType getItemSelectorReturnType() {
			return new InfoItemRelatedListProviderItemSelectorReturnType();
		}

		@Override
		public SearchContainer<InfoItemRelatedListProvider<?, ?>>
			getSearchContainer() {

			PortletRequest portletRequest =
				(PortletRequest)_httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", themeDisplay.getLocale(), getClass());

			SearchContainer<InfoItemRelatedListProvider<?, ?>> searchContainer =
				new SearchContainer<>(
					portletRequest, _portletURL, null,
					_language.get(
						resourceBundle,
						"there-are-no-related-items-collection-providers"));

			List<InfoItemRelatedListProvider<?, ?>> itemRelatedItemsProviders =
				new ArrayList<>();

			List<String> itemTypes =
				_infoItemRelatedListProviderItemSelectorCriterion.
					getSourceItemTypes();

			for (String itemType : itemTypes) {
				itemRelatedItemsProviders.addAll(
					_infoItemServiceTracker.getAllInfoItemServices(
						(Class<InfoItemRelatedListProvider<?, ?>>)
							(Class<?>)InfoItemRelatedListProvider.class,
						itemType));
			}

			searchContainer.setResults(
				ListUtil.subList(
					itemRelatedItemsProviders, searchContainer.getStart(),
					searchContainer.getEnd()));
			searchContainer.setTotal(itemRelatedItemsProviders.size());

			return searchContainer;
		}

		@Override
		public boolean isShowBreadcrumb() {
			return false;
		}

		private final HttpServletRequest _httpServletRequest;
		private final InfoItemRelatedListProviderItemSelectorCriterion
			_infoItemRelatedListProviderItemSelectorCriterion;
		private final PortletURL _portletURL;

	}

}