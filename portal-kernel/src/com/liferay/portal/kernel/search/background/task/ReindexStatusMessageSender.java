/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.background.task;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Andrew Betts
 */
@ProviderType
public interface ReindexStatusMessageSender {

	public void sendStatusMessage(String className, long count, long total);

	public void sendStatusMessage(
		String state, long searchContext, long[] items);

}