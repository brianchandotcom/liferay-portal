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

import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Iván Zaera
 */
public interface DLEditFileEntryDisplayContext extends DLDisplayContext {

	public DDMFormValues getDDMFormValues(long classPK) throws PortalException;

	public DLFilePicker getDLFilePicker(String onFilePickCallback)
		throws PortalException;

	public default String getFriendlyURLBase() throws PortalException {
		return null;
	}

	public long getMaximumUploadRequestSize() throws PortalException;

	public long getMaximumUploadSize() throws PortalException;

	public String getPublishButtonLabel() throws PortalException;

	public String getSaveButtonLabel() throws PortalException;

	public boolean isCancelCheckoutDocumentButtonDisabled()
		throws PortalException;

	public boolean isCancelCheckoutDocumentButtonVisible()
		throws PortalException;

	public boolean isCheckinButtonDisabled() throws PortalException;

	public boolean isCheckinButtonVisible() throws PortalException;

	public boolean isCheckoutDocumentButtonDisabled() throws PortalException;

	public boolean isCheckoutDocumentButtonVisible() throws PortalException;

	public boolean isDDMStructureVisible(DDMStructure ddmStructure)
		throws PortalException;

	public default boolean isFileNameVisible() throws PortalException {
		return true;
	}

	public boolean isFolderSelectionVisible() throws PortalException;

	public default boolean isNeverExpire() throws PortalException {
		return true;
	}

	public default boolean isNeverReview() throws PortalException {
		return true;
	}

	public default boolean isPermissionsVisible() throws PortalException {
		return true;
	}

	public boolean isPublishButtonDisabled() throws PortalException;

	public boolean isPublishButtonVisible() throws PortalException;

	public boolean isSaveButtonDisabled() throws PortalException;

	public boolean isSaveButtonVisible() throws PortalException;

	public boolean isVersionInfoVisible() throws PortalException;

}