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

package com.liferay.document.library.web.internal.configuration.util;

import com.liferay.document.library.web.internal.configuration.FFExpirationDateReviewDateConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Alicia García
 */
@Component(
	configurationPid = "com.liferay.document.library.web.internal.configuration.FFExpirationDateReviewDateConfiguration",
	immediate = true,
	service = FFExpirationDateReviewDateConfigurationUtil.class
)
public class FFExpirationDateReviewDateConfigurationUtil {

	public static boolean expirationDateEnabled() {
		return _ffExpirationDateReviewDateConfiguration.expirationDateEnabled();
	}

	public static boolean reviewDateEnabled() {
		return _ffExpirationDateReviewDateConfiguration.reviewDateEnabled();
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_ffExpirationDateReviewDateConfiguration =
			ConfigurableUtil.createConfigurable(
				FFExpirationDateReviewDateConfiguration.class, properties);
	}

	private static volatile FFExpirationDateReviewDateConfiguration
		_ffExpirationDateReviewDateConfiguration;

}