/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.rest.context.path;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.context.path.RESTContextPathResolver;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Marco Leo
 */
public class RESTContextPathResolverImpl implements RESTContextPathResolver {

	public RESTContextPathResolverImpl(
		ObjectDefinition objectDefinition,
		ObjectScopeProvider objectScopeProvider, boolean system) {

		_objectDefinition = objectDefinition;
		_objectScopeProvider = objectScopeProvider;
		_system = system;
	}

	public RESTContextPathResolverImpl(
		String contextPath, ObjectScopeProvider objectScopeProvider,
		boolean system) {

		_objectScopeProvider = objectScopeProvider;
		_system = system;

		_initContextPath(contextPath);
	}

	@Override
	public String getRESTContextPath(long groupId) {
		if (Validator.isNull(_contextPath)) {
			_initContextPath("/o" + _objectDefinition.getRESTContextPath());
		}

		if (!_objectScopeProvider.isGroupAware() ||
			!_objectScopeProvider.isValidGroupId(groupId)) {

			return _contextPath;
		}

		return StringUtil.replace(
			_contextPath, new String[] {"{groupId}", "{scopeKey}"},
			new String[] {String.valueOf(groupId), String.valueOf(groupId)});
	}

	private void _initContextPath(String contextPath) {
		_contextPath = contextPath;

		if (_objectScopeProvider.isGroupAware() && !_system) {
			_contextPath = _contextPath + "/scopes/{scopeKey}";
		}
	}

	private String _contextPath;
	private ObjectDefinition _objectDefinition;
	private final ObjectScopeProvider _objectScopeProvider;
	private final boolean _system;

}