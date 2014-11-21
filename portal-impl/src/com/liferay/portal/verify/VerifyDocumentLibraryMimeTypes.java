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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.documentlibrary.webdav.DLWebDAVStorageImpl;

import java.io.InputStream;

/**
 * @author Michael C. Han
 */
public class VerifyDocumentLibraryMimeTypes extends VerifyProcess {

	protected void checkFileEntryMimeTypes(final String originalMimeType)
		throws Exception {

		ActionableDynamicQuery actionableDynamicQuery =
			DLFileEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property property = PropertyFactoryUtil.forName("mimeType");

					dynamicQuery.add(property.eq(originalMimeType));
				}

			});
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					DLFileEntry dlFileEntry = (DLFileEntry)object;

					InputStream inputStream = null;

					try {
						inputStream =
							DLFileEntryLocalServiceUtil.getFileAsStream(
								dlFileEntry.getFileEntryId(),
								dlFileEntry.getVersion(), false);
					}
					catch (Exception e) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to find file entry " +
									dlFileEntry.getName(),
								e);
						}

						return;
					}

					String title = DLUtil.getTitleWithExtension(
						dlFileEntry.getTitle(), dlFileEntry.getExtension());

					String mimeType = getMimeType(inputStream, title);

					if (mimeType.equals(originalMimeType)) {
						return;
					}

					dlFileEntry.setMimeType(mimeType);

					DLFileEntryLocalServiceUtil.updateDLFileEntry(dlFileEntry);

					DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

					dlFileVersion.setMimeType(mimeType);

					DLFileVersionLocalServiceUtil.updateDLFileVersion(
						dlFileVersion);
				}

			});

		actionableDynamicQuery.performActions();
	}

	protected void checkFileVersionMimeTypes(final String originalMimeType)
		throws Exception {

		ActionableDynamicQuery actionableDynamicQuery =
			DLFileVersionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property property = PropertyFactoryUtil.forName("mimeType");

					dynamicQuery.add(property.eq(originalMimeType));
				}

			});

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object) {
					DLFileVersion dlFileVersion = (DLFileVersion)object;

					InputStream inputStream = null;

					try {
						inputStream =
							DLFileEntryLocalServiceUtil.getFileAsStream(
								dlFileVersion.getFileEntryId(),
								dlFileVersion.getVersion(), false);
					}
					catch (Exception e) {
						if (_log.isWarnEnabled()) {
							DLFileEntry dlFileEntry =
								DLFileEntryLocalServiceUtil.fetchDLFileEntry(
									dlFileVersion.getFileEntryId());

							if (dlFileEntry == null) {
								_log.warn(
									"Unable to find file entry associated " +
										"with file version " +
											dlFileVersion.getFileVersionId(),
									e);
							}
							else {
								StringBundler sb = new StringBundler(4);

								sb.append("Unable to find file version ");
								sb.append(dlFileVersion.getVersion());
								sb.append(" for file entry ");
								sb.append(dlFileEntry.getName());

								_log.warn(sb.toString(), e);
							}
						}

						return;
					}

					String title = DLUtil.getTitleWithExtension(
						dlFileVersion.getTitle(), dlFileVersion.getExtension());

					String mimeType = getMimeType(inputStream, title);

					if (mimeType.equals(originalMimeType)) {
						return;
					}

					dlFileVersion.setMimeType(mimeType);

					DLFileVersionLocalServiceUtil.updateDLFileVersion(
						dlFileVersion);
				}

			});

		actionableDynamicQuery.performActions();
	}

	protected void checkMimeTypes() throws Exception {
		String[] mimeTypes = {
			ContentTypes.APPLICATION_OCTET_STREAM,
			DLWebDAVStorageImpl.MS_OFFICE_2010_TEXT_XML_UTF8
		};

		for (String mimeType : mimeTypes) {
			checkFileEntryMimeTypes(mimeType);
			checkFileVersionMimeTypes(mimeType);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Fixed file entries with invalid mime types");
		}
	}

	@Override
	protected void doVerify() throws Exception {
		checkMimeTypes();
	}

	protected String getMimeType(InputStream inputStream, String title) {
		String mimeType = null;

		try {
			mimeType = MimeTypesUtil.getContentType(inputStream, title);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}

		return mimeType;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VerifyDocumentLibraryMimeTypes.class);

}