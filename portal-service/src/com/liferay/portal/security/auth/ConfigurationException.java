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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Pei-Jung Lan
 */
public class ConfigurationException extends PortalException {

	public static Class<?>[] getNestedClasses() {
		return _NESTED_CLASSES;
	}

	public ConfigurationException(String msg) {
		super(msg);
	}

	public static class MustBeSelectableScope extends ConfigurationException {

		public MustBeSelectableScope(String scopeId, long groupId) {
			super(
				String.format(
					"Scope %s for group %s must be selectable", scopeId,
					groupId));

			this.groupId = groupId;
			this.scopeId = scopeId;
		}

		public final long groupId;
		public final String scopeId;

	}

	public static class MustBeStrictPortlet extends ConfigurationException {

		public MustBeStrictPortlet(String portletId) {
			super(
				String.format(
					"Portlet preferences for portlet %s must be an instance " +
					"of StrictPortletPreferencesImpl", portletId));

			this.portletId = portletId;
		}

		public final String portletId;

	}

	private static final Class<?>[] _NESTED_CLASSES = {
		ConfigurationException.MustBeSelectableScope.class,
		ConfigurationException.MustBeStrictPortlet.class
	};

}