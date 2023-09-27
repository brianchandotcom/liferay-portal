/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.portlet.bridges.mvc.constants;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

/**
 * @author Michael C. Han
 */
public class MVCRenderConstants {

	public static final String MVC_PATH_REQUEST_ATTRIBUTE_NAME =
		MVCPortlet.class.getName() + "#MVC_PATH";

	public static final String MVC_PATH_VALUE_SKIP_DISPATCH =
		MVCRenderConstants.class.getName() + "#MVC_PATH_SKIP_DISPATCH";

	public static final String
		PORTLET_CONTEXT_OVERRIDE_REQUEST_ATTIBUTE_NAME_PREFIX =
			"portlet.context.override.";

}