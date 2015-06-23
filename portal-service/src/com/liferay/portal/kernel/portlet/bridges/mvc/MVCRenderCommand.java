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
 * Provides an interface to allow the portlet to process a particular action
 * request. This interface can only be used when the portlet is based on
 * {@link MVCPortlet}.
 *
 * <p>
 * The action command that will be invoked is determined based on two different
 * factors:
 * </p>
 *
 * <ul>
 * <li>
 * The portlet name that the action url is referring to.
 * </li>
 * <li>
 * The parameter value <code>ActionRequest.ACTION_NAME</code> of the action url.
 * </li>
 *
 * <p>
 * Implementations of this interface must be OSGi components that are registered
 * in the OSGi Registry with the following properties:
 * </p>
 *
 * <ul>
 * <li>
 * <code>javax.portlet.name</code>: The portlet name associated to this action
 * command.
 * </li>
 * <li>
 * <code>mvc.command.name</code>: the command name that will match the parameter
 * value <code>ActionRequest.ACTION_NAME</code>. This name cannot contain any
 * comma (<code>,</code>).
 * </li>
 *
 * <p>
 * The method <code>processAction</code> in {@link MVCPortlet}
 * will search in the OSGi Registry the action command that will match both the
 * portlet name and the parameter value <code>ActionRequest.ACTION_NAME</code>
 * with the properties <code>javax.portlet.name</code> and
 * <code>mvc.command.name</code>.
 * </p>
 *
 * <p>
 * In general, only one action command will be executed per portlet action url.
 * However, if the parameter value <code>ActionRequest.ACTION_NAME</code> is a
 * comma separated list of multiple names, {@link MVCPortlet} will find the
 * action commands that matches and they will all be invoked sequentially based
 * on the order they were specified in the parameter value
 * <code>ActionRequest.ACTION_NAME</code>.
 * </p>
 *
 * <p>
 * Every time there is more than one action command registered for the same
 * portlet name and with the same command name, only the one with the they
 * highest service ranking will be used.
 * </p>
 *
 * <p>
 * {@link BaseMVCActionCommand} is an abstract class that implements this
 * interface and can be used by extending it by third party developers to
 * simplify the use of action commands.
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

	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException;

}