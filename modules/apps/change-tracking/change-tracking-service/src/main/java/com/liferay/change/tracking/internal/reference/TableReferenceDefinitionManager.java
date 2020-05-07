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

import com.liferay.change.tracking.internal.reference.builder.TableReferenceInfoBuilderImpl;
import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = TableReferenceDefinitionManager.class)
public class TableReferenceDefinitionManager {

	public long getClassNameId(Table<?> table) {
		TableReferenceInfo<?> tableReferenceInfo = _tableReferenceInfos.get(
			table);

		if (tableReferenceInfo == null) {
			throw new IllegalStateException(
				"No table reference definition for " + table);
		}

		return _getClassNameId(tableReferenceInfo);
	}

	public Map<Long, TableReferenceInfo<?>> getCombinedTableReferenceInfos() {
		Map<Long, TableReferenceInfo<?>> combinedTableReferenceInfos =
			_combinedTableReferenceInfos;

		if (combinedTableReferenceInfos != null) {
			return combinedTableReferenceInfos;
		}

		synchronized (this) {
			combinedTableReferenceInfos = new HashMap<>();

			for (TableReferenceInfo<?> tableReferenceInfo :
					_tableReferenceInfos.values()) {

				combinedTableReferenceInfos.put(
					_getClassNameId(tableReferenceInfo),
					_getCombinedTableReferenceInfo(tableReferenceInfo));
			}

			combinedTableReferenceInfos = Collections.unmodifiableMap(
				combinedTableReferenceInfos);

			_combinedTableReferenceInfos = combinedTableReferenceInfos;
		}

		return combinedTableReferenceInfos;
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

	private Map<Table<?>, List<TableJoinHolder>> _copyTableJoinHoldersMap(
		Map<Table<?>, List<TableJoinHolder>> tableJoinHoldersMap) {

		Map<Table<?>, List<TableJoinHolder>> copy = new HashMap<>();

		for (Map.Entry<Table<?>, List<TableJoinHolder>> entry :
				tableJoinHoldersMap.entrySet()) {

			copy.put(entry.getKey(), new ArrayList<>(entry.getValue()));
		}

		return copy;
	}

	private long _getClassNameId(TableReferenceInfo<?> tableReferenceInfo) {
		TableReferenceDefinition<?> tableReferenceDefinition =
			tableReferenceInfo.getTableReferenceDefinition();

		BasePersistence<?> basePersistence =
			tableReferenceDefinition.getBasePersistence();

		return _classNameLocalService.getClassNameId(
			basePersistence.getModelClass());
	}

	private <T extends Table<T>> TableReferenceInfo<T>
		_getCombinedTableReferenceInfo(
			TableReferenceInfo<T> tableReferenceInfo) {

		Map<Table<?>, List<TableJoinHolder>> combinedParentTableJoinHoldersMap =
			_copyTableJoinHoldersMap(
				tableReferenceInfo.getParentTableJoinHoldersMap());

		Map<Table<?>, List<TableJoinHolder>> combinedChildTableJoinHoldersMap =
			_copyTableJoinHoldersMap(
				tableReferenceInfo.getChildTableJoinHoldersMap());

		TableReferenceDefinition<T> tableReferenceDefinition =
			tableReferenceInfo.getTableReferenceDefinition();

		T table = tableReferenceDefinition.getTable();

		for (TableReferenceInfo<?> currentTableReferenceInfo :
				_tableReferenceInfos.values()) {

			if (tableReferenceInfo == currentTableReferenceInfo) {
				continue;
			}

			TableReferenceDefinition<?> currentTableReferenceDefinition =
				currentTableReferenceInfo.getTableReferenceDefinition();

			Map<Table<?>, List<TableJoinHolder>>
				currentParentTableJoinHoldersMap =
					currentTableReferenceInfo.getParentTableJoinHoldersMap();

			List<TableJoinHolder> currentParentTableJoinHolders =
				currentParentTableJoinHoldersMap.get(table);

			if (currentParentTableJoinHolders != null) {
				List<TableJoinHolder> combinedChildTableJoinHolders =
					combinedChildTableJoinHoldersMap.computeIfAbsent(
						currentTableReferenceDefinition.getTable(),
						key -> new ArrayList<>());

				for (TableJoinHolder currentParentTableJoinHolder :
						currentParentTableJoinHolders) {

					combinedChildTableJoinHolders.add(
						TableJoinHolder.reverse(currentParentTableJoinHolder));
				}
			}

			Map<Table<?>, List<TableJoinHolder>>
				currentChildTableJoinHoldersMap =
					currentTableReferenceInfo.getChildTableJoinHoldersMap();

			List<TableJoinHolder> currentChildTableJoinHolders =
				currentChildTableJoinHoldersMap.get(table);

			if (currentChildTableJoinHolders != null) {
				List<TableJoinHolder> combinedParentTableJoinHolders =
					combinedParentTableJoinHoldersMap.computeIfAbsent(
						currentTableReferenceDefinition.getTable(),
						key -> new ArrayList<>());

				for (TableJoinHolder currentChildTableJoinHolder :
						currentChildTableJoinHolders) {

					combinedParentTableJoinHolders.add(
						TableJoinHolder.reverse(currentChildTableJoinHolder));
				}
			}
		}

		return new TableReferenceInfo<>(
			tableReferenceDefinition, combinedParentTableJoinHoldersMap,
			combinedChildTableJoinHoldersMap);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TableReferenceDefinitionManager.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	private volatile Map<Long, TableReferenceInfo<?>>
		_combinedTableReferenceInfos;
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

			synchronized (TableReferenceDefinitionManager.this) {
				TableReferenceDefinition<?> tableReferenceDefinition =
					tableReferenceInfo.getTableReferenceDefinition();

				_tableReferenceInfos.remove(
					tableReferenceDefinition.getTable());

				_combinedTableReferenceInfos = null;
			}

			_bundleContext.ungetService(serviceReference);
		}

		private TableReferenceDefinitionServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		private <T extends Table<T>> TableReferenceInfo<?>
			_createTableReferenceContext(
				TableReferenceDefinition<T> tableReferenceDefinition) {

			Column<T, Long> primaryKeyColumn = TableUtil.getPrimaryKeyColumn(
				tableReferenceDefinition.getTable());

			if (primaryKeyColumn == null) {
				_log.error(
					"No long type primary key column found for " +
						tableReferenceDefinition);

				return null;
			}

			TableReferenceInfoBuilderImpl<T> tableReferenceInfoBuilderImpl =
				new TableReferenceInfoBuilderImpl<>(
					tableReferenceDefinition, primaryKeyColumn);

			tableReferenceDefinition.defineTableReferences(
				tableReferenceInfoBuilderImpl);

			TableReferenceInfo<T> tableReferenceInfo =
				tableReferenceInfoBuilderImpl.build();

			if (tableReferenceInfo != null) {
				synchronized (TableReferenceDefinitionManager.this) {
					_tableReferenceInfos.put(
						tableReferenceDefinition.getTable(),
						tableReferenceInfo);

					_combinedTableReferenceInfos = null;
				}
			}

			return tableReferenceInfo;
		}

		private final BundleContext _bundleContext;

	}

}