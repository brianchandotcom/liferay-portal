/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.email.otp.web.internal.portlet;

import com.liferay.multi.factor.authentication.email.otp.web.internal.constants.MFAEmailOTPPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.InterruptedPortletRequestWhitelistUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.util.PropsValues;

import javax.portlet.Portlet;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.application-type=full-page-application",
		"com.liferay.portlet.css-class-wrapper=portlet-mfa-email-otp-verify",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.preferences-company-wide=true",
		"javax.portlet.display-name=Multi Factor Authentication Email One-Time Password Verify",
		"javax.portlet.init-param.mvc-command-names-default-views=/mfa_email_otp_verify/verify",
		"javax.portlet.init-param.portlet-title-based-navigation=true",
		"javax.portlet.init-param.template-path=/META-INF/resources/mfa_email_otp_verify/",
		"javax.portlet.name=" + MFAEmailOTPPortletKeys.MFA_EMAIL_OTP_VERIFY,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.version=3.0",
		"portlet.add.default.resource.check.whitelist=" + MFAEmailOTPPortletKeys.MFA_EMAIL_OTP_VERIFY
	},
	service = Portlet.class
)
public class MFAEmailOTPVerifyPortlet extends MVCPortlet {

	@Activate
	protected void activate(BundleContext bundleContext) {
		PropsValues.PORTLET_INTERRUPTED_REQUEST_WHITELIST = ArrayUtil.append(
			PropsValues.PORTLET_INTERRUPTED_REQUEST_WHITELIST,
			MFAEmailOTPPortletKeys.MFA_EMAIL_OTP_VERIFY);

		_interruptedPortletRequestWhitelistUtil.
			resetPortletInvocationWhitelist();
	}

	@Deactivate
	protected void deactivate() {
		PropsValues.PORTLET_INTERRUPTED_REQUEST_WHITELIST = ArrayUtil.remove(
			PropsValues.PORTLET_INTERRUPTED_REQUEST_WHITELIST,
			MFAEmailOTPPortletKeys.MFA_EMAIL_OTP_VERIFY);

		_interruptedPortletRequestWhitelistUtil.
			resetPortletInvocationWhitelist();
	}

	@Reference
	private InterruptedPortletRequestWhitelistUtil
		_interruptedPortletRequestWhitelistUtil;

}