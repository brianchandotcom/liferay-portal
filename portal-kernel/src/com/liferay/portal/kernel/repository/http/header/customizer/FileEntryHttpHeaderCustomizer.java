/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.repository.http.header.customizer;

import com.liferay.portal.kernel.repository.model.FileEntry;

/**
 * @author Adolfo Pérez
 */
public interface FileEntryHttpHeaderCustomizer {

	public String getHttpHeaderValue(FileEntry fileEntry, String currentValue);

}