/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.web.util;

import com.damnhandy.uri.template.UriTemplate;

import java.net.URI;

import java.util.Map;

import org.springframework.web.util.DefaultUriTemplateHandler;

/**
 * @author Shinn Lok
 */
public class UriTemplateHandler extends DefaultUriTemplateHandler {

	@Override
	public URI expand(String uriTemplateString, Map<String, ?> uriVariables) {
		try {
			UriTemplate uriTemplate = UriTemplate.fromTemplate(
				uriTemplateString);

			return new URI(
				uriTemplate.expand((Map<String, Object>)uriVariables));
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

}