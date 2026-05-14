/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.headless.delivery.client.dto.v1_0.AssetEntry;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class AssetEntryResourceTest extends BaseAssetEntryResourceTestCase {

	@Test
	public void testGetAssetEntriesPageWithClassNameIdFilter()
		throws Exception {

		Long groupId = testGroup.getGroupId();

		BlogsEntry blogsEntry = _addBlogsEntry(groupId);
		JournalArticle journalArticle = JournalTestUtil.addArticle(groupId, 0);

		String filter =
			"classNameId eq " +
				PortalUtil.getClassNameId(BlogsEntry.class.getName());

		Page<AssetEntry> page = assetEntryResource.getAssetEntriesPage(
			new Long[] {groupId}, null, null, filter, Pagination.of(1, 20),
			null);

		List<AssetEntry> assetEntries = (List<AssetEntry>)page.getItems();

		Assert.assertTrue(
			_containsClassPK(assetEntries, blogsEntry.getEntryId()));
		Assert.assertFalse(
			_containsClassPK(
				assetEntries, journalArticle.getResourcePrimKey()));
	}

	@Test
	public void testGetAssetEntriesPageWithClassTypeIdFilter()
		throws Exception {

		Long groupId = testGroup.getGroupId();

		JournalArticle journalArticle1 = JournalTestUtil.addArticle(groupId, 0);
		JournalArticle journalArticle2 = JournalTestUtil.addArticle(groupId, 0);

		DDMStructure ddmStructure1 = journalArticle1.getDDMStructure();
		DDMStructure ddmStructure2 = journalArticle2.getDDMStructure();

		Assert.assertNotEquals(ddmStructure1, ddmStructure2);

		String filter = "classTypeId eq " + ddmStructure1.getStructureId();

		Page<AssetEntry> page = assetEntryResource.getAssetEntriesPage(
			new Long[] {groupId}, null, null, filter, Pagination.of(1, 20),
			null);

		List<AssetEntry> assetEntries = (List<AssetEntry>)page.getItems();

		Assert.assertTrue(
			_containsClassPK(
				assetEntries, journalArticle1.getResourcePrimKey()));
		Assert.assertFalse(
			_containsClassPK(
				assetEntries, journalArticle2.getResourcePrimKey()));
	}

	@Test
	public void testGetAssetEntriesPageWithMultipleClassNameIdsFilter()
		throws Exception {

		Long groupId = testGroup.getGroupId();

		BlogsEntry blogsEntry = _addBlogsEntry(groupId);
		JournalArticle journalArticle = JournalTestUtil.addArticle(groupId, 0);

		String filter = StringBundler.concat(
			"classNameId in (",
			PortalUtil.getClassNameId(BlogsEntry.class.getName()), ", ",
			PortalUtil.getClassNameId(JournalArticle.class.getName()), ")");

		Page<AssetEntry> page = assetEntryResource.getAssetEntriesPage(
			new Long[] {groupId}, null, null, filter, Pagination.of(1, 20),
			null);

		List<AssetEntry> assetEntries = (List<AssetEntry>)page.getItems();

		Assert.assertTrue(
			_containsClassPK(assetEntries, blogsEntry.getEntryId()));
		Assert.assertTrue(
			_containsClassPK(
				assetEntries, journalArticle.getResourcePrimKey()));
	}

	@Test
	public void testGetAssetEntriesPageWithMultipleGroupIds() throws Exception {
		Long groupId1 = testGroup.getGroupId();
		Long groupId2 = irrelevantGroup.getGroupId();

		BlogsEntry blogsEntry1 = _addBlogsEntry(groupId1);
		BlogsEntry blogsEntry2 = _addBlogsEntry(groupId2);

		Page<AssetEntry> page = assetEntryResource.getAssetEntriesPage(
			new Long[] {groupId1, groupId2}, null, null, null,
			Pagination.of(1, 50), null);

		List<AssetEntry> assetEntries = (List<AssetEntry>)page.getItems();

		Assert.assertTrue(
			_containsClassPK(assetEntries, blogsEntry1.getEntryId()));
		Assert.assertTrue(
			_containsClassPK(assetEntries, blogsEntry2.getEntryId()));
	}

	@Test
	public void testGetAssetEntriesPageWithStatusFilter() throws Exception {
		Long groupId = testGroup.getGroupId();

		BlogsEntry approvedBlogsEntry = _addBlogsEntry(groupId);

		BlogsEntry draftBlogsEntry = _addBlogsEntry(groupId);

		BlogsEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), draftBlogsEntry.getEntryId(),
			WorkflowConstants.STATUS_DRAFT,
			ServiceContextTestUtil.getServiceContext(
				groupId, TestPropsValues.getUserId()),
			null);

		Page<AssetEntry> page = assetEntryResource.getAssetEntriesPage(
			new Long[] {groupId}, null, null, "status eq 'approved'",
			Pagination.of(1, 50), null);

		List<AssetEntry> assetEntries = (List<AssetEntry>)page.getItems();

		Assert.assertTrue(
			_containsClassPK(assetEntries, approvedBlogsEntry.getEntryId()));
		Assert.assertFalse(
			_containsClassPK(assetEntries, draftBlogsEntry.getEntryId()));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"assetEntryId", "className", "classPK", "title"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"classNameId", "classTypeId", "status"};
	}

	@Override
	protected AssetEntry testGetAssetEntriesPage_addAssetEntry(
			AssetEntry assetEntry)
		throws Exception {

		BlogsEntry blogsEntry = _addBlogsEntry(testGroup.getGroupId());

		return _toAssetEntry(
			BlogsEntry.class.getName(), blogsEntry.getEntryId());
	}

	private BlogsEntry _addBlogsEntry(long groupId) throws Exception {
		return BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				groupId, TestPropsValues.getUserId()));
	}

	private boolean _containsClassPK(
		List<AssetEntry> assetEntries, long classPK) {

		for (AssetEntry assetEntry : assetEntries) {
			Long assetEntryClassPK = assetEntry.getClassPK();

			if ((assetEntryClassPK != null) && (assetEntryClassPK == classPK)) {
				return true;
			}
		}

		return false;
	}

	private AssetEntry _toAssetEntry(String className, long classPK) {
		com.liferay.asset.kernel.model.AssetEntry persistedAssetEntry =
			_assetEntryLocalService.fetchEntry(className, classPK);

		AssetEntry assetEntry = new AssetEntry();

		assetEntry.setAssetEntryId(persistedAssetEntry.getEntryId());
		assetEntry.setClassName(className);
		assetEntry.setClassPK(classPK);
		assetEntry.setTitle(
			persistedAssetEntry.getTitle(LocaleUtil.getDefault()));

		return assetEntry;
	}

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

}