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

package com.liferay.mobile.device.rules.service.persistence.impl;

import com.liferay.mobile.device.rules.model.MDRRuleGroup;
import com.liferay.mobile.device.rules.model.impl.MDRRuleGroupImpl;
import com.liferay.mobile.device.rules.service.persistence.MDRRuleGroupFinder;
import com.liferay.mobile.device.rules.util.comparator.RuleGroupCreateDateComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Edward Han
 * @author Eduardo Lundgren
 * @author Manuel de la Peña
 */
@Component(service = MDRRuleGroupFinder.class)
public class MDRRuleGroupFinderImpl
	extends MDRRuleGroupFinderBaseImpl implements MDRRuleGroupFinder {

	public static final String COUNT_BY_G_N =
		MDRRuleGroupFinder.class.getName() + ".countByG_N";

	public static final String FIND_BY_G_N =
		MDRRuleGroupFinder.class.getName() + ".findByG_N";

	@Override
	public int countByKeywords(
		long groupId, String keywords, LinkedHashMap<String, Object> params) {

		String[] names = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			names = _customSQL.keywords(keywords);
		}
		else {
			andOperator = true;
		}

		return countByG_N(groupId, names, params, andOperator);
	}

	@Override
	public int countByG_N(
		long groupId, String name, LinkedHashMap<String, Object> params,
		boolean andOperator) {

		String[] names = _customSQL.keywords(name);

		return countByG_N(groupId, names, params, andOperator);
	}

	@Override
	public int countByG_N(
		long groupId, String[] names, LinkedHashMap<String, Object> params,
		boolean andOperator) {

		names = _customSQL.keywords(names);

		if (params == null) {
			params = _emptyLinkedHashMap;
		}

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_G_N);

			sql = StringUtil.replace(sql, "[$GROUP_ID$]", getGroupIds(params));
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(name)", StringPool.LIKE, true, names);
			sql = _customSQL.replaceAndOperator(sql, true);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			setGroupIds(qPos, groupId, params);

			qPos.add(names, 2);

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
	public List<MDRRuleGroup> findByKeywords(
		long groupId, String keywords, LinkedHashMap<String, Object> params,
		int start, int end) {

		return findByKeywords(
			groupId, keywords, params, start, end,
			new RuleGroupCreateDateComparator());
	}

	@Override
	public List<MDRRuleGroup> findByKeywords(
		long groupId, String keywords, LinkedHashMap<String, Object> params,
		int start, int end, OrderByComparator<MDRRuleGroup> obc) {

		String[] names = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			names = _customSQL.keywords(keywords);
		}
		else {
			andOperator = true;
		}

		return findByG_N(groupId, names, params, andOperator, start, end, obc);
	}

	@Override
	public List<MDRRuleGroup> findByG_N(
		long groupId, String name, LinkedHashMap<String, Object> params,
		boolean andOperator) {

		return findByG_N(
			groupId, name, params, andOperator, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	@Override
	public List<MDRRuleGroup> findByG_N(
		long groupId, String name, LinkedHashMap<String, Object> params,
		boolean andOperator, int start, int end) {

		String[] names = _customSQL.keywords(name);

		return findByG_N(groupId, names, params, andOperator, start, end);
	}

	@Override
	public List<MDRRuleGroup> findByG_N(
		long groupId, String[] names, LinkedHashMap<String, Object> params,
		boolean andOperator, int start, int end) {

		return findByG_N(
			groupId, names, params, andOperator, start, end,
			new RuleGroupCreateDateComparator());
	}

	@Override
	public List<MDRRuleGroup> findByG_N(
		long groupId, String[] names, LinkedHashMap<String, Object> params,
		boolean andOperator, int start, int end,
		OrderByComparator<MDRRuleGroup> obc) {

		names = _customSQL.keywords(names);

		if (params == null) {
			params = _emptyLinkedHashMap;
		}

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_G_N);

			sql = StringUtil.replace(sql, "[$GROUP_ID$]", getGroupIds(params));
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(name)", StringPool.LIKE, true, names);
			sql = _customSQL.replaceAndOperator(sql, andOperator);
			sql = _customSQL.replaceOrderBy(sql, obc);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("MDRRuleGroup", MDRRuleGroupImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			setGroupIds(qPos, groupId, params);

			qPos.add(names, 2);

			return (List<MDRRuleGroup>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getGroupIds(Map<String, Object> params) {
		Boolean includeGlobalScope = (Boolean)params.get("includeGlobalScope");

		if ((includeGlobalScope != null) && includeGlobalScope) {
			return "((groupId = ?) OR (groupId = ?))";
		}

		return "(groupId = ?)";
	}

	protected void setGroupIds(
			QueryPos qPos, long groupId, Map<String, Object> params)
		throws PortalException {

		Boolean includeGlobalScope = (Boolean)params.get("includeGlobalScope");

		if ((includeGlobalScope != null) && includeGlobalScope) {
			qPos.add(groupId);

			Group group = _groupLocalService.getGroup(groupId);

			Group companyGroup = _groupLocalService.getCompanyGroup(
				group.getCompanyId());

			qPos.add(companyGroup.getGroupId());
		}
		else {
			qPos.add(groupId);
		}
	}

	@Reference
	private CustomSQL _customSQL;

	private final LinkedHashMap<String, Object> _emptyLinkedHashMap =
		new LinkedHashMap<>();

	@Reference
	private GroupLocalService _groupLocalService;

}