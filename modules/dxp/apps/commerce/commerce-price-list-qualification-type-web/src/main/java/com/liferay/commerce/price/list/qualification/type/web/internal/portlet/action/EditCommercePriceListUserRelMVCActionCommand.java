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

package com.liferay.commerce.price.list.qualification.type.web.internal.portlet.action;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.price.list.qualification.type.exception.NoSuchPriceListUserRelException;
import com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel;
import com.liferay.commerce.price.list.qualification.type.service.CommercePriceListUserRelService;
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
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_PRICE_LIST,
		"mvc.command.name=editCommercePriceListUserRel"
	},
	service = MVCActionCommand.class
)
public class EditCommercePriceListUserRelMVCActionCommand
	extends BaseMVCActionCommand {

	protected void addCommercePriceListUserRels(ActionRequest actionRequest)
		throws Exception {

		long[] addClassPKs = null;

		long commercePriceListQualificationTypeRelId = ParamUtil.getLong(
			actionRequest, "commercePriceListQualificationTypeRelId");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");

		if (classPK > 0) {
			addClassPKs = new long[] {classPK};
		}
		else {
			addClassPKs = StringUtil.split(
				ParamUtil.getString(actionRequest, "addClassPKs"), 0L);
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommercePriceListUserRel.class.getName(), actionRequest);

		for (long addClassPK : addClassPKs) {
			String className = ParamUtil.getString(actionRequest, "className");

			_commercePriceListUserRelService.addCommercePriceListUserRel(
				commercePriceListQualificationTypeRelId, className, addClassPK,
				serviceContext);
		}
	}

	protected void deleteCommercePriceListUserRels(ActionRequest actionRequest)
		throws Exception {

		long[] deleteClassPKs = null;

		long classPK = ParamUtil.getLong(actionRequest, "classPK");

		if (classPK > 0) {
			deleteClassPKs = new long[] {classPK};
		}
		else {
			deleteClassPKs = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteClassPKs"), 0L);
		}

		for (long deleteClassPK : deleteClassPKs) {
			long commercePriceListQualificationTypeRelId = ParamUtil.getLong(
				actionRequest, "commercePriceListQualificationTypeRelId");

			String className = ParamUtil.getString(actionRequest, "className");

			_commercePriceListUserRelService.deleteCommercePriceListUserRels(
				commercePriceListQualificationTypeRelId, className,
				deleteClassPK);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) ||
				cmd.equals(Constants.ADD_MULTIPLE)) {

				addCommercePriceListUserRels(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommercePriceListUserRels(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchPriceListUserRelException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw e;
			}
		}
	}

	@Reference
	private CommercePriceListUserRelService _commercePriceListUserRelService;

}