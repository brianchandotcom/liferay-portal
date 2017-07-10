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

import com.liferay.asset.kernel.exception.AssetCategoryException;
import com.liferay.knowledge.base.constants.KBArticleConstants;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.exception.KBArticleImportException;
import com.liferay.knowledge.base.internal.importer.util.KBArticleMarkdownConverter;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author James Hinkey
 * @author Sergio González
 * @author Jesse Rao
 */
public class KBArticleImporter {

	public KBArticleImporter(
		KBArchiveFactory kbArchiveFactory,
		KBArticleLocalService kbArticleLocalService, Portal portal) {

		_kbArchiveFactory = kbArchiveFactory;
		_kbArticleLocalService = kbArticleLocalService;
		_portal = portal;
	}

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

		KBArticle kbArticle = _kbArticleLocalService.fetchKBArticleByUrlTitle(
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

				kbArticle = _kbArticleLocalService.addKBArticle(
					userId, parentResourceClassNameId, parentResourcePrimaryKey,
					kbArticleMarkdownConverter.getTitle(), urlTitle, markdown,
					null, kbArticleMarkdownConverter.getSourceURL(), null, null,
					serviceContext);

				serviceContext.setWorkflowAction(workflowAction);
			}
		}
		catch (AssetCategoryException ace) {
			throw new KBArticleImportException.MustHaveACategory(ace);
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
					userId, kbArticle, zipReader, new HashMap<>());

			kbArticle = _kbArticleLocalService.updateKBArticle(
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

		KBArchive kbArchive = _kbArchiveFactory.createKBArchive(
			groupId, zipReader);

		Map<KBArchive.File, KBArticle> introFileNameKBArticleMap =
			new HashMap<>();

		for (KBArchive.Folder folder : kbArchive.getFolders()) {
			KBArchive.File introFile = folder.getIntroFile();

			KBArticle introKBArticle = introFileNameKBArticleMap.get(introFile);

			if ((introFile != null) && (introKBArticle == null)) {
				long sectionResourceClassNameId = _portal.getClassNameId(
					KBFolderConstants.getClassName());
				long sectionResourcePrimaryKey = parentKBFolderId;

				KBArticle parentIntroKBArticle = introFileNameKBArticleMap.get(
					folder.getParentFolderIntroFile());

				if (parentIntroKBArticle != null) {
					sectionResourceClassNameId = _portal.getClassNameId(
						KBArticleConstants.getClassName());
					sectionResourcePrimaryKey =
						parentIntroKBArticle.getResourcePrimKey();
				}

				introKBArticle = addKBArticleMarkdown(
					userId, groupId, parentKBFolderId,
					sectionResourceClassNameId, sectionResourcePrimaryKey,
					introFile.getContent(), introFile.getName(), zipReader,
					metadata, prioritizationStrategy, serviceContext);

				importedKBArticlesCount++;

				introFileNameKBArticleMap.put(introFile, introKBArticle);
			}

			long sectionResourceClassNameId = _portal.getClassNameId(
				KBFolderConstants.getClassName());
			long sectionResourcePrimaryKey = parentKBFolderId;

			if (introKBArticle != null) {
				sectionResourceClassNameId = _portal.getClassNameId(
					KBArticleConstants.getClassName());
				sectionResourcePrimaryKey = introKBArticle.getResourcePrimKey();
			}

			for (KBArchive.File file : folder.getFiles()) {
				String markdown = file.getContent();

				if (Validator.isNull(markdown)) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Missing Markdown in file entry " + file.getName());
					}
				}

				addKBArticleMarkdown(
					userId, groupId, parentKBFolderId,
					sectionResourceClassNameId, sectionResourcePrimaryKey,
					markdown, file.getName(), zipReader, metadata,
					prioritizationStrategy, serviceContext);

				importedKBArticlesCount++;
			}
		}

		prioritizationStrategy.prioritizeKBArticles();

		return importedKBArticlesCount;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KBArticleImporter.class);

	private final KBArchiveFactory _kbArchiveFactory;
	private final KBArticleLocalService _kbArticleLocalService;
	private final Portal _portal;

}