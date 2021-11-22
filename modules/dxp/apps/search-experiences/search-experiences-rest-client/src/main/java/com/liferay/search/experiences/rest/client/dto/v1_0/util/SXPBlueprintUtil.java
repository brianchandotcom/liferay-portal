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