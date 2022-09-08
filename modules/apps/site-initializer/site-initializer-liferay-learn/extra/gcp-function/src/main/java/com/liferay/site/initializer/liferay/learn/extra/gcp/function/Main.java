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

package com.liferay.site.initializer.liferay.learn.extra.gcp.function;

import com.liferay.headless.delivery.client.dto.v1_0.ContentField;
import com.liferay.headless.delivery.client.dto.v1_0.ContentFieldValue;
import com.liferay.headless.delivery.client.dto.v1_0.Document;
import com.liferay.headless.delivery.client.dto.v1_0.DocumentFolder;
import com.liferay.headless.delivery.client.dto.v1_0.StructuredContent;
import com.liferay.headless.delivery.client.dto.v1_0.StructuredContentFolder;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.resource.v1_0.DocumentFolderResource;
import com.liferay.headless.delivery.client.resource.v1_0.DocumentResource;
import com.liferay.headless.delivery.client.resource.v1_0.StructuredContentFolderResource;
import com.liferay.headless.delivery.client.resource.v1_0.StructuredContentResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.File;
import java.io.InputStream;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import org.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Allen Ziegenfus
 */
public class Main {

	public static void main(String[] arguments) throws Exception {
		Properties properties = new Properties();

		try (InputStream inputStream = Main.class.getResourceAsStream(
				"dependencies/application.properties")) {

			properties.load(inputStream);
		}

		Main main = new Main(
			properties.getProperty("liferay.article.structure.id"),
			properties.getProperty("liferay.client.id"),
			properties.getProperty("liferay.client.secret"),
			properties.getProperty("liferay.url"),
			properties.getProperty("liferay.site.id"),
			properties.getProperty("sphinx.output.directory"));

		main.uploadToLiferay();
	}

	public Main(
		String liferayArticleStructureId, String liferayClientId,
		String liferayClientSecret, String liferayURL, String liferaySiteId,
		String sphinxOutputDirectory) {

		_liferayArticleStructureId = GetterUtil.getLong(
			liferayArticleStructureId);
		_liferayClientId = liferayClientId;
		_liferayClientSecret = liferayClientSecret;
		_liferayURL = liferayURL;
		_liferaySiteId = GetterUtil.getLong(liferaySiteId);
		_sphinxOutputDirectory = sphinxOutputDirectory;

		_logger = Logger.getLogger(Main.class.getName());

		try {
			LocalDateTime localDateTime = LocalDateTime.now(ZoneOffset.UTC);

			FileHandler fileHandler = new FileHandler(
				StringBundler.concat(
					localDateTime.getYear(), "-", localDateTime.getMonthValue(),
					"-", localDateTime.getDayOfMonth(), ".log"),
				true);

			fileHandler.setFormatter(new SimpleFormatter());

			_logger.addHandler(fileHandler);

			_addFileNames(_sphinxOutputDirectory);

			_initResourceBuilders(_getOAuthAuthorization());
		}
		catch (Exception exception) {
			_logger.log(Level.SEVERE, exception.getMessage(), exception);
		}
	}

	public void uploadToLiferay() throws Exception {
		for (String fileName : _fileNames) {
			if (!fileName.contains("/en/") || !fileName.endsWith(".fjson")) {
				continue;
			}

			System.out.println(fileName);

			_structuredContentResource.
				postStructuredContentFolderStructuredContent(
					_getStructuredContentFolderId(fileName),
					_toStructuredContent(fileName));
		}
	}

	private void _addFileNames(String fileName) {
		File file = new File(fileName);

		if (file.isDirectory()) {
			for (String currentFileName : file.list()) {
				_addFileNames(fileName + "/" + currentFileName);
			}
		}

		_fileNames.add(fileName);
	}

