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

package com.liferay.dynamic.data.mapping.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.exception.NoSuchTemplateLinkException;
import com.liferay.dynamic.data.mapping.model.DDMTemplateLink;
import com.liferay.dynamic.data.mapping.model.impl.DDMTemplateLinkImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMTemplateLinkModelImpl;
import com.liferay.dynamic.data.mapping.service.persistence.DDMTemplateLinkPersistence;

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
 * The persistence implementation for the d d m template link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateLinkPersistence
 * @see com.liferay.dynamic.data.mapping.service.persistence.DDMTemplateLinkUtil
 * @generated
 */
@ProviderType
public class DDMTemplateLinkPersistenceImpl extends BasePersistenceImpl<DDMTemplateLink>
	implements DDMTemplateLinkPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DDMTemplateLinkUtil} to access the d d m template link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DDMTemplateLinkImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DDMTemplateLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateLinkModelImpl.FINDER_CACHE_ENABLED,
			DDMTemplateLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DDMTemplateLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateLinkModelImpl.FINDER_CACHE_ENABLED,
			DDMTemplateLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DDMTemplateLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CLASSNAMEID =
		new FinderPath(DDMTemplateLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateLinkModelImpl.FINDER_CACHE_ENABLED,
			DDMTemplateLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByClassNameId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CLASSNAMEID =
		new FinderPath(DDMTemplateLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateLinkModelImpl.FINDER_CACHE_ENABLED,
			DDMTemplateLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByClassNameId",
			new String[] { Long.class.getName() },
			DDMTemplateLinkModelImpl.CLASSNAMEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CLASSNAMEID = new FinderPath(DDMTemplateLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByClassNameId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the d d m template links where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @return the matching d d m template links
	 */
	@Override
	public List<DDMTemplateLink> findByClassNameId(long classNameId) {
		return findByClassNameId(classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m template links where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m template links
	 * @param end the upper bound of the range of d d m template links (not inclusive)
	 * @return the range of matching d d m template links
	 */
	@Override
	public List<DDMTemplateLink> findByClassNameId(long classNameId, int start,
		int end) {
		return findByClassNameId(classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m template links where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m template links
	 * @param end the upper bound of the range of d d m template links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m template links
	 */
	@Override
	public List<DDMTemplateLink> findByClassNameId(long classNameId, int start,
		int end, OrderByComparator<DDMTemplateLink> orderByComparator) {
		return findByClassNameId(classNameId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the d d m template links where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m template links
	 * @param end the upper bound of the range of d d m template links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m template links
	 */
	@Override
	public List<DDMTemplateLink> findByClassNameId(long classNameId, int start,
		int end, OrderByComparator<DDMTemplateLink> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CLASSNAMEID;
			finderArgs = new Object[] { classNameId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_CLASSNAMEID;
			finderArgs = new Object[] { classNameId, start, end, orderByComparator };
		}

		List<DDMTemplateLink> list = null;

		if (retrieveFromCache) {
			list = (List<DDMTemplateLink>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMTemplateLink ddmTemplateLink : list) {
					if ((classNameId != ddmTemplateLink.getClassNameId())) {
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

			query.append(_SQL_SELECT_DDMTEMPLATELINK_WHERE);

			query.append(_FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMTemplateLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				if (!pagination) {
					list = (List<DDMTemplateLink>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMTemplateLink>)QueryUtil.list(q,
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
	 * Returns the first d d m template link in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template link
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateLinkException if a matching d d m template link could not be found
	 */
	@Override
	public DDMTemplateLink findByClassNameId_First(long classNameId,
		OrderByComparator<DDMTemplateLink> orderByComparator)
		throws NoSuchTemplateLinkException {
		DDMTemplateLink ddmTemplateLink = fetchByClassNameId_First(classNameId,
				orderByComparator);

		if (ddmTemplateLink != null) {
			return ddmTemplateLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateLinkException(msg.toString());
	}

	/**
	 * Returns the first d d m template link in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template link, or <code>null</code> if a matching d d m template link could not be found
	 */
	@Override
	public DDMTemplateLink fetchByClassNameId_First(long classNameId,
		OrderByComparator<DDMTemplateLink> orderByComparator) {
		List<DDMTemplateLink> list = findByClassNameId(classNameId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m template link in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template link
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateLinkException if a matching d d m template link could not be found
	 */
	@Override
	public DDMTemplateLink findByClassNameId_Last(long classNameId,
		OrderByComparator<DDMTemplateLink> orderByComparator)
		throws NoSuchTemplateLinkException {
		DDMTemplateLink ddmTemplateLink = fetchByClassNameId_Last(classNameId,
				orderByComparator);

		if (ddmTemplateLink != null) {
			return ddmTemplateLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateLinkException(msg.toString());
	}

	/**
	 * Returns the last d d m template link in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template link, or <code>null</code> if a matching d d m template link could not be found
	 */
	@Override
	public DDMTemplateLink fetchByClassNameId_Last(long classNameId,
		OrderByComparator<DDMTemplateLink> orderByComparator) {
		int count = countByClassNameId(classNameId);

		if (count == 0) {
			return null;
		}

		List<DDMTemplateLink> list = findByClassNameId(classNameId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m template links before and after the current d d m template link in the ordered set where classNameId = &#63;.
	 *
	 * @param templateLinkId the primary key of the current d d m template link
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template link
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateLinkException if a d d m template link with the primary key could not be found
	 */
	@Override
	public DDMTemplateLink[] findByClassNameId_PrevAndNext(
		long templateLinkId, long classNameId,
		OrderByComparator<DDMTemplateLink> orderByComparator)
		throws NoSuchTemplateLinkException {
		DDMTemplateLink ddmTemplateLink = findByPrimaryKey(templateLinkId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplateLink[] array = new DDMTemplateLinkImpl[3];

			array[0] = getByClassNameId_PrevAndNext(session, ddmTemplateLink,
					classNameId, orderByComparator, true);

			array[1] = ddmTemplateLink;

			array[2] = getByClassNameId_PrevAndNext(session, ddmTemplateLink,
					classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMTemplateLink getByClassNameId_PrevAndNext(Session session,
		DDMTemplateLink ddmTemplateLink, long classNameId,
		OrderByComparator<DDMTemplateLink> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMTEMPLATELINK_WHERE);

		query.append(_FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2);

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
			query.append(DDMTemplateLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplateLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplateLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m template links where classNameId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByClassNameId(long classNameId) {
		for (DDMTemplateLink ddmTemplateLink : findByClassNameId(classNameId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmTemplateLink);
		}
	}

	/**
	 * Returns the number of d d m template links where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @return the number of matching d d m template links
	 */
	@Override
	public int countByClassNameId(long classNameId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CLASSNAMEID;

		Object[] finderArgs = new Object[] { classNameId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMTEMPLATELINK_WHERE);

			query.append(_FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

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

	private static final String _FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2 = "ddmTemplateLink.classNameId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_TEMPLATEID =
		new FinderPath(DDMTemplateLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateLinkModelImpl.FINDER_CACHE_ENABLED,
			DDMTemplateLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByTemplateId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TEMPLATEID =
		new FinderPath(DDMTemplateLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateLinkModelImpl.FINDER_CACHE_ENABLED,
			DDMTemplateLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByTemplateId",
			new String[] { Long.class.getName() },
			DDMTemplateLinkModelImpl.TEMPLATEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_TEMPLATEID = new FinderPath(DDMTemplateLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTemplateId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the d d m template links where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @return the matching d d m template links
	 */
	@Override
	public List<DDMTemplateLink> findByTemplateId(long templateId) {
		return findByTemplateId(templateId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m template links where templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param start the lower bound of the range of d d m template links
	 * @param end the upper bound of the range of d d m template links (not inclusive)
	 * @return the range of matching d d m template links
	 */
	@Override
	public List<DDMTemplateLink> findByTemplateId(long templateId, int start,
		int end) {
		return findByTemplateId(templateId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m template links where templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param start the lower bound of the range of d d m template links
	 * @param end the upper bound of the range of d d m template links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m template links
	 */
	@Override
	public List<DDMTemplateLink> findByTemplateId(long templateId, int start,
		int end, OrderByComparator<DDMTemplateLink> orderByComparator) {
		return findByTemplateId(templateId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m template links where templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param start the lower bound of the range of d d m template links
	 * @param end the upper bound of the range of d d m template links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m template links
	 */
	@Override
	public List<DDMTemplateLink> findByTemplateId(long templateId, int start,
		int end, OrderByComparator<DDMTemplateLink> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TEMPLATEID;
			finderArgs = new Object[] { templateId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_TEMPLATEID;
			finderArgs = new Object[] { templateId, start, end, orderByComparator };
		}

		List<DDMTemplateLink> list = null;

		if (retrieveFromCache) {
			list = (List<DDMTemplateLink>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMTemplateLink ddmTemplateLink : list) {
					if ((templateId != ddmTemplateLink.getTemplateId())) {
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

			query.append(_SQL_SELECT_DDMTEMPLATELINK_WHERE);

			query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMTemplateLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(templateId);

				if (!pagination) {
					list = (List<DDMTemplateLink>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMTemplateLink>)QueryUtil.list(q,
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
	 * Returns the first d d m template link in the ordered set where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template link
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateLinkException if a matching d d m template link could not be found
	 */
	@Override
	public DDMTemplateLink findByTemplateId_First(long templateId,
		OrderByComparator<DDMTemplateLink> orderByComparator)
		throws NoSuchTemplateLinkException {
		DDMTemplateLink ddmTemplateLink = fetchByTemplateId_First(templateId,
				orderByComparator);

		if (ddmTemplateLink != null) {
			return ddmTemplateLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("templateId=");
		msg.append(templateId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateLinkException(msg.toString());
	}

	/**
	 * Returns the first d d m template link in the ordered set where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template link, or <code>null</code> if a matching d d m template link could not be found
	 */
	@Override
	public DDMTemplateLink fetchByTemplateId_First(long templateId,
		OrderByComparator<DDMTemplateLink> orderByComparator) {
		List<DDMTemplateLink> list = findByTemplateId(templateId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m template link in the ordered set where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template link
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateLinkException if a matching d d m template link could not be found
	 */
	@Override
	public DDMTemplateLink findByTemplateId_Last(long templateId,
		OrderByComparator<DDMTemplateLink> orderByComparator)
		throws NoSuchTemplateLinkException {
		DDMTemplateLink ddmTemplateLink = fetchByTemplateId_Last(templateId,
				orderByComparator);

		if (ddmTemplateLink != null) {
			return ddmTemplateLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("templateId=");
		msg.append(templateId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateLinkException(msg.toString());
	}

	/**
	 * Returns the last d d m template link in the ordered set where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template link, or <code>null</code> if a matching d d m template link could not be found
	 */
	@Override
	public DDMTemplateLink fetchByTemplateId_Last(long templateId,
		OrderByComparator<DDMTemplateLink> orderByComparator) {
		int count = countByTemplateId(templateId);

		if (count == 0) {
			return null;
		}

		List<DDMTemplateLink> list = findByTemplateId(templateId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m template links before and after the current d d m template link in the ordered set where templateId = &#63;.
	 *
	 * @param templateLinkId the primary key of the current d d m template link
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template link
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateLinkException if a d d m template link with the primary key could not be found
	 */
	@Override
	public DDMTemplateLink[] findByTemplateId_PrevAndNext(long templateLinkId,
		long templateId, OrderByComparator<DDMTemplateLink> orderByComparator)
		throws NoSuchTemplateLinkException {
		DDMTemplateLink ddmTemplateLink = findByPrimaryKey(templateLinkId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplateLink[] array = new DDMTemplateLinkImpl[3];

			array[0] = getByTemplateId_PrevAndNext(session, ddmTemplateLink,
					templateId, orderByComparator, true);

			array[1] = ddmTemplateLink;

			array[2] = getByTemplateId_PrevAndNext(session, ddmTemplateLink,
					templateId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMTemplateLink getByTemplateId_PrevAndNext(Session session,
		DDMTemplateLink ddmTemplateLink, long templateId,
		OrderByComparator<DDMTemplateLink> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMTEMPLATELINK_WHERE);

		query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_2);

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
			query.append(DDMTemplateLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(templateId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplateLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplateLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m template links where templateId = &#63; from the database.
	 *
	 * @param templateId the template ID
	 */
	@Override
	public void removeByTemplateId(long templateId) {
		for (DDMTemplateLink ddmTemplateLink : findByTemplateId(templateId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmTemplateLink);
		}
	}

	/**
	 * Returns the number of d d m template links where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @return the number of matching d d m template links
	 */
	@Override
	public int countByTemplateId(long templateId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_TEMPLATEID;

		Object[] finderArgs = new Object[] { templateId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMTEMPLATELINK_WHERE);

			query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(templateId);

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

	private static final String _FINDER_COLUMN_TEMPLATEID_TEMPLATEID_2 = "ddmTemplateLink.templateId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C = new FinderPath(DDMTemplateLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateLinkModelImpl.FINDER_CACHE_ENABLED,
			DDMTemplateLinkImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			DDMTemplateLinkModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			DDMTemplateLinkModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(DDMTemplateLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns the d d m template link where classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.dynamic.data.mapping.NoSuchTemplateLinkException} if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching d d m template link
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateLinkException if a matching d d m template link could not be found
	 */
	@Override
	public DDMTemplateLink findByC_C(long classNameId, long classPK)
		throws NoSuchTemplateLinkException {
		DDMTemplateLink ddmTemplateLink = fetchByC_C(classNameId, classPK);

		if (ddmTemplateLink == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchTemplateLinkException(msg.toString());
		}

		return ddmTemplateLink;
	}

	/**
	 * Returns the d d m template link where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching d d m template link, or <code>null</code> if a matching d d m template link could not be found
	 */
	@Override
	public DDMTemplateLink fetchByC_C(long classNameId, long classPK) {
		return fetchByC_C(classNameId, classPK, true);
	}

	/**
	 * Returns the d d m template link where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching d d m template link, or <code>null</code> if a matching d d m template link could not be found
	 */
	@Override
	public DDMTemplateLink fetchByC_C(long classNameId, long classPK,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { classNameId, classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_C,
					finderArgs, this);
		}

		if (result instanceof DDMTemplateLink) {
			DDMTemplateLink ddmTemplateLink = (DDMTemplateLink)result;

			if ((classNameId != ddmTemplateLink.getClassNameId()) ||
					(classPK != ddmTemplateLink.getClassPK())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DDMTEMPLATELINK_WHERE);

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

				List<DDMTemplateLink> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_C, finderArgs,
						list);
				}
				else {
					DDMTemplateLink ddmTemplateLink = list.get(0);

					result = ddmTemplateLink;

					cacheResult(ddmTemplateLink);

					if ((ddmTemplateLink.getClassNameId() != classNameId) ||
							(ddmTemplateLink.getClassPK() != classPK)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_C,
							finderArgs, ddmTemplateLink);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_C, finderArgs);

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
			return (DDMTemplateLink)result;
		}
	}

	/**
	 * Removes the d d m template link where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the d d m template link that was removed
	 */
	@Override
	public DDMTemplateLink removeByC_C(long classNameId, long classPK)
		throws NoSuchTemplateLinkException {
		DDMTemplateLink ddmTemplateLink = findByC_C(classNameId, classPK);

		return remove(ddmTemplateLink);
	}

	/**
	 * Returns the number of d d m template links where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching d d m template links
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C;

		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMTEMPLATELINK_WHERE);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "ddmTemplateLink.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "ddmTemplateLink.classPK = ?";

	public DDMTemplateLinkPersistenceImpl() {
		setModelClass(DDMTemplateLink.class);
	}

	/**
	 * Caches the d d m template link in the entity cache if it is enabled.
	 *
	 * @param ddmTemplateLink the d d m template link
	 */
	@Override
	public void cacheResult(DDMTemplateLink ddmTemplateLink) {
		entityCache.putResult(DDMTemplateLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateLinkImpl.class, ddmTemplateLink.getPrimaryKey(),
			ddmTemplateLink);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				ddmTemplateLink.getClassNameId(), ddmTemplateLink.getClassPK()
			}, ddmTemplateLink);

		ddmTemplateLink.resetOriginalValues();
	}

	/**
	 * Caches the d d m template links in the entity cache if it is enabled.
	 *
	 * @param ddmTemplateLinks the d d m template links
	 */
	@Override
	public void cacheResult(List<DDMTemplateLink> ddmTemplateLinks) {
		for (DDMTemplateLink ddmTemplateLink : ddmTemplateLinks) {
			if (entityCache.getResult(
						DDMTemplateLinkModelImpl.ENTITY_CACHE_ENABLED,
						DDMTemplateLinkImpl.class,
						ddmTemplateLink.getPrimaryKey()) == null) {
				cacheResult(ddmTemplateLink);
			}
			else {
				ddmTemplateLink.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all d d m template links.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DDMTemplateLinkImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the d d m template link.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DDMTemplateLink ddmTemplateLink) {
		entityCache.removeResult(DDMTemplateLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateLinkImpl.class, ddmTemplateLink.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((DDMTemplateLinkModelImpl)ddmTemplateLink);
	}

	@Override
	public void clearCache(List<DDMTemplateLink> ddmTemplateLinks) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DDMTemplateLink ddmTemplateLink : ddmTemplateLinks) {
			entityCache.removeResult(DDMTemplateLinkModelImpl.ENTITY_CACHE_ENABLED,
				DDMTemplateLinkImpl.class, ddmTemplateLink.getPrimaryKey());

			clearUniqueFindersCache((DDMTemplateLinkModelImpl)ddmTemplateLink);
		}
	}

	protected void cacheUniqueFindersCache(
		DDMTemplateLinkModelImpl ddmTemplateLinkModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					ddmTemplateLinkModelImpl.getClassNameId(),
					ddmTemplateLinkModelImpl.getClassPK()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_C_C, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_C_C, args,
				ddmTemplateLinkModelImpl);
		}
		else {
			if ((ddmTemplateLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmTemplateLinkModelImpl.getClassNameId(),
						ddmTemplateLinkModelImpl.getClassPK()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_C_C, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_C_C, args,
					ddmTemplateLinkModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		DDMTemplateLinkModelImpl ddmTemplateLinkModelImpl) {
		Object[] args = new Object[] {
				ddmTemplateLinkModelImpl.getClassNameId(),
				ddmTemplateLinkModelImpl.getClassPK()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_C_C, args);

		if ((ddmTemplateLinkModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_C.getColumnBitmask()) != 0) {
			args = new Object[] {
					ddmTemplateLinkModelImpl.getOriginalClassNameId(),
					ddmTemplateLinkModelImpl.getOriginalClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_C, args);
		}
	}

	/**
	 * Creates a new d d m template link with the primary key. Does not add the d d m template link to the database.
	 *
	 * @param templateLinkId the primary key for the new d d m template link
	 * @return the new d d m template link
	 */
	@Override
	public DDMTemplateLink create(long templateLinkId) {
		DDMTemplateLink ddmTemplateLink = new DDMTemplateLinkImpl();

		ddmTemplateLink.setNew(true);
		ddmTemplateLink.setPrimaryKey(templateLinkId);

		return ddmTemplateLink;
	}

	/**
	 * Removes the d d m template link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param templateLinkId the primary key of the d d m template link
	 * @return the d d m template link that was removed
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateLinkException if a d d m template link with the primary key could not be found
	 */
	@Override
	public DDMTemplateLink remove(long templateLinkId)
		throws NoSuchTemplateLinkException {
		return remove((Serializable)templateLinkId);
	}

	/**
	 * Removes the d d m template link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the d d m template link
	 * @return the d d m template link that was removed
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateLinkException if a d d m template link with the primary key could not be found
	 */
	@Override
	public DDMTemplateLink remove(Serializable primaryKey)
		throws NoSuchTemplateLinkException {
		Session session = null;

		try {
			session = openSession();

			DDMTemplateLink ddmTemplateLink = (DDMTemplateLink)session.get(DDMTemplateLinkImpl.class,
					primaryKey);

			if (ddmTemplateLink == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTemplateLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(ddmTemplateLink);
		}
		catch (NoSuchTemplateLinkException nsee) {
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
	protected DDMTemplateLink removeImpl(DDMTemplateLink ddmTemplateLink) {
		ddmTemplateLink = toUnwrappedModel(ddmTemplateLink);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ddmTemplateLink)) {
				ddmTemplateLink = (DDMTemplateLink)session.get(DDMTemplateLinkImpl.class,
						ddmTemplateLink.getPrimaryKeyObj());
			}

			if (ddmTemplateLink != null) {
				session.delete(ddmTemplateLink);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (ddmTemplateLink != null) {
			clearCache(ddmTemplateLink);
		}

		return ddmTemplateLink;
	}

	@Override
	public DDMTemplateLink updateImpl(DDMTemplateLink ddmTemplateLink) {
		ddmTemplateLink = toUnwrappedModel(ddmTemplateLink);

		boolean isNew = ddmTemplateLink.isNew();

		DDMTemplateLinkModelImpl ddmTemplateLinkModelImpl = (DDMTemplateLinkModelImpl)ddmTemplateLink;

		Session session = null;

		try {
			session = openSession();

			if (ddmTemplateLink.isNew()) {
				session.save(ddmTemplateLink);

				ddmTemplateLink.setNew(false);
			}
			else {
				ddmTemplateLink = (DDMTemplateLink)session.merge(ddmTemplateLink);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !DDMTemplateLinkModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((ddmTemplateLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CLASSNAMEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmTemplateLinkModelImpl.getOriginalClassNameId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CLASSNAMEID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CLASSNAMEID,
					args);

				args = new Object[] { ddmTemplateLinkModelImpl.getClassNameId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CLASSNAMEID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CLASSNAMEID,
					args);
			}

			if ((ddmTemplateLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TEMPLATEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmTemplateLinkModelImpl.getOriginalTemplateId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_TEMPLATEID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TEMPLATEID,
					args);

				args = new Object[] { ddmTemplateLinkModelImpl.getTemplateId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_TEMPLATEID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TEMPLATEID,
					args);
			}
		}

		entityCache.putResult(DDMTemplateLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateLinkImpl.class, ddmTemplateLink.getPrimaryKey(),
			ddmTemplateLink, false);

		clearUniqueFindersCache(ddmTemplateLinkModelImpl);
		cacheUniqueFindersCache(ddmTemplateLinkModelImpl, isNew);

		ddmTemplateLink.resetOriginalValues();

		return ddmTemplateLink;
	}

	protected DDMTemplateLink toUnwrappedModel(DDMTemplateLink ddmTemplateLink) {
		if (ddmTemplateLink instanceof DDMTemplateLinkImpl) {
			return ddmTemplateLink;
		}

		DDMTemplateLinkImpl ddmTemplateLinkImpl = new DDMTemplateLinkImpl();

		ddmTemplateLinkImpl.setNew(ddmTemplateLink.isNew());
		ddmTemplateLinkImpl.setPrimaryKey(ddmTemplateLink.getPrimaryKey());

		ddmTemplateLinkImpl.setTemplateLinkId(ddmTemplateLink.getTemplateLinkId());
		ddmTemplateLinkImpl.setCompanyId(ddmTemplateLink.getCompanyId());
		ddmTemplateLinkImpl.setClassNameId(ddmTemplateLink.getClassNameId());
		ddmTemplateLinkImpl.setClassPK(ddmTemplateLink.getClassPK());
		ddmTemplateLinkImpl.setTemplateId(ddmTemplateLink.getTemplateId());

		return ddmTemplateLinkImpl;
	}

	/**
	 * Returns the d d m template link with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m template link
	 * @return the d d m template link
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateLinkException if a d d m template link with the primary key could not be found
	 */
	@Override
	public DDMTemplateLink findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTemplateLinkException {
		DDMTemplateLink ddmTemplateLink = fetchByPrimaryKey(primaryKey);

		if (ddmTemplateLink == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTemplateLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return ddmTemplateLink;
	}

	/**
	 * Returns the d d m template link with the primary key or throws a {@link com.liferay.dynamic.data.mapping.NoSuchTemplateLinkException} if it could not be found.
	 *
	 * @param templateLinkId the primary key of the d d m template link
	 * @return the d d m template link
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateLinkException if a d d m template link with the primary key could not be found
	 */
	@Override
	public DDMTemplateLink findByPrimaryKey(long templateLinkId)
		throws NoSuchTemplateLinkException {
		return findByPrimaryKey((Serializable)templateLinkId);
	}

	/**
	 * Returns the d d m template link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m template link
	 * @return the d d m template link, or <code>null</code> if a d d m template link with the primary key could not be found
	 */
	@Override
	public DDMTemplateLink fetchByPrimaryKey(Serializable primaryKey) {
		DDMTemplateLink ddmTemplateLink = (DDMTemplateLink)entityCache.getResult(DDMTemplateLinkModelImpl.ENTITY_CACHE_ENABLED,
				DDMTemplateLinkImpl.class, primaryKey);

		if (ddmTemplateLink == _nullDDMTemplateLink) {
			return null;
		}

		if (ddmTemplateLink == null) {
			Session session = null;

			try {
				session = openSession();

				ddmTemplateLink = (DDMTemplateLink)session.get(DDMTemplateLinkImpl.class,
						primaryKey);

				if (ddmTemplateLink != null) {
					cacheResult(ddmTemplateLink);
				}
				else {
					entityCache.putResult(DDMTemplateLinkModelImpl.ENTITY_CACHE_ENABLED,
						DDMTemplateLinkImpl.class, primaryKey,
						_nullDDMTemplateLink);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(DDMTemplateLinkModelImpl.ENTITY_CACHE_ENABLED,
					DDMTemplateLinkImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return ddmTemplateLink;
	}

	/**
	 * Returns the d d m template link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param templateLinkId the primary key of the d d m template link
	 * @return the d d m template link, or <code>null</code> if a d d m template link with the primary key could not be found
	 */
	@Override
	public DDMTemplateLink fetchByPrimaryKey(long templateLinkId) {
		return fetchByPrimaryKey((Serializable)templateLinkId);
	}

	@Override
	public Map<Serializable, DDMTemplateLink> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DDMTemplateLink> map = new HashMap<Serializable, DDMTemplateLink>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DDMTemplateLink ddmTemplateLink = fetchByPrimaryKey(primaryKey);

			if (ddmTemplateLink != null) {
				map.put(primaryKey, ddmTemplateLink);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			DDMTemplateLink ddmTemplateLink = (DDMTemplateLink)entityCache.getResult(DDMTemplateLinkModelImpl.ENTITY_CACHE_ENABLED,
					DDMTemplateLinkImpl.class, primaryKey);

			if (ddmTemplateLink == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, ddmTemplateLink);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_DDMTEMPLATELINK_WHERE_PKS_IN);

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

			for (DDMTemplateLink ddmTemplateLink : (List<DDMTemplateLink>)q.list()) {
				map.put(ddmTemplateLink.getPrimaryKeyObj(), ddmTemplateLink);

				cacheResult(ddmTemplateLink);

				uncachedPrimaryKeys.remove(ddmTemplateLink.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(DDMTemplateLinkModelImpl.ENTITY_CACHE_ENABLED,
					DDMTemplateLinkImpl.class, primaryKey, _nullDDMTemplateLink);
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
	 * Returns all the d d m template links.
	 *
	 * @return the d d m template links
	 */
	@Override
	public List<DDMTemplateLink> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m template links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m template links
	 * @param end the upper bound of the range of d d m template links (not inclusive)
	 * @return the range of d d m template links
	 */
	@Override
	public List<DDMTemplateLink> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m template links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m template links
	 * @param end the upper bound of the range of d d m template links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of d d m template links
	 */
	@Override
	public List<DDMTemplateLink> findAll(int start, int end,
		OrderByComparator<DDMTemplateLink> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m template links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m template links
	 * @param end the upper bound of the range of d d m template links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of d d m template links
	 */
	@Override
	public List<DDMTemplateLink> findAll(int start, int end,
		OrderByComparator<DDMTemplateLink> orderByComparator,
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

		List<DDMTemplateLink> list = null;

		if (retrieveFromCache) {
			list = (List<DDMTemplateLink>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DDMTEMPLATELINK);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DDMTEMPLATELINK;

				if (pagination) {
					sql = sql.concat(DDMTemplateLinkModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DDMTemplateLink>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMTemplateLink>)QueryUtil.list(q,
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
	 * Removes all the d d m template links from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DDMTemplateLink ddmTemplateLink : findAll()) {
			remove(ddmTemplateLink);
		}
	}

	/**
	 * Returns the number of d d m template links.
	 *
	 * @return the number of d d m template links
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DDMTEMPLATELINK);

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
		return DDMTemplateLinkModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the d d m template link persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(DDMTemplateLinkImpl.class.getName());
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
	private static final String _SQL_SELECT_DDMTEMPLATELINK = "SELECT ddmTemplateLink FROM DDMTemplateLink ddmTemplateLink";
	private static final String _SQL_SELECT_DDMTEMPLATELINK_WHERE_PKS_IN = "SELECT ddmTemplateLink FROM DDMTemplateLink ddmTemplateLink WHERE templateLinkId IN (";
	private static final String _SQL_SELECT_DDMTEMPLATELINK_WHERE = "SELECT ddmTemplateLink FROM DDMTemplateLink ddmTemplateLink WHERE ";
	private static final String _SQL_COUNT_DDMTEMPLATELINK = "SELECT COUNT(ddmTemplateLink) FROM DDMTemplateLink ddmTemplateLink";
	private static final String _SQL_COUNT_DDMTEMPLATELINK_WHERE = "SELECT COUNT(ddmTemplateLink) FROM DDMTemplateLink ddmTemplateLink WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ddmTemplateLink.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DDMTemplateLink exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DDMTemplateLink exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(DDMTemplateLinkPersistenceImpl.class);
	private static final DDMTemplateLink _nullDDMTemplateLink = new DDMTemplateLinkImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<DDMTemplateLink> toCacheModel() {
				return _nullDDMTemplateLinkCacheModel;
			}
		};

	private static final CacheModel<DDMTemplateLink> _nullDDMTemplateLinkCacheModel =
		new CacheModel<DDMTemplateLink>() {
			@Override
			public DDMTemplateLink toEntityModel() {
				return _nullDDMTemplateLink;
			}
		};
}