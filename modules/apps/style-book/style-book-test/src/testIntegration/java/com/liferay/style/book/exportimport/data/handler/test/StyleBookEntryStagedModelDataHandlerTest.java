/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.DateTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Petteri Karttunen
 */
@RunWith(Arquillian.class)
public class StyleBookEntryStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testExportImportPreservesModifiedDateWithPreviewFileEntry()
		throws Exception {

		StyleBookEntry styleBookEntry = (StyleBookEntry)addStagedModel(
			stagingGroup, new HashMap<>());

		FileEntry previewFileEntry = _addPreviewFileEntry(styleBookEntry);

		styleBookEntry = _styleBookEntryLocalService.updatePreviewFileEntryId(
			styleBookEntry.getStyleBookEntryId(),
			previewFileEntry.getFileEntryId(),
			ServiceContextTestUtil.getServiceContext(
				stagingGroup.getGroupId(), TestPropsValues.getUserId()));

		Thread.sleep(1000);

		exportImportStagedModel(styleBookEntry);

		StyleBookEntry importedStyleBookEntry = (StyleBookEntry)getStagedModel(
			styleBookEntry.getUuid(), liveGroup);

		Assert.assertNotNull(importedStyleBookEntry);
		Assert.assertTrue(importedStyleBookEntry.getPreviewFileEntryId() > 0);

		DateTestUtil.assertEquals(
			styleBookEntry.getModifiedDate(),
			importedStyleBookEntry.getModifiedDate());
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		return _styleBookEntryLocalService.addStyleBookEntry(
			null, TestPropsValues.getUserId(), group.getGroupId(), false,
			StringPool.BLANK, RandomTestUtil.randomString(), StringPool.BLANK,
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext());
	}

	@Override
	protected StagedModel addVersion(StagedModel stagedModel) throws Exception {
		StyleBookEntry styleBookEntry = (StyleBookEntry)stagedModel;

		Thread.sleep(1000);

		return _styleBookEntryLocalService.updateStyleBookEntry(
			styleBookEntry.getStyleBookEntryId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				styleBookEntry.getGroupId(), TestPropsValues.getUserId()));
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group)
		throws PortalException {

		return _styleBookEntryLocalService.fetchStyleBookEntryByUuidAndGroupId(
			uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return StyleBookEntry.class;
	}

	@Override
	protected boolean isVersionableStagedModel() {
		return true;
	}

	private FileEntry _addPreviewFileEntry(StyleBookEntry styleBookEntry)
		throws Exception {

		Repository repository = PortletFileRepositoryUtil.addPortletRepository(
			stagingGroup.getGroupId(), RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				stagingGroup.getGroupId(), TestPropsValues.getUserId()));

		Class<?> clazz = getClass();

		return PortletFileRepositoryUtil.addPortletFileEntry(
			null, stagingGroup.getGroupId(), TestPropsValues.getUserId(),
			StyleBookEntry.class.getName(),
			styleBookEntry.getStyleBookEntryId(), RandomTestUtil.randomString(),
			repository.getDlFolderId(),
			clazz.getResourceAsStream("dependencies/thumbnail.png"),
			RandomTestUtil.randomString(), ContentTypes.IMAGE_PNG, false);
	}

	@Inject
	private StyleBookEntryLocalService _styleBookEntryLocalService;

}