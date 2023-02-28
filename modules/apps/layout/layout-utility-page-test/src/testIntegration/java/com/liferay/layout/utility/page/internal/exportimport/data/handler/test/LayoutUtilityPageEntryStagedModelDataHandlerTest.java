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
	public void testDefaultLayoutUtilityPageEntryAfterUpdate()
		throws Exception {

		initExport();

		long userId = TestPropsValues.getUserId();

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.addLayoutUtilityPageEntry(
				null, userId, stagingGroup.getGroupId(), 0, 0, false,
				"Test Entry", LayoutUtilityPageEntryConstants.TYPE_SC_NOT_FOUND,
				0,
				ServiceContextTestUtil.getServiceContext(
					stagingGroup.getGroupId(), userId));

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, layoutUtilityPageEntry);

		initImport();

		LayoutUtilityPageEntry exportedLayoutUtilityPageEntry =
			(LayoutUtilityPageEntry)readExportedStagedModel(
				layoutUtilityPageEntry);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedLayoutUtilityPageEntry);

		LayoutUtilityPageEntry importedLayoutUtilityPageEntry =
			(LayoutUtilityPageEntry)getStagedModel(
				layoutUtilityPageEntry.getUuid(), liveGroup);

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

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedLayoutUtilityPageEntry);

		importedLayoutUtilityPageEntry = (LayoutUtilityPageEntry)getStagedModel(
			layoutUtilityPageEntry.getUuid(), liveGroup);

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

	@Inject
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

}