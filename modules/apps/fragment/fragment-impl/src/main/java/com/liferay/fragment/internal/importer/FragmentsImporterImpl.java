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

package com.liferay.fragment.internal.importer;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.constants.FragmentExportImportConstants;
import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.exception.DuplicateFragmentCollectionKeyException;
import com.liferay.fragment.exception.DuplicateFragmentCompositionKeyException;
import com.liferay.fragment.exception.DuplicateFragmentEntryKeyException;
import com.liferay.fragment.exception.FragmentCollectionNameException;
import com.liferay.fragment.importer.FragmentsImporter;
import com.liferay.fragment.importer.FragmentsImporterResultEntry;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentCollectionService;
import com.liferay.fragment.service.FragmentCompositionLocalService;
import com.liferay.fragment.service.FragmentCompositionService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.service.FragmentEntryService;
import com.liferay.fragment.validator.FragmentEntryValidator;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.File;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = FragmentsImporter.class)
public class FragmentsImporterImpl implements FragmentsImporter {

	@Override
	public List<String> importFile(
			long userId, long groupId, long fragmentCollectionId, File file,
			boolean overwrite)
		throws Exception {

		List<FragmentsImporterResultEntry> fragmentsImporterResultEntries =
			importFragmentEntries(
				userId, groupId, fragmentCollectionId, file, overwrite);

		Stream<FragmentsImporterResultEntry> stream =
			fragmentsImporterResultEntries.stream();

		return stream.map(
			FragmentsImporterResultEntry::getName
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public List<FragmentsImporterResultEntry> importFragmentEntries(
			long userId, long groupId, long fragmentCollectionId, File file,
			boolean overwrite)
		throws Exception {

		_fragmentsImporterResultEntries = new ArrayList<>();

		try (ZipFile zipFile = new ZipFile(file)) {
			Map<String, String> orphanFragmentCompositions = new HashMap<>();
			Map<String, String> orphanFragmentEntries = new HashMap<>();

			Map<String, FragmentCollectionFolder> fragmentCollectionFolderMap =
				_getFragmentCollectionFolderMap(
					zipFile, groupId, orphanFragmentCompositions,
					orphanFragmentEntries);

			for (Map.Entry<String, FragmentCollectionFolder> entry :
					fragmentCollectionFolderMap.entrySet()) {

				FragmentCollectionFolder fragmentCollectionFolder =
					entry.getValue();

				String name = entry.getKey();
				String description = StringPool.BLANK;

				String collectionJSON = _getContent(
					zipFile, fragmentCollectionFolder.getFileName());

				if (Validator.isNotNull(collectionJSON)) {
					JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
						collectionJSON);

					name = jsonObject.getString("name");
					description = jsonObject.getString("description");
				}

				if (Validator.isNull(name)) {
					throw new FragmentCollectionNameException();
				}

				FragmentCollection fragmentCollection = _addFragmentCollection(
					groupId, entry.getKey(), name, description, overwrite);

				_importResources(
					userId, groupId,
					fragmentCollection.getFragmentCollectionId(),
					fragmentCollection.getResourcesFolderId(), zipFile);

				_importFragmentCompositions(
					userId, groupId, zipFile,
					fragmentCollection.getFragmentCollectionId(),
					fragmentCollectionFolder.getFragmentCompositions(),
					overwrite);

				_importFragmentEntries(
					userId, groupId, zipFile,
					fragmentCollection.getFragmentCollectionId(),
					fragmentCollectionFolder.getFragmentEntries(), overwrite);
			}

			if (MapUtil.isNotEmpty(orphanFragmentCompositions) ||
				MapUtil.isNotEmpty(orphanFragmentEntries)) {

				FragmentCollection fragmentCollection =
					_fragmentCollectionService.fetchFragmentCollection(
						fragmentCollectionId);

				if (fragmentCollection == null) {
					fragmentCollection =
						_fragmentCollectionLocalService.fetchFragmentCollection(
							groupId, _FRAGMENT_COLLECTION_KEY_DEFAULT);

					if (fragmentCollection == null) {
						Locale locale = _portal.getSiteDefaultLocale(groupId);

						fragmentCollection =
							_fragmentCollectionService.addFragmentCollection(
								groupId, _FRAGMENT_COLLECTION_KEY_DEFAULT,
								LanguageUtil.get(
									locale, _FRAGMENT_COLLECTION_KEY_DEFAULT),
								StringPool.BLANK,
								ServiceContextThreadLocal.getServiceContext());
					}

					fragmentCollectionId =
						fragmentCollection.getFragmentCollectionId();
				}

				_importFragmentCompositions(
					userId, groupId, zipFile, fragmentCollectionId,
					orphanFragmentCompositions, overwrite);

				_importFragmentEntries(
					userId, groupId, zipFile, fragmentCollectionId,
					orphanFragmentEntries, overwrite);
			}
		}

		return _fragmentsImporterResultEntries;
	}

	private FragmentCollection _addFragmentCollection(
			long groupId, String fragmentCollectionKey, String name,
			String description, boolean overwrite)
		throws Exception {

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.fetchFragmentCollection(
				groupId, fragmentCollectionKey);

		if (fragmentCollection == null) {
			fragmentCollection =
				_fragmentCollectionService.addFragmentCollection(
					groupId, fragmentCollectionKey, name, description,
					ServiceContextThreadLocal.getServiceContext());
		}
		else if (overwrite) {
			fragmentCollection =
				_fragmentCollectionService.updateFragmentCollection(
					fragmentCollection.getFragmentCollectionId(), name,
					description);
		}
		else {
			throw new DuplicateFragmentCollectionKeyException(
				fragmentCollectionKey);
		}

		return fragmentCollection;
	}

	private FragmentEntry _addFragmentEntry(
			long fragmentCollectionId, String fragmentEntryKey, String name,
			String css, String html, String js, boolean cacheable,
			String configuration, boolean readOnly, String typeLabel,
			boolean overwrite)
		throws Exception {

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.getFragmentCollection(
				fragmentCollectionId);

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.fetchFragmentEntry(
				fragmentCollection.getGroupId(), fragmentEntryKey);

		if ((fragmentEntry != null) && !overwrite) {
			throw new DuplicateFragmentEntryKeyException(fragmentEntryKey);
		}

		int type = FragmentConstants.getTypeFromLabel(
			StringUtil.toLowerCase(StringUtil.trim(typeLabel)));
		int status = WorkflowConstants.STATUS_APPROVED;
		String errorMessage = null;

		try {
			_fragmentEntryProcessorRegistry.validateFragmentEntryHTML(
				html, configuration);

			_fragmentEntryValidator.validateConfiguration(configuration);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}

			if (type == FragmentConstants.TYPE_REACT) {
				_fragmentsImporterResultEntries.add(
					new FragmentsImporterResultEntry(
						name, FragmentsImporterResultEntry.Status.INVALID,
						portalException.getMessage()));

				return null;
			}

			status = WorkflowConstants.STATUS_DRAFT;
			errorMessage = portalException.getLocalizedMessage();
		}

		try {
			if (fragmentEntry == null) {
				fragmentEntry = _fragmentEntryService.addFragmentEntry(
					fragmentCollection.getGroupId(), fragmentCollectionId,
					fragmentEntryKey, name, css, html, js, cacheable,
					configuration, 0, type, status,
					ServiceContextThreadLocal.getServiceContext());
			}
			else {
				fragmentEntry = _fragmentEntryService.updateFragmentEntry(
					fragmentEntry.getFragmentEntryId(), fragmentCollectionId,
					name, css, html, js, cacheable, configuration,
					fragmentEntry.getPreviewFileEntryId(), status);
			}

			if (fragmentEntry.isReadOnly() != readOnly) {
				fragmentEntry.setReadOnly(readOnly);

				fragmentEntry = _fragmentEntryLocalService.updateFragmentEntry(
					fragmentEntry);
			}

			FragmentsImporterResultEntry.Status
				fragmentsImporterResultEntryStatus =
					FragmentsImporterResultEntry.Status.IMPORTED;

			if (fragmentEntry.getStatus() == WorkflowConstants.STATUS_DRAFT) {
				fragmentsImporterResultEntryStatus =
					FragmentsImporterResultEntry.Status.IMPORTED_DRAFT;
			}

			_fragmentsImporterResultEntries.add(
				new FragmentsImporterResultEntry(
					name, fragmentsImporterResultEntryStatus, errorMessage));

			return fragmentEntry;
		}
		catch (PortalException portalException) {
			_fragmentsImporterResultEntries.add(
				new FragmentsImporterResultEntry(
					name, FragmentsImporterResultEntry.Status.INVALID,
					portalException.getMessage()));
		}

		return null;
	}

