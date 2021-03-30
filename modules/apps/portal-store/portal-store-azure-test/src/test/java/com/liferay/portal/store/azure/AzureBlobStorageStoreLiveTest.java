package com.liferay.portal.store.azure;

import com.azure.core.http.rest.PagedIterable;
import com.azure.core.http.rest.Response;
import com.azure.core.util.Context;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobContainerProperties;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.models.BlobRequestConditions;
import com.azure.storage.blob.models.ListBlobsOptions;
import com.azure.storage.common.Utility;
import com.liferay.document.library.kernel.exception.NoSuchFileException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.store.azure.test.Fixtures;
import com.liferay.portal.store.azure.test.TestFiles;
import org.apache.commons.io.IOUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This is LIVE test - tries to call Azure using the Java SDK, using supplied
 * unit test credentials (see gradle.properties).
 *
 * @author Josef Sustacek
 */
public class AzureBlobStorageStoreLiveTest {

	@BeforeClass
	public static void setUp() throws IllegalAccessException {

		Fixtures.Fixture<AzureBlobStorageStore> fixture = Fixtures.getAzureBlobStorageStore();

		// init the admin client
		String testsContainerName = fixture.getSutProps().get("containerName").toString();

		_adminContainerClient = new BlobServiceClientBuilder()
			.connectionString(storageAdminConnectionString)
			.buildClient()
			.getBlobContainerClient(testsContainerName);

		// the container must exist before the store can be activated
		Boolean containerExistsValue =
			_adminContainerClient.existsWithResponse(Duration.ofSeconds(5), Context.NONE).getValue();

		if (containerExistsValue != null && containerExistsValue.booleanValue()) {
			_adminContainerClient.deleteWithResponse(null, Duration.ofSeconds(5), Context.NONE);
		}

		_adminContainerClient.createWithResponse(
			Collections.emptyMap(), null, Duration.ofSeconds(10), Context.NONE);

		BlobContainerProperties properties =
			_adminContainerClient.getPropertiesWithResponse(
				null, Duration.ofSeconds(5), Context.NONE).getValue();

		System.out.printf(
			"Created container '%s' in Azure to be used for tests, it has the following properties: %n" +
				"  * Last Modified: %s%n  * Public Access Type: %s%n  * Legal Hold? %b%n  * Immutable? %b%n",
			testsContainerName,
			properties.getLastModified(),
			properties.getBlobPublicAccess(),
			properties.hasLegalHold(),
			properties.hasImmutabilityPolicy());

		// get the store object + simulate OSGi lifecycle
		_sut = fixture.getSut();
		_sut.activate(fixture.getSutProps());
	}

	@AfterClass
	public static void tearDown() {
		_sut.deactivate();

		if (cleanupAfterTests) {
			System.out.println(
				"Deleting container '" + _adminContainerClient.getBlobContainerName() + "' used for unit tests.");

			// clean the whole container and its blobs (will be GC-ed by Azure later, not immediately)
			_adminContainerClient.deleteWithResponse(
				new BlobRequestConditions(), Duration.ofSeconds(10), null);
		}
	}

	@Test(timeout = _DEFAULT_TIMEOUT_MS)
	public void _10xxx_addFile() throws IOException, PortalException {
		// when
		TestFiles.TestFile testFile = TestFiles.SMALL_PDF_FILE;

		try (InputStream is = testFile.openStream()) {
			_sut.addFile(10000, 12345, "file-name.pdf", "1.0", is);
		}
		try (InputStream is = testFile.openStream()) {
			// space in filename
			_sut.addFile(10000, 12345, "file name 2.pdf", "2.0", is);
		}
		try (InputStream is = testFile.openStream()) {
			// file in a subdirectory
			_sut.addFile(10100, 123456, "subdir/file-name-3.pdf", "3.0", is);
		}

		// then
		List<String> blobsExpectedToExist = Arrays.asList(
			"10000/12345/file-name.pdf/1.0",
			"10000/12345/file name 2.pdf/2.0",
			"10100/123456/subdir/file-name-3.pdf/3.0"
		);

		for (String blobExpectedToExist: blobsExpectedToExist) {
			BlobClient bc = _adminContainerClient.getBlobClient(Utility.urlEncode(blobExpectedToExist));

			assertTrue(
				String.format(
					"'%s' should have been created in the container '%s'.",
					bc.getBlobName(),  bc.getContainerName()
				),
				bc.exists());
			assertEquals(
				String.format(
					"The size (in bytes) of the blob '%s' should be matching the uploaded test file.",
					blobExpectedToExist, testFile.getSizeBytes()),
				testFile.getSizeBytes(), bc.getProperties().getBlobSize());
		}
	}

