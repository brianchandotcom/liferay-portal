/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryRegistryUtil;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.exportimport.test.util.model.Dummy;
import com.liferay.exportimport.test.util.model.DummyFolder;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistryUtil;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Akos Thurzo
 */
@RunWith(Arquillian.class)
public class DummyStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_dummyStagedModelRepository =
			(StagedModelRepository<Dummy>)
				StagedModelRepositoryRegistryUtil.getStagedModelRepository(
					Dummy.class.getName());

		_dummyFolderStagedModelRepository =
			(StagedModelRepository<DummyFolder>)
				StagedModelRepositoryRegistryUtil.getStagedModelRepository(
					DummyFolder.class.getName());

		PersistedModelLocalServiceRegistryUtil.register(
			Dummy.class.getName(),
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

			});
	}

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new LinkedHashMap<>();

		DummyFolder dummyFolder =
			_dummyFolderStagedModelRepository.addStagedModel(
				null,
				new DummyFolder(
					TestPropsValues.getCompanyId(), group.getGroupId(),
					TestPropsValues.getUser()));

		addDependentStagedModel(
			dependentStagedModelsMap, DummyFolder.class, dummyFolder);

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> dummyFolderStagedModels =
			dependentStagedModelsMap.get(DummyFolder.class.getSimpleName());

		DummyFolder dummyFolder = (DummyFolder)dummyFolderStagedModels.get(0);

		Dummy dummy = new Dummy(
			group.getCompanyId(), group.getGroupId(), dummyFolder.getId(),
			TestPropsValues.getUser(), new ArrayList<>());

		return _dummyStagedModelRepository.addStagedModel(
			portletDataContext, dummy);
	}

	@Override
	protected void deleteStagedModel(
			StagedModel dummy,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		_dummyStagedModelRepository.deleteStagedModel((Dummy)dummy);
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		return _dummyStagedModelRepository.fetchStagedModelByUuidAndGroupId(
			uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return Dummy.class;
	}

	private StagedModelRepository<DummyFolder>
		_dummyFolderStagedModelRepository;
	private StagedModelRepository<Dummy> _dummyStagedModelRepository;

}