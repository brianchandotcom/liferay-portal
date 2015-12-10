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

package com.liferay.journal.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.journal.exception.NoSuchArticleImageException;
import com.liferay.journal.model.JournalArticleImage;
import com.liferay.journal.model.impl.JournalArticleImageImpl;
import com.liferay.journal.model.impl.JournalArticleImageModelImpl;
import com.liferay.journal.service.persistence.JournalArticleImagePersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the journal article image service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticleImagePersistence
 * @see com.liferay.journal.service.persistence.JournalArticleImageUtil
 * @generated
 */
@ProviderType
public class JournalArticleImagePersistenceImpl extends BasePersistenceImpl<JournalArticleImage>
	implements JournalArticleImagePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link JournalArticleImageUtil} to access the journal article image persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = JournalArticleImageImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			JournalArticleImageModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the journal article images where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching journal article images
	 */
	@Override
	public List<JournalArticleImage> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal article images where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal article images
	 * @param end the upper bound of the range of journal article images (not inclusive)
	 * @return the range of matching journal article images
	 */
	@Override
	public List<JournalArticleImage> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal article images where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal article images
	 * @param end the upper bound of the range of journal article images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal article images
	 */
	@Override
	public List<JournalArticleImage> findByGroupId(long groupId, int start,
		int end, OrderByComparator<JournalArticleImage> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal article images where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal article images
	 * @param end the upper bound of the range of journal article images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching journal article images
	 */
	@Override
	public List<JournalArticleImage> findByGroupId(long groupId, int start,
		int end, OrderByComparator<JournalArticleImage> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<JournalArticleImage> list = null;

		if (retrieveFromCache) {
			list = (List<JournalArticleImage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (JournalArticleImage journalArticleImage : list) {
					if ((groupId != journalArticleImage.getGroupId())) {
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

			query.append(_SQL_SELECT_JOURNALARTICLEIMAGE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleImageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<JournalArticleImage>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticleImage>)QueryUtil.list(q,
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
	 * Returns the first journal article image in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article image
	 * @throws com.liferay.journal.NoSuchArticleImageException if a matching journal article image could not be found
	 */
	@Override
	public JournalArticleImage findByGroupId_First(long groupId,
		OrderByComparator<JournalArticleImage> orderByComparator)
		throws NoSuchArticleImageException {
		JournalArticleImage journalArticleImage = fetchByGroupId_First(groupId,
				orderByComparator);

		if (journalArticleImage != null) {
			return journalArticleImage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleImageException(msg.toString());
	}

	/**
	 * Returns the first journal article image in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article image, or <code>null</code> if a matching journal article image could not be found
	 */
	@Override
	public JournalArticleImage fetchByGroupId_First(long groupId,
		OrderByComparator<JournalArticleImage> orderByComparator) {
		List<JournalArticleImage> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article image in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article image
	 * @throws com.liferay.journal.NoSuchArticleImageException if a matching journal article image could not be found
	 */
	@Override
	public JournalArticleImage findByGroupId_Last(long groupId,
		OrderByComparator<JournalArticleImage> orderByComparator)
		throws NoSuchArticleImageException {
		JournalArticleImage journalArticleImage = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (journalArticleImage != null) {
			return journalArticleImage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleImageException(msg.toString());
	}

	/**
	 * Returns the last journal article image in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article image, or <code>null</code> if a matching journal article image could not be found
	 */
	@Override
	public JournalArticleImage fetchByGroupId_Last(long groupId,
		OrderByComparator<JournalArticleImage> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<JournalArticleImage> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal article images before and after the current journal article image in the ordered set where groupId = &#63;.
	 *
	 * @param articleImageId the primary key of the current journal article image
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article image
	 * @throws com.liferay.journal.NoSuchArticleImageException if a journal article image with the primary key could not be found
	 */
	@Override
	public JournalArticleImage[] findByGroupId_PrevAndNext(
		long articleImageId, long groupId,
		OrderByComparator<JournalArticleImage> orderByComparator)
		throws NoSuchArticleImageException {
		JournalArticleImage journalArticleImage = findByPrimaryKey(articleImageId);

		Session session = null;

		try {
			session = openSession();

			JournalArticleImage[] array = new JournalArticleImageImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, journalArticleImage,
					groupId, orderByComparator, true);

			array[1] = journalArticleImage;

			array[2] = getByGroupId_PrevAndNext(session, journalArticleImage,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticleImage getByGroupId_PrevAndNext(Session session,
		JournalArticleImage journalArticleImage, long groupId,
		OrderByComparator<JournalArticleImage> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLEIMAGE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			query.append(JournalArticleImageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticleImage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticleImage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal article images where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (JournalArticleImage journalArticleImage : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticleImage);
		}
	}

	/**
	 * Returns the number of journal article images where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching journal article images
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALARTICLEIMAGE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "journalArticleImage.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_TEMPIMAGE =
		new FinderPath(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByTempImage",
			new String[] {
				Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TEMPIMAGE =
		new FinderPath(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByTempImage",
			new String[] { Boolean.class.getName() },
			JournalArticleImageModelImpl.TEMPIMAGE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_TEMPIMAGE = new FinderPath(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTempImage",
			new String[] { Boolean.class.getName() });

	/**
	 * Returns all the journal article images where tempImage = &#63;.
	 *
	 * @param tempImage the temp image
	 * @return the matching journal article images
	 */
	@Override
	public List<JournalArticleImage> findByTempImage(boolean tempImage) {
		return findByTempImage(tempImage, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal article images where tempImage = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param tempImage the temp image
	 * @param start the lower bound of the range of journal article images
	 * @param end the upper bound of the range of journal article images (not inclusive)
	 * @return the range of matching journal article images
	 */
	@Override
	public List<JournalArticleImage> findByTempImage(boolean tempImage,
		int start, int end) {
		return findByTempImage(tempImage, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal article images where tempImage = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param tempImage the temp image
	 * @param start the lower bound of the range of journal article images
	 * @param end the upper bound of the range of journal article images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal article images
	 */
	@Override
	public List<JournalArticleImage> findByTempImage(boolean tempImage,
		int start, int end,
		OrderByComparator<JournalArticleImage> orderByComparator) {
		return findByTempImage(tempImage, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal article images where tempImage = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param tempImage the temp image
	 * @param start the lower bound of the range of journal article images
	 * @param end the upper bound of the range of journal article images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching journal article images
	 */
	@Override
	public List<JournalArticleImage> findByTempImage(boolean tempImage,
		int start, int end,
		OrderByComparator<JournalArticleImage> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TEMPIMAGE;
			finderArgs = new Object[] { tempImage };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_TEMPIMAGE;
			finderArgs = new Object[] { tempImage, start, end, orderByComparator };
		}

		List<JournalArticleImage> list = null;

		if (retrieveFromCache) {
			list = (List<JournalArticleImage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (JournalArticleImage journalArticleImage : list) {
					if ((tempImage != journalArticleImage.getTempImage())) {
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

			query.append(_SQL_SELECT_JOURNALARTICLEIMAGE_WHERE);

			query.append(_FINDER_COLUMN_TEMPIMAGE_TEMPIMAGE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleImageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tempImage);

				if (!pagination) {
					list = (List<JournalArticleImage>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticleImage>)QueryUtil.list(q,
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
	 * Returns the first journal article image in the ordered set where tempImage = &#63;.
	 *
	 * @param tempImage the temp image
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article image
	 * @throws com.liferay.journal.NoSuchArticleImageException if a matching journal article image could not be found
	 */
	@Override
	public JournalArticleImage findByTempImage_First(boolean tempImage,
		OrderByComparator<JournalArticleImage> orderByComparator)
		throws NoSuchArticleImageException {
		JournalArticleImage journalArticleImage = fetchByTempImage_First(tempImage,
				orderByComparator);

		if (journalArticleImage != null) {
			return journalArticleImage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("tempImage=");
		msg.append(tempImage);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleImageException(msg.toString());
	}

	/**
	 * Returns the first journal article image in the ordered set where tempImage = &#63;.
	 *
	 * @param tempImage the temp image
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article image, or <code>null</code> if a matching journal article image could not be found
	 */
	@Override
	public JournalArticleImage fetchByTempImage_First(boolean tempImage,
		OrderByComparator<JournalArticleImage> orderByComparator) {
		List<JournalArticleImage> list = findByTempImage(tempImage, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article image in the ordered set where tempImage = &#63;.
	 *
	 * @param tempImage the temp image
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article image
	 * @throws com.liferay.journal.NoSuchArticleImageException if a matching journal article image could not be found
	 */
	@Override
	public JournalArticleImage findByTempImage_Last(boolean tempImage,
		OrderByComparator<JournalArticleImage> orderByComparator)
		throws NoSuchArticleImageException {
		JournalArticleImage journalArticleImage = fetchByTempImage_Last(tempImage,
				orderByComparator);

		if (journalArticleImage != null) {
			return journalArticleImage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("tempImage=");
		msg.append(tempImage);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleImageException(msg.toString());
	}

	/**
	 * Returns the last journal article image in the ordered set where tempImage = &#63;.
	 *
	 * @param tempImage the temp image
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article image, or <code>null</code> if a matching journal article image could not be found
	 */
	@Override
	public JournalArticleImage fetchByTempImage_Last(boolean tempImage,
		OrderByComparator<JournalArticleImage> orderByComparator) {
		int count = countByTempImage(tempImage);

		if (count == 0) {
			return null;
		}

		List<JournalArticleImage> list = findByTempImage(tempImage, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal article images before and after the current journal article image in the ordered set where tempImage = &#63;.
	 *
	 * @param articleImageId the primary key of the current journal article image
	 * @param tempImage the temp image
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article image
	 * @throws com.liferay.journal.NoSuchArticleImageException if a journal article image with the primary key could not be found
	 */
	@Override
	public JournalArticleImage[] findByTempImage_PrevAndNext(
		long articleImageId, boolean tempImage,
		OrderByComparator<JournalArticleImage> orderByComparator)
		throws NoSuchArticleImageException {
		JournalArticleImage journalArticleImage = findByPrimaryKey(articleImageId);

		Session session = null;

		try {
			session = openSession();

			JournalArticleImage[] array = new JournalArticleImageImpl[3];

			array[0] = getByTempImage_PrevAndNext(session, journalArticleImage,
					tempImage, orderByComparator, true);

			array[1] = journalArticleImage;

			array[2] = getByTempImage_PrevAndNext(session, journalArticleImage,
					tempImage, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticleImage getByTempImage_PrevAndNext(Session session,
		JournalArticleImage journalArticleImage, boolean tempImage,
		OrderByComparator<JournalArticleImage> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLEIMAGE_WHERE);

		query.append(_FINDER_COLUMN_TEMPIMAGE_TEMPIMAGE_2);

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
			query.append(JournalArticleImageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(tempImage);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticleImage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticleImage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal article images where tempImage = &#63; from the database.
	 *
	 * @param tempImage the temp image
	 */
	@Override
	public void removeByTempImage(boolean tempImage) {
		for (JournalArticleImage journalArticleImage : findByTempImage(
				tempImage, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticleImage);
		}
	}

	/**
	 * Returns the number of journal article images where tempImage = &#63;.
	 *
	 * @param tempImage the temp image
	 * @return the number of matching journal article images
	 */
	@Override
	public int countByTempImage(boolean tempImage) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_TEMPIMAGE;

		Object[] finderArgs = new Object[] { tempImage };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALARTICLEIMAGE_WHERE);

			query.append(_FINDER_COLUMN_TEMPIMAGE_TEMPIMAGE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tempImage);

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

	private static final String _FINDER_COLUMN_TEMPIMAGE_TEMPIMAGE_2 = "journalArticleImage.tempImage = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A_V = new FinderPath(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_A_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Double.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A_V = new FinderPath(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_A_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Double.class.getName()
			},
			JournalArticleImageModelImpl.GROUPID_COLUMN_BITMASK |
			JournalArticleImageModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleImageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A_V = new FinderPath(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_A_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Double.class.getName()
			});

	/**
	 * Returns all the journal article images where groupId = &#63; and articleId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 * @return the matching journal article images
	 */
	@Override
	public List<JournalArticleImage> findByG_A_V(long groupId,
		String articleId, double version) {
		return findByG_A_V(groupId, articleId, version, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal article images where groupId = &#63; and articleId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 * @param start the lower bound of the range of journal article images
	 * @param end the upper bound of the range of journal article images (not inclusive)
	 * @return the range of matching journal article images
	 */
	@Override
	public List<JournalArticleImage> findByG_A_V(long groupId,
		String articleId, double version, int start, int end) {
		return findByG_A_V(groupId, articleId, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal article images where groupId = &#63; and articleId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 * @param start the lower bound of the range of journal article images
	 * @param end the upper bound of the range of journal article images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal article images
	 */
	@Override
	public List<JournalArticleImage> findByG_A_V(long groupId,
		String articleId, double version, int start, int end,
		OrderByComparator<JournalArticleImage> orderByComparator) {
		return findByG_A_V(groupId, articleId, version, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal article images where groupId = &#63; and articleId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 * @param start the lower bound of the range of journal article images
	 * @param end the upper bound of the range of journal article images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching journal article images
	 */
	@Override
	public List<JournalArticleImage> findByG_A_V(long groupId,
		String articleId, double version, int start, int end,
		OrderByComparator<JournalArticleImage> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A_V;
			finderArgs = new Object[] { groupId, articleId, version };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A_V;
			finderArgs = new Object[] {
					groupId, articleId, version,
					
					start, end, orderByComparator
				};
		}

		List<JournalArticleImage> list = null;

		if (retrieveFromCache) {
			list = (List<JournalArticleImage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (JournalArticleImage journalArticleImage : list) {
					if ((groupId != journalArticleImage.getGroupId()) ||
							!Validator.equals(articleId,
								journalArticleImage.getArticleId()) ||
							(version != journalArticleImage.getVersion())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_JOURNALARTICLEIMAGE_WHERE);

			query.append(_FINDER_COLUMN_G_A_V_GROUPID_2);

			boolean bindArticleId = false;

			if (articleId == null) {
				query.append(_FINDER_COLUMN_G_A_V_ARTICLEID_1);
			}
			else if (articleId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_A_V_ARTICLEID_3);
			}
			else {
				bindArticleId = true;

				query.append(_FINDER_COLUMN_G_A_V_ARTICLEID_2);
			}

			query.append(_FINDER_COLUMN_G_A_V_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleImageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindArticleId) {
					qPos.add(articleId);
				}

				qPos.add(version);

				if (!pagination) {
					list = (List<JournalArticleImage>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticleImage>)QueryUtil.list(q,
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
	 * Returns the first journal article image in the ordered set where groupId = &#63; and articleId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article image
	 * @throws com.liferay.journal.NoSuchArticleImageException if a matching journal article image could not be found
	 */
	@Override
	public JournalArticleImage findByG_A_V_First(long groupId,
		String articleId, double version,
		OrderByComparator<JournalArticleImage> orderByComparator)
		throws NoSuchArticleImageException {
		JournalArticleImage journalArticleImage = fetchByG_A_V_First(groupId,
				articleId, version, orderByComparator);

		if (journalArticleImage != null) {
			return journalArticleImage;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", articleId=");
		msg.append(articleId);

		msg.append(", version=");
		msg.append(version);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleImageException(msg.toString());
	}

	/**
	 * Returns the first journal article image in the ordered set where groupId = &#63; and articleId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article image, or <code>null</code> if a matching journal article image could not be found
	 */
	@Override
	public JournalArticleImage fetchByG_A_V_First(long groupId,
		String articleId, double version,
		OrderByComparator<JournalArticleImage> orderByComparator) {
		List<JournalArticleImage> list = findByG_A_V(groupId, articleId,
				version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article image in the ordered set where groupId = &#63; and articleId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article image
	 * @throws com.liferay.journal.NoSuchArticleImageException if a matching journal article image could not be found
	 */
	@Override
	public JournalArticleImage findByG_A_V_Last(long groupId, String articleId,
		double version, OrderByComparator<JournalArticleImage> orderByComparator)
		throws NoSuchArticleImageException {
		JournalArticleImage journalArticleImage = fetchByG_A_V_Last(groupId,
				articleId, version, orderByComparator);

		if (journalArticleImage != null) {
			return journalArticleImage;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", articleId=");
		msg.append(articleId);

		msg.append(", version=");
		msg.append(version);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleImageException(msg.toString());
	}

	/**
	 * Returns the last journal article image in the ordered set where groupId = &#63; and articleId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article image, or <code>null</code> if a matching journal article image could not be found
	 */
	@Override
	public JournalArticleImage fetchByG_A_V_Last(long groupId,
		String articleId, double version,
		OrderByComparator<JournalArticleImage> orderByComparator) {
		int count = countByG_A_V(groupId, articleId, version);

		if (count == 0) {
			return null;
		}

		List<JournalArticleImage> list = findByG_A_V(groupId, articleId,
				version, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal article images before and after the current journal article image in the ordered set where groupId = &#63; and articleId = &#63; and version = &#63;.
	 *
	 * @param articleImageId the primary key of the current journal article image
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article image
	 * @throws com.liferay.journal.NoSuchArticleImageException if a journal article image with the primary key could not be found
	 */
	@Override
	public JournalArticleImage[] findByG_A_V_PrevAndNext(long articleImageId,
		long groupId, String articleId, double version,
		OrderByComparator<JournalArticleImage> orderByComparator)
		throws NoSuchArticleImageException {
		JournalArticleImage journalArticleImage = findByPrimaryKey(articleImageId);

		Session session = null;

		try {
			session = openSession();

			JournalArticleImage[] array = new JournalArticleImageImpl[3];

			array[0] = getByG_A_V_PrevAndNext(session, journalArticleImage,
					groupId, articleId, version, orderByComparator, true);

			array[1] = journalArticleImage;

			array[2] = getByG_A_V_PrevAndNext(session, journalArticleImage,
					groupId, articleId, version, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticleImage getByG_A_V_PrevAndNext(Session session,
		JournalArticleImage journalArticleImage, long groupId,
		String articleId, double version,
		OrderByComparator<JournalArticleImage> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLEIMAGE_WHERE);

		query.append(_FINDER_COLUMN_G_A_V_GROUPID_2);

		boolean bindArticleId = false;

		if (articleId == null) {
			query.append(_FINDER_COLUMN_G_A_V_ARTICLEID_1);
		}
		else if (articleId.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_A_V_ARTICLEID_3);
		}
		else {
			bindArticleId = true;

			query.append(_FINDER_COLUMN_G_A_V_ARTICLEID_2);
		}

		query.append(_FINDER_COLUMN_G_A_V_VERSION_2);

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
			query.append(JournalArticleImageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindArticleId) {
			qPos.add(articleId);
		}

		qPos.add(version);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticleImage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticleImage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal article images where groupId = &#63; and articleId = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 */
	@Override
	public void removeByG_A_V(long groupId, String articleId, double version) {
		for (JournalArticleImage journalArticleImage : findByG_A_V(groupId,
				articleId, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticleImage);
		}
	}

	/**
	 * Returns the number of journal article images where groupId = &#63; and articleId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 * @return the number of matching journal article images
	 */
	@Override
	public int countByG_A_V(long groupId, String articleId, double version) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_A_V;

		Object[] finderArgs = new Object[] { groupId, articleId, version };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_JOURNALARTICLEIMAGE_WHERE);

			query.append(_FINDER_COLUMN_G_A_V_GROUPID_2);

			boolean bindArticleId = false;

			if (articleId == null) {
				query.append(_FINDER_COLUMN_G_A_V_ARTICLEID_1);
			}
			else if (articleId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_A_V_ARTICLEID_3);
			}
			else {
				bindArticleId = true;

				query.append(_FINDER_COLUMN_G_A_V_ARTICLEID_2);
			}

			query.append(_FINDER_COLUMN_G_A_V_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindArticleId) {
					qPos.add(articleId);
				}

				qPos.add(version);

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

	private static final String _FINDER_COLUMN_G_A_V_GROUPID_2 = "journalArticleImage.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_V_ARTICLEID_1 = "journalArticleImage.articleId IS NULL AND ";
	private static final String _FINDER_COLUMN_G_A_V_ARTICLEID_2 = "journalArticleImage.articleId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_V_ARTICLEID_3 = "(journalArticleImage.articleId IS NULL OR journalArticleImage.articleId = '') AND ";
	private static final String _FINDER_COLUMN_G_A_V_VERSION_2 = "journalArticleImage.version = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_A_V_E_E_L = new FinderPath(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImageImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_A_V_E_E_L",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Double.class.getName(), String.class.getName(),
				String.class.getName(), String.class.getName()
			},
			JournalArticleImageModelImpl.GROUPID_COLUMN_BITMASK |
			JournalArticleImageModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleImageModelImpl.VERSION_COLUMN_BITMASK |
			JournalArticleImageModelImpl.ELINSTANCEID_COLUMN_BITMASK |
			JournalArticleImageModelImpl.ELNAME_COLUMN_BITMASK |
			JournalArticleImageModelImpl.LANGUAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A_V_E_E_L = new FinderPath(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_A_V_E_E_L",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Double.class.getName(), String.class.getName(),
				String.class.getName(), String.class.getName()
			});

	/**
	 * Returns the journal article image where groupId = &#63; and articleId = &#63; and version = &#63; and elInstanceId = &#63; and elName = &#63; and languageId = &#63; or throws a {@link com.liferay.journal.NoSuchArticleImageException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 * @param elInstanceId the el instance ID
	 * @param elName the el name
	 * @param languageId the language ID
	 * @return the matching journal article image
	 * @throws com.liferay.journal.NoSuchArticleImageException if a matching journal article image could not be found
	 */
	@Override
	public JournalArticleImage findByG_A_V_E_E_L(long groupId,
		String articleId, double version, String elInstanceId, String elName,
		String languageId) throws NoSuchArticleImageException {
		JournalArticleImage journalArticleImage = fetchByG_A_V_E_E_L(groupId,
				articleId, version, elInstanceId, elName, languageId);

		if (journalArticleImage == null) {
			StringBundler msg = new StringBundler(14);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", articleId=");
			msg.append(articleId);

			msg.append(", version=");
			msg.append(version);

			msg.append(", elInstanceId=");
			msg.append(elInstanceId);

			msg.append(", elName=");
			msg.append(elName);

			msg.append(", languageId=");
			msg.append(languageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchArticleImageException(msg.toString());
		}

		return journalArticleImage;
	}

	/**
	 * Returns the journal article image where groupId = &#63; and articleId = &#63; and version = &#63; and elInstanceId = &#63; and elName = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 * @param elInstanceId the el instance ID
	 * @param elName the el name
	 * @param languageId the language ID
	 * @return the matching journal article image, or <code>null</code> if a matching journal article image could not be found
	 */
	@Override
	public JournalArticleImage fetchByG_A_V_E_E_L(long groupId,
		String articleId, double version, String elInstanceId, String elName,
		String languageId) {
		return fetchByG_A_V_E_E_L(groupId, articleId, version, elInstanceId,
			elName, languageId, true);
	}

	/**
	 * Returns the journal article image where groupId = &#63; and articleId = &#63; and version = &#63; and elInstanceId = &#63; and elName = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 * @param elInstanceId the el instance ID
	 * @param elName the el name
	 * @param languageId the language ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching journal article image, or <code>null</code> if a matching journal article image could not be found
	 */
	@Override
	public JournalArticleImage fetchByG_A_V_E_E_L(long groupId,
		String articleId, double version, String elInstanceId, String elName,
		String languageId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] {
				groupId, articleId, version, elInstanceId, elName, languageId
			};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_A_V_E_E_L,
					finderArgs, this);
		}

		if (result instanceof JournalArticleImage) {
			JournalArticleImage journalArticleImage = (JournalArticleImage)result;

			if ((groupId != journalArticleImage.getGroupId()) ||
					!Validator.equals(articleId,
						journalArticleImage.getArticleId()) ||
					(version != journalArticleImage.getVersion()) ||
					!Validator.equals(elInstanceId,
						journalArticleImage.getElInstanceId()) ||
					!Validator.equals(elName, journalArticleImage.getElName()) ||
					!Validator.equals(languageId,
						journalArticleImage.getLanguageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(8);

			query.append(_SQL_SELECT_JOURNALARTICLEIMAGE_WHERE);

			query.append(_FINDER_COLUMN_G_A_V_E_E_L_GROUPID_2);

			boolean bindArticleId = false;

			if (articleId == null) {
				query.append(_FINDER_COLUMN_G_A_V_E_E_L_ARTICLEID_1);
			}
			else if (articleId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_A_V_E_E_L_ARTICLEID_3);
			}
			else {
				bindArticleId = true;

				query.append(_FINDER_COLUMN_G_A_V_E_E_L_ARTICLEID_2);
			}

			query.append(_FINDER_COLUMN_G_A_V_E_E_L_VERSION_2);

			boolean bindElInstanceId = false;

			if (elInstanceId == null) {
				query.append(_FINDER_COLUMN_G_A_V_E_E_L_ELINSTANCEID_1);
			}
			else if (elInstanceId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_A_V_E_E_L_ELINSTANCEID_3);
			}
			else {
				bindElInstanceId = true;

				query.append(_FINDER_COLUMN_G_A_V_E_E_L_ELINSTANCEID_2);
			}

			boolean bindElName = false;

			if (elName == null) {
				query.append(_FINDER_COLUMN_G_A_V_E_E_L_ELNAME_1);
			}
			else if (elName.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_A_V_E_E_L_ELNAME_3);
			}
			else {
				bindElName = true;

				query.append(_FINDER_COLUMN_G_A_V_E_E_L_ELNAME_2);
			}

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_G_A_V_E_E_L_LANGUAGEID_1);
			}
			else if (languageId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_A_V_E_E_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_G_A_V_E_E_L_LANGUAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindArticleId) {
					qPos.add(articleId);
				}

				qPos.add(version);

				if (bindElInstanceId) {
					qPos.add(elInstanceId);
				}

				if (bindElName) {
					qPos.add(elName);
				}

				if (bindLanguageId) {
					qPos.add(languageId);
				}

				List<JournalArticleImage> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_A_V_E_E_L,
						finderArgs, list);
				}
				else {
					JournalArticleImage journalArticleImage = list.get(0);

					result = journalArticleImage;

					cacheResult(journalArticleImage);

					if ((journalArticleImage.getGroupId() != groupId) ||
							(journalArticleImage.getArticleId() == null) ||
							!journalArticleImage.getArticleId().equals(articleId) ||
							(journalArticleImage.getVersion() != version) ||
							(journalArticleImage.getElInstanceId() == null) ||
							!journalArticleImage.getElInstanceId()
													.equals(elInstanceId) ||
							(journalArticleImage.getElName() == null) ||
							!journalArticleImage.getElName().equals(elName) ||
							(journalArticleImage.getLanguageId() == null) ||
							!journalArticleImage.getLanguageId()
													.equals(languageId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_A_V_E_E_L,
							finderArgs, journalArticleImage);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_A_V_E_E_L,
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
			return (JournalArticleImage)result;
		}
	}

	/**
	 * Removes the journal article image where groupId = &#63; and articleId = &#63; and version = &#63; and elInstanceId = &#63; and elName = &#63; and languageId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 * @param elInstanceId the el instance ID
	 * @param elName the el name
	 * @param languageId the language ID
	 * @return the journal article image that was removed
	 */
	@Override
	public JournalArticleImage removeByG_A_V_E_E_L(long groupId,
		String articleId, double version, String elInstanceId, String elName,
		String languageId) throws NoSuchArticleImageException {
		JournalArticleImage journalArticleImage = findByG_A_V_E_E_L(groupId,
				articleId, version, elInstanceId, elName, languageId);

		return remove(journalArticleImage);
	}

	/**
	 * Returns the number of journal article images where groupId = &#63; and articleId = &#63; and version = &#63; and elInstanceId = &#63; and elName = &#63; and languageId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 * @param elInstanceId the el instance ID
	 * @param elName the el name
	 * @param languageId the language ID
	 * @return the number of matching journal article images
	 */
	@Override
	public int countByG_A_V_E_E_L(long groupId, String articleId,
		double version, String elInstanceId, String elName, String languageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_A_V_E_E_L;

		Object[] finderArgs = new Object[] {
				groupId, articleId, version, elInstanceId, elName, languageId
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(7);

			query.append(_SQL_COUNT_JOURNALARTICLEIMAGE_WHERE);

			query.append(_FINDER_COLUMN_G_A_V_E_E_L_GROUPID_2);

			boolean bindArticleId = false;

			if (articleId == null) {
				query.append(_FINDER_COLUMN_G_A_V_E_E_L_ARTICLEID_1);
			}
			else if (articleId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_A_V_E_E_L_ARTICLEID_3);
			}
			else {
				bindArticleId = true;

				query.append(_FINDER_COLUMN_G_A_V_E_E_L_ARTICLEID_2);
			}

			query.append(_FINDER_COLUMN_G_A_V_E_E_L_VERSION_2);

			boolean bindElInstanceId = false;

			if (elInstanceId == null) {
				query.append(_FINDER_COLUMN_G_A_V_E_E_L_ELINSTANCEID_1);
			}
			else if (elInstanceId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_A_V_E_E_L_ELINSTANCEID_3);
			}
			else {
				bindElInstanceId = true;

				query.append(_FINDER_COLUMN_G_A_V_E_E_L_ELINSTANCEID_2);
			}

			boolean bindElName = false;

			if (elName == null) {
				query.append(_FINDER_COLUMN_G_A_V_E_E_L_ELNAME_1);
			}
			else if (elName.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_A_V_E_E_L_ELNAME_3);
			}
			else {
				bindElName = true;

				query.append(_FINDER_COLUMN_G_A_V_E_E_L_ELNAME_2);
			}

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_G_A_V_E_E_L_LANGUAGEID_1);
			}
			else if (languageId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_A_V_E_E_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_G_A_V_E_E_L_LANGUAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindArticleId) {
					qPos.add(articleId);
				}

				qPos.add(version);

				if (bindElInstanceId) {
					qPos.add(elInstanceId);
				}

				if (bindElName) {
					qPos.add(elName);
				}

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

	private static final String _FINDER_COLUMN_G_A_V_E_E_L_GROUPID_2 = "journalArticleImage.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_V_E_E_L_ARTICLEID_1 = "journalArticleImage.articleId IS NULL AND ";
	private static final String _FINDER_COLUMN_G_A_V_E_E_L_ARTICLEID_2 = "journalArticleImage.articleId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_V_E_E_L_ARTICLEID_3 = "(journalArticleImage.articleId IS NULL OR journalArticleImage.articleId = '') AND ";
	private static final String _FINDER_COLUMN_G_A_V_E_E_L_VERSION_2 = "journalArticleImage.version = ? AND ";
	private static final String _FINDER_COLUMN_G_A_V_E_E_L_ELINSTANCEID_1 = "journalArticleImage.elInstanceId IS NULL AND ";
	private static final String _FINDER_COLUMN_G_A_V_E_E_L_ELINSTANCEID_2 = "journalArticleImage.elInstanceId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_V_E_E_L_ELINSTANCEID_3 = "(journalArticleImage.elInstanceId IS NULL OR journalArticleImage.elInstanceId = '') AND ";
	private static final String _FINDER_COLUMN_G_A_V_E_E_L_ELNAME_1 = "journalArticleImage.elName IS NULL AND ";
	private static final String _FINDER_COLUMN_G_A_V_E_E_L_ELNAME_2 = "journalArticleImage.elName = ? AND ";
	private static final String _FINDER_COLUMN_G_A_V_E_E_L_ELNAME_3 = "(journalArticleImage.elName IS NULL OR journalArticleImage.elName = '') AND ";
	private static final String _FINDER_COLUMN_G_A_V_E_E_L_LANGUAGEID_1 = "journalArticleImage.languageId IS NULL";
	private static final String _FINDER_COLUMN_G_A_V_E_E_L_LANGUAGEID_2 = "journalArticleImage.languageId = ?";
	private static final String _FINDER_COLUMN_G_A_V_E_E_L_LANGUAGEID_3 = "(journalArticleImage.languageId IS NULL OR journalArticleImage.languageId = '')";

	public JournalArticleImagePersistenceImpl() {
		setModelClass(JournalArticleImage.class);
	}

	/**
	 * Caches the journal article image in the entity cache if it is enabled.
	 *
	 * @param journalArticleImage the journal article image
	 */
	@Override
	public void cacheResult(JournalArticleImage journalArticleImage) {
		entityCache.putResult(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageImpl.class, journalArticleImage.getPrimaryKey(),
			journalArticleImage);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_A_V_E_E_L,
			new Object[] {
				journalArticleImage.getGroupId(),
				journalArticleImage.getArticleId(),
				journalArticleImage.getVersion(),
				journalArticleImage.getElInstanceId(),
				journalArticleImage.getElName(),
				journalArticleImage.getLanguageId()
			}, journalArticleImage);

		journalArticleImage.resetOriginalValues();
	}

	/**
	 * Caches the journal article images in the entity cache if it is enabled.
	 *
	 * @param journalArticleImages the journal article images
	 */
	@Override
	public void cacheResult(List<JournalArticleImage> journalArticleImages) {
		for (JournalArticleImage journalArticleImage : journalArticleImages) {
			if (entityCache.getResult(
						JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
						JournalArticleImageImpl.class,
						journalArticleImage.getPrimaryKey()) == null) {
				cacheResult(journalArticleImage);
			}
			else {
				journalArticleImage.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all journal article images.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(JournalArticleImageImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the journal article image.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(JournalArticleImage journalArticleImage) {
		entityCache.removeResult(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageImpl.class, journalArticleImage.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((JournalArticleImageModelImpl)journalArticleImage);
	}

	@Override
	public void clearCache(List<JournalArticleImage> journalArticleImages) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (JournalArticleImage journalArticleImage : journalArticleImages) {
			entityCache.removeResult(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
				JournalArticleImageImpl.class,
				journalArticleImage.getPrimaryKey());

			clearUniqueFindersCache((JournalArticleImageModelImpl)journalArticleImage);
		}
	}

	protected void cacheUniqueFindersCache(
		JournalArticleImageModelImpl journalArticleImageModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					journalArticleImageModelImpl.getGroupId(),
					journalArticleImageModelImpl.getArticleId(),
					journalArticleImageModelImpl.getVersion(),
					journalArticleImageModelImpl.getElInstanceId(),
					journalArticleImageModelImpl.getElName(),
					journalArticleImageModelImpl.getLanguageId()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_G_A_V_E_E_L, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_G_A_V_E_E_L, args,
				journalArticleImageModelImpl);
		}
		else {
			if ((journalArticleImageModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_A_V_E_E_L.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleImageModelImpl.getGroupId(),
						journalArticleImageModelImpl.getArticleId(),
						journalArticleImageModelImpl.getVersion(),
						journalArticleImageModelImpl.getElInstanceId(),
						journalArticleImageModelImpl.getElName(),
						journalArticleImageModelImpl.getLanguageId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_G_A_V_E_E_L, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_G_A_V_E_E_L, args,
					journalArticleImageModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		JournalArticleImageModelImpl journalArticleImageModelImpl) {
		Object[] args = new Object[] {
				journalArticleImageModelImpl.getGroupId(),
				journalArticleImageModelImpl.getArticleId(),
				journalArticleImageModelImpl.getVersion(),
				journalArticleImageModelImpl.getElInstanceId(),
				journalArticleImageModelImpl.getElName(),
				journalArticleImageModelImpl.getLanguageId()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A_V_E_E_L, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_G_A_V_E_E_L, args);

		if ((journalArticleImageModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_A_V_E_E_L.getColumnBitmask()) != 0) {
			args = new Object[] {
					journalArticleImageModelImpl.getOriginalGroupId(),
					journalArticleImageModelImpl.getOriginalArticleId(),
					journalArticleImageModelImpl.getOriginalVersion(),
					journalArticleImageModelImpl.getOriginalElInstanceId(),
					journalArticleImageModelImpl.getOriginalElName(),
					journalArticleImageModelImpl.getOriginalLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A_V_E_E_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_A_V_E_E_L, args);
		}
	}

	/**
	 * Creates a new journal article image with the primary key. Does not add the journal article image to the database.
	 *
	 * @param articleImageId the primary key for the new journal article image
	 * @return the new journal article image
	 */
	@Override
	public JournalArticleImage create(long articleImageId) {
		JournalArticleImage journalArticleImage = new JournalArticleImageImpl();

		journalArticleImage.setNew(true);
		journalArticleImage.setPrimaryKey(articleImageId);

		journalArticleImage.setCompanyId(companyProvider.getCompanyId());

		return journalArticleImage;
	}

	/**
	 * Removes the journal article image with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param articleImageId the primary key of the journal article image
	 * @return the journal article image that was removed
	 * @throws com.liferay.journal.NoSuchArticleImageException if a journal article image with the primary key could not be found
	 */
	@Override
	public JournalArticleImage remove(long articleImageId)
		throws NoSuchArticleImageException {
		return remove((Serializable)articleImageId);
	}

	/**
	 * Removes the journal article image with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the journal article image
	 * @return the journal article image that was removed
	 * @throws com.liferay.journal.NoSuchArticleImageException if a journal article image with the primary key could not be found
	 */
	@Override
	public JournalArticleImage remove(Serializable primaryKey)
		throws NoSuchArticleImageException {
		Session session = null;

		try {
			session = openSession();

			JournalArticleImage journalArticleImage = (JournalArticleImage)session.get(JournalArticleImageImpl.class,
					primaryKey);

			if (journalArticleImage == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchArticleImageException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(journalArticleImage);
		}
		catch (NoSuchArticleImageException nsee) {
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
	protected JournalArticleImage removeImpl(
		JournalArticleImage journalArticleImage) {
		journalArticleImage = toUnwrappedModel(journalArticleImage);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(journalArticleImage)) {
				journalArticleImage = (JournalArticleImage)session.get(JournalArticleImageImpl.class,
						journalArticleImage.getPrimaryKeyObj());
			}

			if (journalArticleImage != null) {
				session.delete(journalArticleImage);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (journalArticleImage != null) {
			clearCache(journalArticleImage);
		}

		return journalArticleImage;
	}

	@Override
	public JournalArticleImage updateImpl(
		JournalArticleImage journalArticleImage) {
		journalArticleImage = toUnwrappedModel(journalArticleImage);

		boolean isNew = journalArticleImage.isNew();

		JournalArticleImageModelImpl journalArticleImageModelImpl = (JournalArticleImageModelImpl)journalArticleImage;

		Session session = null;

		try {
			session = openSession();

			if (journalArticleImage.isNew()) {
				session.save(journalArticleImage);

				journalArticleImage.setNew(false);
			}
			else {
				journalArticleImage = (JournalArticleImage)session.merge(journalArticleImage);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !JournalArticleImageModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((journalArticleImageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleImageModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { journalArticleImageModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((journalArticleImageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TEMPIMAGE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleImageModelImpl.getOriginalTempImage()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_TEMPIMAGE, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TEMPIMAGE,
					args);

				args = new Object[] { journalArticleImageModelImpl.getTempImage() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_TEMPIMAGE, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TEMPIMAGE,
					args);
			}

			if ((journalArticleImageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A_V.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleImageModelImpl.getOriginalGroupId(),
						journalArticleImageModelImpl.getOriginalArticleId(),
						journalArticleImageModelImpl.getOriginalVersion()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A_V, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A_V,
					args);

				args = new Object[] {
						journalArticleImageModelImpl.getGroupId(),
						journalArticleImageModelImpl.getArticleId(),
						journalArticleImageModelImpl.getVersion()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A_V, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A_V,
					args);
			}
		}

		entityCache.putResult(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageImpl.class, journalArticleImage.getPrimaryKey(),
			journalArticleImage, false);

		clearUniqueFindersCache(journalArticleImageModelImpl);
		cacheUniqueFindersCache(journalArticleImageModelImpl, isNew);

		journalArticleImage.resetOriginalValues();

		return journalArticleImage;
	}

	protected JournalArticleImage toUnwrappedModel(
		JournalArticleImage journalArticleImage) {
		if (journalArticleImage instanceof JournalArticleImageImpl) {
			return journalArticleImage;
		}

		JournalArticleImageImpl journalArticleImageImpl = new JournalArticleImageImpl();

		journalArticleImageImpl.setNew(journalArticleImage.isNew());
		journalArticleImageImpl.setPrimaryKey(journalArticleImage.getPrimaryKey());

		journalArticleImageImpl.setArticleImageId(journalArticleImage.getArticleImageId());
		journalArticleImageImpl.setGroupId(journalArticleImage.getGroupId());
		journalArticleImageImpl.setCompanyId(journalArticleImage.getCompanyId());
		journalArticleImageImpl.setArticleId(journalArticleImage.getArticleId());
		journalArticleImageImpl.setVersion(journalArticleImage.getVersion());
		journalArticleImageImpl.setElInstanceId(journalArticleImage.getElInstanceId());
		journalArticleImageImpl.setElName(journalArticleImage.getElName());
		journalArticleImageImpl.setLanguageId(journalArticleImage.getLanguageId());
		journalArticleImageImpl.setTempImage(journalArticleImage.isTempImage());

		return journalArticleImageImpl;
	}

	/**
	 * Returns the journal article image with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the journal article image
	 * @return the journal article image
	 * @throws com.liferay.journal.NoSuchArticleImageException if a journal article image with the primary key could not be found
	 */
	@Override
	public JournalArticleImage findByPrimaryKey(Serializable primaryKey)
		throws NoSuchArticleImageException {
		JournalArticleImage journalArticleImage = fetchByPrimaryKey(primaryKey);

		if (journalArticleImage == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchArticleImageException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return journalArticleImage;
	}

	/**
	 * Returns the journal article image with the primary key or throws a {@link com.liferay.journal.NoSuchArticleImageException} if it could not be found.
	 *
	 * @param articleImageId the primary key of the journal article image
	 * @return the journal article image
	 * @throws com.liferay.journal.NoSuchArticleImageException if a journal article image with the primary key could not be found
	 */
	@Override
	public JournalArticleImage findByPrimaryKey(long articleImageId)
		throws NoSuchArticleImageException {
		return findByPrimaryKey((Serializable)articleImageId);
	}

	/**
	 * Returns the journal article image with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the journal article image
	 * @return the journal article image, or <code>null</code> if a journal article image with the primary key could not be found
	 */
	@Override
	public JournalArticleImage fetchByPrimaryKey(Serializable primaryKey) {
		JournalArticleImage journalArticleImage = (JournalArticleImage)entityCache.getResult(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
				JournalArticleImageImpl.class, primaryKey);

		if (journalArticleImage == _nullJournalArticleImage) {
			return null;
		}

		if (journalArticleImage == null) {
			Session session = null;

			try {
				session = openSession();

				journalArticleImage = (JournalArticleImage)session.get(JournalArticleImageImpl.class,
						primaryKey);

				if (journalArticleImage != null) {
					cacheResult(journalArticleImage);
				}
				else {
					entityCache.putResult(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
						JournalArticleImageImpl.class, primaryKey,
						_nullJournalArticleImage);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
					JournalArticleImageImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return journalArticleImage;
	}

	/**
	 * Returns the journal article image with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param articleImageId the primary key of the journal article image
	 * @return the journal article image, or <code>null</code> if a journal article image with the primary key could not be found
	 */
	@Override
	public JournalArticleImage fetchByPrimaryKey(long articleImageId) {
		return fetchByPrimaryKey((Serializable)articleImageId);
	}

	@Override
	public Map<Serializable, JournalArticleImage> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, JournalArticleImage> map = new HashMap<Serializable, JournalArticleImage>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			JournalArticleImage journalArticleImage = fetchByPrimaryKey(primaryKey);

			if (journalArticleImage != null) {
				map.put(primaryKey, journalArticleImage);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			JournalArticleImage journalArticleImage = (JournalArticleImage)entityCache.getResult(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
					JournalArticleImageImpl.class, primaryKey);

			if (journalArticleImage == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, journalArticleImage);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_JOURNALARTICLEIMAGE_WHERE_PKS_IN);

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

			for (JournalArticleImage journalArticleImage : (List<JournalArticleImage>)q.list()) {
				map.put(journalArticleImage.getPrimaryKeyObj(),
					journalArticleImage);

				cacheResult(journalArticleImage);

				uncachedPrimaryKeys.remove(journalArticleImage.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
					JournalArticleImageImpl.class, primaryKey,
					_nullJournalArticleImage);
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
	 * Returns all the journal article images.
	 *
	 * @return the journal article images
	 */
	@Override
	public List<JournalArticleImage> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal article images.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal article images
	 * @param end the upper bound of the range of journal article images (not inclusive)
	 * @return the range of journal article images
	 */
	@Override
	public List<JournalArticleImage> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal article images.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal article images
	 * @param end the upper bound of the range of journal article images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of journal article images
	 */
	@Override
	public List<JournalArticleImage> findAll(int start, int end,
		OrderByComparator<JournalArticleImage> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal article images.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal article images
	 * @param end the upper bound of the range of journal article images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of journal article images
	 */
	@Override
	public List<JournalArticleImage> findAll(int start, int end,
		OrderByComparator<JournalArticleImage> orderByComparator,
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

		List<JournalArticleImage> list = null;

		if (retrieveFromCache) {
			list = (List<JournalArticleImage>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_JOURNALARTICLEIMAGE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_JOURNALARTICLEIMAGE;

				if (pagination) {
					sql = sql.concat(JournalArticleImageModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<JournalArticleImage>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticleImage>)QueryUtil.list(q,
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
	 * Removes all the journal article images from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (JournalArticleImage journalArticleImage : findAll()) {
			remove(journalArticleImage);
		}
	}

	/**
	 * Returns the number of journal article images.
	 *
	 * @return the number of journal article images
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_JOURNALARTICLEIMAGE);

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
		return JournalArticleImageModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the journal article image persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(JournalArticleImageImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_JOURNALARTICLEIMAGE = "SELECT journalArticleImage FROM JournalArticleImage journalArticleImage";
	private static final String _SQL_SELECT_JOURNALARTICLEIMAGE_WHERE_PKS_IN = "SELECT journalArticleImage FROM JournalArticleImage journalArticleImage WHERE articleImageId IN (";
	private static final String _SQL_SELECT_JOURNALARTICLEIMAGE_WHERE = "SELECT journalArticleImage FROM JournalArticleImage journalArticleImage WHERE ";
	private static final String _SQL_COUNT_JOURNALARTICLEIMAGE = "SELECT COUNT(journalArticleImage) FROM JournalArticleImage journalArticleImage";
	private static final String _SQL_COUNT_JOURNALARTICLEIMAGE_WHERE = "SELECT COUNT(journalArticleImage) FROM JournalArticleImage journalArticleImage WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "journalArticleImage.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No JournalArticleImage exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No JournalArticleImage exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(JournalArticleImagePersistenceImpl.class);
	private static final JournalArticleImage _nullJournalArticleImage = new JournalArticleImageImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<JournalArticleImage> toCacheModel() {
				return _nullJournalArticleImageCacheModel;
			}
		};

	private static final CacheModel<JournalArticleImage> _nullJournalArticleImageCacheModel =
		new CacheModel<JournalArticleImage>() {
			@Override
			public JournalArticleImage toEntityModel() {
				return _nullJournalArticleImage;
			}
		};
}