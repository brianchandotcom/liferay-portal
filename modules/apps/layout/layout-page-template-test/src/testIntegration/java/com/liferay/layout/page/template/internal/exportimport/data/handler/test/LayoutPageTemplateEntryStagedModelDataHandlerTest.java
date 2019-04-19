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
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Kyle Miho
 */
@RunWith(Arquillian.class)
public class LayoutPageTemplateEntryStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testImportLayoutPageTemplateEntryByDefaultUser()
		throws Exception {

		long companyId = stagingGroup.getCompanyId();

		Company company = CompanyLocalServiceUtil.getCompany(companyId);

		Group companyGroup = company.getGroup();

		User defaultUser = company.getDefaultUser();

		_layoutPrototype = addLayoutPrototype(
			company.getCompanyId(), companyGroup.getGroupId(),
			"Test Layout Prototype", defaultUser.getUserId());

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				fetchFirstLayoutPageTemplateEntry(
					_layoutPrototype.getLayoutPrototypeId());

		_targetCompany = CompanyTestUtil.addCompany();

		User targetDefaultUser = _targetCompany.getDefaultUser();

		addLayoutPrototype(
			_targetCompany.getCompanyId(), _targetCompany.getGroupId(),
			"Test Layout Prototype", targetDefaultUser.getUserId());

		initExport(companyGroup);

		try {
			ExportImportThreadLocal.setPortletExportInProcess(true);

			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, layoutPageTemplateEntry);
		}
		finally {
			ExportImportThreadLocal.setPortletExportInProcess(false);
		}

		initImport(companyGroup, _targetCompany.getGroup());

		portletDataContext.setUserIdStrategy(
			new TestUserIdStrategy(targetDefaultUser));

		StagedModel exportedStagedModel = readExportedStagedModel(
			layoutPageTemplateEntry);

		Assert.assertNotNull(exportedStagedModel);

		try {
			ExportImportThreadLocal.setPortletImportInProcess(true);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, exportedStagedModel);
		}
		finally {
			ExportImportThreadLocal.setPortletImportInProcess(false);
		}
	}

	protected LayoutPrototype addLayoutPrototype(
			long companyId, long groupId, String name, long userId)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getDefault(), name);

		return LayoutPrototypeLocalServiceUtil.addLayoutPrototype(
			userId, companyId, nameMap, (Map<Locale, String>)null, true,
			ServiceContextTestUtil.getServiceContext(
				companyId, groupId, userId));
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		long userId = TestPropsValues.getUserId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					userId, group.getGroupId(), "Test Collection",
					StringPool.BLANK, serviceContext);

		return _layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			userId, group.getGroupId(),
			layoutPageTemplateCollection.getLayoutPageTemplateCollectionId(),
			"Test Entry", LayoutPageTemplateEntryTypeConstants.TYPE_BASIC,
			WorkflowConstants.STATUS_APPROVED, serviceContext);
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

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			(LayoutPageTemplateEntry)stagedModel;
		LayoutPageTemplateEntry importLayoutPageTemplateEntry =
			(LayoutPageTemplateEntry)importedStagedModel;

		Assert.assertEquals(
			layoutPageTemplateEntry.getName(),
			importLayoutPageTemplateEntry.getName());
		Assert.assertEquals(
			layoutPageTemplateEntry.getType(),
			importLayoutPageTemplateEntry.getType());
	}

	@Inject
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@DeleteAfterTestRun
	private LayoutPrototype _layoutPrototype;

	@DeleteAfterTestRun
	private Company _targetCompany;

}