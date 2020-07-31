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

package com.liferay.layout.page.template.internal.importer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateExportImportConstants;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporter;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporterResultEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.structure.CollectionLayoutStructureItem;
import com.liferay.layout.util.structure.ColumnLayoutStructureItem;
import com.liferay.layout.util.structure.ContainerLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.RowLayoutStructureItem;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.permission.PortletPermission;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import javax.portlet.GenericPortlet;
import javax.portlet.Portlet;

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
import org.osgi.framework.ServiceRegistration;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class LayoutPageTemplatesImporterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_bundle = FrameworkUtil.getBundle(getClass());

		_bundleContext = _bundle.getBundleContext();

		_group = GroupTestUtil.addGroup();
		_testPortletName = "TEST_PORTLET_" + RandomTestUtil.randomString();
		_user = TestPropsValues.getUser();
	}

	@After
	public void tearDown() {
		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}

		_serviceRegistrations.clear();
	}

	@Test
	public void testImportEmptyLayoutPageTemplateEntryCollection()
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getImportLayoutPageTemplateEntry("collection", new HashMap<>());

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					_group.getGroupId(), layoutPageTemplateEntry.getPlid());

		Assert.assertNotNull(layoutPageTemplateStructure);

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(0));

		LayoutStructureItem layoutStructureItem =
			_getMainChildLayoutStructureItem(layoutStructure);

		Assert.assertTrue(
			layoutStructureItem instanceof CollectionLayoutStructureItem);

		CollectionLayoutStructureItem collectionLayoutStructureItem =
			(CollectionLayoutStructureItem)layoutStructureItem;

		Assert.assertNotNull(collectionLayoutStructureItem);

		Assert.assertEquals(
			2, collectionLayoutStructureItem.getNumberOfColumns());
		Assert.assertEquals(
			4, collectionLayoutStructureItem.getNumberOfItems());
	}

	@Test
	public void testImportEmptyLayoutPageTemplateEntryRow() throws Exception {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getImportLayoutPageTemplateEntry("row", new HashMap<>());

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					_group.getGroupId(), layoutPageTemplateEntry.getPlid());

		Assert.assertNotNull(layoutPageTemplateStructure);

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(0));

		LayoutStructureItem layoutStructureItem =
			_getMainChildLayoutStructureItem(layoutStructure);

		Assert.assertTrue(
			layoutStructureItem instanceof RowLayoutStructureItem);

		RowLayoutStructureItem rowLayoutStructureItem =
			(RowLayoutStructureItem)layoutStructureItem;

		Assert.assertNotNull(rowLayoutStructureItem);

		Assert.assertEquals(6, rowLayoutStructureItem.getNumberOfColumns());
		Assert.assertFalse(rowLayoutStructureItem.isGutters());

		List<String> rowChildrenItemsIds =
			rowLayoutStructureItem.getChildrenItemIds();

		Assert.assertEquals(
			rowChildrenItemsIds.toString(), 6, rowChildrenItemsIds.size());

		for (String rowChildItemId : rowChildrenItemsIds) {
			LayoutStructureItem childLayoutStructureItem =
				layoutStructure.getLayoutStructureItem(rowChildItemId);

			Assert.assertEquals(
				LayoutDataItemTypeConstants.TYPE_COLUMN,
				childLayoutStructureItem.getItemType());

			ColumnLayoutStructureItem columnLayoutStructureItem =
				(ColumnLayoutStructureItem)childLayoutStructureItem;

			Assert.assertEquals(2, columnLayoutStructureItem.getSize());
		}
	}

	@Test
	public void testImportEmptyLayoutPageTemplateEntrySection()
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getImportLayoutPageTemplateEntry("section", new HashMap<>());

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					_group.getGroupId(), layoutPageTemplateEntry.getPlid());

		Assert.assertNotNull(layoutPageTemplateStructure);

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(0));

		LayoutStructureItem layoutStructureItem =
			_getMainChildLayoutStructureItem(layoutStructure);

		Assert.assertTrue(
			layoutStructureItem instanceof ContainerLayoutStructureItem);

		ContainerLayoutStructureItem containerLayoutStructureItem =
			(ContainerLayoutStructureItem)layoutStructureItem;

		Assert.assertNotNull(layoutStructure);

		Assert.assertEquals(
			"fluid", containerLayoutStructureItem.getContainerType());
		Assert.assertEquals(5, containerLayoutStructureItem.getPaddingBottom());
		Assert.assertEquals(5, containerLayoutStructureItem.getPaddingLeft());
		Assert.assertEquals(0, containerLayoutStructureItem.getMarginRight());
		Assert.assertEquals(5, containerLayoutStructureItem.getPaddingTop());
		Assert.assertEquals(
			"fluid", containerLayoutStructureItem.getWidthType());

		JSONObject jsonObject =
			containerLayoutStructureItem.getBackgroundImageJSONObject();

		Assert.assertEquals("test.jpg", jsonObject.get("title"));
		Assert.assertEquals("test-image.jpg", jsonObject.get("url"));
	}

	@Test
	public void testImportEmptyLayoutPageTemplateEntryWidget()
		throws Exception {

		_registerTestPortlet(_testPortletName);

		String configProperty1 = RandomTestUtil.randomString();
		String configProperty2 = RandomTestUtil.randomString();

		Role role = _roleLocalService.getDefaultGroupRole(_group.getGroupId());

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getImportLayoutPageTemplateEntry(
				"widget",
				HashMapBuilder.put(
					"CONFIG_PROPERTY_1", configProperty1
				).put(
					"CONFIG_PROPERTY_2", configProperty2
				).put(
					"ROLE_KEY", role.getName()
				).put(
					"WIDGET_NAME", _testPortletName
				).build());

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					_group.getGroupId(), layoutPageTemplateEntry.getPlid());

		Assert.assertNotNull(layoutPageTemplateStructure);

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(0));

		LayoutStructureItem layoutStructureItem =
			_getMainChildLayoutStructureItem(layoutStructure);

		Assert.assertTrue(
			layoutStructureItem instanceof FragmentLayoutStructureItem);

		FragmentLayoutStructureItem fragmentLayoutStructureItem =
			(FragmentLayoutStructureItem)layoutStructureItem;

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentLayoutStructureItem.getFragmentEntryLinkId());

		Assert.assertNotNull(fragmentEntryLink);

		JSONObject editableValues = JSONFactoryUtil.createJSONObject(
			fragmentEntryLink.getEditableValues());

		String portletId = editableValues.getString("portletId");

		Assert.assertEquals(_testPortletName, portletId);

		String instanceId = editableValues.getString("instanceId");

		Assert.assertNotNull(instanceId);

		PortletPreferences portletPreferences =
			_portletPreferencesLocalService.fetchPortletPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
				layoutPageTemplateEntry.getPlid(),
				PortletIdCodec.encode(portletId, instanceId));

		javax.portlet.PortletPreferences jxPortletPreferences =
			PortletPreferencesFactoryUtil.fromDefaultXML(
				portletPreferences.getPreferences());

		Assert.assertEquals(
			configProperty1,
			jxPortletPreferences.getValue("config-property-1", null));

		Assert.assertEquals(
			configProperty2,
			jxPortletPreferences.getValue("config-property-2", null));

		String resourcePrimKey = _portletPermission.getPrimaryKey(
			layoutPageTemplateEntry.getPlid(),
			PortletIdCodec.encode(portletId, instanceId));

		List<ResourcePermission> resourcePermissions =
			_resourcePermissionLocalService.getResourcePermissions(
				layoutPageTemplateEntry.getCompanyId(), _testPortletName,
				ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey);

		Assert.assertEquals(
			resourcePermissions.toString(), 1, resourcePermissions.size());

		ResourcePermission resourcePermission = resourcePermissions.get(0);

		Assert.assertEquals(role.getRoleId(), resourcePermission.getRoleId());

		List<ResourceAction> resourceActions =
			_resourceActionLocalService.getResourceActions(_testPortletName);

		Stream<ResourceAction> stream = resourceActions.stream();

		ResourceAction resourceAction = stream.filter(
			resourceAction1 -> Objects.equals(
				resourceAction1.getActionId(), "VIEW")
		).findFirst(
		).orElse(
			null
		);

		Assert.assertNotNull(resourceAction);

		long bitwiseValue = resourceAction.getBitwiseValue();

		Assert.assertTrue(
			(resourcePermission.getActionIds() & bitwiseValue) == bitwiseValue);
	}

	@Test
	public void testImportLayoutPageTemplateEntryHTMLFragment()
		throws Exception {

		String html =
			"<lfr-editable id=\"element-html\" type=\"html\">\n\t\t<h1>" +
				"\n\t\t\tEdited HTML\n\t\t</h1>\n\n\t\t<p>\n\t\t\tEdited " +
					"<strong>paragraph</strong>.\n\t\t</p>\n\t</lfr-editable>";

		_createFragmentEntry("test-html-fragment", "Test HTML Fragment", html);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getImportLayoutPageTemplateEntry("html-fragment", new HashMap<>());

		FragmentEntryLink fragmentEntryLink = _getFragmentEntryLink(
			layoutPageTemplateEntry);

		_validateHTMLFragmentEntryLinkEditableValues(
			fragmentEntryLink.getEditableValues());
	}

	@Test
	public void testImportLayoutPageTemplateEntryImageFragment()
		throws Exception {

		String html =
			"<lfr-editable id=\"element-image\" type=\"image\"><img src=\"#\"" +
				"</lfr-editable>";

		_createFragmentEntry(
			"test-image-fragment", "Test Image Fragment", html);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getImportLayoutPageTemplateEntry(
				"image-fragment", new HashMap<>());

		FragmentEntryLink fragmentEntryLink = _getFragmentEntryLink(
			layoutPageTemplateEntry);

		_validateImageFragmentEntryLinkEditableValues(
			fragmentEntryLink.getEditableValues());
	}

	@Test
	public void testImportLayoutPageTemplateEntryLinkFragment()
		throws Exception {

		String html =
			"<lfr-editable id=\"element-link\" type=\"link\"><a href=\"\">" +
				"Go Somewhere</a></lfr-editable>";

		_createFragmentEntry("test-link-fragment", "Test Link Fragment", html);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getImportLayoutPageTemplateEntry("link-fragment", new HashMap<>());

		FragmentEntryLink fragmentEntryLink = _getFragmentEntryLink(
			layoutPageTemplateEntry);

		_validateLinkFragmentEntryLinkEditableValues(
			fragmentEntryLink.getEditableValues());
	}

	@Test
	public void testImportLayoutPageTemplateEntryTextFragment()
		throws Exception {

		String html =
			"<lfr-editable id=\"element-text\" type=\"text\">Test Text " +
				"Fragment</lfr-editable>";

		_createFragmentEntry("test-text-fragment", "Test Text Fragment", html);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getImportLayoutPageTemplateEntry("text-fragment", new HashMap<>());

		FragmentEntryLink fragmentEntryLink = _getFragmentEntryLink(
			layoutPageTemplateEntry);

		_validateTextFragmentEntryLinkEditableValues(
			fragmentEntryLink.getEditableValues());
	}

	@Test
	public void testImportLayoutPageTemplates() throws Exception {
		List<LayoutPageTemplatesImporterResultEntry>
			layoutPageTemplatesImporterResultEntries =
				_getLayoutPageTemplatesImporterResultEntries(
					"layout-page-template-multiple", new HashMap<>());

		Assert.assertEquals(
			layoutPageTemplatesImporterResultEntries.toString(), 2,
			layoutPageTemplatesImporterResultEntries.size());

		LayoutPageTemplateEntry layoutPageTemplateEntry1 =
			_getLayoutPageTemplateEntry(
				layoutPageTemplatesImporterResultEntries, 0);
		LayoutPageTemplateEntry layoutPageTemplateEntry2 =
			_getLayoutPageTemplateEntry(
				layoutPageTemplatesImporterResultEntries, 1);

		List<String> actualLayoutPageTemplateEntryNames = ListUtil.sort(
			new ArrayList() {
				{
					add(layoutPageTemplateEntry1.getName());
					add(layoutPageTemplateEntry2.getName());
				}
			});

		Assert.assertArrayEquals(
			new String[] {
				"Layout Page Template One", "Layout Page Template Two"
			},
			actualLayoutPageTemplateEntryNames.toArray(new String[0]));
	}

	@Test
	public void testImportLayoutPageTemplateWithCustomLookAndFeel()
		throws Exception {

		List<LayoutPageTemplatesImporterResultEntry>
			layoutPageTemplatesImporterResultEntries =
				_getLayoutPageTemplatesImporterResultEntries(
					"layout-page-template-custom-look-and-feel",
					new HashMap<>());

		Assert.assertEquals(
			layoutPageTemplatesImporterResultEntries.toString(), 1,
			layoutPageTemplatesImporterResultEntries.size());

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getLayoutPageTemplateEntry(
				layoutPageTemplatesImporterResultEntries, 0);

		Layout layout = _layoutLocalService.fetchLayout(
			layoutPageTemplateEntry.getPlid());

		Assert.assertNotNull(layout);

		UnicodeProperties typeSettingsUnicodeProperties =
			layout.getTypeSettingsProperties();

		Assert.assertEquals(
			"false",
			typeSettingsUnicodeProperties.getProperty(
				"lfr-theme:regular:show-footer"));
		Assert.assertEquals(
			"false",
			typeSettingsUnicodeProperties.getProperty(
				"lfr-theme:regular:show-header"));
		Assert.assertEquals(
			"false",
			typeSettingsUnicodeProperties.getProperty(
				"lfr-theme:regular:show-header-search"));
		Assert.assertEquals(
			"true",
			typeSettingsUnicodeProperties.getProperty(
				"lfr-theme:regular:show-maximize-minimize-application-links"));
		Assert.assertEquals(
			"false",
			typeSettingsUnicodeProperties.getProperty(
				"lfr-theme:regular:wrap-widget-page-content"));
	}

	@Test
	public void testImportLayoutPageTemplateWithMasterPage() throws Exception {
		LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				"Test Master Page",
				LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT,
				WorkflowConstants.STATUS_DRAFT,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		List<LayoutPageTemplatesImporterResultEntry>
			layoutPageTemplatesImporterResultEntries =
				_getLayoutPageTemplatesImporterResultEntries(
					"layout-page-template-master-page", new HashMap<>());

		Assert.assertEquals(
			layoutPageTemplatesImporterResultEntries.toString(), 1,
			layoutPageTemplatesImporterResultEntries.size());

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getLayoutPageTemplateEntry(
				layoutPageTemplatesImporterResultEntries, 0);

		Layout layout = _layoutLocalService.fetchLayout(
			layoutPageTemplateEntry.getPlid());

		Assert.assertEquals(
			masterLayoutPageTemplateEntry.getPlid(),
			layout.getMasterLayoutPlid());
	}

	@Test
	public void testImportLayoutPageTemplateWithThumbnail() throws Exception {
		List<LayoutPageTemplatesImporterResultEntry>
			layoutPageTemplatesImporterResultEntries =
				_getLayoutPageTemplatesImporterResultEntries(
					"layout-page-template-thumbnail", new HashMap<>());

		Assert.assertEquals(
			layoutPageTemplatesImporterResultEntries.toString(), 1,
			layoutPageTemplatesImporterResultEntries.size());

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getLayoutPageTemplateEntry(
				layoutPageTemplatesImporterResultEntries, 0);

		FileEntry portletFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				layoutPageTemplateEntry.getPreviewFileEntryId());

		Assert.assertNotNull(portletFileEntry);
	}

	private void _addZipWriterEntry(
			ZipWriter zipWriter, URL url, Map<String, String> valuesMap)
		throws IOException {

		String entryPath = url.getPath();

		String zipPath = StringUtil.removeSubstring(
			entryPath, _LAYOUT_PATE_TEMPLATES_PATH);

		String content = StringUtil.read(url.openStream());

		zipWriter.addEntry(
			zipPath, StringUtil.replace(content, "${", "}", valuesMap));
	}

	private void _createFragmentEntry(String key, String name, String html)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				TestPropsValues.getUserId(), _group.getGroupId(),
				"Test Collection", StringPool.BLANK, serviceContext);

		_fragmentEntryLocalService.addFragmentEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			fragmentCollection.getFragmentCollectionId(), key, name,
			StringPool.BLANK, html, StringPool.BLANK, StringPool.BLANK, 0,
			FragmentConstants.TYPE_COMPONENT, WorkflowConstants.STATUS_APPROVED,
			serviceContext);
	}

	private File _generateZipFile(String type, Map<String, String> valuesMap)
		throws Exception {

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		StringBuilder sb = new StringBuilder(3);

		sb.append(_LAYOUT_PATE_TEMPLATES_PATH + type);
		sb.append(StringPool.FORWARD_SLASH + _ROOT_FOLDER);
		sb.append(StringPool.FORWARD_SLASH);

		Enumeration<URL> enumeration = _bundle.findEntries(
			sb.toString(),
			LayoutPageTemplateExportImportConstants.
				FILE_NAME_PAGE_TEMPLATE_COLLECTION,
			true);

		try {
			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				_populateZipWriter(zipWriter, url, valuesMap);
			}

			return zipWriter.getFile();
		}
		catch (Exception exception) {
			throw new Exception(exception);
		}
	}

	private FragmentEntryLink _getFragmentEntryLink(
		LayoutPageTemplateEntry layoutPageTemplateEntry) {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					_group.getGroupId(), layoutPageTemplateEntry.getPlid());

		Assert.assertNotNull(layoutPageTemplateStructure);

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(0));

		LayoutStructureItem layoutStructureItem =
			_getMainChildLayoutStructureItem(layoutStructure);

		Assert.assertTrue(
			layoutStructureItem instanceof FragmentLayoutStructureItem);

		FragmentLayoutStructureItem fragmentLayoutStructureItem =
			(FragmentLayoutStructureItem)layoutStructureItem;

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentLayoutStructureItem.getFragmentEntryLinkId());

		Assert.assertNotNull(fragmentEntryLink);

		return fragmentEntryLink;
	}

	private LayoutPageTemplateEntry _getImportLayoutPageTemplateEntry(
			String type, Map<String, String> valuesMap)
		throws Exception {

		File file = _generateZipFile(type, valuesMap);

		List<LayoutPageTemplatesImporterResultEntry>
			layoutPageTemplatesImporterResultEntries = null;

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		try {
			layoutPageTemplatesImporterResultEntries =
				_layoutPageTemplatesImporter.importFile(
					_user.getUserId(), _group.getGroupId(), 0, file, false);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		Assert.assertNotNull(layoutPageTemplatesImporterResultEntries);

		Assert.assertEquals(
			layoutPageTemplatesImporterResultEntries.toString(), 1,
			layoutPageTemplatesImporterResultEntries.size());

		LayoutPageTemplatesImporterResultEntry layoutPageTemplateImportEntry =
			layoutPageTemplatesImporterResultEntries.get(0);

		Assert.assertEquals(
			LayoutPageTemplatesImporterResultEntry.Status.IMPORTED,
			layoutPageTemplateImportEntry.getStatus());

		String layoutPageTemplateEntryKey = StringUtil.toLowerCase(
			layoutPageTemplateImportEntry.getName());

		layoutPageTemplateEntryKey = StringUtil.replace(
			layoutPageTemplateEntryKey, CharPool.SPACE, CharPool.DASH);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				_group.getGroupId(), layoutPageTemplateEntryKey);

		Assert.assertNotNull(layoutPageTemplateEntry);

		return layoutPageTemplateEntry;
	}

	private LayoutPageTemplateEntry _getLayoutPageTemplateEntry(
		List<LayoutPageTemplatesImporterResultEntry>
			layoutPageTemplatesImporterResultEntries,
		int index) {

		LayoutPageTemplatesImporterResultEntry
			layoutPageTemplatesImporterResultEntry =
				layoutPageTemplatesImporterResultEntries.get(index);

		Assert.assertEquals(
			LayoutPageTemplatesImporterResultEntry.Status.IMPORTED,
			layoutPageTemplatesImporterResultEntry.getStatus());

		String layoutPageTemplateEntryKey = StringUtil.toLowerCase(
			layoutPageTemplatesImporterResultEntry.getName());

		layoutPageTemplateEntryKey = StringUtil.replace(
			layoutPageTemplateEntryKey, CharPool.SPACE, CharPool.DASH);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				_group.getGroupId(), layoutPageTemplateEntryKey);

		Assert.assertNotNull(layoutPageTemplateEntry);

		return layoutPageTemplateEntry;
	}

	private List<LayoutPageTemplatesImporterResultEntry>
			_getLayoutPageTemplatesImporterResultEntries(
				String testCaseName, Map<String, String> valuesMap)
		throws Exception {

		File file = _generateZipFile(testCaseName, valuesMap);

		List<LayoutPageTemplatesImporterResultEntry>
			layoutPageTemplatesImporterResultEntries = null;

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		try {
			layoutPageTemplatesImporterResultEntries =
				_layoutPageTemplatesImporter.importFile(
					_user.getUserId(), _group.getGroupId(), 0, file, false);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		Assert.assertNotNull(layoutPageTemplatesImporterResultEntries);

		return layoutPageTemplatesImporterResultEntries;
	}

	private LayoutStructureItem _getMainChildLayoutStructureItem(
		LayoutStructure layoutStructure) {

		LayoutStructureItem mainLayoutStructureItem =
			layoutStructure.getMainLayoutStructureItem();

		List<String> childrenItemIds =
			mainLayoutStructureItem.getChildrenItemIds();

		Assert.assertEquals(
			childrenItemIds.toString(), 1, childrenItemIds.size());

		String childItemId = childrenItemIds.get(0);

		return layoutStructure.getLayoutStructureItem(childItemId);
	}

	private void _populateZipWriter(
			ZipWriter zipWriter, URL url, Map<String, String> valuesMap)
		throws IOException {

		String zipPath = StringUtil.removeSubstring(
			url.getFile(), _LAYOUT_PATE_TEMPLATES_PATH);

		zipWriter.addEntry(zipPath, url.openStream());

		String path = FileUtil.getPath(url.getPath());

		Enumeration<URL> enumeration = _bundle.findEntries(
			path,
			LayoutPageTemplateExportImportConstants.FILE_NAME_PAGE_TEMPLATE,
			true);

		while (enumeration.hasMoreElements()) {
			URL elementUrl = enumeration.nextElement();

			_addZipWriterEntry(zipWriter, elementUrl, valuesMap);
		}

		enumeration = _bundle.findEntries(
			path,
			LayoutPageTemplateExportImportConstants.FILE_NAME_PAGE_DEFINITION,
			true);

		while (enumeration.hasMoreElements()) {
			URL elementUrl = enumeration.nextElement();

			_addZipWriterEntry(zipWriter, elementUrl, valuesMap);
		}

		enumeration = _bundle.findEntries(path, "thumbnail.png", true);

		if (enumeration == null) {
			return;
		}

		while (enumeration.hasMoreElements()) {
			URL elementUrl = enumeration.nextElement();

			_addZipWriterEntry(zipWriter, elementUrl, valuesMap);
		}
	}

	private void _registerTestPortlet(final String portletId) throws Exception {
		_serviceRegistrations.add(
			_bundleContext.registerService(
				Portlet.class,
				new LayoutPageTemplatesImporterTest.TestPortlet(),
				new HashMapDictionary<String, String>() {
					{
						put("com.liferay.portlet.instanceable", "true");
						put("javax.portlet.name", portletId);
					}
				}));
	}

	private void _validateHTMLFragmentEntryLinkEditableValues(
			String editableValues)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			editableValues);

		JSONObject editableFragmentEntryProcessorJSONObject =
			jsonObject.getJSONObject(
				"com.liferay.fragment.entry.processor.editable." +
					"EditableFragmentEntryProcessor");

		Assert.assertNotNull(editableFragmentEntryProcessorJSONObject);

		JSONObject elementJSONObject =
			editableFragmentEntryProcessorJSONObject.getJSONObject(
				"element-html");

		Assert.assertNotNull(elementJSONObject);

		Assert.assertEquals(
			"\n\t\t<h1>\n\t\t\tEdited HTML\n\t\t</h1>\n\n\t\t<p>\n\t\t\t" +
				"Edited <strong>paragraph</strong>.\n\t\t</p>\n\t",
			elementJSONObject.getString("en_US"));
	}

	private void _validateImageFragmentEntryLinkEditableValues(
			String editableValues)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			editableValues);

		JSONObject editableFragmentEntryProcessorJSONObject =
			jsonObject.getJSONObject(
				"com.liferay.fragment.entry.processor.editable." +
					"EditableFragmentEntryProcessor");

		Assert.assertNotNull(editableFragmentEntryProcessorJSONObject);

		JSONObject elementJSONObject =
			editableFragmentEntryProcessorJSONObject.getJSONObject(
				"element-image");

		Assert.assertNotNull(elementJSONObject);

		JSONObject configJSONObject = elementJSONObject.getJSONObject("config");

		Assert.assertNotNull(configJSONObject);

		Assert.assertEquals(
			"Test image description", configJSONObject.getString("alt"));
		Assert.assertEquals("www.test.com", configJSONObject.getString("href"));
		Assert.assertEquals("_blank", configJSONObject.getString("target"));

		JSONObject freeMarkerFragmentEntryProcessorJSONObject =
			jsonObject.getJSONObject(
				"com.liferay.fragment.entry.processor.freemarker." +
					"FreeMarkerFragmentEntryProcessor");

		Assert.assertNotNull(freeMarkerFragmentEntryProcessorJSONObject);

		Assert.assertEquals(
			"4",
			freeMarkerFragmentEntryProcessorJSONObject.getString(
				"bottomSpacing"));
		Assert.assertEquals(
			"center",
			freeMarkerFragmentEntryProcessorJSONObject.getString("imageAlign"));
		Assert.assertEquals(
			"w-0",
			freeMarkerFragmentEntryProcessorJSONObject.getString("imageSize"));
	}

	private void _validateLinkFragmentEntryLinkEditableValues(
			String editableValues)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			editableValues);

		JSONObject editableFragmentEntryProcessorJSONObject =
			jsonObject.getJSONObject(
				"com.liferay.fragment.entry.processor.editable." +
					"EditableFragmentEntryProcessor");

		Assert.assertNotNull(editableFragmentEntryProcessorJSONObject);

		JSONObject elementJSONObject =
			editableFragmentEntryProcessorJSONObject.getJSONObject(
				"element-link");

		Assert.assertNotNull(elementJSONObject);

		JSONObject configJSONObject = elementJSONObject.getJSONObject("config");

		Assert.assertNotNull(configJSONObject);

		Assert.assertEquals(
			"http://www.test.com", configJSONObject.getString("href"));

		Assert.assertEquals(
			"Edited Link", elementJSONObject.getString("en_US"));
	}

	private void _validateTextFragmentEntryLinkEditableValues(
			String editableValues)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			editableValues);

		JSONObject editableFragmentEntryProcessorJSONObject =
			jsonObject.getJSONObject(
				"com.liferay.fragment.entry.processor.editable." +
					"EditableFragmentEntryProcessor");

		Assert.assertNotNull(editableFragmentEntryProcessorJSONObject);

		JSONObject elementJSONObject =
			editableFragmentEntryProcessorJSONObject.getJSONObject(
				"element-text");

		Assert.assertNotNull(elementJSONObject);

		JSONObject configJSONObject = elementJSONObject.getJSONObject("config");

		Assert.assertNotNull(configJSONObject);

		Assert.assertEquals("www.test.com", configJSONObject.getString("href"));

		Assert.assertEquals("_blank", configJSONObject.getString("target"));

		Assert.assertEquals(
			"Edited Text", elementJSONObject.getString("en_US"));

		JSONObject freeMarkerFragmentEntryProcessorJSONObject =
			jsonObject.getJSONObject(
				"com.liferay.fragment.entry.processor.freemarker." +
					"FreeMarkerFragmentEntryProcessor");

		Assert.assertNotNull(freeMarkerFragmentEntryProcessorJSONObject);

		Assert.assertEquals(
			"2",
			freeMarkerFragmentEntryProcessorJSONObject.getString(
				"bottomSpacing"));
		Assert.assertEquals(
			"h2",
			freeMarkerFragmentEntryProcessorJSONObject.getString(
				"headingLevel"));
		Assert.assertEquals(
			"center",
			freeMarkerFragmentEntryProcessorJSONObject.getString("textAlign"));
		Assert.assertEquals(
			"danger",
			freeMarkerFragmentEntryProcessorJSONObject.getString("textColor"));
	}

	private static final String _LAYOUT_PATE_TEMPLATES_PATH =
		"com/liferay/layout/page/template/internal/importer/test/dependencies/";

	private static final String _ROOT_FOLDER = "page-templates";

	private Bundle _bundle;
	private BundleContext _bundleContext;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private LayoutPageTemplatesImporter _layoutPageTemplatesImporter;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject
	private PortletPermission _portletPermission;

	@Inject
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Inject
	private ResourceActionLocalService _resourceActionLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new CopyOnWriteArrayList<>();
	private String _testPortletName;
	private User _user;

	private class TestPortlet extends GenericPortlet {
	}

}