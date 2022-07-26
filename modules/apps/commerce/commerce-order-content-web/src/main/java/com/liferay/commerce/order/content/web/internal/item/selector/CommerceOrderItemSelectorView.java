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

package com.liferay.commerce.order.content.web.internal.item.selector;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.info.item.selector.InfoItemSelectorView;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.item.selector.criteria.InfoItemItemSelectorReturnType;
import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Danny Situ
 */
@Component(
	enabled = false, property = "item.selector.view.order:Integer=200",
	service = ItemSelectorView.class
)
public class CommerceOrderItemSelectorView
	implements InfoItemSelectorView,
			   ItemSelectorView<InfoItemItemSelectorCriterion> {

	@Override
	public String getClassName() {
		return CommerceOrder.class.getName();
	}

	@Override
	public Class<InfoItemItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return InfoItemItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		return _language.get(locale, "orders");
	}

	@Override
	public boolean isVisible(
		InfoItemItemSelectorCriterion infoItemItemSelectorCriterion,
		ThemeDisplay themeDisplay) {

		if (GetterUtil.getBoolean(
				PropsUtil.get("feature.flag.COMMERCE-9410"))) {

			return true;
		}

		return false;
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			InfoItemItemSelectorCriterion infoItemItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		_itemSelectorViewDescriptorRenderer.renderHTML(
			servletRequest, servletResponse, infoItemItemSelectorCriterion,
			portletURL, itemSelectedEventName, search,
			new CommerceOrderItemSelectorViewDescriptor(
				(HttpServletRequest)servletRequest, portletURL));
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new InfoItemItemSelectorReturnType());

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private ItemSelectorViewDescriptorRenderer<InfoItemItemSelectorCriterion>
		_itemSelectorViewDescriptorRenderer;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	private class CommerceOrderItemDescriptor
		implements ItemSelectorViewDescriptor.ItemDescriptor {

		public CommerceOrderItemDescriptor(
			CommerceOrder commerceOrder,
			HttpServletRequest httpServletRequest) {

			_commerceOrder = commerceOrder;
			_httpServletRequest = httpServletRequest;
		}

		@Override
		public String getIcon() {
			return null;
		}

		@Override
		public String getImageURL() {
			return null;
		}

		@Override
		public Date getModifiedDate() {
			return _commerceOrder.getModifiedDate();
		}

		@Override
		public String getPayload() {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			return JSONUtil.put(
				"className", CommerceOrder.class.getName()
			).put(
				"classNameId",
				_portal.getClassNameId(CommerceOrder.class.getName())
			).put(
				"classPK", _commerceOrder.getCommerceOrderId()
			).put(
				"title", _commerceOrder.getCommerceOrderId()
			).put(
				"type",
				ResourceActionsUtil.getModelResource(
					themeDisplay.getLocale(), CommerceOrder.class.getName())
			).toString();
		}

		@Override
		public String getSubtitle(Locale locale) {
			Date modifiedDate = _commerceOrder.getModifiedDate();

			return _language.format(
				locale, "x-ago-by-x",
				new Object[] {
					_language.getTimeDescription(
						locale,
						System.currentTimeMillis() - modifiedDate.getTime(),
						true),
					HtmlUtil.escape(_commerceOrder.getUserName())
				});
		}

		@Override
		public String getTitle(Locale locale) {
			return String.valueOf(_commerceOrder.getCommerceOrderId());
		}

		@Override
		public long getUserId() {
			return _commerceOrder.getUserId();
		}

		@Override
		public String getUserName() {
			return _commerceOrder.getUserName();
		}

		private final CommerceOrder _commerceOrder;
		private HttpServletRequest _httpServletRequest;

	}

	private class CommerceOrderItemSelectorViewDescriptor
		implements ItemSelectorViewDescriptor<CommerceOrder> {

		public CommerceOrderItemSelectorViewDescriptor(
			HttpServletRequest httpServletRequest, PortletURL portletURL) {

			_httpServletRequest = httpServletRequest;
			_portletURL = portletURL;
		}

		@Override
		public ItemDescriptor getItemDescriptor(CommerceOrder commerceOrder) {
			return new CommerceOrderItemDescriptor(
				commerceOrder, _httpServletRequest);
		}

		@Override
		public ItemSelectorReturnType getItemSelectorReturnType() {
			return new InfoItemItemSelectorReturnType();
		}

		@Override
		public SearchContainer<CommerceOrder> getSearchContainer() {
			SearchContainer<CommerceOrder> entriesSearchContainer =
				new SearchContainer<>(
					(PortletRequest)_httpServletRequest.getAttribute(
						JavaConstants.JAVAX_PORTLET_REQUEST),
					_portletURL, null, "no-entries-were-found");

			entriesSearchContainer.setResultsAndTotal(
				() -> _commerceOrderLocalService.getCommerceOrders(
					entriesSearchContainer.getStart(),
					entriesSearchContainer.getEnd()),
				_commerceOrderLocalService.getCommerceOrdersCount());

			return entriesSearchContainer;
		}

		private HttpServletRequest _httpServletRequest;
		private final PortletURL _portletURL;

	}

}