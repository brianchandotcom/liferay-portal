/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryRegistryUtil;
import com.liferay.exportimport.test.util.exportimport.data.handler.DummyFolderStagedModelDataHandler;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.exportimport.test.util.model.DummyFolder;
import com.liferay.exportimport.test.util.model.util.DummyFolderTestUtil;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Akos Thurzo
 */
@RunWith(Arquillian.class)
public class DummyFolderStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		_dummyFolderStagedModelDataHandler =
			(DummyFolderStagedModelDataHandler)
				StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
					DummyFolder.class.getName());
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_dummyFolderStagedModelRepository =
			(StagedModelRepository<DummyFolder>)
				StagedModelRepositoryRegistryUtil.getStagedModelRepository(
					DummyFolder.class.getName());

		Bundle bundle = FrameworkUtil.getBundle(
			DummyFolderStagedModelDataHandlerTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			PersistedModelLocalService.class,
			new PersistedModelLocalService() {

				@Override
				public PersistedModel deletePersistedModel(
						PersistedModel persistedModel)
					throws PortalException {

					return null;
				}

				@Override
				public <T> T dslQuery(DSLQuery dslQuery) {
					return null;
				}

				@Override
				public int dslQueryCount(DSLQuery dslQuery) {
					return 0;
				}

				@Override
				public BasePersistence<?> getBasePersistence() {
					return null;
				}

				@Override
				public PersistedModel getPersistedModel(
						Serializable primaryKeyObj)
					throws PortalException {

					return null;
				}

			},
			MapUtil.singletonDictionary(
				"model.class.name", DummyFolder.class.getName()));
	}

	@After
	@Override
	public void tearDown() throws Exception {
		_serviceRegistration.unregister();

		super.tearDown();
	}

	@Override
	@Test
	public void testCleanStagedModelDataHandler() throws Exception {
		try {
			_dummyFolderStagedModelDataHandler.setEnabled(true);

			super.testCleanStagedModelDataHandler();

			_dummyFolderStagedModelDataHandler.setEnabled(false);

			initExport();

			Map<String, List<StagedModel>> dependentStagedModelsMap =
				addDependentStagedModelsMap(stagingGroup);

			StagedModel stagedModel = addStagedModel(
				stagingGroup, dependentStagedModelsMap);

			addComments(stagedModel);

			addRatings(stagedModel);

			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, stagedModel);

			validateExport(
				portletDataContext, stagedModel, dependentStagedModelsMap);

			initImport();

			deleteStagedModel(
				stagedModel, dependentStagedModelsMap, stagingGroup);

			StagedModel exportedStagedModel = readExportedStagedModel(
				stagedModel);

			Assert.assertNull(exportedStagedModel);
		}
		finally {
			_dummyFolderStagedModelDataHandler.setEnabled(false);
		}
	}

	@Override
	@Test
	public void testExportImportWithDefaultData() throws Exception {
		try {
			_dummyFolderStagedModelDataHandler.setEnabled(true);

			super.testExportImportWithDefaultData();

			_dummyFolderStagedModelDataHandler.setEnabled(false);

			initExport();

			Map<String, List<StagedModel>> defaultDependentStagedModelsMap =
				addDefaultDependentStagedModelsMap(stagingGroup);

			StagedModel stagedModel = addDefaultStagedModel(
				stagingGroup, defaultDependentStagedModelsMap);

			if (stagedModel == null) {
				return;
			}

			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, stagedModel);

			validateExport(
				portletDataContext, stagedModel,
				defaultDependentStagedModelsMap);

			Map<String, List<StagedModel>> secondDependentStagedModelsMap =
				addDefaultDependentStagedModelsMap(liveGroup);

			addDefaultStagedModel(liveGroup, secondDependentStagedModelsMap);

			initImport();

			StagedModel exportedStagedModel = readExportedStagedModel(
				stagedModel);

			Assert.assertNull(exportedStagedModel);
		}
		finally {
			_dummyFolderStagedModelDataHandler.setEnabled(false);
		}
	}

	@Override
	@Test
	public void testStagedModelDataHandler() throws Exception {
		try {
			_dummyFolderStagedModelDataHandler.setEnabled(true);

			super.testStagedModelDataHandler();

			_dummyFolderStagedModelDataHandler.setEnabled(false);

			initExport();

			Map<String, List<StagedModel>> dependentStagedModelsMap =
				addDependentStagedModelsMap(stagingGroup);

			StagedModel stagedModel = addStagedModel(
				stagingGroup, dependentStagedModelsMap);

			addComments(stagedModel);

			addRatings(stagedModel);

			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, stagedModel);

			validateExport(
				portletDataContext, stagedModel, dependentStagedModelsMap);

			initImport();

			StagedModel exportedStagedModel = readExportedStagedModel(
				stagedModel);

			Assert.assertNull(exportedStagedModel);
		}
		finally {
			_dummyFolderStagedModelDataHandler.setEnabled(false);
		}
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		return _dummyFolderStagedModelRepository.addStagedModel(
			portletDataContext,
			DummyFolderTestUtil.createDummyFolder(group.getGroupId()));
	}

	@Override
	protected void deleteStagedModel(
			StagedModel dummyFolder,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		_dummyFolderStagedModelRepository.deleteStagedModel(
			(DummyFolder)dummyFolder);
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		return _dummyFolderStagedModelRepository.
			fetchStagedModelByUuidAndGroupId(uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return DummyFolder.class;
	}

	private static DummyFolderStagedModelDataHandler
		_dummyFolderStagedModelDataHandler;

	private StagedModelRepository<DummyFolder>
		_dummyFolderStagedModelRepository;
	private ServiceRegistration<?> _serviceRegistration;

	@Inject
	private StagedModelDataHandler<DummyFolder> _stagedModelDataHandler;

}