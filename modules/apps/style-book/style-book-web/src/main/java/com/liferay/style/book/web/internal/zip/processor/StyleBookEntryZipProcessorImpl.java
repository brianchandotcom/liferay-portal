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

package com.liferay.style.book.web.internal.zip.processor;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactory;
import com.liferay.style.book.constants.StyleBookPortletKeys;
import com.liferay.style.book.exception.DuplicateStyleBookEntryKeyException;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;
import com.liferay.style.book.service.StyleBookEntryService;
import com.liferay.style.book.zip.processor.StyleBookEntryZipProcessor;
import com.liferay.style.book.zip.processor.StyleBookEntryZipProcessorImportResultEntry;

import java.io.File;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = StyleBookEntryZipProcessor.class)
public class StyleBookEntryZipProcessorImpl
	implements StyleBookEntryZipProcessor {

	@Override
	public File exportStyleBookEntries(List<StyleBookEntry> styleBookEntries)
		throws PortletException {

		ZipWriter zipWriter = _zipWriterFactory.getZipWriter();

		try {
			for (StyleBookEntry styleBookEntry : styleBookEntries) {
				styleBookEntry.populateZipWriter(zipWriter, StringPool.BLANK);
			}

			return zipWriter.getFile();
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	@Override
	public List<StyleBookEntryZipProcessorImportResultEntry>
			importStyleBookEntries(
				long userId, long groupId, File file, boolean overwrite)
		throws Exception {

		_importResultEntries = new ArrayList<>();

		try (ZipFile zipFile = new ZipFile(file)) {
			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			while (enumeration.hasMoreElements()) {
				ZipEntry zipEntry = enumeration.nextElement();

				if (zipEntry.isDirectory()) {
					continue;
				}

				String fileName = zipEntry.getName();

				if (!_isStyleBookEntry(fileName)) {
					continue;
				}

				_importStyleBookEntries(
					userId, groupId, zipFile, fileName, overwrite);
			}
		}

		return _importResultEntries;
	}

	private StyleBookEntry _addStyleBookEntry(
			long groupId, String frontendTokensValues, String name,
			boolean overwrite, String styleBookEntryKey)
		throws Exception {

		StyleBookEntry styleBookEntry =
			_styleBookEntryEntryLocalService.fetchStyleBookEntry(
				groupId, styleBookEntryKey);

		if ((styleBookEntry != null) && !overwrite) {
			throw new DuplicateStyleBookEntryKeyException(styleBookEntryKey);
		}

		try {
			if (styleBookEntry == null) {
				styleBookEntry = _styleBookEntryEntryService.addStyleBookEntry(
					groupId, frontendTokensValues, name, styleBookEntryKey,
					ServiceContextThreadLocal.getServiceContext());
			}
			else {
				styleBookEntry =
					_styleBookEntryEntryService.updateStyleBookEntry(
						styleBookEntry.getStyleBookEntryId(),
						frontendTokensValues, name);
			}

			_importResultEntries.add(
				new StyleBookEntryZipProcessorImportResultEntry(
					name,
					StyleBookEntryZipProcessorImportResultEntry.Status.IMPORTED,
					StringPool.BLANK));

			return styleBookEntry;
		}
		catch (PortalException portalException) {
			_importResultEntries.add(
				new StyleBookEntryZipProcessorImportResultEntry(
					name,
					StyleBookEntryZipProcessorImportResultEntry.Status.INVALID,
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
		else if (fileName.equals("style-book.json")) {
			JSONObject styleBookJSONObject = JSONFactoryUtil.createJSONObject(
				StringUtil.read(
					zipFile.getInputStream(zipFile.getEntry(fileName))));

			key = _styleBookEntryEntryLocalService.generateStyleBookEntryKey(
				groupId, styleBookJSONObject.getString("name"));
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

		InputStream inputStream = _getStyleBookEntryInputStream(
			zipFile, fileName, contentPath);

		if (inputStream == null) {
			return 0;
		}

		Repository repository =
			PortletFileRepositoryUtil.fetchPortletRepository(
				groupId, StyleBookPortletKeys.STYLE_BOOK);

		if (repository == null) {
			if (groupId == GroupConstants.DEFAULT_PARENT_GROUP_ID) {
				StyleBookEntry styleBookEntry =
					_styleBookEntryEntryLocalService.getStyleBookEntry(classPK);

				Company company = _companyLocalService.getCompany(
					styleBookEntry.getCompanyId());

				groupId = company.getGroupId();
			}

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			repository = PortletFileRepositoryUtil.addPortletRepository(
				groupId, StyleBookPortletKeys.STYLE_BOOK, serviceContext);
		}

		FileEntry fileEntry = PortletFileRepositoryUtil.addPortletFileEntry(
			groupId, userId, className, classPK,
			StyleBookPortletKeys.STYLE_BOOK, repository.getDlFolderId(),
			inputStream,
			classPK + "_preview." + FileUtil.getExtension(contentPath),
			MimeTypesUtil.getContentType(contentPath), false);

		return fileEntry.getFileEntryId();
	}

	private String _getStyleBookEntryContent(
			ZipFile zipFile, String fileName, String contentPath)
		throws Exception {

		InputStream inputStream = _getStyleBookEntryInputStream(
			zipFile, fileName, contentPath);

		if (inputStream == null) {
			return StringPool.BLANK;
		}

		return StringUtil.read(inputStream);
	}

	private InputStream _getStyleBookEntryInputStream(
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

	private void _importStyleBookEntries(
			long userId, long groupId, ZipFile zipFile, String fileName,
			boolean overwrite)
		throws Exception {

		boolean defaultStyleBookEntry = false;

		String styleBookEntryKey = _getKey(zipFile, groupId, fileName);

		String name = styleBookEntryKey;

		String frontendTokensValues = StringPool.BLANK;

		String styleBookEntryContent = _getContent(zipFile, fileName);

		if (Validator.isNotNull(styleBookEntryContent)) {
			JSONObject styleBookEntryJSONObject =
				JSONFactoryUtil.createJSONObject(styleBookEntryContent);

			defaultStyleBookEntry = styleBookEntryJSONObject.getBoolean(
				"defaultStyleBookEntry");
			frontendTokensValues = _getStyleBookEntryContent(
				zipFile, fileName,
				styleBookEntryJSONObject.getString("frontendTokensValuesPath"));
			name = styleBookEntryJSONObject.getString("name");
		}

		StyleBookEntry styleBookEntry = _addStyleBookEntry(
			groupId, frontendTokensValues, name, overwrite, styleBookEntryKey);

		if (styleBookEntry == null) {
			return;
		}

		if (defaultStyleBookEntry) {
			_styleBookEntryEntryService.updateDefaultStyleBookEntry(
				styleBookEntry.getStyleBookEntryId(), true);
		}

		if (Validator.isNotNull(styleBookEntryContent)) {
			if (styleBookEntry.getPreviewFileEntryId() > 0) {
				PortletFileRepositoryUtil.deletePortletFileEntry(
					styleBookEntry.getPreviewFileEntryId());
			}

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				styleBookEntryContent);

			String thumbnailPath = jsonObject.getString("thumbnailPath");

			if (Validator.isNotNull(thumbnailPath)) {
				_styleBookEntryEntryService.updatePreviewFileEntryId(
					styleBookEntry.getStyleBookEntryId(),
					_getPreviewFileEntryId(
						userId, groupId, zipFile,
						StyleBookEntry.class.getName(),
						styleBookEntry.getStyleBookEntryId(), fileName,
						thumbnailPath));
			}
		}
	}

	private boolean _isStyleBookEntry(String fileName) {
		if (Objects.equals(_getFileName(fileName), "style-book.json")) {
			return true;
		}

		return false;
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	private List<StyleBookEntryZipProcessorImportResultEntry>
		_importResultEntries;

	@Reference
	private StyleBookEntryLocalService _styleBookEntryEntryLocalService;

	@Reference
	private StyleBookEntryService _styleBookEntryEntryService;

	@Reference
	private ZipWriterFactory _zipWriterFactory;

}