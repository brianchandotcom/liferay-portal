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

package com.liferay.commerce.wish.list.web.internal.portlet.action;

import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.commerce.wish.list.constants.CommerceWishListPortletKeys;
import com.liferay.commerce.wish.list.model.CommerceWishList;
import com.liferay.commerce.wish.list.model.CommerceWishListItem;
import com.liferay.commerce.wish.list.service.CommerceWishListItemService;
import com.liferay.commerce.wish.list.service.CommerceWishListService;
import com.liferay.commerce.wish.list.util.CommerceWishListHttpHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @author Andrea Di Giorgi
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CommerceWishListPortletKeys.COMMERCE_WISH_LIST_CONTENT,
		"mvc.command.name=/commerce_wish_list_content/add_commerce_wish_list_item"
	},
	service = MVCActionCommand.class
)
public class AddCommerceWishListItemMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");
		long cpInstanceId = ParamUtil.getLong(actionRequest, "cpInstanceId");
		String ddmFormValues = ParamUtil.getString(
			actionRequest, "ddmFormValues");

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);
		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(actionResponse);

		try {
			CPInstance cpInstance = _cpInstanceLocalService.fetchCPInstance(
				cpInstanceId);

			String cpInstanceUuid = StringPool.BLANK;

			if (cpInstance != null) {
				cpInstanceUuid = cpInstance.getCPInstanceUuid();
			}

			CommerceWishList commerceWishList =
				_commerceWishListHttpHelper.getCurrentCommerceWishList(
					httpServletRequest, httpServletResponse);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				CommerceWishListItem.class.getName(), actionRequest);

			if (commerceWishList == null) {
				commerceWishList = _commerceWishListService.addCommerceWishList(
					LanguageUtil.get(serviceContext.getLocale(), "default"),
					true, serviceContext);
			}

			CommerceWishListItem commerceWishListItem =
				_commerceWishListItemService.addCommerceWishListItem(
					CommerceUtil.getCommerceAccountId(
						(CommerceContext)httpServletRequest.getAttribute(
							CommerceWebKeys.COMMERCE_CONTEXT)),
					commerceWishList.getCommerceWishListId(), cpDefinitionId,
					cpInstanceUuid, ddmFormValues, serviceContext);

			int commerceWishListItemsCount =
				_commerceWishListItemService.getCommerceWishListItemsCount(
					commerceWishList.getCommerceWishListId());

			jsonObject.put(
				"commerceWishListItemId",
				commerceWishListItem.getCommerceWishListItemId()
			).put(
				"commerceWishListItemsCount", commerceWishListItemsCount
			).put(
				"success", true
			);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			jsonObject.put(
				"error", exception.getMessage()
			).put(
				"success", false
			);
		}

		hideDefaultSuccessMessage(actionRequest);

		_writeJSON(httpServletResponse, jsonObject);
	}

	private void _writeJSON(
			HttpServletResponse httpServletResponse, JSONObject jsonObject)
		throws IOException {

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

		ServletResponseUtil.write(httpServletResponse, jsonObject.toString());

		httpServletResponse.flushBuffer();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddCommerceWishListItemMVCActionCommand.class);

	@Reference
	private CommerceWishListHttpHelper _commerceWishListHttpHelper;

	@Reference
	private CommerceWishListItemService _commerceWishListItemService;

	@Reference
	private CommerceWishListService _commerceWishListService;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}