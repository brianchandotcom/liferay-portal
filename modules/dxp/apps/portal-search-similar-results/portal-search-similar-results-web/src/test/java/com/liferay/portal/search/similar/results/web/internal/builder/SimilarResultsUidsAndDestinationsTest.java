/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.similar.results.web.internal.builder;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.search.model.uid.UIDFactory;
import com.liferay.portal.search.similar.results.web.internal.contributor.asset.publisher.AssetPublisherSimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.internal.contributor.blogs.BlogsSimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.internal.contributor.document.library.DocumentLibrarySimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.internal.contributor.message.boards.MessageBoardsSimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.internal.contributor.url.parameters.ClassNameClassPKSimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.internal.contributor.url.parameters.ClassNameIdClassPKSimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.internal.contributor.url.parameters.ClassUUIDSimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.internal.contributor.url.parameters.EntryIdSimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.internal.contributor.url.parameters.UIDSimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.internal.contributor.wiki.WikiDisplaySimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.internal.portlet.search.Criteria;
import com.liferay.portal.search.similar.results.web.internal.portlet.search.CriteriaBuilderImpl;
import com.liferay.portal.search.similar.results.web.internal.portlet.search.CriteriaHelperImpl;
import com.liferay.portal.search.similar.results.web.internal.util.http.HttpHelper;
import com.liferay.portal.search.similar.results.web.internal.util.http.HttpHelperImpl;
import com.liferay.portal.search.similar.results.web.spi.contributor.SimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.CriteriaHelper;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.DestinationHelper;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiNodeLocalService;
import com.liferay.wiki.service.WikiPageLocalService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
public class SimilarResultsUidsAndDestinationsTest {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		_http = TestHttp.getInstance();

		_httpHelper = new HttpHelperImpl() {
			{
				setHttp(_http);
			}
		};