	private String _getContent(ZipFile zipFile, String fileName)
		throws Exception {

		ZipEntry zipEntry = zipFile.getEntry(fileName);

		if (zipEntry == null) {
			return StringPool.BLANK;
		}

		return StringUtil.read(zipFile.getInputStream(zipEntry));
	}

	private String _getFileName(String path) {
		int pos = path.lastIndexOf(CharPool.SLASH);

		if (pos > 0) {
			return path.substring(pos + 1);
		}

		return path;
	}

	private Map<String, FragmentCollectionFolder>
			_getFragmentCollectionFolderMap(
				ZipFile zipFile, long groupId,
				Map<String, String> orphanFragmentCompositions,
				Map<String, String> orphanFragmentEntries)
		throws Exception {

		Map<String, FragmentCollectionFolder> fragmentCollectionFolderMap =
			new HashMap<>();

		Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

		while (enumeration.hasMoreElements()) {
			ZipEntry zipEntry = enumeration.nextElement();

			if (zipEntry.isDirectory()) {
				continue;
			}

			String fileName = zipEntry.getName();

			if (!_isFragmentCollection(fileName)) {
				continue;
			}

			fragmentCollectionFolderMap.put(
				_getKey(zipFile, groupId, fileName),
				new FragmentCollectionFolder(fileName));
		}

		enumeration = zipFile.entries();

		while (enumeration.hasMoreElements()) {
			ZipEntry zipEntry = enumeration.nextElement();

			if (zipEntry.isDirectory()) {
				continue;
			}

			String fileName = zipEntry.getName();

			if (!_isFragmentComposition(fileName) &&
				!_isFragmentEntry(fileName)) {

				continue;
			}

			String fragmentCollectionPath = fileName;

			String fragmentCollectionKey = StringPool.BLANK;

			while (fragmentCollectionPath.length() > 0) {
				fragmentCollectionPath = fragmentCollectionPath.substring(
					0,
					fragmentCollectionPath.lastIndexOf(StringPool.SLASH) + 1);

				String fragmentCollectionFileName =
					fragmentCollectionPath +
						FragmentExportImportConstants.FILE_NAME_COLLECTION;

				ZipEntry fragmentCollectionZipEntry = zipFile.getEntry(
					fragmentCollectionFileName);

				if (fragmentCollectionZipEntry != null) {
					fragmentCollectionKey = _getKey(
						zipFile, groupId, fragmentCollectionFileName);

					break;
				}

				if (Validator.isNull(fragmentCollectionPath)) {
					break;
				}

				fragmentCollectionPath = fragmentCollectionPath.substring(
					0, fragmentCollectionPath.lastIndexOf(StringPool.SLASH));
			}

			if (Validator.isNull(fragmentCollectionKey) &&
				_isFragmentComposition(fileName)) {

				orphanFragmentCompositions.put(
					_getKey(zipFile, groupId, fileName), fileName);

				continue;
			}
			else if (Validator.isNull(fragmentCollectionKey) &&
					 _isFragmentEntry(fileName)) {

				orphanFragmentEntries.put(
					_getKey(zipFile, groupId, fileName), fileName);

				continue;
			}

			FragmentCollectionFolder fragmentCollectionFolder =
				fragmentCollectionFolderMap.get(fragmentCollectionKey);

			if ((fragmentCollectionFolder == null) &&
				_isFragmentComposition(fileName)) {

				orphanFragmentCompositions.put(
					_getKey(zipFile, groupId, fileName), fileName);

				continue;
			}
			else if ((fragmentCollectionFolder == null) &&
					 _isFragmentEntry(fileName)) {

				orphanFragmentEntries.put(
					_getKey(zipFile, groupId, fileName), fileName);

				continue;
			}

			if (_isFragmentComposition(fileName)) {
				fragmentCollectionFolder.addFragmentComposition(
					_getKey(zipFile, groupId, fileName), fileName);
			}
			else {
				fragmentCollectionFolder.addFragmentEntry(
					_getKey(zipFile, groupId, fileName), fileName);
			}
		}

		return fragmentCollectionFolderMap;
	}

