/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.filter.factory;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.expression.Expression;

/**
 * @author Gabriel Albuquerque
 */
public interface FilterFactory<T> {

	public T create(
		EntityModel entityModel, String filterString,
		ObjectDefinition objectDefinition);

	public T create(
		Expression filterExpression, ObjectDefinition objectDefinition);

	public T create(String filterString, ObjectDefinition objectDefinition);

}