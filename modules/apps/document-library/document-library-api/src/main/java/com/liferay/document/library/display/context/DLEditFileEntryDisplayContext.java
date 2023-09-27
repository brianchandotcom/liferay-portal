/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.display.context;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

/**
 * @author Iván Zaera
 */
public interface DLEditFileEntryDisplayContext extends DLDisplayContext {

	public default Map<String, Long> getAllMimeTypeSizeLimit()
		throws PortalException {

		return Collections.emptyMap();
	}

	public DDMFormValues getDDMFormValues(
			DDMStructure ddmStructure, long fileVersionId)
		throws PortalException;

	public DDMFormValues getDDMFormValues(long classPK) throws PortalException;

	public String getDLFileEntryTypeLanguageId(
		DDMStructure ddmStructure, Locale locale);

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