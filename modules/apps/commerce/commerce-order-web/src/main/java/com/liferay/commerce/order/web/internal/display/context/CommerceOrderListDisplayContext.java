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
import com.liferay.commerce.order.web.internal.display.context.helper.CommerceOrderRequestHelper;
import com.liferay.commerce.order.web.internal.search.CommerceOrderDisplayTerms;
import com.liferay.commerce.order.web.internal.security.permission.resource.CommerceOrderPermission;
import com.liferay.commerce.service.CommerceOrderNoteService;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.data.set.model.FDSSortItemBuilder;
import com.liferay.frontend.data.set.model.FDSSortItemList;
import com.liferay.frontend.data.set.model.FDSSortItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import javax.portlet.PortletRequest;
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

	public List<DropdownItem> getBulkActionDropdownItems() {
		PortletDisplay portletDisplay =
			_commerceOrderRequestHelper.getPortletDisplay();
		ThemeDisplay themeDisplay =
			_commerceOrderRequestHelper.getThemeDisplay();

		return ListUtil.fromArray(
			new FDSActionDropdownItem(
				PortletURLBuilder.create(
					PortletURLFactoryUtil.create(
						_commerceOrderRequestHelper.getRequest(),
						portletDisplay.getId(), themeDisplay.getPlid(),
						PortletRequest.ACTION_PHASE)
				).setActionName(
					"/commerce_order/edit_commerce_order"
				).setCMD(
					Constants.DELETE
				).setRedirect(
					themeDisplay.getURLCurrent()
				).buildString(),
				"trash", "delete", "delete",
				LanguageUtil.get(
					_commerceOrderRequestHelper.getRequest(), "delete"),
				"delete", null));
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

	public List<FDSActionDropdownItem> getFDSActionDropdownItems()
		throws PortalException {

		return ListUtil.fromArray(
			new FDSActionDropdownItem(
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
				"get", null, null),
			new FDSActionDropdownItem(
				"/o/headless-commerce-admin-order/v1.0/orders/{id}", "trash",
				"delete",
				LanguageUtil.get(
					_commerceOrderRequestHelper.getRequest(), "delete"),
				"delete", "delete", "async"));
	}

	public FDSSortItemList getFDSSortItemList() {
		return FDSSortItemListBuilder.add(
			FDSSortItemBuilder.setDirection(
				"desc"
			).setKey(
				"createDate"
			).build()
		).build();
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

	private final CommerceOrderNoteService _commerceOrderNoteService;
	private final CommerceOrderRequestHelper _commerceOrderRequestHelper;
	private final String _keywords;

}