	@Test(timeout = _DEFAULT_TIMEOUT_MS)
	public void _20xxx_deleteDirectory() {
		// assuming
		TestFiles.TestFile testFile = TestFiles.SMALL_PDF_FILE;

		BlobClient blobClient1 = _adminContainerClient.getBlobClient("20221/10000/dir-name-AAA/file-name.pdf/1.0");
		BlobClient blobClient2 = _adminContainerClient.getBlobClient("20221/10000/dir-name-AAA/file-name-2.pdf/1.0");
		BlobClient blobClient3 = _adminContainerClient.getBlobClient("20221/10000/dir-name-AAA/file-name-2.pdf/1.2");
		BlobClient blobClient4 = _adminContainerClient.getBlobClient("20221/10000/dir-name-AAA/subdir/file-name-3.pdf/1.0");
		BlobClient blobClient5 = _adminContainerClient.getBlobClient("20221/10000/dir-name-BBB/file-name-4.pdf/1.0");
		BlobClient blobClient6 = _adminContainerClient.getBlobClient("20221/10000/dir-name-BBB/subdir/file-name-5.pdf/1.0");
		BlobClient blobClient7 = _adminContainerClient.getBlobClient("20221/10001/dir-name-CCC/file-name-6.pdf/1.0");
		BlobClient blobClient8 = _adminContainerClient.getBlobClient("20222/10010/dir-name-DDD/file-name-7.pdf/1.0");

		for (BlobClient bc:
				Arrays.asList(
					blobClient1, blobClient2, blobClient3, blobClient4, blobClient5,
					blobClient6, blobClient7, blobClient8)) {

			bc.upload(testFile.openStream(), testFile.getSizeBytes());
		}

		assertArrayNotEmpty(_blobsWithPrefix("20221/10000/dir-name-AAA/"));
		assertArrayNotEmpty(_blobsWithPrefix("20221/10000/dir-name-BBB/"));
		assertArrayNotEmpty(_blobsWithPrefix("20221/10001/dir-name-CCC/"));
		assertArrayNotEmpty(_blobsWithPrefix("20222/10010/dir-name-DDD/"));

		// when
		_sut.deleteDirectory(20221, 10000, "dir-name-AAA");

		// then
		assertArrayEquals(_EMPTY, _blobsWithPrefix("20221/10000/dir-name-AAA/"));
		assertArrayNotEmpty(_blobsWithPrefix("20221/10000/dir-name-BBB/"));
		assertArrayNotEmpty(_blobsWithPrefix("20221/10001/dir-name-CCC/"));
		assertArrayNotEmpty(_blobsWithPrefix("20222/10010/dir-name-DDD/"));

		// when
		// '/' at the end of the path
		_sut.deleteDirectory(20221, 10000, "dir-name-BBB/subdir/");

		// then
		assertArrayEquals(_EMPTY, _blobsWithPrefix("20221/10000/dir-name-BBB/subdir/"));
		assertArrayNotEmpty(_blobsWithPrefix("20221/10000/dir-name-BBB/"));
		assertArrayNotEmpty(_blobsWithPrefix("20221/10001/"));
		assertArrayNotEmpty(_blobsWithPrefix("20222/10010/dir-name-DDD/"));

		// when
		// empty dirName (cannot be null)
		_sut.deleteDirectory(20221, 10000, "");

		// then
		assertArrayEquals(_EMPTY, _blobsWithPrefix("20221/10000/"));
		assertArrayNotEmpty(_blobsWithPrefix("20222/10010/dir-name-DDD/"));
	}

