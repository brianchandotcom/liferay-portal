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

package com.liferay.asset.publisher.upgrade.v1_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.publisher.web.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.web.upgrade.v1_0_0.UpgradePortletPreferences;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.text.DateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests the upgrade behavior for the Asset Publisher's portlet preferences.
 *
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class UpgradePortletPreferencesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_active = CacheRegistryUtil.isActive();

		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addLayout(_group);

		CacheRegistryUtil.setActive(false);

		setUpDateFormatFactories();
		setUpUpgradePortletPreferences();
	}

	@After
	public void tearDown() {
		CacheRegistryUtil.setActive(_active);
	}

	@Test
	public void testUpgradeDLDateFieldsValues() throws Exception {
		DDMStructure ddmStructure = addDDMStructure(
			DLFileEntryMetadata.class.getName());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeLocalServiceUtil.addFileEntryType(
				TestPropsValues.getUserId(), _group.getGroupId(),
				"New File Entry Type", StringPool.BLANK,
				new long[] {ddmStructure.getStructureId()}, serviceContext);

		Map<String, String> portletPreferencesMap = new HashMap<>();

		portletPreferencesMap.put(
			"subtypeFieldsFilterEnabled", Boolean.TRUE.toString());

		portletPreferencesMap.put(
			"subtypeFieldsFilterEnabledDLFileEntryAssetRendererFactory",
			Boolean.TRUE.toString());

		portletPreferencesMap.put(
			"anyClassTypeDLFileEntryAssetRendererFactory",
			String.valueOf(dlFileEntryType.getFileEntryTypeId()));

		Date now = new Date();

		String dateString = _oldDateFormat.format(now);

		portletPreferencesMap.put("ddmStructureFieldName", "Birthday");

		portletPreferencesMap.put("ddmStructureFieldValue", dateString);

		String portletId = getPortletId();

		PortletPreferences portletPreferences = updatePortletPreferences(
			portletId, portletPreferencesMap);

		String ddmStructureFieldValue = portletPreferences.getValue(
			"ddmStructureFieldValue", StringPool.BLANK);

		Assert.assertEquals(dateString, ddmStructureFieldValue);

		_upgradePortletPreferences.upgrade();

		portletPreferences = getPortletPreferences(portletId);

		ddmStructureFieldValue = portletPreferences.getValue(
			"ddmStructureFieldValue", StringPool.BLANK);

		dateString = _newDateFormat.format(now);

		Assert.assertEquals(dateString, ddmStructureFieldValue);
	}

	@Test
	public void testUpgradeDLDateFieldsValuesWithEmptyValue() throws Exception {
		DDMStructure ddmStructure = addDDMStructure(
			DLFileEntryMetadata.class.getName());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeLocalServiceUtil.addFileEntryType(
				TestPropsValues.getUserId(), _group.getGroupId(),
				"New File Entry Type", StringPool.BLANK,
				new long[] {ddmStructure.getStructureId()}, serviceContext);

		Map<String, String> portletPreferencesMap = new HashMap<>();

		portletPreferencesMap.put(
			"subtypeFieldsFilterEnabled", Boolean.TRUE.toString());

		portletPreferencesMap.put(
			"subtypeFieldsFilterEnabledDLFileEntryAssetRendererFactory",
			Boolean.TRUE.toString());

		portletPreferencesMap.put(
			"anyClassTypeDLFileEntryAssetRendererFactory",
			String.valueOf(dlFileEntryType.getFileEntryTypeId()));

		portletPreferencesMap.put("ddmStructureFieldName", "Birthday");

		portletPreferencesMap.put("ddmStructureFieldValue", StringPool.BLANK);

		String portletId = getPortletId();

		updatePortletPreferences(portletId, portletPreferencesMap);

		_upgradePortletPreferences.upgrade();

		PortletPreferences portletPreferences = getPortletPreferences(
			portletId);

		String ddmStructureFieldValue = portletPreferences.getValue(
			"ddmStructureFieldValue", StringPool.BLANK);

		Assert.assertEquals(StringPool.BLANK, ddmStructureFieldValue);
	}

	@Test
	public void testUpgradeJournalDateFieldValue() throws Exception {
		DDMStructure ddmStructure = addDDMStructure(
			JournalArticle.class.getName());

		Map<String, String> portletPreferencesMap = new HashMap<>();

		portletPreferencesMap.put(
			"subtypeFieldsFilterEnabled", Boolean.TRUE.toString());

		portletPreferencesMap.put(
			"subtypeFieldsFilterEnabledJournalArticleAssetRendererFactory",
			Boolean.TRUE.toString());

		portletPreferencesMap.put(
			"anyClassTypeJournalArticleAssetRendererFactory",
			String.valueOf(ddmStructure.getStructureId()));

		portletPreferencesMap.put("ddmStructureFieldName", "Birthday");

		Date now = new Date();

		String dateString = _oldDateFormat.format(now);

		portletPreferencesMap.put("ddmStructureFieldValue", dateString);

		String portletId = getPortletId();

		PortletPreferences portletPreferences = updatePortletPreferences(
			portletId, portletPreferencesMap);

		String fieldValue = portletPreferences.getValue(
			"ddmStructureFieldValue", StringPool.BLANK);

		Assert.assertEquals(dateString, fieldValue);

		_upgradePortletPreferences.upgrade();

		portletPreferences = getPortletPreferences(portletId);

		fieldValue = portletPreferences.getValue(
			"ddmStructureFieldValue", StringPool.BLANK);

		dateString = _newDateFormat.format(now);

		Assert.assertEquals(dateString, fieldValue);
	}

	@Test
	public void testUpgradeJournalDateFieldValueWithEmptyValue()
		throws Exception {

		DDMStructure ddmStructure = addDDMStructure(
			JournalArticle.class.getName());

		Map<String, String> portletPreferencesMap = new HashMap<>();

		portletPreferencesMap.put(
			"subtypeFieldsFilterEnabled", Boolean.TRUE.toString());

		portletPreferencesMap.put(
			"subtypeFieldsFilterEnabledJournalArticleAssetRendererFactory",
			Boolean.TRUE.toString());

		portletPreferencesMap.put(
			"anyClassTypeJournalArticleAssetRendererFactory",
			String.valueOf(ddmStructure.getStructureId()));

		portletPreferencesMap.put("ddmStructureFieldName", "Birthday");

		portletPreferencesMap.put("ddmStructureFieldValue", StringPool.BLANK);

		String portletId = getPortletId();

		updatePortletPreferences(portletId, portletPreferencesMap);

		_upgradePortletPreferences.upgrade();

		PortletPreferences portletPreferences = getPortletPreferences(
			portletId);

		String fieldValue = portletPreferences.getValue(
			"ddmStructureFieldValue", StringPool.BLANK);

		Assert.assertEquals(StringPool.BLANK, fieldValue);
	}

	@Test
	public void testUpgradeOrderByColumns() throws Exception {
		DDMStructure ddmStructure = addDDMStructure(
			JournalArticle.class.getName());

		DDMFormField ddmFormField = ddmStructure.getDDMFormField("Text");

		Map<String, String> portletPreferencesMap = new HashMap<>();

		portletPreferencesMap.put(
			"subtypeFieldsFilterEnabled", Boolean.TRUE.toString());

		StringBundler sb = new StringBundler(5);

		sb.append("ddm");
		sb.append(StringPool.DOUBLE_UNDERLINE);
		sb.append(ddmStructure.getStructureId());
		sb.append(StringPool.DOUBLE_UNDERLINE);
		sb.append(ddmFormField.getName());

		portletPreferencesMap.put("orderByColumn1", sb.toString());
		portletPreferencesMap.put("orderByColumn2", sb.toString());

		String portletId = getPortletId();

		updatePortletPreferences(portletId, portletPreferencesMap);

		_upgradePortletPreferences.upgrade();

		PortletPreferences portletPreferences = getPortletPreferences(
			portletId);

		String orderByColumn1 = portletPreferences.getValue(
			"orderByColumn1", StringPool.BLANK);

		String orderByColumn2 = portletPreferences.getValue(
			"orderByColumn2", StringPool.BLANK);

		String expectedOrderByColumn = getExpectedOrderByColumnValue(
			ddmStructure, ddmFormField);

		Assert.assertEquals(expectedOrderByColumn, orderByColumn1);
		Assert.assertEquals(expectedOrderByColumn, orderByColumn2);
	}

	@Test
	public void testUpgradeOrderByColumnsOldValueFormat() throws Exception {
		DDMStructure ddmStructure = addDDMStructure(
			JournalArticle.class.getName());

		DDMFormField ddmFormField = ddmStructure.getDDMFormField("Text");

		Map<String, String> portletPreferencesMap = new HashMap<>();

		portletPreferencesMap.put(
			"subtypeFieldsFilterEnabled", Boolean.TRUE.toString());

		StringBundler sb = new StringBundler(5);

		sb.append("ddm");
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(ddmStructure.getStructureId());
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(ddmFormField.getName());

		portletPreferencesMap.put("orderByColumn1", sb.toString());

		sb = new StringBundler(7);

		sb.append("ddm");
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(ddmFormField.getIndexType());
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(ddmStructure.getStructureId());
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(ddmFormField.getName());

		portletPreferencesMap.put("orderByColumn2", sb.toString());

		String portletId = getPortletId();

		updatePortletPreferences(portletId, portletPreferencesMap);

		_upgradePortletPreferences.upgrade();

		PortletPreferences portletPreferences = getPortletPreferences(
			portletId);

		String orderByColumn1 = portletPreferences.getValue(
			"orderByColumn1", StringPool.BLANK);

		String orderByColumn2 = portletPreferences.getValue(
			"orderByColumn2", StringPool.BLANK);

		String expectedOrderByColumn = getExpectedOrderByColumnValue(
			ddmStructure, ddmFormField);

		Assert.assertEquals(expectedOrderByColumn, orderByColumn1);
		Assert.assertEquals(expectedOrderByColumn, orderByColumn2);
	}

	protected DDMStructure addDDMStructure(String className) throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormFieldText =
			DDMFormTestUtil.createLocalizableTextDDMFormField("Text");

		ddmFormFieldText.setIndexType("text");

		ddmForm.addDDMFormField(ddmFormFieldText);

		DDMFormField ddmFormFieldDate = DDMFormTestUtil.createDDMFormField(
			"Birthday", "Birthday", DDMFormFieldType.DATE, "string", false,
			false, false);

		ddmForm.addDDMFormField(ddmFormFieldDate);

		return DDMStructureTestUtil.addStructure(
			_group.getGroupId(), className, ddmForm);
	}

	protected String getExpectedOrderByColumnValue(
		DDMStructure ddmStructure, DDMFormField ddmFormField) {

		StringBundler sb = new StringBundler(7);

		sb.append("ddm");
		sb.append(StringPool.DOUBLE_UNDERLINE);
		sb.append(ddmFormField.getIndexType());
		sb.append(StringPool.DOUBLE_UNDERLINE);
		sb.append(ddmStructure.getStructureId());
		sb.append(StringPool.DOUBLE_UNDERLINE);
		sb.append(ddmFormField.getName());

		return sb.toString();
	}

	protected String getPortletId() {
		StringBundler sb = new StringBundler(3);

		sb.append(AssetPublisherPortletKeys.ASSET_PUBLISHER);
		sb.append("_INSTANCE_");
		sb.append(StringUtil.randomId());

		return sb.toString();
	}

	protected PortletPreferences getPortletPreferences(String portletId)
		throws Exception {

		return LayoutTestUtil.getPortletPreferences(_layout, portletId);
	}

	protected void setUpDateFormatFactories() {
		_newDateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd");
		_oldDateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyyMMddHHmmss");
	}

	protected void setUpUpgradePortletPreferences() {
		Registry registry = RegistryUtil.getRegistry();

		DDMStructureLocalService ddmStructureLocalService = registry.getService(
			DDMStructureLocalService.class);

		DDMStructureLinkLocalService ddmStructureLinkLocalService =
			registry.getService(DDMStructureLinkLocalService.class);

		SAXReader saxReader = registry.getService(SAXReader.class);

		_upgradePortletPreferences = new UpgradePortletPreferences(
			ddmStructureLocalService, ddmStructureLinkLocalService, saxReader);
	}

	protected PortletPreferences updatePortletPreferences(
			String portletId, Map<String, String> portletPreferencesMap)
		throws Exception {

		LayoutTestUtil.updateLayoutPortletPreferences(
			_layout, portletId, portletPreferencesMap);

		return getPortletPreferences(portletId);
	}

	private boolean _active;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;
	private DateFormat _newDateFormat;
	private DateFormat _oldDateFormat;
	private UpgradePortletPreferences _upgradePortletPreferences;

}