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

import com.liferay.search.experiences.rest.dto.v1_0.ElementDefinition;
import com.liferay.search.experiences.rest.dto.v1_0.SXPElement;

/**
 * @author André de Oliveira
 */
public class SXPElementUtil {

	public static SXPElement toSXPElement(String json) {
		return unpack(SXPElement.unsafeToDTO(json));
	}

	protected static SXPElement unpack(SXPElement sxpElement) {
		ElementDefinition elementDefinition = sxpElement.getElementDefinition();

		if (elementDefinition != null) {
			sxpElement.setElementDefinition(
				ElementDefinitionUtil.unpack(elementDefinition));
		}

		return sxpElement;
	}

}