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

package com.liferay.dynamic.data.mapping.webdav;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.portal.kernel.webdav.Resource;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;

/**
 * @author Rafael Praxedes
 */
public interface DDMWebDAV {

	public static final String TYPE_STRUCTURES = "Structures";

	public static final String TYPE_TEMPLATES = "Templates";

	public int addResource(WebDAVRequest webDAVRequest, long classNameId)
		throws Exception;

	public int deleteResource(
			WebDAVRequest webDAVRequest, String rootPath, String token,
			long classNameId)
		throws WebDAVException;

	public Resource getResource(
			WebDAVRequest webDAVRequest, String rootPath, String token,
			long classNameId)
		throws WebDAVException;

	public int putResource(
			WebDAVRequest webDAVRequest, String rootPath, String token,
			long classNameId)
		throws WebDAVException;

	public Resource toResource(
		WebDAVRequest webDAVRequest, DDMStructure structure, String rootPath,
		boolean appendPath);

	public Resource toResource(
		WebDAVRequest webDAVRequest, DDMTemplate template, String rootPath,
		boolean appendPath);

	public Resource toResource(
		WebDAVRequest webDAVRequest, String type, String rootPath,
		boolean appendPath);

}