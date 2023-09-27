/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

import com.liferay.petra.string.StringBundler;

/**
 * @author Brian Wing Shun Chan
 */
public class OrganizationParentException extends PortalException {

	public OrganizationParentException() {
	}

	public OrganizationParentException(String msg) {
		super(msg);
	}

	public OrganizationParentException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public OrganizationParentException(Throwable throwable) {
		super(throwable);
	}

	public static class MustBeRootable extends OrganizationParentException {

		public MustBeRootable(String type) {
			super(
				"Organization of type " + type +
					" must not be a root organization");

			_type = type;
		}

		public String getType() {
			return _type;
		}

		private String _type;

	}

	public static class MustHaveValidChildType
		extends OrganizationParentException {

		public MustHaveValidChildType(
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

	public static class MustNotHaveChildren
		extends OrganizationParentException {

		public MustNotHaveChildren(String type) {
			super("Organization of type " + type + " must not have children");

			_type = type;
		}

		public String getType() {
			return _type;
		}

		private String _type;

	}

}