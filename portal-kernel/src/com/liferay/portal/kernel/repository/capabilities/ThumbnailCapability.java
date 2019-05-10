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