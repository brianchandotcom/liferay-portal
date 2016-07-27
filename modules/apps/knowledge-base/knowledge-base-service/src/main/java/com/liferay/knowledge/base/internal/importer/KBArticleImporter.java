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

package com.liferay.knowledge.base.internal.importer;

import com.liferay.knowledge.base.configuration.KBGroupServiceConfiguration;
import com.liferay.knowledge.base.constants.KBArticleConstants;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.exception.KBArticleImportException;
import com.liferay.knowledge.base.internal.importer.util.KBArticleMarkdownConverter;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author James Hinkey
 * @author Sergio González
 * @author Jesse Rao
 */
@Component(service = KBArticleImporter.class)
public class KBArticleImporter {

	public int processZipFile(
			long userId, long groupId, long parentKBFolderId,
			boolean prioritizeByNumericalPrefix, InputStream inputStream,
			ServiceContext serviceContext)
		throws PortalException {

		if (inputStream == null) {
			throw new KBArticleImportException("Input stream is null");
		}

		try {
			ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(
				inputStream);

			Map<String, String> metadata = getMetadata(zipReader);

			return processKBArticleFiles(
				userId, groupId, parentKBFolderId, prioritizeByNumericalPrefix,
				zipReader, metadata, serviceContext);
		}
		catch (IOException ioe) {
			throw new KBArticleImportException(ioe);
		}
	}

	protected KBArticle addKBArticleMarkdown(
			long userId, long groupId, long parentKBFolderId,
			long parentResourceClassNameId, long parentResourcePrimaryKey,
			String markdown, String fileEntryName, ZipReader zipReader,
			Map<String, String> metadata,
			PrioritizationStrategy prioritizationStrategy,
			ServiceContext serviceContext)
		throws KBArticleImportException {

		if (Validator.isNull(markdown)) {
			throw new KBArticleImportException(
				"Markdown is null for file entry " + fileEntryName);
		}

		KBArticleMarkdownConverter kbArticleMarkdownConverter =
			new KBArticleMarkdownConverter(markdown, fileEntryName, metadata);

		String urlTitle = kbArticleMarkdownConverter.getUrlTitle();

		KBArticle kbArticle =
			KBArticleLocalServiceUtil.fetchKBArticleByUrlTitle(
				groupId, parentKBFolderId, urlTitle);

		boolean newKBArticle = false;

		if (kbArticle == null) {
			newKBArticle = true;
		}

		try {
			if (kbArticle == null) {
				int workflowAction = serviceContext.getWorkflowAction();

				serviceContext.setWorkflowAction(
					WorkflowConstants.ACTION_SAVE_DRAFT);

				kbArticle = KBArticleLocalServiceUtil.addKBArticle(
					userId, parentResourceClassNameId, parentResourcePrimaryKey,
					kbArticleMarkdownConverter.getTitle(), urlTitle, markdown,
					null, kbArticleMarkdownConverter.getSourceURL(), null, null,
					serviceContext);

				serviceContext.setWorkflowAction(workflowAction);
			}
		}
		catch (Exception e) {
			StringBundler sb = new StringBundler(4);

			sb.append("Unable to add basic KB article for file entry ");
			sb.append(fileEntryName);
			sb.append(": ");
			sb.append(e.getLocalizedMessage());

			throw new KBArticleImportException(sb.toString(), e);
		}

		try {
			String html =
				kbArticleMarkdownConverter.processAttachmentsReferences(
					userId, kbArticle, zipReader,
					new HashMap<String, FileEntry>());

			kbArticle = KBArticleLocalServiceUtil.updateKBArticle(
				userId, kbArticle.getResourcePrimKey(),
				kbArticleMarkdownConverter.getTitle(), html,
				kbArticle.getDescription(),
				kbArticleMarkdownConverter.getSourceURL(), null, null, null,
				serviceContext);

			if (newKBArticle) {
				prioritizationStrategy.addKBArticle(kbArticle, fileEntryName);
			}
			else {
				prioritizationStrategy.updateKBArticle(
					kbArticle, fileEntryName);
			}

			return kbArticle;
		}
		catch (Exception e) {
			StringBundler sb = new StringBundler(4);

			sb.append("Unable to update KB article for file entry ");
			sb.append(fileEntryName);
			sb.append(": ");
			sb.append(e.getLocalizedMessage());

			throw new KBArticleImportException(sb.toString(), e);
		}
	}

