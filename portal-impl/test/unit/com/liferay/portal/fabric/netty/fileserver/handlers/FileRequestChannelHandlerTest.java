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

package com.liferay.portal.fabric.netty.fileserver.handlers;

import com.liferay.portal.fabric.netty.fileserver.CompressionLevel;
import com.liferay.portal.fabric.netty.fileserver.FileRequest;
import com.liferay.portal.fabric.netty.fileserver.FileResponse;
import com.liferay.portal.kernel.io.BigEndianCodec;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.StreamUtil;

import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;
import io.netty.channel.embedded.EmbeddedChannel;

import java.io.IOException;
import java.io.InputStream;

import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class FileRequestChannelHandlerTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@After
	public void tearDown() {
		_embeddedChannel.finish();

		FileServerTestUtil.cleanUp();
	}

	@Test
	public void testFileNotFound() {
		Path path = FileServerTestUtil.createNotExistFile(
			Paths.get("testNotExistFile"));

		_embeddedChannel.writeInbound(new FileRequest(path, 0, false));

		Queue<Object> queue = _embeddedChannel.outboundMessages();

		Assert.assertEquals(1, queue.size());
		Assert.assertEquals(
			new FileResponse(path, FileResponse.FILE_NOT_FOUND, -1, false),
			queue.poll());
	}

	@Test
	public void testFileNotModified() throws IOException {
		Path path = FileServerTestUtil.createEmptyFile(
			Paths.get("testEmptyFile"));

		FileTime fileTime = Files.getLastModifiedTime(path);

		_embeddedChannel.writeInbound(
			new FileRequest(path, fileTime.toMillis(), false));

		Queue<Object> queue = _embeddedChannel.outboundMessages();

		Assert.assertEquals(1, queue.size());
		Assert.assertEquals(
			new FileResponse(path, FileResponse.FILE_NOT_MODIFIED, -1, false),
			queue.poll());
	}

	@Test
	public void testFileTransfer() throws IOException {
		doTestFileTransfer(true);
		doTestFileTransfer(false);
	}

	@Test
	public void testFolderTransfer() throws IOException {
		doTestFolderTransfer(true);
		doTestFolderTransfer(false);
	}

	protected void doTestFileTransfer(boolean deleteAfterFetch)
		throws IOException {

		Path path = FileServerTestUtil.createFileWithData(
			Paths.get("testFile"));

		FileTime fileTime = Files.getLastModifiedTime(path);

		byte[] data = Files.readAllBytes(path);

		_embeddedChannel.writeInbound(
			new FileRequest(path, 0, deleteAfterFetch));

		Queue<Object> queue = _embeddedChannel.outboundMessages();

		Assert.assertEquals(2, queue.size());

		if (deleteAfterFetch) {
			Assert.assertTrue(Files.notExists(path));
		}

		Assert.assertEquals(
			new FileResponse(path, data.length, fileTime.toMillis(), false),
			queue.poll());
		Assert.assertArrayEquals(
			data, _readFileRegion((DefaultFileRegion)queue.poll()));
	}

	protected void doTestFolderTransfer(boolean deleteAfterFetch)
		throws IOException {

		Path path = FileServerTestUtil.createFolderWithFiles(
			Paths.get("testFolder"));

		FileTime fileTime = Files.getLastModifiedTime(path);

		_embeddedChannel.writeInbound(
			new FileRequest(path, 0, deleteAfterFetch));

		Queue<Object> queue = _embeddedChannel.outboundMessages();

		Assert.assertEquals(2, queue.size());

		if (deleteAfterFetch) {
			Assert.assertTrue(Files.notExists(path));
		}

		FileResponse fileResponse = (FileResponse)queue.poll();

		Assert.assertEquals(
			new FileResponse(
				path, fileResponse.getSize(), fileTime.toMillis(), true),
			fileResponse);

		if (deleteAfterFetch) {
			Assert.assertTrue(queue.poll() instanceof FileRegion);
		}
		else {
			_assertZipStream(
				path,
				new UnsyncByteArrayInputStream(
					_readFileRegion((FileRegion)queue.poll())));
		}
	}

	private void _assertZipStream(
			Path expectedRootFolder, InputStream inputStream)
		throws IOException {

		final List<Path> files = new ArrayList<Path>();

		try (ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
			ZipEntry zipEntry = null;

			while ((zipEntry = zipInputStream.getNextEntry()) != null) {
				if (zipEntry.isDirectory()) {
					continue;
				}

				Path expectedFile = expectedRootFolder.resolve(
					zipEntry.getName());

				expectedFile = expectedFile.toAbsolutePath();

				Assert.assertTrue(
					"Zip entry file " + expectedFile + " does not exist",
					Files.exists(expectedFile));

				FileTime fileTime = Files.getLastModifiedTime(expectedFile);

				Assert.assertEquals(
					"Last modified time mismatch", fileTime.toMillis(),
					BigEndianCodec.getLong(zipEntry.getExtra(), 0));
				Assert.assertEquals(
					"File size mismatch", Files.size(expectedFile),
					BigEndianCodec.getLong(zipEntry.getExtra(), 8));
				Assert.assertArrayEquals(
					"File content mismatch", Files.readAllBytes(expectedFile),
					_readInputStream(zipInputStream));

				files.add(expectedFile);
			}
		}

		Files.walkFileTree(
			expectedRootFolder,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
					Path file, BasicFileAttributes attrs) {

					file = file.toAbsolutePath();

					Assert.assertTrue(
						"Miss file " + file + " from zip stream",
						files.contains(file));

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private byte[] _readFileRegion(FileRegion fileRegion) throws IOException {
		try (UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream();
			WritableByteChannel writableByteChannel = Channels.newChannel(
				unsyncByteArrayOutputStream)) {

			while (fileRegion.transfered() < fileRegion.count()) {
				fileRegion.transferTo(
					writableByteChannel, fileRegion.transfered());
			}

			return unsyncByteArrayOutputStream.toByteArray();
		}
		finally {
			fileRegion.release();
		}
	}

	private byte[] _readInputStream(InputStream inputStream)
		throws IOException {

		try (UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream()) {

			StreamUtil.transfer(
				inputStream, unsyncByteArrayOutputStream, false);

			return unsyncByteArrayOutputStream.toByteArray();
		}
	}

	private final EmbeddedChannel _embeddedChannel = new EmbeddedChannel(
		new FileRequestChannelHandler(CompressionLevel.NO_COMPRESSION));

}