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

package com.liferay.exportimport.changeset;

import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Máté Thurzó
 */
public class ChangesetEnvironment {

	public static Builder create(long groupId, String portletId) {
		return new Builder(new ChangesetEnvironment(), groupId, portletId);
	}

	public long getGroupId() {
		return _groupId;
	}

	public Map<String, String> getParameterMap() {
		return _parameterMap;
	}

	public long getPlid() {
		return _plid;
	}

	public String getPortletId() {
		return _portletId;
	}

	public long getUserId() {
		return _userId;
	}

	public static class Builder {

		public Builder(
			ChangesetEnvironment changesetEnvironment, long groupId,
			String portletId) {

			_changesetEnvironment = changesetEnvironment;

			_changesetEnvironment._groupId = groupId;
			_changesetEnvironment._portletId = portletId;

			_changesetEnvironment._parameterMap = new HashMap<>();
			_changesetEnvironment._plid = 0;

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			_changesetEnvironment._userId = permissionChecker.getUserId();
		}

		public Builder addParameter(String key, String value) {
			_changesetEnvironment._parameterMap.put(key, value);

			return this;
		}

		public Builder addPlid(long plid) {
			_changesetEnvironment._plid = plid;

			return this;
		}

		public Builder addUserId(long userId) {
			_changesetEnvironment._userId = userId;

			return this;
		}

		public ChangesetEnvironment create() {
			return _changesetEnvironment;
		}

		private final ChangesetEnvironment _changesetEnvironment;

	}

	private ChangesetEnvironment() {
	}

	private long _groupId;
	private Map<String, String> _parameterMap;
	private long _plid;
	private String _portletId;
	private long _userId;

}