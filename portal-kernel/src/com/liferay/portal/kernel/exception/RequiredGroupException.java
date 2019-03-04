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