	private String _getFragmentEntryContent(
			ZipFile zipFile, String fileName, String contentPath)
		throws Exception {

		InputStream inputStream = _getFragmentEntryInputStream(
			zipFile, fileName, contentPath);

		if (inputStream == null) {
			return StringPool.BLANK;
		}

		return StringUtil.read(inputStream);
	}

	private InputStream _getFragmentEntryInputStream(
			ZipFile zipFile, String fileName, String contentPath)
		throws Exception {

		if (contentPath.startsWith(StringPool.SLASH)) {
			return _getInputStream(zipFile, contentPath.substring(1));
		}

		if (contentPath.startsWith("./")) {
			contentPath = contentPath.substring(2);
		}

		String path = fileName.substring(
			0, fileName.lastIndexOf(StringPool.SLASH));

		return _getInputStream(zipFile, path + StringPool.SLASH + contentPath);
	}

	private InputStream _getInputStream(ZipFile zipFile, String fileName)
		throws Exception {

		ZipEntry zipEntry = zipFile.getEntry(fileName);

		if (zipEntry == null) {
			return null;
		}

		return zipFile.getInputStream(zipEntry);
	}

	private String _getKey(ZipFile zipFile, long groupId, String fileName)
		throws Exception {

		String key = StringPool.BLANK;

		if (fileName.lastIndexOf(CharPool.SLASH) != -1) {
			String path = fileName.substring(
				0, fileName.lastIndexOf(CharPool.SLASH));

			key = path.substring(path.lastIndexOf(CharPool.SLASH) + 1);
		}
		else if (fileName.equals(
					FragmentExportImportConstants.FILE_NAME_COLLECTION)) {

			JSONObject collectionJSONObject = JSONFactoryUtil.createJSONObject(
				StringUtil.read(
					zipFile.getInputStream(zipFile.getEntry(fileName))));

			key = _fragmentCollectionLocalService.generateFragmentCollectionKey(
				groupId, collectionJSONObject.getString("name"));
		}
		else if (fileName.equals(
					FragmentExportImportConstants.FILE_NAME_FRAGMENT)) {

			JSONObject fragmentJSONObject = JSONFactoryUtil.createJSONObject(
				StringUtil.read(
					zipFile.getInputStream(zipFile.getEntry(fileName))));

			key = _fragmentEntryLocalService.generateFragmentEntryKey(
				groupId, fragmentJSONObject.getString("name"));
		}

		if (Validator.isNotNull(key)) {
			return key;
		}

		throw new IllegalArgumentException("Incorrect file name " + fileName);
	}