	private String[] _getDirNames(String fileName) throws Exception {
		List<String> dirNames = new ArrayList<>();

		String relativeFileName = fileName.substring(
			_sphinxOutputDirectory.length() + 1);

		String[] parts = relativeFileName.split(
			Matcher.quoteReplacement(System.getProperty("file.separator")));

		for (String part : parts) {
			if (part.endsWith(".html") || part.endsWith(".md") ||
				part.endsWith(".fjson") || part.endsWith(".rst") ||
				part.equalsIgnoreCase("..") || part.equalsIgnoreCase("docs") ||
				part.equalsIgnoreCase(".") || part.equalsIgnoreCase("latest")) {

				continue;
			}

			String dirName = part;

			dirNames.add(dirName);
		}

		return dirNames.toArray(new String[0]);
	}

	private Long _getDocumentFolderId(String fileName) throws Exception {
		Long documentFolderId = 0L;

		for (String dirName : _getDirNames(fileName)) {
			documentFolderId = _getDocumentFolderId(dirName, documentFolderId);
		}

		return documentFolderId;
	}

	private Long _getDocumentFolderId(
			String dirName, Long parentDocumentFolderId)
		throws Exception {

		String key = parentDocumentFolderId + "#" + dirName;

		Long documentFolderId = _documentFolderIds.get(key);

		if (documentFolderId != null) {
			return documentFolderId;
		}

		DocumentFolder documentFolder = null;

		if (parentDocumentFolderId == 0) {
			Page<DocumentFolder> page =
				_documentFolderResource.getSiteDocumentFoldersPage(
					_liferaySiteId, null, null, null,
					"name eq '" + dirName + "'", null, null);

			documentFolder = page.fetchFirstItem();

			if (documentFolder == null) {
				documentFolder = _documentFolderResource.postSiteDocumentFolder(
					_liferaySiteId,
					new DocumentFolder() {
						{
							description = "";
							name = dirName;
						}
					});
			}
		}
		else {
			Page<DocumentFolder> page =
				_documentFolderResource.getDocumentFolderDocumentFoldersPage(
					parentDocumentFolderId, null, null, null,
					"name eq '" + dirName + "'", null, null);

			documentFolder = page.fetchFirstItem();

			if (documentFolder == null) {
				documentFolder =
					_documentFolderResource.postDocumentFolderDocumentFolder(
						parentDocumentFolderId,
						new DocumentFolder() {
							{
								description = "";
								name = dirName;
							}
						});
			}
		}

		documentFolderId = documentFolder.getId();

		_documentFolderIds.put(key, documentFolderId);

		return documentFolderId;
	}

	private String _getJSONObjectString(
		JSONObject jsonObject, String key, String defaultValue) {

		if (!jsonObject.has(key)) {
			return defaultValue;
		}

		return jsonObject.getString(key);
	}

	private String _getOAuthAuthorization() throws Exception {
		HttpPost httpPost = new HttpPost(_liferayURL + "/o/oauth2/token");

		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

		httpPost.setEntity(
			new UrlEncodedFormEntity(
				Arrays.asList(
					new BasicNameValuePair("client_id", _liferayClientId),
					new BasicNameValuePair(
						"client_secret", _liferayClientSecret),
					new BasicNameValuePair(
						"grant_type", "client_credentials"))));

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build()) {

			CloseableHttpResponse closeableHttpResponse =
				closeableHttpClient.execute(httpPost);

			StatusLine statusLine = closeableHttpResponse.getStatusLine();

			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				JSONObject responseJSONObject = new JSONObject(
					EntityUtils.toString(
						closeableHttpResponse.getEntity(),
						Charset.defaultCharset()));

				return responseJSONObject.getString("token_type") + " " +
					responseJSONObject.getString("access_token");
			}

