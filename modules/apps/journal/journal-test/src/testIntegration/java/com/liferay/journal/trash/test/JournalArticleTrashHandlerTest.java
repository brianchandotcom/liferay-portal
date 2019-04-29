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

package com.liferay.journal.trash.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.journal.service.JournalFolderServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.WorkflowedModel;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;
import com.liferay.trash.TrashHelper;
import com.liferay.trash.test.util.BaseTrashHandlerTestCase;
import com.liferay.trash.test.util.DefaultWhenIsAssetable;
import com.liferay.trash.test.util.DefaultWhenIsIndexableBaseModel;
import com.liferay.trash.test.util.WhenCanBeDuplicatedInTrash;
import com.liferay.trash.test.util.WhenHasDraftStatus;
import com.liferay.trash.test.util.WhenHasGrandParent;
import com.liferay.trash.test.util.WhenHasMyBaseModel;
import com.liferay.trash.test.util.WhenHasRecentBaseModelCount;
import com.liferay.trash.test.util.WhenIsAssetable;
import com.liferay.trash.test.util.WhenIsAssetableBaseModel;
import com.liferay.trash.test.util.WhenIsAssetableParentModel;
import com.liferay.trash.test.util.WhenIsIndexableBaseModel;
import com.liferay.trash.test.util.WhenIsMoveableFromTrashBaseModel;
import com.liferay.trash.test.util.WhenIsRestorableBaseModel;
import com.liferay.trash.test.util.WhenIsRestorableParentBaseModelFromTrash;
import com.liferay.trash.test.util.WhenIsUpdatableBaseModel;
import com.liferay.trash.test.util.WhenIsVersionableBaseModel;

