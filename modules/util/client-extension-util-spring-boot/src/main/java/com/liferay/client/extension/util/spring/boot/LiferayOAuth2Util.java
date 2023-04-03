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

package com.liferay.client.extension.util.spring.boot;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Raymond Augé
 * @author Gregory Amerson
 * @author Brian Wing Shun Chan
 */
public class LiferayOAuth2Util {

	public static String getClientId(
		String externalReferenceCode, String lxcMainDomain,
		String lxcServerProtocol) {

		while (true) {
			try {
				String baseURL =
					lxcServerProtocol + "://" + lxcMainDomain +
						"/o/oauth2/application";

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Obtaining a clientId from " + baseURL + " using " +
							externalReferenceCode);
				}

				return WebClient.create(
					baseURL
				).get(
				).uri(
					uriBuilder -> uriBuilder.queryParam(
						"externalReferenceCode", externalReferenceCode
					).build()
				).retrieve(
				).bodyToMono(
					ApplicationInfo.class
				).block(
				).client_id;
			}
			catch (Throwable throwable) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get client ID: " + throwable.getMessage());
				}

				try {
					Thread.sleep(1000);
				}
				catch (InterruptedException interruptedException) {
					_log.error(
						"Thread.sleep interupted: " +
							interruptedException.getMessage());
				}
			}
		}
	}

	private static final Log _log = LogFactory.getLog(LiferayOAuth2Util.class);

	private static class ApplicationInfo {

		public String client_id;

	}

}