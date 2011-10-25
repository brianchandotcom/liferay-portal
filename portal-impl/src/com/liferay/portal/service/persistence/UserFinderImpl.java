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

import com.liferay.portal.kernel.dao.orm.CustomSQLParam;
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
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Jon Steer
 * @author Raymond Augé
 * @author Connor McKay
 */
public class UserFinderImpl
	extends BasePersistenceImpl<User> implements UserFinder {

	public static String COUNT_BY_USER =
		UserFinder.class.getName() + ".countByUser";

	public static String COUNT_BY_C_FN_MN_LN_SN_EA_S =
		UserFinder.class.getName() + ".countByC_FN_MN_LN_SN_EA_S";

	public static String FIND_BY_NO_ANNOUNCEMENTS_DELIVERIES =
		UserFinder.class.getName() + ".findByNoAnnouncementsDeliveries";

	public static String FIND_BY_NO_CONTACTS =
		UserFinder.class.getName() + ".findByNoContacts";

	public static String FIND_BY_NO_GROUPS =
		UserFinder.class.getName() + ".findByNoGroups";

	public static String FIND_BY_C_FN_MN_LN_SN_EA_S =
		UserFinder.class.getName() + ".findByC_FN_MN_LN_SN_EA_S";

	public static String JOIN_BY_CONTACT_TWITTER_SN =
		UserFinder.class.getName() + ".joinByContactTwitterSN";

	public static String JOIN_BY_NO_ORGANIZATIONS =
		UserFinder.class.getName() + ".joinByNoOrganizations";

	public static String JOIN_BY_PERMISSION =
		UserFinder.class.getName() + ".joinByPermission";

	public static String JOIN_BY_USER_GROUP_ROLE =
		UserFinder.class.getName() + ".joinByUserGroupRole";

	public static String JOIN_BY_USERS_GROUPS =
		UserFinder.class.getName() + ".joinByUsersGroups";

	public static String JOIN_BY_USERS_ORGS =
		UserFinder.class.getName() + ".joinByUsersOrgs";

	public static String JOIN_BY_USERS_ORGS_TREE =
		UserFinder.class.getName() + ".joinByUsersOrgsTree";

	public static String JOIN_BY_USERS_PASSWORD_POLICIES =
		UserFinder.class.getName() + ".joinByUsersPasswordPolicies";

	public static String JOIN_BY_USERS_ROLES =
		UserFinder.class.getName() + ".joinByUsersRoles";

	public static String JOIN_BY_USERS_TEAMS =
		UserFinder.class.getName() + ".joinByUsersTeams";

	public static String JOIN_BY_USERS_USER_GROUPS =
		UserFinder.class.getName() + ".joinByUsersUserGroups";

	public static String JOIN_BY_ANNOUNCEMENTS_DELIVERY_EMAIL_OR_SMS =
		UserFinder.class.getName() + ".joinByAnnouncementsDeliveryEmailOrSms";

	public static String JOIN_BY_SOCIAL_MUTUAL_RELATION =
		UserFinder.class.getName() + ".joinBySocialMutualRelation";

	public static String JOIN_BY_SOCIAL_MUTUAL_RELATION_TYPE =
		UserFinder.class.getName() + ".joinBySocialMutualRelationType";

	public static String JOIN_BY_SOCIAL_RELATION =
		UserFinder.class.getName() + ".joinBySocialRelation";

	public static String JOIN_BY_SOCIAL_RELATION_TYPE =
		UserFinder.class.getName() + ".joinBySocialRelationType";

	public int countByUser(long userId, LinkedHashMap<String, Object> params)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_USER);

			sql = StringUtil.replace(sql, "[$JOIN$]", getJoin(params));
			sql = StringUtil.replace(sql, "[$WHERE$]", getWhere(params));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			setJoin(qPos, params);

			qPos.add(userId);

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

	public int countByKeywords(
			long companyId, String keywords, int status,
			LinkedHashMap<String, Object> params)
		throws SystemException {

		String[] firstNames = null;
		String[] middleNames = null;
		String[] lastNames = null;
		String[] screenNames = null;
		String[] emailAddresses = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			firstNames = CustomSQLUtil.keywords(keywords);
			middleNames = CustomSQLUtil.keywords(keywords);
			lastNames = CustomSQLUtil.keywords(keywords);
			screenNames = CustomSQLUtil.keywords(keywords);
			emailAddresses = CustomSQLUtil.keywords(keywords);
		}
		else {
			andOperator = true;
		}

		return countByC_FN_MN_LN_SN_EA_S(
			companyId, firstNames, middleNames, lastNames, screenNames,
			emailAddresses, status, params, andOperator);
	}

	public int countByC_FN_MN_LN_SN_EA_S(
			long companyId, String firstName, String middleName,
			String lastName, String screenName, String emailAddress,
			int status, LinkedHashMap<String, Object> params,
			boolean andOperator)
		throws SystemException {

		String[] firstNames = CustomSQLUtil.keywords(firstName);
		String[] middleNames = CustomSQLUtil.keywords(middleName);
		String[] lastNames = CustomSQLUtil.keywords(lastName);
		String[] screenNames = CustomSQLUtil.keywords(lastName);
		String[] emailAddresses = CustomSQLUtil.keywords(emailAddress);

		return countByC_FN_MN_LN_SN_EA_S(
			companyId, firstNames, middleNames, lastNames, screenNames,
			emailAddresses, status, params, andOperator);
	}

	public int countByC_FN_MN_LN_SN_EA_S(
			long companyId, String[] firstNames, String[] middleNames,
			String[] lastNames, String[] screenNames, String[] emailAddresses,
			int status, LinkedHashMap<String, Object> params,
			boolean andOperator)
		throws SystemException {

		firstNames = CustomSQLUtil.keywords(firstNames);
		middleNames = CustomSQLUtil.keywords(middleNames);
		lastNames = CustomSQLUtil.keywords(lastNames);
		screenNames = CustomSQLUtil.keywords(screenNames);
		emailAddresses = CustomSQLUtil.keywords(emailAddresses);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_C_FN_MN_LN_SN_EA_S);

			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(User_.firstName)", StringPool.LIKE, false,
				firstNames);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(User_.middleName)", StringPool.LIKE, false,
				middleNames);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(User_.lastName)", StringPool.LIKE, false,
				lastNames);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(User_.screenName)", StringPool.LIKE, false,
				screenNames);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(User_.emailAddress)", StringPool.LIKE, true,
				emailAddresses);

			if (status == WorkflowConstants.STATUS_ANY) {
				sql = StringUtil.replace(sql, STATUS_SQL, StringPool.BLANK);
			}

			sql = StringUtil.replace(sql, "[$JOIN$]", getJoin(params));
			sql = StringUtil.replace(sql, "[$WHERE$]", getWhere(params));
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			setJoin(qPos, params);

			qPos.add(companyId);
			qPos.add(false);
			qPos.add(firstNames, 2);
			qPos.add(middleNames, 2);
			qPos.add(lastNames, 2);
			qPos.add(screenNames, 2);
			qPos.add(emailAddresses, 2);

			if (status != WorkflowConstants.STATUS_ANY) {
				qPos.add(status);
			}

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

	public List<User> findByKeywords(
			long companyId, String keywords, int status,
			LinkedHashMap<String, Object> params, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		String[] firstNames = null;
		String[] middleNames = null;
		String[] lastNames = null;
		String[] screenNames = null;
		String[] emailAddresses = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			firstNames = CustomSQLUtil.keywords(keywords);
			middleNames = CustomSQLUtil.keywords(keywords);
			lastNames = CustomSQLUtil.keywords(keywords);
			screenNames = CustomSQLUtil.keywords(keywords);
			emailAddresses = CustomSQLUtil.keywords(keywords);
		}
		else {
			andOperator = true;
		}

		return findByC_FN_MN_LN_SN_EA_S(
			companyId, firstNames, middleNames, lastNames, screenNames,
			emailAddresses, status, params, andOperator, start, end, obc);
	}

	public List<User> findByNoAnnouncementsDeliveries(String type)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_NO_ANNOUNCEMENTS_DELIVERIES);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("User_", UserImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(type);

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<User> findByNoContacts() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_NO_CONTACTS);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("User_", UserImpl.class);

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<User> findByNoGroups() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_NO_GROUPS);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("User_", UserImpl.class);

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<User> findByC_FN_MN_LN_SN_EA_S(
			long companyId, String firstName, String middleName,
			String lastName, String screenName, String emailAddress,
			int status, LinkedHashMap<String, Object> params,
			boolean andOperator, int start, int end, OrderByComparator obc)
		throws SystemException {

		String[] firstNames = CustomSQLUtil.keywords(firstName);
		String[] middleNames = CustomSQLUtil.keywords(middleName);
		String[] lastNames = CustomSQLUtil.keywords(lastName);
		String[] screenNames = CustomSQLUtil.keywords(screenName);
		String[] emailAddresses = CustomSQLUtil.keywords(emailAddress);

		return findByC_FN_MN_LN_SN_EA_S(
			companyId, firstNames, middleNames, lastNames, screenNames,
			emailAddresses, status, params, andOperator, start, end, obc);
	}

	public List<User> findByC_FN_MN_LN_SN_EA_S(
			long companyId, String[] firstNames, String[] middleNames,
			String[] lastNames, String[] screenNames, String[] emailAddresses,
			int status, LinkedHashMap<String, Object> params,
			boolean andOperator, int start, int end, OrderByComparator obc)
		throws SystemException {

		firstNames = CustomSQLUtil.keywords(firstNames);
		middleNames = CustomSQLUtil.keywords(middleNames);
		lastNames = CustomSQLUtil.keywords(lastNames);
		screenNames = CustomSQLUtil.keywords(screenNames);
		emailAddresses = CustomSQLUtil.keywords(emailAddresses);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_FN_MN_LN_SN_EA_S);

			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(User_.firstName)", StringPool.LIKE, false,
				firstNames);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(User_.middleName)", StringPool.LIKE, false,
				middleNames);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(User_.lastName)", StringPool.LIKE, false,
				lastNames);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(User_.screenName)", StringPool.LIKE, false,
				screenNames);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(User_.emailAddress)", StringPool.LIKE, true,
				emailAddresses);

			if (status == WorkflowConstants.STATUS_ANY) {
				sql = StringUtil.replace(sql, STATUS_SQL, StringPool.BLANK);
			}

			sql = StringUtil.replace(sql, "[$JOIN$]", getJoin(params));
			sql = StringUtil.replace(sql, "[$WHERE$]", getWhere(params));
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);
			sql = CustomSQLUtil.replaceOrderBy(sql, obc);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("User_", UserImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			setJoin(qPos, params);

			qPos.add(companyId);
			qPos.add(false);
			qPos.add(firstNames, 2);
			qPos.add(middleNames, 2);
			qPos.add(lastNames, 2);
			qPos.add(screenNames, 2);
			qPos.add(emailAddresses, 2);

			if (status != WorkflowConstants.STATUS_ANY) {
				qPos.add(status);
			}

			return (List<User>)QueryUtil.list(q, getDialect(), start, end);
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

		Iterator<Map.Entry<String, Object>> itr = params.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, Object> entry = itr.next();

			String key = entry.getKey();

			if (key.equals("expandoAttributes")) {
				continue;
			}

			Object value = entry.getValue();

			if (Validator.isNotNull(value)) {
				sb.append(getJoin(key, value));
			}
		}

		return sb.toString();
	}

	protected String getJoin(String key, Object value) {
		String join = StringPool.BLANK;

		if (key.equals("contactTwitterSn")) {
			join = CustomSQLUtil.get(JOIN_BY_CONTACT_TWITTER_SN);
		}
		else if (key.equals("noOrganizations")) {
			join = CustomSQLUtil.get(JOIN_BY_NO_ORGANIZATIONS);
		}
		else if (key.equals("permission")) {
			join = CustomSQLUtil.get(JOIN_BY_PERMISSION);
		}
		else if (key.equals("userGroupRole")) {
			join = CustomSQLUtil.get(JOIN_BY_USER_GROUP_ROLE);
		}
		else if (key.equals("usersGroups")) {
			join = CustomSQLUtil.get(JOIN_BY_USERS_GROUPS);
		}
		else if (key.equals("usersOrgs")) {
			join = CustomSQLUtil.get(JOIN_BY_USERS_ORGS);
		}
		else if (key.equals("usersOrgsTree")) {
			join = CustomSQLUtil.get(JOIN_BY_USERS_ORGS_TREE);
		}
		else if (key.equals("usersPasswordPolicies")) {
			join = CustomSQLUtil.get(JOIN_BY_USERS_PASSWORD_POLICIES);
		}
		else if (key.equals("usersRoles")) {
			join = CustomSQLUtil.get(JOIN_BY_USERS_ROLES);
		}
		else if (key.equals("usersTeams")) {
			join = CustomSQLUtil.get(JOIN_BY_USERS_TEAMS);
		}
		else if (key.equals("usersUserGroups")) {
			join = CustomSQLUtil.get(JOIN_BY_USERS_USER_GROUPS);
		}
		else if (key.equals("announcementsDeliveryEmailOrSms")) {
			join = CustomSQLUtil.get(
				JOIN_BY_ANNOUNCEMENTS_DELIVERY_EMAIL_OR_SMS);
		}
		else if (key.equals("socialMutualRelation")) {
			join = CustomSQLUtil.get(JOIN_BY_SOCIAL_MUTUAL_RELATION);
		}
		else if (key.equals("socialMutualRelationType")) {
			join = CustomSQLUtil.get(JOIN_BY_SOCIAL_MUTUAL_RELATION_TYPE);
		}
		else if (key.equals("socialRelation")) {
			join = CustomSQLUtil.get(JOIN_BY_SOCIAL_RELATION);
		}
		else if (key.equals("socialRelationType")) {
			join = CustomSQLUtil.get(JOIN_BY_SOCIAL_RELATION_TYPE);
		}
		else if (value instanceof CustomSQLParam) {
			CustomSQLParam customSQLParam = (CustomSQLParam)value;

			join = customSQLParam.getSQL();
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

	protected String getWhere(String key, Object value) {
		String join = StringPool.BLANK;

		if (key.equals("contactTwitterSn")) {
			join = CustomSQLUtil.get(JOIN_BY_CONTACT_TWITTER_SN);
		}
		else if (key.equals("noOrganizations")) {
			join = CustomSQLUtil.get(JOIN_BY_NO_ORGANIZATIONS);
		}
		else if (key.equals("permission")) {
			join = CustomSQLUtil.get(JOIN_BY_PERMISSION);
		}
		else if (key.equals("userGroupRole")) {
			join = CustomSQLUtil.get(JOIN_BY_USER_GROUP_ROLE);
		}
		else if (key.equals("usersGroups")) {
			join = CustomSQLUtil.get(JOIN_BY_USERS_GROUPS);
		}
		else if (key.equals("usersOrgs")) {
			if (value instanceof Long) {
				join = CustomSQLUtil.get(JOIN_BY_USERS_ORGS);
			}
			else if (value instanceof Long[]) {
				Long[] organizationIds = (Long[])value;

				if (organizationIds.length == 0) {
					join = "WHERE ((Users_Orgs.organizationId = -1) ))";
				}
				else {
					StringBundler sb = new StringBundler(
						organizationIds.length * 2 + 1);

					sb.append("WHERE (");

					for (int i = 0; i < organizationIds.length; i++) {
						sb.append("(Users_Orgs.organizationId = ?) ");

						if ((i + 1) < organizationIds.length) {
							sb.append("OR ");
						}
					}

					sb.append(")");

					join = sb.toString();
				}
			}
		}
		else if (key.equals("usersOrgsTree")) {
			List<Organization> organizationsTree = (List<Organization>)value;

			int size = organizationsTree.size();

			if (size > 0) {
				StringBundler sb = new StringBundler(size * 2 + 1);

				sb.append("WHERE (");

				for (int i = 0; i < size; i++) {
					sb.append("(Organization_.treePath LIKE ?) ");

					if ((i + 1) < size) {
						sb.append("OR ");
					}
				}

				sb.append(")");

				join = sb.toString();
			}
		}
		else if (key.equals("usersPasswordPolicies")) {
			join = CustomSQLUtil.get(JOIN_BY_USERS_PASSWORD_POLICIES);
		}
		else if (key.equals("usersRoles")) {
			join = CustomSQLUtil.get(JOIN_BY_USERS_ROLES);
		}
		else if (key.equals("usersTeams")) {
			join = CustomSQLUtil.get(JOIN_BY_USERS_TEAMS);
		}
		else if (key.equals("usersUserGroups")) {
			join = CustomSQLUtil.get(JOIN_BY_USERS_USER_GROUPS);
		}
		else if (key.equals("announcementsDeliveryEmailOrSms")) {
			join = CustomSQLUtil.get(
				JOIN_BY_ANNOUNCEMENTS_DELIVERY_EMAIL_OR_SMS);
		}
		else if (key.equals("socialMutualRelation")) {
			join = CustomSQLUtil.get(JOIN_BY_SOCIAL_MUTUAL_RELATION);
		}
		else if (key.equals("socialMutualRelationType")) {
			join = CustomSQLUtil.get(JOIN_BY_SOCIAL_MUTUAL_RELATION_TYPE);
		}
		else if (key.equals("socialRelation")) {
			join = CustomSQLUtil.get(JOIN_BY_SOCIAL_RELATION);
		}
		else if (key.equals("socialRelationType")) {
			join = CustomSQLUtil.get(JOIN_BY_SOCIAL_RELATION_TYPE);
		}
		else if (value instanceof CustomSQLParam) {
			CustomSQLParam customSQLParam = (CustomSQLParam)value;

			join = customSQLParam.getSQL();
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
			String key = entry.getKey();

			if (key.equals("expandoAttributes")) {
				continue;
			}

			Object value = entry.getValue();

			if (key.equals("usersOrgsTree")) {
				List<Organization> organizationsTree =
					(List<Organization>)value;

				if (!organizationsTree.isEmpty()) {
					for (Organization organization : organizationsTree) {
						StringBundler treePath = new StringBundler(5);

						treePath.append(StringPool.PERCENT);
						treePath.append(StringPool.SLASH);
						treePath.append(organization.getOrganizationId());
						treePath.append(StringPool.SLASH);
						treePath.append(StringPool.PERCENT);

						qPos.add(treePath.toString());
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
			else if (value instanceof String[]) {
				String[] valueArray = (String[])value;

				for (String element : valueArray) {
					if (Validator.isNotNull(element)) {
						qPos.add(element);
					}
				}
			}
			else if (value instanceof CustomSQLParam) {
				CustomSQLParam customSQLParam = (CustomSQLParam)value;

				customSQLParam.process(qPos);
			}
		}
	}

	protected static String STATUS_SQL = "AND (User_.status = ?)";

}