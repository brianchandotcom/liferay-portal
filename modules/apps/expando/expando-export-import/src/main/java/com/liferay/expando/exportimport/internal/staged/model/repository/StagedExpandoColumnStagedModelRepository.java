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

package com.liferay.expando.exportimport.internal.staged.model.repository;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.adapter.StagedExpandoColumn;
import com.liferay.expando.kernel.model.adapter.StagedExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.exportimport.kernel.lar.ExportImportHelper;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.adapter.ModelAdapterUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	property = "model.class.name=com.liferay.expando.kernel.model.adapter.StagedExpandoColumn",
	service = StagedModelRepository.class
)
public class StagedExpandoColumnStagedModelRepository
	implements StagedModelRepository<StagedExpandoColumn> {

	@Override
	public StagedExpandoColumn addStagedModel(
			PortletDataContext portletDataContext,
			StagedExpandoColumn stagedExpandoColumn)
		throws PortalException {

		long columnId = stagedExpandoColumn.getColumnId();

		stagedExpandoColumn.setColumnId(0);

		ExpandoColumn expandoColumn = _expandoColumnLocalService.addColumn(
			stagedExpandoColumn.getTableId(), stagedExpandoColumn.getName(),
			stagedExpandoColumn.getType(),
			stagedExpandoColumn.getDefaultValue());

		stagedExpandoColumn.setColumnId(columnId);

		expandoColumn = _expandoColumnLocalService.updateTypeSettings(
			expandoColumn.getColumnId(), stagedExpandoColumn.getTypeSettings());

		return ModelAdapterUtil.adapt(
			expandoColumn, ExpandoColumn.class, StagedExpandoColumn.class);
	}

	@Override
	public void deleteStagedModel(StagedExpandoColumn stagedExpandoColumn)
		throws PortalException {

		_expandoColumnLocalService.deleteColumn(stagedExpandoColumn);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		JSONObject extraDataJSONObject = _jsonFactory.createJSONObject(
			extraData);

		List<StagedExpandoColumn> stagedExpandoColumns =
			fetchStagedModelsByUuidAndCompanyId(
				extraDataJSONObject.getString("uuid"),
				extraDataJSONObject.getInt("companyId"));

		if (ListUtil.isEmpty(stagedExpandoColumns)) {
			return;
		}

		for (StagedExpandoColumn stagedExpandoColumn : stagedExpandoColumns) {
			deleteStagedModel(stagedExpandoColumn);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		List<ExpandoColumn> expandoColumns =
			_expandoColumnLocalService.getExpandoColumns(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (ExpandoColumn expandoColumn : expandoColumns) {
			_expandoColumnLocalService.deleteColumn(expandoColumn);
		}
	}

	@Override
	public StagedExpandoColumn fetchMissingReference(
		String uuid, long groupId) {

		return null;
	}

	@Override
	public StagedExpandoColumn fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return null;
	}

	@Override
	public List<StagedExpandoColumn> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		DynamicQuery dynamicQuery = _expandoColumnLocalService.dynamicQuery();

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(companyId));

		List<StagedExpandoTable> stagedExpandoTables =
			_stagedExpandoTableStagedModelRepository.
				fetchStagedModelsByUuidAndCompanyId(
					_parseExpandoTableUuid(uuid), companyId);

		if (ListUtil.isEmpty(stagedExpandoTables)) {
			return null;
		}

		StagedExpandoTable stagedExpandoTable = stagedExpandoTables.get(0);

		Property tableIdProperty = PropertyFactoryUtil.forName("tableId");

		dynamicQuery.add(tableIdProperty.eq(stagedExpandoTable.getTableId()));

		Property nameProperty = PropertyFactoryUtil.forName("name");

		String name = _parseExpandoColumnName(uuid);

		dynamicQuery.add(nameProperty.eq(name));

		List<ExpandoColumn> expandoColumns =
			_expandoColumnLocalService.dynamicQuery(dynamicQuery);

		if (ListUtil.isNotEmpty(expandoColumns)) {
			return ModelAdapterUtil.adapt(
				expandoColumns, ExpandoColumn.class, StagedExpandoColumn.class);
		}

		return Collections.emptyList();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			new ExportActionableDynamicQuery() {

				@Override
				public long performCount() throws PortalException {
					ManifestSummary manifestSummary =
						portletDataContext.getManifestSummary();

					StagedModelType stagedModelType = getStagedModelType();

					long modelAdditionCount = super.performCount();

					manifestSummary.addModelAdditionCount(
						stagedModelType, modelAdditionCount);

					long modelDeletionCount =
						_exportImportHelper.getModelDeletionCount(
							portletDataContext, stagedModelType);

					manifestSummary.addModelDeletionCount(
						stagedModelType, modelDeletionCount);

					return modelAdditionCount;
				}

			};

		exportActionableDynamicQuery.setBaseLocalService(
			_expandoColumnLocalService);

		Class<? extends ExpandoColumnLocalService>
			expandoColumnLocalServiceClass =
				_expandoColumnLocalService.getClass();

		exportActionableDynamicQuery.setClassLoader(
			expandoColumnLocalServiceClass.getClassLoader());

		exportActionableDynamicQuery.setCompanyId(
			portletDataContext.getCompanyId());
		exportActionableDynamicQuery.setModelClass(ExpandoColumn.class);
		exportActionableDynamicQuery.setPerformActionMethod(
			(ExpandoColumn expandoColumn) -> {
				ExpandoTable expandoTable =
					_expandoTableLocalService.fetchExpandoTable(
						expandoColumn.getTableId());

				if (expandoTable == null) {
					return;
				}

				ClassName className = _classNameLocalService.fetchClassName(
					expandoTable.getClassNameId());

				if (className == null) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"No class name exists for class name ID " +
								expandoTable.getClassNameId());
					}

					return;
				}

				StagedExpandoColumn stagedExpandoColumn =
					ModelAdapterUtil.adapt(
						expandoColumn, ExpandoColumn.class,
						StagedExpandoColumn.class);

				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, stagedExpandoColumn);
			});
		exportActionableDynamicQuery.setPrimaryKeyPropertyName("tableId");
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(StagedExpandoColumn.class));

		return exportActionableDynamicQuery;
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext,
			StagedExpandoColumn stagedModel)
		throws PortletDataException {
	}

	@Override
	public StagedExpandoColumn saveStagedModel(
			StagedExpandoColumn stagedExpandoColumn)
		throws PortalException {

		ExpandoColumn expandoColumn =
			_expandoColumnLocalService.updateExpandoColumn(stagedExpandoColumn);

		return ModelAdapterUtil.adapt(
			expandoColumn, ExpandoColumn.class, StagedExpandoColumn.class);
	}

	@Override
	public StagedExpandoColumn updateStagedModel(
			PortletDataContext portletDataContext,
			StagedExpandoColumn stagedExpandoColumn)
		throws PortalException {

		_expandoColumnLocalService.updateColumn(
			stagedExpandoColumn.getColumnId(), stagedExpandoColumn.getName(),
			stagedExpandoColumn.getType(),
			stagedExpandoColumn.getDefaultValue());

		ExpandoColumn expandoColumn =
			_expandoColumnLocalService.updateTypeSettings(
				stagedExpandoColumn.getColumnId(),
				stagedExpandoColumn.getTypeSettings());

		return ModelAdapterUtil.adapt(
			expandoColumn, ExpandoColumn.class, StagedExpandoColumn.class);
	}

	private String _parseExpandoColumnName(String uuid) {
		return uuid.substring(uuid.lastIndexOf(StringPool.POUND) + 1);
	}

	private String _parseExpandoTableUuid(String uuid) {
		return uuid.substring(0, uuid.lastIndexOf(StringPool.POUND));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagedExpandoColumnStagedModelRepository.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

	@Reference
	private ExportImportHelper _exportImportHelper;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference(
		target = "(model.class.name=com.liferay.expando.kernel.model.adapter.StagedExpandoTable)"
	)
	private StagedModelRepository<StagedExpandoTable>
		_stagedExpandoTableStagedModelRepository;

}