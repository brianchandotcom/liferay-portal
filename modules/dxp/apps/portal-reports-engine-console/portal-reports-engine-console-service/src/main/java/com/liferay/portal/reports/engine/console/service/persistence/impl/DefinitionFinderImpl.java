/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.service.persistence.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.reports.engine.console.model.Definition;
import com.liferay.portal.reports.engine.console.model.impl.DefinitionImpl;
import com.liferay.portal.reports.engine.console.service.persistence.DefinitionFinder;

import java.util.Iterator;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(service = DefinitionFinder.class)
public class DefinitionFinderImpl
	extends DefinitionFinderBaseImpl implements DefinitionFinder {

	public static final String COUNT_BY_G_S_N_D_RN =
		DefinitionFinder.class.getName() + ".countByG_S_N_D_RN";

	public static final String FIND_BY_G_S_N_D_RN =
		DefinitionFinder.class.getName() + ".findByG_S_N_D_RN";

	@Override
	public int countByG_S_N_D_RN(
		long groupId, String name, String description, long sourceId,
		String reportName, boolean andOperator) {

		return doCountByG_S_N_D_RN(
			groupId, name, description, sourceId, reportName, andOperator,
			false);
	}

	@Override
	public int filterCountByG_S_N_D_RN(
		long groupId, String name, String description, long sourceId,
		String reportName, boolean andOperator) {

		return doCountByG_S_N_D_RN(
			groupId, name, description, sourceId, reportName, andOperator,
			true);
	}

	@Override
	public List<Definition> filterFindByG_S_N_D_RN(
		long groupId, String name, String description, long sourceId,
		String reportName, boolean andOperator, int start, int end,
		OrderByComparator<Definition> orderByComparator) {

		return doFindByG_S_N_D_RN(
			groupId, name, description, sourceId, reportName, andOperator,
			start, end, orderByComparator, true);
	}

	@Override
	public List<Definition> findByG_S_N_D_RN(
		long groupId, String name, String description, long sourceId,
		String reportName, boolean andOperator, int start, int end,
		OrderByComparator<Definition> orderByComparator) {

		return doFindByG_S_N_D_RN(
			groupId, name, description, sourceId, reportName, andOperator,
			start, end, orderByComparator, false);
	}

	protected int doCountByG_S_N_D_RN(
		long groupId, String name, String description, long sourceId,
		String reportName, boolean andOperator, boolean inlineSQLHelper) {

		if (Validator.isNull(name) || Validator.isNull(description) ||
			Validator.isNull(reportName)) {

			andOperator = true;
		}

		String[] names = _customSQL.keywords(name);
		String[] descriptions = _customSQL.keywords(description);
		String[] reportNames = _customSQL.keywords(reportName);

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_G_S_N_D_RN);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, Definition.class.getName(),
					"Reports_Definition.definitionId", groupId);
			}

			if (groupId <= 0) {
				sql = StringUtil.replace(
					sql, "(Reports_Definition.groupId = ?) AND",
					StringPool.BLANK);
			}

			if (sourceId <= 0) {
				sql = StringUtil.replace(
					sql,
					"(Reports_Definition.sourceId = ?) [$AND_OR_CONNECTOR$]",
					StringPool.BLANK);
			}

			sql = _customSQL.replaceKeywords(
				sql, "LOWER(CAST_TEXT(Reports_Definition.name))",
				StringPool.LIKE, false, names);
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(CAST_TEXT(Reports_Definition.description))",
				StringPool.LIKE, false, descriptions);
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(Reports_Definition.reportName)", StringPool.LIKE,
				true, reportNames);

			sql = _customSQL.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (groupId > 0) {
				qPos.add(groupId);
			}

			if (sourceId > 0) {
				qPos.add(sourceId);
			}

			qPos.add(names, 2);
			qPos.add(descriptions, 2);
			qPos.add(reportNames, 2);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<Definition> doFindByG_S_N_D_RN(
		long groupId, String name, String description, long sourceId,
		String reportName, boolean andOperator, int start, int end,
		OrderByComparator<Definition> orderByComparator,
		boolean inlineSQLHelper) {

		if (Validator.isNull(name) || Validator.isNull(description) ||
			Validator.isNull(reportName)) {

			andOperator = true;
		}

		String[] names = _customSQL.keywords(name);
		String[] descriptions = _customSQL.keywords(description);
		String[] reportNames = _customSQL.keywords(reportName);

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_G_S_N_D_RN);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, Definition.class.getName(),
					"Reports_Definition.definitionId", groupId);
			}

			if (groupId <= 0) {
				sql = StringUtil.replace(
					sql, "(Reports_Definition.groupId = ?) AND",
					StringPool.BLANK);
			}

			if (sourceId <= 0) {
				sql = StringUtil.replace(
					sql,
					"(Reports_Definition.sourceId = ?) [$AND_OR_CONNECTOR$]",
					StringPool.BLANK);
			}

			sql = _customSQL.replaceKeywords(
				sql, "LOWER(CAST_TEXT(Reports_Definition.name))",
				StringPool.LIKE, false, names);
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(CAST_TEXT(Reports_Definition.description))",
				StringPool.LIKE, false, descriptions);
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(Reports_Definition.reportName)", StringPool.LIKE,
				true, reportNames);

			sql = _customSQL.replaceAndOperator(sql, andOperator);

			if (orderByComparator != null) {
				sql = _customSQL.replaceOrderBy(sql, orderByComparator);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("Reports_Definition", DefinitionImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			if (groupId > 0) {
				qPos.add(groupId);
			}

			if (sourceId > 0) {
				qPos.add(sourceId);
			}

			qPos.add(names, 2);
			qPos.add(descriptions, 2);
			qPos.add(reportNames, 2);

			return (List<Definition>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Reference
	private CustomSQL _customSQL;

}