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

package com.liferay.portal.fabric.netty.fileserver;

import com.liferay.petra.io.BigEndianCodec;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.fabric.netty.fileserver.handlers.FileServerTestUtil;
import com.liferay.portal.kernel.nio.FileSystemProviderWrapper;
import com.liferay.portal.kernel.nio.FileSystemWrapper;
import com.liferay.portal.kernel.nio.PathWrapper;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.SwappableSecurityManager;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.nio.file.spi.FileSystemProvider;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class FileHelperUtilTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@After
	public void tearDown() {
		FileServerTestUtil.cleanUp();
	}

	@Test
	public void testConstructor() {
		new FileHelperUtil();
	}

	@Test
	public void testDeleteNoSuchFile() throws IOException {
		Path noSuchFilePath = Paths.get("NoSuchFile");

		Files.deleteIfExists(noSuchFilePath);

		FileHelperUtil.delete(true, noSuchFilePath);
		FileHelperUtil.delete(noSuchFilePath);
	}

	@Test
	public void testDeleteRegularDirectoryWithRegularFile() {
		Path regularDirectoryPath = Paths.get("RegularDirectory");

		Path regularFilePath = regularDirectoryPath.resolve("RegularFile");

		createFile(regularFilePath);

		FileHelperUtil.delete(true, regularDirectoryPath);

		Assert.assertTrue(Files.notExists(regularFilePath));
		Assert.assertTrue(Files.notExists(regularDirectoryPath));

		createFile(regularFilePath);

		FileHelperUtil.delete(regularDirectoryPath);

		Assert.assertTrue(Files.notExists(regularFilePath));
		Assert.assertTrue(Files.notExists(regularDirectoryPath));
	}

	@Test
	public void testDeleteRegularDirectoryWithUndeleteableFile()
		throws IOException {

		final IOException ioException = new IOException("Unable to delete");

		Path regularDirectoryPath = Paths.get("RegularDirectory");

		final Path undeleteableFilePath = regularDirectoryPath.resolve(
			"UndeleteableFile");

		createFile(undeleteableFilePath);

		try (SwappableSecurityManager swappableSecurityManager =
				new SwappableSecurityManager() {

					@Override
					public void checkDelete(String file) {
						if (file.equals(undeleteableFilePath.toString())) {
							ReflectionUtil.throwException(
								new DirectoryIteratorException(ioException));
						}
					}

				}) {

			swappableSecurityManager.install();

			FileHelperUtil.delete(true, regularDirectoryPath);
			FileHelperUtil.delete(regularDirectoryPath);

			Assert.fail();
		}
		catch (Exception e) {
			Assert.assertSame(ioException, e.getCause());
		}
		finally {
			Files.delete(undeleteableFilePath);
			Files.delete(regularDirectoryPath);
		}
	}

	@Test
	public void testDeleteRegularFile() {
		Path regularFilePath = Paths.get("RegularFile");

		createFile(regularFilePath);

		FileHelperUtil.delete(true, regularFilePath);

		Assert.assertTrue(Files.notExists(regularFilePath));

		createFile(regularFilePath);

		FileHelperUtil.delete(regularFilePath);

		Assert.assertTrue(Files.notExists(regularFilePath));
	}

	@Test
	public void testDeleteUndeleteableDirectoryWithRegularFile()
		throws IOException {

		final Path undeleteableDirectoryPath = Paths.get(
			"UndeleteableDirectory");

		Path regularFilePath = undeleteableDirectoryPath.resolve("RegularFile");

		final Path newRegularFilePath = undeleteableDirectoryPath.resolve(
			"NewRegularFile");

		createFile(regularFilePath);

		try (SwappableSecurityManager swappableSecurityManager =
				new SwappableSecurityManager() {

					@Override
					public void checkDelete(String file) {
						if (!file.equals(
								undeleteableDirectoryPath.toString())) {

							return;
						}

						createFile(newRegularFilePath);
					}

				}) {

			swappableSecurityManager.install();

			FileHelperUtil.delete(true, undeleteableDirectoryPath);

			Files.delete(newRegularFilePath);

			createFile(regularFilePath);

			FileHelperUtil.delete(undeleteableDirectoryPath);

			Assert.fail();
		}
		catch (Exception e) {
			Assert.assertSame(DirectoryNotEmptyException.class, e.getClass());
		}
		finally {
			Files.delete(newRegularFilePath);
			Files.delete(undeleteableDirectoryPath);
		}
	}

	@Test
	public void testDeleteUndeleteableFile() throws IOException {
		final IOException ioException = new IOException("Unable to delete");

		final Path undeleteableFilePath = Paths.get("UndeleteableFile");

		createFile(undeleteableFilePath);

		try (SwappableSecurityManager swappableSecurityManager =
				new SwappableSecurityManager() {

					@Override
					public void checkDelete(String file) {
						if (file.equals(undeleteableFilePath.toString())) {
							ReflectionUtil.throwException(ioException);
						}
					}

				}) {

			swappableSecurityManager.install();

			FileHelperUtil.delete(true, undeleteableFilePath);
			FileHelperUtil.delete(undeleteableFilePath);

			Assert.fail();
		}
		catch (Exception e) {
			Assert.assertSame(ioException, e);
		}
		finally {
			Files.delete(undeleteableFilePath);
		}
	}

	@Test
	public void testDeleteUnreadableFile() throws IOException {
		final IOException ioException = new IOException("Unable to read");

		final Path unreadableFilePath = Paths.get("UnreadableFile");

		createFile(unreadableFilePath);

		try (SwappableSecurityManager swappableSecurityManager =
				new SwappableSecurityManager() {

					@Override
					public void checkRead(String file) {
						if (file.equals(unreadableFilePath.toString())) {
							ReflectionUtil.throwException(ioException);
						}
					}

				}) {

			swappableSecurityManager.install();

			FileHelperUtil.delete(true, unreadableFilePath);
			FileHelperUtil.delete(unreadableFilePath);

			Assert.fail();
		}
		catch (Exception e) {
			Assert.assertSame(ioException, e);
		}
		finally {
			Files.delete(unreadableFilePath);
		}
	}

	@Test
	public void testMoveNoSuchFile() throws IOException {
		Path noSuchFilePath = Paths.get("NoSuchFile");
		Path toFilePath = Paths.get("ToFile");

		Files.deleteIfExists(noSuchFilePath);

		try {
			FileHelperUtil.move(noSuchFilePath, toFilePath, false);

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertSame(NoSuchFileException.class, ioe.getClass());
		}

		try {
			FileHelperUtil.move(noSuchFilePath, toFilePath);

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertSame(NoSuchFileException.class, ioe.getClass());
		}
	}

	@Test
	public void testMoveRegularDirectoryWithRegularFile() throws IOException {
		Path regularFromDirectoryPath = Paths.get("RegularFromDirectory");

		Path regularFromFilePath = regularFromDirectoryPath.resolve(
			"RegularFromFile");

		Path regularToDirectoryPath = Paths.get("RegularToDirectory");

		Path regularToFilePath = regularToDirectoryPath.resolve(
			regularFromDirectoryPath.relativize(regularFromFilePath));

		createFile(regularFromFilePath);

		FileTime originalFileTime = Files.getLastModifiedTime(
			regularFromDirectoryPath);

		FileHelperUtil.move(
			regularFromDirectoryPath, regularToDirectoryPath, false);

		Assert.assertEquals(
			originalFileTime,
			Files.getLastModifiedTime(regularToDirectoryPath));
		Assert.assertTrue(Files.notExists(regularFromFilePath));
		Assert.assertTrue(Files.notExists(regularFromDirectoryPath));
		Assert.assertTrue(Files.exists(regularToDirectoryPath));
		Assert.assertTrue(Files.exists(regularToFilePath));

		Files.delete(regularToFilePath);
		Files.delete(regularToDirectoryPath);

		createFile(regularFromFilePath);

		originalFileTime = Files.getLastModifiedTime(regularFromDirectoryPath);

		FileHelperUtil.move(regularFromDirectoryPath, regularToDirectoryPath);

		Assert.assertEquals(
			originalFileTime,
			Files.getLastModifiedTime(regularToDirectoryPath));
		Assert.assertTrue(Files.notExists(regularFromFilePath));
		Assert.assertTrue(Files.notExists(regularFromDirectoryPath));
		Assert.assertTrue(Files.exists(regularToDirectoryPath));
		Assert.assertTrue(Files.exists(regularToFilePath));

		Files.delete(regularToFilePath);
		Files.delete(regularToDirectoryPath);
	}

	@Test
	public void testMoveRegularFile() throws IOException {
		Path regularFromFilePath = Paths.get("RegularFromFile");
		Path regularToFilePath = Paths.get("RegularToFile");

		createFile(regularFromFilePath);

		FileHelperUtil.move(regularFromFilePath, regularToFilePath, false);

		Assert.assertTrue(Files.notExists(regularFromFilePath));
		Assert.assertTrue(Files.exists(regularToFilePath));

		Files.delete(regularToFilePath);

		createFile(regularFromFilePath);

		FileHelperUtil.move(regularFromFilePath, regularToFilePath);

		Assert.assertTrue(Files.notExists(regularFromFilePath));
		Assert.assertTrue(Files.exists(regularToFilePath));

		Files.delete(regularToFilePath);
	}

	@Test
	public void testMoveRegularFileAtomicFailure() throws IOException {
		final Path regularFromFilePath = Paths.get("RegularFromFile");
		Path regularToFilePath = Paths.get("RegularToFile");

		final AtomicMoveNotSupportedException atomicMoveNotSupportedException =
			new AtomicMoveNotSupportedException(
				regularFromFilePath.toString(), regularToFilePath.toString(),
				"Atomic move not supported");

		createFile(regularFromFilePath);

		try (SwappableSecurityManager swappableSecurityManager =
				new SwappableSecurityManager() {

					@Override
					public void checkWrite(String file) {
						if (file.equals(regularFromFilePath.toString())) {
							ReflectionUtil.throwException(
								atomicMoveNotSupportedException);
						}
					}

				}) {

			swappableSecurityManager.install();

			FileHelperUtil.move(regularFromFilePath, regularToFilePath);
		}

		Assert.assertTrue(Files.notExists(regularFromFilePath));
		Assert.assertTrue(Files.exists(regularToFilePath));

		Files.delete(regularToFilePath);
	}

	@Test
	public void testMoveUnmoveableDirectoryWithRegularFile()
		throws IOException {

		final Path unmoveableFromDirectoryPath = Paths.get(
			"UnmoveableDirectory");

		Path regularFromFilePath = unmoveableFromDirectoryPath.resolve(
			"RegularFromFile");

		Path regularToDirectoryPath = Paths.get("RegularToDirectoryPath");

		Path regularToFilePath = regularToDirectoryPath.resolve(
			unmoveableFromDirectoryPath.relativize(regularFromFilePath));

		final Path newRegularFilePath = unmoveableFromDirectoryPath.resolve(
			"NewRegularFile");

		createFile(regularFromFilePath);

		try (SwappableSecurityManager swappableSecurityManager =
				new SwappableSecurityManager() {

					@Override
					public void checkDelete(String file) {
						if (!file.equals(
								unmoveableFromDirectoryPath.toString())) {

							return;
						}

						createFile(newRegularFilePath);
					}

				}) {

			swappableSecurityManager.install();

			FileHelperUtil.move(
				unmoveableFromDirectoryPath, regularToDirectoryPath, false);

			Assert.fail();
		}
		catch (DirectoryNotEmptyException dnee) {
		}
		finally {
			Assert.assertTrue(Files.exists(regularToFilePath));
			Assert.assertTrue(Files.exists(regularToDirectoryPath));
			Assert.assertTrue(Files.notExists(regularFromFilePath));
			Assert.assertTrue(Files.exists(newRegularFilePath));
			Assert.assertTrue(Files.exists(unmoveableFromDirectoryPath));

			Files.delete(regularToFilePath);
			Files.delete(regularToDirectoryPath);
			Files.delete(newRegularFilePath);
			Files.delete(unmoveableFromDirectoryPath);
		}

		createFile(regularFromFilePath);

		try (SwappableSecurityManager swappableSecurityManager =
				new SwappableSecurityManager() {

					@Override
					public void checkDelete(String file) {
						if (!file.equals(
								unmoveableFromDirectoryPath.toString())) {

							return;
						}

						createFile(newRegularFilePath);
					}

				}) {

			swappableSecurityManager.install();

			FileHelperUtil.move(
				unmoveableFromDirectoryPath, regularToDirectoryPath);

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertEquals(
				"Source path " + unmoveableFromDirectoryPath +
					" was left in an inconsistent state",
				ioe.getMessage());

			Throwable throwable = ioe.getCause();

			Assert.assertSame(
				DirectoryNotEmptyException.class, throwable.getClass());
		}
		finally {
			Assert.assertTrue(Files.notExists(regularToFilePath));
			Assert.assertTrue(Files.notExists(regularToDirectoryPath));
			Assert.assertTrue(Files.notExists(regularFromFilePath));
			Assert.assertTrue(Files.exists(newRegularFilePath));
			Assert.assertTrue(Files.exists(unmoveableFromDirectoryPath));

			Files.delete(newRegularFilePath);
			Files.delete(unmoveableFromDirectoryPath);
		}
	}

	@Test
	public void testUnzipCorrupttedFile() throws IOException {
		Path corrupttedZipFilePath = FileServerTestUtil.registerForCleanUp(
			FileServerTestUtil.createEmptyFile(Paths.get("CorrupttedZipFile")));

		String fileEntryName = "CorrupttedFile";
		int annotatedSize = 1024;
		int actualSize = 512;

		try (ZipOutputStream zipOutputStream = new ZipOutputStream(
				Files.newOutputStream(corrupttedZipFilePath))) {

			ZipEntry zipEntry = new ZipEntry(fileEntryName);

			byte[] buffer = new byte[16];

			BigEndianCodec.putLong(buffer, 0, System.currentTimeMillis());
			BigEndianCodec.putLong(buffer, 8, annotatedSize);

			zipEntry.setExtra(buffer);

			zipOutputStream.putNextEntry(zipEntry);
			zipOutputStream.write(
				FileServerTestUtil.createRandomData(actualSize));
			zipOutputStream.closeEntry();
		}

		try {
			FileHelperUtil.unzip(
				corrupttedZipFilePath, FileHelperUtil.TEMP_DIR_PATH);

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertEquals(
				StringBundler.concat(
					"Zip stream for entry ", fileEntryName, " is ", actualSize,
					" bytes but should ", annotatedSize, " bytes"),
				ioe.getMessage());
		}
	}

	@Test
	public void testUnzipImpossibleScenario() throws IOException {
		FileSystem fileSystem = FileSystems.getDefault();

		FileSystemProvider fileSystemProvider = new FileSystemProviderWrapper(
			fileSystem.provider()) {

			@Override
			public InputStream newInputStream(
				Path path, OpenOption... options) {

				return null;
			}

		};

		Path impossiableSourceFilePath = Paths.get("ImpossibleSourceFilePath");

		impossiableSourceFilePath = fileSystemProvider.getPath(
			impossiableSourceFilePath.toUri());

		try {
			FileHelperUtil.unzip(
				impossiableSourceFilePath, FileHelperUtil.TEMP_DIR_PATH);

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}
	}

	@Test
	public void testUnzipNoSuchFile() {
		try {
			FileHelperUtil.unzip(
				Paths.get("NoSuchFile"), FileHelperUtil.TEMP_DIR_PATH);
		}
		catch (IOException ioe) {
			Assert.assertSame(NoSuchFileException.class, ioe.getClass());
		}
	}

	@Test
	public void testUnzipNullZipInputStream() throws IOException {
		try {
			FileHelperUtil.unzip((ZipInputStream)null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}
	}

	@Test
	public void testUnzipUnreadableInputStream() {
		final IOException ioException = new IOException();

		try {
			FileHelperUtil.unzip(
				new ZipInputStream(
					new UnsyncByteArrayInputStream(new byte[0])) {

					@Override
					public int read() throws IOException {
						throw ioException;
					}

				},
				null);
		}
		catch (IOException ioe) {
			Assert.assertSame(ioException, ioe);
		}
	}

	@Test
	public void testZipAndUnzip() throws IOException {

		// With log

		String folderName = "TestFolder";

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					FileHelperUtil.class.getName(), Level.FINEST)) {

			Path folderPath = FileServerTestUtil.createFolderWithFiles(
				Paths.get(folderName));

			Path zipFilePath = FileServerTestUtil.registerForCleanUp(
				FileHelperUtil.zip(
					folderPath, FileHelperUtil.TEMP_DIR_PATH,
					CompressionLevel.BEST_COMPRESSION));

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.remove(0);

			String message = logRecord.getMessage();

			Assert.assertTrue(message.startsWith("Zipped"));

			try (FileSystem fileSystem = FileSystems.newFileSystem(
					zipFilePath, null)) {

				Files.createDirectory(fileSystem.getPath("EmptyFolder"));

				FileServerTestUtil.assertFileEquals(
					folderPath, fileSystem.getPath("/"));
			}

			Path unzipFolderPath = FileServerTestUtil.registerForCleanUp(
				FileHelperUtil.unzip(
					zipFilePath, FileHelperUtil.TEMP_DIR_PATH));

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			logRecord = logRecords.remove(0);

			message = logRecord.getMessage();

			Assert.assertTrue(message.startsWith("Unzipped"));

			FileServerTestUtil.assertFileEquals(folderPath, unzipFolderPath);
		}

		// Without log

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					FileHelperUtil.class.getName(), Level.OFF)) {

			Path folderPath = FileServerTestUtil.createFolderWithFiles(
				Paths.get(folderName));

			Path zipFilePath = FileServerTestUtil.registerForCleanUp(
				FileHelperUtil.zip(
					folderPath, FileHelperUtil.TEMP_DIR_PATH,
					CompressionLevel.BEST_COMPRESSION));

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertTrue(logRecords.toString(), logRecords.isEmpty());

			try (FileSystem fileSystem = FileSystems.newFileSystem(
					zipFilePath, null)) {

				Files.createDirectory(fileSystem.getPath("EmptyFolder"));

				FileServerTestUtil.assertFileEquals(
					folderPath, fileSystem.getPath("/"));
			}

			Path unzipFolderPath = FileServerTestUtil.registerForCleanUp(
				FileHelperUtil.unzip(
					zipFilePath, FileHelperUtil.TEMP_DIR_PATH));

			Assert.assertTrue(logRecords.toString(), logRecords.isEmpty());

			FileServerTestUtil.assertFileEquals(folderPath, unzipFolderPath);
		}
	}

	@Test
	public void testZipImpossibleScenario() throws IOException {
		FileSystem fileSystem = FileSystems.getDefault();

		FileSystemProvider fileSystemProvider = new FileSystemProviderWrapper(
			fileSystem.provider()) {

			@Override
			public OutputStream newOutputStream(
				Path path, OpenOption... options) {

				return null;
			}

		};

		Path impossiableDestDirPath = new PathWrapper(
			FileHelperUtil.TEMP_DIR_PATH,
			new FileSystemWrapper(fileSystem, fileSystemProvider));

		try {
			FileHelperUtil.zip(
				null, impossiableDestDirPath, CompressionLevel.NO_COMPRESSION);

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}
	}

	@Test
	public void testZipNoSuchFile() {
		try {
			FileHelperUtil.zip(
				Paths.get("NoSuchFile"), FileHelperUtil.TEMP_DIR_PATH,
				CompressionLevel.NO_COMPRESSION);
		}
		catch (IOException ioe) {
			Assert.assertSame(NoSuchFileException.class, ioe.getClass());
		}
	}

	@Test
	public void testZipNullZipOutputStream() throws IOException {
		try {
			FileHelperUtil.zip(
				FileServerTestUtil.createFolderWithFiles(
					Paths.get("TestFolder")),
				(ZipOutputStream)null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}
	}

	protected void createFile(Path path) {
		try {
			boolean deleteDirectory = false;

			Path parentPath = path.getParent();

			if ((parentPath != null) && Files.notExists(parentPath)) {
				Files.createDirectories(parentPath);

				deleteDirectory = true;
			}

			Files.createFile(path);

			File file = path.toFile();

			file.deleteOnExit();

			if (deleteDirectory) {
				file = parentPath.toFile();

				file.deleteOnExit();
			}
		}
		catch (IOException ioe) {
			ReflectionUtil.throwException(ioe);
		}
	}

}