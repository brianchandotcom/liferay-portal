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
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
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
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.lang.reflect.Constructor;

import java.text.DateFormat;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletPreferences;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

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

		_layout = LayoutTestUtil.addTypePortletLayout(_group);

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
		Date date = new Date();

		String dateString = _oldDateFormat.format(date);

		PortletPreferences portletPreferences = updatePortletPreferences(
			getPortletId(),
			HashMapBuilder.put(
				"anyClassTypeDLFileEntryAssetRendererFactory",
				() -> {
					DDMStructure ddmStructure = addDDMStructure(
						DLFileEntryMetadata.class.getName());

					DLFileEntryType dlFileEntryType =
						DLFileEntryTypeLocalServiceUtil.addFileEntryType(
							TestPropsValues.getUserId(), _group.getGroupId(),
							"New File Entry Type", StringPool.BLANK,
							new long[] {ddmStructure.getStructureId()},
							ServiceContextTestUtil.getServiceContext());

					return String.valueOf(dlFileEntryType.getFileEntryTypeId());
				}
			).put(
				"ddmStructureFieldName", "Birthday"
			).put(
				"ddmStructureFieldValue", dateString
			).put(
				"subtypeFieldsFilterEnabled", Boolean.TRUE.toString()
			).put(
				"subtypeFieldsFilterEnabledDLFileEntryAssetRendererFactory",
				Boolean.TRUE.toString()
			).build());

		String ddmStructureFieldValue = portletPreferences.getValue(
			"ddmStructureFieldValue", StringPool.BLANK);

		Assert.assertEquals(dateString, ddmStructureFieldValue);

		portletPreferences = ReflectionTestUtil.invoke(
			_upgradePortletPreferences, "upgradePreferences",
			new Class<?>[] {PortletPreferences.class}, portletPreferences);

		ddmStructureFieldValue = portletPreferences.getValue(
			"ddmStructureFieldValue", StringPool.BLANK);

		dateString = _newDateFormat.format(date);

		Assert.assertEquals(dateString, ddmStructureFieldValue);
	}

	@Test
	public void testUpgradeDLDateFieldsValuesWithEmptyValue() throws Exception {
		PortletPreferences portletPreferences = updatePortletPreferences(
			getPortletId(),
			HashMapBuilder.put(
				"anyClassTypeDLFileEntryAssetRendererFactory",
				() -> {
					DDMStructure ddmStructure = addDDMStructure(
						DLFileEntryMetadata.class.getName());

					DLFileEntryType dlFileEntryType =
						DLFileEntryTypeLocalServiceUtil.addFileEntryType(
							TestPropsValues.getUserId(), _group.getGroupId(),
							"New File Entry Type", StringPool.BLANK,
							new long[] {ddmStructure.getStructureId()},
							ServiceContextTestUtil.getServiceContext());

					return String.valueOf(dlFileEntryType.getFileEntryTypeId());
				}
			).put(
				"ddmStructureFieldName", "Birthday"
			).put(
				"ddmStructureFieldValue", StringPool.BLANK
			).put(
				"subtypeFieldsFilterEnabled", Boolean.TRUE.toString()
			).put(
				"subtypeFieldsFilterEnabledDLFileEntryAssetRendererFactory",
				Boolean.TRUE.toString()
			).build());

		portletPreferences = ReflectionTestUtil.invoke(
			_upgradePortletPreferences, "upgradePreferences",
			new Class<?>[] {PortletPreferences.class}, portletPreferences);

		String ddmStructureFieldValue = portletPreferences.getValue(
			"ddmStructureFieldValue", StringPool.BLANK);

		Assert.assertEquals(StringPool.BLANK, ddmStructureFieldValue);
	}

	@Test
	public void testUpgradeJournalDateFieldValue() throws Exception {
		Date date = new Date();

		String dateString = _oldDateFormat.format(date);

		PortletPreferences portletPreferences = updatePortletPreferences(
			getPortletId(),
			HashMapBuilder.put(
				"anyClassTypeJournalArticleAssetRendererFactory",
				() -> {
					DDMStructure ddmStructure = addDDMStructure(
						JournalArticle.class.getName());

					return String.valueOf(ddmStructure.getStructureId());
				}
			).put(
				"ddmStructureFieldName", "Birthday"
			).put(
				"ddmStructureFieldValue", dateString
			).put(
				"subtypeFieldsFilterEnabled", Boolean.TRUE.toString()
			).put(
				"subtypeFieldsFilterEnabledJournalArticleAssetRendererFactory",
				Boolean.TRUE.toString()
			).build());

		String fieldValue = portletPreferences.getValue(
			"ddmStructureFieldValue", StringPool.BLANK);

		Assert.assertEquals(dateString, fieldValue);

		portletPreferences = ReflectionTestUtil.invoke(
			_upgradePortletPreferences, "upgradePreferences",
			new Class<?>[] {PortletPreferences.class}, portletPreferences);

		fieldValue = portletPreferences.getValue(
			"ddmStructureFieldValue", StringPool.BLANK);

		dateString = _newDateFormat.format(date);

		Assert.assertEquals(dateString, fieldValue);
	}

	@Test
	public void testUpgradeJournalDateFieldValueWithEmptyValue()
		throws Exception {

		PortletPreferences portletPreferences = updatePortletPreferences(
			getPortletId(),
			HashMapBuilder.put(
				"anyClassTypeJournalArticleAssetRendererFactory",
				() -> {
					DDMStructure ddmStructure = addDDMStructure(
						JournalArticle.class.getName());

					return String.valueOf(ddmStructure.getStructureId());
				}
			).put(
				"ddmStructureFieldName", "Birthday"
			).put(
				"ddmStructureFieldValue", StringPool.BLANK
			).put(
				"subtypeFieldsFilterEnabled", Boolean.TRUE.toString()
			).put(
				"subtypeFieldsFilterEnabledJournalArticleAssetRendererFactory",
				Boolean.TRUE.toString()
			).build());

		portletPreferences = ReflectionTestUtil.invoke(
			_upgradePortletPreferences, "upgradePreferences",
			new Class<?>[] {PortletPreferences.class}, portletPreferences);

		String fieldValue = portletPreferences.getValue(
			"ddmStructureFieldValue", StringPool.BLANK);

		Assert.assertEquals(StringPool.BLANK, fieldValue);
	}

	@Test
	public void testUpgradeOrderByColumns() throws Exception {
		DDMStructure ddmStructure = addDDMStructure(
			JournalArticle.class.getName());

		DDMFormField ddmFormField = ddmStructure.getDDMFormField("Text");

		StringBundler sb = new StringBundler(4);

		sb.append("ddm__");
		sb.append(ddmStructure.getStructureId());
		sb.append(StringPool.DOUBLE_UNDERLINE);
		sb.append(ddmFormField.getName());

		PortletPreferences portletPreferences = updatePortletPreferences(
			getPortletId(),
			HashMapBuilder.put(
				"orderByColumn1", sb.toString()
			).put(
				"orderByColumn2", sb.toString()
			).put(
				"subtypeFieldsFilterEnabled", Boolean.TRUE.toString()
			).build());

		portletPreferences = ReflectionTestUtil.invoke(
			_upgradePortletPreferences, "upgradePreferences",
			new Class<?>[] {PortletPreferences.class}, portletPreferences);

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

		PortletPreferences portletPreferences = updatePortletPreferences(
			getPortletId(),
			HashMapBuilder.put(
				"orderByColumn1",
				StringBundler.concat(
					"ddm/", ddmStructure.getStructureId(),
					StringPool.FORWARD_SLASH, ddmFormField.getName())
			).put(
				"orderByColumn2",
				StringBundler.concat(
					"ddm/", ddmFormField.getIndexType(),
					StringPool.FORWARD_SLASH, ddmStructure.getStructureId(),
					StringPool.FORWARD_SLASH, ddmFormField.getName())
			).put(
				"subtypeFieldsFilterEnabled", Boolean.TRUE.toString()
			).build());

		portletPreferences = ReflectionTestUtil.invoke(
			_upgradePortletPreferences, "upgradePreferences",
			new Class<?>[] {PortletPreferences.class}, portletPreferences);

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

		return StringBundler.concat(
			"ddm__", ddmFormField.getIndexType(), StringPool.DOUBLE_UNDERLINE,
			ddmStructure.getStructureId(), StringPool.DOUBLE_UNDERLINE,
			ddmFormField.getName());
	}

	protected String getPortletId() {
		return StringBundler.concat(
			AssetPublisherPortletKeys.ASSET_PUBLISHER, "_INSTANCE_",
			StringUtil.randomId());
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

	protected void setUpUpgradePortletPreferences()
		throws ReflectiveOperationException {

		Bundle bundle = FrameworkUtil.getBundle(
			UpgradePortletPreferencesTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		Bundle assetPublisherWebBundle = null;

		for (Bundle curBundle : bundleContext.getBundles()) {
			if (Objects.equals(
					curBundle.getSymbolicName(),
					"com.liferay.asset.publisher.web")) {

				assetPublisherWebBundle = curBundle;

				break;
			}
		}

		Assert.assertNotNull(
			"Unable to find asset-publisher-web bundle",
			assetPublisherWebBundle);

		Class<?> clazz = assetPublisherWebBundle.loadClass(
			"com.liferay.asset.publisher.web.internal.upgrade.v1_0_0." +
				"UpgradePortletPreferences");

		Constructor<?> constructor = clazz.getConstructor(
			DDMStructureLocalService.class, DDMStructureLinkLocalService.class,
			SAXReader.class);

		_upgradePortletPreferences = constructor.newInstance(
			_ddmStructureLocalService, _ddmStructureLinkLocalService,
			_saxReader);
	}

	protected PortletPreferences updatePortletPreferences(
			String portletId, Map<String, String> portletPreferencesMap)
		throws Exception {

		LayoutTestUtil.updateLayoutPortletPreferences(
			_layout, portletId, portletPreferencesMap);

		return getPortletPreferences(portletId);
	}

	@Inject
	private static DDMStructureLinkLocalService _ddmStructureLinkLocalService;

	@Inject
	private static DDMStructureLocalService _ddmStructureLocalService;

	@Inject
	private static SAXReader _saxReader;

	private boolean _active;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;
	private DateFormat _newDateFormat;
	private DateFormat _oldDateFormat;
	private Object _upgradePortletPreferences;

}