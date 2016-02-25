/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.portlet.bridges.mvc;

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