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

package com.liferay.commerce.machine.learning.forecast.alert.web.internal.portlet.actions;

import com.liferay.commerce.machine.learning.forecast.alert.constants.CommerceMLForecastAlertActionKeys;
import com.liferay.commerce.machine.learning.forecast.alert.constants.CommerceMLForecastAlertPortletKeys;
import com.liferay.commerce.machine.learning.forecast.alert.model.CommerceMLForecastAlertEntry;
import com.liferay.commerce.machine.learning.forecast.alert.service.CommerceMLForecastAlertEntryService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceMLForecastAlertPortletKeys.COMMERCE_ML_FORECAST_ALERT,
		"mvc.command.name=updateStatus"
	},
	service = MVCActionCommand.class
)
public class UpdateCommerceMLForecastAlertEntryMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceMLForecastAlertEntry.class.getName(), actionRequest);

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			long commerceMLForecastAlertEntryId = ParamUtil.getLong(
				actionRequest, "commerceMLForecastAlertEntryId");

			int status = ParamUtil.getInteger(actionRequest, "status");

			if (cmd.equals(
					CommerceMLForecastAlertActionKeys.MANAGE_ALERT_STATUS)) {

				_commerceMLForecastAlertEntryService.updateStatus(
					serviceContext.getUserId(), commerceMLForecastAlertEntryId,
					status);
			}
		}
		catch (Throwable t) {
			if (t instanceof PrincipalException) {
				hideDefaultErrorMessage(actionRequest);

				SessionErrors.add(actionRequest, "principalException");

				sendRedirect(actionRequest, actionResponse);
			}
		}
	}

	@Reference
	private CommerceMLForecastAlertEntryService
		_commerceMLForecastAlertEntryService;

}