/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.pubsub.configuration;

import com.liferay.osb.spring.boot.client.pubsub.router.BaseMessageRouter;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Kyle Bischof
 */
@Configuration
@EnableConfigurationProperties(PubsubProperties.class)
public class PubsubAutoConfiguration {

	@Bean
	public BaseMessageRouter baseMessageRouter() {
		return new BaseMessageRouter();
	}

}