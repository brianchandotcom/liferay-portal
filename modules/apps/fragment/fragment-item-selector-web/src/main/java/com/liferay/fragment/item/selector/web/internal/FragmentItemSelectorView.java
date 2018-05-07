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

package com.liferay.fragment.item.selector.web.internal;

import com.liferay.fragment.item.selector.criterion.FragmentItemSelectorCriterion;
import com.liferay.fragment.item.selector.web.internal.constants.FragmentItemSelectorWebKeys;
import com.liferay.fragment.item.selector.web.internal.display.context.FragmentItemSelectorViewDisplayContext;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
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
 * @author Pavel Savinov
 */
@Component(
	property = "item.selector.view.order:Integer=100",
	service = ItemSelectorView.class
)
public class FragmentItemSelectorView
	implements ItemSelectorView<FragmentItemSelectorCriterion> {

	@Override
	public Class<FragmentItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return FragmentItemSelectorCriterion.class;
	}

	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(
				LocaleUtil.toLanguageId(locale));

		return LanguageUtil.get(resourceBundle, "fragments");
	}

	@Override
	public boolean isShowSearch() {
		return true;
	}

	@Override
	public boolean isVisible(ThemeDisplay themeDisplay) {
		return true;
	}

	@Override
	public void renderHTML(
			ServletRequest request, ServletResponse response,
			FragmentItemSelectorCriterion fragmentItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		FragmentItemSelectorViewDisplayContext
			fragmentItemSelectorViewDisplayContext =
				new FragmentItemSelectorViewDisplayContext(
					portletRequest, portletResponse,
					(HttpServletRequest)request, itemSelectedEventName, search,
					portletURL);

		request.setAttribute(
			FragmentItemSelectorWebKeys.
				FRAGMENT_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT,
			fragmentItemSelectorViewDisplayContext);

		ServletContext servletContext = getServletContext();

		String jspPath = _getJSPPath(portletRequest);

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(jspPath);

		requestDispatcher.include(request, response);
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.fragment.item.selector.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private String _getJSPPath(PortletRequest portletRequest) {
		long fragmentCollectionId = ParamUtil.getLong(
			portletRequest, "fragmentCollectionId");

		if (fragmentCollectionId > 0) {
			return "/fragment_entries.jsp";
		}

		return "/fragment_collections.jsp";
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.unmodifiableList(
			ListUtil.fromArray(
				new ItemSelectorReturnType[] {
					new UUIDItemSelectorReturnType()
				}));

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.fragment.item.selector.web)",
		unbind = "-"
	)
	private ResourceBundleLoader _resourceBundleLoader;

	private ServletContext _servletContext;

}