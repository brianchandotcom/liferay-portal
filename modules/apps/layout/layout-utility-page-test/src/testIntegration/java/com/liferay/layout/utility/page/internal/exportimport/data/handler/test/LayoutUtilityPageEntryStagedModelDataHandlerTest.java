/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.DateTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class LayoutUtilityPageEntryStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testChangeDefaultLayoutUtilityPageEntry() throws Exception {
		initExport();

		LayoutUtilityPageEntry layoutUtilityPageEntry1 =
			_addLayoutUtilityPageEntry(true, stagingGroup);

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, layoutUtilityPageEntry1);

		initImport();

		LayoutUtilityPageEntry exportedLayoutUtilityPageEntry1 =
			(LayoutUtilityPageEntry)readExportedStagedModel(
				layoutUtilityPageEntry1);

		LayoutUtilityPageEntry importedLayoutUtilityPageEntry1 =
			_getImportedLayoutUtilityPageEntry(
				exportedLayoutUtilityPageEntry1, liveGroup,
				layoutUtilityPageEntry1);

		Assert.assertTrue(
			importedLayoutUtilityPageEntry1.isDefaultLayoutUtilityPageEntry());

		initExport();

		LayoutUtilityPageEntry layoutUtilityPageEntry2 =
			_addLayoutUtilityPageEntry(true, stagingGroup);

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, layoutUtilityPageEntry2);

		// We changed the default utility page entry, so we have to export
		// the model again

		layoutUtilityPageEntry1 =
			_layoutUtilityPageEntryLocalService.getLayoutUtilityPageEntry(
				layoutUtilityPageEntry1.getLayoutUtilityPageEntryId());

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, layoutUtilityPageEntry1);

		initImport();

		exportedLayoutUtilityPageEntry1 =
			(LayoutUtilityPageEntry)readExportedStagedModel(
				layoutUtilityPageEntry1);

		importedLayoutUtilityPageEntry1 = _getImportedLayoutUtilityPageEntry(
			exportedLayoutUtilityPageEntry1, liveGroup,
			layoutUtilityPageEntry1);

		Assert.assertFalse(
			importedLayoutUtilityPageEntry1.isDefaultLayoutUtilityPageEntry());

		LayoutUtilityPageEntry exportedLayoutUtilityPageEntry2 =
			(LayoutUtilityPageEntry)readExportedStagedModel(
				layoutUtilityPageEntry2);

		LayoutUtilityPageEntry importedLayoutUtilityPageEntry2 =
			_getImportedLayoutUtilityPageEntry(
				exportedLayoutUtilityPageEntry2, liveGroup,
				layoutUtilityPageEntry2);

		Assert.assertTrue(
			importedLayoutUtilityPageEntry2.isDefaultLayoutUtilityPageEntry());
	}

	@Test
	public void testDefaultLayoutUtilityPageEntryAfterUpdate()
		throws Exception {

		initExport();

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_addLayoutUtilityPageEntry(false, stagingGroup);

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, layoutUtilityPageEntry);

		initImport();

		LayoutUtilityPageEntry exportedLayoutUtilityPageEntry =
			(LayoutUtilityPageEntry)readExportedStagedModel(
				layoutUtilityPageEntry);

		LayoutUtilityPageEntry importedLayoutUtilityPageEntry =
			_getImportedLayoutUtilityPageEntry(
				exportedLayoutUtilityPageEntry, liveGroup,
				layoutUtilityPageEntry);

		Assert.assertFalse(
			importedLayoutUtilityPageEntry.isDefaultLayoutUtilityPageEntry());

		initExport();

		layoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.
				setDefaultLayoutUtilityPageEntry(
					layoutUtilityPageEntry.getLayoutUtilityPageEntryId());

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, layoutUtilityPageEntry);

		initImport();

		exportedLayoutUtilityPageEntry =
			(LayoutUtilityPageEntry)readExportedStagedModel(
				layoutUtilityPageEntry);

		importedLayoutUtilityPageEntry = _getImportedLayoutUtilityPageEntry(
			exportedLayoutUtilityPageEntry, liveGroup, layoutUtilityPageEntry);

		Assert.assertTrue(
			importedLayoutUtilityPageEntry.isDefaultLayoutUtilityPageEntry());
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		long userId = TestPropsValues.getUserId();

		return _layoutUtilityPageEntryLocalService.addLayoutUtilityPageEntry(
			null, userId, group.getGroupId(), 0, 0, false, "Test Entry",
			LayoutUtilityPageEntryConstants.TYPE_SC_NOT_FOUND, 0,
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), userId));
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		return _layoutUtilityPageEntryLocalService.
			fetchLayoutUtilityPageEntryByUuidAndGroupId(
				uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return LayoutUtilityPageEntry.class;
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		DateTestUtil.assertEquals(
			stagedModel.getCreateDate(), importedStagedModel.getCreateDate());

		Assert.assertEquals(
			stagedModel.getUuid(), importedStagedModel.getUuid());

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			(LayoutUtilityPageEntry)stagedModel;
		LayoutUtilityPageEntry importLayoutUtilityPageEntry =
			(LayoutUtilityPageEntry)importedStagedModel;

		Assert.assertEquals(
			layoutUtilityPageEntry.getName(),
			importLayoutUtilityPageEntry.getName());
		Assert.assertEquals(
			layoutUtilityPageEntry.getType(),
			importLayoutUtilityPageEntry.getType());
	}

	private LayoutUtilityPageEntry _addLayoutUtilityPageEntry(
			boolean defaultLayoutUtilityPageEntry, Group group)
		throws Exception {

		return _layoutUtilityPageEntryLocalService.addLayoutUtilityPageEntry(
			null, TestPropsValues.getUserId(), group.getGroupId(), 0, 0,
			defaultLayoutUtilityPageEntry, RandomTestUtil.randomString(),
			LayoutUtilityPageEntryConstants.TYPE_SC_NOT_FOUND, 0,
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId()));
	}

	private LayoutUtilityPageEntry _getImportedLayoutUtilityPageEntry(
			LayoutUtilityPageEntry exportedLayoutUtilityPageEntry, Group group,
			LayoutUtilityPageEntry layoutUtilityPageEntry)
		throws Exception {

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedLayoutUtilityPageEntry);

		return (LayoutUtilityPageEntry)getStagedModel(
			layoutUtilityPageEntry.getUuid(), group);
	}

	@Inject
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

}