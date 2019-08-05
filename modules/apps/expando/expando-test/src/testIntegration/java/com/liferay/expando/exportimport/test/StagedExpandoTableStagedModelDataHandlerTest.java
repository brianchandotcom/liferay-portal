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

package com.liferay.expando.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.adapter.StagedExpandoTable;
import com.liferay.expando.kernel.service.ExpandoTableLocalServiceUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryRegistryUtil;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.adapter.ModelAdapterUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Akos Thurzo
 */
@RunWith(Arquillian.class)
@Sync
public class StagedExpandoTableStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_stagedModelRepository =
			(StagedModelRepository<StagedExpandoTable>)
				StagedModelRepositoryRegistryUtil.getStagedModelRepository(
					StagedExpandoTable.class.getName());
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		ExpandoTable expandoTable = ExpandoTableLocalServiceUtil.addTable(
			group.getCompanyId(), ExpandoTable.class.getName(),
			RandomTestUtil.randomString());

		return ModelAdapterUtil.adapt(
			expandoTable, ExpandoTable.class, StagedExpandoTable.class);
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group)
		throws PortalException {

		List<StagedExpandoTable> stagedExpandoTables =
			_stagedModelRepository.fetchStagedModelsByUuidAndCompanyId(
				uuid, group.getCompanyId());

		if (ListUtil.isNotEmpty(stagedExpandoTables)) {
			return stagedExpandoTables.get(0);
		}

		throw new PortalException(
			StringBundler.concat(
				"Unable to find StagedExpandoTable with uuid: ", uuid,
				", companyId: ", group.getCompanyId()));
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return StagedExpandoTable.class;
	}

	@Override
	protected boolean supportLastPublishDateUpdate() {
		return false;
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		StagedExpandoTable stagedExpandoTable = (StagedExpandoTable)stagedModel;
		StagedExpandoTable importedStagedExpandoTable =
			(StagedExpandoTable)importedStagedModel;

		Assert.assertEquals(
			stagedExpandoTable.getClass(),
			importedStagedExpandoTable.getClass());
		Assert.assertEquals(
			stagedExpandoTable.getCompanyId(),
			importedStagedExpandoTable.getCompanyId());
		Assert.assertEquals(
			stagedExpandoTable.getName(), importedStagedExpandoTable.getName());
		Assert.assertEquals(
			stagedExpandoTable.getUuid(), importedStagedExpandoTable.getUuid());
	}

	private StagedModelRepository<StagedExpandoTable> _stagedModelRepository;

}