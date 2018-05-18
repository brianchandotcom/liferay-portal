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

package com.liferay.commerce.product.tax.category.web.internal.portlet.action;

import com.liferay.commerce.admin.constants.CommerceAdminPortletKeys;
import com.liferay.commerce.product.exception.CPTaxCategoryNameException;
import com.liferay.commerce.product.exception.NoSuchCPTaxCategoryException;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.CPTaxCategoryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;
import java.util.Map;

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
		"javax.portlet.name=" + CommerceAdminPortletKeys.COMMERCE_ADMIN,
		"mvc.command.name=editCPTaxCategory"
	},
	service = MVCActionCommand.class
)
public class EditCPTaxCategoryMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteCPTaxCategories(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCPTaxCategoryIds = null;

		long cpTaxCategoryId = ParamUtil.getLong(
			actionRequest, "cpTaxCategoryId");

		if (cpTaxCategoryId > 0) {
			deleteCPTaxCategoryIds = new long[] {cpTaxCategoryId};
		}
		else {
			deleteCPTaxCategoryIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCPTaxCategoryIds"),
				0L);
		}

		for (long deleteCPTaxCategoryId : deleteCPTaxCategoryIds) {
			_cpTaxCategoryService.deleteCPTaxCategory(deleteCPTaxCategoryId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteCPTaxCategories(actionRequest);
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.UPDATE)) {

				updateCPTaxCategory(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchCPTaxCategoryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof CPTaxCategoryNameException) {
				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "editCPTaxCategory");
			}
			else {
				throw e;
			}
		}
	}

	protected void updateCPTaxCategory(ActionRequest actionRequest)
		throws PortalException {

		long cpTaxCategoryId = ParamUtil.getLong(
			actionRequest, "cpTaxCategoryId");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPTaxCategory.class.getName(), actionRequest);

		if (cpTaxCategoryId <= 0) {
			_cpTaxCategoryService.addCPTaxCategory(
				nameMap, descriptionMap, serviceContext);
		}
		else {
			_cpTaxCategoryService.updateCPTaxCategory(
				cpTaxCategoryId, nameMap, descriptionMap);
		}
	}

	@Reference
	private CPTaxCategoryService _cpTaxCategoryService;

}