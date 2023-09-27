/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.spring.hibernate;

import java.sql.Connection;

import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.env.spi.SchemaNameResolver;

/**
 * @author Shuyang Zhou
 */
public class NullSchemaNameResolver implements SchemaNameResolver {

	@Override
	public String resolveSchemaName(Connection connection, Dialect dialect) {
		return null;
	}

}