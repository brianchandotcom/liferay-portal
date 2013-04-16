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

package com.liferay.portlet.mobiledevicerules.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;
import com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Edward Han
 * @author Eduardo Lundgren
 * @author Manuel de la Peña
 */
public class MDRRuleGroupFinderImpl extends BasePersistenceImpl<MDRRuleGroup>
	implements MDRRuleGroupFinder {

	public static final String COUNT_BY_G_N =
		MDRRuleGroupFinder.class.getName() + ".countByG_N";

	public static final String FIND_BY_G_N =
		MDRRuleGroupFinder.class.getName() + ".findByG_N";

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #countByKeywords(long,
	 *             java.util.LinkedHashMap, String)}
	 */
	public int countByKeywords(long groupId, String keywords)
		throws SystemException {

		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();

		params.put("includeGlobalScope", true);

		return countByKeywords(groupId, params, keywords);
	}

	public int countByKeywords(
			long groupId, LinkedHashMap<String, Object> params, String keywords)
		throws SystemException {

		String[] names = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			names = CustomSQLUtil.keywords(keywords);
		}
		else {
			andOperator = true;
		}

		return countByG_N(groupId, names, params, andOperator);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #countByG_N(long, String,
	 *             java.util.LinkedHashMap, boolean)}
	 */
	public int countByG_N(long groupId, String name, boolean andOperator)
		throws SystemException {

		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();

		params.put("includeGlobalScope", true);

		return countByG_N(groupId, name, params, andOperator);
	}

	public int countByG_N(
			long groupId, String name, LinkedHashMap<String, Object> params,
			boolean andOperator)
		throws SystemException {

		String[] names = CustomSQLUtil.keywords(name);

		return countByG_N(groupId, names, params, andOperator);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #countByG_N(long, String[],
	 *             java.util.LinkedHashMap, boolean)}
	 */
	public int countByG_N(long groupId, String[] names, boolean andOperator)
		throws SystemException {

		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();

		params.put("includeGlobalScope", true);

		return countByG_N(groupId, names, params, andOperator);
	}

	public int countByG_N(
			long groupId, String[] names, LinkedHashMap<String, Object> params,
			boolean andOperator)
		throws SystemException {

		names = CustomSQLUtil.keywords(names);

		if (params == null) {
			params = _emptyLinkedHashMap;
		}

		LinkedHashMap<String, Object> params1 = params;

		Session session = null;

		try {
			session = openSession();

			String sql = null;

			if (params1.size() > 0) {
				String sqlKey = _buildSQLKey(params1);

				sql = _countByG_NCache.get(sqlKey);
			}

			if (sql == null) {
				String countByG_N = CustomSQLUtil.get(COUNT_BY_G_N);

				StringBundler sb = new StringBundler();

				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append(replaceGroupIdComparator(countByG_N, params1));
				sb.append(StringPool.CLOSE_PARENTHESIS);

				if (params1.size() > 0) {
					String sqlKey = _buildSQLKey(params1);

					_countByG_NCache.put(sqlKey, sb.toString());
				}

				sql = sb.toString();
			}

			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(name)", StringPool.LIKE, true, names);
			sql = CustomSQLUtil.replaceAndOperator(sql, false);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			setParameterValues(qPos, params1);

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

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #findByKeywords(long, String,
	 *             java.util.LinkedHashMap, int, int)}
	 */
	public List<MDRRuleGroup> findByKeywords(
			long groupId, String keywords, int start, int end)
		throws SystemException {

		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();

		params.put("includeGlobalScope", true);

		return findByKeywords(groupId, keywords, params, start, end);
	}

	public List<MDRRuleGroup> findByKeywords(
			long groupId, String keywords, LinkedHashMap<String, Object> params,
			int start, int end)
		throws SystemException {

		String[] names = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			names = CustomSQLUtil.keywords(keywords);
		}
		else {
			andOperator = true;
		}

		return findByG_N(groupId, names, params, andOperator, start, end);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #findByG_N(long, String,
	 *             java.util.LinkedHashMap, boolean)}
	 */
	public List<MDRRuleGroup> findByG_N(
			long groupId, String name, boolean andOperator)
		throws SystemException {

		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();

		params.put("includeGlobalScope", true);

		return findByG_N(groupId, name, params, andOperator);
	}

	public List<MDRRuleGroup> findByG_N(
			long groupId, String name, LinkedHashMap<String, Object> params,
			boolean andOperator)
		throws SystemException {

		return findByG_N(
			groupId, name, params, andOperator, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #findByG_N(long, String,
	 *             java.util.LinkedHashMap, boolean, int, int)}
	 */
	public List<MDRRuleGroup> findByG_N(
			long groupId, String name, boolean andOperator, int start, int end)
		throws SystemException {

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("includeGlobalScope", true);

		return findByG_N(groupId, name, params, andOperator, start, end);
	}

	public List<MDRRuleGroup> findByG_N(
			long groupId, String name, LinkedHashMap<String, Object> params,
			boolean andOperator, int start, int end)
		throws SystemException {

		String[] names = CustomSQLUtil.keywords(name);

		return findByG_N(groupId, names, params, andOperator, start, end);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #findByG_N(long, String,
	 *             java.util.LinkedHashMap, boolean, int, int)}
	 */
	public List<MDRRuleGroup> findByG_N(
			long groupId, String[] names, boolean andOperator, int start,
			int end)
		throws SystemException {

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("includeGlobalScope", true);

		return findByG_N(groupId, names, params, andOperator, start, end);
	}

	public List<MDRRuleGroup> findByG_N(
			long groupId, String[] names, LinkedHashMap<String, Object> params,
			boolean andOperator, int start, int end)
		throws SystemException {

		names = CustomSQLUtil.keywords(names);

		if (params == null) {
			params = _emptyLinkedHashMap;
		}

		LinkedHashMap<String, Object> params1 = params;

		Session session = null;

		try {
			session = openSession();

			String sql = null;

			if (params1.size() > 0) {
				String sqlKey = _buildSQLKey(params1);

				sql = _findByG_NCache.get(sqlKey);
			}

			if (sql == null) {
				String findByG_N = CustomSQLUtil.get(FIND_BY_G_N);

				StringBundler sb = new StringBundler();

				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append(replaceGroupIdComparator(findByG_N, params1));
				sb.append(StringPool.CLOSE_PARENTHESIS);

				if (params1.size() > 0) {
					String sqlKey = _buildSQLKey(params1);

					_findByG_NCache.put(sqlKey, sb.toString());
				}

				sql = sb.toString();
			}

			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(name)", StringPool.LIKE, true, names);
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("MDRRuleGroup", MDRRuleGroupImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			setParameterValues(qPos, params1);

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

	protected String getGroupIdComparator(
		LinkedHashMap<String, Object> params) {

		if ((params == null) || params.isEmpty()) {
			return "(groupId = ?)";
		}

		StringBundler sb = new StringBundler(params.size());

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();

			if (key.equals("includeGlobalScope")) {
				boolean includeGlobalScope = (Boolean)entry.getValue();

				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append("(groupId = ?)");

				if (includeGlobalScope) {
					sb.append(" OR ");
					sb.append("(groupId = ?)");
					sb.append(StringPool.CLOSE_PARENTHESIS);
				}
			}
		}

		return sb.toString();
	}

	protected String replaceGroupIdComparator(
		String sql, LinkedHashMap<String, Object> params) {

		if (params.isEmpty()) {
			return StringUtil.replace(
				sql,
				new String[] {
					"[$GROUP_ID$]"
				},
				new String[] {
					"(groupId = ?)"
				});
		}

		String cacheKey = _getCacheKey(sql, params);

		String resultSQL = _replaceWhereSQLCache.get(cacheKey);

		if (resultSQL == null) {
			resultSQL = StringUtil.replace(
				sql, "[$GROUP_ID$]", getGroupIdComparator(params));

			_replaceWhereSQLCache.put(cacheKey, resultSQL);
		}

		return resultSQL;
	}

	protected void setParameterValues(
			QueryPos qPos, LinkedHashMap<String, Object> params)
		throws PortalException, SystemException {

		if ((params == null) || params.isEmpty()) {
			return;
		}

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();

			if (key.equals("includeGlobalScope")) {

				boolean includeGlobalScope = (Boolean)entry.getValue();

				if (includeGlobalScope) {
					long currentCompanyId = CompanyThreadLocal.getCompanyId();

					Company company = CompanyLocalServiceUtil.getCompany(
						currentCompanyId);

					Group globalGroup = company.getGroup();

					qPos.add(globalGroup.getGroupId());
				}
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

	private String _buildSQLKey(LinkedHashMap<String, Object> param1) {

		StringBundler sb = new StringBundler(param1.size() + 1);

		for (Map.Entry<String, Object> entry : param1.entrySet()) {
			String key = entry.getKey();

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
			else {
				sb.append(key);
			}
		}

		return sb.toString();
	}

	private String _getCacheKey(
		String sql, LinkedHashMap<String, Object> params) {

		StringBundler sb = new StringBundler();

		sb.append(sql);
		sb.append(_buildSQLKey(params));

		return sb.toString();
	}

	private Map<String, String> _countByG_NCache =
		new ConcurrentHashMap<String, String>();
	private LinkedHashMap<String, Object> _emptyLinkedHashMap =
		new LinkedHashMap<String, Object>(0);
	private Map<String, String> _findByG_NCache =
		new ConcurrentHashMap<String, String>();
	private Map<String, String> _replaceWhereSQLCache =
		new ConcurrentHashMap<String, String>();

}