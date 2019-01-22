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
public class DEDataDefinitionException extends PortalException {

	public DEDataDefinitionException() {
	}

	public DEDataDefinitionException(String msg) {
		super(msg);
	}

	public DEDataDefinitionException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public DEDataDefinitionException(Throwable cause) {
		super(cause);
	}

	public static class MustHaveNoDataRecordCollection
		extends DEDataDefinitionException {

		public MustHaveNoDataRecordCollection(
			long deDataDefinitionId, Throwable cause) {

			super(cause);

			_deDataDefinitionId = deDataDefinitionId;
		}

		public long getDEDataDefinitionId() {
			return _deDataDefinitionId;
		}

		private final long _deDataDefinitionId;

	}

	public static class MustHavePermission extends DEDataDefinitionException {

		public MustHavePermission(String[] actionId, Throwable cause) {
			super(cause);

			_actionId = actionId;
		}

		public String[] getActionId() {
			return _actionId;
		}

		private final String[] _actionId;

	}

	public static class NoSuchDataDefinition extends DEDataDefinitionException {

		public NoSuchDataDefinition(long deDataDefinitionId, Throwable cause) {
			super(cause);

			_deDataDefinitionId = deDataDefinitionId;
		}

		public long getDEDataDefinitionId() {
			return _deDataDefinitionId;
		}

		private final long _deDataDefinitionId;

	}

	public static class NoSuchRoles extends DEDataDefinitionException {

		public NoSuchRoles(String[] roleNames) {
			_roleNames = roleNames;
		}

		public String[] getRoleNames() {
			return _roleNames;
		}

		private final String[] _roleNames;

	}

	public static class PrincipalException extends DEDataDefinitionException {

		public PrincipalException(Throwable cause) {
			super(cause);
		}

	}

}