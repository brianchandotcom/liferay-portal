/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.data.provider;

import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;

/**
 * @author Leonardo Barros
 */
public interface DDMDataProviderInstanceSettings {

	public <T> T getSettings(
		DDMDataProviderInstance ddmDataProviderInstance, Class<T> clazz);

}