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

package com.liferay.layout.page.template.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Kyle Miho
 */
@RunWith(Arquillian.class)
public class DisplayPageTemplateStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception, PortalException {
		super.setUp();

		_classNameId = PortalUtil.getClassNameId(
			"com.liferay.journal.model.JournalArticle");

		Group group = GroupLocalServiceUtil.getCompanyGroup(
			TestPropsValues.getCompanyId());

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			group.getGroupId(), _classNameId, "BASIC-WEB-CONTENT");

		_classTypeId = ddmStructure.getStructureId();
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		return _layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			TestPropsValues.getUserId(), group.getGroupId(), 0, _classNameId,
			_classTypeId, RandomTestUtil.randomString(),
			LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE, false, 0, 0,
			0, WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		return _layoutPageTemplateEntryLocalService.
			fetchLayoutPageTemplateEntryByUuidAndGroupId(
				uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return LayoutPageTemplateEntry.class;
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		Assert.assertTrue(
			stagedModel.getCreateDate() + " " +
				importedStagedModel.getCreateDate(),
			DateUtil.equals(
				stagedModel.getCreateDate(),
				importedStagedModel.getCreateDate()));
		Assert.assertEquals(
			stagedModel.getUuid(), importedStagedModel.getUuid());

		LayoutPageTemplateEntry displayPageTemplate =
			(LayoutPageTemplateEntry)stagedModel;
		LayoutPageTemplateEntry importDisplayPageTemplate =
			(LayoutPageTemplateEntry)importedStagedModel;

		Assert.assertEquals(
			displayPageTemplate.getClassNameId(),
			importDisplayPageTemplate.getClassNameId());
		Assert.assertEquals(
			displayPageTemplate.getClassTypeId(),
			importDisplayPageTemplate.getClassTypeId());
		Assert.assertEquals(
			displayPageTemplate.getName(), importDisplayPageTemplate.getName());
		Assert.assertEquals(
			displayPageTemplate.getType(), importDisplayPageTemplate.getType());
	}

	private long _classNameId;
	private long _classTypeId;

	@Inject
	private DDMStructureLocalService _ddmStructureLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

}