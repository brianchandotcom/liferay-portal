/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.internal.provider;

import com.liferay.captcha.configuration.CaptchaConfiguration;
import com.liferay.captcha.provider.CaptchaProvider;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.captcha.Captcha;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Lily Chi
 */
@Component(
	configurationPid = "com.liferay.captcha.configuration.CaptchaConfiguration",
	service = CaptchaProvider.class
)
public class CaptchaProviderImpl implements CaptchaProvider {

	@Override
	public Captcha getCaptcha() throws ConfigurationException {
		CaptchaConfiguration captchaConfiguration =
			(CaptchaConfiguration)
				ConfigurationProviderUtil.getCompanyConfiguration(
					CaptchaConfiguration.class,
					CompanyThreadLocal.getCompanyId());

		String captchaClassName = captchaConfiguration.captchaEngine();

		return _serviceTrackerMap.getService(captchaClassName);
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, Captcha.class, "captcha.engine.impl");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, Captcha> _serviceTrackerMap;

}