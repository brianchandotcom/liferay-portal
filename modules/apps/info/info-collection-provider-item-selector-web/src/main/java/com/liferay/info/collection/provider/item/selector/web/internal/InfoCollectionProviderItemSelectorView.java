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

import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.collection.provider.SingleFormVariationInfoCollectionProvider;
import com.liferay.info.collection.provider.item.selector.criterion.InfoCollectionProviderItemSelectorCriterion;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
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
 * @author Eudaldo Alonso
 */
@Component(service = ItemSelectorView.class)
public class InfoCollectionProviderItemSelectorView
	implements ItemSelectorView<InfoCollectionProviderItemSelectorCriterion> {

	@Override
	public Class<? extends InfoCollectionProviderItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return InfoCollectionProviderItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return _language.get(resourceBundle, "collection-providers");
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			InfoCollectionProviderItemSelectorCriterion
				infoCollectionProviderItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		_itemSelectorViewDescriptorRenderer.renderHTML(
			servletRequest, servletResponse,
			infoCollectionProviderItemSelectorCriterion, portletURL,
			itemSelectedEventName, search,
			new InfoCollectionProviderItemSelectorViewDescriptor(
				(HttpServletRequest)servletRequest,
				infoCollectionProviderItemSelectorCriterion, portletURL));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InfoCollectionProviderItemSelectorView.class);

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new InfoListProviderItemSelectorReturnType());

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private ItemSelectorViewDescriptorRenderer
		<InfoCollectionProviderItemSelectorCriterion>
			_itemSelectorViewDescriptorRenderer;

	@Reference
	private Language _language;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.info.collection.provider.item.selector.web)"
	)
	private ServletContext _servletContext;

	private class InfoCollectionProviderItemSelectorViewDescriptor
		implements ItemSelectorViewDescriptor<InfoCollectionProvider<?>> {

		public InfoCollectionProviderItemSelectorViewDescriptor(
			HttpServletRequest httpServletRequest,
			InfoCollectionProviderItemSelectorCriterion
				infoCollectionProviderItemSelectorCriterion,
			PortletURL portletURL) {

			_httpServletRequest = httpServletRequest;
			_infoCollectionProviderItemSelectorCriterion =
				infoCollectionProviderItemSelectorCriterion;
			_portletURL = portletURL;
		}

		@Override
		public ItemDescriptor getItemDescriptor(
			InfoCollectionProvider<?> infoCollectionProvider) {

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

					JSONObject jsonObject = JSONUtil.put(
						"itemType",
						infoCollectionProvider.getCollectionItemClassName()
					).put(
						"key", infoCollectionProvider.getKey()
					).put(
						"title",
						infoCollectionProvider.getLabel(
							themeDisplay.getLocale())
					);

					if (infoCollectionProvider instanceof
							SingleFormVariationInfoCollectionProvider) {

						SingleFormVariationInfoCollectionProvider<?>
							singleFormVariationInfoCollectionProvider =
								(SingleFormVariationInfoCollectionProvider<?>)
									infoCollectionProvider;

						jsonObject.put(
							"itemSubtype",
							singleFormVariationInfoCollectionProvider.
								getFormVariationKey());
					}

					return jsonObject.toString();
				}

				@Override
				public String getSubtitle(Locale locale) {
					String className =
						infoCollectionProvider.getCollectionItemClassName();

					if (Validator.isNotNull(className)) {
						return ResourceActionsUtil.getModelResource(
							locale, className);
					}

					return StringPool.BLANK;
				}

				@Override
				public String getTitle(Locale locale) {
					return infoCollectionProvider.getLabel(locale);
				}

			};
		}

		@Override
		public ItemSelectorReturnType getItemSelectorReturnType() {
			return new InfoListProviderItemSelectorReturnType();
		}

		@Override
		public SearchContainer<InfoCollectionProvider<?>> getSearchContainer() {
			PortletRequest portletRequest =
				(PortletRequest)_httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", themeDisplay.getLocale(), getClass());

			SearchContainer<InfoCollectionProvider<?>> searchContainer =
				new SearchContainer<>(
					portletRequest, _portletURL, null,
					_language.get(
						resourceBundle, "there-are-no-collection-providers"));

			List<InfoCollectionProvider<?>> infoCollectionProviders =
				new ArrayList<>();

			List<String> itemTypes =
				_infoCollectionProviderItemSelectorCriterion.getItemTypes();

			if (ListUtil.isNotEmpty(itemTypes)) {
				for (String itemType : itemTypes) {
					infoCollectionProviders.addAll(
						(List<InfoCollectionProvider<?>>)
							(List<?>)
								_infoItemServiceTracker.getAllInfoItemServices(
									InfoCollectionProvider.class, itemType));
				}
			}
			else {
				infoCollectionProviders =
					(List<InfoCollectionProvider<?>>)
						(List<?>)_infoItemServiceTracker.getAllInfoItemServices(
							InfoCollectionProvider.class);
			}

			infoCollectionProviders = ListUtil.filter(
				infoCollectionProviders,
				infoCollectionProvider -> {
					try {
						String label = infoCollectionProvider.getLabel(
							themeDisplay.getLocale());

						if (Validator.isNotNull(label) &&
							infoCollectionProvider.isAvailable()) {

							return true;
						}

						return false;
					}
					catch (Exception exception) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to get info list provider label",
								exception);
						}

						return false;
					}
				});

			searchContainer.setResults(
				ListUtil.subList(
					infoCollectionProviders, searchContainer.getStart(),
					searchContainer.getEnd()));
			searchContainer.setTotal(infoCollectionProviders.size());

			return searchContainer;
		}

		@Override
		public boolean isShowBreadcrumb() {
			return false;
		}

		private final HttpServletRequest _httpServletRequest;
		private final InfoCollectionProviderItemSelectorCriterion
			_infoCollectionProviderItemSelectorCriterion;
		private final PortletURL _portletURL;

	}

}