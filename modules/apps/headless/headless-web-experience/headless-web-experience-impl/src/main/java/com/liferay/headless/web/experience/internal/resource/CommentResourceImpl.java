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

import com.liferay.headless.web.experience.dto.Comment;
import com.liferay.headless.web.experience.dto.StructuredContent;
import com.liferay.headless.web.experience.resource.CommentResource;
import com.liferay.portal.vulcan.context.AcceptLanguage;
import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.dto.Page;

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
	scope = ServiceScope.PROTOTYPE, service = CommentResource.class
)
@Generated("")
public class CommentResourceImpl implements CommentResource {

	@Override
	public Page<StructuredContent> getContentSpaceStructuredContentsPage(
			Integer parentId, String filter, String sort,
			AcceptLanguage acceptLanguage, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Page<Comment> getStructuredContentsCommentPage(
			StructuredContent parentId, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

}