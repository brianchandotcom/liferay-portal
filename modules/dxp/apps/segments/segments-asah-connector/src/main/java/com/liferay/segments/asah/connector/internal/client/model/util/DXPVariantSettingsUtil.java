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

		dxpVariantSettings.setTrafficSplit(split * 100);

		dxpVariantSettings.setDXPVariantId(segmentsExperienceKey);

		return dxpVariantSettings;
	}

}