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

package com.liferay.portal.kernel.security.permission;

/**
 * Provides utility methods for filtering SQL queries by the user's permissions.
 *
 * @author Raymond Augé
 */
public interface InlineSQLHelper {

	/**
	 * Returns <code>true</code> if the inline SQL helper is enabled.
	 *
	 * @return <code>true</code> if the inline SQL helper is enabled;
	 *         <code>false</code> otherwise
	 */
	public boolean isEnabled();

	/**
	 * Returns <code>true</code> if the inline SQL helper is enabled for the
	 * group.
	 *
	 * @param  groupId the primary key of the group
	 * @return <code>true</code> if the inline SQL helper is enabled for the
	 *         group; <code>false</code> otherwise
	 */
	public boolean isEnabled(long groupId);

	/**
	 * Returns <code>true</code> if the inline SQL helper is enabled for the
	 * company or group.
	 *
	 * @param  companyId the primary key of the company
	 * @param  groupId the primary key of the group
	 * @return <code>true</code> if the inline SQL helper is enabled for the
	 *         company or group; <code>false</code> otherwise
	 */
	public boolean isEnabled(long companyId, long groupId);

	/**
	 * Returns <code>true</code> if the inline SQL helper is enabled for the
	 * groups.
	 *
	 * @param  groupIds the primary keys of the groups
	 * @return <code>true</code> if the inline SQL helper is enabled for the
	 *         groups; <code>false</code> otherwise
	 */
	public boolean isEnabled(long[] groupIds);

	/**
	 * Modifies the SQL query to only match resources that the user has
	 * permission to view.
	 *
	 * @param  sql the SQL query
	 * @param  className the fully qualified class name of the resources matched
	 *         by the query
	 * @param  classPKField the name of the column containing the resource's
	 *         primary key
	 * @return the modified SQL query
	 */
	public String replacePermissionCheck(
		String sql, String className, String classPKField);

	/**
	 * Modifies the SQL query to only match resources that the user has
	 * permission to view.
	 *
	 * @param  sql the SQL query
	 * @param  className the fully qualified class name of the resources matched
	 *         by the query
	 * @param  classPKField the name of the column containing the resource's
	 *         primary key
	 * @param  groupId the primary key of the group containing the resources
	 *         (optionally <code>null</code>)
	 * @return the modified SQL query
	 */
	public String replacePermissionCheck(
		String sql, String className, String classPKField, long groupId);

	/**
	 * Modifies the SQL query to only match resources that the user has
	 * permission to view.
	 *
	 * @param  sql the SQL query
	 * @param  className the fully qualified class name of the resources matched
	 *         by the query
	 * @param  classPKField the name of the column containing the resource's
	 *         primary key
	 * @param  groupId the primary key of the group containing the resources
	 *         (optionally <code>null</code>)
	 * @param  bridgeJoin an additional join clause to insert before the
	 *         permission join (optionally <code>null</code>)
	 * @return the modified SQL query
	 */
	public String replacePermissionCheck(
		String sql, String className, String classPKField, long groupId,
		String bridgeJoin);

	/**
	 * Modifies the SQL query to only match resources that the user has
	 * permission to view.
	 *
	 * @param  sql the SQL query
	 * @param  className the fully qualified class name of the resources matched
	 *         by the query
	 * @param  classPKField the name of the column containing the resource's
	 *         primary key
	 * @param  groupIds the primary keys of the groups containing the resources
	 *         (optionally <code>null</code>)
	 * @return the modified SQL query
	 */
	public String replacePermissionCheck(
		String sql, String className, String classPKField, long[] groupIds);

	/**
	 * Modifies the SQL query to only match resources that the user has
	 * permission to view.
	 *
	 * @param  sql the SQL query
	 * @param  className the fully qualified class name of the resources matched
	 *         by the query
	 * @param  classPKField the name of the column containing the resource's
	 *         primary key
	 * @param  groupIds the primary keys of the groups containing the resources
	 *         (optionally <code>null</code>)
	 * @param  bridgeJoin an additional join clause to insert before the
	 *         permission join (optionally <code>null</code>)
	 * @return the modified SQL query
	 */
	public String replacePermissionCheck(
		String sql, String className, String classPKField, long[] groupIds,
		String bridgeJoin);

