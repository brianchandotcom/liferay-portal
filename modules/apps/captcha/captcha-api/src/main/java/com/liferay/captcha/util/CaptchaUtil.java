/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.util;

import com.liferay.captcha.configuration.CaptchaConfiguration;
import com.liferay.captcha.provider.CaptchaProvider;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.captcha.Captcha;
import com.liferay.portal.kernel.captcha.CaptchaConfigurationException;
import com.liferay.portal.kernel.captcha.CaptchaException;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.IOException;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Pei-Jung Lan
 */
public class CaptchaUtil {

	public static void check(HttpServletRequest httpServletRequest)
		throws CaptchaException {

		getCaptcha(
			httpServletRequest
		).check(
			httpServletRequest
		);
	}

	public static void check(PortletRequest portletRequest)
		throws CaptchaException {

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(portletRequest);

		getCaptcha(
			httpServletRequest
		).check(
			httpServletRequest
		);
	}

	public static void enforceCaptcha(HttpServletRequest httpServletRequest)
		throws CaptchaConfigurationException {

		getCaptcha(
			httpServletRequest
		).enforceCaptcha(
			httpServletRequest
		);
	}

	public static void enforceCaptcha(PortletRequest portletRequest)
		throws CaptchaConfigurationException {

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(portletRequest);

		getCaptcha(
			httpServletRequest
		).enforceCaptcha(
			httpServletRequest
		);
	}

	public static Captcha getCaptcha(HttpServletRequest httpServletRequest)
		throws CaptchaConfigurationException {

		try {
			CaptchaConfiguration captchaConfiguration =
				(CaptchaConfiguration)
					ConfigurationProviderUtil.getCompanyConfiguration(
						CaptchaConfiguration.class,
						PortalUtil.getCompanyId(httpServletRequest));

			CaptchaProvider captchaProvider = _captchaProviderSnapshot.get();

			return captchaProvider.getCaptcha(captchaConfiguration);
		}
		catch (Exception exception) {
			throw new CaptchaConfigurationException(exception);
		}
	}

	public static String getTaglibPath(HttpServletRequest httpServletRequest)
		throws CaptchaConfigurationException {

		return getCaptcha(
			httpServletRequest
		).getTaglibPath();
	}

	public static boolean isEnabled(HttpServletRequest httpServletRequest)
		throws CaptchaConfigurationException {

		return getCaptcha(
			httpServletRequest
		).isEnabled(
			httpServletRequest
		);
	}

	public static boolean isEnabled(PortletRequest portletRequest)
		throws CaptchaConfigurationException {

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(portletRequest);

		return getCaptcha(
			httpServletRequest
		).isEnabled(
			httpServletRequest
		);
	}

	public static void serveImage(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws CaptchaConfigurationException, IOException {

		getCaptcha(
			httpServletRequest
		).serveImage(
			httpServletRequest, httpServletResponse
		);
	}

	private static final Snapshot<CaptchaProvider> _captchaProviderSnapshot =
		new Snapshot<>(CaptchaUtil.class, CaptchaProvider.class);

}