	private long _getPreviewFileEntryId(
			long userId, long groupId, ZipFile zipFile, String className,
			long classPK, String fileName, String contentPath)
		throws Exception {

		InputStream inputStream = _getFragmentEntryInputStream(
			zipFile, fileName, contentPath);

		if (inputStream == null) {
			return 0;
		}

		Repository repository =
			PortletFileRepositoryUtil.fetchPortletRepository(
				groupId, FragmentPortletKeys.FRAGMENT);

		if (repository == null) {
			if ((groupId == CompanyConstants.SYSTEM) &&
				Objects.equals(className, FragmentEntry.class.getName())) {

				FragmentEntry fragmentEntry =
					_fragmentEntryLocalService.getFragmentEntry(classPK);

				Company company = _companyLocalService.getCompany(
					fragmentEntry.getCompanyId());

				groupId = company.getGroupId();
			}
			else if ((groupId == CompanyConstants.SYSTEM) &&
					 Objects.equals(
						 className, FragmentComposition.class.getName())) {

				FragmentComposition fragmentComposition =
					_fragmentCompositionLocalService.getFragmentComposition(
						classPK);

				Company company = _companyLocalService.getCompany(
					fragmentComposition.getCompanyId());

				groupId = company.getGroupId();
			}

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			repository = PortletFileRepositoryUtil.addPortletRepository(
				groupId, FragmentPortletKeys.FRAGMENT, serviceContext);
		}

		FileEntry fileEntry = PortletFileRepositoryUtil.addPortletFileEntry(
			groupId, userId, className, classPK, FragmentPortletKeys.FRAGMENT,
			repository.getDlFolderId(), inputStream,
			classPK + "_preview." + FileUtil.getExtension(contentPath),
			MimeTypesUtil.getContentType(contentPath), false);

		return fileEntry.getFileEntryId();
	}

