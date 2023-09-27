/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.expression;

import com.liferay.dynamic.data.mapping.expression.model.Expression;

import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
@ProviderType
public interface DDMExpression<T> {

	public T evaluate() throws DDMExpressionException;

	public com.liferay.petra.sql.dsl.expression.Expression<?> getDSLExpression()
		throws DDMExpressionException;

	public Expression getModel();

	public void setVariable(String name, Object value);

	public void setVariables(Map<String, Object> variables);

}