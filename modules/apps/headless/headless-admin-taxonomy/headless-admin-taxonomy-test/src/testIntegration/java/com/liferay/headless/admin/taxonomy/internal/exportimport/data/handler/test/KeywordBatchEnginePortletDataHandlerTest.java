/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.taxonomy.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.exportimport.test.rule.ExportImportScopes;
import com.liferay.exportimport.test.util.exportimport.data.handler.BaseBatchEnginePortletDataHandlerTestCase;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.Scope;
import com.liferay.headless.admin.taxonomy.resource.v1_0.KeywordResource;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@ExportImportScopes(Scope.SITE)
@RunWith(Arquillian.class)
public class KeywordBatchEnginePortletDataHandlerTest
	extends BaseBatchEnginePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected String addEntry(long groupId, long userId, Date dateModified)
		throws Exception {

		AssetTag assetTag = _assetTagLocalService.addTag(
			null, userId, groupId, _randomName(),
			ServiceContextTestUtil.getServiceContext(groupId, userId));

		assetTag.setModifiedDate(dateModified);

		assetTag = _assetTagLocalService.updateAssetTag(assetTag);

		return assetTag.getExternalReferenceCode();
	}

	@Override
	protected void addStagedModels() throws Exception {
	}

	@Override
	protected void deleteEntry(long groupId, String externalReferenceCode)
		throws Exception {

		_assetTagLocalService.deleteTag(
			_getAssetTag(groupId, externalReferenceCode));
	}

	@Override
	protected long getCreatorUserId(long groupId, String externalReferenceCode)
		throws Exception {

		AssetTag assetTag = _getAssetTag(groupId, externalReferenceCode);

		return assetTag.getUserId();
	}

	@Override
	protected Object getEntryValue(long groupId, String externalReferenceCode)
		throws Exception {

		AssetTag assetTag = _getAssetTag(groupId, externalReferenceCode);

		return assetTag.getName();
	}

	@Override
	protected ExportImportVulcanBatchEngineTaskItemDelegate<?>
		getExportImportVulcanBatchEngineTaskItemDelegate() {

		return getExportImportVulcanBatchEngineTaskItemDelegate(
			KeywordResource.class);
	}

	@Override
	protected List<String> getExternalReferenceCodes(long groupId)
		throws Exception {

		return TransformUtil.transform(
			_assetTagLocalService.getGroupTags(groupId),
			AssetTag::getExternalReferenceCode);
	}

	@Override
	protected long getPrimaryKey(long groupId, String externalReferenceCode)
		throws Exception {

		AssetTag assetTag = _getAssetTag(groupId, externalReferenceCode);

		return assetTag.getTagId();
	}

	@Override
	protected boolean supportsComments() {
		return false;
	}

	@Override
	protected boolean supportsEmptyEntries() {
		return false;
	}

	@Override
	protected boolean supportsPermissions() {
		return false;
	}

	@Override
	protected void updateEntry(long groupId, String externalReferenceCode)
		throws Exception {

		AssetTag assetTag = _getAssetTag(groupId, externalReferenceCode);

		_assetTagLocalService.updateTag(
			assetTag.getExternalReferenceCode(), assetTag.getUserId(),
			assetTag.getTagId(), _randomName(),
			ServiceContextTestUtil.getServiceContext(
				groupId, assetTag.getUserId()));
	}

	private AssetTag _getAssetTag(long groupId, String externalReferenceCode)
		throws Exception {

		return _assetTagLocalService.fetchAssetTagByExternalReferenceCode(
			externalReferenceCode, groupId);
	}

	private String _randomName() {
		return StringUtil.toLowerCase(RandomTestUtil.randomString());
	}

	@Inject
	private AssetTagLocalService _assetTagLocalService;

}