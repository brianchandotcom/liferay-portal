/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.taxonomy.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.exportimport.test.util.exportimport.data.handler.BaseBatchEnginePortletDataHandlerTestCase;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.Scope;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyCategoryResource;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Alberto Javier Moreno Lage
 */
@RunWith(Arquillian.class)
public class TaxonomyCategoryBatchEnginePortletDataHandlerTest
	extends BaseBatchEnginePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected String addEmptyEntry(long groupId, long userId) throws Exception {
		AssetCategory assetCategory =
			_assetCategoryLocalService.getOrAddEmptyCategory(
				RandomTestUtil.randomString(), userId, groupId);

		return assetCategory.getExternalReferenceCode();
	}

	@Override
	protected String addEntry(long groupId, long userId, Date dateModified)
		throws Exception {

		AssetCategory assetCategory = _assetCategoryLocalService.addCategory(
			userId, groupId, RandomTestUtil.randomString(),
			_getVocabularyId(groupId, userId),
			ServiceContextTestUtil.getServiceContext(groupId, userId));

		assetCategory.setModifiedDate(dateModified);

		assetCategory = _assetCategoryLocalService.updateAssetCategory(
			assetCategory);

		return assetCategory.getExternalReferenceCode();
	}

	@Override
	protected void addStagedModels() throws Exception {
	}

	@Override
	protected void deleteEntry(long groupId, String externalReferenceCode)
		throws Exception {

		_assetCategoryLocalService.deleteCategory(
			_getAssetCategory(groupId, externalReferenceCode));
	}

	@Override
	protected long getCreatorUserId(long groupId, String externalReferenceCode)
		throws Exception {

		AssetCategory assetCategory = _getAssetCategory(
			groupId, externalReferenceCode);

		return assetCategory.getUserId();
	}

	@Override
	protected Object getEntryValue(long groupId, String externalReferenceCode)
		throws Exception {

		AssetCategory assetCategory = _getAssetCategory(
			groupId, externalReferenceCode);

		return assetCategory.getTitle(LocaleUtil.getSiteDefault());
	}

	@Override
	protected ExportImportVulcanBatchEngineTaskItemDelegate<?>
		getExportImportVulcanBatchEngineTaskItemDelegate() {

		return getExportImportVulcanBatchEngineTaskItemDelegate(
			TaxonomyCategoryResource.class);
	}

	@Override
	protected List<String> getExternalReferenceCodes(long groupId)
		throws Exception {

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.
				fetchAssetVocabularyByExternalReferenceCode(
					_VOCABULARY_EXTERNAL_REFERENCE_CODE, groupId);

		if (assetVocabulary == null) {
			return List.of();
		}

		return TransformUtil.transform(
			_assetCategoryLocalService.getVocabularyCategories(
				assetVocabulary.getVocabularyId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null),
			AssetCategory::getExternalReferenceCode);
	}

	@Override
	protected long getPrimaryKey(long groupId, String externalReferenceCode)
		throws Exception {

		AssetCategory assetCategory = _getAssetCategory(
			groupId, externalReferenceCode);

		return assetCategory.getCategoryId();
	}

	@Override
	protected Scope getScope() {
		return Scope.SITE;
	}

	@Override
	protected int getStatus(long groupId, String externalReferenceCode)
		throws Exception {

		AssetCategory assetCategory = _getAssetCategory(
			groupId, externalReferenceCode);

		return assetCategory.getStatus();
	}

	@Override
	protected boolean supportsComments() {
		return false;
	}

	@Override
	protected boolean supportsEmptyEntries() {
		return true;
	}

	@Override
	protected boolean supportsPermissions() {
		return true;
	}

	@Override
	protected void updateEntry(long groupId, String externalReferenceCode)
		throws Exception {

		AssetCategory assetCategory = _getAssetCategory(
			groupId, externalReferenceCode);

		_assetCategoryLocalService.updateCategory(
			assetCategory.getExternalReferenceCode(), assetCategory.getUserId(),
			assetCategory.getCategoryId(),
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), RandomTestUtil.randomString()
			).build(),
			assetCategory.getDescriptionMap(),
			_getVocabularyId(groupId, assetCategory.getUserId()), new String[0],
			ServiceContextTestUtil.getServiceContext(
				groupId, assetCategory.getUserId()));
	}

	private AssetCategory _getAssetCategory(
			long groupId, String externalReferenceCode)
		throws Exception {

		return _assetCategoryLocalService.
			fetchAssetCategoryByExternalReferenceCode(
				externalReferenceCode, groupId);
	}

	private long _getVocabularyId(long groupId, long userId) throws Exception {
		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.
				fetchAssetVocabularyByExternalReferenceCode(
					_VOCABULARY_EXTERNAL_REFERENCE_CODE, groupId);

		if (assetVocabulary == null) {
			assetVocabulary = _assetVocabularyLocalService.addVocabulary(
				_VOCABULARY_EXTERNAL_REFERENCE_CODE, userId, groupId,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				HashMapBuilder.put(
					LocaleUtil.getSiteDefault(), RandomTestUtil.randomString()
				).build(),
				HashMapBuilder.put(
					LocaleUtil.getSiteDefault(), StringPool.BLANK
				).build(),
				null, AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC,
				new ServiceContext());
		}

		return assetVocabulary.getVocabularyId();
	}

	private static final String _VOCABULARY_EXTERNAL_REFERENCE_CODE =
		"x" + RandomTestUtil.randomString();

	@Inject
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

}