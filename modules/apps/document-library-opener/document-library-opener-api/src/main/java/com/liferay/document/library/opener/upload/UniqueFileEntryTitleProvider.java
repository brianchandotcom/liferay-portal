/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.upload;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

/**
 * @author Adolfo Pérez
 */
public interface UniqueFileEntryTitleProvider {

	public String provide(long groupId, long folderId, Locale locale)
		throws PortalException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #provide(long
	 *             groupId, long folderId, String extension, String title)}
	 */
	@Deprecated
	public String provide(long groupId, long folderId, String title)
		throws PortalException;

	public String provide(
			long groupId, long folderId, String extension, Locale locale)
		throws PortalException;

	public String provide(
			long groupId, long folderId, String extension, String title)
		throws PortalException;

}