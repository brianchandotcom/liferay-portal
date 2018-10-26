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

package com.liferay.portlet.internal;

import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portlet.PortletBagFactory;

import javax.portlet.Portlet;
import javax.portlet.PortletException;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 * @author Neil Griffin
 */
public class PortletBagUtil {

	public static Portlet getPortletInstance(
			ServletContext servletContext,
			com.liferay.portal.kernel.model.Portlet portletModel,
			String rootPortletId)
		throws PortletException {

		PortletBag portletBag = PortletBagPool.get(rootPortletId);

		// Portlet bag should never be null unless the portlet has been
		// undeployed

		if (portletBag == null) {
			PortletBagFactory portletBagFactory = new PortletBagFactory();

			portletBagFactory.setClassLoader(
				PortalClassLoaderUtil.getClassLoader());
			portletBagFactory.setServletContext(servletContext);
			portletBagFactory.setWARFile(false);

			try {
				portletBag = portletBagFactory.create(portletModel);
			}
			catch (Exception e) {
				throw new PortletException(e);
			}
		}

		return portletBag.getPortletInstance();
	}

}