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

package com.liferay.portal.lar.test;

import com.liferay.portal.LocaleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.ExportImportClassedModelUtil;
import com.liferay.portal.kernel.lar.ExportImportDateUtil;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetLinkLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMTemplateTestUtil;
import com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
			new String[] {String.valueOf(true)});

		exportImportPortlet(
			getPortletId(), exportParameterMap, getImportParameterMap());

		importedStagedModel = getStagedModel(
			stagedModelUuid, importedGroup.getGroupId());

		Assert.assertNotNull(importedStagedModel);

		Map<String, String[]> importParameterMap = new LinkedHashMap<>();

		importParameterMap.put(
			PortletDataHandlerKeys.DELETIONS,
			new String[] {String.valueOf(true)});

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
			new Locale[] {LocaleUtil.US, LocaleUtil.SPAIN},
			new Locale[] {LocaleUtil.US, LocaleUtil.GERMANY}, true);
	}

	@Test
	public void testExportImportValidAvailableLocales() throws Exception {
		testExportImportAvailableLocales(
			new Locale[] {LocaleUtil.US, LocaleUtil.SPAIN},
			new Locale[] {LocaleUtil.US, LocaleUtil.SPAIN, LocaleUtil.GERMANY},
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
			new HashMap<String, String[]>());

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getStrictPortletSetup(
				layout, getPortletId());

		portletPreferences.setValue(
			"last-publish-date", String.valueOf(lastPublishDate.getTime()));

		portletPreferences.store();

		Map<String, String[]> exportParameterMap = new LinkedHashMap<>();

		exportParameterMap.put(
			PortletDataHandlerKeys.UPDATE_LAST_PUBLISH_DATE,
			new String[] {String.valueOf(true)});
		exportParameterMap.put(
			"range",
			new String[] {ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE});

		Map<String, String[]> importParameterMap = new LinkedHashMap<>();

		Date startDate = new Date(
			stagedModelCreationDate.getTime() + Time.MINUTE);
		Date endDate = new Date();

		exportImportPortlet(
			getPortletId(), exportParameterMap, importParameterMap, startDate,
			endDate);

		portletPreferences =
			PortletPreferencesFactoryUtil.getStrictPortletSetup(
				layout, getPortletId());

		lastPublishDate = ExportImportDateUtil.getLastPublishDate(
			portletPreferences);

		Assert.assertEquals(endDate.getTime(), lastPublishDate.getTime());

		StagedModel importedStagedModel = getStagedModel(
			getStagedModelUuid(stagedModel), importedGroup.getGroupId());

		Assert.assertNotNull(importedStagedModel);
	}

	protected AssetLink addAssetLink(
			StagedModel sourceStagedModel, StagedModel targetStagedModel,
			int weight)
		throws PortalException {

		AssetEntry originAssetEntry = getAssetEntry(sourceStagedModel);
		AssetEntry targetAssetEntry = getAssetEntry(targetStagedModel);

		return AssetLinkLocalServiceUtil.addLink(
			TestPropsValues.getUserId(), originAssetEntry.getEntryId(),
			targetAssetEntry.getEntryId(), 0, weight);
	}

	protected void addParameter(
		Map<String, String[]> parameterMap, String name, boolean value) {

		addParameter(parameterMap, getNamespace(), name, value);
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

		exportImportPortlet(
			portletId, exportParameterMap, importParameterMap, null, null);
	}

	protected void exportImportPortlet(
			String portletId, Map<String, String[]> exportParameterMap,
			Map<String, String[]> importParameterMap, Date startDate,
			Date endDate)
		throws Exception {

		MapUtil.merge(getExportParameterMap(), exportParameterMap);

		larFile = LayoutLocalServiceUtil.exportPortletInfoAsFile(
			layout.getPlid(), layout.getGroupId(), portletId,
			exportParameterMap, startDate, endDate);

		importedLayout = LayoutTestUtil.addLayout(importedGroup);

		MapUtil.merge(getImportParameterMap(), importParameterMap);

		LayoutLocalServiceUtil.importPortletInfo(
			TestPropsValues.getUserId(), importedLayout.getPlid(),
			importedGroup.getGroupId(), portletId, importParameterMap, larFile);
	}

	protected AssetEntry getAssetEntry(StagedModel stagedModel)
		throws PortalException {

		return AssetEntryLocalServiceUtil.getEntry(
			ExportImportClassedModelUtil.getClassName(stagedModel),
			ExportImportClassedModelUtil.getClassPK(stagedModel));
	}

	protected PortletPreferences getImportedPortletPreferences(
			Map<String, String[]> preferenceMap)
		throws Exception {

		String portletId = LayoutTestUtil.addPortletToLayout(
			TestPropsValues.getUserId(), this.layout, getPortletId(),
			"column-1", preferenceMap);

		exportImportPortlet(portletId);

		return LayoutTestUtil.getPortletPreferences(importedLayout, portletId);
	}

	protected void testExportImportAvailableLocales(
			Locale[] sourceAvailableLocales, Locale[] targetAvailableLocales,
			boolean expectFailure)
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

			if (expectFailure) {
				Assert.fail();
			}
		}
		catch (LocaleException le) {
			if (!expectFailure) {
				Assert.fail();
			}
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

		if (templateHandler == null) {
			Assert.assertTrue("This test does not apply", true);

			return;
		}

		String className = templateHandler.getClassName();

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			displayStyleGroupId, PortalUtil.getClassNameId(className), 0);

		Map<String, String[]> preferenceMap = new HashMap<>();

		String displayStyle =
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX + ddmTemplate.getUuid();

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
			originalAssetLinks.size(), importedAssetLinks.size());

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
				AssetEntry importedLinkTargetAssetEntry =
					AssetEntryLocalServiceUtil.getEntry(
						importedLink.getEntryId2());

				if (!sourceAssetEntry.getClassUuid().equals(
						importedLinkSourceAssetEntry.getClassUuid())) {

					continue;
				}

				if (!targetAssetEntry.getClassUuid().equals(
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

		Assert.assertEquals(0, importedAssetLinks.size());
	}

}