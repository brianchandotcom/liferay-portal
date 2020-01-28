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

package com.liferay.layout.seo.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.kernel.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManagerUtil;
import com.liferay.dynamic.data.mapping.kernel.Value;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.service.LayoutSEOEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.nio.charset.StandardCharsets;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class LayoutSEOEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_layout = _layoutLocalService.getLayout(TestPropsValues.getPlid());
	}

	@Test
	public void testAddLayoutSEOEntry() throws PortalException {
		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			_layout.getLayoutId(), false,
			Collections.singletonMap(LocaleUtil.US, "http://example.com"),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		LayoutSEOEntry layoutSEOEntry =
			_layoutSEOEntryLocalService.fetchLayoutSEOEntry(
				_group.getGroupId(), false, _layout.getLayoutId());

		Assert.assertEquals(
			"http://example.com",
			layoutSEOEntry.getCanonicalURL(LocaleUtil.US));
		Assert.assertFalse(layoutSEOEntry.isCanonicalURLEnabled());
		Assert.assertEquals(
			StringPool.BLANK,
			layoutSEOEntry.getOpenGraphDescription(LocaleUtil.US));
		Assert.assertFalse(layoutSEOEntry.isOpenGraphDescriptionEnabled());
		Assert.assertEquals(0, layoutSEOEntry.getOpenGraphImageFileEntryId());
		Assert.assertEquals(
			StringPool.BLANK, layoutSEOEntry.getOpenGraphTitle(LocaleUtil.US));
		Assert.assertFalse(layoutSEOEntry.isOpenGraphTitleEnabled());
		Assert.assertEquals(0, layoutSEOEntry.getDDMStorageId());
	}

	@Test
	public void testAddLayoutSEOEntryWithAllFields() throws PortalException {
		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			_layout.getLayoutId(), false,
			Collections.singletonMap(LocaleUtil.US, "http://example.com"), true,
			Collections.singletonMap(LocaleUtil.US, "description"),
			Collections.singletonMap(LocaleUtil.US, "image alt"), 12345, true,
			Collections.singletonMap(LocaleUtil.US, "title"),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		LayoutSEOEntry layoutSEOEntry =
			_layoutSEOEntryLocalService.fetchLayoutSEOEntry(
				_group.getGroupId(), false, _layout.getLayoutId());

		Assert.assertEquals(
			"http://example.com",
			layoutSEOEntry.getCanonicalURL(LocaleUtil.US));
		Assert.assertFalse(layoutSEOEntry.isCanonicalURLEnabled());
		Assert.assertEquals(
			12345, layoutSEOEntry.getOpenGraphImageFileEntryId());
		Assert.assertEquals(
			"description",
			layoutSEOEntry.getOpenGraphDescription(LocaleUtil.US));
		Assert.assertTrue(layoutSEOEntry.isOpenGraphDescriptionEnabled());
		Assert.assertEquals(
			"title", layoutSEOEntry.getOpenGraphTitle(LocaleUtil.US));
		Assert.assertTrue(layoutSEOEntry.isOpenGraphTitleEnabled());
		Assert.assertEquals(0, layoutSEOEntry.getDDMStorageId());
	}

	@Test
	public void testAddLayoutSEOEntryWithCustomTags() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAttribute(
			_getDDMStructureId() + "ddmFormValues",
			new String(
				FileUtil.getBytes(
					getClass(),
					"dependencies/custom_meta_tags_ddm_form_values.json"),
				StandardCharsets.UTF_8));

		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			_layout.getLayoutId(), false,
			Collections.singletonMap(LocaleUtil.US, "http://example.com"), true,
			Collections.singletonMap(LocaleUtil.US, "description"),
			Collections.singletonMap(LocaleUtil.US, "image alt"), 12345, true,
			Collections.singletonMap(LocaleUtil.US, "title"), serviceContext);

		LayoutSEOEntry layoutSEOEntry =
			_layoutSEOEntryLocalService.fetchLayoutSEOEntry(
				_group.getGroupId(), false, _layout.getLayoutId());

		DDMFormValues ddmFormValues = StorageEngineManagerUtil.getDDMFormValues(
			layoutSEOEntry.getDDMStorageId());

		Assert.assertNotNull(ddmFormValues);

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		Collection<List<DDMFormFieldValue>> values =
			ddmFormFieldValuesMap.values();

		Assert.assertEquals(values.toString(), 1, values.size());

		for (List<DDMFormFieldValue> fieldValues : values) {
			Assert.assertEquals(fieldValues.toString(), 2, fieldValues.size());

			DDMFormFieldValue nameDDMFormFieldValue = fieldValues.get(0);

			Value nameValue = nameDDMFormFieldValue.getValue();

			Assert.assertEquals(
				"property1", nameValue.getString(LocaleUtil.US));

			List<DDMFormFieldValue> nestedDDMFormFieldValues =
				nameDDMFormFieldValue.getNestedDDMFormFieldValues();

			DDMFormFieldValue valueDDMFormFieldValue =
				nestedDDMFormFieldValues.get(0);

			Value valueValue = valueDDMFormFieldValue.getValue();

			Assert.assertEquals(
				"content1", valueValue.getString(LocaleUtil.US));

			nameDDMFormFieldValue = fieldValues.get(1);

			nameValue = nameDDMFormFieldValue.getValue();

			Assert.assertEquals(
				"property2", nameValue.getString(LocaleUtil.US));

			nestedDDMFormFieldValues =
				nameDDMFormFieldValue.getNestedDDMFormFieldValues();

			valueDDMFormFieldValue = nestedDDMFormFieldValues.get(0);

			valueValue = valueDDMFormFieldValue.getValue();

			Assert.assertEquals(
				"content2", valueValue.getString(LocaleUtil.US));
		}
	}

	@Test
	public void testAddLayoutSEOEntryWithEmptyCustomTags()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAttribute(
			_getDDMStructureId() + "ddmFormValues",
			new String(
				FileUtil.getBytes(
					getClass(),
					"dependencies/empty_custom_meta_tags_ddm_form_values.json"),
				StandardCharsets.UTF_8));

		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			_layout.getLayoutId(), false,
			Collections.singletonMap(LocaleUtil.US, "http://example.com"), true,
			Collections.singletonMap(LocaleUtil.US, "description"),
			Collections.singletonMap(LocaleUtil.US, "image alt"), 12345, true,
			Collections.singletonMap(LocaleUtil.US, "title"), serviceContext);

		LayoutSEOEntry layoutSEOEntry =
			_layoutSEOEntryLocalService.fetchLayoutSEOEntry(
				_group.getGroupId(), false, _layout.getLayoutId());

		Assert.assertEquals(0, layoutSEOEntry.getDDMStorageId());
	}

	@Test
	public void testDeleteLayoutSEOEntry() throws PortalException {
		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			_layout.getLayoutId(), false,
			Collections.singletonMap(LocaleUtil.US, "http://example.com"),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_layoutSEOEntryLocalService.deleteLayoutSEOEntry(
			_group.getGroupId(), false, _layout.getLayoutId());

		LayoutSEOEntry layoutSEOEntry =
			_layoutSEOEntryLocalService.fetchLayoutSEOEntry(
				_group.getGroupId(), false, _layout.getLayoutId());

		Assert.assertNull(layoutSEOEntry);
	}

	@Test
	public void testUpdateLayoutSEOEntry() throws PortalException {
		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			_layout.getLayoutId(), false,
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			_layout.getLayoutId(), true,
			Collections.singletonMap(LocaleUtil.US, "http://example.com"),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		LayoutSEOEntry layoutSEOEntry =
			_layoutSEOEntryLocalService.fetchLayoutSEOEntry(
				_group.getGroupId(), false, _layout.getLayoutId());

		Assert.assertEquals(
			"http://example.com",
			layoutSEOEntry.getCanonicalURL(LocaleUtil.US));
		Assert.assertTrue(layoutSEOEntry.isCanonicalURLEnabled());
		Assert.assertEquals(
			StringPool.BLANK,
			layoutSEOEntry.getOpenGraphDescription(LocaleUtil.US));
		Assert.assertFalse(layoutSEOEntry.isOpenGraphDescriptionEnabled());
		Assert.assertEquals(0, layoutSEOEntry.getOpenGraphImageFileEntryId());
		Assert.assertEquals(
			StringPool.BLANK, layoutSEOEntry.getOpenGraphTitle(LocaleUtil.US));
		Assert.assertFalse(layoutSEOEntry.isOpenGraphTitleEnabled());
		Assert.assertEquals(0, layoutSEOEntry.getDDMStorageId());
	}

	@Test
	public void testUpdateLayoutSEOEntryWithAllFields() throws PortalException {
		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			_layout.getLayoutId(), false,
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			_layout.getLayoutId(), true,
			Collections.singletonMap(LocaleUtil.US, "http://example.com"), true,
			Collections.singletonMap(LocaleUtil.US, "description"),
			Collections.singletonMap(LocaleUtil.US, "image alt"), 12345, true,
			Collections.singletonMap(LocaleUtil.US, "title"),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		LayoutSEOEntry layoutSEOEntry =
			_layoutSEOEntryLocalService.fetchLayoutSEOEntry(
				_group.getGroupId(), false, _layout.getLayoutId());

		Assert.assertEquals(
			"http://example.com",
			layoutSEOEntry.getCanonicalURL(LocaleUtil.US));
		Assert.assertTrue(layoutSEOEntry.isCanonicalURLEnabled());
		Assert.assertEquals(
			"description",
			layoutSEOEntry.getOpenGraphDescription(LocaleUtil.US));
		Assert.assertTrue(layoutSEOEntry.isOpenGraphDescriptionEnabled());
		Assert.assertEquals(
			12345, layoutSEOEntry.getOpenGraphImageFileEntryId());
		Assert.assertEquals(
			"title", layoutSEOEntry.getOpenGraphTitle(LocaleUtil.US));
		Assert.assertTrue(layoutSEOEntry.isOpenGraphTitleEnabled());
		Assert.assertEquals(0, layoutSEOEntry.getDDMStorageId());
	}

	private long _getDDMStructureId() throws PortalException {
		Group companyGroup = _groupLocalService.getCompanyGroup(
			TestPropsValues.getCompanyId());

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			companyGroup.getGroupId(),
			_classNameLocalService.getClassNameId(
				LayoutSEOEntry.class.getName()),
			"custom-meta-tags");

		return ddmStructure.getStructureId();
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private DDM _ddm;

	@Inject
	private DDMStructureLocalService _ddmStructureLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutSEOEntryLocalService _layoutSEOEntryLocalService;

}