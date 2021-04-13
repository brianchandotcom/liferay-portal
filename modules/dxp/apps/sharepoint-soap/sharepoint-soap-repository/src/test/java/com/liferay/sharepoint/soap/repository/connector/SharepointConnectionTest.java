/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.sharepoint.soap.repository.connector;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.sharepoint.soap.repository.connector.internal.util.test.SharepointConnectionTestUtil;
import com.liferay.sharepoint.soap.repository.connector.schema.query.Query;
import com.liferay.sharepoint.soap.repository.connector.schema.query.QueryField;
import com.liferay.sharepoint.soap.repository.connector.schema.query.QueryOptionsList;
import com.liferay.sharepoint.soap.repository.connector.schema.query.QueryValue;
import com.liferay.sharepoint.soap.repository.connector.schema.query.operator.ContainsOperator;
import com.liferay.sharepoint.soap.repository.connector.schema.query.option.FolderQueryOption;
import com.liferay.sharepoint.soap.repository.connector.schema.query.option.ViewAttributesQueryOption;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Iván Zaera
 */
public class SharepointConnectionTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_sharepointConnection = SharepointConnectionFactory.getInstance(
			_SERVER_VERSION, _SERVER_PROTOCOL,
			SharepointConnectionTestUtil.getSharepointVMHostname(),
			_SERVER_PORT, _SITE_PATH, _LIBRARY_NAME, _LIBRARY_PATH, _USER_NAME,
			_PASSWORD);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		SharepointConnectionInfo sharepointConnectionInfo =
			_sharepointConnection.getSharepointConnectionInfo();

		SharepointConnectionTestUtil.releaseSharepointVM(
			sharepointConnectionInfo.getServerAddress());
	}

	@Before
	public void setUp() throws Exception {
		_fileExtension1 = "txt";

		_fileName1 = StringBundler.concat(
			"File1 ", _TIMESTAMP, StringPool.PERIOD, _fileExtension1);

		_fileName2 = "File2 " + _TIMESTAMP + ".txt";

		_filePath1 = StringPool.SLASH + _fileName1;

		_folderName1 = "Folder1 " + _TIMESTAMP;
		_folderName2 = "Folder2 " + _TIMESTAMP;

		_folderPath1 = StringPool.SLASH + _folderName1;
		_folderPath2 = StringPool.SLASH + _folderName2;

		deleteSharepointObjects();
	}

	@After
	public void tearDown() throws Exception {
		deleteSharepointObjects();
	}

	@Test
	public void testAddFile() throws Exception {
		String folderPath = StringPool.SLASH;
		String fileName = "CreatedFile " + _TIMESTAMP + ".txt";

		_sharepointConnection.addFile(
			folderPath, fileName, StringPool.BLANK,
			getInputStream(_CONTENT_HELLO_WORLD));

		String filePath = folderPath + fileName;

		Assert.assertNotNull(
			_sharepointConnection.getSharepointObject(filePath));
	}

	@Test
	public void testAddFolder() throws Exception {
		String folderName = "CreatedFolder " + System.currentTimeMillis();

		_sharepointConnection.addFolder(StringPool.SLASH, folderName);

		Assert.assertNotNull(
			_sharepointConnection.getSharepointObject(
				StringPool.SLASH + folderName));
	}

	@Test
	public void testCheckOutFileThenUpdateFileThenCheckInFile()
		throws Exception {

		addSharepointObjects(true, false, false, false);

		_sharepointConnection.checkOutFile(_filePath1);

		SharepointObject sharepointObject =
			_sharepointConnection.getSharepointObject(_filePath1);

		Assert.assertNotNull(sharepointObject.getCheckedOutBy());

		_sharepointConnection.updateFile(
			_filePath1, getInputStream(_CONTENT_BYE_WORLD));

		_sharepointConnection.checkInFile(
			_filePath1, String.valueOf(new Date()),
			SharepointConnection.CheckInType.MAJOR);

		sharepointObject = _sharepointConnection.getSharepointObject(
			_filePath1);

		Assert.assertNull(sharepointObject.getCheckedOutBy());

		InputStream inputStream = _sharepointConnection.getInputStream(
			sharepointObject);

		String inputStreamString = getString(inputStream);

		Assert.assertEquals(_CONTENT_BYE_WORLD, inputStreamString);
	}

	@Test
	public void testCheckOutThenCancelCheckOut() throws Exception {
		addSharepointObjects(true, false, false, false);

		_sharepointConnection.checkOutFile(_filePath1);

		SharepointObject sharepointObject =
			_sharepointConnection.getSharepointObject(_filePath1);

		Assert.assertNotNull(sharepointObject.getCheckedOutBy());

		_sharepointConnection.cancelCheckOutFile(_filePath1);

		sharepointObject = _sharepointConnection.getSharepointObject(
			_filePath1);

		Assert.assertNull(sharepointObject.getCheckedOutBy());
	}

	@Test
	public void testCheckOutThenCheckIn() throws Exception {
		addSharepointObjects(true, false, false, false);

		_sharepointConnection.checkOutFile(_filePath1);

		SharepointObject sharepointObject =
			_sharepointConnection.getSharepointObject(_filePath1);

		Assert.assertNotNull(sharepointObject.getCheckedOutBy());

		_sharepointConnection.checkInFile(
			_filePath1, String.valueOf(new Date()),
			SharepointConnection.CheckInType.MAJOR);

		sharepointObject = _sharepointConnection.getSharepointObject(
			_filePath1);

		Assert.assertNull(sharepointObject.getCheckedOutBy());
	}

	@Test
	public void testCopyFile() throws Exception {
		addSharepointObjects(true, false, true, false);

		String copiedFilePath = StringBundler.concat(
			"/Folder1 ", _TIMESTAMP, "/CopiedFile ", _TIMESTAMP, ".txt");

		Assert.assertNull(
			_sharepointConnection.getSharepointObject(copiedFilePath));

		_sharepointConnection.copySharepointObject(_filePath1, copiedFilePath);

		Assert.assertNotNull(
			_sharepointConnection.getSharepointObject(copiedFilePath));

		Assert.assertNotNull(
			_sharepointConnection.getSharepointObject(_filePath1));
	}

	@Test
	public void testCopyFolder() throws Exception {
		addSharepointObjects(false, false, true, true);

		String copiedFolderPath = StringBundler.concat(
			"/Folder2 ", _TIMESTAMP, "/CopiedFolder ", _TIMESTAMP);

		Assert.assertNull(
			_sharepointConnection.getSharepointObject(copiedFolderPath));

		_sharepointConnection.copySharepointObject(
			_folderPath1, copiedFolderPath);

		Assert.assertNotNull(
			_sharepointConnection.getSharepointObject(
				StringBundler.concat(
					_folderPath1, "/Subfile1 ", _TIMESTAMP, ".txt")));
		Assert.assertNotNull(
			_sharepointConnection.getSharepointObject(
				StringBundler.concat(
					_folderPath1, "/Subfile2 ", _TIMESTAMP, ".txt")));
		Assert.assertNotNull(
			_sharepointConnection.getSharepointObject(
				_folderPath1 + "/Subfolder1 " + _TIMESTAMP));
		Assert.assertNotNull(
			_sharepointConnection.getSharepointObject(
				_folderPath1 + "/Subfolder2 " + _TIMESTAMP));
		Assert.assertNotNull(
			_sharepointConnection.getSharepointObject(
				StringBundler.concat(
					copiedFolderPath, "/Subfile1 ", _TIMESTAMP, ".txt")));
		Assert.assertNotNull(
			_sharepointConnection.getSharepointObject(
				StringBundler.concat(
					copiedFolderPath, "/Subfile2 ", _TIMESTAMP, ".txt")));
		Assert.assertNotNull(
			_sharepointConnection.getSharepointObject(
				copiedFolderPath + "/Subfolder1 " + _TIMESTAMP));
		Assert.assertNotNull(
			_sharepointConnection.getSharepointObject(
				copiedFolderPath + "/Subfolder2 " + _TIMESTAMP));
	}

	@Test
	public void testDeleteFile() throws Exception {
		addSharepointObjects(true, false, false, false);

		_sharepointConnection.deleteSharepointObject(_filePath1);

		Assert.assertNull(
			_sharepointConnection.getSharepointObject(_filePath1));
	}

	@Test
	public void testDeleteFolder() throws Exception {
		addSharepointObjects(false, false, true, false);

		_sharepointConnection.deleteSharepointObject(_folderPath1);

		Assert.assertNull(
			_sharepointConnection.getSharepointObject(_folderPath1));
	}

	@Test
	public void testGetFileByPath() throws Exception {
		addSharepointObjects(true, false, false, false);

		SharepointObject sharepointObject =
			_sharepointConnection.getSharepointObject(_filePath1);

		Assert.assertNotNull(sharepointObject);
		Assert.assertEquals(_fileExtension1, sharepointObject.getExtension());
		Assert.assertEquals(StringPool.SLASH, sharepointObject.getFolderPath());
		Assert.assertEquals(_fileName1, sharepointObject.getName());
		Assert.assertEquals(_filePath1, sharepointObject.getPath());
		Assert.assertTrue(sharepointObject.isFile());
	}

	@Test
	public void testGetFileByPathAndSharepointObjectId() throws Exception {
		addSharepointObjects(true, false, false, false);

		SharepointObject sharepointObject1 =
			_sharepointConnection.getSharepointObject(_filePath1);

		SharepointObject sharepointObject2 =
			_sharepointConnection.getSharepointObject(
				sharepointObject1.getSharepointObjectId());

		Assert.assertEquals(
			sharepointObject1.getSharepointObjectId(),
			sharepointObject2.getSharepointObjectId());
	}

	@Test
	public void testGetFolderByPath() throws Exception {
		addSharepointObjects(false, false, true, false);

		SharepointObject sharepointObject =
			_sharepointConnection.getSharepointObject(_folderPath1);

		Assert.assertNotNull(sharepointObject);
		Assert.assertEquals(StringPool.BLANK, sharepointObject.getExtension());
		Assert.assertEquals(StringPool.SLASH, sharepointObject.getFolderPath());
		Assert.assertEquals(_folderName1, sharepointObject.getName());
		Assert.assertEquals(_folderPath1, sharepointObject.getPath());
		Assert.assertTrue(sharepointObject.isFolder());
	}

	@Test
	public void testGetRootFolderByPath() throws Exception {
		SharepointObject rootFolderSharepointObject =
			_sharepointConnection.getSharepointObject(StringPool.SLASH);

		assertRootFolderSharepointObject(rootFolderSharepointObject);
	}

	@Test
	public void testGetRootFolderBySharepointObjectId() throws Exception {
		SharepointObject rootFolderSharepointObject =
			_sharepointConnection.getSharepointObject(
				SharepointConnectionImpl.
					SHAREPOINT_ROOT_FOLDER_SHAREPOINT_OBJECT_ID);

		assertRootFolderSharepointObject(rootFolderSharepointObject);
	}

	@Test
	public void testGetSharepointObjectInputStream() throws Exception {
		addSharepointObjects(true, false, false, false);

		InputStream inputStream = _sharepointConnection.getInputStream(
			_sharepointConnection.getSharepointObject(_filePath1));

		Assert.assertEquals(_CONTENT_HELLO_WORLD, getString(inputStream));
	}

	@Test
	public void testGetSharepointObjectsByFileName() throws Exception {
		addSharepointObjects(true, true, true, true);

		List<SharepointObject> sharepointObjects =
			_sharepointConnection.getSharepointObjects(_fileName1);

		Assert.assertEquals(
			sharepointObjects.toString(), 2, sharepointObjects.size());

		assertContainsName(sharepointObjects, _fileName1);
	}

	@Test
	public void testGetSharepointObjectsByFolderPath() throws Exception {
		addSharepointObjects(true, true, true, true);

		List<SharepointObject> sharepointObjects =
			_sharepointConnection.getSharepointObjects(
				_folderPath1, SharepointConnection.ObjectTypeFilter.ALL);

		Assert.assertEquals(
			sharepointObjects.toString(), 4, sharepointObjects.size());

		sharepointObjects = _sharepointConnection.getSharepointObjects(
			_folderPath1, SharepointConnection.ObjectTypeFilter.FILES);

		Assert.assertEquals(
			sharepointObjects.toString(), 2, sharepointObjects.size());

		assertIsFile(sharepointObjects);

		sharepointObjects = _sharepointConnection.getSharepointObjects(
			_folderPath1, SharepointConnection.ObjectTypeFilter.FOLDERS);

		Assert.assertEquals(
			sharepointObjects.toString(), 2, sharepointObjects.size());

		assertIsFolder(sharepointObjects);
	}

	@Test
	public void testGetSharepointObjectsByQuery() throws Exception {
		addSharepointObjects(true, true, true, true);

		Query query = new Query(
			new ContainsOperator(
				new QueryField("BaseName"), new QueryValue("SubFile")));

		List<SharepointObject> sharepointObjects =
			_sharepointConnection.getSharepointObjects(
				query, _getQueryOptionsList());

		Assert.assertEquals(
			sharepointObjects.toString(), 2, sharepointObjects.size());

		assertContainsName(sharepointObjects, "SubFile");
	}

	@Test
	public void testGetSharepointObjectsCount() throws Exception {
		addSharepointObjects(true, true, true, true);

		Assert.assertEquals(
			4,
			_sharepointConnection.getSharepointObjectsCount(
				StringPool.SLASH, SharepointConnection.ObjectTypeFilter.ALL));

		String folderPath = "/Folder1 " + _TIMESTAMP;

		Assert.assertEquals(
			4,
			_sharepointConnection.getSharepointObjectsCount(
				folderPath, SharepointConnection.ObjectTypeFilter.ALL));
		Assert.assertEquals(
			2,
			_sharepointConnection.getSharepointObjectsCount(
				folderPath, SharepointConnection.ObjectTypeFilter.FILES));
		Assert.assertEquals(
			2,
			_sharepointConnection.getSharepointObjectsCount(
				folderPath, SharepointConnection.ObjectTypeFilter.FOLDERS));
	}

	@Test
	public void testGetSharepointVersionInputStream() throws Exception {
		addSharepointObjects(true, false, false, false);

		_sharepointConnection.checkOutFile(_filePath1);

		SharepointObject sharepointObject =
			_sharepointConnection.getSharepointObject(_filePath1);

		Assert.assertNotNull(sharepointObject.getCheckedOutBy());

		_sharepointConnection.updateFile(
			_filePath1, getInputStream(_CONTENT_BYE_WORLD));

		_sharepointConnection.checkInFile(
			_filePath1, String.valueOf(new Date()),
			SharepointConnection.CheckInType.MAJOR);

		sharepointObject = _sharepointConnection.getSharepointObject(
			_filePath1);

		Assert.assertNull(sharepointObject.getCheckedOutBy());

		List<SharepointVersion> sharepointVersions =
			_sharepointConnection.getSharepointVersions(_filePath1);

		InputStream inputStream = _sharepointConnection.getInputStream(
			sharepointVersions.get(0));

		Assert.assertEquals(_CONTENT_BYE_WORLD, getString(inputStream));

		inputStream = _sharepointConnection.getInputStream(
			sharepointVersions.get(1));

		Assert.assertEquals(_CONTENT_HELLO_WORLD, getString(inputStream));
	}

	@Test
	public void testGetSharepointVersions() throws Exception {
		addSharepointObjects(true, false, false, false);

		addFileVersion(
			_filePath1, _CONTENT_BYE_WORLD,
			SharepointConnection.CheckInType.MAJOR);

		addFileVersion(
			_filePath1, _CONTENT_HELLO_WORLD,
			SharepointConnection.CheckInType.MAJOR);

		addFileVersion(
			_filePath1, _CONTENT_BYE_WORLD,
			SharepointConnection.CheckInType.MAJOR);

		addFileVersion(
			_filePath1, _CONTENT_HELLO_WORLD,
			SharepointConnection.CheckInType.MAJOR);

		addFileVersion(
			_filePath1, _CONTENT_BYE_WORLD,
			SharepointConnection.CheckInType.MAJOR);

		addFileVersion(
			_filePath1, _CONTENT_HELLO_WORLD,
			SharepointConnection.CheckInType.MAJOR);

		addFileVersion(
			_filePath1, _CONTENT_BYE_WORLD,
			SharepointConnection.CheckInType.MAJOR);

		addFileVersion(
			_filePath1, _CONTENT_HELLO_WORLD,
			SharepointConnection.CheckInType.MINOR);

		List<SharepointVersion> sharepointVersions =
			_sharepointConnection.getSharepointVersions(_filePath1);

		Assert.assertEquals(
			sharepointVersions.toString(), 9, sharepointVersions.size());

		SharepointVersion sharepointVersion = sharepointVersions.get(8);

		Assert.assertEquals("1.0", sharepointVersion.getVersion());

		sharepointVersion = sharepointVersions.get(1);

		Assert.assertEquals("8.0", sharepointVersion.getVersion());

		sharepointVersion = sharepointVersions.get(0);

		Assert.assertEquals("8.1", sharepointVersion.getVersion());
	}

	@Test
	public void testMoveFile() throws Exception {
		addSharepointObjects(true, false, true, false);

		String movedFilePath = StringBundler.concat(
			_folderPath1, "/MovedFile ", _TIMESTAMP, ".txt");

		Assert.assertNull(
			_sharepointConnection.getSharepointObject(movedFilePath));

		_sharepointConnection.moveSharepointObject(_filePath1, movedFilePath);

		Assert.assertNotNull(
			_sharepointConnection.getSharepointObject(movedFilePath));

		Assert.assertNull(
			_sharepointConnection.getSharepointObject(_filePath1));
	}

	@Test
	public void testMoveFolder() throws Exception {
		addSharepointObjects(false, false, true, true);

		String movedFolderPath = _folderPath2 + "/MovedFolder " + _TIMESTAMP;

		_sharepointConnection.moveSharepointObject(
			_folderPath1, movedFolderPath);

		Assert.assertNotNull(
			_sharepointConnection.getSharepointObject(
				StringBundler.concat(
					movedFolderPath, "/Subfile1 ", _TIMESTAMP, ".txt")));
		Assert.assertNotNull(
			_sharepointConnection.getSharepointObject(
				StringBundler.concat(
					movedFolderPath, "/Subfile2 ", _TIMESTAMP, ".txt")));
		Assert.assertNotNull(
			_sharepointConnection.getSharepointObject(
				movedFolderPath + "/Subfolder1 " + _TIMESTAMP));
		Assert.assertNotNull(
			_sharepointConnection.getSharepointObject(
				movedFolderPath + "/Subfolder2 " + _TIMESTAMP));
	}

	@Test
	public void testRenameFile() throws Exception {
		addSharepointObjects(true, false, false, false);

		SharepointObject sharepointObject =
			_sharepointConnection.getSharepointObject(_filePath1);

		long sharepointObjectId = sharepointObject.getSharepointObjectId();

		addFileVersion(
			_filePath1, _CONTENT_HELLO_WORLD,
			SharepointConnection.CheckInType.MAJOR);
		addFileVersion(
			_filePath1, _CONTENT_HELLO_WORLD,
			SharepointConnection.CheckInType.MAJOR);
		addFileVersion(
			_filePath1, _CONTENT_HELLO_WORLD,
			SharepointConnection.CheckInType.MAJOR);

		String renamedFilePath = "/RenamedFile " + _TIMESTAMP + ".txt";

		Assert.assertNull(
			_sharepointConnection.getSharepointObject(renamedFilePath));

		_sharepointConnection.checkOutFile(_filePath1);

		_sharepointConnection.moveSharepointObject(_filePath1, renamedFilePath);

		_sharepointConnection.checkInFile(
			renamedFilePath, StringPool.BLANK,
			SharepointConnection.CheckInType.MAJOR);

		Assert.assertNull(
			_sharepointConnection.getSharepointObject(_filePath1));

		SharepointObject renamedFileSharepointObject =
			_sharepointConnection.getSharepointObject(renamedFilePath);

		Assert.assertNotNull(renamedFileSharepointObject);
		Assert.assertEquals(
			sharepointObjectId,
			renamedFileSharepointObject.getSharepointObjectId());
		Assert.assertEquals(
			renamedFilePath, renamedFileSharepointObject.getPath());

		List<SharepointVersion> renamedFileSharepointVersions =
			_sharepointConnection.getSharepointVersions(renamedFilePath);

		Assert.assertEquals(
			renamedFileSharepointVersions.toString(), 5,
			renamedFileSharepointVersions.size());
	}

	@Test
	public void testRenameFolder() throws Exception {
		addSharepointObjects(false, false, true, false);

		String renamedFolderPath = "/RenamedFolder " + _TIMESTAMP;

		Assert.assertNull(
			_sharepointConnection.getSharepointObject(renamedFolderPath));

		_sharepointConnection.moveSharepointObject(
			_folderPath1, renamedFolderPath);

		Assert.assertNull(
			_sharepointConnection.getSharepointObject(_folderPath1));
		Assert.assertNotNull(
			_sharepointConnection.getSharepointObject(renamedFolderPath));
	}

	protected void addFileVersion(
			String filePath, String content,
			SharepointConnection.CheckInType checkInType)
		throws SharepointException {

		_sharepointConnection.checkOutFile(filePath);

		_sharepointConnection.updateFile(filePath, getInputStream(content));

		_sharepointConnection.checkInFile(
			filePath, String.valueOf(new Date()), checkInType);
	}

	protected void addSharepointObjects(
			boolean file1, boolean file2, boolean folder1, boolean folder2)
		throws SharepointException {

		if (file1) {
			_sharepointConnection.addFile(
				StringPool.SLASH, _fileName1, StringPool.BLANK,
				getInputStream(_CONTENT_HELLO_WORLD));
		}

		if (file2) {
			_sharepointConnection.addFile(
				StringPool.SLASH, _fileName2, StringPool.BLANK,
				getInputStream(_CONTENT_HELLO_WORLD));
		}

		if (folder1) {
			_sharepointConnection.addFolder(StringPool.SLASH, _folderName1);

			_sharepointConnection.addFile(
				StringPool.SLASH + _folderName1, "Sub" + _fileName1,
				StringPool.BLANK, getInputStream(_CONTENT_HELLO_WORLD));

			_sharepointConnection.addFile(
				StringPool.SLASH + _folderName1, "Sub" + _fileName2,
				StringPool.BLANK, getInputStream(_CONTENT_HELLO_WORLD));

			_sharepointConnection.addFolder(
				StringPool.SLASH + _folderName1, "Sub" + _folderName1);
		}

		if (folder2) {
			_sharepointConnection.addFolder(
				StringPool.SLASH + _folderName1, "Sub" + _folderName2);

			_sharepointConnection.addFolder(StringPool.SLASH, _folderName2);
		}
	}

	protected void assertContainsName(
		List<SharepointObject> sharepointObjects, String name) {

		for (SharepointObject sharepointObject : sharepointObjects) {
			String sharepointObjectName = sharepointObject.getName();

			Assert.assertTrue(sharepointObjectName.contains(name));
		}
	}

	protected void assertIsFile(List<SharepointObject> sharepointObjects) {
		for (SharepointObject sharepointObject : sharepointObjects) {
			Assert.assertTrue(sharepointObject.isFile());
		}
	}

	protected void assertIsFolder(List<SharepointObject> sharepointObjects) {
		for (SharepointObject sharepointObject : sharepointObjects) {
			Assert.assertTrue(sharepointObject.isFolder());
		}
	}

	protected void assertRootFolderSharepointObject(
		SharepointObject rootFolderSharepointObject) {

		Assert.assertNotNull(rootFolderSharepointObject);
		Assert.assertEquals(
			StringPool.BLANK, rootFolderSharepointObject.getExtension());
		Assert.assertEquals(
			StringPool.SLASH, rootFolderSharepointObject.getFolderPath());
		Assert.assertEquals(
			StringPool.SLASH, rootFolderSharepointObject.getName());
		Assert.assertEquals(
			StringPool.SLASH, rootFolderSharepointObject.getPath());
		Assert.assertEquals(
			SharepointConnectionImpl.
				SHAREPOINT_ROOT_FOLDER_SHAREPOINT_OBJECT_ID,
			rootFolderSharepointObject.getSharepointObjectId());
		Assert.assertTrue(rootFolderSharepointObject.isFolder());
	}

	protected void deleteSharepointObjects() throws SharepointException {
		List<SharepointObject> sharepointObjects =
			_sharepointConnection.getSharepointObjects(
				StringPool.SLASH, SharepointConnection.ObjectTypeFilter.ALL);

		for (SharepointObject sharepointObject : sharepointObjects) {
			_sharepointConnection.deleteSharepointObject(
				sharepointObject.getPath());
		}
	}

	protected InputStream getInputStream(String content) {
		return new ByteArrayInputStream(
			content.getBytes(StandardCharsets.UTF_8));
	}

	protected String getString(InputStream inputStream) throws IOException {
		return new String(
			FileUtil.getBytes(inputStream), StandardCharsets.UTF_8);
	}

	private QueryOptionsList _getQueryOptionsList() {
		if (_SERVER_VERSION.equals(
				SharepointConnection.ServerVersion.SHAREPOINT_2013)) {

			return new QueryOptionsList(
				new FolderQueryOption(StringPool.BLANK),
				new ViewAttributesQueryOption(true));
		}

		return new QueryOptionsList(new FolderQueryOption(StringPool.BLANK));
	}

	private static final String _CONTENT_BYE_WORLD = "Bye world!";

	private static final String _CONTENT_HELLO_WORLD = "Hello world!";

	private static final String _LIBRARY_NAME = "Documents";

	private static final String _LIBRARY_PATH = "Documents";

	private static final String _PASSWORD = "password";

	private static final int _SERVER_PORT = 80;

	private static final String _SERVER_PROTOCOL = "http";

	private static final SharepointConnection.ServerVersion _SERVER_VERSION =
		SharepointConnection.ServerVersion.SHAREPOINT_2010;

	private static final String _SITE_PATH = StringPool.BLANK;

	private static final long _TIMESTAMP = System.currentTimeMillis();

	private static final String _USER_NAME = "Administrator";

	private static SharepointConnection _sharepointConnection;

	private String _fileExtension1;
	private String _fileName1;
	private String _fileName2;
	private String _filePath1;
	private String _folderName1;
	private String _folderName2;
	private String _folderPath1;
	private String _folderPath2;

}