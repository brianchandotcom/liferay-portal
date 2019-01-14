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

package com.liferay.data.engine.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Leonardo Barros
 */
public class DEDataRecordCollectionException extends PortalException {

	public DEDataRecordCollectionException() {
	}

	public DEDataRecordCollectionException(String msg) {
		super(msg);
	}

	public DEDataRecordCollectionException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public DEDataRecordCollectionException(Throwable cause) {
		super(cause);
	}

	public static class MustHavePermission
		extends DEDataRecordCollectionException {

		public MustHavePermission(String[] actionId, Throwable cause) {
			super(cause);

			_actionId = actionId;
		}

		public String[] getActionId() {
			return _actionId;
		}

		private final String[] _actionId;

	}

	public static class NoSuchDataRecordCollection
		extends DEDataRecordCollectionException {

		public NoSuchDataRecordCollection(
			long deDataRecordCollectionId, Throwable cause) {

			super(cause);

			_deDataRecordCollectionId = deDataRecordCollectionId;
		}

		public long getDEDataRecordCollectionId() {
			return _deDataRecordCollectionId;
		}

		private final long _deDataRecordCollectionId;

	}

	public static class NoSuchRoles extends DEDataRecordCollectionException {

		public NoSuchRoles(String[] roleNames) {
			_roleNames = roleNames;
		}

		public String[] getRoleNames() {
			return _roleNames;
		}

		private final String[] _roleNames;

	}

	public static class PrincipalException
		extends DEDataRecordCollectionException {

		public PrincipalException(Throwable cause) {
			super(cause);
		}

	}

}