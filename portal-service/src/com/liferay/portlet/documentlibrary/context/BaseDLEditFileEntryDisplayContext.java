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

package com.liferay.portlet.documentlibrary.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;

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
		HttpServletRequest request, HttpServletResponse response,
		DLFileEntryType dlFileEntryType) {

		super(uuid, parentDLEditFileEntryDisplayContext, request, response);

		this.dlFileEntryType = dlFileEntryType;
	}

	public BaseDLEditFileEntryDisplayContext(
		UUID uuid,
		DLEditFileEntryDisplayContext parentDLEditFileEntryDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		FileEntry fileEntry) {

		super(uuid, parentDLEditFileEntryDisplayContext, request, response);

		this.fileEntry = fileEntry;

		if (fileEntry.getModel() instanceof DLFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

			try {
				this.dlFileEntryType = dlFileEntry.getDLFileEntryType();
			}
			catch (PortalException pe) {
				throw new SystemException(pe);
			}
		}
	}

	@Override
	public long getMaximumUploadSize() throws PortalException {
		return parentDLDisplayContext.getMaximumUploadSize();
	}

	@Override
	public String getPublishButtonLabel() throws PortalException {
		return parentDLDisplayContext.getPublishButtonLabel();
	}

	@Override
	public String getSaveButtonLabel() throws PortalException {
		return parentDLDisplayContext.getSaveButtonLabel();
	}

	@Override
	public boolean isCancelCheckoutDocumentButtonDisabled()
		throws PortalException {

		return parentDLDisplayContext.isCancelCheckoutDocumentButtonDisabled();
	}

	@Override
	public boolean isCancelCheckoutDocumentButtonVisible()
		throws PortalException {

		return parentDLDisplayContext.isCancelCheckoutDocumentButtonVisible();
	}

	@Override
	public boolean isCheckinButtonDisabled() throws PortalException {
		return parentDLDisplayContext.isCheckinButtonDisabled();
	}

	@Override
	public boolean isCheckinButtonVisible() throws PortalException {
		return parentDLDisplayContext.isCheckinButtonVisible();
	}

	@Override
	public boolean isCheckoutDocumentButtonDisabled() throws PortalException {
		return parentDLDisplayContext.isCheckoutDocumentButtonDisabled();
	}

	@Override
	public boolean isCheckoutDocumentButtonVisible() throws PortalException {
		return parentDLDisplayContext.isCheckoutDocumentButtonVisible();
	}

	@Override
	public boolean isDDMStructureVisible(DDMStructure ddmStructure)
		throws PortalException {

		return parentDLDisplayContext.isDDMStructureVisible(ddmStructure);
	}

	@Override
	public boolean isPublishButtonDisabled() throws PortalException {
		return parentDLDisplayContext.isPublishButtonDisabled();
	}

	@Override
	public boolean isPublishButtonVisible() throws PortalException {
		return parentDLDisplayContext.isPublishButtonVisible();
	}

	@Override
	public boolean isSaveButtonDisabled() throws PortalException {
		return parentDLDisplayContext.isSaveButtonDisabled();
	}

	@Override
	public boolean isSaveButtonVisible() throws PortalException {
		return parentDLDisplayContext.isSaveButtonVisible();
	}

	protected DLFileEntryType dlFileEntryType;
	protected FileEntry fileEntry;

}