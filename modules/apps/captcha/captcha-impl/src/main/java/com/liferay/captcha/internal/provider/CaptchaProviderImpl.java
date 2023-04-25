/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.captcha.internal.provider;

import com.liferay.captcha.configuration.CaptchaConfiguration;
import com.liferay.captcha.provider.CaptchaProvider;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.captcha.Captcha;

import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Lily Chi
 */
@Component(
	configurationPid = "com.liferay.captcha.configuration.CaptchaConfiguration",
	service = CaptchaProvider.class
)
public class CaptchaProviderImpl implements CaptchaProvider {

	@Override
	public Captcha getCaptcha() {
		String captchaClassName = _captchaConfiguration.captchaEngine();

		return _serviceTrackerMap.getService(captchaClassName);
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		modified(properties);

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, Captcha.class, "captcha.engine.impl");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	@Modified
	protected void modified(Map<String, Object> properties) {
		_captchaConfiguration = ConfigurableUtil.createConfigurable(
			CaptchaConfiguration.class, properties);
	}

	private volatile CaptchaConfiguration _captchaConfiguration;
	private ServiceTrackerMap<String, Captcha> _serviceTrackerMap;

}