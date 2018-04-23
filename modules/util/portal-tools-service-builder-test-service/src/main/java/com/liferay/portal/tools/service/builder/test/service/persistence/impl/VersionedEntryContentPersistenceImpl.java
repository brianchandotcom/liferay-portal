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

package com.liferay.portal.tools.service.builder.test.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentException;
import com.liferay.portal.tools.service.builder.test.model.VersionedEntryContent;
import com.liferay.portal.tools.service.builder.test.model.impl.VersionedEntryContentImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.VersionedEntryContentModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.VersionedEntryContentPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the versioned entry content service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see VersionedEntryContentPersistence
 * @see com.liferay.portal.tools.service.builder.test.service.persistence.VersionedEntryContentUtil
 * @generated
 */
@ProviderType
public class VersionedEntryContentPersistenceImpl extends BasePersistenceImpl<VersionedEntryContent>
	implements VersionedEntryContentPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link VersionedEntryContentUtil} to access the versioned entry content persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = VersionedEntryContentImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(VersionedEntryContentModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentModelImpl.FINDER_CACHE_ENABLED,
			VersionedEntryContentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(VersionedEntryContentModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentModelImpl.FINDER_CACHE_ENABLED,
			VersionedEntryContentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(VersionedEntryContentModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_VERSIONEDENTRYID =
		new FinderPath(VersionedEntryContentModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentModelImpl.FINDER_CACHE_ENABLED,
			VersionedEntryContentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByVersionedEntryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VERSIONEDENTRYID =
		new FinderPath(VersionedEntryContentModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentModelImpl.FINDER_CACHE_ENABLED,
			VersionedEntryContentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByVersionedEntryId", new String[] { Long.class.getName() },
			VersionedEntryContentModelImpl.VERSIONEDENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_VERSIONEDENTRYID = new FinderPath(VersionedEntryContentModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByVersionedEntryId", new String[] { Long.class.getName() });

	/**
	 * Returns all the versioned entry contents where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @return the matching versioned entry contents
	 */
	@Override
	public List<VersionedEntryContent> findByVersionedEntryId(
		long versionedEntryId) {
		return findByVersionedEntryId(versionedEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the versioned entry contents where versionedEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param start the lower bound of the range of versioned entry contents
	 * @param end the upper bound of the range of versioned entry contents (not inclusive)
	 * @return the range of matching versioned entry contents
	 */
	@Override
	public List<VersionedEntryContent> findByVersionedEntryId(
		long versionedEntryId, int start, int end) {
		return findByVersionedEntryId(versionedEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the versioned entry contents where versionedEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param start the lower bound of the range of versioned entry contents
	 * @param end the upper bound of the range of versioned entry contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching versioned entry contents
	 */
	@Override
	public List<VersionedEntryContent> findByVersionedEntryId(
		long versionedEntryId, int start, int end,
		OrderByComparator<VersionedEntryContent> orderByComparator) {
		return findByVersionedEntryId(versionedEntryId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the versioned entry contents where versionedEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param start the lower bound of the range of versioned entry contents
	 * @param end the upper bound of the range of versioned entry contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching versioned entry contents
	 */
	@Override
	public List<VersionedEntryContent> findByVersionedEntryId(
		long versionedEntryId, int start, int end,
		OrderByComparator<VersionedEntryContent> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VERSIONEDENTRYID;
			finderArgs = new Object[] { versionedEntryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_VERSIONEDENTRYID;
			finderArgs = new Object[] {
					versionedEntryId,
					
					start, end, orderByComparator
				};
		}

		List<VersionedEntryContent> list = null;

		if (retrieveFromCache) {
			list = (List<VersionedEntryContent>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (VersionedEntryContent versionedEntryContent : list) {
					if ((versionedEntryId != versionedEntryContent.getVersionedEntryId())) {
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
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_VERSIONEDENTRYCONTENT_WHERE);

			query.append(_FINDER_COLUMN_VERSIONEDENTRYID_VERSIONEDENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(VersionedEntryContentModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(versionedEntryId);

				if (!pagination) {
					list = (List<VersionedEntryContent>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<VersionedEntryContent>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first versioned entry content in the ordered set where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry content
	 * @throws NoSuchVersionedEntryContentException if a matching versioned entry content could not be found
	 */
	@Override
	public VersionedEntryContent findByVersionedEntryId_First(
		long versionedEntryId,
		OrderByComparator<VersionedEntryContent> orderByComparator)
		throws NoSuchVersionedEntryContentException {
		VersionedEntryContent versionedEntryContent = fetchByVersionedEntryId_First(versionedEntryId,
				orderByComparator);

		if (versionedEntryContent != null) {
			return versionedEntryContent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("versionedEntryId=");
		msg.append(versionedEntryId);

		msg.append("}");

		throw new NoSuchVersionedEntryContentException(msg.toString());
	}

	/**
	 * Returns the first versioned entry content in the ordered set where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry content, or <code>null</code> if a matching versioned entry content could not be found
	 */
	@Override
	public VersionedEntryContent fetchByVersionedEntryId_First(
		long versionedEntryId,
		OrderByComparator<VersionedEntryContent> orderByComparator) {
		List<VersionedEntryContent> list = findByVersionedEntryId(versionedEntryId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last versioned entry content in the ordered set where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching versioned entry content
	 * @throws NoSuchVersionedEntryContentException if a matching versioned entry content could not be found
	 */
	@Override
	public VersionedEntryContent findByVersionedEntryId_Last(
		long versionedEntryId,
		OrderByComparator<VersionedEntryContent> orderByComparator)
		throws NoSuchVersionedEntryContentException {
		VersionedEntryContent versionedEntryContent = fetchByVersionedEntryId_Last(versionedEntryId,
				orderByComparator);

		if (versionedEntryContent != null) {
			return versionedEntryContent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("versionedEntryId=");
		msg.append(versionedEntryId);

		msg.append("}");

		throw new NoSuchVersionedEntryContentException(msg.toString());
	}

	/**
	 * Returns the last versioned entry content in the ordered set where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching versioned entry content, or <code>null</code> if a matching versioned entry content could not be found
	 */
	@Override
	public VersionedEntryContent fetchByVersionedEntryId_Last(
		long versionedEntryId,
		OrderByComparator<VersionedEntryContent> orderByComparator) {
		int count = countByVersionedEntryId(versionedEntryId);

		if (count == 0) {
			return null;
		}

		List<VersionedEntryContent> list = findByVersionedEntryId(versionedEntryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the versioned entry contents before and after the current versioned entry content in the ordered set where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryContentId the primary key of the current versioned entry content
	 * @param versionedEntryId the versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next versioned entry content
	 * @throws NoSuchVersionedEntryContentException if a versioned entry content with the primary key could not be found
	 */
	@Override
	public VersionedEntryContent[] findByVersionedEntryId_PrevAndNext(
		long versionedEntryContentId, long versionedEntryId,
		OrderByComparator<VersionedEntryContent> orderByComparator)
		throws NoSuchVersionedEntryContentException {
		VersionedEntryContent versionedEntryContent = findByPrimaryKey(versionedEntryContentId);

		Session session = null;

		try {
			session = openSession();

			VersionedEntryContent[] array = new VersionedEntryContentImpl[3];

			array[0] = getByVersionedEntryId_PrevAndNext(session,
					versionedEntryContent, versionedEntryId, orderByComparator,
					true);

			array[1] = versionedEntryContent;

			array[2] = getByVersionedEntryId_PrevAndNext(session,
					versionedEntryContent, versionedEntryId, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected VersionedEntryContent getByVersionedEntryId_PrevAndNext(
		Session session, VersionedEntryContent versionedEntryContent,
		long versionedEntryId,
		OrderByComparator<VersionedEntryContent> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_VERSIONEDENTRYCONTENT_WHERE);

		query.append(_FINDER_COLUMN_VERSIONEDENTRYID_VERSIONEDENTRYID_2);

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
			query.append(VersionedEntryContentModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(versionedEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(versionedEntryContent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<VersionedEntryContent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the versioned entry contents where versionedEntryId = &#63; from the database.
	 *
	 * @param versionedEntryId the versioned entry ID
	 */
	@Override
	public void removeByVersionedEntryId(long versionedEntryId) {
		for (VersionedEntryContent versionedEntryContent : findByVersionedEntryId(
				versionedEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(versionedEntryContent);
		}
	}

	/**
	 * Returns the number of versioned entry contents where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @return the number of matching versioned entry contents
	 */
	@Override
	public int countByVersionedEntryId(long versionedEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_VERSIONEDENTRYID;

		Object[] finderArgs = new Object[] { versionedEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_VERSIONEDENTRYCONTENT_WHERE);

			query.append(_FINDER_COLUMN_VERSIONEDENTRYID_VERSIONEDENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(versionedEntryId);

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

	private static final String _FINDER_COLUMN_VERSIONEDENTRYID_VERSIONEDENTRYID_2 =
		"versionedEntryContent.versionedEntryId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_VERSIONEDENTRYID_LANGUAGEID =
		new FinderPath(VersionedEntryContentModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentModelImpl.FINDER_CACHE_ENABLED,
			VersionedEntryContentImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByVersionedEntryId_LanguageId",
			new String[] { Long.class.getName(), String.class.getName() },
			VersionedEntryContentModelImpl.VERSIONEDENTRYID_COLUMN_BITMASK |
			VersionedEntryContentModelImpl.LANGUAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_VERSIONEDENTRYID_LANGUAGEID =
		new FinderPath(VersionedEntryContentModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByVersionedEntryId_LanguageId",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the versioned entry content where versionedEntryId = &#63; and languageId = &#63; or throws a {@link NoSuchVersionedEntryContentException} if it could not be found.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param languageId the language ID
	 * @return the matching versioned entry content
	 * @throws NoSuchVersionedEntryContentException if a matching versioned entry content could not be found
	 */
	@Override
	public VersionedEntryContent findByVersionedEntryId_LanguageId(
		long versionedEntryId, String languageId)
		throws NoSuchVersionedEntryContentException {
		VersionedEntryContent versionedEntryContent = fetchByVersionedEntryId_LanguageId(versionedEntryId,
				languageId);

		if (versionedEntryContent == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("versionedEntryId=");
			msg.append(versionedEntryId);

			msg.append(", languageId=");
			msg.append(languageId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchVersionedEntryContentException(msg.toString());
		}

		return versionedEntryContent;
	}

	/**
	 * Returns the versioned entry content where versionedEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param languageId the language ID
	 * @return the matching versioned entry content, or <code>null</code> if a matching versioned entry content could not be found
	 */
	@Override
	public VersionedEntryContent fetchByVersionedEntryId_LanguageId(
		long versionedEntryId, String languageId) {
		return fetchByVersionedEntryId_LanguageId(versionedEntryId, languageId,
			true);
	}

	/**
	 * Returns the versioned entry content where versionedEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param languageId the language ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching versioned entry content, or <code>null</code> if a matching versioned entry content could not be found
	 */
	@Override
	public VersionedEntryContent fetchByVersionedEntryId_LanguageId(
		long versionedEntryId, String languageId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { versionedEntryId, languageId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_VERSIONEDENTRYID_LANGUAGEID,
					finderArgs, this);
		}

		if (result instanceof VersionedEntryContent) {
			VersionedEntryContent versionedEntryContent = (VersionedEntryContent)result;

			if ((versionedEntryId != versionedEntryContent.getVersionedEntryId()) ||
					!Objects.equals(languageId,
						versionedEntryContent.getLanguageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_VERSIONEDENTRYCONTENT_WHERE);

			query.append(_FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_VERSIONEDENTRYID_2);

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_LANGUAGEID_1);
			}
			else if (languageId.equals("")) {
				query.append(_FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_LANGUAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(versionedEntryId);

				if (bindLanguageId) {
					qPos.add(languageId);
				}

				List<VersionedEntryContent> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_VERSIONEDENTRYID_LANGUAGEID,
						finderArgs, list);
				}
				else {
					VersionedEntryContent versionedEntryContent = list.get(0);

					result = versionedEntryContent;

					cacheResult(versionedEntryContent);

					if ((versionedEntryContent.getVersionedEntryId() != versionedEntryId) ||
							(versionedEntryContent.getLanguageId() == null) ||
							!versionedEntryContent.getLanguageId()
													  .equals(languageId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_VERSIONEDENTRYID_LANGUAGEID,
							finderArgs, versionedEntryContent);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_VERSIONEDENTRYID_LANGUAGEID,
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
			return (VersionedEntryContent)result;
		}
	}

	/**
	 * Removes the versioned entry content where versionedEntryId = &#63; and languageId = &#63; from the database.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param languageId the language ID
	 * @return the versioned entry content that was removed
	 */
	@Override
	public VersionedEntryContent removeByVersionedEntryId_LanguageId(
		long versionedEntryId, String languageId)
		throws NoSuchVersionedEntryContentException {
		VersionedEntryContent versionedEntryContent = findByVersionedEntryId_LanguageId(versionedEntryId,
				languageId);

		return remove(versionedEntryContent);
	}

	/**
	 * Returns the number of versioned entry contents where versionedEntryId = &#63; and languageId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param languageId the language ID
	 * @return the number of matching versioned entry contents
	 */
	@Override
	public int countByVersionedEntryId_LanguageId(long versionedEntryId,
		String languageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_VERSIONEDENTRYID_LANGUAGEID;

		Object[] finderArgs = new Object[] { versionedEntryId, languageId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_VERSIONEDENTRYCONTENT_WHERE);

			query.append(_FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_VERSIONEDENTRYID_2);

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_LANGUAGEID_1);
			}
			else if (languageId.equals("")) {
				query.append(_FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_LANGUAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(versionedEntryId);

				if (bindLanguageId) {
					qPos.add(languageId);
				}

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

	private static final String _FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_VERSIONEDENTRYID_2 =
		"versionedEntryContent.versionedEntryId = ? AND ";
	private static final String _FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_LANGUAGEID_1 =
		"versionedEntryContent.languageId IS NULL";
	private static final String _FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_LANGUAGEID_2 =
		"versionedEntryContent.languageId = ?";
	private static final String _FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_LANGUAGEID_3 =
		"(versionedEntryContent.languageId IS NULL OR versionedEntryContent.languageId = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_HEADID = new FinderPath(VersionedEntryContentModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentModelImpl.FINDER_CACHE_ENABLED,
			VersionedEntryContentImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByHeadId", new String[] { Long.class.getName() },
			VersionedEntryContentModelImpl.HEADID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_HEADID = new FinderPath(VersionedEntryContentModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByHeadId",
			new String[] { Long.class.getName() });

	/**
	 * Returns the versioned entry content where headId = &#63; or throws a {@link NoSuchVersionedEntryContentException} if it could not be found.
	 *
	 * @param headId the head ID
	 * @return the matching versioned entry content
	 * @throws NoSuchVersionedEntryContentException if a matching versioned entry content could not be found
	 */
	@Override
	public VersionedEntryContent findByHeadId(long headId)
		throws NoSuchVersionedEntryContentException {
		VersionedEntryContent versionedEntryContent = fetchByHeadId(headId);

		if (versionedEntryContent == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("headId=");
			msg.append(headId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchVersionedEntryContentException(msg.toString());
		}

		return versionedEntryContent;
	}

	/**
	 * Returns the versioned entry content where headId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param headId the head ID
	 * @return the matching versioned entry content, or <code>null</code> if a matching versioned entry content could not be found
	 */
	@Override
	public VersionedEntryContent fetchByHeadId(long headId) {
		return fetchByHeadId(headId, true);
	}

	/**
	 * Returns the versioned entry content where headId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param headId the head ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching versioned entry content, or <code>null</code> if a matching versioned entry content could not be found
	 */
	@Override
	public VersionedEntryContent fetchByHeadId(long headId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { headId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_HEADID,
					finderArgs, this);
		}

		if (result instanceof VersionedEntryContent) {
			VersionedEntryContent versionedEntryContent = (VersionedEntryContent)result;

			if ((headId != versionedEntryContent.getHeadId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_VERSIONEDENTRYCONTENT_WHERE);

			query.append(_FINDER_COLUMN_HEADID_HEADID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(headId);

				List<VersionedEntryContent> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_HEADID,
						finderArgs, list);
				}
				else {
					VersionedEntryContent versionedEntryContent = list.get(0);

					result = versionedEntryContent;

					cacheResult(versionedEntryContent);

					if ((versionedEntryContent.getHeadId() != headId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_HEADID,
							finderArgs, versionedEntryContent);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_HEADID, finderArgs);

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
			return (VersionedEntryContent)result;
		}
	}

	/**
	 * Removes the versioned entry content where headId = &#63; from the database.
	 *
	 * @param headId the head ID
	 * @return the versioned entry content that was removed
	 */
	@Override
	public VersionedEntryContent removeByHeadId(long headId)
		throws NoSuchVersionedEntryContentException {
		VersionedEntryContent versionedEntryContent = findByHeadId(headId);

		return remove(versionedEntryContent);
	}

	/**
	 * Returns the number of versioned entry contents where headId = &#63;.
	 *
	 * @param headId the head ID
	 * @return the number of matching versioned entry contents
	 */
	@Override
	public int countByHeadId(long headId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_HEADID;

		Object[] finderArgs = new Object[] { headId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_VERSIONEDENTRYCONTENT_WHERE);

			query.append(_FINDER_COLUMN_HEADID_HEADID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(headId);

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

	private static final String _FINDER_COLUMN_HEADID_HEADID_2 = "versionedEntryContent.headId = ?";

	public VersionedEntryContentPersistenceImpl() {
		setModelClass(VersionedEntryContent.class);
	}

	/**
	 * Caches the versioned entry content in the entity cache if it is enabled.
	 *
	 * @param versionedEntryContent the versioned entry content
	 */
	@Override
	public void cacheResult(VersionedEntryContent versionedEntryContent) {
		entityCache.putResult(VersionedEntryContentModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentImpl.class,
			versionedEntryContent.getPrimaryKey(), versionedEntryContent);

		finderCache.putResult(FINDER_PATH_FETCH_BY_VERSIONEDENTRYID_LANGUAGEID,
			new Object[] {
				versionedEntryContent.getVersionedEntryId(),
				versionedEntryContent.getLanguageId()
			}, versionedEntryContent);

		finderCache.putResult(FINDER_PATH_FETCH_BY_HEADID,
			new Object[] { versionedEntryContent.getHeadId() },
			versionedEntryContent);

		versionedEntryContent.resetOriginalValues();
	}

	/**
	 * Caches the versioned entry contents in the entity cache if it is enabled.
	 *
	 * @param versionedEntryContents the versioned entry contents
	 */
	@Override
	public void cacheResult(List<VersionedEntryContent> versionedEntryContents) {
		for (VersionedEntryContent versionedEntryContent : versionedEntryContents) {
			if (entityCache.getResult(
						VersionedEntryContentModelImpl.ENTITY_CACHE_ENABLED,
						VersionedEntryContentImpl.class,
						versionedEntryContent.getPrimaryKey()) == null) {
				cacheResult(versionedEntryContent);
			}
			else {
				versionedEntryContent.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all versioned entry contents.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(VersionedEntryContentImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the versioned entry content.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(VersionedEntryContent versionedEntryContent) {
		entityCache.removeResult(VersionedEntryContentModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentImpl.class,
			versionedEntryContent.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((VersionedEntryContentModelImpl)versionedEntryContent,
			true);
	}

	@Override
	public void clearCache(List<VersionedEntryContent> versionedEntryContents) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (VersionedEntryContent versionedEntryContent : versionedEntryContents) {
			entityCache.removeResult(VersionedEntryContentModelImpl.ENTITY_CACHE_ENABLED,
				VersionedEntryContentImpl.class,
				versionedEntryContent.getPrimaryKey());

			clearUniqueFindersCache((VersionedEntryContentModelImpl)versionedEntryContent,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		VersionedEntryContentModelImpl versionedEntryContentModelImpl) {
		Object[] args = new Object[] {
				versionedEntryContentModelImpl.getVersionedEntryId(),
				versionedEntryContentModelImpl.getLanguageId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_VERSIONEDENTRYID_LANGUAGEID,
			args, Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_VERSIONEDENTRYID_LANGUAGEID,
			args, versionedEntryContentModelImpl, false);

		args = new Object[] { versionedEntryContentModelImpl.getHeadId() };

		finderCache.putResult(FINDER_PATH_COUNT_BY_HEADID, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_HEADID, args,
			versionedEntryContentModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		VersionedEntryContentModelImpl versionedEntryContentModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					versionedEntryContentModelImpl.getVersionedEntryId(),
					versionedEntryContentModelImpl.getLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_VERSIONEDENTRYID_LANGUAGEID,
				args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_VERSIONEDENTRYID_LANGUAGEID,
				args);
		}

		if ((versionedEntryContentModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_VERSIONEDENTRYID_LANGUAGEID.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					versionedEntryContentModelImpl.getOriginalVersionedEntryId(),
					versionedEntryContentModelImpl.getOriginalLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_VERSIONEDENTRYID_LANGUAGEID,
				args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_VERSIONEDENTRYID_LANGUAGEID,
				args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					versionedEntryContentModelImpl.getHeadId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_HEADID, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_HEADID, args);
		}

		if ((versionedEntryContentModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_HEADID.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					versionedEntryContentModelImpl.getOriginalHeadId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_HEADID, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_HEADID, args);
		}
	}

	/**
	 * Creates a new versioned entry content with the primary key. Does not add the versioned entry content to the database.
	 *
	 * @param versionedEntryContentId the primary key for the new versioned entry content
	 * @return the new versioned entry content
	 */
	@Override
	public VersionedEntryContent create(long versionedEntryContentId) {
		VersionedEntryContent versionedEntryContent = new VersionedEntryContentImpl();

		versionedEntryContent.setNew(true);
		versionedEntryContent.setPrimaryKey(versionedEntryContentId);

		return versionedEntryContent;
	}

	/**
	 * Removes the versioned entry content with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param versionedEntryContentId the primary key of the versioned entry content
	 * @return the versioned entry content that was removed
	 * @throws NoSuchVersionedEntryContentException if a versioned entry content with the primary key could not be found
	 */
	@Override
	public VersionedEntryContent remove(long versionedEntryContentId)
		throws NoSuchVersionedEntryContentException {
		return remove((Serializable)versionedEntryContentId);
	}

	/**
	 * Removes the versioned entry content with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the versioned entry content
	 * @return the versioned entry content that was removed
	 * @throws NoSuchVersionedEntryContentException if a versioned entry content with the primary key could not be found
	 */
	@Override
	public VersionedEntryContent remove(Serializable primaryKey)
		throws NoSuchVersionedEntryContentException {
		Session session = null;

		try {
			session = openSession();

			VersionedEntryContent versionedEntryContent = (VersionedEntryContent)session.get(VersionedEntryContentImpl.class,
					primaryKey);

			if (versionedEntryContent == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchVersionedEntryContentException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(versionedEntryContent);
		}
		catch (NoSuchVersionedEntryContentException nsee) {
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
	protected VersionedEntryContent removeImpl(
		VersionedEntryContent versionedEntryContent) {
		versionedEntryContent = toUnwrappedModel(versionedEntryContent);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(versionedEntryContent)) {
				versionedEntryContent = (VersionedEntryContent)session.get(VersionedEntryContentImpl.class,
						versionedEntryContent.getPrimaryKeyObj());
			}

			if (versionedEntryContent != null) {
				session.delete(versionedEntryContent);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (versionedEntryContent != null) {
			clearCache(versionedEntryContent);
		}

		return versionedEntryContent;
	}

	@Override
	public VersionedEntryContent updateImpl(
		VersionedEntryContent versionedEntryContent) {
		versionedEntryContent = toUnwrappedModel(versionedEntryContent);

		boolean isNew = versionedEntryContent.isNew();

		VersionedEntryContentModelImpl versionedEntryContentModelImpl = (VersionedEntryContentModelImpl)versionedEntryContent;

		Session session = null;

		try {
			session = openSession();

			if (versionedEntryContent.isNew()) {
				session.save(versionedEntryContent);

				versionedEntryContent.setNew(false);
			}
			else {
				versionedEntryContent = (VersionedEntryContent)session.merge(versionedEntryContent);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!VersionedEntryContentModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					versionedEntryContentModelImpl.getVersionedEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_VERSIONEDENTRYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VERSIONEDENTRYID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((versionedEntryContentModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VERSIONEDENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						versionedEntryContentModelImpl.getOriginalVersionedEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_VERSIONEDENTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VERSIONEDENTRYID,
					args);

				args = new Object[] {
						versionedEntryContentModelImpl.getVersionedEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_VERSIONEDENTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VERSIONEDENTRYID,
					args);
			}
		}

		entityCache.putResult(VersionedEntryContentModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentImpl.class,
			versionedEntryContent.getPrimaryKey(), versionedEntryContent, false);

		clearUniqueFindersCache(versionedEntryContentModelImpl, false);
		cacheUniqueFindersCache(versionedEntryContentModelImpl);

		versionedEntryContent.resetOriginalValues();

		return versionedEntryContent;
	}

	protected VersionedEntryContent toUnwrappedModel(
		VersionedEntryContent versionedEntryContent) {
		if (versionedEntryContent instanceof VersionedEntryContentImpl) {
			return versionedEntryContent;
		}

		VersionedEntryContentImpl versionedEntryContentImpl = new VersionedEntryContentImpl();

		versionedEntryContentImpl.setNew(versionedEntryContent.isNew());
		versionedEntryContentImpl.setPrimaryKey(versionedEntryContent.getPrimaryKey());

		versionedEntryContentImpl.setMvccVersion(versionedEntryContent.getMvccVersion());
		versionedEntryContentImpl.setVersionedEntryContentId(versionedEntryContent.getVersionedEntryContentId());
		versionedEntryContentImpl.setVersionedEntryId(versionedEntryContent.getVersionedEntryId());
		versionedEntryContentImpl.setLanguageId(versionedEntryContent.getLanguageId());
		versionedEntryContentImpl.setContent(versionedEntryContent.getContent());
		versionedEntryContentImpl.setHeadId(versionedEntryContent.getHeadId());

		return versionedEntryContentImpl;
	}

	/**
	 * Returns the versioned entry content with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the versioned entry content
	 * @return the versioned entry content
	 * @throws NoSuchVersionedEntryContentException if a versioned entry content with the primary key could not be found
	 */
	@Override
	public VersionedEntryContent findByPrimaryKey(Serializable primaryKey)
		throws NoSuchVersionedEntryContentException {
		VersionedEntryContent versionedEntryContent = fetchByPrimaryKey(primaryKey);

		if (versionedEntryContent == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchVersionedEntryContentException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return versionedEntryContent;
	}

	/**
	 * Returns the versioned entry content with the primary key or throws a {@link NoSuchVersionedEntryContentException} if it could not be found.
	 *
	 * @param versionedEntryContentId the primary key of the versioned entry content
	 * @return the versioned entry content
	 * @throws NoSuchVersionedEntryContentException if a versioned entry content with the primary key could not be found
	 */
	@Override
	public VersionedEntryContent findByPrimaryKey(long versionedEntryContentId)
		throws NoSuchVersionedEntryContentException {
		return findByPrimaryKey((Serializable)versionedEntryContentId);
	}

	/**
	 * Returns the versioned entry content with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the versioned entry content
	 * @return the versioned entry content, or <code>null</code> if a versioned entry content with the primary key could not be found
	 */
	@Override
	public VersionedEntryContent fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(VersionedEntryContentModelImpl.ENTITY_CACHE_ENABLED,
				VersionedEntryContentImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		VersionedEntryContent versionedEntryContent = (VersionedEntryContent)serializable;

		if (versionedEntryContent == null) {
			Session session = null;

			try {
				session = openSession();

				versionedEntryContent = (VersionedEntryContent)session.get(VersionedEntryContentImpl.class,
						primaryKey);

				if (versionedEntryContent != null) {
					cacheResult(versionedEntryContent);
				}
				else {
					entityCache.putResult(VersionedEntryContentModelImpl.ENTITY_CACHE_ENABLED,
						VersionedEntryContentImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(VersionedEntryContentModelImpl.ENTITY_CACHE_ENABLED,
					VersionedEntryContentImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return versionedEntryContent;
	}

	/**
	 * Returns the versioned entry content with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param versionedEntryContentId the primary key of the versioned entry content
	 * @return the versioned entry content, or <code>null</code> if a versioned entry content with the primary key could not be found
	 */
	@Override
	public VersionedEntryContent fetchByPrimaryKey(long versionedEntryContentId) {
		return fetchByPrimaryKey((Serializable)versionedEntryContentId);
	}

	@Override
	public Map<Serializable, VersionedEntryContent> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, VersionedEntryContent> map = new HashMap<Serializable, VersionedEntryContent>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			VersionedEntryContent versionedEntryContent = fetchByPrimaryKey(primaryKey);

			if (versionedEntryContent != null) {
				map.put(primaryKey, versionedEntryContent);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(VersionedEntryContentModelImpl.ENTITY_CACHE_ENABLED,
					VersionedEntryContentImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (VersionedEntryContent)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_VERSIONEDENTRYCONTENT_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (VersionedEntryContent versionedEntryContent : (List<VersionedEntryContent>)q.list()) {
				map.put(versionedEntryContent.getPrimaryKeyObj(),
					versionedEntryContent);

				cacheResult(versionedEntryContent);

				uncachedPrimaryKeys.remove(versionedEntryContent.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(VersionedEntryContentModelImpl.ENTITY_CACHE_ENABLED,
					VersionedEntryContentImpl.class, primaryKey, nullModel);
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
	 * Returns all the versioned entry contents.
	 *
	 * @return the versioned entry contents
	 */
	@Override
	public List<VersionedEntryContent> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the versioned entry contents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of versioned entry contents
	 * @param end the upper bound of the range of versioned entry contents (not inclusive)
	 * @return the range of versioned entry contents
	 */
	@Override
	public List<VersionedEntryContent> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the versioned entry contents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of versioned entry contents
	 * @param end the upper bound of the range of versioned entry contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of versioned entry contents
	 */
	@Override
	public List<VersionedEntryContent> findAll(int start, int end,
		OrderByComparator<VersionedEntryContent> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the versioned entry contents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of versioned entry contents
	 * @param end the upper bound of the range of versioned entry contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of versioned entry contents
	 */
	@Override
	public List<VersionedEntryContent> findAll(int start, int end,
		OrderByComparator<VersionedEntryContent> orderByComparator,
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

		List<VersionedEntryContent> list = null;

		if (retrieveFromCache) {
			list = (List<VersionedEntryContent>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_VERSIONEDENTRYCONTENT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_VERSIONEDENTRYCONTENT;

				if (pagination) {
					sql = sql.concat(VersionedEntryContentModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<VersionedEntryContent>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<VersionedEntryContent>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes all the versioned entry contents from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (VersionedEntryContent versionedEntryContent : findAll()) {
			remove(versionedEntryContent);
		}
	}

	/**
	 * Returns the number of versioned entry contents.
	 *
	 * @return the number of versioned entry contents
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_VERSIONEDENTRYCONTENT);

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
	protected Map<String, Integer> getTableColumnsMap() {
		return VersionedEntryContentModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the versioned entry content persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(VersionedEntryContentImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_VERSIONEDENTRYCONTENT = "SELECT versionedEntryContent FROM VersionedEntryContent versionedEntryContent";
	private static final String _SQL_SELECT_VERSIONEDENTRYCONTENT_WHERE_PKS_IN = "SELECT versionedEntryContent FROM VersionedEntryContent versionedEntryContent WHERE versionedEntryContentId IN (";
	private static final String _SQL_SELECT_VERSIONEDENTRYCONTENT_WHERE = "SELECT versionedEntryContent FROM VersionedEntryContent versionedEntryContent WHERE ";
	private static final String _SQL_COUNT_VERSIONEDENTRYCONTENT = "SELECT COUNT(versionedEntryContent) FROM VersionedEntryContent versionedEntryContent";
	private static final String _SQL_COUNT_VERSIONEDENTRYCONTENT_WHERE = "SELECT COUNT(versionedEntryContent) FROM VersionedEntryContent versionedEntryContent WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "versionedEntryContent.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No VersionedEntryContent exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No VersionedEntryContent exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(VersionedEntryContentPersistenceImpl.class);
}