	protected Map<String, List<String>> getFolderNameFileEntryNamesMap(
			ZipReader zipReader,
			KBGroupServiceConfiguration kbGroupServiceConfiguration)
		throws KBArticleImportException {

		Map<String, List<String>> folderNameFileEntryNamesMap = new TreeMap<>();

		for (String zipEntry : _getEntries(zipReader)) {
			String extension = FileUtil.getExtension(zipEntry);

			if (!ArrayUtil.contains(
					kbGroupServiceConfiguration.
						markdownImporterArticleExtensions(),
					StringPool.PERIOD.concat(extension))) {

				continue;
			}

			String folderName = StringPool.SLASH;

			if (zipEntry.indexOf(CharPool.SLASH) != -1) {
				folderName = zipEntry.substring(
					0, zipEntry.lastIndexOf(StringPool.SLASH));
			}

			List<String> fileEntryNames = folderNameFileEntryNamesMap.get(
				folderName);

			if (fileEntryNames == null) {
				fileEntryNames = new ArrayList<>();
			}

			fileEntryNames.add(zipEntry);

			folderNameFileEntryNamesMap.put(folderName, fileEntryNames);
		}

		return folderNameFileEntryNamesMap;
	}

	protected Map<String, String> getMetadata(ZipReader zipReader)
		throws KBArticleImportException {

		InputStream inputStream = null;

		try {
			inputStream = zipReader.getEntryAsInputStream(".METADATA");

			if (inputStream == null) {
				return Collections.emptyMap();
			}

			Properties properties = new Properties();

			properties.load(inputStream);

			Map<String, String> metadata = new HashMap<>(properties.size());

			for (Map.Entry<Object, Object> entry : properties.entrySet()) {
				Object key = entry.getKey();
				Object value = entry.getValue();

				if (value != null) {
					metadata.put(key.toString(), value.toString());
				}
			}

			return metadata;
		}
		catch (IOException ioe) {
			throw new KBArticleImportException(ioe);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	protected int processKBArticleFiles(
			long userId, long groupId, long parentKBFolderId,
			boolean prioritizeByNumericalPrefix, ZipReader zipReader,
			Map<String, String> metadata, ServiceContext serviceContext)
		throws PortalException {

		int importedKBArticlesCount = 0;

		PrioritizationStrategy prioritizationStrategy =
			PrioritizationStrategy.create(
				groupId, parentKBFolderId, prioritizeByNumericalPrefix);

		KBGroupServiceConfiguration kbGroupServiceConfiguration =
			_configurationProvider.getGroupConfiguration(
				KBGroupServiceConfiguration.class, groupId);

		_validateFileEntryNames(zipReader, kbGroupServiceConfiguration);

		Map<String, List<String>> folderNameFileEntryNamesMap =
			getFolderNameFileEntryNamesMap(
				zipReader, kbGroupServiceConfiguration);

		Set<String> folderNames = folderNameFileEntryNamesMap.keySet();

		// Map intro files to their folders

		Map<String, String> folderNameIntroFileNameMap = new TreeMap<>();

		for (String folderName : folderNames) {
			List<String> fileEntryNames = folderNameFileEntryNamesMap.get(
				folderName);

			for (String fileEntryName : fileEntryNames) {
				if (fileEntryName.endsWith(
						kbGroupServiceConfiguration.
							markdownImporterArticleIntro())) {

					folderNameIntroFileNameMap.put(folderName, fileEntryName);

					break;
				}
			}
		}

		// Map ancestor intro files to folders that have no intro files

		for (String folderName : folderNames) {
			String introFileName = folderNameIntroFileNameMap.get(folderName);

			if (Validator.isNull(introFileName)) {
				for (String path : _extractPaths(folderName)) {
					String parentIntroFileName = folderNameIntroFileNameMap.get(
						path);

					if (Validator.isNotNull(parentIntroFileName)) {
						folderNameIntroFileNameMap.put(
							folderName, parentIntroFileName);

						break;
					}
				}

				if (Validator.isNull(
						folderNameIntroFileNameMap.get(folderName))) {

					folderNameIntroFileNameMap.put(
						folderName, StringPool.BLANK);
				}
			}
		}

		// Add a KB article for each intro file

		Map<String, KBArticle> introFileNameKBArticleMap = new HashMap<>();

		for (String folderName : folderNames) {
			long parentResourceClassNameId = PortalUtil.getClassNameId(
				KBFolderConstants.getClassName());
			long parentResourcePrimaryKey = parentKBFolderId;

			long sectionResourceClassNameId = parentResourceClassNameId;
			long sectionResourcePrimaryKey = parentResourcePrimaryKey;

			String introFileName = folderNameIntroFileNameMap.get(folderName);

			if (Validator.isNotNull(introFileName)) {

				// Check for parent intro file

				for (String path : _extractPaths(folderName)) {
					String parentIntroFileName = folderNameIntroFileNameMap.get(
						path);

					if (Validator.isNotNull(parentIntroFileName)) {
						KBArticle parentIntroKBArticle =
							introFileNameKBArticleMap.get(parentIntroFileName);

						sectionResourceClassNameId = PortalUtil.getClassNameId(
							KBArticleConstants.getClassName());
						sectionResourcePrimaryKey =
							parentIntroKBArticle.getResourcePrimKey();

						break;
					}
				}

				KBArticle introKBArticle = introFileNameKBArticleMap.get(
					introFileName);

				if (Validator.isNull(introKBArticle)) {
					introKBArticle = addKBArticleMarkdown(
						userId, groupId, parentKBFolderId,
						sectionResourceClassNameId, sectionResourcePrimaryKey,
						zipReader.getEntryAsString(introFileName),
						introFileName, zipReader, metadata,
						prioritizationStrategy, serviceContext);

					importedKBArticlesCount++;

					introFileNameKBArticleMap.put(
						introFileName, introKBArticle);
				}
			}
		}

		// Add non-intro files as KB articles

		for (String folderName : folderNames) {
			long parentResourceClassNameId = PortalUtil.getClassNameId(
				KBFolderConstants.getClassName());
			long parentResourcePrimaryKey = parentKBFolderId;

			long sectionResourceClassNameId = parentResourceClassNameId;
			long sectionResourcePrimaryKey = parentResourcePrimaryKey;

			// Lookup section intro article for folder

			String introFileName = folderNameIntroFileNameMap.get(folderName);

			if (Validator.isNotNull(introFileName)) {
				KBArticle introKBArticle = introFileNameKBArticleMap.get(
					introFileName);

				sectionResourceClassNameId = PortalUtil.getClassNameId(
					KBArticleConstants.getClassName());
				sectionResourcePrimaryKey = introKBArticle.getResourcePrimKey();
			}

			List<String> fileEntryNames = folderNameFileEntryNamesMap.get(
				folderName);

			for (String fileEntryName : fileEntryNames) {
				if (!fileEntryName.endsWith(
						kbGroupServiceConfiguration.
							markdownImporterArticleIntro())) {

					String markdown = zipReader.getEntryAsString(fileEntryName);

					if (Validator.isNull(markdown)) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Missing Markdown in file entry " +
									fileEntryName);
						}
					}

					addKBArticleMarkdown(
						userId, groupId, parentKBFolderId,
						sectionResourceClassNameId, sectionResourcePrimaryKey,
						markdown, fileEntryName, zipReader, metadata,
						prioritizationStrategy, serviceContext);

					importedKBArticlesCount++;
				}
			}
		}

