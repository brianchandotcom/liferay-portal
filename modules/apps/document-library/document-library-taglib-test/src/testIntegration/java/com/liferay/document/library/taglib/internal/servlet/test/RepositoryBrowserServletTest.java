/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.taglib.internal.servlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.petra.memory.DeleteFileFinalizeAction;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upload.test.util.UploadTestUtil;

import jakarta.servlet.Servlet;
import jakarta.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class RepositoryBrowserServletTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = UserTestUtil.addUser();
	}

	@Test
	public void testDoPutFileEntryWithGuestPermissions() throws Exception {
		String name = RandomTestUtil.randomString();

		MockMultipartFile mockMultipartFile = new MockMultipartFile(
			"file", _BYTES);

		_servlet.service(
			_getMockMultipartHttpServletRequest(
				name, RandomTestUtil.randomString(), mockMultipartFile, ".txt",
				false, true),
			new MockHttpServletResponse());

		_getFileEntry(name);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testDoPutFileEntryWithoutGuestPermissions() throws Exception {
		String name = RandomTestUtil.randomString();

		MockMultipartFile mockMultipartFile = new MockMultipartFile(
			"file", _BYTES);

		_servlet.service(
			_getMockMultipartHttpServletRequest(
				name, RandomTestUtil.randomString(), mockMultipartFile, ".txt",
				false, false),
			new MockHttpServletResponse());

		_getFileEntry(name);
	}

	@Test
	public void testDoPutFolderWithGuestPermissions() throws Exception {
		String name = RandomTestUtil.randomString();

		_servlet.service(
			_getMockMultipartHttpServletRequest(0, name, "PUT", false, true),
			new MockHttpServletResponse());

		_getFolder(name);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testDoPutFolderWithoutGuestPermissions() throws Exception {
		String name = RandomTestUtil.randomString();

		_servlet.service(
			_getMockMultipartHttpServletRequest(0, name, "PUT", false, false),
			new MockHttpServletResponse());

		_getFolder(name);
	}

	@Test
	@TestInfo("LPD-55643")
	public void testIncludeExtensionWhenNewNameHasDifferentExtension()
		throws Exception {

		String extension = "txt";
		String mimeType = "text/plain";
		String fileName = RandomTestUtil.randomString() + "." + extension;
		String newName = RandomTestUtil.randomString() + ".pdf";

		String expectedNewName = newName + "." + extension;

		MockMultipartFile mockMultipartFile = new MockMultipartFile(
			"file", _BYTES);

		_servlet.service(
			_getMockMultipartHttpServletRequest(
				fileName, newName, mockMultipartFile, extension, true, true),
			new MockHttpServletResponse());

		FileEntry fileEntry = _getFileEntry(fileName);

		_assertFileEntryAttributes(
			fileEntry, fileName, fileName, extension, mimeType);

		_servlet.service(
			_getMockMultipartHttpServletRequest(
				fileEntry.getFileEntryId(), newName, "POST", true, true),
			new MockHttpServletResponse());

		_assertFileEntryAttributes(
			_getFileEntry(expectedNewName), expectedNewName, expectedNewName,
			extension, mimeType);
	}

	@Test
	@TestInfo("LPD-55643")
	public void testIncludeExtensionWhenNewNameHasSameExtension()
		throws Exception {

		String extension = "txt";
		String mimeType = "text/plain";
		String fileName = RandomTestUtil.randomString() + "." + extension;
		String newName = RandomTestUtil.randomString() + "." + extension;

		MockMultipartFile mockMultipartFile = new MockMultipartFile(
			"file", _BYTES);

		_servlet.service(
			_getMockMultipartHttpServletRequest(
				fileName, newName, mockMultipartFile, extension, true, true),
			new MockHttpServletResponse());

		FileEntry fileEntry = _getFileEntry(fileName);

		_assertFileEntryAttributes(
			fileEntry, fileName, fileName, extension, mimeType);

		_servlet.service(
			_getMockMultipartHttpServletRequest(
				fileEntry.getFileEntryId(), newName, "POST", true, true),
			new MockHttpServletResponse());

		_assertFileEntryAttributes(
			_getFileEntry(newName), newName, newName, extension, mimeType);
	}

	@Test
	@TestInfo("LPD-55643")
	public void testIncludeExtensionWhenOriginalFileHasExtensionANDNewNameHasNoExtension()
		throws Exception {

		String extension = "txt";
		String mimeType = "text/plain";
		String fileName = RandomTestUtil.randomString() + "." + extension;
		String newName = RandomTestUtil.randomString();

		String expectedNewName = newName + "." + extension;

		MockMultipartFile mockMultipartFile = new MockMultipartFile(
			"file", _BYTES);

		_servlet.service(
			_getMockMultipartHttpServletRequest(
				fileName, newName, mockMultipartFile, extension, true, true),
			new MockHttpServletResponse());

		FileEntry fileEntry = _getFileEntry(fileName);

		_assertFileEntryAttributes(
			fileEntry, fileName, fileName, extension, mimeType);

		_servlet.service(
			_getMockMultipartHttpServletRequest(
				fileEntry.getFileEntryId(), newName, "POST", true, true),
			new MockHttpServletResponse());

		_assertFileEntryAttributes(
			_getFileEntry(expectedNewName), expectedNewName, expectedNewName,
			extension, mimeType);
	}

	@Test
	@TestInfo("LPD-55643")
	public void testIncludeExtensionWhenOriginalHasNoExtension()
		throws Exception {

		String extension = "";
		String mimeType = "text/plain";
		String fileName = RandomTestUtil.randomString();
		String newName = RandomTestUtil.randomString();

		MockMultipartFile mockMultipartFile = new MockMultipartFile(
			"file", _BYTES);

		_servlet.service(
			_getMockMultipartHttpServletRequest(
				fileName, newName, mockMultipartFile, extension, true, true),
			new MockHttpServletResponse());

		FileEntry fileEntry = _getFileEntry(fileName);

		_assertFileEntryAttributes(
			fileEntry, fileName, fileName, extension, mimeType);

		_servlet.service(
			_getMockMultipartHttpServletRequest(
				fileEntry.getFileEntryId(), newName, "POST", true, true),
			new MockHttpServletResponse());

		_assertFileEntryAttributes(
			_getFileEntry(newName), newName, newName, extension, mimeType);
	}

	@Test
	@TestInfo("LPD-55643")
	public void testNotIncludeExtension() throws Exception {
		String extension = "txt";
		String mimeType = "text/plain";
		String title = RandomTestUtil.randomString();

		String fileName = title + "." + extension;

		String newName = RandomTestUtil.randomString() + "." + extension;

		MockMultipartFile mockMultipartFile = new MockMultipartFile(
			"file", _BYTES);

		_servlet.service(
			_getMockMultipartHttpServletRequest(
				fileName, newName, mockMultipartFile, extension, false, true),
			new MockHttpServletResponse());

		FileEntry fileEntry = _getFileEntry(title);

		_assertFileEntryAttributes(
			fileEntry, fileName, title, extension, mimeType);

		_servlet.service(
			_getMockMultipartHttpServletRequest(
				fileEntry.getFileEntryId(), newName, "POST", false, true),
			new MockHttpServletResponse());

		_assertFileEntryAttributes(
			_getFileEntry(newName), newName, newName, extension, mimeType);
	}

	private void _assertFileEntryAttributes(
		FileEntry fileEntry, String fileName, String title, String extension,
		String mimeType) {

		Assert.assertEquals(fileName, fileEntry.getFileName());
		Assert.assertEquals(title, fileEntry.getTitle());
		Assert.assertEquals(extension, fileEntry.getExtension());
		Assert.assertEquals(mimeType, fileEntry.getMimeType());
	}

	private FileItem _createFileItem(
			byte[] bytes, String fileName, String extension)
		throws Exception {

		Path tempFilePath = Files.createTempFile(null, extension);

		Files.write(tempFilePath, bytes);

		File tempFile = tempFilePath.toFile();

		FinalizeManager.register(
			tempFile, new DeleteFileFinalizeAction(tempFile.getAbsolutePath()),
			FinalizeManager.PHANTOM_REFERENCE_FACTORY);

		return ProxyUtil.newDelegateProxyInstance(
			FileItem.class.getClassLoader(), FileItem.class,
			new Object() {

				public void delete() {
					tempFile.delete();
				}

				public String getContentType() {
					return StringPool.BLANK;
				}

				public String getFileName() {
					return fileName;
				}

				public String getFullFileName() {
					return tempFile.getName();
				}

				public InputStream getInputStream() throws IOException {
					return new FileInputStream(tempFile);
				}

				public long getSize() {
					return bytes.length;
				}

				public int getSizeThreshold() {
					return 1024;
				}

				public File getStoreLocation() {
					return tempFile;
				}

				public boolean isFormField() {
					return true;
				}

				public boolean isInMemory() {
					return false;
				}

			},
			null);
	}

	private FileEntry _getFileEntry(String title) throws Exception {
		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, _permissionCheckerFactory.create(_user))) {

			return _dlAppService.getFileEntry(
				_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				title);
		}
	}

	private Folder _getFolder(String name) throws Exception {
		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, _permissionCheckerFactory.create(_user))) {

			return _dlAppService.getFolder(
				_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				name);
		}
	}

	private MockMultipartHttpServletRequest _getMockMultipartHttpServletRequest(
			long fileEntryId, String name, String method,
			boolean includeExtension, boolean viewableByGuest)
		throws Exception {

		MockMultipartHttpServletRequest mockMultipartHttpServletRequest =
			new MockMultipartHttpServletRequest();

		mockMultipartHttpServletRequest.setAttribute(
			WebKeys.CURRENT_URL, RandomTestUtil.randomString());
		mockMultipartHttpServletRequest.setAttribute(
			WebKeys.USER, TestPropsValues.getUser());
		mockMultipartHttpServletRequest.setContentType(
			"multipart/form-data;boundary=" + System.currentTimeMillis());
		mockMultipartHttpServletRequest.setMethod(method);
		mockMultipartHttpServletRequest.setParameter(
			"fileEntryId", String.valueOf(fileEntryId));
		mockMultipartHttpServletRequest.setParameter(
			"includeExtension", String.valueOf(includeExtension));
		mockMultipartHttpServletRequest.setParameter("name", name);
		mockMultipartHttpServletRequest.setParameter(
			"repositoryId", String.valueOf(_group.getGroupId()));
		mockMultipartHttpServletRequest.setParameter(
			"viewableByGuest", String.valueOf(viewableByGuest));

		return mockMultipartHttpServletRequest;
	}

	private HttpServletRequest _getMockMultipartHttpServletRequest(
			String fileName, String name, MockMultipartFile mockMultipartFile,
			String extension, boolean includeExtension, boolean viewableByGuest)
		throws Exception {

		MockMultipartHttpServletRequest mockMultipartHttpServletRequest =
			_getMockMultipartHttpServletRequest(
				0, name, "PUT", includeExtension, viewableByGuest);

		mockMultipartHttpServletRequest.addFile(mockMultipartFile);
		mockMultipartHttpServletRequest.setContent(_BYTES);
		mockMultipartHttpServletRequest.setContentType(
			"multipart/form-data;boundary=" + System.currentTimeMillis());

		return UploadTestUtil.createUploadPortletRequest(
			UploadTestUtil.createUploadServletRequest(
				mockMultipartHttpServletRequest,
				HashMapBuilder.put(
					"file",
					new FileItem[] {
						_createFileItem(_BYTES, fileName, extension)
					}
				).build(),
				new HashMap<>()),
			null, RandomTestUtil.randomString());
	}

	private static final byte[] _BYTES =
		"Enterprise. Open Source. For Life.".getBytes();

	@Inject
	private DLAppService _dlAppService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Inject(
		filter = "osgi.http.whiteboard.servlet.name=com.liferay.document.library.taglib.internal.servlet.RepositoryBrowserServlet"
	)
	private Servlet _servlet;

	@DeleteAfterTestRun
	private User _user;

}