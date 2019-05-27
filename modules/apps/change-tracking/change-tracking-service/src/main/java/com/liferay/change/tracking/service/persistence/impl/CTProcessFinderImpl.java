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

package com.liferay.change.tracking.service.persistence.impl;

import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.model.impl.CTProcessImpl;
import com.liferay.change.tracking.service.persistence.CTProcessFinder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(service = CTProcessFinder.class)
public class CTProcessFinderImpl
	extends CTProcessFinderBaseImpl implements CTProcessFinder {

	public static final String FIND_BY_C_U_S_N_D =
		CTProcessFinder.class.getName() + ".findByC_U_S_N_D";

	@SuppressWarnings("unchecked")
	public List<CTProcess> findByC_U_S_N_D(
		long companyId, long userId, int status, String keywords, int start,
		int end, OrderByComparator<?> orderByComparator) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_C_U_S_N_D);

			if (userId <= 0) {
				sql = StringUtil.replace(
					sql, "(CTProcess.userId = ?) AND", StringPool.BLANK);
			}

			if (status == WorkflowConstants.STATUS_ANY) {
				sql = StringUtil.replace(
					sql, "(BackgroundTask.status = ?) AND", StringPool.BLANK);
			}

			String[] names = _customSQL.keywords(
				keywords, true, WildcardMode.SURROUND);
			String[] descriptions = _customSQL.keywords(
				keywords, true, WildcardMode.SURROUND);

			boolean keywordsEmpty = _isKeywordsEmpty(names);

			if (keywordsEmpty) {
				sql = _replaceKeywordConditionsWithBlank(sql);
			}
			else {
				sql = _customSQL.replaceKeywords(
					sql, "LOWER(CTCollection.name)", StringPool.LIKE, false,
					names);
				sql = _customSQL.replaceKeywords(
					sql, "LOWER(CTCollection.description)", StringPool.LIKE,
					true, descriptions);
				sql = _customSQL.replaceAndOperator(sql, false);
			}

			sql = _customSQL.replaceOrderBy(sql, orderByComparator);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("CTProcess", CTProcessImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			if (userId > 0) {
				qPos.add(userId);
			}

			if (status != WorkflowConstants.STATUS_ANY) {
				qPos.add(status);
			}

			if (!keywordsEmpty) {
				qPos.add(names, 2);

				qPos.add(descriptions, 2);
			}

			return (List<CTProcess>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private boolean _isKeywordsEmpty(String[] keywords) {
		boolean emptyKeywords = false;
		boolean nonEmptyKeywords = false;

		for (String keyword : keywords) {
			emptyKeywords = Validator.isNull(keyword);
			nonEmptyKeywords = Validator.isNotNull(keyword);
		}

		if (emptyKeywords && !nonEmptyKeywords) {
			return true;
		}

		return false;
	}

	private String _replaceKeywordConditionsWithBlank(String sql) {
		Matcher matcher = _pattern.matcher(sql);

		return matcher.replaceAll(StringPool.BLANK);
	}

	private static final Pattern _pattern = Pattern.compile(
		"AND\\s*\\(\\s*\\([A-Za-z\\(\\)\\.\\s\\?\\[\\]$_]*\\)\\s*\\)");

	@Reference
	private CustomSQL _customSQL;

}