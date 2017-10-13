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

package com.liferay.portal.kernel.struts;

import com.liferay.petra.lang.ClassResolverUtil;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.PortalClassInvoker;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletActionInvoker {

	public static void processAction(
			String className, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ClassLoader portalClassLoader = PortalClassLoaderUtil.getClassLoader();

		MethodKey methodKey = new MethodKey(
			ClassResolverUtil.resolve(className, portalClassLoader),
			"processAction",
			new Class<?>[] {
				ClassResolverUtil.resolve(
					"org.apache.struts.action.ActionMapping",
					portalClassLoader),
				ClassResolverUtil.resolve(
					"org.apache.struts.action.ActionForm", portalClassLoader),
				PortletConfig.class, ActionRequest.class, ActionResponse.class
			});

		PortalClassInvoker.invoke(
			methodKey, null, null, portletConfig, actionRequest,
			actionResponse);
	}

}