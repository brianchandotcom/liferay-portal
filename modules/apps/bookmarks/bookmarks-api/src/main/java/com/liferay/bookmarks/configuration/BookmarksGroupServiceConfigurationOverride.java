/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.configuration;

/**
 * @author Iván Zaera
 */
public interface BookmarksGroupServiceConfigurationOverride {

	public String emailEntryAddedBodyXml();

	public String emailEntryAddedSubjectXml();

	public String emailEntryUpdatedBodyXml();

	public String emailEntryUpdatedSubjectXml();

	public long rootFolderId();

}