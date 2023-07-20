/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.filter.factory;

import com.liferay.portal.odata.entity.EntityModel;

/**
 * @author Gabriel Albuquerque
 */
public interface FilterFactory<T> {

	public T create(
		EntityModel entityModel, String filterString, long objectDefinitionId);

	public T create(String filterString, long objectDefinitionId);

}