/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		sxpBlueprint2.setExternalReferenceCode(
			sxpBlueprint1.getExternalReferenceCode());
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