/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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