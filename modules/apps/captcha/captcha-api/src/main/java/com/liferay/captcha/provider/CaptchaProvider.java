/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.provider;

import com.liferay.captcha.configuration.CaptchaConfiguration;
import com.liferay.portal.kernel.captcha.Captcha;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;

/**
 * @author Lily Chi
 */
public interface CaptchaProvider {

	public Captcha getCaptcha() throws ConfigurationException;

	public CaptchaConfiguration getCaptchaConfiguration()
		throws ConfigurationException;

}