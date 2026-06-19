/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntryRel;
import com.liferay.client.extension.service.ClientExtensionEntryRelLocalService;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.layout.set.model.adapter.StagedLayoutSet;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.model.adapter.util.ModelAdapterUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Vy Bui
 */
@RunWith(Arquillian.class)
public class ClientExtensionEntryRelStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	@TestInfo("LPD-95403")
	public void testImportKeepsClientExtensionEntryRelMissingFromSourceGroup()
		throws Exception {

		LayoutSet layoutSet = stagingGroup.getPublicLayoutSet();

		ClientExtensionEntryRel clientExtensionEntryRel =
			_clientExtensionEntryRelLocalService.addClientExtensionEntryRel(
				TestPropsValues.getUserId(), stagingGroup.getGroupId(),
				_portal.getClassNameId(LayoutSet.class),
				layoutSet.getLayoutSetId(), RandomTestUtil.randomString(),
				ClientExtensionEntryConstants.TYPE_THEME_FAVICON,
				StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(
					stagingGroup.getGroupId()));

		StagedLayoutSet stagedLayoutSet = ModelAdapterUtil.adapt(
			layoutSet, LayoutSet.class, StagedLayoutSet.class);

		initExport();

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, stagedLayoutSet);

		_clientExtensionEntryRelLocalService.deleteClientExtensionEntryRel(
			clientExtensionEntryRel);

		try (SafeCloseable safeCloseable = initImportWithSafeCloseable()) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, readExportedStagedModel(stagedLayoutSet));
		}

		LayoutSet liveLayoutSet = liveGroup.getPublicLayoutSet();

		List<ClientExtensionEntryRel> clientExtensionEntryRels =
			_clientExtensionEntryRelLocalService.getClientExtensionEntryRels(
				_portal.getClassNameId(LayoutSet.class),
				liveLayoutSet.getLayoutSetId());

		Assert.assertEquals(
			clientExtensionEntryRels.toString(), 1,
			clientExtensionEntryRels.size());
	}

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<>();

		addDependentStagedModel(
			dependentStagedModelsMap, LayoutSet.class,
			ModelAdapterUtil.adapt(
				group.getPublicLayoutSet(), LayoutSet.class,
				StagedLayoutSet.class));

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			LayoutSet.class.getSimpleName());

		StagedLayoutSet stagedLayoutSet =
			(StagedLayoutSet)dependentStagedModels.get(0);

		LayoutSet layoutSet = stagedLayoutSet.getLayoutSet();

		return _clientExtensionEntryRelLocalService.addClientExtensionEntryRel(
			TestPropsValues.getUserId(), group.getGroupId(),
			_portal.getClassNameId(LayoutSet.class), layoutSet.getLayoutSetId(),
			RandomTestUtil.randomString(),
			ClientExtensionEntryConstants.TYPE_THEME_FAVICON, StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group)
		throws PortalException {

		return _clientExtensionEntryRelLocalService.
			getClientExtensionEntryRelByUuidAndGroupId(
				uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return ClientExtensionEntryRel.class;
	}

	@Inject
	private ClientExtensionEntryRelLocalService
		_clientExtensionEntryRelLocalService;

	@Inject
	private Portal _portal;

}