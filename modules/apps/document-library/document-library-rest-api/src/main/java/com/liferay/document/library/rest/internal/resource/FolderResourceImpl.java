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

package com.liferay.document.library.rest.internal.resource;

import com.liferay.document.library.rest.resource.FolderResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.name=document-library-application)",
		JaxrsWhiteboardConstants.JAX_RS_RESOURCE + "=true", "api.version=1.0.0"
	},
	scope = ServiceScope.PROTOTYPE,
	service = FolderResource.class)
public class FolderResourceImpl implements FolderResource {
}