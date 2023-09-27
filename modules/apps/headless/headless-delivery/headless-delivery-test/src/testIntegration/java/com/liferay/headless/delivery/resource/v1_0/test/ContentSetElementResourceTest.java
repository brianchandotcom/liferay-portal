/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalServiceUtil;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.headless.delivery.client.dto.v1_0.ContentSetElement;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.test.util.SearchTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class ContentSetElementResourceTest
	extends BaseContentSetElementResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_serviceContext = _getServiceContext();

		_assetListEntry = AssetListEntryLocalServiceUtil.addAssetListEntry(
			TestPropsValues.getUserId(), testGroup.getGroupId(),
			RandomTestUtil.randomString(),
			AssetListEntryTypeConstants.TYPE_DYNAMIC, _serviceContext);
		_depotAssetListEntry = AssetListEntryLocalServiceUtil.addAssetListEntry(
			TestPropsValues.getUserId(), testDepotEntry.getGroupId(),
			RandomTestUtil.randomString(),
			AssetListEntryTypeConstants.TYPE_DYNAMIC, _serviceContext);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	@Override
	protected ContentSetElement
			testGetAssetLibraryContentSetByKeyContentSetElementsPage_addContentSetElement(
				Long assetLibraryId, String key,
				ContentSetElement contentSetElement)
		throws Exception {

		return _toContentSetElement(
			JournalTestUtil.addArticle(_depotAssetListEntry.getGroupId(), 0));
	}

	@Override
	protected String
		testGetAssetLibraryContentSetByKeyContentSetElementsPage_getKey() {

		return _depotAssetListEntry.getAssetListEntryKey();
	}

	@Override
	protected ContentSetElement
			testGetAssetLibraryContentSetByUuidContentSetElementsPage_addContentSetElement(
				Long assetLibraryId, String uuid,
				ContentSetElement contentSetElement)
		throws Exception {

		return _toContentSetElement(
			JournalTestUtil.addArticle(_depotAssetListEntry.getGroupId(), 0));
	}

	@Override
	protected String
		testGetAssetLibraryContentSetByUuidContentSetElementsPage_getUuid() {

		return _depotAssetListEntry.getUuid();
	}

	@Override
	protected ContentSetElement
			testGetContentSetContentSetElementsPage_addContentSetElement(
				Long contentSetId, ContentSetElement contentSetElement)
		throws Exception {

		return _toContentSetElement(_addBlogsEntry());
	}

	@Override
	protected Long testGetContentSetContentSetElementsPage_getContentSetId() {
		return _assetListEntry.getAssetListEntryId();
	}

	@Override
	protected ContentSetElement
			testGetSiteContentSetByKeyContentSetElementsPage_addContentSetElement(
				Long siteId, String key, ContentSetElement contentSetElement)
		throws Exception {

		return _toContentSetElement(_addBlogsEntry());
	}

	@Override
	protected String testGetSiteContentSetByKeyContentSetElementsPage_getKey() {
		return _assetListEntry.getAssetListEntryKey();
	}

	@Override
	protected ContentSetElement
			testGetSiteContentSetByUuidContentSetElementsPage_addContentSetElement(
				Long siteId, String uuid, ContentSetElement contentSetElement)
		throws Exception {

		return _toContentSetElement(_addBlogsEntry());
	}

	@Override
	protected String
		testGetSiteContentSetByUuidContentSetElementsPage_getUuid() {

		return _assetListEntry.getUuid();
	}

	private BlogsEntry _addBlogsEntry() throws Exception {
		return BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), _serviceContext);
	}

	private ServiceContext _getServiceContext() throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAttribute(
			WorkflowConstants.CONTEXT_USER_ID, TestPropsValues.getUserId());
		serviceContext.setCompanyId(testGroup.getCompanyId());
		serviceContext.setScopeGroupId(testGroup.getGroupId());
		serviceContext.setUserId(TestPropsValues.getUserId());

		return serviceContext;
	}

	private ContentSetElement _toContentSetElement(BlogsEntry blogsEntry) {
		return new ContentSetElement() {
			{
				id = blogsEntry.getEntryId();
				title = blogsEntry.getTitle();
			}
		};
	}

	private ContentSetElement _toContentSetElement(
		JournalArticle journalArticle) {

		return new ContentSetElement() {
			{
				id = journalArticle.getId();
				title = journalArticle.getTitle();
			}
		};
	}

	private AssetListEntry _assetListEntry;
	private AssetListEntry _depotAssetListEntry;
	private ServiceContext _serviceContext;

}