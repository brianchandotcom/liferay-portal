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
import com.liferay.exportimport.test.util.model.DummyFolder;
import com.liferay.exportimport.test.util.model.util.DummyFolderTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

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
public class DummyFolderStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_dummyFolderStagedModelRepository =
			(StagedModelRepository<DummyFolder>)
				StagedModelRepositoryRegistryUtil.getStagedModelRepository(
					DummyFolder.class.getName());

		PersistedModelLocalServiceRegistryUtil.register(
			DummyFolder.class.getName(),
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

	private StagedModelRepository<DummyFolder>
		_dummyFolderStagedModelRepository;

}