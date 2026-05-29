/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.pubsub.configuration;

import com.liferay.osb.spring.boot.client.pubsub.credentials.ServiceAccountCredentialsProvider;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Kyle Bischof
 */
@Configuration
public class PubsubAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public ServiceAccountCredentialsProvider
		serviceAccountCredentialsProvider() {

		return new ServiceAccountCredentialsProvider();
	}

}