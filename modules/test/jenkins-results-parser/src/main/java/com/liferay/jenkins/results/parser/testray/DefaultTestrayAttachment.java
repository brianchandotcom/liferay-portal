/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import java.net.URL;

/**
 * @author Michael Hashimoto
 */
public class DefaultTestrayAttachment extends BaseTestrayAttachment {

	public DefaultTestrayAttachment(
		TestrayCaseResult testrayCaseResult, String name, String key) {

		this(testrayCaseResult, name, key, null);
	}

	public DefaultTestrayAttachment(
		TestrayCaseResult testrayCaseResult, String name, String key, URL url) {

		super(testrayCaseResult, name, key, url);
	}

}