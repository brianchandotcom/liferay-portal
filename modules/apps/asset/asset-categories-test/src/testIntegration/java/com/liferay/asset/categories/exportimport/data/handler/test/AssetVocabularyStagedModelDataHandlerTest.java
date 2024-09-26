/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
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
 * @author Máté Thurzó
 */
@RunWith(Arquillian.class)
public class AssetVocabularyStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAssetVocabularyWithDepotSettingsAreNotLost()
		throws Exception {

		initExport();

		String classNameIds =
			_portal.getClassNameId(JournalArticle.class.getName()) + ":-1";

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.create(
				true
			).put(
				"depotRequiredClassNameIds", classNameIds
			).put(
				"multivalued", true
			).put(
				"selectedClassNameIds", classNameIds
			).build();

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				TestPropsValues.getUserId(), stagingGroup.getGroupId(),
				RandomTestUtil.randomString(),
				HashMapBuilder.put(
					LocaleUtil.getDefault(), RandomTestUtil.randomString()
				).build(),
				HashMapBuilder.put(
					LocaleUtil.getDefault(), RandomTestUtil.randomString()
				).build(),
				typeSettingsUnicodeProperties.toString(),
				ServiceContextTestUtil.getServiceContext(
					stagingGroup.getGroupId()));

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, assetVocabulary);

		initImport();

		StagedModel exportedStagedModel = readExportedStagedModel(
			assetVocabulary);

		Assert.assertNotNull(exportedStagedModel);

		ExportImportThreadLocal.setPortletImportInProcess(true);

		try {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, exportedStagedModel);
		}
		finally {
			ExportImportThreadLocal.setPortletImportInProcess(false);
		}

		AssetVocabulary importAssetVocabulary =
			_assetVocabularyLocalService.fetchAssetVocabularyByUuidAndGroupId(
				assetVocabulary.getUuid(), liveGroup.getGroupId());

		Assert.assertNotNull(importAssetVocabulary);

		UnicodeProperties importedTypeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.create(
				true
			).fastLoad(
				importAssetVocabulary.getSettings()
			).build();

		Assert.assertEquals(
			classNameIds,
			importedTypeSettingsUnicodeProperties.get(
				"depotRequiredClassNameIds"));
		Assert.assertEquals(
			"true", importedTypeSettingsUnicodeProperties.get("multivalued"));
		Assert.assertEquals(
			classNameIds,
			importedTypeSettingsUnicodeProperties.get("selectedClassNameIds"));
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		return AssetTestUtil.addVocabulary(
			group.getGroupId(),
			RandomTestUtil.randomString(3) + StringPool.SPACE +
				RandomTestUtil.randomString(4));
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group)
		throws PortalException {

		return AssetVocabularyLocalServiceUtil.
			getAssetVocabularyByUuidAndGroupId(uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return AssetVocabulary.class;
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		AssetVocabulary vocabulary = (AssetVocabulary)stagedModel;
		AssetVocabulary importedVocabulary =
			(AssetVocabulary)importedStagedModel;

		Assert.assertEquals(
			vocabulary.getExternalReferenceCode(),
			importedVocabulary.getExternalReferenceCode());
		Assert.assertEquals(vocabulary.getName(), importedVocabulary.getName());
		Assert.assertEquals(
			vocabulary.getTitle(LocaleUtil.getDefault()),
			importedVocabulary.getTitle(LocaleUtil.getDefault()));
		Assert.assertEquals(
			vocabulary.getDescription(LocaleUtil.getDefault()),
			importedVocabulary.getDescription(LocaleUtil.getDefault()));
		Assert.assertEquals(
			vocabulary.getSettings(), importedVocabulary.getSettings());
	}

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Inject
	private Portal _portal;

}