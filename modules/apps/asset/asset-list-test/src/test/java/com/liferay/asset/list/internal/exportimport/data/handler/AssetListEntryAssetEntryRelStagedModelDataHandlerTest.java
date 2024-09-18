/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.internal.exportimport.data.handler;

import com.liferay.asset.list.model.AssetListEntryAssetEntryRel;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Gustavo Lima
 */
public class AssetListEntryAssetEntryRelStagedModelDataHandlerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testDoImportStagedModelHandlesNullStagedModelCorrectly()
		throws Exception {

		_setUpGroupLocalService();
		_setUpStagedModel();

		PortletDataContext portletDataContext = Mockito.spy(
			PortletDataContext.class);

		_assetListEntryAssetEntryRelStagedModelDataHandler.doImportStagedModel(
			portletDataContext, _getAssetListEntryAssetEntryRel());

		Mockito.verify(
			portletDataContext, Mockito.never()
		).importClassedModel(
			Mockito.any(), Mockito.any()
		);
	}

	private AssetListEntryAssetEntryRel _getAssetListEntryAssetEntryRel() {
		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel = Mockito.mock(
			AssetListEntryAssetEntryRel.class);

		Mockito.doReturn(
			assetListEntryAssetEntryRel
		).when(
			assetListEntryAssetEntryRel
		).clone();

		Mockito.doReturn(
			RandomTestUtil.randomLong()
		).when(
			assetListEntryAssetEntryRel
		).getAssetListEntryId();

		return assetListEntryAssetEntryRel;
	}

	private void _setUpGroupLocalService() {
		GroupLocalService groupLocalService = Mockito.mock(
			GroupLocalService.class);

		Mockito.doReturn(
			Mockito.mock(Group.class)
		).when(
			groupLocalService
		).fetchGroup(
			Mockito.anyLong()
		);

		ReflectionTestUtil.setFieldValue(
			_assetListEntryAssetEntryRelStagedModelDataHandler,
			"_groupLocalService", groupLocalService);
	}

	private void _setUpStagedModel() throws Exception {
		StagedModelRepository<AssetListEntryAssetEntryRel>
			stagedModelRepository = Mockito.mock(StagedModelRepository.class);

		Mockito.doReturn(
			null
		).when(
			stagedModelRepository
		).addStagedModel(
			Mockito.any(), Mockito.any()
		);

		Mockito.doReturn(
			null
		).when(
			stagedModelRepository
		).fetchStagedModelByUuidAndGroupId(
			Mockito.anyString(), Mockito.anyLong()
		);

		ReflectionTestUtil.setFieldValue(
			_assetListEntryAssetEntryRelStagedModelDataHandler,
			"_stagedModelRepository", stagedModelRepository);
	}

	private final AssetListEntryAssetEntryRelStagedModelDataHandler
		_assetListEntryAssetEntryRelStagedModelDataHandler =
			new AssetListEntryAssetEntryRelStagedModelDataHandler();

}