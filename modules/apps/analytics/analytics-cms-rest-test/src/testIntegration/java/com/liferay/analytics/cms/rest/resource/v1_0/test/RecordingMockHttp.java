/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.cms.rest.resource.v1_0.test;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.test.util.MockHttp;
import com.liferay.portal.kernel.util.Http;

import java.io.IOException;

import java.util.Map;

/**
 * @author Rachael Koestartyo
 */
public class RecordingMockHttp extends MockHttp {

	public RecordingMockHttp(
		Map<String, UnsafeSupplier<String, Exception>> unsafeSuppliers) {

		super(unsafeSuppliers);
	}

	public String getLocation() {
		return _location;
	}

	@Override
	public String URLtoString(Http.Options options) throws IOException {
		_location = options.getLocation();

		return super.URLtoString(options);
	}

	private String _location;

}