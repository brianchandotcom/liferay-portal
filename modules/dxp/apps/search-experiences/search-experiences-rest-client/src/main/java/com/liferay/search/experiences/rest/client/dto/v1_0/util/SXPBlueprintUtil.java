/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.rest.client.dto.v1_0.util;

import com.liferay.search.experiences.rest.client.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.client.dto.v1_0.SXPBlueprint;

/**
 * @author Bryan Engler
 */
public class SXPBlueprintUtil {

	public static SXPBlueprint toSXPBlueprint(String json) {
		return unpack(SXPBlueprint.toDTO(json));
	}

	protected static SXPBlueprint unpack(SXPBlueprint sxpBlueprint) {
		Configuration configuration = sxpBlueprint.getConfiguration();

		if (configuration != null) {
			sxpBlueprint.setConfiguration(
				ConfigurationUtil.unpack(configuration));
		}

		return sxpBlueprint;
	}

}