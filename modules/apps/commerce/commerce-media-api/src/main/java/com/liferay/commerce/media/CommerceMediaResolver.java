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

package com.liferay.commerce.media;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alec Sloan
 * @author Alessio Antonio Rendina
 */
@ProviderType
public interface CommerceMediaResolver {

	public String getDefaultURL(long groupId);

	public String getDownloadURL(
			long commerceAccountId, long cpAttachmentFileEntryId)
		throws PortalException;

	public String getDownloadVirtualOrderItemURL(
			long commerceVirtualOrderItemId)
		throws PortalException;

	public String getDownloadVirtualProductSampleURL(
			String className, long classPK, long commerceAccountId,
			long fileEntryId)
		throws PortalException;

	public String getDownloadVirtualProductURL(
			String className, long classPK, long commerceAccountId,
			long fileEntryId)
		throws PortalException;

	public String getThumbnailURL(
			long commerceAccountId, long cpAttachmentFileEntryId)
		throws PortalException;

	public String getURL(long commerceAccountId, long cpAttachmentFileEntryId)
		throws PortalException;

	public String getURL(
			long commerceAccountId, long cpAttachmentFileEntryId,
			boolean download, boolean thumbnail)
		throws PortalException;

	public String getURL(
			long commerceAccountId, long cpAttachmentFileEntryId,
			boolean download, boolean thumbnail, boolean secure)
		throws PortalException;

}