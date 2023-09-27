/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.field.business.type;

import java.util.List;
import java.util.Set;

/**
 * @author Marcela Cunha
 */
public interface ObjectFieldBusinessTypeRegistry {

	public ObjectFieldBusinessType getObjectFieldBusinessType(String name);

	public List<ObjectFieldBusinessType> getObjectFieldBusinessTypes();

	public Set<String> getObjectFieldDBTypes();

}