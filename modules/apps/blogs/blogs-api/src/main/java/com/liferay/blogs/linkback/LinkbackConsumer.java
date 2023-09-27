/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.linkback;

/**
 * @author Matthew Tambara
 */
public interface LinkbackConsumer {

	public void addNewTrackback(long commentId, String url, String entryURL);

	public void verifyNewTrackbacks();

	public void verifyTrackback(long commentId, String url, String entryURL);

}