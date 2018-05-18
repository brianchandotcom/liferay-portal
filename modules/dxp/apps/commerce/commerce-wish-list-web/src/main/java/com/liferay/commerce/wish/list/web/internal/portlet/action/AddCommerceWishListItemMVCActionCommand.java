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

package com.liferay.commerce.wish.list.web.internal.portlet.action;

import com.liferay.commerce.wish.list.constants.CommerceWishListPortletKeys;
import com.liferay.commerce.wish.list.model.CommerceWishList;
import com.liferay.commerce.wish.list.model.CommerceWishListItem;
import com.liferay.commerce.wish.list.service.CommerceWishListItemService;
import com.liferay.commerce.wish.list.service.CommerceWishListService;
import com.liferay.commerce.wish.list.util.CommerceWishListHttpHelper;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
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
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceWishListPortletKeys.COMMERCE_WISH_LIST_CONTENT,
		"mvc.command.name=addCommerceWishListItem"
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
			CommerceWishList commerceWishList =
				_commerceWishListHttpHelper.getCurrentCommerceWishList(
					httpServletRequest, httpServletResponse);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				CommerceWishListItem.class.getName(), actionRequest);

			CommerceWishListItem commerceWishListItem =
				_commerceWishListItemService.addCommerceWishListItem(
					commerceWishList.getCommerceWishListId(), cpDefinitionId,
					cpInstanceId, ddmFormValues, serviceContext);

			int commerceWishListItemsCount =
				_commerceWishListItemService.getCommerceWishListItemsCount(
					commerceWishList.getCommerceWishListId());

			jsonObject.put(
				"commerceWishListItemId",
				commerceWishListItem.getCommerceWishListItemId());
			jsonObject.put(
				"commerceWishListItemsCount", commerceWishListItemsCount);
			jsonObject.put("success", true);
		}
		catch (Exception e) {
			_log.error(e, e);

			jsonObject.put("error", e.getMessage());
			jsonObject.put("success", false);
		}

		hideDefaultSuccessMessage(actionRequest);

		writeJSON(httpServletResponse, jsonObject);
	}

	protected void writeJSON(
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
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}