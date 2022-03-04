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

package com.liferay.headless.commerce.admin.catalog.internal.util.v1_0;

import com.liferay.commerce.product.exception.CPAttachmentFileEntryProtocolException;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Attachment;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.AttachmentBase64;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.AttachmentUrl;
import com.liferay.headless.commerce.admin.catalog.internal.util.DateConfigUtil;
import com.liferay.headless.commerce.core.util.DateConfig;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.upload.UniqueFileNameProvider;

import java.io.File;

import java.net.URL;
import java.net.URLConnection;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Alessio Antonio Rendina
 */
public class AttachmentUtil {

	public static FileEntry addFileEntry(
			Attachment attachment,
			UniqueFileNameProvider uniqueFileNameProvider,
			ServiceContext serviceContext)
		throws Exception {

		if (Validator.isNotNull(attachment.getAttachment())) {
			return addFileEntry(
				attachment, uniqueFileNameProvider, serviceContext);
		}

		if (Validator.isNotNull(attachment.getSrc())) {
			URL url = new URL(attachment.getSrc());

			if (Objects.equals("file", url.getProtocol())) {
				throw new CPAttachmentFileEntryProtocolException(
					"Unsupported URL protocol");
			}

			URLConnection urlConnection = url.openConnection();

			urlConnection.connect();

			File file = FileUtil.createTempFile(urlConnection.getInputStream());

			return _addFileEntry(
				file, attachment.getContentType(), uniqueFileNameProvider,
				serviceContext);
		}

		return null;
	}

	public static FileEntry addFileEntry(
			AttachmentBase64 attachmentBase64,
			UniqueFileNameProvider uniqueFileNameProvider,
			ServiceContext serviceContext)
		throws Exception {

		String base64EncodedContent = attachmentBase64.getAttachment();

		if (Validator.isNull(base64EncodedContent)) {
			return null;
		}

		File file = FileUtil.createTempFile(
			Base64.decode(base64EncodedContent));

		return _addFileEntry(
			file, attachmentBase64.getContentType(), uniqueFileNameProvider,
			serviceContext);
	}

	public static FileEntry addFileEntry(
			AttachmentUrl attachmentUrl,
			UniqueFileNameProvider uniqueFileNameProvider,
			ServiceContext serviceContext)
		throws Exception {

		if (Validator.isNull(attachmentUrl.getSrc())) {
			return null;
		}

		File file = FileUtil.createTempFile(
			HttpUtil.URLtoInputStream(attachmentUrl.getSrc()));

		return _addFileEntry(
			file, attachmentUrl.getContentType(), uniqueFileNameProvider,
			serviceContext);
	}

	public static CPAttachmentFileEntry addOrUpdateCPAttachmentFileEntry(
			CPAttachmentFileEntryService cpAttachmentFileEntryService,
			UniqueFileNameProvider uniqueFileNameProvider,
			AttachmentBase64 attachmentBase64, long classNameId, long classPK,
			int type, ServiceContext serviceContext)
		throws Exception {

		Calendar displayCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		if (attachmentBase64.getDisplayDate() != null) {
			displayCalendar = DateConfigUtil.convertDateToCalendar(
				attachmentBase64.getDisplayDate());
		}

		DateConfig displayDateConfig = new DateConfig(displayCalendar);

		Calendar expirationCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		expirationCalendar.add(Calendar.MONTH, 1);

		if (attachmentBase64.getExpirationDate() != null) {
			expirationCalendar = DateConfigUtil.convertDateToCalendar(
				attachmentBase64.getExpirationDate());
		}

		DateConfig expirationDateConfig = new DateConfig(expirationCalendar);

		long fileEntryId = 0;

		FileEntry fileEntry = addFileEntry(
			attachmentBase64, uniqueFileNameProvider, serviceContext);

		if (fileEntry != null) {
			fileEntryId = fileEntry.getFileEntryId();
		}

		return cpAttachmentFileEntryService.addOrUpdateCPAttachmentFileEntry(
			attachmentBase64.getExternalReferenceCode(),
			serviceContext.getScopeGroupId(), classNameId, classPK,
			GetterUtil.getLong(attachmentBase64.getId()), fileEntryId, false,
			null, displayDateConfig.getMonth(), displayDateConfig.getDay(),
			displayDateConfig.getYear(), displayDateConfig.getHour(),
			displayDateConfig.getMinute(), expirationDateConfig.getMonth(),
			expirationDateConfig.getDay(), expirationDateConfig.getYear(),
			expirationDateConfig.getHour(), expirationDateConfig.getMinute(),
			GetterUtil.get(attachmentBase64.getNeverExpire(), false),
			getTitleMap(null, attachmentBase64.getTitle()),
			GetterUtil.getString(attachmentBase64.getOptions()),
			GetterUtil.getDouble(attachmentBase64.getPriority()), type,
			serviceContext);
	}

