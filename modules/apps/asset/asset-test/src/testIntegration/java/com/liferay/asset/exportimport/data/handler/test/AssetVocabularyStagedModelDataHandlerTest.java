/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
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

}