	private void _importFragmentCompositions(
			long userId, long groupId, ZipFile zipFile,
			long fragmentCollectionId, Map<String, String> fragmentCompositions,
			boolean overwrite)
		throws Exception {

		for (Map.Entry<String, String> entry :
				fragmentCompositions.entrySet()) {

			String compositionJSON = _getContent(zipFile, entry.getValue());

			if (Validator.isNull(compositionJSON)) {
				continue;
			}

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				compositionJSON);

			String name = jsonObject.getString("name", entry.getKey());
			String description = jsonObject.getString("description");
			String definitionData = _getFragmentEntryContent(
				zipFile, entry.getValue(),
				jsonObject.getString("fragmentCompositionDefinitionPath"));

			FragmentComposition fragmentComposition =
				_fragmentCompositionService.fetchFragmentComposition(
					groupId, entry.getKey());

			if (fragmentComposition == null) {
				fragmentComposition =
					_fragmentCompositionService.addFragmentComposition(
						groupId, fragmentCollectionId, entry.getKey(), name,
						description, definitionData, 0L,
						WorkflowConstants.STATUS_APPROVED,
						ServiceContextThreadLocal.getServiceContext());
			}
			else if (!overwrite) {
				throw new DuplicateFragmentCompositionKeyException();
			}
			else {
				fragmentComposition =
					_fragmentCompositionService.updateFragmentComposition(
						fragmentComposition.getFragmentCompositionId(),
						fragmentCollectionId, name, description, definitionData,
						fragmentComposition.getPreviewFileEntryId(),
						fragmentComposition.getStatus());
			}

			if (fragmentComposition.getPreviewFileEntryId() > 0) {
				PortletFileRepositoryUtil.deletePortletFileEntry(
					fragmentComposition.getPreviewFileEntryId());
			}

			String thumbnailPath = jsonObject.getString("thumbnailPath");

			if (Validator.isNotNull(thumbnailPath)) {
				long previewFileEntryId = _getPreviewFileEntryId(
					userId, groupId, zipFile,
					FragmentComposition.class.getName(),
					fragmentComposition.getFragmentCompositionId(),
					entry.getValue(), thumbnailPath);

				_fragmentCompositionService.updateFragmentComposition(
					fragmentComposition.getFragmentCompositionId(),
					previewFileEntryId);
			}
		}
	}

	private void _importFragmentEntries(
			long userId, long groupId, ZipFile zipFile,
			long fragmentCollectionId, Map<String, String> fragmentEntries,
			boolean overwrite)
		throws Exception {

		for (Map.Entry<String, String> entry : fragmentEntries.entrySet()) {
			String name = entry.getKey();
			String css = StringPool.BLANK;
			String html = StringPool.BLANK;
			String js = StringPool.BLANK;
			boolean cacheable = false;
			String configuration = StringPool.BLANK;
			boolean readOnly = false;
			String typeLabel = StringPool.BLANK;

			String fragmentJSON = _getContent(zipFile, entry.getValue());

			if (Validator.isNotNull(fragmentJSON)) {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					fragmentJSON);

				name = jsonObject.getString("name");
				css = _getFragmentEntryContent(
					zipFile, entry.getValue(), jsonObject.getString("cssPath"));
				html = _getFragmentEntryContent(
					zipFile, entry.getValue(),
					jsonObject.getString("htmlPath"));
				js = _getFragmentEntryContent(
					zipFile, entry.getValue(), jsonObject.getString("jsPath"));
				cacheable = jsonObject.getBoolean("cacheable");
				configuration = _getFragmentEntryContent(
					zipFile, entry.getValue(),
					jsonObject.getString("configurationPath"));
				readOnly = jsonObject.getBoolean("readOnly");
				typeLabel = jsonObject.getString("type");
			}

			FragmentEntry fragmentEntry = _addFragmentEntry(
				fragmentCollectionId, entry.getKey(), name, css, html, js,
				cacheable, configuration, readOnly, typeLabel, overwrite);

			if (fragmentEntry == null) {
				continue;
			}

			if (Validator.isNotNull(fragmentJSON)) {
				if (fragmentEntry.getPreviewFileEntryId() > 0) {
					PortletFileRepositoryUtil.deletePortletFileEntry(
						fragmentEntry.getPreviewFileEntryId());
				}

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					fragmentJSON);

				String thumbnailPath = jsonObject.getString("thumbnailPath");

				if (Validator.isNotNull(thumbnailPath)) {
					long previewFileEntryId = _getPreviewFileEntryId(
						userId, groupId, zipFile, FragmentEntry.class.getName(),
						fragmentEntry.getFragmentEntryId(), entry.getValue(),
						thumbnailPath);

					_fragmentEntryLocalService.updateFragmentEntry(
						fragmentEntry.getFragmentEntryId(), previewFileEntryId);
				}
			}
		}
	}

	private void _importResources(
			long userId, long groupId, long fragmentCollectionId, long folderId,
			ZipFile zipFile)
		throws Exception {

		Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

		List<? extends ZipEntry> zipEntries = Collections.list(enumeration);

		Stream<? extends ZipEntry> stream = zipEntries.stream();

		Set<String> excludePaths = stream.filter(
			zipEntry -> {
				String name = zipEntry.getName();

				return name.endsWith(
					FragmentExportImportConstants.FILE_NAME_COLLECTION) ||
					   name.endsWith(
						   FragmentExportImportConstants.FILE_NAME_FRAGMENT);
			}
		).flatMap(
			zipEntry -> {
				String name = zipEntry.getName();

				String path = name.substring(
					0, name.lastIndexOf(StringPool.SLASH) + 1);

				if (name.endsWith(
						FragmentExportImportConstants.FILE_NAME_COLLECTION)) {

					return Arrays.stream(new String[] {name});
				}

				try {
					String fragmentJSON = StringUtil.read(
						zipFile.getInputStream(zipEntry));

					JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
						fragmentJSON);

					return Arrays.stream(
						new String[] {
							path + "fragment.json",
							path + jsonObject.getString("configuration"),
							path + jsonObject.getString("cssPath"),
							path + jsonObject.getString("htmlPath"),
							path + jsonObject.getString("jsPath"),
							path + jsonObject.getString("thumbnailPath")
						});
				}
				catch (Exception exception) {
					_log.error(
						"Unable to read fragments.json file " + name,
						exception);
				}

				return Arrays.stream(new String[0]);
			}
		).collect(
			Collectors.toSet()
		);

		Map<String, String> zipEntryNames = new HashMap<>();

		for (ZipEntry zipEntry : zipEntries) {
			String[] paths = StringUtil.split(
				zipEntry.getName(), StringPool.FORWARD_SLASH);

			if (!ArrayUtil.contains(paths, "resources") ||
				excludePaths.contains(zipEntry.getName())) {

				continue;
			}

			zipEntryNames.put(
				_getFileName(zipEntry.getName()), zipEntry.getName());
		}

		for (FileEntry fileEntry :
				PortletFileRepositoryUtil.getPortletFileEntries(
					groupId, folderId)) {

			if (zipEntryNames.containsKey(fileEntry.getFileName())) {
				PortletFileRepositoryUtil.deletePortletFileEntry(
					fileEntry.getFileEntryId());
			}
		}

		for (Map.Entry<String, String> entry : zipEntryNames.entrySet()) {
			InputStream inputStream = _getInputStream(
				zipFile, entry.getValue());

			String fileName = entry.getKey();

			PortletFileRepositoryUtil.addPortletFileEntry(
				groupId, userId, FragmentCollection.class.getName(),
				fragmentCollectionId, FragmentPortletKeys.FRAGMENT, folderId,
				inputStream, fileName, MimeTypesUtil.getContentType(fileName),
				false);
		}
	}

	private boolean _isFragmentCollection(String fileName) {
		if (Objects.equals(
				_getFileName(fileName),
				FragmentExportImportConstants.FILE_NAME_COLLECTION)) {

			return true;
		}

		return false;
	}

	private boolean _isFragmentComposition(String fileName) {
		if (Objects.equals(
				_getFileName(fileName),
				FragmentExportImportConstants.FILE_NAME_FRAGMENT_COMPOSITION)) {

			return true;
		}

		return false;
	}

	private boolean _isFragmentEntry(String fileName) {
		if (Objects.equals(
				_getFileName(fileName),
				FragmentExportImportConstants.FILE_NAME_FRAGMENT)) {

			return true;
		}

		return false;
	}

	private static final String _FRAGMENT_COLLECTION_KEY_DEFAULT = "imported";

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentsImporterImpl.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Reference
	private FragmentCollectionService _fragmentCollectionService;

	@Reference
	private FragmentCompositionLocalService _fragmentCompositionLocalService;

	@Reference
	private FragmentCompositionService _fragmentCompositionService;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private FragmentEntryProcessorRegistry _fragmentEntryProcessorRegistry;

	@Reference
	private FragmentEntryService _fragmentEntryService;

	@Reference
	private FragmentEntryValidator _fragmentEntryValidator;

	private List<FragmentsImporterResultEntry> _fragmentsImporterResultEntries;

	@Reference
	private Portal _portal;

	private class FragmentCollectionFolder {

		public FragmentCollectionFolder(String fileName) {
			_fileName = fileName;
		}

		public void addFragmentComposition(String key, String fileName) {
			_fragmentCompositions.put(key, fileName);
		}

		public void addFragmentEntry(String key, String fileName) {
			_fragmentEntries.put(key, fileName);
		}

		public String getFileName() {
			return _fileName;
		}

		public Map<String, String> getFragmentCompositions() {
			return _fragmentCompositions;
		}

		public Map<String, String> getFragmentEntries() {
			return _fragmentEntries;
		}

		private final String _fileName;
		private final Map<String, String> _fragmentCompositions =
			new HashMap<>();
		private final Map<String, String> _fragmentEntries = new HashMap<>();

	}

}