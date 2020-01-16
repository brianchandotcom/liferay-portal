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

package com.liferay.multi.factor.authentication.email.otp.web.internal.portal.settings.configuration.admin.display;

import com.liferay.multi.factor.authentication.email.otp.web.internal.configuration.MFAEmailOTPConfiguration;
import com.liferay.multi.factor.authentication.email.otp.web.internal.settings.MFAEmailOTPConfigurationLocalizedValuesMap;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.settings.configuration.admin.display.PortalSettingsConfigurationScreenContributor;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marta Medio
 */
@Component(service = PortalSettingsConfigurationScreenContributor.class)
public class MFAEmailOTPPortalSettingsConfigurationScreenContributor
	implements PortalSettingsConfigurationScreenContributor {

	@Override
	public String getCategoryKey() {
		return "multi-factor-authentication";
	}

	public String getJspPath() {
		return "/portal_settings/email_otp_configuration.jsp";
	}

	@Override
	public String getKey() {
		return "mfa-email-otp-configuration-name";
	}

	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(
			ResourceBundleUtil.getBundle(
				locale, MFAEmailOTPConfiguration.class),
			"mfa-email-otp-configuration-name");
	}

	@Override
	public String getSaveMVCActionCommandName() {
		return "/portal_settings/multi_factor_authentication/email_otp";
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public void setAttributes(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		MFAEmailOTPConfiguration mfaEmailOTPConfiguration = null;

		try {
			mfaEmailOTPConfiguration =
				ConfigurationProviderUtil.getCompanyConfiguration(
					MFAEmailOTPConfiguration.class,
					themeDisplay.getCompanyId());
		}
		catch (ConfigurationException configurationException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to load email one-time password configuration",
					configurationException);
			}

			throw new IllegalStateException(configurationException);
		}

		httpServletRequest.setAttribute(
			"bodyLocalizedValuesMap",
			_getEmailOTPTemplate(
				MFAEmailOTPConfiguration.DEFAULT_EMAIL_OTP_BODY,
				mfaEmailOTPConfiguration.emailTemplateBody()));
		httpServletRequest.setAttribute(
			"subjectLocalizedValuesMap",
			_getEmailOTPTemplate(
				MFAEmailOTPConfiguration.DEFAULT_EMAIL_OTP_SUBJECT,
				mfaEmailOTPConfiguration.emailTemplateSubject()));
	}

	private MFAEmailOTPConfigurationLocalizedValuesMap _getEmailOTPTemplate(
		String defaultTemplate, String properties) {

		UnicodeProperties unicodeProperties = new UnicodeProperties(true);

		unicodeProperties.fastLoad(properties);

		return new MFAEmailOTPConfigurationLocalizedValuesMap(
			StringUtil.read(getClass(), defaultTemplate), unicodeProperties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MFAEmailOTPPortalSettingsConfigurationScreenContributor.class);

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.multi.factor.authentication.email.otp.web)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}