/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.repository.capabilities;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Adolfo Pérez
 */
@ProviderType
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