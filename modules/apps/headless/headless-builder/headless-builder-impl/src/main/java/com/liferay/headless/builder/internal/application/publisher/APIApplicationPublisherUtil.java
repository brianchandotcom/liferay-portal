/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.application.publisher;

import com.liferay.headless.builder.application.provider.APIApplicationProvider;
import com.liferay.headless.builder.application.publisher.APIApplicationPublisher;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Alejandro Tardín
 */
public class APIApplicationPublisherUtil {

	public static void publish(String baseURL, long companyId)
		throws Exception {

		_apiApplicationPublisher.publish(
			_apiApplicationProvider.fetchAPIApplication(baseURL, companyId));
	}

	public static void unpublish(String baseURL, long companyId) {
		_apiApplicationPublisher.unpublish(baseURL, companyId);
	}

	private static volatile APIApplicationProvider _apiApplicationProvider =
		ServiceProxyFactory.newServiceTrackedInstance(
			APIApplicationProvider.class, APIApplicationPublisherUtil.class,
			"_apiApplicationProvider", false, true);
	private static volatile APIApplicationPublisher _apiApplicationPublisher =
		ServiceProxyFactory.newServiceTrackedInstance(
			APIApplicationPublisher.class, APIApplicationPublisherUtil.class,
			"_apiApplicationPublisher", false, true);

}