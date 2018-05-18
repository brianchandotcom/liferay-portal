/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.item.selector.web.internal;

import com.liferay.commerce.item.selector.criterion.CommerceWarehouseItemSelectorCriterion;
import com.liferay.commerce.item.selector.web.internal.display.context.CommerceWarehouseItemSelectorViewDisplayContext;
import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.commerce.service.CommerceWarehouseService;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

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
 * @author Andrea Di Giorgi
 */
@Component(immediate = true)
public class CommerceWarehouseItemSelectorView
	implements ItemSelectorView<CommerceWarehouseItemSelectorCriterion> {

	@Override
	public Class<CommerceWarehouseItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return CommerceWarehouseItemSelectorCriterion.class;
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
		return LanguageUtil.get(locale, "warehouses");
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
			ServletRequest servletRequest, ServletResponse servletResponse,
			CommerceWarehouseItemSelectorCriterion
				commerceWarehouseItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)servletRequest;

		CommerceWarehouseItemSelectorViewDisplayContext
			commerceWarehouseItemSelectorViewDisplayContext =
				new CommerceWarehouseItemSelectorViewDisplayContext(
					_commerceCountryService, _commerceWarehouseService,
					httpServletRequest, portletURL, itemSelectedEventName,
					search);

		servletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			commerceWarehouseItemSelectorViewDisplayContext);

		ServletContext servletContext = getServletContext();

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher("/warehouse_item_selector.jsp");

		requestDispatcher.include(servletRequest, servletResponse);
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.unmodifiableList(
			ListUtil.fromArray(
				new ItemSelectorReturnType[] {
					new UUIDItemSelectorReturnType()
				}));

	@Reference
	private CommerceCountryService _commerceCountryService;

	@Reference
	private CommerceWarehouseService _commerceWarehouseService;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.item.selector.web)"
	)
	private ServletContext _servletContext;

}