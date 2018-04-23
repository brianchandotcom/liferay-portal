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

package com.liferay.commerce.product.catalog.rule.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.exception.NoSuchCPRuleException;
import com.liferay.commerce.product.exception.NoSuchCPRuleUserSegmentRelException;
import com.liferay.commerce.product.model.CPRuleUserSegmentRel;
import com.liferay.commerce.product.service.CPRuleUserSegmentRelService;
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
		"javax.portlet.name=" + CPPortletKeys.CP_CATALOG_RULE,
		"mvc.command.name=editCPRuleUserSegmentRel"
	},
	service = MVCActionCommand.class
)
public class EditCPRuleUserSegmentRelMVCActionCommand
	extends BaseMVCActionCommand {

	protected void addCPRuleUserSegmentRels(ActionRequest actionRequest)
		throws Exception {

		long[] addCommerceUserSegmentEntryIds = null;

		long cpRuleId = ParamUtil.getLong(actionRequest, "cpRuleId");
		long commerceUserSegmentEntryId = ParamUtil.getLong(
			actionRequest, "commerceUserSegmentEntryId");

		if (commerceUserSegmentEntryId > 0) {
			addCommerceUserSegmentEntryIds =
				new long[] {commerceUserSegmentEntryId};
		}
		else {
			addCommerceUserSegmentEntryIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "commerceUserSegmentEntryIds"),
				0L);
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPRuleUserSegmentRel.class.getName(), actionRequest);

		for (long addCommerceUserSegmentEntryId :
				addCommerceUserSegmentEntryIds) {

			_cpRuleUserSegmentRelService.addCPRuleUserSegmentRel(
				cpRuleId, addCommerceUserSegmentEntryId, serviceContext);
		}
	}

	protected void deleteCPRuleUserSegmentRels(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCPRuleUserSegmentRelIds = null;

		long cpRuleUserSegmentRelId = ParamUtil.getLong(
			actionRequest, "cpRuleUserSegmentRelId");

		if (cpRuleUserSegmentRelId > 0) {
			deleteCPRuleUserSegmentRelIds = new long[] {cpRuleUserSegmentRelId};
		}
		else {
			deleteCPRuleUserSegmentRelIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCPRuleUserSegmentRelIds"),
				0L);
		}

		for (long deleteCPRuleUserSegmentRelId :
				deleteCPRuleUserSegmentRelIds) {

			_cpRuleUserSegmentRelService.deleteCPRuleUserSegmentRel(
				deleteCPRuleUserSegmentRelId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD)) {
				addCPRuleUserSegmentRels(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCPRuleUserSegmentRels(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchCPRuleException ||
				e instanceof NoSuchCPRuleUserSegmentRelException ||
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
	private CPRuleUserSegmentRelService _cpRuleUserSegmentRelService;

}