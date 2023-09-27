/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.client.model.util;

import com.liferay.segments.asah.connector.internal.client.model.DXPVariantSettings;

import java.util.Objects;

/**
 * @author Sarai Díaz
 */
public class DXPVariantSettingsUtil {

	public static DXPVariantSettings toDXPVariantSettings(
		String controlSegmentsExperienceKey, String segmentsExperienceKey,
		Double split) {

		DXPVariantSettings dxpVariantSettings = new DXPVariantSettings();

		dxpVariantSettings.setControl(
			Objects.equals(
				controlSegmentsExperienceKey, segmentsExperienceKey));
		dxpVariantSettings.setDXPVariantId(segmentsExperienceKey);
		dxpVariantSettings.setTrafficSplit(split * 100);

		return dxpVariantSettings;
	}

}