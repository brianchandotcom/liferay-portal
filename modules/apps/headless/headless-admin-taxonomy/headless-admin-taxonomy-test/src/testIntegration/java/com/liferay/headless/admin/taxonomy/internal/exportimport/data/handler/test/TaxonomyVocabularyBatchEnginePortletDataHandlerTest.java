/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.taxonomy.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.exportimport.test.rule.ExportImportScopes;
import com.liferay.exportimport.test.util.exportimport.data.handler.BaseBatchEnginePortletDataHandlerTestCase;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.Scope;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyVocabularyResource;
import com.liferay.petra.function.transform.TransformUtil;
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
@ExportImportScopes(Scope.SITE)
@RunWith(Arquillian.class)
public class TaxonomyVocabularyBatchEnginePortletDataHandlerTest
	extends BaseBatchEnginePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected String addEmptyEntry(long groupId, long userId) throws Exception {
		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.getOrAddEmptyVocabulary(
				RandomTestUtil.randomString(), userId, groupId);

		return assetVocabulary.getExternalReferenceCode();
	}

	@Override
	protected String addEntry(long groupId, long userId, Date dateModified)
		throws Exception {

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				userId, groupId, RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext(groupId, userId));

		assetVocabulary.setModifiedDate(dateModified);

		assetVocabulary = _assetVocabularyLocalService.updateAssetVocabulary(
			assetVocabulary);

		return assetVocabulary.getExternalReferenceCode();
	}

	@Override
	protected void addStagedModels() throws Exception {
	}

	@Override
	protected void deleteEntry(long groupId, String externalReferenceCode)
		throws Exception {

		_assetVocabularyLocalService.deleteVocabulary(
			_getAssetVocabulary(groupId, externalReferenceCode));
	}

	@Override
	protected long getCreatorUserId(long groupId, String externalReferenceCode)
		throws Exception {

		AssetVocabulary assetVocabulary = _getAssetVocabulary(
			groupId, externalReferenceCode);

		return assetVocabulary.getUserId();
	}

	@Override
	protected Object getEntryValue(long groupId, String externalReferenceCode)
		throws Exception {

		AssetVocabulary assetVocabulary = _getAssetVocabulary(
			groupId, externalReferenceCode);

		return assetVocabulary.getTitle(LocaleUtil.getSiteDefault());
	}

	@Override
	protected ExportImportVulcanBatchEngineTaskItemDelegate<?>
		getExportImportVulcanBatchEngineTaskItemDelegate() {

		return getExportImportVulcanBatchEngineTaskItemDelegate(
			TaxonomyVocabularyResource.class);
	}

	@Override
	protected List<String> getExternalReferenceCodes(long groupId)
		throws Exception {

		return TransformUtil.transform(
			_assetVocabularyLocalService.getGroupVocabularies(groupId),
			AssetVocabulary::getExternalReferenceCode);
	}

	@Override
	protected long getPrimaryKey(long groupId, String externalReferenceCode)
		throws Exception {

		AssetVocabulary assetVocabulary = _getAssetVocabulary(
			groupId, externalReferenceCode);

		return assetVocabulary.getVocabularyId();
	}

	@Override
	protected int getStatus(long groupId, String externalReferenceCode)
		throws Exception {

		AssetVocabulary assetVocabulary = _getAssetVocabulary(
			groupId, externalReferenceCode);

		return assetVocabulary.getStatus();
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

		AssetVocabulary assetVocabulary = _getAssetVocabulary(
			groupId, externalReferenceCode);

		_assetVocabularyLocalService.updateVocabulary(
			assetVocabulary.getExternalReferenceCode(),
			assetVocabulary.getVocabularyId(),
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), RandomTestUtil.randomString()
			).build(),
			assetVocabulary.getDescriptionMap(), assetVocabulary.getSettings());
	}

	private AssetVocabulary _getAssetVocabulary(
			long groupId, String externalReferenceCode)
		throws Exception {

		return _assetVocabularyLocalService.
			fetchAssetVocabularyByExternalReferenceCode(
				externalReferenceCode, groupId);
	}

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

}