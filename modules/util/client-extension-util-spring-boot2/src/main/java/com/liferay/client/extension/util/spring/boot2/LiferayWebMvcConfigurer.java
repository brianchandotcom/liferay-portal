/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.util.spring.boot2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Gregory Amerson
 */
@Configuration
@Order(Ordered.LOWEST_PRECEDENCE)
public class LiferayWebMvcConfigurer implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry corsRegistry) {
		corsRegistry.addMapping(
			"/**"
		).allowedHeaders(
			"Authorization", "Content-Type"
		).allowedMethods(
			"DELETE", "GET", "HEAD", "OPTIONS", "PATCH", "POST", "PUT"
		).allowedOrigins(
			_getAllowedOrigins()
		);
	}

	private String[] _getAllowedOrigins() {
		List<String> allowedOrigins = new ArrayList<>();

		for (String lxcDXPDomain : _lxcDXPDomains.split("\\s*[,\n]\\s*")) {
			allowedOrigins.add("http://" + lxcDXPDomain);
			allowedOrigins.add("https://" + lxcDXPDomain);
		}

		return allowedOrigins.toArray(new String[0]);
	}

	@Value("${com.liferay.lxc.dxp.domains}")
	private String _lxcDXPDomains;

}