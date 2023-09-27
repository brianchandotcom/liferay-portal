/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.permission;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.DSLQuery;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides utility methods for filtering SQL queries by the user's permissions.
 *
 * @author Raymond Augé
 */
@ProviderType
public interface InlineSQLHelper {

	public <T extends Table<T>> Predicate getPermissionWherePredicate(
		Class<?> modelClass, Column<T, Long> classPKColumn, long... groupIds);

	public <T extends Table<T>> Predicate getPermissionWherePredicate(
		String modelClassName, Column<T, Long> classPKColumn, long... groupIds);

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

	public <T extends Table<T>> DSLQuery replacePermissionCheck(
		DSLQuery dslQuery, Class<?> modelClass, Column<T, Long> classPKColumn,
		long... groupIds);

	/**
	 * Modifies the SQL query to only match resources that the user has
	 * permission to view.
	 *
	 * Note that this method is only intended for use with relatively simple SQL
	 * queries (i.e. queries that have no more than one WHERE clause). For more
	 * complex SQL queries, it is recommended to use the DSLQuery methods
	 * instead.
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
	 * Note that this method is only intended for use with relatively simple SQL
	 * queries (i.e. queries that have no more than one WHERE clause). For more
	 * complex SQL queries, it is recommended to use the DSLQuery methods
	 * instead.
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
	 * Note that this method is only intended for use with relatively simple SQL
	 * queries (i.e. queries that have no more than one WHERE clause). For more
	 * complex SQL queries, it is recommended to use the DSLQuery methods
	 * instead.
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
	 * Note that this method is only intended for use with relatively simple SQL
	 * queries (i.e. queries that have no more than one WHERE clause). For more
	 * complex SQL queries, it is recommended to use the DSLQuery methods
	 * instead.
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
	 * Note that this method is only intended for use with relatively simple SQL
	 * queries (i.e. queries that have no more than one WHERE clause). For more
	 * complex SQL queries, it is recommended to use the DSLQuery methods
	 * instead.
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
	 * Note that this method is only intended for use with relatively simple SQL
	 * queries (i.e. queries that have no more than one WHERE clause). For more
	 * complex SQL queries, it is recommended to use the DSLQuery methods
	 * instead.
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
	 * Note that this method is only intended for use with relatively simple SQL
	 * queries (i.e. queries that have no more than one WHERE clause). For more
	 * complex SQL queries, it is recommended to use the DSLQuery methods
	 * instead.
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
	 * Note that this method is only intended for use with relatively simple SQL
	 * queries (i.e. queries that have no more than one WHERE clause). For more
	 * complex SQL queries, it is recommended to use the DSLQuery methods
	 * instead.
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
	 * Note that this method is only intended for use with relatively simple SQL
	 * queries (i.e. queries that have no more than one WHERE clause). For more
	 * complex SQL queries, it is recommended to use the DSLQuery methods
	 * instead.
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
	 * Note that this method is only intended for use with relatively simple SQL
	 * queries (i.e. queries that have no more than one WHERE clause). For more
	 * complex SQL queries, it is recommended to use the DSLQuery methods
	 * instead.
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
	 * Note that this method is only intended for use with relatively simple SQL
	 * queries (i.e. queries that have no more than one WHERE clause). For more
	 * complex SQL queries, it is recommended to use the DSLQuery methods
	 * instead.
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
	 * Note that this method is only intended for use with relatively simple SQL
	 * queries (i.e. queries that have no more than one WHERE clause). For more
	 * complex SQL queries, it is recommended to use the DSLQuery methods
	 * instead.
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