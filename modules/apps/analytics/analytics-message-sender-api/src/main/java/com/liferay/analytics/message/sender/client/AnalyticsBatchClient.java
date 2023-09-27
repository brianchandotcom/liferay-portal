/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.sender.client;

import java.io.File;
import java.io.InputStream;

import java.util.Date;

/**
 * @author Riccardo Ferrari
 * @deprecated As of Cavanaugh (7.4.x)
 */
@Deprecated
public interface AnalyticsBatchClient {

	public File downloadResource(
		long companyId, Date resourceLastModifiedDate, String resourceName);

	public void uploadResource(
		long companyId, InputStream resourceInputStream, String resourceName);

}