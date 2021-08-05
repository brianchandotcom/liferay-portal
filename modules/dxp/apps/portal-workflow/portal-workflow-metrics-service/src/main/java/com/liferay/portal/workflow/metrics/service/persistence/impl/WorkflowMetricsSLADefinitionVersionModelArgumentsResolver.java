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

package com.liferay.portal.workflow.metrics.service.persistence.impl;

import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinitionVersionTable;
import com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLADefinitionVersionImpl;
import com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLADefinitionVersionModelImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * The arguments resolver class for retrieving value from WorkflowMetricsSLADefinitionVersion.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	immediate = true,
	service = {
		WorkflowMetricsSLADefinitionVersionModelArgumentsResolver.class,
		ArgumentsResolver.class
	}
)
public class WorkflowMetricsSLADefinitionVersionModelArgumentsResolver
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

		WorkflowMetricsSLADefinitionVersionModelImpl
			workflowMetricsSLADefinitionVersionModelImpl =
				(WorkflowMetricsSLADefinitionVersionModelImpl)baseModel;

		long columnBitmask =
			workflowMetricsSLADefinitionVersionModelImpl.getColumnBitmask();

		if (!checkColumn || (columnBitmask == 0)) {
			return _getValue(
				workflowMetricsSLADefinitionVersionModelImpl, columnNames,
				original);
		}

		Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
			finderPath);

		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				finderPathColumnBitmask |=
					workflowMetricsSLADefinitionVersionModelImpl.
						getColumnBitmask(columnName);
			}

			if (finderPath.isBaseModelResult() &&
				(WorkflowMetricsSLADefinitionVersionPersistenceImpl.
					FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION ==
						finderPath.getCacheName())) {

				finderPathColumnBitmask |= _ORDER_BY_COLUMNS_BITMASK;
			}

			_finderPathColumnBitmasksCache.put(
				finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return _getValue(
				workflowMetricsSLADefinitionVersionModelImpl, columnNames,
				original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return WorkflowMetricsSLADefinitionVersionImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return WorkflowMetricsSLADefinitionVersionTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		WorkflowMetricsSLADefinitionVersionModelImpl
			workflowMetricsSLADefinitionVersionModelImpl,
		String[] columnNames, boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] =
					workflowMetricsSLADefinitionVersionModelImpl.
						getColumnOriginalValue(columnName);
			}
			else {
				arguments[i] =
					workflowMetricsSLADefinitionVersionModelImpl.getColumnValue(
						columnName);
			}
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

	private static final long _ORDER_BY_COLUMNS_BITMASK;

	static {
		long orderByColumnsBitmask = 0;

		orderByColumnsBitmask |=
			WorkflowMetricsSLADefinitionVersionModelImpl.getColumnBitmask(
				"modifiedDate");

		_ORDER_BY_COLUMNS_BITMASK = orderByColumnsBitmask;
	}

}