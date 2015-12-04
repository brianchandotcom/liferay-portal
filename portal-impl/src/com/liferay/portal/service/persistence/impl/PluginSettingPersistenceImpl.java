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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.NoSuchPluginSettingException;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.PluginSetting;
import com.liferay.portal.model.impl.PluginSettingImpl;
import com.liferay.portal.model.impl.PluginSettingModelImpl;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.PluginSettingPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the plugin setting service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PluginSettingPersistence
 * @see com.liferay.portal.service.persistence.PluginSettingUtil
 * @generated
 */
@ProviderType
public class PluginSettingPersistenceImpl extends BasePersistenceImpl<PluginSetting>
	implements PluginSettingPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link PluginSettingUtil} to access the plugin setting persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = PluginSettingImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingModelImpl.FINDER_CACHE_ENABLED,
			PluginSettingImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingModelImpl.FINDER_CACHE_ENABLED,
			PluginSettingImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingModelImpl.FINDER_CACHE_ENABLED,
			PluginSettingImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingModelImpl.FINDER_CACHE_ENABLED,
			PluginSettingImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCompanyId", new String[] { Long.class.getName() },
			PluginSettingModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the plugin settings where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching plugin settings
	 */
	@Override
	public List<PluginSetting> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the plugin settings where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PluginSettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of plugin settings
	 * @param end the upper bound of the range of plugin settings (not inclusive)
	 * @return the range of matching plugin settings
	 */
	@Override
	public List<PluginSetting> findByCompanyId(long companyId, int start,
		int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the plugin settings where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PluginSettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of plugin settings
	 * @param end the upper bound of the range of plugin settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching plugin settings
	 */
	@Override
	public List<PluginSetting> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<PluginSetting> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the plugin settings where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PluginSettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of plugin settings
	 * @param end the upper bound of the range of plugin settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching plugin settings
	 */
	@Override
	public List<PluginSetting> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<PluginSetting> orderByComparator,
		boolean retrieveFromCache) {
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

		List<PluginSetting> list = null;

		if (retrieveFromCache) {
			list = (List<PluginSetting>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (PluginSetting pluginSetting : list) {
					if ((companyId != pluginSetting.getCompanyId())) {
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

			query.append(_SQL_SELECT_PLUGINSETTING_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(PluginSettingModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<PluginSetting>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PluginSetting>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first plugin setting in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching plugin setting
	 * @throws NoSuchPluginSettingException if a matching plugin setting could not be found
	 */
	@Override
	public PluginSetting findByCompanyId_First(long companyId,
		OrderByComparator<PluginSetting> orderByComparator)
		throws NoSuchPluginSettingException {
		PluginSetting pluginSetting = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (pluginSetting != null) {
			return pluginSetting;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPluginSettingException(msg.toString());
	}

	/**
	 * Returns the first plugin setting in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching plugin setting, or <code>null</code> if a matching plugin setting could not be found
	 */
	@Override
	public PluginSetting fetchByCompanyId_First(long companyId,
		OrderByComparator<PluginSetting> orderByComparator) {
		List<PluginSetting> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last plugin setting in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching plugin setting
	 * @throws NoSuchPluginSettingException if a matching plugin setting could not be found
	 */
	@Override
	public PluginSetting findByCompanyId_Last(long companyId,
		OrderByComparator<PluginSetting> orderByComparator)
		throws NoSuchPluginSettingException {
		PluginSetting pluginSetting = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (pluginSetting != null) {
			return pluginSetting;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPluginSettingException(msg.toString());
	}

	/**
	 * Returns the last plugin setting in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching plugin setting, or <code>null</code> if a matching plugin setting could not be found
	 */
	@Override
	public PluginSetting fetchByCompanyId_Last(long companyId,
		OrderByComparator<PluginSetting> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<PluginSetting> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the plugin settings before and after the current plugin setting in the ordered set where companyId = &#63;.
	 *
	 * @param pluginSettingId the primary key of the current plugin setting
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next plugin setting
	 * @throws NoSuchPluginSettingException if a plugin setting with the primary key could not be found
	 */
	@Override
	public PluginSetting[] findByCompanyId_PrevAndNext(long pluginSettingId,
		long companyId, OrderByComparator<PluginSetting> orderByComparator)
		throws NoSuchPluginSettingException {
		PluginSetting pluginSetting = findByPrimaryKey(pluginSettingId);

		Session session = null;

		try {
			session = openSession();

			PluginSetting[] array = new PluginSettingImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, pluginSetting,
					companyId, orderByComparator, true);

			array[1] = pluginSetting;

			array[2] = getByCompanyId_PrevAndNext(session, pluginSetting,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PluginSetting getByCompanyId_PrevAndNext(Session session,
		PluginSetting pluginSetting, long companyId,
		OrderByComparator<PluginSetting> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PLUGINSETTING_WHERE);

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
			query.append(PluginSettingModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(pluginSetting);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PluginSetting> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the plugin settings where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (PluginSetting pluginSetting : findByCompanyId(companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(pluginSetting);
		}
	}

	/**
	 * Returns the number of plugin settings where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching plugin settings
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_PLUGINSETTING_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "pluginSetting.companyId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_I_T = new FinderPath(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingModelImpl.FINDER_CACHE_ENABLED,
			PluginSettingImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByC_I_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			PluginSettingModelImpl.COMPANYID_COLUMN_BITMASK |
			PluginSettingModelImpl.PLUGINID_COLUMN_BITMASK |
			PluginSettingModelImpl.PLUGINTYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_I_T = new FinderPath(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_I_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns the plugin setting where companyId = &#63; and pluginId = &#63; and pluginType = &#63; or throws a {@link NoSuchPluginSettingException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param pluginId the plugin ID
	 * @param pluginType the plugin type
	 * @return the matching plugin setting
	 * @throws NoSuchPluginSettingException if a matching plugin setting could not be found
	 */
	@Override
	public PluginSetting findByC_I_T(long companyId, String pluginId,
		String pluginType) throws NoSuchPluginSettingException {
		PluginSetting pluginSetting = fetchByC_I_T(companyId, pluginId,
				pluginType);

		if (pluginSetting == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", pluginId=");
			msg.append(pluginId);

			msg.append(", pluginType=");
			msg.append(pluginType);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPluginSettingException(msg.toString());
		}

		return pluginSetting;
	}

	/**
	 * Returns the plugin setting where companyId = &#63; and pluginId = &#63; and pluginType = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param pluginId the plugin ID
	 * @param pluginType the plugin type
	 * @return the matching plugin setting, or <code>null</code> if a matching plugin setting could not be found
	 */
	@Override
	public PluginSetting fetchByC_I_T(long companyId, String pluginId,
		String pluginType) {
		return fetchByC_I_T(companyId, pluginId, pluginType, true);
	}

	/**
	 * Returns the plugin setting where companyId = &#63; and pluginId = &#63; and pluginType = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param pluginId the plugin ID
	 * @param pluginType the plugin type
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching plugin setting, or <code>null</code> if a matching plugin setting could not be found
	 */
	@Override
	public PluginSetting fetchByC_I_T(long companyId, String pluginId,
		String pluginType, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { companyId, pluginId, pluginType };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_I_T,
					finderArgs, this);
		}

		if (result instanceof PluginSetting) {
			PluginSetting pluginSetting = (PluginSetting)result;

			if ((companyId != pluginSetting.getCompanyId()) ||
					!Validator.equals(pluginId, pluginSetting.getPluginId()) ||
					!Validator.equals(pluginType, pluginSetting.getPluginType())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_PLUGINSETTING_WHERE);

			query.append(_FINDER_COLUMN_C_I_T_COMPANYID_2);

			boolean bindPluginId = false;

			if (pluginId == null) {
				query.append(_FINDER_COLUMN_C_I_T_PLUGINID_1);
			}
			else if (pluginId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_I_T_PLUGINID_3);
			}
			else {
				bindPluginId = true;

				query.append(_FINDER_COLUMN_C_I_T_PLUGINID_2);
			}

			boolean bindPluginType = false;

			if (pluginType == null) {
				query.append(_FINDER_COLUMN_C_I_T_PLUGINTYPE_1);
			}
			else if (pluginType.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_I_T_PLUGINTYPE_3);
			}
			else {
				bindPluginType = true;

				query.append(_FINDER_COLUMN_C_I_T_PLUGINTYPE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindPluginId) {
					qPos.add(pluginId);
				}

				if (bindPluginType) {
					qPos.add(pluginType);
				}

				List<PluginSetting> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_I_T,
						finderArgs, list);
				}
				else {
					PluginSetting pluginSetting = list.get(0);

					result = pluginSetting;

					cacheResult(pluginSetting);

					if ((pluginSetting.getCompanyId() != companyId) ||
							(pluginSetting.getPluginId() == null) ||
							!pluginSetting.getPluginId().equals(pluginId) ||
							(pluginSetting.getPluginType() == null) ||
							!pluginSetting.getPluginType().equals(pluginType)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_I_T,
							finderArgs, pluginSetting);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_I_T, finderArgs);

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
			return (PluginSetting)result;
		}
	}

	/**
	 * Removes the plugin setting where companyId = &#63; and pluginId = &#63; and pluginType = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param pluginId the plugin ID
	 * @param pluginType the plugin type
	 * @return the plugin setting that was removed
	 */
	@Override
	public PluginSetting removeByC_I_T(long companyId, String pluginId,
		String pluginType) throws NoSuchPluginSettingException {
		PluginSetting pluginSetting = findByC_I_T(companyId, pluginId,
				pluginType);

		return remove(pluginSetting);
	}

	/**
	 * Returns the number of plugin settings where companyId = &#63; and pluginId = &#63; and pluginType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param pluginId the plugin ID
	 * @param pluginType the plugin type
	 * @return the number of matching plugin settings
	 */
	@Override
	public int countByC_I_T(long companyId, String pluginId, String pluginType) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_I_T;

		Object[] finderArgs = new Object[] { companyId, pluginId, pluginType };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_PLUGINSETTING_WHERE);

			query.append(_FINDER_COLUMN_C_I_T_COMPANYID_2);

			boolean bindPluginId = false;

			if (pluginId == null) {
				query.append(_FINDER_COLUMN_C_I_T_PLUGINID_1);
			}
			else if (pluginId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_I_T_PLUGINID_3);
			}
			else {
				bindPluginId = true;

				query.append(_FINDER_COLUMN_C_I_T_PLUGINID_2);
			}

			boolean bindPluginType = false;

			if (pluginType == null) {
				query.append(_FINDER_COLUMN_C_I_T_PLUGINTYPE_1);
			}
			else if (pluginType.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_I_T_PLUGINTYPE_3);
			}
			else {
				bindPluginType = true;

				query.append(_FINDER_COLUMN_C_I_T_PLUGINTYPE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindPluginId) {
					qPos.add(pluginId);
				}

				if (bindPluginType) {
					qPos.add(pluginType);
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

	private static final String _FINDER_COLUMN_C_I_T_COMPANYID_2 = "pluginSetting.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_I_T_PLUGINID_1 = "pluginSetting.pluginId IS NULL AND ";
	private static final String _FINDER_COLUMN_C_I_T_PLUGINID_2 = "pluginSetting.pluginId = ? AND ";
	private static final String _FINDER_COLUMN_C_I_T_PLUGINID_3 = "(pluginSetting.pluginId IS NULL OR pluginSetting.pluginId = '') AND ";
	private static final String _FINDER_COLUMN_C_I_T_PLUGINTYPE_1 = "pluginSetting.pluginType IS NULL";
	private static final String _FINDER_COLUMN_C_I_T_PLUGINTYPE_2 = "pluginSetting.pluginType = ?";
	private static final String _FINDER_COLUMN_C_I_T_PLUGINTYPE_3 = "(pluginSetting.pluginType IS NULL OR pluginSetting.pluginType = '')";

	public PluginSettingPersistenceImpl() {
		setModelClass(PluginSetting.class);
	}

	/**
	 * Caches the plugin setting in the entity cache if it is enabled.
	 *
	 * @param pluginSetting the plugin setting
	 */
	@Override
	public void cacheResult(PluginSetting pluginSetting) {
		entityCache.putResult(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingImpl.class, pluginSetting.getPrimaryKey(),
			pluginSetting);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_I_T,
			new Object[] {
				pluginSetting.getCompanyId(), pluginSetting.getPluginId(),
				pluginSetting.getPluginType()
			}, pluginSetting);

		pluginSetting.resetOriginalValues();
	}

	/**
	 * Caches the plugin settings in the entity cache if it is enabled.
	 *
	 * @param pluginSettings the plugin settings
	 */
	@Override
	public void cacheResult(List<PluginSetting> pluginSettings) {
		for (PluginSetting pluginSetting : pluginSettings) {
			if (entityCache.getResult(
						PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
						PluginSettingImpl.class, pluginSetting.getPrimaryKey()) == null) {
				cacheResult(pluginSetting);
			}
			else {
				pluginSetting.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all plugin settings.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(PluginSettingImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the plugin setting.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PluginSetting pluginSetting) {
		entityCache.removeResult(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingImpl.class, pluginSetting.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((PluginSettingModelImpl)pluginSetting);
	}

	@Override
	public void clearCache(List<PluginSetting> pluginSettings) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (PluginSetting pluginSetting : pluginSettings) {
			entityCache.removeResult(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
				PluginSettingImpl.class, pluginSetting.getPrimaryKey());

			clearUniqueFindersCache((PluginSettingModelImpl)pluginSetting);
		}
	}

	protected void cacheUniqueFindersCache(
		PluginSettingModelImpl pluginSettingModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					pluginSettingModelImpl.getCompanyId(),
					pluginSettingModelImpl.getPluginId(),
					pluginSettingModelImpl.getPluginType()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_C_I_T, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_C_I_T, args,
				pluginSettingModelImpl);
		}
		else {
			if ((pluginSettingModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_I_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						pluginSettingModelImpl.getCompanyId(),
						pluginSettingModelImpl.getPluginId(),
						pluginSettingModelImpl.getPluginType()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_C_I_T, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_C_I_T, args,
					pluginSettingModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		PluginSettingModelImpl pluginSettingModelImpl) {
		Object[] args = new Object[] {
				pluginSettingModelImpl.getCompanyId(),
				pluginSettingModelImpl.getPluginId(),
				pluginSettingModelImpl.getPluginType()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_C_I_T, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_C_I_T, args);

		if ((pluginSettingModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_I_T.getColumnBitmask()) != 0) {
			args = new Object[] {
					pluginSettingModelImpl.getOriginalCompanyId(),
					pluginSettingModelImpl.getOriginalPluginId(),
					pluginSettingModelImpl.getOriginalPluginType()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_I_T, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_I_T, args);
		}
	}

	/**
	 * Creates a new plugin setting with the primary key. Does not add the plugin setting to the database.
	 *
	 * @param pluginSettingId the primary key for the new plugin setting
	 * @return the new plugin setting
	 */
	@Override
	public PluginSetting create(long pluginSettingId) {
		PluginSetting pluginSetting = new PluginSettingImpl();

		pluginSetting.setNew(true);
		pluginSetting.setPrimaryKey(pluginSettingId);

		return pluginSetting;
	}

	/**
	 * Removes the plugin setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param pluginSettingId the primary key of the plugin setting
	 * @return the plugin setting that was removed
	 * @throws NoSuchPluginSettingException if a plugin setting with the primary key could not be found
	 */
	@Override
	public PluginSetting remove(long pluginSettingId)
		throws NoSuchPluginSettingException {
		return remove((Serializable)pluginSettingId);
	}

	/**
	 * Removes the plugin setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the plugin setting
	 * @return the plugin setting that was removed
	 * @throws NoSuchPluginSettingException if a plugin setting with the primary key could not be found
	 */
	@Override
	public PluginSetting remove(Serializable primaryKey)
		throws NoSuchPluginSettingException {
		Session session = null;

		try {
			session = openSession();

			PluginSetting pluginSetting = (PluginSetting)session.get(PluginSettingImpl.class,
					primaryKey);

			if (pluginSetting == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPluginSettingException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(pluginSetting);
		}
		catch (NoSuchPluginSettingException nsee) {
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
	protected PluginSetting removeImpl(PluginSetting pluginSetting) {
		pluginSetting = toUnwrappedModel(pluginSetting);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(pluginSetting)) {
				pluginSetting = (PluginSetting)session.get(PluginSettingImpl.class,
						pluginSetting.getPrimaryKeyObj());
			}

			if (pluginSetting != null) {
				session.delete(pluginSetting);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (pluginSetting != null) {
			clearCache(pluginSetting);
		}

		return pluginSetting;
	}

	@Override
	public PluginSetting updateImpl(PluginSetting pluginSetting) {
		pluginSetting = toUnwrappedModel(pluginSetting);

		boolean isNew = pluginSetting.isNew();

		PluginSettingModelImpl pluginSettingModelImpl = (PluginSettingModelImpl)pluginSetting;

		Session session = null;

		try {
			session = openSession();

			if (pluginSetting.isNew()) {
				session.save(pluginSetting);

				pluginSetting.setNew(false);
			}
			else {
				pluginSetting = (PluginSetting)session.merge(pluginSetting);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !PluginSettingModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((pluginSettingModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						pluginSettingModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { pluginSettingModelImpl.getCompanyId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}
		}

		entityCache.putResult(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingImpl.class, pluginSetting.getPrimaryKey(),
			pluginSetting, false);

		clearUniqueFindersCache(pluginSettingModelImpl);
		cacheUniqueFindersCache(pluginSettingModelImpl, isNew);

		pluginSetting.resetOriginalValues();

		return pluginSetting;
	}

	protected PluginSetting toUnwrappedModel(PluginSetting pluginSetting) {
		if (pluginSetting instanceof PluginSettingImpl) {
			return pluginSetting;
		}

		PluginSettingImpl pluginSettingImpl = new PluginSettingImpl();

		pluginSettingImpl.setNew(pluginSetting.isNew());
		pluginSettingImpl.setPrimaryKey(pluginSetting.getPrimaryKey());

		pluginSettingImpl.setMvccVersion(pluginSetting.getMvccVersion());
		pluginSettingImpl.setPluginSettingId(pluginSetting.getPluginSettingId());
		pluginSettingImpl.setCompanyId(pluginSetting.getCompanyId());
		pluginSettingImpl.setPluginId(pluginSetting.getPluginId());
		pluginSettingImpl.setPluginType(pluginSetting.getPluginType());
		pluginSettingImpl.setRoles(pluginSetting.getRoles());
		pluginSettingImpl.setActive(pluginSetting.isActive());

		return pluginSettingImpl;
	}

	/**
	 * Returns the plugin setting with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the plugin setting
	 * @return the plugin setting
	 * @throws NoSuchPluginSettingException if a plugin setting with the primary key could not be found
	 */
	@Override
	public PluginSetting findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPluginSettingException {
		PluginSetting pluginSetting = fetchByPrimaryKey(primaryKey);

		if (pluginSetting == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPluginSettingException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return pluginSetting;
	}

	/**
	 * Returns the plugin setting with the primary key or throws a {@link NoSuchPluginSettingException} if it could not be found.
	 *
	 * @param pluginSettingId the primary key of the plugin setting
	 * @return the plugin setting
	 * @throws NoSuchPluginSettingException if a plugin setting with the primary key could not be found
	 */
	@Override
	public PluginSetting findByPrimaryKey(long pluginSettingId)
		throws NoSuchPluginSettingException {
		return findByPrimaryKey((Serializable)pluginSettingId);
	}

	/**
	 * Returns the plugin setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the plugin setting
	 * @return the plugin setting, or <code>null</code> if a plugin setting with the primary key could not be found
	 */
	@Override
	public PluginSetting fetchByPrimaryKey(Serializable primaryKey) {
		PluginSetting pluginSetting = (PluginSetting)entityCache.getResult(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
				PluginSettingImpl.class, primaryKey);

		if (pluginSetting == _nullPluginSetting) {
			return null;
		}

		if (pluginSetting == null) {
			Session session = null;

			try {
				session = openSession();

				pluginSetting = (PluginSetting)session.get(PluginSettingImpl.class,
						primaryKey);

				if (pluginSetting != null) {
					cacheResult(pluginSetting);
				}
				else {
					entityCache.putResult(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
						PluginSettingImpl.class, primaryKey, _nullPluginSetting);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
					PluginSettingImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return pluginSetting;
	}

	/**
	 * Returns the plugin setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param pluginSettingId the primary key of the plugin setting
	 * @return the plugin setting, or <code>null</code> if a plugin setting with the primary key could not be found
	 */
	@Override
	public PluginSetting fetchByPrimaryKey(long pluginSettingId) {
		return fetchByPrimaryKey((Serializable)pluginSettingId);
	}

	@Override
	public Map<Serializable, PluginSetting> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, PluginSetting> map = new HashMap<Serializable, PluginSetting>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			PluginSetting pluginSetting = fetchByPrimaryKey(primaryKey);

			if (pluginSetting != null) {
				map.put(primaryKey, pluginSetting);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			PluginSetting pluginSetting = (PluginSetting)entityCache.getResult(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
					PluginSettingImpl.class, primaryKey);

			if (pluginSetting == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, pluginSetting);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_PLUGINSETTING_WHERE_PKS_IN);

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

			for (PluginSetting pluginSetting : (List<PluginSetting>)q.list()) {
				map.put(pluginSetting.getPrimaryKeyObj(), pluginSetting);

				cacheResult(pluginSetting);

				uncachedPrimaryKeys.remove(pluginSetting.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
					PluginSettingImpl.class, primaryKey, _nullPluginSetting);
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
	 * Returns all the plugin settings.
	 *
	 * @return the plugin settings
	 */
	@Override
	public List<PluginSetting> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the plugin settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PluginSettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of plugin settings
	 * @param end the upper bound of the range of plugin settings (not inclusive)
	 * @return the range of plugin settings
	 */
	@Override
	public List<PluginSetting> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the plugin settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PluginSettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of plugin settings
	 * @param end the upper bound of the range of plugin settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of plugin settings
	 */
	@Override
	public List<PluginSetting> findAll(int start, int end,
		OrderByComparator<PluginSetting> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the plugin settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PluginSettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of plugin settings
	 * @param end the upper bound of the range of plugin settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of plugin settings
	 */
	@Override
	public List<PluginSetting> findAll(int start, int end,
		OrderByComparator<PluginSetting> orderByComparator,
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

		List<PluginSetting> list = null;

		if (retrieveFromCache) {
			list = (List<PluginSetting>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_PLUGINSETTING);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_PLUGINSETTING;

				if (pagination) {
					sql = sql.concat(PluginSettingModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<PluginSetting>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PluginSetting>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the plugin settings from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (PluginSetting pluginSetting : findAll()) {
			remove(pluginSetting);
		}
	}

	/**
	 * Returns the number of plugin settings.
	 *
	 * @return the number of plugin settings
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_PLUGINSETTING);

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
		return PluginSettingModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the plugin setting persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(PluginSettingImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_PLUGINSETTING = "SELECT pluginSetting FROM PluginSetting pluginSetting";
	private static final String _SQL_SELECT_PLUGINSETTING_WHERE_PKS_IN = "SELECT pluginSetting FROM PluginSetting pluginSetting WHERE pluginSettingId IN (";
	private static final String _SQL_SELECT_PLUGINSETTING_WHERE = "SELECT pluginSetting FROM PluginSetting pluginSetting WHERE ";
	private static final String _SQL_COUNT_PLUGINSETTING = "SELECT COUNT(pluginSetting) FROM PluginSetting pluginSetting";
	private static final String _SQL_COUNT_PLUGINSETTING_WHERE = "SELECT COUNT(pluginSetting) FROM PluginSetting pluginSetting WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "pluginSetting.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No PluginSetting exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No PluginSetting exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(PluginSettingPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"active"
			});
	private static final PluginSetting _nullPluginSetting = new PluginSettingImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<PluginSetting> toCacheModel() {
				return _nullPluginSettingCacheModel;
			}
		};

	private static final CacheModel<PluginSetting> _nullPluginSettingCacheModel = new NullCacheModel();

	private static class NullCacheModel implements CacheModel<PluginSetting>,
		MVCCModel {
		@Override
		public long getMvccVersion() {
			return -1;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
		}

		@Override
		public PluginSetting toEntityModel() {
			return _nullPluginSetting;
		}
	}
}