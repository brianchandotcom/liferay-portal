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

package com.liferay.portlet.softwarecatalog.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.service.persistence.impl.TableMapper;
import com.liferay.portal.service.persistence.impl.TableMapperFactory;

import com.liferay.portlet.softwarecatalog.NoSuchProductVersionException;
import com.liferay.portlet.softwarecatalog.model.SCProductVersion;
import com.liferay.portlet.softwarecatalog.model.impl.SCProductVersionImpl;
import com.liferay.portlet.softwarecatalog.model.impl.SCProductVersionModelImpl;
import com.liferay.portlet.softwarecatalog.service.persistence.SCFrameworkVersionPersistence;
import com.liferay.portlet.softwarecatalog.service.persistence.SCProductVersionPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the s c product version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SCProductVersionPersistence
 * @see com.liferay.portlet.softwarecatalog.service.persistence.SCProductVersionUtil
 * @generated
 */
@ProviderType
public class SCProductVersionPersistenceImpl extends BasePersistenceImpl<SCProductVersion>
	implements SCProductVersionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SCProductVersionUtil} to access the s c product version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SCProductVersionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED,
			SCProductVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED,
			SCProductVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_PRODUCTENTRYID =
		new FinderPath(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED,
			SCProductVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByProductEntryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PRODUCTENTRYID =
		new FinderPath(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED,
			SCProductVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByProductEntryId",
			new String[] { Long.class.getName() },
			SCProductVersionModelImpl.PRODUCTENTRYID_COLUMN_BITMASK |
			SCProductVersionModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PRODUCTENTRYID = new FinderPath(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByProductEntryId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the s c product versions where productEntryId = &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @return the matching s c product versions
	 */
	@Override
	public List<SCProductVersion> findByProductEntryId(long productEntryId) {
		return findByProductEntryId(productEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c product versions where productEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param productEntryId the product entry ID
	 * @param start the lower bound of the range of s c product versions
	 * @param end the upper bound of the range of s c product versions (not inclusive)
	 * @return the range of matching s c product versions
	 */
	@Override
	public List<SCProductVersion> findByProductEntryId(long productEntryId,
		int start, int end) {
		return findByProductEntryId(productEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c product versions where productEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param productEntryId the product entry ID
	 * @param start the lower bound of the range of s c product versions
	 * @param end the upper bound of the range of s c product versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c product versions
	 */
	@Override
	public List<SCProductVersion> findByProductEntryId(long productEntryId,
		int start, int end,
		OrderByComparator<SCProductVersion> orderByComparator) {
		return findByProductEntryId(productEntryId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the s c product versions where productEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param productEntryId the product entry ID
	 * @param start the lower bound of the range of s c product versions
	 * @param end the upper bound of the range of s c product versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the ordered range of matching s c product versions
	 */
	@Override
	public List<SCProductVersion> findByProductEntryId(long productEntryId,
		int start, int end,
		OrderByComparator<SCProductVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PRODUCTENTRYID;
			finderArgs = new Object[] { productEntryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_PRODUCTENTRYID;
			finderArgs = new Object[] {
					productEntryId,
					
					start, end, orderByComparator
				};
		}

		List<SCProductVersion> list = null;

		if (retrieveFromCache) {
			list = (List<SCProductVersion>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SCProductVersion scProductVersion : list) {
					if ((productEntryId != scProductVersion.getProductEntryId())) {
						list = null;

						break;
					}
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

			query.append(_SQL_SELECT_SCPRODUCTVERSION_WHERE);

			query.append(_FINDER_COLUMN_PRODUCTENTRYID_PRODUCTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SCProductVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(productEntryId);

				if (!pagination) {
					list = (List<SCProductVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SCProductVersion>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first s c product version in the ordered set where productEntryId = &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c product version
	 * @throws NoSuchProductVersionException if a matching s c product version could not be found
	 */
	@Override
	public SCProductVersion findByProductEntryId_First(long productEntryId,
		OrderByComparator<SCProductVersion> orderByComparator)
		throws NoSuchProductVersionException {
		SCProductVersion scProductVersion = fetchByProductEntryId_First(productEntryId,
				orderByComparator);

		if (scProductVersion != null) {
			return scProductVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("productEntryId=");
		msg.append(productEntryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductVersionException(msg.toString());
	}

	/**
	 * Returns the first s c product version in the ordered set where productEntryId = &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c product version, or <code>null</code> if a matching s c product version could not be found
	 */
	@Override
	public SCProductVersion fetchByProductEntryId_First(long productEntryId,
		OrderByComparator<SCProductVersion> orderByComparator) {
		List<SCProductVersion> list = findByProductEntryId(productEntryId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last s c product version in the ordered set where productEntryId = &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c product version
	 * @throws NoSuchProductVersionException if a matching s c product version could not be found
	 */
	@Override
	public SCProductVersion findByProductEntryId_Last(long productEntryId,
		OrderByComparator<SCProductVersion> orderByComparator)
		throws NoSuchProductVersionException {
		SCProductVersion scProductVersion = fetchByProductEntryId_Last(productEntryId,
				orderByComparator);

		if (scProductVersion != null) {
			return scProductVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("productEntryId=");
		msg.append(productEntryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductVersionException(msg.toString());
	}

	/**
	 * Returns the last s c product version in the ordered set where productEntryId = &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c product version, or <code>null</code> if a matching s c product version could not be found
	 */
	@Override
	public SCProductVersion fetchByProductEntryId_Last(long productEntryId,
		OrderByComparator<SCProductVersion> orderByComparator) {
		int count = countByProductEntryId(productEntryId);

		if (count == 0) {
			return null;
		}

		List<SCProductVersion> list = findByProductEntryId(productEntryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the s c product versions before and after the current s c product version in the ordered set where productEntryId = &#63;.
	 *
	 * @param productVersionId the primary key of the current s c product version
	 * @param productEntryId the product entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c product version
	 * @throws NoSuchProductVersionException if a s c product version with the primary key could not be found
	 */
	@Override
	public SCProductVersion[] findByProductEntryId_PrevAndNext(
		long productVersionId, long productEntryId,
		OrderByComparator<SCProductVersion> orderByComparator)
		throws NoSuchProductVersionException {
		SCProductVersion scProductVersion = findByPrimaryKey(productVersionId);

		Session session = null;

		try {
			session = openSession();

			SCProductVersion[] array = new SCProductVersionImpl[3];

			array[0] = getByProductEntryId_PrevAndNext(session,
					scProductVersion, productEntryId, orderByComparator, true);

			array[1] = scProductVersion;

			array[2] = getByProductEntryId_PrevAndNext(session,
					scProductVersion, productEntryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SCProductVersion getByProductEntryId_PrevAndNext(
		Session session, SCProductVersion scProductVersion,
		long productEntryId,
		OrderByComparator<SCProductVersion> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SCPRODUCTVERSION_WHERE);

		query.append(_FINDER_COLUMN_PRODUCTENTRYID_PRODUCTENTRYID_2);

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
			query.append(SCProductVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(productEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(scProductVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCProductVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the s c product versions where productEntryId = &#63; from the database.
	 *
	 * @param productEntryId the product entry ID
	 */
	@Override
	public void removeByProductEntryId(long productEntryId) {
		for (SCProductVersion scProductVersion : findByProductEntryId(
				productEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(scProductVersion);
		}
	}

	/**
	 * Returns the number of s c product versions where productEntryId = &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @return the number of matching s c product versions
	 */
	@Override
	public int countByProductEntryId(long productEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_PRODUCTENTRYID;

		Object[] finderArgs = new Object[] { productEntryId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SCPRODUCTVERSION_WHERE);

			query.append(_FINDER_COLUMN_PRODUCTENTRYID_PRODUCTENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(productEntryId);

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

	private static final String _FINDER_COLUMN_PRODUCTENTRYID_PRODUCTENTRYID_2 = "scProductVersion.productEntryId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL = new FinderPath(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED,
			SCProductVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByDirectDownloadURL",
			new String[] { String.class.getName() },
			SCProductVersionModelImpl.DIRECTDOWNLOADURL_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_DIRECTDOWNLOADURL = new FinderPath(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByDirectDownloadURL", new String[] { String.class.getName() });

	/**
	 * Returns the s c product version where directDownloadURL = &#63; or throws a {@link NoSuchProductVersionException} if it could not be found.
	 *
	 * @param directDownloadURL the direct download u r l
	 * @return the matching s c product version
	 * @throws NoSuchProductVersionException if a matching s c product version could not be found
	 */
	@Override
	public SCProductVersion findByDirectDownloadURL(String directDownloadURL)
		throws NoSuchProductVersionException {
		SCProductVersion scProductVersion = fetchByDirectDownloadURL(directDownloadURL);

		if (scProductVersion == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("directDownloadURL=");
			msg.append(directDownloadURL);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchProductVersionException(msg.toString());
		}

		return scProductVersion;
	}

	/**
	 * Returns the s c product version where directDownloadURL = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param directDownloadURL the direct download u r l
	 * @return the matching s c product version, or <code>null</code> if a matching s c product version could not be found
	 */
	@Override
	public SCProductVersion fetchByDirectDownloadURL(String directDownloadURL) {
		return fetchByDirectDownloadURL(directDownloadURL, true);
	}

	/**
	 * Returns the s c product version where directDownloadURL = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param directDownloadURL the direct download u r l
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching s c product version, or <code>null</code> if a matching s c product version could not be found
	 */
	@Override
	public SCProductVersion fetchByDirectDownloadURL(String directDownloadURL,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { directDownloadURL };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
					finderArgs, this);
		}

		if (result instanceof SCProductVersion) {
			SCProductVersion scProductVersion = (SCProductVersion)result;

			if (!Validator.equals(directDownloadURL,
						scProductVersion.getDirectDownloadURL())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_SCPRODUCTVERSION_WHERE);

			boolean bindDirectDownloadURL = false;

			if (directDownloadURL == null) {
				query.append(_FINDER_COLUMN_DIRECTDOWNLOADURL_DIRECTDOWNLOADURL_1);
			}
			else if (directDownloadURL.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_DIRECTDOWNLOADURL_DIRECTDOWNLOADURL_3);
			}
			else {
				bindDirectDownloadURL = true;

				query.append(_FINDER_COLUMN_DIRECTDOWNLOADURL_DIRECTDOWNLOADURL_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindDirectDownloadURL) {
					qPos.add(StringUtil.toLowerCase(directDownloadURL));
				}

				List<SCProductVersion> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"SCProductVersionPersistenceImpl.fetchByDirectDownloadURL(String, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					SCProductVersion scProductVersion = list.get(0);

					result = scProductVersion;

					cacheResult(scProductVersion);

					if ((scProductVersion.getDirectDownloadURL() == null) ||
							!scProductVersion.getDirectDownloadURL()
												 .equals(directDownloadURL)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
							finderArgs, scProductVersion);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (SCProductVersion)result;
		}
	}

	/**
	 * Removes the s c product version where directDownloadURL = &#63; from the database.
	 *
	 * @param directDownloadURL the direct download u r l
	 * @return the s c product version that was removed
	 */
	@Override
	public SCProductVersion removeByDirectDownloadURL(String directDownloadURL)
		throws NoSuchProductVersionException {
		SCProductVersion scProductVersion = findByDirectDownloadURL(directDownloadURL);

		return remove(scProductVersion);
	}

	/**
	 * Returns the number of s c product versions where directDownloadURL = &#63;.
	 *
	 * @param directDownloadURL the direct download u r l
	 * @return the number of matching s c product versions
	 */
	@Override
	public int countByDirectDownloadURL(String directDownloadURL) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_DIRECTDOWNLOADURL;

		Object[] finderArgs = new Object[] { directDownloadURL };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SCPRODUCTVERSION_WHERE);

			boolean bindDirectDownloadURL = false;

			if (directDownloadURL == null) {
				query.append(_FINDER_COLUMN_DIRECTDOWNLOADURL_DIRECTDOWNLOADURL_1);
			}
			else if (directDownloadURL.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_DIRECTDOWNLOADURL_DIRECTDOWNLOADURL_3);
			}
			else {
				bindDirectDownloadURL = true;

				query.append(_FINDER_COLUMN_DIRECTDOWNLOADURL_DIRECTDOWNLOADURL_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindDirectDownloadURL) {
					qPos.add(StringUtil.toLowerCase(directDownloadURL));
				}

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

	private static final String _FINDER_COLUMN_DIRECTDOWNLOADURL_DIRECTDOWNLOADURL_1 =
		"scProductVersion.directDownloadURL IS NULL";
	private static final String _FINDER_COLUMN_DIRECTDOWNLOADURL_DIRECTDOWNLOADURL_2 =
		"lower(scProductVersion.directDownloadURL) = ?";
	private static final String _FINDER_COLUMN_DIRECTDOWNLOADURL_DIRECTDOWNLOADURL_3 =
		"(scProductVersion.directDownloadURL IS NULL OR scProductVersion.directDownloadURL = '')";

	public SCProductVersionPersistenceImpl() {
		setModelClass(SCProductVersion.class);
	}

	/**
	 * Caches the s c product version in the entity cache if it is enabled.
	 *
	 * @param scProductVersion the s c product version
	 */
	@Override
	public void cacheResult(SCProductVersion scProductVersion) {
		EntityCacheUtil.putResult(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionImpl.class, scProductVersion.getPrimaryKey(),
			scProductVersion);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
			new Object[] { scProductVersion.getDirectDownloadURL() },
			scProductVersion);

		scProductVersion.resetOriginalValues();
	}

	/**
	 * Caches the s c product versions in the entity cache if it is enabled.
	 *
	 * @param scProductVersions the s c product versions
	 */
	@Override
	public void cacheResult(List<SCProductVersion> scProductVersions) {
		for (SCProductVersion scProductVersion : scProductVersions) {
			if (EntityCacheUtil.getResult(
						SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
						SCProductVersionImpl.class,
						scProductVersion.getPrimaryKey()) == null) {
				cacheResult(scProductVersion);
			}
			else {
				scProductVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all s c product versions.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(SCProductVersionImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the s c product version.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SCProductVersion scProductVersion) {
		EntityCacheUtil.removeResult(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionImpl.class, scProductVersion.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((SCProductVersionModelImpl)scProductVersion);
	}

	@Override
	public void clearCache(List<SCProductVersion> scProductVersions) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SCProductVersion scProductVersion : scProductVersions) {
			EntityCacheUtil.removeResult(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
				SCProductVersionImpl.class, scProductVersion.getPrimaryKey());

			clearUniqueFindersCache((SCProductVersionModelImpl)scProductVersion);
		}
	}

	protected void cacheUniqueFindersCache(
		SCProductVersionModelImpl scProductVersionModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					scProductVersionModelImpl.getDirectDownloadURL()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_DIRECTDOWNLOADURL,
				args, Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
				args, scProductVersionModelImpl);
		}
		else {
			if ((scProductVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						scProductVersionModelImpl.getDirectDownloadURL()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_DIRECTDOWNLOADURL,
					args, Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
					args, scProductVersionModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		SCProductVersionModelImpl scProductVersionModelImpl) {
		Object[] args = new Object[] {
				scProductVersionModelImpl.getDirectDownloadURL()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_DIRECTDOWNLOADURL,
			args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
			args);

		if ((scProductVersionModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL.getColumnBitmask()) != 0) {
			args = new Object[] {
					scProductVersionModelImpl.getOriginalDirectDownloadURL()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_DIRECTDOWNLOADURL,
				args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
				args);
		}
	}

	/**
	 * Creates a new s c product version with the primary key. Does not add the s c product version to the database.
	 *
	 * @param productVersionId the primary key for the new s c product version
	 * @return the new s c product version
	 */
	@Override
	public SCProductVersion create(long productVersionId) {
		SCProductVersion scProductVersion = new SCProductVersionImpl();

		scProductVersion.setNew(true);
		scProductVersion.setPrimaryKey(productVersionId);

		return scProductVersion;
	}

	/**
	 * Removes the s c product version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param productVersionId the primary key of the s c product version
	 * @return the s c product version that was removed
	 * @throws NoSuchProductVersionException if a s c product version with the primary key could not be found
	 */
	@Override
	public SCProductVersion remove(long productVersionId)
		throws NoSuchProductVersionException {
		return remove((Serializable)productVersionId);
	}

	/**
	 * Removes the s c product version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the s c product version
	 * @return the s c product version that was removed
	 * @throws NoSuchProductVersionException if a s c product version with the primary key could not be found
	 */
	@Override
	public SCProductVersion remove(Serializable primaryKey)
		throws NoSuchProductVersionException {
		Session session = null;

		try {
			session = openSession();

			SCProductVersion scProductVersion = (SCProductVersion)session.get(SCProductVersionImpl.class,
					primaryKey);

			if (scProductVersion == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchProductVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(scProductVersion);
		}
		catch (NoSuchProductVersionException nsee) {
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
	protected SCProductVersion removeImpl(SCProductVersion scProductVersion) {
		scProductVersion = toUnwrappedModel(scProductVersion);

		scProductVersionToSCFrameworkVersionTableMapper.deleteLeftPrimaryKeyTableMappings(0,
			scProductVersion.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(scProductVersion)) {
				scProductVersion = (SCProductVersion)session.get(SCProductVersionImpl.class,
						scProductVersion.getPrimaryKeyObj());
			}

			if (scProductVersion != null) {
				session.delete(scProductVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (scProductVersion != null) {
			clearCache(scProductVersion);
		}

		return scProductVersion;
	}

	@Override
	public SCProductVersion updateImpl(SCProductVersion scProductVersion) {
		scProductVersion = toUnwrappedModel(scProductVersion);

		boolean isNew = scProductVersion.isNew();

		SCProductVersionModelImpl scProductVersionModelImpl = (SCProductVersionModelImpl)scProductVersion;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (scProductVersion.getCreateDate() == null)) {
			if (serviceContext == null) {
				scProductVersion.setCreateDate(now);
			}
			else {
				scProductVersion.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!scProductVersionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				scProductVersion.setModifiedDate(now);
			}
			else {
				scProductVersion.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (scProductVersion.isNew()) {
				session.save(scProductVersion);

				scProductVersion.setNew(false);
			}
			else {
				scProductVersion = (SCProductVersion)session.merge(scProductVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !SCProductVersionModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((scProductVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PRODUCTENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						scProductVersionModelImpl.getOriginalProductEntryId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PRODUCTENTRYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PRODUCTENTRYID,
					args);

				args = new Object[] {
						scProductVersionModelImpl.getProductEntryId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PRODUCTENTRYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PRODUCTENTRYID,
					args);
			}
		}

		EntityCacheUtil.putResult(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionImpl.class, scProductVersion.getPrimaryKey(),
			scProductVersion, false);

		clearUniqueFindersCache(scProductVersionModelImpl);
		cacheUniqueFindersCache(scProductVersionModelImpl, isNew);

		scProductVersion.resetOriginalValues();

		return scProductVersion;
	}

	protected SCProductVersion toUnwrappedModel(
		SCProductVersion scProductVersion) {
		if (scProductVersion instanceof SCProductVersionImpl) {
			return scProductVersion;
		}

		SCProductVersionImpl scProductVersionImpl = new SCProductVersionImpl();

		scProductVersionImpl.setNew(scProductVersion.isNew());
		scProductVersionImpl.setPrimaryKey(scProductVersion.getPrimaryKey());

		scProductVersionImpl.setProductVersionId(scProductVersion.getProductVersionId());
		scProductVersionImpl.setCompanyId(scProductVersion.getCompanyId());
		scProductVersionImpl.setUserId(scProductVersion.getUserId());
		scProductVersionImpl.setUserName(scProductVersion.getUserName());
		scProductVersionImpl.setCreateDate(scProductVersion.getCreateDate());
		scProductVersionImpl.setModifiedDate(scProductVersion.getModifiedDate());
		scProductVersionImpl.setProductEntryId(scProductVersion.getProductEntryId());
		scProductVersionImpl.setVersion(scProductVersion.getVersion());
		scProductVersionImpl.setChangeLog(scProductVersion.getChangeLog());
		scProductVersionImpl.setDownloadPageURL(scProductVersion.getDownloadPageURL());
		scProductVersionImpl.setDirectDownloadURL(scProductVersion.getDirectDownloadURL());
		scProductVersionImpl.setRepoStoreArtifact(scProductVersion.isRepoStoreArtifact());

		return scProductVersionImpl;
	}

	/**
	 * Returns the s c product version with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the s c product version
	 * @return the s c product version
	 * @throws NoSuchProductVersionException if a s c product version with the primary key could not be found
	 */
	@Override
	public SCProductVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchProductVersionException {
		SCProductVersion scProductVersion = fetchByPrimaryKey(primaryKey);

		if (scProductVersion == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchProductVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return scProductVersion;
	}

	/**
	 * Returns the s c product version with the primary key or throws a {@link NoSuchProductVersionException} if it could not be found.
	 *
	 * @param productVersionId the primary key of the s c product version
	 * @return the s c product version
	 * @throws NoSuchProductVersionException if a s c product version with the primary key could not be found
	 */
	@Override
	public SCProductVersion findByPrimaryKey(long productVersionId)
		throws NoSuchProductVersionException {
		return findByPrimaryKey((Serializable)productVersionId);
	}

	/**
	 * Returns the s c product version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the s c product version
	 * @return the s c product version, or <code>null</code> if a s c product version with the primary key could not be found
	 */
	@Override
	public SCProductVersion fetchByPrimaryKey(Serializable primaryKey) {
		SCProductVersion scProductVersion = (SCProductVersion)EntityCacheUtil.getResult(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
				SCProductVersionImpl.class, primaryKey);

		if (scProductVersion == _nullSCProductVersion) {
			return null;
		}

		if (scProductVersion == null) {
			Session session = null;

			try {
				session = openSession();

				scProductVersion = (SCProductVersion)session.get(SCProductVersionImpl.class,
						primaryKey);

				if (scProductVersion != null) {
					cacheResult(scProductVersion);
				}
				else {
					EntityCacheUtil.putResult(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
						SCProductVersionImpl.class, primaryKey,
						_nullSCProductVersion);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
					SCProductVersionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return scProductVersion;
	}

	/**
	 * Returns the s c product version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param productVersionId the primary key of the s c product version
	 * @return the s c product version, or <code>null</code> if a s c product version with the primary key could not be found
	 */
	@Override
	public SCProductVersion fetchByPrimaryKey(long productVersionId) {
		return fetchByPrimaryKey((Serializable)productVersionId);
	}

	@Override
	public Map<Serializable, SCProductVersion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, SCProductVersion> map = new HashMap<Serializable, SCProductVersion>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			SCProductVersion scProductVersion = fetchByPrimaryKey(primaryKey);

			if (scProductVersion != null) {
				map.put(primaryKey, scProductVersion);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			SCProductVersion scProductVersion = (SCProductVersion)EntityCacheUtil.getResult(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
					SCProductVersionImpl.class, primaryKey);

			if (scProductVersion == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, scProductVersion);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_SCPRODUCTVERSION_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append(String.valueOf(primaryKey));

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (SCProductVersion scProductVersion : (List<SCProductVersion>)q.list()) {
				map.put(scProductVersion.getPrimaryKeyObj(), scProductVersion);

				cacheResult(scProductVersion);

				uncachedPrimaryKeys.remove(scProductVersion.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				EntityCacheUtil.putResult(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
					SCProductVersionImpl.class, primaryKey,
					_nullSCProductVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the s c product versions.
	 *
	 * @return the s c product versions
	 */
	@Override
	public List<SCProductVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c product versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of s c product versions
	 * @param end the upper bound of the range of s c product versions (not inclusive)
	 * @return the range of s c product versions
	 */
	@Override
	public List<SCProductVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c product versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of s c product versions
	 * @param end the upper bound of the range of s c product versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of s c product versions
	 */
	@Override
	public List<SCProductVersion> findAll(int start, int end,
		OrderByComparator<SCProductVersion> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the s c product versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of s c product versions
	 * @param end the upper bound of the range of s c product versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the ordered range of s c product versions
	 */
	@Override
	public List<SCProductVersion> findAll(int start, int end,
		OrderByComparator<SCProductVersion> orderByComparator,
		boolean retrieveFromCache) {
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

		List<SCProductVersion> list = null;

		if (retrieveFromCache) {
			list = (List<SCProductVersion>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SCPRODUCTVERSION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SCPRODUCTVERSION;

				if (pagination) {
					sql = sql.concat(SCProductVersionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<SCProductVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SCProductVersion>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes all the s c product versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SCProductVersion scProductVersion : findAll()) {
			remove(scProductVersion);
		}
	}

	/**
	 * Returns the number of s c product versions.
	 *
	 * @return the number of s c product versions
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SCPRODUCTVERSION);

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
	 * Returns the primaryKeys of s c framework versions associated with the s c product version.
	 *
	 * @param pk the primary key of the s c product version
	 * @return long[] of the primaryKeys of s c framework versions associated with the s c product version
	 */
	@Override
	public long[] getSCFrameworkVersionPrimaryKeys(long pk) {
		long[] pks = scProductVersionToSCFrameworkVersionTableMapper.getRightPrimaryKeys(0,
				pk);

		return pks.clone();
	}

	/**
	 * Returns all the s c framework versions associated with the s c product version.
	 *
	 * @param pk the primary key of the s c product version
	 * @return the s c framework versions associated with the s c product version
	 */
	@Override
	public List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		long pk) {
		return getSCFrameworkVersions(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the s c framework versions associated with the s c product version.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the s c product version
	 * @param start the lower bound of the range of s c product versions
	 * @param end the upper bound of the range of s c product versions (not inclusive)
	 * @return the range of s c framework versions associated with the s c product version
	 */
	@Override
	public List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		long pk, int start, int end) {
		return getSCFrameworkVersions(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c framework versions associated with the s c product version.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the s c product version
	 * @param start the lower bound of the range of s c product versions
	 * @param end the upper bound of the range of s c product versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of s c framework versions associated with the s c product version
	 */
	@Override
	public List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		long pk, int start, int end,
		OrderByComparator<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> orderByComparator) {
		return scProductVersionToSCFrameworkVersionTableMapper.getRightBaseModels(0,
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of s c framework versions associated with the s c product version.
	 *
	 * @param pk the primary key of the s c product version
	 * @return the number of s c framework versions associated with the s c product version
	 */
	@Override
	public int getSCFrameworkVersionsSize(long pk) {
		long[] pks = scProductVersionToSCFrameworkVersionTableMapper.getRightPrimaryKeys(0,
				pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the s c framework version is associated with the s c product version.
	 *
	 * @param pk the primary key of the s c product version
	 * @param scFrameworkVersionPK the primary key of the s c framework version
	 * @return <code>true</code> if the s c framework version is associated with the s c product version; <code>false</code> otherwise
	 */
	@Override
	public boolean containsSCFrameworkVersion(long pk, long scFrameworkVersionPK) {
		return scProductVersionToSCFrameworkVersionTableMapper.containsTableMapping(0,
			pk, scFrameworkVersionPK);
	}

	/**
	 * Returns <code>true</code> if the s c product version has any s c framework versions associated with it.
	 *
	 * @param pk the primary key of the s c product version to check for associations with s c framework versions
	 * @return <code>true</code> if the s c product version has any s c framework versions associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsSCFrameworkVersions(long pk) {
		if (getSCFrameworkVersionsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the s c product version and the s c framework version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product version
	 * @param scFrameworkVersionPK the primary key of the s c framework version
	 */
	@Override
	public void addSCFrameworkVersion(long pk, long scFrameworkVersionPK) {
		scProductVersionToSCFrameworkVersionTableMapper.addTableMapping(0, pk,
			scFrameworkVersionPK);
	}

	/**
	 * Adds an association between the s c product version and the s c framework version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product version
	 * @param scFrameworkVersion the s c framework version
	 */
	@Override
	public void addSCFrameworkVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion) {
		scProductVersionToSCFrameworkVersionTableMapper.addTableMapping(0, pk,
			scFrameworkVersion.getPrimaryKey());
	}

	/**
	 * Adds an association between the s c product version and the s c framework versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product version
	 * @param scFrameworkVersionPKs the primary keys of the s c framework versions
	 */
	@Override
	public void addSCFrameworkVersions(long pk, long[] scFrameworkVersionPKs) {
		for (long scFrameworkVersionPK : scFrameworkVersionPKs) {
			scProductVersionToSCFrameworkVersionTableMapper.addTableMapping(0,
				pk, scFrameworkVersionPK);
		}
	}

	/**
	 * Adds an association between the s c product version and the s c framework versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product version
	 * @param scFrameworkVersions the s c framework versions
	 */
	@Override
	public void addSCFrameworkVersions(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions) {
		for (com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion : scFrameworkVersions) {
			scProductVersionToSCFrameworkVersionTableMapper.addTableMapping(0,
				pk, scFrameworkVersion.getPrimaryKey());
		}
	}

	/**
	 * Clears all associations between the s c product version and its s c framework versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product version to clear the associated s c framework versions from
	 */
	@Override
	public void clearSCFrameworkVersions(long pk) {
		scProductVersionToSCFrameworkVersionTableMapper.deleteLeftPrimaryKeyTableMappings(0,
			pk);
	}

	/**
	 * Removes the association between the s c product version and the s c framework version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product version
	 * @param scFrameworkVersionPK the primary key of the s c framework version
	 */
	@Override
	public void removeSCFrameworkVersion(long pk, long scFrameworkVersionPK) {
		scProductVersionToSCFrameworkVersionTableMapper.deleteTableMapping(0,
			pk, scFrameworkVersionPK);
	}

	/**
	 * Removes the association between the s c product version and the s c framework version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product version
	 * @param scFrameworkVersion the s c framework version
	 */
	@Override
	public void removeSCFrameworkVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion) {
		scProductVersionToSCFrameworkVersionTableMapper.deleteTableMapping(0,
			pk, scFrameworkVersion.getPrimaryKey());
	}

	/**
	 * Removes the association between the s c product version and the s c framework versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product version
	 * @param scFrameworkVersionPKs the primary keys of the s c framework versions
	 */
	@Override
	public void removeSCFrameworkVersions(long pk, long[] scFrameworkVersionPKs) {
		for (long scFrameworkVersionPK : scFrameworkVersionPKs) {
			scProductVersionToSCFrameworkVersionTableMapper.deleteTableMapping(0,
				pk, scFrameworkVersionPK);
		}
	}

	/**
	 * Removes the association between the s c product version and the s c framework versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product version
	 * @param scFrameworkVersions the s c framework versions
	 */
	@Override
	public void removeSCFrameworkVersions(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions) {
		for (com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion : scFrameworkVersions) {
			scProductVersionToSCFrameworkVersionTableMapper.deleteTableMapping(0,
				pk, scFrameworkVersion.getPrimaryKey());
		}
	}

	/**
	 * Sets the s c framework versions associated with the s c product version, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product version
	 * @param scFrameworkVersionPKs the primary keys of the s c framework versions to be associated with the s c product version
	 */
	@Override
	public void setSCFrameworkVersions(long pk, long[] scFrameworkVersionPKs) {
		Set<Long> newSCFrameworkVersionPKsSet = SetUtil.fromArray(scFrameworkVersionPKs);
		Set<Long> oldSCFrameworkVersionPKsSet = SetUtil.fromArray(scProductVersionToSCFrameworkVersionTableMapper.getRightPrimaryKeys(
					0, pk));

		Set<Long> removeSCFrameworkVersionPKsSet = new HashSet<Long>(oldSCFrameworkVersionPKsSet);

		removeSCFrameworkVersionPKsSet.removeAll(newSCFrameworkVersionPKsSet);

		for (long removeSCFrameworkVersionPK : removeSCFrameworkVersionPKsSet) {
			scProductVersionToSCFrameworkVersionTableMapper.deleteTableMapping(0,
				pk, removeSCFrameworkVersionPK);
		}

		newSCFrameworkVersionPKsSet.removeAll(oldSCFrameworkVersionPKsSet);

		for (long newSCFrameworkVersionPK : newSCFrameworkVersionPKsSet) {
			scProductVersionToSCFrameworkVersionTableMapper.addTableMapping(0,
				pk, newSCFrameworkVersionPK);
		}
	}

	/**
	 * Sets the s c framework versions associated with the s c product version, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product version
	 * @param scFrameworkVersions the s c framework versions to be associated with the s c product version
	 */
	@Override
	public void setSCFrameworkVersions(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions) {
		try {
			long[] scFrameworkVersionPKs = new long[scFrameworkVersions.size()];

			for (int i = 0; i < scFrameworkVersions.size(); i++) {
				com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion =
					scFrameworkVersions.get(i);

				scFrameworkVersionPKs[i] = scFrameworkVersion.getPrimaryKey();
			}

			setSCFrameworkVersions(pk, scFrameworkVersionPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return SCProductVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the s c product version persistence.
	 */
	public void afterPropertiesSet() {
		scProductVersionToSCFrameworkVersionTableMapper = TableMapperFactory.getTableMapper("SCFrameworkVersi_SCProductVers",
				"companyId", "productVersionId", "frameworkVersionId", this,
				scFrameworkVersionPersistence);
	}

	public void destroy() {
		EntityCacheUtil.removeCache(SCProductVersionImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		TableMapperFactory.removeTableMapper("SCFrameworkVersi_SCProductVers");
	}

	@BeanReference(type = SCFrameworkVersionPersistence.class)
	protected SCFrameworkVersionPersistence scFrameworkVersionPersistence;
	protected TableMapper<SCProductVersion, com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scProductVersionToSCFrameworkVersionTableMapper;
	private static final String _SQL_SELECT_SCPRODUCTVERSION = "SELECT scProductVersion FROM SCProductVersion scProductVersion";
	private static final String _SQL_SELECT_SCPRODUCTVERSION_WHERE_PKS_IN = "SELECT scProductVersion FROM SCProductVersion scProductVersion WHERE productVersionId IN (";
	private static final String _SQL_SELECT_SCPRODUCTVERSION_WHERE = "SELECT scProductVersion FROM SCProductVersion scProductVersion WHERE ";
	private static final String _SQL_COUNT_SCPRODUCTVERSION = "SELECT COUNT(scProductVersion) FROM SCProductVersion scProductVersion";
	private static final String _SQL_COUNT_SCPRODUCTVERSION_WHERE = "SELECT COUNT(scProductVersion) FROM SCProductVersion scProductVersion WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "scProductVersion.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SCProductVersion exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SCProductVersion exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(SCProductVersionPersistenceImpl.class);
	private static final SCProductVersion _nullSCProductVersion = new SCProductVersionImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<SCProductVersion> toCacheModel() {
				return _nullSCProductVersionCacheModel;
			}
		};

	private static final CacheModel<SCProductVersion> _nullSCProductVersionCacheModel =
		new CacheModel<SCProductVersion>() {
			@Override
			public SCProductVersion toEntityModel() {
				return _nullSCProductVersion;
			}
		};
}