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

package com.liferay.polls.service.persistence.impl;

import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.model.impl.PollsQuestionImpl;
import com.liferay.polls.service.persistence.PollsQuestionFinder;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;
import java.util.List;

/**
 * @author Rafael Praxedes
 */
public class PollsQuestionFinderImpl
	extends PollsQuestionFinderBaseImpl implements PollsQuestionFinder {

	public static final String COUNT_BY_C_G_T_D =
		PollsQuestionFinder.class.getName() + ".countByC_G_T_D";

	public static final String FIND_BY_C_G_T_D =
		PollsQuestionFinder.class.getName() + ".findByC_G_T_D";

	@Override
	public int countByKeywords(
		long companyId, long[] groupIds, String keywords) {

		return doCountByKeywords(companyId, groupIds, keywords, false);
	}

	protected int doCountByKeywords(
		long companyId, long[] groupIds, String keywords,
		boolean inlineSQLHelper) {

		String[] titles = null;
		String[] descriptions = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			titles = CustomSQLUtil.keywords(keywords);
			descriptions = CustomSQLUtil.keywords(keywords, false);
		}
		else {
			andOperator = true;
		}

		return doCountByC_G_T_D(
			companyId, groupIds, titles, descriptions, andOperator,
			inlineSQLHelper);
	}

	@Override
	public int countByC_G_T_D(
		long companyId, long[] groupIds, String title, String description,
		boolean andOperator) {

		String[] names = CustomSQLUtil.keywords(title);
		String[] descriptions = CustomSQLUtil.keywords(description, false);

		return doCountByC_G_T_D(
			companyId, groupIds, names, descriptions, andOperator, false);
	}

	@Override
	public List<PollsQuestion> findByC_G_T_D(
		long companyId, long[] groupIds, String title, String description,
		boolean andOperator, int start, int end,
		OrderByComparator<PollsQuestion> orderByComparator) {

		String[] titles = CustomSQLUtil.keywords(title);
		String[] descriptions = CustomSQLUtil.keywords(description, false);

		return doFindByC_G_T_D(
			companyId, groupIds, titles, descriptions, andOperator, start, end,
			orderByComparator, false);
	}

	@Override
	public List<PollsQuestion> findByKeywords(
		long companyId, long[] groupIds, String keywords, int start, int end,
		OrderByComparator<PollsQuestion> orderByComparator) {

		String[] titles = null;
		String[] descriptions = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			titles = CustomSQLUtil.keywords(keywords);
			descriptions = CustomSQLUtil.keywords(keywords, false);
		}
		else {
			andOperator = true;
		}

		return doFindByC_G_T_D(
			companyId, groupIds, titles, descriptions, andOperator, start, end,
			orderByComparator, false);
	}

	protected int doCountByC_G_T_D(
		long companyId, long[] groupIds, String[] titles, String[] descriptions,
		boolean andOperator, boolean inlineSQLHelper) {

		titles = CustomSQLUtil.keywords(titles);
		descriptions = CustomSQLUtil.keywords(descriptions, false);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_C_G_T_D);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, PollsQuestion.class.getName(),
					"PollsQuestion.questionId", groupIds);
			}

			sql = StringUtil.replace(
				sql, "[$GROUP_ID$]", getGroupIds(groupIds));
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(PollsQuestion.title)", StringPool.LIKE, false,
				titles);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "PollsQuestion.description", StringPool.LIKE, true,
				descriptions);
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			if (groupIds != null) {
				qPos.add(groupIds);
			}

			qPos.add(titles, 2);
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

	protected List<PollsQuestion> doFindByC_G_T_D(
		long companyId, long[] groupIds, String[] titles, String[] descriptions,
		boolean andOperator, int start, int end,
		OrderByComparator<PollsQuestion> orderByComparator,
		boolean inlineSQLHelper) {

		titles = CustomSQLUtil.keywords(titles);
		descriptions = CustomSQLUtil.keywords(descriptions, false);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_G_T_D);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, PollsQuestion.class.getName(),
					"PollsQuestion.questionId", groupIds);
			}

			sql = StringUtil.replace(
				sql, "[$GROUP_ID$]", getGroupIds(groupIds));
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(PollsQuestion.title)", StringPool.LIKE, false,
				titles);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "PollsQuestion.description", StringPool.LIKE, true,
				descriptions);
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);
			sql = CustomSQLUtil.replaceOrderBy(sql, orderByComparator);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("PollsQuestion", PollsQuestionImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			if (groupIds != null) {
				qPos.add(groupIds);
			}

			qPos.add(titles, 2);
			qPos.add(descriptions, 2);

			return (List<PollsQuestion>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getGroupIds(long[] groupIds) {
		if (ArrayUtil.isEmpty(groupIds)) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(groupIds.length * 2);

		sb.append("(");

		for (int i = 0; i < groupIds.length; i++) {
			sb.append("groupId = ?");

			if ((i + 1) < groupIds.length) {
				sb.append(" OR ");
			}
		}

		sb.append(") AND");

		return sb.toString();
	}

}