import java.io.InputStream;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class JournalArticleTrashHandlerTest
	extends BaseTrashHandlerTestCase
	implements WhenCanBeDuplicatedInTrash, WhenHasDraftStatus,
			   WhenHasGrandParent, WhenHasMyBaseModel,
			   WhenHasRecentBaseModelCount, WhenIsAssetableBaseModel,
			   WhenIsAssetableParentModel, WhenIsIndexableBaseModel,
			   WhenIsMoveableFromTrashBaseModel, WhenIsRestorableBaseModel,
			   WhenIsRestorableParentBaseModelFromTrash,
			   WhenIsUpdatableBaseModel, WhenIsVersionableBaseModel {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(TrashHelper.class.getName());

		_serviceTracker.open();
	}

	@AfterClass
	public static void tearDownClass() {
		_serviceTracker.close();
	}

	@Override
	public BaseModel<?> addDraftBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, ServiceContext serviceContext)
		throws Exception {

		JournalFolder folder = (JournalFolder)parentBaseModel;

		return JournalTestUtil.addArticleWithWorkflow(
			serviceContext.getScopeGroupId(), folder.getFolderId(),
			getSearchKeywords(), getSearchKeywords(), false);
	}

	@Override
	public BaseModel<?> expireBaseModel(
			BaseModel<?> baseModel, ServiceContext serviceContext)
		throws Exception {

		JournalArticle article = (JournalArticle)baseModel;

		return JournalArticleLocalServiceUtil.expireArticle(
			article.getUserId(), article.getGroupId(), article.getArticleId(),
			article.getVersion(), StringPool.BLANK, serviceContext);
	}

	@Override
	public AssetEntry fetchAssetEntry(ClassedModel classedModel)
		throws Exception {

		return _whenIsAssetable.fetchAssetEntry(classedModel);
	}

	@Override
	public String getBaseModelName(ClassedModel classedModel) {
		JournalArticle article = (JournalArticle)classedModel;

		return article.getArticleId();
	}

	@Override
	public List<? extends WorkflowedModel> getChildrenWorkflowedModels(
			BaseModel<?> parentBaseModel)
		throws Exception {

		JournalFolder folder = (JournalFolder)parentBaseModel;

		return JournalArticleLocalServiceUtil.getArticles(
			folder.getGroupId(), folder.getFolderId());
	}

	@Override
	public int getMineBaseModelsCount(long groupId, long userId)
		throws Exception {

		return JournalArticleServiceUtil.getGroupArticlesCount(
			groupId, userId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	@Override
	public String getParentBaseModelClassName() {
		Class<JournalFolder> journalFolderClass = JournalFolder.class;

		return journalFolderClass.getName();
	}

	@Override
	public int getRecentBaseModelsCount(long groupId) throws Exception {
		return JournalArticleServiceUtil.getGroupArticlesCount(
			groupId, 0, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	@Override
	public String getSearchKeywords() {
		return "Article";
	}

	@Override
	public boolean isAssetEntryVisible(ClassedModel classedModel, long classPK)
		throws Exception {

		return _whenIsAssetable.isAssetEntryVisible(classedModel, classPK);
	}

	@Override
	public BaseModel<?> moveBaseModelFromTrash(
			ClassedModel classedModel, Group group,
			ServiceContext serviceContext)
		throws Exception {

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		JournalArticleServiceUtil.moveArticleFromTrash(
			group.getGroupId(), getAssetClassPK(classedModel),
			(Long)parentBaseModel.getPrimaryKeyObj(), serviceContext);

		return parentBaseModel;
	}

	@Override
	public void moveParentBaseModelToTrash(long primaryKey) throws Exception {
		JournalFolderServiceUtil.moveFolderToTrash(primaryKey);
	}

	@Override
	public void restoreParentBaseModelFromTrash(long primaryKey)
		throws Exception {

		JournalFolderServiceUtil.restoreFolderFromTrash(primaryKey);
	}

	@Override
	public int searchBaseModelsCount(Class<?> clazz, long groupId)
		throws Exception {

		return _whenIsIndexableBaseModel.searchBaseModelsCount(clazz, groupId);
	}

	@Override
	public int searchTrashEntriesCount(
			String keywords, ServiceContext serviceContext)
		throws Exception {

		return _whenIsIndexableBaseModel.searchTrashEntriesCount(
			keywords, serviceContext);
	}

	@Before
	@Override
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_trashHelper = _serviceTracker.getService();

		super.setUp();
	}

	@Test
	public void testArticleImages() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		String definition = StringUtil.read(
			classLoader,
			"com/liferay/journal/dependencies" +
				"/test-ddm-structure-image-field.xml");

		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(
				definition);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_ddmFormDeserializer.deserialize(builder.build());

		DDMForm ddmForm = ddmFormDeserializerDeserializeResponse.getDDMForm();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			serviceContext.getScopeGroupId(), JournalArticle.class.getName(),
			ddmForm);

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			serviceContext.getScopeGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class));

		InputStream inputStream = classLoader.getResourceAsStream(
			"/com/liferay/journal/dependencies/liferay.png");

		FileEntry tempFileEntry = TempFileEntryUtil.addTempFileEntry(
			group.getGroupId(), TestPropsValues.getUserId(),
			JournalArticle.class.getName(), "liferay.png", inputStream,
			ContentTypes.IMAGE_PNG);

		String content = StringUtil.read(
			classLoader,
			"com/liferay/journal/dependencies/test-journal-content-image-" +
				"field.xml");

		Document document = SAXReaderUtil.read(content);

		Element dynamicContent = (Element)document.selectSingleNode(
			"//dynamic-content");

		JSONObject jsonObject = JSONUtil.put(
			"groupId", group.getGroupId()
		).put(
			"name", "liferay.png"
		).put(
			"tempFile", Boolean.TRUE.toString()
		).put(
			"title", "liferay.png"
		).put(
			"type", "journal"
		).put(
			"uuid", tempFileEntry.getUuid()
		);

		dynamicContent.setText(jsonObject.toString());

		baseModel = JournalTestUtil.addArticleWithXMLContent(
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, document.asXML(),
			ddmStructure.getStructureKey(), ddmTemplate.getTemplateKey(),
			serviceContext);

		JournalArticle article = (JournalArticle)baseModel;

		long folderId = article.getImagesFolderId();

		Assert.assertEquals(
			1,
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				group.getGroupId(), folderId));

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			0,
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				group.getGroupId(), folderId,
				WorkflowConstants.STATUS_APPROVED));
		Assert.assertEquals(
			1,
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				group.getGroupId(), folderId,
				WorkflowConstants.STATUS_IN_TRASH));

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.deleteTrashEntry(getTrashEntryClassPK(baseModel));

		Assert.assertEquals(
			0,
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				group.getGroupId(), folderId));
	}

	@Override
	public BaseModel<?> updateBaseModel(
			long primaryKey, ServiceContext serviceContext)
		throws Exception {

		JournalArticle article = JournalArticleLocalServiceUtil.getArticle(
			primaryKey);

		return JournalTestUtil.updateArticle(
			article, "Content: Enterprise. Open Source. For Life.",
			article.getContent(), false, true, serviceContext);
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, ServiceContext serviceContext)
		throws Exception {

		JournalFolder folder = (JournalFolder)parentBaseModel;

		return JournalTestUtil.addArticleWithWorkflow(
			serviceContext.getScopeGroupId(), folder.getFolderId(),
			getSearchKeywords(), getSearchKeywords(), true);
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			ServiceContext serviceContext)
		throws Exception {

		return JournalTestUtil.addArticleWithWorkflow(
			serviceContext.getScopeGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			getSearchKeywords(), getSearchKeywords(), true);
	}

	@Override
	protected void deleteParentBaseModel(
			BaseModel<?> parentBaseModel, boolean includeTrashedEntries)
		throws Exception {

		JournalFolder folder = (JournalFolder)parentBaseModel;

		JournalFolderServiceUtil.deleteFolder(folder.getFolderId(), false);
	}

	@Override
	protected Long getAssetClassPK(ClassedModel classedModel) {
		if (classedModel instanceof JournalArticle) {
			JournalArticle article = (JournalArticle)classedModel;

			try {
				JournalArticleResource journalArticleResource =
					JournalArticleResourceLocalServiceUtil.getArticleResource(
						article.getResourcePrimKey());

				return journalArticleResource.getResourcePrimKey();
			}
			catch (Exception e) {
				return super.getAssetClassPK(classedModel);
			}
		}
		else {
			return super.getAssetClassPK(classedModel);
		}
	}

	@Override
	protected BaseModel<?> getBaseModel(long primaryKey) throws Exception {
		return JournalArticleLocalServiceUtil.getArticle(primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return JournalArticle.class;
	}

	@Override
	protected int getNotInTrashBaseModelsCount(BaseModel<?> parentBaseModel)
		throws Exception {

		JournalFolder folder = (JournalFolder)parentBaseModel;

		return JournalArticleLocalServiceUtil.getNotInTrashArticlesCount(
			folder.getGroupId(), folder.getFolderId());
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, long parentBaseModelId, ServiceContext serviceContext)
		throws Exception {

		return JournalTestUtil.addFolder(
			group.getGroupId(), parentBaseModelId,
			RandomTestUtil.randomString(_FOLDER_NAME_MAX_LENGTH));
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		return getParentBaseModel(
			group, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			serviceContext);
	}

	@Override
	protected long getTrashEntryClassPK(ClassedModel classedModel) {
		JournalArticle article = (JournalArticle)classedModel;

		return article.getResourcePrimKey();
	}

	@Override
	protected String getUniqueTitle(BaseModel<?> baseModel) {
		JournalArticle article = (JournalArticle)baseModel;

		String articleId = article.getArticleId();

		return _trashHelper.getOriginalTitle(articleId);
	}

	@Override
	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
		JournalArticle article = JournalArticleLocalServiceUtil.getArticle(
			primaryKey);

		JournalArticleLocalServiceUtil.moveArticleToTrash(
			TestPropsValues.getUserId(), article);
	}

	private static final int _FOLDER_NAME_MAX_LENGTH = 100;

	private static ServiceTracker<TrashHelper, TrashHelper> _serviceTracker;

	@Inject(filter = "ddm.form.deserializer.type=xsd")
	private DDMFormDeserializer _ddmFormDeserializer;

	private TrashHelper _trashHelper;
	private final WhenIsAssetable _whenIsAssetable =
		new DefaultWhenIsAssetable();
	private final WhenIsIndexableBaseModel _whenIsIndexableBaseModel =
		new DefaultWhenIsIndexableBaseModel();

}