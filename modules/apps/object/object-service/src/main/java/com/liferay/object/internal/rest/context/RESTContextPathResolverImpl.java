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

package com.liferay.object.internal.rest.context;

import com.liferay.object.rest.context.RESTContextPathResolver;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Marco Leo
 */
public class RESTContextPathResolverImpl implements RESTContextPathResolver {

	public RESTContextPathResolverImpl(
		String contextPath, ObjectScopeProvider objectScopeProvider,
		boolean system) {

		_objectScopeProvider = objectScopeProvider;

		if (_objectScopeProvider.isGroupAware() && !system) {
			contextPath = contextPath + "/scopes/{scopeKey}";
		}

		_contextPath = contextPath;
	}

	@Override
	public String getRESTContextPath(long groupId) {
		String restContextPath = _contextPath;

		if (_objectScopeProvider.isGroupAware() &&
			_objectScopeProvider.isValidGroupId(groupId)) {

			restContextPath = StringUtil.replace(
				"{scopeKey}", restContextPath, String.valueOf(groupId));
			restContextPath = StringUtil.replace(
				"{groupId}", restContextPath, String.valueOf(groupId));
		}

		return restContextPath;
	}

	private final String _contextPath;
	private final ObjectScopeProvider _objectScopeProvider;

}