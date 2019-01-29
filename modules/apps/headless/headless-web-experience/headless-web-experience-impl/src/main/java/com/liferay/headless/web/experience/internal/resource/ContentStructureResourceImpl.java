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

package com.liferay.headless.web.experience.internal.resource;

import com.liferay.headless.web.experience.dto.ContentStructure;
import com.liferay.headless.web.experience.dto.ContentStructureCollection;
import com.liferay.headless.web.experience.resource.ContentStructureResource;
import com.liferay.portal.vulcan.context.Pagination;

import java.util.Collections;

import javax.annotation.Generated;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Javier Gamarra
 * @generated
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.name=headless-web-experience-application.rest)",
		JaxrsWhiteboardConstants.JAX_RS_RESOURCE + "=true", "api.version=1.0.0"
	},
	scope = ServiceScope.PROTOTYPE, service = ContentStructureResource.class
)
@Generated("")
public class ContentStructureResourceImpl implements ContentStructureResource {

	@Override
	public ContentStructureCollection<ContentStructure> getContentStructureCollection(
			Pagination pagination, String size)
		throws Exception {

		return new ContentStructureCollection(Collections.emptyList(), 0);
	}

}