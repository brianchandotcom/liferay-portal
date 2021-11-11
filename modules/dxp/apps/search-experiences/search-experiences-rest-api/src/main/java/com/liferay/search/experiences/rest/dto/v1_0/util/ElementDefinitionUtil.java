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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.ElementDefinition;

/**
 * @author André de Oliveira
 */
public class ElementDefinitionUtil {

	public static ElementDefinition toElementDefinition(String json) {
		if (Validator.isNull(json)) {
			return null;
		}

		return unpack(ElementDefinition.unsafeToDTO(json));
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

}