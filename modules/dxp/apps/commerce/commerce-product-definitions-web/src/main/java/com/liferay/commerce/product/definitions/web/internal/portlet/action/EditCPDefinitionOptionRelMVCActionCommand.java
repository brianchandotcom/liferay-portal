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

package com.liferay.commerce.product.definitions.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.definitions.web.portlet.action.ActionHelper;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_DEFINITIONS,
		"mvc.command.name=editProductDefinitionOptionRel"
	},
	service = MVCActionCommand.class
)
public class EditCPDefinitionOptionRelMVCActionCommand
	extends BaseMVCActionCommand {

	protected void addCPDefinitionOptionRels(ActionRequest actionRequest)
		throws Exception {

		long[] addCPOptionIds = null;

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");
		long cpOptionId = ParamUtil.getLong(actionRequest, "cpOptionId");

		if (cpOptionId > 0) {
			addCPOptionIds = new long[] {cpOptionId};
		}
		else {
			addCPOptionIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "cpOptionIds"), 0L);
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPDefinitionOptionRel.class.getName(), actionRequest);

		for (long addCPOptionId : addCPOptionIds) {
			_cpDefinitionOptionRelService.addCPDefinitionOptionRel(
				cpDefinitionId, addCPOptionId, serviceContext);
		}
	}

	protected void deleteCPDefinitionOptionRels(
			long cpDefinitionOptionRelId, ActionRequest actionRequest)
		throws Exception {

		long[] deleteCPDefinitionOptionRelIds = null;

		if (cpDefinitionOptionRelId > 0) {
			deleteCPDefinitionOptionRelIds =
				new long[] {cpDefinitionOptionRelId};
		}
		else {
			deleteCPDefinitionOptionRelIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCPDefinitionOptionRelIds"),
				0L);
		}

		for (long deleteCPDefinitionOptionRelId :
				deleteCPDefinitionOptionRelIds) {

			_cpDefinitionOptionRelService.deleteCPDefinitionOptionRel(
				deleteCPDefinitionOptionRelId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long cpDefinitionOptionRelId = ParamUtil.getLong(
			actionRequest, "cpDefinitionOptionRelId");

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		try {
			if (cmd.equals(Constants.ADD) ||
				cmd.equals(Constants.ADD_MULTIPLE)) {

				addCPDefinitionOptionRels(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				_cpDefinitionOptionRelService.deleteCPDefinitionOptionRel(
					cpDefinitionOptionRelId);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				CPDefinitionOptionRel cpDefinitionOptionRel =
					updateCPDefinitionOptionRel(
						cpDefinitionOptionRelId, actionRequest);

				jsonObject.put(
					"cpDefinitionOptionRelId",
					cpDefinitionOptionRel.getCPDefinitionOptionRelId());
			}

			jsonObject.put("success", true);
		}
		catch (Exception e) {
			_log.error(e);

			jsonObject.put("message", e.getMessage());
			jsonObject.put("success", false);
		}

		hideDefaultSuccessMessage(actionRequest);

		_actionHelper.writeJSON(actionRequest, actionResponse, jsonObject);
	}

	protected CPDefinitionOptionRel updateCPDefinitionOptionRel(
			long cpDefinitionOptionRelId, ActionRequest actionRequest)
		throws Exception {

		long cpOptionId = ParamUtil.getLong(actionRequest, "cpOptionId");
		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");
		String ddmFormFieldTypeName = ParamUtil.getString(
			actionRequest, "DDMFormFieldTypeName");
		double priority = ParamUtil.getDouble(actionRequest, "priority");
		boolean facetable = ParamUtil.getBoolean(actionRequest, "facetable");
		boolean required = ParamUtil.getBoolean(actionRequest, "required");
		boolean skuContributor = ParamUtil.getBoolean(
			actionRequest, "skuContributor");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPDefinitionOptionRel.class.getName(), actionRequest);

		CPDefinitionOptionRel cpDefinitionOptionRel =
			_cpDefinitionOptionRelService.updateCPDefinitionOptionRel(
				cpDefinitionOptionRelId, cpOptionId, nameMap, descriptionMap,
				ddmFormFieldTypeName, priority, facetable, required,
				skuContributor, serviceContext);

		return cpDefinitionOptionRel;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCPDefinitionOptionRelMVCActionCommand.class);

	@Reference
	private ActionHelper _actionHelper;

	@Reference
	private CPDefinitionOptionRelService _cpDefinitionOptionRelService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}