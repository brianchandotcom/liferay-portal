/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Brian Wing Shun Chan
 * @author Isaac Obrist
 */
public class RequiredGroupException extends PortalException {

	public static class MustNotDeleteCurrentGroup
		extends RequiredGroupException {

		public MustNotDeleteCurrentGroup(long groupId) {
			super(
				String.format(
					"Site %s cannot be deleted because it is currently being " +
						"accessed",
					groupId));

			this.groupId = groupId;
		}

		public long groupId;

	}

	public static class MustNotDeleteGroupThatHasChild
		extends RequiredGroupException {

		public MustNotDeleteGroupThatHasChild(long groupId) {
			super(
				String.format(
					"Site %s cannot be deleted because it has child sites",
					groupId));

			this.groupId = groupId;
		}

		public long groupId;

	}

	public static class MustNotDeleteSystemGroup
		extends RequiredGroupException {

		public MustNotDeleteSystemGroup(long groupId) {
			super(
				String.format(
					"Site %s cannot be deleted because it is a system " +
						"required site",
					groupId));

			this.groupId = groupId;
		}

		public long groupId;

	}

	private RequiredGroupException(String message) {
		super(message);
	}

}