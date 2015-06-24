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

	public ConfigurationException(String msg) {
		super(msg);
	}

	public static class MustBeSelectableScope extends ConfigurationException {

		public MustBeSelectableScope(String scopeId, long groupId) {
			super(
				String.format(
					"Scope %s for group %s is not selectable", scopeId,
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
					"Portlet preferences for portlet %s is not an instance " +
					"of StrictPortletPreferencesImpl", portletId));

			this.portletId = portletId;
		}

		public final String portletId;

	}

	public static class MustHaveAncestor extends ConfigurationException {

		public MustHaveAncestor(long childGroupId, long ancestorGroupId) {
			super(
				String.format(
					"Child group %s must have group %s as its ancestor",
					childGroupId, ancestorGroupId));

			this.ancestorGroupId = ancestorGroupId;
			this.childGroupId = childGroupId;
		}

		public final long ancestorGroupId;
		public final long childGroupId;

	}

	public static class MustHaveContentSharingWithChildrenEnabled
		extends ConfigurationException {

		public MustHaveContentSharingWithChildrenEnabled(long scopeGroupId) {
			super(
				String.format(
					"Content sharing with children site is not enabled for " +
					"site %s", scopeGroupId));

			this.scopeGroupId = scopeGroupId;
		}

		public final long scopeGroupId;

	}

	public static class MustHaveValidScopeGroupId
		extends ConfigurationException {

		public MustHaveValidScopeGroupId(long scopeGroupId) {
			super(
				String.format(
					"No scope group found matching id %s", scopeGroupId));

			this.scopeGroupId = scopeGroupId;
		}

		public final long scopeGroupId;

	}

	public static class MustNotBeNull extends ConfigurationException {

		public MustNotBeNull(String resourceName) {
			super(String.format("%s must not be null", resourceName));

			this.resourceName = resourceName;
		}

		public final String resourceName;

	}

}