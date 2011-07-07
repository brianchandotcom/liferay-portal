/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class GroupFinderImpl
	extends BasePersistenceImpl<Group> implements GroupFinder {

	public static String COUNT_BY_GROUP_ID =
		GroupFinder.class.getName() + ".countByGroupId";

	public static String COUNT_BY_C_N_D =
		GroupFinder.class.getName() + ".countByC_N_D";

	public static String FIND_BY_LIVE_GROUPS =
		GroupFinder.class.getName() + ".findByLiveGroups";

	public static String FIND_BY_NO_LAYOUTS =
		GroupFinder.class.getName() + ".findByNoLayouts";

	public static String FIND_BY_NULL_FRIENDLY_URL =
		GroupFinder.class.getName() + ".findByNullFriendlyURL";

	public static String FIND_BY_SYSTEM =
		GroupFinder.class.getName() + ".findBySystem";

	public static String FIND_BY_C_N =
		GroupFinder.class.getName() + ".findByC_N";

	public static String FIND_BY_C_N_D =
		GroupFinder.class.getName() + ".findByC_N_D";

	public static String JOIN_BY_ACTIVE =
		GroupFinder.class.getName() + ".joinByActive";

	public static String JOIN_BY_CREATOR_USER_ID =
		GroupFinder.class.getName() + ".joinByCreatorUserId";

	public static String JOIN_BY_GROUP_ORG =
		GroupFinder.class.getName() + ".joinByGroupOrg";

	public static String JOIN_BY_GROUPS_ORGS =
		GroupFinder.class.getName() + ".joinByGroupsOrgs";

	public static String JOIN_BY_GROUPS_ROLES =
		GroupFinder.class.getName() + ".joinByGroupsRoles";

	public static String JOIN_BY_GROUPS_USER_GROUPS =
		GroupFinder.class.getName() + ".joinByGroupsUserGroups";

	public static String JOIN_BY_LAYOUT_SET =
		GroupFinder.class.getName() + ".joinByLayoutSet";

	public static String JOIN_BY_PAGE_COUNT =
		GroupFinder.class.getName() + ".joinByPageCount";

	public static String JOIN_BY_ROLE_PERMISSIONS =
		GroupFinder.class.getName() + ".joinByRolePermissions";

	public static String JOIN_BY_ROLE_RESOURCE_PERMISSIONS =
		GroupFinder.class.getName() + ".joinByRoleResourcePermissions";

	public static String JOIN_BY_SITE =
		GroupFinder.class.getName() + ".joinBySite";

	public static String JOIN_BY_TYPE =
		GroupFinder.class.getName() + ".joinByType";

	public static String JOIN_BY_USER_GROUP_ROLE =
		GroupFinder.class.getName() + ".joinByUserGroupRole";

	public static String JOIN_BY_USERS_GROUPS =
		GroupFinder.class.getName() + ".joinByUsersGroups";

	public int countByG_U(long groupId, long userId, boolean inherit)
		throws SystemException {

		LinkedHashMap<String, Object> params1 =
			new LinkedHashMap<String, Object>();

		params1.put("usersGroups", userId);

		LinkedHashMap<String, Object> params2 =
			new LinkedHashMap<String, Object>();

		params2.put("groupOrg", userId);

		LinkedHashMap<String, Object> params3 =
			new LinkedHashMap<String, Object>();

		params3.put("groupsOrgs", userId);

		LinkedHashMap<String, Object> params4 =
			new LinkedHashMap<String, Object>();

		params4.put("groupsUserGroups", userId);

		Session session = null;

		try {
			session = openSession();

			int count = countByGroupId(session, groupId, params1);

			if (inherit) {
				count += countByGroupId(session, groupId, params2);
				count += countByGroupId(session, groupId, params3);
				count += countByGroupId(session, groupId, params4);
			}

			return count;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByC_N_D(
			long companyId, String name, String realName, String description,
			LinkedHashMap<String, Object> params)
		throws SystemException {

		long[] classNameIds = {
			PortalUtil.getClassNameId(Group.class),
			PortalUtil.getClassNameId(Organization.class)
		};

		return countByC_C_N_D(
			companyId, classNameIds, name, realName, description, params);
	}

	public int countByC_C_N_D(
			long companyId, long[] classNameIds, String name, String realName,
			String description, LinkedHashMap<String, Object> params)
		throws SystemException {

		name = StringUtil.lowerCase(name);
		description = StringUtil.lowerCase(description);

		if (params == null) {
			params = new LinkedHashMap<String, Object>();
		}

		Long userId = (Long)params.get("usersGroups");

		LinkedHashMap<String, Object> params1 = params;

		LinkedHashMap<String, Object> params2 =
			new LinkedHashMap<String, Object>(params1);

		LinkedHashMap<String, Object> params3 =
			new LinkedHashMap<String, Object>(params1);

		LinkedHashMap<String, Object> params4 =
			new LinkedHashMap<String, Object>(params1);

		if (userId != null) {
			params2.remove("usersGroups");
			params2.put("groupOrg", userId);

			params3.remove("usersGroups");
			params3.put("groupsOrgs", userId);

			params4.remove("usersGroups");
			params4.put("groupsUserGroups", userId);
		}

		Session session = null;

		try {
			session = openSession();

			Set<Long> groupIds = new HashSet<Long>();

			groupIds.addAll(
				countByC_C_N_D(
					session, companyId, classNameIds, name, realName,
					description, params1));

			if (Validator.isNotNull(userId)) {
				groupIds.addAll(
					countByC_C_N_D(
						session, companyId, classNameIds, name, realName,
						description, params2));

				groupIds.addAll(
					countByC_C_N_D(
						session, companyId, classNameIds, name, realName,
						description, params3));

				groupIds.addAll(
					countByC_C_N_D(
						session, companyId, classNameIds, name, realName,
						description, params4));
			}

			return groupIds.size();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Group> findByLiveGroups() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_LIVE_GROUPS);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Group_", GroupImpl.class);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Group> findByNoLayouts(
			long classNameId, boolean privateLayout, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_SYSTEM);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Group_", GroupImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);
			qPos.add(privateLayout);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Group> findByNullFriendlyURL() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_NULL_FRIENDLY_URL);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Group_", GroupImpl.class);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Group> findBySystem(long companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_SYSTEM);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Group_", GroupImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Group findByC_N(long companyId, String name)
		throws NoSuchGroupException, SystemException {

		name = StringUtil.lowerCase(name);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_N);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Group_", GroupImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(name);

			List<Group> list = q.list();

			if (!list.isEmpty()) {
				return list.get(0);
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}

		StringBundler sb = new StringBundler(5);

		sb.append("No Group exists with the key {companyId=");
		sb.append(companyId);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		throw new NoSuchGroupException(sb.toString());
	}

	public List<Group> findByC_N_D(
			long companyId, String name, String realName, String description,
			LinkedHashMap<String, Object> params, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		long[] classNameIds = {
			PortalUtil.getClassNameId(Group.class),
			PortalUtil.getClassNameId(Organization.class)
		};

		return findByC_C_N_D(
			companyId, classNameIds, name, realName, description,params, start,
			end, obc);
	}

	public List<Group> findByC_C_N_D(
			long companyId, long[] classNameIds, String name, String realName,
			String description, LinkedHashMap<String, Object> params,
			int start, int end, OrderByComparator obc)
		throws SystemException {

		name = StringUtil.lowerCase(name);
		description = StringUtil.lowerCase(description);

		if (params == null) {
			params = new LinkedHashMap<String, Object>();
		}

		Long userId = (Long)params.get("usersGroups");
		boolean inherit = GetterUtil.getBoolean(
			(Serializable)params.get("inherit"), true);

		LinkedHashMap<String, Object> params1 = params;

		LinkedHashMap<String, Object> params2 =
			new LinkedHashMap<String, Object>(params1);

		LinkedHashMap<String, Object> params3 =
			new LinkedHashMap<String, Object>(params1);

		LinkedHashMap<String, Object> params4 =
			new LinkedHashMap<String, Object>(params1);

		if ((userId != null) && inherit) {
			params2.remove("usersGroups");
			params2.put("groupOrg", userId);

			params3.remove("usersGroups");
			params3.put("groupsOrgs", userId);

			params4.remove("usersGroups");
			params4.put("groupsUserGroups", userId);
		}

		String findByCND_SQL = CustomSQLUtil.get(FIND_BY_C_N_D);

		if (classNameIds == null) {
			findByCND_SQL = StringUtil.replace(
				findByCND_SQL, "AND (Group_.classNameId = ?)",
				StringPool.BLANK);
		}
		else {
			findByCND_SQL = StringUtil.replace(
				findByCND_SQL, "Group_.classNameId = ?",
				"Group_.classNameId = ".concat(
					StringUtil.merge(
						classNameIds, " OR Group_.classNameId = ")));
		}

		StringBundler sb = new StringBundler();

		sb.append("(");
		sb.append(replaceJoinAndWhere(findByCND_SQL, params1));
		sb.append(")");

		if (Validator.isNotNull(userId) && inherit) {
			sb.append(" UNION (");
			sb.append(replaceJoinAndWhere(findByCND_SQL, params2));
			sb.append(") UNION (");
			sb.append(replaceJoinAndWhere(findByCND_SQL, params3));
			sb.append(") UNION (");
			sb.append(replaceJoinAndWhere(findByCND_SQL, params4));
			sb.append(")");
		}

		String sql = sb.toString();

		sql = CustomSQLUtil.replaceOrderBy(sql, obc);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar("groupId", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			setJoin(qPos, params1);
			qPos.add(companyId);
			qPos.add(name);
			qPos.add(realName);
			qPos.add(name);
			qPos.add(description);
			qPos.add(description);

			if (Validator.isNotNull(userId) && inherit) {
				setJoin(qPos, params2);
				qPos.add(companyId);
				qPos.add(name);
				qPos.add(realName);
				qPos.add(name);
				qPos.add(description);
				qPos.add(description);

				setJoin(qPos, params3);
				qPos.add(companyId);
				qPos.add(name);
				qPos.add(realName);
				qPos.add(name);
				qPos.add(description);
				qPos.add(description);

				setJoin(qPos, params4);
				qPos.add(companyId);
				qPos.add(name);
				qPos.add(realName);
				qPos.add(name);
				qPos.add(description);
				qPos.add(description);
			}

			List<Long> groupIds = (List<Long>)QueryUtil.list(
				q, getDialect(), start, end);

			List<Group> groups = new ArrayList<Group>(groupIds.size());

			for (Long groupId : groupIds) {
				Group group = GroupUtil.findByPrimaryKey(groupId);

				groups.add(group);
			}

			return groups;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected int countByGroupId(
		Session session, long groupId, LinkedHashMap<String, Object> params) {

		String sql = CustomSQLUtil.get(COUNT_BY_GROUP_ID);

		sql = StringUtil.replace(sql, "[$JOIN$]", getJoin(params));
		sql = StringUtil.replace(sql, "[$WHERE$]", getWhere(params));

		SQLQuery q = session.createSQLQuery(sql);

		q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

		QueryPos qPos = QueryPos.getInstance(q);

		setJoin(qPos, params);
		qPos.add(groupId);

		Iterator<Long> itr = q.list().iterator();

		if (itr.hasNext()) {
			Long count = itr.next();

			if (count != null) {
				return count.intValue();
			}
		}

		return 0;
	}

	protected List<Long> countByC_C_N_D(
		Session session, long companyId, long[] classNameIds, String name,
		String realName, String description,
		LinkedHashMap<String, Object> params) {

		String sql = CustomSQLUtil.get(COUNT_BY_C_N_D);

		if (classNameIds == null) {
			sql = StringUtil.replace(
				sql, "AND (Group_.classNameId = ?)", StringPool.BLANK);
		}
		else {
			sql = StringUtil.replace(
				sql, "Group_.classNameId = ?",
				"Group_.classNameId = ".concat(
					StringUtil.merge(
						classNameIds, " OR Group_.classNameId = ")));
		}

		sql = StringUtil.replace(sql, "[$JOIN$]", getJoin(params));
		sql = StringUtil.replace(sql, "[$WHERE$]", getWhere(params));

		SQLQuery q = session.createSQLQuery(sql);

		q.addScalar("groupId", Type.LONG);

		QueryPos qPos = QueryPos.getInstance(q);

		setJoin(qPos, params);
		qPos.add(companyId);
		qPos.add(name);
		qPos.add(realName);
		qPos.add(name);
		qPos.add(description);
		qPos.add(description);

		return q.list();
	}

	protected String getJoin(LinkedHashMap<String, Object> params) {
		if ((params == null) || params.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(params.size());

		Iterator<Map.Entry<String, Object>> itr = params.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, Object> entry = itr.next();

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

		if (key.equals("groupOrg")) {
			join = CustomSQLUtil.get(JOIN_BY_GROUP_ORG);
		}
		else if (key.equals("groupsOrgs")) {
			join = CustomSQLUtil.get(JOIN_BY_GROUPS_ORGS);
		}
		else if (key.equals("groupsRoles")) {
			join = CustomSQLUtil.get(JOIN_BY_GROUPS_ROLES);
		}
		else if (key.equals("groupsUserGroups")) {
			join = CustomSQLUtil.get(JOIN_BY_GROUPS_USER_GROUPS);
		}
		else if (key.equals("layoutSet")) {
			join = CustomSQLUtil.get(JOIN_BY_LAYOUT_SET);
		}
		else if (key.equals("pageCount")) {
			join = CustomSQLUtil.get(JOIN_BY_PAGE_COUNT);
		}
		else if (key.equals("rolePermissions")) {
			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
				join = CustomSQLUtil.get(JOIN_BY_ROLE_RESOURCE_PERMISSIONS);
			}
			else {
				join = CustomSQLUtil.get(JOIN_BY_ROLE_PERMISSIONS);
			}
		}
		else if (key.equals("site")) {
			join = CustomSQLUtil.get(JOIN_BY_SITE);
		}
		else if (key.equals("userGroupRole")) {
			join = CustomSQLUtil.get(JOIN_BY_USER_GROUP_ROLE);
		}
		else if (key.equals("usersGroups")) {
			join = CustomSQLUtil.get(JOIN_BY_USERS_GROUPS);
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

		Iterator<Map.Entry<String, Object>> itr = params.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, Object> entry = itr.next();

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

		if (key.equals("active")) {
			join = CustomSQLUtil.get(JOIN_BY_ACTIVE);
		}
		else if (key.equals("creatorUserId")) {
			join = CustomSQLUtil.get(JOIN_BY_CREATOR_USER_ID);
		}
		else if (key.equals("groupOrg")) {
			join = CustomSQLUtil.get(JOIN_BY_GROUP_ORG);
		}
		else if (key.equals("groupsOrgs")) {
			join = CustomSQLUtil.get(JOIN_BY_GROUPS_ORGS);
		}
		else if (key.equals("groupsRoles")) {
			join = CustomSQLUtil.get(JOIN_BY_GROUPS_ROLES);
		}
		else if (key.equals("groupsUserGroups")) {
			join = CustomSQLUtil.get(JOIN_BY_GROUPS_USER_GROUPS);
		}
		else if (key.equals("layoutSet")) {
			join = CustomSQLUtil.get(JOIN_BY_LAYOUT_SET);
		}
		else if (key.equals("pageCount")) {
			join = CustomSQLUtil.get(JOIN_BY_PAGE_COUNT);
		}
		else if (key.equals("rolePermissions")) {
			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
				join = CustomSQLUtil.get(JOIN_BY_ROLE_RESOURCE_PERMISSIONS);
			}
			else {
				join = CustomSQLUtil.get(JOIN_BY_ROLE_PERMISSIONS);
			}
		}
		else if (key.equals("site")) {
			join = CustomSQLUtil.get(JOIN_BY_SITE);
		}
		else if (key.equals("type")) {
			join = CustomSQLUtil.get(JOIN_BY_TYPE);
		}
		else if (key.equals("types")) {
			List<Integer> types = (List<Integer>)value;

			if (!types.isEmpty()) {
				StringBundler sb = new StringBundler(types.size() * 2 + 1);

				sb.append("WHERE (");

				for (int i = 0; i < types.size(); i++) {
					sb.append("(Group_.type_ = ?) ");

					if ((i + 1) < types.size()) {
						sb.append("OR ");
					}
				}

				sb.append(")");

				join = sb.toString();
			}
		}
		else if (key.equals("userGroupRole")) {
			join = CustomSQLUtil.get(JOIN_BY_USER_GROUP_ROLE);
		}
		else if (key.equals("usersGroups")) {
			join = CustomSQLUtil.get(JOIN_BY_USERS_GROUPS);
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

	protected String replaceJoinAndWhere(
		String sql, LinkedHashMap<String, Object> params) {

		sql = StringUtil.replace(sql, "[$JOIN$]", getJoin(params));
		sql = StringUtil.replace(sql, "[$WHERE$]", getWhere(params));

		return sql;
	}

	protected void setJoin(
		QueryPos qPos, LinkedHashMap<String, Object> params) {

		if (params != null) {
			Iterator<Map.Entry<String, Object>> itr =
				params.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = itr.next();

				String key = entry.getKey();

				if (key.equals("active") || key.equals("layoutSet") ||
					key.equals("site")) {

					Boolean value = (Boolean)entry.getValue();

					qPos.add(value);
				}
				else if (key.equals("pageCount")) {
				}
				else if (key.equals("rolePermissions")) {
					List<Object> values = (List<Object>)entry.getValue();

					for (int i = 0; i < values.size(); i++) {
						Object value = values.get(i);

						if (value instanceof Integer) {
							Integer valueInteger = (Integer)value;

							qPos.add(valueInteger);
						}
						else if (value instanceof Long) {
							Long valueLong = (Long)value;

							qPos.add(valueLong);
						}
						else if (value instanceof String) {
							String valueString = (String)value;

							qPos.add(valueString);
						}
					}
				}
				else if (key.equals("types")) {
					List<Integer> values = (List<Integer>)entry.getValue();

					for (int i = 0; i < values.size(); i++) {
						Integer value = values.get(i);

						qPos.add(value);
					}
				}
				else if (key.equals("userGroupRole")) {
					List<Long> values = (List<Long>)entry.getValue();

					Long userId = values.get(0);
					Long roleId = values.get(1);

					qPos.add(userId);
					qPos.add(roleId);
				}
				else {
					Object value = entry.getValue();

					if (value instanceof Integer) {
						Integer valueInteger = (Integer)value;

						if (Validator.isNotNull(valueInteger)) {
							qPos.add(valueInteger);
						}
					}
					else if (value instanceof Long) {
						Long valueLong = (Long)value;

						if (Validator.isNotNull(valueLong)) {
							qPos.add(valueLong);
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
	}

}