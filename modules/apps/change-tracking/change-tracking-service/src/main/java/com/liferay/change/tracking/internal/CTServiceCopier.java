/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal;

import com.liferay.petra.string.StringBundler;
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
		Connection connection = CurrentConnectionUtil.getConnection(
			ctPersistence.getDataSource());

		Map<String, Integer> tableColumnsMap =
			ctPersistence.getTableColumnsMap();

		StringBundler sb = new StringBundler((3 * tableColumnsMap.size()) + 5);

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

		sb.append(ctPersistence.getTableName());
		sb.append(" t1 where t1.ctCollectionId = ");
		sb.append(_sourceCTCollectionId);

		CTRowUtil.copyCTRows(ctPersistence, connection, sb.toString());

		return null;
	}

	private final CTService<T> _ctService;
	private final long _sourceCTCollectionId;
	private final long _targetCTCollectionId;

}