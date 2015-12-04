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

package com.liferay.portlet.documentlibrary.service.persistence.impl;

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
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.documentlibrary.NoSuchContentException;
import com.liferay.portlet.documentlibrary.model.DLContent;
import com.liferay.portlet.documentlibrary.model.impl.DLContentImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLContentModelImpl;
import com.liferay.portlet.documentlibrary.service.persistence.DLContentPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the document library content service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLContentPersistence
 * @see com.liferay.portlet.documentlibrary.service.persistence.DLContentUtil
 * @generated
 */
@ProviderType
public class DLContentPersistenceImpl extends BasePersistenceImpl<DLContent>
	implements DLContentPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DLContentUtil} to access the document library content persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DLContentImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DLContentModelImpl.ENTITY_CACHE_ENABLED,
			DLContentModelImpl.FINDER_CACHE_ENABLED, DLContentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DLContentModelImpl.ENTITY_CACHE_ENABLED,
			DLContentModelImpl.FINDER_CACHE_ENABLED, DLContentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DLContentModelImpl.ENTITY_CACHE_ENABLED,
			DLContentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_R = new FinderPath(DLContentModelImpl.ENTITY_CACHE_ENABLED,
			DLContentModelImpl.FINDER_CACHE_ENABLED, DLContentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_R",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_R = new FinderPath(DLContentModelImpl.ENTITY_CACHE_ENABLED,
			DLContentModelImpl.FINDER_CACHE_ENABLED, DLContentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_R",
			new String[] { Long.class.getName(), Long.class.getName() },
			DLContentModelImpl.COMPANYID_COLUMN_BITMASK |
			DLContentModelImpl.REPOSITORYID_COLUMN_BITMASK |
			DLContentModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_R = new FinderPath(DLContentModelImpl.ENTITY_CACHE_ENABLED,
			DLContentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_R",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the document library contents where companyId = &#63; and repositoryId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @return the matching document library contents
	 */
	@Override
	public List<DLContent> findByC_R(long companyId, long repositoryId) {
		return findByC_R(companyId, repositoryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library contents where companyId = &#63; and repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of document library contents
	 * @param end the upper bound of the range of document library contents (not inclusive)
	 * @return the range of matching document library contents
	 */
	@Override
	public List<DLContent> findByC_R(long companyId, long repositoryId,
		int start, int end) {
		return findByC_R(companyId, repositoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library contents where companyId = &#63; and repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of document library contents
	 * @param end the upper bound of the range of document library contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library contents
	 */
	@Override
	public List<DLContent> findByC_R(long companyId, long repositoryId,
		int start, int end, OrderByComparator<DLContent> orderByComparator) {
		return findByC_R(companyId, repositoryId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library contents where companyId = &#63; and repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of document library contents
	 * @param end the upper bound of the range of document library contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library contents
	 */
	@Override
	public List<DLContent> findByC_R(long companyId, long repositoryId,
		int start, int end, OrderByComparator<DLContent> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_R;
			finderArgs = new Object[] { companyId, repositoryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_R;
			finderArgs = new Object[] {
					companyId, repositoryId,
					
					start, end, orderByComparator
				};
		}

		List<DLContent> list = null;

		if (retrieveFromCache) {
			list = (List<DLContent>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLContent dlContent : list) {
					if ((companyId != dlContent.getCompanyId()) ||
							(repositoryId != dlContent.getRepositoryId())) {
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

			query.append(_SQL_SELECT_DLCONTENT_WHERE);

			query.append(_FINDER_COLUMN_C_R_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_R_REPOSITORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLContentModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(repositoryId);

				if (!pagination) {
					list = (List<DLContent>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLContent>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library content in the ordered set where companyId = &#63; and repositoryId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library content
	 * @throws NoSuchContentException if a matching document library content could not be found
	 */
	@Override
	public DLContent findByC_R_First(long companyId, long repositoryId,
		OrderByComparator<DLContent> orderByComparator)
		throws NoSuchContentException {
		DLContent dlContent = fetchByC_R_First(companyId, repositoryId,
				orderByComparator);

		if (dlContent != null) {
			return dlContent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", repositoryId=");
		msg.append(repositoryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContentException(msg.toString());
	}

	/**
	 * Returns the first document library content in the ordered set where companyId = &#63; and repositoryId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library content, or <code>null</code> if a matching document library content could not be found
	 */
	@Override
	public DLContent fetchByC_R_First(long companyId, long repositoryId,
		OrderByComparator<DLContent> orderByComparator) {
		List<DLContent> list = findByC_R(companyId, repositoryId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library content in the ordered set where companyId = &#63; and repositoryId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library content
	 * @throws NoSuchContentException if a matching document library content could not be found
	 */
	@Override
	public DLContent findByC_R_Last(long companyId, long repositoryId,
		OrderByComparator<DLContent> orderByComparator)
		throws NoSuchContentException {
		DLContent dlContent = fetchByC_R_Last(companyId, repositoryId,
				orderByComparator);

		if (dlContent != null) {
			return dlContent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", repositoryId=");
		msg.append(repositoryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContentException(msg.toString());
	}

	/**
	 * Returns the last document library content in the ordered set where companyId = &#63; and repositoryId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library content, or <code>null</code> if a matching document library content could not be found
	 */
	@Override
	public DLContent fetchByC_R_Last(long companyId, long repositoryId,
		OrderByComparator<DLContent> orderByComparator) {
		int count = countByC_R(companyId, repositoryId);

		if (count == 0) {
			return null;
		}

		List<DLContent> list = findByC_R(companyId, repositoryId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library contents before and after the current document library content in the ordered set where companyId = &#63; and repositoryId = &#63;.
	 *
	 * @param contentId the primary key of the current document library content
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library content
	 * @throws NoSuchContentException if a document library content with the primary key could not be found
	 */
	@Override
	public DLContent[] findByC_R_PrevAndNext(long contentId, long companyId,
		long repositoryId, OrderByComparator<DLContent> orderByComparator)
		throws NoSuchContentException {
		DLContent dlContent = findByPrimaryKey(contentId);

		Session session = null;

		try {
			session = openSession();

			DLContent[] array = new DLContentImpl[3];

			array[0] = getByC_R_PrevAndNext(session, dlContent, companyId,
					repositoryId, orderByComparator, true);

			array[1] = dlContent;

			array[2] = getByC_R_PrevAndNext(session, dlContent, companyId,
					repositoryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLContent getByC_R_PrevAndNext(Session session,
		DLContent dlContent, long companyId, long repositoryId,
		OrderByComparator<DLContent> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLCONTENT_WHERE);

		query.append(_FINDER_COLUMN_C_R_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_R_REPOSITORYID_2);

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
			query.append(DLContentModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(repositoryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlContent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLContent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library contents where companyId = &#63; and repositoryId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 */
	@Override
	public void removeByC_R(long companyId, long repositoryId) {
		for (DLContent dlContent : findByC_R(companyId, repositoryId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlContent);
		}
	}

	/**
	 * Returns the number of document library contents where companyId = &#63; and repositoryId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @return the number of matching document library contents
	 */
	@Override
	public int countByC_R(long companyId, long repositoryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_R;

		Object[] finderArgs = new Object[] { companyId, repositoryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLCONTENT_WHERE);

			query.append(_FINDER_COLUMN_C_R_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_R_REPOSITORYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(repositoryId);

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

	private static final String _FINDER_COLUMN_C_R_COMPANYID_2 = "dlContent.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_R_REPOSITORYID_2 = "dlContent.repositoryId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_R_P = new FinderPath(DLContentModelImpl.ENTITY_CACHE_ENABLED,
			DLContentModelImpl.FINDER_CACHE_ENABLED, DLContentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_R_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_R_P = new FinderPath(DLContentModelImpl.ENTITY_CACHE_ENABLED,
			DLContentModelImpl.FINDER_CACHE_ENABLED, DLContentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_R_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			DLContentModelImpl.COMPANYID_COLUMN_BITMASK |
			DLContentModelImpl.REPOSITORYID_COLUMN_BITMASK |
			DLContentModelImpl.PATH_COLUMN_BITMASK |
			DLContentModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_R_P = new FinderPath(DLContentModelImpl.ENTITY_CACHE_ENABLED,
			DLContentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_R_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns all the document library contents where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @return the matching document library contents
	 */
	@Override
	public List<DLContent> findByC_R_P(long companyId, long repositoryId,
		String path) {
		return findByC_R_P(companyId, repositoryId, path, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library contents where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param start the lower bound of the range of document library contents
	 * @param end the upper bound of the range of document library contents (not inclusive)
	 * @return the range of matching document library contents
	 */
	@Override
	public List<DLContent> findByC_R_P(long companyId, long repositoryId,
		String path, int start, int end) {
		return findByC_R_P(companyId, repositoryId, path, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library contents where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param start the lower bound of the range of document library contents
	 * @param end the upper bound of the range of document library contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library contents
	 */
	@Override
	public List<DLContent> findByC_R_P(long companyId, long repositoryId,
		String path, int start, int end,
		OrderByComparator<DLContent> orderByComparator) {
		return findByC_R_P(companyId, repositoryId, path, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library contents where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param start the lower bound of the range of document library contents
	 * @param end the upper bound of the range of document library contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library contents
	 */
	@Override
	public List<DLContent> findByC_R_P(long companyId, long repositoryId,
		String path, int start, int end,
		OrderByComparator<DLContent> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_R_P;
			finderArgs = new Object[] { companyId, repositoryId, path };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_R_P;
			finderArgs = new Object[] {
					companyId, repositoryId, path,
					
					start, end, orderByComparator
				};
		}

		List<DLContent> list = null;

		if (retrieveFromCache) {
			list = (List<DLContent>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLContent dlContent : list) {
					if ((companyId != dlContent.getCompanyId()) ||
							(repositoryId != dlContent.getRepositoryId()) ||
							!Validator.equals(path, dlContent.getPath())) {
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

			query.append(_SQL_SELECT_DLCONTENT_WHERE);

			query.append(_FINDER_COLUMN_C_R_P_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_R_P_REPOSITORYID_2);

			boolean bindPath = false;

			if (path == null) {
				query.append(_FINDER_COLUMN_C_R_P_PATH_1);
			}
			else if (path.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_R_P_PATH_3);
			}
			else {
				bindPath = true;

				query.append(_FINDER_COLUMN_C_R_P_PATH_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLContentModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(repositoryId);

				if (bindPath) {
					qPos.add(path);
				}

				if (!pagination) {
					list = (List<DLContent>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLContent>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library content in the ordered set where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library content
	 * @throws NoSuchContentException if a matching document library content could not be found
	 */
	@Override
	public DLContent findByC_R_P_First(long companyId, long repositoryId,
		String path, OrderByComparator<DLContent> orderByComparator)
		throws NoSuchContentException {
		DLContent dlContent = fetchByC_R_P_First(companyId, repositoryId, path,
				orderByComparator);

		if (dlContent != null) {
			return dlContent;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", repositoryId=");
		msg.append(repositoryId);

		msg.append(", path=");
		msg.append(path);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContentException(msg.toString());
	}

	/**
	 * Returns the first document library content in the ordered set where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library content, or <code>null</code> if a matching document library content could not be found
	 */
	@Override
	public DLContent fetchByC_R_P_First(long companyId, long repositoryId,
		String path, OrderByComparator<DLContent> orderByComparator) {
		List<DLContent> list = findByC_R_P(companyId, repositoryId, path, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library content in the ordered set where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library content
	 * @throws NoSuchContentException if a matching document library content could not be found
	 */
	@Override
	public DLContent findByC_R_P_Last(long companyId, long repositoryId,
		String path, OrderByComparator<DLContent> orderByComparator)
		throws NoSuchContentException {
		DLContent dlContent = fetchByC_R_P_Last(companyId, repositoryId, path,
				orderByComparator);

		if (dlContent != null) {
			return dlContent;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", repositoryId=");
		msg.append(repositoryId);

		msg.append(", path=");
		msg.append(path);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContentException(msg.toString());
	}

	/**
	 * Returns the last document library content in the ordered set where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library content, or <code>null</code> if a matching document library content could not be found
	 */
	@Override
	public DLContent fetchByC_R_P_Last(long companyId, long repositoryId,
		String path, OrderByComparator<DLContent> orderByComparator) {
		int count = countByC_R_P(companyId, repositoryId, path);

		if (count == 0) {
			return null;
		}

		List<DLContent> list = findByC_R_P(companyId, repositoryId, path,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library contents before and after the current document library content in the ordered set where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	 *
	 * @param contentId the primary key of the current document library content
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library content
	 * @throws NoSuchContentException if a document library content with the primary key could not be found
	 */
	@Override
	public DLContent[] findByC_R_P_PrevAndNext(long contentId, long companyId,
		long repositoryId, String path,
		OrderByComparator<DLContent> orderByComparator)
		throws NoSuchContentException {
		DLContent dlContent = findByPrimaryKey(contentId);

		Session session = null;

		try {
			session = openSession();

			DLContent[] array = new DLContentImpl[3];

			array[0] = getByC_R_P_PrevAndNext(session, dlContent, companyId,
					repositoryId, path, orderByComparator, true);

			array[1] = dlContent;

			array[2] = getByC_R_P_PrevAndNext(session, dlContent, companyId,
					repositoryId, path, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLContent getByC_R_P_PrevAndNext(Session session,
		DLContent dlContent, long companyId, long repositoryId, String path,
		OrderByComparator<DLContent> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLCONTENT_WHERE);

		query.append(_FINDER_COLUMN_C_R_P_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_R_P_REPOSITORYID_2);

		boolean bindPath = false;

		if (path == null) {
			query.append(_FINDER_COLUMN_C_R_P_PATH_1);
		}
		else if (path.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_C_R_P_PATH_3);
		}
		else {
			bindPath = true;

			query.append(_FINDER_COLUMN_C_R_P_PATH_2);
		}

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
			query.append(DLContentModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(repositoryId);

		if (bindPath) {
			qPos.add(path);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlContent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLContent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library contents where companyId = &#63; and repositoryId = &#63; and path = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 */
	@Override
	public void removeByC_R_P(long companyId, long repositoryId, String path) {
		for (DLContent dlContent : findByC_R_P(companyId, repositoryId, path,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlContent);
		}
	}

	/**
	 * Returns the number of document library contents where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @return the number of matching document library contents
	 */
	@Override
	public int countByC_R_P(long companyId, long repositoryId, String path) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_R_P;

		Object[] finderArgs = new Object[] { companyId, repositoryId, path };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLCONTENT_WHERE);

			query.append(_FINDER_COLUMN_C_R_P_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_R_P_REPOSITORYID_2);

			boolean bindPath = false;

			if (path == null) {
				query.append(_FINDER_COLUMN_C_R_P_PATH_1);
			}
			else if (path.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_R_P_PATH_3);
			}
			else {
				bindPath = true;

				query.append(_FINDER_COLUMN_C_R_P_PATH_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(repositoryId);

				if (bindPath) {
					qPos.add(path);
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

	private static final String _FINDER_COLUMN_C_R_P_COMPANYID_2 = "dlContent.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_R_P_REPOSITORYID_2 = "dlContent.repositoryId = ? AND ";
	private static final String _FINDER_COLUMN_C_R_P_PATH_1 = "dlContent.path IS NULL";
	private static final String _FINDER_COLUMN_C_R_P_PATH_2 = "dlContent.path = ?";
	private static final String _FINDER_COLUMN_C_R_P_PATH_3 = "(dlContent.path IS NULL OR dlContent.path = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_R_LIKEP =
		new FinderPath(DLContentModelImpl.ENTITY_CACHE_ENABLED,
			DLContentModelImpl.FINDER_CACHE_ENABLED, DLContentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_R_LikeP",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_R_LIKEP =
		new FinderPath(DLContentModelImpl.ENTITY_CACHE_ENABLED,
			DLContentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_R_LikeP",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns all the document library contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @return the matching document library contents
	 */
	@Override
	public List<DLContent> findByC_R_LikeP(long companyId, long repositoryId,
		String path) {
		return findByC_R_LikeP(companyId, repositoryId, path,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param start the lower bound of the range of document library contents
	 * @param end the upper bound of the range of document library contents (not inclusive)
	 * @return the range of matching document library contents
	 */
	@Override
	public List<DLContent> findByC_R_LikeP(long companyId, long repositoryId,
		String path, int start, int end) {
		return findByC_R_LikeP(companyId, repositoryId, path, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param start the lower bound of the range of document library contents
	 * @param end the upper bound of the range of document library contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library contents
	 */
	@Override
	public List<DLContent> findByC_R_LikeP(long companyId, long repositoryId,
		String path, int start, int end,
		OrderByComparator<DLContent> orderByComparator) {
		return findByC_R_LikeP(companyId, repositoryId, path, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param start the lower bound of the range of document library contents
	 * @param end the upper bound of the range of document library contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library contents
	 */
	@Override
	public List<DLContent> findByC_R_LikeP(long companyId, long repositoryId,
		String path, int start, int end,
		OrderByComparator<DLContent> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_R_LIKEP;
		finderArgs = new Object[] {
				companyId, repositoryId, path,
				
				start, end, orderByComparator
			};

		List<DLContent> list = null;

		if (retrieveFromCache) {
			list = (List<DLContent>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLContent dlContent : list) {
					if ((companyId != dlContent.getCompanyId()) ||
							(repositoryId != dlContent.getRepositoryId()) ||
							!StringUtil.wildcardMatches(dlContent.getPath(),
								path, CharPool.UNDERLINE, CharPool.PERCENT,
								CharPool.BACK_SLASH, true)) {
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

			query.append(_SQL_SELECT_DLCONTENT_WHERE);

			query.append(_FINDER_COLUMN_C_R_LIKEP_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_R_LIKEP_REPOSITORYID_2);

			boolean bindPath = false;

			if (path == null) {
				query.append(_FINDER_COLUMN_C_R_LIKEP_PATH_1);
			}
			else if (path.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_R_LIKEP_PATH_3);
			}
			else {
				bindPath = true;

				query.append(_FINDER_COLUMN_C_R_LIKEP_PATH_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLContentModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(repositoryId);

				if (bindPath) {
					qPos.add(path);
				}

				if (!pagination) {
					list = (List<DLContent>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLContent>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library content in the ordered set where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library content
	 * @throws NoSuchContentException if a matching document library content could not be found
	 */
	@Override
	public DLContent findByC_R_LikeP_First(long companyId, long repositoryId,
		String path, OrderByComparator<DLContent> orderByComparator)
		throws NoSuchContentException {
		DLContent dlContent = fetchByC_R_LikeP_First(companyId, repositoryId,
				path, orderByComparator);

		if (dlContent != null) {
			return dlContent;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", repositoryId=");
		msg.append(repositoryId);

		msg.append(", path=");
		msg.append(path);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContentException(msg.toString());
	}

	/**
	 * Returns the first document library content in the ordered set where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library content, or <code>null</code> if a matching document library content could not be found
	 */
	@Override
	public DLContent fetchByC_R_LikeP_First(long companyId, long repositoryId,
		String path, OrderByComparator<DLContent> orderByComparator) {
		List<DLContent> list = findByC_R_LikeP(companyId, repositoryId, path,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library content in the ordered set where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library content
	 * @throws NoSuchContentException if a matching document library content could not be found
	 */
	@Override
	public DLContent findByC_R_LikeP_Last(long companyId, long repositoryId,
		String path, OrderByComparator<DLContent> orderByComparator)
		throws NoSuchContentException {
		DLContent dlContent = fetchByC_R_LikeP_Last(companyId, repositoryId,
				path, orderByComparator);

		if (dlContent != null) {
			return dlContent;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", repositoryId=");
		msg.append(repositoryId);

		msg.append(", path=");
		msg.append(path);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContentException(msg.toString());
	}

	/**
	 * Returns the last document library content in the ordered set where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library content, or <code>null</code> if a matching document library content could not be found
	 */
	@Override
	public DLContent fetchByC_R_LikeP_Last(long companyId, long repositoryId,
		String path, OrderByComparator<DLContent> orderByComparator) {
		int count = countByC_R_LikeP(companyId, repositoryId, path);

		if (count == 0) {
			return null;
		}

		List<DLContent> list = findByC_R_LikeP(companyId, repositoryId, path,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library contents before and after the current document library content in the ordered set where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	 *
	 * @param contentId the primary key of the current document library content
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library content
	 * @throws NoSuchContentException if a document library content with the primary key could not be found
	 */
	@Override
	public DLContent[] findByC_R_LikeP_PrevAndNext(long contentId,
		long companyId, long repositoryId, String path,
		OrderByComparator<DLContent> orderByComparator)
		throws NoSuchContentException {
		DLContent dlContent = findByPrimaryKey(contentId);

		Session session = null;

		try {
			session = openSession();

			DLContent[] array = new DLContentImpl[3];

			array[0] = getByC_R_LikeP_PrevAndNext(session, dlContent,
					companyId, repositoryId, path, orderByComparator, true);

			array[1] = dlContent;

			array[2] = getByC_R_LikeP_PrevAndNext(session, dlContent,
					companyId, repositoryId, path, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLContent getByC_R_LikeP_PrevAndNext(Session session,
		DLContent dlContent, long companyId, long repositoryId, String path,
		OrderByComparator<DLContent> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLCONTENT_WHERE);

		query.append(_FINDER_COLUMN_C_R_LIKEP_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_R_LIKEP_REPOSITORYID_2);

		boolean bindPath = false;

		if (path == null) {
			query.append(_FINDER_COLUMN_C_R_LIKEP_PATH_1);
		}
		else if (path.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_C_R_LIKEP_PATH_3);
		}
		else {
			bindPath = true;

			query.append(_FINDER_COLUMN_C_R_LIKEP_PATH_2);
		}

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
			query.append(DLContentModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(repositoryId);

		if (bindPath) {
			qPos.add(path);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlContent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLContent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 */
	@Override
	public void removeByC_R_LikeP(long companyId, long repositoryId, String path) {
		for (DLContent dlContent : findByC_R_LikeP(companyId, repositoryId,
				path, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlContent);
		}
	}

	/**
	 * Returns the number of document library contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @return the number of matching document library contents
	 */
	@Override
	public int countByC_R_LikeP(long companyId, long repositoryId, String path) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_R_LIKEP;

		Object[] finderArgs = new Object[] { companyId, repositoryId, path };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLCONTENT_WHERE);

			query.append(_FINDER_COLUMN_C_R_LIKEP_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_R_LIKEP_REPOSITORYID_2);

			boolean bindPath = false;

			if (path == null) {
				query.append(_FINDER_COLUMN_C_R_LIKEP_PATH_1);
			}
			else if (path.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_R_LIKEP_PATH_3);
			}
			else {
				bindPath = true;

				query.append(_FINDER_COLUMN_C_R_LIKEP_PATH_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(repositoryId);

				if (bindPath) {
					qPos.add(path);
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

	private static final String _FINDER_COLUMN_C_R_LIKEP_COMPANYID_2 = "dlContent.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_R_LIKEP_REPOSITORYID_2 = "dlContent.repositoryId = ? AND ";
	private static final String _FINDER_COLUMN_C_R_LIKEP_PATH_1 = "dlContent.path IS NULL";
	private static final String _FINDER_COLUMN_C_R_LIKEP_PATH_2 = "dlContent.path LIKE ?";
	private static final String _FINDER_COLUMN_C_R_LIKEP_PATH_3 = "(dlContent.path IS NULL OR dlContent.path LIKE '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_R_P_V = new FinderPath(DLContentModelImpl.ENTITY_CACHE_ENABLED,
			DLContentModelImpl.FINDER_CACHE_ENABLED, DLContentImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_R_P_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			},
			DLContentModelImpl.COMPANYID_COLUMN_BITMASK |
			DLContentModelImpl.REPOSITORYID_COLUMN_BITMASK |
			DLContentModelImpl.PATH_COLUMN_BITMASK |
			DLContentModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_R_P_V = new FinderPath(DLContentModelImpl.ENTITY_CACHE_ENABLED,
			DLContentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_R_P_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			});

	/**
	 * Returns the document library content where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; or throws a {@link NoSuchContentException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param version the version
	 * @return the matching document library content
	 * @throws NoSuchContentException if a matching document library content could not be found
	 */
	@Override
	public DLContent findByC_R_P_V(long companyId, long repositoryId,
		String path, String version) throws NoSuchContentException {
		DLContent dlContent = fetchByC_R_P_V(companyId, repositoryId, path,
				version);

		if (dlContent == null) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", repositoryId=");
			msg.append(repositoryId);

			msg.append(", path=");
			msg.append(path);

			msg.append(", version=");
			msg.append(version);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchContentException(msg.toString());
		}

		return dlContent;
	}

	/**
	 * Returns the document library content where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param version the version
	 * @return the matching document library content, or <code>null</code> if a matching document library content could not be found
	 */
	@Override
	public DLContent fetchByC_R_P_V(long companyId, long repositoryId,
		String path, String version) {
		return fetchByC_R_P_V(companyId, repositoryId, path, version, true);
	}

	/**
	 * Returns the document library content where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param version the version
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching document library content, or <code>null</code> if a matching document library content could not be found
	 */
	@Override
	public DLContent fetchByC_R_P_V(long companyId, long repositoryId,
		String path, String version, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] {
				companyId, repositoryId, path, version
			};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_R_P_V,
					finderArgs, this);
		}

		if (result instanceof DLContent) {
			DLContent dlContent = (DLContent)result;

			if ((companyId != dlContent.getCompanyId()) ||
					(repositoryId != dlContent.getRepositoryId()) ||
					!Validator.equals(path, dlContent.getPath()) ||
					!Validator.equals(version, dlContent.getVersion())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_SELECT_DLCONTENT_WHERE);

			query.append(_FINDER_COLUMN_C_R_P_V_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_R_P_V_REPOSITORYID_2);

			boolean bindPath = false;

			if (path == null) {
				query.append(_FINDER_COLUMN_C_R_P_V_PATH_1);
			}
			else if (path.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_R_P_V_PATH_3);
			}
			else {
				bindPath = true;

				query.append(_FINDER_COLUMN_C_R_P_V_PATH_2);
			}

			boolean bindVersion = false;

			if (version == null) {
				query.append(_FINDER_COLUMN_C_R_P_V_VERSION_1);
			}
			else if (version.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_R_P_V_VERSION_3);
			}
			else {
				bindVersion = true;

				query.append(_FINDER_COLUMN_C_R_P_V_VERSION_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(repositoryId);

				if (bindPath) {
					qPos.add(path);
				}

				if (bindVersion) {
					qPos.add(version);
				}

				List<DLContent> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_R_P_V,
						finderArgs, list);
				}
				else {
					DLContent dlContent = list.get(0);

					result = dlContent;

					cacheResult(dlContent);

					if ((dlContent.getCompanyId() != companyId) ||
							(dlContent.getRepositoryId() != repositoryId) ||
							(dlContent.getPath() == null) ||
							!dlContent.getPath().equals(path) ||
							(dlContent.getVersion() == null) ||
							!dlContent.getVersion().equals(version)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_R_P_V,
							finderArgs, dlContent);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_R_P_V,
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
			return (DLContent)result;
		}
	}

	/**
	 * Removes the document library content where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param version the version
	 * @return the document library content that was removed
	 */
	@Override
	public DLContent removeByC_R_P_V(long companyId, long repositoryId,
		String path, String version) throws NoSuchContentException {
		DLContent dlContent = findByC_R_P_V(companyId, repositoryId, path,
				version);

		return remove(dlContent);
	}

	/**
	 * Returns the number of document library contents where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param version the version
	 * @return the number of matching document library contents
	 */
	@Override
	public int countByC_R_P_V(long companyId, long repositoryId, String path,
		String version) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_R_P_V;

		Object[] finderArgs = new Object[] {
				companyId, repositoryId, path, version
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_DLCONTENT_WHERE);

			query.append(_FINDER_COLUMN_C_R_P_V_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_R_P_V_REPOSITORYID_2);

			boolean bindPath = false;

			if (path == null) {
				query.append(_FINDER_COLUMN_C_R_P_V_PATH_1);
			}
			else if (path.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_R_P_V_PATH_3);
			}
			else {
				bindPath = true;

				query.append(_FINDER_COLUMN_C_R_P_V_PATH_2);
			}

			boolean bindVersion = false;

			if (version == null) {
				query.append(_FINDER_COLUMN_C_R_P_V_VERSION_1);
			}
			else if (version.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_R_P_V_VERSION_3);
			}
			else {
				bindVersion = true;

				query.append(_FINDER_COLUMN_C_R_P_V_VERSION_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(repositoryId);

				if (bindPath) {
					qPos.add(path);
				}

				if (bindVersion) {
					qPos.add(version);
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

	private static final String _FINDER_COLUMN_C_R_P_V_COMPANYID_2 = "dlContent.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_R_P_V_REPOSITORYID_2 = "dlContent.repositoryId = ? AND ";
	private static final String _FINDER_COLUMN_C_R_P_V_PATH_1 = "dlContent.path IS NULL AND ";
	private static final String _FINDER_COLUMN_C_R_P_V_PATH_2 = "dlContent.path = ? AND ";
	private static final String _FINDER_COLUMN_C_R_P_V_PATH_3 = "(dlContent.path IS NULL OR dlContent.path = '') AND ";
	private static final String _FINDER_COLUMN_C_R_P_V_VERSION_1 = "dlContent.version IS NULL";
	private static final String _FINDER_COLUMN_C_R_P_V_VERSION_2 = "dlContent.version = ?";
	private static final String _FINDER_COLUMN_C_R_P_V_VERSION_3 = "(dlContent.version IS NULL OR dlContent.version = '')";

	public DLContentPersistenceImpl() {
		setModelClass(DLContent.class);
	}

	/**
	 * Caches the document library content in the entity cache if it is enabled.
	 *
	 * @param dlContent the document library content
	 */
	@Override
	public void cacheResult(DLContent dlContent) {
		entityCache.putResult(DLContentModelImpl.ENTITY_CACHE_ENABLED,
			DLContentImpl.class, dlContent.getPrimaryKey(), dlContent);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_R_P_V,
			new Object[] {
				dlContent.getCompanyId(), dlContent.getRepositoryId(),
				dlContent.getPath(), dlContent.getVersion()
			}, dlContent);

		dlContent.resetOriginalValues();
	}

	/**
	 * Caches the document library contents in the entity cache if it is enabled.
	 *
	 * @param dlContents the document library contents
	 */
	@Override
	public void cacheResult(List<DLContent> dlContents) {
		for (DLContent dlContent : dlContents) {
			if (entityCache.getResult(DLContentModelImpl.ENTITY_CACHE_ENABLED,
						DLContentImpl.class, dlContent.getPrimaryKey()) == null) {
				cacheResult(dlContent);
			}
			else {
				dlContent.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all document library contents.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DLContentImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the document library content.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DLContent dlContent) {
		entityCache.removeResult(DLContentModelImpl.ENTITY_CACHE_ENABLED,
			DLContentImpl.class, dlContent.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((DLContentModelImpl)dlContent);
	}

	@Override
	public void clearCache(List<DLContent> dlContents) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DLContent dlContent : dlContents) {
			entityCache.removeResult(DLContentModelImpl.ENTITY_CACHE_ENABLED,
				DLContentImpl.class, dlContent.getPrimaryKey());

			clearUniqueFindersCache((DLContentModelImpl)dlContent);
		}
	}

	protected void cacheUniqueFindersCache(
		DLContentModelImpl dlContentModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					dlContentModelImpl.getCompanyId(),
					dlContentModelImpl.getRepositoryId(),
					dlContentModelImpl.getPath(),
					dlContentModelImpl.getVersion()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_C_R_P_V, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_C_R_P_V, args,
				dlContentModelImpl);
		}
		else {
			if ((dlContentModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_R_P_V.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlContentModelImpl.getCompanyId(),
						dlContentModelImpl.getRepositoryId(),
						dlContentModelImpl.getPath(),
						dlContentModelImpl.getVersion()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_C_R_P_V, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_C_R_P_V, args,
					dlContentModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		DLContentModelImpl dlContentModelImpl) {
		Object[] args = new Object[] {
				dlContentModelImpl.getCompanyId(),
				dlContentModelImpl.getRepositoryId(),
				dlContentModelImpl.getPath(), dlContentModelImpl.getVersion()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_C_R_P_V, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_C_R_P_V, args);

		if ((dlContentModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_R_P_V.getColumnBitmask()) != 0) {
			args = new Object[] {
					dlContentModelImpl.getOriginalCompanyId(),
					dlContentModelImpl.getOriginalRepositoryId(),
					dlContentModelImpl.getOriginalPath(),
					dlContentModelImpl.getOriginalVersion()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_R_P_V, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_R_P_V, args);
		}
	}

	/**
	 * Creates a new document library content with the primary key. Does not add the document library content to the database.
	 *
	 * @param contentId the primary key for the new document library content
	 * @return the new document library content
	 */
	@Override
	public DLContent create(long contentId) {
		DLContent dlContent = new DLContentImpl();

		dlContent.setNew(true);
		dlContent.setPrimaryKey(contentId);

		return dlContent;
	}

	/**
	 * Removes the document library content with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contentId the primary key of the document library content
	 * @return the document library content that was removed
	 * @throws NoSuchContentException if a document library content with the primary key could not be found
	 */
	@Override
	public DLContent remove(long contentId) throws NoSuchContentException {
		return remove((Serializable)contentId);
	}

	/**
	 * Removes the document library content with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the document library content
	 * @return the document library content that was removed
	 * @throws NoSuchContentException if a document library content with the primary key could not be found
	 */
	@Override
	public DLContent remove(Serializable primaryKey)
		throws NoSuchContentException {
		Session session = null;

		try {
			session = openSession();

			DLContent dlContent = (DLContent)session.get(DLContentImpl.class,
					primaryKey);

			if (dlContent == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchContentException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(dlContent);
		}
		catch (NoSuchContentException nsee) {
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
	protected DLContent removeImpl(DLContent dlContent) {
		dlContent = toUnwrappedModel(dlContent);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(dlContent)) {
				dlContent = (DLContent)session.get(DLContentImpl.class,
						dlContent.getPrimaryKeyObj());
			}

			if (dlContent != null) {
				session.delete(dlContent);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (dlContent != null) {
			clearCache(dlContent);
		}

		return dlContent;
	}

	@Override
	public DLContent updateImpl(DLContent dlContent) {
		dlContent = toUnwrappedModel(dlContent);

		boolean isNew = dlContent.isNew();

		DLContentModelImpl dlContentModelImpl = (DLContentModelImpl)dlContent;

		Session session = null;

		try {
			session = openSession();

			if (dlContent.isNew()) {
				session.save(dlContent);

				dlContent.setNew(false);
			}
			else {
				session.evict(dlContent);
				session.saveOrUpdate(dlContent);
			}

			session.flush();
			session.clear();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !DLContentModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((dlContentModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_R.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlContentModelImpl.getOriginalCompanyId(),
						dlContentModelImpl.getOriginalRepositoryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_R, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_R,
					args);

				args = new Object[] {
						dlContentModelImpl.getCompanyId(),
						dlContentModelImpl.getRepositoryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_R, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_R,
					args);
			}

			if ((dlContentModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_R_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlContentModelImpl.getOriginalCompanyId(),
						dlContentModelImpl.getOriginalRepositoryId(),
						dlContentModelImpl.getOriginalPath()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_R_P, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_R_P,
					args);

				args = new Object[] {
						dlContentModelImpl.getCompanyId(),
						dlContentModelImpl.getRepositoryId(),
						dlContentModelImpl.getPath()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_R_P, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_R_P,
					args);
			}
		}

		entityCache.putResult(DLContentModelImpl.ENTITY_CACHE_ENABLED,
			DLContentImpl.class, dlContent.getPrimaryKey(), dlContent, false);

		clearUniqueFindersCache(dlContentModelImpl);
		cacheUniqueFindersCache(dlContentModelImpl, isNew);

		dlContent.resetOriginalValues();

		return dlContent;
	}

	protected DLContent toUnwrappedModel(DLContent dlContent) {
		if (dlContent instanceof DLContentImpl) {
			return dlContent;
		}

		DLContentImpl dlContentImpl = new DLContentImpl();

		dlContentImpl.setNew(dlContent.isNew());
		dlContentImpl.setPrimaryKey(dlContent.getPrimaryKey());

		dlContentImpl.setContentId(dlContent.getContentId());
		dlContentImpl.setGroupId(dlContent.getGroupId());
		dlContentImpl.setCompanyId(dlContent.getCompanyId());
		dlContentImpl.setRepositoryId(dlContent.getRepositoryId());
		dlContentImpl.setPath(dlContent.getPath());
		dlContentImpl.setVersion(dlContent.getVersion());
		dlContentImpl.setData(dlContent.getData());
		dlContentImpl.setSize(dlContent.getSize());

		return dlContentImpl;
	}

	/**
	 * Returns the document library content with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the document library content
	 * @return the document library content
	 * @throws NoSuchContentException if a document library content with the primary key could not be found
	 */
	@Override
	public DLContent findByPrimaryKey(Serializable primaryKey)
		throws NoSuchContentException {
		DLContent dlContent = fetchByPrimaryKey(primaryKey);

		if (dlContent == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchContentException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return dlContent;
	}

	/**
	 * Returns the document library content with the primary key or throws a {@link NoSuchContentException} if it could not be found.
	 *
	 * @param contentId the primary key of the document library content
	 * @return the document library content
	 * @throws NoSuchContentException if a document library content with the primary key could not be found
	 */
	@Override
	public DLContent findByPrimaryKey(long contentId)
		throws NoSuchContentException {
		return findByPrimaryKey((Serializable)contentId);
	}

	/**
	 * Returns the document library content with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the document library content
	 * @return the document library content, or <code>null</code> if a document library content with the primary key could not be found
	 */
	@Override
	public DLContent fetchByPrimaryKey(Serializable primaryKey) {
		DLContent dlContent = (DLContent)entityCache.getResult(DLContentModelImpl.ENTITY_CACHE_ENABLED,
				DLContentImpl.class, primaryKey);

		if (dlContent == _nullDLContent) {
			return null;
		}

		if (dlContent == null) {
			Session session = null;

			try {
				session = openSession();

				dlContent = (DLContent)session.get(DLContentImpl.class,
						primaryKey);

				if (dlContent != null) {
					cacheResult(dlContent);
				}
				else {
					entityCache.putResult(DLContentModelImpl.ENTITY_CACHE_ENABLED,
						DLContentImpl.class, primaryKey, _nullDLContent);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(DLContentModelImpl.ENTITY_CACHE_ENABLED,
					DLContentImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return dlContent;
	}

	/**
	 * Returns the document library content with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param contentId the primary key of the document library content
	 * @return the document library content, or <code>null</code> if a document library content with the primary key could not be found
	 */
	@Override
	public DLContent fetchByPrimaryKey(long contentId) {
		return fetchByPrimaryKey((Serializable)contentId);
	}

	@Override
	public Map<Serializable, DLContent> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DLContent> map = new HashMap<Serializable, DLContent>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DLContent dlContent = fetchByPrimaryKey(primaryKey);

			if (dlContent != null) {
				map.put(primaryKey, dlContent);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			DLContent dlContent = (DLContent)entityCache.getResult(DLContentModelImpl.ENTITY_CACHE_ENABLED,
					DLContentImpl.class, primaryKey);

			if (dlContent == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, dlContent);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_DLCONTENT_WHERE_PKS_IN);

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

			for (DLContent dlContent : (List<DLContent>)q.list()) {
				map.put(dlContent.getPrimaryKeyObj(), dlContent);

				cacheResult(dlContent);

				uncachedPrimaryKeys.remove(dlContent.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(DLContentModelImpl.ENTITY_CACHE_ENABLED,
					DLContentImpl.class, primaryKey, _nullDLContent);
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
	 * Returns all the document library contents.
	 *
	 * @return the document library contents
	 */
	@Override
	public List<DLContent> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library contents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library contents
	 * @param end the upper bound of the range of document library contents (not inclusive)
	 * @return the range of document library contents
	 */
	@Override
	public List<DLContent> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library contents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library contents
	 * @param end the upper bound of the range of document library contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of document library contents
	 */
	@Override
	public List<DLContent> findAll(int start, int end,
		OrderByComparator<DLContent> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library contents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library contents
	 * @param end the upper bound of the range of document library contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of document library contents
	 */
	@Override
	public List<DLContent> findAll(int start, int end,
		OrderByComparator<DLContent> orderByComparator,
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

		List<DLContent> list = null;

		if (retrieveFromCache) {
			list = (List<DLContent>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DLCONTENT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DLCONTENT;

				if (pagination) {
					sql = sql.concat(DLContentModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DLContent>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLContent>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the document library contents from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DLContent dlContent : findAll()) {
			remove(dlContent);
		}
	}

	/**
	 * Returns the number of document library contents.
	 *
	 * @return the number of document library contents
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DLCONTENT);

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
		return DLContentModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the document library content persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(DLContentImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_DLCONTENT = "SELECT dlContent FROM DLContent dlContent";
	private static final String _SQL_SELECT_DLCONTENT_WHERE_PKS_IN = "SELECT dlContent FROM DLContent dlContent WHERE contentId IN (";
	private static final String _SQL_SELECT_DLCONTENT_WHERE = "SELECT dlContent FROM DLContent dlContent WHERE ";
	private static final String _SQL_COUNT_DLCONTENT = "SELECT COUNT(dlContent) FROM DLContent dlContent";
	private static final String _SQL_COUNT_DLCONTENT_WHERE = "SELECT COUNT(dlContent) FROM DLContent dlContent WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "dlContent.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DLContent exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DLContent exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(DLContentPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"path", "data", "size"
			});
	private static final DLContent _nullDLContent = new DLContentImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<DLContent> toCacheModel() {
				return _nullDLContentCacheModel;
			}
		};

	private static final CacheModel<DLContent> _nullDLContentCacheModel = new CacheModel<DLContent>() {
			@Override
			public DLContent toEntityModel() {
				return _nullDLContent;
			}
		};
}