/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.repository.friendly.url.resolver;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;

/**
 * @author Adolfo Pérez
 */
public interface FileEntryFriendlyURLResolver {

	public FileEntry resolveFriendlyURL(long groupId, String friendlyURL)
		throws PortalException;

}