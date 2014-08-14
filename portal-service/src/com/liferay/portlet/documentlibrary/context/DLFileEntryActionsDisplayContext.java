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

/**
 * @author Iván Zaera
 */
public interface DLFileEntryActionsDisplayContext extends DLDisplayContext {

	String getPublishButtonLabel() throws PortalException;

	String getSaveButtonLabel() throws PortalException;

	boolean isAssetMetadataVisible() throws PortalException;

	boolean isCancelCheckoutDocumentButtonDisabled() throws PortalException;

	boolean isCancelCheckoutDocumentButtonVisible() throws PortalException;

	boolean isCheckinButtonDisabled() throws PortalException;

	boolean isCheckinButtonVisible() throws PortalException;

	boolean isCheckoutDocumentButtonVisible() throws PortalException;

	boolean isCheckoutDocumentDisabled() throws PortalException;

	boolean isDeleteButtonVisible() throws PortalException;

	boolean isDownloadButtonVisible() throws PortalException;

	boolean isEditButtonVisible() throws PortalException;

	boolean isMoveButtonVisible() throws PortalException;

	boolean isMoveToTheRecycleBinButtonVisible() throws PortalException;

	boolean isOpenInMsOfficeButtonVisible() throws PortalException;

	boolean isPermissionsButtonVisible() throws PortalException;

	boolean isPublishButtonDisabled() throws PortalException;

	boolean isPublishButtonVisible() throws PortalException;

	boolean isSaveButtonDisabled() throws PortalException;

	boolean isSaveButtonVisible() throws PortalException;

	boolean isViewButtonVisible() throws PortalException;

	boolean isViewOriginalFileButtonVisible() throws PortalException;

}