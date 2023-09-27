/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.display.url.provider;

/**
 * @author Jürgen Kappler
 */
public interface InfoEditURLProviderRegistry {

	public <T> InfoEditURLProvider<T> getInfoEditURLProvider(String className);

}