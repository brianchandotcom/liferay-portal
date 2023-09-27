/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import java.util.List;
import java.util.Properties;

/**
 * @author Iván Zaera
 */
public class MockFile implements com.liferay.portal.kernel.util.File {

	@Override
	public String appendParentheticalSuffix(String fileName, String suffix) {
		return null;
	}

	@Override
	public String appendSuffix(String fileName, String suffix) {
		return null;
	}

	@Override
	public void copyDirectory(File source, File destination) {
	}

	@Override
	public void copyDirectory(String sourceDirName, String destinationDirName) {
	}

	@Override
	public void copyFile(File source, File destination) {
	}

	@Override
	public void copyFile(String source, String destination) {
	}

	@Override
	public File createTempFile() {
		return null;
	}

	@Override
	public File createTempFile(byte[] bytes) {
		return null;
	}

	@Override
	public File createTempFile(InputStream inputStream) {
		return null;
	}

	@Override
	public File createTempFile(String extension) {
		return null;
	}

	@Override
	public File createTempFile(String prefix, String extension) {
		return null;
	}

	@Override
	public String createTempFileName() {
		return null;
	}

	@Override
	public String createTempFileName(String extension) {
		return null;
	}

	@Override
	public String createTempFileName(String prefix, String extension) {
		return null;
	}

	@Override
	public File createTempFolder() {
		return null;
	}

	@Override
	public String decodeSafeFileName(String fileName) {
		return null;
	}

	@Override
	public boolean delete(File file) {
		return false;
	}

	@Override
	public boolean delete(String file) {
		return false;
	}

	@Override
	public void deltree(File directory) {
	}

	@Override
	public void deltree(String directory) {
	}

	@Override
	public String encodeSafeFileName(String fileName) {
		return null;
	}

	@Override
	public boolean exists(File file) {
		return false;
	}

	@Override
	public boolean exists(String fileName) {
		return false;
	}

	@Override
	public String getAbsolutePath(File file) {
		return null;
	}

	@Override
	public byte[] getBytes(Class<?> clazz, String fileName) {
		return new byte[0];
	}

	@Override
	public byte[] getBytes(File file) {
		return new byte[0];
	}

	@Override
	public byte[] getBytes(InputStream inputStream) {
		return new byte[0];
	}

	@Override
	public byte[] getBytes(InputStream inputStream, int bufferSize) {
		return new byte[0];
	}

	@Override
	public byte[] getBytes(
		InputStream inputStream, int bufferSize, boolean cleanUpStream) {

		return new byte[0];
	}

	@Override
	public String getExtension(String fileName) {
		return null;
	}

	@Override
	public String getMD5Checksum(File file) {
		return null;
	}

	@Override
	public String getPath(String fullFileName) {
		return null;
	}

	@Override
	public String getShortFileName(String fullFileName) {
		return null;
	}

	@Override
	public boolean isSameContent(File file, byte[] bytes, int length) {
		return false;
	}

	@Override
	public boolean isSameContent(File file, String s) {
		return false;
	}

	@Override
	public String[] listDirs(File file) {
		return new String[0];
	}

	@Override
	public String[] listDirs(String fileName) {
		return new String[0];
	}

	@Override
	public String[] listFiles(File file) {
		return new String[0];
	}

	@Override
	public String[] listFiles(String fileName) {
		return new String[0];
	}

	@Override
	public void mkdirs(File file) {
	}

	@Override
	public void mkdirs(String pathName) {
	}

	@Override
	public boolean move(File source, File destination) {
		return false;
	}

	@Override
	public boolean move(String sourceFileName, String destinationFileName) {
		return false;
	}

	@Override
	public String read(File file) {
		return null;
	}

	@Override
	public String read(File file, boolean raw) {
		return null;
	}

	@Override
	public String read(String fileName) {
		return null;
	}

	@Override
	public String replaceSeparator(String fileName) {
		return null;
	}

	@Override
	public File[] sortFiles(File[] files) {
		return new File[0];
	}

	@Override
	public String stripExtension(String fileName) {
		return null;
	}

	@Override
	public String stripParentheticalSuffix(String fileName) {
		return null;
	}

	@Override
	public List<String> toList(Reader reader) {
		return null;
	}

	@Override
	public List<String> toList(String fileName) {
		return null;
	}

	@Override
	public Properties toProperties(FileInputStream fileInputStream) {
		return null;
	}

	@Override
	public Properties toProperties(String fileName) {
		return null;
	}

	@Override
	public void touch(File file) {
	}

	@Override
	public void touch(String fileName) {
	}

	@Override
	public void unzip(File source, File destination) {
	}

	@Override
	public void write(File file, byte[] bytes) {
	}

	@Override
	public void write(File file, byte[] bytes, boolean append)
		throws IOException {

		File parentFile = file.getParentFile();

		parentFile.mkdirs();

		try (FileOutputStream fileOutputStream = new FileOutputStream(
				file, append)) {

			fileOutputStream.write(bytes);
		}
	}

	@Override
	public void write(File file, byte[] bytes, int offset, int length) {
	}

	@Override
	public void write(
		File file, byte[] bytes, int offset, int length, boolean append) {
	}

	@Override
	public void write(File file, InputStream inputStream) {
	}

	@Override
	public void write(File file, String s) {
	}

	@Override
	public void write(File file, String s, boolean lazy) {
	}

	@Override
	public void write(File file, String s, boolean lazy, boolean append) {
	}

	@Override
	public void write(String fileName, byte[] bytes) {
	}

	@Override
	public void write(String fileName, InputStream inputStream) {
	}

	@Override
	public void write(String fileName, String s) {
	}

	@Override
	public void write(String fileName, String s, boolean lazy) {
	}

	@Override
	public void write(String fileName, String s, boolean lazy, boolean append) {
	}

	@Override
	public void write(String pathName, String fileName, String s) {
	}

	@Override
	public void write(
		String pathName, String fileName, String s, boolean lazy) {
	}

	@Override
	public void write(
		String pathName, String fileName, String s, boolean lazy,
		boolean append) {
	}

}