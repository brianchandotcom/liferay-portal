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

package com.liferay.fragment.collection.item.selector.web.internal;

import com.liferay.fragment.collection.item.selector.FragmentCollectionItemSelectorReturnType;
import com.liferay.fragment.collection.item.selector.criterion.FragmentCollectionItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	property = "item.selector.view.order:Integer=300",
	service = ItemSelectorView.class
)
public class FragmentCollectionSiteItemSelectorView
	implements ItemSelectorView<FragmentCollectionItemSelectorCriterion> {

	@Override
	public Class<FragmentCollectionItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return FragmentCollectionItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		String title = LanguageUtil.get(locale, "site");

		if (serviceContext == null) {
			return title;
		}

		Group scopeGroup = null;

		try {
			scopeGroup = serviceContext.getScopeGroup();

			if (scopeGroup == null) {
				return title;
			}

			return scopeGroup.getDescriptiveName(locale);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return title;
		}
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			FragmentCollectionItemSelectorCriterion
				fragmentCollectionItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)servletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		fragmentCollectionItemSelectorCriterion.setGroupId(
			themeDisplay.getSiteGroupId());

		_itemSelectorViewDescriptorRenderer.renderHTML(
			servletRequest, servletResponse,
			fragmentCollectionItemSelectorCriterion, portletURL,
			itemSelectedEventName, search,
			new FragmentCollectionItemSelectorViewDescriptor(
				fragmentCollectionItemSelectorCriterion,
				(HttpServletRequest)servletRequest, portletURL));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentCollectionSiteItemSelectorView.class);

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new FragmentCollectionItemSelectorReturnType());

	@Reference
	private ItemSelectorViewDescriptorRenderer
		<FragmentCollectionItemSelectorCriterion>
			_itemSelectorViewDescriptorRenderer;

}