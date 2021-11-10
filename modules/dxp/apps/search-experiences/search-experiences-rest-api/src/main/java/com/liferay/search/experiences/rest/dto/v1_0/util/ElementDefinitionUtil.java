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

package com.liferay.search.experiences.rest.dto.v1_0.util;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.ElementDefinition;

/**
 * @author André de Oliveira
 */
public class ElementDefinitionUtil {

	public static ElementDefinition toElementDefinition(String json) {
		return unpack(ElementDefinition.unsafeToDTO(json));
	}

	public static ElementDefinition[] toElementDefinitions(String json) {
		if (json == null) {
			return null;
		}

		try {
			return JSONUtil.toArray(
				JSONFactoryUtil.createJSONArray(json),
				jsonObject -> toElementDefinition(jsonObject.toString()),
				ElementDefinition.class);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public static ElementDefinition unpack(
		ElementDefinition elementDefinition) {

		if (elementDefinition == null) {
			return null;
		}

		Configuration configuration = elementDefinition.getConfiguration();

		if (configuration != null) {
			elementDefinition.setConfiguration(
				ConfigurationUtil.unpack(configuration));
		}

		return elementDefinition;
	}

	public static ElementDefinition[] unpack(
		ElementDefinition[] elementDefinitions) {

		for (int i = 0; i < elementDefinitions.length; i++) {
			elementDefinitions[i] = unpack(elementDefinitions[i]);
		}

		return elementDefinitions;
	}

}