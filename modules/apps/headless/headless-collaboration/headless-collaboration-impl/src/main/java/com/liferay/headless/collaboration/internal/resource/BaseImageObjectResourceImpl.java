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

package com.liferay.headless.collaboration.internal.resource;

import com.liferay.headless.collaboration.dto.ImageObject;
import com.liferay.headless.collaboration.resource.ImageObjectResource;
import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.dto.Page;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import javax.annotation.Generated;

import javax.ws.rs.core.Response;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseImageObjectResourceImpl
	implements ImageObjectResource {

	@Override
	public Response deleteImageObject(Long id) throws Exception {
		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public ImageObject getImageObject(Long id) throws Exception {
		return new ImageObject();
	}

	@Override
	public Page<ImageObject> getImageObjectRepositoryImageObjectPage(
			Long parentId, Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	public ImageObject postImageObjectRepositoryImageObject(Long parentId)
		throws Exception {

		return new ImageObject();
	}

	@Override
	public ImageObject postImageObjectRepositoryImageObjectBatchCreate(
			Long parentId)
		throws Exception {

		return new ImageObject();
	}

	protected <T, R> List<R> transform(
		List<T> list, Function<T, R> transformFunction) {

		return TransformUtil.transform(list, transformFunction);
	}

}