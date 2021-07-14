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

package com.liferay.info.collection.provider.item.selector.web.internal;

import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.info.collection.provider.item.selector.criterion.RelatedInfoItemCollectionProviderItemSelectorCriterion;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
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
public class RelatedInfoItemCollectionProviderItemSelectorView
	implements ItemSelectorView
		<RelatedInfoItemCollectionProviderItemSelectorCriterion> {

	@Override
	public Class
		<? extends RelatedInfoItemCollectionProviderItemSelectorCriterion>
			getItemSelectorCriterionClass() {

		return RelatedInfoItemCollectionProviderItemSelectorCriterion.class;
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
			RelatedInfoItemCollectionProviderItemSelectorCriterion
				relatedInfoItemCollectionProviderItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		_itemSelectorViewDescriptorRenderer.renderHTML(
			servletRequest, servletResponse,
			relatedInfoItemCollectionProviderItemSelectorCriterion, portletURL,
			itemSelectedEventName, search,
			new InfoItemRelatedItemsProviderItemSelectorViewDescriptor(
				(HttpServletRequest)servletRequest,
				relatedInfoItemCollectionProviderItemSelectorCriterion,
				portletURL));
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new InfoListProviderItemSelectorReturnType());

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private ItemSelectorViewDescriptorRenderer
		<RelatedInfoItemCollectionProviderItemSelectorCriterion>
			_itemSelectorViewDescriptorRenderer;

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.info.collection.provider.item.selector.web)"
	)
	private ServletContext _servletContext;

	private class InfoItemRelatedItemsProviderItemSelectorViewDescriptor
		implements ItemSelectorViewDescriptor
			<RelatedInfoItemCollectionProvider<?, ?>> {

		public InfoItemRelatedItemsProviderItemSelectorViewDescriptor(
			HttpServletRequest httpServletRequest,
			RelatedInfoItemCollectionProviderItemSelectorCriterion
				relatedInfoItemCollectionProviderItemSelectorCriterion,
			PortletURL portletURL) {

			_httpServletRequest = httpServletRequest;
			_relatedInfoItemCollectionProviderItemSelectorCriterion =
				relatedInfoItemCollectionProviderItemSelectorCriterion;
			_portletURL = portletURL;
		}

		@Override
		public ItemDescriptor getItemDescriptor(
			RelatedInfoItemCollectionProvider<?, ?>
				relatedInfoItemCollectionProvider) {

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
					ThemeDisplay themeDisplay =
						(ThemeDisplay)_httpServletRequest.getAttribute(
							WebKeys.THEME_DISPLAY);

					return JSONUtil.put(
						"itemType",
						relatedInfoItemCollectionProvider.
							getCollectionItemClassName()
					).put(
						"key", relatedInfoItemCollectionProvider.getKey()
					).put(
						"sourceItemType",
						relatedInfoItemCollectionProvider.
							getSourceItemClassName()
					).put(
						"title",
						relatedInfoItemCollectionProvider.getLabel(
							themeDisplay.getLocale())
					).toString();
				}

				@Override
				public String getSubtitle(Locale locale) {
					return ResourceActionsUtil.getModelResource(
						locale,
						relatedInfoItemCollectionProvider.
							getCollectionItemClassName());
				}

				@Override
				public String getTitle(Locale locale) {
					return relatedInfoItemCollectionProvider.getLabel(locale);
				}

			};
		}

		@Override
		public ItemSelectorReturnType getItemSelectorReturnType() {
			return new InfoListProviderItemSelectorReturnType();
		}

		@Override
		public SearchContainer<RelatedInfoItemCollectionProvider<?, ?>>
			getSearchContainer() {

			PortletRequest portletRequest =
				(PortletRequest)_httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", themeDisplay.getLocale(), getClass());

			SearchContainer<RelatedInfoItemCollectionProvider<?, ?>>
				searchContainer = new SearchContainer<>(
					portletRequest, _portletURL, null,
					_language.get(
						resourceBundle,
						"there-are-no-related-items-collection-providers"));

			List<RelatedInfoItemCollectionProvider<?, ?>>
				itemRelatedItemsProviders = new ArrayList<>();

			List<String> itemTypes =
				_relatedInfoItemCollectionProviderItemSelectorCriterion.
					getSourceItemTypes();

			for (String itemType : itemTypes) {
				itemRelatedItemsProviders.addAll(
					_infoItemServiceTracker.getAllInfoItemServices(
						(Class<RelatedInfoItemCollectionProvider<?, ?>>)
							(Class<?>)RelatedInfoItemCollectionProvider.class,
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
		private final PortletURL _portletURL;
		private final RelatedInfoItemCollectionProviderItemSelectorCriterion
			_relatedInfoItemCollectionProviderItemSelectorCriterion;

	}

}