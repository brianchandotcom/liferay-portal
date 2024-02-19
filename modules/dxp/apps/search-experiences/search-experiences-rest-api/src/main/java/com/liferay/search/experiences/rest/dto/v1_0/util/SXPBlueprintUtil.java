/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.rest.dto.v1_0.util;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.ElementInstance;
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

		SXPBlueprint sxpBlueprint2 = new SXPBlueprint() {
			{
				setDescription(sxpBlueprint1::getDescription);
				setDescription_i18n(sxpBlueprint1::getDescription_i18n);
				setElementInstances(sxpBlueprint1::getElementInstances);
				setExternalReferenceCode(
					sxpBlueprint1::getExternalReferenceCode);
				setId(sxpBlueprint1::getId);
				setTitle(sxpBlueprint1::getTitle);
				setTitle_i18n(sxpBlueprint1::getTitle_i18n);
			}
		};

		sxpBlueprint2.setConfiguration(
			() -> ConfigurationUtil.toConfiguration(configuration));

		return sxpBlueprint2;
	}

	public static SXPBlueprint unpack(SXPBlueprint sxpBlueprint) {
		Configuration configuration = sxpBlueprint.getConfiguration();

		if (configuration != null) {
			sxpBlueprint.setConfiguration(
				() -> ConfigurationUtil.unpack(configuration));
		}

		ElementInstance[] elementInstances = sxpBlueprint.getElementInstances();

		if (ArrayUtil.isNotEmpty(elementInstances)) {
			sxpBlueprint.setElementInstances(
				() -> ElementInstanceUtil.unpack(elementInstances));
		}

		return sxpBlueprint;
	}

}