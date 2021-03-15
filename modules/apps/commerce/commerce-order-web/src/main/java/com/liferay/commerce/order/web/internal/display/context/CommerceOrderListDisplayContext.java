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

package com.liferay.commerce.order.web.internal.display.context;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.web.internal.display.context.util.CommerceOrderRequestHelper;
import com.liferay.commerce.order.web.internal.search.CommerceOrderDisplayTerms;
import com.liferay.commerce.order.web.internal.security.permission.resource.CommerceOrderPermission;
import com.liferay.commerce.service.CommerceOrderNoteService;
import com.liferay.frontend.taglib.clay.data.set.servlet.taglib.util.ClayDataSetActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.SortItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.SortItemList;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderListDisplayContext {

	public CommerceOrderListDisplayContext(
		CommerceOrderNoteService commerceOrderNoteService,
		RenderRequest renderRequest) {

		_commerceOrderNoteService = commerceOrderNoteService;

		_commerceOrderRequestHelper = new CommerceOrderRequestHelper(
			renderRequest);

		_keywords = ParamUtil.getString(renderRequest, "keywords");
	}

	public List<ClayDataSetActionDropdownItem>
			getClayDataSetActionDropdownItems()
		throws PortalException {

		List<ClayDataSetActionDropdownItem> clayDataSetActionDropdownItems =
			new ArrayList<>();

		clayDataSetActionDropdownItems.add(
			new ClayDataSetActionDropdownItem(
				PortletURLBuilder.create(
					PortletProviderUtil.getPortletURL(
						_commerceOrderRequestHelper.getRequest(),
						CommerceOrder.class.getName(),
						PortletProvider.Action.MANAGE)
				).setMVCRenderCommandName(
					"/commerce_order/edit_commerce_order"
				).setParameter(
					"commerceOrderId", "{id}"
				).buildString(),
				"view", "view",
				LanguageUtil.get(
					_commerceOrderRequestHelper.getRequest(), "view"),
				"get", null, null));

		clayDataSetActionDropdownItems.add(
			new ClayDataSetActionDropdownItem(
				"/o/headless-commerce-admin-order/v1.0/orders/{id}", "trash",
				"delete",
				LanguageUtil.get(
					_commerceOrderRequestHelper.getRequest(), "delete"),
				"delete", "delete", "async"));

		return clayDataSetActionDropdownItems;
	}

	public int getCommerceOrderNotesCount(CommerceOrder commerceOrder)
		throws PortalException {

		if (CommerceOrderPermission.contains(
				_commerceOrderRequestHelper.getPermissionChecker(),
				commerceOrder, ActionKeys.UPDATE_DISCUSSION)) {

			return _commerceOrderNoteService.getCommerceOrderNotesCount(
				commerceOrder.getCommerceOrderId());
		}

		return _commerceOrderNoteService.getCommerceOrderNotesCount(
			commerceOrder.getCommerceOrderId(), false);
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = getSearchURL();

		for (String displayTerm : CommerceOrderDisplayTerms.VALID_TERMS) {
			String paramValue = ParamUtil.getString(
				_commerceOrderRequestHelper.getRequest(), displayTerm);

			if (Validator.isNotNull(paramValue)) {
				portletURL.setParameter(displayTerm, paramValue);
			}
		}

		return portletURL;
	}

	public PortletURL getSearchURL() {
		LiferayPortletResponse liferayPortletResponse =
			_commerceOrderRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		if (Validator.isNotNull(_keywords)) {
			portletURL.setParameter("keywords", _keywords);
		}

		return portletURL;
	}

	public SortItemList getSortItemList() {
		SortItemList sortItemList = new SortItemList();

		SortItem sortItem = new SortItem();

		sortItem.setDirection("desc");
		sortItem.setKey("createDate");

		sortItemList.add(sortItem);

		return sortItemList;
	}

	private final CommerceOrderNoteService _commerceOrderNoteService;
	private final CommerceOrderRequestHelper _commerceOrderRequestHelper;
	private final String _keywords;

}