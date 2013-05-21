/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.nio.charset.CharsetEncoderUtil;
import com.liferay.portal.kernel.process.ClassPathUtil;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.process.ProcessExecutor;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileComparator;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.PwdGenerator;
import com.liferay.util.ant.ExpandTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.tika.Tika;
import org.apache.tools.ant.DirectoryScanner;

import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsPSMDetector;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
@DoPrivileged
public class FileImpl implements com.liferay.portal.kernel.util.File {

	public static FileImpl getInstance() {
		return _instance;
	}

	public void copyDirectory(File source, File destination)
		throws IOException {

		if (!source.exists() || !source.isDirectory()) {
			return;
		}

		if (!destination.exists()) {
			destination.mkdirs();
		}

		File[] fileArray = source.listFiles();

		for (int i = 0; i < fileArray.length; i++) {
			if (fileArray[i].isDirectory()) {
				copyDirectory(
					fileArray[i],
					new File(
						destination.getPath() + File.separator +
							fileArray[i].getName()));
			}
			else {
				copyFile(
					fileArray[i],
					new File(
						destination.getPath() + File.separator +
							fileArray[i].getName()));
			}
		}
	}

	public void copyDirectory(String sourceDirName, String destinationDirName)
		throws IOException {

		copyDirectory(new File(sourceDirName), new File(destinationDirName));
	}

	public void copyFile(File source, File destination) throws IOException {
		copyFile(source, destination, false);
	}

	public void copyFile(File source, File destination, boolean lazy)
		throws IOException {

		if (!source.exists()) {
			return;
		}

		if (lazy) {
			String oldContent = null;

			try {
				oldContent = read(source);
			}
			catch (Exception e) {
				return;
			}

			String newContent = null;

			try {
				newContent = read(destination);
			}
			catch (Exception e) {
			}

			if ((oldContent == null) || !oldContent.equals(newContent)) {
				copyFile(source, destination, false);
			}
		}
		else {
			mkdirsParentFile(destination);

			StreamUtil.transfer(
				new FileInputStream(source), new FileOutputStream(destination));
		}
	}

	public void copyFile(String source, String destination) throws IOException {
		copyFile(source, destination, false);
	}

	public void copyFile(String source, String destination, boolean lazy)
		throws IOException {

		copyFile(new File(source), new File(destination), lazy);
	}

	public File createTempFile() {
		return createTempFile(StringPool.BLANK);
	}

	public File createTempFile(byte[] bytes) throws IOException {
		File file = createTempFile(StringPool.BLANK);

		write(file, bytes);

		return file;
	}

	public File createTempFile(InputStream is) throws IOException {
		File file = createTempFile(StringPool.BLANK);

		write(file, is);

		return file;
	}

	public File createTempFile(String extension) {
		return new File(createTempFileName(extension));
	}

	public String createTempFileName() {
		return createTempFileName(null);
	}

	public String createTempFileName(String extension) {
		StringBundler sb = new StringBundler();

		sb.append(SystemProperties.get(SystemProperties.TMP_DIR));
		sb.append(StringPool.SLASH);
		sb.append(Time.getTimestamp());
		sb.append(PwdGenerator.getPassword(PwdGenerator.KEY2, 8));

		if (Validator.isFileExtension(extension)) {
			sb.append(StringPool.PERIOD);
			sb.append(extension);
		}

		return sb.toString();
	}

	public File createTempFolder() {
		File file = new File(createTempFileName());

		file.mkdirs();

		return file;
	}

	public String decodeSafeFileName(String fileName) {
		return StringUtil.replace(
			fileName, _SAFE_FILE_NAME_2, _SAFE_FILE_NAME_1);
	}

	public boolean delete(File file) {
		if (file != null) {
			boolean exists = true;

			try {
				exists = file.exists();
			}
			catch (SecurityException se) {

				// We may have the permission to delete a specific file without
				// having the permission to check if the file exists

			}

			if (exists) {
				return file.delete();
			}
		}

		return false;
	}

	public boolean delete(String file) {
		return delete(new File(file));
	}

	public void deltree(File directory) {
		if (directory.exists() && directory.isDirectory()) {
			File[] fileArray = directory.listFiles();

			for (int i = 0; i < fileArray.length; i++) {
				if (fileArray[i].isDirectory()) {
					deltree(fileArray[i]);
				}
				else {
					fileArray[i].delete();
				}
			}

			directory.delete();
		}
	}

	public void deltree(String directory) {
		deltree(new File(directory));
	}

	public String encodeSafeFileName(String fileName) {
		if (fileName == null) {
			return StringPool.BLANK;
		}

		return StringUtil.replace(
			fileName, _SAFE_FILE_NAME_1, _SAFE_FILE_NAME_2);
	}

	public boolean exists(File file) {
		return file.exists();
	}

