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
import com.liferay.info.field.InfoField;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.layout.exporter.LayoutsExporter;
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
import com.liferay.layout.provider.LayoutStructureProvider;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.util.structure.ColumnLayoutStructureItem;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.RowStyledLayoutStructureItem;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
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
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;

import java.io.File;
import java.io.InputStream;

import java.net.URL;

import java.util.Collections;
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
				_getFile(_RESOURCES_PATH_DISPLAY_PAGE_TEMPLATES),
				LayoutsImportStrategy.DO_NOT_OVERWRITE, true);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		Assert.assertEquals(
			layoutsImporterResultEntries.toString(), 3,
			layoutsImporterResultEntries.size());

		for (LayoutsImporterResultEntry layoutsImporterResultEntry :
				layoutsImporterResultEntries) {

			Assert.assertEquals(
				LayoutsImporterResultEntry.Status.IMPORTED,
				layoutsImporterResultEntry.getStatus());
			Assert.assertEquals(
				LayoutsImporterResultEntry.TYPE_ENTRY,
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

		File file = _layoutsExporter.exportLayoutPageTemplateEntries(
			new long[] {layoutPageTemplateEntry.getLayoutPageTemplateEntryId()},
			LayoutPageTemplateEntryTypeConstants.BASIC);

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

		Assert.assertEquals(
			layoutsImporterResultEntries.toString(), 1,
			layoutsImporterResultEntries.size());

		_assertLayoutsImporterResultEntries(
			layoutsImporterResultEntries,
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

		_addFragmentEntryLinks(layoutPageTemplateEntry, fragmentEntry);

		File file = _layoutsExporter.exportLayoutPageTemplateEntries(
			new long[] {layoutPageTemplateEntry.getLayoutPageTemplateEntryId()},
			LayoutPageTemplateEntryTypeConstants.BASIC);

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

	@Test
	@TestInfo("LPD-33951")
	public void testImportLayoutPageTemplateEntryWithFormContainer()
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_addLayoutPageTemplateEntry();

		FragmentEntryLink fragmentEntryLink = _addInputFragmentEntryLink(
			layoutPageTemplateEntry);

		FragmentEntry fragmentEntry = _addFragmentEntry(
			_fragmentEntryLocalService.getFragmentEntry(
				fragmentEntryLink.getFragmentEntryId()),
			_serviceContext2);

		File file = _layoutsExporter.exportLayoutPageTemplateEntries(
			new long[] {layoutPageTemplateEntry.getLayoutPageTemplateEntryId()},
			LayoutPageTemplateEntryTypeConstants.BASIC);

		List<LayoutsImporterResultEntry> layoutsImporterResultEntries =
			_layoutsImporter.importFile(
				TestPropsValues.getUserId(), _group2.getGroupId(), 0, file,
				LayoutsImportStrategy.DO_NOT_OVERWRITE, true);

		_assertLayoutPageTemplateEntry(
			fragmentEntry, fragmentEntryLink,
			_getLayoutPageTemplateEntryKey(layoutsImporterResultEntries));
	}

	@Test
	@TestInfo({"LPS-128399", "LPS-129107"})
	public void testImportLayoutPageTemplateEntryWithStyleBookAndMissingFragmentEntry()
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_addLayoutPageTemplateEntry();

		FragmentEntry fragmentEntry =
			_fragmentCollectionContributorRegistry.getFragmentEntry(
				"BASIC_COMPONENT-heading");

		String fragmentEntryKey = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		_addFragmentEntryLinks(
			layoutPageTemplateEntry, fragmentEntry,
			_addFragmentEntry(
				RandomTestUtil.randomString(), fragmentEntryKey,
				RandomTestUtil.randomString(), _serviceContext1));

		StyleBookEntry styleBookEntry = _addStyleBookEntry(
			_serviceContext1, RandomTestUtil.randomString());

		_updateLayoutStyleBookEntryId(
			layoutPageTemplateEntry, styleBookEntry.getStyleBookEntryId());

		File file = _layoutsExporter.exportLayoutPageTemplateEntries(
			new long[] {layoutPageTemplateEntry.getLayoutPageTemplateEntryId()},
			LayoutPageTemplateEntryTypeConstants.BASIC);

		StyleBookEntry curStyleBookEntry = _addStyleBookEntry(
			_serviceContext2, styleBookEntry.getStyleBookEntryKey());

		List<LayoutsImporterResultEntry> layoutsImporterResultEntries =
			_layoutsImporter.importFile(
				TestPropsValues.getUserId(), _group2.getGroupId(), 0, file,
				LayoutsImportStrategy.DO_NOT_OVERWRITE, true);

		Assert.assertEquals(
			layoutsImporterResultEntries.toString(), 1,
			layoutsImporterResultEntries.size());

		LayoutsImporterResultEntry layoutsImporterResultEntry =
			layoutsImporterResultEntries.get(0);

		Assert.assertEquals(
			LayoutsImporterResultEntry.Status.IMPORTED,
			layoutsImporterResultEntry.getStatus());

		Assert.assertArrayEquals(
			new String[] {
				"Fragment with key " + fragmentEntryKey +
					" was ignored because it does not exist."
			},
			layoutsImporterResultEntry.getWarningMessages());

		LayoutPageTemplateEntry curLayoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				_group2.getGroupId(),
				_getLayoutPageTemplateEntryKey(layoutsImporterResultEntries));

		_assertFragmentEntryLink(fragmentEntry, curLayoutPageTemplateEntry);

		_assertStyleBookEntryId(
			curLayoutPageTemplateEntry,
			curStyleBookEntry.getStyleBookEntryId());
	}

	@Test
	@TestInfo("LPD-16086")
	public void testImportLayoutPageTemplateEntryWithURLTypeFragmentConfigurationField()
		throws Exception {

		JSONObject configurationJSONObject = JSONUtil.put(
			"fieldSets",
			JSONUtil.put(
				JSONUtil.put(
					"fields",
					JSONUtil.put(
						JSONUtil.put(
							"label", RandomTestUtil.randomString()
						).put(
							"name", "myURL"
						).put(
							"type", "url"
						)))));

		FragmentEntry fragmentEntry = _addFragmentEntry(
			configurationJSONObject.toString(), RandomTestUtil.randomString(),
			_serviceContext1);

		JSONObject editableValuesJSONObject = JSONUtil.put(
			FragmentEntryProcessorConstants.
				KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR,
			JSONUtil.put(
				"myURL", JSONUtil.put("href", "https://www.liferay.com")));

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_addLayoutPageTemplateEntry(
				editableValuesJSONObject.toString(), fragmentEntry);

		File file = _layoutsExporter.exportLayoutPageTemplateEntries(
			new long[] {layoutPageTemplateEntry.getLayoutPageTemplateEntryId()},
			LayoutPageTemplateEntryTypeConstants.BASIC);

		FragmentEntry curFragmentEntry = _addFragmentEntry(
			fragmentEntry, _serviceContext2);

		List<LayoutsImporterResultEntry> layoutsImporterResultEntries =
			_layoutsImporter.importFile(
				TestPropsValues.getUserId(), _group2.getGroupId(), 0, file,
				LayoutsImportStrategy.DO_NOT_OVERWRITE, true);

		_assertLayoutPageTemplateEntry(
			configurationJSONObject, editableValuesJSONObject, curFragmentEntry,
			_getLayoutPageTemplateEntryKey(layoutsImporterResultEntries));
	}

	@Test
	public void testValidateFile() throws Exception {
		Assert.assertTrue(
			_layoutsImporter.validateFile(
				_group1.getGroupId(),
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				_getFile(_RESOURCES_PATH_DISPLAY_PAGE_TEMPLATES)));
		Assert.assertTrue(
			_layoutsImporter.validateFile(
				_group1.getGroupId(),
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				_getFile(_RESOURCES_PATH_MASTER_PAGES)));
		Assert.assertTrue(
			_layoutsImporter.validateFile(
				_group1.getGroupId(),
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				_getFile(_RESOURCES_PATH_PAGE_TEMPLATES)));
	}

	@Test
	public void testValidateFileWithChildDuplicatedDisplayPageLayoutPageTemplateCollection()
		throws Exception {

		_layoutPageTemplateCollectionLocalService.
			addLayoutPageTemplateCollection(
				null, TestPropsValues.getUserId(), _group1.getGroupId(),
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				"Child Display Page Collection", StringPool.BLANK,
				LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE,
				ServiceContextTestUtil.getServiceContext(_group1.getGroupId()));

		Assert.assertTrue(
			_layoutsImporter.validateFile(
				_group1.getGroupId(),
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				_getFile(_RESOURCES_PATH_DISPLAY_PAGE_TEMPLATES)));
	}

	@Test
	public void testValidateFileWithDuplicatedBasicLayoutPageTemplateCollection()
		throws Exception {

		_layoutPageTemplateCollectionLocalService.
			addLayoutPageTemplateCollection(
				null, TestPropsValues.getUserId(), _group1.getGroupId(),
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				"Page Template Set", StringPool.BLANK,
				LayoutPageTemplateEntryTypeConstants.BASIC,
				ServiceContextTestUtil.getServiceContext(_group1.getGroupId()));

		Assert.assertFalse(
			_layoutsImporter.validateFile(
				_group1.getGroupId(),
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				_getFile(_RESOURCES_PATH_PAGE_TEMPLATES)));
	}

	@Test
	public void testValidateFileWithDuplicatedBasicLayoutPageTemplateEntry()
		throws Exception {

		_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			null, TestPropsValues.getUserId(), _group1.getGroupId(),
			LayoutPageTemplateConstants.
				PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
			"Page Template", LayoutPageTemplateEntryTypeConstants.BASIC, 0,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId()));

		Assert.assertFalse(
			_layoutsImporter.validateFile(
				_group1.getGroupId(),
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				_getFile(_RESOURCES_PATH_PAGE_TEMPLATES)));
	}

	@Test
	public void testValidateFileWithDuplicatedBasicLayoutPageTemplateEntryInADifferentLayoutPageTemplateCollection()
		throws Exception {

		_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			null, TestPropsValues.getUserId(), _group1.getGroupId(),
			LayoutPageTemplateConstants.
				PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
			"Page Template", LayoutPageTemplateEntryTypeConstants.BASIC, 0,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId()));

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					null, TestPropsValues.getUserId(), _group1.getGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					RandomTestUtil.randomString(), StringPool.BLANK,
					LayoutPageTemplateEntryTypeConstants.BASIC,
					ServiceContextTestUtil.getServiceContext(
						_group1.getGroupId()));

		Assert.assertTrue(
			_layoutsImporter.validateFile(
				_group1.getGroupId(),
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				_getFile(_RESOURCES_PATH_PAGE_TEMPLATES)));
	}

	@Test
	public void testValidateFileWithDuplicatedDisplayPageLayoutPageTemplateCollection()
		throws Exception {

		_layoutPageTemplateCollectionLocalService.
			addLayoutPageTemplateCollection(
				null, TestPropsValues.getUserId(), _group1.getGroupId(),
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				"Display Page Collection", StringPool.BLANK,
				LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE,
				ServiceContextTestUtil.getServiceContext(_group1.getGroupId()));

		Assert.assertFalse(
			_layoutsImporter.validateFile(
				_group1.getGroupId(),
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				_getFile(_RESOURCES_PATH_DISPLAY_PAGE_TEMPLATES)));
	}

	@Test
	public void testValidateFileWithDuplicatedDisplayPageLayoutPageTemplateCollectionInADifferentLayoutPageTemplateCollection()
		throws Exception {

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					null, TestPropsValues.getUserId(), _group1.getGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					RandomTestUtil.randomString(), StringPool.BLANK,
					LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE,
					ServiceContextTestUtil.getServiceContext(
						_group1.getGroupId()));

		_layoutPageTemplateCollectionLocalService.
			addLayoutPageTemplateCollection(
				null, TestPropsValues.getUserId(), _group1.getGroupId(),
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				"Display Page Collection", StringPool.BLANK,
				LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE,
				ServiceContextTestUtil.getServiceContext(_group1.getGroupId()));

		Assert.assertTrue(
			_layoutsImporter.validateFile(
				_group1.getGroupId(),
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				_getFile(_RESOURCES_PATH_DISPLAY_PAGE_TEMPLATES)));
	}

	@Test
	public void testValidateFileWithDuplicatedDisplayPageLayoutPageTemplateCollectionInsideADisplayPageLayoutPageTemplateCollection()
		throws Exception {

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					null, TestPropsValues.getUserId(), _group1.getGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					RandomTestUtil.randomString(), StringPool.BLANK,
					LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE,
					ServiceContextTestUtil.getServiceContext(
						_group1.getGroupId()));

		_layoutPageTemplateCollectionLocalService.
			addLayoutPageTemplateCollection(
				null, TestPropsValues.getUserId(), _group1.getGroupId(),
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				"Display Page Collection", StringPool.BLANK,
				LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE,
				ServiceContextTestUtil.getServiceContext(_group1.getGroupId()));

		Assert.assertFalse(
			_layoutsImporter.validateFile(
				_group1.getGroupId(),
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				_getFile(_RESOURCES_PATH_DISPLAY_PAGE_TEMPLATES)));
	}

	@Test
	public void testValidateFileWithDuplicatedDisplayPageLayoutPageTemplateEntry()
		throws Exception {

		_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			null, TestPropsValues.getUserId(), _group1.getGroupId(),
			LayoutPageTemplateConstants.
				PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
			"Basic Web Content Display Page Template",
			LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE, 0,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId()));

		Assert.assertFalse(
			_layoutsImporter.validateFile(
				_group1.getGroupId(),
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				_getFile(_RESOURCES_PATH_DISPLAY_PAGE_TEMPLATES)));
	}

	@Test
	public void testValidateFileWithDuplicatedDisplayPageLayoutPageTemplateEntryInADifferentLayoutPageTemplateCollection()
		throws Exception {

		_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			null, TestPropsValues.getUserId(), _group1.getGroupId(),
			LayoutPageTemplateConstants.
				PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
			"Basic Web Content Display Page Template",
			LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE, 0,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId()));

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					null, TestPropsValues.getUserId(), _group1.getGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					RandomTestUtil.randomString(), StringPool.BLANK,
					LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE,
					ServiceContextTestUtil.getServiceContext(
						_group1.getGroupId()));

		Assert.assertTrue(
			_layoutsImporter.validateFile(
				_group1.getGroupId(),
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				_getFile(_RESOURCES_PATH_DISPLAY_PAGE_TEMPLATES)));
	}

	@Test
	public void testValidateFileWithDuplicatedDisplayPageLayoutPageTemplateEntryInsideAChildDisplayPageLayoutPageTemplateCollection()
		throws Exception {

		_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			null, TestPropsValues.getUserId(), _group1.getGroupId(),
			LayoutPageTemplateConstants.
				PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
			"Blogs Display Page Template",
			LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE, 0,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId()));

		Assert.assertTrue(
			_layoutsImporter.validateFile(
				_group1.getGroupId(),
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				_getFile(_RESOURCES_PATH_DISPLAY_PAGE_TEMPLATES)));
	}

	@Test
	public void testValidateFileWithDuplicatedMasterLayoutLayoutPageTemplateEntry()
		throws Exception {

		_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			null, TestPropsValues.getUserId(), _group1.getGroupId(),
			LayoutPageTemplateConstants.
				PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
			"Default Master Page",
			LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT, 0,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId()));

		Assert.assertFalse(
			_layoutsImporter.validateFile(
				_group1.getGroupId(),
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				_getFile(_RESOURCES_PATH_MASTER_PAGES)));
	}

	private FragmentEntry _addFragmentEntry(
			FragmentEntry fragmentEntry, ServiceContext serviceContext)
		throws Exception {

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				null, TestPropsValues.getUserId(),
				serviceContext.getScopeGroupId(), StringUtil.randomString(),
				StringPool.BLANK, serviceContext);

		return _fragmentEntryLocalService.addFragmentEntry(
			null, TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			fragmentCollection.getFragmentCollectionId(),
			fragmentEntry.getFragmentEntryKey(), fragmentEntry.getName(),
			fragmentEntry.getCss(), fragmentEntry.getHtml(),
			fragmentEntry.getJs(), fragmentEntry.isCacheable(),
			fragmentEntry.getConfiguration(), null, 0, false,
			fragmentEntry.getType(), fragmentEntry.getTypeOptions(),
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	private FragmentEntry _addFragmentEntry(
			String configuration, String fragmentEntryKey,
			ServiceContext serviceContext)
		throws Exception {

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				null, TestPropsValues.getUserId(),
				serviceContext.getScopeGroupId(), StringUtil.randomString(),
				StringPool.BLANK, serviceContext);

		return _fragmentEntryLocalService.addFragmentEntry(
			null, TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			fragmentCollection.getFragmentCollectionId(), fragmentEntryKey,
			RandomTestUtil.randomString(), StringPool.BLANK,
			"<div class=\"fragment_1\"><a href=${configuration.myURL}>" +
				RandomTestUtil.randomString() + "</a></div>",
			StringPool.BLANK, false, configuration, null, 0, false,
			FragmentConstants.TYPE_COMPONENT, null,
			WorkflowConstants.STATUS_APPROVED, serviceContext);
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

	private void _addFragmentEntryLinks(
			LayoutPageTemplateEntry layoutPageTemplateEntry,
			FragmentEntry... fragmentEntries)
		throws Exception {

		Layout layout = _layoutLocalService.getLayout(
			layoutPageTemplateEntry.getPlid());

		Layout draftLayout = layout.fetchDraftLayout();

		List<Long> fragmentEntryLinkIds = TransformUtil.transformToList(
			fragmentEntries,
			fragmentEntry -> {
				FragmentEntryLink fragmentEntryLink =
					ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
						null, fragmentEntry.getCss(),
						fragmentEntry.getConfiguration(),
						fragmentEntry.getFragmentEntryId(),
						fragmentEntry.getHtml(), fragmentEntry.getJs(),
						draftLayout, fragmentEntry.getFragmentEntryKey(),
						_segmentsExperienceLocalService.
							fetchDefaultSegmentsExperienceId(
								draftLayout.getPlid()),
						fragmentEntry.getType());

				return fragmentEntryLink.getFragmentEntryLinkId();
			});

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layoutPageTemplateEntry.getGroupId(),
					layoutPageTemplateEntry.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getDefaultSegmentsExperienceData());

		Map<Long, LayoutStructureItem> fragmentLayoutStructureItems =
			layoutStructure.getFragmentLayoutStructureItems();

		Assert.assertTrue(
			MapUtil.toString(fragmentLayoutStructureItems),
			fragmentEntryLinkIds.containsAll(
				fragmentLayoutStructureItems.keySet()));

		ContentLayoutTestUtil.publishLayout(draftLayout, layout);
	}

	private FragmentEntryLink _addInputFragmentEntryLink(
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws Exception {

		ObjectDefinition objectDefinition = _addObjectDefinition();

		InfoItemFormProvider<?> infoItemFormProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFormProvider.class, objectDefinition.getClassName());

		InfoForm infoForm = infoItemFormProvider.getInfoForm(
			StringPool.BLANK, _group1.getGroupId());

		List<InfoField<?>> infoFields = ListUtil.filter(
			infoForm.getAllInfoFields(), InfoField::isEditable);

		Assert.assertEquals(infoFields.toString(), 1, infoFields.size());

		InfoField<?> infoField = infoFields.get(0);

		ContentLayoutTestUtil.addFormToPublishedLayout(
			false,
			String.valueOf(
				_portal.getClassNameId(objectDefinition.getClassName())),
			"0",
			_layoutLocalService.getLayout(layoutPageTemplateEntry.getPlid()),
			_layoutStructureProvider, infoField);

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinksByPlid(
				layoutPageTemplateEntry.getGroupId(),
				layoutPageTemplateEntry.getPlid());

		Assert.assertEquals(
			fragmentEntryLinks.toString(), 1, fragmentEntryLinks.size());

		FragmentEntryLink fragmentEntryLink = fragmentEntryLinks.get(0);

		Assert.assertTrue(
			fragmentEntryLink.getEditableValues(),
			JSONUtil.equals(
				JSONUtil.put(
					FragmentEntryProcessorConstants.
						KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR,
					JSONUtil.put("inputFieldId", infoField.getUniqueId())),
				_jsonFactory.createJSONObject(
					fragmentEntryLink.getEditableValues())));

		return fragmentEntryLink;
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
			String editableValues, FragmentEntry fragmentEntry)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_addLayoutPageTemplateEntry();

		Layout layout = _layoutLocalService.getLayout(
			layoutPageTemplateEntry.getPlid());

		Layout draftLayout = layout.fetchDraftLayout();
		long segmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid());

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				null, TestPropsValues.getUserId(), _group1.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(), segmentsExperienceId,
				draftLayout.getPlid(), fragmentEntry.getCss(),
				fragmentEntry.getHtml(), fragmentEntry.getConfiguration(),
				fragmentEntry.getConfiguration(), editableValues,
				StringPool.BLANK, 0, fragmentEntry.getFragmentEntryKey(),
				fragmentEntry.getType(), _serviceContext1);

		ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
			fragmentEntryLink, draftLayout, null, 0, segmentsExperienceId);

		ContentLayoutTestUtil.publishLayout(layout.fetchDraftLayout(), layout);

		return layoutPageTemplateEntry;
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
				null, TestPropsValues.getUserId(), _group1.getGroupId(), 0,
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

	private ObjectDefinition _addObjectDefinition() throws Exception {
		return ObjectDefinitionTestUtil.publishObjectDefinition(
			Collections.singletonList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, "myTextField",
					"myTextField", false)));
	}

	private StyleBookEntry _addStyleBookEntry(
			ServiceContext serviceContext, String styleBookEntryKey)
		throws Exception {

		return _styleBookEntryLocalService.addStyleBookEntry(
			null, TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			false, StringPool.BLANK, RandomTestUtil.randomString(),
			styleBookEntryKey, StringPool.BLANK, serviceContext);
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
			FragmentEntry fragmentEntry, FragmentEntryLink fragmentEntryLink,
			String layoutPageTemplateEntryKey)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				_group2.getGroupId(), layoutPageTemplateEntryKey);

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinksByPlid(
				layoutPageTemplateEntry.getGroupId(),
				layoutPageTemplateEntry.getPlid());

		Assert.assertEquals(
			fragmentEntryLinks.toString(), 1, fragmentEntryLinks.size());

		FragmentEntryLink curFragmentEntryLink = fragmentEntryLinks.get(0);

		Assert.assertEquals(
			fragmentEntry.getFragmentEntryId(),
			curFragmentEntryLink.getFragmentEntryId());

		Assert.assertTrue(
			curFragmentEntryLink.getEditableValues(),
			JSONUtil.equals(
				_jsonFactory.createJSONObject(
					fragmentEntryLink.getEditableValues()),
				_jsonFactory.createJSONObject(
					curFragmentEntryLink.getEditableValues())));
	}

	private void _assertLayoutPageTemplateEntry(
			JSONObject configurationJSONObject,
			JSONObject editableValuesJSONObject, FragmentEntry fragmentEntry,
			String layoutPageTemplateEntryKey)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				_group2.getGroupId(), layoutPageTemplateEntryKey);

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinksByPlid(
				layoutPageTemplateEntry.getGroupId(),
				layoutPageTemplateEntry.getPlid());

		Assert.assertEquals(
			fragmentEntryLinks.toString(), 1, fragmentEntryLinks.size());

		FragmentEntryLink fragmentEntryLink = fragmentEntryLinks.get(0);

		Assert.assertEquals(
			fragmentEntry.getFragmentEntryId(),
			fragmentEntryLink.getFragmentEntryId());
		Assert.assertTrue(
			fragmentEntryLink.getConfiguration(),
			JSONUtil.equals(
				configurationJSONObject,
				_jsonFactory.createJSONObject(
					fragmentEntryLink.getConfiguration())));
		Assert.assertTrue(
			fragmentEntryLink.getEditableValues(),
			JSONUtil.equals(
				editableValuesJSONObject,
				_jsonFactory.createJSONObject(
					fragmentEntryLink.getEditableValues())));
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
			layoutPageTemplateEntryKey.equals(
				"basic-web-content-display-page-template") ||
			layoutPageTemplateEntryKey.equals("blogs-display-page-template") ||
			layoutPageTemplateEntryKey.equals("product-display-page-template"));

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
		else if (Objects.equals(
					layoutPageTemplateEntryKey,
					"product-display-page-template")) {

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

		LayoutPageTemplateEntry curLayoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				_group2.getGroupId(),
				_getLayoutPageTemplateEntryKey(layoutsImporterResultEntries));

		_assertFragmentEntryLink(fragmentEntry, curLayoutPageTemplateEntry);
	}

	private void _assertLayoutsImporterResultEntries(
			List<LayoutsImporterResultEntry> layoutsImporterResultEntries,
			LayoutPageTemplateStructure layoutPageTemplateStructure)
		throws Exception {

		LayoutPageTemplateEntry curLayoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				_group2.getGroupId(),
				_getLayoutPageTemplateEntryKey(layoutsImporterResultEntries));

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

	private void _assertStyleBookEntryId(
			LayoutPageTemplateEntry layoutPageTemplateEntry,
			long styleBookEntryId)
		throws Exception {

		Layout layout = _layoutLocalService.getLayout(
			layoutPageTemplateEntry.getPlid());

		Assert.assertEquals(styleBookEntryId, layout.getStyleBookEntryId());

		Layout draftLayout = layout.fetchDraftLayout();

		Assert.assertEquals(
			styleBookEntryId, draftLayout.getStyleBookEntryId());
	}

	private ContainerStyledLayoutStructureItem _getContainerLayoutStructureItem(
		LayoutStructure layoutStructure) {

		LayoutStructureItem layoutStructureItem =
			_getMainChildLayoutStructureItem(layoutStructure);

		Assert.assertTrue(
			layoutStructureItem instanceof ContainerStyledLayoutStructureItem);

		return (ContainerStyledLayoutStructureItem)layoutStructureItem;
	}

	private File _getFile(String resourcePath) throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		Enumeration<URL> enumeration = bundle.findEntries(
			resourcePath, "*", true);

		ZipWriter zipWriter = _zipWriterFactory.getZipWriter();

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			String path = url.getPath();

			if (!path.endsWith(StringPool.SLASH)) {
				try (InputStream inputStream = url.openStream()) {
					zipWriter.addEntry(
						StringUtil.removeSubstring(url.getPath(), resourcePath),
						inputStream);
				}
			}
		}

		return zipWriter.getFile();
	}

	private String _getLayoutPageTemplateEntryKey(
		List<LayoutsImporterResultEntry> layoutsImporterResultEntries) {

		Assert.assertEquals(
			layoutsImporterResultEntries.toString(), 1,
			layoutsImporterResultEntries.size());

		LayoutsImporterResultEntry layoutsImporterResultEntry =
			layoutsImporterResultEntries.get(0);

		Assert.assertEquals(
			LayoutsImporterResultEntry.Status.IMPORTED,
			layoutsImporterResultEntry.getStatus());

		return StringUtil.replace(
			StringUtil.toLowerCase(layoutsImporterResultEntry.getName()),
			CharPool.SPACE, CharPool.DASH);
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

	private void _updateLayoutStyleBookEntryId(
			LayoutPageTemplateEntry layoutPageTemplateEntry,
			long styleBookEntryId)
		throws Exception {

		Layout layout = _layoutLocalService.getLayout(
			layoutPageTemplateEntry.getPlid());

		Layout draftLayout = layout.fetchDraftLayout();

		ContentLayoutTestUtil.publishLayout(
			_layoutLocalService.updateStyleBookEntryId(
				draftLayout.getGroupId(), draftLayout.isPrivateLayout(),
				draftLayout.getLayoutId(), styleBookEntryId),
			layout);

		_assertStyleBookEntryId(layoutPageTemplateEntry, styleBookEntryId);
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

	private static final String _RESOURCES_PATH_DISPLAY_PAGE_TEMPLATES =
		"com/liferay/layout/page/template/admin/web/internal/importer/test" +
			"/dependencies/display-page-templates";

	private static final String _RESOURCES_PATH_MASTER_PAGES =
		"com/liferay/layout/page/template/admin/web/internal/importer/test" +
			"/dependencies/master-pages";

	private static final String _RESOURCES_PATH_PAGE_TEMPLATES =
		"com/liferay/layout/page/template/admin/web/internal/importer/test" +
			"/dependencies/page-templates";

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
	private InfoItemServiceRegistry _infoItemServiceRegistry;

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
	private LayoutsExporter _layoutsExporter;

	@Inject
	private LayoutsImporter _layoutsImporter;

	@Inject
	private LayoutStructureProvider _layoutStructureProvider;

	@Inject
	private Portal _portal;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	private ServiceContext _serviceContext1;
	private ServiceContext _serviceContext2;

	@Inject
	private StyleBookEntryLocalService _styleBookEntryLocalService;

	@Inject
	private ZipWriterFactory _zipWriterFactory;

}