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

import com.liferay.dynamic.data.mapping.exception.NoSuchTemplateVersionException;
import com.liferay.dynamic.data.mapping.model.DDMTemplateVersion;
import com.liferay.dynamic.data.mapping.model.impl.DDMTemplateVersionImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMTemplateVersionModelImpl;
import com.liferay.dynamic.data.mapping.service.persistence.DDMTemplateVersionPersistence;

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
 * The persistence implementation for the d d m template version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateVersionPersistence
 * @see com.liferay.dynamic.data.mapping.service.persistence.DDMTemplateVersionUtil
 * @generated
 */
@ProviderType
public class DDMTemplateVersionPersistenceImpl extends BasePersistenceImpl<DDMTemplateVersion>
	implements DDMTemplateVersionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DDMTemplateVersionUtil} to access the d d m template version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DDMTemplateVersionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateVersionModelImpl.FINDER_CACHE_ENABLED,
			DDMTemplateVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateVersionModelImpl.FINDER_CACHE_ENABLED,
			DDMTemplateVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_TEMPLATEID =
		new FinderPath(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateVersionModelImpl.FINDER_CACHE_ENABLED,
			DDMTemplateVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByTemplateId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TEMPLATEID =
		new FinderPath(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateVersionModelImpl.FINDER_CACHE_ENABLED,
			DDMTemplateVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByTemplateId",
			new String[] { Long.class.getName() },
			DDMTemplateVersionModelImpl.TEMPLATEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_TEMPLATEID = new FinderPath(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTemplateId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the d d m template versions where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @return the matching d d m template versions
	 */
	@Override
	public List<DDMTemplateVersion> findByTemplateId(long templateId) {
		return findByTemplateId(templateId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m template versions where templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param start the lower bound of the range of d d m template versions
	 * @param end the upper bound of the range of d d m template versions (not inclusive)
	 * @return the range of matching d d m template versions
	 */
	@Override
	public List<DDMTemplateVersion> findByTemplateId(long templateId,
		int start, int end) {
		return findByTemplateId(templateId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m template versions where templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param start the lower bound of the range of d d m template versions
	 * @param end the upper bound of the range of d d m template versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m template versions
	 */
	@Override
	public List<DDMTemplateVersion> findByTemplateId(long templateId,
		int start, int end,
		OrderByComparator<DDMTemplateVersion> orderByComparator) {
		return findByTemplateId(templateId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m template versions where templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param start the lower bound of the range of d d m template versions
	 * @param end the upper bound of the range of d d m template versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m template versions
	 */
	@Override
	public List<DDMTemplateVersion> findByTemplateId(long templateId,
		int start, int end,
		OrderByComparator<DDMTemplateVersion> orderByComparator,
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

		List<DDMTemplateVersion> list = null;

		if (retrieveFromCache) {
			list = (List<DDMTemplateVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMTemplateVersion ddmTemplateVersion : list) {
					if ((templateId != ddmTemplateVersion.getTemplateId())) {
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

			query.append(_SQL_SELECT_DDMTEMPLATEVERSION_WHERE);

			query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMTemplateVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(templateId);

				if (!pagination) {
					list = (List<DDMTemplateVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMTemplateVersion>)QueryUtil.list(q,
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
	 * Returns the first d d m template version in the ordered set where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template version
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateVersionException if a matching d d m template version could not be found
	 */
	@Override
	public DDMTemplateVersion findByTemplateId_First(long templateId,
		OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws NoSuchTemplateVersionException {
		DDMTemplateVersion ddmTemplateVersion = fetchByTemplateId_First(templateId,
				orderByComparator);

		if (ddmTemplateVersion != null) {
			return ddmTemplateVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("templateId=");
		msg.append(templateId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateVersionException(msg.toString());
	}

	/**
	 * Returns the first d d m template version in the ordered set where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template version, or <code>null</code> if a matching d d m template version could not be found
	 */
	@Override
	public DDMTemplateVersion fetchByTemplateId_First(long templateId,
		OrderByComparator<DDMTemplateVersion> orderByComparator) {
		List<DDMTemplateVersion> list = findByTemplateId(templateId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m template version in the ordered set where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template version
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateVersionException if a matching d d m template version could not be found
	 */
	@Override
	public DDMTemplateVersion findByTemplateId_Last(long templateId,
		OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws NoSuchTemplateVersionException {
		DDMTemplateVersion ddmTemplateVersion = fetchByTemplateId_Last(templateId,
				orderByComparator);

		if (ddmTemplateVersion != null) {
			return ddmTemplateVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("templateId=");
		msg.append(templateId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateVersionException(msg.toString());
	}

	/**
	 * Returns the last d d m template version in the ordered set where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template version, or <code>null</code> if a matching d d m template version could not be found
	 */
	@Override
	public DDMTemplateVersion fetchByTemplateId_Last(long templateId,
		OrderByComparator<DDMTemplateVersion> orderByComparator) {
		int count = countByTemplateId(templateId);

		if (count == 0) {
			return null;
		}

		List<DDMTemplateVersion> list = findByTemplateId(templateId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m template versions before and after the current d d m template version in the ordered set where templateId = &#63;.
	 *
	 * @param templateVersionId the primary key of the current d d m template version
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template version
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateVersionException if a d d m template version with the primary key could not be found
	 */
	@Override
	public DDMTemplateVersion[] findByTemplateId_PrevAndNext(
		long templateVersionId, long templateId,
		OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws NoSuchTemplateVersionException {
		DDMTemplateVersion ddmTemplateVersion = findByPrimaryKey(templateVersionId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplateVersion[] array = new DDMTemplateVersionImpl[3];

			array[0] = getByTemplateId_PrevAndNext(session, ddmTemplateVersion,
					templateId, orderByComparator, true);

			array[1] = ddmTemplateVersion;

			array[2] = getByTemplateId_PrevAndNext(session, ddmTemplateVersion,
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

	protected DDMTemplateVersion getByTemplateId_PrevAndNext(Session session,
		DDMTemplateVersion ddmTemplateVersion, long templateId,
		OrderByComparator<DDMTemplateVersion> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMTEMPLATEVERSION_WHERE);

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
			query.append(DDMTemplateVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(templateId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplateVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplateVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m template versions where templateId = &#63; from the database.
	 *
	 * @param templateId the template ID
	 */
	@Override
	public void removeByTemplateId(long templateId) {
		for (DDMTemplateVersion ddmTemplateVersion : findByTemplateId(
				templateId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmTemplateVersion);
		}
	}

	/**
	 * Returns the number of d d m template versions where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @return the number of matching d d m template versions
	 */
	@Override
	public int countByTemplateId(long templateId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_TEMPLATEID;

		Object[] finderArgs = new Object[] { templateId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMTEMPLATEVERSION_WHERE);

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

	private static final String _FINDER_COLUMN_TEMPLATEID_TEMPLATEID_2 = "ddmTemplateVersion.templateId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_T_V = new FinderPath(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateVersionModelImpl.FINDER_CACHE_ENABLED,
			DDMTemplateVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByT_V",
			new String[] { Long.class.getName(), String.class.getName() },
			DDMTemplateVersionModelImpl.TEMPLATEID_COLUMN_BITMASK |
			DDMTemplateVersionModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_T_V = new FinderPath(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_V",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the d d m template version where templateId = &#63; and version = &#63; or throws a {@link com.liferay.dynamic.data.mapping.NoSuchTemplateVersionException} if it could not be found.
	 *
	 * @param templateId the template ID
	 * @param version the version
	 * @return the matching d d m template version
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateVersionException if a matching d d m template version could not be found
	 */
	@Override
	public DDMTemplateVersion findByT_V(long templateId, String version)
		throws NoSuchTemplateVersionException {
		DDMTemplateVersion ddmTemplateVersion = fetchByT_V(templateId, version);

		if (ddmTemplateVersion == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("templateId=");
			msg.append(templateId);

			msg.append(", version=");
			msg.append(version);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchTemplateVersionException(msg.toString());
		}

		return ddmTemplateVersion;
	}

	/**
	 * Returns the d d m template version where templateId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param templateId the template ID
	 * @param version the version
	 * @return the matching d d m template version, or <code>null</code> if a matching d d m template version could not be found
	 */
	@Override
	public DDMTemplateVersion fetchByT_V(long templateId, String version) {
		return fetchByT_V(templateId, version, true);
	}

	/**
	 * Returns the d d m template version where templateId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param templateId the template ID
	 * @param version the version
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching d d m template version, or <code>null</code> if a matching d d m template version could not be found
	 */
	@Override
	public DDMTemplateVersion fetchByT_V(long templateId, String version,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { templateId, version };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_T_V,
					finderArgs, this);
		}

		if (result instanceof DDMTemplateVersion) {
			DDMTemplateVersion ddmTemplateVersion = (DDMTemplateVersion)result;

			if ((templateId != ddmTemplateVersion.getTemplateId()) ||
					!Validator.equals(version, ddmTemplateVersion.getVersion())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DDMTEMPLATEVERSION_WHERE);

			query.append(_FINDER_COLUMN_T_V_TEMPLATEID_2);

			boolean bindVersion = false;

			if (version == null) {
				query.append(_FINDER_COLUMN_T_V_VERSION_1);
			}
			else if (version.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_T_V_VERSION_3);
			}
			else {
				bindVersion = true;

				query.append(_FINDER_COLUMN_T_V_VERSION_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(templateId);

				if (bindVersion) {
					qPos.add(version);
				}

				List<DDMTemplateVersion> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_T_V, finderArgs,
						list);
				}
				else {
					DDMTemplateVersion ddmTemplateVersion = list.get(0);

					result = ddmTemplateVersion;

					cacheResult(ddmTemplateVersion);

					if ((ddmTemplateVersion.getTemplateId() != templateId) ||
							(ddmTemplateVersion.getVersion() == null) ||
							!ddmTemplateVersion.getVersion().equals(version)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_T_V,
							finderArgs, ddmTemplateVersion);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_T_V, finderArgs);

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
			return (DDMTemplateVersion)result;
		}
	}

	/**
	 * Removes the d d m template version where templateId = &#63; and version = &#63; from the database.
	 *
	 * @param templateId the template ID
	 * @param version the version
	 * @return the d d m template version that was removed
	 */
	@Override
	public DDMTemplateVersion removeByT_V(long templateId, String version)
		throws NoSuchTemplateVersionException {
		DDMTemplateVersion ddmTemplateVersion = findByT_V(templateId, version);

		return remove(ddmTemplateVersion);
	}

	/**
	 * Returns the number of d d m template versions where templateId = &#63; and version = &#63;.
	 *
	 * @param templateId the template ID
	 * @param version the version
	 * @return the number of matching d d m template versions
	 */
	@Override
	public int countByT_V(long templateId, String version) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_T_V;

		Object[] finderArgs = new Object[] { templateId, version };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMTEMPLATEVERSION_WHERE);

			query.append(_FINDER_COLUMN_T_V_TEMPLATEID_2);

			boolean bindVersion = false;

			if (version == null) {
				query.append(_FINDER_COLUMN_T_V_VERSION_1);
			}
			else if (version.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_T_V_VERSION_3);
			}
			else {
				bindVersion = true;

				query.append(_FINDER_COLUMN_T_V_VERSION_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(templateId);

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

	private static final String _FINDER_COLUMN_T_V_TEMPLATEID_2 = "ddmTemplateVersion.templateId = ? AND ";
	private static final String _FINDER_COLUMN_T_V_VERSION_1 = "ddmTemplateVersion.version IS NULL";
	private static final String _FINDER_COLUMN_T_V_VERSION_2 = "ddmTemplateVersion.version = ?";
	private static final String _FINDER_COLUMN_T_V_VERSION_3 = "(ddmTemplateVersion.version IS NULL OR ddmTemplateVersion.version = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_T_S = new FinderPath(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateVersionModelImpl.FINDER_CACHE_ENABLED,
			DDMTemplateVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByT_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_S = new FinderPath(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateVersionModelImpl.FINDER_CACHE_ENABLED,
			DDMTemplateVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByT_S",
			new String[] { Long.class.getName(), Integer.class.getName() },
			DDMTemplateVersionModelImpl.TEMPLATEID_COLUMN_BITMASK |
			DDMTemplateVersionModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_T_S = new FinderPath(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_S",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the d d m template versions where templateId = &#63; and status = &#63;.
	 *
	 * @param templateId the template ID
	 * @param status the status
	 * @return the matching d d m template versions
	 */
	@Override
	public List<DDMTemplateVersion> findByT_S(long templateId, int status) {
		return findByT_S(templateId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m template versions where templateId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param status the status
	 * @param start the lower bound of the range of d d m template versions
	 * @param end the upper bound of the range of d d m template versions (not inclusive)
	 * @return the range of matching d d m template versions
	 */
	@Override
	public List<DDMTemplateVersion> findByT_S(long templateId, int status,
		int start, int end) {
		return findByT_S(templateId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m template versions where templateId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param status the status
	 * @param start the lower bound of the range of d d m template versions
	 * @param end the upper bound of the range of d d m template versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m template versions
	 */
	@Override
	public List<DDMTemplateVersion> findByT_S(long templateId, int status,
		int start, int end,
		OrderByComparator<DDMTemplateVersion> orderByComparator) {
		return findByT_S(templateId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m template versions where templateId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param status the status
	 * @param start the lower bound of the range of d d m template versions
	 * @param end the upper bound of the range of d d m template versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m template versions
	 */
	@Override
	public List<DDMTemplateVersion> findByT_S(long templateId, int status,
		int start, int end,
		OrderByComparator<DDMTemplateVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_S;
			finderArgs = new Object[] { templateId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_T_S;
			finderArgs = new Object[] {
					templateId, status,
					
					start, end, orderByComparator
				};
		}

		List<DDMTemplateVersion> list = null;

		if (retrieveFromCache) {
			list = (List<DDMTemplateVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMTemplateVersion ddmTemplateVersion : list) {
					if ((templateId != ddmTemplateVersion.getTemplateId()) ||
							(status != ddmTemplateVersion.getStatus())) {
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

			query.append(_SQL_SELECT_DDMTEMPLATEVERSION_WHERE);

			query.append(_FINDER_COLUMN_T_S_TEMPLATEID_2);

			query.append(_FINDER_COLUMN_T_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMTemplateVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(templateId);

				qPos.add(status);

				if (!pagination) {
					list = (List<DDMTemplateVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMTemplateVersion>)QueryUtil.list(q,
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
	 * Returns the first d d m template version in the ordered set where templateId = &#63; and status = &#63;.
	 *
	 * @param templateId the template ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template version
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateVersionException if a matching d d m template version could not be found
	 */
	@Override
	public DDMTemplateVersion findByT_S_First(long templateId, int status,
		OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws NoSuchTemplateVersionException {
		DDMTemplateVersion ddmTemplateVersion = fetchByT_S_First(templateId,
				status, orderByComparator);

		if (ddmTemplateVersion != null) {
			return ddmTemplateVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("templateId=");
		msg.append(templateId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateVersionException(msg.toString());
	}

	/**
	 * Returns the first d d m template version in the ordered set where templateId = &#63; and status = &#63;.
	 *
	 * @param templateId the template ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m template version, or <code>null</code> if a matching d d m template version could not be found
	 */
	@Override
	public DDMTemplateVersion fetchByT_S_First(long templateId, int status,
		OrderByComparator<DDMTemplateVersion> orderByComparator) {
		List<DDMTemplateVersion> list = findByT_S(templateId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m template version in the ordered set where templateId = &#63; and status = &#63;.
	 *
	 * @param templateId the template ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template version
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateVersionException if a matching d d m template version could not be found
	 */
	@Override
	public DDMTemplateVersion findByT_S_Last(long templateId, int status,
		OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws NoSuchTemplateVersionException {
		DDMTemplateVersion ddmTemplateVersion = fetchByT_S_Last(templateId,
				status, orderByComparator);

		if (ddmTemplateVersion != null) {
			return ddmTemplateVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("templateId=");
		msg.append(templateId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTemplateVersionException(msg.toString());
	}

	/**
	 * Returns the last d d m template version in the ordered set where templateId = &#63; and status = &#63;.
	 *
	 * @param templateId the template ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m template version, or <code>null</code> if a matching d d m template version could not be found
	 */
	@Override
	public DDMTemplateVersion fetchByT_S_Last(long templateId, int status,
		OrderByComparator<DDMTemplateVersion> orderByComparator) {
		int count = countByT_S(templateId, status);

		if (count == 0) {
			return null;
		}

		List<DDMTemplateVersion> list = findByT_S(templateId, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m template versions before and after the current d d m template version in the ordered set where templateId = &#63; and status = &#63;.
	 *
	 * @param templateVersionId the primary key of the current d d m template version
	 * @param templateId the template ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m template version
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateVersionException if a d d m template version with the primary key could not be found
	 */
	@Override
	public DDMTemplateVersion[] findByT_S_PrevAndNext(long templateVersionId,
		long templateId, int status,
		OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws NoSuchTemplateVersionException {
		DDMTemplateVersion ddmTemplateVersion = findByPrimaryKey(templateVersionId);

		Session session = null;

		try {
			session = openSession();

			DDMTemplateVersion[] array = new DDMTemplateVersionImpl[3];

			array[0] = getByT_S_PrevAndNext(session, ddmTemplateVersion,
					templateId, status, orderByComparator, true);

			array[1] = ddmTemplateVersion;

			array[2] = getByT_S_PrevAndNext(session, ddmTemplateVersion,
					templateId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMTemplateVersion getByT_S_PrevAndNext(Session session,
		DDMTemplateVersion ddmTemplateVersion, long templateId, int status,
		OrderByComparator<DDMTemplateVersion> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMTEMPLATEVERSION_WHERE);

		query.append(_FINDER_COLUMN_T_S_TEMPLATEID_2);

		query.append(_FINDER_COLUMN_T_S_STATUS_2);

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
			query.append(DDMTemplateVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(templateId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmTemplateVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMTemplateVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m template versions where templateId = &#63; and status = &#63; from the database.
	 *
	 * @param templateId the template ID
	 * @param status the status
	 */
	@Override
	public void removeByT_S(long templateId, int status) {
		for (DDMTemplateVersion ddmTemplateVersion : findByT_S(templateId,
				status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmTemplateVersion);
		}
	}

	/**
	 * Returns the number of d d m template versions where templateId = &#63; and status = &#63;.
	 *
	 * @param templateId the template ID
	 * @param status the status
	 * @return the number of matching d d m template versions
	 */
	@Override
	public int countByT_S(long templateId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_T_S;

		Object[] finderArgs = new Object[] { templateId, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMTEMPLATEVERSION_WHERE);

			query.append(_FINDER_COLUMN_T_S_TEMPLATEID_2);

			query.append(_FINDER_COLUMN_T_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(templateId);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_T_S_TEMPLATEID_2 = "ddmTemplateVersion.templateId = ? AND ";
	private static final String _FINDER_COLUMN_T_S_STATUS_2 = "ddmTemplateVersion.status = ?";

	public DDMTemplateVersionPersistenceImpl() {
		setModelClass(DDMTemplateVersion.class);
	}

	/**
	 * Caches the d d m template version in the entity cache if it is enabled.
	 *
	 * @param ddmTemplateVersion the d d m template version
	 */
	@Override
	public void cacheResult(DDMTemplateVersion ddmTemplateVersion) {
		entityCache.putResult(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateVersionImpl.class, ddmTemplateVersion.getPrimaryKey(),
			ddmTemplateVersion);

		finderCache.putResult(FINDER_PATH_FETCH_BY_T_V,
			new Object[] {
				ddmTemplateVersion.getTemplateId(),
				ddmTemplateVersion.getVersion()
			}, ddmTemplateVersion);

		ddmTemplateVersion.resetOriginalValues();
	}

	/**
	 * Caches the d d m template versions in the entity cache if it is enabled.
	 *
	 * @param ddmTemplateVersions the d d m template versions
	 */
	@Override
	public void cacheResult(List<DDMTemplateVersion> ddmTemplateVersions) {
		for (DDMTemplateVersion ddmTemplateVersion : ddmTemplateVersions) {
			if (entityCache.getResult(
						DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
						DDMTemplateVersionImpl.class,
						ddmTemplateVersion.getPrimaryKey()) == null) {
				cacheResult(ddmTemplateVersion);
			}
			else {
				ddmTemplateVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all d d m template versions.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DDMTemplateVersionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the d d m template version.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DDMTemplateVersion ddmTemplateVersion) {
		entityCache.removeResult(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateVersionImpl.class, ddmTemplateVersion.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((DDMTemplateVersionModelImpl)ddmTemplateVersion);
	}

	@Override
	public void clearCache(List<DDMTemplateVersion> ddmTemplateVersions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DDMTemplateVersion ddmTemplateVersion : ddmTemplateVersions) {
			entityCache.removeResult(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
				DDMTemplateVersionImpl.class, ddmTemplateVersion.getPrimaryKey());

			clearUniqueFindersCache((DDMTemplateVersionModelImpl)ddmTemplateVersion);
		}
	}

	protected void cacheUniqueFindersCache(
		DDMTemplateVersionModelImpl ddmTemplateVersionModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					ddmTemplateVersionModelImpl.getTemplateId(),
					ddmTemplateVersionModelImpl.getVersion()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_T_V, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_T_V, args,
				ddmTemplateVersionModelImpl);
		}
		else {
			if ((ddmTemplateVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_T_V.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmTemplateVersionModelImpl.getTemplateId(),
						ddmTemplateVersionModelImpl.getVersion()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_T_V, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_T_V, args,
					ddmTemplateVersionModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		DDMTemplateVersionModelImpl ddmTemplateVersionModelImpl) {
		Object[] args = new Object[] {
				ddmTemplateVersionModelImpl.getTemplateId(),
				ddmTemplateVersionModelImpl.getVersion()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_T_V, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_T_V, args);

		if ((ddmTemplateVersionModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_T_V.getColumnBitmask()) != 0) {
			args = new Object[] {
					ddmTemplateVersionModelImpl.getOriginalTemplateId(),
					ddmTemplateVersionModelImpl.getOriginalVersion()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_T_V, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_T_V, args);
		}
	}

	/**
	 * Creates a new d d m template version with the primary key. Does not add the d d m template version to the database.
	 *
	 * @param templateVersionId the primary key for the new d d m template version
	 * @return the new d d m template version
	 */
	@Override
	public DDMTemplateVersion create(long templateVersionId) {
		DDMTemplateVersion ddmTemplateVersion = new DDMTemplateVersionImpl();

		ddmTemplateVersion.setNew(true);
		ddmTemplateVersion.setPrimaryKey(templateVersionId);

		ddmTemplateVersion.setCompanyId(companyProvider.getCompanyId());

		return ddmTemplateVersion;
	}

	/**
	 * Removes the d d m template version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param templateVersionId the primary key of the d d m template version
	 * @return the d d m template version that was removed
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateVersionException if a d d m template version with the primary key could not be found
	 */
	@Override
	public DDMTemplateVersion remove(long templateVersionId)
		throws NoSuchTemplateVersionException {
		return remove((Serializable)templateVersionId);
	}

	/**
	 * Removes the d d m template version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the d d m template version
	 * @return the d d m template version that was removed
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateVersionException if a d d m template version with the primary key could not be found
	 */
	@Override
	public DDMTemplateVersion remove(Serializable primaryKey)
		throws NoSuchTemplateVersionException {
		Session session = null;

		try {
			session = openSession();

			DDMTemplateVersion ddmTemplateVersion = (DDMTemplateVersion)session.get(DDMTemplateVersionImpl.class,
					primaryKey);

			if (ddmTemplateVersion == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTemplateVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(ddmTemplateVersion);
		}
		catch (NoSuchTemplateVersionException nsee) {
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
	protected DDMTemplateVersion removeImpl(
		DDMTemplateVersion ddmTemplateVersion) {
		ddmTemplateVersion = toUnwrappedModel(ddmTemplateVersion);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ddmTemplateVersion)) {
				ddmTemplateVersion = (DDMTemplateVersion)session.get(DDMTemplateVersionImpl.class,
						ddmTemplateVersion.getPrimaryKeyObj());
			}

			if (ddmTemplateVersion != null) {
				session.delete(ddmTemplateVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (ddmTemplateVersion != null) {
			clearCache(ddmTemplateVersion);
		}

		return ddmTemplateVersion;
	}

	@Override
	public DDMTemplateVersion updateImpl(DDMTemplateVersion ddmTemplateVersion) {
		ddmTemplateVersion = toUnwrappedModel(ddmTemplateVersion);

		boolean isNew = ddmTemplateVersion.isNew();

		DDMTemplateVersionModelImpl ddmTemplateVersionModelImpl = (DDMTemplateVersionModelImpl)ddmTemplateVersion;

		Session session = null;

		try {
			session = openSession();

			if (ddmTemplateVersion.isNew()) {
				session.save(ddmTemplateVersion);

				ddmTemplateVersion.setNew(false);
			}
			else {
				ddmTemplateVersion = (DDMTemplateVersion)session.merge(ddmTemplateVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !DDMTemplateVersionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((ddmTemplateVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TEMPLATEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmTemplateVersionModelImpl.getOriginalTemplateId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_TEMPLATEID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TEMPLATEID,
					args);

				args = new Object[] { ddmTemplateVersionModelImpl.getTemplateId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_TEMPLATEID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TEMPLATEID,
					args);
			}

			if ((ddmTemplateVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmTemplateVersionModelImpl.getOriginalTemplateId(),
						ddmTemplateVersionModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_T_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_S,
					args);

				args = new Object[] {
						ddmTemplateVersionModelImpl.getTemplateId(),
						ddmTemplateVersionModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_T_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_S,
					args);
			}
		}

		entityCache.putResult(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMTemplateVersionImpl.class, ddmTemplateVersion.getPrimaryKey(),
			ddmTemplateVersion, false);

		clearUniqueFindersCache(ddmTemplateVersionModelImpl);
		cacheUniqueFindersCache(ddmTemplateVersionModelImpl, isNew);

		ddmTemplateVersion.resetOriginalValues();

		return ddmTemplateVersion;
	}

	protected DDMTemplateVersion toUnwrappedModel(
		DDMTemplateVersion ddmTemplateVersion) {
		if (ddmTemplateVersion instanceof DDMTemplateVersionImpl) {
			return ddmTemplateVersion;
		}

		DDMTemplateVersionImpl ddmTemplateVersionImpl = new DDMTemplateVersionImpl();

		ddmTemplateVersionImpl.setNew(ddmTemplateVersion.isNew());
		ddmTemplateVersionImpl.setPrimaryKey(ddmTemplateVersion.getPrimaryKey());

		ddmTemplateVersionImpl.setTemplateVersionId(ddmTemplateVersion.getTemplateVersionId());
		ddmTemplateVersionImpl.setGroupId(ddmTemplateVersion.getGroupId());
		ddmTemplateVersionImpl.setCompanyId(ddmTemplateVersion.getCompanyId());
		ddmTemplateVersionImpl.setUserId(ddmTemplateVersion.getUserId());
		ddmTemplateVersionImpl.setUserName(ddmTemplateVersion.getUserName());
		ddmTemplateVersionImpl.setCreateDate(ddmTemplateVersion.getCreateDate());
		ddmTemplateVersionImpl.setClassNameId(ddmTemplateVersion.getClassNameId());
		ddmTemplateVersionImpl.setClassPK(ddmTemplateVersion.getClassPK());
		ddmTemplateVersionImpl.setTemplateId(ddmTemplateVersion.getTemplateId());
		ddmTemplateVersionImpl.setVersion(ddmTemplateVersion.getVersion());
		ddmTemplateVersionImpl.setName(ddmTemplateVersion.getName());
		ddmTemplateVersionImpl.setDescription(ddmTemplateVersion.getDescription());
		ddmTemplateVersionImpl.setLanguage(ddmTemplateVersion.getLanguage());
		ddmTemplateVersionImpl.setScript(ddmTemplateVersion.getScript());
		ddmTemplateVersionImpl.setStatus(ddmTemplateVersion.getStatus());
		ddmTemplateVersionImpl.setStatusByUserId(ddmTemplateVersion.getStatusByUserId());
		ddmTemplateVersionImpl.setStatusByUserName(ddmTemplateVersion.getStatusByUserName());
		ddmTemplateVersionImpl.setStatusDate(ddmTemplateVersion.getStatusDate());

		return ddmTemplateVersionImpl;
	}

	/**
	 * Returns the d d m template version with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m template version
	 * @return the d d m template version
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateVersionException if a d d m template version with the primary key could not be found
	 */
	@Override
	public DDMTemplateVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTemplateVersionException {
		DDMTemplateVersion ddmTemplateVersion = fetchByPrimaryKey(primaryKey);

		if (ddmTemplateVersion == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTemplateVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return ddmTemplateVersion;
	}

	/**
	 * Returns the d d m template version with the primary key or throws a {@link com.liferay.dynamic.data.mapping.NoSuchTemplateVersionException} if it could not be found.
	 *
	 * @param templateVersionId the primary key of the d d m template version
	 * @return the d d m template version
	 * @throws com.liferay.dynamic.data.mapping.NoSuchTemplateVersionException if a d d m template version with the primary key could not be found
	 */
	@Override
	public DDMTemplateVersion findByPrimaryKey(long templateVersionId)
		throws NoSuchTemplateVersionException {
		return findByPrimaryKey((Serializable)templateVersionId);
	}

	/**
	 * Returns the d d m template version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m template version
	 * @return the d d m template version, or <code>null</code> if a d d m template version with the primary key could not be found
	 */
	@Override
	public DDMTemplateVersion fetchByPrimaryKey(Serializable primaryKey) {
		DDMTemplateVersion ddmTemplateVersion = (DDMTemplateVersion)entityCache.getResult(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
				DDMTemplateVersionImpl.class, primaryKey);

		if (ddmTemplateVersion == _nullDDMTemplateVersion) {
			return null;
		}

		if (ddmTemplateVersion == null) {
			Session session = null;

			try {
				session = openSession();

				ddmTemplateVersion = (DDMTemplateVersion)session.get(DDMTemplateVersionImpl.class,
						primaryKey);

				if (ddmTemplateVersion != null) {
					cacheResult(ddmTemplateVersion);
				}
				else {
					entityCache.putResult(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
						DDMTemplateVersionImpl.class, primaryKey,
						_nullDDMTemplateVersion);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
					DDMTemplateVersionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return ddmTemplateVersion;
	}

	/**
	 * Returns the d d m template version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param templateVersionId the primary key of the d d m template version
	 * @return the d d m template version, or <code>null</code> if a d d m template version with the primary key could not be found
	 */
	@Override
	public DDMTemplateVersion fetchByPrimaryKey(long templateVersionId) {
		return fetchByPrimaryKey((Serializable)templateVersionId);
	}

	@Override
	public Map<Serializable, DDMTemplateVersion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DDMTemplateVersion> map = new HashMap<Serializable, DDMTemplateVersion>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DDMTemplateVersion ddmTemplateVersion = fetchByPrimaryKey(primaryKey);

			if (ddmTemplateVersion != null) {
				map.put(primaryKey, ddmTemplateVersion);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			DDMTemplateVersion ddmTemplateVersion = (DDMTemplateVersion)entityCache.getResult(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
					DDMTemplateVersionImpl.class, primaryKey);

			if (ddmTemplateVersion == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, ddmTemplateVersion);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_DDMTEMPLATEVERSION_WHERE_PKS_IN);

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

			for (DDMTemplateVersion ddmTemplateVersion : (List<DDMTemplateVersion>)q.list()) {
				map.put(ddmTemplateVersion.getPrimaryKeyObj(),
					ddmTemplateVersion);

				cacheResult(ddmTemplateVersion);

				uncachedPrimaryKeys.remove(ddmTemplateVersion.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(DDMTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
					DDMTemplateVersionImpl.class, primaryKey,
					_nullDDMTemplateVersion);
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
	 * Returns all the d d m template versions.
	 *
	 * @return the d d m template versions
	 */
	@Override
	public List<DDMTemplateVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m template versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m template versions
	 * @param end the upper bound of the range of d d m template versions (not inclusive)
	 * @return the range of d d m template versions
	 */
	@Override
	public List<DDMTemplateVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m template versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m template versions
	 * @param end the upper bound of the range of d d m template versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of d d m template versions
	 */
	@Override
	public List<DDMTemplateVersion> findAll(int start, int end,
		OrderByComparator<DDMTemplateVersion> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m template versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m template versions
	 * @param end the upper bound of the range of d d m template versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of d d m template versions
	 */
	@Override
	public List<DDMTemplateVersion> findAll(int start, int end,
		OrderByComparator<DDMTemplateVersion> orderByComparator,
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

		List<DDMTemplateVersion> list = null;

		if (retrieveFromCache) {
			list = (List<DDMTemplateVersion>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DDMTEMPLATEVERSION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DDMTEMPLATEVERSION;

				if (pagination) {
					sql = sql.concat(DDMTemplateVersionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DDMTemplateVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMTemplateVersion>)QueryUtil.list(q,
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
	 * Removes all the d d m template versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DDMTemplateVersion ddmTemplateVersion : findAll()) {
			remove(ddmTemplateVersion);
		}
	}

	/**
	 * Returns the number of d d m template versions.
	 *
	 * @return the number of d d m template versions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DDMTEMPLATEVERSION);

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
		return DDMTemplateVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the d d m template version persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(DDMTemplateVersionImpl.class.getName());
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
	private static final String _SQL_SELECT_DDMTEMPLATEVERSION = "SELECT ddmTemplateVersion FROM DDMTemplateVersion ddmTemplateVersion";
	private static final String _SQL_SELECT_DDMTEMPLATEVERSION_WHERE_PKS_IN = "SELECT ddmTemplateVersion FROM DDMTemplateVersion ddmTemplateVersion WHERE templateVersionId IN (";
	private static final String _SQL_SELECT_DDMTEMPLATEVERSION_WHERE = "SELECT ddmTemplateVersion FROM DDMTemplateVersion ddmTemplateVersion WHERE ";
	private static final String _SQL_COUNT_DDMTEMPLATEVERSION = "SELECT COUNT(ddmTemplateVersion) FROM DDMTemplateVersion ddmTemplateVersion";
	private static final String _SQL_COUNT_DDMTEMPLATEVERSION_WHERE = "SELECT COUNT(ddmTemplateVersion) FROM DDMTemplateVersion ddmTemplateVersion WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ddmTemplateVersion.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DDMTemplateVersion exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DDMTemplateVersion exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(DDMTemplateVersionPersistenceImpl.class);
	private static final DDMTemplateVersion _nullDDMTemplateVersion = new DDMTemplateVersionImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<DDMTemplateVersion> toCacheModel() {
				return _nullDDMTemplateVersionCacheModel;
			}
		};

	private static final CacheModel<DDMTemplateVersion> _nullDDMTemplateVersionCacheModel =
		new CacheModel<DDMTemplateVersion>() {
			@Override
			public DDMTemplateVersion toEntityModel() {
				return _nullDDMTemplateVersion;
			}
		};
}