	/**
	 * Modifies the SQL query to only match resources that the user has
	 * permission to view.
	 *
	 * @param  sql the SQL query
	 * @param  className the fully qualified class name of the resources matched
	 *         by the query
	 * @param  classPKField the name of the column containing the resource's
	 *         primary key
	 * @param  userIdField the name of the column containing  the resource
	 *         owner's primary key (optionally <code>null</code>)
	 * @return the modified SQL query
	 */
	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField);

	/**
	 * Modifies the SQL query to only match resources that the user has
	 * permission to view.
	 *
	 * @param  sql the SQL query
	 * @param  className the fully qualified class name of the resources matched
	 *         by the query
	 * @param  classPKField the name of the column containing the resource's
	 *         primary key
	 * @param  userIdField the name of the column containing  the resource
	 *         owner's primary key (optionally <code>null</code>)
	 * @param  groupId the primary key of the group containing the resources
	 *         (optionally <code>null</code>)
	 * @return the modified SQL query
	 */
	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		long groupId);

	/**
	 * Modifies the SQL query to only match resources that the user has
	 * permission to view.
	 *
	 * @param  sql the SQL query
	 * @param  className the fully qualified class name of the resources matched
	 *         by the query
	 * @param  classPKField the name of the column containing the resource's
	 *         primary key
	 * @param  userIdField the name of the column containing  the resource
	 *         owner's primary key (optionally <code>null</code>)
	 * @param  groupId the primary key of the group containing the resources
	 *         (optionally <code>null</code>)
	 * @param  bridgeJoin an additional join clause to insert before the
	 *         permission join (optionally <code>null</code>)
	 * @return the modified SQL query
	 */
	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		long groupId, String bridgeJoin);

	/**
	 * Modifies the SQL query to only match resources that the user has
	 * permission to view.
	 *
	 * @param  sql the SQL query
	 * @param  className the fully qualified class name of the resources matched
	 *         by the query
	 * @param  classPKField the name of the column containing the resource's
	 *         primary key
	 * @param  userIdField the name of the column containing  the resource
	 *         owner's primary key (optionally <code>null</code>)
	 * @param  groupIds the primary keys of the groups containing the resources
	 *         (optionally <code>null</code>)
	 * @return the modified SQL query
	 */
	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		long[] groupIds);

	/**
	 * Modifies the SQL query to only match resources that the user has
	 * permission to view.
	 *
	 * @param  sql the SQL query
	 * @param  className the fully qualified class name of the resources matched
	 *         by the query
	 * @param  classPKField the name of the column containing the resource's
	 *         primary key
	 * @param  userIdField the name of the column containing  the resource
	 *         owner's primary key (optionally <code>null</code>)
	 * @param  groupIds the primary keys of the groups containing the resources
	 *         (optionally <code>null</code>)
	 * @param  bridgeJoin an additional join clause to insert before the
	 *         permission join (optionally <code>null</code>)
	 * @return the modified SQL query
	 */
	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		long[] groupIds, String bridgeJoin);

	/**
	 * Modifies the SQL query to only match resources that the user has
	 * permission to view.
	 *
	 * @param  sql the SQL query
	 * @param  className the fully qualified class name of the resources matched
	 *         by the query
	 * @param  classPKField the name of the column containing the resource's
	 *         primary key
	 * @param  userIdField the name of the column containing  the resource
	 *         owner's primary key (optionally <code>null</code>)
	 * @param  bridgeJoin an additional join clause to insert before the
	 *         permission join (optionally <code>null</code>)
	 * @return the modified SQL query
	 */
	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		String bridgeJoin);

	/**
	 * Modifies the SQL query to only match resources that the user has
	 * permission to view.
	 *
	 * @param  sql the SQL query
	 * @param  className the fully qualified class name of the resources matched
	 *         by the query
	 * @param  classPKField the name of the column containing the resource's
	 *         primary key
	 * @param  userIdField the name of the column containing  the resource
	 *         owner's primary key (optionally <code>null</code>)
	 * @param  groupIdField the name of the column containing the resource's
	 *         group ID (optionally <code>null</code>)
	 * @param  groupIds the primary keys of the groups containing the resources
	 *         (optionally <code>null</code>)
	 * @param  bridgeJoin an additional join clause to insert before the
	 *         permission join (optionally <code>null</code>)
	 * @return the modified SQL query
	 */
	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		String groupIdField, long[] groupIds, String bridgeJoin);

}