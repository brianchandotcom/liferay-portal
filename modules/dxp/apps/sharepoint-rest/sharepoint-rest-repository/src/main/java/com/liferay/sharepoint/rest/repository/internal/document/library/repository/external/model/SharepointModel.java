/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.rest.repository.internal.document.library.repository.external.model;

import com.liferay.document.library.repository.external.ExtRepositoryFileEntry;
import com.liferay.document.library.repository.external.ExtRepositoryFileVersion;

/**
 * @author Adolfo Pérez
 */
public interface SharepointModel {

	public ExtRepositoryFileVersion asFileVersion();

	public String getCanonicalContentURL();

	public ExtRepositoryFileEntry getExtRepositoryFileEntry();

}