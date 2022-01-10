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

package com.liferay.portal.language.override.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.language.override.exception.NoSuchPLOEntryException;
import com.liferay.portal.language.override.model.PLOEntry;
import com.liferay.portal.language.override.model.PLOEntryTable;
import com.liferay.portal.language.override.model.impl.PLOEntryImpl;
import com.liferay.portal.language.override.model.impl.PLOEntryModelImpl;
import com.liferay.portal.language.override.service.persistence.PLOEntryPersistence;
import com.liferay.portal.language.override.service.persistence.PLOEntryUtil;
import com.liferay.portal.language.override.service.persistence.impl.constants.PLOPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the plo entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Drew Brokke
 * @generated
 */
@Component(service = {PLOEntryPersistence.class, BasePersistence.class})
public class PLOEntryPersistenceImpl
	extends BasePersistenceImpl<PLOEntry> implements PLOEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>PLOEntryUtil</code> to access the plo entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		PLOEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the plo entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching plo entries
	 */
	@Override
	public List<PLOEntry> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the plo entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @return the range of matching plo entries
	 */
	@Override
	public List<PLOEntry> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the plo entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching plo entries
	 */
	@Override
	public List<PLOEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<PLOEntry> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the plo entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching plo entries
	 */
	@Override
	public List<PLOEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<PLOEntry> orderByComparator, boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCompanyId;
				finderArgs = new Object[] {companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCompanyId;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<PLOEntry> list = null;

		if (useFinderCache) {
			list = (List<PLOEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (PLOEntry ploEntry : list) {
					if (companyId != ploEntry.getCompanyId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_PLOENTRY_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(PLOEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<PLOEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first plo entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching plo entry
	 * @throws NoSuchPLOEntryException if a matching plo entry could not be found
	 */
	@Override
	public PLOEntry findByCompanyId_First(
			long companyId, OrderByComparator<PLOEntry> orderByComparator)
		throws NoSuchPLOEntryException {

		PLOEntry ploEntry = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (ploEntry != null) {
			return ploEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchPLOEntryException(sb.toString());
	}

	/**
	 * Returns the first plo entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching plo entry, or <code>null</code> if a matching plo entry could not be found
	 */
	@Override
	public PLOEntry fetchByCompanyId_First(
		long companyId, OrderByComparator<PLOEntry> orderByComparator) {

		List<PLOEntry> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last plo entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching plo entry
	 * @throws NoSuchPLOEntryException if a matching plo entry could not be found
	 */
	@Override
	public PLOEntry findByCompanyId_Last(
			long companyId, OrderByComparator<PLOEntry> orderByComparator)
		throws NoSuchPLOEntryException {

		PLOEntry ploEntry = fetchByCompanyId_Last(companyId, orderByComparator);

		if (ploEntry != null) {
			return ploEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchPLOEntryException(sb.toString());
	}

	/**
	 * Returns the last plo entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching plo entry, or <code>null</code> if a matching plo entry could not be found
	 */
	@Override
	public PLOEntry fetchByCompanyId_Last(
		long companyId, OrderByComparator<PLOEntry> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<PLOEntry> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the plo entries before and after the current plo entry in the ordered set where companyId = &#63;.
	 *
	 * @param ploEntryId the primary key of the current plo entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next plo entry
	 * @throws NoSuchPLOEntryException if a plo entry with the primary key could not be found
	 */
	@Override
	public PLOEntry[] findByCompanyId_PrevAndNext(
			long ploEntryId, long companyId,
			OrderByComparator<PLOEntry> orderByComparator)
		throws NoSuchPLOEntryException {

		PLOEntry ploEntry = findByPrimaryKey(ploEntryId);

		Session session = null;

		try {
			session = openSession();

			PLOEntry[] array = new PLOEntryImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, ploEntry, companyId, orderByComparator, true);

			array[1] = ploEntry;

			array[2] = getByCompanyId_PrevAndNext(
				session, ploEntry, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected PLOEntry getByCompanyId_PrevAndNext(
		Session session, PLOEntry ploEntry, long companyId,
		OrderByComparator<PLOEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_PLOENTRY_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(PLOEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ploEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<PLOEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the plo entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (PLOEntry ploEntry :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(ploEntry);
		}
	}

	/**
	 * Returns the number of plo entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching plo entries
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_PLOENTRY_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"ploEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByC_K;
	private FinderPath _finderPathWithoutPaginationFindByC_K;
	private FinderPath _finderPathCountByC_K;

	/**
	 * Returns all the plo entries where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the matching plo entries
	 */
	@Override
	public List<PLOEntry> findByC_K(long companyId, String key) {
		return findByC_K(
			companyId, key, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the plo entries where companyId = &#63; and key = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @return the range of matching plo entries
	 */
	@Override
	public List<PLOEntry> findByC_K(
		long companyId, String key, int start, int end) {

		return findByC_K(companyId, key, start, end, null);
	}

	/**
	 * Returns an ordered range of all the plo entries where companyId = &#63; and key = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching plo entries
	 */
	@Override
	public List<PLOEntry> findByC_K(
		long companyId, String key, int start, int end,
		OrderByComparator<PLOEntry> orderByComparator) {

		return findByC_K(companyId, key, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the plo entries where companyId = &#63; and key = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching plo entries
	 */
	@Override
	public List<PLOEntry> findByC_K(
		long companyId, String key, int start, int end,
		OrderByComparator<PLOEntry> orderByComparator, boolean useFinderCache) {

		key = Objects.toString(key, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_K;
				finderArgs = new Object[] {companyId, key};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_K;
			finderArgs = new Object[] {
				companyId, key, start, end, orderByComparator
			};
		}

		List<PLOEntry> list = null;

		if (useFinderCache) {
			list = (List<PLOEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (PLOEntry ploEntry : list) {
					if ((companyId != ploEntry.getCompanyId()) ||
						!key.equals(ploEntry.getKey())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_PLOENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_K_COMPANYID_2);

			boolean bindKey = false;

			if (key.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_K_KEY_3);
			}
			else {
				bindKey = true;

				sb.append(_FINDER_COLUMN_C_K_KEY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(PLOEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindKey) {
					queryPos.add(key);
				}

				list = (List<PLOEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first plo entry in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching plo entry
	 * @throws NoSuchPLOEntryException if a matching plo entry could not be found
	 */
	@Override
	public PLOEntry findByC_K_First(
			long companyId, String key,
			OrderByComparator<PLOEntry> orderByComparator)
		throws NoSuchPLOEntryException {

		PLOEntry ploEntry = fetchByC_K_First(companyId, key, orderByComparator);

		if (ploEntry != null) {
			return ploEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", key=");
		sb.append(key);

		sb.append("}");

		throw new NoSuchPLOEntryException(sb.toString());
	}

	/**
	 * Returns the first plo entry in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching plo entry, or <code>null</code> if a matching plo entry could not be found
	 */
	@Override
	public PLOEntry fetchByC_K_First(
		long companyId, String key,
		OrderByComparator<PLOEntry> orderByComparator) {

		List<PLOEntry> list = findByC_K(
			companyId, key, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last plo entry in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching plo entry
	 * @throws NoSuchPLOEntryException if a matching plo entry could not be found
	 */
	@Override
	public PLOEntry findByC_K_Last(
			long companyId, String key,
			OrderByComparator<PLOEntry> orderByComparator)
		throws NoSuchPLOEntryException {

		PLOEntry ploEntry = fetchByC_K_Last(companyId, key, orderByComparator);

		if (ploEntry != null) {
			return ploEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", key=");
		sb.append(key);

		sb.append("}");

		throw new NoSuchPLOEntryException(sb.toString());
	}

	/**
	 * Returns the last plo entry in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching plo entry, or <code>null</code> if a matching plo entry could not be found
	 */
	@Override
	public PLOEntry fetchByC_K_Last(
		long companyId, String key,
		OrderByComparator<PLOEntry> orderByComparator) {

		int count = countByC_K(companyId, key);

		if (count == 0) {
			return null;
		}

		List<PLOEntry> list = findByC_K(
			companyId, key, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the plo entries before and after the current plo entry in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param ploEntryId the primary key of the current plo entry
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next plo entry
	 * @throws NoSuchPLOEntryException if a plo entry with the primary key could not be found
	 */
	@Override
	public PLOEntry[] findByC_K_PrevAndNext(
			long ploEntryId, long companyId, String key,
			OrderByComparator<PLOEntry> orderByComparator)
		throws NoSuchPLOEntryException {

		key = Objects.toString(key, "");

		PLOEntry ploEntry = findByPrimaryKey(ploEntryId);

		Session session = null;

		try {
			session = openSession();

			PLOEntry[] array = new PLOEntryImpl[3];

			array[0] = getByC_K_PrevAndNext(
				session, ploEntry, companyId, key, orderByComparator, true);

			array[1] = ploEntry;

			array[2] = getByC_K_PrevAndNext(
				session, ploEntry, companyId, key, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected PLOEntry getByC_K_PrevAndNext(
		Session session, PLOEntry ploEntry, long companyId, String key,
		OrderByComparator<PLOEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_PLOENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_K_COMPANYID_2);

		boolean bindKey = false;

		if (key.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_K_KEY_3);
		}
		else {
			bindKey = true;

			sb.append(_FINDER_COLUMN_C_K_KEY_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(PLOEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (bindKey) {
			queryPos.add(key);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ploEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<PLOEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the plo entries where companyId = &#63; and key = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 */
	@Override
	public void removeByC_K(long companyId, String key) {
		for (PLOEntry ploEntry :
				findByC_K(
					companyId, key, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ploEntry);
		}
	}

	/**
	 * Returns the number of plo entries where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the number of matching plo entries
	 */
	@Override
	public int countByC_K(long companyId, String key) {
		key = Objects.toString(key, "");

		FinderPath finderPath = _finderPathCountByC_K;

		Object[] finderArgs = new Object[] {companyId, key};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_PLOENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_K_COMPANYID_2);

			boolean bindKey = false;

			if (key.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_K_KEY_3);
			}
			else {
				bindKey = true;

				sb.append(_FINDER_COLUMN_C_K_KEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindKey) {
					queryPos.add(key);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_K_COMPANYID_2 =
		"ploEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_K_KEY_2 = "ploEntry.key = ?";

	private static final String _FINDER_COLUMN_C_K_KEY_3 =
		"(ploEntry.key IS NULL OR ploEntry.key = '')";

	private FinderPath _finderPathWithPaginationFindByC_L;
	private FinderPath _finderPathWithoutPaginationFindByC_L;
	private FinderPath _finderPathCountByC_L;

	/**
	 * Returns all the plo entries where companyId = &#63; and languageId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @return the matching plo entries
	 */
	@Override
	public List<PLOEntry> findByC_L(long companyId, String languageId) {
		return findByC_L(
			companyId, languageId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the plo entries where companyId = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @return the range of matching plo entries
	 */
	@Override
	public List<PLOEntry> findByC_L(
		long companyId, String languageId, int start, int end) {

		return findByC_L(companyId, languageId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the plo entries where companyId = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching plo entries
	 */
	@Override
	public List<PLOEntry> findByC_L(
		long companyId, String languageId, int start, int end,
		OrderByComparator<PLOEntry> orderByComparator) {

		return findByC_L(
			companyId, languageId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the plo entries where companyId = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching plo entries
	 */
	@Override
	public List<PLOEntry> findByC_L(
		long companyId, String languageId, int start, int end,
		OrderByComparator<PLOEntry> orderByComparator, boolean useFinderCache) {

		languageId = Objects.toString(languageId, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_L;
				finderArgs = new Object[] {companyId, languageId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_L;
			finderArgs = new Object[] {
				companyId, languageId, start, end, orderByComparator
			};
		}

		List<PLOEntry> list = null;

		if (useFinderCache) {
			list = (List<PLOEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (PLOEntry ploEntry : list) {
					if ((companyId != ploEntry.getCompanyId()) ||
						!languageId.equals(ploEntry.getLanguageId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_PLOENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_L_COMPANYID_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(_FINDER_COLUMN_C_L_LANGUAGEID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(PLOEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindLanguageId) {
					queryPos.add(languageId);
				}

				list = (List<PLOEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first plo entry in the ordered set where companyId = &#63; and languageId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching plo entry
	 * @throws NoSuchPLOEntryException if a matching plo entry could not be found
	 */
	@Override
	public PLOEntry findByC_L_First(
			long companyId, String languageId,
			OrderByComparator<PLOEntry> orderByComparator)
		throws NoSuchPLOEntryException {

		PLOEntry ploEntry = fetchByC_L_First(
			companyId, languageId, orderByComparator);

		if (ploEntry != null) {
			return ploEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", languageId=");
		sb.append(languageId);

		sb.append("}");

		throw new NoSuchPLOEntryException(sb.toString());
	}

	/**
	 * Returns the first plo entry in the ordered set where companyId = &#63; and languageId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching plo entry, or <code>null</code> if a matching plo entry could not be found
	 */
	@Override
	public PLOEntry fetchByC_L_First(
		long companyId, String languageId,
		OrderByComparator<PLOEntry> orderByComparator) {

		List<PLOEntry> list = findByC_L(
			companyId, languageId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last plo entry in the ordered set where companyId = &#63; and languageId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching plo entry
	 * @throws NoSuchPLOEntryException if a matching plo entry could not be found
	 */
	@Override
	public PLOEntry findByC_L_Last(
			long companyId, String languageId,
			OrderByComparator<PLOEntry> orderByComparator)
		throws NoSuchPLOEntryException {

		PLOEntry ploEntry = fetchByC_L_Last(
			companyId, languageId, orderByComparator);

		if (ploEntry != null) {
			return ploEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", languageId=");
		sb.append(languageId);

		sb.append("}");

		throw new NoSuchPLOEntryException(sb.toString());
	}

	/**
	 * Returns the last plo entry in the ordered set where companyId = &#63; and languageId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching plo entry, or <code>null</code> if a matching plo entry could not be found
	 */
	@Override
	public PLOEntry fetchByC_L_Last(
		long companyId, String languageId,
		OrderByComparator<PLOEntry> orderByComparator) {

		int count = countByC_L(companyId, languageId);

		if (count == 0) {
			return null;
		}

		List<PLOEntry> list = findByC_L(
			companyId, languageId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the plo entries before and after the current plo entry in the ordered set where companyId = &#63; and languageId = &#63;.
	 *
	 * @param ploEntryId the primary key of the current plo entry
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next plo entry
	 * @throws NoSuchPLOEntryException if a plo entry with the primary key could not be found
	 */
	@Override
	public PLOEntry[] findByC_L_PrevAndNext(
			long ploEntryId, long companyId, String languageId,
			OrderByComparator<PLOEntry> orderByComparator)
		throws NoSuchPLOEntryException {

		languageId = Objects.toString(languageId, "");

		PLOEntry ploEntry = findByPrimaryKey(ploEntryId);

		Session session = null;

		try {
			session = openSession();

			PLOEntry[] array = new PLOEntryImpl[3];

			array[0] = getByC_L_PrevAndNext(
				session, ploEntry, companyId, languageId, orderByComparator,
				true);

			array[1] = ploEntry;

			array[2] = getByC_L_PrevAndNext(
				session, ploEntry, companyId, languageId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected PLOEntry getByC_L_PrevAndNext(
		Session session, PLOEntry ploEntry, long companyId, String languageId,
		OrderByComparator<PLOEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_PLOENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_L_COMPANYID_2);

		boolean bindLanguageId = false;

		if (languageId.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_L_LANGUAGEID_3);
		}
		else {
			bindLanguageId = true;

			sb.append(_FINDER_COLUMN_C_L_LANGUAGEID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(PLOEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (bindLanguageId) {
			queryPos.add(languageId);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ploEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<PLOEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the plo entries where companyId = &#63; and languageId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 */
	@Override
	public void removeByC_L(long companyId, String languageId) {
		for (PLOEntry ploEntry :
				findByC_L(
					companyId, languageId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ploEntry);
		}
	}

	/**
	 * Returns the number of plo entries where companyId = &#63; and languageId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @return the number of matching plo entries
	 */
	@Override
	public int countByC_L(long companyId, String languageId) {
		languageId = Objects.toString(languageId, "");

		FinderPath finderPath = _finderPathCountByC_L;

		Object[] finderArgs = new Object[] {companyId, languageId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_PLOENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_L_COMPANYID_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(_FINDER_COLUMN_C_L_LANGUAGEID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindLanguageId) {
					queryPos.add(languageId);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_L_COMPANYID_2 =
		"ploEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_L_LANGUAGEID_2 =
		"ploEntry.languageId = ?";

	private static final String _FINDER_COLUMN_C_L_LANGUAGEID_3 =
		"(ploEntry.languageId IS NULL OR ploEntry.languageId = '')";

	private FinderPath _finderPathFetchByC_K_L;
	private FinderPath _finderPathCountByC_K_L;

	/**
	 * Returns the plo entry where companyId = &#63; and key = &#63; and languageId = &#63; or throws a <code>NoSuchPLOEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param languageId the language ID
	 * @return the matching plo entry
	 * @throws NoSuchPLOEntryException if a matching plo entry could not be found
	 */
	@Override
	public PLOEntry findByC_K_L(long companyId, String key, String languageId)
		throws NoSuchPLOEntryException {

		PLOEntry ploEntry = fetchByC_K_L(companyId, key, languageId);

		if (ploEntry == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", key=");
			sb.append(key);

			sb.append(", languageId=");
			sb.append(languageId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchPLOEntryException(sb.toString());
		}

		return ploEntry;
	}

	/**
	 * Returns the plo entry where companyId = &#63; and key = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param languageId the language ID
	 * @return the matching plo entry, or <code>null</code> if a matching plo entry could not be found
	 */
	@Override
	public PLOEntry fetchByC_K_L(
		long companyId, String key, String languageId) {

		return fetchByC_K_L(companyId, key, languageId, true);
	}

	/**
	 * Returns the plo entry where companyId = &#63; and key = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching plo entry, or <code>null</code> if a matching plo entry could not be found
	 */
	@Override
	public PLOEntry fetchByC_K_L(
		long companyId, String key, String languageId, boolean useFinderCache) {

		key = Objects.toString(key, "");
		languageId = Objects.toString(languageId, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, key, languageId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_K_L, finderArgs);
		}

		if (result instanceof PLOEntry) {
			PLOEntry ploEntry = (PLOEntry)result;

			if ((companyId != ploEntry.getCompanyId()) ||
				!Objects.equals(key, ploEntry.getKey()) ||
				!Objects.equals(languageId, ploEntry.getLanguageId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_PLOENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_K_L_COMPANYID_2);

			boolean bindKey = false;

			if (key.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_K_L_KEY_3);
			}
			else {
				bindKey = true;

				sb.append(_FINDER_COLUMN_C_K_L_KEY_2);
			}

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_K_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(_FINDER_COLUMN_C_K_L_LANGUAGEID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindKey) {
					queryPos.add(key);
				}

				if (bindLanguageId) {
					queryPos.add(languageId);
				}

				List<PLOEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_K_L, finderArgs, list);
					}
				}
				else {
					PLOEntry ploEntry = list.get(0);

					result = ploEntry;

					cacheResult(ploEntry);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (PLOEntry)result;
		}
	}

	/**
	 * Removes the plo entry where companyId = &#63; and key = &#63; and languageId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param languageId the language ID
	 * @return the plo entry that was removed
	 */
	@Override
	public PLOEntry removeByC_K_L(long companyId, String key, String languageId)
		throws NoSuchPLOEntryException {

		PLOEntry ploEntry = findByC_K_L(companyId, key, languageId);

		return remove(ploEntry);
	}

	/**
	 * Returns the number of plo entries where companyId = &#63; and key = &#63; and languageId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param languageId the language ID
	 * @return the number of matching plo entries
	 */
	@Override
	public int countByC_K_L(long companyId, String key, String languageId) {
		key = Objects.toString(key, "");
		languageId = Objects.toString(languageId, "");

		FinderPath finderPath = _finderPathCountByC_K_L;

		Object[] finderArgs = new Object[] {companyId, key, languageId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_PLOENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_K_L_COMPANYID_2);

			boolean bindKey = false;

			if (key.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_K_L_KEY_3);
			}
			else {
				bindKey = true;

				sb.append(_FINDER_COLUMN_C_K_L_KEY_2);
			}

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_K_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(_FINDER_COLUMN_C_K_L_LANGUAGEID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindKey) {
					queryPos.add(key);
				}

				if (bindLanguageId) {
					queryPos.add(languageId);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_K_L_COMPANYID_2 =
		"ploEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_K_L_KEY_2 =
		"ploEntry.key = ? AND ";

	private static final String _FINDER_COLUMN_C_K_L_KEY_3 =
		"(ploEntry.key IS NULL OR ploEntry.key = '') AND ";

	private static final String _FINDER_COLUMN_C_K_L_LANGUAGEID_2 =
		"ploEntry.languageId = ?";

	private static final String _FINDER_COLUMN_C_K_L_LANGUAGEID_3 =
		"(ploEntry.languageId IS NULL OR ploEntry.languageId = '')";

	public PLOEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("key", "key_");

		setDBColumnNames(dbColumnNames);

		setModelClass(PLOEntry.class);

		setModelImplClass(PLOEntryImpl.class);
		setModelPKClass(long.class);

		setTable(PLOEntryTable.INSTANCE);
	}

	/**
	 * Caches the plo entry in the entity cache if it is enabled.
	 *
	 * @param ploEntry the plo entry
	 */
	@Override
	public void cacheResult(PLOEntry ploEntry) {
		entityCache.putResult(
			PLOEntryImpl.class, ploEntry.getPrimaryKey(), ploEntry);

		finderCache.putResult(
			_finderPathFetchByC_K_L,
			new Object[] {
				ploEntry.getCompanyId(), ploEntry.getKey(),
				ploEntry.getLanguageId()
			},
			ploEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the plo entries in the entity cache if it is enabled.
	 *
	 * @param ploEntries the plo entries
	 */
	@Override
	public void cacheResult(List<PLOEntry> ploEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (ploEntries.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (PLOEntry ploEntry : ploEntries) {
			if (entityCache.getResult(
					PLOEntryImpl.class, ploEntry.getPrimaryKey()) == null) {

				cacheResult(ploEntry);
			}
		}
	}

	/**
	 * Clears the cache for all plo entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(PLOEntryImpl.class);

		finderCache.clearCache(PLOEntryImpl.class);
	}

	/**
	 * Clears the cache for the plo entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PLOEntry ploEntry) {
		entityCache.removeResult(PLOEntryImpl.class, ploEntry);
	}

	@Override
	public void clearCache(List<PLOEntry> ploEntries) {
		for (PLOEntry ploEntry : ploEntries) {
			entityCache.removeResult(PLOEntryImpl.class, ploEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(PLOEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(PLOEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		PLOEntryModelImpl ploEntryModelImpl) {

		Object[] args = new Object[] {
			ploEntryModelImpl.getCompanyId(), ploEntryModelImpl.getKey(),
			ploEntryModelImpl.getLanguageId()
		};

		finderCache.putResult(_finderPathCountByC_K_L, args, Long.valueOf(1));
		finderCache.putResult(_finderPathFetchByC_K_L, args, ploEntryModelImpl);
	}

	/**
	 * Creates a new plo entry with the primary key. Does not add the plo entry to the database.
	 *
	 * @param ploEntryId the primary key for the new plo entry
	 * @return the new plo entry
	 */
	@Override
	public PLOEntry create(long ploEntryId) {
		PLOEntry ploEntry = new PLOEntryImpl();

		ploEntry.setNew(true);
		ploEntry.setPrimaryKey(ploEntryId);

		ploEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return ploEntry;
	}

	/**
	 * Removes the plo entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ploEntryId the primary key of the plo entry
	 * @return the plo entry that was removed
	 * @throws NoSuchPLOEntryException if a plo entry with the primary key could not be found
	 */
	@Override
	public PLOEntry remove(long ploEntryId) throws NoSuchPLOEntryException {
		return remove((Serializable)ploEntryId);
	}

	/**
	 * Removes the plo entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the plo entry
	 * @return the plo entry that was removed
	 * @throws NoSuchPLOEntryException if a plo entry with the primary key could not be found
	 */
	@Override
	public PLOEntry remove(Serializable primaryKey)
		throws NoSuchPLOEntryException {

		Session session = null;

		try {
			session = openSession();

			PLOEntry ploEntry = (PLOEntry)session.get(
				PLOEntryImpl.class, primaryKey);

			if (ploEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPLOEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ploEntry);
		}
		catch (NoSuchPLOEntryException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected PLOEntry removeImpl(PLOEntry ploEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ploEntry)) {
				ploEntry = (PLOEntry)session.get(
					PLOEntryImpl.class, ploEntry.getPrimaryKeyObj());
			}

			if (ploEntry != null) {
				session.delete(ploEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ploEntry != null) {
			clearCache(ploEntry);
		}

		return ploEntry;
	}

	@Override
	public PLOEntry updateImpl(PLOEntry ploEntry) {
		boolean isNew = ploEntry.isNew();

		if (!(ploEntry instanceof PLOEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ploEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(ploEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ploEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom PLOEntry implementation " +
					ploEntry.getClass());
		}

		PLOEntryModelImpl ploEntryModelImpl = (PLOEntryModelImpl)ploEntry;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (ploEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				ploEntry.setCreateDate(date);
			}
			else {
				ploEntry.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!ploEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				ploEntry.setModifiedDate(date);
			}
			else {
				ploEntry.setModifiedDate(serviceContext.getModifiedDate(date));
			}
		}

		long userId = GetterUtil.getLong(PrincipalThreadLocal.getName());

		if (userId > 0) {
			long companyId = ploEntry.getCompanyId();

			long groupId = 0;

			long ploEntryId = 0;

			if (!isNew) {
				ploEntryId = ploEntry.getPrimaryKey();
			}

			try {
				ploEntry.setValue(
					SanitizerUtil.sanitize(
						companyId, groupId, userId, PLOEntry.class.getName(),
						ploEntryId, ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
						ploEntry.getValue(), null));
			}
			catch (SanitizerException sanitizerException) {
				throw new SystemException(sanitizerException);
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(ploEntry);
			}
			else {
				ploEntry = (PLOEntry)session.merge(ploEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			PLOEntryImpl.class, ploEntryModelImpl, false, true);

		cacheUniqueFindersCache(ploEntryModelImpl);

		if (isNew) {
			ploEntry.setNew(false);
		}

		ploEntry.resetOriginalValues();

		return ploEntry;
	}

	/**
	 * Returns the plo entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the plo entry
	 * @return the plo entry
	 * @throws NoSuchPLOEntryException if a plo entry with the primary key could not be found
	 */
	@Override
	public PLOEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPLOEntryException {

		PLOEntry ploEntry = fetchByPrimaryKey(primaryKey);

		if (ploEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPLOEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ploEntry;
	}

	/**
	 * Returns the plo entry with the primary key or throws a <code>NoSuchPLOEntryException</code> if it could not be found.
	 *
	 * @param ploEntryId the primary key of the plo entry
	 * @return the plo entry
	 * @throws NoSuchPLOEntryException if a plo entry with the primary key could not be found
	 */
	@Override
	public PLOEntry findByPrimaryKey(long ploEntryId)
		throws NoSuchPLOEntryException {

		return findByPrimaryKey((Serializable)ploEntryId);
	}

	/**
	 * Returns the plo entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ploEntryId the primary key of the plo entry
	 * @return the plo entry, or <code>null</code> if a plo entry with the primary key could not be found
	 */
	@Override
	public PLOEntry fetchByPrimaryKey(long ploEntryId) {
		return fetchByPrimaryKey((Serializable)ploEntryId);
	}

	/**
	 * Returns all the plo entries.
	 *
	 * @return the plo entries
	 */
	@Override
	public List<PLOEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the plo entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @return the range of plo entries
	 */
	@Override
	public List<PLOEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the plo entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of plo entries
	 */
	@Override
	public List<PLOEntry> findAll(
		int start, int end, OrderByComparator<PLOEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the plo entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of plo entries
	 */
	@Override
	public List<PLOEntry> findAll(
		int start, int end, OrderByComparator<PLOEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<PLOEntry> list = null;

		if (useFinderCache) {
			list = (List<PLOEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_PLOENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_PLOENTRY;

				sql = sql.concat(PLOEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<PLOEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the plo entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (PLOEntry ploEntry : findAll()) {
			remove(ploEntry);
		}
	}

	/**
	 * Returns the number of plo entries.
	 *
	 * @return the number of plo entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_PLOENTRY);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "ploEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_PLOENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return PLOEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the plo entry persistence.
	 */
	@Activate
	public void activate() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId"}, true);

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			true);

		_finderPathCountByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			false);

		_finderPathWithPaginationFindByC_K = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_K",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "key_"}, true);

		_finderPathWithoutPaginationFindByC_K = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_K",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "key_"}, true);

		_finderPathCountByC_K = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_K",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "key_"}, false);

		_finderPathWithPaginationFindByC_L = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_L",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "languageId"}, true);

		_finderPathWithoutPaginationFindByC_L = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_L",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "languageId"}, true);

		_finderPathCountByC_L = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_L",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "languageId"}, false);

		_finderPathFetchByC_K_L = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_K_L",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"companyId", "key_", "languageId"}, true);

		_finderPathCountByC_K_L = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_K_L",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"companyId", "key_", "languageId"}, false);

		_setPLOEntryUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setPLOEntryUtilPersistence(null);

		entityCache.removeCache(PLOEntryImpl.class.getName());
	}

	private void _setPLOEntryUtilPersistence(
		PLOEntryPersistence ploEntryPersistence) {

		try {
			Field field = PLOEntryUtil.class.getDeclaredField("_persistence");

			field.setAccessible(true);

			field.set(null, ploEntryPersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@Override
	@Reference(
		target = PLOPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = PLOPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = PLOPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_PLOENTRY =
		"SELECT ploEntry FROM PLOEntry ploEntry";

	private static final String _SQL_SELECT_PLOENTRY_WHERE =
		"SELECT ploEntry FROM PLOEntry ploEntry WHERE ";

	private static final String _SQL_COUNT_PLOENTRY =
		"SELECT COUNT(ploEntry) FROM PLOEntry ploEntry";

	private static final String _SQL_COUNT_PLOENTRY_WHERE =
		"SELECT COUNT(ploEntry) FROM PLOEntry ploEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "ploEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No PLOEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No PLOEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		PLOEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"key"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private PLOEntryModelArgumentsResolver _ploEntryModelArgumentsResolver;

}