	@Test(timeout = _DEFAULT_TIMEOUT_MS, expected = NullPointerException.class)
	public void _20xxx_deleteDirectory_invalid() {
		_sut.deleteDirectory(20000, 10000, null);
	}

	@Test(timeout = _DEFAULT_TIMEOUT_MS)
	public void _30xxx_deleteFile() {
		// assuming
		_upload("30100/1000/subdir/file.pdf/1.0", TestFiles.SMALL_PDF_FILE);
		_upload("30100/1000/subdir/file.pdf/2.0", TestFiles.SMALL_PDF_FILE);

		// when
		_sut.deleteFile(30100, 1000, "subdir/file.pdf", "2.0");

		// then
		assertTrue(_exists("30100/1000/subdir/file.pdf/1.0"));
		assertFalse(_exists("30100/1000/subdir/file.pdf/2.0"));
	}

	@Test(timeout = _DEFAULT_TIMEOUT_MS)
	public void _30xxx_deleteFile_doesNotExist() {
		// when
		_sut.deleteFile(30200, 1000, "file.pdf", "2.0");

		// then
		// OK, no exception raised
	}

	@Test(timeout = _DEFAULT_TIMEOUT_MS)
	public void _40xxx_getFileAsStream() throws IOException, PortalException {
		// assuming
		TestFiles.TestFile testFile = TestFiles.SMALL_PDF_FILE;

		_upload("40000/10000/subdir/file 2.pdf/2.0", testFile);
		_upload("40000/10000/subdir/file 2.pdf/2.2", testFile);

		byte[] uploadedBytes = IOUtils.toByteArray(testFile.openStream());

		// when
		try(InputStream bis = _sut.getFileAsStream(
			40000, 10000, "subdir/file 2.pdf", "2.0")) {

			// then
			byte[] bytes = IOUtils.toByteArray(bis);

			Assert.assertArrayEquals("File content should be as uploaded.", uploadedBytes, bytes);
		}

		// when -- no versions specified
		try(InputStream bis = _sut.getFileAsStream(
			40000, 10000, "subdir/file 2.pdf", "")) {

			// then
			byte[] bytes = IOUtils.toByteArray(bis);

			Assert.assertArrayEquals("File content should be as uploaded.", uploadedBytes, bytes);
		}
	}

	@Test(timeout = _DEFAULT_TIMEOUT_MS, expected = NoSuchFileException.class)
	public void _40xxx_getFileAsStream_missingFile() throws IOException, PortalException {
		// assuming
		TestFiles.TestFile testFile = TestFiles.SMALL_PDF_FILE;

		_upload("40000/10001/subdir/file 2.pdf/2.0", testFile);

		byte[] uploadedBytes = IOUtils.toByteArray(testFile.openStream());

		// when
		try(InputStream bis = _sut.getFileAsStream(
			40000, 10001, "missing-file.pdf", "1.0")) {

			assertTrue(false);
		}
	}

	@Test(timeout = _DEFAULT_TIMEOUT_MS)
	public void _50xxx_getFileNames() {
		// assuming
		TestFiles.TestFile testFile = TestFiles.SMALL_PDF_FILE;
		_upload("50000/10000/file-1.pdf/1.0", testFile);
		_upload("50000/10000/file-1.pdf/2.0", testFile);
		_upload("50000/10000/file-2.pdf/1.1", testFile);
		_upload("50000/10000/subdir/file-3.pdf/3.0", testFile);
		_upload("50000/10000/subdir/file-3.pdf/3.1", testFile);
		_upload("50000/10000/subdir/file-4.pdf/3.2", testFile);
		_upload("50000/10000/subdir/subdir2/file-5.pdf/2.0", testFile);
		_upload("50000/10000/aaa-file-6.pdf/2.0", testFile);

		// when
		String[] fileNames = _sut.getFileNames(50000, 10000, "");

		// then
		String[] expectedFileNames = new String[]{
			"aaa-file-6.pdf",
			"file-1.pdf",
			"file-2.pdf",
			"subdir/file-3.pdf",
			"subdir/file-4.pdf",
			"subdir/subdir2/file-5.pdf",
			};

		// when
		fileNames = _sut.getFileNames(50000, 10000, "subdir");

		// then
		expectedFileNames = new String[]{
			"subdir/file-3.pdf",
			"subdir/file-4.pdf",
			"subdir/subdir2/file-5.pdf",
			};

		assertArrayEquals(expectedFileNames, fileNames);

		// when
		fileNames = _sut.getFileNames(50000, 10000, "subdir/subdir2");

		// then
		expectedFileNames = new String[]{
			"subdir/subdir2/file-5.pdf",
			};

		assertArrayEquals(expectedFileNames, fileNames);

		// when
		fileNames = _sut.getFileNames(50000, 10000, "subdir/subdir2/bad-one");

		// then
		expectedFileNames = new String[]{};

		assertArrayEquals(expectedFileNames, fileNames);
	}

