/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.staging;

import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.portal.kernel.util.UnicodeProperties;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Akos Thurzo
 */
@ProviderType
public interface StagingURLHelper {

	public String buildRemoteURL(
		ExportImportConfiguration exportImportConfiguration);

	public String buildRemoteURL(
		String remoteAddress, int remotePort, String remotePathContext,
		boolean secureConnection);

	public String buildRemoteURL(
		UnicodeProperties typeSettingsUnicodeProperties);

}