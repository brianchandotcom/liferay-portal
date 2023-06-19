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

package com.liferay.headless.admin.taxonomy.internal.dto.v1_0.action.metadata;

import com.liferay.headless.admin.taxonomy.internal.resource.v1_0.KeywordResourceImpl;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.vulcan.dto.action.ActionInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Javier Gamarra
 * @generated
 */
public abstract class BaseKeywordDTOActionMetadataProvider {

	public BaseKeywordDTOActionMetadataProvider() {
		_actionInfoMap.put(
			"delete",
			new ActionInfo(
				getDeleteActionName(), KeywordResourceImpl.class,
				getDeleteResourceMethodName()));
		_actionInfoMap.put(
			"get",
			new ActionInfo(
				getGetActionName(), KeywordResourceImpl.class,
				getGetResourceMethodName()));
		_actionInfoMap.put(
			"replace",
			new ActionInfo(
				getReplaceActionName(), KeywordResourceImpl.class,
				getReplaceResourceMethodName()));
		_actionInfoMap.put(
			"update",
			new ActionInfo(
				getUpdateActionName(), KeywordResourceImpl.class,
				getUpdateResourceMethodName()));
	}

	public final ActionInfo getActionInfo(String actionName) {
		return _actionInfoMap.get(actionName);
	}

	public final Set<String> getActionNames() {
		return _actionInfoMap.keySet();
	}

	public abstract String getPermissionName();

	protected String getDeleteActionName() {
		return ActionKeys.DELETE;
	}

	protected String getDeleteResourceMethodName() {
		return "deleteKeyword";
	}

	protected String getGetActionName() {
		return ActionKeys.VIEW;
	}

	protected String getGetResourceMethodName() {
		return "getKeyword";
	}

	protected String getReplaceActionName() {
		return ActionKeys.UPDATE;
	}

	protected String getReplaceResourceMethodName() {
		return "putKeyword";
	}

	protected String getUpdateActionName() {
		return ActionKeys.UPDATE;
	}

	protected abstract String getUpdateResourceMethodName();

	protected final void registerActionInfo(
		String actionName, ActionInfo actionInfo) {

		_actionInfoMap.put(actionName, actionInfo);
	}

	private final Map<String, ActionInfo> _actionInfoMap = new HashMap<>();

}