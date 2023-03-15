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