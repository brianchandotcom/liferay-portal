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
 * @author Sergio González
 */
public interface RenderCommand {

	public static final RenderCommand EMPTY = new RenderCommand() {

		@Override
		public String processCommand(
			RenderRequest renderRequest, RenderResponse renderResponse) {

			return StringPool.BLANK;
		}

	};

	public static final String RENDER_COMMAND_POSTFIX = "RenderCommand";

	public static final String RENDER_PACKAGE_NAME = "render.package.prefix";

	public String processCommand(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException;

}