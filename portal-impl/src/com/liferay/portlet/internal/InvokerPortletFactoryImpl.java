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

import com.liferay.portal.kernel.portlet.InvokerFilterContainer;
import com.liferay.portal.kernel.portlet.InvokerPortlet;
import com.liferay.portal.kernel.portlet.InvokerPortletFactory;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;

import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;

/**
 * @author Shuyang Zhou
 * @author Neil Griffin
 */
@OSGiBeanProperties(property = "service.ranking:Integer=1")
public class InvokerPortletFactoryImpl implements InvokerPortletFactory {

	@Override
	public InvokerPortlet create(
			com.liferay.portal.kernel.model.Portlet portletModel,
			Portlet portlet, PortletConfig portletConfig,
			PortletContext portletContext,
			InvokerFilterContainer invokerFilterContainer,
			boolean checkAuthToken, boolean facesPortlet, boolean headerPortlet)
		throws PortletException {

		try {
			return new InvokerPortletImpl(
				portletModel, portlet, portletConfig, portletContext,
				invokerFilterContainer, checkAuthToken, facesPortlet,
				headerPortlet);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #create(com.liferay.portal.kernel.model.Portlet, Portlet,
	 *             PortletConfig, PortletContext, InvokerFilterContainer,
	 *             boolean, boolean, boolean)}
	 */
	@Deprecated
	@Override
	public InvokerPortlet create(
			com.liferay.portal.kernel.model.Portlet portletModel,
			Portlet portlet, PortletConfig portletConfig,
			PortletContext portletContext,
			InvokerFilterContainer invokerFilterContainer,
			boolean checkAuthToken, boolean facesPortlet, boolean strutsPortlet,
			boolean strutsBridgePortlet)
		throws PortletException {

		return create(
			portletModel, portlet, portletConfig, portletContext,
			invokerFilterContainer, checkAuthToken, facesPortlet, false);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #create(com.liferay.portal.kernel.model.Portlet, Portlet,
	 *             PortletConfig, PortletContext, InvokerFilterContainer,
	 *             boolean, boolean, boolean)}
	 */
	@Deprecated
	@Override
	public InvokerPortlet create(
			com.liferay.portal.kernel.model.Portlet portletModel,
			Portlet portlet, PortletConfig portletConfig,
			PortletContext portletContext,
			InvokerFilterContainer invokerFilterContainer,
			boolean checkAuthToken, boolean facesPortlet, boolean headerPortlet,
			boolean strutsPortlet, boolean strutsBridgePortlet)
		throws PortletException {

		return create(
			portletModel, portlet, portletConfig, portletContext,
			invokerFilterContainer, checkAuthToken, facesPortlet,
			headerPortlet);
	}

	@Override
	public InvokerPortlet create(
			com.liferay.portal.kernel.model.Portlet portletModel,
			Portlet portlet, PortletContext portletContext,
			InvokerFilterContainer invokerFilterContainer)
		throws PortletException {

		try {
			return new InvokerPortletImpl(
				portletModel, portlet, portletContext, invokerFilterContainer);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

}