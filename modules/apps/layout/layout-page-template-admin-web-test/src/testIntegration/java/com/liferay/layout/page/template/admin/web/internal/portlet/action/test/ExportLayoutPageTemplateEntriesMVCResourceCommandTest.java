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

package com.liferay.layout.page.template.admin.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.io.File;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.portlet.ResourceRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class ExportLayoutPageTemplateEntriesMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, TestPropsValues.getUserId());
	}

	@Test
	public void testGetFileMultipleLayoutPageTemplatesSingleCollection()
		throws Exception {

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					TestPropsValues.getUserId(), _group.getGroupId(),
					"Page Template Collection One", StringPool.BLANK,
					_serviceContext);

		String name1 = "Page Template One";

		LayoutPageTemplateEntry layoutPageTemplateEntry1 =
			_addLayoutPageTemplateEntry(
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				name1);

		String name2 = "Page Template Two";

		LayoutPageTemplateEntry layoutPageTemplateEntry2 =
			_addLayoutPageTemplateEntry(
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				name2);

		File file = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "getFile", new Class<?>[] {long[].class},
			new long[] {
				layoutPageTemplateEntry1.getLayoutPageTemplateEntryId(),
				layoutPageTemplateEntry2.getLayoutPageTemplateEntryId()
			});

		try (ZipFile zipFile = new ZipFile(file)) {
			int count = 0;

			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			while (enumeration.hasMoreElements()) {
				ZipEntry zipEntry = enumeration.nextElement();

				if (!zipEntry.isDirectory()) {
					_validateZipEntry(
						new String[] {name1, name2}, zipEntry, zipFile);

					count++;
				}
			}

			Assert.assertEquals(7, count);
		}
	}

	@Test
	public void testGetFileNameMultiplePageTemplates() {
		String fileName = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "getFileName", new Class<?>[] {long[].class},
			new long[] {
				RandomTestUtil.randomLong(), RandomTestUtil.randomLong()
			});

		Assert.assertTrue(fileName.startsWith("page-templates-"));
		Assert.assertTrue(fileName.endsWith(".zip"));
	}

	@Test
	public void testGetFileNameSinglePageTemplate() throws Exception {
		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					TestPropsValues.getUserId(), _group.getGroupId(),
					"Page Template Collection One", StringPool.BLANK,
					_serviceContext);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				"Page Template One",
				LayoutPageTemplateEntryTypeConstants.TYPE_BASIC, 0,
				WorkflowConstants.STATUS_DRAFT, _serviceContext);

		String fileName = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "getFileName", new Class<?>[] {long[].class},
			new long[] {
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId()
			});

		Assert.assertTrue(
			fileName.startsWith(
				"page-template-" +
					layoutPageTemplateEntry.getLayoutPageTemplateEntryKey() +
						"-"));
		Assert.assertTrue(fileName.endsWith(".zip"));
	}

	@Test
	public void testGetFileSingleLayoutPageTemplate() throws Exception {
		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					TestPropsValues.getUserId(), _group.getGroupId(),
					"Page Template Collection One", StringPool.BLANK,
					_serviceContext);

		String name = "Page Template One";

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_addLayoutPageTemplateEntry(
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				name);

		File file = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "getFile", new Class<?>[] {long[].class},
			new long[] {
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId()
			});

		try (ZipFile zipFile = new ZipFile(file)) {
			int count = 0;

			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			while (enumeration.hasMoreElements()) {
				ZipEntry zipEntry = enumeration.nextElement();

				if (!zipEntry.isDirectory()) {
					_validateZipEntry(new String[] {name}, zipEntry, zipFile);

					count++;
				}
			}

			Assert.assertEquals(4, count);
		}
	}

	@Test
	public void testGetFileSingleLayoutPageTemplateDraft() throws Exception {
		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					TestPropsValues.getUserId(), _group.getGroupId(),
					RandomTestUtil.randomString(10), StringPool.BLANK,
					_serviceContext);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_addLayoutPageTemplateEntry(
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				RandomTestUtil.randomString(10),
				WorkflowConstants.STATUS_DRAFT);

		File file = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "getFile", new Class<?>[] {long[].class},
			new long[] {
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId()
			});

		try (ZipFile zipFile = new ZipFile(file)) {
			Assert.assertEquals(0, zipFile.size());
		}
	}

	@Test
	public void testGetLayoutPageTemplateEntryIdsSinglePageTemplate() {
		long expectedLayoutPageTemplateEntryId = RandomTestUtil.randomLong();

		long[] actualLayoutPageTemplateEntryIds = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "getLayoutPageTemplateEntryIds",
			new Class<?>[] {ResourceRequest.class},
			_getMockLiferayResourceRequest(expectedLayoutPageTemplateEntryId));

		Assert.assertEquals(
			Arrays.toString(actualLayoutPageTemplateEntryIds), 1,
			actualLayoutPageTemplateEntryIds.length);
		Assert.assertEquals(
			expectedLayoutPageTemplateEntryId,
			actualLayoutPageTemplateEntryIds[0]);
	}

	private LayoutPageTemplateEntry _addLayoutPageTemplateEntry(
			long layoutPageTemplateCollectionId, String name)
		throws Exception {

		return _addLayoutPageTemplateEntry(
			layoutPageTemplateCollectionId, name,
			WorkflowConstants.STATUS_APPROVED);
	}

	private LayoutPageTemplateEntry _addLayoutPageTemplateEntry(
			long layoutPageTemplateCollectionId, String name, int status)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
				layoutPageTemplateCollectionId, name,
				LayoutPageTemplateEntryTypeConstants.TYPE_BASIC, 0, status,
				_serviceContext);

		long defaultSegmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layoutPageTemplateEntry.getPlid());

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructureData(
				_group.getGroupId(), layoutPageTemplateEntry.getPlid(),
				defaultSegmentsExperienceId, _read("layout_data.json"));

		Repository repository = PortletFileRepositoryUtil.addPortletRepository(
			_group.getGroupId(), RandomTestUtil.randomString(),
			_serviceContext);

		Class<?> clazz = getClass();

		FileEntry fileEntry = PortletFileRepositoryUtil.addPortletFileEntry(
			_group.getGroupId(), TestPropsValues.getUserId(),
			LayoutPageTemplateEntry.class.getName(),
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			RandomTestUtil.randomString(), repository.getDlFolderId(),
			clazz.getResourceAsStream("dependencies/thumbnail.png"),
			RandomTestUtil.randomString(), ContentTypes.IMAGE_PNG, false);

		_layoutPageTemplateEntryLocalService.updateLayoutPageTemplateEntry(
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			fileEntry.getFileEntryId());

		return layoutPageTemplateEntry;
	}

	private MockLiferayResourceRequest _getMockLiferayResourceRequest(
		long layoutPageTemplateEntryId) {

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		mockLiferayResourceRequest.addParameter(
			"layoutPageTemplateEntryId",
			String.valueOf(layoutPageTemplateEntryId));

		return mockLiferayResourceRequest;
	}

	private boolean _isPageDefinitionFile(String path) {
		String[] pathParts = StringUtil.split(path, CharPool.SLASH);

		if ((pathParts.length == 4) &&
			Objects.equals(pathParts[0], "page-templates") &&
			Objects.equals(pathParts[3], "page-definition.json")) {

			return true;
		}

		return false;
	}

	private boolean _isPageTemplateCollectionFile(String path) {
		String[] pathParts = StringUtil.split(path, CharPool.SLASH);

		if ((pathParts.length == 3) &&
			Objects.equals(pathParts[0], "page-templates") &&
			Objects.equals(pathParts[2], "page-template-collection.json")) {

			return true;
		}

		return false;
	}

	private boolean _isPageTemplateFile(String path) {
		String[] pathParts = StringUtil.split(path, CharPool.SLASH);

		if ((pathParts.length == 4) &&
			Objects.equals(pathParts[0], "page-templates") &&
			Objects.equals(pathParts[3], "page-template.json")) {

			return true;
		}

		return false;
	}

	private boolean _isPageTemplateThumbnailFile(String fileName) {
		String[] pathParts = StringUtil.split(fileName, CharPool.SLASH);

		if ((pathParts.length == 4) &&
			Objects.equals(pathParts[0], "page-templates") &&
			Objects.equals(pathParts[3], "thumbnail.png")) {

			return true;
		}

		return false;
	}

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

	private void _validateContent(String content, String expectedFileName)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(content);

		JSONObject expectedJSONObject = JSONFactoryUtil.createJSONObject(
			_read(expectedFileName));

		Assert.assertEquals(
			expectedJSONObject.toJSONString(), jsonObject.toJSONString());
	}

	private void _validateContent(
			String content, String expectedFileName,
			String[] expectedPageTemplateNames)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(content);

		boolean equals = false;

		for (String expectedPageTemplateName : expectedPageTemplateNames) {
			JSONObject expectedJSONObject = JSONFactoryUtil.createJSONObject(
				StringUtil.replace(
					_read(expectedFileName), "${", "}",
					HashMapBuilder.put(
						"PAGE_TEMPLATE_NAME", expectedPageTemplateName
					).build()));

			String expectedJSON1 = expectedJSONObject.toJSONString();

			equals = expectedJSON1.equals(jsonObject.toJSONString());

			if (equals) {
				break;
			}
		}

		Assert.assertTrue(equals);
	}

	private void _validateZipEntry(
			String[] expectedPageTemplateNames, ZipEntry zipEntry,
			ZipFile zipFile)
		throws Exception {

		if (_isPageDefinitionFile(zipEntry.getName())) {
			_validateContent(
				StringUtil.read(zipFile.getInputStream(zipEntry)),
				"expected_page_template_page_definition.json");
		}

		if (_isPageTemplateCollectionFile(zipEntry.getName())) {
			_validateContent(
				StringUtil.read(zipFile.getInputStream(zipEntry)),
				"expected_page_template_collection.json");
		}

		if (_isPageTemplateFile(zipEntry.getName())) {
			_validateContent(
				StringUtil.read(zipFile.getInputStream(zipEntry)),
				"expected_page_template.json", expectedPageTemplateNames);
		}

		if (_isPageTemplateThumbnailFile(zipEntry.getName())) {
			Assert.assertArrayEquals(
				FileUtil.getBytes(getClass(), "dependencies/thumbnail.png"),
				FileUtil.getBytes(zipFile.getInputStream(zipEntry)));
		}
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject(
		filter = "mvc.command.name=/layout_page_template_admin/export_layout_page_template_entries"
	)
	private MVCResourceCommand _mvcResourceCommand;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	private ServiceContext _serviceContext;

}