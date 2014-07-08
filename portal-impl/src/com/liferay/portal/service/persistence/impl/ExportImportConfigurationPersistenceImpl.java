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

package com.liferay.portal.service.persistence.impl;

import com.liferay.portal.NoSuchExportImportConfigurationException;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.ExportImportConfigurationImpl;
import com.liferay.portal.model.impl.ExportImportConfigurationModelImpl;
import com.liferay.portal.service.persistence.ExportImportConfigurationPersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the export import configuration service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ExportImportConfigurationPersistence
 * @see ExportImportConfigurationUtil
 * @generated
 */
public class ExportImportConfigurationPersistenceImpl
	extends BasePersistenceImpl<ExportImportConfiguration>
	implements ExportImportConfigurationPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ExportImportConfigurationUtil} to access the export import configuration persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ExportImportConfigurationImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ExportImportConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ExportImportConfigurationModelImpl.FINDER_CACHE_ENABLED,
			ExportImportConfigurationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ExportImportConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ExportImportConfigurationModelImpl.FINDER_CACHE_ENABLED,
			ExportImportConfigurationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ExportImportConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ExportImportConfigurationModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(ExportImportConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ExportImportConfigurationModelImpl.FINDER_CACHE_ENABLED,
			ExportImportConfigurationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(ExportImportConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ExportImportConfigurationModelImpl.FINDER_CACHE_ENABLED,
			ExportImportConfigurationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			ExportImportConfigurationModelImpl.GROUPID_COLUMN_BITMASK |
			ExportImportConfigurationModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(ExportImportConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ExportImportConfigurationModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] { Long.class.getName() });

	/**
	 * Returns all the export import configurations where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the export import configurations where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ExportImportConfigurationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @return the range of matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByGroupId(long groupId,
		int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the export import configurations where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ExportImportConfigurationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByGroupId(long groupId,
		int start, int end, OrderByComparator orderByComparator) {
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

		List<ExportImportConfiguration> list = (List<ExportImportConfiguration>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ExportImportConfiguration exportImportConfiguration : list) {
				if ((groupId != exportImportConfiguration.getGroupId())) {
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

			query.append(_SQL_SELECT_EXPORTIMPORTCONFIGURATION_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ExportImportConfigurationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<ExportImportConfiguration>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ExportImportConfiguration>)QueryUtil.list(q,
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
	 * Returns the first export import configuration in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching export import configuration
	 * @throws com.liferay.portal.NoSuchExportImportConfigurationException if a matching export import configuration could not be found
	 */
	@Override
	public ExportImportConfiguration findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchExportImportConfigurationException {
		ExportImportConfiguration exportImportConfiguration = fetchByGroupId_First(groupId,
				orderByComparator);

		if (exportImportConfiguration != null) {
			return exportImportConfiguration;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchExportImportConfigurationException(msg.toString());
	}

	/**
	 * Returns the first export import configuration in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching export import configuration, or <code>null</code> if a matching export import configuration could not be found
	 */
	@Override
	public ExportImportConfiguration fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) {
		List<ExportImportConfiguration> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last export import configuration in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching export import configuration
	 * @throws com.liferay.portal.NoSuchExportImportConfigurationException if a matching export import configuration could not be found
	 */
	@Override
	public ExportImportConfiguration findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchExportImportConfigurationException {
		ExportImportConfiguration exportImportConfiguration = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (exportImportConfiguration != null) {
			return exportImportConfiguration;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchExportImportConfigurationException(msg.toString());
	}

	/**
	 * Returns the last export import configuration in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching export import configuration, or <code>null</code> if a matching export import configuration could not be found
	 */
	@Override
	public ExportImportConfiguration fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<ExportImportConfiguration> list = findByGroupId(groupId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the export import configurations before and after the current export import configuration in the ordered set where groupId = &#63;.
	 *
	 * @param exportImportConfigurationId the primary key of the current export import configuration
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next export import configuration
	 * @throws com.liferay.portal.NoSuchExportImportConfigurationException if a export import configuration with the primary key could not be found
	 */
	@Override
	public ExportImportConfiguration[] findByGroupId_PrevAndNext(
		long exportImportConfigurationId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchExportImportConfigurationException {
		ExportImportConfiguration exportImportConfiguration = findByPrimaryKey(exportImportConfigurationId);

		Session session = null;

		try {
			session = openSession();

			ExportImportConfiguration[] array = new ExportImportConfigurationImpl[3];

			array[0] = getByGroupId_PrevAndNext(session,
					exportImportConfiguration, groupId, orderByComparator, true);

			array[1] = exportImportConfiguration;

			array[2] = getByGroupId_PrevAndNext(session,
					exportImportConfiguration, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ExportImportConfiguration getByGroupId_PrevAndNext(
		Session session, ExportImportConfiguration exportImportConfiguration,
		long groupId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_EXPORTIMPORTCONFIGURATION_WHERE);

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
			query.append(ExportImportConfigurationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(exportImportConfiguration);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ExportImportConfiguration> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the export import configurations where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (ExportImportConfiguration exportImportConfiguration : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(exportImportConfiguration);
		}
	}

	/**
	 * Returns the number of export import configurations where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching export import configurations
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_EXPORTIMPORTCONFIGURATION_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "exportImportConfiguration.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(ExportImportConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ExportImportConfigurationModelImpl.FINDER_CACHE_ENABLED,
			ExportImportConfigurationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(ExportImportConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ExportImportConfigurationModelImpl.FINDER_CACHE_ENABLED,
			ExportImportConfigurationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			ExportImportConfigurationModelImpl.COMPANYID_COLUMN_BITMASK |
			ExportImportConfigurationModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(ExportImportConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ExportImportConfigurationModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCompanyId", new String[] { Long.class.getName() });

	/**
	 * Returns all the export import configurations where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the export import configurations where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ExportImportConfigurationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @return the range of matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByCompanyId(long companyId,
		int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the export import configurations where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ExportImportConfigurationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByCompanyId(long companyId,
		int start, int end, OrderByComparator orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<ExportImportConfiguration> list = (List<ExportImportConfiguration>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ExportImportConfiguration exportImportConfiguration : list) {
				if ((companyId != exportImportConfiguration.getCompanyId())) {
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

			query.append(_SQL_SELECT_EXPORTIMPORTCONFIGURATION_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ExportImportConfigurationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<ExportImportConfiguration>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ExportImportConfiguration>)QueryUtil.list(q,
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
	 * Returns the first export import configuration in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching export import configuration
	 * @throws com.liferay.portal.NoSuchExportImportConfigurationException if a matching export import configuration could not be found
	 */
	@Override
	public ExportImportConfiguration findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchExportImportConfigurationException {
		ExportImportConfiguration exportImportConfiguration = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (exportImportConfiguration != null) {
			return exportImportConfiguration;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchExportImportConfigurationException(msg.toString());
	}

	/**
	 * Returns the first export import configuration in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching export import configuration, or <code>null</code> if a matching export import configuration could not be found
	 */
	@Override
	public ExportImportConfiguration fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) {
		List<ExportImportConfiguration> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last export import configuration in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching export import configuration
	 * @throws com.liferay.portal.NoSuchExportImportConfigurationException if a matching export import configuration could not be found
	 */
	@Override
	public ExportImportConfiguration findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchExportImportConfigurationException {
		ExportImportConfiguration exportImportConfiguration = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (exportImportConfiguration != null) {
			return exportImportConfiguration;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchExportImportConfigurationException(msg.toString());
	}

	/**
	 * Returns the last export import configuration in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching export import configuration, or <code>null</code> if a matching export import configuration could not be found
	 */
	@Override
	public ExportImportConfiguration fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<ExportImportConfiguration> list = findByCompanyId(companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the export import configurations before and after the current export import configuration in the ordered set where companyId = &#63;.
	 *
	 * @param exportImportConfigurationId the primary key of the current export import configuration
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next export import configuration
	 * @throws com.liferay.portal.NoSuchExportImportConfigurationException if a export import configuration with the primary key could not be found
	 */
	@Override
	public ExportImportConfiguration[] findByCompanyId_PrevAndNext(
		long exportImportConfigurationId, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchExportImportConfigurationException {
		ExportImportConfiguration exportImportConfiguration = findByPrimaryKey(exportImportConfigurationId);

		Session session = null;

		try {
			session = openSession();

			ExportImportConfiguration[] array = new ExportImportConfigurationImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session,
					exportImportConfiguration, companyId, orderByComparator,
					true);

			array[1] = exportImportConfiguration;

			array[2] = getByCompanyId_PrevAndNext(session,
					exportImportConfiguration, companyId, orderByComparator,
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

	protected ExportImportConfiguration getByCompanyId_PrevAndNext(
		Session session, ExportImportConfiguration exportImportConfiguration,
		long companyId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_EXPORTIMPORTCONFIGURATION_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			query.append(ExportImportConfigurationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(exportImportConfiguration);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ExportImportConfiguration> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the export import configurations where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (ExportImportConfiguration exportImportConfiguration : findByCompanyId(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(exportImportConfiguration);
		}
	}

	/**
	 * Returns the number of export import configurations where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching export import configurations
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_EXPORTIMPORTCONFIGURATION_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "exportImportConfiguration.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T = new FinderPath(ExportImportConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ExportImportConfigurationModelImpl.FINDER_CACHE_ENABLED,
			ExportImportConfigurationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T = new FinderPath(ExportImportConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ExportImportConfigurationModelImpl.FINDER_CACHE_ENABLED,
			ExportImportConfigurationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_T",
			new String[] { Long.class.getName(), Integer.class.getName() },
			ExportImportConfigurationModelImpl.GROUPID_COLUMN_BITMASK |
			ExportImportConfigurationModelImpl.TYPE_COLUMN_BITMASK |
			ExportImportConfigurationModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_T = new FinderPath(ExportImportConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ExportImportConfigurationModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_T",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the export import configurations where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByG_T(long groupId, int type) {
		return findByG_T(groupId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the export import configurations where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ExportImportConfigurationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @return the range of matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByG_T(long groupId, int type,
		int start, int end) {
		return findByG_T(groupId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the export import configurations where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ExportImportConfigurationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByG_T(long groupId, int type,
		int start, int end, OrderByComparator orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T;
			finderArgs = new Object[] { groupId, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T;
			finderArgs = new Object[] {
					groupId, type,
					
					start, end, orderByComparator
				};
		}

		List<ExportImportConfiguration> list = (List<ExportImportConfiguration>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ExportImportConfiguration exportImportConfiguration : list) {
				if ((groupId != exportImportConfiguration.getGroupId()) ||
						(type != exportImportConfiguration.getType())) {
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

			query.append(_SQL_SELECT_EXPORTIMPORTCONFIGURATION_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ExportImportConfigurationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(type);

				if (!pagination) {
					list = (List<ExportImportConfiguration>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ExportImportConfiguration>)QueryUtil.list(q,
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
	 * Returns the first export import configuration in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching export import configuration
	 * @throws com.liferay.portal.NoSuchExportImportConfigurationException if a matching export import configuration could not be found
	 */
	@Override
	public ExportImportConfiguration findByG_T_First(long groupId, int type,
		OrderByComparator orderByComparator)
		throws NoSuchExportImportConfigurationException {
		ExportImportConfiguration exportImportConfiguration = fetchByG_T_First(groupId,
				type, orderByComparator);

		if (exportImportConfiguration != null) {
			return exportImportConfiguration;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchExportImportConfigurationException(msg.toString());
	}

	/**
	 * Returns the first export import configuration in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching export import configuration, or <code>null</code> if a matching export import configuration could not be found
	 */
	@Override
	public ExportImportConfiguration fetchByG_T_First(long groupId, int type,
		OrderByComparator orderByComparator) {
		List<ExportImportConfiguration> list = findByG_T(groupId, type, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last export import configuration in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching export import configuration
	 * @throws com.liferay.portal.NoSuchExportImportConfigurationException if a matching export import configuration could not be found
	 */
	@Override
	public ExportImportConfiguration findByG_T_Last(long groupId, int type,
		OrderByComparator orderByComparator)
		throws NoSuchExportImportConfigurationException {
		ExportImportConfiguration exportImportConfiguration = fetchByG_T_Last(groupId,
				type, orderByComparator);

		if (exportImportConfiguration != null) {
			return exportImportConfiguration;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchExportImportConfigurationException(msg.toString());
	}

	/**
	 * Returns the last export import configuration in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching export import configuration, or <code>null</code> if a matching export import configuration could not be found
	 */
	@Override
	public ExportImportConfiguration fetchByG_T_Last(long groupId, int type,
		OrderByComparator orderByComparator) {
		int count = countByG_T(groupId, type);

		if (count == 0) {
			return null;
		}

		List<ExportImportConfiguration> list = findByG_T(groupId, type,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the export import configurations before and after the current export import configuration in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param exportImportConfigurationId the primary key of the current export import configuration
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next export import configuration
	 * @throws com.liferay.portal.NoSuchExportImportConfigurationException if a export import configuration with the primary key could not be found
	 */
	@Override
	public ExportImportConfiguration[] findByG_T_PrevAndNext(
		long exportImportConfigurationId, long groupId, int type,
		OrderByComparator orderByComparator)
		throws NoSuchExportImportConfigurationException {
		ExportImportConfiguration exportImportConfiguration = findByPrimaryKey(exportImportConfigurationId);

		Session session = null;

		try {
			session = openSession();

			ExportImportConfiguration[] array = new ExportImportConfigurationImpl[3];

			array[0] = getByG_T_PrevAndNext(session, exportImportConfiguration,
					groupId, type, orderByComparator, true);

			array[1] = exportImportConfiguration;

			array[2] = getByG_T_PrevAndNext(session, exportImportConfiguration,
					groupId, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ExportImportConfiguration getByG_T_PrevAndNext(Session session,
		ExportImportConfiguration exportImportConfiguration, long groupId,
		int type, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_EXPORTIMPORTCONFIGURATION_WHERE);

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_T_TYPE_2);

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
			query.append(ExportImportConfigurationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(exportImportConfiguration);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ExportImportConfiguration> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the export import configurations where groupId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 */
	@Override
	public void removeByG_T(long groupId, int type) {
		for (ExportImportConfiguration exportImportConfiguration : findByG_T(
				groupId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(exportImportConfiguration);
		}
	}

	/**
	 * Returns the number of export import configurations where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching export import configurations
	 */
	@Override
	public int countByG_T(long groupId, int type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_T;

		Object[] finderArgs = new Object[] { groupId, type };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_EXPORTIMPORTCONFIGURATION_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(type);

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

	private static final String _FINDER_COLUMN_G_T_GROUPID_2 = "exportImportConfiguration.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_T_TYPE_2 = "exportImportConfiguration.type = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_S = new FinderPath(ExportImportConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ExportImportConfigurationModelImpl.FINDER_CACHE_ENABLED,
			ExportImportConfigurationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S = new FinderPath(ExportImportConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ExportImportConfigurationModelImpl.FINDER_CACHE_ENABLED,
			ExportImportConfigurationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_S",
			new String[] { Long.class.getName(), Integer.class.getName() },
			ExportImportConfigurationModelImpl.GROUPID_COLUMN_BITMASK |
			ExportImportConfigurationModelImpl.STATUS_COLUMN_BITMASK |
			ExportImportConfigurationModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_S = new FinderPath(ExportImportConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ExportImportConfigurationModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_S",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the export import configurations where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByG_S(long groupId, int status) {
		return findByG_S(groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the export import configurations where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ExportImportConfigurationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @return the range of matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByG_S(long groupId, int status,
		int start, int end) {
		return findByG_S(groupId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the export import configurations where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ExportImportConfigurationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByG_S(long groupId, int status,
		int start, int end, OrderByComparator orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S;
			finderArgs = new Object[] { groupId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_S;
			finderArgs = new Object[] {
					groupId, status,
					
					start, end, orderByComparator
				};
		}

		List<ExportImportConfiguration> list = (List<ExportImportConfiguration>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ExportImportConfiguration exportImportConfiguration : list) {
				if ((groupId != exportImportConfiguration.getGroupId()) ||
						(status != exportImportConfiguration.getStatus())) {
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

			query.append(_SQL_SELECT_EXPORTIMPORTCONFIGURATION_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ExportImportConfigurationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(status);

				if (!pagination) {
					list = (List<ExportImportConfiguration>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ExportImportConfiguration>)QueryUtil.list(q,
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
	 * Returns the first export import configuration in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching export import configuration
	 * @throws com.liferay.portal.NoSuchExportImportConfigurationException if a matching export import configuration could not be found
	 */
	@Override
	public ExportImportConfiguration findByG_S_First(long groupId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchExportImportConfigurationException {
		ExportImportConfiguration exportImportConfiguration = fetchByG_S_First(groupId,
				status, orderByComparator);

		if (exportImportConfiguration != null) {
			return exportImportConfiguration;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchExportImportConfigurationException(msg.toString());
	}

	/**
	 * Returns the first export import configuration in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching export import configuration, or <code>null</code> if a matching export import configuration could not be found
	 */
	@Override
	public ExportImportConfiguration fetchByG_S_First(long groupId, int status,
		OrderByComparator orderByComparator) {
		List<ExportImportConfiguration> list = findByG_S(groupId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last export import configuration in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching export import configuration
	 * @throws com.liferay.portal.NoSuchExportImportConfigurationException if a matching export import configuration could not be found
	 */
	@Override
	public ExportImportConfiguration findByG_S_Last(long groupId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchExportImportConfigurationException {
		ExportImportConfiguration exportImportConfiguration = fetchByG_S_Last(groupId,
				status, orderByComparator);

		if (exportImportConfiguration != null) {
			return exportImportConfiguration;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchExportImportConfigurationException(msg.toString());
	}

	/**
	 * Returns the last export import configuration in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching export import configuration, or <code>null</code> if a matching export import configuration could not be found
	 */
	@Override
	public ExportImportConfiguration fetchByG_S_Last(long groupId, int status,
		OrderByComparator orderByComparator) {
		int count = countByG_S(groupId, status);

		if (count == 0) {
			return null;
		}

		List<ExportImportConfiguration> list = findByG_S(groupId, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the export import configurations before and after the current export import configuration in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param exportImportConfigurationId the primary key of the current export import configuration
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next export import configuration
	 * @throws com.liferay.portal.NoSuchExportImportConfigurationException if a export import configuration with the primary key could not be found
	 */
	@Override
	public ExportImportConfiguration[] findByG_S_PrevAndNext(
		long exportImportConfigurationId, long groupId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchExportImportConfigurationException {
		ExportImportConfiguration exportImportConfiguration = findByPrimaryKey(exportImportConfigurationId);

		Session session = null;

		try {
			session = openSession();

			ExportImportConfiguration[] array = new ExportImportConfigurationImpl[3];

			array[0] = getByG_S_PrevAndNext(session, exportImportConfiguration,
					groupId, status, orderByComparator, true);

			array[1] = exportImportConfiguration;

			array[2] = getByG_S_PrevAndNext(session, exportImportConfiguration,
					groupId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ExportImportConfiguration getByG_S_PrevAndNext(Session session,
		ExportImportConfiguration exportImportConfiguration, long groupId,
		int status, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_EXPORTIMPORTCONFIGURATION_WHERE);

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_S_STATUS_2);

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
			query.append(ExportImportConfigurationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(exportImportConfiguration);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ExportImportConfiguration> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the export import configurations where groupId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 */
	@Override
	public void removeByG_S(long groupId, int status) {
		for (ExportImportConfiguration exportImportConfiguration : findByG_S(
				groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(exportImportConfiguration);
		}
	}

	/**
	 * Returns the number of export import configurations where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching export import configurations
	 */
	@Override
	public int countByG_S(long groupId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_S;

		Object[] finderArgs = new Object[] { groupId, status };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_EXPORTIMPORTCONFIGURATION_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_G_S_GROUPID_2 = "exportImportConfiguration.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_S_STATUS_2 = "exportImportConfiguration.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T_S = new FinderPath(ExportImportConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ExportImportConfigurationModelImpl.FINDER_CACHE_ENABLED,
			ExportImportConfigurationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_T_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T_S = new FinderPath(ExportImportConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ExportImportConfigurationModelImpl.FINDER_CACHE_ENABLED,
			ExportImportConfigurationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_T_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName()
			},
			ExportImportConfigurationModelImpl.GROUPID_COLUMN_BITMASK |
			ExportImportConfigurationModelImpl.TYPE_COLUMN_BITMASK |
			ExportImportConfigurationModelImpl.STATUS_COLUMN_BITMASK |
			ExportImportConfigurationModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_T_S = new FinderPath(ExportImportConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ExportImportConfigurationModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_T_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the export import configurations where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @return the matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByG_T_S(long groupId, int type,
		int status) {
		return findByG_T_S(groupId, type, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the export import configurations where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ExportImportConfigurationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @return the range of matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByG_T_S(long groupId, int type,
		int status, int start, int end) {
		return findByG_T_S(groupId, type, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the export import configurations where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ExportImportConfigurationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByG_T_S(long groupId, int type,
		int status, int start, int end, OrderByComparator orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T_S;
			finderArgs = new Object[] { groupId, type, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T_S;
			finderArgs = new Object[] {
					groupId, type, status,
					
					start, end, orderByComparator
				};
		}

		List<ExportImportConfiguration> list = (List<ExportImportConfiguration>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ExportImportConfiguration exportImportConfiguration : list) {
				if ((groupId != exportImportConfiguration.getGroupId()) ||
						(type != exportImportConfiguration.getType()) ||
						(status != exportImportConfiguration.getStatus())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_EXPORTIMPORTCONFIGURATION_WHERE);

			query.append(_FINDER_COLUMN_G_T_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_T_S_TYPE_2);

			query.append(_FINDER_COLUMN_G_T_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ExportImportConfigurationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(type);

				qPos.add(status);

				if (!pagination) {
					list = (List<ExportImportConfiguration>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ExportImportConfiguration>)QueryUtil.list(q,
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
	 * Returns the first export import configuration in the ordered set where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching export import configuration
	 * @throws com.liferay.portal.NoSuchExportImportConfigurationException if a matching export import configuration could not be found
	 */
	@Override
	public ExportImportConfiguration findByG_T_S_First(long groupId, int type,
		int status, OrderByComparator orderByComparator)
		throws NoSuchExportImportConfigurationException {
		ExportImportConfiguration exportImportConfiguration = fetchByG_T_S_First(groupId,
				type, status, orderByComparator);

		if (exportImportConfiguration != null) {
			return exportImportConfiguration;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", type=");
		msg.append(type);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchExportImportConfigurationException(msg.toString());
	}

	/**
	 * Returns the first export import configuration in the ordered set where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching export import configuration, or <code>null</code> if a matching export import configuration could not be found
	 */
	@Override
	public ExportImportConfiguration fetchByG_T_S_First(long groupId, int type,
		int status, OrderByComparator orderByComparator) {
		List<ExportImportConfiguration> list = findByG_T_S(groupId, type,
				status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last export import configuration in the ordered set where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching export import configuration
	 * @throws com.liferay.portal.NoSuchExportImportConfigurationException if a matching export import configuration could not be found
	 */
	@Override
	public ExportImportConfiguration findByG_T_S_Last(long groupId, int type,
		int status, OrderByComparator orderByComparator)
		throws NoSuchExportImportConfigurationException {
		ExportImportConfiguration exportImportConfiguration = fetchByG_T_S_Last(groupId,
				type, status, orderByComparator);

		if (exportImportConfiguration != null) {
			return exportImportConfiguration;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", type=");
		msg.append(type);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchExportImportConfigurationException(msg.toString());
	}

	/**
	 * Returns the last export import configuration in the ordered set where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching export import configuration, or <code>null</code> if a matching export import configuration could not be found
	 */
	@Override
	public ExportImportConfiguration fetchByG_T_S_Last(long groupId, int type,
		int status, OrderByComparator orderByComparator) {
		int count = countByG_T_S(groupId, type, status);

		if (count == 0) {
			return null;
		}

		List<ExportImportConfiguration> list = findByG_T_S(groupId, type,
				status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the export import configurations before and after the current export import configuration in the ordered set where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param exportImportConfigurationId the primary key of the current export import configuration
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next export import configuration
	 * @throws com.liferay.portal.NoSuchExportImportConfigurationException if a export import configuration with the primary key could not be found
	 */
	@Override
	public ExportImportConfiguration[] findByG_T_S_PrevAndNext(
		long exportImportConfigurationId, long groupId, int type, int status,
		OrderByComparator orderByComparator)
		throws NoSuchExportImportConfigurationException {
		ExportImportConfiguration exportImportConfiguration = findByPrimaryKey(exportImportConfigurationId);

		Session session = null;

		try {
			session = openSession();

			ExportImportConfiguration[] array = new ExportImportConfigurationImpl[3];

			array[0] = getByG_T_S_PrevAndNext(session,
					exportImportConfiguration, groupId, type, status,
					orderByComparator, true);

			array[1] = exportImportConfiguration;

			array[2] = getByG_T_S_PrevAndNext(session,
					exportImportConfiguration, groupId, type, status,
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

	protected ExportImportConfiguration getByG_T_S_PrevAndNext(
		Session session, ExportImportConfiguration exportImportConfiguration,
		long groupId, int type, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_EXPORTIMPORTCONFIGURATION_WHERE);

		query.append(_FINDER_COLUMN_G_T_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_T_S_TYPE_2);

		query.append(_FINDER_COLUMN_G_T_S_STATUS_2);

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
			query.append(ExportImportConfigurationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(type);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(exportImportConfiguration);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ExportImportConfiguration> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the export import configurations where groupId = &#63; and type = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 */
	@Override
	public void removeByG_T_S(long groupId, int type, int status) {
		for (ExportImportConfiguration exportImportConfiguration : findByG_T_S(
				groupId, type, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(exportImportConfiguration);
		}
	}

	/**
	 * Returns the number of export import configurations where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @return the number of matching export import configurations
	 */
	@Override
	public int countByG_T_S(long groupId, int type, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_T_S;

		Object[] finderArgs = new Object[] { groupId, type, status };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_EXPORTIMPORTCONFIGURATION_WHERE);

			query.append(_FINDER_COLUMN_G_T_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_T_S_TYPE_2);

			query.append(_FINDER_COLUMN_G_T_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(type);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_G_T_S_GROUPID_2 = "exportImportConfiguration.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_T_S_TYPE_2 = "exportImportConfiguration.type = ? AND ";
	private static final String _FINDER_COLUMN_G_T_S_STATUS_2 = "exportImportConfiguration.status = ?";

	public ExportImportConfigurationPersistenceImpl() {
		setModelClass(ExportImportConfiguration.class);
	}

	/**
	 * Caches the export import configuration in the entity cache if it is enabled.
	 *
	 * @param exportImportConfiguration the export import configuration
	 */
	@Override
	public void cacheResult(ExportImportConfiguration exportImportConfiguration) {
		EntityCacheUtil.putResult(ExportImportConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ExportImportConfigurationImpl.class,
			exportImportConfiguration.getPrimaryKey(), exportImportConfiguration);

		exportImportConfiguration.resetOriginalValues();
	}

	/**
	 * Caches the export import configurations in the entity cache if it is enabled.
	 *
	 * @param exportImportConfigurations the export import configurations
	 */
	@Override
	public void cacheResult(
		List<ExportImportConfiguration> exportImportConfigurations) {
		for (ExportImportConfiguration exportImportConfiguration : exportImportConfigurations) {
			if (EntityCacheUtil.getResult(
						ExportImportConfigurationModelImpl.ENTITY_CACHE_ENABLED,
						ExportImportConfigurationImpl.class,
						exportImportConfiguration.getPrimaryKey()) == null) {
				cacheResult(exportImportConfiguration);
			}
			else {
				exportImportConfiguration.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all export import configurations.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(ExportImportConfigurationImpl.class.getName());
		}

		EntityCacheUtil.clearCache(ExportImportConfigurationImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the export import configuration.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ExportImportConfiguration exportImportConfiguration) {
		EntityCacheUtil.removeResult(ExportImportConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ExportImportConfigurationImpl.class,
			exportImportConfiguration.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<ExportImportConfiguration> exportImportConfigurations) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ExportImportConfiguration exportImportConfiguration : exportImportConfigurations) {
			EntityCacheUtil.removeResult(ExportImportConfigurationModelImpl.ENTITY_CACHE_ENABLED,
				ExportImportConfigurationImpl.class,
				exportImportConfiguration.getPrimaryKey());
		}
	}

	/**
	 * Creates a new export import configuration with the primary key. Does not add the export import configuration to the database.
	 *
	 * @param exportImportConfigurationId the primary key for the new export import configuration
	 * @return the new export import configuration
	 */
	@Override
	public ExportImportConfiguration create(long exportImportConfigurationId) {
		ExportImportConfiguration exportImportConfiguration = new ExportImportConfigurationImpl();

		exportImportConfiguration.setNew(true);
		exportImportConfiguration.setPrimaryKey(exportImportConfigurationId);

		return exportImportConfiguration;
	}

	/**
	 * Removes the export import configuration with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param exportImportConfigurationId the primary key of the export import configuration
	 * @return the export import configuration that was removed
	 * @throws com.liferay.portal.NoSuchExportImportConfigurationException if a export import configuration with the primary key could not be found
	 */
	@Override
	public ExportImportConfiguration remove(long exportImportConfigurationId)
		throws NoSuchExportImportConfigurationException {
		return remove((Serializable)exportImportConfigurationId);
	}

	/**
	 * Removes the export import configuration with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the export import configuration
	 * @return the export import configuration that was removed
	 * @throws com.liferay.portal.NoSuchExportImportConfigurationException if a export import configuration with the primary key could not be found
	 */
	@Override
	public ExportImportConfiguration remove(Serializable primaryKey)
		throws NoSuchExportImportConfigurationException {
		Session session = null;

		try {
			session = openSession();

			ExportImportConfiguration exportImportConfiguration = (ExportImportConfiguration)session.get(ExportImportConfigurationImpl.class,
					primaryKey);

			if (exportImportConfiguration == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchExportImportConfigurationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(exportImportConfiguration);
		}
		catch (NoSuchExportImportConfigurationException nsee) {
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
	protected ExportImportConfiguration removeImpl(
		ExportImportConfiguration exportImportConfiguration) {
		exportImportConfiguration = toUnwrappedModel(exportImportConfiguration);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(exportImportConfiguration)) {
				exportImportConfiguration = (ExportImportConfiguration)session.get(ExportImportConfigurationImpl.class,
						exportImportConfiguration.getPrimaryKeyObj());
			}

			if (exportImportConfiguration != null) {
				session.delete(exportImportConfiguration);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (exportImportConfiguration != null) {
			clearCache(exportImportConfiguration);
		}

		return exportImportConfiguration;
	}

	@Override
	public ExportImportConfiguration updateImpl(
		com.liferay.portal.model.ExportImportConfiguration exportImportConfiguration) {
		exportImportConfiguration = toUnwrappedModel(exportImportConfiguration);

		boolean isNew = exportImportConfiguration.isNew();

		ExportImportConfigurationModelImpl exportImportConfigurationModelImpl = (ExportImportConfigurationModelImpl)exportImportConfiguration;

		Session session = null;

		try {
			session = openSession();

			if (exportImportConfiguration.isNew()) {
				session.save(exportImportConfiguration);

				exportImportConfiguration.setNew(false);
			}
			else {
				session.merge(exportImportConfiguration);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew ||
				!ExportImportConfigurationModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((exportImportConfigurationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						exportImportConfigurationModelImpl.getOriginalGroupId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						exportImportConfigurationModelImpl.getGroupId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((exportImportConfigurationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						exportImportConfigurationModelImpl.getOriginalCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						exportImportConfigurationModelImpl.getCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((exportImportConfigurationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						exportImportConfigurationModelImpl.getOriginalGroupId(),
						exportImportConfigurationModelImpl.getOriginalType()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T,
					args);

				args = new Object[] {
						exportImportConfigurationModelImpl.getGroupId(),
						exportImportConfigurationModelImpl.getType()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T,
					args);
			}

			if ((exportImportConfigurationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						exportImportConfigurationModelImpl.getOriginalGroupId(),
						exportImportConfigurationModelImpl.getOriginalStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S,
					args);

				args = new Object[] {
						exportImportConfigurationModelImpl.getGroupId(),
						exportImportConfigurationModelImpl.getStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S,
					args);
			}

			if ((exportImportConfigurationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						exportImportConfigurationModelImpl.getOriginalGroupId(),
						exportImportConfigurationModelImpl.getOriginalType(),
						exportImportConfigurationModelImpl.getOriginalStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_T_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T_S,
					args);

				args = new Object[] {
						exportImportConfigurationModelImpl.getGroupId(),
						exportImportConfigurationModelImpl.getType(),
						exportImportConfigurationModelImpl.getStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_T_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T_S,
					args);
			}
		}

		EntityCacheUtil.putResult(ExportImportConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ExportImportConfigurationImpl.class,
			exportImportConfiguration.getPrimaryKey(),
			exportImportConfiguration, false);

		exportImportConfiguration.resetOriginalValues();

		return exportImportConfiguration;
	}

	protected ExportImportConfiguration toUnwrappedModel(
		ExportImportConfiguration exportImportConfiguration) {
		if (exportImportConfiguration instanceof ExportImportConfigurationImpl) {
			return exportImportConfiguration;
		}

		ExportImportConfigurationImpl exportImportConfigurationImpl = new ExportImportConfigurationImpl();

		exportImportConfigurationImpl.setNew(exportImportConfiguration.isNew());
		exportImportConfigurationImpl.setPrimaryKey(exportImportConfiguration.getPrimaryKey());

		exportImportConfigurationImpl.setMvccVersion(exportImportConfiguration.getMvccVersion());
		exportImportConfigurationImpl.setExportImportConfigurationId(exportImportConfiguration.getExportImportConfigurationId());
		exportImportConfigurationImpl.setGroupId(exportImportConfiguration.getGroupId());
		exportImportConfigurationImpl.setCompanyId(exportImportConfiguration.getCompanyId());
		exportImportConfigurationImpl.setUserId(exportImportConfiguration.getUserId());
		exportImportConfigurationImpl.setUserName(exportImportConfiguration.getUserName());
		exportImportConfigurationImpl.setCreateDate(exportImportConfiguration.getCreateDate());
		exportImportConfigurationImpl.setModifiedDate(exportImportConfiguration.getModifiedDate());
		exportImportConfigurationImpl.setName(exportImportConfiguration.getName());
		exportImportConfigurationImpl.setDescription(exportImportConfiguration.getDescription());
		exportImportConfigurationImpl.setType(exportImportConfiguration.getType());
		exportImportConfigurationImpl.setSettings(exportImportConfiguration.getSettings());
		exportImportConfigurationImpl.setStatus(exportImportConfiguration.getStatus());
		exportImportConfigurationImpl.setStatusByUserId(exportImportConfiguration.getStatusByUserId());
		exportImportConfigurationImpl.setStatusByUserName(exportImportConfiguration.getStatusByUserName());
		exportImportConfigurationImpl.setStatusDate(exportImportConfiguration.getStatusDate());

		return exportImportConfigurationImpl;
	}

	/**
	 * Returns the export import configuration with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the export import configuration
	 * @return the export import configuration
	 * @throws com.liferay.portal.NoSuchExportImportConfigurationException if a export import configuration with the primary key could not be found
	 */
	@Override
	public ExportImportConfiguration findByPrimaryKey(Serializable primaryKey)
		throws NoSuchExportImportConfigurationException {
		ExportImportConfiguration exportImportConfiguration = fetchByPrimaryKey(primaryKey);

		if (exportImportConfiguration == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchExportImportConfigurationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return exportImportConfiguration;
	}

	/**
	 * Returns the export import configuration with the primary key or throws a {@link com.liferay.portal.NoSuchExportImportConfigurationException} if it could not be found.
	 *
	 * @param exportImportConfigurationId the primary key of the export import configuration
	 * @return the export import configuration
	 * @throws com.liferay.portal.NoSuchExportImportConfigurationException if a export import configuration with the primary key could not be found
	 */
	@Override
	public ExportImportConfiguration findByPrimaryKey(
		long exportImportConfigurationId)
		throws NoSuchExportImportConfigurationException {
		return findByPrimaryKey((Serializable)exportImportConfigurationId);
	}

	/**
	 * Returns the export import configuration with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the export import configuration
	 * @return the export import configuration, or <code>null</code> if a export import configuration with the primary key could not be found
	 */
	@Override
	public ExportImportConfiguration fetchByPrimaryKey(Serializable primaryKey) {
		ExportImportConfiguration exportImportConfiguration = (ExportImportConfiguration)EntityCacheUtil.getResult(ExportImportConfigurationModelImpl.ENTITY_CACHE_ENABLED,
				ExportImportConfigurationImpl.class, primaryKey);

		if (exportImportConfiguration == _nullExportImportConfiguration) {
			return null;
		}

		if (exportImportConfiguration == null) {
			Session session = null;

			try {
				session = openSession();

				exportImportConfiguration = (ExportImportConfiguration)session.get(ExportImportConfigurationImpl.class,
						primaryKey);

				if (exportImportConfiguration != null) {
					cacheResult(exportImportConfiguration);
				}
				else {
					EntityCacheUtil.putResult(ExportImportConfigurationModelImpl.ENTITY_CACHE_ENABLED,
						ExportImportConfigurationImpl.class, primaryKey,
						_nullExportImportConfiguration);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(ExportImportConfigurationModelImpl.ENTITY_CACHE_ENABLED,
					ExportImportConfigurationImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return exportImportConfiguration;
	}

	/**
	 * Returns the export import configuration with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param exportImportConfigurationId the primary key of the export import configuration
	 * @return the export import configuration, or <code>null</code> if a export import configuration with the primary key could not be found
	 */
	@Override
	public ExportImportConfiguration fetchByPrimaryKey(
		long exportImportConfigurationId) {
		return fetchByPrimaryKey((Serializable)exportImportConfigurationId);
	}

	@Override
	public Map<Serializable, ExportImportConfiguration> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ExportImportConfiguration> map = new HashMap<Serializable, ExportImportConfiguration>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ExportImportConfiguration exportImportConfiguration = fetchByPrimaryKey(primaryKey);

			if (exportImportConfiguration != null) {
				map.put(primaryKey, exportImportConfiguration);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			ExportImportConfiguration exportImportConfiguration = (ExportImportConfiguration)EntityCacheUtil.getResult(ExportImportConfigurationModelImpl.ENTITY_CACHE_ENABLED,
					ExportImportConfigurationImpl.class, primaryKey);

			if (exportImportConfiguration == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, exportImportConfiguration);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_EXPORTIMPORTCONFIGURATION_WHERE_PKS_IN);

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

			for (ExportImportConfiguration exportImportConfiguration : (List<ExportImportConfiguration>)q.list()) {
				map.put(exportImportConfiguration.getPrimaryKeyObj(),
					exportImportConfiguration);

				cacheResult(exportImportConfiguration);

				uncachedPrimaryKeys.remove(exportImportConfiguration.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				EntityCacheUtil.putResult(ExportImportConfigurationModelImpl.ENTITY_CACHE_ENABLED,
					ExportImportConfigurationImpl.class, primaryKey,
					_nullExportImportConfiguration);
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
	 * Returns all the export import configurations.
	 *
	 * @return the export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the export import configurations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ExportImportConfigurationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @return the range of export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the export import configurations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ExportImportConfigurationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findAll(int start, int end,
		OrderByComparator orderByComparator) {
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

		List<ExportImportConfiguration> list = (List<ExportImportConfiguration>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_EXPORTIMPORTCONFIGURATION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_EXPORTIMPORTCONFIGURATION;

				if (pagination) {
					sql = sql.concat(ExportImportConfigurationModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<ExportImportConfiguration>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ExportImportConfiguration>)QueryUtil.list(q,
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
	 * Removes all the export import configurations from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ExportImportConfiguration exportImportConfiguration : findAll()) {
			remove(exportImportConfiguration);
		}
	}

	/**
	 * Returns the number of export import configurations.
	 *
	 * @return the number of export import configurations
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_EXPORTIMPORTCONFIGURATION);

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

	@Override
	protected Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	/**
	 * Initializes the export import configuration persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.ExportImportConfiguration")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ExportImportConfiguration>> listenersList = new ArrayList<ModelListener<ExportImportConfiguration>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ExportImportConfiguration>)InstanceFactory.newInstance(
							getClassLoader(), listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ExportImportConfigurationImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_EXPORTIMPORTCONFIGURATION = "SELECT exportImportConfiguration FROM ExportImportConfiguration exportImportConfiguration";
	private static final String _SQL_SELECT_EXPORTIMPORTCONFIGURATION_WHERE_PKS_IN =
		"SELECT exportImportConfiguration FROM ExportImportConfiguration exportImportConfiguration WHERE exportImportConfigurationId IN (";
	private static final String _SQL_SELECT_EXPORTIMPORTCONFIGURATION_WHERE = "SELECT exportImportConfiguration FROM ExportImportConfiguration exportImportConfiguration WHERE ";
	private static final String _SQL_COUNT_EXPORTIMPORTCONFIGURATION = "SELECT COUNT(exportImportConfiguration) FROM ExportImportConfiguration exportImportConfiguration";
	private static final String _SQL_COUNT_EXPORTIMPORTCONFIGURATION_WHERE = "SELECT COUNT(exportImportConfiguration) FROM ExportImportConfiguration exportImportConfiguration WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "exportImportConfiguration.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ExportImportConfiguration exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ExportImportConfiguration exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(ExportImportConfigurationPersistenceImpl.class);
	private static Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"type", "settings"
			});
	private static ExportImportConfiguration _nullExportImportConfiguration = new ExportImportConfigurationImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<ExportImportConfiguration> toCacheModel() {
				return _nullExportImportConfigurationCacheModel;
			}
		};

	private static CacheModel<ExportImportConfiguration> _nullExportImportConfigurationCacheModel =
		new NullCacheModel();

	private static class NullCacheModel implements CacheModel<ExportImportConfiguration>,
		MVCCModel {
		@Override
		public long getMvccVersion() {
			return 0;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
		}

		@Override
		public ExportImportConfiguration toEntityModel() {
			return _nullExportImportConfiguration;
		}
	}
}