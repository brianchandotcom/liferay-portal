/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.internal.test;

import java.util.Collections;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.core.Application;

/**
 * @author Víctor Galán
 */
public class TestPreviewURLApplication extends Application {

	public TestPreviewURLApplication(String previewURL) {
		_previewURL = previewURL;
	}

	@GET
	public String getPreviewURL() {
		return _previewURL;
	}

	@Override
	public Set<Object> getSingletons() {
		return Collections.<Object>singleton(this);
	}

	private final String _previewURL;

}