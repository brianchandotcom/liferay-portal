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

package com.liferay.commerce.avalara.tax.engine.fixed.web.internal.portlet.action;

import com.liferay.commerce.avalara.connector.configuration.CommerceAvalaraConnectorChannelConfiguration;
import com.liferay.commerce.avalara.connector.helper.CommerceAvalaraDispatchTriggerHelper;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.commerce.tax.service.CommerceTaxMethodService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Calvin Keum
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_TAX_METHODS,
		"mvc.command.name=/commerce_tax_methods/edit_commerce_tax_avalara"
	},
	service = MVCActionCommand.class
)
public class EditCommerceTaxAvalaraMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals("runNow")) {
			_commerceAvalaraDispatchTriggerHelper.runJob(
				_getCommerceTaxMethod(actionRequest));
		}
		else {
			_updateCommerceTaxAvalara(actionRequest);
		}
	}

	private CommerceTaxMethod _getCommerceTaxMethod(ActionRequest actionRequest)
		throws Exception {

		long commerceTaxMethodId = ParamUtil.getLong(
			actionRequest, "commerceTaxMethodId");

		return _commerceTaxMethodService.getCommerceTaxMethod(
			commerceTaxMethodId);
	}

	private void _updateCommerceTaxAvalara(ActionRequest actionRequest)
		throws Exception {

		CommerceTaxMethod commerceTaxMethod = _getCommerceTaxMethod(
			actionRequest);

		Settings settings = _settingsFactory.getSettings(
			new GroupServiceSettingsLocator(
				commerceTaxMethod.getGroupId(),
				CommerceAvalaraConnectorChannelConfiguration.class.getName()));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		String companyCode = ParamUtil.getString(actionRequest, "companyCode");

		modifiableSettings.setValue("companyCode", companyCode);

		Boolean disabledDocumentRecording = ParamUtil.getBoolean(
			actionRequest, "disabledDocumentRecording");

		modifiableSettings.setValue(
			"disabledDocumentRecording",
			String.valueOf(disabledDocumentRecording));

		modifiableSettings.store();
	}

	@Reference
	private CommerceAvalaraDispatchTriggerHelper
		_commerceAvalaraDispatchTriggerHelper;

	@Reference
	private CommerceTaxMethodService _commerceTaxMethodService;

	@Reference
	private SettingsFactory _settingsFactory;

}