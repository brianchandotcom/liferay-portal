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

import com.liferay.portal.kernel.portlet.LiferayResourceResponse;
import com.liferay.portlet.internal.ResourceRequestImpl;
import com.liferay.portlet.internal.ResourceResponseImpl;

import javax.portlet.ResourceRequest;
import javax.portlet.filter.ResourceRequestWrapper;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Neil Griffin
 */
public class ResourceResponseFactory {

	public static LiferayResourceResponse create(
		ResourceRequest resourceRequest,
		HttpServletResponse httpServletResponse) {

		while (resourceRequest instanceof ResourceRequestWrapper) {
			ResourceRequestWrapper resourceRequestWrapper =
				(ResourceRequestWrapper)resourceRequest;

			resourceRequest = resourceRequestWrapper.getRequest();
		}

		ResourceResponseImpl resourceResponseImpl = new ResourceResponseImpl();

		resourceResponseImpl.init(
			(ResourceRequestImpl)resourceRequest, httpServletResponse);

		return resourceResponseImpl;
	}

}