	public boolean exists(String fileName) {
		return exists(new File(fileName));
	}

	public String extractText(InputStream is, String fileName) {
		String text = null;

		ClassLoader portalClassLoader = ClassLoaderUtil.getPortalClassLoader();

		ClassLoader contextClassLoader =
			ClassLoaderUtil.getContextClassLoader();

		try {
			if (contextClassLoader != portalClassLoader) {
				ClassLoaderUtil.setContextClassLoader(portalClassLoader);
			}

			Tika tika = new Tika();

			tika.setMaxStringLength(-1);

			boolean forkProcess = false;

			if (PropsValues.TEXT_EXTRACTION_FORK_PROCESS_ENABLED) {
				String mimeType = tika.detect(is);

				if (ArrayUtil.contains(
						PropsValues.TEXT_EXTRACTION_FORK_PROCESS_MIME_TYPES,
						mimeType)) {

					forkProcess = true;
				}
			}

			if (forkProcess) {
				Future<String> future = ProcessExecutor.execute(
					ClassPathUtil.getPortalClassPath(),
					new ExtractTextProcessCallable(getBytes(is)));

				text = future.get();
			}
			else {
				text = tika.parseToString(is);
			}
		}
		catch (Exception e) {
			Throwable throwable = ExceptionUtils.getRootCause(e);

			if ((throwable instanceof CryptographyException) ||
				(throwable instanceof EncryptedDocumentException)) {

				if (_log.isWarnEnabled()) {
					_log.warn("Unable to extract text from an encrypted file");
				}
			}
			else {
				_log.error(e, e);
			}
		}
		finally {
			if (contextClassLoader != portalClassLoader) {
				ClassLoaderUtil.setContextClassLoader(contextClassLoader);
			}
		}

		if (_log.isInfoEnabled()) {
			if (text == null) {
				_log.info("Text extraction failed for " + fileName);
			}
			else {
				_log.info("Text was extracted for " + fileName);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Extractor returned text:\n\n" + text);
		}

		if (text == null) {
			text = StringPool.BLANK;
		}

		return text;
	}

	public String[] find(String directory, String includes, String excludes) {
		if (directory.length() > 0) {
			directory = replaceSeparator(directory);

			if (directory.charAt(directory.length() - 1) == CharPool.SLASH) {
				directory = directory.substring(0, directory.length() - 1);
			}
		}

		if (!exists(directory)) {
			if (_log.isWarnEnabled()) {
				_log.warn("Directory " + directory + " does not exist");
			}

			return new String[0];
		}

		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(directory);
		directoryScanner.setExcludes(StringUtil.split(excludes));
		directoryScanner.setIncludes(StringUtil.split(includes));

		directoryScanner.scan();

		String[] includedFiles = directoryScanner.getIncludedFiles();

		for (int i = 0; i < includedFiles.length; i++) {
			includedFiles[i] = directory.concat(
				StringPool.SLASH).concat(replaceSeparator(includedFiles[i]));
		}

		return includedFiles;
	}

	public String getAbsolutePath(File file) {
		return StringUtil.replace(
			file.getAbsolutePath(), CharPool.BACK_SLASH, CharPool.SLASH);
	}

	public byte[] getBytes(File file) throws IOException {
		if ((file == null) || !file.exists()) {
			return null;
		}

		RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");

		byte[] bytes = new byte[(int)randomAccessFile.length()];

		randomAccessFile.readFully(bytes);

		randomAccessFile.close();

		return bytes;
	}

	public byte[] getBytes(InputStream is) throws IOException {
		return getBytes(is, -1);
	}

	public byte[] getBytes(InputStream inputStream, int bufferSize)
		throws IOException {

		return getBytes(inputStream, bufferSize, true);
	}

	public byte[] getBytes(
			InputStream inputStream, int bufferSize, boolean cleanUpStream)
		throws IOException {

		if (inputStream == null) {
			return null;
		}

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		StreamUtil.transfer(
			inputStream, unsyncByteArrayOutputStream, bufferSize,
			cleanUpStream);

		return unsyncByteArrayOutputStream.toByteArray();
	}

	public String getExtension(String fileName) {
		if (fileName == null) {
			return null;
		}

		int pos = fileName.lastIndexOf(CharPool.PERIOD);

		if (pos > 0) {
			return fileName.substring(pos + 1, fileName.length()).toLowerCase();
		}
		else {
			return StringPool.BLANK;
		}
	}

	public String getPath(String fullFileName) {
		int x = fullFileName.lastIndexOf(CharPool.SLASH);
		int y = fullFileName.lastIndexOf(CharPool.BACK_SLASH);

		if ((x == -1) && (y == -1)) {
			return StringPool.SLASH;
		}

		String shortFileName = fullFileName.substring(0, Math.max(x, y));

		return shortFileName;
	}

	public String getShortFileName(String fullFileName) {
		int x = fullFileName.lastIndexOf(CharPool.SLASH);
		int y = fullFileName.lastIndexOf(CharPool.BACK_SLASH);

		String shortFileName = fullFileName.substring(Math.max(x, y) + 1);

		return shortFileName;
	}

	public boolean isAscii(File file) throws IOException {
		boolean ascii = true;

		nsDetector detector = new nsDetector(nsPSMDetector.ALL);

		InputStream inputStream = new FileInputStream(file);

		byte[] buffer = new byte[1024];

		int len = 0;

		while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
			if (ascii) {
				ascii = detector.isAscii(buffer, len);

				if (!ascii) {
					break;
				}
			}
		}

		detector.DataEnd();

		inputStream.close();

		return ascii;
	}

