/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.pubsub.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * @author Kyle Bischof
 */
@ComponentScan(
	basePackages = "com.liferay.osb.spring.boot.client.pubsub",
	excludeFilters = @ComponentScan.Filter(classes = PubsubAutoConfiguration.class, type = FilterType.ASSIGNABLE_TYPE)
)
@Configuration
public class PubsubAutoConfiguration {
}