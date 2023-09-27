/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.render;

import com.liferay.osgi.util.service.Snapshot;

/**
 * @author Pablo Carvalho
 */
public class DDMFormFieldRendererRegistryUtil {

	public static DDMFormFieldRenderer getDDMFormFieldRenderer(
		String ddmFormFieldType) {

		DDMFormFieldRendererRegistry ddmFormFieldRendererRegistry =
			_ddmFormFieldRendererRegistrySnapshot.get();

		return ddmFormFieldRendererRegistry.getDDMFormFieldRenderer(
			ddmFormFieldType);
	}

	private static final Snapshot<DDMFormFieldRendererRegistry>
		_ddmFormFieldRendererRegistrySnapshot = new Snapshot<>(
			DDMFormFieldRendererRegistryUtil.class,
			DDMFormFieldRendererRegistry.class);

}