		prioritizationStrategy.prioritizeKBArticles();

		return importedKBArticlesCount;
	}

	@Reference(unbind = "-")
	protected void setConfigurationProvider(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
	}

	private List<String> _extractPaths(String folderName) {
		List<String> paths = new ArrayList<>();

		int length = folderName.length();

		for (int from = 0; from < length;) {
			int index = folderName.indexOf('/', from);

			if (index == -1) {
				break;
			}
			else {
				String path = folderName.substring(0, index);

				paths.add(0, path);
			}

			from = index + 1;
		}

		return paths;
	}

	private List<String> _getEntries(ZipReader zipReader)
		throws KBArticleImportException {

		List<String> entries = zipReader.getEntries();

		if (entries == null) {
			throw new KBArticleImportException(
				"The uploaded file is not a ZIP archive or it is corrupted");
		}

		return entries;
	}

	private void _validateFileEntryNames(
			ZipReader zipReader,
			KBGroupServiceConfiguration kbGroupServiceConfiguration)
		throws KBArticleImportException {

		Map<String, List<String>> folderNameFileEntryNamesMap =
			getFolderNameFileEntryNamesMap(
				zipReader, kbGroupServiceConfiguration);

		Set<String> folderNames = folderNameFileEntryNamesMap.keySet();

		for (String folderName : folderNames) {
			List<String> fileEntryNames = folderNameFileEntryNamesMap.get(
				folderName);

			String introFileEntryName = null;

			for (String fileEntryName : fileEntryNames) {
				if (fileEntryName.endsWith(
						kbGroupServiceConfiguration.
							markdownImporterArticleIntro())) {

					if (Validator.isNull(introFileEntryName)) {
						introFileEntryName = fileEntryName;
					}
					else {
						StringBundler sb = new StringBundler(6);

						sb.append("Multiple files with section designator ");
						sb.append(
							kbGroupServiceConfiguration.
								markdownImporterArticleIntro());
						sb.append(": ");
						sb.append(introFileEntryName);
						sb.append(", ");
						sb.append(fileEntryName);

						throw new KBArticleImportException(sb.toString());
					}
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KBArticleImporter.class);

	private ConfigurationProvider _configurationProvider;

}