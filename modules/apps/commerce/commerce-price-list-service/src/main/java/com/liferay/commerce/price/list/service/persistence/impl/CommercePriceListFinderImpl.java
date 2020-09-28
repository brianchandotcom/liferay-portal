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

package com.liferay.commerce.price.list.service.persistence.impl;

import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.impl.CommercePriceEntryImpl;
import com.liferay.commerce.price.list.model.impl.CommercePriceListImpl;
import com.liferay.commerce.price.list.service.persistence.CommercePriceListFinder;
import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Alessio Antonio Rendina
 * @author Riccardo Alberti
 */
public class CommercePriceListFinderImpl
	extends CommercePriceListFinderBaseImpl implements CommercePriceListFinder {

	public static final String COUNT_BY_COMMERCE_PRICING_CLASS_ID =
		CommercePriceListFinder.class.getName() +
			".countByCommercePricingClassId";

	public static final String COUNT_BY_CPINSTANCE_UUID =
		CommercePriceListFinder.class.getName() + ".countByCPInstanceUuid";

	public static final String FIND_BASE_PRICE_ENTRY =
		CommercePriceListFinder.class.getName() + ".findBasePriceEntry";

	public static final String FIND_BY_EXPIRATION_DATE =
		CommercePriceListFinder.class.getName() + ".findByExpirationDate";

	public static final String FIND_BY_COMMERCE_ACCOUNT_ID =
		CommercePriceListFinder.class.getName() + ".findByCommerceAccountId";

	public static final String FIND_BY_COMMERCE_PRICING_CLASS_ID =
		CommercePriceListFinder.class.getName() +
			".findByCommercePricingClassId";

	public static final String FIND_BY_CPINSTANCE_UUID =
		CommercePriceListFinder.class.getName() + ".findByCPInstanceUuid";

	public static final String FIND_BY_ACCOUNT_AND_CHANNEL_ID =
		CommercePriceListFinder.class.getName() + ".findByAccountAndChannelId";

	public static final String FIND_BY_COMMERCE_ACCOUNT_GROUP_IDS =
		CommercePriceListFinder.class.getName() +
			".findByCommerceAccountGroupIds";

	public static final String FIND_BY_ACCOUNT_GROUPS_AND_CHANNEL_ID =
		CommercePriceListFinder.class.getName() +
			".findByAccountGroupsAndChannelId";

	public static final String FIND_BY_COMMERCE_CHANNEL_ID =
		CommercePriceListFinder.class.getName() + ".findByCommerceChannelId";

	public static final String FIND_BY_UNQUALIFIED =
		CommercePriceListFinder.class.getName() + ".findByUnqualified";

	public static final String FIND_BY_LOWEST_PRICE =
		CommercePriceListFinder.class.getName() + ".findByLowestPrice";

	@Override
	public int countByCommercePricingClassId(
		long commercePricingClassId, String name) {

		return countByCommercePricingClassId(
			commercePricingClassId, name, false);
	}

	@Override
	public int countByCommercePricingClassId(
		long commercePricingClassId, String name, boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), COUNT_BY_COMMERCE_PRICING_CLASS_ID);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, CommercePriceList.class.getName(),
					"CommercePriceList.commercePriceListId", null, null,
					new long[] {0}, null);
			}

			String[] keywords = _customSQL.keywords(name, true);

			if (Validator.isNotNull(name)) {
				sql = _customSQL.replaceKeywords(
					sql, "(LOWER(CommercePriceList.name)", StringPool.LIKE,
					true, keywords);
				sql = _customSQL.replaceAndOperator(sql, false);
			}
			else {
				sql = StringUtil.removeSubstring(
					sql,
					" AND (LOWER(CommercePriceList.name) LIKE ? " +
						"[$AND_OR_NULL_CHECK$])");
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(
				PortalUtil.getClassNameId(
					CommercePricingClass.class.getName()));
			queryPos.add(commercePricingClassId);

			if (Validator.isNotNull(name)) {
				queryPos.add(keywords, 2);
			}

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

	@Override
	public int countByCPInstanceUuid(String cpInstanceUuid) {
		return countByCPInstanceUuid(cpInstanceUuid, false);
	}

	@Override
	public int countByCPInstanceUuid(
		String cpInstanceUuid, boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_CPINSTANCE_UUID);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, CommercePriceList.class.getName(),
					"CommercePriceEntry.commercePriceListId", null, null,
					new long[] {0}, null);
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(cpInstanceUuid);

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

	@Override
	public CommercePriceEntry findBasePriceEntry(
		String cpInstanceUuid, String priceListType) {

		return findBasePriceEntry(cpInstanceUuid, priceListType, false);
	}

	@Override
	public CommercePriceEntry findBasePriceEntry(
		String cpInstanceUuid, String priceListType, boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BASE_PRICE_ENTRY);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, CommercePriceList.class.getName(),
					"CommercePriceEntry.commercePriceListId", null, null,
					new long[] {0}, null);
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				CommercePriceEntryImpl.TABLE_NAME,
				CommercePriceEntryImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(cpInstanceUuid);
			queryPos.add(priceListType);

			List<CommercePriceEntry> commercePriceEntries = sqlQuery.list();

			if (!commercePriceEntries.isEmpty()) {
				return commercePriceEntries.get(0);
			}

			return null;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<CommercePriceList> findByCommerceAccountAndChannelId(
		QueryDefinition<CommercePriceList> queryDefinition) {

		return doFindByPK(
			FIND_BY_ACCOUNT_AND_CHANNEL_ID,
			(Long)queryDefinition.getAttribute("groupId"),
			(String)queryDefinition.getAttribute("type"),
			(Long)queryDefinition.getAttribute("commerceChannelId"),
			(Long)queryDefinition.getAttribute("commerceAccountId"),
			queryDefinition.getStart(), queryDefinition.getEnd());
	}

	@Override
	public List<CommercePriceList> findByExpirationDate(
		Date expirationDate,
		QueryDefinition<CommercePriceList> queryDefinition) {

		return doFindByExpirationDate(expirationDate, queryDefinition);
	}

	@Override
	public List<CommercePriceList> findByCommerceAccountId(
		QueryDefinition<CommercePriceList> queryDefinition) {

		return doFindByPK(
			FIND_BY_COMMERCE_ACCOUNT_ID,
			(Long)queryDefinition.getAttribute("groupId"),
			(String)queryDefinition.getAttribute("type"),
			(Long)queryDefinition.getAttribute("commerceAccountId"),
			queryDefinition.getStart(), queryDefinition.getEnd());
	}

	@Override
	public List<CommercePriceList> findByCommerceAccountGroupIds(
		QueryDefinition<CommercePriceList> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), FIND_BY_COMMERCE_ACCOUNT_GROUP_IDS);

			long[] commerceAccountGroupIds =
				(long[])queryDefinition.getAttribute("commerceAccountGroupIds");

			sql = replaceAccountGroupIds(sql, commerceAccountGroupIds);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				CommercePriceListImpl.TABLE_NAME, CommercePriceListImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			long groupId = (Long)queryDefinition.getAttribute("groupId");
			String type = (String)queryDefinition.getAttribute("type");

			queryPos.add(groupId);
			queryPos.add(type);

			return (List<CommercePriceList>)QueryUtil.list(
				sqlQuery, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<CommercePriceList> findByCommerceAccountGroupsAndChannelId(
		QueryDefinition<CommercePriceList> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), FIND_BY_ACCOUNT_GROUPS_AND_CHANNEL_ID);

			long[] commerceAccountGroupIds =
				(long[])queryDefinition.getAttribute("commerceAccountGroupIds");

			sql = replaceAccountGroupIds(sql, commerceAccountGroupIds);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				CommercePriceListImpl.TABLE_NAME, CommercePriceListImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			long commerceChannelId = (Long)queryDefinition.getAttribute(
				"commerceChannelId");
			long groupId = (Long)queryDefinition.getAttribute("groupId");
			String type = (String)queryDefinition.getAttribute("type");

			queryPos.add(commerceChannelId);
			queryPos.add(groupId);
			queryPos.add(type);

			return (List<CommercePriceList>)QueryUtil.list(
				sqlQuery, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<CommercePriceList> findByCommercePricingClassId(
		long commercePricingClassId, String name, int start, int end) {

		return findByCommercePricingClassId(
			commercePricingClassId, name, start, end, false);
	}

	@Override
	public List<CommercePriceList> findByCommercePricingClassId(
		long commercePricingClassId, String name, int start, int end,
		boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String[] keywords = _customSQL.keywords(name, true);

			String sql = _customSQL.get(
				getClass(), FIND_BY_COMMERCE_PRICING_CLASS_ID);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, CommercePriceList.class.getName(),
					"CommercePriceList.commercePriceListId", null, null,
					new long[] {0}, null);
			}

			if (Validator.isNotNull(name)) {
				sql = _customSQL.replaceKeywords(
					sql, "(LOWER(CommercePriceList.name)", StringPool.LIKE,
					true, keywords);
				sql = _customSQL.replaceAndOperator(sql, false);
			}
			else {
				sql = StringUtil.removeSubstring(
					sql,
					" AND (LOWER(CommercePriceList.name) LIKE ? " +
						"[$AND_OR_NULL_CHECK$])");
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				CommercePriceListImpl.TABLE_NAME, CommercePriceListImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(
				PortalUtil.getClassNameId(
					CommercePricingClass.class.getName()));
			queryPos.add(commercePricingClassId);

			if (Validator.isNotNull(name)) {
				queryPos.add(keywords, 2);
			}

			return (List<CommercePriceList>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<CommercePriceEntry> findByCPInstanceUuid(
		String cpInstanceUuid, int start, int end) {

		return findByCPInstanceUuid(cpInstanceUuid, start, end, false);
	}

	@Override
	public List<CommercePriceEntry> findByCPInstanceUuid(
		String cpInstanceUuid, int start, int end, boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_CPINSTANCE_UUID);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, CommercePriceList.class.getName(),
					"CommercePriceEntry.commercePriceListId", null, null,
					new long[] {0}, null);
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				CommercePriceEntryImpl.TABLE_NAME,
				CommercePriceEntryImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(cpInstanceUuid);

			return (List<CommercePriceEntry>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<CommercePriceList> findByCommerceChannelId(
		QueryDefinition<CommercePriceList> queryDefinition) {

		return doFindByPK(
			FIND_BY_COMMERCE_CHANNEL_ID,
			(Long)queryDefinition.getAttribute("groupId"),
			(String)queryDefinition.getAttribute("type"),
			(Long)queryDefinition.getAttribute("commerceChannelId"),
			queryDefinition.getStart(), queryDefinition.getEnd());
	}

	@Override
	public List<CommercePriceList> findByUnqualified(
		QueryDefinition<CommercePriceList> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_UNQUALIFIED);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				CommercePriceListImpl.TABLE_NAME, CommercePriceListImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			long groupId = (Long)queryDefinition.getAttribute("groupId");
			String type = (String)queryDefinition.getAttribute("type");

			queryPos.add(groupId);
			queryPos.add(type);

			return (List<CommercePriceList>)QueryUtil.list(
				sqlQuery, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<CommercePriceEntry> findByLowestPrice(
		QueryDefinition<CommercePriceList> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_LOWEST_PRICE);

			long[] commerceAccountGroupIds =
				(long[])queryDefinition.getAttribute("commerceAccountGroupIds");

			sql = replaceAccountGroupIds(sql, commerceAccountGroupIds);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				CommercePriceEntryImpl.TABLE_NAME,
				CommercePriceEntryImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			String cPInstanceUuid = (String)queryDefinition.getAttribute(
				"cPInstanceUuid");
			long groupId = (Long)queryDefinition.getAttribute("groupId");
			String type = (String)queryDefinition.getAttribute("type");
			long commerceAccountId = (Long)queryDefinition.getAttribute(
				"commerceAccountId");
			long commerceChannelId = (Long)queryDefinition.getAttribute(
				"commerceChannelId");

			queryPos.add(cPInstanceUuid);
			queryPos.add(groupId);
			queryPos.add(type);
			queryPos.add(commerceAccountId);
			queryPos.add(commerceChannelId);

			return (List<CommercePriceEntry>)QueryUtil.list(
				sqlQuery, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<CommercePriceList> doFindByExpirationDate(
		Date expirationDate,
		QueryDefinition<CommercePriceList> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), FIND_BY_EXPIRATION_DATE, queryDefinition,
				CommercePriceListImpl.TABLE_NAME);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				CommercePriceListImpl.TABLE_NAME, CommercePriceListImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (expirationDate != null) {
				queryPos.add(expirationDate);
			}

			queryPos.add(queryDefinition.getStatus());

			return (List<CommercePriceList>)QueryUtil.list(
				sqlQuery, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<CommercePriceList> doFindByPK(
		String queryName, long groupId, String type, long classPK, int start,
		int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), queryName);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				CommercePriceListImpl.TABLE_NAME, CommercePriceListImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(classPK);
			queryPos.add(groupId);
			queryPos.add(type);

			return (List<CommercePriceList>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<CommercePriceList> doFindByPK(
		String queryName, long groupId, String type, long classPK1,
		long classPK2, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), queryName);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				CommercePriceListImpl.TABLE_NAME, CommercePriceListImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(classPK1);
			queryPos.add(classPK2);
			queryPos.add(groupId);
			queryPos.add(type);

			return (List<CommercePriceList>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected String replaceAccountGroupIds(
		String sql, long[] commerceAccountGroupIds) {

		if (commerceAccountGroupIds.length == 0) {
			return StringUtil.replace(sql, "[$ACCOUNT_GROUP_IDS$]", "0");
		}

		StringBundler sb = new StringBundler(commerceAccountGroupIds.length);

		for (int i = 0; i < commerceAccountGroupIds.length; i++) {
			sb.append(commerceAccountGroupIds[i]);

			if (i != (commerceAccountGroupIds.length - 1)) {
				sb.append(", ");
			}
		}

		return StringUtil.replace(sql, "[$ACCOUNT_GROUP_IDS$]", sb.toString());
	}

	@ServiceReference(type = CustomSQL.class)
	private CustomSQL _customSQL;

}