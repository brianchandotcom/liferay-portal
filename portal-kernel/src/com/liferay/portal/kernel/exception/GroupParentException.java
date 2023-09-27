/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Brian Wing Shun Chan
 * @author Isaac Obrist
 */
public class GroupParentException extends PortalException {

	public static class MustNotBeOwnParent extends GroupParentException {

		public MustNotBeOwnParent(long groupId) {
			super(
				String.format(
					"Site %s cannot be its own parent site", groupId));

			this.groupId = groupId;
		}

		public long groupId;

	}

	public static class MustNotHaveChildParent extends GroupParentException {

		public MustNotHaveChildParent(long groupId, long parentGroupId) {
			super(
				String.format(
					"Site %s cannot have a child site %s as its parent site",
					groupId, parentGroupId));

			this.groupId = groupId;
			this.parentGroupId = parentGroupId;
		}

		public long groupId;
		public long parentGroupId;

	}

	public static class MustNotHaveStagingParent extends GroupParentException {

		public MustNotHaveStagingParent(long groupId, long parentGroupId) {
			super(
				String.format(
					"Site %s cannot have a staging site %s as its parent site",
					groupId, parentGroupId));

			this.groupId = groupId;
			this.parentGroupId = parentGroupId;
		}

		public long groupId;
		public long parentGroupId;

	}

	private GroupParentException(String message) {
		super(message);
	}

}