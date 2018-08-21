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
 * @author István András Dézsi
 */
public class RequiredWorkflowRoleException extends RequiredRoleException {

	public static Class<?>[] getNestedClasses() {
		return _NESTED_CLASSES;
	}

	public RequiredWorkflowRoleException(String msg) {
		super(msg);
	}

	public static class MustNotDeleteRoleReferencedByCurrentWorkflowTask
		extends RequiredWorkflowRoleException {

		public MustNotDeleteRoleReferencedByCurrentWorkflowTask(long roleId) {
			super(
				String.format(
					"Role %s cannot be deleted because it is referenced by " +
						"one or more current workflow tasks",
					roleId));

			this.roleId = roleId;
		}

		public long roleId;

	}

	public static class MustNotDeleteRoleReferencedByWorkflowDefinition
		extends RequiredWorkflowRoleException {

		public MustNotDeleteRoleReferencedByWorkflowDefinition(long roleId) {
			super(
				String.format(
					"Role %s cannot be deleted because it is referenced by " +
						"one or more active workflow definitions",
					roleId));

		this.roleId = roleId;
	}

	public long roleId;
}

	private static final Class<?>[] _NESTED_CLASSES = {
		RequiredWorkflowRoleException.class,
		RequiredWorkflowRoleException.
			MustNotDeleteRoleReferencedByCurrentWorkflowTask.class,
		RequiredWorkflowRoleException.
			MustNotDeleteRoleReferencedByWorkflowDefinition.class
	};

}