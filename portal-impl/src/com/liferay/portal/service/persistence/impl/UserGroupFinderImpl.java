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

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.NoSuchUserGroupException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.persistence.UserGroupFinder;
import com.liferay.portal.kernel.service.persistence.UserGroupUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.impl.UserGroupImpl;
import com.liferay.portal.service.persistence.constants.UserGroupFinderConstants;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Charles May
 */
public class UserGroupFinderImpl
	extends UserGroupFinderBaseImpl implements UserGroupFinder {

	public static final String COUNT_BY_C_N_D =
		UserGroupFinder.class.getName() + ".countByC_N_D";

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
		long companyId, String keywords, LinkedHashMap<String, Object> params) {

		return doCountByKeywords(companyId, keywords, params, false);
	}

	@Override
	public int countByC_N_D(
		long companyId, String name, String description,
		LinkedHashMap<String, Object> params, boolean andOperator) {

		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description);

		return countByC_N_D(
			companyId, names, descriptions, params, andOperator);
	}

	@Override
	public int countByC_N_D(
		long companyId, String[] names, String[] descriptions,
		LinkedHashMap<String, Object> params, boolean andOperator) {

		return doCountByC_N_D(
			companyId, names, descriptions, params, andOperator, false);
	}

	@Override
	public int filterCountByKeywords(
		long companyId, String keywords, LinkedHashMap<String, Object> params) {

		return doCountByKeywords(companyId, keywords, params, true);
	}

	@Override
	public int filterCountByC_N_D(
		long companyId, String name, String description,
		LinkedHashMap<String, Object> params, boolean andOperator) {

		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description);

		return filterCountByC_N_D(
			companyId, names, descriptions, params, andOperator);
	}

	@Override
	public int filterCountByC_N_D(
		long companyId, String[] names, String[] descriptions,
		LinkedHashMap<String, Object> params, boolean andOperator) {

		return doCountByC_N_D(
			companyId, names, descriptions, params, andOperator, true);
	}

	@Override
	public List<UserGroup> filterFindByKeywords(
		long companyId, String keywords, LinkedHashMap<String, Object> params,
		int start, int end, OrderByComparator<UserGroup> obc) {

		return doFindByKeywords(
			companyId, keywords, params, start, end, obc, true);
	}

	@Override
	public List<UserGroup> filterFindByC_N_D(
		long companyId, String name, String description,
		LinkedHashMap<String, Object> params, boolean andOperator, int start,
		int end, OrderByComparator<UserGroup> obc) {

		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description);

		return filterFindByC_N_D(
			companyId, names, descriptions, params, andOperator, start, end,
			obc);
	}

	@Override
	public List<UserGroup> filterFindByC_N_D(
		long companyId, String[] names, String[] descriptions,
		LinkedHashMap<String, Object> params, boolean andOperator, int start,
		int end, OrderByComparator<UserGroup> obc) {

		return doFindByC_N_D(
			companyId, names, descriptions, params, andOperator, start, end,
			obc, true);
	}

	@Override
	public List<UserGroup> findByKeywords(
		long companyId, String keywords, LinkedHashMap<String, Object> params,
		int start, int end, OrderByComparator<UserGroup> obc) {

		return doFindByKeywords(
			companyId, keywords, params, start, end, obc, false);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public UserGroup findByC_N(long companyId, String name)
		throws NoSuchUserGroupException {

		return UserGroupUtil.findByC_N(companyId, name);
	}

	@Override
	public List<UserGroup> findByC_N_D(
		long companyId, String name, String description,
		LinkedHashMap<String, Object> params, boolean andOperator, int start,
		int end, OrderByComparator<UserGroup> obc) {

		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description);

		return findByC_N_D(
			companyId, names, descriptions, params, andOperator, start, end,
			obc);
	}

	@Override
	public List<UserGroup> findByC_N_D(
		long companyId, String[] names, String[] descriptions,
		LinkedHashMap<String, Object> params, boolean andOperator, int start,
		int end, OrderByComparator<UserGroup> obc) {

		return doFindByC_N_D(
			companyId, names, descriptions, params, andOperator, start, end,
			obc, false);
	}

	protected int doCountByKeywords(
		long companyId, String keywords, LinkedHashMap<String, Object> params,
		boolean inlineSQLHelper) {

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

		return doCountByC_N_D(
			companyId, names, descriptions, params, andOperator,
			inlineSQLHelper);
	}

	protected int doCountByC_N_D(
		long companyId, String[] names, String[] descriptions,
		LinkedHashMap<String, Object> params, boolean andOperator,
		boolean inlineSQLHelper) {

		names = CustomSQLUtil.keywords(names);
		descriptions = CustomSQLUtil.keywords(descriptions);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_C_N_D);

			sql = CustomSQLUtil.replaceKeywords(
				sql, "LOWER(UserGroup.name)", StringPool.LIKE, false, names);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "LOWER(UserGroup.description)", StringPool.LIKE, true,
				descriptions);
			sql = StringUtil.replace(sql, "[$JOIN$]", getJoin(params));
			sql = StringUtil.replace(sql, "[$WHERE$]", getWhere(params));
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			if (inlineSQLHelper &&
				InlineSQLHelperUtil.isEnabled(companyId, 0)) {

				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, UserGroup.class.getName(), "UserGroup.userGroupId",
					null, null, new long[] {0}, null);
			}

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

	protected List<UserGroup> doFindByKeywords(
		long companyId, String keywords, LinkedHashMap<String, Object> params,
		int start, int end, OrderByComparator<UserGroup> obc,
		boolean inlineSQLHelper) {

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

		return doFindByC_N_D(
			companyId, names, descriptions, params, andOperator, start, end,
			obc, inlineSQLHelper);
	}

	protected List<UserGroup> doFindByC_N_D(
		long companyId, String[] names, String[] descriptions,
		LinkedHashMap<String, Object> params, boolean andOperator, int start,
		int end, OrderByComparator<UserGroup> obc, boolean inlineSQLHelper) {

		names = CustomSQLUtil.keywords(names);
		descriptions = CustomSQLUtil.keywords(descriptions);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_N_D);

			sql = CustomSQLUtil.replaceKeywords(
				sql, "LOWER(UserGroup.name)", StringPool.LIKE, false, names);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "LOWER(UserGroup.description)", StringPool.LIKE, true,
				descriptions);

			sql = StringUtil.replace(sql, "[$JOIN$]", getJoin(params));
			sql = StringUtil.replace(sql, "[$WHERE$]", getWhere(params));
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);
			sql = CustomSQLUtil.replaceOrderBy(sql, obc);

			if (inlineSQLHelper &&
				InlineSQLHelperUtil.isEnabled(companyId, 0)) {

				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, UserGroup.class.getName(), "UserGroup.userGroupId",
					null, null, new long[] {0}, null);
			}

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

			if (!_isFinderParam(key)) {
				continue;
			}

			if (Validator.isNotNull(entry.getValue())) {
				sb.append(getJoin(key));
			}
		}

		return sb.toString();
	}

	protected String getJoin(String key) {
		String join = StringPool.BLANK;

		if (key.equals(
				UserGroupFinderConstants.PARAM_KEY_USER_GROUP_GROUP_ROLE)) {

			join = CustomSQLUtil.get(JOIN_BY_USER_GROUP_GROUP_ROLE);
		}
		else if (key.equals(
					UserGroupFinderConstants.PARAM_KEY_USER_GROUPS_GROUPS)) {

			join = CustomSQLUtil.get(JOIN_BY_USER_GROUPS_GROUPS);
		}
		else if (key.equals(
					UserGroupFinderConstants.PARAM_KEY_USER_GROUPS_ROLES)) {

			join = CustomSQLUtil.get(JOIN_BY_USER_GROUPS_ROLES);
		}
		else if (key.equals(
					UserGroupFinderConstants.PARAM_KEY_USER_GROUPS_TEAMS)) {

			join = CustomSQLUtil.get(JOIN_BY_USER_GROUPS_TEAMS);
		}
		else if (key.equals(
					UserGroupFinderConstants.PARAM_KEY_USER_GROUPS_USERS)) {

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

			if (!_isFinderParam(key)) {
				continue;
			}

			Object value = entry.getValue();

			if (Validator.isNotNull(value)) {
				sb.append(getWhere(key, value));
			}
		}

		return sb.toString();
	}

	protected String getWhere(String key, Object value) {
		String join = StringPool.BLANK;

		if (key.equals(
				UserGroupFinderConstants.PARAM_KEY_USER_GROUP_GROUP_ROLE)) {

			join = CustomSQLUtil.get(JOIN_BY_USER_GROUP_GROUP_ROLE);
		}
		else if (key.equals(
					UserGroupFinderConstants.PARAM_KEY_USER_GROUPS_GROUPS)) {

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
		else if (key.equals(
					UserGroupFinderConstants.PARAM_KEY_USER_GROUPS_ROLES)) {

			join = CustomSQLUtil.get(JOIN_BY_USER_GROUPS_ROLES);
		}
		else if (key.equals(
					UserGroupFinderConstants.PARAM_KEY_USER_GROUPS_TEAMS)) {

			join = CustomSQLUtil.get(JOIN_BY_USER_GROUPS_TEAMS);
		}
		else if (key.equals(
					UserGroupFinderConstants.PARAM_KEY_USER_GROUPS_USERS)) {

			join = CustomSQLUtil.get(JOIN_BY_USER_GROUPS_USERS);
		}

		if (Validator.isNotNull(join)) {
			int pos = join.indexOf("WHERE");

			if (pos != -1) {
				join = join.substring(pos + 5);

				join = join.concat(" AND ");
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
			if (!_isFinderParam(entry.getKey())) {
				continue;
			}

			Object value = entry.getValue();

			if (value instanceof Long) {
				Long valueLong = (Long)value;

				if (Validator.isNotNull(valueLong)) {
					qPos.add(valueLong);
				}
			}
			else if (value instanceof Long[]) {
				Long[] valueArray = (Long[])value;

				for (Long curValue : valueArray) {
					if (Validator.isNotNull(curValue)) {
						qPos.add(curValue);
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

	private boolean _isFinderParam(String key) {
		return ArrayUtil.contains(UserGroupFinderConstants.PARAM_KEYS, key);
	}

}