/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.sharepoint.methods;

import com.liferay.portal.sharepoint.SharepointException;
import com.liferay.portal.sharepoint.SharepointRequest;

/**
 * @author Bruno Farache
 */
public interface Method {

	public static final String GET = "GET";

	public static final String POST = "POST";

	public String getMethodName();

	public String getRootPath(SharepointRequest sharepointRequest);

	public void process(SharepointRequest sharepointRequest)
		throws SharepointException;

}