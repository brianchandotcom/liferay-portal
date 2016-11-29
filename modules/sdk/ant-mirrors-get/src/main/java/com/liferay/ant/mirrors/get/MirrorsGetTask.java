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

package com.liferay.ant.mirrors.get;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.URL;
import java.net.URLConnection;

import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipFile;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Checksum;

/**
 * @author Peter Yoo
 */
public class MirrorsGetTask extends Task {

	@Override
	public void execute() throws BuildException {
		try {
			doExecute();
		}
		catch (IOException ioe) {
			throw new BuildException(ioe);
		}
	}

	public void setDest(File dest) {
		_dest = dest;
	}

	public void setForce(boolean force) {
		_force = force;
	}

	public void setIgnoreErrors(boolean ignoreErrors) {
		_ignoreErrors = ignoreErrors;
	}

	public void setSrc(String src) {
		Matcher matcher = _pattern.matcher(src);

		if (!matcher.find()) {
			throw new RuntimeException("Invalid src attribute: " + src);
		}

		_fileName = matcher.group(2);
		_path = matcher.group(1);

		if (_path.startsWith("mirrors/")) {
			_path = _path.replace("mirrors/", _HOSTNAME);
		}

		while (_path.endsWith("/")) {
			_path = _path.substring(0, _path.length() - 1);
		}
	}

	public void setTryLocalNetwork(boolean tryLocalNetwork) {
		_tryLocalNetwork = tryLocalNetwork;
	}

	public void setVerbose(boolean verbose) {
		_verbose = verbose;
	}

	protected void copyFile(File sourceFile, File targetFile)
		throws IOException {

		StringBuilder sb = new StringBuilder();

		sb.append("Copying ");
		sb.append(sourceFile.getPath());
		sb.append(" to ");
		sb.append(targetFile.getPath());
		sb.append(".");

		System.out.println(sb.toString());

		URL sourceFileURL = new URL("file:" + sourceFile.getAbsolutePath());

		long time = System.currentTimeMillis();

		int size = toFile(sourceFileURL, targetFile);

		if (_verbose) {
			sb = new StringBuilder();

			sb.append("Copied ");
			sb.append(size);
			sb.append(" bytes in ");
			sb.append(System.currentTimeMillis() - time);
			sb.append(" milliseconds.");

			System.out.println(sb.toString());
		}
	}

