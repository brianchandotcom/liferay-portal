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
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.File;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
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
public class ExportDisplayPagesMVCResourceCommandTest {

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
	public void testGetFile() throws Exception {
		String className = "com.liferay.journal.model.JournalArticle";

		long classNameId = _portal.getClassNameId(className);

		InfoDisplayContributor infoDisplayContributor =
			_infoDisplayContributorTracker.getInfoDisplayContributor(className);

		long classTypeId = 0;

		List<ClassType> classTypes = infoDisplayContributor.getClassTypes(
			_group.getGroupId(), LocaleUtil.getSiteDefault());

		for (ClassType classType : classTypes) {
			if (Objects.equals(classType.getName(), "Basic Web Content")) {
				classTypeId = classType.getClassTypeId();
			}
		}

		Assert.assertNotEquals(0, classTypeId);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
				0, classNameId, classTypeId, "Display Page Template One",
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE, 0,
				WorkflowConstants.STATUS_APPROVED, _serviceContext);

		_layoutPageTemplateStructureLocalService.addLayoutPageTemplateStructure(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_portal.getClassNameId(Layout.class.getName()),
			layoutPageTemplateEntry.getPlid(), _read("layout_data.json"),
			_serviceContext);

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

		long[] layoutPageTemplateEntryIds = {
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId()
		};

		File file = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "getFile", new Class<?>[] {long[].class},
			layoutPageTemplateEntryIds);

		try (ZipFile zipFile = new ZipFile(file)) {
			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			while (enumeration.hasMoreElements()) {
				ZipEntry zipEntry = enumeration.nextElement();

				_validateZipEntry(zipEntry, zipFile);
			}

			Assert.assertEquals(3, zipFile.size());
		}
	}

	@Test
	public void testGetFileNameMultipleDisplayPageTemplates() {
		long[] layoutPageTemplateEntryIds = {
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong()
		};

		String fileName = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "getFileName", new Class<?>[] {long[].class},
			layoutPageTemplateEntryIds);

		Assert.assertTrue(fileName.startsWith("display-page-templates-"));
		Assert.assertTrue(fileName.endsWith(".zip"));
	}

	@Test
	public void testGetFileNameSingleDisplayPageTemplate() throws Exception {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
				0, "Display Page Template One",
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE, 0,
				WorkflowConstants.STATUS_APPROVED, _serviceContext);

		long[] layoutPageTemplateEntryIds = {
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId()
		};

		String fileName = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "getFileName", new Class<?>[] {long[].class},
			layoutPageTemplateEntryIds);

		Assert.assertTrue(
			fileName.startsWith(
				"display-page-template-" +
					layoutPageTemplateEntry.getLayoutPageTemplateEntryKey() +
						"-"));
		Assert.assertTrue(fileName.endsWith(".zip"));
	}

	@Test
	public void testGetLayoutPageTemplateEntryIdsSingleDisplayPageTemplate() {
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

	private MockLiferayResourceRequest _getMockLiferayResourceRequest(
		long layoutPageTemplateEntryId) {

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		mockLiferayResourceRequest.addParameter(
			"layoutPageTemplateEntryId",
			String.valueOf(layoutPageTemplateEntryId));

		return mockLiferayResourceRequest;
	}

	private boolean _isDisplayPageFile(String path) {
		String[] pathParts = StringUtil.split(path, CharPool.SLASH);

		if ((pathParts.length == 3) &&
			Objects.equals(pathParts[0], "display-page-templates") &&
			Objects.equals(pathParts[2], "display-page-template.json")) {

			return true;
		}

		return false;
	}

	private boolean _isDisplayPageThumbnailFile(String fileName) {
		String[] pathParts = StringUtil.split(fileName, CharPool.SLASH);

		if ((pathParts.length == 3) &&
			Objects.equals(pathParts[0], "display-page-templates") &&
			Objects.equals(pathParts[2], "thumbnail.png")) {

			return true;
		}

		return false;
	}

	private boolean _isPageDefinitionFile(String path) {
		String[] pathParts = StringUtil.split(path, CharPool.SLASH);

		if ((pathParts.length == 3) &&
			Objects.equals(pathParts[0], "display-pages") &&
			Objects.equals(pathParts[2], "page-definition.json")) {

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

	private void _validateZipEntry(ZipEntry zipEntry, ZipFile zipFile)
		throws Exception {

		if (_isPageDefinitionFile(zipEntry.getName())) {
			_validateContent(
				StringUtil.read(zipFile.getInputStream(zipEntry)),
				"expected_display_page_page_template_definition.json");
		}

		if (_isDisplayPageFile(zipEntry.getName())) {
			_validateContent(
				StringUtil.read(zipFile.getInputStream(zipEntry)),
				"expected_display_page_template.json");
		}

		if (_isDisplayPageThumbnailFile(zipEntry.getName())) {
			Assert.assertArrayEquals(
				FileUtil.getBytes(getClass(), "dependencies/thumbnail.png"),
				FileUtil.getBytes(zipFile.getInputStream(zipEntry)));
		}
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject(
		filter = "mvc.command.name=/layout_page_template/export_display_page"
	)
	private MVCResourceCommand _mvcResourceCommand;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;

}