	@Test(timeout = _DEFAULT_TIMEOUT_MS)
	public void _60xxx_getFileSize() throws PortalException {
		// assuming
		TestFiles.TestFile testFile = TestFiles.SMALL_PDF_FILE;
		_upload("60000/10000/subdir/file.pdf/1.0", testFile);
		_upload("60000/10000/subdir/file.pdf/1.1", testFile);

		// when
		long fileSize =
			_sut.getFileSize(60000, 10000, "subdir/file.pdf", "1.0");

		// then
		assertEquals("Size of file does not match the uploaded one.", testFile.getSizeBytes(), fileSize);

		// when - no version specified
		fileSize =
			_sut.getFileSize(60000, 10000, "subdir/file.pdf", "");

		// then
		assertEquals("Size of file does not match the uploaded one.", testFile.getSizeBytes(), fileSize);
	}

	@Test(timeout = _DEFAULT_TIMEOUT_MS, expected = NoSuchFileException.class)
	public void _60xxx_getFileSize_missingFile() throws PortalException {
		// assuming
		TestFiles.TestFile testFile = TestFiles.SMALL_PDF_FILE;
		_upload("60000/10001/subdir/file.pdf/1.0", testFile);

		// when
		long fileSize =
			_sut.getFileSize(60000, 10001, "missing-file.pdf", "1.0");

		// then
		assertTrue(false);
	}

	@Test(timeout = _DEFAULT_TIMEOUT_MS)
	public void _70xxx_getFileVersions() {
		// assuming
		TestFiles.TestFile testFile = TestFiles.SMALL_PDF_FILE;
		_upload("70000/10000/file-1.pdf/1.0", testFile);	// multiple versions
		_upload("70000/10000/file-1.pdf/1.1", testFile);
		_upload("70000/10000/file-1.pdf/1.2", testFile);
		_upload("70000/10000/file-1.pdf/1.3/messed-up-file", testFile); // make sure a subdir is ignored, if created by mistake

		_upload("70000/10000/subdir/file-2.pdf/2.0", testFile); // single version

		_upload("70000/10000/file-3.pdf", testFile);	// the path is a blob => return no versions

		_upload("70000/10000/file-4.pdf/subdir/aaa", testFile); // no blob under the path => return no versions

		// when
		String[] fileVersions =
			_sut.getFileVersions(70000, 10000, "file-1.pdf");

		// then
		String[] expected = new String[]{ "1.0", "1.1", "1.2" };
		assertArrayEquals(expected, fileVersions);

		// when
		fileVersions =
			_sut.getFileVersions(70000, 10000, "subdir/file-2.pdf");

		// then
		expected = new String[]{ "2.0" };
		assertArrayEquals(expected, fileVersions);

		// when
		fileVersions =
			_sut.getFileVersions(70000, 10000, "file-3.pdf");

		// then
		expected = new String[]{};
		assertArrayEquals(expected, fileVersions);

		// when
		fileVersions =
			_sut.getFileVersions(70000, 10000, "file-4.pdf");

		// then
		expected = new String[]{};
		assertArrayEquals(expected, fileVersions);

		// when
		fileVersions =
			_sut.getFileVersions(70000, 10000, "missing-file.pdf");

		// then
		expected = new String[]{};
		assertArrayEquals(expected, fileVersions);
	}

