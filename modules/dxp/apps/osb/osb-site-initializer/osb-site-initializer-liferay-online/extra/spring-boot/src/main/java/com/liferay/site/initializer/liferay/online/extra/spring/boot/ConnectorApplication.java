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

package com.liferay.site.initializer.liferay.online.extra.spring.boot;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Zoltán Takács
 */
@SpringBootApplication
public class ConnectorApplication {

	public static Charset charset() {
		return StandardCharsets.UTF_8;
	}

	public static void main(String[] args) {
		SpringApplication.run(ConnectorApplication.class, args);
	}

}