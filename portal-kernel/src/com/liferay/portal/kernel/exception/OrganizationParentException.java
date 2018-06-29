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

package com.liferay.portal.kernel.exception;

import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Brian Wing Shun Chan
 */
public class OrganizationParentException extends PortalException {

	public OrganizationParentException() {
	}

	public OrganizationParentException(String msg) {
		super(msg);
	}

	public OrganizationParentException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public OrganizationParentException(Throwable cause) {
		super(cause);
	}

	public static class InvalidChildType extends OrganizationParentException {

		public InvalidChildType(
			String childOrganizationType, String parentOrganizationType) {

			super(
				StringBundler.concat(
					"Organization of type ", childOrganizationType,
					" is not allowed as a child of ", parentOrganizationType));

			_childOrganizationType = childOrganizationType;
			_parentOrganizationType = parentOrganizationType;
		}

		public String getChildOrganizationType() {
			return _childOrganizationType;
		}

		public String getParentOrganizationType() {
			return _parentOrganizationType;
		}

		private final String _childOrganizationType;
		private final String _parentOrganizationType;

	}

	public static class MustBeRootable extends OrganizationParentException {

		public MustBeRootable(String type) {
			super(
				"Organization of type " + type +
					" cannot be a root organization");

			_type = type;
		}

		public String getType() {
			return _type;
		}

		private String _type;

	}

	public static class MustNotHaveChildren
		extends OrganizationParentException {

		public MustNotHaveChildren(String type) {
			super("Organization of type " + type + " cannot have children");

			_type = type;
		}

		public String getType() {
			return _type;
		}

		private String _type;

	}

}