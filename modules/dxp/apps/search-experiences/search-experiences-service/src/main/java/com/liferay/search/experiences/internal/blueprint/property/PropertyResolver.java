/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.blueprint.property;

import java.util.Map;

/**
 * @author André de Oliveira
 */
public interface PropertyResolver {

	public Object resolve(String name, Map<String, String> options);

}