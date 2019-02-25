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

package com.liferay.document.library.internal.search.spi.model.index.contributor;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalService;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManager;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManager;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentHelper;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.RelatedEntryIndexer;
import com.liferay.portal.kernel.search.RelatedEntryIndexerRegistry;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.trash.TrashHelper;

import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = ModelDocumentContributor.class
)
public class DLFileEntryModelDocumentContributor
	implements ModelDocumentContributor<DLFileEntry> {

	@Override
	public void contribute(Document document, DLFileEntry dlFileEntry) {
		if (_log.isDebugEnabled()) {
			_log.debug("Indexing document " + dlFileEntry);
		}

		boolean indexContent = true;

		String[] ignoreExtensions = PrefsPropsUtil.getStringArray(
			PropsKeys.DL_FILE_INDEXING_IGNORE_EXTENSIONS, StringPool.COMMA);

		if (ArrayUtil.contains(
				ignoreExtensions,
				StringPool.PERIOD + dlFileEntry.getExtension())) {

			indexContent = false;
		}

		InputStream is = null;

		if (indexContent) {
			try {
				DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

				is = dlFileVersion.getContentStream(false);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to retrieve document stream", e);
				}
			}
		}

		try {
			DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

			if (indexContent) {
				if (is != null) {
					try {
						Locale defaultLocale = portal.getSiteDefaultLocale(
							dlFileEntry.getGroupId());

						String localizedField = Field.getLocalizedName(
							defaultLocale.toString(), Field.CONTENT);

						document.addFile(
							localizedField, is, dlFileEntry.getTitle(),
							PropsValues.DL_FILE_INDEXING_MAX_SIZE);
					}
					catch (IOException ioe) {
						if (_log.isWarnEnabled()) {
							_log.warn("Unable to index content", ioe);
						}
					}
				}
				else if (_log.isDebugEnabled()) {
					_log.debug(
						"Document " + dlFileEntry +
							" does not have any content");
				}
			}

			document.addKeyword(
				Field.CLASS_TYPE_ID, dlFileEntry.getFileEntryTypeId());
			document.addText(Field.DESCRIPTION, dlFileEntry.getDescription());
			document.addKeyword(Field.FOLDER_ID, dlFileEntry.getFolderId());
			document.addKeyword(Field.HIDDEN, dlFileEntry.isInHiddenFolder());
			document.addText(
				Field.PROPERTIES, dlFileEntry.getLuceneProperties());
			document.addKeyword(Field.STATUS, dlFileVersion.getStatus());

			String title = dlFileEntry.getTitle();

			if (dlFileEntry.isInTrash()) {
				title = trashHelper.getOriginalTitle(title);
			}

			document.addText(Field.TITLE, title);

			document.addKeyword(
				Field.TREE_PATH,
				StringUtil.split(dlFileEntry.getTreePath(), CharPool.SLASH));

			document.addKeyword(
				"dataRepositoryId", dlFileEntry.getDataRepositoryId());
			//todo is this necessary?
			document.addText(
				"ddmContent",
				extractDDMContent(dlFileVersion, LocaleUtil.getSiteDefault()));
			document.addTextSortable("extension", dlFileEntry.getExtension());
			document.addKeyword(
				"fileEntryTypeId", dlFileEntry.getFileEntryTypeId());
			document.addTextSortable(
				"mimeType",
				StringUtil.replace(
					dlFileEntry.getMimeType(), CharPool.FORWARD_SLASH,
					CharPool.UNDERLINE));
			document.addKeyword("path", dlFileEntry.getTitle());
			document.addKeyword("readCount", dlFileEntry.getReadCount());
			document.addNumber("size", dlFileEntry.getSize());

			addFileEntryTypeAttributes(document, dlFileVersion);

			if (dlFileEntry.isInHiddenFolder()) {
				List<RelatedEntryIndexer> relatedEntryIndexers =
					relatedEntryIndexerRegistry.getRelatedEntryIndexers(
						dlFileEntry.getClassName());

				if (ListUtil.isNotEmpty(relatedEntryIndexers)) {
					for (RelatedEntryIndexer relatedEntryIndexer :
							relatedEntryIndexers) {

						relatedEntryIndexer.addRelatedEntryFields(
							document, new LiferayFileEntry(dlFileEntry));

						DocumentHelper documentHelper = new DocumentHelper(
							document);

						documentHelper.setAttachmentOwnerKey(
							portal.getClassNameId(dlFileEntry.getClassName()),
							dlFileEntry.getClassPK());

						document.addKeyword(Field.RELATED_ENTRY, true);
					}
				}
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Document " + dlFileEntry + " indexed successfully");
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			if (is != null) {
				try {
					is.close();
				}
				catch (IOException ioe) {
				}
			}
		}
	}

	protected void addFileEntryTypeAttributes(
			Document document, DLFileVersion dlFileVersion)
		throws PortalException {

		List<DLFileEntryMetadata> dlFileEntryMetadatas =
			dlFileEntryMetadataLocalService.getFileVersionFileEntryMetadatas(
				dlFileVersion.getFileVersionId());

		for (DLFileEntryMetadata dlFileEntryMetadata : dlFileEntryMetadatas) {
			try {
				DDMFormValues ddmFormValues =
					storageEngineManager.getDDMFormValues(
						dlFileEntryMetadata.getDDMStorageId());

				if (ddmFormValues != null) {
					ddmStructureManager.addAttributes(
						dlFileEntryMetadata.getDDMStructureId(), document,
						ddmFormValues);
				}
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to retrieve metadata values", e);
				}
			}
		}
	}

	protected String extractDDMContent(
			DLFileVersion dlFileVersion, Locale locale)
		throws Exception {

		List<DLFileEntryMetadata> dlFileEntryMetadatas =
			dlFileEntryMetadataLocalService.getFileVersionFileEntryMetadatas(
				dlFileVersion.getFileVersionId());

		StringBundler sb = new StringBundler(dlFileEntryMetadatas.size());

		for (DLFileEntryMetadata dlFileEntryMetadata : dlFileEntryMetadatas) {
			try {
				DDMFormValues ddmFormValues =
					storageEngineManager.getDDMFormValues(
						dlFileEntryMetadata.getDDMStorageId());

				if (ddmFormValues != null) {
					sb.append(
						ddmStructureManager.extractAttributes(
							dlFileEntryMetadata.getDDMStructureId(),
							ddmFormValues, locale));
				}
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to retrieve metadata values", e);
				}
			}
		}

		return sb.toString();
	}

	@Reference
	protected DDMStructureManager ddmStructureManager;

	@Reference
	protected DLFileEntryMetadataLocalService dlFileEntryMetadataLocalService;

	@Reference
	protected Portal portal;

	@Reference
	protected RelatedEntryIndexerRegistry relatedEntryIndexerRegistry;

	@Reference
	protected StorageEngineManager storageEngineManager;

	@Reference
	protected TrashHelper trashHelper;

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileEntryModelDocumentContributor.class);

}