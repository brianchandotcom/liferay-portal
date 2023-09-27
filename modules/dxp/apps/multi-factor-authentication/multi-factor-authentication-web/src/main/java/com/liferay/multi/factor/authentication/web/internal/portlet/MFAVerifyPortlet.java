/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.web.internal.portlet;

import com.liferay.multi.factor.authentication.web.internal.constants.MFAPortletKeys;
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
		"com.liferay.portlet.css-class-wrapper=portlet-mfa-verify",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.preferences-company-wide=true",
		"javax.portlet.display-name=Multi Factor Authentication Verify",
		"javax.portlet.init-param.mvc-command-names-default-views=/mfa_verify/view",
		"javax.portlet.init-param.portlet-title-based-navigation=true",
		"javax.portlet.init-param.template-path=/META-INF/resources/mfa_verify/",
		"javax.portlet.name=" + MFAPortletKeys.MFA_VERIFY,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.version=3.0",
		"portlet.add.default.resource.check.whitelist=" + MFAPortletKeys.MFA_VERIFY
	},
	service = Portlet.class
)
public class MFAVerifyPortlet extends MVCPortlet {

	@Activate
	protected void activate(BundleContext bundleContext) {
		PropsValues.PORTLET_INTERRUPTED_REQUEST_WHITELIST = ArrayUtil.append(
			PropsValues.PORTLET_INTERRUPTED_REQUEST_WHITELIST,
			MFAPortletKeys.MFA_VERIFY);

		_interruptedPortletRequestWhitelistUtil.
			resetPortletInvocationWhitelist();
	}

	@Deactivate
	protected void deactivate() {
		PropsValues.PORTLET_INTERRUPTED_REQUEST_WHITELIST = ArrayUtil.remove(
			PropsValues.PORTLET_INTERRUPTED_REQUEST_WHITELIST,
			MFAPortletKeys.MFA_VERIFY);

		_interruptedPortletRequestWhitelistUtil.
			resetPortletInvocationWhitelist();
	}

	@Reference
	private InterruptedPortletRequestWhitelistUtil
		_interruptedPortletRequestWhitelistUtil;

}