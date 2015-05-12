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

package com.liferay.portlet.dynamicdatamapping.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureLinkImpl;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureLinkModelImpl;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMStructureLinkPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the d d m structure link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLinkPersistence
 * @see com.liferay.portlet.dynamicdatamapping.service.persistence.DDMStructureLinkUtil
 * @generated
 */
@ProviderType
public class DDMStructureLinkPersistenceImpl extends BasePersistenceImpl<DDMStructureLink>
	implements DDMStructureLinkPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DDMStructureLinkUtil} to access the d d m structure link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DDMStructureLinkImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLinkModelImpl.FINDER_CACHE_ENABLED,
			DDMStructureLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLinkModelImpl.FINDER_CACHE_ENABLED,
			DDMStructureLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_STRUCTUREID =
		new FinderPath(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLinkModelImpl.FINDER_CACHE_ENABLED,
			DDMStructureLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByStructureId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID =
		new FinderPath(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLinkModelImpl.FINDER_CACHE_ENABLED,
			DDMStructureLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByStructureId",
			new String[] { Long.class.getName() },
			DDMStructureLinkModelImpl.STRUCTUREID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_STRUCTUREID = new FinderPath(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByStructureId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the d d m structure links where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the matching d d m structure links
	 */
	@Override
	public List<DDMStructureLink> findByStructureId(long structureId) {
		return findByStructureId(structureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structure links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of d d m structure links
	 * @param end the upper bound of the range of d d m structure links (not inclusive)
	 * @return the range of matching d d m structure links
	 */
	@Override
	public List<DDMStructureLink> findByStructureId(long structureId,
		int start, int end) {
		return findByStructureId(structureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structure links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of d d m structure links
	 * @param end the upper bound of the range of d d m structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structure links
	 */
	@Override
	public List<DDMStructureLink> findByStructureId(long structureId,
		int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID;
			finderArgs = new Object[] { structureId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_STRUCTUREID;
			finderArgs = new Object[] { structureId, start, end, orderByComparator };
		}

		List<DDMStructureLink> list = (List<DDMStructureLink>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDMStructureLink ddmStructureLink : list) {
				if ((structureId != ddmStructureLink.getStructureId())) {
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

			query.append(_SQL_SELECT_DDMSTRUCTURELINK_WHERE);

			query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMStructureLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(structureId);

				if (!pagination) {
					list = (List<DDMStructureLink>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMStructureLink>)QueryUtil.list(q,
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
	 * Returns the first d d m structure link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure link
	 * @throws NoSuchStructureLinkException if a matching d d m structure link could not be found
	 */
	@Override
	public DDMStructureLink findByStructureId_First(long structureId,
		OrderByComparator<DDMStructureLink> orderByComparator)
		throws NoSuchStructureLinkException {
		DDMStructureLink ddmStructureLink = fetchByStructureId_First(structureId,
				orderByComparator);

		if (ddmStructureLink != null) {
			return ddmStructureLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("structureId=");
		msg.append(structureId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureLinkException(msg.toString());
	}

	/**
	 * Returns the first d d m structure link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	 */
	@Override
	public DDMStructureLink fetchByStructureId_First(long structureId,
		OrderByComparator<DDMStructureLink> orderByComparator) {
		List<DDMStructureLink> list = findByStructureId(structureId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m structure link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure link
	 * @throws NoSuchStructureLinkException if a matching d d m structure link could not be found
	 */
	@Override
	public DDMStructureLink findByStructureId_Last(long structureId,
		OrderByComparator<DDMStructureLink> orderByComparator)
		throws NoSuchStructureLinkException {
		DDMStructureLink ddmStructureLink = fetchByStructureId_Last(structureId,
				orderByComparator);

		if (ddmStructureLink != null) {
			return ddmStructureLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("structureId=");
		msg.append(structureId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureLinkException(msg.toString());
	}

	/**
	 * Returns the last d d m structure link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	 */
	@Override
	public DDMStructureLink fetchByStructureId_Last(long structureId,
		OrderByComparator<DDMStructureLink> orderByComparator) {
		int count = countByStructureId(structureId);

		if (count == 0) {
			return null;
		}

		List<DDMStructureLink> list = findByStructureId(structureId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m structure links before and after the current d d m structure link in the ordered set where structureId = &#63;.
	 *
	 * @param structureLinkId the primary key of the current d d m structure link
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure link
	 * @throws NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	 */
	@Override
	public DDMStructureLink[] findByStructureId_PrevAndNext(
		long structureLinkId, long structureId,
		OrderByComparator<DDMStructureLink> orderByComparator)
		throws NoSuchStructureLinkException {
		DDMStructureLink ddmStructureLink = findByPrimaryKey(structureLinkId);

		Session session = null;

		try {
			session = openSession();

			DDMStructureLink[] array = new DDMStructureLinkImpl[3];

			array[0] = getByStructureId_PrevAndNext(session, ddmStructureLink,
					structureId, orderByComparator, true);

			array[1] = ddmStructureLink;

			array[2] = getByStructureId_PrevAndNext(session, ddmStructureLink,
					structureId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMStructureLink getByStructureId_PrevAndNext(Session session,
		DDMStructureLink ddmStructureLink, long structureId,
		OrderByComparator<DDMStructureLink> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTRUCTURELINK_WHERE);

		query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);

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
			query.append(DDMStructureLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(structureId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStructureLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructureLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m structure links where structureId = &#63; from the database.
	 *
	 * @param structureId the structure ID
	 */
	@Override
	public void removeByStructureId(long structureId) {
		for (DDMStructureLink ddmStructureLink : findByStructureId(
				structureId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmStructureLink);
		}
	}

	/**
	 * Returns the number of d d m structure links where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the number of matching d d m structure links
	 */
	@Override
	public int countByStructureId(long structureId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_STRUCTUREID;

		Object[] finderArgs = new Object[] { structureId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMSTRUCTURELINK_WHERE);

			query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(structureId);

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

	private static final String _FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2 = "ddmStructureLink.structureId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C = new FinderPath(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLinkModelImpl.FINDER_CACHE_ENABLED,
			DDMStructureLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C = new FinderPath(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLinkModelImpl.FINDER_CACHE_ENABLED,
			DDMStructureLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			DDMStructureLinkModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			DDMStructureLinkModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the d d m structure links where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching d d m structure links
	 */
	@Override
	public List<DDMStructureLink> findByC_C(long classNameId, long classPK) {
		return findByC_C(classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structure links where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m structure links
	 * @param end the upper bound of the range of d d m structure links (not inclusive)
	 * @return the range of matching d d m structure links
	 */
	@Override
	public List<DDMStructureLink> findByC_C(long classNameId, long classPK,
		int start, int end) {
		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structure links where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of d d m structure links
	 * @param end the upper bound of the range of d d m structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structure links
	 */
	@Override
	public List<DDMStructureLink> findByC_C(long classNameId, long classPK,
		int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] { classNameId, classPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] {
					classNameId, classPK,
					
					start, end, orderByComparator
				};
		}

		List<DDMStructureLink> list = (List<DDMStructureLink>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDMStructureLink ddmStructureLink : list) {
				if ((classNameId != ddmStructureLink.getClassNameId()) ||
						(classPK != ddmStructureLink.getClassPK())) {
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

			query.append(_SQL_SELECT_DDMSTRUCTURELINK_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMStructureLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (!pagination) {
					list = (List<DDMStructureLink>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMStructureLink>)QueryUtil.list(q,
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
	 * Returns the first d d m structure link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure link
	 * @throws NoSuchStructureLinkException if a matching d d m structure link could not be found
	 */
	@Override
	public DDMStructureLink findByC_C_First(long classNameId, long classPK,
		OrderByComparator<DDMStructureLink> orderByComparator)
		throws NoSuchStructureLinkException {
		DDMStructureLink ddmStructureLink = fetchByC_C_First(classNameId,
				classPK, orderByComparator);

		if (ddmStructureLink != null) {
			return ddmStructureLink;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureLinkException(msg.toString());
	}

	/**
	 * Returns the first d d m structure link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	 */
	@Override
	public DDMStructureLink fetchByC_C_First(long classNameId, long classPK,
		OrderByComparator<DDMStructureLink> orderByComparator) {
		List<DDMStructureLink> list = findByC_C(classNameId, classPK, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m structure link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure link
	 * @throws NoSuchStructureLinkException if a matching d d m structure link could not be found
	 */
	@Override
	public DDMStructureLink findByC_C_Last(long classNameId, long classPK,
		OrderByComparator<DDMStructureLink> orderByComparator)
		throws NoSuchStructureLinkException {
		DDMStructureLink ddmStructureLink = fetchByC_C_Last(classNameId,
				classPK, orderByComparator);

		if (ddmStructureLink != null) {
			return ddmStructureLink;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureLinkException(msg.toString());
	}

	/**
	 * Returns the last d d m structure link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	 */
	@Override
	public DDMStructureLink fetchByC_C_Last(long classNameId, long classPK,
		OrderByComparator<DDMStructureLink> orderByComparator) {
		int count = countByC_C(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<DDMStructureLink> list = findByC_C(classNameId, classPK,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m structure links before and after the current d d m structure link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param structureLinkId the primary key of the current d d m structure link
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure link
	 * @throws NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	 */
	@Override
	public DDMStructureLink[] findByC_C_PrevAndNext(long structureLinkId,
		long classNameId, long classPK,
		OrderByComparator<DDMStructureLink> orderByComparator)
		throws NoSuchStructureLinkException {
		DDMStructureLink ddmStructureLink = findByPrimaryKey(structureLinkId);

		Session session = null;

		try {
			session = openSession();

			DDMStructureLink[] array = new DDMStructureLinkImpl[3];

			array[0] = getByC_C_PrevAndNext(session, ddmStructureLink,
					classNameId, classPK, orderByComparator, true);

			array[1] = ddmStructureLink;

			array[2] = getByC_C_PrevAndNext(session, ddmStructureLink,
					classNameId, classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMStructureLink getByC_C_PrevAndNext(Session session,
		DDMStructureLink ddmStructureLink, long classNameId, long classPK,
		OrderByComparator<DDMStructureLink> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTRUCTURELINK_WHERE);

		query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

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
			query.append(DDMStructureLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStructureLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructureLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m structure links where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 */
	@Override
	public void removeByC_C(long classNameId, long classPK) {
		for (DDMStructureLink ddmStructureLink : findByC_C(classNameId,
				classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmStructureLink);
		}
	}

	/**
	 * Returns the number of d d m structure links where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching d d m structure links
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C;

		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMSTRUCTURELINK_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "ddmStructureLink.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "ddmStructureLink.classPK = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C_S = new FinderPath(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLinkModelImpl.FINDER_CACHE_ENABLED,
			DDMStructureLinkImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			DDMStructureLinkModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			DDMStructureLinkModelImpl.CLASSPK_COLUMN_BITMASK |
			DDMStructureLinkModelImpl.STRUCTUREID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_S = new FinderPath(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns the d d m structure link where classNameId = &#63; and classPK = &#63; and structureId = &#63; or throws a {@link NoSuchStructureLinkException} if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param structureId the structure ID
	 * @return the matching d d m structure link
	 * @throws NoSuchStructureLinkException if a matching d d m structure link could not be found
	 */
	@Override
	public DDMStructureLink findByC_C_S(long classNameId, long classPK,
		long structureId) throws NoSuchStructureLinkException {
		DDMStructureLink ddmStructureLink = fetchByC_C_S(classNameId, classPK,
				structureId);

		if (ddmStructureLink == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", structureId=");
			msg.append(structureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchStructureLinkException(msg.toString());
		}

		return ddmStructureLink;
	}

	/**
	 * Returns the d d m structure link where classNameId = &#63; and classPK = &#63; and structureId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param structureId the structure ID
	 * @return the matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	 */
	@Override
	public DDMStructureLink fetchByC_C_S(long classNameId, long classPK,
		long structureId) {
		return fetchByC_C_S(classNameId, classPK, structureId, true);
	}

	/**
	 * Returns the d d m structure link where classNameId = &#63; and classPK = &#63; and structureId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param structureId the structure ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	 */
	@Override
	public DDMStructureLink fetchByC_C_S(long classNameId, long classPK,
		long structureId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { classNameId, classPK, structureId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_C_S,
					finderArgs, this);
		}

		if (result instanceof DDMStructureLink) {
			DDMStructureLink ddmStructureLink = (DDMStructureLink)result;

			if ((classNameId != ddmStructureLink.getClassNameId()) ||
					(classPK != ddmStructureLink.getClassPK()) ||
					(structureId != ddmStructureLink.getStructureId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_DDMSTRUCTURELINK_WHERE);

			query.append(_FINDER_COLUMN_C_C_S_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_S_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_S_STRUCTUREID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(structureId);

				List<DDMStructureLink> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_S,
						finderArgs, list);
				}
				else {
					DDMStructureLink ddmStructureLink = list.get(0);

					result = ddmStructureLink;

					cacheResult(ddmStructureLink);

					if ((ddmStructureLink.getClassNameId() != classNameId) ||
							(ddmStructureLink.getClassPK() != classPK) ||
							(ddmStructureLink.getStructureId() != structureId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_S,
							finderArgs, ddmStructureLink);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C_S,
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
			return (DDMStructureLink)result;
		}
	}

	/**
	 * Removes the d d m structure link where classNameId = &#63; and classPK = &#63; and structureId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param structureId the structure ID
	 * @return the d d m structure link that was removed
	 */
	@Override
	public DDMStructureLink removeByC_C_S(long classNameId, long classPK,
		long structureId) throws NoSuchStructureLinkException {
		DDMStructureLink ddmStructureLink = findByC_C_S(classNameId, classPK,
				structureId);

		return remove(ddmStructureLink);
	}

	/**
	 * Returns the number of d d m structure links where classNameId = &#63; and classPK = &#63; and structureId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param structureId the structure ID
	 * @return the number of matching d d m structure links
	 */
	@Override
	public int countByC_C_S(long classNameId, long classPK, long structureId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C_S;

		Object[] finderArgs = new Object[] { classNameId, classPK, structureId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DDMSTRUCTURELINK_WHERE);

			query.append(_FINDER_COLUMN_C_C_S_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_S_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_S_STRUCTUREID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(structureId);

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

	private static final String _FINDER_COLUMN_C_C_S_CLASSNAMEID_2 = "ddmStructureLink.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_S_CLASSPK_2 = "ddmStructureLink.classPK = ? AND ";
	private static final String _FINDER_COLUMN_C_C_S_STRUCTUREID_2 = "ddmStructureLink.structureId = ?";

	public DDMStructureLinkPersistenceImpl() {
		setModelClass(DDMStructureLink.class);
	}

	/**
	 * Caches the d d m structure link in the entity cache if it is enabled.
	 *
	 * @param ddmStructureLink the d d m structure link
	 */
	@Override
	public void cacheResult(DDMStructureLink ddmStructureLink) {
		EntityCacheUtil.putResult(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLinkImpl.class, ddmStructureLink.getPrimaryKey(),
			ddmStructureLink);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_S,
			new Object[] {
				ddmStructureLink.getClassNameId(), ddmStructureLink.getClassPK(),
				ddmStructureLink.getStructureId()
			}, ddmStructureLink);

		ddmStructureLink.resetOriginalValues();
	}

	/**
	 * Caches the d d m structure links in the entity cache if it is enabled.
	 *
	 * @param ddmStructureLinks the d d m structure links
	 */
	@Override
	public void cacheResult(List<DDMStructureLink> ddmStructureLinks) {
		for (DDMStructureLink ddmStructureLink : ddmStructureLinks) {
			if (EntityCacheUtil.getResult(
						DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
						DDMStructureLinkImpl.class,
						ddmStructureLink.getPrimaryKey()) == null) {
				cacheResult(ddmStructureLink);
			}
			else {
				ddmStructureLink.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all d d m structure links.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(DDMStructureLinkImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the d d m structure link.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DDMStructureLink ddmStructureLink) {
		EntityCacheUtil.removeResult(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLinkImpl.class, ddmStructureLink.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(ddmStructureLink);
	}

	@Override
	public void clearCache(List<DDMStructureLink> ddmStructureLinks) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DDMStructureLink ddmStructureLink : ddmStructureLinks) {
			EntityCacheUtil.removeResult(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
				DDMStructureLinkImpl.class, ddmStructureLink.getPrimaryKey());

			clearUniqueFindersCache(ddmStructureLink);
		}
	}

	protected void cacheUniqueFindersCache(DDMStructureLink ddmStructureLink) {
		if (ddmStructureLink.isNew()) {
			Object[] args = new Object[] {
					ddmStructureLink.getClassNameId(),
					ddmStructureLink.getClassPK(),
					ddmStructureLink.getStructureId()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C_S, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_S, args,
				ddmStructureLink);
		}
		else {
			DDMStructureLinkModelImpl ddmStructureLinkModelImpl = (DDMStructureLinkModelImpl)ddmStructureLink;

			if ((ddmStructureLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_C_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmStructureLink.getClassNameId(),
						ddmStructureLink.getClassPK(),
						ddmStructureLink.getStructureId()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C_S, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_S, args,
					ddmStructureLink);
			}
		}
	}

	protected void clearUniqueFindersCache(DDMStructureLink ddmStructureLink) {
		DDMStructureLinkModelImpl ddmStructureLinkModelImpl = (DDMStructureLinkModelImpl)ddmStructureLink;

		Object[] args = new Object[] {
				ddmStructureLink.getClassNameId(), ddmStructureLink.getClassPK(),
				ddmStructureLink.getStructureId()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C_S, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C_S, args);

		if ((ddmStructureLinkModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_C_S.getColumnBitmask()) != 0) {
			args = new Object[] {
					ddmStructureLinkModelImpl.getOriginalClassNameId(),
					ddmStructureLinkModelImpl.getOriginalClassPK(),
					ddmStructureLinkModelImpl.getOriginalStructureId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C_S, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C_S, args);
		}
	}

	/**
	 * Creates a new d d m structure link with the primary key. Does not add the d d m structure link to the database.
	 *
	 * @param structureLinkId the primary key for the new d d m structure link
	 * @return the new d d m structure link
	 */
	@Override
	public DDMStructureLink create(long structureLinkId) {
		DDMStructureLink ddmStructureLink = new DDMStructureLinkImpl();

		ddmStructureLink.setNew(true);
		ddmStructureLink.setPrimaryKey(structureLinkId);

		return ddmStructureLink;
	}

	/**
	 * Removes the d d m structure link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param structureLinkId the primary key of the d d m structure link
	 * @return the d d m structure link that was removed
	 * @throws NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	 */
	@Override
	public DDMStructureLink remove(long structureLinkId)
		throws NoSuchStructureLinkException {
		return remove((Serializable)structureLinkId);
	}

	/**
	 * Removes the d d m structure link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the d d m structure link
	 * @return the d d m structure link that was removed
	 * @throws NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	 */
	@Override
	public DDMStructureLink remove(Serializable primaryKey)
		throws NoSuchStructureLinkException {
		Session session = null;

		try {
			session = openSession();

			DDMStructureLink ddmStructureLink = (DDMStructureLink)session.get(DDMStructureLinkImpl.class,
					primaryKey);

			if (ddmStructureLink == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchStructureLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(ddmStructureLink);
		}
		catch (NoSuchStructureLinkException nsee) {
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
	protected DDMStructureLink removeImpl(DDMStructureLink ddmStructureLink) {
		ddmStructureLink = toUnwrappedModel(ddmStructureLink);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ddmStructureLink)) {
				ddmStructureLink = (DDMStructureLink)session.get(DDMStructureLinkImpl.class,
						ddmStructureLink.getPrimaryKeyObj());
			}

			if (ddmStructureLink != null) {
				session.delete(ddmStructureLink);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (ddmStructureLink != null) {
			clearCache(ddmStructureLink);
		}

		return ddmStructureLink;
	}

	@Override
	public DDMStructureLink updateImpl(DDMStructureLink ddmStructureLink) {
		ddmStructureLink = toUnwrappedModel(ddmStructureLink);

		boolean isNew = ddmStructureLink.isNew();

		DDMStructureLinkModelImpl ddmStructureLinkModelImpl = (DDMStructureLinkModelImpl)ddmStructureLink;

		Session session = null;

		try {
			session = openSession();

			if (ddmStructureLink.isNew()) {
				session.save(ddmStructureLink);

				ddmStructureLink.setNew(false);
			}
			else {
				session.merge(ddmStructureLink);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !DDMStructureLinkModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((ddmStructureLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmStructureLinkModelImpl.getOriginalStructureId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_STRUCTUREID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID,
					args);

				args = new Object[] { ddmStructureLinkModelImpl.getStructureId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_STRUCTUREID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID,
					args);
			}

			if ((ddmStructureLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmStructureLinkModelImpl.getOriginalClassNameId(),
						ddmStructureLinkModelImpl.getOriginalClassPK()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);

				args = new Object[] {
						ddmStructureLinkModelImpl.getClassNameId(),
						ddmStructureLinkModelImpl.getClassPK()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);
			}
		}

		EntityCacheUtil.putResult(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLinkImpl.class, ddmStructureLink.getPrimaryKey(),
			ddmStructureLink, false);

		clearUniqueFindersCache(ddmStructureLink);
		cacheUniqueFindersCache(ddmStructureLink);

		ddmStructureLink.resetOriginalValues();

		return ddmStructureLink;
	}

	protected DDMStructureLink toUnwrappedModel(
		DDMStructureLink ddmStructureLink) {
		if (ddmStructureLink instanceof DDMStructureLinkImpl) {
			return ddmStructureLink;
		}

		DDMStructureLinkImpl ddmStructureLinkImpl = new DDMStructureLinkImpl();

		ddmStructureLinkImpl.setNew(ddmStructureLink.isNew());
		ddmStructureLinkImpl.setPrimaryKey(ddmStructureLink.getPrimaryKey());

		ddmStructureLinkImpl.setStructureLinkId(ddmStructureLink.getStructureLinkId());
		ddmStructureLinkImpl.setClassNameId(ddmStructureLink.getClassNameId());
		ddmStructureLinkImpl.setClassPK(ddmStructureLink.getClassPK());
		ddmStructureLinkImpl.setStructureId(ddmStructureLink.getStructureId());

		return ddmStructureLinkImpl;
	}

	/**
	 * Returns the d d m structure link with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m structure link
	 * @return the d d m structure link
	 * @throws NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	 */
	@Override
	public DDMStructureLink findByPrimaryKey(Serializable primaryKey)
		throws NoSuchStructureLinkException {
		DDMStructureLink ddmStructureLink = fetchByPrimaryKey(primaryKey);

		if (ddmStructureLink == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchStructureLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return ddmStructureLink;
	}

	/**
	 * Returns the d d m structure link with the primary key or throws a {@link NoSuchStructureLinkException} if it could not be found.
	 *
	 * @param structureLinkId the primary key of the d d m structure link
	 * @return the d d m structure link
	 * @throws NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	 */
	@Override
	public DDMStructureLink findByPrimaryKey(long structureLinkId)
		throws NoSuchStructureLinkException {
		return findByPrimaryKey((Serializable)structureLinkId);
	}

	/**
	 * Returns the d d m structure link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m structure link
	 * @return the d d m structure link, or <code>null</code> if a d d m structure link with the primary key could not be found
	 */
	@Override
	public DDMStructureLink fetchByPrimaryKey(Serializable primaryKey) {
		DDMStructureLink ddmStructureLink = (DDMStructureLink)EntityCacheUtil.getResult(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
				DDMStructureLinkImpl.class, primaryKey);

		if (ddmStructureLink == _nullDDMStructureLink) {
			return null;
		}

		if (ddmStructureLink == null) {
			Session session = null;

			try {
				session = openSession();

				ddmStructureLink = (DDMStructureLink)session.get(DDMStructureLinkImpl.class,
						primaryKey);

				if (ddmStructureLink != null) {
					cacheResult(ddmStructureLink);
				}
				else {
					EntityCacheUtil.putResult(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
						DDMStructureLinkImpl.class, primaryKey,
						_nullDDMStructureLink);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
					DDMStructureLinkImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return ddmStructureLink;
	}

	/**
	 * Returns the d d m structure link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param structureLinkId the primary key of the d d m structure link
	 * @return the d d m structure link, or <code>null</code> if a d d m structure link with the primary key could not be found
	 */
	@Override
	public DDMStructureLink fetchByPrimaryKey(long structureLinkId) {
		return fetchByPrimaryKey((Serializable)structureLinkId);
	}

	@Override
	public Map<Serializable, DDMStructureLink> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DDMStructureLink> map = new HashMap<Serializable, DDMStructureLink>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DDMStructureLink ddmStructureLink = fetchByPrimaryKey(primaryKey);

			if (ddmStructureLink != null) {
				map.put(primaryKey, ddmStructureLink);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			DDMStructureLink ddmStructureLink = (DDMStructureLink)EntityCacheUtil.getResult(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
					DDMStructureLinkImpl.class, primaryKey);

			if (ddmStructureLink == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, ddmStructureLink);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_DDMSTRUCTURELINK_WHERE_PKS_IN);

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

			for (DDMStructureLink ddmStructureLink : (List<DDMStructureLink>)q.list()) {
				map.put(ddmStructureLink.getPrimaryKeyObj(), ddmStructureLink);

				cacheResult(ddmStructureLink);

				uncachedPrimaryKeys.remove(ddmStructureLink.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				EntityCacheUtil.putResult(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
					DDMStructureLinkImpl.class, primaryKey,
					_nullDDMStructureLink);
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
	 * Returns all the d d m structure links.
	 *
	 * @return the d d m structure links
	 */
	@Override
	public List<DDMStructureLink> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structure links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m structure links
	 * @param end the upper bound of the range of d d m structure links (not inclusive)
	 * @return the range of d d m structure links
	 */
	@Override
	public List<DDMStructureLink> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structure links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m structure links
	 * @param end the upper bound of the range of d d m structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of d d m structure links
	 */
	@Override
	public List<DDMStructureLink> findAll(int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator) {
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

		List<DDMStructureLink> list = (List<DDMStructureLink>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DDMSTRUCTURELINK);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DDMSTRUCTURELINK;

				if (pagination) {
					sql = sql.concat(DDMStructureLinkModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DDMStructureLink>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMStructureLink>)QueryUtil.list(q,
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
	 * Removes all the d d m structure links from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DDMStructureLink ddmStructureLink : findAll()) {
			remove(ddmStructureLink);
		}
	}

	/**
	 * Returns the number of d d m structure links.
	 *
	 * @return the number of d d m structure links
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DDMSTRUCTURELINK);

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
	 * Initializes the d d m structure link persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		EntityCacheUtil.removeCache(DDMStructureLinkImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_DDMSTRUCTURELINK = "SELECT ddmStructureLink FROM DDMStructureLink ddmStructureLink";
	private static final String _SQL_SELECT_DDMSTRUCTURELINK_WHERE_PKS_IN = "SELECT ddmStructureLink FROM DDMStructureLink ddmStructureLink WHERE structureLinkId IN (";
	private static final String _SQL_SELECT_DDMSTRUCTURELINK_WHERE = "SELECT ddmStructureLink FROM DDMStructureLink ddmStructureLink WHERE ";
	private static final String _SQL_COUNT_DDMSTRUCTURELINK = "SELECT COUNT(ddmStructureLink) FROM DDMStructureLink ddmStructureLink";
	private static final String _SQL_COUNT_DDMSTRUCTURELINK_WHERE = "SELECT COUNT(ddmStructureLink) FROM DDMStructureLink ddmStructureLink WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ddmStructureLink.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DDMStructureLink exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DDMStructureLink exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(DDMStructureLinkPersistenceImpl.class);
	private static final DDMStructureLink _nullDDMStructureLink = new DDMStructureLinkImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<DDMStructureLink> toCacheModel() {
				return _nullDDMStructureLinkCacheModel;
			}
		};

	private static final CacheModel<DDMStructureLink> _nullDDMStructureLinkCacheModel =
		new CacheModel<DDMStructureLink>() {
			@Override
			public DDMStructureLink toEntityModel() {
				return _nullDDMStructureLink;
			}
		};
}