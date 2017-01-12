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

package com.liferay.portal.tools.bundle.support.internal.util;

import com.liferay.portal.tools.bundle.support.BundleSupport;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.URI;
import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.RedirectLocations;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * @author Andrea Di Giorgi
 * @author David Truong
 */
public class FileUtil {

	public static void appendTar(File entryFile, Path entryPath, File tarFile)
		throws IOException {

		try (TarArchiveOutputStream tarArchiveOutputStream =
				new TarArchiveOutputStream(
					new GzipCompressorOutputStream(
						new BufferedOutputStream(
							new FileOutputStream(tarFile))))) {

			_appendTar(entryFile, entryPath, tarArchiveOutputStream);
		}
	}

	public static void appendZip(File entryFile, Path entryPath, File zipFile)
		throws Exception {

		try (FileSystem fileSystem = _createFileSystem(zipFile, false)) {
			_appendZip(entryFile, entryPath, fileSystem);
		}
	}

	public static void copyDirectory(File sourceFile, File destinationFile)
		throws IOException {

		_copyDirectory(sourceFile.toPath(), destinationFile.toPath());
	}

	public static void copyFile(File sourceFile, File destinationFile)
		throws IOException {

		_copyFile(sourceFile.toPath(), destinationFile.toPath());
	}

	public static void deleteDirectory(Path dirPath) throws IOException {
		Files.walkFileTree(
			dirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult postVisitDirectory(
						Path path, IOException ioe)
					throws IOException {

					Files.delete(path);

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Files.delete(path);

					return FileVisitResult.CONTINUE;
				}

			});
	}

	public static File downloadFile(
			URI uri, String userName, String password, File cacheDir)
		throws Exception {

		File file;

		try (CloseableHttpClient closeableHttpClient = _getHttpClient(
				uri, userName, password)) {

			HttpHead httpHead = new HttpHead(uri);

			HttpContext httpContext = new BasicHttpContext();

			String fileName = null;
			Date lastModifiedDate;

			try (CloseableHttpResponse closeableHttpResponse =
					closeableHttpClient.execute(httpHead, httpContext)) {

				_checkResponseStatus(closeableHttpResponse);

				Header dispositionHeader = closeableHttpResponse.getFirstHeader(
					"Content-Disposition");

				if (dispositionHeader != null) {
					String dispositionValue = dispositionHeader.getValue();

					int index = dispositionValue.indexOf("filename=");

					if (index > 0) {
						fileName = dispositionValue.substring(
							index + 10, dispositionValue.length() - 1);
					}
				}
				else {
					RedirectLocations redirectLocations = (RedirectLocations)
						httpContext.getAttribute(
							HttpClientContext.REDIRECT_LOCATIONS);

					if (redirectLocations != null) {
						uri = redirectLocations.get(
							redirectLocations.size() - 1);
					}
				}

				Header lastModifiedHeader =
					closeableHttpResponse.getFirstHeader("Last-Modified");

				if (lastModifiedHeader != null) {
					String lastModifiedValue = lastModifiedHeader.getValue();

					DateFormat dateFormat = new SimpleDateFormat(
						"EEE, dd MMM yyyy HH:mm:ss zzz");

					lastModifiedDate = dateFormat.parse(lastModifiedValue);
				}
				else {
					lastModifiedDate = new Date();
				}
			}

			if (fileName == null) {
				String path = uri.getPath();

				fileName = path.substring(path.lastIndexOf('/') + 1);
			}

			file = new File(cacheDir, fileName);

			if (file.exists() &&
				(file.lastModified() == lastModifiedDate.getTime())) {

				return file;
			}
			else if (file.exists()) {
				file.delete();
			}

			if (!cacheDir.exists()) {
				cacheDir.mkdirs();
			}

			file.createNewFile();

			HttpGet httpGet = new HttpGet(uri);

			try (CloseableHttpResponse closeableHttpResponse =
					closeableHttpClient.execute(httpGet);
				FileOutputStream fileOutputStream = new FileOutputStream(
					file)) {

				_checkResponseStatus(closeableHttpResponse);

				HttpEntity httpEntity = closeableHttpResponse.getEntity();

				fileOutputStream.write(EntityUtils.toByteArray(httpEntity));

				file.setLastModified(lastModifiedDate.getTime());
			}
		}

		return file;
	}

	public static String getExtension(String fileName) {
		int pos = fileName.lastIndexOf('.');

		if (pos == -1) {
			return "";
		}

		return fileName.substring(pos + 1);
	}

	public static String getFileName(String path) {
		String fileName = path.substring(path.lastIndexOf('/') + 1);

		fileName = fileName.substring(fileName.lastIndexOf('\\') + 1);

		return fileName;
	}

	public static File getJarFile() throws Exception {
		ProtectionDomain protectionDomain =
			BundleSupport.class.getProtectionDomain();

		CodeSource codeSource = protectionDomain.getCodeSource();

		URL url = codeSource.getLocation();

		return new File(url.toURI());
	}

	public static void tar(Path sourcePath, File tarFile, boolean includeFolder)
		throws Exception {

		final Path parentPath;

		if (includeFolder) {
			parentPath = sourcePath.getParent();
		}
		else {
			parentPath = sourcePath;
		}

		try (TarArchiveOutputStream tarArchiveOutputStream =
				new TarArchiveOutputStream(
					new GzipCompressorOutputStream(
						new BufferedOutputStream(
							new FileOutputStream(tarFile))))) {

			Files.walkFileTree(
				sourcePath,
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult visitFile(
							Path path, BasicFileAttributes basicFileAttributes)
						throws IOException {

						Path entryPath = parentPath.relativize(path);

						_appendTar(
							path.toFile(), entryPath, tarArchiveOutputStream);

						return FileVisitResult.CONTINUE;
					}

				});
		}
	}

	public static void untar(
			File tarFile, Path destinationPath, int stripComponents)
		throws IOException {

		try (TarArchiveInputStream tarArchiveInputStream =
				new TarArchiveInputStream(
					new GzipCompressorInputStream(
						new FileInputStream(tarFile)))) {

			TarArchiveEntry tarArchiveEntry = null;

			while ((tarArchiveEntry =
						tarArchiveInputStream.getNextTarEntry()) != null) {

				if (tarArchiveEntry.isDirectory()) {
					continue;
				}

				Path entryPath = Paths.get(tarArchiveEntry.getName());

				entryPath = destinationPath.resolve(
					entryPath.subpath(
						stripComponents, entryPath.getNameCount()));

				Files.createDirectories(entryPath.getParent());

				Files.copy(tarArchiveInputStream, entryPath);

				Date lastModifiedDate = tarArchiveEntry.getLastModifiedDate();

				Files.setLastModifiedTime(
					entryPath, FileTime.fromMillis(lastModifiedDate.getTime()));
			}
		}
	}

	public static void unzip(
			File zipFile, final Path destinationPath, final int stripComponents)
		throws Exception {

		try (FileSystem fileSystem = _createFileSystem(zipFile, false)) {
			Files.walkFileTree(
				fileSystem.getPath("/"),
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult visitFile(
							Path path, BasicFileAttributes basicFileAttributes)
						throws IOException {

						Path entryPath = path.subpath(
							stripComponents, path.getNameCount());

						_copyFile(
							path, Paths.get(
								destinationPath.toString(),
								entryPath.toString()));

						return FileVisitResult.CONTINUE;
					}

				});
		}
	}

	public static void zip(
			Path sourcePath, final File zipFile, boolean includeFolder)
		throws Exception {

		final Path parentPath;

		if (includeFolder) {
			parentPath = sourcePath.getParent();
		}
		else {
			parentPath = sourcePath;
		}

		try (FileSystem fileSystem = _createFileSystem(zipFile, true)) {
			Files.walkFileTree(
				sourcePath,
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult visitFile(
							Path path, BasicFileAttributes basicFileAttributes)
						throws IOException {

						Path entryPath = parentPath.relativize(path);

						_copyFile(
							path, fileSystem.getPath(entryPath.toString()));

						return FileVisitResult.CONTINUE;
					}

				});
		}
	}

	private static void _appendTar(
			File entryFile, Path entryPath,
			TarArchiveOutputStream tarArchiveOutputStream)
		throws IOException {

		TarArchiveEntry tarArchiveEntry = new TarArchiveEntry(
			entryPath.toFile());

		tarArchiveEntry.setSize(entryFile.length());

		tarArchiveOutputStream.putArchiveEntry(tarArchiveEntry);

		IOUtils.copy(new FileInputStream(entryFile), tarArchiveOutputStream);

		tarArchiveOutputStream.closeArchiveEntry();
	}

	private static void _appendZip(
			File entryFile, Path entryPath, FileSystem fileSystem)
		throws IOException {

		Path zipPath = fileSystem.getPath(entryPath.toString());

		_copyFile(entryFile.toPath(), zipPath);
	}

	private static void _checkResponseStatus(HttpResponse httpResponse)
		throws IOException {

		StatusLine statusLine = httpResponse.getStatusLine();

		if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
			throw new IOException(statusLine.getReasonPhrase());
		}
	}

	private static void _copyDirectory(
			final Path sourcePath, final Path destinationPath)
		throws IOException {

		Files.walkFileTree(
			sourcePath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					_copyFile(
						path,
						destinationPath.resolve(sourcePath.relativize(path)));

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private static void _copyFile(Path sourcePath, Path destinationPath)
		throws IOException {

		Files.createDirectories(destinationPath);

		Files.copy(
			sourcePath.toAbsolutePath(), destinationPath,
			StandardCopyOption.REPLACE_EXISTING);

		Files.setLastModifiedTime(
			destinationPath, Files.getLastModifiedTime(sourcePath));
	}

	private static FileSystem _createFileSystem(File file, boolean create)
		throws Exception {

		Map<String, String> properties = new HashMap<>();

		properties.put("create", Boolean.toString(create));
		properties.put("encoding", StandardCharsets.UTF_8.name());

		URI uri = file.toURI();

		return FileSystems.newFileSystem(
			new URI("jar:" + uri.getScheme(), uri.getPath(), null), properties);
	}

	private static CloseableHttpClient _getHttpClient(
		URI uri, String userName, String password) {

		HttpClientBuilder httpClientBuilder = HttpClients.custom();

		CredentialsProvider credentialsProvider =
			new BasicCredentialsProvider();

		httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);

		RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();

		requestConfigBuilder.setCookieSpec(CookieSpecs.STANDARD);
		requestConfigBuilder.setRedirectsEnabled(true);

		httpClientBuilder.setDefaultRequestConfig(requestConfigBuilder.build());

		if ((userName != null) && (password != null)) {
			credentialsProvider.setCredentials(
				new AuthScope(uri.getHost(), uri.getPort()),
				new UsernamePasswordCredentials(userName, password));
		}

		String scheme = uri.getScheme();

		String proxyHost = System.getProperty(scheme + ".proxyHost");
		String proxyPort = System.getProperty(scheme + ".proxyPort");
		String proxyUser = System.getProperty(scheme + ".proxyUser");
		String proxyPassword = System.getProperty(scheme + ".proxyPassword");

		if ((proxyHost != null) && (proxyPort != null) && (proxyUser != null) &&
			(proxyPassword != null)) {

			credentialsProvider.setCredentials(
				new AuthScope(proxyHost, Integer.parseInt(proxyPort)),
				new UsernamePasswordCredentials(proxyUser, proxyPassword));
		}

		httpClientBuilder.useSystemProperties();

		return httpClientBuilder.build();
	}

}