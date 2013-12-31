/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchUserGroupException;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.impl.UserGroupImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Charles May
 */
public class UserGroupFinderImpl
	extends BasePersistenceImpl<UserGroup> implements UserGroupFinder {

	public static final String COUNT_BY_C_N_D =
		UserGroupFinder.class.getName() + ".countByC_N_D";

	public static final String FIND_BY_C_N =
		UserGroupFinder.class.getName() + ".findByC_N";

	public static final String FIND_BY_C_N_D =
		UserGroupFinder.class.getName() + ".findByC_N_D";

	public static final String JOIN_BY_USER_GROUP_GROUP_ROLE =
		UserGroupFinder.class.getName() + ".joinByUserGroupGroupRole";

	public static final String JOIN_BY_USER_GROUPS_GROUPS =
		UserGroupFinder.class.getName() + ".joinByUserGroupsGroups";

	public static final String JOIN_BY_USER_GROUPS_ROLES =
		UserGroupFinder.class.getName() + ".joinByUserGroupsRoles";

	public static final String JOIN_BY_USER_GROUPS_TEAMS =
		UserGroupFinder.class.getName() + ".joinByUserGroupsTeams";

	public static final String JOIN_BY_USER_GROUPS_USERS =
		UserGroupFinder.class.getName() + ".joinByUserGroupsUsers";

	@Override
	public int countByKeywords(
			long companyId, String keywords,
			LinkedHashMap<String, Object> params)
		throws SystemException {

		String[] names = null;
		String[] descriptions = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			names = CustomSQLUtil.keywords(keywords);
			descriptions = CustomSQLUtil.keywords(keywords);
		}
		else {
			andOperator = true;
		}

		return countByC_N_D(
			companyId, names, descriptions, params, andOperator);
	}

	@Override
	public int countByC_N_D(
			long companyId, String name, String description,
			LinkedHashMap<String, Object> params, boolean andOperator)
		throws SystemException {

		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description);

		return countByC_N_D(
			companyId, names, descriptions, params, andOperator);
	}

	@Override
	public int countByC_N_D(
			long companyId, String[] names, String[] descriptions,
			LinkedHashMap<String, Object> params, boolean andOperator)
		throws SystemException {

		names = CustomSQLUtil.keywords(names);
		descriptions = CustomSQLUtil.keywords(descriptions);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_C_N_D);

			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(UserGroup.name)", StringPool.LIKE, false, names);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(UserGroup.description)", StringPool.LIKE, true,
				descriptions);
			sql = StringUtil.replace(sql, "[$JOIN$]", getJoin(params));
			sql = StringUtil.replace(sql, "[$WHERE$]", getWhere(params));
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			setJoin(qPos, params);

			qPos.add(companyId);
			qPos.add(names, 2);
			qPos.add(descriptions, 2);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<UserGroup> findByKeywords(
			long companyId, String keywords,
			LinkedHashMap<String, Object> params, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		String[] names = null;
		String[] descriptions = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			names = CustomSQLUtil.keywords(keywords);
			descriptions = CustomSQLUtil.keywords(keywords);
		}
		else {
			andOperator = true;
		}

		return findByC_N_D(
			companyId, names, descriptions, params, andOperator, start, end,
			obc);
	}

	@Override
	public UserGroup findByC_N(long companyId, String name)
		throws NoSuchUserGroupException, SystemException {

		name = StringUtil.lowerCase(name);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_N);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("UserGroup", UserGroupImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(name);

			List<UserGroup> userGroups = q.list();

			if (!userGroups.isEmpty()) {
				return userGroups.get(0);
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}

		StringBundler sb = new StringBundler(5);

		sb.append("No UserGroup exists with the key {companyId=");
		sb.append(companyId);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		throw new NoSuchUserGroupException(sb.toString());
	}

	@Override
	public List<UserGroup> findByC_N_D(
			long companyId, String name, String description,
			LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end, OrderByComparator obc)
		throws SystemException {

		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description);

		return findByC_N_D(
			companyId, names, descriptions, params, andOperator, start, end,
			obc);
	}

	@Override
	public List<UserGroup> findByC_N_D(
			long companyId, String[] names, String[] descriptions,
			LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end, OrderByComparator obc)
		throws SystemException {

		names = CustomSQLUtil.keywords(names);
		descriptions = CustomSQLUtil.keywords(descriptions);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_N_D);

			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(UserGroup.name)", StringPool.LIKE, false, names);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(UserGroup.description)", StringPool.LIKE, true,
				descriptions);

			sql = StringUtil.replace(sql, "[$JOIN$]", getJoin(params));
			sql = StringUtil.replace(sql, "[$WHERE$]", getWhere(params));
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);
			sql = CustomSQLUtil.replaceOrderBy(sql, obc);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("UserGroup", UserGroupImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			setJoin(qPos, params);

			qPos.add(companyId);
			qPos.add(names, 2);
			qPos.add(descriptions, 2);

			return (List<UserGroup>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getJoin(LinkedHashMap<String, Object> params) {
		if ((params == null) || params.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(params.size());

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();

			if (Validator.isNotNull(value)) {
				sb.append(getJoin(key));
			}
		}

		return sb.toString();
	}

	protected String getJoin(String key) {
		String join = StringPool.BLANK;

		if (key.equals("userGroupGroupRole")) {
			join = CustomSQLUtil.get(JOIN_BY_USER_GROUP_GROUP_ROLE);
		}
		else if (key.equals("userGroupsGroups")) {
			join = CustomSQLUtil.get(JOIN_BY_USER_GROUPS_GROUPS);
		}
		else if (key.equals("userGroupsRoles")) {
			join = CustomSQLUtil.get(JOIN_BY_USER_GROUPS_ROLES);
		}
		else if (key.equals("userGroupsTeams")) {
			join = CustomSQLUtil.get(JOIN_BY_USER_GROUPS_TEAMS);
		}
		else if (key.equals("userGroupsUsers")) {
			join = CustomSQLUtil.get(JOIN_BY_USER_GROUPS_USERS);
		}

		if (Validator.isNotNull(join)) {
			int pos = join.indexOf("WHERE");

			if (pos != -1) {
				join = join.substring(0, pos);
			}
		}

		return join;
	}

	protected String getWhere(LinkedHashMap<String, Object> params) {
		if ((params == null) || params.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(params.size());

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();

			if (Validator.isNotNull(value)) {
				sb.append(getWhere(key, value));
			}
		}

		return sb.toString();
	}

	protected String getWhere(String key, Object value) {
		String join = StringPool.BLANK;

		if (key.equals("userGroupGroupRole")) {
			join = CustomSQLUtil.get(JOIN_BY_USER_GROUP_GROUP_ROLE);
		}
		else if (key.equals("userGroupsGroups")) {
			if (value instanceof Long) {
				join = CustomSQLUtil.get(JOIN_BY_USER_GROUPS_GROUPS);
			}
			else if (value instanceof Long[]) {
				Long[] userGroupIds = (Long[])value;

				if (userGroupIds.length == 0) {
					join = "WHERE (Groups_UserGroups.groupId = -1)";
				}
				else {
					StringBundler sb = new StringBundler(
						userGroupIds.length * 2 + 1);

					sb.append("WHERE (");

					for (int i = 0; i < userGroupIds.length; i++) {
						sb.append("(Groups_UserGroups.groupId = ?) ");

						if ((i + 1) < userGroupIds.length) {
							sb.append("OR ");
						}
					}

					sb.append(StringPool.CLOSE_PARENTHESIS);

					join = sb.toString();
				}
			}
		}
		else if (key.equals("userGroupsRoles")) {
			join = CustomSQLUtil.get(JOIN_BY_USER_GROUPS_ROLES);
		}
		else if (key.equals("userGroupsTeams")) {
			join = CustomSQLUtil.get(JOIN_BY_USER_GROUPS_TEAMS);
		}
		else if (key.equals("userGroupsUsers")) {
			join = CustomSQLUtil.get(JOIN_BY_USER_GROUPS_USERS);
		}

		if (Validator.isNotNull(join)) {
			int pos = join.indexOf("WHERE");

			if (pos != -1) {
				join = join.substring(pos + 5, join.length()).concat(" AND ");
			}
			else {
				join = StringPool.BLANK;
			}
		}

		return join;
	}

	protected void setJoin(
		QueryPos qPos, LinkedHashMap<String, Object> params) {

		if (params == null) {
			return;
		}

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			Object value = entry.getValue();

			if (value instanceof Long) {
				Long valueLong = (Long)value;

				if (Validator.isNotNull(valueLong)) {
					qPos.add(valueLong);
				}
			}
			else if (value instanceof Long[]) {
				Long[] valueArray = (Long[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (Validator.isNotNull(valueArray[i])) {
						qPos.add(valueArray[i]);
					}
				}
			}
			else if (value instanceof String) {
				String valueString = (String)value;

				if (Validator.isNotNull(valueString)) {
					qPos.add(valueString);
				}
			}
		}
	}

}