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

package com.liferay.osb.testray.service.impl;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.petra.sql.dsl.DynamicObjectDefinitionTable;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.osb.testray.service.base.CompareRunsLocalServiceBaseImpl;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author José Abelenda
 * @author Felipe Veloso
 */
@Component(
	property = "model.class.name=com.liferay.osb.testray.model.CompareRuns",
	service = AopService.class
)
public class CompareRunsLocalServiceImpl
	extends CompareRunsLocalServiceBaseImpl {

	public int getComparison(
		long companyId, long testrayRunId1, long testrayRunId2,
		String testrayDueStatus1, String testrayDueStatus2) {

		DynamicObjectDefinitionTable testrayCaseExtensionDynamicTable =
			_getDynamicObjectDefinitionTable(
				true, _getObjectDefinitionByTableName(companyId, "_Case"));

		DynamicObjectDefinitionTable testrayCaseResultExtensionDynamicTable =
			_getDynamicObjectDefinitionTable(
				true,
				_getObjectDefinitionByTableName(companyId, "_CaseResult"));

		DynamicObjectDefinitionTable testrayCaseResultDynamicTable =
			_getDynamicObjectDefinitionTable(
				false,
				_getObjectDefinitionByTableName(companyId, "_CaseResult"));

		Column<DynamicObjectDefinitionTable, Long>
			testrayRunToCaseResultColumn =
				(Column<DynamicObjectDefinitionTable, Long>)
					testrayCaseResultExtensionDynamicTable.getColumn(
						"r_runToCaseResult_c_runId");

		Column<DynamicObjectDefinitionTable, String> testrayDueStatusColumn =
			(Column<DynamicObjectDefinitionTable, String>)
				testrayCaseResultDynamicTable.getColumn("dueStatus_");

		Collection<Column<?, ?>> tableTemplate = new ArrayList<>();

		Column<DynamicObjectDefinitionTable, Long> testrayCaseIdDynamicColumn =
			(Column<DynamicObjectDefinitionTable, Long>)
				testrayCaseExtensionDynamicTable.getColumn("c_caseId_");

		tableTemplate.add(testrayCaseIdDynamicColumn);

		Table table = _getJoinStep(
			testrayCaseExtensionDynamicTable, testrayCaseResultDynamicTable,
			testrayCaseResultExtensionDynamicTable
		).where(
			testrayRunToCaseResultColumn.eq(
				testrayRunId1
			).and(
				testrayDueStatusColumn.eq(testrayDueStatus1)
			)
		).as(
			"table", tableTemplate
		);

		Column<?, ?> testrayCaseIdColumn = table.getColumn("c_caseId_");

		return _objectEntryLocalService.dslQueryCount(
			DSLQueryFactoryUtil.count(
			).from(
				table
			).where(
				testrayCaseIdColumn.in(
					_getJoinStep(
						testrayCaseExtensionDynamicTable,
						testrayCaseResultDynamicTable,
						testrayCaseResultExtensionDynamicTable
					).where(
						testrayRunToCaseResultColumn.eq(
							testrayRunId2
						).and(
							testrayDueStatusColumn.eq(testrayDueStatus2)
						)
					))
			));
	}

	private DynamicObjectDefinitionTable _getDynamicObjectDefinitionTable(
		boolean extension, ObjectDefinition objectDefinition) {

		if (extension) {
			return new DynamicObjectDefinitionTable(
				objectDefinition,
				_objectFieldLocalService.getObjectFields(
					objectDefinition.getObjectDefinitionId(),
					objectDefinition.getExtensionDBTableName()),
				objectDefinition.getExtensionDBTableName());
		}

		return new DynamicObjectDefinitionTable(
			objectDefinition,
			_objectFieldLocalService.getObjectFields(
				objectDefinition.getObjectDefinitionId(),
				objectDefinition.getDBTableName()),
			objectDefinition.getDBTableName());
	}

	private JoinStep _getJoinStep(
		DynamicObjectDefinitionTable testrayCaseExtensionDynamicTable,
		DynamicObjectDefinitionTable testrayCaseResultDynamicTable,
		DynamicObjectDefinitionTable testrayCaseResultExtensionDynamicTable) {

		Column<DynamicObjectDefinitionTable, Long>
			testrayCaseToCaseResultIdColumn =
				(Column<DynamicObjectDefinitionTable, Long>)
					testrayCaseResultExtensionDynamicTable.getColumn(
						"r_caseToCaseResult_c_caseId");

		return DSLQueryFactoryUtil.select(
			testrayCaseExtensionDynamicTable.getColumn("c_caseId_")
		).from(
			testrayCaseResultExtensionDynamicTable
		).innerJoinON(
			testrayCaseExtensionDynamicTable,
			testrayCaseExtensionDynamicTable.getPrimaryKeyColumn(
			).eq(
				testrayCaseToCaseResultIdColumn
			)
		).innerJoinON(
			testrayCaseResultDynamicTable,
			testrayCaseResultExtensionDynamicTable.getPrimaryKeyColumn(
			).eq(
				testrayCaseResultDynamicTable.getPrimaryKeyColumn()
			)
		);
	}

	private ObjectDefinition _getObjectDefinitionByTableName(
		long companyId, String tableName) {

		DynamicQuery dynamicQuery =
			_objectDefinitionLocalService.dynamicQuery();

		Criterion criterion = RestrictionsFactoryUtil.like(
			"dbTableName", "%" + String.valueOf(companyId) + tableName);

		RestrictionsFactoryUtil.and(
			criterion, RestrictionsFactoryUtil.eq("companyId", companyId));

		dynamicQuery.add(criterion);

		List<ObjectDefinition> objectDefinitionList =
			_objectDefinitionLocalService.dynamicQuery(dynamicQuery);

		return objectDefinitionList.get(0);
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}