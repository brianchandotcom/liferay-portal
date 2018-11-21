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

package com.liferay.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryRegistryUtil;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.exportimport.test.util.model.Dummy;
import com.liferay.exportimport.test.util.model.DummyFolder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistryUtil;
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