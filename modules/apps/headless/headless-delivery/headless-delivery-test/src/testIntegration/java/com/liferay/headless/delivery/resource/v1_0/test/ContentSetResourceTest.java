/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.headless.delivery.client.dto.v1_0.ContentSet;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@FeatureFlag("LPD-91525")
@RunWith(Arquillian.class)
public class ContentSetResourceTest extends BaseContentSetResourceTestCase {

	@Override
	@Test
	public void testGetAssetLibraryContentSetsPage() throws Exception {
		super.testGetAssetLibraryContentSetsPage();
	}

	@Override
	@Test
	public void testGetSiteContentSetsPage() throws Exception {
		super.testGetSiteContentSetsPage();

		_testGetSiteContentSetsPageWithItemSubtype();
		_testGetSiteContentSetsPageWithItemType();
		_testGetSiteContentSetsPageWithSearch();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"classNameId", "classPK", "externalReferenceCode", "itemSubtype",
			"itemType", "title"
		};
	}

	@Override
	protected ContentSet testGetAssetLibraryContentSetsPage_addContentSet(
			Long assetLibraryId, ContentSet contentSet)
		throws Exception {

		return _addContentSetFromDTO(contentSet, assetLibraryId);
	}

	@Override
	protected Long testGetAssetLibraryContentSetsPage_getAssetLibraryId()
		throws Exception {

		return testDepotEntryGroup.getGroupId();
	}

	@Override
	protected Long
			testGetAssetLibraryContentSetsPage_getIrrelevantAssetLibraryId()
		throws Exception {

		return irrelevantDepotEntryGroup.getGroupId();
	}

	@Override
	protected ContentSet testGetSiteContentSetsPage_addContentSet(
			Long siteId, ContentSet contentSet)
		throws Exception {

		return _addContentSetFromDTO(contentSet, siteId);
	}

	private ContentSet _addContentSet(Long groupId, String title)
		throws Exception {

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				groupId, title, AssetListEntryTypeConstants.TYPE_MANUAL,
				ServiceContextTestUtil.getServiceContext(
					groupId, TestPropsValues.getUserId()));

		return _toClientDTO(assetListEntry);
	}

	private ContentSet _addContentSetFromDTO(
			ContentSet contentSet, Long groupId)
		throws Exception {

		AssetListEntry persistedAssetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				contentSet.getExternalReferenceCode(),
				TestPropsValues.getUserId(), groupId, contentSet.getTitle(),
				AssetListEntryTypeConstants.TYPE_MANUAL,
				ServiceContextTestUtil.getServiceContext(
					groupId, TestPropsValues.getUserId()));

		if ((contentSet.getItemType() != null) ||
			(contentSet.getItemSubtype() != null)) {

			persistedAssetListEntry.setAssetEntrySubtype(
				contentSet.getItemSubtype());
			persistedAssetListEntry.setAssetEntryType(contentSet.getItemType());

			persistedAssetListEntry =
				_assetListEntryLocalService.updateAssetListEntry(
					persistedAssetListEntry);
		}

		return _toClientDTO(persistedAssetListEntry);
	}

	private ContentSet _addContentSetWithTypeAndSubtype(
			String assetEntrySubtype, String assetEntryType, Long groupId,
			String title)
		throws Exception {

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				groupId, title, AssetListEntryTypeConstants.TYPE_MANUAL,
				ServiceContextTestUtil.getServiceContext(
					groupId, TestPropsValues.getUserId()));

		assetListEntry.setAssetEntrySubtype(assetEntrySubtype);
		assetListEntry.setAssetEntryType(assetEntryType);

		assetListEntry = _assetListEntryLocalService.updateAssetListEntry(
			assetListEntry);

		return _toClientDTO(assetListEntry);
	}

	private boolean _containsContentSetId(
		List<ContentSet> contentSets, long contentSetId) {

		for (ContentSet contentSet : contentSets) {
			Long actualContentSetId = contentSet.getContentSetId();

			if ((actualContentSetId != null) &&
				(actualContentSetId == contentSetId)) {

				return true;
			}
		}

		return false;
	}

	private void _testGetSiteContentSetsPageWithItemSubtype() throws Exception {
		Long siteId = testGroup.getGroupId();

		String itemSubtype1 = RandomTestUtil.randomString();
		String itemSubtype2 = RandomTestUtil.randomString();
		String itemType = RandomTestUtil.randomString();

		ContentSet contentSet1 = _addContentSetWithTypeAndSubtype(
			itemSubtype1, itemType, siteId, RandomTestUtil.randomString());
		ContentSet contentSet2 = _addContentSetWithTypeAndSubtype(
			itemSubtype2, itemType, siteId, RandomTestUtil.randomString());

		Page<ContentSet> page = contentSetResource.getSiteContentSetsPage(
			siteId, itemSubtype1, itemType, null, Pagination.of(1, 50));

		List<ContentSet> contentSets = (List<ContentSet>)page.getItems();

		Assert.assertTrue(
			_containsContentSetId(contentSets, contentSet1.getContentSetId()));
		Assert.assertFalse(
			_containsContentSetId(contentSets, contentSet2.getContentSetId()));
	}

	private void _testGetSiteContentSetsPageWithItemType() throws Exception {
		Long siteId = testGroup.getGroupId();

		String itemType1 = RandomTestUtil.randomString();
		String itemType2 = RandomTestUtil.randomString();

		ContentSet contentSet1 = _addContentSetWithTypeAndSubtype(
			null, itemType1, siteId, RandomTestUtil.randomString());
		ContentSet contentSet2 = _addContentSetWithTypeAndSubtype(
			null, itemType2, siteId, RandomTestUtil.randomString());

		Page<ContentSet> page = contentSetResource.getSiteContentSetsPage(
			siteId, null, itemType1, null, Pagination.of(1, 50));

		List<ContentSet> contentSets = (List<ContentSet>)page.getItems();

		Assert.assertTrue(
			_containsContentSetId(contentSets, contentSet1.getContentSetId()));
		Assert.assertFalse(
			_containsContentSetId(contentSets, contentSet2.getContentSetId()));
	}

	private void _testGetSiteContentSetsPageWithSearch() throws Exception {
		Long siteId = testGroup.getGroupId();

		String keyword = RandomTestUtil.randomString();

		ContentSet contentSet1 = _addContentSet(
			siteId, RandomTestUtil.randomString() + keyword);

		ContentSet contentSet2 = _addContentSet(
			siteId, RandomTestUtil.randomString());

		Page<ContentSet> page = contentSetResource.getSiteContentSetsPage(
			siteId, null, null, keyword, Pagination.of(1, 50));

		List<ContentSet> contentSets = (List<ContentSet>)page.getItems();

		Assert.assertTrue(
			_containsContentSetId(contentSets, contentSet1.getContentSetId()));
		Assert.assertFalse(
			_containsContentSetId(contentSets, contentSet2.getContentSetId()));
	}

	private ContentSet _toClientDTO(AssetListEntry assetListEntry) {
		ContentSet contentSet = new ContentSet();

		contentSet.setClassNameId(
			PortalUtil.getClassNameId(AssetListEntry.class));
		contentSet.setClassPK(assetListEntry.getAssetListEntryId());
		contentSet.setContentSetId(assetListEntry.getAssetListEntryId());
		contentSet.setDateCreated(assetListEntry.getCreateDate());
		contentSet.setDateModified(assetListEntry.getModifiedDate());
		contentSet.setExternalReferenceCode(
			assetListEntry.getExternalReferenceCode());
		contentSet.setItemSubtype(assetListEntry.getAssetEntrySubtype());
		contentSet.setItemType(assetListEntry.getAssetEntryType());
		contentSet.setTitle(assetListEntry.getTitle());

		return contentSet;
	}

	@Inject
	private AssetListEntryLocalService _assetListEntryLocalService;

}