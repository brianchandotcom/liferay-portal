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

package com.liferay.change.tracking.internal.reference;

import com.liferay.change.tracking.internal.reference.helper.TableReferenceDefinitionHelperImpl;
import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = {})
public class TableReferenceDefinitionManager {

	public TableReferenceInfo<?> getTableReferenceInfo(Table<?> table) {
		return _tableReferenceInfos.get(table);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTracker = new ServiceTracker<>(
			bundleContext, TableReferenceDefinition.class,
			new TableReferenceDefinitionServiceTrackerCustomizer(
				bundleContext));

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TableReferenceDefinitionManager.class);

	private ServiceTracker<?, ?> _serviceTracker;
	private final Map<Table<?>, TableReferenceInfo<?>> _tableReferenceInfos =
		new ConcurrentHashMap<>();

	private class TableReferenceDefinitionServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<TableReferenceDefinition, TableReferenceInfo<?>> {

		@Override
		public TableReferenceInfo<?> addingService(
			ServiceReference<TableReferenceDefinition> serviceReference) {

			TableReferenceDefinition<?> tableReferenceDefinition =
				_bundleContext.getService(serviceReference);

			return _createTableReferenceContext(tableReferenceDefinition);
		}

		@Override
		public void modifiedService(
			ServiceReference<TableReferenceDefinition> serviceReference,
			TableReferenceInfo<?> tableReferenceInfo) {
		}

		@Override
		public void removedService(
			ServiceReference<TableReferenceDefinition> serviceReference,
			TableReferenceInfo<?> tableReferenceInfo) {

			TableReferenceDefinition<?> tableReferenceDefinition =
				tableReferenceInfo.getTableReferenceDefinition();

			_tableReferenceInfos.remove(tableReferenceDefinition.getTable());

			_bundleContext.ungetService(serviceReference);
		}

		private TableReferenceDefinitionServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		@SuppressWarnings("unchecked")
		private <T extends Table<T>> TableReferenceInfo<?>
			_createTableReferenceContext(
				TableReferenceDefinition<T> tableReferenceDefinition) {

			T table = tableReferenceDefinition.getTable();

			Column<T, ?> primaryKeyColumn = null;

			for (Column<T, ?> column : table.getColumns()) {
				if (column.isPrimaryKey() &&
					!Objects.equals(column.getName(), "ctCollectionId")) {

					primaryKeyColumn = column;

					break;
				}
			}

			if (primaryKeyColumn == null) {
				_log.error(
					"No primary key column found for " +
						tableReferenceDefinition);

				return null;
			}

			if (!Long.class.isAssignableFrom(primaryKeyColumn.getJavaType())) {
				_log.error(
					"Primary key column must be of long type for " +
						tableReferenceDefinition);

				return null;
			}

			TableReferenceDefinitionHelperImpl<T>
				tableReferenceDefinitionHelperImpl =
					new TableReferenceDefinitionHelperImpl<>(
						tableReferenceDefinition,
						(Column<T, Long>)primaryKeyColumn);

			tableReferenceDefinition.defineTableReferences(
				tableReferenceDefinitionHelperImpl);

			TableReferenceInfo<?> tableReferenceInfo =
				tableReferenceDefinitionHelperImpl.getTableReferenceInfo();

			if (tableReferenceInfo != null) {
				_tableReferenceInfos.put(table, tableReferenceInfo);
			}

			return tableReferenceInfo;
		}

		private final BundleContext _bundleContext;

	}

}