		_similarResultsContributorsRegistry =
			createSimilarResultsContributorsRegistry();
	}

	@Test
	public void testAssetPublisher() {
		String urlString = StringBundler.concat(
			"http://localhost:8080/web/guest/ap-page/-/asset_publisher",
			"/BNPTUvWUBXIr/content/id",
			"?_com_liferay_asset_publisher_web_portlet_AssetPublisherPortlet_",
			"INSTANCE_BNPTUvWUBXIr_assetEntryId=43152",
			"&_com_liferay_asset_publisher_web_portlet_AssetPublisherPortlet_",
			"INSTANCE_BNPTUvWUBXIr_redirect=",
			"http%3A%2F%2Flocalhost%3A8080%2Fweb%2Fguest%2F",
			"ap-page%3Fp_p_id=com_liferay_asset_publisher_web_portlet_",
			"AssetPublisherPortlet_",
			"INSTANCE_BNPTUvWUBXIr&p_p_lifecycle=0&p_p_state=normal",
			"&p_p_mode=view",
			"&_com_liferay_asset_publisher_web_portlet_AssetPublisherPortlet_",
			"INSTANCE_BNPTUvWUBXIr_cur=0&p_r_p_resetCur=false",
			"&_com_liferay_asset_publisher_web_portlet_AssetPublisherPortlet_",
			"INSTANCE_BNPTUvWUBXIr_assetEntryId=43152");

		String className1 = JournalArticle.class.getName();
		String className2 = BlogsEntry.class.getName();
		long classPK1 = 12345678;
		long classPK2 = 87654321;
		long entryId1 = 43152;
		long entryId2 = 25134;

		String expectedUID = className1 + "_PORTLET_12345678";

		String expectedDestination = StringBundler.concat(
			"http://localhost:8080/web/guest/ap-page/-/asset_publisher",
			"/BNPTUvWUBXIr/blog/id",
			"?_com_liferay_asset_publisher_web_portlet_AssetPublisherPortlet_",
			"INSTANCE_BNPTUvWUBXIr_assetEntryId=25134",
			"&_com_liferay_asset_publisher_web_portlet_AssetPublisherPortlet_",
			"INSTANCE_BNPTUvWUBXIr_redirect=http://localhost:8080/web/guest",
			"/ap-page?p_p_id=com_liferay_asset_publisher_web_portlet_",
			"AssetPublisherPortlet_",
			"INSTANCE_BNPTUvWUBXIr&p_p_lifecycle=0&p_p_state=normal",
			"&p_p_mode=view",
			"&_com_liferay_asset_publisher_web_portlet_AssetPublisherPortlet_",
			"INSTANCE_BNPTUvWUBXIr_cur=0&p_r_p_resetCur=false",
			"&_com_liferay_asset_publisher_web_portlet_AssetPublisherPortlet_",
			"INSTANCE_BNPTUvWUBXIr_assetEntryId=25134");

		AssetEntry assetEntry1 = getAssetEntry(className1, classPK1);

		Mockito.doReturn(
			entryId1
		).when(
			assetEntry1
		).getEntryId();

		Mockito.doReturn(
			assetEntry1
		).when(
			_assetEntryLocalService
		).fetchAssetEntry(
			entryId1
		);

		JournalArticle journalArticle = Mockito.mock(JournalArticle.class);

		Mockito.doReturn(
			classPK1
		).when(
			journalArticle
		).getId();

		AssetRenderer assetRenderer = Mockito.mock(AssetRenderer.class);

		Mockito.doReturn(
			journalArticle
		).when(
			assetRenderer
		).getAssetObject();

		Mockito.doReturn(
			assetRenderer
		).when(
			assetEntry1
		).getAssetRenderer();

		AssetEntry assetEntry2 = getAssetEntry(className2, classPK2);

		Mockito.doReturn(
			entryId2
		).when(
			assetEntry2
		).getEntryId();

		setUpDestinationAssetEntry(assetEntry2);

		setUpDestinationClassName(className2);

		setUpUIDFactory(expectedUID);

		assertSimilarResultsContributor(
			urlString, expectedUID, expectedDestination);
	}

	@Test
	public void testBlogs() {
		String urlString = StringBundler.concat(
			"http://localhost:8080/web/guest/blogs/-/blogs/blog-2",
			"?_com_liferay_blogs_web_portlet_BlogsPortlet",
			"_redirect=%2Fweb%2Fguest%2Fassetpublisher");

		String className = "com_liferay_blogs_web_portlet_BlogsPortlet";
		int classPK = 12345;
		long entryId = 98766;
		long groupId = RandomTestUtil.randomLong();

		String expectedUID = className + "_PORTLET_" + entryId;

		String urlTitle = "2-golb";
		String uuid = "abcde-efgh";

		String expectedDestination = StringBundler.concat(
			"http://localhost:8080/web/guest/blogs/-/blogs/", urlTitle,
			"?_com_liferay_blogs_web_portlet_BlogsPortlet",
			"_redirect=/web/guest/assetpublisher");

		AssetEntry assetEntry = getAssetEntry(className, classPK);

		AssetRenderer assetRenderer = Mockito.mock(AssetRenderer.class);

		Mockito.doReturn(
			urlTitle
		).when(
			assetRenderer
		).getUrlTitle();

		BlogsEntry blogsEntry = Mockito.mock(BlogsEntry.class);

		Mockito.doReturn(
			entryId
		).when(
			blogsEntry
		).getEntryId();

		Mockito.doReturn(
			uuid
		).when(
			blogsEntry
		).getUuid();

		setUpAssetEntryLocalServiceFetchGroupIdUUID(assetEntry, groupId, uuid);
		setUpBlogsEntryLocalService(blogsEntry);
		setUpInputGroupId(groupId);
		setUpDestinationAssetRenderer(assetRenderer);
		setUpUIDFactory(expectedUID);

		assertSimilarResultsContributor(
			urlString, expectedUID, expectedDestination);
	}

	@Test
	public void testDocumentLibraryDLFileEntry() {
		String urlString = StringBundler.concat(
			"http://localhost:8080/web/guest/document-and-media/-",
			"/document_library/oagkfEivnD1J/view_file/39730?",
			"_com_liferay_document_library_web_portlet_DLPortlet_INSTANCE");

		String className = DLFileEntry.class.getName();
		long fileEntryId = 33333;
		long groupId = RandomTestUtil.randomLong();

		String expectedUID = className + "_PORTLET_" + fileEntryId;

		String expectedDestination = StringBundler.concat(
			"http://localhost:8080/web/guest/document-and-media/-",
			"/document_library/oagkfEivnD1J/view_file/", fileEntryId,
			"?_com_liferay_document_library_web_portlet_DLPortlet_INSTANCE");

		DLFileEntry dlFileEntry = Mockito.mock(DLFileEntry.class);

		Mockito.doReturn(
			fileEntryId
		).when(
			dlFileEntry
		).getFileEntryId();

		AssetRenderer assetRenderer = Mockito.mock(AssetRenderer.class);

		Mockito.doReturn(
			dlFileEntry
		).when(
			assetRenderer
		).getAssetObject();

		AssetEntry assetEntry = getAssetEntry(className, 12345);

		setUpAssetEntryLocalServiceFetchUUID(assetEntry);

		setUpDestinationAssetRenderer(assetRenderer);
		setUpDestinationClassName(className);
		setUpDLFileEntryLocalService(dlFileEntry);
		setUpInputGroupId(groupId);

		assertSimilarResultsContributor(
			urlString, expectedUID, expectedDestination);
	}

	@Test
	public void testDocumentLibraryDLFolder() {
		String urlString = StringBundler.concat(
			"http://localhost:8080/web/guest/document-and-media/-",
			"/document_library/oagkfEivnD1J/view_file/39730?",
			"_com_liferay_document_library_web_portlet_DLPortlet_INSTANCE");

		String className = DLFolder.class.getName();
		long folderId = 33333;
		long groupId = RandomTestUtil.randomLong();

		String expectedUID = className + "_PORTLET_" + folderId;

		String expectedDestination = StringBundler.concat(
			"http://localhost:8080/web/guest/document-and-media/-",
			"/document_library/oagkfEivnD1J/view_file/", folderId,
			"?_com_liferay_document_library_web_portlet_DLPortlet_INSTANCE");

		DLFolder dlFolder = Mockito.mock(DLFolder.class);

		Mockito.doReturn(
			folderId
		).when(
			dlFolder
		).getFolderId();

		AssetRenderer assetRenderer = Mockito.mock(AssetRenderer.class);

		Mockito.doReturn(
			dlFolder
		).when(
			assetRenderer
		).getAssetObject();

		AssetEntry assetEntry = getAssetEntry(className, 12345);

		setUpAssetEntryLocalServiceFetchUUID(assetEntry);

		setUpDestinationAssetRenderer(assetRenderer);
		setUpDestinationClassName(className);
		setUpDLFolderLocalService(dlFolder);
		setUpInputGroupId(groupId);

		assertSimilarResultsContributor(
			urlString, expectedUID, expectedDestination);
	}

	@Test
	public void testMessageBoardsCategory() {
		String urlString = StringBundler.concat(
			"http://localhost:8080/web/guest/message-boards/-/message_boards",
			"/category/39748?",
			"_com_liferay_message_boards_web_portlet_MBPortlet_redirect=",
			"%2Fweb%2Fguest%2Fassetpublisher");

		String className = "com.liferay.message.boards.model.MBCategory";
		long categoryId = 22222;
		long groupId = RandomTestUtil.randomLong();

		String expectedUID = className + "_PORTLET_" + categoryId;

		String expectedDestination = StringBundler.concat(
			"http://localhost:8080/web/guest/message-boards/-/message_boards",
			"/category/", categoryId,
			"?_com_liferay_message_boards_web_portlet_MBPortlet_redirect=/web",
			"/guest/assetpublisher");

		Mockito.doReturn(
			MBCategory.class.getName()
		).when(
			destinationHelper
		).getClassName();

		Mockito.doReturn(
			categoryId
		).when(
			destinationHelper
		).getClassPK();

		setUpInputGroupId(groupId);
		setUpMBCategoryLocalService(categoryId);

		assertSimilarResultsContributor(
			urlString, expectedUID, expectedDestination);
	}

	@Test
	public void testMessageBoardsMessage() {
		String urlString = StringBundler.concat(
			"http://localhost:8080/web/guest/message-boards/-/message_boards",
			"/message/39753?",
			"_com_liferay_message_boards_web_portlet_MBPortlet_redirect=",
			"%2Fweb%2Fguest%2Fassetpublisher");

		String className = "com_liferay_message_web_portlet_MessagePortlet";
		long groupId = RandomTestUtil.randomLong();

		long messageId = 123456;

		String expectedDestination = StringBundler.concat(
			"http://localhost:8080/web/guest/message-boards/-/message_boards",
			"/message/", messageId,
			"?_com_liferay_message_boards_web_portlet_MBPortlet_redirect=/web",
			"/guest/assetpublisher");

		String expectedUID = className + "_PORTLET_" + 123456;

		Mockito.doReturn(
			MBMessage.class.getName()
		).when(
			destinationHelper
		).getClassName();

		Mockito.doReturn(
			messageId
		).when(
			destinationHelper
		).getClassPK();

		AssetEntry assetEntry = getAssetEntry(className, 12345);

		setUpAssetEntryLocalServiceFetchUUID(assetEntry);

		setUpInputGroupId(groupId);
		setUpMBMessageLocalService(messageId);

		assertSimilarResultsContributor(
			urlString, expectedUID, expectedDestination);
	}

	@Test
	public void testURLBlank() {
		Optional<SimilarResultsRoute> optional =
			_similarResultsContributorsRegistry.detectRoute(StringPool.BLANK);

		Assert.assertFalse(optional.isPresent());
	}

	@Test
	public void testURLClassNameClassPK() {
		String urlString =
			"http://localhost:8080/web/guest/blabal?" +
				"className=ABCDE&classPK=34567";

		String className = "EDCBA";
		int classPK = 76543;

		String expectedUID = "ABCDE_PORTLET_34567";

		String expectedDestination = StringBundler.concat(
			"http://localhost:8080/web/guest/blabal?className=", className,
			"&classPK=", classPK);

		AssetEntry assetEntry = getAssetEntry(className, classPK);

		setUpDestinationAssetEntry(assetEntry);

		assertSimilarResultsContributor(
			urlString, expectedUID, expectedDestination);
	}

	@Test
	public void testURLClassNameIDClassPK() {
		String urlString =
			"http://localhost:8080/web/guest/blabal?classNameId" +
				"=142857&classPK=34567";

		String className = "ClassNamePortlet";
		long classNameId1 = 142857;
		long classNameId2 = 758241;
		long classPK1 = 34567;
		long classPK2 = 76543;

		String expectedUID = "ClassNamePortlet_PORTLET_34567";

		String expectedDestination =
			"http://localhost:8080/web/guest/blabal?classNameId=" +
				classNameId2 + "&classPK=" + classPK2;

		AssetEntry assetEntry1 = getAssetEntry(className, classPK1);

		Mockito.doReturn(
			classNameId1
		).when(
			assetEntry1
		).getClassNameId();

		Mockito.when(
			_assetEntryLocalService.fetchEntry(classNameId1, classPK1)
		).thenReturn(
			assetEntry1
		);

		AssetEntry assetEntry2 = getAssetEntry(className, classPK2);

		Mockito.doReturn(
			classNameId2
		).when(
			assetEntry2
		).getClassNameId();

		setUpDestinationAssetEntry(assetEntry2);

		assertSimilarResultsContributor(
			urlString, expectedUID, expectedDestination);
	}

	@Test
	public void testURLClassUUID() {
		String uuid1 = "abcde-efgh";

		String urlString =
			"http://localhost:8080/web/guest/blabal?classUuid=" + uuid1;

		String className = "ABCDE";
		long classPK = 8885;
		long groupId = RandomTestUtil.randomLong();

		String uuid2 = "edcba-hgfe";

		String expectedUID = "ABCDE_PORTLET_8885";
		String expectedDestination =
			"http://localhost:8080/web/guest/blabal?classUuid=" + uuid2;

		AssetEntry assetEntry1 = getAssetEntry(className, classPK);

		AssetEntry assetEntry2 = Mockito.mock(AssetEntry.class);

		Mockito.doReturn(
			uuid2
		).when(
			assetEntry2
		).getClassUuid();

		setUpAssetEntryLocalServiceFetchGroupIdUUID(
			assetEntry1, groupId, uuid1);
		setUpDestinationAssetEntry(assetEntry2);
		setUpInputGroupId(groupId);

		assertSimilarResultsContributor(
			urlString, expectedUID, expectedDestination);
	}

	@Test
	public void testURLEntryId() {
		String urlString =
			"http://localhost:8080/web/guest/blabal?entryId=12345";

		String className = "ClassNamePortlet";
		long classPK = 9995;
		long entryId = 54321;

		String expectedUID = className + "_PORTLET_" + classPK;

		String expectedDestination =
			"http://localhost:8080/web/guest/blabal?entryId=" + entryId;

		AssetEntry assetEntry = getAssetEntry(className, classPK);

		Mockito.doReturn(
			entryId
		).when(
			assetEntry
		).getEntryId();

		Mockito.when(
			_assetEntryLocalService.fetchAssetEntry(Matchers.anyLong())
		).thenReturn(
			assetEntry
		);

		setUpDestinationAssetEntry(assetEntry);

		assertSimilarResultsContributor(
			urlString, expectedUID, expectedDestination);
	}

	@Test
	public void testURLUID() {
		String urlString = "http://localhost:8080/web/guest/blabal?uid=old";

		String uid = "new";

		Mockito.doReturn(
			uid
		).when(
			destinationHelper
		).getUID();

		String expectedUID = "old";

		String expectedDestination =
			"http://localhost:8080/web/guest/blabal?uid=new";

		assertSimilarResultsContributor(
			urlString, expectedUID, expectedDestination);
	}

	@Test
	public void testWikiDisplay() {
		String urlString = StringBundler.concat(
			"http://localhost:8080/web/guest/wiki",
			"?p_p_id=com_liferay_wiki_web_portlet_WikiDisplayPortlet_INSTANCE",
			"&_com_liferay_wiki_web_portlet_WikiDisplayPortlet_INSTANCE",
			"_nodeName=OLDNODE",
			"&_com_liferay_wiki_web_portlet_WikiDisplayPortlet_INSTANCE",
			"_title=OLDTITLE");

		String className = WikiPage.class.getName();
		long classPK = 12345;

		long groupId = RandomTestUtil.randomLong();

		String nodeName = "NEWNODE";
		String title = "NEWTITLE";

		String expectedUID = className + "_PORTLET_" + classPK;

		String expectedDestination = StringBundler.concat(
			"http://localhost:8080/web/guest/wiki",
			"?p_p_id=com_liferay_wiki_web_portlet_WikiDisplayPortlet_INSTANCE",
			"&_com_liferay_wiki_web_portlet_WikiDisplayPortlet_INSTANCE",
			"_nodeName=", nodeName,
			"&_com_liferay_wiki_web_portlet_WikiDisplayPortlet_INSTANCE",
			"_title=", title);

		setUpDestinationClassName(className);

		WikiNode wikiNode = Mockito.mock(WikiNode.class);

		Mockito.doReturn(
			nodeName
		).when(
			wikiNode
		).getName();

		WikiPage wikiPage = Mockito.mock(WikiPage.class);

		Mockito.doReturn(
			wikiNode
		).when(
			wikiPage
		).getNode();

		Mockito.doReturn(
			title
		).when(
			wikiPage
		).getTitle();

		AssetRenderer assetRenderer = Mockito.mock(AssetRenderer.class);

		Mockito.doReturn(
			wikiPage
		).when(
			assetRenderer
		).getAssetObject();

		AssetEntry assetEntry = getAssetEntry(className, classPK);

		setUpAssetEntryLocalServiceFetchUUID(assetEntry);

		setUpDestinationAssetRenderer(assetRenderer);
		setUpInputGroupId(groupId);
		setUpUIDFactory(expectedUID);
		setUpWikiNodeLocalService(wikiNode);
		setUpWikiPageLocalService(wikiPage);

		assertSimilarResultsContributor(
			urlString, expectedUID, expectedDestination);
	}

	protected void assertSimilarResultsContributor(
		String urlString, String expectedUID, String expectedDestination) {

		SimilarResultsRoute similarResultsRoute = detectRoute(urlString);

		Assert.assertEquals(expectedUID, resolveUID(similarResultsRoute));

		Assert.assertEquals(
			expectedDestination,
			writeDestination(urlString, similarResultsRoute));
	}

	protected SimilarResultsContributor
		createAssetPublisherSimilarResultsContributor() {

		return new AssetPublisherSimilarResultsContributor() {
			{
				setAssetEntryLocalService(_assetEntryLocalService);
				setHttpHelper(_httpHelper);
				setUIDFactory(_uidFactory);
			}
		};
	}

	protected SimilarResultsContributor createBlogsSimilarResultsContributor() {
		return new BlogsSimilarResultsContributor() {
			{
				setBlogsEntryLocalService(_blogsEntryLocalService);
				setHttpHelper(_httpHelper);
				setUIDFactory(_uidFactory);
			}
		};
	}

	protected SimilarResultsContributor
		createClassNameClassPKSimilarResultsContributor() {

		return new ClassNameClassPKSimilarResultsContributor() {
			{
				setHttpHelper(_httpHelper);
			}
		};
	}

	protected SimilarResultsContributor
		createClassNameIdClassPKSimilarResultsContributor() {

		return new ClassNameIdClassPKSimilarResultsContributor() {
			{
				setAssetEntryLocalService(_assetEntryLocalService);
				setHttpHelper(_httpHelper);
			}
		};
	}

	protected SimilarResultsContributor
		createClassUUIDSimilarResultsContributor() {

		return new ClassUUIDSimilarResultsContributor() {
			{
				setAssetEntryLocalService(_assetEntryLocalService);
				setHttpHelper(_httpHelper);
			}
		};
	}

	protected SimilarResultsContributor
		createDocumentLibrarySimilarResultsContributor() {

		return new DocumentLibrarySimilarResultsContributor() {
			{
				setAssetEntryLocalService(_assetEntryLocalService);
				setDLFileEntryLocalService(_dlFileEntryLocalService);
				setDLFolderLocalService(_dlFolderLocalService);
				setHttpHelper(_httpHelper);
			}
		};
	}

	protected SimilarResultsContributor
		createEntryIdSimilarResultsContributor() {

		return new EntryIdSimilarResultsContributor() {
			{
				setAssetEntryLocalService(_assetEntryLocalService);
				setHttpHelper(_httpHelper);
			}
		};
	}

	protected SimilarResultsContributor
		createMessageBoardsSimilarResultsContributor() {

		return new MessageBoardsSimilarResultsContributor() {
			{
				setAssetEntryLocalService(_assetEntryLocalService);
				setMbCategoryLocalService(_mbCategoryLocalService);
				setMbMessageLocalService(_mbMessageLocalService);
				setHttpHelper(_httpHelper);
			}
		};
	}

	protected SimilarResultsContributorsRegistry
		createSimilarResultsContributorsRegistry() {

		List<SimilarResultsContributor> list = Arrays.asList(
			createAssetPublisherSimilarResultsContributor(),
			createBlogsSimilarResultsContributor(),
			createClassNameClassPKSimilarResultsContributor(),
			createClassNameIdClassPKSimilarResultsContributor(),
			createClassUUIDSimilarResultsContributor(),
			createDocumentLibrarySimilarResultsContributor(),
			createEntryIdSimilarResultsContributor(),
			createMessageBoardsSimilarResultsContributor(),
			createUIDSimilarResultsContributor(),
			createWikiSimilarResultsContributor());

		SimilarResultsContributorsHolderImpl
			similarResultsContributorsHolderImpl =
				new SimilarResultsContributorsHolderImpl() {
					{
						list.forEach(this::addSimilarResultsContributor);
					}
				};

		return new SimilarResultsContributorsRegistryImpl() {
			{
				setHttp(_http);
				setSimilarResultsContributorsHolder(
					similarResultsContributorsHolderImpl);
			}
		};
	}

	protected SimilarResultsContributor createUIDSimilarResultsContributor() {
		return new UIDSimilarResultsContributor() {
			{
				setHttpHelper(_httpHelper);
			}
		};
	}

	protected SimilarResultsContributor createWikiSimilarResultsContributor() {
		return new WikiDisplaySimilarResultsContributor() {
			{
				setAssetEntryLocalService(_assetEntryLocalService);
				setHttpHelper(_httpHelper);
				setUIDFactory(_uidFactory);
				setWikiNodeLocalService(_wikiNodeLocalService);
				setWikiPageLocalService(_wikiPageLocalService);
			}
		};
	}

	protected SimilarResultsRoute detectRoute(String urlString) {
		Optional<SimilarResultsRoute> optional =
			_similarResultsContributorsRegistry.detectRoute(urlString);

		return optional.get();
	}

	protected AssetEntry getAssetEntry(String className, long classPK) {
		AssetEntry assetEntry = Mockito.mock(AssetEntry.class);

		Mockito.doReturn(
			className
		).when(
			assetEntry
		).getClassName();

		Mockito.doReturn(
			classPK
		).when(
			assetEntry
		).getClassPK();

		return assetEntry;
	}

	protected Criteria resolveCriteria(
		SimilarResultsRoute similarResultsRoute) {

		CriteriaBuilderImpl criteriaBuilderImpl = new CriteriaBuilderImpl();

		CriteriaHelper inputHelper = new CriteriaHelperImpl(
			_groupId, similarResultsRoute);

		SimilarResultsContributor similarResultsContributor =
			similarResultsRoute.getContributor();

		similarResultsContributor.resolveCriteria(
			criteriaBuilderImpl, inputHelper);

		Optional<Criteria> optional = criteriaBuilderImpl.build();

		return optional.get();
	}

	protected String resolveUID(SimilarResultsRoute similarResultsContributor) {
		Criteria similarResultsInput = resolveCriteria(
			similarResultsContributor);

		return similarResultsInput.getUID();
	}

	protected void setUpAssetEntryLocalServiceFetchGroupIdUUID(
		AssetEntry assetEntry, long groupId, String uuid) {

		Mockito.doReturn(
			assetEntry
		).when(
			_assetEntryLocalService
		).fetchEntry(
			Matchers.eq(groupId), Matchers.eq(uuid)
		);
	}

	protected void setUpAssetEntryLocalServiceFetchUUID(AssetEntry assetEntry) {
		Mockito.when(
			_assetEntryLocalService.fetchEntry(
				Matchers.anyLong(), Matchers.anyString())
		).thenReturn(
			assetEntry
		);
	}

	protected void setUpBlogsEntryLocalService(BlogsEntry blogsEntry) {
		Mockito.when(
			_blogsEntryLocalService.fetchEntry(
				Matchers.anyLong(), Matchers.anyString())
		).thenReturn(
			blogsEntry
		);
	}

	protected void setUpDestinationAssetEntry(AssetEntry assetEntry) {
		Mockito.doReturn(
			assetEntry
		).when(
			destinationHelper
		).getAssetEntry();
	}

	protected void setUpDestinationAssetRenderer(AssetRenderer assetRenderer) {
		Mockito.doReturn(
			assetRenderer
		).when(
			destinationHelper
		).getAssetRenderer();
	}

	protected void setUpDestinationClassName(String className) {
		Mockito.doReturn(
			className
		).when(
			destinationHelper
		).getClassName();
	}

	protected void setUpDLFileEntryLocalService(DLFileEntry dlFileEntry) {
		Mockito.when(
			_dlFileEntryLocalService.fetchDLFileEntry(Matchers.anyLong())
		).thenReturn(
			dlFileEntry
		);
	}

	protected void setUpDLFolderLocalService(DLFolder dlFolder) {
		Mockito.doReturn(
			dlFolder
		).when(
			_dlFolderLocalService
		).fetchDLFolder(
			Mockito.anyLong()
		);
	}

	protected void setUpInputGroupId(long groupId) {
		_groupId = groupId;
	}

	protected void setUpMBCategoryLocalService(long categoryId) {
		MBCategory mbCategory = Mockito.mock(MBCategory.class);

		Mockito.doReturn(
			categoryId
		).when(
			mbCategory
		).getCategoryId();

		Mockito.when(
			_mbCategoryLocalService.fetchMBCategory(Matchers.anyLong())
		).thenReturn(
			mbCategory
		);
	}

	protected void setUpMBMessageLocalService(long messageId) {
		MBMessage mbMessage = Mockito.mock(MBMessage.class);

		Mockito.doReturn(
			messageId
		).when(
			mbMessage
		).getRootMessageId();

		Mockito.when(
			_mbMessageLocalService.fetchMBMessage(Matchers.anyLong())
		).thenReturn(
			mbMessage
		);
	}

	protected void setUpUIDFactory(String uid) {
		Mockito.when(
			_uidFactory.getUID(Matchers.any(ClassedModel.class))
		).thenReturn(
			uid
		);
	}

	protected void setUpWikiNodeLocalService(WikiNode wikiNode) {
		Mockito.when(
			_wikiNodeLocalService.fetchNode(
				Matchers.anyLong(), Matchers.anyString())
		).thenReturn(
			wikiNode
		);
	}

	protected void setUpWikiPageLocalService(WikiPage wikiPage) {
		Mockito.when(
			_wikiPageLocalService.fetchPage(
				Matchers.anyLong(), Matchers.anyString(), Matchers.anyLong())
		).thenReturn(
			wikiPage
		);
	}

	protected String writeDestination(
		String urlString, SimilarResultsRoute similarResultsRoute) {

		DestinationBuilderImpl destinationBuilderImpl =
			new DestinationBuilderImpl(urlString, _http);

		SimilarResultsContributor similarResultsContributor =
			similarResultsRoute.getContributor();

		Mockito.doAnswer(
			invocationOnMock -> similarResultsRoute.getRouteParameter(
				invocationOnMock.getArgumentAt(0, String.class))
		).when(
			destinationHelper
		).getRouteParameter(
			Mockito.anyString()
		);

		similarResultsContributor.writeDestination(
			destinationBuilderImpl, destinationHelper);

		return destinationBuilderImpl.build();
	}

	@Mock
	protected DestinationHelper destinationHelper;

	@Mock
	private AssetEntryLocalService _assetEntryLocalService;

	@Mock
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Mock
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Mock
	private DLFolderLocalService _dlFolderLocalService;

	private long _groupId;
	private Http _http;
	private HttpHelper _httpHelper;

	@Mock
	private MBCategoryLocalService _mbCategoryLocalService;

	@Mock
	private MBMessageLocalService _mbMessageLocalService;

	private SimilarResultsContributorsRegistry
		_similarResultsContributorsRegistry;

	@Mock
	private UIDFactory _uidFactory;

	@Mock
	private WikiNodeLocalService _wikiNodeLocalService;

	@Mock
	private WikiPageLocalService _wikiPageLocalService;

}