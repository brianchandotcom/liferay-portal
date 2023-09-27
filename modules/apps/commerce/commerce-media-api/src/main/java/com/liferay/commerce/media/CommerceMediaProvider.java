/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.media;

import com.liferay.portal.kernel.repository.model.FileEntry;

/**
 * @author Alec Sloan
 */
public interface CommerceMediaProvider {

	public FileEntry getDefaultImageFileEntry(long companyId, long groupId)
		throws Exception;

}