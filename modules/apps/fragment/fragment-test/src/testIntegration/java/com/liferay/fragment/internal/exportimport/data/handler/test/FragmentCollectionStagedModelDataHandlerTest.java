/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.test.util.FragmentTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.DateTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Kyle Miho
 */
@RunWith(Arquillian.class)
public class FragmentCollectionStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	@TestInfo("LPD-91119")
	public void testImportPreservesFragmentCollectionKey() throws Exception {
		initExport();

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(
				stagingGroup.getGroupId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString());

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, fragmentCollection);

		try (SafeCloseable safeCloseable = initImportWithSafeCloseable()) {
			StagedModel exportedStagedModel = readExportedStagedModel(
				fragmentCollection);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, exportedStagedModel);

			FragmentCollection importedFragmentCollection =
				_fragmentCollectionLocalService.
					getFragmentCollectionByUuidAndGroupId(
						fragmentCollection.getUuid(), liveGroup.getGroupId());

			Assert.assertEquals(
				fragmentCollection.getFragmentCollectionKey(),
				importedFragmentCollection.getFragmentCollectionKey());
		}
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		return FragmentTestUtil.addFragmentCollection(group.getGroupId());
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group)
		throws PortalException {

		return _fragmentCollectionLocalService.
			getFragmentCollectionByUuidAndGroupId(uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return FragmentCollection.class;
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		DateTestUtil.assertEquals(
			stagedModel.getCreateDate(), importedStagedModel.getCreateDate());

		Assert.assertEquals(
			stagedModel.getUuid(), importedStagedModel.getUuid());

		FragmentCollection fragmentCollection = (FragmentCollection)stagedModel;
		FragmentCollection importedFragmentCollection =
			(FragmentCollection)importedStagedModel;

		Assert.assertEquals(
			fragmentCollection.getName(), importedFragmentCollection.getName());
		Assert.assertEquals(
			fragmentCollection.getFragmentCollectionKey(),
			importedFragmentCollection.getFragmentCollectionKey());
	}

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

}