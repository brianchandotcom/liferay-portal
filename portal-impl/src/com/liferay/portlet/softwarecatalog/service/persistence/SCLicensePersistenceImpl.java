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

package com.liferay.portlet.softwarecatalog.service.persistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.service.persistence.impl.TableMapper;
import com.liferay.portal.service.persistence.impl.TableMapperFactory;

import com.liferay.portlet.softwarecatalog.NoSuchLicenseException;
import com.liferay.portlet.softwarecatalog.model.SCLicense;
import com.liferay.portlet.softwarecatalog.model.impl.SCLicenseImpl;
import com.liferay.portlet.softwarecatalog.model.impl.SCLicenseModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the s c license service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SCLicensePersistence
 * @see SCLicenseUtil
 * @generated
 */
public class SCLicensePersistenceImpl extends BasePersistenceImpl<SCLicense>
	implements SCLicensePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SCLicenseUtil} to access the s c license persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SCLicenseImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
			SCLicenseModelImpl.FINDER_CACHE_ENABLED, SCLicenseImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
			SCLicenseModelImpl.FINDER_CACHE_ENABLED, SCLicenseImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
			SCLicenseModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ACTIVE = new FinderPath(SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
			SCLicenseModelImpl.FINDER_CACHE_ENABLED, SCLicenseImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByActive",
			new String[] {
				Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTIVE =
		new FinderPath(SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
			SCLicenseModelImpl.FINDER_CACHE_ENABLED, SCLicenseImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByActive",
			new String[] { Boolean.class.getName() },
			SCLicenseModelImpl.ACTIVE_COLUMN_BITMASK |
			SCLicenseModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ACTIVE = new FinderPath(SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
			SCLicenseModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByActive",
			new String[] { Boolean.class.getName() });

	/**
	 * Returns all the s c licenses where active = &#63;.
	 *
	 * @param active the active
	 * @return the matching s c licenses
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SCLicense> findByActive(boolean active)
		throws SystemException {
		return findByActive(active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c licenses where active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param active the active
	 * @param start the lower bound of the range of s c licenses
	 * @param end the upper bound of the range of s c licenses (not inclusive)
	 * @return the range of matching s c licenses
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SCLicense> findByActive(boolean active, int start, int end)
		throws SystemException {
		return findByActive(active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c licenses where active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param active the active
	 * @param start the lower bound of the range of s c licenses
	 * @param end the upper bound of the range of s c licenses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c licenses
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SCLicense> findByActive(boolean active, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTIVE;
			finderArgs = new Object[] { active };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ACTIVE;
			finderArgs = new Object[] { active, start, end, orderByComparator };
		}

		List<SCLicense> list = (List<SCLicense>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SCLicense scLicense : list) {
				if ((active != scLicense.getActive())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SCLICENSE_WHERE);

			query.append(_FINDER_COLUMN_ACTIVE_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SCLicenseModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

				if (!pagination) {
					list = (List<SCLicense>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<SCLicense>(list);
				}
				else {
					list = (List<SCLicense>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first s c license in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c license
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException if a matching s c license could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCLicense findByActive_First(boolean active,
		OrderByComparator orderByComparator)
		throws NoSuchLicenseException, SystemException {
		SCLicense scLicense = fetchByActive_First(active, orderByComparator);

		if (scLicense != null) {
			return scLicense;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("active=");
		msg.append(active);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLicenseException(msg.toString());
	}

	/**
	 * Returns the first s c license in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c license, or <code>null</code> if a matching s c license could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCLicense fetchByActive_First(boolean active,
		OrderByComparator orderByComparator) throws SystemException {
		List<SCLicense> list = findByActive(active, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last s c license in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c license
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException if a matching s c license could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCLicense findByActive_Last(boolean active,
		OrderByComparator orderByComparator)
		throws NoSuchLicenseException, SystemException {
		SCLicense scLicense = fetchByActive_Last(active, orderByComparator);

		if (scLicense != null) {
			return scLicense;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("active=");
		msg.append(active);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLicenseException(msg.toString());
	}

	/**
	 * Returns the last s c license in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c license, or <code>null</code> if a matching s c license could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCLicense fetchByActive_Last(boolean active,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByActive(active);

		if (count == 0) {
			return null;
		}

		List<SCLicense> list = findByActive(active, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the s c licenses before and after the current s c license in the ordered set where active = &#63;.
	 *
	 * @param licenseId the primary key of the current s c license
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c license
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException if a s c license with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCLicense[] findByActive_PrevAndNext(long licenseId, boolean active,
		OrderByComparator orderByComparator)
		throws NoSuchLicenseException, SystemException {
		SCLicense scLicense = findByPrimaryKey(licenseId);

		Session session = null;

		try {
			session = openSession();

			SCLicense[] array = new SCLicenseImpl[3];

			array[0] = getByActive_PrevAndNext(session, scLicense, active,
					orderByComparator, true);

			array[1] = scLicense;

			array[2] = getByActive_PrevAndNext(session, scLicense, active,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SCLicense getByActive_PrevAndNext(Session session,
		SCLicense scLicense, boolean active,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SCLICENSE_WHERE);

		query.append(_FINDER_COLUMN_ACTIVE_ACTIVE_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(SCLicenseModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(scLicense);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCLicense> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the s c licenses that the user has permission to view where active = &#63;.
	 *
	 * @param active the active
	 * @return the matching s c licenses that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SCLicense> filterFindByActive(boolean active)
		throws SystemException {
		return filterFindByActive(active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the s c licenses that the user has permission to view where active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param active the active
	 * @param start the lower bound of the range of s c licenses
	 * @param end the upper bound of the range of s c licenses (not inclusive)
	 * @return the range of matching s c licenses that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SCLicense> filterFindByActive(boolean active, int start, int end)
		throws SystemException {
		return filterFindByActive(active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c licenses that the user has permissions to view where active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param active the active
	 * @param start the lower bound of the range of s c licenses
	 * @param end the upper bound of the range of s c licenses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c licenses that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SCLicense> filterFindByActive(boolean active, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByActive(active, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCLICENSE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SCLICENSE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_ACTIVE_ACTIVE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCLICENSE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SCLicenseModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SCLicenseModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCLicense.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SCLicenseImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SCLicenseImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(active);

			return (List<SCLicense>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the s c licenses before and after the current s c license in the ordered set of s c licenses that the user has permission to view where active = &#63;.
	 *
	 * @param licenseId the primary key of the current s c license
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c license
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException if a s c license with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCLicense[] filterFindByActive_PrevAndNext(long licenseId,
		boolean active, OrderByComparator orderByComparator)
		throws NoSuchLicenseException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByActive_PrevAndNext(licenseId, active, orderByComparator);
		}

		SCLicense scLicense = findByPrimaryKey(licenseId);

		Session session = null;

		try {
			session = openSession();

			SCLicense[] array = new SCLicenseImpl[3];

			array[0] = filterGetByActive_PrevAndNext(session, scLicense,
					active, orderByComparator, true);

			array[1] = scLicense;

			array[2] = filterGetByActive_PrevAndNext(session, scLicense,
					active, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SCLicense filterGetByActive_PrevAndNext(Session session,
		SCLicense scLicense, boolean active,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCLICENSE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SCLICENSE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_ACTIVE_ACTIVE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCLICENSE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SCLicenseModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SCLicenseModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCLicense.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SCLicenseImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SCLicenseImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(scLicense);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCLicense> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the s c licenses where active = &#63; from the database.
	 *
	 * @param active the active
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByActive(boolean active) throws SystemException {
		for (SCLicense scLicense : findByActive(active, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(scLicense);
		}
	}

	/**
	 * Returns the number of s c licenses where active = &#63;.
	 *
	 * @param active the active
	 * @return the number of matching s c licenses
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByActive(boolean active) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ACTIVE;

		Object[] finderArgs = new Object[] { active };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SCLICENSE_WHERE);

			query.append(_FINDER_COLUMN_ACTIVE_ACTIVE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of s c licenses that the user has permission to view where active = &#63;.
	 *
	 * @param active the active
	 * @return the number of matching s c licenses that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int filterCountByActive(boolean active) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByActive(active);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_SCLICENSE_WHERE);

		query.append(_FINDER_COLUMN_ACTIVE_ACTIVE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCLicense.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(active);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_ACTIVE_ACTIVE_2 = "scLicense.active = ?";
	private static final String _FINDER_COLUMN_ACTIVE_ACTIVE_2_SQL = "scLicense.active_ = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_A_R = new FinderPath(SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
			SCLicenseModelImpl.FINDER_CACHE_ENABLED, SCLicenseImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByA_R",
			new String[] {
				Boolean.class.getName(), Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_A_R = new FinderPath(SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
			SCLicenseModelImpl.FINDER_CACHE_ENABLED, SCLicenseImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByA_R",
			new String[] { Boolean.class.getName(), Boolean.class.getName() },
			SCLicenseModelImpl.ACTIVE_COLUMN_BITMASK |
			SCLicenseModelImpl.RECOMMENDED_COLUMN_BITMASK |
			SCLicenseModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_A_R = new FinderPath(SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
			SCLicenseModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA_R",
			new String[] { Boolean.class.getName(), Boolean.class.getName() });

	/**
	 * Returns all the s c licenses where active = &#63; and recommended = &#63;.
	 *
	 * @param active the active
	 * @param recommended the recommended
	 * @return the matching s c licenses
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SCLicense> findByA_R(boolean active, boolean recommended)
		throws SystemException {
		return findByA_R(active, recommended, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c licenses where active = &#63; and recommended = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param active the active
	 * @param recommended the recommended
	 * @param start the lower bound of the range of s c licenses
	 * @param end the upper bound of the range of s c licenses (not inclusive)
	 * @return the range of matching s c licenses
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SCLicense> findByA_R(boolean active, boolean recommended,
		int start, int end) throws SystemException {
		return findByA_R(active, recommended, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c licenses where active = &#63; and recommended = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param active the active
	 * @param recommended the recommended
	 * @param start the lower bound of the range of s c licenses
	 * @param end the upper bound of the range of s c licenses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c licenses
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SCLicense> findByA_R(boolean active, boolean recommended,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_A_R;
			finderArgs = new Object[] { active, recommended };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_A_R;
			finderArgs = new Object[] {
					active, recommended,
					
					start, end, orderByComparator
				};
		}

		List<SCLicense> list = (List<SCLicense>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SCLicense scLicense : list) {
				if ((active != scLicense.getActive()) ||
						(recommended != scLicense.getRecommended())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_SCLICENSE_WHERE);

			query.append(_FINDER_COLUMN_A_R_ACTIVE_2);

			query.append(_FINDER_COLUMN_A_R_RECOMMENDED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SCLicenseModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

				qPos.add(recommended);

				if (!pagination) {
					list = (List<SCLicense>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<SCLicense>(list);
				}
				else {
					list = (List<SCLicense>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first s c license in the ordered set where active = &#63; and recommended = &#63;.
	 *
	 * @param active the active
	 * @param recommended the recommended
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c license
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException if a matching s c license could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCLicense findByA_R_First(boolean active, boolean recommended,
		OrderByComparator orderByComparator)
		throws NoSuchLicenseException, SystemException {
		SCLicense scLicense = fetchByA_R_First(active, recommended,
				orderByComparator);

		if (scLicense != null) {
			return scLicense;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("active=");
		msg.append(active);

		msg.append(", recommended=");
		msg.append(recommended);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLicenseException(msg.toString());
	}

	/**
	 * Returns the first s c license in the ordered set where active = &#63; and recommended = &#63;.
	 *
	 * @param active the active
	 * @param recommended the recommended
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c license, or <code>null</code> if a matching s c license could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCLicense fetchByA_R_First(boolean active, boolean recommended,
		OrderByComparator orderByComparator) throws SystemException {
		List<SCLicense> list = findByA_R(active, recommended, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last s c license in the ordered set where active = &#63; and recommended = &#63;.
	 *
	 * @param active the active
	 * @param recommended the recommended
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c license
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException if a matching s c license could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCLicense findByA_R_Last(boolean active, boolean recommended,
		OrderByComparator orderByComparator)
		throws NoSuchLicenseException, SystemException {
		SCLicense scLicense = fetchByA_R_Last(active, recommended,
				orderByComparator);

		if (scLicense != null) {
			return scLicense;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("active=");
		msg.append(active);

		msg.append(", recommended=");
		msg.append(recommended);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLicenseException(msg.toString());
	}

	/**
	 * Returns the last s c license in the ordered set where active = &#63; and recommended = &#63;.
	 *
	 * @param active the active
	 * @param recommended the recommended
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c license, or <code>null</code> if a matching s c license could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCLicense fetchByA_R_Last(boolean active, boolean recommended,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByA_R(active, recommended);

		if (count == 0) {
			return null;
		}

		List<SCLicense> list = findByA_R(active, recommended, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the s c licenses before and after the current s c license in the ordered set where active = &#63; and recommended = &#63;.
	 *
	 * @param licenseId the primary key of the current s c license
	 * @param active the active
	 * @param recommended the recommended
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c license
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException if a s c license with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCLicense[] findByA_R_PrevAndNext(long licenseId, boolean active,
		boolean recommended, OrderByComparator orderByComparator)
		throws NoSuchLicenseException, SystemException {
		SCLicense scLicense = findByPrimaryKey(licenseId);

		Session session = null;

		try {
			session = openSession();

			SCLicense[] array = new SCLicenseImpl[3];

			array[0] = getByA_R_PrevAndNext(session, scLicense, active,
					recommended, orderByComparator, true);

			array[1] = scLicense;

			array[2] = getByA_R_PrevAndNext(session, scLicense, active,
					recommended, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SCLicense getByA_R_PrevAndNext(Session session,
		SCLicense scLicense, boolean active, boolean recommended,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SCLICENSE_WHERE);

		query.append(_FINDER_COLUMN_A_R_ACTIVE_2);

		query.append(_FINDER_COLUMN_A_R_RECOMMENDED_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(SCLicenseModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(active);

		qPos.add(recommended);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(scLicense);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCLicense> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the s c licenses that the user has permission to view where active = &#63; and recommended = &#63;.
	 *
	 * @param active the active
	 * @param recommended the recommended
	 * @return the matching s c licenses that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SCLicense> filterFindByA_R(boolean active, boolean recommended)
		throws SystemException {
		return filterFindByA_R(active, recommended, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c licenses that the user has permission to view where active = &#63; and recommended = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param active the active
	 * @param recommended the recommended
	 * @param start the lower bound of the range of s c licenses
	 * @param end the upper bound of the range of s c licenses (not inclusive)
	 * @return the range of matching s c licenses that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SCLicense> filterFindByA_R(boolean active, boolean recommended,
		int start, int end) throws SystemException {
		return filterFindByA_R(active, recommended, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c licenses that the user has permissions to view where active = &#63; and recommended = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param active the active
	 * @param recommended the recommended
	 * @param start the lower bound of the range of s c licenses
	 * @param end the upper bound of the range of s c licenses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c licenses that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SCLicense> filterFindByA_R(boolean active, boolean recommended,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByA_R(active, recommended, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCLICENSE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SCLICENSE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_A_R_ACTIVE_2_SQL);

		query.append(_FINDER_COLUMN_A_R_RECOMMENDED_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCLICENSE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SCLicenseModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SCLicenseModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCLicense.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SCLicenseImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SCLicenseImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(active);

			qPos.add(recommended);

			return (List<SCLicense>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the s c licenses before and after the current s c license in the ordered set of s c licenses that the user has permission to view where active = &#63; and recommended = &#63;.
	 *
	 * @param licenseId the primary key of the current s c license
	 * @param active the active
	 * @param recommended the recommended
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c license
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException if a s c license with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCLicense[] filterFindByA_R_PrevAndNext(long licenseId,
		boolean active, boolean recommended, OrderByComparator orderByComparator)
		throws NoSuchLicenseException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByA_R_PrevAndNext(licenseId, active, recommended,
				orderByComparator);
		}

		SCLicense scLicense = findByPrimaryKey(licenseId);

		Session session = null;

		try {
			session = openSession();

			SCLicense[] array = new SCLicenseImpl[3];

			array[0] = filterGetByA_R_PrevAndNext(session, scLicense, active,
					recommended, orderByComparator, true);

			array[1] = scLicense;

			array[2] = filterGetByA_R_PrevAndNext(session, scLicense, active,
					recommended, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SCLicense filterGetByA_R_PrevAndNext(Session session,
		SCLicense scLicense, boolean active, boolean recommended,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCLICENSE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SCLICENSE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_A_R_ACTIVE_2_SQL);

		query.append(_FINDER_COLUMN_A_R_RECOMMENDED_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCLICENSE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SCLicenseModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SCLicenseModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCLicense.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SCLicenseImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SCLicenseImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(active);

		qPos.add(recommended);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(scLicense);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCLicense> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the s c licenses where active = &#63; and recommended = &#63; from the database.
	 *
	 * @param active the active
	 * @param recommended the recommended
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByA_R(boolean active, boolean recommended)
		throws SystemException {
		for (SCLicense scLicense : findByA_R(active, recommended,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(scLicense);
		}
	}

	/**
	 * Returns the number of s c licenses where active = &#63; and recommended = &#63;.
	 *
	 * @param active the active
	 * @param recommended the recommended
	 * @return the number of matching s c licenses
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByA_R(boolean active, boolean recommended)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_A_R;

		Object[] finderArgs = new Object[] { active, recommended };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SCLICENSE_WHERE);

			query.append(_FINDER_COLUMN_A_R_ACTIVE_2);

			query.append(_FINDER_COLUMN_A_R_RECOMMENDED_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

				qPos.add(recommended);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of s c licenses that the user has permission to view where active = &#63; and recommended = &#63;.
	 *
	 * @param active the active
	 * @param recommended the recommended
	 * @return the number of matching s c licenses that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int filterCountByA_R(boolean active, boolean recommended)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByA_R(active, recommended);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_SCLICENSE_WHERE);

		query.append(_FINDER_COLUMN_A_R_ACTIVE_2_SQL);

		query.append(_FINDER_COLUMN_A_R_RECOMMENDED_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCLicense.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(active);

			qPos.add(recommended);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_A_R_ACTIVE_2 = "scLicense.active = ? AND ";
	private static final String _FINDER_COLUMN_A_R_ACTIVE_2_SQL = "scLicense.active_ = ? AND ";
	private static final String _FINDER_COLUMN_A_R_RECOMMENDED_2 = "scLicense.recommended = ?";

	public SCLicensePersistenceImpl() {
		setModelClass(SCLicense.class);
	}

	/**
	 * Caches the s c license in the entity cache if it is enabled.
	 *
	 * @param scLicense the s c license
	 */
	@Override
	public void cacheResult(SCLicense scLicense) {
		EntityCacheUtil.putResult(SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
			SCLicenseImpl.class, scLicense.getPrimaryKey(), scLicense);

		scLicense.resetOriginalValues();
	}

	/**
	 * Caches the s c licenses in the entity cache if it is enabled.
	 *
	 * @param scLicenses the s c licenses
	 */
	@Override
	public void cacheResult(List<SCLicense> scLicenses) {
		for (SCLicense scLicense : scLicenses) {
			if (EntityCacheUtil.getResult(
						SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
						SCLicenseImpl.class, scLicense.getPrimaryKey()) == null) {
				cacheResult(scLicense);
			}
			else {
				scLicense.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all s c licenses.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(SCLicenseImpl.class.getName());
		}

		EntityCacheUtil.clearCache(SCLicenseImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the s c license.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SCLicense scLicense) {
		EntityCacheUtil.removeResult(SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
			SCLicenseImpl.class, scLicense.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<SCLicense> scLicenses) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SCLicense scLicense : scLicenses) {
			EntityCacheUtil.removeResult(SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
				SCLicenseImpl.class, scLicense.getPrimaryKey());
		}
	}

	/**
	 * Creates a new s c license with the primary key. Does not add the s c license to the database.
	 *
	 * @param licenseId the primary key for the new s c license
	 * @return the new s c license
	 */
	@Override
	public SCLicense create(long licenseId) {
		SCLicense scLicense = new SCLicenseImpl();

		scLicense.setNew(true);
		scLicense.setPrimaryKey(licenseId);

		return scLicense;
	}

	/**
	 * Removes the s c license with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param licenseId the primary key of the s c license
	 * @return the s c license that was removed
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException if a s c license with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCLicense remove(long licenseId)
		throws NoSuchLicenseException, SystemException {
		return remove((Serializable)licenseId);
	}

	/**
	 * Removes the s c license with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the s c license
	 * @return the s c license that was removed
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException if a s c license with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCLicense remove(Serializable primaryKey)
		throws NoSuchLicenseException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SCLicense scLicense = (SCLicense)session.get(SCLicenseImpl.class,
					primaryKey);

			if (scLicense == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLicenseException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(scLicense);
		}
		catch (NoSuchLicenseException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected SCLicense removeImpl(SCLicense scLicense)
		throws SystemException {
		scLicense = toUnwrappedModel(scLicense);

		scLicenseToSCProductEntryTableMapper.deleteLeftPrimaryKeyTableMappings(scLicense.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(scLicense)) {
				scLicense = (SCLicense)session.get(SCLicenseImpl.class,
						scLicense.getPrimaryKeyObj());
			}

			if (scLicense != null) {
				session.delete(scLicense);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (scLicense != null) {
			clearCache(scLicense);
		}

		return scLicense;
	}

	@Override
	public SCLicense updateImpl(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws SystemException {
		scLicense = toUnwrappedModel(scLicense);

		boolean isNew = scLicense.isNew();

		SCLicenseModelImpl scLicenseModelImpl = (SCLicenseModelImpl)scLicense;

		Session session = null;

		try {
			session = openSession();

			if (scLicense.isNew()) {
				session.save(scLicense);

				scLicense.setNew(false);
			}
			else {
				session.merge(scLicense);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !SCLicenseModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((scLicenseModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTIVE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						scLicenseModelImpl.getOriginalActive()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ACTIVE, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTIVE,
					args);

				args = new Object[] { scLicenseModelImpl.getActive() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ACTIVE, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTIVE,
					args);
			}

			if ((scLicenseModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_A_R.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						scLicenseModelImpl.getOriginalActive(),
						scLicenseModelImpl.getOriginalRecommended()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_A_R, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_A_R,
					args);

				args = new Object[] {
						scLicenseModelImpl.getActive(),
						scLicenseModelImpl.getRecommended()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_A_R, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_A_R,
					args);
			}
		}

		EntityCacheUtil.putResult(SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
			SCLicenseImpl.class, scLicense.getPrimaryKey(), scLicense);

		scLicense.resetOriginalValues();

		return scLicense;
	}

	protected SCLicense toUnwrappedModel(SCLicense scLicense) {
		if (scLicense instanceof SCLicenseImpl) {
			return scLicense;
		}

		SCLicenseImpl scLicenseImpl = new SCLicenseImpl();

		scLicenseImpl.setNew(scLicense.isNew());
		scLicenseImpl.setPrimaryKey(scLicense.getPrimaryKey());

		scLicenseImpl.setLicenseId(scLicense.getLicenseId());
		scLicenseImpl.setName(scLicense.getName());
		scLicenseImpl.setUrl(scLicense.getUrl());
		scLicenseImpl.setOpenSource(scLicense.isOpenSource());
		scLicenseImpl.setActive(scLicense.isActive());
		scLicenseImpl.setRecommended(scLicense.isRecommended());

		return scLicenseImpl;
	}

	/**
	 * Returns the s c license with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the s c license
	 * @return the s c license
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException if a s c license with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCLicense findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLicenseException, SystemException {
		SCLicense scLicense = fetchByPrimaryKey(primaryKey);

		if (scLicense == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLicenseException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return scLicense;
	}

	/**
	 * Returns the s c license with the primary key or throws a {@link com.liferay.portlet.softwarecatalog.NoSuchLicenseException} if it could not be found.
	 *
	 * @param licenseId the primary key of the s c license
	 * @return the s c license
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException if a s c license with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCLicense findByPrimaryKey(long licenseId)
		throws NoSuchLicenseException, SystemException {
		return findByPrimaryKey((Serializable)licenseId);
	}

	/**
	 * Returns the s c license with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the s c license
	 * @return the s c license, or <code>null</code> if a s c license with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCLicense fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		SCLicense scLicense = (SCLicense)EntityCacheUtil.getResult(SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
				SCLicenseImpl.class, primaryKey);

		if (scLicense == _nullSCLicense) {
			return null;
		}

		if (scLicense == null) {
			Session session = null;

			try {
				session = openSession();

				scLicense = (SCLicense)session.get(SCLicenseImpl.class,
						primaryKey);

				if (scLicense != null) {
					cacheResult(scLicense);
				}
				else {
					EntityCacheUtil.putResult(SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
						SCLicenseImpl.class, primaryKey, _nullSCLicense);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
					SCLicenseImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return scLicense;
	}

	/**
	 * Returns the s c license with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param licenseId the primary key of the s c license
	 * @return the s c license, or <code>null</code> if a s c license with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCLicense fetchByPrimaryKey(long licenseId)
		throws SystemException {
		return fetchByPrimaryKey((Serializable)licenseId);
	}

	/**
	 * Returns all the s c licenses.
	 *
	 * @return the s c licenses
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SCLicense> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c licenses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of s c licenses
	 * @param end the upper bound of the range of s c licenses (not inclusive)
	 * @return the range of s c licenses
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SCLicense> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c licenses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of s c licenses
	 * @param end the upper bound of the range of s c licenses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of s c licenses
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SCLicense> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<SCLicense> list = (List<SCLicense>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SCLICENSE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SCLICENSE;

				if (pagination) {
					sql = sql.concat(SCLicenseModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<SCLicense>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<SCLicense>(list);
				}
				else {
					list = (List<SCLicense>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the s c licenses from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (SCLicense scLicense : findAll()) {
			remove(scLicense);
		}
	}

	/**
	 * Returns the number of s c licenses.
	 *
	 * @return the number of s c licenses
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SCLICENSE);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns all the s c product entries associated with the s c license.
	 *
	 * @param pk the primary key of the s c license
	 * @return the s c product entries associated with the s c license
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCProductEntries(
		long pk) throws SystemException {
		return getSCProductEntries(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the s c product entries associated with the s c license.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the s c license
	 * @param start the lower bound of the range of s c licenses
	 * @param end the upper bound of the range of s c licenses (not inclusive)
	 * @return the range of s c product entries associated with the s c license
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCProductEntries(
		long pk, int start, int end) throws SystemException {
		return getSCProductEntries(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c product entries associated with the s c license.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the s c license
	 * @param start the lower bound of the range of s c licenses
	 * @param end the upper bound of the range of s c licenses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of s c product entries associated with the s c license
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCProductEntries(
		long pk, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		return scLicenseToSCProductEntryTableMapper.getRightBaseModels(pk,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of s c product entries associated with the s c license.
	 *
	 * @param pk the primary key of the s c license
	 * @return the number of s c product entries associated with the s c license
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getSCProductEntriesSize(long pk) throws SystemException {
		long[] pks = scLicenseToSCProductEntryTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the s c product entry is associated with the s c license.
	 *
	 * @param pk the primary key of the s c license
	 * @param scProductEntryPK the primary key of the s c product entry
	 * @return <code>true</code> if the s c product entry is associated with the s c license; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public boolean containsSCProductEntry(long pk, long scProductEntryPK)
		throws SystemException {
		return scLicenseToSCProductEntryTableMapper.containsTableMapping(pk,
			scProductEntryPK);
	}

	/**
	 * Returns <code>true</code> if the s c license has any s c product entries associated with it.
	 *
	 * @param pk the primary key of the s c license to check for associations with s c product entries
	 * @return <code>true</code> if the s c license has any s c product entries associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public boolean containsSCProductEntries(long pk) throws SystemException {
		if (getSCProductEntriesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the s c license and the s c product entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c license
	 * @param scProductEntryPK the primary key of the s c product entry
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addSCProductEntry(long pk, long scProductEntryPK)
		throws SystemException {
		scLicenseToSCProductEntryTableMapper.addTableMapping(pk,
			scProductEntryPK);
	}

	/**
	 * Adds an association between the s c license and the s c product entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c license
	 * @param scProductEntry the s c product entry
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addSCProductEntry(long pk,
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry)
		throws SystemException {
		scLicenseToSCProductEntryTableMapper.addTableMapping(pk,
			scProductEntry.getPrimaryKey());
	}

	/**
	 * Adds an association between the s c license and the s c product entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c license
	 * @param scProductEntryPKs the primary keys of the s c product entries
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addSCProductEntries(long pk, long[] scProductEntryPKs)
		throws SystemException {
		for (long scProductEntryPK : scProductEntryPKs) {
			scLicenseToSCProductEntryTableMapper.addTableMapping(pk,
				scProductEntryPK);
		}
	}

	/**
	 * Adds an association between the s c license and the s c product entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c license
	 * @param scProductEntries the s c product entries
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addSCProductEntries(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> scProductEntries)
		throws SystemException {
		for (com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry : scProductEntries) {
			scLicenseToSCProductEntryTableMapper.addTableMapping(pk,
				scProductEntry.getPrimaryKey());
		}
	}

	/**
	 * Clears all associations between the s c license and its s c product entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c license to clear the associated s c product entries from
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void clearSCProductEntries(long pk) throws SystemException {
		scLicenseToSCProductEntryTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the s c license and the s c product entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c license
	 * @param scProductEntryPK the primary key of the s c product entry
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeSCProductEntry(long pk, long scProductEntryPK)
		throws SystemException {
		scLicenseToSCProductEntryTableMapper.deleteTableMapping(pk,
			scProductEntryPK);
	}

	/**
	 * Removes the association between the s c license and the s c product entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c license
	 * @param scProductEntry the s c product entry
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeSCProductEntry(long pk,
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry)
		throws SystemException {
		scLicenseToSCProductEntryTableMapper.deleteTableMapping(pk,
			scProductEntry.getPrimaryKey());
	}

	/**
	 * Removes the association between the s c license and the s c product entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c license
	 * @param scProductEntryPKs the primary keys of the s c product entries
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeSCProductEntries(long pk, long[] scProductEntryPKs)
		throws SystemException {
		for (long scProductEntryPK : scProductEntryPKs) {
			scLicenseToSCProductEntryTableMapper.deleteTableMapping(pk,
				scProductEntryPK);
		}
	}

	/**
	 * Removes the association between the s c license and the s c product entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c license
	 * @param scProductEntries the s c product entries
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeSCProductEntries(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> scProductEntries)
		throws SystemException {
		for (com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry : scProductEntries) {
			scLicenseToSCProductEntryTableMapper.deleteTableMapping(pk,
				scProductEntry.getPrimaryKey());
		}
	}

	/**
	 * Sets the s c product entries associated with the s c license, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c license
	 * @param scProductEntryPKs the primary keys of the s c product entries to be associated with the s c license
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void setSCProductEntries(long pk, long[] scProductEntryPKs)
		throws SystemException {
		Set<Long> newSCProductEntryPKsSet = SetUtil.fromArray(scProductEntryPKs);
		Set<Long> oldSCProductEntryPKsSet = SetUtil.fromArray(scLicenseToSCProductEntryTableMapper.getRightPrimaryKeys(
					pk));

		Set<Long> removeSCProductEntryPKsSet = new HashSet<Long>(oldSCProductEntryPKsSet);

		removeSCProductEntryPKsSet.removeAll(newSCProductEntryPKsSet);

		for (long removeSCProductEntryPK : removeSCProductEntryPKsSet) {
			scLicenseToSCProductEntryTableMapper.deleteTableMapping(pk,
				removeSCProductEntryPK);
		}

		newSCProductEntryPKsSet.removeAll(oldSCProductEntryPKsSet);

		for (long newSCProductEntryPK : newSCProductEntryPKsSet) {
			scLicenseToSCProductEntryTableMapper.addTableMapping(pk,
				newSCProductEntryPK);
		}
	}

	/**
	 * Sets the s c product entries associated with the s c license, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c license
	 * @param scProductEntries the s c product entries to be associated with the s c license
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void setSCProductEntries(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> scProductEntries)
		throws SystemException {
		try {
			long[] scProductEntryPKs = new long[scProductEntries.size()];

			for (int i = 0; i < scProductEntries.size(); i++) {
				com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry =
					scProductEntries.get(i);

				scProductEntryPKs[i] = scProductEntry.getPrimaryKey();
			}

			setSCProductEntries(pk, scProductEntryPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCLicenseModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME);
		}
	}

	@Override
	protected Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	/**
	 * Initializes the s c license persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.softwarecatalog.model.SCLicense")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SCLicense>> listenersList = new ArrayList<ModelListener<SCLicense>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SCLicense>)InstanceFactory.newInstance(
							getClassLoader(), listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		scLicenseToSCProductEntryTableMapper = TableMapperFactory.getTableMapper("SCLicenses_SCProductEntries",
				"licenseId", "productEntryId", this, scProductEntryPersistence);
	}

	public void destroy() {
		EntityCacheUtil.removeCache(SCLicenseImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = SCProductEntryPersistence.class)
	protected SCProductEntryPersistence scProductEntryPersistence;
	protected TableMapper<SCLicense, com.liferay.portlet.softwarecatalog.model.SCProductEntry> scLicenseToSCProductEntryTableMapper;
	private static final String _SQL_SELECT_SCLICENSE = "SELECT scLicense FROM SCLicense scLicense";
	private static final String _SQL_SELECT_SCLICENSE_WHERE = "SELECT scLicense FROM SCLicense scLicense WHERE ";
	private static final String _SQL_COUNT_SCLICENSE = "SELECT COUNT(scLicense) FROM SCLicense scLicense";
	private static final String _SQL_COUNT_SCLICENSE_WHERE = "SELECT COUNT(scLicense) FROM SCLicense scLicense WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "scLicense.licenseId";
	private static final String _FILTER_SQL_SELECT_SCLICENSE_WHERE = "SELECT DISTINCT {scLicense.*} FROM SCLicense scLicense WHERE ";
	private static final String _FILTER_SQL_SELECT_SCLICENSE_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {SCLicense.*} FROM (SELECT DISTINCT scLicense.licenseId FROM SCLicense scLicense WHERE ";
	private static final String _FILTER_SQL_SELECT_SCLICENSE_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN SCLicense ON TEMP_TABLE.licenseId = SCLicense.licenseId";
	private static final String _FILTER_SQL_COUNT_SCLICENSE_WHERE = "SELECT COUNT(DISTINCT scLicense.licenseId) AS COUNT_VALUE FROM SCLicense scLicense WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "scLicense";
	private static final String _FILTER_ENTITY_TABLE = "SCLicense";
	private static final String _ORDER_BY_ENTITY_ALIAS = "scLicense.";
	private static final String _ORDER_BY_ENTITY_TABLE = "SCLicense.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SCLicense exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SCLicense exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(SCLicensePersistenceImpl.class);
	private static Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"active"
			});
	private static SCLicense _nullSCLicense = new SCLicenseImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<SCLicense> toCacheModel() {
				return _nullSCLicenseCacheModel;
			}
		};

	private static CacheModel<SCLicense> _nullSCLicenseCacheModel = new CacheModel<SCLicense>() {
			@Override
			public SCLicense toEntityModel() {
				return _nullSCLicense;
			}
		};
}