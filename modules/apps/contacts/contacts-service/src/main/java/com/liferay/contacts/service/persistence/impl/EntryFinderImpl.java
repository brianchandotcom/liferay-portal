/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.contacts.service.persistence.impl;

import com.liferay.contacts.model.Entry;
import com.liferay.contacts.model.impl.EntryImpl;
import com.liferay.contacts.service.persistence.EntryFinder;
import com.liferay.contacts.service.persistence.EntryUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.comparator.UserLastNameComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(service = EntryFinder.class)
public class EntryFinderImpl
	extends EntryFinderBaseImpl implements EntryFinder {

	public static final String COUNT_BY_U_FN_EA =
		EntryFinder.class.getName() + ".countByU_FN_EA";

	public static final String FIND_BY_U_FN_EA =
		EntryFinder.class.getName() + ".findByU_FN_EA";

	@Override
	public int countByKeywords(long companyId, long userId, String keywords) {
		if (Validator.isNotNull(keywords)) {
			int count = _userLocalService.searchCount(
				companyId, keywords, keywords, keywords, keywords, keywords, 0,
				null, false);

			String[] fullNames = _customSQL.keywords(keywords);
			String[] emailAddresses = _customSQL.keywords(keywords);

			count += countByU_FN_EA(userId, fullNames, emailAddresses);

			return count;
		}

		int count = _userLocalService.getUsersCount(
			companyId, WorkflowConstants.STATUS_APPROVED);

		count += EntryUtil.countByUserId(userId);

		return count;
	}

	@Override
	public int countByKeywords(long userId, String keywords) {
		if (Validator.isNotNull(keywords)) {
			String[] fullNames = _customSQL.keywords(keywords);
			String[] emailAddresses = _customSQL.keywords(keywords);

			return countByU_FN_EA(userId, fullNames, emailAddresses);
		}

		return EntryUtil.countByUserId(userId);
	}

	@Override
	public List<BaseModel<?>> findByKeywords(
		long companyId, long userId, String keywords, int start, int end) {

		List<BaseModel<?>> models = new ArrayList<>();

		if (Validator.isNotNull(keywords)) {
			models.addAll(
				_userLocalService.search(
					companyId, keywords, keywords, keywords, keywords, keywords,
					0, null, false, start, end,
					new UserLastNameComparator(true)));

			if (models.size() < (end - start)) {
				int count = _userLocalService.searchCount(
					companyId, keywords, keywords, keywords, keywords, keywords,
					0, null, false);

				start -= count;
				end -= count;

				String[] fullNames = _customSQL.keywords(keywords);
				String[] emailAddresses = _customSQL.keywords(keywords);

				models.addAll(
					findByU_FN_EA(
						userId, fullNames, emailAddresses, start, end));
			}
		}
		else {
			models.addAll(
				_userLocalService.getUsers(
					companyId, WorkflowConstants.STATUS_APPROVED, start, end,
					new UserLastNameComparator(true)));

			if (models.size() < (end - start)) {
				int count = _userLocalService.getUsersCount(
					companyId, WorkflowConstants.STATUS_APPROVED);

				start -= count;
				end -= count;

				models.addAll(EntryUtil.findByUserId(userId, start, end));
			}
		}

		return models;
	}

	@Override
	public List<Entry> findByKeywords(
		long userId, String keywords, int start, int end) {

		if (Validator.isNotNull(keywords)) {
			String[] fullNames = _customSQL.keywords(keywords);
			String[] emailAddresses = _customSQL.keywords(keywords);

			return findByU_FN_EA(userId, fullNames, emailAddresses, start, end);
		}

		return EntryUtil.findByUserId(userId, start, end);
	}

	protected int countByU_FN_EA(
		long userId, String[] fullNames, String[] emailAddresses) {

		fullNames = _customSQL.keywords(fullNames, true);
		emailAddresses = _customSQL.keywords(emailAddresses, true);

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_U_FN_EA);

			sql = _customSQL.replaceKeywords(
				sql, "LOWER(fullName)", StringPool.LIKE, false, fullNames);
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(emailAddress)", StringPool.LIKE, true,
				emailAddresses);
			sql = _customSQL.replaceAndOperator(sql, false);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(userId);
			queryPos.add(fullNames, 2);
			queryPos.add(emailAddresses, 2);

			Iterator<Long> iterator = sqlQuery.iterate();

			if (iterator.hasNext()) {
				Long count = iterator.next();

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

	protected List<Entry> findByU_FN_EA(
		long userId, String[] fullNames, String[] emailAddresses, int start,
		int end) {

		fullNames = _customSQL.keywords(fullNames, true);
		emailAddresses = _customSQL.keywords(emailAddresses, true);

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_U_FN_EA);

			sql = _customSQL.replaceKeywords(
				sql, "LOWER(fullName)", StringPool.LIKE, false, fullNames);
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(emailAddress)", StringPool.LIKE, true,
				emailAddresses);
			sql = _customSQL.replaceAndOperator(sql, false);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity("Contacts_Entry", EntryImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(userId);
			queryPos.add(fullNames, 2);
			queryPos.add(emailAddresses, 2);

			return (List<Entry>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
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

	@Reference
	private UserLocalService _userLocalService;

}