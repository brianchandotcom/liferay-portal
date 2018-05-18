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

import com.liferay.commerce.wish.list.constants.CommerceWishListPortletKeys;
import com.liferay.commerce.wish.list.exception.CommerceWishListNameException;
import com.liferay.commerce.wish.list.exception.NoSuchWishListException;
import com.liferay.commerce.wish.list.model.CommerceWishList;
import com.liferay.commerce.wish.list.service.CommerceWishListService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceWishListPortletKeys.COMMERCE_WISH_LIST,
		"javax.portlet.name=" + CommerceWishListPortletKeys.COMMERCE_WISH_LIST_CONTENT,
		"mvc.command.name=editCommerceWishList"
	},
	service = MVCActionCommand.class
)
public class EditCommerceWishListMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteCommerceWishLists(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceWishListIds = null;

		long commerceWishListId = ParamUtil.getLong(
			actionRequest, "commerceWishListId");

		if (commerceWishListId > 0) {
			deleteCommerceWishListIds = new long[] {commerceWishListId};
		}
		else {
			deleteCommerceWishListIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCommerceWishListIds"),
				0L);
		}

		for (long deleteCommerceWishListId : deleteCommerceWishListIds) {
			_commerceWishListService.deleteCommerceWishList(
				deleteCommerceWishListId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCommerceWishList(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceWishLists(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchWishListException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else if (e instanceof CommerceWishListNameException) {
				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "editCommerceWishList");
			}
			else {
				throw e;
			}
		}
	}

	protected void updateCommerceWishList(ActionRequest actionRequest)
		throws PortalException {

		long commerceWishListId = ParamUtil.getLong(
			actionRequest, "commerceWishListId");

		String name = ParamUtil.getString(actionRequest, "name");
		boolean defaultWishList = ParamUtil.getBoolean(
			actionRequest, "defaultWishList");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceWishList.class.getName(), actionRequest);

		if (commerceWishListId > 0) {
			_commerceWishListService.updateCommerceWishList(
				commerceWishListId, name, defaultWishList);
		}
		else {
			_commerceWishListService.addCommerceWishList(
				name, defaultWishList, serviceContext);
		}
	}

	@Reference
	private CommerceWishListService _commerceWishListService;

}