	public static CPAttachmentFileEntry addOrUpdateCPAttachmentFileEntry(
			CPAttachmentFileEntryService cpAttachmentFileEntryService,
			UniqueFileNameProvider uniqueFileNameProvider,
			AttachmentUrl attachmentUrl, long classNameId, long classPK,
			int type, ServiceContext serviceContext)
		throws Exception {

		Calendar displayCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		if (attachmentUrl.getDisplayDate() != null) {
			displayCalendar = DateConfigUtil.convertDateToCalendar(
				attachmentUrl.getDisplayDate());
		}

		DateConfig displayDateConfig = new DateConfig(displayCalendar);

		Calendar expirationCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		expirationCalendar.add(Calendar.MONTH, 1);

		if (attachmentUrl.getExpirationDate() != null) {
			expirationCalendar = DateConfigUtil.convertDateToCalendar(
				attachmentUrl.getExpirationDate());
		}

		DateConfig expirationDateConfig = new DateConfig(expirationCalendar);

		long fileEntryId = 0;

		FileEntry fileEntry = addFileEntry(
			attachmentUrl, uniqueFileNameProvider, serviceContext);

		if (fileEntry != null) {
			fileEntryId = fileEntry.getFileEntryId();
		}

		return cpAttachmentFileEntryService.addOrUpdateCPAttachmentFileEntry(
			attachmentUrl.getExternalReferenceCode(),
			serviceContext.getScopeGroupId(), classNameId, classPK,
			GetterUtil.getLong(attachmentUrl.getId()), fileEntryId, false, null,
			displayDateConfig.getMonth(), displayDateConfig.getDay(),
			displayDateConfig.getYear(), displayDateConfig.getHour(),
			displayDateConfig.getMinute(), expirationDateConfig.getMonth(),
			expirationDateConfig.getDay(), expirationDateConfig.getYear(),
			expirationDateConfig.getHour(), expirationDateConfig.getMinute(),
			GetterUtil.get(attachmentUrl.getNeverExpire(), false),
			getTitleMap(null, attachmentUrl.getTitle()),
			GetterUtil.getString(attachmentUrl.getOptions()),
			GetterUtil.getDouble(attachmentUrl.getPriority()), type,
			serviceContext);
	}

	public static CPAttachmentFileEntry addOrUpdateCPAttachmentFileEntry(
			long groupId,
			CPAttachmentFileEntryService cpAttachmentFileEntryService,
			UniqueFileNameProvider uniqueFileNameProvider,
			Attachment attachment, long classNameId, long classPK, int type,
			ServiceContext serviceContext)
		throws Exception {

		Calendar displayCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		if (attachment.getDisplayDate() != null) {
			displayCalendar = DateConfigUtil.convertDateToCalendar(
				attachment.getDisplayDate());
		}

		DateConfig displayDateConfig = new DateConfig(displayCalendar);

		Calendar expirationCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		expirationCalendar.add(Calendar.MONTH, 1);

		if (attachment.getExpirationDate() != null) {
			expirationCalendar = DateConfigUtil.convertDateToCalendar(
				attachment.getExpirationDate());
		}

		DateConfig expirationDateConfig = new DateConfig(expirationCalendar);

		long fileEntryId = 0;

		FileEntry fileEntry = addFileEntry(
			attachment, uniqueFileNameProvider, serviceContext);

		if (fileEntry != null) {
			fileEntryId = fileEntry.getFileEntryId();
		}

		return cpAttachmentFileEntryService.addOrUpdateCPAttachmentFileEntry(
			attachment.getExternalReferenceCode(), groupId, classNameId,
			classPK, GetterUtil.getLong(attachment.getId()), fileEntryId,
			GetterUtil.get(attachment.getCdnEnabled(), false),
			GetterUtil.getString(attachment.getCdnURL()),
			displayDateConfig.getMonth(), displayDateConfig.getDay(),
			displayDateConfig.getYear(), displayDateConfig.getHour(),
			displayDateConfig.getMinute(), expirationDateConfig.getMonth(),
			expirationDateConfig.getDay(), expirationDateConfig.getYear(),
			expirationDateConfig.getHour(), expirationDateConfig.getMinute(),
			GetterUtil.get(attachment.getNeverExpire(), false),
			getTitleMap(null, attachment.getTitle()),
			GetterUtil.getString(attachment.getOptions()),
			GetterUtil.getDouble(attachment.getPriority()), type,
			serviceContext);
	}

	public static Map<Locale, String> getTitleMap(
			CPAttachmentFileEntry cpAttachmentFileEntry,
			Map<String, String> titleMap)
		throws PortalException {

		if (titleMap != null) {
			return LanguageUtils.getLocalizedMap(titleMap);
		}

		if (cpAttachmentFileEntry == null) {
			return null;
		}

		return cpAttachmentFileEntry.getTitleMap();
	}

	private static FileEntry _addFileEntry(
			File file, String contentType,
			UniqueFileNameProvider uniqueFileNameProvider,
			ServiceContext serviceContext)
		throws Exception {

		String uniqueFileName = uniqueFileNameProvider.provide(
			file.getName(),
			curFileName -> _exists(
				serviceContext.getScopeGroupId(), serviceContext.getUserId(),
				curFileName));

		if (Validator.isNull(contentType)) {
			contentType = MimeTypesUtil.getContentType(file);
		}

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			null, serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, uniqueFileName,
			contentType, uniqueFileName, StringPool.BLANK, null,
			StringPool.BLANK, file, null, null, serviceContext);

		FileUtil.delete(file);

		return fileEntry;
	}

	private static boolean _exists(
		long groupId, long userId, String curFileName) {

		try {
			FileEntry fileEntry = TempFileEntryUtil.getTempFileEntry(
				groupId, userId, _TEMP_FILE_NAME, curFileName);

			if (fileEntry != null) {
				return true;
			}

			return false;
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return false;
		}
	}

	private static final String _TEMP_FILE_NAME =
		AttachmentUtil.class.getName();

	private static final Log _log = LogFactoryUtil.getLog(AttachmentUtil.class);

}