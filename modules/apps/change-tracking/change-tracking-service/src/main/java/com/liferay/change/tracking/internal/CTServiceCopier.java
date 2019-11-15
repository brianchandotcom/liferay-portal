/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.change.tracking.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.change.tracking.registry.CTModelRegistration;
import com.liferay.portal.change.tracking.registry.CTModelRegistry;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnectionUtil;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import java.sql.Connection;

import java.util.Map;

/**
 * @author Preston Crary
 */
public class CTServiceCopier<T extends CTModel<T>> {

	public CTServiceCopier(
		CTService<T> ctService, long sourceCTCollectionId,
		long targetCTCollectionId) {

		_ctService = ctService;
		_sourceCTCollectionId = sourceCTCollectionId;
		_targetCTCollectionId = targetCTCollectionId;
	}

	public void copy() throws Exception {
		_ctService.updateWithUnsafeFunction(this::_copy);
	}

	private Void _copy(CTPersistence<T> ctPersistence) throws Exception {
		CTModelRegistration ctModelRegistration =
			CTModelRegistry.getCTModelRegistration(
				ctPersistence.getModelClass());

		if (ctModelRegistration == null) {
			throw new IllegalStateException(
				"Unable find CTModelRegistration for " +
					_ctService.getModelClass());
		}

		Connection connection = CurrentConnectionUtil.getConnection(
			ctPersistence.getDataSource());

		Map<String, Integer> tableColumnsMap =
			ctModelRegistration.getTableColumnsMap();

		StringBundler sb = new StringBundler(3 * tableColumnsMap.size() + 5);

		sb.append("select ");

		for (String name : tableColumnsMap.keySet()) {
			if (name.equals("ctCollectionId")) {
				sb.append(_targetCTCollectionId);
				sb.append(" as ");
			}
			else {
				sb.append("t1.");
			}

			sb.append(name);
			sb.append(", ");
		}

		sb.setStringAt(" from ", sb.index() - 1);

		sb.append(ctModelRegistration.getTableName());
		sb.append(" t1 where t1.ctCollectionId = ");
		sb.append(_sourceCTCollectionId);

		CTRowUtil.copyCTRows(ctModelRegistration, connection, sb.toString());

		return null;
	}

	private final CTService<T> _ctService;
	private final long _sourceCTCollectionId;
	private final long _targetCTCollectionId;

}