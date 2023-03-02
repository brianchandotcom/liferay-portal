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

package com.liferay.object.internal.rest.context.path;

import com.liferay.object.rest.context.path.RESTContextPathResolver;
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

		if (objectScopeProvider.isGroupAware() && !system) {
			_contextPath = contextPath + "/scopes/{scopeKey}";
		}
		else {
			_contextPath = contextPath;
		}
	}

	@Override
	public String getRESTContextPath(long groupId) {
		if (!_objectScopeProvider.isGroupAware() ||
			!_objectScopeProvider.isValidGroupId(groupId)) {

			return _contextPath;
		}

		return StringUtil.replace(
			_contextPath, new String[] {"{groupId}", "{scopeKey}"},
			new String[] {String.valueOf(groupId), String.valueOf(groupId)});
	}

	private final String _contextPath;
	private final ObjectScopeProvider _objectScopeProvider;

}