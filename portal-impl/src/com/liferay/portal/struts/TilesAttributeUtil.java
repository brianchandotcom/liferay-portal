/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.struts;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.PageContext;

import org.apache.struts.taglib.tiles.ComponentConstants;
import org.apache.struts.tiles.ComponentContext;

/**
 * @author Shuyang Zhou
 */
public class TilesAttributeUtil {

	public static Object getTilesAttribute(
		PageContext pageContext, String tilesAttributeName) {

		ServletRequest servletRequest = pageContext.getRequest();

		String value = servletRequest.getParameter(tilesAttributeName);

		if (value != null) {
			return value;
		}

		ComponentContext componentContext =
			(ComponentContext)pageContext.getAttribute(
				ComponentConstants.COMPONENT_CONTEXT,
				PageContext.REQUEST_SCOPE);

		if (componentContext == null) {
			return null;
		}

		return componentContext.getAttribute(tilesAttributeName);
	}

	public static void removeComponentContext(PageContext pageContext) {
		pageContext.removeAttribute(ComponentConstants.COMPONENT_CONTEXT);
	}

}