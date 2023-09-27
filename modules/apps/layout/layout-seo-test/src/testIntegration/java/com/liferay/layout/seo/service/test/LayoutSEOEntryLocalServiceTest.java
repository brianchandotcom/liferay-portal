/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.DDMStorageEngineManager;
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

import java.util.Collections;
import java.util.List;

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

		LayoutSEOEntry layoutSEOEntry =
			_layoutSEOEntryLocalService.updateLayoutSEOEntry(
				TestPropsValues.getUserId(), _group.getGroupId(), false,
				_layout.getLayoutId(), false,
				Collections.singletonMap(LocaleUtil.US, "http://example.com"),
				true, Collections.singletonMap(LocaleUtil.US, "description"),
				Collections.singletonMap(LocaleUtil.US, "image alt"), 12345,
				true, Collections.singletonMap(LocaleUtil.US, "title"),
				serviceContext);

		_assertDDMFormValues(layoutSEOEntry.getDDMStorageId());
	}

	@Test
	public void testAddLayoutSEOEntryWithEmptyCustomTags() throws Exception {
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

		Assert.assertNull(
			_layoutSEOEntryLocalService.fetchLayoutSEOEntry(
				_group.getGroupId(), false, _layout.getLayoutId()));
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

	@Test
	public void testUpdateLayoutSEOEntryWithCustomTags() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		LayoutSEOEntry layoutSEOEntry =
			_layoutSEOEntryLocalService.updateLayoutSEOEntry(
				TestPropsValues.getUserId(), _group.getGroupId(), false,
				_layout.getLayoutId(), false,
				Collections.singletonMap(LocaleUtil.US, "http://example.com"),
				true, Collections.singletonMap(LocaleUtil.US, "description"),
				Collections.singletonMap(LocaleUtil.US, "image alt"), 12345,
				true, Collections.singletonMap(LocaleUtil.US, "title"),
				serviceContext);

		Assert.assertEquals(0, layoutSEOEntry.getDDMStorageId());

		serviceContext.setAttribute(
			_getDDMStructureId() + "ddmFormValues",
			new String(
				FileUtil.getBytes(
					getClass(),
					"dependencies/custom_meta_tags_ddm_form_values.json"),
				StandardCharsets.UTF_8));

		layoutSEOEntry = _layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			_layout.getLayoutId(), false,
			Collections.singletonMap(LocaleUtil.US, "http://example.com"), true,
			Collections.singletonMap(LocaleUtil.US, "description"),
			Collections.singletonMap(LocaleUtil.US, "image alt"), 12345, true,
			Collections.singletonMap(LocaleUtil.US, "title"), serviceContext);

		_assertDDMFormValues(layoutSEOEntry.getDDMStorageId());
	}

	@Test
	public void testUpdateLayoutSEOEntryWithEmptyCustomTags() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAttribute(
			_getDDMStructureId() + "ddmFormValues",
			new String(
				FileUtil.getBytes(
					getClass(),
					"dependencies/custom_meta_tags_ddm_form_values.json"),
				StandardCharsets.UTF_8));

		LayoutSEOEntry layoutSEOEntry =
			_layoutSEOEntryLocalService.updateLayoutSEOEntry(
				TestPropsValues.getUserId(), _group.getGroupId(), false,
				_layout.getLayoutId(), false,
				Collections.singletonMap(LocaleUtil.US, "http://example.com"),
				true, Collections.singletonMap(LocaleUtil.US, "description"),
				Collections.singletonMap(LocaleUtil.US, "image alt"), 12345,
				true, Collections.singletonMap(LocaleUtil.US, "title"),
				serviceContext);

		Assert.assertNotEquals(0, layoutSEOEntry.getDDMStorageId());

		serviceContext.setAttribute(
			_getDDMStructureId() + "ddmFormValues",
			new String(
				FileUtil.getBytes(
					getClass(),
					"dependencies/empty_custom_meta_tags_ddm_form_values.json"),
				StandardCharsets.UTF_8));

		layoutSEOEntry = _layoutSEOEntryLocalService.updateCustomMetaTags(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			_layout.getLayoutId(), serviceContext);

		Assert.assertEquals(0, layoutSEOEntry.getDDMStorageId());
	}

	private void _assertDDMFormFieldValueEquals(
		String expected, DDMFormFieldValue ddmFormFieldValue) {

		Value value = ddmFormFieldValue.getValue();

		Assert.assertEquals(expected, value.getString(LocaleUtil.US));
	}

	private void _assertDDMFormValues(long ddmStorageId) throws Exception {
		DDMFormValues ddmFormValues = _ddmStorageEngineManager.getDDMFormValues(
			ddmStorageId);

		Assert.assertNotNull(ddmFormValues);

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		Assert.assertEquals(
			ddmFormFieldValues.toString(), 2, ddmFormFieldValues.size());

		DDMFormFieldValue firstDDMFormFieldValue = ddmFormFieldValues.get(0);

		_assertDDMFormFieldValueEquals("property1", firstDDMFormFieldValue);

		List<DDMFormFieldValue> firstNestedDDMFormFieldValues =
			firstDDMFormFieldValue.getNestedDDMFormFieldValues();

		_assertDDMFormFieldValueEquals(
			"content1", firstNestedDDMFormFieldValues.get(0));

		DDMFormFieldValue secondDDMFormFieldValue = ddmFormFieldValues.get(1);

		_assertDDMFormFieldValueEquals("property2", secondDDMFormFieldValue);

		List<DDMFormFieldValue> secondNestedDDMFormFieldValues =
			secondDDMFormFieldValue.getNestedDDMFormFieldValues();

		_assertDDMFormFieldValueEquals(
			"content2", secondNestedDDMFormFieldValues.get(0));
	}

	private long _getDDMStructureId() throws Exception {
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
	private DDMStorageEngineManager _ddmStorageEngineManager;

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