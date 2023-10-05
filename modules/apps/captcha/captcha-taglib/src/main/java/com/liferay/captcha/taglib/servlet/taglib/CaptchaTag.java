/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.taglib.servlet.taglib;

import com.liferay.captcha.configuration.CaptchaConfiguration;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Brian Wing Shun Chan
 */
public class CaptchaTag extends IncludeTag {

	public String getErrorMessage() {
		return _errorMessage;
	}

	public String getUrl() {
		return _url;
	}

	public void setErrorMessage(String errorMessage) {
		_errorMessage = errorMessage;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(_servletContextSnapshot.get());
	}

	public void setUrl(String url) {
		_url = url;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_errorMessage = null;
		_url = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-captcha:captcha:errorMessage", _errorMessage);
		httpServletRequest.setAttribute(
			"liferay-captcha:captcha:url", _getURL(httpServletRequest));
	}

	private String _getURL(HttpServletRequest httpServletRequest) {
		if (Validator.isNotNull(_url)) {
			return _url;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String url = themeDisplay.getPathMain() + "/portal/captcha/get_image";

		if (FeatureFlagManagerUtil.isEnabled("LPS-185213")) {
			try {
				CaptchaConfiguration captchaConfiguration =
					ConfigurationProviderUtil.getSystemConfiguration(
						CaptchaConfiguration.class);

				if (captchaConfiguration.enableSimpleCaptchaHeadlessAPI()) {
					url = "/o/captcha/v1.0/simple";
				}
			}
			catch (ConfigurationException configurationException) {
				if (_log.isDebugEnabled()) {
					_log.debug(configurationException);
				}
			}
		}

		String portletId = PortalUtil.getPortletId(httpServletRequest);

		if (Validator.isNotNull(portletId)) {
			url += "?portletId=" + portletId;
		}

		return url;
	}

	private static final String _PAGE = "/captcha/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(CaptchaTag.class);

	private static final Snapshot<ServletContext> _servletContextSnapshot =
		new Snapshot<>(
			CaptchaTag.class, ServletContext.class,
			"(osgi.web.symbolicname=com.liferay.captcha.taglib)");

	private String _errorMessage;
	private String _url;

}