/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.url;

import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.net.URI;

/**
 * @author Alejandro Tardín
 */
public interface AMImageURLFactory {

	public URI createFileEntryURL(
		FileVersion fileVersion,
		AMImageConfigurationEntry amImageConfigurationEntry);

	public URI createFileVersionURL(
		FileVersion fileVersion,
		AMImageConfigurationEntry amImageConfigurationEntry);

}