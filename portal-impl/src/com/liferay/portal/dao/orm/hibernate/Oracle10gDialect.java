/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.orm.hibernate;

import org.hibernate.type.StandardBasicTypes;

/**
 * @author Marco Galluzzi
 */
public class Oracle10gDialect extends org.hibernate.dialect.Oracle10gDialect {

	public Oracle10gDialect() {
		registerHibernateType(
			_BINARY_DOUBLE_TYPE, StandardBasicTypes.DOUBLE.getName());
	}

	private static final int _BINARY_DOUBLE_TYPE = 101;

}