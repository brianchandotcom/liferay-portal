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

package com.liferay.fragment.service.test;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.configuration.FragmentServiceConfiguration;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.util.FragmentTestUtil;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.settings.definition.ConfigurationBeanDeclaration;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.InputStream;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Kyle Miho
 */
@RunWith(Arquillian.class)
public class FragmentEntryLinkLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypeContentLayout(_group);

		_fragmentCollection = FragmentTestUtil.addFragmentCollection(
			_group.getGroupId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), TestPropsValues.getUserId());

		_serviceContext.setRequest(_getMockHttpServletRequest());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);

		_fragmentEntry = _fragmentEntryLocalService.addFragmentEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_fragmentCollection.getFragmentCollectionId(), null,
			"Fragment Name", StringPool.BLANK, "<div>test</div>",
			StringPool.BLANK, false, _read("configuration-light.json"), null, 0,
			FragmentConstants.TYPE_SECTION, WorkflowConstants.STATUS_APPROVED,
			_serviceContext);

		_fragmentEntryWithFreeMarker =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), null,
				"Fragment Name", StringPool.BLANK,
				_read("fragment-freemarker.html"), StringPool.BLANK, false,
				_read("configuration-light.json"), null, 0,
				FragmentConstants.TYPE_SECTION,
				WorkflowConstants.STATUS_APPROVED, _serviceContext);

		_objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
			}
		};
	}

	@After
	public void tearDown() throws Exception {
		_configurationProvider.saveCompanyConfiguration(
			FragmentServiceConfiguration.class, _group.getCompanyId(),
			HashMapDictionaryBuilder.<String, Object>put(
				"propagateChanges", false
			).build());

		_setFreeMarkerEnabled(true);
	}

	@Test
	public void testAddFragmentEntryLink() throws Exception {
		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				_fragmentEntry.getFragmentEntryId(), 0, _layout.getPlid(),
				_fragmentEntry.getCss(), _fragmentEntry.getHtml(),
				_fragmentEntry.getJs(), _fragmentEntry.getConfiguration(),
				_read("editable-values-light-modified.json"), StringPool.BLANK,
				0, null, _serviceContext);

		Assert.assertNotNull(
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLink.getFragmentEntryLinkId()));

		Assert.assertEquals(_layout.getPlid(), fragmentEntryLink.getPlid());

		Assert.assertEquals(
			_fragmentEntry.getCss(), fragmentEntryLink.getCss());

		Assert.assertEquals(_fragmentEntry.getJs(), fragmentEntryLink.getJs());

		Assert.assertEquals(
			_fragmentEntry.getConfiguration(),
			fragmentEntryLink.getConfiguration());

		Assert.assertEquals(
			_fragmentEntry.getHtml(), fragmentEntryLink.getHtml());

		Assert.assertEquals(
			_read("expected-editable-values-light-modified.json"),
			fragmentEntryLink.getEditableValues());
	}

	@Test
	public void testAddFragmentEntryLinkWithFreeMarkerDisabledEmptyRendererKey()
		throws Exception {

		_setFreeMarkerEnabled(false);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				_fragmentEntryWithFreeMarker.getFragmentEntryId(), 0,
				_layout.getPlid(), _fragmentEntryWithFreeMarker.getCss(),
				_fragmentEntryWithFreeMarker.getHtml(),
				_fragmentEntryWithFreeMarker.getJs(),
				_fragmentEntryWithFreeMarker.getConfiguration(),
				StringPool.BLANK, StringPool.BLANK, 0, null, _serviceContext);

		Assert.assertNotNull(
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLink.getFragmentEntryLinkId()));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			fragmentEntryLink.getEditableValues());

		JSONObject editableJSONObject = jsonObject.getJSONObject(
			_EDITABLE_PROCESSOR_KEY);

		Assert.assertEquals(1, editableJSONObject.length());
	}

	@Test
	public void testAddFragmentEntryLinkWithFreeMarkerDisabledNotEmptyRendererKey()
		throws Exception {

		_setFreeMarkerEnabled(false);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				_fragmentEntryWithFreeMarker.getFragmentEntryId(), 0,
				_layout.getPlid(), _fragmentEntryWithFreeMarker.getCss(),
				_fragmentEntryWithFreeMarker.getHtml(),
				_fragmentEntryWithFreeMarker.getJs(),
				_fragmentEntryWithFreeMarker.getConfiguration(),
				StringPool.BLANK, StringPool.BLANK, 0, "TABS", _serviceContext);

		Assert.assertNotNull(
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLink.getFragmentEntryLinkId()));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			fragmentEntryLink.getEditableValues());

		JSONObject editableJSONObject = jsonObject.getJSONObject(
			_EDITABLE_PROCESSOR_KEY);

		Assert.assertEquals(3, editableJSONObject.length());
	}

	@Test
	public void testAddFragmentEntryLinkWithFreeMarkerEnabledEmptyRendererKey()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				_fragmentEntryWithFreeMarker.getFragmentEntryId(), 0,
				_layout.getPlid(), _fragmentEntryWithFreeMarker.getCss(),
				_fragmentEntryWithFreeMarker.getHtml(),
				_fragmentEntryWithFreeMarker.getJs(),
				_fragmentEntryWithFreeMarker.getConfiguration(),
				StringPool.BLANK, StringPool.BLANK, 0, null, _serviceContext);

		Assert.assertNotNull(
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLink.getFragmentEntryLinkId()));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			fragmentEntryLink.getEditableValues());

		JSONObject editableJSONObject = jsonObject.getJSONObject(
			_EDITABLE_PROCESSOR_KEY);

		Assert.assertEquals(3, editableJSONObject.length());
	}

	@Test
	public void testAddFragmentEntryLinkWithFreeMarkerEnabledNotEmptyRendererKey()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				_fragmentEntryWithFreeMarker.getFragmentEntryId(), 0,
				_layout.getPlid(), _fragmentEntryWithFreeMarker.getCss(),
				_fragmentEntryWithFreeMarker.getHtml(),
				_fragmentEntryWithFreeMarker.getJs(),
				_fragmentEntryWithFreeMarker.getConfiguration(),
				StringPool.BLANK, StringPool.BLANK, 0, null, _serviceContext);

		Assert.assertNotNull(
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLink.getFragmentEntryLinkId()));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			fragmentEntryLink.getEditableValues());

		JSONObject editableJSONObject = jsonObject.getJSONObject(
			_EDITABLE_PROCESSOR_KEY);

		Assert.assertEquals(3, editableJSONObject.length());
	}

	@Test
	public void testAddMultipleFragmentEntryLinks() throws PortalException {
		List<FragmentEntryLink> originalFragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinks(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		_fragmentEntryLinkLocalService.addFragmentEntryLink(
			TestPropsValues.getUserId(), _group.getGroupId(), 0,
			_fragmentEntry.getFragmentEntryId(), 0, _layout.getPlid(),
			_fragmentEntry.getCss(), _fragmentEntry.getHtml(),
			_fragmentEntry.getJs(), _fragmentEntry.getConfiguration(),
			StringPool.BLANK, StringPool.BLANK, 0, null, _serviceContext);

		_fragmentEntryLinkLocalService.addFragmentEntryLink(
			TestPropsValues.getUserId(), _group.getGroupId(), 0,
			_fragmentEntry.getFragmentEntryId(), 0, _layout.getPlid(),
			_fragmentEntry.getCss(), _fragmentEntry.getHtml(),
			_fragmentEntry.getJs(), _fragmentEntry.getConfiguration(),
			StringPool.BLANK, StringPool.BLANK, 1, null, _serviceContext);

		List<FragmentEntryLink> actualFragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinks(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			actualFragmentEntryLinks.toString(),
			originalFragmentEntryLinks.size() + 2,
			actualFragmentEntryLinks.size());
	}

	@Test
	public void testDeleteFragmentEntryLink() throws PortalException {
		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				_fragmentEntry.getFragmentEntryId(), 0, _layout.getPlid(),
				_fragmentEntry.getCss(), _fragmentEntry.getHtml(),
				_fragmentEntry.getJs(), _fragmentEntry.getConfiguration(),
				StringPool.BLANK, StringPool.BLANK, 0, null, _serviceContext);

		_fragmentEntryLinkLocalService.deleteFragmentEntryLink(
			fragmentEntryLink.getFragmentEntryLinkId());

		Assert.assertNull(
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLink.getFragmentEntryLinkId()));
	}

	@Test
	public void testDeleteFragmentEntryLinks() throws PortalException {
		FragmentEntryLink fragmentEntryLink1 =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				_fragmentEntry.getFragmentEntryId(), 0, _layout.getPlid(),
				_fragmentEntry.getCss(), _fragmentEntry.getHtml(),
				_fragmentEntry.getJs(), _fragmentEntry.getConfiguration(),
				StringPool.BLANK, StringPool.BLANK, 0, null, _serviceContext);
		FragmentEntryLink fragmentEntryLink2 =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				_fragmentEntry.getFragmentEntryId(), 0, _layout.getPlid(),
				_fragmentEntry.getCss(), _fragmentEntry.getHtml(),
				_fragmentEntry.getJs(), _fragmentEntry.getConfiguration(),
				StringPool.BLANK, StringPool.BLANK, 0, null, _serviceContext);

		_fragmentEntryLinkLocalService.deleteFragmentEntryLinks(
			_group.getGroupId());

		Assert.assertNull(
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLink1.getFragmentEntryLinkId()));
		Assert.assertNull(
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLink2.getFragmentEntryLinkId()));
	}

	@Test
	public void testGetAllFragmentEntryLinksByFragmentEntryId()
		throws Exception {

		FragmentEntryLink fragmentEntryLink1 = _addFragmentEntryLinkToLayout();
		FragmentEntryLink fragmentEntryLink2 =
			_addFragmentEntryLinkToLayoutPageTemplateEntry();

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.
				getAllFragmentEntryLinksByFragmentEntryId(
					_group.getGroupId(), _fragmentEntry.getFragmentEntryId(),
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertTrue(fragmentEntryLinks.contains(fragmentEntryLink1));
		Assert.assertTrue(fragmentEntryLinks.contains(fragmentEntryLink2));
	}

	@Test
	public void testGetAllFragmentEntryLinksCountByFragmentEntryId()
		throws Exception {

		_addFragmentEntryLinkToLayout();
		_addFragmentEntryLinkToLayoutPageTemplateEntry();

		Assert.assertEquals(
			2,
			_fragmentEntryLinkLocalService.
				getAllFragmentEntryLinksCountByFragmentEntryId(
					_group.getGroupId(), _fragmentEntry.getFragmentEntryId()));
	}

	@Test
	public void testGetLayoutFragmentEntryLinksByFragmentEntryId()
		throws Exception {

		FragmentEntryLink fragmentEntryLink1 = _addFragmentEntryLinkToLayout();
		FragmentEntryLink fragmentEntryLink2 =
			_addFragmentEntryLinkToLayoutPageTemplateEntry();

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.
				getLayoutFragmentEntryLinksByFragmentEntryId(
					_group.getGroupId(), _fragmentEntry.getFragmentEntryId(),
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertTrue(fragmentEntryLinks.contains(fragmentEntryLink1));
		Assert.assertFalse(fragmentEntryLinks.contains(fragmentEntryLink2));
	}

	@Test
	public void testGetLayoutFragmentEntryLinksCountByFragmentEntryId()
		throws Exception {

		_addFragmentEntryLinkToLayout();
		_addFragmentEntryLinkToLayoutPageTemplateEntry();

		Assert.assertEquals(
			1,
			_fragmentEntryLinkLocalService.
				getLayoutFragmentEntryLinksCountByFragmentEntryId(
					_group.getGroupId(), _fragmentEntry.getFragmentEntryId()));
	}

	@Test
	public void testGetLayoutPageTemplateFragmentEntryLinksByFragmentEntryId()
		throws Exception {

		FragmentEntryLink fragmentEntryLink1 = _addFragmentEntryLinkToLayout();
		FragmentEntryLink fragmentEntryLink2 =
			_addFragmentEntryLinkToLayoutPageTemplateEntry();

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.
				getLayoutPageTemplateFragmentEntryLinksByFragmentEntryId(
					_group.getGroupId(), _fragmentEntry.getFragmentEntryId(),
					LayoutPageTemplateEntryTypeConstants.TYPE_BASIC,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertFalse(fragmentEntryLinks.contains(fragmentEntryLink1));
		Assert.assertTrue(fragmentEntryLinks.contains(fragmentEntryLink2));
	}

	@Test
	public void testGetLayoutPageTemplateFragmentEntryLinksCountByFragmentEntryId()
		throws Exception {

		_addFragmentEntryLinkToLayout();
		_addFragmentEntryLinkToLayoutPageTemplateEntry();

		Assert.assertEquals(
			1,
			_fragmentEntryLinkLocalService.
				getLayoutPageTemplateFragmentEntryLinksCountByFragmentEntryId(
					_group.getGroupId(), _fragmentEntry.getFragmentEntryId(),
					LayoutPageTemplateEntryTypeConstants.TYPE_BASIC));
	}

	@Test
	public void testUpdateFragmentEntryLinkPosition() throws PortalException {
		FragmentEntryLink fragmentEntryLink1 =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				_fragmentEntry.getFragmentEntryId(), 0, _layout.getPlid(),
				_fragmentEntry.getCss(), _fragmentEntry.getHtml(),
				_fragmentEntry.getJs(), _fragmentEntry.getConfiguration(),
				StringPool.BLANK, StringPool.BLANK, 1, null, _serviceContext);
		FragmentEntryLink fragmentEntryLink2 =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				_fragmentEntry.getFragmentEntryId(), 0, _layout.getPlid(),
				_fragmentEntry.getCss(), _fragmentEntry.getHtml(),
				_fragmentEntry.getJs(), _fragmentEntry.getConfiguration(),
				StringPool.BLANK, StringPool.BLANK, 2, null, _serviceContext);

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinksByPlid(
				_group.getGroupId(), _layout.getPlid());

		Assert.assertEquals(
			fragmentEntryLinks.toString(), 2, fragmentEntryLinks.size());
		Assert.assertEquals(fragmentEntryLink1, fragmentEntryLinks.get(0));
		Assert.assertEquals(fragmentEntryLink2, fragmentEntryLinks.get(1));

		fragmentEntryLink1 =
			_fragmentEntryLinkLocalService.updateFragmentEntryLink(
				fragmentEntryLink1.getFragmentEntryLinkId(), 3);

		Assert.assertEquals(3, fragmentEntryLink1.getPosition());

		fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinksByPlid(
				_group.getGroupId(), _layout.getPlid());

		Assert.assertEquals(
			fragmentEntryLinks.toString(), 2, fragmentEntryLinks.size());
		Assert.assertEquals(fragmentEntryLink2, fragmentEntryLinks.get(0));
		Assert.assertEquals(fragmentEntryLink1, fragmentEntryLinks.get(1));
	}

	@Test
	public void testUpdateFragmentEntryLinkWithoutPropagation()
		throws Exception {

		_configurationProvider.saveCompanyConfiguration(
			FragmentServiceConfiguration.class, _group.getCompanyId(),
			HashMapDictionaryBuilder.<String, Object>put(
				"propagateChanges", false
			).build());

		String configuration = _read("configuration-light.json");

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), null,
				"Fragment Name", RandomTestUtil.randomString(),
				"<div>test</div>", RandomTestUtil.randomString(), false,
				configuration, null, 0, FragmentConstants.TYPE_SECTION,
				WorkflowConstants.STATUS_APPROVED, _serviceContext);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(), 0, _layout.getPlid(),
				fragmentEntry.getCss(), fragmentEntry.getHtml(),
				fragmentEntry.getJs(), fragmentEntry.getConfiguration(),
				_read("editable-values-light-modified.json"), StringPool.BLANK,
				0, null, _serviceContext);

		_fragmentEntryLocalService.updateFragmentEntry(
			TestPropsValues.getUserId(), fragmentEntry.getFragmentEntryId(),
			fragmentEntry.getName(), StringPool.BLANK,
			StringUtil.randomString(), StringUtil.randomString(),
			_read("configuration-dark.json"),
			fragmentEntry.getPreviewFileEntryId(),
			WorkflowConstants.STATUS_APPROVED);

		fragmentEntryLink = _fragmentEntryLinkLocalService.getFragmentEntryLink(
			fragmentEntryLink.getFragmentEntryLinkId());

		Assert.assertEquals(
			fragmentEntry.getHtml(), fragmentEntryLink.getHtml());
		Assert.assertEquals(fragmentEntry.getCss(), fragmentEntryLink.getCss());
		Assert.assertEquals(fragmentEntry.getJs(), fragmentEntryLink.getJs());
		Assert.assertEquals(
			fragmentEntry.getConfiguration(),
			fragmentEntryLink.getConfiguration());

		Assert.assertEquals(
			configuration, fragmentEntryLink.getConfiguration());

		Assert.assertEquals(
			_read("expected-editable-values-light-modified.json"),
			fragmentEntryLink.getEditableValues());
	}

	@Test
	public void testUpdateFragmentEntryLinkWithPropagation() throws Exception {
		_configurationProvider.saveCompanyConfiguration(
			FragmentServiceConfiguration.class, _group.getCompanyId(),
			HashMapDictionaryBuilder.<String, Object>put(
				"propagateChanges", true
			).build());

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), null,
				"Fragment Name", StringPool.BLANK, StringUtil.randomString(),
				StringPool.BLANK, false, _read("configuration-light.json"),
				null, 0, 0, WorkflowConstants.STATUS_APPROVED, _serviceContext);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(), 0, _layout.getPlid(),
				fragmentEntry.getCss(), fragmentEntry.getHtml(),
				fragmentEntry.getJs(), fragmentEntry.getConfiguration(),
				_read("editable-values-light-modified.json"), StringPool.BLANK,
				0, null, _serviceContext);

		String newCSS = StringUtil.randomString();
		String newHTML = StringUtil.randomString();
		String newJS = StringUtil.randomString();

		String newConfiguration = _read("configuration-dark.json");

		_fragmentEntryLocalService.updateFragmentEntry(
			TestPropsValues.getUserId(), fragmentEntry.getFragmentEntryId(),
			"Fragment Name", newCSS, newHTML, newJS, newConfiguration,
			fragmentEntry.getPreviewFileEntryId(),
			WorkflowConstants.STATUS_APPROVED);

		fragmentEntryLink = _fragmentEntryLinkLocalService.getFragmentEntryLink(
			fragmentEntryLink.getFragmentEntryLinkId());

		Assert.assertEquals(newCSS, fragmentEntryLink.getCss());
		Assert.assertEquals(newHTML, fragmentEntryLink.getHtml());
		Assert.assertEquals(newJS, fragmentEntryLink.getJs());
		Assert.assertEquals(
			newConfiguration, fragmentEntryLink.getConfiguration());

		JSONObject editableValuesJSONObject = JSONFactoryUtil.createJSONObject(
			fragmentEntryLink.getEditableValues());

		Assert.assertEquals(
			_objectMapper.readTree(
				_read("expected-editable-values-light-modified.json")),
			_objectMapper.readTree(editableValuesJSONObject.toString()));
	}

	@Test
	public void testUpdateFragmentEntryLinkWithPropagationAndNewConfigurationValues()
		throws Exception {

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), null,
				"Fragment Name", StringPool.BLANK,
				_read("fragment-configuration.html"), StringPool.BLANK, false,
				_read("configuration-new-field.json"), null, 0,
				FragmentConstants.TYPE_COMPONENT,
				WorkflowConstants.STATUS_APPROVED, _serviceContext);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(), 0, _layout.getPlid(),
				fragmentEntry.getCss(), fragmentEntry.getHtml(),
				fragmentEntry.getJs(), fragmentEntry.getConfiguration(),
				StringPool.BLANK, StringPool.BLANK, 0, null, _serviceContext);

		_fragmentEntryLocalService.updateFragmentEntry(
			TestPropsValues.getUserId(), fragmentEntry.getFragmentEntryId(),
			fragmentEntry.getName(), fragmentEntry.getCss(),
			_read("updated-fragment-configuration.html"), fragmentEntry.getJs(),
			_read("updated-configuration-new-field.json"),
			WorkflowConstants.STATUS_APPROVED);

		_fragmentEntryLinkLocalService.updateLatestChanges(
			fragmentEntryLink.getFragmentEntryLinkId());

		fragmentEntryLink =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLink.getFragmentEntryLinkId());

		Assert.assertEquals(
			_objectMapper.readTree(
				_read("updated-configuration-new-field.json")),
			_objectMapper.readTree(fragmentEntryLink.getConfiguration()));
	}

	@Test
	public void testUpdateFragmentEntryLinkWithPropagationAndNewEditableItems()
		throws Exception {

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), null,
				"Fragment Name", StringPool.BLANK,
				_read("fragment-editable.html"), StringPool.BLANK, false,
				StringPool.BLANK, null, 0, FragmentConstants.TYPE_COMPONENT,
				WorkflowConstants.STATUS_APPROVED, _serviceContext);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(), 0, _layout.getPlid(),
				fragmentEntry.getCss(), fragmentEntry.getHtml(),
				fragmentEntry.getJs(), fragmentEntry.getConfiguration(),
				StringPool.BLANK, StringPool.BLANK, 0, null, _serviceContext);

		_fragmentEntryLocalService.updateFragmentEntry(
			TestPropsValues.getUserId(), fragmentEntry.getFragmentEntryId(),
			fragmentEntry.getName(), fragmentEntry.getCss(),
			_read("updated-fragment-editable.html"), fragmentEntry.getJs(),
			fragmentEntry.getConfiguration(),
			WorkflowConstants.STATUS_APPROVED);

		_fragmentEntryLinkLocalService.updateLatestChanges(
			fragmentEntryLink.getFragmentEntryLinkId());

		fragmentEntryLink =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLink.getFragmentEntryLinkId());

		Assert.assertEquals(
			_objectMapper.readTree(
				_read("expected-editable-values-update-latest-changes.json")),
			_objectMapper.readTree(fragmentEntryLink.getEditableValues()));
	}

	@Test
	public void testUpdateFragmentEntryLinkWithPropagationAndUpdatedEditableItems()
		throws Exception {

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), null,
				"Fragment Name", StringPool.BLANK,
				_read("fragment-editable.html"), StringPool.BLANK, false,
				StringPool.BLANK, null, 0, FragmentConstants.TYPE_COMPONENT,
				WorkflowConstants.STATUS_APPROVED, _serviceContext);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(), 0, _layout.getPlid(),
				fragmentEntry.getCss(), fragmentEntry.getHtml(),
				fragmentEntry.getJs(), fragmentEntry.getConfiguration(),
				StringPool.BLANK, StringPool.BLANK, 0, null, _serviceContext);

		_fragmentEntryLinkLocalService.updateFragmentEntryLink(
			fragmentEntryLink.getFragmentEntryLinkId(),
			_read("updated-editable-values-update-latest-changes.json"));

		_fragmentEntryLocalService.updateFragmentEntry(
			TestPropsValues.getUserId(), fragmentEntry.getFragmentEntryId(),
			fragmentEntry.getName(), fragmentEntry.getCss(),
			_read("updated-fragment-editable.html"), fragmentEntry.getJs(),
			fragmentEntry.getConfiguration(),
			WorkflowConstants.STATUS_APPROVED);

		_fragmentEntryLinkLocalService.updateLatestChanges(
			fragmentEntryLink.getFragmentEntryLinkId());

		fragmentEntryLink =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLink.getFragmentEntryLinkId());

		Assert.assertEquals(
			_objectMapper.readTree(
				_read(
					"expected-updated-editable-values-update-latest-changes." +
						"json")),
			_objectMapper.readTree(fragmentEntryLink.getEditableValues()));
	}

	private FragmentEntryLink _addFragmentEntryLinkToLayout() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		return _fragmentEntryLinkLocalService.addFragmentEntryLink(
			TestPropsValues.getUserId(), _group.getGroupId(), 0,
			_fragmentEntry.getFragmentEntryId(), 0, layout.getPlid(),
			_fragmentEntry.getCss(), _fragmentEntry.getHtml(),
			_fragmentEntry.getJs(), _fragmentEntry.getConfiguration(),
			StringPool.BLANK, StringPool.BLANK, 0, null, _serviceContext);
	}

	private FragmentEntryLink _addFragmentEntryLinkToLayoutPageTemplateEntry()
		throws Exception {

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					TestPropsValues.getUserId(), _group.getGroupId(),
					"Page Template Collection", StringPool.BLANK,
					_serviceContext);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				"Page Template One",
				LayoutPageTemplateEntryTypeConstants.TYPE_BASIC, 0,
				WorkflowConstants.STATUS_APPROVED, _serviceContext);

		return _fragmentEntryLinkLocalService.addFragmentEntryLink(
			TestPropsValues.getUserId(), _group.getGroupId(), 0,
			_fragmentEntry.getFragmentEntryId(), 0,
			layoutPageTemplateEntry.getPlid(), _fragmentEntry.getCss(),
			_fragmentEntry.getHtml(), _fragmentEntry.getJs(),
			_fragmentEntry.getConfiguration(), StringPool.BLANK,
			StringPool.BLANK, 0, null, _serviceContext);
	}

	private MockHttpServletRequest _getMockHttpServletRequest()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLayout(_layout);

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			_group.getGroupId(), false);

		themeDisplay.setLookAndFeel(
			layoutSet.getTheme(), layoutSet.getColorScheme());

		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setUser(TestPropsValues.getUser());

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockHttpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE,
			new MockLiferayPortletActionResponse());

		return mockHttpServletRequest;
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	private void _setFreeMarkerEnabled(boolean freeMarkerEnabled)
		throws Exception {

		_configurationProvider.saveCompanyConfiguration(
			_configurationBeanDeclaration.getConfigurationBeanClass(),
			_group.getCompanyId(),
			HashMapDictionaryBuilder.<String, Object>put(
				"enable.freemarker", freeMarkerEnabled
			).build());

		Thread.sleep(200);
	}

	private static final String _EDITABLE_PROCESSOR_KEY =
		"com.liferay.fragment.entry.processor.editable." +
			"EditableFragmentEntryProcessor";

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(
		filter = "component.name=com.liferay.fragment.entry.processor.freemarker.internal.settings.definition.FreeMarkerFragmentEntryProcessorConfigurationBeanDeclaration"
	)
	private ConfigurationBeanDeclaration _configurationBeanDeclaration;

	@Inject
	private ConfigurationProvider _configurationProvider;

	private FragmentCollection _fragmentCollection;
	private FragmentEntry _fragmentEntry;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	private FragmentEntry _fragmentEntryWithFreeMarker;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

	private ObjectMapper _objectMapper;
	private ServiceContext _serviceContext;

}