/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import java.util.List;
import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class FileUtil {

	public static String appendParentheticalSuffix(
		String fileName, String suffix) {

		return _file.appendParentheticalSuffix(fileName, suffix);
	}

	public static String appendSuffix(String fileName, String suffix) {
		return _file.appendSuffix(fileName, suffix);
	}

	public static void copyDirectory(File source, File destination)
		throws IOException {

		_file.copyDirectory(source, destination);
	}

	public static void copyDirectory(
			String sourceDirName, String destinationDirName)
		throws IOException {

		_file.copyDirectory(sourceDirName, destinationDirName);
	}

	public static void copyFile(File source, File destination)
		throws IOException {

		_file.copyFile(source, destination);
	}

	public static void copyFile(String source, String destination)
		throws IOException {

		_file.copyFile(source, destination);
	}

	public static File createTempFile() {
		return _file.createTempFile();
	}

	public static File createTempFile(byte[] bytes) throws IOException {
		return _file.createTempFile(bytes);
	}

	public static File createTempFile(InputStream inputStream)
		throws IOException {

		return _file.createTempFile(inputStream);
	}

	public static File createTempFile(String extension) {
		return _file.createTempFile(extension);
	}

	public static File createTempFile(String prefix, String extension) {
		return _file.createTempFile(prefix, extension);
	}

	public static String createTempFileName() {
		return _file.createTempFileName();
	}

	public static String createTempFileName(String extension) {
		return _file.createTempFileName(extension);
	}

	public static String createTempFileName(String prefix, String extension) {
		return _file.createTempFileName(prefix, extension);
	}

	public static File createTempFolder() throws IOException {
		return _file.createTempFolder();
	}

	public static String decodeSafeFileName(String fileName) {
		return _file.decodeSafeFileName(fileName);
	}

	public static boolean delete(File file) {
		return _file.delete(file);
	}

	public static boolean delete(String file) {
		return _file.delete(file);
	}

	public static void deltree(File directory) {
		_file.deltree(directory);
	}

	public static void deltree(String directory) {
		_file.deltree(directory);
	}

	public static String encodeSafeFileName(String fileName) {
		return _file.encodeSafeFileName(fileName);
	}

	public static boolean exists(File file) {
		return _file.exists(file);
	}

	public static boolean exists(String fileName) {
		return _file.exists(fileName);
	}

	public static String getAbsolutePath(File file) {
		return _file.getAbsolutePath(file);
	}

	public static byte[] getBytes(Class<?> clazz, String fileName)
		throws Exception {

		return _file.getBytes(clazz, fileName);
	}

	public static byte[] getBytes(File file) throws IOException {
		return _file.getBytes(file);
	}

	public static byte[] getBytes(InputStream inputStream) throws IOException {
		return _file.getBytes(inputStream);
	}

	public static byte[] getBytes(InputStream inputStream, int bufferSize)
		throws IOException {

		return _file.getBytes(inputStream);
	}

	public static byte[] getBytes(
			InputStream inputStream, int bufferSize, boolean cleanUpStream)
		throws IOException {

		return _file.getBytes(inputStream, bufferSize, cleanUpStream);
	}

	public static String getExtension(String fileName) {
		return _file.getExtension(fileName);
	}

	public static com.liferay.portal.kernel.util.File getFile() {
		return _file;
	}

	public static String getMD5Checksum(File file) throws IOException {
		return _file.getMD5Checksum(file);
	}

	public static String getPath(String fullFileName) {
		return _file.getPath(fullFileName);
	}

	public static String getShortFileName(String fullFileName) {
		return _file.getShortFileName(fullFileName);
	}

	public static boolean isSameContent(File file, byte[] bytes, int length) {
		return _file.isSameContent(file, bytes, length);
	}

	public static boolean isSameContent(File file, String s) {
		return _file.isSameContent(file, s);
	}

	public static String[] listDirs(File file) {
		return _file.listDirs(file);
	}

	public static String[] listDirs(String fileName) {
		return _file.listDirs(fileName);
	}

	public static String[] listFiles(File file) {
		return _file.listFiles(file);
	}

	public static String[] listFiles(String fileName) {
		return _file.listFiles(fileName);
	}

	public static void mkdirs(File file) {
		_file.mkdirs(file);
	}

	public static void mkdirs(String pathName) {
		_file.mkdirs(pathName);
	}

	public static boolean move(File source, File destination) {
		return _file.move(source, destination);
	}

	public static boolean move(
		String sourceFileName, String destinationFileName) {

		return _file.move(sourceFileName, destinationFileName);
	}

	public static String read(File file) throws IOException {
		return _file.read(file);
	}

	public static String read(File file, boolean raw) throws IOException {
		return _file.read(file, raw);
	}

	public static String read(String fileName) throws IOException {
		return _file.read(fileName);
	}

	public static String replaceSeparator(String fileName) {
		return _file.replaceSeparator(fileName);
	}

	public static File[] sortFiles(File[] files) {
		return _file.sortFiles(files);
	}

	public static String stripExtension(String fileName) {
		return _file.stripExtension(fileName);
	}

	public static String stripParentheticalSuffix(String fileName) {
		return _file.stripParentheticalSuffix(fileName);
	}

	public static List<String> toList(Reader reader) {
		return _file.toList(reader);
	}

	public static List<String> toList(String fileName) {
		return _file.toList(fileName);
	}

	public static Properties toProperties(FileInputStream fileInputStream) {
		return _file.toProperties(fileInputStream);
	}

	public static Properties toProperties(String fileName) {
		return _file.toProperties(fileName);
	}

	public static void touch(File file) throws IOException {
		_file.touch(file);
	}

	public static void touch(String fileName) throws IOException {
		_file.touch(fileName);
	}

	public static void unzip(File source, File destination) {
		_file.unzip(source, destination);
	}

	public static void write(File file, byte[] bytes) throws IOException {
		write(file, bytes, false);
	}

	public static void write(File file, byte[] bytes, boolean append)
		throws IOException {

		_file.write(file, bytes, append);
	}

	public static void write(File file, byte[] bytes, int offset, int length)
		throws IOException {

		write(file, bytes, offset, length, false);
	}

	public static void write(
			File file, byte[] bytes, int offset, int length, boolean append)
		throws IOException {

		_file.write(file, bytes, offset, length, append);
	}

	public static void write(File file, InputStream inputStream)
		throws IOException {

		_file.write(file, inputStream);
	}

	public static void write(File file, String s) throws IOException {
		_file.write(file, s);
	}

	public static void write(File file, String s, boolean lazy)
		throws IOException {

		_file.write(file, s, lazy);
	}

	public static void write(File file, String s, boolean lazy, boolean append)
		throws IOException {

		_file.write(file, s, lazy, append);
	}

	public static void write(String fileName, byte[] bytes) throws IOException {
		_file.write(fileName, bytes);
	}

	public static void write(String fileName, InputStream inputStream)
		throws IOException {

		_file.write(fileName, inputStream);
	}

	public static void write(String fileName, String s) throws IOException {
		_file.write(fileName, s);
	}

	public static void write(String fileName, String s, boolean lazy)
		throws IOException {

		_file.write(fileName, s, lazy);
	}

	public static void write(
			String fileName, String s, boolean lazy, boolean append)
		throws IOException {

		_file.write(fileName, s, lazy, append);
	}

	public static void write(String pathName, String fileName, String s)
		throws IOException {

		_file.write(pathName, fileName, s);
	}

	public static void write(
			String pathName, String fileName, String s, boolean lazy)
		throws IOException {

		_file.write(pathName, fileName, s, lazy);
	}

	public static void write(
			String pathName, String fileName, String s, boolean lazy,
			boolean append)
		throws IOException {

		_file.write(pathName, fileName, s, lazy, append);
	}

	public void setFile(com.liferay.portal.kernel.util.File file) {
		_file = file;
	}

	private static com.liferay.portal.kernel.util.File _file;

}