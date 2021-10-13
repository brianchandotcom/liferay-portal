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
import com.liferay.info.collection.provider.item.selector.criterion.InfoCollectionProviderItemSelectorCriterion;
import com.liferay.info.collection.provider.item.selector.web.internal.constants.InfoCollectionProviderItemSelectorWebKeys;
import com.liferay.info.collection.provider.item.selector.web.internal.display.context.InfoCollectionProviderItemSelectorDisplayContext;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ListUtil;
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

		List<InfoCollectionProvider<?>> infoCollectionProviders =
			_getInfoCollectionProviders(
				infoCollectionProviderItemSelectorCriterion);

		servletRequest.setAttribute(
			InfoCollectionProviderItemSelectorWebKeys.
				INFO_COLLECTION_PROVIDER_ITEM_SELECTOR_DISPLAY_CONTEXT,
			new InfoCollectionProviderItemSelectorDisplayContext(
				(HttpServletRequest)servletRequest, infoCollectionProviders,
				_infoItemServiceTracker, itemSelectedEventName, _language,
				portletURL));

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(
				"/view_info_collection_providers.jsp");

		requestDispatcher.include(servletRequest, servletResponse);
	}

	private List<InfoCollectionProvider<?>> _getInfoCollectionProviders(
		InfoCollectionProviderItemSelectorCriterion
			infoCollectionProviderItemSelectorCriterion) {

		List<InfoCollectionProvider<?>> infoCollectionProviders =
			new ArrayList<>();

		List<String> itemTypes =
			infoCollectionProviderItemSelectorCriterion.getItemTypes();

		for (String itemType : itemTypes) {
			infoCollectionProviders.addAll(
				_getInfoCollectionProviders(itemType));
		}

		return Collections.unmodifiableList(infoCollectionProviders);
	}

	private List<InfoCollectionProvider<?>> _getInfoCollectionProviders(
		String itemType) {

		return ListUtil.filter(
			_infoItemServiceTracker.getAllInfoItemServices(
				(Class<InfoCollectionProvider<?>>)
					(Class<?>)InfoCollectionProvider.class,
				itemType),
			InfoCollectionProvider::isAvailable);
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