/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.site.initializer.liferay.online.extra.spring.boot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * @author Zoltán Takács
 */
@Configuration
public class LoggingConfig {

	@Bean
	public CommonsRequestLoggingFilter requestLoggingFilter() {
		CommonsRequestLoggingFilter requestLoggingFilter =
			new CommonsRequestLoggingFilter();

		requestLoggingFilter.setIncludeClientInfo(true);
		requestLoggingFilter.setIncludeQueryString(true);
		requestLoggingFilter.setIncludePayload(true);
		requestLoggingFilter.setMaxPayloadLength(10000);

		return requestLoggingFilter;
	}

}