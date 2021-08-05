/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.kaleo.forms.service.persistence.impl;

import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLinkTable;
import com.liferay.portal.workflow.kaleo.forms.model.impl.KaleoProcessLinkImpl;
import com.liferay.portal.workflow.kaleo.forms.model.impl.KaleoProcessLinkModelImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * The arguments resolver class for retrieving value from KaleoProcessLink.
 *
 * @author Marcellus Tavares
 * @generated
 */
@Component(
	immediate = true,
	service = {
		KaleoProcessLinkModelArgumentsResolver.class, ArgumentsResolver.class
	}
)
public class KaleoProcessLinkModelArgumentsResolver
	implements ArgumentsResolver {

	@Override
	public Object[] getArguments(
		FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
		boolean original) {

		String[] columnNames = finderPath.getColumnNames();

		if ((columnNames == null) || (columnNames.length == 0)) {
			if (baseModel.isNew()) {
				return new Object[0];
			}

			return null;
		}

		KaleoProcessLinkModelImpl kaleoProcessLinkModelImpl =
			(KaleoProcessLinkModelImpl)baseModel;

		long columnBitmask = kaleoProcessLinkModelImpl.getColumnBitmask();

		if (!checkColumn || (columnBitmask == 0)) {
			return _getValue(kaleoProcessLinkModelImpl, columnNames, original);
		}

		Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
			finderPath);

		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				finderPathColumnBitmask |=
					kaleoProcessLinkModelImpl.getColumnBitmask(columnName);
			}

			_finderPathColumnBitmasksCache.put(
				finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return _getValue(kaleoProcessLinkModelImpl, columnNames, original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return KaleoProcessLinkImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return KaleoProcessLinkTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		KaleoProcessLinkModelImpl kaleoProcessLinkModelImpl,
		String[] columnNames, boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] = kaleoProcessLinkModelImpl.getColumnOriginalValue(
					columnName);
			}
			else {
				arguments[i] = kaleoProcessLinkModelImpl.getColumnValue(
					columnName);
			}
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

}