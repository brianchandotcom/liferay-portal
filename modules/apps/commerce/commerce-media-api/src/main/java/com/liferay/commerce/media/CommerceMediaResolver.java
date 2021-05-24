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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alec Sloan
 * @author Alessio Antonio Rendina
 */
@ProviderType
public interface CommerceMediaResolver {

	public String getDefaultUrl(long groupId);

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #getDownloadURL(long, long)}}
	 */
	@Deprecated
	public String getDownloadUrl(long cpAttachmentFileEntryId)
		throws PortalException;

	public String getDownloadURL(
			long commerceAccountId, long cpAttachmentFileEntryId)
		throws PortalException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public byte[] getMediaBytes(HttpServletRequest httpServletRequest)
		throws IOException, PortalException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #getThumbnailURL(long, long)}}
	 */
	@Deprecated
	public String getThumbnailUrl(long cpAttachmentFileEntryId)
		throws PortalException;

	public String getThumbnailURL(
			long commerceAccountId, long cpAttachmentFileEntryId)
		throws PortalException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #getURL(long, long)}}
	 */
	@Deprecated
	public String getUrl(long cpAttachmentFileEntryId) throws PortalException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #getURL(long, long, boolean, boolean)}}
	 */
	@Deprecated
	public String getUrl(
			long cpAttachmentFileEntryId, boolean download, boolean thumbnail)
		throws PortalException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #getURL(long, long, boolean, boolean, boolean)}}
	 */
	@Deprecated
	public String getUrl(
			long cpAttachmentFileEntryId, boolean download, boolean thumbnail,
			boolean secure)
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

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public void sendMediaBytes(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public void sendMediaBytes(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String download)
		throws IOException;

}