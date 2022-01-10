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

package com.liferay.fragment.internal.struts.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentCompositionService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ProgressTracker;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upload.LiferayFileItem;
import com.liferay.portal.upload.LiferayFileItemFactory;
import com.liferay.portal.upload.UploadPortletRequestImpl;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portal.util.PropsValues;

import java.io.OutputStream;

import java.net.URL;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;

/**
 * @author Pavel Savinov
 */
@RunWith(Arquillian.class)
public class ImportFragmentEntriesStrutsActionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_bundle = FrameworkUtil.getBundle(getClass());

		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(_group);

		_userLocalService.deleteUser(_user);
	}

	@Test
	public void testImportZipFile() throws Exception {
		_user = UserTestUtil.addOmniAdminUser();

		UserTestUtil.setUser(_user);

		byte[] bytes = _getFileBytes();

		Map<String, FileItem[]> fileParameters = _getFileParameters(
			bytes, "file");

		HttpServletRequest httpServletRequest = _getMultipartHttpServletRequest(
			bytes, "file");

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					httpServletRequest, fileParameters,
					HashMapBuilder.put(
						"groupId",
						Collections.singletonList(
							String.valueOf(_group.getGroupId()))
					).build()),
				null, RandomTestUtil.randomString());

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_processEvents(uploadPortletRequest, mockHttpServletResponse, _user);

		_importFragmentEntriesStrutsAction.execute(
			uploadPortletRequest, mockHttpServletResponse);

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionLocalService.getFragmentCollections(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			fragmentCollections.toString(), 1, fragmentCollections.size());

		FragmentCollection fragmentCollection = fragmentCollections.get(0);

		Assert.assertEquals(
			4,
			_fragmentEntryLocalService.getFragmentEntriesCount(
				fragmentCollection.getFragmentCollectionId()));

		Assert.assertEquals(
			1,
			_fragmentCompositionService.getFragmentCompositionsCount(
				fragmentCollection.getFragmentCollectionId()));

		Assert.assertNotNull(
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				_group.getGroupId(), "master-page"));

		Assert.assertNotNull(
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				_group.getGroupId(), "page-template"));
	}

	private byte[] _getFileBytes() throws Exception {
		Enumeration<URL> enumeration = _bundle.findEntries(
			_RESOURCES_PATH, "*", true);

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			String path = url.getPath();

			if (!path.endsWith(StringPool.SLASH)) {
				zipWriter.addEntry(
					StringUtil.removeSubstring(url.getPath(), _RESOURCES_PATH),
					url.openStream());
			}
		}

		return FileUtil.getBytes(zipWriter.getFile());
	}

	private Map<String, FileItem[]> _getFileParameters(
			byte[] bytes, String namespace)
		throws Exception {

		return HashMapBuilder.<String, FileItem[]>put(
			namespace,
			() -> {
				LiferayFileItemFactory fileItemFactory =
					new LiferayFileItemFactory(
						UploadServletRequestImpl.getTempDir());

				LiferayFileItem liferayFileItem = fileItemFactory.createItem(
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString(), true,
					RandomTestUtil.randomString());

				try (OutputStream outputStream =
						liferayFileItem.getOutputStream()) {

					outputStream.write(bytes);
				}

				return new FileItem[] {liferayFileItem};
			}
		).build();
	}

	private HttpServletRequest _getMultipartHttpServletRequest(
		byte[] bytes, String fileNameParameter) {

		MockMultipartHttpServletRequest mockMultipartHttpServletRequest =
			new MockMultipartHttpServletRequest();

		mockMultipartHttpServletRequest.addFile(
			new MockMultipartFile(fileNameParameter, bytes));
		mockMultipartHttpServletRequest.setContent(bytes);
		mockMultipartHttpServletRequest.setContentType(
			"multipart/form-data;boundary=" + System.currentTimeMillis());
		mockMultipartHttpServletRequest.setCharacterEncoding("UTF-8");

		MockHttpSession mockHttpSession = new MockHttpSession();

		mockHttpSession.setAttribute(ProgressTracker.PERCENT, new Object());

		mockMultipartHttpServletRequest.setSession(mockHttpSession);

		return mockMultipartHttpServletRequest;
	}

	private void _processEvents(
			HttpServletRequest mockHttpServletRequest,
			MockHttpServletResponse mockHttpServletResponse, User user)
		throws Exception {

		mockHttpServletRequest.setAttribute(
			WebKeys.CURRENT_URL, "/portal/fragment/import_fragment_entries");

		mockHttpServletRequest.setAttribute(WebKeys.USER, user);

		EventsProcessorUtil.process(
			PropsKeys.SERVLET_SERVICE_EVENTS_PRE,
			PropsValues.SERVLET_SERVICE_EVENTS_PRE, mockHttpServletRequest,
			mockHttpServletResponse);
	}

	private static final String _RESOURCES_PATH =
		"com/liferay/fragment/dependencies/fragments/import/";

	private Bundle _bundle;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentCompositionService _fragmentCompositionService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	private Group _group;

	@Inject(filter = "component.name=*.ImportFragmentEntriesStrutsAction")
	private StrutsAction _importFragmentEntriesStrutsAction;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}