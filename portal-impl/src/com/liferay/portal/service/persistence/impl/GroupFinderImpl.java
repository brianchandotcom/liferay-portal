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
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.service.impl.GroupLocalServiceImpl;
import com.liferay.portal.service.persistence.GroupFinder;
import com.liferay.portal.service.persistence.GroupUtil;
import com.liferay.portal.util.comparator.GroupNameComparator;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class GroupFinderImpl
	extends BasePersistenceImpl<Group> implements GroupFinder {

	public static final String COUNT_BY_LAYOUTS =
		GroupFinder.class.getName() + ".countByLayouts";

	public static final String COUNT_BY_GROUP_ID =
		GroupFinder.class.getName() + ".countByGroupId";

	public static final String COUNT_BY_C_C_PG_N_D =
		GroupFinder.class.getName() + ".countByC_C_PG_N_D";

	public static final String FIND_BY_LAYOUTS =
		GroupFinder.class.getName() + ".findByLayouts";

	public static final String FIND_BY_LIVE_GROUPS =
		GroupFinder.class.getName() + ".findByLiveGroups";

	public static final String FIND_BY_NO_LAYOUTS =
		GroupFinder.class.getName() + ".findByNoLayouts";

	public static final String FIND_BY_NULL_FRIENDLY_URL =
		GroupFinder.class.getName() + ".findByNullFriendlyURL";

	public static final String FIND_BY_SYSTEM =
		GroupFinder.class.getName() + ".findBySystem";

	public static final String FIND_BY_C_C =
		GroupFinder.class.getName() + ".findByC_C";

	public static final String FIND_BY_C_P =
		GroupFinder.class.getName() + ".findByC_P";

	public static final String FIND_BY_C_N =
		GroupFinder.class.getName() + ".findByC_N";

	public static final String FIND_BY_C_C_PG_N_D =
		GroupFinder.class.getName() + ".findByC_C_PG_N_D";

	public static final String JOIN_BY_ACTIVE =
		GroupFinder.class.getName() + ".joinByActive";

	public static final String JOIN_BY_CREATOR_USER_ID =
		GroupFinder.class.getName() + ".joinByCreatorUserId";

	public static final String JOIN_BY_GROUP_ORG =
		GroupFinder.class.getName() + ".joinByGroupOrg";

	public static final String JOIN_BY_GROUPS_ORGS =
		GroupFinder.class.getName() + ".joinByGroupsOrgs";

	public static final String JOIN_BY_GROUPS_ROLES =
		GroupFinder.class.getName() + ".joinByGroupsRoles";

	public static final String JOIN_BY_GROUPS_USER_GROUPS =
		GroupFinder.class.getName() + ".joinByGroupsUserGroups";

	public static final String JOIN_BY_LAYOUT_SET =
		GroupFinder.class.getName() + ".joinByLayoutSet";

	public static final String JOIN_BY_MANUAL_MEMBERSHIP =
		GroupFinder.class.getName() + ".joinByManualMembership";

	public static final String JOIN_BY_MEMBERSHIP_RESTRICTION =
		GroupFinder.class.getName() + ".joinByMembershipRestriction";

	public static final String JOIN_BY_PAGE_COUNT =
		GroupFinder.class.getName() + ".joinByPageCount";

	public static final String JOIN_BY_ROLE_RESOURCE_PERMISSIONS =
		GroupFinder.class.getName() + ".joinByRoleResourcePermissions";

	public static final String JOIN_BY_ROLE_RESOURCE_TYPE_PERMISSIONS =
		GroupFinder.class.getName() + ".joinByRoleResourceTypePermissions";

	public static final String JOIN_BY_SITE =
		GroupFinder.class.getName() + ".joinBySite";

	public static final String JOIN_BY_TYPE =
		GroupFinder.class.getName() + ".joinByType";

	public static final String JOIN_BY_USER_GROUP_ROLE =
		GroupFinder.class.getName() + ".joinByUserGroupRole";

	public static final String JOIN_BY_USERS_GROUPS =
		GroupFinder.class.getName() + ".joinByUsersGroups";

	@Override
	public int countByLayouts(
		long companyId, long parentGroupId, boolean site) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_LAYOUTS);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(parentGroupId);
			qPos.add(site);

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
	public int countByG_U(long groupId, long userId, boolean inherit) {
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

	@Override
	public int countByC_C_PG_N_D(
		long companyId, long[] classNameIds, long parentGroupId, String[] names,
		String[] descriptions, LinkedHashMap<String, Object> params,
		boolean andOperator) {

		String parentGroupIdComparator = StringPool.EQUAL;

		if (parentGroupId == GroupConstants.ANY_PARENT_GROUP_ID) {
			parentGroupIdComparator = StringPool.NOT_EQUAL;
		}

		names = CustomSQLUtil.keywords(names);
		descriptions = CustomSQLUtil.keywords(descriptions);

		if (params == null) {
			params = _emptyLinkedHashMap;
		}

		LinkedHashMap<String, Object> params1 = params;

		LinkedHashMap<String, Object> params2 = null;

		LinkedHashMap<String, Object> params3 = null;

		LinkedHashMap<String, Object> params4 = null;

		Long userId = (Long)params.get("usersGroups");

		boolean doUnion = Validator.isNotNull(userId);

		if (doUnion) {
			params2 = new LinkedHashMap<String, Object>(params1);

			params2.remove("usersGroups");
			params2.put("groupOrg", userId);

			params3 = new LinkedHashMap<String, Object>(params1);

			params3.remove("usersGroups");
			params3.put("groupsOrgs", userId);

			params4 = new LinkedHashMap<String, Object>(params1);

			params4.remove("usersGroups");
			params4.put("groupsUserGroups", userId);
		}

		Session session = null;

		try {
			session = openSession();

			Set<Long> groupIds = new HashSet<Long>();

			groupIds.addAll(
				countByC_C_PG_N_D(
					session, companyId, classNameIds, parentGroupId,
					parentGroupIdComparator, names, descriptions, params1,
					andOperator));

			if (doUnion) {
				groupIds.addAll(
					countByC_C_PG_N_D(
						session, companyId, classNameIds, parentGroupId,
						parentGroupIdComparator, names, descriptions, params2,
						andOperator));

				groupIds.addAll(
					countByC_C_PG_N_D(
						session, companyId, classNameIds, parentGroupId,
						parentGroupIdComparator, names, descriptions, params3,
						andOperator));

				groupIds.addAll(
					countByC_C_PG_N_D(
						session, companyId, classNameIds, parentGroupId,
						parentGroupIdComparator, names, descriptions, params4,
						andOperator));
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

	@Deprecated
	@Override
	public List<Group> findByLayouts(
		long companyId, long parentGroupId, boolean site, int start, int end) {

		return findByLayouts(companyId, parentGroupId, site, start, end, null);
	}

	@Override
	public List<Group> findByLayouts(
		long companyId, long parentGroupId, boolean site, int start, int end,
		OrderByComparator obc) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_LAYOUTS);

			sql = CustomSQLUtil.replaceOrderBy(sql, obc);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("Group_", GroupImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(parentGroupId);
			qPos.add(site);

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<Group> findByLiveGroups() {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_LIVE_GROUPS);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("Group_", GroupImpl.class);

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<Group> findByNoLayouts(
		long classNameId, boolean privateLayout, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_NO_LAYOUTS);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("Group_", GroupImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);
			qPos.add(privateLayout);

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<Group> findByNullFriendlyURL() {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_NULL_FRIENDLY_URL);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("Group_", GroupImpl.class);

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<Group> findBySystem(long companyId) {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_SYSTEM);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("Group_", GroupImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<Group> findByCompanyId(
		long companyId, LinkedHashMap<String, Object> params, int start,
		int end, OrderByComparator obc) {

		if (params == null) {
			params = _emptyLinkedHashMap;
		}

		LinkedHashMap<String, Object> params1 = params;

		LinkedHashMap<String, Object> params2 = null;

		LinkedHashMap<String, Object> params3 = null;

		LinkedHashMap<String, Object> params4 = null;

		Long userId = (Long)params.get("usersGroups");
		boolean inherit = GetterUtil.getBoolean(params.get("inherit"), true);

		boolean doUnion = Validator.isNotNull(userId) && inherit;

		if (doUnion) {
			params2 = new LinkedHashMap<String, Object>(params1);

			params2.remove("usersGroups");
			params2.put("groupOrg", userId);

			params3 = new LinkedHashMap<String, Object>(params1);

			params3.remove("usersGroups");
			params3.put("groupsOrgs", userId);

			params4 = new LinkedHashMap<String, Object>(params1);

			params4.remove("usersGroups");
			params4.put("groupsUserGroups", userId);
		}

		String sql = null;

		String sqlKey = _buildSQLKey(
			params1, params2, params3, params4, obc, doUnion);

		sql = _findByCompanyIdSQLCache.get(sqlKey);

		if (sql == null) {
			String findByC_C_SQL = CustomSQLUtil.get(FIND_BY_C_C);

			if (params.get("active") == Boolean.TRUE) {
				findByC_C_SQL = StringUtil.replace(
					findByC_C_SQL, "(Group_.liveGroupId = 0) AND",
					StringPool.BLANK);
			}

			findByC_C_SQL = StringUtil.replace(
				findByC_C_SQL, "Group_.classNameId = ?",
				"Group_.classNameId = ".concat(
					StringUtil.merge(
						_getGroupOrganizationClassNameIds(),
						" OR Group_.classNameId = ")));
			findByC_C_SQL = replaceOrderBy(findByC_C_SQL, obc);

			StringBundler sb = new StringBundler(12);

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(replaceJoinAndWhere(findByC_C_SQL, params1));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			if (doUnion) {
				sb.append(" UNION (");
				sb.append(replaceJoinAndWhere(findByC_C_SQL, params2));
				sb.append(") UNION (");
				sb.append(replaceJoinAndWhere(findByC_C_SQL, params3));
				sb.append(") UNION (");
				sb.append(replaceJoinAndWhere(findByC_C_SQL, params4));
				sb.append(StringPool.CLOSE_PARENTHESIS);
			}

			if (obc != null) {
				sb.append(" ORDER BY ");
				sb.append(obc.toString());
			}

			sql = sb.toString();

			_findByCompanyIdSQLCache.put(sqlKey, sql);
		}

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar("groupId", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			setJoin(qPos, params1);

			qPos.add(companyId);

			if (doUnion) {
				setJoin(qPos, params2);

				qPos.add(companyId);

				setJoin(qPos, params3);

				qPos.add(companyId);

				setJoin(qPos, params4);

				qPos.add(companyId);
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

	@Override
	public List<Long> findByC_P(
		long companyId, long parentGroupId, long previousGroupId, int size) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_P);

			if (previousGroupId <= 0) {
				sql = StringUtil.replace(
					sql, "(groupId > ?) AND", StringPool.BLANK);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar("groupId", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (previousGroupId > 0) {
				qPos.add(previousGroupId);
			}

			qPos.add(companyId);
			qPos.add(parentGroupId);

			return (List<Long>)QueryUtil.list(q, getDialect(), 0, size);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public Group findByC_N(long companyId, String name)
		throws NoSuchGroupException, SystemException {

		name = StringUtil.lowerCase(name);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_N);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("Group_", GroupImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(name);

			List<Group> groups = q.list();

			if (!groups.isEmpty()) {
				return groups.get(0);
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

	@Override
	public List<Group> findByC_C_PG_N_D(
		long companyId, long[] classNameIds, long parentGroupId, String[] names,
		String[] descriptions, LinkedHashMap<String, Object> params,
		boolean andOperator, int start, int end, OrderByComparator obc) {

		String parentGroupIdComparator = StringPool.EQUAL;

		if (parentGroupId == GroupConstants.ANY_PARENT_GROUP_ID) {
			parentGroupIdComparator = StringPool.NOT_EQUAL;
		}

		names = CustomSQLUtil.keywords(names);
		descriptions = CustomSQLUtil.keywords(descriptions);

		if (params == null) {
			params = _emptyLinkedHashMap;
		}

		LinkedHashMap<String, Object> params1 = params;

		LinkedHashMap<String, Object> params2 = null;

		LinkedHashMap<String, Object> params3 = null;

		LinkedHashMap<String, Object> params4 = null;

		Long userId = (Long)params.get("usersGroups");
		boolean inherit = GetterUtil.getBoolean(params.get("inherit"), true);

		boolean doUnion = Validator.isNotNull(userId) && inherit;

		if (doUnion) {
			params2 = new LinkedHashMap<String, Object>(params1);

			params2.remove("usersGroups");
			params2.put("groupOrg", userId);

			params3 = new LinkedHashMap<String, Object>(params1);

			params3.remove("usersGroups");
			params3.put("groupsOrgs", userId);

			params4 = new LinkedHashMap<String, Object>(params1);

			params4.remove("usersGroups");
			params4.put("groupsUserGroups", userId);
		}

		if (obc == null) {
			obc = new GroupNameComparator(true);
		}

		String sql = null;

		if (classNameIds == _getGroupOrganizationClassNameIds()) {
			String sqlKey = _buildSQLKey(
				params1, params2, params3, params4, obc, doUnion);

			sql = _findByC_C_PG_N_DSQLCache.get(sqlKey);
		}

		if (sql == null) {
			String findByC_PG_N_D_SQL = CustomSQLUtil.get(FIND_BY_C_C_PG_N_D);

			if (classNameIds == null) {
				findByC_PG_N_D_SQL = StringUtil.replace(
					findByC_PG_N_D_SQL, "AND (Group_.classNameId = ?)",
					StringPool.BLANK);
			}
			else {
				findByC_PG_N_D_SQL = StringUtil.replace(
					findByC_PG_N_D_SQL, "Group_.classNameId = ?",
					"Group_.classNameId = ".concat(
						StringUtil.merge(
							classNameIds, " OR Group_.classNameId = ")));
			}

			findByC_PG_N_D_SQL = replaceOrderBy(findByC_PG_N_D_SQL, obc);

			StringBundler sb = new StringBundler(12);

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(replaceJoinAndWhere(findByC_PG_N_D_SQL, params1));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			if (doUnion) {
				sb.append(" UNION (");
				sb.append(replaceJoinAndWhere(findByC_PG_N_D_SQL, params2));
				sb.append(") UNION (");
				sb.append(replaceJoinAndWhere(findByC_PG_N_D_SQL, params3));
				sb.append(") UNION (");
				sb.append(replaceJoinAndWhere(findByC_PG_N_D_SQL, params4));
				sb.append(StringPool.CLOSE_PARENTHESIS);
			}

			if (obc != null) {
				sb.append(" ORDER BY ");
				sb.append(obc.toString());
			}

			sql = sb.toString();

			if (classNameIds == _getGroupOrganizationClassNameIds()) {
				String sqlKey = _buildSQLKey(
					params1, params2, params3, params4, obc, doUnion);

				_findByC_C_PG_N_DSQLCache.put(sqlKey, sql);
			}
		}

		sql = StringUtil.replace(
			sql, "[$PARENT_GROUP_ID_COMPARATOR$]",
			parentGroupIdComparator.equals(StringPool.EQUAL) ?
				StringPool.EQUAL : StringPool.NOT_EQUAL);
		sql = CustomSQLUtil.replaceKeywords(
			sql, "lower(Group_.name)", StringPool.LIKE, false, names);
		sql = CustomSQLUtil.replaceKeywords(
			sql, "lower(Group_.description)", StringPool.LIKE, true,
			descriptions);
		sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar("groupId", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			setJoin(qPos, params1);

			qPos.add(companyId);
			qPos.add(parentGroupId);
			qPos.add(names, 2);
			qPos.add(descriptions, 2);

			if (doUnion) {
				setJoin(qPos, params2);

				qPos.add(companyId);
				qPos.add(parentGroupId);
				qPos.add(names, 2);
				qPos.add(descriptions, 2);

				setJoin(qPos, params3);

				qPos.add(companyId);
				qPos.add(parentGroupId);
				qPos.add(names, 2);
				qPos.add(descriptions, 2);

				setJoin(qPos, params4);

				qPos.add(companyId);
				qPos.add(parentGroupId);
				qPos.add(names, 2);
				qPos.add(descriptions, 2);
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
			Session session, long groupId, LinkedHashMap<String, Object> params)
		throws Exception {

		String sql = CustomSQLUtil.get(COUNT_BY_GROUP_ID);

		sql = replaceJoinAndWhere(sql, params);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

		QueryPos qPos = QueryPos.getInstance(q);

		setJoin(qPos, params);

		qPos.add(groupId);

		Iterator<Long> itr = q.iterate();

		if (itr.hasNext()) {
			Long count = itr.next();

			if (count != null) {
				return count.intValue();
			}
		}

		return 0;
	}

	protected List<Long> countByC_C_PG_N_D(
			Session session, long companyId, long[] classNameIds,
			long parentGroupId, String parentGroupIdComparator, String[] names,
			String[] descriptions, LinkedHashMap<String, Object> params,
			boolean andOperator)
		throws Exception {

		String sql = CustomSQLUtil.get(COUNT_BY_C_C_PG_N_D);

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

		sql = StringUtil.replace(
			sql, "[$PARENT_GROUP_ID_COMPARATOR$]",
			parentGroupIdComparator.equals(StringPool.EQUAL) ?
				StringPool.EQUAL : StringPool.NOT_EQUAL);
		sql = CustomSQLUtil.replaceKeywords(
			sql, "lower(Group_.name)", StringPool.LIKE, false, names);
		sql = CustomSQLUtil.replaceKeywords(
			sql, "lower(Group_.description)", StringPool.LIKE, true,
			descriptions);

		sql = replaceJoinAndWhere(sql, params);
		sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.addScalar("groupId", Type.LONG);

		QueryPos qPos = QueryPos.getInstance(q);

		setJoin(qPos, params);

		qPos.add(companyId);
		qPos.add(parentGroupId);
		qPos.add(names, 2);
		qPos.add(descriptions, 2);

		return q.list(true);
	}

	protected String getJoin(LinkedHashMap<String, Object> params) {
		if ((params == null) || params.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(params.size());

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();

			if (Validator.isNull(value)) {
				continue;
			}

			if (key.equals("rolePermissions")) {
				List<Object> values = (List<Object>)value;

				String name = (String)values.get(0);

				if (ResourceBlockLocalServiceUtil.isSupported(name)) {
					key = "rolePermissions_6_block";
				}
				else {
					key = "rolePermissions_6";
				}
			}

			Map<String, String> joinMap = _getJoinMap();

			String joinValue = joinMap.get(key);

			if (Validator.isNotNull(joinValue)) {
				sb.append(joinValue);
			}
		}

		return sb.toString();
	}

	protected String getWhere(LinkedHashMap<String, Object> params) {
		if ((params == null) || params.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(params.size());

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();

			if (key.equals("excludedGroupIds")) {
				List<Long> excludedGroupIds = (List<Long>)entry.getValue();

				if (!excludedGroupIds.isEmpty()) {
					sb.append(StringPool.OPEN_PARENTHESIS);

					for (int i = 0; i < excludedGroupIds.size(); i++) {
						sb.append("(Group_.groupId != ?)");

						if ((i + 1) < excludedGroupIds.size()) {
							sb.append(" AND ");
						}
					}

					sb.append(") AND ");
				}
			}
			else if (key.equals("groupsTree")) {
				List<Group> groupsTree = (List<Group>)entry.getValue();

				if (!groupsTree.isEmpty()) {
					sb.append(StringPool.OPEN_PARENTHESIS);

					for (int i = 0; i < groupsTree.size(); i++) {
						sb.append("(Group_.treePath LIKE ?) ");

						if ((i + 1) < groupsTree.size()) {
							sb.append("OR ");
						}
					}

					sb.append(") AND ");
				}
			}
			else if (key.equals("types")) {
				List<Integer> types = (List<Integer>)entry.getValue();

				if (!types.isEmpty()) {
					sb.append(StringPool.OPEN_PARENTHESIS);

					for (int i = 0; i < types.size(); i++) {
						sb.append("(Group_.type_ = ?) ");

						if ((i + 1) < types.size()) {
							sb.append("OR ");
						}
					}

					sb.append(") AND ");
				}
			}
			else {
				if (key.equals("rolePermissions")) {
					List<Object> values = (List<Object>)entry.getValue();

					String name = (String)values.get(0);

					if (ResourceBlockLocalServiceUtil.isSupported(name)) {
						key = "rolePermissions_6_block";
					}
					else {
						key = "rolePermissions_6";
					}
				}

				Map<String, String> whereMap = _getWhereMap();

				String whereValue = whereMap.get(key);

				if (Validator.isNotNull(whereValue)) {
					sb.append(whereValue);
				}
			}
		}

		return sb.toString();
	}

	protected String replaceJoinAndWhere(
		String sql, LinkedHashMap<String, Object> params) {

		if (params.isEmpty()) {
			return StringUtil.replace(
				sql,
				new String[] {
					"[$JOIN$]", "[$WHERE$]"
				},
				new String[] {
					StringPool.BLANK, StringPool.BLANK
				});
		}

		String cacheKey = _getCacheKey(sql, params);

		String resultSQL = _replaceJoinAndWhereSQLCache.get(cacheKey);

		if (resultSQL == null) {
			sql = StringUtil.replace(sql, "[$JOIN$]", getJoin(params));

			resultSQL = StringUtil.replace(sql, "[$WHERE$]", getWhere(params));

			_replaceJoinAndWhereSQLCache.put(cacheKey, resultSQL);
		}

		return resultSQL;
	}

	protected String replaceOrderBy(String sql, OrderByComparator obc) {
		if (obc instanceof GroupNameComparator) {
			sql = StringUtil.replace(
				sql, "Group_.name AS groupName",
				"REPLACE(Group_.name, '" +
					GroupLocalServiceImpl.ORGANIZATION_NAME_SUFFIX +
						"', '') AS groupName");
		}

		return sql;
	}

	protected void setJoin(QueryPos qPos, LinkedHashMap<String, Object> params)
		throws Exception {

		if (params == null) {
			return;
		}

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();

			if (key.equals("active") || key.equals("layoutSet") ||
				key.equals("manualMembership") || key.equals("site")) {

				Boolean value = (Boolean)entry.getValue();

				qPos.add(value);
			}
			else if (key.equals("excludedGroupIds")) {
				List<Long> excludedGroupIds = (List<Long>)entry.getValue();

				if (!excludedGroupIds.isEmpty()) {
					for (long excludedGroupId : excludedGroupIds) {
						qPos.add(excludedGroupId);
					}
				}
			}
			else if (key.equals("groupsTree")) {
				List<Group> groupsTree = (List<Group>)entry.getValue();

				if (!groupsTree.isEmpty()) {
					for (Group group : groupsTree) {
						StringBundler sb = new StringBundler(5);

						sb.append(StringPool.PERCENT);
						sb.append(StringPool.SLASH);
						sb.append(group.getGroupId());
						sb.append(StringPool.SLASH);
						sb.append(StringPool.PERCENT);

						qPos.add(sb.toString());
					}
				}
			}
			else if (key.equals("pageCount")) {
			}
			else if (key.equals("rolePermissions")) {
				List<Object> values = (List<Object>)entry.getValue();

				String name = (String)values.get(0);
				Integer scope = (Integer)values.get(1);
				String actionId = (String)values.get(2);
				Long roleId = (Long)values.get(3);

				ResourceAction resourceAction =
					ResourceActionLocalServiceUtil.getResourceAction(
						name, actionId);

				if (ResourceBlockLocalServiceUtil.isSupported(name)) {

					// Scope is assumed to always be group

					qPos.add(name);
					qPos.add(roleId);
					qPos.add(resourceAction.getBitwiseValue());
				}
				else {
					qPos.add(name);
					qPos.add(scope);
					qPos.add(roleId);
					qPos.add(resourceAction.getBitwiseValue());
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

	private String _buildSQLKey(
		LinkedHashMap<String, Object> param1,
		LinkedHashMap<String, Object> param2,
		LinkedHashMap<String, Object> param3,
		LinkedHashMap<String, Object> param4, OrderByComparator obc,
		boolean doUnion) {

		StringBundler sb = null;

		if (doUnion) {
			sb = new StringBundler(
				param1.size() + param2.size() + param3.size() + param4.size() +
					1);

			for (String key : param1.keySet()) {
				sb.append(key);
			}

			for (String key : param2.keySet()) {
				sb.append(key);
			}

			for (String key : param3.keySet()) {
				sb.append(key);
			}

			for (String key : param4.keySet()) {
				sb.append(key);
			}
		}
		else {
			sb = new StringBundler(param1.size() + 1);

			for (String key : param1.keySet()) {
				sb.append(key);
			}
		}

		sb.append(obc.getOrderBy());

		return sb.toString();
	}

	private String _getCacheKey(
		String sql, LinkedHashMap<String, Object> params) {

		StringBundler sb = new StringBundler();

		sb.append(sql);

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();

			if (key.equals("rolePermissions")) {
				List<Object> values = (List<Object>)entry.getValue();

				String name = (String)values.get(0);

				if (ResourceBlockLocalServiceUtil.isSupported(name)) {
					key = "rolePermissions_6_block";
				}
				else {
					key = "rolePermissions_6";
				}
			}
			else {
				Object value = entry.getValue();

				if (value instanceof List<?>) {
					List<Object> values = (List<Object>)value;

					if (!values.isEmpty()) {
						for (int i = 0; i < values.size(); i++) {
							sb.append(key);
							sb.append(StringPool.DASH);
							sb.append(i);
						}
					}
				}
			}

			sb.append(key);
		}

		return sb.toString();
	}

	private String _getCondition(String join) {
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

	private long[] _getGroupOrganizationClassNameIds() {
		if (_groupOrganizationClassNameIds == null) {
			_groupOrganizationClassNameIds = new long[] {
				ClassNameLocalServiceUtil.getClassNameId(Group.class),
				ClassNameLocalServiceUtil.getClassNameId(Organization.class)
			};
		}

		return _groupOrganizationClassNameIds;
	}

	private Map<String, String> _getJoinMap() {
		if (_joinMap != null) {
			return _joinMap;
		}

		Map<String, String> joinMap = new HashMap<String, String>();

		joinMap.put("active", _removeWhere(CustomSQLUtil.get(JOIN_BY_ACTIVE)));
		joinMap.put(
			"groupOrg", _removeWhere(CustomSQLUtil.get(JOIN_BY_GROUP_ORG)));
		joinMap.put(
			"groupsOrgs", _removeWhere(CustomSQLUtil.get(JOIN_BY_GROUPS_ORGS)));
		joinMap.put(
			"groupsRoles",
			_removeWhere(CustomSQLUtil.get(JOIN_BY_GROUPS_ROLES)));
		joinMap.put(
			"groupsUserGroups",
			_removeWhere(CustomSQLUtil.get(JOIN_BY_GROUPS_USER_GROUPS)));
		joinMap.put(
			"layoutSet", _removeWhere(CustomSQLUtil.get(JOIN_BY_LAYOUT_SET)));
		joinMap.put(
			"pageCount", _removeWhere(CustomSQLUtil.get(JOIN_BY_PAGE_COUNT)));
		joinMap.put(
			"membershipRestriction",
			_removeWhere(CustomSQLUtil.get(JOIN_BY_MEMBERSHIP_RESTRICTION)));
		joinMap.put(
			"rolePermissions_6",
			_removeWhere(CustomSQLUtil.get(JOIN_BY_ROLE_RESOURCE_PERMISSIONS)));
		joinMap.put(
			"rolePermissions_6_block",
			_removeWhere(
				CustomSQLUtil.get(JOIN_BY_ROLE_RESOURCE_TYPE_PERMISSIONS)));
		joinMap.put("site", _removeWhere(CustomSQLUtil.get(JOIN_BY_SITE)));
		joinMap.put("type", _removeWhere(CustomSQLUtil.get(JOIN_BY_TYPE)));
		joinMap.put(
			"userGroupRole",
			_removeWhere(CustomSQLUtil.get(JOIN_BY_USER_GROUP_ROLE)));
		joinMap.put(
			"usersGroups",
			_removeWhere(CustomSQLUtil.get(JOIN_BY_USERS_GROUPS)));

		_joinMap = joinMap;

		return _joinMap;
	}

	private Map<String, String> _getWhereMap() {
		if (_whereMap != null) {
			return _whereMap;
		}

		Map<String, String> whereMap = new HashMap<String, String>();

		whereMap.put(
			"active", _getCondition(CustomSQLUtil.get(JOIN_BY_ACTIVE)));
		whereMap.put(
			"creatorUserId",
			_getCondition(CustomSQLUtil.get(JOIN_BY_CREATOR_USER_ID)));
		whereMap.put(
			"groupOrg", _getCondition(CustomSQLUtil.get(JOIN_BY_GROUP_ORG)));
		whereMap.put(
			"groupsOrgs",
			_getCondition(CustomSQLUtil.get(JOIN_BY_GROUPS_ORGS)));
		whereMap.put(
			"groupsRoles",
			_getCondition(CustomSQLUtil.get(JOIN_BY_GROUPS_ROLES)));
		whereMap.put(
			"groupsUserGroups",
			_getCondition(CustomSQLUtil.get(JOIN_BY_GROUPS_USER_GROUPS)));
		whereMap.put(
			"layoutSet", _getCondition(CustomSQLUtil.get(JOIN_BY_LAYOUT_SET)));
		whereMap.put(
			"manualMembership",
			_getCondition(CustomSQLUtil.get(JOIN_BY_MANUAL_MEMBERSHIP)));
		whereMap.put(
			"membershipRestriction",
			_getCondition(CustomSQLUtil.get(JOIN_BY_MEMBERSHIP_RESTRICTION)));
		whereMap.put(
			"pageCount", _getCondition(CustomSQLUtil.get(JOIN_BY_PAGE_COUNT)));
		whereMap.put(
			"rolePermissions_6",
			_getCondition(
				CustomSQLUtil.get(JOIN_BY_ROLE_RESOURCE_PERMISSIONS)));
		whereMap.put(
			"rolePermissions_6_block",
			_getCondition(
				CustomSQLUtil.get(JOIN_BY_ROLE_RESOURCE_TYPE_PERMISSIONS)));
		whereMap.put("site", _getCondition(CustomSQLUtil.get(JOIN_BY_SITE)));
		whereMap.put("type", _getCondition(CustomSQLUtil.get(JOIN_BY_TYPE)));
		whereMap.put(
			"userGroupRole",
			_getCondition(CustomSQLUtil.get(JOIN_BY_USER_GROUP_ROLE)));
		whereMap.put(
			"usersGroups",
			_getCondition(CustomSQLUtil.get(JOIN_BY_USERS_GROUPS)));

		_whereMap = whereMap;

		return _whereMap;
	}

	private String _removeWhere(String join) {
		if (Validator.isNotNull(join)) {
			int pos = join.indexOf("WHERE");

			if (pos != -1) {
				join = join.substring(0, pos);
			}
		}

		return join;
	}

	private LinkedHashMap<String, Object> _emptyLinkedHashMap =
		new LinkedHashMap<String, Object>(0);
	private Map<String, String> _findByC_C_PG_N_DSQLCache =
		new ConcurrentHashMap<String, String>();
	private Map<String, String> _findByCompanyIdSQLCache =
		new ConcurrentHashMap<String, String>();
	private volatile long[] _groupOrganizationClassNameIds;
	private volatile Map<String, String> _joinMap;
	private Map<String, String> _replaceJoinAndWhereSQLCache =
		new ConcurrentHashMap<String, String>();
	private volatile Map<String, String> _whereMap;

}