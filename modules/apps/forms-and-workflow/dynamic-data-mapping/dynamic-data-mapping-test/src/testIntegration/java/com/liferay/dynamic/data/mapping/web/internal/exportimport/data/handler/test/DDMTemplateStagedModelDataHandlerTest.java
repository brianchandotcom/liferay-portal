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

package com.liferay.dynamic.data.mapping.web.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.lar.test.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Daniel Kocsis
 */
@RunWith(Arquillian.class)
public class DDMTemplateStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testPublishTemplateToLiveBeforeStructure() throws Exception {
		DDMTemplate template = DDMTemplateTestUtil.addTemplate(
			stagingGroup.getGroupId(), 0,
			PortalUtil.getClassNameId(_CLASS_NAME));

		DDMStructure structure = DDMStructureTestUtil.addStructure(
			stagingGroup.getGroupId(), _CLASS_NAME);

		exportImportTemplate(template);

		template.setClassPK(structure.getStructureId());

		DDMTemplateLocalServiceUtil.updateDDMTemplate(template);

		exportImportTemplateAndStructure(template, structure);

		DDMStructure importedStructure =
			DDMStructureLocalServiceUtil.fetchDDMStructureByUuidAndGroupId(
				structure.getUuid(), liveGroup.getGroupId());

		DDMTemplate importedTemplate = (DDMTemplate)getStagedModel(
			template.getUuid(), liveGroup);

		Assert.assertNotNull(importedTemplate);

		Assert.assertNotNull(importedStructure);
		Assert.assertEquals(
			importedStructure.getStructureId(), importedTemplate.getClassPK());
	}

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<>();

		DDMStructure structure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), _CLASS_NAME);

		addDependentStagedModel(
			dependentStagedModelsMap, DDMStructure.class, structure);

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			DDMStructure.class.getSimpleName());

		DDMStructure structure = (DDMStructure)dependentStagedModels.get(0);

		return DDMTemplateTestUtil.addTemplate(
			group.getGroupId(), structure.getStructureId(),
			PortalUtil.getClassNameId(_CLASS_NAME));
	}

	protected void exportImportTemplate(DDMTemplate template) throws Exception {
		exportTemplateAndStructure(template, null);
		importTemplateAndStructure(template, null);
	}

	protected void exportImportTemplateAndStructure(
			DDMTemplate template, DDMStructure structure)
		throws Exception {

		exportTemplateAndStructure(template, structure);
		importTemplateAndStructure(template, structure);
	}

	protected void exportTemplateAndStructure(
			DDMTemplate template, DDMStructure structure)
		throws Exception {

		initExport();

		if (Objects.nonNull(structure)) {
			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, structure);
		}

		if (Objects.nonNull(template)) {
			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, template);
		}
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return DDMTemplateLocalServiceUtil.getDDMTemplateByUuidAndGroupId(
				uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return DDMTemplate.class;
	}

	protected void importTemplateAndStructure(
			DDMTemplate template, DDMStructure structure)
		throws Exception {

		initImport();

		if (Objects.nonNull(structure)) {
			DDMStructure exportedStructure =
				(DDMStructure)readExportedStagedModel(structure);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, exportedStructure);
		}

		if (Objects.nonNull(template)) {
			DDMTemplate exportedTemplate = (DDMTemplate)readExportedStagedModel(
				template);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, exportedTemplate);
		}
	}

	@Override
	protected void validateImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			DDMStructure.class.getSimpleName());

		Assert.assertEquals(
			dependentStagedModels.toString(), 1, dependentStagedModels.size());

		DDMStructure structure = (DDMStructure)dependentStagedModels.get(0);

		DDMStructureLocalServiceUtil.getDDMStructureByUuidAndGroupId(
			structure.getUuid(), group.getGroupId());
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		DDMTemplate template = (DDMTemplate)stagedModel;
		DDMTemplate importedTemplate = (DDMTemplate)importedStagedModel;

		Assert.assertEquals(
			template.getTemplateKey(), importedTemplate.getTemplateKey());
		Assert.assertEquals(template.getName(), importedTemplate.getName());
		Assert.assertEquals(
			template.getDescription(), importedTemplate.getDescription());
		Assert.assertEquals(template.getMode(), importedTemplate.getMode());
		Assert.assertEquals(
			template.getLanguage(), importedTemplate.getLanguage());
		Assert.assertEquals(template.getScript(), importedTemplate.getScript());
		Assert.assertEquals(
			template.isCacheable(), importedTemplate.isCacheable());
		Assert.assertEquals(
			template.isSmallImage(), importedTemplate.isSmallImage());
	}

	private static final String _CLASS_NAME =
		"com.liferay.dynamic.data.lists.model.DDLRecordSet";

}