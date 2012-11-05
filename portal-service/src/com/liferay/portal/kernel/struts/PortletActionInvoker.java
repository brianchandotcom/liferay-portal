/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.ClassResolverUtil;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.PortalClassInvoker;

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

		MethodKey methodKey = new MethodKey(
			ClassResolverUtil.resolveByPortalClassLoader(className),
			"processAction",
			new Class<?>[] {
				ClassResolverUtil.resolveByPortalClassLoader(
					"org.apache.struts.action.ActionMapping"),
				ClassResolverUtil.resolveByPortalClassLoader(
					"org.apache.struts.action.ActionForm"),
				PortletConfig.class, ActionRequest.class, ActionResponse.class
			});

		PortalClassInvoker.invoke(
			true, methodKey, null, null, portletConfig, actionRequest,
			actionResponse);
	}

}