			throw new Exception("Unable to obtain OAuth token");
		}
	}

	private Long _getStructuredContentFolderId(String fileName)
		throws Exception {

		Long structuredContentFolderId = 0L;

		for (String dirName : _getDirNames(fileName)) {
			structuredContentFolderId = _getStructuredContentFolderId(
				dirName, structuredContentFolderId);
		}

		return structuredContentFolderId;
	}

	private Long _getStructuredContentFolderId(
			String dirName, Long parentStructuredContentFolderId)
		throws Exception {

		String key = parentStructuredContentFolderId + "#" + dirName;

		Long structuredContentFolderId = _structuredContentFolderIds.get(key);

		if (structuredContentFolderId != null) {
			return structuredContentFolderId;
		}

		StructuredContentFolder structuredContentFolder = null;

		if (parentStructuredContentFolderId == 0) {
			Page<StructuredContentFolder> page =
				_structuredContentFolderResource.
					getSiteStructuredContentFoldersPage(
						_liferaySiteId, null, null, null,
						"name eq '" + dirName + "'", null, null);

			structuredContentFolder = page.fetchFirstItem();

			if (structuredContentFolder == null) {
				structuredContentFolder =
					_structuredContentFolderResource.
						postSiteStructuredContentFolder(
							_liferaySiteId,
							new StructuredContentFolder() {
								{
									description = "";
									name = dirName;
								}
							});
			}
		}
		else {
			Page<StructuredContentFolder> page =
				_structuredContentFolderResource.
					getStructuredContentFolderStructuredContentFoldersPage(
						parentStructuredContentFolderId, null, null,
						"name eq '" + dirName + "'", null, null);

			structuredContentFolder = page.fetchFirstItem();

			if (structuredContentFolder == null) {
				structuredContentFolder =
					_structuredContentFolderResource.
						postStructuredContentFolderStructuredContentFolder(
							parentStructuredContentFolderId,
							new StructuredContentFolder() {
								{
									description = "";
									name = dirName;
								}
							});
			}
		}

		structuredContentFolderId = structuredContentFolder.getId();

		_structuredContentFolderIds.put(key, structuredContentFolderId);

		return structuredContentFolderId;
	}

	private void _initResourceBuilders(String authorization) {
		DocumentFolderResource.Builder documentFolderResourceBuilder =
			DocumentFolderResource.builder();

		_documentFolderResource = documentFolderResourceBuilder.header(
			"Authorization", authorization
		).build();

		DocumentResource.Builder documentResourceBuilder =
			DocumentResource.builder();

		_documentResource = documentResourceBuilder.header(
			"Authorization", authorization
		).build();

		StructuredContentResource.Builder structuredContentResourceBuilder =
			StructuredContentResource.builder();

		_structuredContentResource = structuredContentResourceBuilder.header(
			"Authorization", authorization
		).build();

		StructuredContentFolderResource.Builder
			structuredContentFolderResourceBuilder =
				StructuredContentFolderResource.builder();

		_structuredContentFolderResource =
			structuredContentFolderResourceBuilder.header(
				"Authorization", authorization
			).build();
	}

	private String _toFriendlyURLPath(String fileName) {
		String relativeFileName = fileName.substring(
			_sphinxOutputDirectory.length() + 1);

		return FilenameUtils.removeExtension(relativeFileName);
	}

	private StructuredContent _toStructuredContent(String fileName)
		throws Exception {

		StructuredContent structuredContent = new StructuredContent();

		File englishFile = new File(fileName);

		String englishText = FileUtils.readFileToString(
			englishFile, StandardCharsets.UTF_8);

		JSONObject englishJSONObject = new JSONObject(englishText);

		ContentFieldValue englishContentFieldValue = new ContentFieldValue() {
			{
				data = _uploadImagesFromHTML(
					fileName,
					_getJSONObjectString(
						englishJSONObject, "body", StringPool.BLANK));
			}
		};

		String englishTitle = _getJSONObjectString(
			englishJSONObject, "title", fileName);

		File japaneseFile = new File(
			StringUtil.replace(fileName, "/en/", "/ja/"));

		if (japaneseFile.exists()) {
			String japaneseText = FileUtils.readFileToString(
				japaneseFile, StandardCharsets.UTF_8);

			JSONObject japaneseJSONObject = new JSONObject(japaneseText);

			structuredContent.setContentFields(
				new ContentField[] {
					new ContentField() {
						{
							contentFieldValue = englishContentFieldValue;
							contentFieldValue_i18n = HashMapBuilder.put(
								"en-US", englishContentFieldValue
							).put(
								"ja-JP",
								new ContentFieldValue() {
									{
										data = _uploadImagesFromHTML(
											japaneseFile.getAbsolutePath(),
											_getJSONObjectString(
												japaneseJSONObject, "body",
												StringPool.BLANK));
									}
								}
							).build();
							name = "content";
						}
					}
				});

			structuredContent.setFriendlyUrlPath_i18n(
				HashMapBuilder.put(
					"en-US", _toFriendlyURLPath(fileName)
				).put(
					"ja-JP", _toFriendlyURLPath(japaneseFile.getPath())
				).build());
			structuredContent.setTitle_i18n(
				HashMapBuilder.put(
					"en-US", englishTitle
				).put(
					"ja-JP",
					_getJSONObjectString(
						japaneseJSONObject, "title", japaneseFile.getPath())
				).build());
		}
		else {
			structuredContent.setContentFields(
				new ContentField[] {
					new ContentField() {
						{
							contentFieldValue = englishContentFieldValue;
							name = "content";
						}
					}
				});
		}

		structuredContent.setContentStructureId(
			GetterUtil.getLong(_liferayArticleStructureId));
		structuredContent.setFriendlyUrlPath(_toFriendlyURLPath(fileName));
		structuredContent.setTitle(englishTitle);

		return structuredContent;
	}

	private String _uploadImage(String sphinxFileName, String url)
		throws Exception {

		String fileName =
			System.getProperty("file.separator") +
				FilenameUtils.getPath(sphinxFileName) + url;

		File file = new File(fileName);

		if (!file.exists()) {
			file = new File(fileName.replaceAll("/ja/", "/en/"));
		}

		if (!file.exists()) {
			System.out.println("Missing image file " + file);

			return "";
		}

		File finalFile = file;

		String filePathString = file.getPath();

		String imageURL = _imageURLs.get(filePathString);

		if (imageURL == null) {
			String relativeFilePath = filePathString.substring(
				_sphinxOutputDirectory.length());

			Document document = _documentResource.postDocumentFolderDocument(
				_getDocumentFolderId(filePathString),
				new Document() {
					{
						title = relativeFilePath;
					}
				},
				HashMapBuilder.<String, File>put(
					"file", finalFile
				).build());

			imageURL = document.getContentUrl();

			_imageURLs.put(filePathString, imageURL);
		}

		return imageURL;
	}

	private String _uploadImagesFromHTML(String fileName, String html)
		throws Exception {

		org.jsoup.nodes.Document document = Jsoup.parse(html);

		Elements imageElements = document.select("img");

		for (Element imageElement : imageElements) {
			String imageUrl = imageElement.attr("src");

			imageElement.attr("src", _uploadImage(fileName, imageUrl));
		}

		return document.html();
	}

	private final Map<String, Long> _documentFolderIds = new HashMap<>();
	private DocumentFolderResource _documentFolderResource;
	private DocumentResource _documentResource;
	private final Set<String> _fileNames = new TreeSet<>();
	private final Map<String, String> _imageURLs = new HashMap<>();
	private final long _liferayArticleStructureId;
	private final String _liferayClientId;
	private final String _liferayClientSecret;
	private final long _liferaySiteId;
	private final String _liferayURL;
	private final Logger _logger;
	private final String _sphinxOutputDirectory;
	private final Map<String, Long> _structuredContentFolderIds =
		new HashMap<>();
	private StructuredContentFolderResource _structuredContentFolderResource;
	private StructuredContentResource _structuredContentResource;

}