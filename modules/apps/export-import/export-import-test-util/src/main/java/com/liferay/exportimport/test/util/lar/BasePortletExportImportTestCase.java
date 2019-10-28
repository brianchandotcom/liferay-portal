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

package com.liferay.exportimport.test.util.lar;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLink;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetLinkLocalServiceUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMTemplate;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleManagerUtil;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.exportimport.kernel.service.ExportImportLocalServiceUtil;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.PortletDisplayTemplateManager;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMTemplateTestUtil;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Juan Fernández
 */
public abstract class BasePortletExportImportTestCase
	extends BaseExportImportTestCase {

	public String getNamespace() {
		return null;
	}

	public String getPortletId() throws Exception {
		return null;
	}

	@Test
	public void testExportImportAssetLinks() throws Exception {
		StagedModel stagedModel = addStagedModel(group.getGroupId());

		StagedModel relatedStagedModel1 = addStagedModel(group.getGroupId());
		StagedModel relatedStagedModel2 = addStagedModel(group.getGroupId());

		addAssetLink(stagedModel, relatedStagedModel1, 1);
		addAssetLink(stagedModel, relatedStagedModel2, 2);

		exportImportPortlet(getPortletId());

		StagedModel importedStagedModel = getStagedModel(
			getStagedModelUuid(stagedModel), importedGroup.getGroupId());

		Assert.assertNotNull(importedStagedModel);

		validateImportedLinks(stagedModel, importedStagedModel);
	}

	@Test
	public void testExportImportDeletions() throws Exception {
		StagedModel stagedModel = addStagedModel(group.getGroupId());

		if (stagedModel == null) {
			return;
		}

		String stagedModelUuid = getStagedModelUuid(stagedModel);

		exportImportPortlet(getPortletId());

		deleteStagedModel(stagedModel);

		exportImportPortlet(getPortletId());

		StagedModel importedStagedModel = getStagedModel(
			stagedModelUuid, importedGroup.getGroupId());

		Assert.assertNotNull(importedStagedModel);

		Map<String, String[]> exportParameterMap = new LinkedHashMap<>();

		exportParameterMap.put(
			PortletDataHandlerKeys.DELETIONS,
			new String[] {Boolean.TRUE.toString()});

		Map<String, String[]> importParameterMap = new LinkedHashMap<>();

		importParameterMap.put(
			PortletDataHandlerKeys.DELETIONS,
			new String[] {Boolean.TRUE.toString()});

		exportImportPortlet(
			getPortletId(), exportParameterMap, importParameterMap);

		try {
			importedStagedModel = getStagedModel(
				stagedModelUuid, importedGroup.getGroupId());

			Assert.assertNull(importedStagedModel);
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testExportImportDisplayStyleFromCurrentGroup()
		throws Exception {

		testExportImportDisplayStyle(group.getGroupId(), StringPool.BLANK);
	}

	@Test
	public void testExportImportDisplayStyleFromDifferentGroup()
		throws Exception {

		Group group2 = GroupTestUtil.addGroup();

		testExportImportDisplayStyle(group2.getGroupId(), StringPool.BLANK);
	}

	@Test
	public void testExportImportDisplayStyleFromGlobalScope() throws Exception {
		Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
			group.getCompanyId());

		testExportImportDisplayStyle(companyGroup.getGroupId(), "company");
	}

	@Test
	public void testExportImportDisplayStyleFromLayoutScope() throws Exception {
		testExportImportDisplayStyle(group.getGroupId(), "layout");
	}

	@Test
	public void testExportImportInvalidAvailableLocales() throws Exception {
		testExportImportAvailableLocales(
			Arrays.asList(LocaleUtil.US, LocaleUtil.SPAIN),
			Arrays.asList(LocaleUtil.US, LocaleUtil.GERMANY), true);
	}

	@Test
	public void testExportImportValidAvailableLocales() throws Exception {
		testExportImportAvailableLocales(
			Arrays.asList(LocaleUtil.US, LocaleUtil.SPAIN),
			Arrays.asList(LocaleUtil.US, LocaleUtil.SPAIN, LocaleUtil.GERMANY),
			false);
	}

	@Test
	public void testUpdateLastPublishDate() throws Exception {
		Date lastPublishDate = new Date(System.currentTimeMillis() - Time.HOUR);

		Date stagedModelCreationDate = new Date(
			lastPublishDate.getTime() + Time.MINUTE);

		StagedModel stagedModel = addStagedModel(
			group.getGroupId(), stagedModelCreationDate);

		if (stagedModel == null) {
			return;
		}

		LayoutTestUtil.addPortletToLayout(
			TestPropsValues.getUserId(), layout, getPortletId(), "column-1",
			new HashMap<>());

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getStrictPortletSetup(
				layout, getPortletId());

		portletPreferences.setValue(
			"last-publish-date", String.valueOf(lastPublishDate.getTime()));

		portletPreferences.store();

		portletPreferences =
			PortletPreferencesFactoryUtil.getStrictPortletSetup(
				layout, getPortletId());

		Date oldLastPublishDate = ExportImportDateUtil.getLastPublishDate(
			portletPreferences);

		publishPortlet(getPortletId());

		portletPreferences =
			PortletPreferencesFactoryUtil.getStrictPortletSetup(
				layout, getPortletId());

		Date newLastPublishDate = ExportImportDateUtil.getLastPublishDate(
			portletPreferences);

		Assert.assertTrue(newLastPublishDate.after(oldLastPublishDate));

		StagedModel importedStagedModel = getStagedModel(
			getStagedModelUuid(stagedModel), importedGroup.getGroupId());

		Assert.assertNotNull(importedStagedModel);
	}

	@Test
	public void testVersioning1() throws Exception {
		if (!isVersioningEnabled()) {
			return;
		}

		StagedModel stagedModel = addStagedModel(group.getGroupId());

		addVersion(stagedModel);

		exportImportPortlet(getPortletId());

		validateVersions();
	}

	@Test
	public void testVersioning2() throws Exception {
		if (!isVersioningEnabled()) {
			return;
		}

		StagedModel stagedModel = addStagedModel(group.getGroupId());

		Thread.sleep(4000);

		exportImportPortlet(getPortletId());

		validateVersions();

		addVersion(stagedModel);

		exportImportPortlet(getPortletId());

		validateVersions();
	}

	@Test
	public void testVersioningDeleteFirst() throws Exception {
		if (!isVersioningEnabled()) {
			return;
		}

		StagedModel stagedModel = addStagedModel(group.getGroupId());

		stagedModel = addVersion(stagedModel);

		exportImportPortlet(getPortletId());

		validateVersions();

		deleteFirstVersion(stagedModel);

		exportImportPortlet(getPortletId());

		validateVersions();
	}

	@Test
	public void testVersioningDeleteLatest() throws Exception {
		if (!isVersioningEnabled()) {
			return;
		}

		StagedModel stagedModel = addStagedModel(group.getGroupId());

		stagedModel = addVersion(stagedModel);

		exportImportPortlet(getPortletId());

		validateVersions();

		deleteLatestVersion(stagedModel);

		exportImportPortlet(getPortletId());

		validateVersions();
	}

	@Test
	public void testVersioningExportImportTwice() throws Exception {
		if (!isVersioningEnabled()) {
			return;
		}

		StagedModel stagedModel = addStagedModel(group.getGroupId());

		addVersion(stagedModel);

		exportImportPortlet(getPortletId());

		validateVersions();

		exportImportPortlet(getPortletId());

		validateVersions();
	}

	protected void addParameter(
		Map<String, String[]> parameterMap, String name, boolean value) {

		addParameter(parameterMap, getNamespace(), name, value);
	}

	protected StagedModel addVersion(StagedModel stagedModel) throws Exception {
		return null;
	}

	protected void deleteFirstVersion(StagedModel stagedModel)
		throws Exception {
	}

	protected void deleteLatestVersion(StagedModel stagedModel)
		throws Exception {
	}

	protected void exportImportPortlet(String portletId) throws Exception {
		exportImportPortlet(
			portletId, new LinkedHashMap<String, String[]>(),
			new LinkedHashMap<String, String[]>());
	}

	protected void exportImportPortlet(
			String portletId, Map<String, String[]> exportParameterMap,
			Map<String, String[]> importParameterMap)
		throws Exception {

		User user = TestPropsValues.getUser();

		MapUtil.merge(getExportParameterMap(), exportParameterMap);

		Map<String, Serializable> settingsMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
				buildExportPortletSettingsMap(
					user, layout.getPlid(), layout.getGroupId(), portletId,
					exportParameterMap, StringPool.BLANK);

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addDraftExportImportConfiguration(
					user.getUserId(),
					ExportImportConfigurationConstants.
						TYPE_PUBLISH_PORTLET_LOCAL,
					settingsMap);

		ExportImportThreadLocal.setPortletStagingInProcess(true);

		ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
			ExportImportLifecycleConstants.
				EVENT_PUBLICATION_PORTLET_LOCAL_STARTED,
			ExportImportLifecycleConstants.
				PROCESS_FLAG_PORTLET_STAGING_IN_PROCESS,
			String.valueOf(
				exportImportConfiguration.getExportImportConfigurationId()),
			exportImportConfiguration);

		try {
			larFile = ExportImportLocalServiceUtil.exportPortletInfoAsFile(
				exportImportConfiguration);

			importedLayout = LayoutTestUtil.addLayout(importedGroup);

			MapUtil.merge(getImportParameterMap(), importParameterMap);

			settingsMap =
				ExportImportConfigurationSettingsMapFactoryUtil.
					buildImportPortletSettingsMap(
						user, importedLayout.getPlid(),
						importedGroup.getGroupId(), portletId,
						importParameterMap);

			exportImportConfiguration =
				ExportImportConfigurationLocalServiceUtil.
					updateExportImportConfiguration(
						user.getUserId(),
						exportImportConfiguration.
							getExportImportConfigurationId(),
						StringPool.BLANK, StringPool.BLANK, settingsMap,
						new ServiceContext());

			exportImportConfiguration.setGroupId(importedGroup.getGroupId());

			exportImportConfiguration =
				ExportImportConfigurationLocalServiceUtil.
					updateExportImportConfiguration(exportImportConfiguration);

			ExportImportLocalServiceUtil.importPortletDataDeletions(
				exportImportConfiguration, larFile);

			ExportImportLocalServiceUtil.importPortletInfo(
				exportImportConfiguration, larFile);

			ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
				ExportImportLifecycleConstants.
					EVENT_PUBLICATION_PORTLET_LOCAL_SUCCEEDED,
				ExportImportLifecycleConstants.
					PROCESS_FLAG_PORTLET_STAGING_IN_PROCESS,
				String.valueOf(
					exportImportConfiguration.getExportImportConfigurationId()),
				exportImportConfiguration);
		}
		finally {
			ExportImportThreadLocal.setPortletStagingInProcess(false);
		}
	}

	protected PortletPreferences getImportedPortletPreferences(
			Map<String, String[]> preferenceMap)
		throws Exception {

		String portletId = LayoutTestUtil.addPortletToLayout(
			TestPropsValues.getUserId(), layout, getPortletId(), "column-1",
			preferenceMap);

		exportImportPortlet(portletId);

		return LayoutTestUtil.getPortletPreferences(importedLayout, portletId);
	}

	protected boolean isVersioningEnabled() {
		return false;
	}

	protected void publishPortlet(String portletId) throws Exception {
		Map<String, String[]> parameterMap =
			ExportImportConfigurationParameterMapFactoryUtil.
				buildFullPublishParameterMap();

		if (importedLayout == null) {
			importedLayout = LayoutTestUtil.addLayout(importedGroup);
		}

		Map<String, Serializable> settingsMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
				buildPublishPortletSettingsMap(
					TestPropsValues.getUser(), layout.getGroupId(),
					layout.getPlid(), importedLayout.getGroupId(),
					importedLayout.getPlid(), portletId, parameterMap);

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(),
					ExportImportConfigurationConstants.
						TYPE_PUBLISH_PORTLET_LOCAL,
					settingsMap);

		StagingUtil.publishPortlet(
			TestPropsValues.getUserId(), exportImportConfiguration);
	}

	protected void testExportImportAvailableLocales(
			Collection<Locale> sourceAvailableLocales,
			Collection<Locale> targetAvailableLocales, boolean expectFailure)
		throws Exception {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			group.getCompanyId(), getPortletId());

		if (portlet == null) {
			return;
		}

		PortletDataHandler portletDataHandler =
			portlet.getPortletDataHandlerInstance();

		if (!portletDataHandler.isDataLocalized()) {
			Assert.assertTrue("This test does not apply", true);

			return;
		}

		GroupTestUtil.updateDisplaySettings(
			group.getGroupId(), sourceAvailableLocales, null);
		GroupTestUtil.updateDisplaySettings(
			importedGroup.getGroupId(), targetAvailableLocales, null);

		try {
			exportImportPortlet(getPortletId());

			Assert.assertFalse(expectFailure);
		}
		catch (LocaleException le) {
			Assert.assertTrue(expectFailure);
		}
	}

	protected void testExportImportDisplayStyle(
			long displayStyleGroupId, String scopeType)
		throws Exception {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			group.getCompanyId(), getPortletId());

		if (portlet == null) {
			return;
		}

		if (scopeType.equals("layout") && !portlet.isScopeable()) {
			Assert.assertTrue("This test does not apply", true);

			return;
		}

		TemplateHandler templateHandler = portlet.getTemplateHandlerInstance();

		if ((templateHandler == null) ||
			!templateHandler.isDisplayTemplateHandler()) {

			Assert.assertTrue("This test does not apply", true);

			return;
		}

		long resourceClassNameId = PortalUtil.getClassNameId(
			"com.liferay.portlet.display.template.PortletDisplayTemplate");

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			displayStyleGroupId,
			PortalUtil.getClassNameId(templateHandler.getClassName()), 0,
			resourceClassNameId);

		String displayStyle =
			PortletDisplayTemplateManager.DISPLAY_STYLE_PREFIX +
				ddmTemplate.getTemplateKey();

		Map<String, String[]> preferenceMap = new HashMap<>();

		preferenceMap.put("displayStyle", new String[] {displayStyle});

		preferenceMap.put(
			"displayStyleGroupId",
			new String[] {String.valueOf(ddmTemplate.getGroupId())});

		if (scopeType.equals("layout")) {
			preferenceMap.put(
				"lfrScopeLayoutUuid", new String[] {this.layout.getUuid()});
		}

		preferenceMap.put("lfrScopeType", new String[] {scopeType});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		String importedDisplayStyle = portletPreferences.getValue(
			"displayStyle", StringPool.BLANK);

		Assert.assertEquals(displayStyle, importedDisplayStyle);

		long importedDisplayStyleGroupId = GetterUtil.getLong(
			portletPreferences.getValue("displayStyleGroupId", null));

		long expectedDisplayStyleGroupId = importedGroup.getGroupId();

		if (scopeType.equals("company")) {
			Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
				importedGroup.getCompanyId());

			expectedDisplayStyleGroupId = companyGroup.getGroupId();
		}
		else if (displayStyleGroupId != group.getGroupId()) {
			expectedDisplayStyleGroupId = displayStyleGroupId;
		}

		Assert.assertEquals(
			expectedDisplayStyleGroupId, importedDisplayStyleGroupId);
	}

	protected void validateImportedLinks(
			StagedModel originalStagedModel, StagedModel importedStagedModel)
		throws PortalException {

		AssetEntry originalAssetEntry = getAssetEntry(originalStagedModel);

		List<AssetLink> originalAssetLinks = AssetLinkLocalServiceUtil.getLinks(
			originalAssetEntry.getEntryId());

		AssetEntry importedAssetEntry = getAssetEntry(importedStagedModel);

		List<AssetLink> importedAssetLinks = AssetLinkLocalServiceUtil.getLinks(
			importedAssetEntry.getEntryId());

		Assert.assertEquals(
			importedAssetLinks.toString(), originalAssetLinks.size(),
			importedAssetLinks.size());

		for (AssetLink originalLink : originalAssetLinks) {
			AssetEntry sourceAssetEntry = AssetEntryLocalServiceUtil.getEntry(
				originalLink.getEntryId1());

			AssetEntry targetAssetEntry = AssetEntryLocalServiceUtil.getEntry(
				originalLink.getEntryId2());

			Iterator<AssetLink> iterator = importedAssetLinks.iterator();

			while (iterator.hasNext()) {
				AssetLink importedLink = iterator.next();

				AssetEntry importedLinkSourceAssetEntry =
					AssetEntryLocalServiceUtil.getEntry(
						importedLink.getEntryId1());

				if (!Objects.equals(
						sourceAssetEntry.getClassUuid(),
						importedLinkSourceAssetEntry.getClassUuid())) {

					continue;
				}

				AssetEntry importedLinkTargetAssetEntry =
					AssetEntryLocalServiceUtil.getEntry(
						importedLink.getEntryId2());

				if (!Objects.equals(
						targetAssetEntry.getClassUuid(),
						importedLinkTargetAssetEntry.getClassUuid())) {

					continue;
				}

				Assert.assertEquals(
					originalLink.getWeight(), importedLink.getWeight());
				Assert.assertEquals(
					originalLink.getType(), importedLink.getType());

				iterator.remove();

				break;
			}
		}

		Assert.assertEquals(
			importedAssetLinks.toString(), 0, importedAssetLinks.size());
	}

	protected void validateVersions() throws Exception {
	}

}