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
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.portal.aop.AopService;

import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

	public int getComparison(long runIdA, long runIdB, String statusA, String statusB, long companyId) {


		ObjectDefinition caseObjectDefinition = _getObjectDefinitionByTableName(_CASE, companyId).get(0);
		DynamicObjectDefinitionTable caseExtensionDynamicTable = _getDynamicObjectDefinitionTable(caseObjectDefinition, true);

		ObjectDefinition caseResultObjectDefinition = _getObjectDefinitionByTableName(_CASE_RESULT, companyId).get(0);
		DynamicObjectDefinitionTable caseResultExtensionDynamicTable = _getDynamicObjectDefinitionTable(caseResultObjectDefinition, true);

		DynamicObjectDefinitionTable caseResultDynamicTable = _getDynamicObjectDefinitionTable(caseResultObjectDefinition, false);

		Column<DynamicObjectDefinitionTable, Long> runToCaseResultColumn =
			(Column<DynamicObjectDefinitionTable, Long>) caseResultExtensionDynamicTable.getColumn("r_runToCaseResult_c_runId");

		Column<DynamicObjectDefinitionTable, String> dueStatucColumn =
			(Column<DynamicObjectDefinitionTable, String>) caseResultDynamicTable.getColumn("dueStatus_");

		Column<DynamicObjectDefinitionTable, Long> caseToCaseResultIdColumn =
			(Column<DynamicObjectDefinitionTable, Long>) caseResultExtensionDynamicTable.getColumn("r_caseToCaseResult_c_caseId");

		Predicate predicateA = runToCaseResultColumn.eq(Long.valueOf(runIdA)).and(dueStatucColumn.eq(statusA));

		Predicate predicateB = runToCaseResultColumn.eq(runIdB).and(dueStatucColumn.eq(statusB));

		JoinStep genericQuery = DSLQueryFactoryUtil.select(
				caseExtensionDynamicTable.getColumn("c_caseId_")
			).from(caseResultExtensionDynamicTable).
			innerJoinON(caseExtensionDynamicTable,
				caseExtensionDynamicTable.getPrimaryKeyColumn().
					eq(caseToCaseResultIdColumn)
			).innerJoinON(caseResultDynamicTable,
				caseResultExtensionDynamicTable.getPrimaryKeyColumn().
					eq(caseResultDynamicTable.getPrimaryKeyColumn())
			);

		Collection<Column<?, ?>> tableTemplate = new ArrayList<Column<?, ?>>() {};

		Column<DynamicObjectDefinitionTable,Long> caseId = (Column<DynamicObjectDefinitionTable,Long>) caseExtensionDynamicTable.getColumn("c_caseId_");

		tableTemplate.add(caseId);

		Table table1 = genericQuery.where(predicateA).as("table1", tableTemplate);

		DSLQuery table2 = genericQuery.where(predicateB);

		return _objectEntryLocalService.dslQueryCount(
			DSLQueryFactoryUtil.count().from(
				table1
			).where(
				table1.getColumn("c_caseId_").in(table2)
			));

	}

	private List<ObjectDefinition> _getObjectDefinitionByTableName(String tableName, Long companyId) {

		DynamicQuery dynamicQuery = _objectDefinitionLocalService.dynamicQuery();

		Criterion criterion = RestrictionsFactoryUtil.like("dbTableName", "%" + String.valueOf(companyId) + tableName);

		RestrictionsFactoryUtil.and(criterion, RestrictionsFactoryUtil.eq("companyId", companyId));

		dynamicQuery.add(criterion);

		return _objectDefinitionLocalService.dynamicQuery(dynamicQuery);

	}

	private DynamicObjectDefinitionTable _getDynamicObjectDefinitionTable(
		ObjectDefinition objectDefinition, boolean extension) {

		if(extension == true) {
			return new DynamicObjectDefinitionTable(
				objectDefinition,
				_objectFieldLocalService.getObjectFields(
					objectDefinition.getObjectDefinitionId(),
					objectDefinition.getExtensionDBTableName()),
				objectDefinition.getExtensionDBTableName()
			);
		} else {
			return new DynamicObjectDefinitionTable(
				objectDefinition,
				_objectFieldLocalService.getObjectFields(
					objectDefinition.getObjectDefinitionId(),
					objectDefinition.getDBTableName()),
				objectDefinition.getDBTableName()
			);
		}
	}

	private static String _CASE = "_Case";
	private static String _CASE_RESULT = "_CaseResult";

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;
	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;
	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}