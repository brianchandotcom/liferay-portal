/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.permission;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * @author Raymond Augé
 * @see    InlineSQLHelper
 */
public class InlineSQLHelperUtil {

	public static InlineSQLHelper getInlineSQLHelper() {
		PortalRuntimePermission.checkGetBeanProperty(InlineSQLHelperUtil.class);

		return _inlineSQLPermission;
	}

	public static boolean isEnabled() {
		return getInlineSQLHelper().isEnabled();
	}

	public static boolean isEnabled(long groupId) {
		return getInlineSQLHelper().isEnabled(groupId);
	}

	public static boolean isEnabled(long[] groupIds) {
		return getInlineSQLHelper().isEnabled(groupIds);
	}

	public static String replacePermissionCheck(
		String sql, String className, String classPKField) {

		return getInlineSQLHelper().replacePermissionCheck(
			sql, className, classPKField);
	}

	public static String replacePermissionCheck(
		String sql, String className, String classPKField, long groupId) {

		return getInlineSQLHelper().replacePermissionCheck(
			sql, className, classPKField, groupId);
	}

	public static String replacePermissionCheck(
		String sql, String className, String classPKField, long groupId,
		String bridgeJoin) {

		return getInlineSQLHelper().replacePermissionCheck(
			sql, className, classPKField, groupId, bridgeJoin);
	}

	public static String replacePermissionCheck(
		String sql, String className, String classPKField, long[] groupIds) {

		return getInlineSQLHelper().replacePermissionCheck(
			sql, className, classPKField, groupIds);
	}

	public static String replacePermissionCheck(
		String sql, String className, String classPKField, long[] groupIds,
		String bridgeJoin) {

		return getInlineSQLHelper().replacePermissionCheck(
			sql, className, classPKField, groupIds, bridgeJoin);
	}

	public static String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField) {

		return getInlineSQLHelper().replacePermissionCheck(
			sql, className, classPKField, userIdField);
	}

	public static String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		long groupId) {

		return getInlineSQLHelper().replacePermissionCheck(
			sql, className, classPKField, userIdField, groupId);
	}

	public static String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		long groupId, String bridgeJoin) {

		return getInlineSQLHelper().replacePermissionCheck(
			sql, className, classPKField, userIdField, groupId, bridgeJoin);
	}

	public static String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		long[] groupIds) {

		return getInlineSQLHelper().replacePermissionCheck(
			sql, className, classPKField, userIdField, groupIds);
	}

	public static String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		long[] groupIds, String bridgeJoin) {

		return getInlineSQLHelper().replacePermissionCheck(
			sql, className, classPKField, userIdField, groupIds, bridgeJoin);
	}

	public static String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		String bridgeJoin) {

		return getInlineSQLHelper().replacePermissionCheck(
			sql, className, classPKField, userIdField, bridgeJoin);
	}

	public static String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		String groupIdField, long[] groupIds, String bridgeJoin) {

		return getInlineSQLHelper().replacePermissionCheck(
			sql, className, classPKField, userIdField, groupIdField, groupIds,
			bridgeJoin);
	}

	public void setInlineSQLHelper(InlineSQLHelper inlineSQLPermission) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_inlineSQLPermission = inlineSQLPermission;
	}

	private static InlineSQLHelper _inlineSQLPermission;

}