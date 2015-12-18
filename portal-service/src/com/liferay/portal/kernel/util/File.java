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

package com.liferay.portal.kernel.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import java.util.List;
import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public interface File {

	public String appendParentheticalSuffix(String fileName, String suffix);

	public String appendSuffix(String fileName, String suffix);

	public void copyDirectory(java.io.File source, java.io.File destination)
		throws IOException;

	public void copyDirectory(String sourceDirName, String destinationDirName)
		throws IOException;

	public void copyFile(java.io.File source, java.io.File destination)
		throws IOException;

	public void copyFile(
			java.io.File source, java.io.File destination, boolean lazy)
		throws IOException;

	public void copyFile(String source, String destination) throws IOException;

	public void copyFile(String source, String destination, boolean lazy)
		throws IOException;

	public java.io.File createTempFile();

	public java.io.File createTempFile(byte[] bytes) throws IOException;

	public java.io.File createTempFile(InputStream is) throws IOException;

	public java.io.File createTempFile(String extension);

	public java.io.File createTempFile(String prefix, String extension);

	public String createTempFileName();

	public String createTempFileName(String extension);

	public String createTempFileName(String prefix, String extension);

	public java.io.File createTempFolder() throws IOException;

	public String decodeSafeFileName(String fileName);

	public boolean delete(java.io.File file);

	public boolean delete(String file);

	public void deltree(java.io.File directory);

	public void deltree(String directory);

	public String encodeSafeFileName(String fileName);

	public boolean exists(java.io.File file);

	public boolean exists(String fileName);

	public String extractText(InputStream is, String fileName);

	public String extractText(
		InputStream is, String fileName, int maxStringLength);

	public String[] find(String directory, String includes, String excludes);

	public String getAbsolutePath(java.io.File file);

	public byte[] getBytes(Class<?> clazz, String fileName) throws IOException;

	public byte[] getBytes(InputStream is) throws IOException;

	public byte[] getBytes(InputStream is, int bufferSize) throws IOException;

	public byte[] getBytes(
			InputStream inputStream, int bufferSize, boolean cleanUpStream)
		throws IOException;

	public byte[] getBytes(java.io.File file) throws IOException;

	public String getExtension(String fileName);

	public String getMD5Checksum(java.io.File file) throws IOException;

	public String getPath(String fullFileName);

	public String getShortFileName(String fullFileName);

	public boolean isAscii(java.io.File file) throws IOException;

	public boolean isSameContent(java.io.File file, byte[] bytes, int length);

	public boolean isSameContent(java.io.File file, String s);

	public String[] listDirs(java.io.File file);

	public String[] listDirs(String fileName);

	public String[] listFiles(java.io.File file);

	public String[] listFiles(String fileName);

	public void mkdirs(java.io.File file) throws IOException;

	public void mkdirs(String pathName);

	public boolean move(java.io.File source, java.io.File destination);

	public boolean move(String sourceFileName, String destinationFileName);

	public String read(java.io.File file) throws IOException;

	public String read(java.io.File file, boolean raw) throws IOException;

	public String read(String fileName) throws IOException;

	public String replaceSeparator(String fileName);

	public java.io.File[] sortFiles(java.io.File[] files);

	public String stripExtension(String fileName);

	public String stripParentheticalSuffix(String fileName);

	public List<String> toList(Reader reader);

	public List<String> toList(String fileName);

	public Properties toProperties(java.io.FileInputStream fis);

	public Properties toProperties(String fileName);

	public void touch(java.io.File file) throws IOException;

	public void touch(String fileName) throws IOException;

	public void unzip(java.io.File source, java.io.File destination);

	public void write(java.io.File file, byte[] bytes) throws IOException;

	public void write(java.io.File file, byte[] bytes, boolean append)
		throws IOException;

	public void write(java.io.File file, byte[] bytes, int offset, int length)
		throws IOException;

	public void write(
			java.io.File file, byte[] bytes, int offset, int length,
			boolean append)
		throws IOException;

	public void write(java.io.File file, InputStream is) throws IOException;

	public void write(java.io.File file, String s) throws IOException;

	public void write(java.io.File file, String s, boolean lazy)
		throws IOException;

	public void write(java.io.File file, String s, boolean lazy, boolean append)
		throws IOException;

	public void write(String fileName, byte[] bytes) throws IOException;

	public void write(String fileName, InputStream is) throws IOException;

	public void write(String fileName, String s) throws IOException;

	public void write(String fileName, String s, boolean lazy)
		throws IOException;

	public void write(String fileName, String s, boolean lazy, boolean append)
		throws IOException;

	public void write(String pathName, String fileName, String s)
		throws IOException;

	public void write(String pathName, String fileName, String s, boolean lazy)
		throws IOException;

	public void write(
			String pathName, String fileName, String s, boolean lazy,
			boolean append)
		throws IOException;

}