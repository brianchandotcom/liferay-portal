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
			StringBuilder sb = new StringBuilder();

			sb.append(System.getProperty("user.home"));
			sb.append(File.separator);
			sb.append(".liferay");
			sb.append(File.separator);
			sb.append("mirrors");
			sb.append(File.separator);
			sb.append(_path);

			File localCacheDir = new File(sb.toString());

			File localCacheFile = new File(localCacheDir, _fileName);

			if (localCacheFile.exists() && !_force &&
				(_fileName.endsWith(".zip") || _fileName.endsWith(".jar") ||
				 _fileName.endsWith(".war") || _fileName.endsWith(".ear"))) {

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
					sb.append(_LOCAL_NETWORK_HOSTNAME);
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

			copyFile(localCacheFile, new File(_destinationDir, _fileName));
		}
		catch (IOException ioe) {
			throw new BuildException(ioe);
		}
	}

	public void setDest(File dest) {
		_destinationDir = dest;
	}

	public void setForce(boolean force) {
		_force = force;
	}

	public void setIgnoreErrors(boolean ignoreErrors) {
		_ignoreErrors = ignoreErrors;
	}

	public void setSrc(String src) {
		Matcher srcMatcher = _SRC_PATTERN.matcher(src);

		srcMatcher.find();

		_fileName = srcMatcher.group("fileName");
		_path = srcMatcher.group("path");
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

		System.out.println(sb.toString());

		URL sourceFileURL = new URL("file:" + sourceFile.getAbsolutePath());

		long start = System.currentTimeMillis();

		int totalBytes = toFile(sourceFileURL, targetFile);

		long duration = System.currentTimeMillis() - start;

		if (_verbose) {
			sb = new StringBuilder();

			sb.append("Copied ");
			sb.append(totalBytes);
			sb.append(" bytes in ");
			sb.append(duration);
			sb.append(" milliseconds.");

			System.out.println(sb.toString());
		}
	}

	protected void downloadFile(URL sourceURL, File targetFile)
		throws IOException {

		StringBuilder sb = new StringBuilder();

		sb.append("Downloading ");
		sb.append(sourceURL.toExternalForm());
		sb.append(" to ");
		sb.append(targetFile.getPath());

		System.out.println(sb.toString());

		int totalBytes = 0;

		long start = System.currentTimeMillis();

		try {
			totalBytes = toFile(sourceURL, targetFile);
		}
		catch (IOException ioe) {
			if (!_ignoreErrors) {
				throw ioe;
			}
			else {
				ioe.printStackTrace();
			}
		}

		long duration = System.currentTimeMillis() - start;

		if (_verbose) {
			sb = new StringBuilder();

			sb.append("Downloaded ");
			sb.append(sourceURL.toExternalForm());
			sb.append(". ");
			sb.append(totalBytes);
			sb.append(" bytes in ");
			sb.append(duration);
			sb.append(" milliseconds.");

			System.out.println(sb.toString());
		}

		if (!isValidMD5(
				targetFile, new URL(sourceURL.toExternalForm() + ".md5"))) {

			targetFile.delete();

			throw new IOException(
				targetFile.getAbsolutePath() + " failed checksum.");
		}
	}

	protected boolean isValidMD5(File file, URL md5URL) throws IOException {
		if ((file == null) || !file.exists()) {
			return false;
		}

		String theirMD5 = null;

		try {
			theirMD5 = toString(md5URL);
		}
		catch (FileNotFoundException fnfe) {
			if (_verbose) {
				System.out.println("md5 file not found.");
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

		String myMD5 = project.getProperty("md5");

		return theirMD5.contains(myMD5);
	}

	protected boolean isValidZip(File file) throws IOException {
		if (!file.exists()) {
			return false;
		}

		ZipFile zipFile = null;

		try {
			zipFile = new ZipFile(file, ZipFile.OPEN_READ);

			Enumeration<?> zipFileEntries = zipFile.entries();

			int totalEntries = 0;

			while (zipFileEntries.hasMoreElements()) {
				totalEntries++;

				zipFileEntries.nextElement();
			}

			StringBuilder sb = new StringBuilder();

			sb.append(file.getPath());
			sb.append(" is a valid zip file with ");
			sb.append(totalEntries);
			sb.append(" entries.");

			System.out.println(sb.toString());

			return true;
		}
		catch (IOException ioe) {
			System.out.println(file.getPath() + " is not a valid zip file.");

			return false;
		}
		finally {
			if (zipFile != null) {
				zipFile.close();
			}
		}
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

		URLConnection connection = url.openConnection();

		InputStream inputStream = connection.getInputStream();

		try {
			byte[] byteArray = new byte[1024 * 16];
			int bytesRead = 0;
			int totalBytes = 0;

			long intervalStart = System.currentTimeMillis();

			while ((bytesRead = inputStream.read(byteArray)) > 0) {
				outputStream.write(byteArray, 0, bytesRead);
				totalBytes += bytesRead;

				if (_verbose &&
					((System.currentTimeMillis() - intervalStart) > 100)) {

					System.out.print(".");

					intervalStart = System.currentTimeMillis();
				}
			}

			if (_verbose) {
				System.out.println("\n");
			}

			return totalBytes;
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

	private static final String _LOCAL_NETWORK_HOSTNAME =
		"mirrors.lax.liferay.com";

	private static final Pattern _SRC_PATTERN = Pattern.compile(
		"https?://(?<path>.+/)(?<fileName>.+)");

	/**/

	private File _destinationDir;

	private String _fileName;
	private boolean _force;
	private boolean _ignoreErrors;
	private String _path;
	private boolean _tryLocalNetwork = true;
	private boolean _verbose;

}