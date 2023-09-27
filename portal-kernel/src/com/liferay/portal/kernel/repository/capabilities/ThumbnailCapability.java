/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.repository.capabilities;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Iván Zaera
 */
@ProviderType
public interface ThumbnailCapability extends Capability {

	public FileEntry fetchImageFileEntry(long imageId);

	public long getCustom1ImageId(FileEntry fileEntry);

	public long getCustom2ImageId(FileEntry fileEntry);

	public long getLargeImageId(FileEntry fileEntry);

	public long getSmallImageId(FileEntry fileEntry);

	public FileEntry setCustom1ImageId(FileEntry fileEntry, long imageId)
		throws PortalException;

	public FileEntry setCustom2ImageId(FileEntry fileEntry, long imageId)
		throws PortalException;

	public FileEntry setLargeImageId(FileEntry fileEntry, long imageId)
		throws PortalException;

	public FileEntry setSmallImageId(FileEntry fileEntry, long imageId)
		throws PortalException;

}