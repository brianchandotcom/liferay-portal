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

package com.liferay.portal.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 * @author Isaac Obrist
 */
public class RequiredGroupException extends PortalException {

	@Deprecated
	public static final int CURRENT_GROUP = 3;

	@Deprecated
	public static final int PARENT_GROUP = 2;

	@Deprecated
	public static final int SYSTEM_GROUP = 1;

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public int getType() {
		return _type;
	}

	public static class MustNotDeleteCurrentGroup
		extends RequiredGroupException {

		public MustNotDeleteCurrentGroup(long groupId) {
			super(
				String.format(
					"Site %s cannot be deleted because it is currently being " +
						"accessed",
					groupId),
				CURRENT_GROUP);

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
					groupId),
				PARENT_GROUP);

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
					groupId),
				SYSTEM_GROUP);

			this.groupId = groupId;
		}

		public long groupId;

	}

	private RequiredGroupException(String message, int type) {
		super(message);

		_type = type;
	}

	private final int _type;

}