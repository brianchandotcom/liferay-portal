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

package com.liferay.search.experiences.federation.internal.x.zendesk;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import java.net.URL;

/**
 * @author André de Oliveira
 */
public class ZendeskUtil {

	public static ZendeskArticlesPage getZendeskArticlesPage(String address) {
		try {
			return _objectMapper.readValue(
				new URL(address), ZendeskArticlesPage.class);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static ObjectMapper _getDefaultObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();

		mapper.configure(
			DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		return mapper;
	}

	private static final ObjectMapper _objectMapper = _getDefaultObjectMapper();

}