	@Test(timeout = _DEFAULT_TIMEOUT_MS)
	public void _80xxx_hasFile() {
		// assuming
		_upload("80000/10000/file-1.pdf/1.0", TestFiles.SMALL_PDF_FILE);
		_upload("80000/10000/subdir/file 2.pdf/1.4", TestFiles.SMALL_PDF_FILE);
		_upload("80000/10000/subdir/file 2.pdf/1.5", TestFiles.SMALL_PDF_FILE);

		// when
		boolean hasFile = _sut.hasFile(80000, 10000, "file-1.pdf", "1.0");

		// then
		assertTrue(hasFile);

		// when
		hasFile = _sut.hasFile(80000, 10000, "subdir/file 2.pdf", "1.4");

		// then
		assertTrue(hasFile);

		// when
		hasFile = _sut.hasFile(80000, 10000, "subdir/file 2.pdf", "1.5");

		// then
		assertTrue(hasFile);

		// when - no version
		hasFile = _sut.hasFile(80000, 10000, "subdir/file 2.pdf", "");

		// then
		assertTrue(hasFile);

		// when
		hasFile = _sut.hasFile(80000, 10000, "subdir/file 2.pdf", "2.0");

		// then
		assertFalse(hasFile);

		// when
		hasFile = _sut.hasFile(80000, 10000, "subdir/file 3.pdf", "1.0");

		// then
		assertFalse(hasFile);

		// when
		hasFile = _sut.hasFile(80000, 10000, "file-2.pdf", "1.0");

		// then
		assertFalse(hasFile);

		// when
		hasFile = _sut.hasFile(80000, 10000, "missing-file.pdf", "1.0");

		// then
		assertFalse(hasFile);
	}

	/**
	 * Returns <code>true</code> in case there are NO blobs under this prefix (== "dir" is empty).
	 * Returns <code>false</code> in case there are some blobs under this prefix (== "dir" is NOT empty).
	 * @param dir
	 * @return
	 */
	private boolean _noBlobsWithPrefix(String dir) {
		return _blobsWithPrefix(dir).length == 0;
	}

	private String[] _blobsWithPrefix(String dir) {
		ListBlobsOptions opts = new ListBlobsOptions()
			.setPrefix(dir);

		PagedIterable<BlobItem> blobItems =
			_adminContainerClient.listBlobs(opts, Duration.ofSeconds(10));

		return blobItems.stream()
			.map(BlobItem::getName)
			.toArray(String[]::new);
	}

	private boolean _exists(String blobPath) {
		ListBlobsOptions opts = new ListBlobsOptions()
			.setPrefix(blobPath);

		PagedIterable<BlobItem> blobItems =
			_adminContainerClient.listBlobs(opts, Duration.ofSeconds(10));

		return blobItems.stream().findFirst().isPresent();
	}

	private void _upload(String blobPath, TestFiles.TestFile testFile) {
		BlobClient bc = _adminContainerClient.getBlobClient(blobPath);

		try (InputStream is = testFile.openStream()) {
			bc.upload(is, testFile.getSizeBytes());
		}
		catch (IOException ioe) {
			throw new UncheckedIOException("Cannot upload test file", ioe);
		}
	}

	void assertArrayEquals(Object[] expected, Object[] actual) {
		try {
			Assert.assertArrayEquals(expected, actual);
		} catch (AssertionError ae) {
			throw new AssertionError(
				String.format(
					"%s (expected: %s, actual: %s)",
					ae.getMessage(), Arrays.toString(expected), Arrays.toString(actual)));
		}
	}

	void assertArrayNotEmpty(Object[] actual) {
		if (actual == null || actual.length == 0) {
			fail("The array should not be empty");
		}
	}

	public static final long _DEFAULT_TIMEOUT_MS = 10000;
	public static final String[] _EMPTY = new String[0];

	private static AzureBlobStorageStore _sut;

	/**
	 * Client with admin privileges, to setup the storage account for tests +
	 * cleanup afterwards.
	 */
	private static BlobContainerClient _adminContainerClient;

	private static final boolean cleanupAfterTests =
		Boolean.parseBoolean(System.getenv("tests.storage-admin.cleanupAfterTests"));

	private static final String storageAdminConnectionString =
		System.getenv("tests.storage-admin.connectionString");

}