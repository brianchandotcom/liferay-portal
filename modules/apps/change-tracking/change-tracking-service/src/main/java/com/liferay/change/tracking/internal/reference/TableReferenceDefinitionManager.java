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

import com.liferay.change.tracking.internal.reference.closure.CTClosureImpl;
import com.liferay.change.tracking.internal.reference.closure.Node;
import com.liferay.change.tracking.internal.reference.helper.TableReferenceDefinitionHelperImpl;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.closure.CTClosure;
import com.liferay.change.tracking.reference.closure.CTClosureFactory;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
@Component(immediate = true, service = CTClosureFactory.class)
public class TableReferenceDefinitionManager implements CTClosureFactory {

	@Override
	public CTClosure create(long ctCollectionId) {
		Map<Long, List<Long>> map = new HashMap<>();

		for (CTEntry ctEntry :
				_ctEntryLocalService.getCTCollectionCTEntries(ctCollectionId)) {

			List<Long> primaryKeys = map.computeIfAbsent(
				ctEntry.getModelClassNameId(), key -> new ArrayList<>());

			primaryKeys.add(ctEntry.getModelClassPK());
		}

		SchemaContext schemaContext = _getSchemaContext();

		Map<Node, Collection<Node>> closureMap = schemaContext.buildClosureMap(
			ctCollectionId, map);

		return new CTClosureImpl(ctCollectionId, closureMap);
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

	private <T extends Table<T>> TableReferenceInfo<T>
		_getCombinedTableReferenceInfo(
			TableReferenceInfo<T> tableReferenceInfo) {

		Map<Table<?>, List<TableJoinHolder>> combinedParentJoinHoldersMap =
			_copyTableJoinHoldersMap(
				tableReferenceInfo.getParentJoinHoldersMap());

		Map<Table<?>, List<TableJoinHolder>> combinedChildJoinHoldersMap =
			_copyTableJoinHoldersMap(
				tableReferenceInfo.getChildJoinHoldersMap());

		TableReferenceDefinition<T> tableReferenceDefinition =
			tableReferenceInfo.getTableReferenceDefinition();

		T table = tableReferenceDefinition.getTable();

		for (TableReferenceInfo<?> currentTableReferenceInfo :
				_tableReferenceInfos) {

			if (tableReferenceInfo == currentTableReferenceInfo) {
				continue;
			}

			TableReferenceDefinition<?> currentTableReferenceDefinition =
				currentTableReferenceInfo.getTableReferenceDefinition();

			Map<Table<?>, List<TableJoinHolder>> currentParentJoinHoldersMap =
				currentTableReferenceInfo.getParentJoinHoldersMap();

			List<TableJoinHolder> currentParentJoinHolders =
				currentParentJoinHoldersMap.get(table);

			if (currentParentJoinHolders != null) {
				List<TableJoinHolder> combinedChildJoinHolders =
					combinedChildJoinHoldersMap.computeIfAbsent(
						currentTableReferenceDefinition.getTable(),
						key -> new ArrayList<>());

				for (TableJoinHolder currentParentJoinHolder :
						currentParentJoinHolders) {

					combinedChildJoinHolders.add(
						currentParentJoinHolder.reverse());
				}
			}

			Map<Table<?>, List<TableJoinHolder>> currentChildJoinHoldersMap =
				currentTableReferenceInfo.getChildJoinHoldersMap();

			List<TableJoinHolder> currentChildJoinHolders =
				currentChildJoinHoldersMap.get(table);

			if (currentChildJoinHolders != null) {
				List<TableJoinHolder> combinedParentJoinHolders =
					combinedParentJoinHoldersMap.computeIfAbsent(
						currentTableReferenceDefinition.getTable(),
						key -> new ArrayList<>());

				for (TableJoinHolder currentChildJoinHolder :
						currentChildJoinHolders) {

					combinedParentJoinHolders.add(
						currentChildJoinHolder.reverse());
				}
			}
		}

		return new TableReferenceInfo<>(
			tableReferenceDefinition, tableReferenceInfo.getPrimaryKeyColumn(),
			combinedParentJoinHoldersMap, combinedChildJoinHoldersMap);
	}

	private SchemaContext _getSchemaContext() {
		SchemaContext schemaContext = _schemaContext;

		if (schemaContext != null) {
			return schemaContext;
		}

		synchronized (_tableReferenceInfos) {
			Map<Table<?>, Long> tableClassNameIds = new HashMap<>();
			Map<Long, TableReferenceInfo<?>> combinedTableReferenceInfos =
				new HashMap<>();

			for (TableReferenceInfo<?> tableReferenceInfo :
					_tableReferenceInfos) {

				TableReferenceDefinition<?> tableReferenceDefinition =
					tableReferenceInfo.getTableReferenceDefinition();

				PersistedModelLocalService persistedModelLocalService =
					tableReferenceDefinition.getPersistedModelLocalService();

				BasePersistence<?> basePersistence =
					persistedModelLocalService.getBasePersistence();

				long classNameId = _classNameLocalService.getClassNameId(
					basePersistence.getModelClass());

				tableClassNameIds.put(
					tableReferenceDefinition.getTable(), classNameId);

				combinedTableReferenceInfos.put(
					classNameId,
					_getCombinedTableReferenceInfo(tableReferenceInfo));
			}

			schemaContext = new SchemaContext(
				tableClassNameIds, combinedTableReferenceInfos);

			_schemaContext = schemaContext;
		}

		return schemaContext;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TableReferenceDefinitionManager.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	private volatile SchemaContext _schemaContext;
	private ServiceTracker<?, ?> _serviceTracker;
	private final Set<TableReferenceInfo<?>> _tableReferenceInfos =
		new HashSet<>();

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

			synchronized (_tableReferenceInfos) {
				_tableReferenceInfos.remove(tableReferenceInfo);

				_schemaContext = null;
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

			TableReferenceDefinitionHelperImpl<T>
				tableReferenceDefinitionHelperImpl =
					new TableReferenceDefinitionHelperImpl<>(
						tableReferenceDefinition, primaryKeyColumn);

			tableReferenceDefinition.defineTableReferences(
				tableReferenceDefinitionHelperImpl);

			TableReferenceInfo<T> tableReferenceInfo =
				tableReferenceDefinitionHelperImpl.getTableReferenceInfo();

			if (tableReferenceInfo != null) {
				synchronized (_tableReferenceInfos) {
					_tableReferenceInfos.add(tableReferenceInfo);

					_schemaContext = null;
				}
			}

			return tableReferenceInfo;
		}

		private final BundleContext _bundleContext;

	}

}