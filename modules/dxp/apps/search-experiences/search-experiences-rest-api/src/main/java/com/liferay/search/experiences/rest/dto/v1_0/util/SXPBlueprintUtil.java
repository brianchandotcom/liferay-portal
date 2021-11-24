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

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;

/**
 * @author Gabriel Albuquerque
 */
public class SXPBlueprintUtil {

	public static SXPBlueprint toSXPBlueprint(String json) {
		return unpack(SXPBlueprint.unsafeToDTO(json));
	}

	public static SXPBlueprint toSXPBlueprint(
		SXPBlueprint sxpBlueprint1, String configuration) {

		SXPBlueprint sxpBlueprint2 = new SXPBlueprint();

		sxpBlueprint2.setConfiguration(
			ConfigurationUtil.toConfiguration(configuration));
		sxpBlueprint2.setDescription(sxpBlueprint1.getDescription());
		sxpBlueprint2.setDescription_i18n(sxpBlueprint1.getDescription_i18n());
		sxpBlueprint2.setElementInstances(sxpBlueprint1.getElementInstances());
		sxpBlueprint2.setId(sxpBlueprint1.getId());
		sxpBlueprint2.setTitle(sxpBlueprint1.getTitle());
		sxpBlueprint2.setTitle_i18n(sxpBlueprint1.getTitle_i18n());

		return sxpBlueprint2;
	}

	public static SXPBlueprint unpack(SXPBlueprint sxpBlueprint) {
		Configuration configuration = sxpBlueprint.getConfiguration();

		if (configuration != null) {
			sxpBlueprint.setConfiguration(
				ConfigurationUtil.unpack(configuration));
		}

		if (ArrayUtil.isNotEmpty(sxpBlueprint.getElementInstances())) {
			sxpBlueprint.setElementInstances(
				ElementInstanceUtil.unpack(sxpBlueprint.getElementInstances()));
		}

		return sxpBlueprint;
	}

}