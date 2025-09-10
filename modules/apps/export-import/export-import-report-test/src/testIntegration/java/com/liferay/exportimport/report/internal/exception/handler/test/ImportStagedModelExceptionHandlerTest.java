/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.internal.exception.handler.test;

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
import com.liferay.exportimport.report.service.ExportImportReportEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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
import org.osgi.framework.ServiceRegistration;

/**
 * @author Alvaro Saugar
 */
@RunWith(Arquillian.class)
public class ImportStagedModelExceptionHandlerTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testErrorEntryIsAddedWhenExceptionIsThrown() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			ImportStagedModelExceptionHandlerTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceRegistration<?> serviceRegistration =
			bundleContext.registerService(
				StagedModelDataHandler.class, new TestStagedModelDataHandler(),
				MapUtil.singletonDictionary(
					"companyId", _group.getCompanyId()));

		TestStagedModel stagedModelDataHandler = new TestStagedModel();

		PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				_group.getCompanyId(), _group.getGroupId(), null, null, null,
				null);

		portletDataContext.setExportImportProcessId(
			RandomTestUtil.randomString());

		long exportImportReportEntriesCount1 =
			_exportImportReportEntryLocalService.
				getExportImportReportEntriesCount();

		try {
			ExportImportThreadLocal.setPortletImportInProcess(true);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, stagedModelDataHandler);
		}
		finally {
			ExportImportThreadLocal.setPortletImportInProcess(false);
			serviceRegistration.unregister();
		}

		Assert.assertEquals(
			exportImportReportEntriesCount1 + 1,
			_exportImportReportEntryLocalService.
				getExportImportReportEntriesCount());
	}

	@Inject
	private ExportImportReportEntryLocalService
		_exportImportReportEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private class TestStagedModel implements StagedModel {

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
			return TestStagedModel.class;
		}

		@Override
		public String getModelClassName() {
			return TestStagedModel.class.getName();
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
				PortalUtil.getClassNameId(TestStagedModel.class.getName()),
				PortalUtil.getClassNameId(TestStagedModel.class.getName()));
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

	private class TestStagedModelDataHandler
		extends BaseStagedModelDataHandler<TestStagedModel> {

		public static final String[] CLASS_NAMES = {
			TestStagedModel.class.getName()
		};

		@Override
		public void deleteStagedModel(
				String uuid, long groupId, String className, String extraData)
			throws PortalException {
		}

		@Override
		public void deleteStagedModel(TestStagedModel stagedModel)
			throws PortalException {
		}

		@Override
		public List<TestStagedModel> fetchStagedModelsByUuidAndCompanyId(
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
				TestStagedModel stagedModel)
			throws PortletDataException {

			throw new PortletDataException();
		}

		@Override
		protected void doExportStagedModel(
				PortletDataContext portletDataContext,
				TestStagedModel stagedModel)
			throws Exception {
		}

		@Override
		protected void doImportStagedModel(
				PortletDataContext portletDataContext,
				TestStagedModel stagedModel)
			throws Exception {
		}

	}

}