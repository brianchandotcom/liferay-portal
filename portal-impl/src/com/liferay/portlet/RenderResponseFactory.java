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

package com.liferay.portlet;

import com.liferay.portal.kernel.portlet.LiferayRenderResponse;
import com.liferay.portlet.internal.RenderRequestImpl;
import com.liferay.portlet.internal.RenderResponseImpl;

import javax.portlet.RenderRequest;
import javax.portlet.filter.RenderRequestWrapper;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Neil Griffin
 */
public class RenderResponseFactory {

	public static LiferayRenderResponse create() {
		return new RenderResponseImpl();
	}

	public static LiferayRenderResponse create(
		RenderRequest renderRequest, HttpServletResponse httpServletResponse) {

		while (renderRequest instanceof RenderRequestWrapper) {
			RenderRequestWrapper renderRequestWrapper =
				(RenderRequestWrapper)renderRequest;

			renderRequest = renderRequestWrapper.getRequest();
		}

		RenderResponseImpl renderResponseImpl = new RenderResponseImpl();

		renderResponseImpl.init(
			(RenderRequestImpl)renderRequest, httpServletResponse);

		return renderResponseImpl;
	}

}