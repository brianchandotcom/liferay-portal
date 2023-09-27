/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.json;

/**
 * @author Igor Spasic
 */
public interface JSONSerializer {

	public JSONSerializer exclude(String... fields);

	public JSONSerializer include(String... fields);

	public String serialize(Object target);

	public String serializeDeep(Object target);

	public JSONSerializer transform(
		JSONTransformer jsonTransformer, Class<?> type);

	public JSONSerializer transform(
		JSONTransformer jsonTransformer, String field);

}