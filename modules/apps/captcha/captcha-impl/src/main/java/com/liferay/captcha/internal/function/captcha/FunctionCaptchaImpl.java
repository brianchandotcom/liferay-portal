/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.internal.function.captcha;

import com.liferay.captcha.internal.configuration.FunctionCaptchaImplConfiguration;
import com.liferay.captcha.simplecaptcha.SimpleCaptchaImpl;
import com.liferay.client.extension.type.CustomElementCET;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.captcha.Captcha;
import com.liferay.portal.kernel.content.security.policy.ContentSecurityPolicyNonceProviderUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Victor Silvestre
 */
@Component(
	configurationPid = "com.liferay.captcha.internal.configuration.FunctionCaptchaImplConfiguration",
	factory = "com.liferay.captcha.internal.function.captcha.FunctionCaptchaImpl",
	service = Captcha.class
)
public class FunctionCaptchaImpl extends SimpleCaptchaImpl {

	@Override
	public String getName() {
		return _functionCaptchaImplConfiguration.captchaName();
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		PrintWriter printWriter = httpServletResponse.getWriter();

		printWriter.write("<script");
		printWriter.write(
			ContentSecurityPolicyNonceProviderUtil.getNonceAttribute(
				httpServletRequest));
		printWriter.write(" src=\"");

		CustomElementCET cet = (CustomElementCET)_cetManager.getCET(
			PortalUtil.getCompanyId(httpServletRequest),
			_functionCaptchaImplConfiguration.
				customElementExternalReferenceCode());

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		printWriter.write(themeDisplay.getPortalURL() + cet.getURLs());

		printWriter.write("\" type=\"module\"></script>");
		printWriter.write(StringPool.LESS_THAN);
		printWriter.write(cet.getHTMLElementName());
		printWriter.write(" liferaywebdavurl=\"");

		StringBundler webDavURLSB = new StringBundler(4);

		try {
			Group group = _groupLocalService.getGroup(
				themeDisplay.getScopeGroupId());

			webDavURLSB.append(themeDisplay.getPortalURL());
			webDavURLSB.append("/webdav");
			webDavURLSB.append(group.getFriendlyURL());
			webDavURLSB.append("/document_library");
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}

		printWriter.write(
			StringUtil.replace(
				webDavURLSB.toString(), CharPool.QUOTE, "&quote;"));
		printWriter.write(StringPool.QUOTE);
		printWriter.write("></");
		printWriter.write(cet.getHTMLElementName());
		printWriter.write(StringPool.GREATER_THAN);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_functionCaptchaImplConfiguration = ConfigurableUtil.createConfigurable(
			FunctionCaptchaImplConfiguration.class, properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FunctionCaptchaImpl.class);

	@Reference
	private CETManager _cetManager;

	private volatile FunctionCaptchaImplConfiguration
		_functionCaptchaImplConfiguration;

	@Reference
	private GroupLocalService _groupLocalService;

}