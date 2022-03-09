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

package com.liferay.document.library.display.context;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public class BaseDLEditFileEntryDisplayContext
	extends BaseDLDisplayContext<DLEditFileEntryDisplayContext>
	implements DLEditFileEntryDisplayContext {

	public BaseDLEditFileEntryDisplayContext(
		UUID uuid,
		DLEditFileEntryDisplayContext parentDLEditFileEntryDisplayContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		DLFileEntryType dlFileEntryType) {

		super(
			uuid, parentDLEditFileEntryDisplayContext, httpServletRequest,
			httpServletResponse);

		this.dlFileEntryType = dlFileEntryType;
	}

	public BaseDLEditFileEntryDisplayContext(
		UUID uuid,
		DLEditFileEntryDisplayContext parentDLEditFileEntryDisplayContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, FileEntry fileEntry) {

		super(
			uuid, parentDLEditFileEntryDisplayContext, httpServletRequest,
			httpServletResponse);

		this.fileEntry = fileEntry;

		if (fileEntry.getModel() instanceof DLFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

			try {
				dlFileEntryType = dlFileEntry.getDLFileEntryType();
			}
			catch (PortalException portalException) {
				throw new SystemException(portalException);
			}
		}
	}

	@Override
	public DDMFormValues getDDMFormValues(long classPK) throws PortalException {
		return parentDisplayContext.getDDMFormValues(classPK);
	}

	@Override
	public DLFilePicker getDLFilePicker(String onFilePickCallback)
		throws PortalException {

		return parentDisplayContext.getDLFilePicker(onFilePickCallback);
	}

	@Override
	public String getFriendlyURLBase() throws PortalException {
		return parentDisplayContext.getFriendlyURLBase();
	}

	@Override
	public long getMaximumUploadRequestSize() throws PortalException {
		return parentDisplayContext.getMaximumUploadRequestSize();
	}

	@Override
	public long getMaximumUploadSize() throws PortalException {
		return parentDisplayContext.getMaximumUploadSize();
	}

	@Override
	public String getPublishButtonLabel() throws PortalException {
		return parentDisplayContext.getPublishButtonLabel();
	}

	@Override
	public String getSaveButtonLabel() throws PortalException {
		return parentDisplayContext.getSaveButtonLabel();
	}

	@Override
	public boolean isCancelCheckoutDocumentButtonDisabled()
		throws PortalException {

		return parentDisplayContext.isCancelCheckoutDocumentButtonDisabled();
	}

	@Override
	public boolean isCancelCheckoutDocumentButtonVisible()
		throws PortalException {

		return parentDisplayContext.isCancelCheckoutDocumentButtonVisible();
	}

	@Override
	public boolean isCheckinButtonDisabled() throws PortalException {
		return parentDisplayContext.isCheckinButtonDisabled();
	}

	@Override
	public boolean isCheckinButtonVisible() throws PortalException {
		return parentDisplayContext.isCheckinButtonVisible();
	}

	@Override
	public boolean isCheckoutDocumentButtonDisabled() throws PortalException {
		return parentDisplayContext.isCheckoutDocumentButtonDisabled();
	}

	@Override
	public boolean isCheckoutDocumentButtonVisible() throws PortalException {
		return parentDisplayContext.isCheckoutDocumentButtonVisible();
	}

	@Override
	public boolean isDDMStructureVisible(DDMStructure ddmStructure)
		throws PortalException {

		return parentDisplayContext.isDDMStructureVisible(ddmStructure);
	}

	@Override
	public boolean isFolderSelectionVisible() throws PortalException {
		return parentDisplayContext.isFolderSelectionVisible();
	}

	@Override
	public boolean isPermissionsVisible() throws PortalException {
		return parentDisplayContext.isPermissionsVisible();
	}

	@Override
	public boolean isPublishButtonDisabled() throws PortalException {
		return parentDisplayContext.isPublishButtonDisabled();
	}

	@Override
	public boolean isPublishButtonVisible() throws PortalException {
		return parentDisplayContext.isPublishButtonVisible();
	}

	@Override
	public boolean isSaveButtonDisabled() throws PortalException {
		return parentDisplayContext.isSaveButtonDisabled();
	}

	@Override
	public boolean isSaveButtonVisible() throws PortalException {
		return parentDisplayContext.isSaveButtonVisible();
	}

	@Override
	public boolean isVersionInfoVisible() throws PortalException {
		return parentDisplayContext.isVersionInfoVisible();
	}

	protected DLFileEntryType dlFileEntryType;
	protected FileEntry fileEntry;

}