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

import com.liferay.portal.kernel.util.StringPool;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * Provides an interface to handle the render phase of the portlet. This
 * interface can only be used when the portlet is based on {@link MVCPortlet}.
 *
 * <p>
 * The render command that will be invoked is determined based on two
 * different factors:
 * </p>
 *
 * <ul>
 * <li>
 * The portlet name that the render url is referring to.
 * </li>
 * <li>
 * The parameter value <code>mvcRenderCommandName</code> of the render url.
 * </li>
 *
 * <p>
 * Implementations of this interface must be OSGi components that are registered
 * in the OSGi Registry with the following properties:
 * </p>
 *
 * <ul>
 * <li>
 * <code>javax.portlet.name</code>: The portlet name associated to this render
 * command.
 * </li>
 * <li>
 * <code>mvc.command.name</code>: the command name that will match the
 * parameter value <code>mvcRenderCommandName</code>. This name cannot contain
 * any comma (<code>,</code>).
 * </li>
 *
 * <p>
 * The method <code>render</code> in {@link MVCPortlet} will search in the OSGi
 * Registry the render command that will match both the portlet name and the
 * parameter value <code>mvc.command.name</code> with the properties
 * <code>javax.portlet.name</code> and <code>mvc.command.name</code>.
 * </p>
 *
 * <p>
 * If there is more than one render command registered for the same portlet name
 * and with the same command name, only the one with the they highest service
 * ranking will be used.
 * </p>
 *
 * @author Sergio González
 */
public interface MVCRenderCommand extends MVCCommand {

	public static final MVCRenderCommand EMPTY = new MVCRenderCommand() {

		@Override
		public String render(
			RenderRequest renderRequest, RenderResponse renderResponse) {

			return StringPool.BLANK;
		}

	};

	/**
	 * Invoked by {@link MVCPortlet} to handle the render phase of the portlet.
	 *
	 * @param renderRequest the render request
	 * @param renderResponse the render response
	 * @return the path that should be dispatched.
	 */
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException;

}