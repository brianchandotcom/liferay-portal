/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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