/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.virtual.host.test;

import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.Inject;

/**
 * @author Dante Wang
 */
public abstract class BaseVirtualHostTestCase {

	protected void assertURLtoString(
			UnsafeBiConsumer<Integer, String, Exception> unsafeBiConsumer,
			String url)
		throws Exception {

		Http.Options options = new Http.Options();

		options.setLocation(url);

		String body = http.URLtoString(options);

		Http.Response response = options.getResponse();

		unsafeBiConsumer.accept(response.getResponseCode(), body);
	}

	protected static final String COMPANY_HOST_1 = "company1.localhost";

	protected static final String COMPANY_HOST_2 = "company2.localhost";

	@Inject
	protected Http http;

}