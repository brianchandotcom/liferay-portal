/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.internal.yaml.openapi;

import java.util.Map;

/**
 * @author Peter Shin
 */
public class RequestBody {

	public Map<String, Content> getContent() {
		return _content;
	}

	public void setContent(Map<String, Content> content) {
		_content = content;
	}

	private Map<String, Content> _content;

}