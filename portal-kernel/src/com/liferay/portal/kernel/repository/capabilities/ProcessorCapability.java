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
import com.liferay.portal.kernel.repository.model.FileVersion;

/**
 * @author Adolfo Pérez
 */
public interface ProcessorCapability extends Capability {

	public void cleanUp(FileEntry fileEntry) throws PortalException;

	public void cleanUp(FileVersion fileVersion) throws PortalException;

	public void copy(FileEntry fileEntry, FileVersion fileVersion)
		throws PortalException;

	public void generateNew(FileEntry fileEntry) throws PortalException;

	public enum ResourceGenerationStrategy {

		ALWAYS_GENERATE, REUSE

	}

}