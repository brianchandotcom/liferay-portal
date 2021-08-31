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
import com.liferay.info.collection.provider.item.selector.web.internal.constants.InfoCollectionProviderItemSelectorWebKeys;
import com.liferay.info.collection.provider.item.selector.web.internal.display.context.RelatedInfoItemCollectionProviderItemSelectorDisplayContext;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
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

		List<RelatedInfoItemCollectionProvider<?, ?>>
			relatedInfoItemCollectionProviders =
				_getRelatedInfoItemCollectionProviders(
					relatedInfoItemCollectionProviderItemSelectorCriterion);

		servletRequest.setAttribute(
			InfoCollectionProviderItemSelectorWebKeys.
				RELATED_INFO_ITEM_COLLECTION_PROVIDER_ITEM_SELECTOR_DISPLAY_CONTEXT,
			new RelatedInfoItemCollectionProviderItemSelectorDisplayContext(
				(HttpServletRequest)servletRequest, itemSelectedEventName,
				_language, portletURL, relatedInfoItemCollectionProviders));

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(
				"/view_related_info_collection_providers.jsp");

		requestDispatcher.include(servletRequest, servletResponse);
	}

	private List<RelatedInfoItemCollectionProvider<?, ?>>
		_getRelatedInfoItemCollectionProviders(
			RelatedInfoItemCollectionProviderItemSelectorCriterion
				relatedInfoItemCollectionProviderItemSelectorCriterion) {

		List<RelatedInfoItemCollectionProvider<?, ?>>
			itemRelatedItemsProviders = new ArrayList<>();

		List<String> itemTypes =
			relatedInfoItemCollectionProviderItemSelectorCriterion.
				getSourceItemTypes();

		for (String itemType : itemTypes) {
			itemRelatedItemsProviders.addAll(
				_infoItemServiceTracker.getAllInfoItemServices(
					(Class<RelatedInfoItemCollectionProvider<?, ?>>)
						(Class<?>)RelatedInfoItemCollectionProvider.class,
					itemType));
		}

		return Collections.unmodifiableList(itemRelatedItemsProviders);
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new InfoListProviderItemSelectorReturnType());

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private Language _language;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.info.collection.provider.item.selector.web)"
	)
	private ServletContext _servletContext;

}