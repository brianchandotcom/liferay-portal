/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.asset.list.service.AssetListEntrySegmentsEntryRelLocalService;
import com.liferay.asset.list.test.util.AssetListTestUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.Collections;
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
public class AssetListEntryStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	@TestInfo({"LPD-86116", "LPD-86506"})
	public void testExportImportAssetListEntry() throws Exception {
		AssetListEntry assetListEntry = (AssetListEntry)addStagedModel(
			stagingGroup, Collections.emptyMap());

		User user = TestPropsValues.getUser();

		SegmentsEntry segmentsEntry = _addSegmentsEntryByFirstName(
			user.getFirstName(), stagingGroup.getGroupId());

		String segmentEntryExternalReferenceCode =
			segmentsEntry.getExternalReferenceCode();

		AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
			stagingGroup.getGroupId(), assetListEntry,
			segmentsEntry.getSegmentsEntryId());

		exportImportStagedModel(assetListEntry);

		_assertEquals(
			assetListEntry,
			(AssetListEntry)getStagedModel(
				assetListEntry.getUuid(), liveGroup));

		Assert.assertNotNull(
			_segmentsEntryLocalService.getSegmentsEntryByExternalReferenceCode(
				segmentEntryExternalReferenceCode, liveGroup.getGroupId()));

		exportImportStagedModel(assetListEntry);

		_assertEquals(
			assetListEntry,
			(AssetListEntry)getStagedModel(
				assetListEntry.getUuid(), liveGroup));

		Assert.assertNotNull(
			_segmentsEntryLocalService.getSegmentsEntryByExternalReferenceCode(
				segmentEntryExternalReferenceCode, liveGroup.getGroupId()));
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		return _assetListEntryLocalService.addAssetListEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			stagingGroup.getGroupId(), RandomTestUtil.randomString(),
			AssetListEntryTypeConstants.TYPE_DYNAMIC,
			ServiceContextTestUtil.getServiceContext(
				stagingGroup.getGroupId()));
	}

	@Override
	protected void exportStagedModel(StagedModel stagedModel) throws Exception {
		initExport();

		try {
			ExportImportThreadLocal.setPortletExportInProcess(true);
			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, stagedModel);
		}
		finally {
			ExportImportThreadLocal.setPortletExportInProcess(false);
		}
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group)
		throws PortalException {

		return _assetListEntryLocalService.fetchAssetListEntryByUuidAndGroupId(
			uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return AssetListEntry.class;
	}

	@Override
	protected SafeCloseable initImportWithSafeCloseable(
			Group exportGroup, Group importGroup)
		throws Exception {

		SafeCloseable safeCloseable = super.initImportWithSafeCloseable(
			exportGroup, importGroup);

		ExportImportThreadLocal.setPortletImportInProcess(true);

		return () -> {
			try {
				ExportImportThreadLocal.setPortletImportInProcess(false);
			}
			finally {
				safeCloseable.close();
			}
		};
	}

	private SegmentsEntry _addSegmentsEntryByFirstName(
			String firstName, long groupId)
		throws Exception {

		Criteria criteria = new Criteria();

		_segmentsCriteriaContributor.contribute(
			criteria, String.format("(firstName eq '%s')", firstName),
			Criteria.Conjunction.AND);

		return SegmentsTestUtil.addSegmentsEntry(
			groupId, CriteriaSerializer.serialize(criteria));
	}

	private void _assertEquals(
			AssetListEntry assetListEntry,
			AssetListEntry importedAssetListEntry)
		throws Exception {

		Assert.assertEquals(
			assetListEntry.getExternalReferenceCode(),
			importedAssetListEntry.getExternalReferenceCode());
		Assert.assertEquals(
			assetListEntry.getAssetListEntryKey(),
			importedAssetListEntry.getAssetListEntryKey());
		Assert.assertEquals(
			assetListEntry.getAssetEntrySubtype(),
			importedAssetListEntry.getAssetEntrySubtype());
		Assert.assertEquals(
			assetListEntry.getAssetEntryType(),
			importedAssetListEntry.getAssetEntryType());
		Assert.assertEquals(
			assetListEntry.getCreateDate(),
			importedAssetListEntry.getCreateDate());
		Assert.assertEquals(
			assetListEntry.getModifiedDate(),
			importedAssetListEntry.getModifiedDate());
	}

	@Inject
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Inject
	private AssetListEntrySegmentsEntryRelLocalService
		_assetListEntrySegmentsEntryRelLocalService;

	@Inject(
		filter = "segments.criteria.contributor.key=user",
		type = SegmentsCriteriaContributor.class
	)
	private SegmentsCriteriaContributor _segmentsCriteriaContributor;

	@Inject
	private SegmentsEntryLocalService _segmentsEntryLocalService;

}