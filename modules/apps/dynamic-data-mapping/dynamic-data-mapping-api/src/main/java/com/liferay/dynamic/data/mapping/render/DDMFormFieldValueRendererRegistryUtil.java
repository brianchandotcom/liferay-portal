/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.render;

import com.liferay.osgi.util.service.Snapshot;

/**
 * @author Marcellus Tavares
 */
public class DDMFormFieldValueRendererRegistryUtil {

	public static DDMFormFieldValueRenderer getDDMFormFieldValueRenderer(
		String ddmFormFieldType) {

		DDMFormFieldValueRendererRegistry ddmFormFieldValueRendererRegistry =
			_ddmFormFieldValueRendererRegistrySnapshot.get();

		return ddmFormFieldValueRendererRegistry.getDDMFormFieldValueRenderer(
			ddmFormFieldType);
	}

	private static final Snapshot<DDMFormFieldValueRendererRegistry>
		_ddmFormFieldValueRendererRegistrySnapshot = new Snapshot<>(
			DDMFormFieldValueRendererRegistryUtil.class,
			DDMFormFieldValueRendererRegistry.class);

}