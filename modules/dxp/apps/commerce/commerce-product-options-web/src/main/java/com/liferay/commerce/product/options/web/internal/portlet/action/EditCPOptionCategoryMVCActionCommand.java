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

package com.liferay.commerce.product.options.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.exception.CPOptionCategoryKeyException;
import com.liferay.commerce.product.exception.NoSuchCPOptionCategoryException;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.service.CPOptionCategoryService;
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
		"javax.portlet.name=" + CPPortletKeys.CP_OPTION_CATEGORIES,
		"mvc.command.name=editProductOptionCategory"
	},
	service = MVCActionCommand.class
)
public class EditCPOptionCategoryMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteCPOptionCategories(ActionRequest actionRequest)
		throws Exception {

		long[] deleteCPOptionCategoryIds = null;

		long cpOptionCategoryId = ParamUtil.getLong(
			actionRequest, "cpOptionCategoryId");

		if (cpOptionCategoryId > 0) {
			deleteCPOptionCategoryIds = new long[] {cpOptionCategoryId};
		}
		else {
			deleteCPOptionCategoryIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCPOptionCategoryIds"),
				0L);
		}

		for (long deleteCPOptionCategoryId : deleteCPOptionCategoryIds) {
			_cpOptionCategoryService.deleteCPOptionCategory(
				deleteCPOptionCategoryId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteCPOptionCategories(actionRequest);
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.UPDATE)) {

				updateCPOptionCategory(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchCPOptionCategoryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof CPOptionCategoryKeyException) {
				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "editProductOptionCategory");
			}
			else {
				throw e;
			}
		}
	}

	protected CPOptionCategory updateCPOptionCategory(
			ActionRequest actionRequest)
		throws Exception {

		long cpOptionCategoryId = ParamUtil.getLong(
			actionRequest, "cpOptionCategoryId");

		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");
		double priority = ParamUtil.getDouble(actionRequest, "priority");
		String key = ParamUtil.getString(actionRequest, "key");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPOptionCategory.class.getName(), actionRequest);

		CPOptionCategory cpOptionCategory = null;

		if (cpOptionCategoryId <= 0) {

			// Add commerce product option category

			cpOptionCategory = _cpOptionCategoryService.addCPOptionCategory(
				titleMap, descriptionMap, priority, key, serviceContext);
		}
		else {

			// Update commerce product option category

			cpOptionCategory = _cpOptionCategoryService.updateCPOptionCategory(
				cpOptionCategoryId, titleMap, descriptionMap, priority, key,
				serviceContext);
		}

		return cpOptionCategory;
	}

	@Reference
	private CPOptionCategoryService _cpOptionCategoryService;

}