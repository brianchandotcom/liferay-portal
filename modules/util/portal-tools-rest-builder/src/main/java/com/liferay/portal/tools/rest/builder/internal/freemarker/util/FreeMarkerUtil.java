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

package com.liferay.portal.tools.rest.builder.internal.freemarker.util;

import com.liferay.portal.tools.rest.builder.internal.freemarker.FreeMarker;

import java.util.Map;

/**
 * @author Peter Shin
 */
public class FreeMarkerUtil {

	public static final String APPLICATION_FTL = "com/liferay/portal/tools/rest/builder/dependencies/application.ftl";

	public static final String COLLECTION_FTL = "com/liferay/portal/tools/rest/builder/dependencies/collection.ftl";

	public static final String DTO_FTL = "com/liferay/portal/tools/rest/builder/dependencies/dto.ftl";

	public static final String RESOURCE_FTL = "com/liferay/portal/tools/rest/builder/dependencies/resource.ftl";

	public static final String RESOURCE_IMPL_FTL = "com/liferay/portal/tools/rest/builder/dependencies/resource_impl.ftl";

	public static String processTemplate(
			String copyrightFileName, String name, Map<String, Object> context)
		throws Exception {

		return _freeMarker.processTemplate(copyrightFileName, name, context);
	}

	private static final FreeMarker _freeMarker = new FreeMarker();

}