	public boolean isSameContent(File file, byte[] bytes, int length) {
		FileChannel fileChannel = null;

		try {
			FileInputStream fileInputStream = new FileInputStream(file);

			fileChannel = fileInputStream.getChannel();

			if (fileChannel.size() != length) {
				return false;
			}

			byte[] buffer = new byte[1024];

			ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);

			int bufferIndex = 0;
			int bufferLength = -1;

			while (((bufferLength = fileChannel.read(byteBuffer)) > 0) &&
				   (bufferIndex < length)) {

				for (int i = 0; i < bufferLength; i++) {
					if (buffer[i] != bytes[bufferIndex++]) {
						return false;
					}
				}

				byteBuffer.clear();
			}

			if ((bufferIndex != length) || (bufferLength != -1)) {
				return false;
			}
			else {
				return true;
			}
		}
		catch (Exception e) {
			return false;
		}
		finally {
			if (fileChannel != null) {
				try {
					fileChannel.close();
				}
				catch (IOException ioe) {
				}
			}
		}
	}

	public boolean isSameContent(File file, String s) {
		ByteBuffer byteBuffer = CharsetEncoderUtil.encode(StringPool.UTF8, s);

		return isSameContent(file, byteBuffer.array(), byteBuffer.limit());
	}

	public String[] listDirs(File file) {
		List<String> dirs = new ArrayList<String>();

		File[] fileArray = file.listFiles();

		for (int i = 0; (fileArray != null) && (i < fileArray.length); i++) {
			if (fileArray[i].isDirectory()) {
				dirs.add(fileArray[i].getName());
			}
		}

		return dirs.toArray(new String[dirs.size()]);
	}

	public String[] listDirs(String fileName) {
		return listDirs(new File(fileName));
	}

	public String[] listFiles(File file) {
		List<String> files = new ArrayList<String>();

		File[] fileArray = file.listFiles();

		for (int i = 0; (fileArray != null) && (i < fileArray.length); i++) {
			if (fileArray[i].isFile()) {
				files.add(fileArray[i].getName());
			}
		}

		return files.toArray(new String[files.size()]);
	}

	public String[] listFiles(String fileName) {
		if (Validator.isNull(fileName)) {
			return new String[0];
		}

		return listFiles(new File(fileName));
	}

	public void mkdirs(String pathName) {
		File file = new File(pathName);

		file.mkdirs();
	}

	public boolean move(File source, File destination) {
		if (!source.exists()) {
			return false;
		}

		destination.delete();

		try {
			if (source.isDirectory()) {
				FileUtils.moveDirectory(source, destination);
			}
			else {
				FileUtils.moveFile(source, destination);
			}
		}
		catch (IOException ioe) {
			return false;
		}

		return true;
	}

	public boolean move(String sourceFileName, String destinationFileName) {
		return move(new File(sourceFileName), new File(destinationFileName));
	}

	public String read(File file) throws IOException {
		return read(file, false);
	}

	public String read(File file, boolean raw) throws IOException {
		byte[] bytes = getBytes(file);

		if (bytes == null) {
			return null;
		}

		String s = new String(bytes, StringPool.UTF8);

		if (raw) {
			return s;
		}
		else {
			return StringUtil.replace(
				s, StringPool.RETURN_NEW_LINE, StringPool.NEW_LINE);
		}
	}

	public String read(String fileName) throws IOException {
		return read(new File(fileName));
	}

	public String replaceSeparator(String fileName) {
		return StringUtil.replace(
			fileName, CharPool.BACK_SLASH, CharPool.SLASH);
	}

	public File[] sortFiles(File[] files) {
		if (files == null) {
			return null;
		}

		Arrays.sort(files, new FileComparator());

		List<File> directoryList = new ArrayList<File>();
		List<File> fileList = new ArrayList<File>();

		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				directoryList.add(files[i]);
			}
			else {
				fileList.add(files[i]);
			}
		}

		directoryList.addAll(fileList);

		return directoryList.toArray(new File[directoryList.size()]);
	}

	public String stripExtension(String fileName) {
		if (fileName == null) {
			return null;
		}

		String ext = getExtension(fileName);

		if (ext.length() > 0) {
			return fileName.substring(0, fileName.length() - ext.length() - 1);
		}
		else {
			return fileName;
		}
	}

	public List<String> toList(Reader reader) {
		List<String> list = new ArrayList<String>();

		try {
			UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(reader);

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				list.add(line);
			}

			unsyncBufferedReader.close();
		}
		catch (IOException ioe) {
		}

		return list;
	}

	public List<String> toList(String fileName) {
		try {
			return toList(new FileReader(fileName));
		}
		catch (IOException ioe) {
			return new ArrayList<String>();
		}
	}

	public Properties toProperties(FileInputStream fis) {
		Properties properties = new Properties();

		try {
			properties.load(fis);
		}
		catch (IOException ioe) {
		}

		return properties;
	}

	public Properties toProperties(String fileName) {
		try {
			return toProperties(new FileInputStream(fileName));
		}
		catch (IOException ioe) {
			return new Properties();
		}
	}

	public void touch(File file) throws IOException {
		FileUtils.touch(file);
	}

	public void touch(String fileName) throws IOException {
		touch(new File(fileName));
	}

	public void unzip(File source, File destination) {
		ExpandTask.expand(source, destination);
	}

	public void write(File file, byte[] bytes) throws IOException {
		write(file, bytes, 0, bytes.length);
	}

	public void write(File file, byte[] bytes, int offset, int length)
		throws IOException {

		mkdirsParentFile(file);

		FileOutputStream fileOutputStream = new FileOutputStream(file);

		fileOutputStream.write(bytes, offset, length);

		fileOutputStream.close();
	}

	public void write(File file, InputStream is) throws IOException {
		mkdirsParentFile(file);

		StreamUtil.transfer(is, new FileOutputStream(file));
	}

	public void write(File file, String s) throws IOException {
		write(file, s, false);
	}

	public void write(File file, String s, boolean lazy) throws IOException {
		write(file, s, lazy, false);
	}

	public void write(File file, String s, boolean lazy, boolean append)
		throws IOException {

		if (s == null) {
			return;
		}

		mkdirsParentFile(file);

		if (lazy && file.exists()) {
			String content = read(file);

			if (content.equals(s)) {
				return;
			}
		}

		Writer writer = new OutputStreamWriter(
			new FileOutputStream(file, append), StringPool.UTF8);

		writer.write(s);

		writer.close();
	}

	public void write(String fileName, byte[] bytes) throws IOException {
		write(new File(fileName), bytes);
	}

	public void write(String fileName, InputStream is) throws IOException {
		write(new File(fileName), is);
	}

	public void write(String fileName, String s) throws IOException {
		write(new File(fileName), s);
	}

	public void write(String fileName, String s, boolean lazy)
		throws IOException {

		write(new File(fileName), s, lazy);
	}

	public void write(String fileName, String s, boolean lazy, boolean append)
		throws IOException {

		write(new File(fileName), s, lazy, append);
	}

	public void write(String pathName, String fileName, String s)
		throws IOException {

		write(new File(pathName, fileName), s);
	}

	public void write(String pathName, String fileName, String s, boolean lazy)
		throws IOException {

		write(new File(pathName, fileName), s, lazy);
	}

	public void write(
			String pathName, String fileName, String s, boolean lazy,
			boolean append)
		throws IOException {

		write(new File(pathName, fileName), s, lazy, append);
	}

	protected void mkdirsParentFile(File file) {
		File parentFile = file.getParentFile();

		if (parentFile == null) {
			return;
		}

		try {
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
		}
		catch (SecurityException se) {

			// We may have the permission to write a specific file without
			// having the permission to check if the parent file exists

		}
	}

	private static final String[] _SAFE_FILE_NAME_1 = {
		StringPool.AMPERSAND, StringPool.CLOSE_PARENTHESIS,
		StringPool.OPEN_PARENTHESIS, StringPool.SEMICOLON
	};

	private static final String[] _SAFE_FILE_NAME_2 = {
		"_AMP_", "_CP_", "_OP_", "_SEM_"
	};

	private static Log _log = LogFactoryUtil.getLog(FileImpl.class);

	private static FileImpl _instance = new FileImpl();

	private static class ExtractTextProcessCallable
		implements ProcessCallable<String> {

		public ExtractTextProcessCallable(byte[] data) {
			_data = data;
		}

		public String call() throws ProcessException {
			Tika tika = new Tika();

			try {
				return tika.parseToString(
					new UnsyncByteArrayInputStream(_data));
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}
		}

		private static final long serialVersionUID = 1L;

		private byte[] _data;

	}

}