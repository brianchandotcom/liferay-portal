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

package com.liferay.multi.factor.authentication.checker.email.otp.web.internal.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.multi.factor.authentication.checker.email.otp.web.internal.configuration.EmailOTPConfiguration;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marta Medio
 */
@Component(
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
		"mvc.command.name=/portal_settings/multi_factor_authentication/email_otp"
	},
	service = MVCActionCommand.class
)
public class SaveEmailOTPConfigurationMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long companyId = themeDisplay.getCompanyId();

		try {
			Dictionary<String, Object> properties = new Hashtable<>();

			properties.put(
				"emailTemplateFromName",
				ParamUtil.getString(actionRequest, "emailTemplateFromName"));
			properties.put(
				"emailTemplateFrom",
				ParamUtil.getString(actionRequest, "emailTemplateFrom"));
			properties.put(
				"enabled", ParamUtil.getBoolean(actionRequest, "enabled"));
			properties.put(
				"failedAttemptsAllowed",
				ParamUtil.getLong(actionRequest, "failedAttemptsAllowed"));
			properties.put(
				"resendEmailTimeout",
				ParamUtil.getLong(actionRequest, "resendEmailTimeout"));
			properties.put(
				"retryTimeout",
				ParamUtil.getLong(actionRequest, "retryTimeout"));
			properties.put(
				"validationExpirationTime",
				ParamUtil.getLong(actionRequest, "validationExpirationTime"));

			UnicodeProperties bodyProperties =
				PropertiesParamUtil.getProperties(
					actionRequest, "preferences--emailOTPTemplateBody_");
			UnicodeProperties subjectProperties =
				PropertiesParamUtil.getProperties(
					actionRequest, "preferences--emailOTPTemplateSubject_");

			properties.put("emailTemplateBody", bodyProperties.toString());
			properties.put(
				"emailTemplateSubject", subjectProperties.toString());

			ConfigurationProviderUtil.saveCompanyConfiguration(
				EmailOTPConfiguration.class, companyId, properties);
		}
		catch (ConfigurationException ce) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to save Email OTP Configuration", ce);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SaveEmailOTPConfigurationMVCActionCommand.class);

}