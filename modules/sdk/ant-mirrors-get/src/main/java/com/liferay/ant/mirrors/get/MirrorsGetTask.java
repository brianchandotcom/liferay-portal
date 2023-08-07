/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ant.mirrors.get;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import java.util.Base64;
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
		catch (IOException ioException) {
			throw new BuildException(ioException);
		}
	}

	public void setDest(File dest) {
		String destPath = dest.getPath();

		if (destPath.matches(".*\\$\\{.+\\}.*")) {
			Project project = getProject();

			_dest = new File(project.replaceProperties(destPath));
		}
		else {
			_dest = dest;
		}
	}

	public void setForce(boolean force) {
		_force = force;
	}

	public void setIgnoreErrors(boolean ignoreErrors) {
		_ignoreErrors = ignoreErrors;
	}

	public void setPassword(String password) {
		if (_password == null) {
			_password = password;
		}
	}

	public void setRetries(int retries) {
		_retries = retries;
	}

	public void setSkipChecksum(boolean skipChecksum) {
		_skipChecksum = skipChecksum;
	}

	public void setSrc(String src) {
		Matcher matcher = _basicAuthenticationURLPattern.matcher(src);

		if (matcher.matches()) {
			_username = matcher.group(2);
			_password = matcher.group(3);

			src = matcher.group(1) + matcher.group(4);
		}

		Project project = getProject();

		_src = project.replaceProperties(src);

		if (_src.startsWith("file:")) {
			return;
		}

		matcher = _srcPattern.matcher(_src);

		if (!matcher.find()) {
			throw new RuntimeException("Invalid src attribute: " + _src);
		}

		_fileName = matcher.group(2);

		_path = matcher.group(1);

		if (_path.startsWith("mirrors/")) {
			_path = _path.replaceFirst("mirrors", getMirrorsHostname());
		}

		while (_path.endsWith("/")) {
			_path = _path.substring(0, _path.length() - 1);
		}
	}

	public void setSSL(boolean ssl) {
		_ssl = ssl;
	}

	public void setTryLocalNetwork(boolean tryLocalNetwork) {
		_tryLocalNetwork = tryLocalNetwork;
	}

	public void setUsername(String username) {
		if (_username == null) {
			_username = username;
		}
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

		URI sourceFileURI = sourceFile.toURI();

		long time = System.currentTimeMillis();

		int size = toFile(sourceFileURI.toURL(), targetFile);

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
		if (_src.startsWith("file:")) {
			File srcFile = new File(_src.substring("file:".length()));

			File targetFile = _dest;

			if (_dest.exists() && _dest.isDirectory()) {
				targetFile = new File(_dest, srcFile.getName());
			}

			copyFile(srcFile, targetFile);

			return;
		}

		Matcher matcher = _mirrorsHostNamePattern.matcher(_path);

		if (_tryLocalNetwork && matcher.find()) {
			String hostname = matcher.group();

			System.out.println(
				"The src attribute has an unnecessary reference to " +
					hostname);

			_path = _path.substring(hostname.length());

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
			String mirrorsHostname = getMirrorsHostname();

			if (_tryLocalNetwork && !mirrorsHostname.isEmpty()) {
				sb = new StringBuilder();

				sb.append(getURLScheme());
				sb.append(mirrorsHostname);
				sb.append("/");
				sb.append(_path);
				sb.append("/");
				sb.append(_fileName);

				URL sourceURL = new URL(sb.toString());

				try {
					downloadFile(sourceURL, localCacheFile, _retries);
				}
				catch (IOException ioException) {
					URL defaultURL = new URL(_src);

					System.out.println(
						"Unable to connect to " + sourceURL +
							", defaulting to " + defaultURL);

					downloadFile(defaultURL, localCacheFile);
				}
			}
			else {
				downloadFile(new URL(_src), localCacheFile);
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

		downloadFile(sourceURL, targetFile, 0);
	}

	protected void downloadFile(URL sourceURL, File targetFile, int retries)
		throws IOException {

		if (retries > 0) {
			for (int i = 0; i < retries; i++) {
				try {
					_downloadFile(sourceURL, targetFile);

					return;
				}
				catch (IOException ioException) {
					System.out.println(
						"Unable to connect to " + sourceURL +
							", will retry in 30 seconds.");

					try {
						Thread.sleep(30000);
					}
					catch (InterruptedException interruptedException) {
						interruptedException.printStackTrace();
					}
				}
			}
		}

		_downloadFile(sourceURL, targetFile);
	}

	protected String getMirrorsHostname() {
		if (_mirrorsHostname != null) {
			return _mirrorsHostname;
		}

		Project project = getProject();

		_mirrorsHostname = project.getProperty("mirrors.hostname");

		if (_mirrorsHostname == null) {
			_mirrorsHostname = "";
		}

		return _mirrorsHostname;
	}

	protected String getPassword() {
		if (_password != null) {
			return _password;
		}

		Project project = getProject();

		_password = project.getProperty("mirrors.password");

		return _password;
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

	protected String getProcessOutput(String[] commands)
		throws IOException, RuntimeException {

		ProcessBuilder processBuilder = new ProcessBuilder(commands);

		Process process = processBuilder.start();

		StringBuilder processOutput = new StringBuilder();

		try {
			BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(process.getInputStream()));

			String line = bufferedReader.readLine();

			while (line != null) {
				processOutput.append(line + System.lineSeparator());
				line = bufferedReader.readLine();
			}

			process.waitFor();
		}
		catch (Exception exception) {
			System.out.println("Unable to get process output.");

			exception.printStackTrace();
		}

		return processOutput.toString();
	}

	protected String getURLScheme() {
		Project project = getProject();

		boolean ssl = _ssl;

		String mirrorsSSL = project.getProperty("mirrors.ssl");

		if ((mirrorsSSL != null) && !mirrorsSSL.isEmpty()) {
			ssl = Boolean.parseBoolean(mirrorsSSL);
		}

		if (ssl) {
			return "https://";
		}

		return "http://";
	}

	protected String getUserAgent() {
		if (_userAgent != null) {
			return _userAgent;
		}

		Project project = getProject();

		_userAgent = project.getProperty("mirrors.user.agent");

		return _userAgent;
	}

	protected String getUsername() {
		if (_username != null) {
			return _username;
		}

		Project project = getProject();

		_username = project.getProperty("mirrors.username");

		return _username;
	}

	protected boolean has7z() {
		String[] command = {"/bin/bash", "-c", "type 7z"};

		try {
			String processOutput = getProcessOutput(command);

			if (processOutput.contains("not found")) {
				System.out.println("Unable to validate 7z file");

				return false;
			}
		}
		catch (Exception exception) {
			System.out.println("Unable to validate 7z file");

			return false;
		}

		return true;
	}

	protected boolean is7ZArchiveValid(File file) {
		if (!has7z()) {
			return true;
		}

		String[] command = {"/bin/bash", "-c", "7z t " + file.toString()};

		try {
			String processOutput = getProcessOutput(command);

			if (!processOutput.contains("Everything is Ok") &&
				processOutput.contains("Files: 0\n")) {

				System.out.println(processOutput);

				System.out.println(file + " archive file is not valid.");

				return false;
			}
		}
		catch (Exception exception) {
			System.out.println(file + " archive file is not valid.");

			return false;
		}

		return true;
	}

	protected boolean is7ZFileName(String fileName) {
		if (fileName.endsWith(".7z")) {
			return true;
		}

		return false;
	}

	protected boolean isValidMD5(File file, URL url) throws IOException {
		if (_skipChecksum) {
			return true;
		}

		if ((file == null) || !file.exists()) {
			return false;
		}

		String remoteMD5 = null;

		try {
			remoteMD5 = toString(url);
		}
		catch (Exception exception) {
			if (_verbose) {
				System.out.println("Unable to access MD5 file");
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
		catch (IOException ioException) {
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

	protected URLConnection openConnection(URL url) throws IOException {
		URLConnection urlConnection = null;

		while (true) {
			urlConnection = url.openConnection();

			if (!(urlConnection instanceof HttpURLConnection)) {
				break;
			}

			HttpURLConnection httpURLConnection =
				(HttpURLConnection)urlConnection;

			String password = getPassword();
			String username = getUsername();

			if ((password != null) && (username != null)) {
				String auth = username + ":" + password;
				Base64.Encoder encoder = Base64.getEncoder();

				httpURLConnection.setRequestProperty(
					"Authorization",
					"Basic " + encoder.encodeToString(auth.getBytes()));
			}

			if (getUserAgent() != null) {
				httpURLConnection.setRequestProperty(
					"User-Agent", getUserAgent());
			}

			int responseCode = httpURLConnection.getResponseCode();

			if ((responseCode != HttpURLConnection.HTTP_MOVED_PERM) &&
				(responseCode != HttpURLConnection.HTTP_MOVED_TEMP)) {

				break;
			}

			url = new URL(httpURLConnection.getHeaderField("Location"));
		}

		return urlConnection;
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
		catch (IOException ioException) {
			if (file.exists()) {
				file.delete();
			}

			throw ioException;
		}
		finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}

	protected int toOutputStream(URL url, OutputStream outputStream)
		throws IOException {

		URLConnection urlConnection = openConnection(url);

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

	private void _downloadFile(URL sourceURL, File targetFile)
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
		catch (IOException ioException) {
			targetFile.delete();

			if (!_ignoreErrors) {
				throw ioException;
			}

			ioException.printStackTrace();
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

		if (is7ZFileName(targetFile.getName()) &&
			!is7ZArchiveValid(targetFile)) {

			targetFile.delete();

			throw new IOException(
				targetFile.getAbsolutePath() + " is an invalid 7z file.");
		}
	}

	private static final Pattern _basicAuthenticationURLPattern =
		Pattern.compile("(https?://)([^:]+):([^@]+)@(.+)");
	private static final Pattern _mirrorsHostNamePattern = Pattern.compile(
		"^mirrors\\.[^\\.]+\\.liferay.com/");
	private static final Pattern _srcPattern = Pattern.compile(
		"https?://(.+/)(.+)");

	private File _dest;
	private String _fileName;
	private boolean _force;
	private boolean _ignoreErrors;
	private String _mirrorsHostname;
	private String _password;
	private String _path;
	private int _retries = 1;
	private boolean _skipChecksum;
	private String _src;
	private boolean _ssl;
	private boolean _tryLocalNetwork = true;
	private String _userAgent;
	private String _username;
	private boolean _verbose;

}