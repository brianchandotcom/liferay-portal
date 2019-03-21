/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.web.experience.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryAssetEntryRelLocalServiceUtil;
import com.liferay.asset.list.service.AssetListEntryLocalServiceUtil;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.service.BookmarksEntryServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerTracker;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.headless.web.experience.dto.v1_0.BlogPosting;
import com.liferay.headless.web.experience.dto.v1_0.ContentListElement;
import com.liferay.headless.web.experience.dto.v1_0.Document;
import com.liferay.headless.web.experience.dto.v1_0.Folder;
import com.liferay.headless.web.experience.dto.v1_0.GenericContentListElement;
import com.liferay.headless.web.experience.dto.v1_0.KnowledgeBaseArticle;
import com.liferay.headless.web.experience.dto.v1_0.StructuredContent;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalServiceUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class ContentListElementResourceTest
	extends BaseContentListElementResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		Registry registry = RegistryUtil.getRegistry();

		_serviceReference = registry.getServiceReference(
			DDMFormDeserializerTracker.class);

		_ddmFormDeserializerTracker = registry.getService(_serviceReference);

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(JournalArticle.class), testGroup);

		_ddmStructure = ddmStructureTestHelper.addStructure(
			PortalUtil.getClassNameId(JournalArticle.class),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			_deserialize(_read("test-structured-content-structure.json")),
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		DDMTemplateTestUtil.addTemplate(
			testGroup.getGroupId(), _ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			TemplateConstants.LANG_TYPE_VM,
			_read("test-structured-content-template.xsl"), LocaleUtil.US);

		_assetListEntry = AssetListEntryLocalServiceUtil.addAssetListEntry(
			TestPropsValues.getUserId(), testGroup.getGroupId(),
			RandomTestUtil.randomString(),
			AssetListEntryTypeConstants.TYPE_MANUAL,
			ServiceContextTestUtil.getServiceContext(testGroup.getGroupId()));
	}

	@After
	@Override
	public void tearDown() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		_ddmFormDeserializerTracker = null;

		registry.ungetService(_serviceReference);

		super.tearDown();
	}

	@Test
	public void testGetContentListContentsPageOfTypeBlogPostingPage()
		throws Exception {

		Long contentSpaceId =
			testGetContentListContentsPage_getContentSpaceId();

		ContentListElement contentListElement1 = _addBlogPosting(
			contentSpaceId, _assetListEntry.getAssetListEntryId(),
			randomBlogPosting());
		ContentListElement contentListElement2 = _addBlogPosting(
			contentSpaceId, _assetListEntry.getAssetListEntryId(),
			randomBlogPosting());

		_addBlogPosting(contentSpaceId, null, randomBlogPosting());

		Page<ContentListElement> page = invokeGetContentListContentsPage(
			_assetListEntry.getAssetListEntryId(), Pagination.of(1, 3));

		Assert.assertEquals(2, page.getTotalCount());

		List<ContentListElement> contentListElements =
			(List<ContentListElement>)page.getItems();

		assertEqualsIgnoringOrder(
			Arrays.asList(contentListElement1, contentListElement2),
			contentListElements);

		assertValid(page);
		assertValid(contentListElements.get(0));
		assertValid(contentListElements.get(1));
	}

	@Test
	public void testGetContentListContentsPageOfTypeDocumentPage()
		throws Exception {

		Long contentSpaceId =
			testGetContentListContentsPage_getContentSpaceId();

		Document document1 = _addDocument(
			contentSpaceId, _assetListEntry.getAssetListEntryId(),
			randomDocument());
		Document document2 = _addDocument(
			contentSpaceId, _assetListEntry.getAssetListEntryId(),
			randomDocument());

		_addDocument(contentSpaceId, null, randomDocument());

		Page<ContentListElement> page = invokeGetContentListContentsPage(
			_assetListEntry.getAssetListEntryId(), Pagination.of(1, 3));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(document1, document2),
			(List<ContentListElement>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetContentListContentsPageOfTypeFolderPage()
		throws Exception {

		Long contentSpaceId =
			testGetContentListContentsPage_getContentSpaceId();

		ContentListElement contentListElement1 = _addFolder(
			contentSpaceId, _assetListEntry.getAssetListEntryId(),
			randomFolder());
		ContentListElement contentListElement2 = _addFolder(
			contentSpaceId, _assetListEntry.getAssetListEntryId(),
			randomFolder());

		_addFolder(contentSpaceId, null, randomFolder());

		Page<ContentListElement> page = invokeGetContentListContentsPage(
			_assetListEntry.getAssetListEntryId(), Pagination.of(1, 3));

		Assert.assertEquals(2, page.getTotalCount());

		List<ContentListElement> contentListElements =
			(List<ContentListElement>)page.getItems();

		assertEqualsIgnoringOrder(
			Arrays.asList(contentListElement1, contentListElement2),
			contentListElements);

		assertValid(page);
		assertValid(contentListElements.get(0));
		assertValid(contentListElements.get(1));
	}

	@Test
	public void testGetContentListContentsPageOfTypeKnowledgeBaseArticlePage()
		throws Exception {

		Long contentSpaceId =
			testGetContentListContentsPage_getContentSpaceId();

		ContentListElement contentListElement1 = _addKnowledgeBaseArticle(
			contentSpaceId, _assetListEntry.getAssetListEntryId(),
			randomKnowledgeBaseArticle());
		ContentListElement contentListElement2 = _addKnowledgeBaseArticle(
			contentSpaceId, _assetListEntry.getAssetListEntryId(),
			randomKnowledgeBaseArticle());

		_addKnowledgeBaseArticle(
			contentSpaceId, null, randomKnowledgeBaseArticle());

		Page<ContentListElement> page = invokeGetContentListContentsPage(
			_assetListEntry.getAssetListEntryId(), Pagination.of(1, 3));

		Assert.assertEquals(2, page.getTotalCount());

		List<ContentListElement> contentListElements =
			(List<ContentListElement>)page.getItems();

		assertEqualsIgnoringOrder(
			Arrays.asList(contentListElement1, contentListElement2),
			contentListElements);

		assertValid(page);
		assertValid(contentListElements.get(0));
		assertValid(contentListElements.get(1));
	}

	@Test
	public void testGetContentListContentsPageOfTypeStructuredContentPage()
		throws Exception {

		Long contentSpaceId =
			testGetContentListContentsPage_getContentSpaceId();

		ContentListElement contentListElement1 = _addStructuredContent(
			contentSpaceId, _assetListEntry.getAssetListEntryId(),
			randomStructuredContent());
		ContentListElement contentListElement2 = _addStructuredContent(
			contentSpaceId, _assetListEntry.getAssetListEntryId(),
			randomStructuredContent());

		_addStructuredContent(contentSpaceId, null, randomStructuredContent());

		Page<ContentListElement> page = invokeGetContentListContentsPage(
			_assetListEntry.getAssetListEntryId(), Pagination.of(1, 3));

		Assert.assertEquals(2, page.getTotalCount());

		List<ContentListElement> contentListElements =
			(List<ContentListElement>)page.getItems();

		assertEqualsIgnoringOrder(
			Arrays.asList(contentListElement1, contentListElement2),
			contentListElements);

		assertValid(page);
		assertValid(contentListElements.get(0));
		assertValid(contentListElements.get(1));
	}

	@Test
	public void testGetContentListContentsPageOfTypeStructuredContentPageWithPagination()
		throws Exception {

		Long contentSpaceId =
			testGetContentListContentsPage_getContentSpaceId();

		ContentListElement contentListElement1 = _addStructuredContent(
			contentSpaceId, _assetListEntry.getAssetListEntryId(),
			randomStructuredContent());
		ContentListElement contentListElement2 = _addStructuredContent(
			contentSpaceId, _assetListEntry.getAssetListEntryId(),
			randomStructuredContent());
		ContentListElement contentListElement3 = _addStructuredContent(
			contentSpaceId, _assetListEntry.getAssetListEntryId(),
			randomStructuredContent());

		Page<ContentListElement> page1 = invokeGetContentListContentsPage(
			_assetListEntry.getAssetListEntryId(), Pagination.of(1, 2));

		List<ContentListElement> contentListElements1 =
			(List<ContentListElement>)page1.getItems();

		Assert.assertEquals(
			contentListElements1.toString(), 2, contentListElements1.size());

		Page<ContentListElement> page2 = invokeGetContentListContentsPage(
			_assetListEntry.getAssetListEntryId(), Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<ContentListElement> contentListElements2 =
			(List<ContentListElement>)page2.getItems();

		Assert.assertEquals(
			contentListElements2.toString(), 1, contentListElements2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				contentListElement1, contentListElement2, contentListElement3),
			new ArrayList<ContentListElement>() {
				{
					addAll(contentListElements1);
					addAll(contentListElements2);
				}
			});

		assertValid(contentListElements1.get(0));
		assertValid(contentListElements1.get(1));
		assertValid(contentListElements2.get(0));
	}

	protected void assertValid(BlogPosting blogPosting) {
		boolean valid = false;

		if ((blogPosting.getAlternativeHeadline() != null) &&
			(blogPosting.getArticleBody() != null) &&
			(blogPosting.getCreator() != null) &&
			(blogPosting.getDateCreated() != null) &&
			(blogPosting.getDateModified() != null) &&
			(blogPosting.getDatePublished() != null) &&
			(blogPosting.getDescription() != null) &&
			(blogPosting.getEncodingFormat() != null) &&
			(blogPosting.getFriendlyUrlPath() != null) &&
			(blogPosting.getHeadline() != null) &&
			(blogPosting.getId() != null) &&
			(blogPosting.getNumberOfComments() != null) &&
			(blogPosting.getOrder() != null)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	@Override
	protected void assertValid(ContentListElement contentListElement) {
		String contentType = contentListElement.getContentType();

		if (contentType.equals("BlogPosting")) {
			assertValid((BlogPosting)contentListElement);
		}
		else if (contentType.equals("Document")) {
			assertValid((Document)contentListElement);
		}
		else if (contentType.equals("Folder")) {
			assertValid((Folder)contentListElement);
		}
		else if (contentType.equals("KnowledgeBaseArticle")) {
			assertValid((KnowledgeBaseArticle)contentListElement);
		}
		else if (contentType.equals("StructuredContent")) {
			assertValid((StructuredContent)contentListElement);
		}
		else {
			assertValid((GenericContentListElement)contentListElement);
		}
	}

	protected void assertValid(Document document) {
		boolean valid = false;

		if ((document.getAdaptedImages() != null) &&
			(document.getAggregateRating() != null) &&
			(document.getDateCreated() != null) &&
			(document.getDateModified() != null) &&
			(document.getTaxonomyCategories() != null) &&
			(document.getTaxonomyCategoryIds() != null) &&
			(document.getContentUrl() != null) &&
			(document.getContentType() != null) &&
			(document.getCreator() != null) &&
			(document.getDateCreated() != null) &&
			(document.getDateModified() != null) &&
			(document.getDescription() != null) &&
			(document.getEncodingFormat() != null) &&
			(document.getFileExtension() != null) &&
			(document.getFolderId() != null) && (document.getId() != null) &&
			(document.getKeywords() != null) && (document.getOrder() != null) &&
			(document.getSizeInBytes() != null) &&
			(document.getTitle() != null)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Folder folder) {
		boolean valid = false;

		if (Objects.equals(
				folder.getContentSpaceId(), testGroup.getGroupId()) &&
			(folder.getDateCreated() != null) &&
			(folder.getDateModified() != null) && (folder.getId() != null)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(
		GenericContentListElement genericContentListElement) {

		boolean valid = false;

		if (Objects.equals(
				genericContentListElement.getContentSpaceId(),
				testGroup.getGroupId()) &&
			(genericContentListElement.getContentType() != null) &&
			(genericContentListElement.getDateCreated() != null) &&
			(genericContentListElement.getDateModified() != null) &&
			(genericContentListElement.getDescription() != null) &&
			(genericContentListElement.getId() != null) &&
			(genericContentListElement.getTitle() != null)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(KnowledgeBaseArticle knowledgeBaseArticle) {
		boolean valid = false;

		if ((knowledgeBaseArticle.getArticleBody() != null) &&
			(knowledgeBaseArticle.getContentSpaceId() != null) &&
			(knowledgeBaseArticle.getCreator() != null) &&
			(knowledgeBaseArticle.getDateCreated() != null) &&
			(knowledgeBaseArticle.getDateModified() != null) &&
			(knowledgeBaseArticle.getDescription() != null) &&
			(knowledgeBaseArticle.getFriendlyUrlPath() != null) &&
			(knowledgeBaseArticle.getId() != null) &&
			(knowledgeBaseArticle.getDateModified() != null) &&
			(knowledgeBaseArticle.getNumberOfAttachments() != null) &&
			(knowledgeBaseArticle.getNumberOfKnowledgeBaseArticles() != null) &&
			(knowledgeBaseArticle.getTitle() != null) &&
			(knowledgeBaseArticle.getContentType() != null) &&
			(knowledgeBaseArticle.getOrder() != null)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(StructuredContent structuredContent) {
		boolean valid = false;

		if (Objects.equals(
				structuredContent.getContentSpaceId(),
				testGroup.getGroupId()) &&
			(structuredContent.getDateCreated() != null) &&
			(structuredContent.getDateModified() != null) &&
			(structuredContent.getId() != null)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(
		BlogPosting blogPosting1, BlogPosting blogPosting2) {

		if (Objects.equals(
				blogPosting1.getAlternativeHeadline(),
				blogPosting2.getAlternativeHeadline()) &&
			Objects.equals(
				blogPosting1.getArticleBody(), blogPosting2.getArticleBody()) &&
			Objects.equals(
				blogPosting1.getDescription(), blogPosting2.getDescription()) &&
			Objects.equals(
				blogPosting1.getHeadline(), blogPosting2.getHeadline())) {

			return true;
		}

		return false;
	}

	protected boolean equals(
		ContentListElement contentListElement1,
		ContentListElement contentListElement2) {

		if (Objects.equals(
				contentListElement1.getContentType(),
				contentListElement2.getContentType()) &&
			Objects.equals(
				contentListElement1.getOrder(),
				contentListElement2.getOrder()) &&
			equals(
				contentListElement1, contentListElement2,
				contentListElement1.getContentType())) {

			return true;
		}

		if (contentListElement1 == contentListElement2) {
			return true;
		}

		return false;
	}

	protected boolean equals(
		ContentListElement contentListElement1,
		ContentListElement contentListElement2, String type) {

		if (type.equals("BlogPosting")) {
			return equals(
				(BlogPosting)contentListElement1,
				(BlogPosting)contentListElement2);
		}
		else if (type.equals("Document")) {
			return equals(
				(Document)contentListElement1, (Document)contentListElement2);
		}
		else if (type.equals("Folder")) {
			return equals(
				(Folder)contentListElement1, (Folder)contentListElement2);
		}
		else if (type.equals("KnowledgeBaseArticle")) {
			return equals(
				(KnowledgeBaseArticle)contentListElement1,
				(KnowledgeBaseArticle)contentListElement2);
		}
		else if (type.equals("StructuredContent")) {
			return equals(
				(StructuredContent)contentListElement1,
				(StructuredContent)contentListElement2);
		}

		return equals(
			(GenericContentListElement)contentListElement1,
			(GenericContentListElement)contentListElement2);
	}

	protected boolean equals(Document document1, Document document2) {
		if (Objects.equals(
				document1.getDescription(), document2.getDescription()) &&
			Objects.equals(document1.getTitle(), document2.getTitle())) {

			return true;
		}

		return false;
	}

	protected boolean equals(Folder folder1, Folder folder2) {
		if (Objects.equals(
				folder1.getDescription(), folder2.getDescription()) &&
			Objects.equals(folder1.getName(), folder2.getName())) {

			return true;
		}

		return false;
	}

	protected boolean equals(
		GenericContentListElement genericContentListElement1,
		GenericContentListElement genericContentListElement2) {

		if (Objects.equals(
				genericContentListElement1.getDescription(),
				genericContentListElement2.getDescription()) &&
			Objects.equals(
				genericContentListElement1.getTitle(),
				genericContentListElement2.getTitle())) {

			return true;
		}

		return false;
	}

	protected boolean equals(
		KnowledgeBaseArticle knowledgeBaseArticle1,
		KnowledgeBaseArticle knowledgeBaseArticle2) {

		if (Objects.equals(
				knowledgeBaseArticle1.getArticleBody(),
				knowledgeBaseArticle2.getArticleBody()) &&
			Objects.equals(
				knowledgeBaseArticle1.getContentSpaceId(),
				knowledgeBaseArticle2.getContentSpaceId()) &&
			Objects.equals(
				knowledgeBaseArticle1.getDescription(),
				knowledgeBaseArticle2.getDescription()) &&
			Objects.equals(
				knowledgeBaseArticle1.getParentKnowledgeBaseFolderId(),
				knowledgeBaseArticle2.getParentKnowledgeBaseFolderId()) &&
			Objects.equals(
				knowledgeBaseArticle1.getTitle(),
				knowledgeBaseArticle2.getTitle())) {

			return true;
		}

		return false;
	}

	protected boolean equals(
		StructuredContent structuredContent1,
		StructuredContent structuredContent2) {

		if (Objects.equals(
				structuredContent1.getContentSpaceId(),
				structuredContent2.getContentSpaceId()) &&
			Objects.equals(
				structuredContent1.getContentStructureId(),
				structuredContent2.getContentStructureId()) &&
			Objects.equals(
				structuredContent1.getDescription(),
				structuredContent2.getDescription()) &&
			Objects.equals(
				structuredContent1.getTitle(), structuredContent2.getTitle())) {

			return true;
		}

		return false;
	}

	protected BlogPosting randomBlogPosting() throws Exception {
		return new BlogPosting() {
			{
				alternativeHeadline = RandomTestUtil.randomString();
				articleBody = RandomTestUtil.randomString();
				contentSpaceId = testGroup.getGroupId();
				description = RandomTestUtil.randomString();
				headline = RandomTestUtil.randomString();
				order = RandomTestUtil.nextDouble();
			}
		};
	}

	protected ContentListElement randomContentListElement() {
		return new GenericContentListElement() {
			{
				contentSpaceId = testGroup.getGroupId();
				contentType = "Bookmark";
				description = RandomTestUtil.randomString();
				order = RandomTestUtil.randomDouble();
				title = RandomTestUtil.randomString();
			}
		};
	}

	protected Document randomDocument() {
		return new Document() {
			{
				description = RandomTestUtil.randomString();
				order = RandomTestUtil.randomDouble();
				title = RandomTestUtil.randomString();
			}
		};
	}

	protected Folder randomFolder() {
		return new Folder() {
			{
				contentSpaceId = testGroup.getGroupId();
				description = RandomTestUtil.randomString();
				name = RandomTestUtil.randomString();
				order = RandomTestUtil.randomDouble();
			}
		};
	}

	protected KnowledgeBaseArticle randomKnowledgeBaseArticle() {
		return new KnowledgeBaseArticle() {
			{
				articleBody = RandomTestUtil.randomString();
				contentSpaceId = testGroup.getGroupId();
				description = RandomTestUtil.randomString();
				order = RandomTestUtil.randomDouble();
				parentKnowledgeBaseFolderId = 0L;
				title = RandomTestUtil.randomString();
			}
		};
	}

	protected StructuredContent randomStructuredContent() {
		return new StructuredContent() {
			{
				contentSpaceId = testGroup.getGroupId();
				contentStructureId = _ddmStructure.getStructureId();
				description = RandomTestUtil.randomString();
				order = RandomTestUtil.randomDouble();
				title = RandomTestUtil.randomString();
			}
		};
	}

	@Override
	protected ContentListElement
			testGetContentListContentsPage_addContentListElement(
				Long contentListId, ContentListElement contentListElement)
		throws Exception {

		String contentType = contentListElement.getContentType();

		if (contentType.equals("Bookmark")) {
			return _addBookmark(
				testGroup.getGroupId(), contentListId,
				(GenericContentListElement)contentListElement);
		}
		else if (contentType.equals("BlogPosting")) {
			return _addBlogPosting(
				testGroup.getGroupId(), contentListId,
				(BlogPosting)contentListElement);
		}
		else if (contentType.equals("Document")) {
			return _addDocument(
				testGroup.getGroupId(), contentListId,
				(Document)contentListElement);
		}
		else if (contentType.equals("Folder")) {
			return _addFolder(
				testGroup.getGroupId(), contentListId,
				(Folder)contentListElement);
		}
		else if (contentType.equals("StructuredContent")) {
			return _addStructuredContent(
				testGroup.getGroupId(), contentListId,
				(StructuredContent)contentListElement);
		}
		else {
			throw new IllegalArgumentException(
				"ContentType not supported: " + contentType);
		}
	}

	@Override
	protected Long testGetContentListContentsPage_getContentListId()
		throws Exception {

		return _assetListEntry.getAssetListEntryId();
	}

	protected Long testGetContentListContentsPage_getContentSpaceId() {
		return testGroup.getGroupId();
	}

	protected BlogPosting toBlogPosting(
		BlogsEntry blogsEntry, AssetEntry assetEntry) {

		return new BlogPosting() {
			{
				alternativeHeadline = blogsEntry.getSubtitle();
				articleBody = blogsEntry.getContent();
				contentSpaceId = blogsEntry.getGroupId();
				contentType = _getContentType(assetEntry.getClassName());
				description = blogsEntry.getDescription();
				dateCreated = blogsEntry.getCreateDate();
				dateModified = blogsEntry.getModifiedDate();
				friendlyUrlPath = blogsEntry.getUrlTitle();
				headline = blogsEntry.getTitle();
				id = blogsEntry.getEntryId();
				order = assetEntry.getPriority();
			}
		};
	}

	protected Document toDocument(FileEntry fileEntry, AssetEntry assetEntry) {
		return new Document() {
			{
				contentType = _getContentType(assetEntry.getClassName());
				dateCreated = fileEntry.getCreateDate();
				dateModified = fileEntry.getModifiedDate();
				description = fileEntry.getDescription();
				encodingFormat = fileEntry.getMimeType();
				fileExtension = fileEntry.getExtension();
				folderId = fileEntry.getFolderId();
				id = fileEntry.getFileEntryId();
				order = assetEntry.getPriority();
				sizeInBytes = fileEntry.getSize();
				title = fileEntry.getTitle();
			}
		};
	}

	protected Folder toFolder(
		com.liferay.portal.kernel.repository.model.Folder folder,
		AssetEntry assetEntry) {

		return new Folder() {
			{
				contentSpaceId = folder.getGroupId();
				dateCreated = folder.getCreateDate();
				dateModified = folder.getModifiedDate();
				description = folder.getDescription();
				name = folder.getName();
				order = assetEntry.getPriority();
				contentType = _getContentType(assetEntry.getClassName());
			}
		};
	}

	protected GenericContentListElement toGenericContentListElement(
		AssetEntry assetEntry) {

		return new GenericContentListElement() {
			{
				contentSpaceId = assetEntry.getGroupId();
				contentType = _getContentType(assetEntry.getClassName());
				dateCreated = assetEntry.getCreateDate();
				dateModified = assetEntry.getModifiedDate();
				description = assetEntry.getDescription();
				id = assetEntry.getEntryId();
				order = assetEntry.getPriority();
				title = assetEntry.getTitle();
			}
		};
	}

	protected KnowledgeBaseArticle toKnowledgeBaseArticle(
		KBArticle kbArticle, AssetEntry assetEntry) {

		return new KnowledgeBaseArticle() {
			{
				articleBody = kbArticle.getContent();
				contentSpaceId = kbArticle.getGroupId();
				contentType = _getContentType(assetEntry.getClassName());
				dateCreated = kbArticle.getCreateDate();
				dateModified = kbArticle.getModifiedDate();
				description = kbArticle.getDescription();
				order = assetEntry.getPriority();
				title = kbArticle.getTitle();
			}
		};
	}

	protected StructuredContent toStructuredContent(
		JournalArticle journalArticle, AssetEntry assetEntry) {

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		return new StructuredContent() {
			{
				contentSpaceId = journalArticle.getGroupId();
				contentStructureId = ddmStructure.getStructureId();
				dateCreated = journalArticle.getCreateDate();
				dateModified = journalArticle.getModifiedDate();
				description = journalArticle.getDescription(LocaleUtil.US);
				id = journalArticle.getResourcePrimKey();
				order = assetEntry.getPriority();
				title = journalArticle.getTitle(LocaleUtil.US);
				contentType = _getContentType(assetEntry.getClassName());
			}
		};
	}

	private BlogPosting _addBlogPosting(
			Long contentSpaceId, Long assetListEntryId, BlogPosting blogPosting)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(contentSpaceId);

		Calendar displayCalendar = CalendarFactoryUtil.getCalendar(2012, 1, 1);

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), blogPosting.getHeadline(),
			blogPosting.getAlternativeHeadline(), blogPosting.getDescription(),
			blogPosting.getArticleBody(), displayCalendar.getTime(), true, true,
			new String[0], null, null, null, serviceContext);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			blogPosting.getContentSpaceId(), blogsEntry.getUuid());

		if (assetListEntryId != null) {
			AssetListEntry assetListEntry =
				AssetListEntryLocalServiceUtil.fetchAssetListEntry(
					assetListEntryId);

			SegmentsEntry segmentsEntry =
				SegmentsEntryLocalServiceUtil.getDefaultSegmentsEntry(
					contentSpaceId);

			AssetListEntryAssetEntryRelLocalServiceUtil.
				addAssetListEntryAssetEntryRel(
					assetListEntry.getAssetListEntryId(),
					assetEntry.getEntryId(), segmentsEntry.getSegmentsEntryId(),
					serviceContext);
		}

		return toBlogPosting(blogsEntry, assetEntry);
	}

	private GenericContentListElement _addBookmark(
			Long contentSpaceId, Long assetListEntryId,
			GenericContentListElement genericContentListElement)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(contentSpaceId);

		Number order = genericContentListElement.getOrder();

		serviceContext.setAssetPriority(order.doubleValue());

		BookmarksEntry bookmarksEntry = BookmarksEntryServiceUtil.addEntry(
			contentSpaceId, 0L, genericContentListElement.getTitle(),
			"http://www." + genericContentListElement.getTitle() + ".com",
			genericContentListElement.getDescription(), serviceContext);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			contentSpaceId, bookmarksEntry.getUuid());

		if (assetListEntryId != null) {
			AssetListEntry assetListEntry =
				AssetListEntryLocalServiceUtil.fetchAssetListEntry(
					assetListEntryId);

			SegmentsEntry segmentsEntry =
				SegmentsEntryLocalServiceUtil.getDefaultSegmentsEntry(
					contentSpaceId);

			AssetListEntryAssetEntryRelLocalServiceUtil.
				addAssetListEntryAssetEntryRel(
					assetListEntry.getAssetListEntryId(),
					assetEntry.getEntryId(), segmentsEntry.getSegmentsEntryId(),
					serviceContext);
		}

		return toGenericContentListElement(assetEntry);
	}

	private Document _addDocument(
			Long contentSpaceId, Long assetListEntryId, Document document)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(contentSpaceId);

		String content = RandomTestUtil.randomString(10);

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			contentSpaceId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, null,
			null, document.getTitle(), document.getDescription(),
			StringPool.BLANK, new ByteArrayInputStream(content.getBytes()), 0L,
			serviceContext);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			contentSpaceId, fileEntry.getUuid());

		if (assetListEntryId != null) {
			AssetListEntry assetListEntry =
				AssetListEntryLocalServiceUtil.fetchAssetListEntry(
					assetListEntryId);

			SegmentsEntry segmentsEntry =
				SegmentsEntryLocalServiceUtil.getDefaultSegmentsEntry(
					contentSpaceId);

			AssetListEntryAssetEntryRelLocalServiceUtil.
				addAssetListEntryAssetEntryRel(
					assetListEntry.getAssetListEntryId(),
					assetEntry.getEntryId(), segmentsEntry.getSegmentsEntryId(),
					serviceContext);
		}

		return toDocument(fileEntry, assetEntry);
	}

	private Folder _addFolder(
			Long contentSpaceId, Long assetListEntryId, Folder folder)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(contentSpaceId);

		com.liferay.portal.kernel.repository.model.Folder newFolder =
			DLAppServiceUtil.addFolder(
				contentSpaceId, 0L, folder.getName(), folder.getDescription(),
				serviceContext);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			contentSpaceId, newFolder.getUuid());

		if (assetListEntryId != null) {
			AssetListEntry assetListEntry =
				AssetListEntryLocalServiceUtil.fetchAssetListEntry(
					assetListEntryId);

			SegmentsEntry segmentsEntry =
				SegmentsEntryLocalServiceUtil.getDefaultSegmentsEntry(
					contentSpaceId);

			AssetListEntryAssetEntryRelLocalServiceUtil.
				addAssetListEntryAssetEntryRel(
					assetListEntry.getAssetListEntryId(),
					assetEntry.getEntryId(), segmentsEntry.getSegmentsEntryId(),
					serviceContext);
		}

		return toFolder(newFolder, assetEntry);
	}

	private KnowledgeBaseArticle _addKnowledgeBaseArticle(
			Long contentSpaceId, Long assetListEntryId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(contentSpaceId);

		Number order = knowledgeBaseArticle.getOrder();

		serviceContext.setAssetPriority(order.doubleValue());

		ClassName className = _classNameLocalService.fetchClassName(
			KBFolder.class.getName());

		KBArticle kbArticle = _kbArticleService.addKBArticle(
			KBPortletKeys.KNOWLEDGE_BASE_DISPLAY, className.getClassNameId(),
			0L, knowledgeBaseArticle.getTitle(),
			knowledgeBaseArticle.getFriendlyUrlPath(),
			knowledgeBaseArticle.getArticleBody(),
			knowledgeBaseArticle.getDescription(), null, null, null,
			serviceContext);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			contentSpaceId, kbArticle.getUuid());

		if (assetListEntryId != null) {
			AssetListEntry assetListEntry =
				AssetListEntryLocalServiceUtil.fetchAssetListEntry(
					assetListEntryId);

			SegmentsEntry segmentsEntry =
				SegmentsEntryLocalServiceUtil.getDefaultSegmentsEntry(
					contentSpaceId);

			AssetListEntryAssetEntryRelLocalServiceUtil.
				addAssetListEntryAssetEntryRel(
					assetListEntry.getAssetListEntryId(),
					assetEntry.getEntryId(), segmentsEntry.getSegmentsEntryId(),
					serviceContext);
		}

		return toKnowledgeBaseArticle(kbArticle, assetEntry);
	}

	private StructuredContent _addStructuredContent(
			Long contentSpaceId, Long assetListEntryId,
			StructuredContent structuredContent)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<>();

		titleMap.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(
			LocaleUtil.getDefault(), RandomTestUtil.randomString());

		String content = DDMStructureTestUtil.getSampleStructuredContent();

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			contentSpaceId, _ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(testGroup.getGroupId());

		Number order = structuredContent.getOrder();

		serviceContext.setAssetPriority(order.doubleValue());

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
			testGroup.getCompanyId());

		JournalArticle journalArticle =
			JournalArticleLocalServiceUtil.addArticle(
				defaultUserId, contentSpaceId,
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, titleMap,
				descriptionMap, content, _ddmStructure.getStructureKey(),
				ddmTemplate.getTemplateKey(), serviceContext);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			journalArticle.getGroupId(),
			journalArticle.getArticleResourceUuid());

		if (assetListEntryId != null) {
			AssetListEntry assetListEntry =
				AssetListEntryLocalServiceUtil.fetchAssetListEntry(
					assetListEntryId);

			SegmentsEntry segmentsEntry =
				SegmentsEntryLocalServiceUtil.getDefaultSegmentsEntry(
					contentSpaceId);

			AssetListEntryAssetEntryRelLocalServiceUtil.
				addAssetListEntryAssetEntryRel(
					assetListEntry.getAssetListEntryId(),
					assetEntry.getEntryId(), segmentsEntry.getSegmentsEntryId(),
					serviceContext);
		}

		return toStructuredContent(journalArticle, assetEntry);
	}

	private DDMForm _deserialize(String content) {
		DDMFormDeserializer ddmFormDeserializer =
			_ddmFormDeserializerTracker.getDDMFormDeserializer("json");

		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(content);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				ddmFormDeserializer.deserialize(builder.build());

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	private String _getContentType(String className) {
		if (className.equals(BlogsEntry.class.getName())) {
			return "BlogPosting";
		}
		else if (className.equals(BookmarksEntry.class.getName())) {
			return "Bookmark";
		}
		else if (className.equals(DLFileEntry.class.getName())) {
			return "Document";
		}
		else if (className.equals(DLFolder.class.getName())) {
			return "Folder";
		}
		else if (className.equals(KBArticle.class.getName())) {
			return "KnowledgeBaseArticle";
		}
		else if (className.equals(JournalArticle.class.getName())) {
			return "StructuredContent";
		}
		else {
			return className;
		}
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	private AssetListEntry _assetListEntry;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	private DDMFormDeserializerTracker _ddmFormDeserializerTracker;
	private DDMStructure _ddmStructure;

	@Inject
	private KBArticleService _kbArticleService;

	private ServiceReference<DDMFormDeserializerTracker> _serviceReference;

}