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

import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.provider.InfoListProviderTracker;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorCriterion;
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
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

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
 * @author Eudaldo Alonso
 */
@Component(service = ItemSelectorView.class)
public class InfoListProviderItemSelectorView
	implements ItemSelectorView<InfoListProviderItemSelectorCriterion> {

	@Override
	public Class<? extends InfoListProviderItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return InfoListProviderItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		return _language.get(locale, "info-list-providers");
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			InfoListProviderItemSelectorCriterion
				infoListProviderItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		_itemSelectorViewDescriptorRenderer.renderHTML(
			servletRequest, servletResponse,
			infoListProviderItemSelectorCriterion, portletURL,
			itemSelectedEventName, search,
			new InfoListProviderItemSelectorViewDescriptor(
				(HttpServletRequest)servletRequest, portletURL));
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new InfoListProviderItemSelectorReturnType());

	@Reference
	private InfoListProviderTracker _infoListProviderTracker;

	@Reference
	private ItemSelectorViewDescriptorRenderer
		<InfoListProviderItemSelectorCriterion>
			_itemSelectorViewDescriptorRenderer;

	@Reference
	private Language _language;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.info.list.provider.item.selector.web)"
	)
	private ServletContext _servletContext;

	private class InfoListProviderItemSelectorViewDescriptor
		implements ItemSelectorViewDescriptor<InfoListProvider> {

		public InfoListProviderItemSelectorViewDescriptor(
			HttpServletRequest httpServletRequest, PortletURL portletURL) {

			_httpServletRequest = httpServletRequest;
			_portletURL = portletURL;
		}

		@Override
		public ItemDescriptor getItemDescriptor(
			InfoListProvider infoListProvider) {

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
						"key", infoListProvider.getKey()
					).put(
						"title",
						infoListProvider.getLabel(themeDisplay.getLocale())
					).toString();
				}

				@Override
				public String getSubtitle(Locale locale) {
					Class<?> clazz = infoListProvider.getClass();

					Type[] genericInterfaceTypes = clazz.getGenericInterfaces();

					for (Type genericInterfaceType : genericInterfaceTypes) {
						ParameterizedType parameterizedType =
							(ParameterizedType)genericInterfaceType;

						Class<?> typeClazz =
							(Class<?>)
								parameterizedType.getActualTypeArguments()[0];

						return ResourceActionsUtil.getModelResource(
							locale, typeClazz.getName());
					}

					return StringPool.BLANK;
				}

				@Override
				public String getTitle(Locale locale) {
					return infoListProvider.getLabel(locale);
				}

			};
		}

		@Override
		public ItemSelectorReturnType getItemSelectorReturnType() {
			return new InfoListProviderItemSelectorReturnType();
		}

		@Override
		public SearchContainer getSearchContainer() {
			PortletRequest portletRequest =
				(PortletRequest)_httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST);

			SearchContainer searchContainer = new SearchContainer<>(
				portletRequest, _portletURL, null,
				"there-are-no-info-list-providers");

			List<InfoListProvider> infoListProviders =
				_infoListProviderTracker.getInfoListProviders();

			searchContainer.setResults(
				ListUtil.subList(
					infoListProviders, searchContainer.getStart(),
					searchContainer.getEnd()));
			searchContainer.setTotal(infoListProviders.size());

			return searchContainer;
		}

		@Override
		public boolean isShowBreadcrumb() {
			return false;
		}

		private final HttpServletRequest _httpServletRequest;
		private final PortletURL _portletURL;

	}

}