	protected void doExecute() throws IOException {
		if (_tryLocalNetwork && _path.startsWith(_HOSTNAME)) {
			System.out.println(
				"The src attribute has an unneceessary reference to " +
					_HOSTNAME);

			_path = _path.substring(_HOSTNAME.length());

			while (_path.startsWith("/")) {
				_path = _path.substring(1);
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(System.getProperty("user.home"));
		sb.append(File.separator);
		sb.append(".liferay");
		sb.append(File.separator);
		sb.append("mirrors");
		sb.append(File.separator);
		sb.append(getPlatformIndependentPath(_path));

		File localCacheDir = new File(sb.toString());

		File localCacheFile = new File(localCacheDir, _fileName);

		if (localCacheFile.exists() && !_force && isZipFileName(_fileName)) {
			_force = !isValidZip(localCacheFile);
		}

		if (localCacheFile.exists() && _force) {
			localCacheFile.delete();
		}

		if (!localCacheFile.exists()) {
			URL sourceURL = null;

			if (_tryLocalNetwork) {
				sb = new StringBuilder();

				sb.append("http://");
				sb.append(_HOSTNAME);
				sb.append("/");
				sb.append(_path);
				sb.append("/");
				sb.append(_fileName);

				sourceURL = new URL(sb.toString());

				try {
					downloadFile(sourceURL, localCacheFile);
				}
				catch (IOException ioe) {
					sb = new StringBuilder();

					sb.append("http://");
					sb.append(_path);
					sb.append("/");
					sb.append(_fileName);

					sourceURL = new URL(sb.toString());

					downloadFile(sourceURL, localCacheFile);
				}
			}
			else {
				sb = new StringBuilder();

				sb.append("http://");
				sb.append(_path);
				sb.append("/");
				sb.append(_fileName);

				sourceURL = new URL(sb.toString());

				downloadFile(sourceURL, localCacheFile);
			}
		}

		if (_dest.exists() && _dest.isDirectory()) {
			copyFile(localCacheFile, new File(_dest, _fileName));
		}
		else {
			copyFile(localCacheFile, _dest);
		}
	}

	protected void downloadFile(URL sourceURL, File targetFile)
		throws IOException {

		StringBuilder sb = new StringBuilder();

		sb.append("Downloading ");
		sb.append(sourceURL.toExternalForm());
		sb.append(" to ");
		sb.append(targetFile.getPath());
		sb.append(".");

		System.out.println(sb.toString());

		long time = System.currentTimeMillis();

		int size = 0;

		try {
			size = toFile(sourceURL, targetFile);
		}
		catch (IOException ioe) {
			if (!_ignoreErrors) {
				throw ioe;
			}
			else {
				ioe.printStackTrace();
			}
		}

		if (_verbose) {
			sb = new StringBuilder();

			sb.append("Downloaded ");
			sb.append(sourceURL.toExternalForm());
			sb.append(". ");
			sb.append(size);
			sb.append(" bytes in ");
			sb.append(System.currentTimeMillis() - time);
			sb.append(" milliseconds.");

			System.out.println(sb.toString());
		}

		if (!isValidMD5(
				targetFile, new URL(sourceURL.toExternalForm() + ".md5"))) {

			targetFile.delete();

			throw new IOException(
				targetFile.getAbsolutePath() + " failed checksum.");
		}

		if (isZipFileName(targetFile.getName()) && !isValidZip(targetFile)) {
			targetFile.delete();

			throw new IOException(
				targetFile.getAbsolutePath() + " is an invalid zip file.");
		}
	}

	protected String getPlatformIndependentPath(String path) {
		String[] separators = {"/", "\\"};

		for (String separator : separators) {
			if (!separator.equals(File.separator)) {
				path = path.replace(separator, File.separator);
			}
		}

		return path;
	}

	protected boolean isValidMD5(File file, URL url) throws IOException {
		if ((file == null) || !file.exists()) {
			return false;
		}

		String remoteMD5 = null;

		try {
			remoteMD5 = toString(url);
		}
		catch (FileNotFoundException fnfe) {
			if (_verbose) {
				System.out.println("URL does not point to a valid MD5 file.");
			}

			return true;
		}

		Checksum checksum = new Checksum();

		checksum.setAlgorithm("MD5");
		checksum.setFile(file);
		checksum.setProject(new Project());
		checksum.setProperty("md5");

		checksum.execute();

		Project project = checksum.getProject();

		String localMD5 = project.getProperty("md5");

		return remoteMD5.contains(localMD5);
	}

	protected boolean isValidZip(File file) throws IOException {
		if (!file.exists()) {
			return false;
		}

		ZipFile zipFile = null;

		try {
			zipFile = new ZipFile(file, ZipFile.OPEN_READ);

			int count = 0;

			Enumeration<?> enumeration = zipFile.entries();

			while (enumeration.hasMoreElements()) {
				enumeration.nextElement();

				count++;
			}

			StringBuilder sb = new StringBuilder();

			sb.append(file.getPath());
			sb.append(" is a valid zip file with ");
			sb.append(count);
			sb.append(" entries.");

			System.out.println(sb.toString());

			return true;
		}
		catch (IOException ioe) {
			System.out.println(file.getPath() + " is an invalid zip file.");

			return false;
		}
		finally {
			if (zipFile != null) {
				zipFile.close();
			}
		}
	}

	protected boolean isZipFileName(String fileName) {
		if (fileName.endsWith(".ear") || fileName.endsWith(".jar") ||
			fileName.endsWith(".war") || fileName.endsWith(".zip")) {

			return true;
		}

		return false;
	}

	protected int toFile(URL url, File file) throws IOException {
		if (file.exists()) {
			file.delete();
		}

		File dir = file.getParentFile();

		if ((dir != null) && !dir.exists()) {
			dir.mkdirs();
		}

		OutputStream outputStream = new FileOutputStream(file);

		try {
			return toOutputStream(url, outputStream);
		}
		catch (IOException ioe) {
			if (file.exists()) {
				file.delete();
			}

			throw ioe;
		}
		finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}

	protected int toOutputStream(URL url, OutputStream outputStream)
		throws IOException {

		URLConnection urlConnection = url.openConnection();

		InputStream inputStream = urlConnection.getInputStream();

		try {
			byte[] bytes = new byte[1024 * 16];
			int read = 0;
			int size = 0;
			long time = System.currentTimeMillis();

			while ((read = inputStream.read(bytes)) > 0) {
				outputStream.write(bytes, 0, read);
				size += read;

				if (_verbose && ((System.currentTimeMillis() - time) > 100)) {
					System.out.print(".");

					time = System.currentTimeMillis();
				}
			}

			if (_verbose) {
				System.out.println("\n");
			}

			return size;
		}
		finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}

	protected String toString(URL url) throws IOException {
		OutputStream outputStream = new ByteArrayOutputStream();

		try {
			toOutputStream(url, outputStream);

			return outputStream.toString();
		}
		finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}

	private static final String _HOSTNAME = "mirrors.lax.liferay.com";

	private static final Pattern _pattern = Pattern.compile(
		"https?://(.+/)(.+)");

	private File _dest;
	private String _fileName;
	private boolean _force;
	private boolean _ignoreErrors;
	private String _path;
	private boolean _tryLocalNetwork = true;
	private boolean _verbose;

}