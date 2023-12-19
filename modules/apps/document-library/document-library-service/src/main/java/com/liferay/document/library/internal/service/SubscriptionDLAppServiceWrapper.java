/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.service;

import com.liferay.document.library.internal.DLAssetDisplayPageUtil;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppServiceWrapper;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.Portal;

import java.io.File;
import java.io.InputStream;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(service = ServiceWrapper.class)
public class SubscriptionDLAppServiceWrapper extends DLAppServiceWrapper {

	@Override
	public FileEntry addFileEntry(
			String externalReferenceCode, long repositoryId, long folderId,
			String sourceFileName, String mimeType, String title,
			String urlTitle, String description, String changeLog, byte[] bytes,
			Date expirationDate, Date reviewDate, ServiceContext serviceContext)
		throws PortalException {

		serviceContext.setAttribute(
			"hasAssetDisplayPage",
			DLAssetDisplayPageUtil.hasAssetDisplayPage(serviceContext));

		return super.addFileEntry(
			externalReferenceCode, repositoryId, folderId, sourceFileName,
			mimeType, title, urlTitle, description, changeLog, bytes,
			expirationDate, reviewDate, serviceContext);
	}

	@Override
	public FileEntry addFileEntry(
			String externalReferenceCode, long repositoryId, long folderId,
			String sourceFileName, String mimeType, String title,
			String urlTitle, String description, String changeLog, File file,
			Date expirationDate, Date reviewDate, ServiceContext serviceContext)
		throws PortalException {

		serviceContext.setAttribute(
			"hasAssetDisplayPage",
			DLAssetDisplayPageUtil.hasAssetDisplayPage(serviceContext));

		return super.addFileEntry(
			externalReferenceCode, repositoryId, folderId, sourceFileName,
			mimeType, title, urlTitle, description, changeLog, file,
			expirationDate, reviewDate, serviceContext);
	}

	@Override
	public FileEntry addFileEntry(
			String externalReferenceCode, long repositoryId, long folderId,
			String sourceFileName, String mimeType, String title,
			String urlTitle, String description, String changeLog,
			InputStream inputStream, long size, Date expirationDate,
			Date reviewDate, ServiceContext serviceContext)
		throws PortalException {

		serviceContext.setAttribute(
			"hasAssetDisplayPage",
			DLAssetDisplayPageUtil.hasAssetDisplayPage(serviceContext));

		return super.addFileEntry(
			externalReferenceCode, repositoryId, folderId, sourceFileName,
			mimeType, title, urlTitle, description, changeLog, inputStream,
			size, expirationDate, reviewDate, serviceContext);
	}

	@Override
	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String urlTitle, String description, String changeLog,
			DLVersionNumberIncrease dlVersionNumberIncrease, byte[] bytes,
			Date expirationDate, Date reviewDate, ServiceContext serviceContext)
		throws PortalException {

		serviceContext.setAttribute(
			"hasAssetDisplayPage",
			DLAssetDisplayPageUtil.hasAssetDisplayPage(serviceContext));

		return super.updateFileEntry(
			fileEntryId, sourceFileName, mimeType, title, urlTitle, description,
			changeLog, dlVersionNumberIncrease, bytes, expirationDate,
			reviewDate, serviceContext);
	}

	@Override
	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String urlTitle, String description, String changeLog,
			DLVersionNumberIncrease dlVersionNumberIncrease, File file,
			Date expirationDate, Date reviewDate, ServiceContext serviceContext)
		throws PortalException {

		serviceContext.setAttribute(
			"hasAssetDisplayPage",
			DLAssetDisplayPageUtil.hasAssetDisplayPage(serviceContext));

		return super.updateFileEntry(
			fileEntryId, sourceFileName, mimeType, title, urlTitle, description,
			changeLog, dlVersionNumberIncrease, file, expirationDate,
			reviewDate, serviceContext);
	}

	@Override
	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String urlTitle, String description, String changeLog,
			DLVersionNumberIncrease dlVersionNumberIncrease,
			InputStream inputStream, long size, Date expirationDate,
			Date reviewDate, ServiceContext serviceContext)
		throws PortalException {

		serviceContext.setAttribute(
			"hasAssetDisplayPage",
			DLAssetDisplayPageUtil.hasAssetDisplayPage(serviceContext));

		return super.updateFileEntry(
			fileEntryId, sourceFileName, mimeType, title, urlTitle, description,
			changeLog, dlVersionNumberIncrease, inputStream, size,
			expirationDate, reviewDate, serviceContext);
	}

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private Portal _portal;

}