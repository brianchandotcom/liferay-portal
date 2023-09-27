/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.internal.change.tracking.hibernate;

import com.liferay.portal.change.tracking.sql.CTSQLTransformer;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import org.hibernate.EmptyInterceptor;

/**
 * @author Preston Crary
 */
public class CTSQLInterceptor extends EmptyInterceptor {

	@Override
	public String onPrepareStatement(String sql) {
		if (_enabled) {
			return _ctSQLTransformer.transform(sql);
		}

		return sql;
	}

	public void setEnabled(boolean enabled) {
		_enabled = enabled;
	}

	private static volatile CTSQLTransformer _ctSQLTransformer =
		ServiceProxyFactory.newServiceTrackedInstance(
			CTSQLTransformer.class, CTSQLInterceptor.class, "_ctSQLTransformer",
			true);

	private boolean _enabled;

}