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

package com.liferay.blogs.rest.internal.application;

import com.liferay.blogs.rest.internal.resource.BlogsRootResource;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Collections;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@ApplicationPath("/")
@Component(immediate = true, service = Application.class)
public class BlogsJaxRsApplication extends Application {

	@Override
	public Set<Object> getSingletons() {
		return SetUtil.fromCollection(
			Collections.singletonList(_blogsRootResource));
	}

	@Reference
	private BlogsRootResource _blogsRootResource;

}