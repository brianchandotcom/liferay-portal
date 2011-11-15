/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class ResourceResponseFactory {

	public static ResourceResponseImpl create(
			ResourceRequestImpl resourceRequestImpl,
			HttpServletResponse response, String portletName, long companyId)
		throws Exception {

		return create(resourceRequestImpl, response, portletName, companyId, 0);
	}

	public static ResourceResponseImpl create(
			ResourceRequestImpl resourceRequestImpl,
			HttpServletResponse response, String portletName, long companyId,
			long plid)
		throws Exception {

		ResourceResponseImpl resourceResponseImpl = new ResourceResponseImpl();

		resourceResponseImpl.init(
			resourceRequestImpl, response, portletName, companyId, plid);

		return resourceResponseImpl;
	}

}