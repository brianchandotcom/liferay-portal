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
public class GroupParentException extends PortalException {

	@Deprecated
	public static final int CHILD_DESCENDANT = 3;

	@Deprecated
	public static final int SELF_DESCENDANT = 1;

	@Deprecated
	public static final int STAGING_DESCENDANT = 2;

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public GroupParentException() {
		_type = 0;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public GroupParentException(int type) {
		_type = type;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public GroupParentException(String msg) {
		super(msg);

		_type = 0;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public GroupParentException(String msg, Throwable cause) {
		super(msg, cause);

		_type = 0;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public GroupParentException(Throwable cause) {
		super(cause);

		_type = 0;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public int getType() {
		return _type;
	}

	public static class MustNotBeOwnParent extends GroupParentException {

		public MustNotBeOwnParent(long groupId) {
			super(
				String.format(
					"Site %s cannot be its own parent site",
					groupId),
				SELF_DESCENDANT);

			this.groupId = groupId;
		}

		public long groupId;

	}

	public static class MustNotHaveChildParent extends GroupParentException {

		public MustNotHaveChildParent(long groupId, long parentGroupId) {
			super(
				String.format(
					"Site %s cannot have a child site %s as its parent site",
					groupId, parentGroupId),
				CHILD_DESCENDANT);

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
					groupId, parentGroupId),
				STAGING_DESCENDANT);

			this.groupId = groupId;
			this.parentGroupId = parentGroupId;
		}

		public long groupId;
		public long parentGroupId;

	}

	private GroupParentException(String message, int type) {
		super(message);

		_type = type;
	}

	private final int _type;

}