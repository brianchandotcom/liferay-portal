/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.serializer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Daniel Sanz
 */
public interface FDSSerializer<T> {

	public static final String TYPE_CUSTOM = "custom";

	public static final String TYPE_SYSTEM = "system";

	public String getKey();

	public T serialize(String fdsName, HttpServletRequest httpServletRequest);

}