/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.admin.web.internal.importer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.contributor.FragmentCollectionContributorRegistry;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.layout.importer.LayoutsImportStrategy;
import com.liferay.layout.importer.LayoutsImporter;
import com.liferay.layout.importer.LayoutsImporterResultEntry;
import com.liferay.layout.page.template.constants.LayoutPageTemplateCollectionTypeConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.util.structure.ColumnLayoutStructureItem;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.RowStyledLayoutStructureItem;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactory;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.io.File;
import java.io.InputStream;

import java.net.URL;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class LayoutsImporterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group1 = GroupTestUtil.addGroup();
		_group2 = GroupTestUtil.addGroup();

		_serviceContext1 = ServiceContextTestUtil.getServiceContext(
			_group1, TestPropsValues.getUserId());
		_serviceContext2 = ServiceContextTestUtil.getServiceContext(
			_group2, TestPropsValues.getUserId());
	}

	@Test
	public void testImportDisplayPageTemplates() throws Exception {
		List<LayoutsImporterResultEntry> layoutsImporterResultEntries = null;

		ServiceContextThreadLocal.pushServiceContext(_serviceContext1);

		try {
			layoutsImporterResultEntries = _layoutsImporter.importFile(
				TestPropsValues.getUserId(), _group1.getGroupId(), 0,
				_getFile(), LayoutsImportStrategy.DO_NOT_OVERWRITE, true);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		Assert.assertNotNull(layoutsImporterResultEntries);

		Assert.assertEquals(
			layoutsImporterResultEntries.toString(), 2,
			layoutsImporterResultEntries.size());

		for (LayoutsImporterResultEntry layoutsImporterResultEntry :
				layoutsImporterResultEntries) {

			Assert.assertEquals(
				LayoutsImporterResultEntry.Status.IMPORTED,
				layoutsImporterResultEntry.getStatus());
			Assert.assertEquals(
				LayoutsImporterResultEntry.ENTRY,
				layoutsImporterResultEntry.getType());

			_assertLayoutPageTemplateEntry(
				StringUtil.replace(
					StringUtil.toLowerCase(
						layoutsImporterResultEntry.getName()),
					CharPool.SPACE, CharPool.DASH));
		}
	}

	@Test
	public void testImportLayoutPageTemplateEntry() throws Exception {
		String html =
			"<lfr-editable id=\"element-text\" type=\"text\">Test Text " +
				"Fragment</lfr-editable>";
		String key = "test-text-fragment";
		String name = "Test Text Fragment";

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_addLayoutPageTemplateEntry(html, key, name);

		File file = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "getFile", new Class<?>[] {long[].class},
			new long[] {
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId()
			});

		_addFragmentEntry(html, key, name, _serviceContext2);

		List<LayoutsImporterResultEntry> layoutsImporterResultEntries = null;

		ServiceContextThreadLocal.pushServiceContext(_serviceContext2);

		try {
			layoutsImporterResultEntries = _layoutsImporter.importFile(
				TestPropsValues.getUserId(), _group2.getGroupId(), 0, file,
				LayoutsImportStrategy.DO_NOT_OVERWRITE, true);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		Assert.assertNotNull(layoutsImporterResultEntries);

		Assert.assertEquals(
			layoutsImporterResultEntries.toString(), 1,
			layoutsImporterResultEntries.size());

		_assertLayoutsImporterResultEntry(
			layoutsImporterResultEntries.get(0),
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layoutPageTemplateEntry.getGroupId(),
					layoutPageTemplateEntry.getPlid()));
	}

	@Test
	public void testImportLayoutPageTemplateEntryWithCTCollection()
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_addLayoutPageTemplateEntry();

		FragmentEntry fragmentEntry =
			_fragmentCollectionContributorRegistry.getFragmentEntry(
				"BASIC_COMPONENT-heading");

		Assert.assertEquals(
			CompanyConstants.SYSTEM, fragmentEntry.getCompanyId());

		_addFragmentEntryLink(fragmentEntry, layoutPageTemplateEntry);

		File file = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "getFile", new Class<?>[] {long[].class},
			new long[] {
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId()
			});

		ServiceContextThreadLocal.pushServiceContext(_serviceContext2);

		CTCollection ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), RandomTestUtil.randomString());

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection.getCtCollectionId())) {

			_assertLayoutsImporterResultEntries(
				fragmentEntry,
				_layoutsImporter.importFile(
					TestPropsValues.getUserId(), _group2.getGroupId(), 0, file,
					LayoutsImportStrategy.DO_NOT_OVERWRITE, true));

			Assert.assertFalse(
				_ctCollectionLocalService.hasUnapprovedChanges(
					ctCollection.getCtCollectionId()));
		}
		finally {
			_ctCollectionLocalService.deleteCTCollection(ctCollection);

			ServiceContextThreadLocal.popServiceContext();
		}
	}

	private FragmentEntry _addFragmentEntry(
			String html, String key, String name, ServiceContext serviceContext)
		throws Exception {

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				null, TestPropsValues.getUserId(),
				serviceContext.getScopeGroupId(), "Test Collection",
				StringPool.BLANK, serviceContext);

		return _fragmentEntryLocalService.addFragmentEntry(
			null, TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			fragmentCollection.getFragmentCollectionId(), key, name,
			StringPool.BLANK, html, StringPool.BLANK, false, StringPool.BLANK,
			null, 0, false, FragmentConstants.TYPE_COMPONENT, null,
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	private void _addFragmentEntryLink(
			FragmentEntry fragmentEntry,
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws Exception {

		Layout layout = _layoutLocalService.getLayout(
			layoutPageTemplateEntry.getPlid());

		Layout draftLayout = layout.fetchDraftLayout();

		ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
			null, fragmentEntry.getCss(), fragmentEntry.getConfiguration(),
			fragmentEntry.getFragmentEntryId(), fragmentEntry.getHtml(),
			fragmentEntry.getJs(), draftLayout,
			fragmentEntry.getFragmentEntryKey(),
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				draftLayout.getPlid()),
			fragmentEntry.getType());

		ContentLayoutTestUtil.publishLayout(draftLayout, layout);

		_assertFragmentEntryLink(fragmentEntry, layoutPageTemplateEntry);
	}

	private LayoutPageTemplateEntry _addLayoutPageTemplateEntry()
		throws Exception {

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					null, _serviceContext1.getUserId(), _group1.getGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					RandomTestUtil.randomString(), StringPool.BLANK,
					LayoutPageTemplateCollectionTypeConstants.BASIC,
					_serviceContext1);

		return _layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			null, _serviceContext1.getUserId(), _group1.getGroupId(),
			layoutPageTemplateCollection.getLayoutPageTemplateCollectionId(),
			RandomTestUtil.randomString(),
			LayoutPageTemplateEntryTypeConstants.BASIC, 0,
			WorkflowConstants.STATUS_APPROVED, _serviceContext1);
	}

	private LayoutPageTemplateEntry _addLayoutPageTemplateEntry(
			String html, String key, String name)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_addLayoutPageTemplateEntry();

		FragmentEntry fragmentEntry = _addFragmentEntry(
			html, key, name, _serviceContext1);

		long defaultSegmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layoutPageTemplateEntry.getPlid());

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group1.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(), defaultSegmentsExperienceId,
				layoutPageTemplateEntry.getPlid(), StringPool.BLANK, html,
				StringPool.BLANK,
				_read("export_import_fragment_field_text_config.json"),
				_read("export_import_fragment_field_text_editable_values.json"),
				StringPool.BLANK, 0, null, fragmentEntry.getType(),
				_serviceContext1);

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructureData(
				_group1.getGroupId(), layoutPageTemplateEntry.getPlid(),
				defaultSegmentsExperienceId,
				StringUtil.replace(
					_read("export_import_layout_data.json"), "${", "}",
					HashMapBuilder.put(
						"FRAGMENT_ENTRY_LINK1_ID",
						String.valueOf(
							fragmentEntryLink.getFragmentEntryLinkId())
					).build()));

		Repository repository = PortletFileRepositoryUtil.addPortletRepository(
			_group1.getGroupId(), RandomTestUtil.randomString(),
			_serviceContext1);

		Class<?> clazz = getClass();

		FileEntry fileEntry = PortletFileRepositoryUtil.addPortletFileEntry(
			null, _group1.getGroupId(), TestPropsValues.getUserId(),
			LayoutPageTemplateEntry.class.getName(),
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			RandomTestUtil.randomString(), repository.getDlFolderId(),
			clazz.getResourceAsStream("dependencies/thumbnail.png"),
			RandomTestUtil.randomString(), ContentTypes.IMAGE_PNG, false);

		return _layoutPageTemplateEntryLocalService.
			updateLayoutPageTemplateEntry(
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
				fileEntry.getFileEntryId());
	}

	private void _assertFragmentEntryLink(
			FragmentEntry fragmentEntry,
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws Exception {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layoutPageTemplateEntry.getGroupId(),
					layoutPageTemplateEntry.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getDefaultSegmentsExperienceData());

		Map<Long, LayoutStructureItem> fragmentLayoutStructureItems =
			layoutStructure.getFragmentLayoutStructureItems();

		Assert.assertEquals(
			MapUtil.toString(fragmentLayoutStructureItems), 1,
			fragmentLayoutStructureItems.size());

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.getFragmentEntryLink(
				SetUtil.randomElement(fragmentLayoutStructureItems.keySet()));

		Assert.assertEquals(
			fragmentEntry.getFragmentEntryKey(),
			fragmentEntryLink.getRendererKey());
	}

	private void _assertLayoutPageTemplateEntry(
			long classNameId, long classTypeId,
			LayoutPageTemplateEntry layoutPageTemplateEntry, String mappedField)
		throws Exception {

		Assert.assertEquals(
			classNameId, layoutPageTemplateEntry.getClassNameId());
		Assert.assertEquals(
			classTypeId, layoutPageTemplateEntry.getClassTypeId());

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layoutPageTemplateEntry.getGroupId(),
					layoutPageTemplateEntry.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getDefaultSegmentsExperienceData());

		Map<Long, LayoutStructureItem> fragmentLayoutStructureItems =
			layoutStructure.getFragmentLayoutStructureItems();

		Assert.assertEquals(
			MapUtil.toString(fragmentLayoutStructureItems), 1,
			fragmentLayoutStructureItems.size());

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.getFragmentEntryLink(
				SetUtil.randomElement(fragmentLayoutStructureItems.keySet()));

		Assert.assertEquals(
			"BASIC_COMPONENT-heading", fragmentEntryLink.getRendererKey());

		Assert.assertTrue(
			Validator.isNotNull(fragmentEntryLink.getEditableValues()));

		JSONObject editableValuesJSONObject = _jsonFactory.createJSONObject(
			fragmentEntryLink.getEditableValues());

		JSONObject editableJSONObject = editableValuesJSONObject.getJSONObject(
			FragmentEntryProcessorConstants.
				KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR);

		JSONObject elementTextJSONObject = editableJSONObject.getJSONObject(
			"element-text");

		Assert.assertEquals(
			mappedField, elementTextJSONObject.getString("mappedField"));
	}

	private void _assertLayoutPageTemplateEntry(
			String layoutPageTemplateEntryKey)
		throws Exception {

		Assert.assertTrue(
			layoutPageTemplateEntryKey,
			ArrayUtil.contains(
				_DISPLAY_PAGE_LAYOUT_PAGE_TEMPLATE_ENTRY_KEYS,
				layoutPageTemplateEntryKey));

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				_group1.getGroupId(), layoutPageTemplateEntryKey);

		Assert.assertNotNull(
			layoutPageTemplateEntryKey, layoutPageTemplateEntry);

		if (Objects.equals(
				layoutPageTemplateEntryKey,
				"basic-web-content-display-page-template")) {

			long classNameId = _portal.getClassNameId(
				"com.liferay.journal.model.JournalArticle");

			DDMStructure ddmStructure =
				_ddmStructureLocalService.fetchStructure(
					layoutPageTemplateEntry.getGroupId(), classNameId,
					"BASIC-WEB-CONTENT", true);

			_assertLayoutPageTemplateEntry(
				classNameId, ddmStructure.getStructureId(),
				layoutPageTemplateEntry, "JournalArticle_title");
		}
		else {
			_assertLayoutPageTemplateEntry(
				_portal.getClassNameId(
					"com.liferay.commerce.product.model.CPDefinition"),
				0, layoutPageTemplateEntry, "CPDefinition_name");
		}
	}

	private void _assertLayoutsImporterResultEntries(
			FragmentEntry fragmentEntry,
			List<LayoutsImporterResultEntry> layoutsImporterResultEntries)
		throws Exception {

		Assert.assertNotNull(layoutsImporterResultEntries);

		Assert.assertEquals(
			layoutsImporterResultEntries.toString(), 1,
			layoutsImporterResultEntries.size());

		LayoutsImporterResultEntry layoutPageTemplateImportEntry =
			layoutsImporterResultEntries.get(0);

		Assert.assertEquals(
			LayoutsImporterResultEntry.Status.IMPORTED,
			layoutPageTemplateImportEntry.getStatus());

		String layoutPageTemplateEntryKey = StringUtil.toLowerCase(
			layoutPageTemplateImportEntry.getName());

		layoutPageTemplateEntryKey = StringUtil.replace(
			layoutPageTemplateEntryKey, CharPool.SPACE, CharPool.DASH);

		LayoutPageTemplateEntry curLayoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				_group2.getGroupId(), layoutPageTemplateEntryKey);

		Assert.assertNotNull(curLayoutPageTemplateEntry);

		_assertFragmentEntryLink(fragmentEntry, curLayoutPageTemplateEntry);
	}

	private void _assertLayoutsImporterResultEntry(
			LayoutsImporterResultEntry layoutPageTemplateImportEntry,
			LayoutPageTemplateStructure layoutPageTemplateStructure)
		throws Exception {

		Assert.assertEquals(
			LayoutsImporterResultEntry.Status.IMPORTED,
			layoutPageTemplateImportEntry.getStatus());

		String layoutPageTemplateEntryKey = StringUtil.toLowerCase(
			layoutPageTemplateImportEntry.getName());

		layoutPageTemplateEntryKey = StringUtil.replace(
			layoutPageTemplateEntryKey, CharPool.SPACE, CharPool.DASH);

		LayoutPageTemplateEntry curLayoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				_group2.getGroupId(), layoutPageTemplateEntryKey);

		Assert.assertNotNull(curLayoutPageTemplateEntry);

		LayoutPageTemplateStructure curLayoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					curLayoutPageTemplateEntry.getGroupId(),
					curLayoutPageTemplateEntry.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getDefaultSegmentsExperienceData());
		LayoutStructure curLayoutStructure = LayoutStructure.of(
			curLayoutPageTemplateStructure.getDefaultSegmentsExperienceData());

		ContainerStyledLayoutStructureItem containerStyledLayoutStructureItem =
			_getContainerLayoutStructureItem(layoutStructure);
		ContainerStyledLayoutStructureItem
			curContainerStyledLayoutStructureItem =
				_getContainerLayoutStructureItem(curLayoutStructure);

		_validateContainerLayoutStructureItem(
			containerStyledLayoutStructureItem,
			curContainerStyledLayoutStructureItem);

		List<String> containerLayoutStructureItemChildrenItemIds =
			containerStyledLayoutStructureItem.getChildrenItemIds();
		List<String> curContainerLayoutStructureItemChildrenItemIds =
			curContainerStyledLayoutStructureItem.getChildrenItemIds();

		RowStyledLayoutStructureItem rowStyledLayoutStructureItem =
			(RowStyledLayoutStructureItem)
				layoutStructure.getLayoutStructureItem(
					containerLayoutStructureItemChildrenItemIds.get(0));
		RowStyledLayoutStructureItem curRowStyledLayoutStructureItem =
			(RowStyledLayoutStructureItem)
				curLayoutStructure.getLayoutStructureItem(
					curContainerLayoutStructureItemChildrenItemIds.get(0));

		_validateRowLayoutStructureItem(
			rowStyledLayoutStructureItem, curRowStyledLayoutStructureItem);

		List<String> rowLayoutStructureItemChildrenItemIds =
			rowStyledLayoutStructureItem.getChildrenItemIds();
		List<String> curRowLayoutStructureItemChildrenItemIds =
			curRowStyledLayoutStructureItem.getChildrenItemIds();

		ColumnLayoutStructureItem columnLayoutStructureItem =
			(ColumnLayoutStructureItem)layoutStructure.getLayoutStructureItem(
				rowLayoutStructureItemChildrenItemIds.get(0));
		ColumnLayoutStructureItem curColumnLayoutStructureItem =
			(ColumnLayoutStructureItem)
				curLayoutStructure.getLayoutStructureItem(
					curRowLayoutStructureItemChildrenItemIds.get(0));

		_validateColumnLayoutStructureItem(
			columnLayoutStructureItem, curColumnLayoutStructureItem);

		List<String> columnLayoutStructureItemChildrenItemIds =
			columnLayoutStructureItem.getChildrenItemIds();
		List<String> curColumnLayoutStructureItemChildrenItemIds =
			curColumnLayoutStructureItem.getChildrenItemIds();

		FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem =
			(FragmentStyledLayoutStructureItem)
				layoutStructure.getLayoutStructureItem(
					columnLayoutStructureItemChildrenItemIds.get(0));
		FragmentStyledLayoutStructureItem curFragmentStyledLayoutStructureItem =
			(FragmentStyledLayoutStructureItem)
				curLayoutStructure.getLayoutStructureItem(
					curColumnLayoutStructureItemChildrenItemIds.get(0));

		_validateFragmentLayoutStructureItem(
			fragmentStyledLayoutStructureItem,
			curFragmentStyledLayoutStructureItem);
	}

	private ContainerStyledLayoutStructureItem _getContainerLayoutStructureItem(
		LayoutStructure layoutStructure) {

		LayoutStructureItem layoutStructureItem =
			_getMainChildLayoutStructureItem(layoutStructure);

		Assert.assertTrue(
			layoutStructureItem instanceof ContainerStyledLayoutStructureItem);

		return (ContainerStyledLayoutStructureItem)layoutStructureItem;
	}

	private File _getFile() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		Enumeration<URL> enumeration = bundle.findEntries(
			_RESOURCES_PATH, "*", true);

		ZipWriter zipWriter = _zipWriterFactory.getZipWriter();

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			String path = url.getPath();

			if (!path.endsWith(StringPool.SLASH)) {
				try (InputStream inputStream = url.openStream()) {
					zipWriter.addEntry(
						StringUtil.removeSubstring(
							url.getPath(), _RESOURCES_PATH),
						inputStream);
				}
			}
		}

		return zipWriter.getFile();
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

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

	private void _validateColumnLayoutStructureItem(
		ColumnLayoutStructureItem expectedColumnLayoutStructureItem,
		ColumnLayoutStructureItem actualColumnLayoutStructureItem) {

		Assert.assertEquals(
			expectedColumnLayoutStructureItem.getSize(),
			actualColumnLayoutStructureItem.getSize());
	}

	private void _validateContainerLayoutStructureItem(
		ContainerStyledLayoutStructureItem
			expectedContainerStyledLayoutStructureItem,
		ContainerStyledLayoutStructureItem
			actualContainerStyledLayoutStructureItem) {

		JSONObject expectedItemConfigJSONObject =
			expectedContainerStyledLayoutStructureItem.
				getItemConfigJSONObject();

		JSONObject actualItemConfigJSONObject =
			actualContainerStyledLayoutStructureItem.getItemConfigJSONObject();

		JSONObject expectedStylesJSONObject =
			expectedItemConfigJSONObject.getJSONObject("styles");

		JSONObject actualStylesJSONObject =
			actualItemConfigJSONObject.getJSONObject("styles");

		Assert.assertEquals(
			expectedStylesJSONObject.getString("backgroundColor"),
			actualStylesJSONObject.getString("backgroundColor"));

		JSONObject expectedBackgroundImageJSONObject =
			expectedContainerStyledLayoutStructureItem.
				getBackgroundImageJSONObject();
		JSONObject actualBackgroundImageJSONObject =
			actualContainerStyledLayoutStructureItem.
				getBackgroundImageJSONObject();

		Assert.assertEquals(
			expectedBackgroundImageJSONObject.toString(),
			actualBackgroundImageJSONObject.toString());

		Assert.assertEquals(
			expectedStylesJSONObject.getString("paddingBottom"),
			actualStylesJSONObject.getString("paddingBottom"));
		Assert.assertEquals(
			expectedStylesJSONObject.getString("paddingLeft"),
			actualStylesJSONObject.getString("paddingLeft"));
		Assert.assertEquals(
			expectedStylesJSONObject.getString("paddingRight"),
			actualStylesJSONObject.getString("paddingRight"));
		Assert.assertEquals(
			expectedStylesJSONObject.getString("paddingTop"),
			actualStylesJSONObject.getString("paddingTop"));
		Assert.assertEquals(
			expectedContainerStyledLayoutStructureItem.getWidthType(),
			actualContainerStyledLayoutStructureItem.getWidthType());
	}

	private void _validateFragmentLayoutStructureItem(
			FragmentStyledLayoutStructureItem
				expectedFragmentStyledLayoutStructureItem,
			FragmentStyledLayoutStructureItem
				actualFragmentStyledLayoutStructureItem)
		throws Exception {

		long expectedFragmentEntryLinkId =
			expectedFragmentStyledLayoutStructureItem.getFragmentEntryLinkId();
		long actualFragmentEntryLinkId =
			actualFragmentStyledLayoutStructureItem.getFragmentEntryLinkId();

		FragmentEntryLink expectedFragmentEntryLink =
			_fragmentEntryLinkLocalService.getFragmentEntryLink(
				expectedFragmentEntryLinkId);
		FragmentEntryLink actualFragmentEntryLink =
			_fragmentEntryLinkLocalService.getFragmentEntryLink(
				actualFragmentEntryLinkId);

		String expectedEditableValues =
			expectedFragmentEntryLink.getEditableValues();
		String actualEditableValues =
			actualFragmentEntryLink.getEditableValues();

		JSONObject expectedEditableValuesJSONObject =
			JSONFactoryUtil.createJSONObject(expectedEditableValues);
		JSONObject actualEditableValuesJSONObject =
			JSONFactoryUtil.createJSONObject(actualEditableValues);

		JSONObject expectedBackgroundImageFragmentEntryProcessorJSONObject =
			expectedEditableValuesJSONObject.getJSONObject(
				FragmentEntryProcessorConstants.
					KEY_BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR);
		JSONObject actualBackgroundImageFragmentEntryProcessorJSONObject =
			actualEditableValuesJSONObject.getJSONObject(
				FragmentEntryProcessorConstants.
					KEY_BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR);

		if (expectedBackgroundImageFragmentEntryProcessorJSONObject == null) {
			Assert.assertNull(
				actualBackgroundImageFragmentEntryProcessorJSONObject);
		}
		else {
			Assert.assertEquals(
				expectedBackgroundImageFragmentEntryProcessorJSONObject.
					toString(),
				actualBackgroundImageFragmentEntryProcessorJSONObject.
					toString());
		}

		JSONObject expectedEditableFragmentEntryProcessorJSONObject =
			expectedEditableValuesJSONObject.getJSONObject(
				FragmentEntryProcessorConstants.
					KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR);
		JSONObject actualEditableFragmentEntryProcessorJSONObject =
			actualEditableValuesJSONObject.getJSONObject(
				FragmentEntryProcessorConstants.
					KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR);

		if (expectedEditableFragmentEntryProcessorJSONObject == null) {
			Assert.assertNull(actualEditableFragmentEntryProcessorJSONObject);
		}
		else {
			JSONObject expectedElementTextJSONObject =
				expectedEditableFragmentEntryProcessorJSONObject.getJSONObject(
					"element-text");
			JSONObject actualElementTextJSONObject =
				actualEditableFragmentEntryProcessorJSONObject.getJSONObject(
					"element-text");

			Assert.assertEquals(
				expectedElementTextJSONObject.getString("en_US"),
				actualElementTextJSONObject.getString("en_US"));

			Assert.assertEquals(
				expectedElementTextJSONObject.getString("es_ES"),
				actualElementTextJSONObject.getString("es_ES"));

			JSONObject expectedElementTextConfigJSONObject =
				expectedElementTextJSONObject.getJSONObject("config");
			JSONObject actualElementTextConfigJSONObject =
				actualElementTextJSONObject.getJSONObject("config");

			Assert.assertEquals(
				expectedElementTextConfigJSONObject.toString(),
				actualElementTextConfigJSONObject.toString());
		}

		JSONObject expectedFreeMarkerFragmentEntryProcessorJSONObject =
			expectedEditableValuesJSONObject.getJSONObject(
				FragmentEntryProcessorConstants.
					KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR);
		JSONObject actualFreeMarkerFragmentEntryProcessorJSONObject =
			actualEditableValuesJSONObject.getJSONObject(
				FragmentEntryProcessorConstants.
					KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR);

		if (expectedFreeMarkerFragmentEntryProcessorJSONObject == null) {
			Assert.assertNull(actualFreeMarkerFragmentEntryProcessorJSONObject);
		}
		else {
			Assert.assertEquals(
				expectedFreeMarkerFragmentEntryProcessorJSONObject.toString(),
				actualFreeMarkerFragmentEntryProcessorJSONObject.toString());
		}

		Assert.assertEquals(
			expectedFragmentEntryLink.getPosition(),
			actualFragmentEntryLink.getPosition());
	}

	private void _validateRowLayoutStructureItem(
		RowStyledLayoutStructureItem expectedRowStyledLayoutStructureItem,
		RowStyledLayoutStructureItem actualRowStyledLayoutStructureItem) {

		Assert.assertEquals(
			expectedRowStyledLayoutStructureItem.isGutters(),
			actualRowStyledLayoutStructureItem.isGutters());
		Assert.assertEquals(
			expectedRowStyledLayoutStructureItem.getNumberOfColumns(),
			actualRowStyledLayoutStructureItem.getNumberOfColumns());
	}

	private static final String[]
		_DISPLAY_PAGE_LAYOUT_PAGE_TEMPLATE_ENTRY_KEYS = {
			"basic-web-content-display-page-template",
			"product-display-page-template"
		};

	private static final String _RESOURCES_PATH =
		"com/liferay/layout/page/template/admin/web/internal/importer/test" +
			"/dependencies/display-page-templates";

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private DDMStructureLocalService _ddmStructureLocalService;

	@Inject
	private FragmentCollectionContributorRegistry
		_fragmentCollectionContributorRegistry;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group1;

	@DeleteAfterTestRun
	private Group _group2;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject
	private LayoutsImporter _layoutsImporter;

	@Inject(
		filter = "mvc.command.name=/layout_page_template_admin/export_layout_page_template_entries"
	)
	private MVCResourceCommand _mvcResourceCommand;

	@Inject
	private Portal _portal;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	private ServiceContext _serviceContext1;
	private ServiceContext _serviceContext2;

	@Inject
	private ZipWriterFactory _zipWriterFactory;

}