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
import com.liferay.commerce.wish.list.exception.NoSuchWishListItemException;
import com.liferay.commerce.wish.list.service.CommerceWishListItemService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceWishListPortletKeys.COMMERCE_WISH_LIST,
		"javax.portlet.name=" + CommerceWishListPortletKeys.COMMERCE_WISH_LIST_CONTENT,
		"mvc.command.name=editCommerceWishListItem"
	},
	service = MVCActionCommand.class
)
public class EditCommerceWishListItemMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCommerceWishListItems(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceWishListItemIds = null;

		long commerceWishListItemId = ParamUtil.getLong(
			actionRequest, "commerceWishListItemId");

		if (commerceWishListItemId > 0) {
			deleteCommerceWishListItemIds = new long[] {commerceWishListItemId};
		}
		else {
			deleteCommerceWishListItemIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommerceWishListItemIds"),
				0L);
		}

		for (long deleteCommerceWishListItemId :
				deleteCommerceWishListItemIds) {

			_commerceWishListItemService.deleteCommerceWishListItem(
				deleteCommerceWishListItemId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteCommerceWishListItems(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchWishListItemException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	@Reference
	private CommerceWishListItemService _commerceWishListItemService;

}