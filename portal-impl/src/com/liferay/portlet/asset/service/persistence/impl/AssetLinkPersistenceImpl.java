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

package com.liferay.portlet.asset.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
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
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.NoSuchLinkException;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.model.impl.AssetLinkImpl;
import com.liferay.portlet.asset.model.impl.AssetLinkModelImpl;
import com.liferay.portlet.asset.service.persistence.AssetLinkPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the asset link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetLinkPersistence
 * @see com.liferay.portlet.asset.service.persistence.AssetLinkUtil
 * @generated
 */
@ProviderType
public class AssetLinkPersistenceImpl extends BasePersistenceImpl<AssetLink>
	implements AssetLinkPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link AssetLinkUtil} to access the asset link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = AssetLinkImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, AssetLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, AssetLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_E1 = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, AssetLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByE1",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E1 = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, AssetLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByE1",
			new String[] { Long.class.getName() },
			AssetLinkModelImpl.ENTRYID1_COLUMN_BITMASK |
			AssetLinkModelImpl.WEIGHT_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_E1 = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByE1",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the asset links where entryId1 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @return the matching asset links
	 */
	@Override
	public List<AssetLink> findByE1(long entryId1) {
		return findByE1(entryId1, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset links where entryId1 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param entryId1 the entry id1
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @return the range of matching asset links
	 */
	@Override
	public List<AssetLink> findByE1(long entryId1, int start, int end) {
		return findByE1(entryId1, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset links where entryId1 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param entryId1 the entry id1
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset links
	 */
	@Override
	public List<AssetLink> findByE1(long entryId1, int start, int end,
		OrderByComparator<AssetLink> orderByComparator) {
		return findByE1(entryId1, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset links where entryId1 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param entryId1 the entry id1
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset links
	 */
	@Override
	public List<AssetLink> findByE1(long entryId1, int start, int end,
		OrderByComparator<AssetLink> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E1;
			finderArgs = new Object[] { entryId1 };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_E1;
			finderArgs = new Object[] { entryId1, start, end, orderByComparator };
		}

		List<AssetLink> list = null;

		if (retrieveFromCache) {
			list = (List<AssetLink>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetLink assetLink : list) {
					if ((entryId1 != assetLink.getEntryId1())) {
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

			query.append(_SQL_SELECT_ASSETLINK_WHERE);

			query.append(_FINDER_COLUMN_E1_ENTRYID1_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId1);

				if (!pagination) {
					list = (List<AssetLink>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetLink>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset link in the ordered set where entryId1 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link
	 * @throws NoSuchLinkException if a matching asset link could not be found
	 */
	@Override
	public AssetLink findByE1_First(long entryId1,
		OrderByComparator<AssetLink> orderByComparator)
		throws NoSuchLinkException {
		AssetLink assetLink = fetchByE1_First(entryId1, orderByComparator);

		if (assetLink != null) {
			return assetLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId1=");
		msg.append(entryId1);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLinkException(msg.toString());
	}

	/**
	 * Returns the first asset link in the ordered set where entryId1 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link, or <code>null</code> if a matching asset link could not be found
	 */
	@Override
	public AssetLink fetchByE1_First(long entryId1,
		OrderByComparator<AssetLink> orderByComparator) {
		List<AssetLink> list = findByE1(entryId1, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset link in the ordered set where entryId1 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link
	 * @throws NoSuchLinkException if a matching asset link could not be found
	 */
	@Override
	public AssetLink findByE1_Last(long entryId1,
		OrderByComparator<AssetLink> orderByComparator)
		throws NoSuchLinkException {
		AssetLink assetLink = fetchByE1_Last(entryId1, orderByComparator);

		if (assetLink != null) {
			return assetLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId1=");
		msg.append(entryId1);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLinkException(msg.toString());
	}

	/**
	 * Returns the last asset link in the ordered set where entryId1 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link, or <code>null</code> if a matching asset link could not be found
	 */
	@Override
	public AssetLink fetchByE1_Last(long entryId1,
		OrderByComparator<AssetLink> orderByComparator) {
		int count = countByE1(entryId1);

		if (count == 0) {
			return null;
		}

		List<AssetLink> list = findByE1(entryId1, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset links before and after the current asset link in the ordered set where entryId1 = &#63;.
	 *
	 * @param linkId the primary key of the current asset link
	 * @param entryId1 the entry id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset link
	 * @throws NoSuchLinkException if a asset link with the primary key could not be found
	 */
	@Override
	public AssetLink[] findByE1_PrevAndNext(long linkId, long entryId1,
		OrderByComparator<AssetLink> orderByComparator)
		throws NoSuchLinkException {
		AssetLink assetLink = findByPrimaryKey(linkId);

		Session session = null;

		try {
			session = openSession();

			AssetLink[] array = new AssetLinkImpl[3];

			array[0] = getByE1_PrevAndNext(session, assetLink, entryId1,
					orderByComparator, true);

			array[1] = assetLink;

			array[2] = getByE1_PrevAndNext(session, assetLink, entryId1,
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

	protected AssetLink getByE1_PrevAndNext(Session session,
		AssetLink assetLink, long entryId1,
		OrderByComparator<AssetLink> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETLINK_WHERE);

		query.append(_FINDER_COLUMN_E1_ENTRYID1_2);

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
			query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(entryId1);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset links where entryId1 = &#63; from the database.
	 *
	 * @param entryId1 the entry id1
	 */
	@Override
	public void removeByE1(long entryId1) {
		for (AssetLink assetLink : findByE1(entryId1, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(assetLink);
		}
	}

	/**
	 * Returns the number of asset links where entryId1 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @return the number of matching asset links
	 */
	@Override
	public int countByE1(long entryId1) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_E1;

		Object[] finderArgs = new Object[] { entryId1 };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETLINK_WHERE);

			query.append(_FINDER_COLUMN_E1_ENTRYID1_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId1);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_E1_ENTRYID1_2 = "assetLink.entryId1 = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_E2 = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, AssetLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByE2",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E2 = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, AssetLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByE2",
			new String[] { Long.class.getName() },
			AssetLinkModelImpl.ENTRYID2_COLUMN_BITMASK |
			AssetLinkModelImpl.WEIGHT_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_E2 = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByE2",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the asset links where entryId2 = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @return the matching asset links
	 */
	@Override
	public List<AssetLink> findByE2(long entryId2) {
		return findByE2(entryId2, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset links where entryId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param entryId2 the entry id2
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @return the range of matching asset links
	 */
	@Override
	public List<AssetLink> findByE2(long entryId2, int start, int end) {
		return findByE2(entryId2, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset links where entryId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param entryId2 the entry id2
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset links
	 */
	@Override
	public List<AssetLink> findByE2(long entryId2, int start, int end,
		OrderByComparator<AssetLink> orderByComparator) {
		return findByE2(entryId2, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset links where entryId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param entryId2 the entry id2
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset links
	 */
	@Override
	public List<AssetLink> findByE2(long entryId2, int start, int end,
		OrderByComparator<AssetLink> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E2;
			finderArgs = new Object[] { entryId2 };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_E2;
			finderArgs = new Object[] { entryId2, start, end, orderByComparator };
		}

		List<AssetLink> list = null;

		if (retrieveFromCache) {
			list = (List<AssetLink>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetLink assetLink : list) {
					if ((entryId2 != assetLink.getEntryId2())) {
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

			query.append(_SQL_SELECT_ASSETLINK_WHERE);

			query.append(_FINDER_COLUMN_E2_ENTRYID2_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId2);

				if (!pagination) {
					list = (List<AssetLink>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetLink>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset link in the ordered set where entryId2 = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link
	 * @throws NoSuchLinkException if a matching asset link could not be found
	 */
	@Override
	public AssetLink findByE2_First(long entryId2,
		OrderByComparator<AssetLink> orderByComparator)
		throws NoSuchLinkException {
		AssetLink assetLink = fetchByE2_First(entryId2, orderByComparator);

		if (assetLink != null) {
			return assetLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId2=");
		msg.append(entryId2);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLinkException(msg.toString());
	}

	/**
	 * Returns the first asset link in the ordered set where entryId2 = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link, or <code>null</code> if a matching asset link could not be found
	 */
	@Override
	public AssetLink fetchByE2_First(long entryId2,
		OrderByComparator<AssetLink> orderByComparator) {
		List<AssetLink> list = findByE2(entryId2, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset link in the ordered set where entryId2 = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link
	 * @throws NoSuchLinkException if a matching asset link could not be found
	 */
	@Override
	public AssetLink findByE2_Last(long entryId2,
		OrderByComparator<AssetLink> orderByComparator)
		throws NoSuchLinkException {
		AssetLink assetLink = fetchByE2_Last(entryId2, orderByComparator);

		if (assetLink != null) {
			return assetLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId2=");
		msg.append(entryId2);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLinkException(msg.toString());
	}

	/**
	 * Returns the last asset link in the ordered set where entryId2 = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link, or <code>null</code> if a matching asset link could not be found
	 */
	@Override
	public AssetLink fetchByE2_Last(long entryId2,
		OrderByComparator<AssetLink> orderByComparator) {
		int count = countByE2(entryId2);

		if (count == 0) {
			return null;
		}

		List<AssetLink> list = findByE2(entryId2, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset links before and after the current asset link in the ordered set where entryId2 = &#63;.
	 *
	 * @param linkId the primary key of the current asset link
	 * @param entryId2 the entry id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset link
	 * @throws NoSuchLinkException if a asset link with the primary key could not be found
	 */
	@Override
	public AssetLink[] findByE2_PrevAndNext(long linkId, long entryId2,
		OrderByComparator<AssetLink> orderByComparator)
		throws NoSuchLinkException {
		AssetLink assetLink = findByPrimaryKey(linkId);

		Session session = null;

		try {
			session = openSession();

			AssetLink[] array = new AssetLinkImpl[3];

			array[0] = getByE2_PrevAndNext(session, assetLink, entryId2,
					orderByComparator, true);

			array[1] = assetLink;

			array[2] = getByE2_PrevAndNext(session, assetLink, entryId2,
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

	protected AssetLink getByE2_PrevAndNext(Session session,
		AssetLink assetLink, long entryId2,
		OrderByComparator<AssetLink> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETLINK_WHERE);

		query.append(_FINDER_COLUMN_E2_ENTRYID2_2);

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
			query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(entryId2);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset links where entryId2 = &#63; from the database.
	 *
	 * @param entryId2 the entry id2
	 */
	@Override
	public void removeByE2(long entryId2) {
		for (AssetLink assetLink : findByE2(entryId2, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(assetLink);
		}
	}

	/**
	 * Returns the number of asset links where entryId2 = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @return the number of matching asset links
	 */
	@Override
	public int countByE2(long entryId2) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_E2;

		Object[] finderArgs = new Object[] { entryId2 };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETLINK_WHERE);

			query.append(_FINDER_COLUMN_E2_ENTRYID2_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId2);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_E2_ENTRYID2_2 = "assetLink.entryId2 = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_E_E = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, AssetLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByE_E",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E_E = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, AssetLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByE_E",
			new String[] { Long.class.getName(), Long.class.getName() },
			AssetLinkModelImpl.ENTRYID1_COLUMN_BITMASK |
			AssetLinkModelImpl.ENTRYID2_COLUMN_BITMASK |
			AssetLinkModelImpl.WEIGHT_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_E_E = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByE_E",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the asset links where entryId1 = &#63; and entryId2 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @return the matching asset links
	 */
	@Override
	public List<AssetLink> findByE_E(long entryId1, long entryId2) {
		return findByE_E(entryId1, entryId2, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset links where entryId1 = &#63; and entryId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @return the range of matching asset links
	 */
	@Override
	public List<AssetLink> findByE_E(long entryId1, long entryId2, int start,
		int end) {
		return findByE_E(entryId1, entryId2, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset links where entryId1 = &#63; and entryId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset links
	 */
	@Override
	public List<AssetLink> findByE_E(long entryId1, long entryId2, int start,
		int end, OrderByComparator<AssetLink> orderByComparator) {
		return findByE_E(entryId1, entryId2, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset links where entryId1 = &#63; and entryId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset links
	 */
	@Override
	public List<AssetLink> findByE_E(long entryId1, long entryId2, int start,
		int end, OrderByComparator<AssetLink> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E_E;
			finderArgs = new Object[] { entryId1, entryId2 };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_E_E;
			finderArgs = new Object[] {
					entryId1, entryId2,
					
					start, end, orderByComparator
				};
		}

		List<AssetLink> list = null;

		if (retrieveFromCache) {
			list = (List<AssetLink>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetLink assetLink : list) {
					if ((entryId1 != assetLink.getEntryId1()) ||
							(entryId2 != assetLink.getEntryId2())) {
						list = null;

						break;
					}
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

			query.append(_SQL_SELECT_ASSETLINK_WHERE);

			query.append(_FINDER_COLUMN_E_E_ENTRYID1_2);

			query.append(_FINDER_COLUMN_E_E_ENTRYID2_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId1);

				qPos.add(entryId2);

				if (!pagination) {
					list = (List<AssetLink>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetLink>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset link in the ordered set where entryId1 = &#63; and entryId2 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link
	 * @throws NoSuchLinkException if a matching asset link could not be found
	 */
	@Override
	public AssetLink findByE_E_First(long entryId1, long entryId2,
		OrderByComparator<AssetLink> orderByComparator)
		throws NoSuchLinkException {
		AssetLink assetLink = fetchByE_E_First(entryId1, entryId2,
				orderByComparator);

		if (assetLink != null) {
			return assetLink;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId1=");
		msg.append(entryId1);

		msg.append(", entryId2=");
		msg.append(entryId2);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLinkException(msg.toString());
	}

	/**
	 * Returns the first asset link in the ordered set where entryId1 = &#63; and entryId2 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link, or <code>null</code> if a matching asset link could not be found
	 */
	@Override
	public AssetLink fetchByE_E_First(long entryId1, long entryId2,
		OrderByComparator<AssetLink> orderByComparator) {
		List<AssetLink> list = findByE_E(entryId1, entryId2, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset link in the ordered set where entryId1 = &#63; and entryId2 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link
	 * @throws NoSuchLinkException if a matching asset link could not be found
	 */
	@Override
	public AssetLink findByE_E_Last(long entryId1, long entryId2,
		OrderByComparator<AssetLink> orderByComparator)
		throws NoSuchLinkException {
		AssetLink assetLink = fetchByE_E_Last(entryId1, entryId2,
				orderByComparator);

		if (assetLink != null) {
			return assetLink;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId1=");
		msg.append(entryId1);

		msg.append(", entryId2=");
		msg.append(entryId2);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLinkException(msg.toString());
	}

	/**
	 * Returns the last asset link in the ordered set where entryId1 = &#63; and entryId2 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link, or <code>null</code> if a matching asset link could not be found
	 */
	@Override
	public AssetLink fetchByE_E_Last(long entryId1, long entryId2,
		OrderByComparator<AssetLink> orderByComparator) {
		int count = countByE_E(entryId1, entryId2);

		if (count == 0) {
			return null;
		}

		List<AssetLink> list = findByE_E(entryId1, entryId2, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset links before and after the current asset link in the ordered set where entryId1 = &#63; and entryId2 = &#63;.
	 *
	 * @param linkId the primary key of the current asset link
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset link
	 * @throws NoSuchLinkException if a asset link with the primary key could not be found
	 */
	@Override
	public AssetLink[] findByE_E_PrevAndNext(long linkId, long entryId1,
		long entryId2, OrderByComparator<AssetLink> orderByComparator)
		throws NoSuchLinkException {
		AssetLink assetLink = findByPrimaryKey(linkId);

		Session session = null;

		try {
			session = openSession();

			AssetLink[] array = new AssetLinkImpl[3];

			array[0] = getByE_E_PrevAndNext(session, assetLink, entryId1,
					entryId2, orderByComparator, true);

			array[1] = assetLink;

			array[2] = getByE_E_PrevAndNext(session, assetLink, entryId1,
					entryId2, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetLink getByE_E_PrevAndNext(Session session,
		AssetLink assetLink, long entryId1, long entryId2,
		OrderByComparator<AssetLink> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETLINK_WHERE);

		query.append(_FINDER_COLUMN_E_E_ENTRYID1_2);

		query.append(_FINDER_COLUMN_E_E_ENTRYID2_2);

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
			query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(entryId1);

		qPos.add(entryId2);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset links where entryId1 = &#63; and entryId2 = &#63; from the database.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 */
	@Override
	public void removeByE_E(long entryId1, long entryId2) {
		for (AssetLink assetLink : findByE_E(entryId1, entryId2,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(assetLink);
		}
	}

	/**
	 * Returns the number of asset links where entryId1 = &#63; and entryId2 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @return the number of matching asset links
	 */
	@Override
	public int countByE_E(long entryId1, long entryId2) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_E_E;

		Object[] finderArgs = new Object[] { entryId1, entryId2 };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETLINK_WHERE);

			query.append(_FINDER_COLUMN_E_E_ENTRYID1_2);

			query.append(_FINDER_COLUMN_E_E_ENTRYID2_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId1);

				qPos.add(entryId2);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_E_E_ENTRYID1_2 = "assetLink.entryId1 = ? AND ";
	private static final String _FINDER_COLUMN_E_E_ENTRYID2_2 = "assetLink.entryId2 = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_E1_T = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, AssetLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByE1_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E1_T = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, AssetLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByE1_T",
			new String[] { Long.class.getName(), Integer.class.getName() },
			AssetLinkModelImpl.ENTRYID1_COLUMN_BITMASK |
			AssetLinkModelImpl.TYPE_COLUMN_BITMASK |
			AssetLinkModelImpl.WEIGHT_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_E1_T = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByE1_T",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the asset links where entryId1 = &#63; and type = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param type the type
	 * @return the matching asset links
	 */
	@Override
	public List<AssetLink> findByE1_T(long entryId1, int type) {
		return findByE1_T(entryId1, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the asset links where entryId1 = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param entryId1 the entry id1
	 * @param type the type
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @return the range of matching asset links
	 */
	@Override
	public List<AssetLink> findByE1_T(long entryId1, int type, int start,
		int end) {
		return findByE1_T(entryId1, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset links where entryId1 = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param entryId1 the entry id1
	 * @param type the type
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset links
	 */
	@Override
	public List<AssetLink> findByE1_T(long entryId1, int type, int start,
		int end, OrderByComparator<AssetLink> orderByComparator) {
		return findByE1_T(entryId1, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset links where entryId1 = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param entryId1 the entry id1
	 * @param type the type
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset links
	 */
	@Override
	public List<AssetLink> findByE1_T(long entryId1, int type, int start,
		int end, OrderByComparator<AssetLink> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E1_T;
			finderArgs = new Object[] { entryId1, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_E1_T;
			finderArgs = new Object[] {
					entryId1, type,
					
					start, end, orderByComparator
				};
		}

		List<AssetLink> list = null;

		if (retrieveFromCache) {
			list = (List<AssetLink>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetLink assetLink : list) {
					if ((entryId1 != assetLink.getEntryId1()) ||
							(type != assetLink.getType())) {
						list = null;

						break;
					}
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

			query.append(_SQL_SELECT_ASSETLINK_WHERE);

			query.append(_FINDER_COLUMN_E1_T_ENTRYID1_2);

			query.append(_FINDER_COLUMN_E1_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId1);

				qPos.add(type);

				if (!pagination) {
					list = (List<AssetLink>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetLink>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset link in the ordered set where entryId1 = &#63; and type = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link
	 * @throws NoSuchLinkException if a matching asset link could not be found
	 */
	@Override
	public AssetLink findByE1_T_First(long entryId1, int type,
		OrderByComparator<AssetLink> orderByComparator)
		throws NoSuchLinkException {
		AssetLink assetLink = fetchByE1_T_First(entryId1, type,
				orderByComparator);

		if (assetLink != null) {
			return assetLink;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId1=");
		msg.append(entryId1);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLinkException(msg.toString());
	}

	/**
	 * Returns the first asset link in the ordered set where entryId1 = &#63; and type = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link, or <code>null</code> if a matching asset link could not be found
	 */
	@Override
	public AssetLink fetchByE1_T_First(long entryId1, int type,
		OrderByComparator<AssetLink> orderByComparator) {
		List<AssetLink> list = findByE1_T(entryId1, type, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset link in the ordered set where entryId1 = &#63; and type = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link
	 * @throws NoSuchLinkException if a matching asset link could not be found
	 */
	@Override
	public AssetLink findByE1_T_Last(long entryId1, int type,
		OrderByComparator<AssetLink> orderByComparator)
		throws NoSuchLinkException {
		AssetLink assetLink = fetchByE1_T_Last(entryId1, type, orderByComparator);

		if (assetLink != null) {
			return assetLink;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId1=");
		msg.append(entryId1);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLinkException(msg.toString());
	}

	/**
	 * Returns the last asset link in the ordered set where entryId1 = &#63; and type = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link, or <code>null</code> if a matching asset link could not be found
	 */
	@Override
	public AssetLink fetchByE1_T_Last(long entryId1, int type,
		OrderByComparator<AssetLink> orderByComparator) {
		int count = countByE1_T(entryId1, type);

		if (count == 0) {
			return null;
		}

		List<AssetLink> list = findByE1_T(entryId1, type, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset links before and after the current asset link in the ordered set where entryId1 = &#63; and type = &#63;.
	 *
	 * @param linkId the primary key of the current asset link
	 * @param entryId1 the entry id1
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset link
	 * @throws NoSuchLinkException if a asset link with the primary key could not be found
	 */
	@Override
	public AssetLink[] findByE1_T_PrevAndNext(long linkId, long entryId1,
		int type, OrderByComparator<AssetLink> orderByComparator)
		throws NoSuchLinkException {
		AssetLink assetLink = findByPrimaryKey(linkId);

		Session session = null;

		try {
			session = openSession();

			AssetLink[] array = new AssetLinkImpl[3];

			array[0] = getByE1_T_PrevAndNext(session, assetLink, entryId1,
					type, orderByComparator, true);

			array[1] = assetLink;

			array[2] = getByE1_T_PrevAndNext(session, assetLink, entryId1,
					type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetLink getByE1_T_PrevAndNext(Session session,
		AssetLink assetLink, long entryId1, int type,
		OrderByComparator<AssetLink> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETLINK_WHERE);

		query.append(_FINDER_COLUMN_E1_T_ENTRYID1_2);

		query.append(_FINDER_COLUMN_E1_T_TYPE_2);

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
			query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(entryId1);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset links where entryId1 = &#63; and type = &#63; from the database.
	 *
	 * @param entryId1 the entry id1
	 * @param type the type
	 */
	@Override
	public void removeByE1_T(long entryId1, int type) {
		for (AssetLink assetLink : findByE1_T(entryId1, type,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(assetLink);
		}
	}

	/**
	 * Returns the number of asset links where entryId1 = &#63; and type = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param type the type
	 * @return the number of matching asset links
	 */
	@Override
	public int countByE1_T(long entryId1, int type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_E1_T;

		Object[] finderArgs = new Object[] { entryId1, type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETLINK_WHERE);

			query.append(_FINDER_COLUMN_E1_T_ENTRYID1_2);

			query.append(_FINDER_COLUMN_E1_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId1);

				qPos.add(type);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_E1_T_ENTRYID1_2 = "assetLink.entryId1 = ? AND ";
	private static final String _FINDER_COLUMN_E1_T_TYPE_2 = "assetLink.type = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_E2_T = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, AssetLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByE2_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E2_T = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, AssetLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByE2_T",
			new String[] { Long.class.getName(), Integer.class.getName() },
			AssetLinkModelImpl.ENTRYID2_COLUMN_BITMASK |
			AssetLinkModelImpl.TYPE_COLUMN_BITMASK |
			AssetLinkModelImpl.WEIGHT_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_E2_T = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByE2_T",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the asset links where entryId2 = &#63; and type = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @return the matching asset links
	 */
	@Override
	public List<AssetLink> findByE2_T(long entryId2, int type) {
		return findByE2_T(entryId2, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the asset links where entryId2 = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @return the range of matching asset links
	 */
	@Override
	public List<AssetLink> findByE2_T(long entryId2, int type, int start,
		int end) {
		return findByE2_T(entryId2, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset links where entryId2 = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset links
	 */
	@Override
	public List<AssetLink> findByE2_T(long entryId2, int type, int start,
		int end, OrderByComparator<AssetLink> orderByComparator) {
		return findByE2_T(entryId2, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset links where entryId2 = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset links
	 */
	@Override
	public List<AssetLink> findByE2_T(long entryId2, int type, int start,
		int end, OrderByComparator<AssetLink> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E2_T;
			finderArgs = new Object[] { entryId2, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_E2_T;
			finderArgs = new Object[] {
					entryId2, type,
					
					start, end, orderByComparator
				};
		}

		List<AssetLink> list = null;

		if (retrieveFromCache) {
			list = (List<AssetLink>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetLink assetLink : list) {
					if ((entryId2 != assetLink.getEntryId2()) ||
							(type != assetLink.getType())) {
						list = null;

						break;
					}
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

			query.append(_SQL_SELECT_ASSETLINK_WHERE);

			query.append(_FINDER_COLUMN_E2_T_ENTRYID2_2);

			query.append(_FINDER_COLUMN_E2_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId2);

				qPos.add(type);

				if (!pagination) {
					list = (List<AssetLink>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetLink>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset link in the ordered set where entryId2 = &#63; and type = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link
	 * @throws NoSuchLinkException if a matching asset link could not be found
	 */
	@Override
	public AssetLink findByE2_T_First(long entryId2, int type,
		OrderByComparator<AssetLink> orderByComparator)
		throws NoSuchLinkException {
		AssetLink assetLink = fetchByE2_T_First(entryId2, type,
				orderByComparator);

		if (assetLink != null) {
			return assetLink;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId2=");
		msg.append(entryId2);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLinkException(msg.toString());
	}

	/**
	 * Returns the first asset link in the ordered set where entryId2 = &#63; and type = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link, or <code>null</code> if a matching asset link could not be found
	 */
	@Override
	public AssetLink fetchByE2_T_First(long entryId2, int type,
		OrderByComparator<AssetLink> orderByComparator) {
		List<AssetLink> list = findByE2_T(entryId2, type, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset link in the ordered set where entryId2 = &#63; and type = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link
	 * @throws NoSuchLinkException if a matching asset link could not be found
	 */
	@Override
	public AssetLink findByE2_T_Last(long entryId2, int type,
		OrderByComparator<AssetLink> orderByComparator)
		throws NoSuchLinkException {
		AssetLink assetLink = fetchByE2_T_Last(entryId2, type, orderByComparator);

		if (assetLink != null) {
			return assetLink;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId2=");
		msg.append(entryId2);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLinkException(msg.toString());
	}

	/**
	 * Returns the last asset link in the ordered set where entryId2 = &#63; and type = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link, or <code>null</code> if a matching asset link could not be found
	 */
	@Override
	public AssetLink fetchByE2_T_Last(long entryId2, int type,
		OrderByComparator<AssetLink> orderByComparator) {
		int count = countByE2_T(entryId2, type);

		if (count == 0) {
			return null;
		}

		List<AssetLink> list = findByE2_T(entryId2, type, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset links before and after the current asset link in the ordered set where entryId2 = &#63; and type = &#63;.
	 *
	 * @param linkId the primary key of the current asset link
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset link
	 * @throws NoSuchLinkException if a asset link with the primary key could not be found
	 */
	@Override
	public AssetLink[] findByE2_T_PrevAndNext(long linkId, long entryId2,
		int type, OrderByComparator<AssetLink> orderByComparator)
		throws NoSuchLinkException {
		AssetLink assetLink = findByPrimaryKey(linkId);

		Session session = null;

		try {
			session = openSession();

			AssetLink[] array = new AssetLinkImpl[3];

			array[0] = getByE2_T_PrevAndNext(session, assetLink, entryId2,
					type, orderByComparator, true);

			array[1] = assetLink;

			array[2] = getByE2_T_PrevAndNext(session, assetLink, entryId2,
					type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetLink getByE2_T_PrevAndNext(Session session,
		AssetLink assetLink, long entryId2, int type,
		OrderByComparator<AssetLink> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETLINK_WHERE);

		query.append(_FINDER_COLUMN_E2_T_ENTRYID2_2);

		query.append(_FINDER_COLUMN_E2_T_TYPE_2);

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
			query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(entryId2);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset links where entryId2 = &#63; and type = &#63; from the database.
	 *
	 * @param entryId2 the entry id2
	 * @param type the type
	 */
	@Override
	public void removeByE2_T(long entryId2, int type) {
		for (AssetLink assetLink : findByE2_T(entryId2, type,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(assetLink);
		}
	}

	/**
	 * Returns the number of asset links where entryId2 = &#63; and type = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @return the number of matching asset links
	 */
	@Override
	public int countByE2_T(long entryId2, int type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_E2_T;

		Object[] finderArgs = new Object[] { entryId2, type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETLINK_WHERE);

			query.append(_FINDER_COLUMN_E2_T_ENTRYID2_2);

			query.append(_FINDER_COLUMN_E2_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId2);

				qPos.add(type);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_E2_T_ENTRYID2_2 = "assetLink.entryId2 = ? AND ";
	private static final String _FINDER_COLUMN_E2_T_TYPE_2 = "assetLink.type = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_E_E_T = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, AssetLinkImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByE_E_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			AssetLinkModelImpl.ENTRYID1_COLUMN_BITMASK |
			AssetLinkModelImpl.ENTRYID2_COLUMN_BITMASK |
			AssetLinkModelImpl.TYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_E_E_T = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByE_E_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns the asset link where entryId1 = &#63; and entryId2 = &#63; and type = &#63; or throws a {@link NoSuchLinkException} if it could not be found.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @return the matching asset link
	 * @throws NoSuchLinkException if a matching asset link could not be found
	 */
	@Override
	public AssetLink findByE_E_T(long entryId1, long entryId2, int type)
		throws NoSuchLinkException {
		AssetLink assetLink = fetchByE_E_T(entryId1, entryId2, type);

		if (assetLink == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("entryId1=");
			msg.append(entryId1);

			msg.append(", entryId2=");
			msg.append(entryId2);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchLinkException(msg.toString());
		}

		return assetLink;
	}

	/**
	 * Returns the asset link where entryId1 = &#63; and entryId2 = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @return the matching asset link, or <code>null</code> if a matching asset link could not be found
	 */
	@Override
	public AssetLink fetchByE_E_T(long entryId1, long entryId2, int type) {
		return fetchByE_E_T(entryId1, entryId2, type, true);
	}

	/**
	 * Returns the asset link where entryId1 = &#63; and entryId2 = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching asset link, or <code>null</code> if a matching asset link could not be found
	 */
	@Override
	public AssetLink fetchByE_E_T(long entryId1, long entryId2, int type,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { entryId1, entryId2, type };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_E_E_T,
					finderArgs, this);
		}

		if (result instanceof AssetLink) {
			AssetLink assetLink = (AssetLink)result;

			if ((entryId1 != assetLink.getEntryId1()) ||
					(entryId2 != assetLink.getEntryId2()) ||
					(type != assetLink.getType())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_ASSETLINK_WHERE);

			query.append(_FINDER_COLUMN_E_E_T_ENTRYID1_2);

			query.append(_FINDER_COLUMN_E_E_T_ENTRYID2_2);

			query.append(_FINDER_COLUMN_E_E_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId1);

				qPos.add(entryId2);

				qPos.add(type);

				List<AssetLink> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_E_E_T,
						finderArgs, list);
				}
				else {
					AssetLink assetLink = list.get(0);

					result = assetLink;

					cacheResult(assetLink);

					if ((assetLink.getEntryId1() != entryId1) ||
							(assetLink.getEntryId2() != entryId2) ||
							(assetLink.getType() != type)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_E_E_T,
							finderArgs, assetLink);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_E_E_T, finderArgs);

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
			return (AssetLink)result;
		}
	}

	/**
	 * Removes the asset link where entryId1 = &#63; and entryId2 = &#63; and type = &#63; from the database.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @return the asset link that was removed
	 */
	@Override
	public AssetLink removeByE_E_T(long entryId1, long entryId2, int type)
		throws NoSuchLinkException {
		AssetLink assetLink = findByE_E_T(entryId1, entryId2, type);

		return remove(assetLink);
	}

	/**
	 * Returns the number of asset links where entryId1 = &#63; and entryId2 = &#63; and type = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @return the number of matching asset links
	 */
	@Override
	public int countByE_E_T(long entryId1, long entryId2, int type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_E_E_T;

		Object[] finderArgs = new Object[] { entryId1, entryId2, type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_ASSETLINK_WHERE);

			query.append(_FINDER_COLUMN_E_E_T_ENTRYID1_2);

			query.append(_FINDER_COLUMN_E_E_T_ENTRYID2_2);

			query.append(_FINDER_COLUMN_E_E_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId1);

				qPos.add(entryId2);

				qPos.add(type);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_E_E_T_ENTRYID1_2 = "assetLink.entryId1 = ? AND ";
	private static final String _FINDER_COLUMN_E_E_T_ENTRYID2_2 = "assetLink.entryId2 = ? AND ";
	private static final String _FINDER_COLUMN_E_E_T_TYPE_2 = "assetLink.type = ?";

	public AssetLinkPersistenceImpl() {
		setModelClass(AssetLink.class);
	}

	/**
	 * Caches the asset link in the entity cache if it is enabled.
	 *
	 * @param assetLink the asset link
	 */
	@Override
	public void cacheResult(AssetLink assetLink) {
		entityCache.putResult(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkImpl.class, assetLink.getPrimaryKey(), assetLink);

		finderCache.putResult(FINDER_PATH_FETCH_BY_E_E_T,
			new Object[] {
				assetLink.getEntryId1(), assetLink.getEntryId2(),
				assetLink.getType()
			}, assetLink);

		assetLink.resetOriginalValues();
	}

	/**
	 * Caches the asset links in the entity cache if it is enabled.
	 *
	 * @param assetLinks the asset links
	 */
	@Override
	public void cacheResult(List<AssetLink> assetLinks) {
		for (AssetLink assetLink : assetLinks) {
			if (entityCache.getResult(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
						AssetLinkImpl.class, assetLink.getPrimaryKey()) == null) {
				cacheResult(assetLink);
			}
			else {
				assetLink.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asset links.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AssetLinkImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asset link.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetLink assetLink) {
		entityCache.removeResult(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkImpl.class, assetLink.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((AssetLinkModelImpl)assetLink);
	}

	@Override
	public void clearCache(List<AssetLink> assetLinks) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetLink assetLink : assetLinks) {
			entityCache.removeResult(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
				AssetLinkImpl.class, assetLink.getPrimaryKey());

			clearUniqueFindersCache((AssetLinkModelImpl)assetLink);
		}
	}

	protected void cacheUniqueFindersCache(
		AssetLinkModelImpl assetLinkModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					assetLinkModelImpl.getEntryId1(),
					assetLinkModelImpl.getEntryId2(),
					assetLinkModelImpl.getType()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_E_E_T, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_E_E_T, args,
				assetLinkModelImpl);
		}
		else {
			if ((assetLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_E_E_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetLinkModelImpl.getEntryId1(),
						assetLinkModelImpl.getEntryId2(),
						assetLinkModelImpl.getType()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_E_E_T, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_E_E_T, args,
					assetLinkModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		AssetLinkModelImpl assetLinkModelImpl) {
		Object[] args = new Object[] {
				assetLinkModelImpl.getEntryId1(),
				assetLinkModelImpl.getEntryId2(), assetLinkModelImpl.getType()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_E_E_T, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_E_E_T, args);

		if ((assetLinkModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_E_E_T.getColumnBitmask()) != 0) {
			args = new Object[] {
					assetLinkModelImpl.getOriginalEntryId1(),
					assetLinkModelImpl.getOriginalEntryId2(),
					assetLinkModelImpl.getOriginalType()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_E_E_T, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_E_E_T, args);
		}
	}

	/**
	 * Creates a new asset link with the primary key. Does not add the asset link to the database.
	 *
	 * @param linkId the primary key for the new asset link
	 * @return the new asset link
	 */
	@Override
	public AssetLink create(long linkId) {
		AssetLink assetLink = new AssetLinkImpl();

		assetLink.setNew(true);
		assetLink.setPrimaryKey(linkId);

		return assetLink;
	}

	/**
	 * Removes the asset link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param linkId the primary key of the asset link
	 * @return the asset link that was removed
	 * @throws NoSuchLinkException if a asset link with the primary key could not be found
	 */
	@Override
	public AssetLink remove(long linkId) throws NoSuchLinkException {
		return remove((Serializable)linkId);
	}

	/**
	 * Removes the asset link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset link
	 * @return the asset link that was removed
	 * @throws NoSuchLinkException if a asset link with the primary key could not be found
	 */
	@Override
	public AssetLink remove(Serializable primaryKey) throws NoSuchLinkException {
		Session session = null;

		try {
			session = openSession();

			AssetLink assetLink = (AssetLink)session.get(AssetLinkImpl.class,
					primaryKey);

			if (assetLink == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(assetLink);
		}
		catch (NoSuchLinkException nsee) {
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
	protected AssetLink removeImpl(AssetLink assetLink) {
		assetLink = toUnwrappedModel(assetLink);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetLink)) {
				assetLink = (AssetLink)session.get(AssetLinkImpl.class,
						assetLink.getPrimaryKeyObj());
			}

			if (assetLink != null) {
				session.delete(assetLink);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (assetLink != null) {
			clearCache(assetLink);
		}

		return assetLink;
	}

	@Override
	public AssetLink updateImpl(AssetLink assetLink) {
		assetLink = toUnwrappedModel(assetLink);

		boolean isNew = assetLink.isNew();

		AssetLinkModelImpl assetLinkModelImpl = (AssetLinkModelImpl)assetLink;

		Session session = null;

		try {
			session = openSession();

			if (assetLink.isNew()) {
				session.save(assetLink);

				assetLink.setNew(false);
			}
			else {
				assetLink = (AssetLink)session.merge(assetLink);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !AssetLinkModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((assetLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E1.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetLinkModelImpl.getOriginalEntryId1()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_E1, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E1,
					args);

				args = new Object[] { assetLinkModelImpl.getEntryId1() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_E1, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E1,
					args);
			}

			if ((assetLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E2.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetLinkModelImpl.getOriginalEntryId2()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_E2, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E2,
					args);

				args = new Object[] { assetLinkModelImpl.getEntryId2() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_E2, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E2,
					args);
			}

			if ((assetLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E_E.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetLinkModelImpl.getOriginalEntryId1(),
						assetLinkModelImpl.getOriginalEntryId2()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_E_E, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E_E,
					args);

				args = new Object[] {
						assetLinkModelImpl.getEntryId1(),
						assetLinkModelImpl.getEntryId2()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_E_E, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E_E,
					args);
			}

			if ((assetLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E1_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetLinkModelImpl.getOriginalEntryId1(),
						assetLinkModelImpl.getOriginalType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_E1_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E1_T,
					args);

				args = new Object[] {
						assetLinkModelImpl.getEntryId1(),
						assetLinkModelImpl.getType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_E1_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E1_T,
					args);
			}

			if ((assetLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E2_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetLinkModelImpl.getOriginalEntryId2(),
						assetLinkModelImpl.getOriginalType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_E2_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E2_T,
					args);

				args = new Object[] {
						assetLinkModelImpl.getEntryId2(),
						assetLinkModelImpl.getType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_E2_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_E2_T,
					args);
			}
		}

		entityCache.putResult(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkImpl.class, assetLink.getPrimaryKey(), assetLink, false);

		clearUniqueFindersCache(assetLinkModelImpl);
		cacheUniqueFindersCache(assetLinkModelImpl, isNew);

		assetLink.resetOriginalValues();

		return assetLink;
	}

	protected AssetLink toUnwrappedModel(AssetLink assetLink) {
		if (assetLink instanceof AssetLinkImpl) {
			return assetLink;
		}

		AssetLinkImpl assetLinkImpl = new AssetLinkImpl();

		assetLinkImpl.setNew(assetLink.isNew());
		assetLinkImpl.setPrimaryKey(assetLink.getPrimaryKey());

		assetLinkImpl.setLinkId(assetLink.getLinkId());
		assetLinkImpl.setCompanyId(assetLink.getCompanyId());
		assetLinkImpl.setUserId(assetLink.getUserId());
		assetLinkImpl.setUserName(assetLink.getUserName());
		assetLinkImpl.setCreateDate(assetLink.getCreateDate());
		assetLinkImpl.setEntryId1(assetLink.getEntryId1());
		assetLinkImpl.setEntryId2(assetLink.getEntryId2());
		assetLinkImpl.setType(assetLink.getType());
		assetLinkImpl.setWeight(assetLink.getWeight());

		return assetLinkImpl;
	}

	/**
	 * Returns the asset link with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset link
	 * @return the asset link
	 * @throws NoSuchLinkException if a asset link with the primary key could not be found
	 */
	@Override
	public AssetLink findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLinkException {
		AssetLink assetLink = fetchByPrimaryKey(primaryKey);

		if (assetLink == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return assetLink;
	}

	/**
	 * Returns the asset link with the primary key or throws a {@link NoSuchLinkException} if it could not be found.
	 *
	 * @param linkId the primary key of the asset link
	 * @return the asset link
	 * @throws NoSuchLinkException if a asset link with the primary key could not be found
	 */
	@Override
	public AssetLink findByPrimaryKey(long linkId) throws NoSuchLinkException {
		return findByPrimaryKey((Serializable)linkId);
	}

	/**
	 * Returns the asset link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset link
	 * @return the asset link, or <code>null</code> if a asset link with the primary key could not be found
	 */
	@Override
	public AssetLink fetchByPrimaryKey(Serializable primaryKey) {
		AssetLink assetLink = (AssetLink)entityCache.getResult(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
				AssetLinkImpl.class, primaryKey);

		if (assetLink == _nullAssetLink) {
			return null;
		}

		if (assetLink == null) {
			Session session = null;

			try {
				session = openSession();

				assetLink = (AssetLink)session.get(AssetLinkImpl.class,
						primaryKey);

				if (assetLink != null) {
					cacheResult(assetLink);
				}
				else {
					entityCache.putResult(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
						AssetLinkImpl.class, primaryKey, _nullAssetLink);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
					AssetLinkImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return assetLink;
	}

	/**
	 * Returns the asset link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param linkId the primary key of the asset link
	 * @return the asset link, or <code>null</code> if a asset link with the primary key could not be found
	 */
	@Override
	public AssetLink fetchByPrimaryKey(long linkId) {
		return fetchByPrimaryKey((Serializable)linkId);
	}

	@Override
	public Map<Serializable, AssetLink> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AssetLink> map = new HashMap<Serializable, AssetLink>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AssetLink assetLink = fetchByPrimaryKey(primaryKey);

			if (assetLink != null) {
				map.put(primaryKey, assetLink);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			AssetLink assetLink = (AssetLink)entityCache.getResult(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
					AssetLinkImpl.class, primaryKey);

			if (assetLink == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, assetLink);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_ASSETLINK_WHERE_PKS_IN);

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

			for (AssetLink assetLink : (List<AssetLink>)q.list()) {
				map.put(assetLink.getPrimaryKeyObj(), assetLink);

				cacheResult(assetLink);

				uncachedPrimaryKeys.remove(assetLink.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
					AssetLinkImpl.class, primaryKey, _nullAssetLink);
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
	 * Returns all the asset links.
	 *
	 * @return the asset links
	 */
	@Override
	public List<AssetLink> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @return the range of asset links
	 */
	@Override
	public List<AssetLink> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset links
	 */
	@Override
	public List<AssetLink> findAll(int start, int end,
		OrderByComparator<AssetLink> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of asset links
	 */
	@Override
	public List<AssetLink> findAll(int start, int end,
		OrderByComparator<AssetLink> orderByComparator,
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

		List<AssetLink> list = null;

		if (retrieveFromCache) {
			list = (List<AssetLink>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_ASSETLINK);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETLINK;

				if (pagination) {
					sql = sql.concat(AssetLinkModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<AssetLink>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetLink>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the asset links from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AssetLink assetLink : findAll()) {
			remove(assetLink);
		}
	}

	/**
	 * Returns the number of asset links.
	 *
	 * @return the number of asset links
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ASSETLINK);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
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
	protected Map<String, Integer> getTableColumnsMap() {
		return AssetLinkModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the asset link persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(AssetLinkImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_ASSETLINK = "SELECT assetLink FROM AssetLink assetLink";
	private static final String _SQL_SELECT_ASSETLINK_WHERE_PKS_IN = "SELECT assetLink FROM AssetLink assetLink WHERE linkId IN (";
	private static final String _SQL_SELECT_ASSETLINK_WHERE = "SELECT assetLink FROM AssetLink assetLink WHERE ";
	private static final String _SQL_COUNT_ASSETLINK = "SELECT COUNT(assetLink) FROM AssetLink assetLink";
	private static final String _SQL_COUNT_ASSETLINK_WHERE = "SELECT COUNT(assetLink) FROM AssetLink assetLink WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "assetLink.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AssetLink exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AssetLink exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(AssetLinkPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"type"
			});
	private static final AssetLink _nullAssetLink = new AssetLinkImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<AssetLink> toCacheModel() {
				return _nullAssetLinkCacheModel;
			}
		};

	private static final CacheModel<AssetLink> _nullAssetLinkCacheModel = new CacheModel<AssetLink>() {
			@Override
			public AssetLink toEntityModel() {
				return _nullAssetLink;
			}
		};
}