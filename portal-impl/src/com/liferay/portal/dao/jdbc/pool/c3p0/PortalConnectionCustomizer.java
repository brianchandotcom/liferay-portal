/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.dao.jdbc.pool.c3p0;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.util.PropsUtil;

import com.mchange.v2.c3p0.ConnectionCustomizer;

import java.sql.Connection;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalConnectionCustomizer implements ConnectionCustomizer {

	@Override
	public void onAcquire(
			Connection connection, String parentDataSourceIdentityToken)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("JDBC property prefix " + parentDataSourceIdentityToken);
		}

		String transactionIsolation = PropsUtil.get(
			parentDataSourceIdentityToken + "transactionIsolation");

		if (_log.isDebugEnabled()) {
			_log.debug("Custom transaction isolation " + transactionIsolation);
		}

		if (transactionIsolation != null) {
			connection.setTransactionIsolation(
				GetterUtil.getInteger(transactionIsolation));
		}
	}

	@Override
	public void onCheckIn(
		Connection connection, String parentDataSourceIdentityToken) {
	}

	@Override
	public void onCheckOut(
		Connection connection, String parentDataSourceIdentityToken) {
	}

	@Override
	public void onDestroy(
		Connection connection, String parentDataSourceIdentityToken) {
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortalConnectionCustomizer.class);

}