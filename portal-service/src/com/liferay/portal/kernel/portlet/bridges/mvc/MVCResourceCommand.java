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

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * Provides an interface to allow the portlet to serve a resource. This
 * interface can only be used when the portlet is based on
 * {@link MVCPortlet}.
 *
 * <p>
 * The resource command that will be invoked is determined based on two
 * different factors:
 * </p>
 *
 * <ul>
 * <li>
 * The portlet name that the resource url is referring to.
 * </li>
 * <li>
 * The <code>ResourceID</code> of the resource request.
 * </li>
 *
 * <p>
 * Implementations of this interface must be OSGi components that are registered
 * in the OSGi Registry with the following properties:
 * </p>
 *
 * <ul>
 * <li>
 * <code>javax.portlet.name</code>: The portlet name associated to this resource
 * command.
 * </li>
 * <li>
 * <code>mvc.command.name</code>: the command name that will match the
 * ResourceID of the resource request. This name cannot contain any
 * comma (<code>,</code>).
 * </li>
 *
 * <p>
 * The method <code>serveResource</code> in {@link MVCPortlet}
 * will search in the OSGi Registry the resource command that will match both
 * the portlet name and the ResourceID of the resource request with the
 * properties <code>javax.portlet.name</code> and <code>mvc.command.name</code>.
 * </p>
 *
 * <p>
 * In general, only one resource command will be executed per portlet resource
 * url. However, if the ResourceID of the resource request is a comma separated
 * list of multiple names, {@link MVCPortlet} will find the resource commands
 * that matches and they will all be invoked sequentially based
 * on the order they were specified in the resourceID of the resource request.
 * </p>
 *
 * <p>
 * Every time there is more than one resource command registered for the same
 * portlet name and with the same command name, only the one with the they
 * highest service ranking will be used.
 * </p>
 *
 * <p>
 * {@link BaseMVCResourceCommand} is an abstract class that implements this
 * interface and can be used by extending it by third party developers to
 * simplify the use of resource commands.
 * </p>
 *
 * @author Sergio González
 */
public interface MVCResourceCommand extends MVCCommand {

	public static final MVCResourceCommand EMPTY = new MVCResourceCommand() {

		@Override
		public boolean serveResource(
			ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) {

			return false;
		}

	};

	/**
	 * Invoked by {@link MVCPortlet} to allow the portlet to serve a resource.
	 *
	 * @param resourceRequest the resource request
	 * @param resourceResponse the resource response
	 * @return <code>true</code> if there was an error during serving the
	 *         resource; <code>false</code> otherwise.
	 */
	public boolean serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException;

}