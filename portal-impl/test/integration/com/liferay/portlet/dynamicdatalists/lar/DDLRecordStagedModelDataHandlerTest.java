/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatalists.lar;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.util.DDLTestUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMStructureTestUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMTemplateTestUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Daniel Kocsis
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DDLRecordStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<String, List<StagedModel>>();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), DDLRecordSet.class.getName());

		addDependentStagedModel(
			dependentStagedModelsMap, DDMStructure.class, ddmStructure);

		DDMTemplate ddmTemplate1 = DDMTemplateTestUtil.addTemplate(
			group.getGroupId(), ddmStructure.getStructureId());

		addDependentStagedModel(
			dependentStagedModelsMap, DDMTemplate.class, ddmTemplate1);

		DDMTemplate ddmTemplate2 = DDMTemplateTestUtil.addTemplate(
			group.getGroupId(), ddmStructure.getStructureId());

		addDependentStagedModel(
			dependentStagedModelsMap, DDMTemplate.class, ddmTemplate2);

		DDLRecordSet recordSet = DDLTestUtil.addRecordSet(
			group.getGroupId(), ddmStructure.getStructureId());

		addDependentStagedModel(
			dependentStagedModelsMap, DDLRecordSet.class, recordSet);

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group, Map<String,
			List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			DDLRecordSet.class.getSimpleName());

		DDLRecordSet recordSet = (DDLRecordSet)dependentStagedModels.get(0);

		return DDLTestUtil.addRecord(
			group.getGroupId(), recordSet.getRecordSetId());
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return DDLRecordLocalServiceUtil.fetchDDLRecordByUuidAndGroupId(
				uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return DDLRecord.class;
	}

	@Override
	protected void validateImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> ddmStructureDependentStagedModels =
			dependentStagedModelsMap.get(DDMStructure.class.getSimpleName());

		Assert.assertEquals(1, ddmStructureDependentStagedModels.size());

		DDMStructure ddmStructure =
			(DDMStructure)ddmStructureDependentStagedModels.get(0);

		DDMStructureLocalServiceUtil.getDDMStructureByUuidAndGroupId(
			ddmStructure.getUuid(), group.getGroupId());

		List<StagedModel> ddmTemplateDependentStagedModels =
			dependentStagedModelsMap.get(DDMTemplate.class.getSimpleName());

		Assert.assertEquals(2, ddmTemplateDependentStagedModels.size());

		for (StagedModel ddmTemplateDependentStagedModel :
				ddmTemplateDependentStagedModels) {

			DDMTemplateLocalServiceUtil.getDDMTemplateByUuidAndGroupId(
				ddmTemplateDependentStagedModel.getUuid(), group.getGroupId());
		}

		List<StagedModel> recordSetDependentStagedModels =
			dependentStagedModelsMap.get(DDLRecordSet.class.getSimpleName());

		Assert.assertEquals(1, recordSetDependentStagedModels.size());

		DDLRecordSet recordSet =
			(DDLRecordSet)recordSetDependentStagedModels.get(0);

		DDLRecordSetLocalServiceUtil.getDDLRecordSetByUuidAndGroupId(
			recordSet.getUuid(), group.getGroupId());
	}

}