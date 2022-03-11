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

package com.liferay.staging.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.exportimport.changeset.constants.ChangesetPortletKeys;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactoryUtil;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.UserIdStrategy;
import com.liferay.exportimport.kernel.service.StagingLocalServiceUtil;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.LayoutSetBranchConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetBranchLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.GroupUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.staging.configuration.StagingConfiguration;

import java.io.File;
import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Julio Camarero
 * @author Daniel Kocsis
 */
@RunWith(Arquillian.class)
@Sync(cleanTransaction = true)
public class StagingImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();
		_remoteLiveGroup = GroupTestUtil.addGroup();
		_remoteStagingGroup = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetRemoteLayout() throws Exception {
		enableRemoteStaging(false);

		Layout remoteStagingGroupLayout = LayoutTestUtil.addTypePortletLayout(
			_remoteStagingGroup);

		Map<String, String[]> parameters =
			ExportImportConfigurationParameterMapFactoryUtil.
				buildFullPublishParameterMap();

		StagingUtil.publishLayouts(
			TestPropsValues.getUserId(), _remoteStagingGroup.getGroupId(),
			_remoteLiveGroup.getGroupId(), false, parameters);

		Layout remoteLiveGroupLayout = StagingUtil.getRemoteLayout(
			TestPropsValues.getUserId(), remoteStagingGroupLayout.getGroupId(),
			remoteStagingGroupLayout.getPlid());

		Assert.assertNotNull(remoteLiveGroupLayout);

		Assert.assertEquals(
			remoteStagingGroupLayout.getUuid(),
			remoteLiveGroupLayout.getUuid());
		Assert.assertEquals(
			remoteStagingGroupLayout.getTitle(),
			remoteLiveGroupLayout.getTitle());
	}

	@Test
	public void testHasRemoteLayout() throws Exception {
		enableRemoteStaging(false);

		Layout remoteStagingGroupLayout = LayoutTestUtil.addTypePortletLayout(
			_remoteStagingGroup);

		Assert.assertFalse(
			StagingUtil.hasRemoteLayout(
				TestPropsValues.getUserId(),
				remoteStagingGroupLayout.getGroupId(),
				remoteStagingGroupLayout.getPlid()));

		Map<String, String[]> parameters =
			ExportImportConfigurationParameterMapFactoryUtil.
				buildFullPublishParameterMap();

		StagingUtil.publishLayouts(
			TestPropsValues.getUserId(), _remoteStagingGroup.getGroupId(),
			_remoteLiveGroup.getGroupId(), false, parameters);

		Assert.assertTrue(
			StagingUtil.hasRemoteLayout(
				TestPropsValues.getUserId(),
				remoteStagingGroupLayout.getGroupId(),
				remoteStagingGroupLayout.getPlid()));
	}

	@Test
	public void testInitialPublication() throws Exception {
		try {
			ConfigurationProviderUtil.saveCompanyConfiguration(
				StagingConfiguration.class, _group.getCompanyId(),
				HashMapDictionaryBuilder.<String, Object>put(
					"stagingDeleteTempLAROnSuccess", false
				).build());

			doTestInitialPublication();
		}
		finally {
			ConfigurationProviderUtil.deleteCompanyConfiguration(
				StagingConfiguration.class, _group.getCompanyId());
		}
	}

	@Test
	public void testLocalStaging() throws Exception {
		enableLocalStaging(false);
	}

	@Test
	public void testLocalStagingAssetCategories() throws Exception {
		enableLocalStagingWithContent(false, true, false);
	}

	@Test
	public void testLocalStagingJournal() throws Exception {
		enableLocalStagingWithContent(true, false, false);
	}

	@Test
	public void testLocalStagingUpdateLastPublishDate() throws Exception {
		enableLocalStagingWithContent(true, false, false);

		Group stagingGroup = _group.getStagingGroup();

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			_group.getGroupId(), false);

		Assert.assertNull(ExportImportDateUtil.getLastPublishDate(layoutSet));

		layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			stagingGroup.getGroupId(), false);

		Assert.assertNotNull(
			ExportImportDateUtil.getLastPublishDate(layoutSet));

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getStrictPortletSetup(
				_group.getCompanyId(), _group.getGroupId(),
				JournalPortletKeys.JOURNAL);

		Assert.assertNull(
			ExportImportDateUtil.getLastPublishDate(portletPreferences));

		portletPreferences =
			PortletPreferencesFactoryUtil.getStrictPortletSetup(
				stagingGroup.getCompanyId(), stagingGroup.getGroupId(),
				JournalPortletKeys.JOURNAL);

		Assert.assertNotNull(
			ExportImportDateUtil.getLastPublishDate(portletPreferences));
	}

	@Test
	public void testLocalStagingWithLayoutVersioning() throws Exception {
		enableLocalStaging(true);
	}

	@Test
	public void testLocalStagingWithLayoutVersioningAssetCategories()
		throws Exception {

		enableLocalStagingWithContent(false, true, true);
	}

	@Test
	public void testLocalStagingWithLayoutVersioningJournal() throws Exception {
		enableLocalStagingWithContent(true, false, true);
	}

	@Test
	public void testRemoteStaging() throws Exception {
		enableRemoteStaging(false);
	}

	@Test
	public void testRemoteStagingWithLayoutVersioning() throws Exception {
		enableRemoteStaging(true);
	}

	protected AssetCategory addAssetCategory(
			long groupId, String title, String description)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<>();
		Map<Locale, String> descriptionMap = new HashMap<>();

		for (Locale locale : _locales) {
			titleMap.put(locale, title.concat(LocaleUtil.toLanguageId(locale)));
			descriptionMap.put(
				locale, description.concat(LocaleUtil.toLanguageId(locale)));
		}

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				TestPropsValues.getUserId(), groupId, "TestVocabulary",
				titleMap, descriptionMap, null, serviceContext);

		return AssetCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(), groupId, 0, titleMap, descriptionMap,
			assetVocabulary.getVocabularyId(), new String[0], serviceContext);
	}

	protected void doTestInitialPublication() throws Exception {
		LayoutTestUtil.addTypePortletLayout(_group);
		LayoutTestUtil.addTypePortletLayout(_group, true);

		JournalTestUtil.addArticle(
			_group.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		enableLocalStaging(false);

		Assert.assertEquals(
			1,
			JournalArticleLocalServiceUtil.getArticlesCount(
				_group.getGroupId()));

		Map<String, String[]> parameterMap =
			ExportImportConfigurationParameterMapFactoryUtil.
				buildParameterMap();

		String userIdStrategyString = MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.USER_ID_STRATEGY);

		UserIdStrategy userIdStrategy =
			ExportImportHelperUtil.getUserIdStrategy(
				TestPropsValues.getUserId(), userIdStrategyString);

		String includePattern = String.valueOf(_group.getGroupId()) + "*.lar";

		List<String> larFileNames = new ArrayList<>();

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				Paths.get(SystemProperties.get(SystemProperties.TMP_DIR)),
				includePattern)) {

			directoryStream.forEach(path -> larFileNames.add(path.toString()));
		}

		larFileNames.sort(null);

		File larFile = new File(larFileNames.get(larFileNames.size() - 1));

		PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createImportPortletDataContext(
				_group.getCompanyId(), _group.getGroupId(), parameterMap,
				userIdStrategy, ZipReaderFactoryUtil.getZipReader(larFile));

		String journalPortletPath = ExportImportPathUtil.getPortletPath(
			portletDataContext, JournalPortletKeys.JOURNAL);

		String portletData = portletDataContext.getZipEntryAsString(
			StringBundler.concat(
				journalPortletPath, StringPool.SLASH, _group.getGroupId(),
				"/portlet-data.xml"));

		if (portletData == null) {
			String changesetPortletPath = ExportImportPathUtil.getPortletPath(
				portletDataContext, ChangesetPortletKeys.CHANGESET);

			portletData = portletDataContext.getZipEntryAsString(
				StringBundler.concat(
					changesetPortletPath, StringPool.SLASH, _group.getGroupId(),
					"/portlet-data.xml"));
		}

		Document document = SAXReaderUtil.read(portletData);

		portletDataContext.setImportDataRootElement(document.getRootElement());

		Element journalElement = portletDataContext.getImportDataGroupElement(
			JournalArticle.class);

		List<Element> journalStagedModelElements = journalElement.elements(
			"staged-model");

		Assert.assertEquals(
			journalStagedModelElements.toString(), 0,
			journalStagedModelElements.size());
	}

	protected void enableLocalStaging(boolean branching) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		Map<String, Serializable> attributes = serviceContext.getAttributes();

		attributes.putAll(
			ExportImportConfigurationParameterMapFactoryUtil.
				buildParameterMap());

		if (branching) {
			serviceContext.setSignedIn(true);
		}

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		enableLocalStaging(branching, serviceContext);

		ServiceContextThreadLocal.popServiceContext();

		if (!branching) {
			return;
		}

		UnicodeProperties typeSettingsUnicodeProperties =
			_group.getTypeSettingsProperties();

		Assert.assertTrue(
			GetterUtil.getBoolean(
				typeSettingsUnicodeProperties.getProperty("branchingPrivate")));
		Assert.assertTrue(
			GetterUtil.getBoolean(
				typeSettingsUnicodeProperties.getProperty("branchingPublic")));

		Group stagingGroup = _group.getStagingGroup();

		LayoutSetBranch layoutSetBranch =
			LayoutSetBranchLocalServiceUtil.fetchLayoutSetBranch(
				stagingGroup.getGroupId(), false,
				LayoutSetBranchConstants.MASTER_BRANCH_NAME);

		Assert.assertNotNull(layoutSetBranch);

		layoutSetBranch = LayoutSetBranchLocalServiceUtil.fetchLayoutSetBranch(
			stagingGroup.getGroupId(), true,
			LayoutSetBranchConstants.MASTER_BRANCH_NAME);

		Assert.assertNotNull(layoutSetBranch);
	}

	protected void enableLocalStaging(
			boolean branching, ServiceContext serviceContext)
		throws Exception {

		int initialLayoutsCount = LayoutLocalServiceUtil.getLayoutsCount(
			_group, false);

		StagingLocalServiceUtil.enableLocalStaging(
			TestPropsValues.getUserId(), _group, branching, branching,
			serviceContext);

		Group stagingGroup = _group.getStagingGroup();

		Assert.assertNotNull(stagingGroup);
		Assert.assertEquals(
			initialLayoutsCount,
			LayoutLocalServiceUtil.getLayoutsCount(stagingGroup, false));
	}

	protected void enableLocalStagingWithContent(
			boolean stageJournal, boolean stageAssetCategories,
			boolean branching)
		throws Exception {

		// Layouts

		LayoutTestUtil.addTypePortletLayout(_group);
		LayoutTestUtil.addTypePortletLayout(_group);

		// Create content

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), "Title", "content");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAttribute(
			StagingUtil.getStagedPortletId(JournalPortletKeys.JOURNAL),
			stageJournal);

		Map<String, Serializable> attributes = serviceContext.getAttributes();

		List<String> portletIds = new ArrayList<>();

		portletIds.add(JournalPortletKeys.JOURNAL);

		Map<String, String[]> parameters =
			ExportImportConfigurationParameterMapFactoryUtil.buildParameterMap(
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR_OVERWRITE, false,
				false, false, false, false, false, false, false, stageJournal,
				false, portletIds, stageJournal, false, portletIds, false,
				portletIds, ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE,
				false, true, UserIdStrategy.CURRENT_USER_ID);

		attributes.putAll(parameters);

		enableLocalStaging(branching, serviceContext);

		Group stagingGroup = _group.getStagingGroup();

		// Update content in staging

		JournalArticle stagingJournalArticle =
			JournalArticleLocalServiceUtil.getArticleByUrlTitle(
				stagingGroup.getGroupId(), journalArticle.getUrlTitle());

		stagingJournalArticle = JournalTestUtil.updateArticle(
			stagingJournalArticle, "Title2",
			stagingJournalArticle.getContent());

		// Publish to live

		StagingUtil.publishLayouts(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			_group.getGroupId(), false, parameters);

		// Retrieve content from live after publishing

		journalArticle = JournalArticleLocalServiceUtil.getArticle(
			_group.getGroupId(), journalArticle.getArticleId());

		if (stageJournal) {
			for (Locale locale : _locales) {
				Assert.assertEquals(
					journalArticle.getTitle(locale),
					stagingJournalArticle.getTitle(locale));
			}
		}
		else {
			for (Locale locale : _locales) {
				Assert.assertNotEquals(
					journalArticle.getTitle(locale),
					stagingJournalArticle.getTitle(locale));
			}
		}
	}

	protected void enableRemoteStaging(boolean branching) throws Exception {
		_setPortalProperty(
			"TUNNELING_SERVLET_SHARED_SECRET",
			"F0E1D2C3B4A5968778695A4B3C2D1E0F");

		_setPortalProperty("TUNNELING_SERVLET_SHARED_SECRET_HEX", true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_remoteStagingGroup.getGroupId());

		Map<String, Serializable> attributes = serviceContext.getAttributes();

		attributes.putAll(
			ExportImportConfigurationParameterMapFactoryUtil.
				buildParameterMap());

		if (branching) {
			serviceContext.setSignedIn(true);
		}

		UserTestUtil.setUser(TestPropsValues.getUser());

		StagingLocalServiceUtil.enableRemoteStaging(
			TestPropsValues.getUserId(), _remoteStagingGroup, branching,
			branching, "localhost", PortalUtil.getPortalServerPort(false),
			PortalUtil.getPathContext(), false, _remoteLiveGroup.getGroupId(),
			serviceContext);

		GroupUtil.clearCache();

		if (!branching) {
			return;
		}

		UnicodeProperties typeSettingsUnicodeProperties =
			_remoteStagingGroup.getTypeSettingsProperties();

		Assert.assertTrue(
			GetterUtil.getBoolean(
				typeSettingsUnicodeProperties.getProperty("branchingPrivate")));
		Assert.assertTrue(
			GetterUtil.getBoolean(
				typeSettingsUnicodeProperties.getProperty("branchingPublic")));

		LayoutSetBranch layoutSetBranch =
			LayoutSetBranchLocalServiceUtil.fetchLayoutSetBranch(
				_remoteStagingGroup.getGroupId(), false,
				LayoutSetBranchConstants.MASTER_BRANCH_NAME);

		Assert.assertNotNull(layoutSetBranch);

		layoutSetBranch = LayoutSetBranchLocalServiceUtil.fetchLayoutSetBranch(
			_remoteStagingGroup.getGroupId(), true,
			LayoutSetBranchConstants.MASTER_BRANCH_NAME);

		Assert.assertNotNull(layoutSetBranch);
	}

	protected AssetCategory updateAssetCategory(
			AssetCategory category, String name)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<>();

		for (Locale locale : _locales) {
			titleMap.put(locale, name.concat(LocaleUtil.toLanguageId(locale)));
		}

		return AssetCategoryLocalServiceUtil.updateCategory(
			TestPropsValues.getUserId(), category.getCategoryId(),
			category.getParentCategoryId(), titleMap,
			category.getDescriptionMap(), category.getVocabularyId(), null,
			ServiceContextTestUtil.getServiceContext());
	}

	private void _setPortalProperty(String propertyName, Object value)
		throws Exception {

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, propertyName);

		field.setAccessible(true);

		Field modifiersField = Field.class.getDeclaredField("modifiers");

		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		field.set(null, value);
	}

	private static final Locale[] _locales = {
		LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US
	};

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private Group _remoteLiveGroup;

	@DeleteAfterTestRun
	private Group _remoteStagingGroup;

}