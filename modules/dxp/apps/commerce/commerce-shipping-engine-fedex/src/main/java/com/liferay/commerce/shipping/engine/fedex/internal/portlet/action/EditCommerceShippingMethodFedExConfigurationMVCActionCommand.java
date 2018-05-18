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

package com.liferay.commerce.shipping.engine.fedex.internal.portlet.action;

import com.liferay.commerce.admin.constants.CommerceAdminPortletKeys;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.shipping.engine.fedex.internal.constants.FedExCommerceShippingEngineConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

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
		"mvc.command.name=editCommerceShippingMethodFedExConfiguration"
	},
	service = MVCActionCommand.class
)
public class EditCommerceShippingMethodFedExConfigurationMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			UnicodeProperties engineParameterMap =
				PropertiesParamUtil.getProperties(actionRequest, "settings--");

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				CommerceShippingMethod.class.getName(), actionRequest);

			Settings settings = _settingsFactory.getSettings(
				new GroupServiceSettingsLocator(
					serviceContext.getScopeGroupId(),
					FedExCommerceShippingEngineConstants.SERVICE_NAME));

			ModifiableSettings modifiableSettings =
				settings.getModifiableSettings();

			for (Map.Entry<String, String> entry :
					engineParameterMap.entrySet()) {

				modifiableSettings.setValue(entry.getKey(), entry.getValue());
			}

			modifiableSettings.store();
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw e;
			}
		}
	}

	@Reference
	private SettingsFactory _settingsFactory;

}