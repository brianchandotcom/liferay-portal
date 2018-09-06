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

package com.liferay.journal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactoryUtil;
import com.liferay.exportimport.kernel.lar.ExportImportClassedModelUtil;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.UserIdStrategy;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleEvent;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleEventListenerRegistryUtil;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleListener;
import com.liferay.exportimport.kernel.service.StagingLocalServiceUtil;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.exportimport.test.util.lar.BasePortletExportImportTestCase;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portlet.documentlibrary.util.test.DLTestUtil;

import java.io.File;
import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Juan Fernández
 */
@RunWith(Arquillian.class)
@Sync(cleanTransaction = true)
public class JournalExportImportTest extends BasePortletExportImportTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	public String getNamespace() {
		return _journalPortletDataHandler.getNamespace();
	}

	@Override
	public String getPortletId() {
		return JournalPortletKeys.JOURNAL;
	}

	public void setPortalProperty(String propertyName, Object value)
		throws Exception {

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, propertyName);

		field.setAccessible(true);

		Field modifiersField = Field.class.getDeclaredField("modifiers");

		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		field.set(null, value);
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		ServiceTestUtil.setUser(TestPropsValues.getUser());
	}

	@Test
	public void testExportImportCompanyScopeStructuredJournalArticle()
		throws Exception {

		exportImportJournalArticle(true);
	}

	@Test
	public void testExportImportJournalArticleWithoutVersionHistory()
		throws Exception {

		JournalArticle article = (JournalArticle)addStagedModel(
			group.getGroupId());

		article = (JournalArticle)addVersion(article);

		int articlesCount = JournalArticleLocalServiceUtil.getArticlesCount(
			group.getGroupId(), article.getArticleId());

		Assert.assertEquals(2, articlesCount);

		Map<String, String[]> exportParameterMap = new HashMap<>();

		addParameter(exportParameterMap, "version-history", false);

		exportImportPortlet(
			JournalPortletKeys.JOURNAL, exportParameterMap,
			new HashMap<String, String[]>());

		JournalArticle importedArticle = (JournalArticle)getStagedModel(
			article.getUuid(), importedGroup.getGroupId());

		Assert.assertNotNull(importedArticle);

		articlesCount = JournalArticleLocalServiceUtil.getArticlesCount(
			importedGroup.getGroupId(), importedArticle.getArticleId());

		Assert.assertEquals(1, articlesCount);
	}

	@Test
	public void testExportImportStructuredJournalArticle() throws Exception {
		exportImportJournalArticle(false);
	}

	@Ignore
	@Test
	public void testReferenceSkipping() throws Exception {
		setPortalProperty("STAGING_DELETE_TEMP_LAR_ON_SUCCESS", false);

		LarFileSetterExportImportLifecycleListener
			larFileSetterExportImportLifecycleListener =
				new LarFileSetterExportImportLifecycleListener();

		ExportImportLifecycleEventListenerRegistryUtil.register(
			larFileSetterExportImportLifecycleListener);

		Layout targetLayout = LayoutTestUtil.addLayout(group);

		StagingLocalServiceUtil.enableLocalStaging(
			TestPropsValues.getUserId(), group, false, false,
			new ServiceContext());

		Group stagingGroup = group.getStagingGroup();

		JournalArticle journalArticle = (JournalArticle)addStagedModel(
			stagingGroup.getGroupId());

		DLFolder dlFolder = DLTestUtil.addDLFolder(stagingGroup.getGroupId());

		DLFileEntry dlFileEntry = DLTestUtil.addDLFileEntry(
			dlFolder.getFolderId());

		DLFileVersion fileVersion = dlFileEntry.getFileVersion();

		DLFileEntryLocalServiceUtil.updateStatus(
			dlFileEntry.getUserId(), fileVersion.getFileVersionId(),
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(), new HashMap<>());

		String content = journalArticle.getContent();

		String dlFileEntryUrl = String.join(
			StringPool.SLASH,
			new String[] {
				StringPool.BLANK, "documents",
				String.valueOf(dlFileEntry.getGroupId()),
				String.valueOf(dlFileEntry.getFolderId()),
				URLCodec.encodeURL(dlFileEntry.getTitle(), true)
			});

		String newContent =
			"<![CDATA[<img data-fileentryid=\"" + dlFileEntry.getFileEntryId() +
				"\" src=\"" + dlFileEntryUrl + "\" />]]>";

		journalArticle = JournalArticleLocalServiceUtil.updateContent(
			journalArticle.getGroupId(), journalArticle.getArticleId(),
			journalArticle.getVersion(),
			content.replaceAll("<\\!\\[CDATA\\[.+?\\]\\]>", newContent));

		Map<String, String[]> parameterMap =
			ExportImportConfigurationParameterMapFactoryUtil.buildParameterMap(
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR_OVERWRITE, true,
				false, true, false, false, false, false, true, true, true, null,
				true, true, null, true, null,
				ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE, true, true,
				UserIdStrategy.CURRENT_USER_ID);

		Layout sourceLayout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
			targetLayout.getUuid(), stagingGroup.getGroupId(), false);

		StagingUtil.publishPortlet(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			group.getGroupId(), sourceLayout.getPlid(), targetLayout.getPlid(),
			getPortletId(), parameterMap);

		checkJournalArticleInLar(journalArticle);

		checkFileEntriesInLar(stagingGroup, false);

		// Update modifiedDate

		JournalArticleLocalServiceUtil.updateContent(
			journalArticle.getGroupId(), journalArticle.getArticleId(),
			journalArticle.getVersion(), journalArticle.getContent());

		Map<String, String[]> modifiedParameterMap = new HashMap<>(
			parameterMap);

		modifiedParameterMap.put(
			PortletDataHandlerControl.getNamespacedControlName(
				getNamespace(), "referenced-content-behavior"),
			new String[] {"include-if-modified"});

		StagingUtil.publishPortlet(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			group.getGroupId(), sourceLayout.getPlid(), targetLayout.getPlid(),
			getPortletId(), modifiedParameterMap);

		checkJournalArticleInLar(journalArticle);

		checkFileEntriesInLar(stagingGroup, true);

		// Update modifiedDate

		JournalArticleLocalServiceUtil.updateContent(
			journalArticle.getGroupId(), journalArticle.getArticleId(),
			journalArticle.getVersion(), journalArticle.getContent());

		StagingUtil.publishPortlet(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			group.getGroupId(), sourceLayout.getPlid(), targetLayout.getPlid(),
			getPortletId(), parameterMap);

		checkJournalArticleInLar(journalArticle);

		checkFileEntriesInLar(stagingGroup, false);

		ExportImportLifecycleEventListenerRegistryUtil.unregister(
			larFileSetterExportImportLifecycleListener);

		setPortalProperty("STAGING_DELETE_TEMP_LAR_ON_SUCCESS", true);
	}

	public class LarFileSetterExportImportLifecycleListener
		implements ExportImportLifecycleListener {

		@Override
		public boolean isParallel() {
			return false;
		}

		@Override
		public void onExportImportLifecycleEvent(
				ExportImportLifecycleEvent exportImportLifecycleEvent)
			throws Exception {

			if (exportImportLifecycleEvent.getCode() !=
					ExportImportLifecycleConstants.
						EVENT_PORTLET_EXPORT_SUCCEEDED) {

				return;
			}

			List<Serializable> attributes =
				exportImportLifecycleEvent.getAttributes();

			PortletDataContext portletDataContext =
				(PortletDataContext)attributes.get(0);

			ZipWriter zipWriter = portletDataContext.getZipWriter();

			larFilePath = zipWriter.getPath();
		}

	}

	@Override
	protected StagedModel addStagedModel(long groupId) throws Exception {
		return JournalTestUtil.addArticle(
			groupId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString());
	}

	@Override
	protected StagedModel addStagedModel(long groupId, Date createdDate)
		throws Exception {

		String title = RandomTestUtil.randomString();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		serviceContext.setCommand(Constants.ADD);
		serviceContext.setCreateDate(createdDate);
		serviceContext.setLayoutFullURL("http://localhost");
		serviceContext.setModifiedDate(createdDate);

		return JournalTestUtil.addArticle(
			groupId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, title, title,
			RandomTestUtil.randomString(), LocaleUtil.getSiteDefault(), false,
			false, serviceContext);
	}

	@Override
	protected StagedModel addVersion(StagedModel stagedModel) throws Exception {
		JournalArticle article = (JournalArticle)stagedModel;

		return JournalTestUtil.updateArticle(
			article, RandomTestUtil.randomString());
	}

	protected void checkFileEntriesInLar(Group group, boolean missing)
		throws Exception {

		PortletDataContext portletDataContext = getPortletDataContext();

		List<DLFileEntry> dlFileEntries =
			DLFileEntryLocalServiceUtil.getGroupFileEntries(
				group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			FileEntry fileEntry =
				(FileEntry)portletDataContext.getZipEntryAsObject(
					ExportImportPathUtil.getModelPath(dlFileEntry));

			if (missing) {

				// Dependency is not exported, but added as a missing reference

				Assert.assertNull(fileEntry);

				Document document = SAXReaderUtil.read(
					portletDataContext.getZipEntryAsInputStream(
						"manifest.xml"));

				Element rootElement = document.getRootElement();

				List<Element> missingReferencesElements = rootElement.elements(
					"missing-references");

				Element missingReferencesElement =
					missingReferencesElements.get(0);

				List<Element> missingReferenceElements =
					missingReferencesElement.elements("missing-reference");

				Stream<Element> elementStream =
					missingReferenceElements.stream();

				Assert.assertEquals(
					1,
					elementStream.filter(
						element -> Objects.equals(
							element.attributeValue("class-name"),
							ExportImportClassedModelUtil.getClassName(
								dlFileEntry)) &&
							(Long.valueOf(element.attributeValue("class-pk")) ==
								dlFileEntry.getPrimaryKey())
					).count());
			}
			else {
				Assert.assertNotNull(fileEntry);
			}
		}
	}

	protected void checkJournalArticleInLar(JournalArticle journalArticle)
		throws Exception {

		PortletDataContext portletDataContext = getPortletDataContext();

		Assert.assertNotNull(
			portletDataContext.getZipEntryAsObject(
				ExportImportPathUtil.getModelPath(journalArticle)));
	}

	@Override
	protected void deleteFirstVersion(StagedModel stagedModel)
		throws Exception {

		JournalArticle article = (JournalArticle)stagedModel;

		List<JournalArticle> articles =
			JournalArticleLocalServiceUtil.getArticles(
				article.getGroupId(), article.getArticleId());

		JournalArticle firstArticle = null;

		for (JournalArticle journalArticle : articles) {
			if ((firstArticle == null) ||
				(journalArticle.getVersion() < firstArticle.getVersion())) {

				firstArticle = journalArticle;
			}
		}

		deleteStagedModel(firstArticle);
	}

	@Override
	protected void deleteLatestVersion(StagedModel stagedModel)
		throws Exception {

		JournalArticle article = (JournalArticle)stagedModel;

		JournalArticle latestArticle =
			JournalArticleLocalServiceUtil.getLatestArticle(
				article.getGroupId(), article.getArticleId());

		deleteStagedModel(latestArticle);
	}

	@Override
	protected void deleteStagedModel(StagedModel stagedModel) throws Exception {
		JournalArticleLocalServiceUtil.deleteArticle(
			(JournalArticle)stagedModel);
	}

	protected void exportImportJournalArticle(boolean companyScopeDependencies)
		throws Exception {

		JournalArticle article = null;
		DDMStructure ddmStructure = null;
		DDMTemplate ddmTemplate = null;

		long groupId = group.getGroupId();

		Company company = CompanyLocalServiceUtil.fetchCompany(
			group.getCompanyId());

		Group companyGroup = company.getGroup();

		if (companyScopeDependencies) {
			groupId = companyGroup.getGroupId();
		}

		ddmStructure = DDMStructureTestUtil.addStructure(
			groupId, JournalArticle.class.getName());

		ddmTemplate = DDMTemplateTestUtil.addTemplate(
			groupId, ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class));

		String content = DDMStructureTestUtil.getSampleStructuredContent();

		article = JournalTestUtil.addArticleWithXMLContent(
			group.getGroupId(), content, ddmStructure.getStructureKey(),
			ddmTemplate.getTemplateKey());

		exportImportPortlet(JournalPortletKeys.JOURNAL);

		int articlesCount = JournalArticleLocalServiceUtil.getArticlesCount(
			importedGroup.getGroupId());

		Assert.assertEquals(1, articlesCount);

		JournalArticle groupArticle =
			JournalArticleLocalServiceUtil.fetchJournalArticleByUuidAndGroupId(
				article.getUuid(), importedGroup.getGroupId());

		Assert.assertNotNull(groupArticle);

		groupId = importedGroup.getGroupId();

		if (companyScopeDependencies) {
			DDMStructure importedDDMStructure =
				DDMStructureLocalServiceUtil.fetchDDMStructureByUuidAndGroupId(
					ddmStructure.getUuid(), groupId);

			Assert.assertNull(importedDDMStructure);

			DDMTemplate importedDDMTemplate =
				DDMTemplateLocalServiceUtil.fetchDDMTemplateByUuidAndGroupId(
					ddmTemplate.getUuid(), groupId);

			Assert.assertNull(importedDDMTemplate);

			groupId = companyGroup.getGroupId();
		}

		DDMStructure dependentDDMStructure =
			DDMStructureLocalServiceUtil.fetchDDMStructureByUuidAndGroupId(
				ddmStructure.getUuid(), groupId);

		Assert.assertNotNull(dependentDDMStructure);

		DDMTemplate dependentDDMTemplate =
			DDMTemplateLocalServiceUtil.fetchDDMTemplateByUuidAndGroupId(
				ddmTemplate.getUuid(), groupId);

		Assert.assertNotNull(dependentDDMTemplate);

		Assert.assertEquals(
			article.getDDMStructureKey(),
			dependentDDMStructure.getStructureKey());
		Assert.assertEquals(
			article.getDDMTemplateKey(), dependentDDMTemplate.getTemplateKey());
		Assert.assertEquals(
			dependentDDMTemplate.getClassPK(),
			dependentDDMStructure.getStructureId());
	}

	@Override
	protected AssetEntry getAssetEntry(StagedModel stagedModel)
		throws PortalException {

		JournalArticle article = (JournalArticle)stagedModel;

		return AssetEntryLocalServiceUtil.getEntry(
			article.getGroupId(), article.getArticleResourceUuid());
	}

	protected Map<String, String[]> getBaseParameterMap(long groupId, long plid)
		throws Exception {

		Map<String, String[]> parameterMap = new HashMap<>();

		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT,
			new String[] {Boolean.FALSE.toString()});

		addParameter(parameterMap, "doAsGroupId", String.valueOf(groupId));
		addParameter(parameterMap, "feeds", true);
		addParameter(parameterMap, "groupId", String.valueOf(groupId));
		addParameter(
			parameterMap, "permissionsAssignedToRoles",
			Boolean.TRUE.toString());
		addParameter(parameterMap, "plid", String.valueOf(plid));
		addParameter(
			parameterMap, "portletResource", JournalPortletKeys.JOURNAL);
		addParameter(parameterMap, "referenced-content", true);
		addParameter(parameterMap, "structures", true);
		addParameter(parameterMap, "version-history", true);
		addParameter(parameterMap, "web-content", true);

		return parameterMap;
	}

	@Override
	protected Map<String, String[]> getExportParameterMap() throws Exception {
		Map<String, String[]> parameterMap = super.getExportParameterMap();

		MapUtil.merge(
			parameterMap,
			getBaseParameterMap(group.getGroupId(), layout.getPlid()));

		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE +
				JournalPortletKeys.JOURNAL,
			new String[] {Boolean.TRUE.toString()});

		return parameterMap;
	}

	@Override
	protected Map<String, String[]> getImportParameterMap() throws Exception {
		Map<String, String[]> parameterMap = super.getImportParameterMap();

		MapUtil.merge(
			parameterMap,
			getBaseParameterMap(
				importedGroup.getGroupId(), importedLayout.getPlid()));

		parameterMap.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[] {UserIdStrategy.CURRENT_USER_ID});

		return parameterMap;
	}

	protected PortletDataContext getPortletDataContext() throws Exception {
		Map<String, String[]> parameterMap =
			ExportImportConfigurationParameterMapFactoryUtil.
				buildParameterMap();

		String userIdStrategyString = MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.USER_ID_STRATEGY);

		UserIdStrategy userIdStrategy =
			ExportImportHelperUtil.getUserIdStrategy(
				TestPropsValues.getUserId(), userIdStrategyString);

		larFile = new File(larFilePath);

		return PortletDataContextFactoryUtil.createImportPortletDataContext(
			group.getCompanyId(), importedGroup.getGroupId(), parameterMap,
			userIdStrategy, ZipReaderFactoryUtil.getZipReader(larFile));
	}

	@Override
	protected StagedModel getStagedModel(String uuid, long groupId) {
		return
			JournalArticleLocalServiceUtil.fetchJournalArticleByUuidAndGroupId(
				uuid, groupId);
	}

	@Override
	protected boolean isVersioningEnabled() {
		return true;
	}

	@Override
	protected void testExportImportDisplayStyle(long groupId, String scopeType)
		throws Exception {
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		Assert.assertTrue(
			stagedModel.getCreateDate() + " " +
				importedStagedModel.getCreateDate(),
			DateUtil.equals(
				stagedModel.getCreateDate(),
				importedStagedModel.getCreateDate()));
		Assert.assertEquals(
			stagedModel.getUuid(), importedStagedModel.getUuid());

		JournalArticle article = (JournalArticle)stagedModel;
		JournalArticle importedArticle = (JournalArticle)importedStagedModel;

		Assert.assertEquals(
			(Double)article.getVersion(), (Double)importedArticle.getVersion());
		Assert.assertEquals(article.getTitle(), importedArticle.getTitle());
		Assert.assertEquals(
			article.getDescription(), importedArticle.getDescription());
		Assert.assertEquals(article.getContent(), importedArticle.getContent());
		Assert.assertTrue(
			String.valueOf(article.getDisplayDate()) + StringPool.SPACE +
				importedArticle.getDisplayDate(),
			DateUtil.equals(
				article.getDisplayDate(), importedArticle.getDisplayDate()));
		Assert.assertTrue(
			String.valueOf(article.getExpirationDate()) + StringPool.SPACE +
				importedArticle.getExpirationDate(),
			DateUtil.equals(
				article.getExpirationDate(),
				importedArticle.getExpirationDate()));
		Assert.assertTrue(
			String.valueOf(article.getReviewDate()) + StringPool.SPACE +
				importedArticle.getReviewDate(),
			DateUtil.equals(
				article.getReviewDate(), importedArticle.getReviewDate()));
		Assert.assertEquals(
			article.isSmallImage(), importedArticle.isSmallImage());
		Assert.assertEquals(
			article.getSmallImageURL(), importedArticle.getSmallImageURL());
		Assert.assertEquals(article.getStatus(), importedArticle.getStatus());
		Assert.assertTrue(
			String.valueOf(article.getStatusDate()) + StringPool.SPACE +
				importedArticle.getStatusDate(),
			DateUtil.equals(
				article.getStatusDate(), importedArticle.getStatusDate()));

		JournalArticleResource articleResource = article.getArticleResource();
		JournalArticleResource importedArticleArticleResource =
			importedArticle.getArticleResource();

		Assert.assertEquals(
			articleResource.getUuid(),
			importedArticleArticleResource.getUuid());
	}

	@Override
	protected void validateVersions() throws Exception {
		List<JournalArticle> articles =
			JournalArticleLocalServiceUtil.getArticles(group.getGroupId());

		for (JournalArticle article : articles) {
			JournalArticle importedArticle = (JournalArticle)getStagedModel(
				article.getUuid(), importedGroup.getGroupId());

			Assert.assertNotNull(importedArticle);

			validateImportedStagedModel(article, importedArticle);
		}
	}

	protected String larFilePath;

	@Inject(filter = "javax.portlet.name=" + JournalPortletKeys.JOURNAL)
	private PortletDataHandler _journalPortletDataHandler;

}