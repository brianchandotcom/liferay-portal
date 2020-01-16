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
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.persistence.OrganizationFinder;
import com.liferay.portal.kernel.service.persistence.OrganizationUtil;
import com.liferay.portal.kernel.service.persistence.UserUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.impl.OrganizationImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Amos Fong
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Connor McKay
 * @author Shuyang Zhou
 */
public class OrganizationFinderImpl
	extends OrganizationFinderBaseImpl implements OrganizationFinder {

	public static final String COUNT_O_BY_GROUP_ID =
		OrganizationFinder.class.getName() + ".countO_ByGroupId";

	public static final String COUNT_O_BY_ORGANIZATION_ID =
		OrganizationFinder.class.getName() + ".countO_ByOrganizationId";

	public static final String COUNT_O_BY_C_PO =
		OrganizationFinder.class.getName() + ".countO_ByC_PO";

	public static final String COUNT_O_BY_C_PO_N_S_C_Z_R_C =
		OrganizationFinder.class.getName() + ".countO_ByC_PO_N_S_C_Z_R_C";

	public static final String COUNT_O_BY_C_PO_N_L_S_C_Z_R_C =
		OrganizationFinder.class.getName() + ".countO_ByC_PO_N_L_S_C_Z_R_C";

	public static final String COUNT_U_BY_C_S_O =
		OrganizationFinder.class.getName() + ".countU_ByC_S_O";

	public static final String FIND_O_BY_NO_ASSETS =
		OrganizationFinder.class.getName() + ".findO_ByNoAssets";

	public static final String FIND_O_BY_GROUP_ID =
		OrganizationFinder.class.getName() + ".findO_ByGroupId";

	public static final String FIND_O_BY_C_P =
		OrganizationFinder.class.getName() + ".findO_ByC_P";

	public static final String FIND_O_BY_C_PO =
		OrganizationFinder.class.getName() + ".findO_ByC_PO";

	public static final String FIND_O_BY_C_PO_N_S_C_Z_R_C =
		OrganizationFinder.class.getName() + ".findO_ByC_PO_N_S_C_Z_R_C";

	public static final String FIND_O_BY_C_PO_N_L_S_C_Z_R_C =
		OrganizationFinder.class.getName() + ".findO_ByC_PO_N_L_S_C_Z_R_C";

	public static final String FIND_U_BY_C_S_O =
		OrganizationFinder.class.getName() + ".findU_ByC_S_O";

	public static final String JOIN_O_BY_ORGANIZATIONS_GROUPS =
		OrganizationFinder.class.getName() + ".joinO_ByOrganizationsGroups";

	public static final String JOIN_O_BY_ORGANIZATIONS_PASSWORD_POLICIES =
		OrganizationFinder.class.getName() +
			".joinO_ByOrganizationsPasswordPolicies";

	public static final String JOIN_O_BY_ORGANIZATIONS_ROLES =
		OrganizationFinder.class.getName() + ".joinO_ByOrganizationsRoles";

	public static final String JOIN_O_BY_ORGANIZATIONS_USERS =
		OrganizationFinder.class.getName() + ".joinO_ByOrganizationsUsers";

	public static final String JOIN_O_BY_USERS_ORGS =
		OrganizationFinder.class.getName() + ".joinO_ByUsersOrgs";

	@Override
	public int countO_ByKeywords(
		long companyId, long parentOrganizationId,
		String parentOrganizationIdComparator, String keywords, String type,
		Long regionId, Long countryId, LinkedHashMap<String, Object> params) {

		String[] names = null;
		String[] streets = null;
		String[] cities = null;
		String[] zips = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			names = CustomSQLUtil.keywords(keywords);
			streets = CustomSQLUtil.keywords(keywords);
			cities = CustomSQLUtil.keywords(keywords);
			zips = CustomSQLUtil.keywords(keywords);
		}
		else {
			andOperator = true;
		}

		return countO_ByC_PO_N_T_S_C_Z_R_C(
			companyId, parentOrganizationId, parentOrganizationIdComparator,
			names, type, streets, cities, zips, regionId, countryId, params,
			andOperator);
	}

	@Override
	public int countO_ByO_U(long organizationId, long userId) {
		LinkedHashMap<String, Object> params1 =
			LinkedHashMapBuilder.<String, Object>put(
				"usersOrgs", userId
			).build();

		Session session = null;

		try {
			session = openSession();

			return countO_ByOrganizationId(session, organizationId, params1);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public int countO_ByC_PO_N_T_S_C_Z_R_C(
		long companyId, long parentOrganizationId,
		String parentOrganizationIdComparator, String name, String type,
		String street, String city, String zip, Long regionId, Long countryId,
		LinkedHashMap<String, Object> params, boolean andOperator) {

		String[] names = CustomSQLUtil.keywords(name);
		String[] streets = CustomSQLUtil.keywords(street);
		String[] cities = CustomSQLUtil.keywords(city);
		String[] zips = CustomSQLUtil.keywords(zip);

		return countO_ByC_PO_N_T_S_C_Z_R_C(
			companyId, parentOrganizationId, parentOrganizationIdComparator,
			names, type, streets, cities, zips, regionId, countryId, params,
			andOperator);
	}

	@Override
	public int countO_ByC_PO_N_T_S_C_Z_R_C(
		long companyId, long parentOrganizationId,
		String parentOrganizationIdComparator, String[] names, String type,
		String[] streets, String[] cities, String[] zips, Long regionId,
		Long countryId, LinkedHashMap<String, Object> params,
		boolean andOperator) {

		names = CustomSQLUtil.keywords(names);
		streets = CustomSQLUtil.keywords(streets);
		cities = CustomSQLUtil.keywords(cities);
		zips = CustomSQLUtil.keywords(zips);

		Session session = null;

		try {
			session = openSession();

			StringBundler sb = new StringBundler(5);

			boolean doUnion = false;

			Long groupOrganization = null;

			if (params != null) {
				groupOrganization = (Long)params.get("groupOrganization");

				if (groupOrganization != null) {
					doUnion = true;
				}
			}

			if (doUnion) {
				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append(CustomSQLUtil.get(COUNT_O_BY_GROUP_ID));
				sb.append(") UNION ALL (");
			}

			if (Validator.isNotNull(type)) {
				sb.append(CustomSQLUtil.get(COUNT_O_BY_C_PO_N_L_S_C_Z_R_C));
			}
			else {
				sb.append(CustomSQLUtil.get(COUNT_O_BY_C_PO_N_S_C_Z_R_C));
			}

			if (doUnion) {
				sb.append(StringPool.CLOSE_PARENTHESIS);
			}

			String sql = sb.toString();

			sql = CustomSQLUtil.replaceKeywords(
				sql, "LOWER(Organization_.name)", StringPool.LIKE, false,
				names);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "LOWER(Address.street1)", StringPool.LIKE, true, streets);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "LOWER(Address.street2)", StringPool.LIKE, true, streets);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "LOWER(Address.street3)", StringPool.LIKE, true, streets);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "LOWER(Address.city)", StringPool.LIKE, false, cities);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "LOWER(Address.zip)", StringPool.LIKE, true, zips);

			if (regionId == null) {
				sql = StringUtil.replace(sql, _REGION_ID_SQL, StringPool.BLANK);
			}

			if (countryId == null) {
				sql = StringUtil.replace(
					sql, _COUNTRY_ID_SQL, StringPool.BLANK);
			}

			sql = StringUtil.replace(sql, "[$JOIN$]", getJoin(params));
			sql = StringUtil.replace(sql, "[$WHERE$]", getWhere(params));

			if (parentOrganizationIdComparator.equals(StringPool.EQUAL)) {
				sql = StringUtil.replace(
					sql, "[$PARENT_ORGANIZATION_ID_COMPARATOR$]",
					StringPool.EQUAL);
			}
			else {
				sql = StringUtil.replace(
					sql, "[$PARENT_ORGANIZATION_ID_COMPARATOR$]",
					StringPool.NOT_EQUAL);
			}

			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (doUnion) {
				qPos.add(groupOrganization);
			}

			setJoin(qPos, params);

			qPos.add(companyId);
			qPos.add(parentOrganizationId);

			if (Validator.isNotNull(type)) {
				qPos.add(type);
			}

			qPos.add(names, 2);
			qPos.add(streets, 6);

			if (regionId != null) {
				qPos.add(regionId);
				qPos.add(regionId);
			}

			if (countryId != null) {
				qPos.add(countryId);
				qPos.add(countryId);
			}

			qPos.add(cities, 2);
			qPos.add(zips, 2);

			int count = 0;

			Iterator<Long> itr = q.iterate();

			while (itr.hasNext()) {
				Long l = itr.next();

				if (l != null) {
					count += l.intValue();
				}
			}

			return count;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public int countO_U_ByC_P(
		long companyId, long parentOrganizationId,
		QueryDefinition<?> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			StringBundler sb = new StringBundler(5);

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(CustomSQLUtil.get(COUNT_O_BY_C_PO));
			sb.append(") UNION ALL (");
			sb.append(getUsersSQL(COUNT_U_BY_C_S_O, queryDefinition));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			SQLQuery q = session.createSynchronizedSQLQuery(sb.toString());

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(parentOrganizationId);
			qPos.add(companyId);

			int status = queryDefinition.getStatus();

			if (status != WorkflowConstants.STATUS_ANY) {
				qPos.add(status);
			}

			qPos.add(parentOrganizationId);

			int count = 0;

			Iterator<Long> itr = q.iterate();

			while (itr.hasNext()) {
				Long l = itr.next();

				if (l != null) {
					count += l.intValue();
				}
			}

			return count;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<Organization> findO_ByKeywords(
		long companyId, long parentOrganizationId,
		String parentOrganizationIdComparator, String keywords, String type,
		Long regionId, Long countryId, LinkedHashMap<String, Object> params,
		int start, int end, OrderByComparator<Organization> obc) {

		String[] names = null;
		String[] streets = null;
		String[] cities = null;
		String[] zips = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			names = CustomSQLUtil.keywords(keywords);
			streets = CustomSQLUtil.keywords(keywords);
			cities = CustomSQLUtil.keywords(keywords);
			zips = CustomSQLUtil.keywords(keywords);
		}
		else {
			andOperator = true;
		}

		return findO_ByC_PO_N_T_S_C_Z_R_C(
			companyId, parentOrganizationId, parentOrganizationIdComparator,
			names, type, streets, cities, zips, regionId, countryId, params,
			andOperator, start, end, obc);
	}

	@Override
	public List<Organization> findO_ByNoAssets() {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_O_BY_NO_ASSETS);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("Organization_", OrganizationImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(PortalUtil.getClassNameId(Organization.class.getName()));

			return q.list(true);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<Long> findO_ByC_P(
		long companyId, long parentOrganizationId, long previousOrganizationId,
		int size) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_O_BY_C_P);

			if (previousOrganizationId == 0) {
				sql = StringUtil.replace(
					sql, "(organizationId > ?) AND", StringPool.BLANK);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar("organizationId", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (previousOrganizationId > 0) {
				qPos.add(previousOrganizationId);
			}

			qPos.add(companyId);
			qPos.add(parentOrganizationId);

			return (List<Long>)QueryUtil.list(q, getDialect(), 0, size);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<Organization> findO_ByC_PO_N_T_S_C_Z_R_C(
		long companyId, long parentOrganizationId,
		String parentOrganizationIdComparator, String name, String type,
		String street, String city, String zip, Long regionId, Long countryId,
		LinkedHashMap<String, Object> params, boolean andOperator, int start,
		int end, OrderByComparator<Organization> obc) {

		String[] names = CustomSQLUtil.keywords(name);
		String[] streets = CustomSQLUtil.keywords(street);
		String[] cities = CustomSQLUtil.keywords(city);
		String[] zips = CustomSQLUtil.keywords(zip);

		return findO_ByC_PO_N_T_S_C_Z_R_C(
			companyId, parentOrganizationId, parentOrganizationIdComparator,
			names, type, streets, cities, zips, regionId, countryId, params,
			andOperator, start, end, obc);
	}

	@Override
	public List<Organization> findO_ByC_PO_N_T_S_C_Z_R_C(
		long companyId, long parentOrganizationId,
		String parentOrganizationIdComparator, String[] names, String type,
		String[] streets, String[] cities, String[] zips, Long regionId,
		Long countryId, LinkedHashMap<String, Object> params,
		boolean andOperator, int start, int end,
		OrderByComparator<Organization> obc) {

		names = CustomSQLUtil.keywords(names);
		streets = CustomSQLUtil.keywords(streets);
		cities = CustomSQLUtil.keywords(cities);
		zips = CustomSQLUtil.keywords(zips);

		if (params == null) {
			params = new LinkedHashMap<>();
		}

		StringBundler sb = new StringBundler(4);

		sb.append(StringPool.OPEN_PARENTHESIS);

		Long groupOrganization = (Long)params.get("groupOrganization");

		boolean doUnion = Validator.isNotNull(groupOrganization);

		if (doUnion) {
			sb.append(CustomSQLUtil.get(FIND_O_BY_GROUP_ID));
			sb.append(") UNION ALL (");
		}

		if (Validator.isNotNull(type)) {
			sb.append(CustomSQLUtil.get(FIND_O_BY_C_PO_N_L_S_C_Z_R_C));
		}
		else {
			sb.append(CustomSQLUtil.get(FIND_O_BY_C_PO_N_S_C_Z_R_C));
		}

		String sql = sb.toString();

		sql = StringUtil.replace(sql, "[$JOIN$]", getJoin(params));
		sql = StringUtil.replace(sql, "[$WHERE$]", getWhere(params));
		sql = sql.concat(StringPool.CLOSE_PARENTHESIS);
		sql = CustomSQLUtil.replaceKeywords(
			sql, "LOWER(Organization_.name)", StringPool.LIKE, false, names);
		sql = CustomSQLUtil.replaceKeywords(
			sql, "LOWER(Address.street1)", StringPool.LIKE, true, streets);
		sql = CustomSQLUtil.replaceKeywords(
			sql, "LOWER(Address.street2)", StringPool.LIKE, true, streets);
		sql = CustomSQLUtil.replaceKeywords(
			sql, "LOWER(Address.street3)", StringPool.LIKE, true, streets);
		sql = CustomSQLUtil.replaceKeywords(
			sql, "LOWER(Address.city)", StringPool.LIKE, false, cities);
		sql = CustomSQLUtil.replaceKeywords(
			sql, "LOWER(Address.zip)", StringPool.LIKE, true, zips);

		if (parentOrganizationIdComparator.equals(StringPool.EQUAL)) {
			sql = StringUtil.replace(
				sql, "[$PARENT_ORGANIZATION_ID_COMPARATOR$]", StringPool.EQUAL);
		}
		else {
			sql = StringUtil.replace(
				sql, "[$PARENT_ORGANIZATION_ID_COMPARATOR$]",
				StringPool.NOT_EQUAL);
		}

		if (regionId == null) {
			sql = StringUtil.replace(sql, _REGION_ID_SQL, StringPool.BLANK);
		}

		if (countryId == null) {
			sql = StringUtil.replace(sql, _COUNTRY_ID_SQL, StringPool.BLANK);
		}

		sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);
		sql = CustomSQLUtil.replaceOrderBy(sql, obc);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar("orgId", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (doUnion) {
				qPos.add(groupOrganization);
			}

			setJoin(qPos, params);

			qPos.add(companyId);
			qPos.add(parentOrganizationId);

			if (Validator.isNotNull(type)) {
				qPos.add(type);
			}

			qPos.add(names, 2);
			qPos.add(streets, 6);

			if (regionId != null) {
				qPos.add(regionId);
				qPos.add(regionId);
			}

			if (countryId != null) {
				qPos.add(countryId);
				qPos.add(countryId);
			}

			qPos.add(cities, 2);
			qPos.add(zips, 2);

			List<Organization> organizations = new ArrayList<>();

			Iterator<Long> itr = (Iterator<Long>)QueryUtil.iterate(
				q, getDialect(), start, end);

			while (itr.hasNext()) {
				Long organizationId = itr.next();

				Organization organization = OrganizationUtil.findByPrimaryKey(
					organizationId.longValue());

				organizations.add(organization);
			}

			return organizations;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<Object> findO_U_ByC_P(
		long companyId, long parentOrganizationId,
		QueryDefinition<?> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			StringBundler sb = new StringBundler(5);

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(CustomSQLUtil.get(FIND_O_BY_C_PO));
			sb.append(") UNION ALL (");
			sb.append(getUsersSQL(FIND_U_BY_C_S_O, queryDefinition));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			String sql = CustomSQLUtil.replaceOrderBy(
				sb.toString(), queryDefinition.getOrderByComparator());

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar("organizationId", Type.LONG);
			q.addScalar("userId", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(parentOrganizationId);
			qPos.add(companyId);

			int status = queryDefinition.getStatus();

			if (status != WorkflowConstants.STATUS_ANY) {
				qPos.add(status);
			}

			qPos.add(parentOrganizationId);

			List<Object> models = new ArrayList<>();

			Iterator<Object[]> itr = (Iterator<Object[]>)QueryUtil.iterate(
				q, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());

			while (itr.hasNext()) {
				Object[] array = itr.next();

				long organizationId = (Long)array[0];

				Object obj = null;

				if (organizationId > 0) {
					obj = OrganizationUtil.findByPrimaryKey(organizationId);
				}
				else {
					long userId = (Long)array[1];

					obj = UserUtil.findByPrimaryKey(userId);
				}

				models.add(obj);
			}

			return models;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected int countO_ByOrganizationId(
		Session session, long organizationId,
		LinkedHashMap<String, Object> params) {

		String sql = CustomSQLUtil.get(COUNT_O_BY_ORGANIZATION_ID);

		sql = StringUtil.replace(sql, "[$JOIN$]", getJoin(params));
		sql = StringUtil.replace(sql, "[$WHERE$]", getWhere(params));

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

		QueryPos qPos = QueryPos.getInstance(q);

		setJoin(qPos, params);

		qPos.add(organizationId);

		Iterator<Long> itr = q.iterate();

		if (itr.hasNext()) {
			Long count = itr.next();

			if (count != null) {
				return count.intValue();
			}
		}

		return 0;
	}

	protected String getJoin(LinkedHashMap<String, Object> params) {
		if ((params == null) || params.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(params.size());

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();

			if (key.equals("expandoAttributes")) {
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

		if (key.equals("organizationsGroups")) {
			join = CustomSQLUtil.get(JOIN_O_BY_ORGANIZATIONS_GROUPS);
		}
		else if (key.equals("organizationsPasswordPolicies")) {
			join = CustomSQLUtil.get(JOIN_O_BY_ORGANIZATIONS_PASSWORD_POLICIES);
		}
		else if (key.equals("organizationsRoles")) {
			join = CustomSQLUtil.get(JOIN_O_BY_ORGANIZATIONS_ROLES);
		}
		else if (key.equals("organizationsUsers")) {
			join = CustomSQLUtil.get(JOIN_O_BY_ORGANIZATIONS_USERS);
		}
		else if (key.equals("usersOrgs")) {
			join = CustomSQLUtil.get(JOIN_O_BY_USERS_ORGS);
		}

		if (Validator.isNotNull(join)) {
			int pos = join.indexOf("WHERE");

			if (pos != -1) {
				join = join.substring(0, pos);
			}
		}

		return join;
	}

	protected String getUsersSQL(
		String id, QueryDefinition<?> queryDefinition) {

		String sql = CustomSQLUtil.get(id);

		int status = queryDefinition.getStatus();

		if (status == WorkflowConstants.STATUS_ANY) {
			sql = StringUtil.replace(
				sql, "(User_.status = ?) AND", StringPool.BLANK);
		}

		return sql;
	}

	protected String getWhere(LinkedHashMap<String, Object> params) {
		if ((params == null) || params.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(params.size());

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();

			if (key.equals("expandoAttributes")) {
				continue;
			}

			Object value = entry.getValue();

			if (Validator.isNotNull(value)) {
				sb.append(getWhere(key, value));
			}
		}

		return sb.toString();
	}

	protected String getWhere(String key) {
		return getWhere(key, null);
	}

	protected String getWhere(String key, Object value) {
		String join = StringPool.BLANK;

		if (key.equals("organizations")) {
			Long[] organizationIds = (Long[])value;

			if (organizationIds.length == 0) {
				join = "WHERE ((Organization_.organizationId = -1) )";
			}
			else {
				StringBundler sb = new StringBundler(
					organizationIds.length * 2 + 1);

				sb.append("WHERE (");

				for (int i = 0; i < organizationIds.length; i++) {
					sb.append("(Organization_.organizationId = ?) ");

					if ((i + 1) < organizationIds.length) {
						sb.append("OR ");
					}
				}

				sb.append(StringPool.CLOSE_PARENTHESIS);

				join = sb.toString();
			}
		}
		else if (key.equals("organizationsGroups")) {
			if (value instanceof Long) {
				join = CustomSQLUtil.get(JOIN_O_BY_ORGANIZATIONS_GROUPS);
			}
			else if (value instanceof Long[]) {
				Long[] organizationGroupIds = (Long[])value;

				if (organizationGroupIds.length == 0) {
					join = "WHERE (Groups_Orgs.groupId = -1)";
				}
				else {
					StringBundler sb = new StringBundler(
						organizationGroupIds.length * 2 + 1);

					sb.append("WHERE (");

					for (int i = 0; i < organizationGroupIds.length; i++) {
						sb.append("(Groups_Orgs.groupId = ?) ");

						if ((i + 1) < organizationGroupIds.length) {
							sb.append("OR ");
						}
					}

					sb.append(StringPool.CLOSE_PARENTHESIS);

					join = sb.toString();
				}
			}
		}
		else if (key.equals("organizationsPasswordPolicies")) {
			join = CustomSQLUtil.get(JOIN_O_BY_ORGANIZATIONS_PASSWORD_POLICIES);
		}
		else if (key.equals("organizationsRoles")) {
			join = CustomSQLUtil.get(JOIN_O_BY_ORGANIZATIONS_ROLES);
		}
		else if (key.equals("organizationsTree")) {
			List<Organization> organizationsTree = (List<Organization>)value;

			int size = organizationsTree.size();

			if (size == 0) {
				join = "WHERE (Organization_.treePath = '')";
			}
			else {
				StringBundler sb = new StringBundler(size * 2 + 1);

				sb.append("WHERE (");

				for (int i = 0; i < size; i++) {
					sb.append("(Organization_.treePath LIKE ?) ");

					if ((i + 1) < size) {
						sb.append("OR ");
					}
				}

				sb.append(StringPool.CLOSE_PARENTHESIS);

				join = sb.toString();
			}
		}
		else if (key.equals("organizationsUsers")) {
			join = CustomSQLUtil.get(JOIN_O_BY_ORGANIZATIONS_USERS);
		}
		else if (key.equals("usersOrgs")) {
			join = CustomSQLUtil.get(JOIN_O_BY_USERS_ORGS);
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
			String key = entry.getKey();

			if (key.equals("expandoAttributes") ||
				key.equals("groupOrganization")) {

				continue;
			}

			Object value = entry.getValue();

			if (key.equals("organizationsTree")) {
				List<Organization> organizationsTree =
					(List<Organization>)value;

				PermissionChecker permissionChecker =
					PermissionThreadLocal.getPermissionChecker();

				if (!organizationsTree.isEmpty()) {
					for (Organization organization : organizationsTree) {
						StringBundler sb = new StringBundler(5);

						sb.append(StringPool.PERCENT);
						sb.append(StringPool.SLASH);
						sb.append(organization.getOrganizationId());
						sb.append(StringPool.SLASH);

						if ((permissionChecker != null) &&
							(permissionChecker.isOrganizationAdmin(
								organization.getOrganizationId()) ||
							 permissionChecker.isOrganizationOwner(
								 organization.getOrganizationId()))) {

							sb.append(StringPool.PERCENT);
						}

						qPos.add(sb.toString());
					}
				}
			}
			else if (value instanceof Long) {
				Long valueLong = (Long)value;

				if (Validator.isNotNull(valueLong)) {
					qPos.add(valueLong);
				}
			}
			else if (value instanceof Long[]) {
				Long[] valueArray = (Long[])value;

				for (Long element : valueArray) {
					if (Validator.isNotNull(element)) {
						qPos.add(element);
					}
				}
			}
			else if (value instanceof Long[][]) {
				Long[][] valueDoubleArray = (Long[][])value;

				for (Long[] valueArray : valueDoubleArray) {
					for (Long valueLong : valueArray) {
						qPos.add(valueLong);
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

	private static final String _COUNTRY_ID_SQL =
		"((Organization_.countryId = ?) OR (Address.countryId = ?)) " +
			"[$AND_OR_CONNECTOR$]";

	private static final String _REGION_ID_SQL =
		"((Organization_.regionId = ?) OR (Address.regionId = ?)) " +
			"[$AND_OR_CONNECTOR$]";

}