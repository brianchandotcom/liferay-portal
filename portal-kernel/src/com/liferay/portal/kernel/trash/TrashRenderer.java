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

package com.liferay.portal.kernel.trash;

import com.liferay.asset.kernel.model.Renderer;

import java.util.Locale;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Zsolt Berentey
 */
public interface TrashRenderer extends Renderer {

	public String getNewName(String oldName, String token);

	public String getPortletId();

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #getSummary(PortletRequest, PortletResponse)}
	 */
	@Deprecated
	public String getSummary(Locale locale);

	public String getType();

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public String renderActions(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception;

}