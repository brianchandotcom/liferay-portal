/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.exportimport.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.report.service.ExportImportReportEntryLocalServiceUtil;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Alvaro Saugar
 */
@RunWith(Arquillian.class)
public class ImportStagedModelErrorHandlerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());
	}

	@Test
	public void testErrorEntryIsAddedWhenExceptionIsThrown() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			ImportStagedModelErrorHandlerTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		bundleContext.registerService(
			StagedModelDataHandler.class,
			new ThrowExceptionStagedModelDataHandler(),
			MapUtil.singletonDictionary("companyId", _company.getCompanyId()));

		MockStagedModel stagedModelDataHandler = new MockStagedModel();

		PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				_company.getCompanyId(), _group.getGroupId(), null, null, null,
				null);

		portletDataContext.setExportImportProcessId(
			BaseStagedModelDataHandlerTestCase.class.getName());

		long exportImportReportEntriesCount1 =
			ExportImportReportEntryLocalServiceUtil.
				getExportImportReportEntriesCount();

		try {
			ExportImportThreadLocal.setPortletImportInProcess(true);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, stagedModelDataHandler);
		}
		finally {
			ExportImportThreadLocal.setPortletImportInProcess(false);
		}

		long exportImportReportEntriesCount2 =
			ExportImportReportEntryLocalServiceUtil.
				getExportImportReportEntriesCount();

		Assert.assertEquals(
			exportImportReportEntriesCount1 + 1,
			exportImportReportEntriesCount2);
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private class MockStagedModel implements StagedModel {

		@Override
		public Object clone() {
			return null;
		}

		@Override
		public long getCompanyId() {
			return 0;
		}

		@Override
		public Date getCreateDate() {
			return null;
		}

		@Override
		public ExpandoBridge getExpandoBridge() {
			return null;
		}

		@Override
		public Class<?> getModelClass() {
			return MockStagedModel.class;
		}

		@Override
		public String getModelClassName() {
			return MockStagedModel.class.getName();
		}

		@Override
		public Date getModifiedDate() {
			return null;
		}

		@Override
		public Serializable getPrimaryKeyObj() {
			return null;
		}

		@Override
		public StagedModelType getStagedModelType() {
			return new StagedModelType(
				PortalUtil.getClassNameId(MockStagedModel.class.getName()),
				PortalUtil.getClassNameId(MockStagedModel.class.getName()));
		}

		@Override
		public String getUuid() {
			return null;
		}

		@Override
		public void setCompanyId(long companyId) {
		}

		@Override
		public void setCreateDate(Date date) {
		}

		@Override
		public void setModifiedDate(Date date) {
		}

		@Override
		public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		}

		@Override
		public void setUuid(String uuid) {
		}

	}

	private class ThrowExceptionStagedModelDataHandler
		extends BaseStagedModelDataHandler<MockStagedModel> {

		public static final String[] CLASS_NAMES = {
			MockStagedModel.class.getName()
		};

		@Override
		public void deleteStagedModel(MockStagedModel stagedModel)
			throws PortalException {
		}

		@Override
		public void deleteStagedModel(
				String uuid, long groupId, String className, String extraData)
			throws PortalException {
		}

		@Override
		public List<MockStagedModel> fetchStagedModelsByUuidAndCompanyId(
			String uuid, long companyId) {

			return null;
		}

		@Override
		public String[] getClassNames() {
			return CLASS_NAMES;
		}

		@Override
		public void importStagedModel(
				PortletDataContext portletDataContext,
				MockStagedModel stagedModel)
			throws PortletDataException {

			throw new PortletDataException();
		}

		@Override
		protected void doExportStagedModel(
				PortletDataContext portletDataContext,
				MockStagedModel stagedModel)
			throws Exception {
		}

		@Override
		protected void doImportStagedModel(
				PortletDataContext portletDataContext,
				MockStagedModel stagedModel)
			throws Exception {
		}

	}

}