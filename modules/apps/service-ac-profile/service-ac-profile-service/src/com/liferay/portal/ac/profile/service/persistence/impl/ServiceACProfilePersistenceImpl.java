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

package com.liferay.portal.ac.profile.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.ac.profile.exception.NoSuchServiceACProfileException;
import com.liferay.portal.ac.profile.model.ServiceACProfile;
import com.liferay.portal.ac.profile.model.impl.ServiceACProfileImpl;
import com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl;
import com.liferay.portal.ac.profile.service.persistence.ServiceACProfilePersistence;
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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the service a c profile service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ServiceACProfilePersistence
 * @see com.liferay.portal.ac.profile.service.persistence.ServiceACProfileUtil
 * @generated
 */
@ProviderType
public class ServiceACProfilePersistenceImpl extends BasePersistenceImpl<ServiceACProfile>
	implements ServiceACProfilePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ServiceACProfileUtil} to access the service a c profile persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ServiceACProfileImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ServiceACProfileModelImpl.ENTITY_CACHE_ENABLED,
			ServiceACProfileModelImpl.FINDER_CACHE_ENABLED,
			ServiceACProfileImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ServiceACProfileModelImpl.ENTITY_CACHE_ENABLED,
			ServiceACProfileModelImpl.FINDER_CACHE_ENABLED,
			ServiceACProfileImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ServiceACProfileModelImpl.ENTITY_CACHE_ENABLED,
			ServiceACProfileModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(ServiceACProfileModelImpl.ENTITY_CACHE_ENABLED,
			ServiceACProfileModelImpl.FINDER_CACHE_ENABLED,
			ServiceACProfileImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(ServiceACProfileModelImpl.ENTITY_CACHE_ENABLED,
			ServiceACProfileModelImpl.FINDER_CACHE_ENABLED,
			ServiceACProfileImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			ServiceACProfileModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(ServiceACProfileModelImpl.ENTITY_CACHE_ENABLED,
			ServiceACProfileModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the service a c profiles where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching service a c profiles
	 */
	@Override
	public List<ServiceACProfile> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the service a c profiles where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of service a c profiles
	 * @param end the upper bound of the range of service a c profiles (not inclusive)
	 * @return the range of matching service a c profiles
	 */
	@Override
	public List<ServiceACProfile> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the service a c profiles where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of service a c profiles
	 * @param end the upper bound of the range of service a c profiles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching service a c profiles
	 */
	@Override
	public List<ServiceACProfile> findByUuid(String uuid, int start, int end,
		OrderByComparator<ServiceACProfile> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<ServiceACProfile> list = (List<ServiceACProfile>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ServiceACProfile serviceACProfile : list) {
				if (!Validator.equals(uuid, serviceACProfile.getUuid())) {
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

			query.append(_SQL_SELECT_SERVICEACPROFILE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ServiceACProfileModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				if (!pagination) {
					list = (List<ServiceACProfile>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ServiceACProfile>)QueryUtil.list(q,
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
	 * Returns the first service a c profile in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching service a c profile
	 * @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a matching service a c profile could not be found
	 */
	@Override
	public ServiceACProfile findByUuid_First(String uuid,
		OrderByComparator<ServiceACProfile> orderByComparator)
		throws NoSuchServiceACProfileException {
		ServiceACProfile serviceACProfile = fetchByUuid_First(uuid,
				orderByComparator);

		if (serviceACProfile != null) {
			return serviceACProfile;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchServiceACProfileException(msg.toString());
	}

	/**
	 * Returns the first service a c profile in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching service a c profile, or <code>null</code> if a matching service a c profile could not be found
	 */
	@Override
	public ServiceACProfile fetchByUuid_First(String uuid,
		OrderByComparator<ServiceACProfile> orderByComparator) {
		List<ServiceACProfile> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last service a c profile in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching service a c profile
	 * @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a matching service a c profile could not be found
	 */
	@Override
	public ServiceACProfile findByUuid_Last(String uuid,
		OrderByComparator<ServiceACProfile> orderByComparator)
		throws NoSuchServiceACProfileException {
		ServiceACProfile serviceACProfile = fetchByUuid_Last(uuid,
				orderByComparator);

		if (serviceACProfile != null) {
			return serviceACProfile;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchServiceACProfileException(msg.toString());
	}

	/**
	 * Returns the last service a c profile in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching service a c profile, or <code>null</code> if a matching service a c profile could not be found
	 */
	@Override
	public ServiceACProfile fetchByUuid_Last(String uuid,
		OrderByComparator<ServiceACProfile> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<ServiceACProfile> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the service a c profiles before and after the current service a c profile in the ordered set where uuid = &#63;.
	 *
	 * @param serviceACProfileId the primary key of the current service a c profile
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next service a c profile
	 * @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a service a c profile with the primary key could not be found
	 */
	@Override
	public ServiceACProfile[] findByUuid_PrevAndNext(long serviceACProfileId,
		String uuid, OrderByComparator<ServiceACProfile> orderByComparator)
		throws NoSuchServiceACProfileException {
		ServiceACProfile serviceACProfile = findByPrimaryKey(serviceACProfileId);

		Session session = null;

		try {
			session = openSession();

			ServiceACProfile[] array = new ServiceACProfileImpl[3];

			array[0] = getByUuid_PrevAndNext(session, serviceACProfile, uuid,
					orderByComparator, true);

			array[1] = serviceACProfile;

			array[2] = getByUuid_PrevAndNext(session, serviceACProfile, uuid,
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

	protected ServiceACProfile getByUuid_PrevAndNext(Session session,
		ServiceACProfile serviceACProfile, String uuid,
		OrderByComparator<ServiceACProfile> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SERVICEACPROFILE_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
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
			query.append(ServiceACProfileModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(serviceACProfile);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ServiceACProfile> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the service a c profiles where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (ServiceACProfile serviceACProfile : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(serviceACProfile);
		}
	}

	/**
	 * Returns the number of service a c profiles where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching service a c profiles
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SERVICEACPROFILE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "serviceACProfile.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "serviceACProfile.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(serviceACProfile.uuid IS NULL OR serviceACProfile.uuid = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(ServiceACProfileModelImpl.ENTITY_CACHE_ENABLED,
			ServiceACProfileModelImpl.FINDER_CACHE_ENABLED,
			ServiceACProfileImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(ServiceACProfileModelImpl.ENTITY_CACHE_ENABLED,
			ServiceACProfileModelImpl.FINDER_CACHE_ENABLED,
			ServiceACProfileImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			ServiceACProfileModelImpl.UUID_COLUMN_BITMASK |
			ServiceACProfileModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(ServiceACProfileModelImpl.ENTITY_CACHE_ENABLED,
			ServiceACProfileModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the service a c profiles where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching service a c profiles
	 */
	@Override
	public List<ServiceACProfile> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the service a c profiles where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of service a c profiles
	 * @param end the upper bound of the range of service a c profiles (not inclusive)
	 * @return the range of matching service a c profiles
	 */
	@Override
	public List<ServiceACProfile> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the service a c profiles where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of service a c profiles
	 * @param end the upper bound of the range of service a c profiles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching service a c profiles
	 */
	@Override
	public List<ServiceACProfile> findByUuid_C(String uuid, long companyId,
		int start, int end,
		OrderByComparator<ServiceACProfile> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] { uuid, companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] {
					uuid, companyId,
					
					start, end, orderByComparator
				};
		}

		List<ServiceACProfile> list = (List<ServiceACProfile>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ServiceACProfile serviceACProfile : list) {
				if (!Validator.equals(uuid, serviceACProfile.getUuid()) ||
						(companyId != serviceACProfile.getCompanyId())) {
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

			query.append(_SQL_SELECT_SERVICEACPROFILE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ServiceACProfileModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				if (!pagination) {
					list = (List<ServiceACProfile>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ServiceACProfile>)QueryUtil.list(q,
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
	 * Returns the first service a c profile in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching service a c profile
	 * @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a matching service a c profile could not be found
	 */
	@Override
	public ServiceACProfile findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<ServiceACProfile> orderByComparator)
		throws NoSuchServiceACProfileException {
		ServiceACProfile serviceACProfile = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (serviceACProfile != null) {
			return serviceACProfile;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchServiceACProfileException(msg.toString());
	}

	/**
	 * Returns the first service a c profile in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching service a c profile, or <code>null</code> if a matching service a c profile could not be found
	 */
	@Override
	public ServiceACProfile fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<ServiceACProfile> orderByComparator) {
		List<ServiceACProfile> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last service a c profile in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching service a c profile
	 * @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a matching service a c profile could not be found
	 */
	@Override
	public ServiceACProfile findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<ServiceACProfile> orderByComparator)
		throws NoSuchServiceACProfileException {
		ServiceACProfile serviceACProfile = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (serviceACProfile != null) {
			return serviceACProfile;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchServiceACProfileException(msg.toString());
	}

	/**
	 * Returns the last service a c profile in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching service a c profile, or <code>null</code> if a matching service a c profile could not be found
	 */
	@Override
	public ServiceACProfile fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<ServiceACProfile> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<ServiceACProfile> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the service a c profiles before and after the current service a c profile in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param serviceACProfileId the primary key of the current service a c profile
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next service a c profile
	 * @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a service a c profile with the primary key could not be found
	 */
	@Override
	public ServiceACProfile[] findByUuid_C_PrevAndNext(
		long serviceACProfileId, String uuid, long companyId,
		OrderByComparator<ServiceACProfile> orderByComparator)
		throws NoSuchServiceACProfileException {
		ServiceACProfile serviceACProfile = findByPrimaryKey(serviceACProfileId);

		Session session = null;

		try {
			session = openSession();

			ServiceACProfile[] array = new ServiceACProfileImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, serviceACProfile, uuid,
					companyId, orderByComparator, true);

			array[1] = serviceACProfile;

			array[2] = getByUuid_C_PrevAndNext(session, serviceACProfile, uuid,
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

	protected ServiceACProfile getByUuid_C_PrevAndNext(Session session,
		ServiceACProfile serviceACProfile, String uuid, long companyId,
		OrderByComparator<ServiceACProfile> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SERVICEACPROFILE_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

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
			query.append(ServiceACProfileModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(serviceACProfile);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ServiceACProfile> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the service a c profiles where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (ServiceACProfile serviceACProfile : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(serviceACProfile);
		}
	}

	/**
	 * Returns the number of service a c profiles where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching service a c profiles
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SERVICEACPROFILE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "serviceACProfile.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "serviceACProfile.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(serviceACProfile.uuid IS NULL OR serviceACProfile.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "serviceACProfile.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(ServiceACProfileModelImpl.ENTITY_CACHE_ENABLED,
			ServiceACProfileModelImpl.FINDER_CACHE_ENABLED,
			ServiceACProfileImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(ServiceACProfileModelImpl.ENTITY_CACHE_ENABLED,
			ServiceACProfileModelImpl.FINDER_CACHE_ENABLED,
			ServiceACProfileImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			ServiceACProfileModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(ServiceACProfileModelImpl.ENTITY_CACHE_ENABLED,
			ServiceACProfileModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the service a c profiles where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching service a c profiles
	 */
	@Override
	public List<ServiceACProfile> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the service a c profiles where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of service a c profiles
	 * @param end the upper bound of the range of service a c profiles (not inclusive)
	 * @return the range of matching service a c profiles
	 */
	@Override
	public List<ServiceACProfile> findByCompanyId(long companyId, int start,
		int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the service a c profiles where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of service a c profiles
	 * @param end the upper bound of the range of service a c profiles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching service a c profiles
	 */
	@Override
	public List<ServiceACProfile> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<ServiceACProfile> orderByComparator) {
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

		List<ServiceACProfile> list = (List<ServiceACProfile>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ServiceACProfile serviceACProfile : list) {
				if ((companyId != serviceACProfile.getCompanyId())) {
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

			query.append(_SQL_SELECT_SERVICEACPROFILE_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ServiceACProfileModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<ServiceACProfile>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ServiceACProfile>)QueryUtil.list(q,
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
	 * Returns the first service a c profile in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching service a c profile
	 * @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a matching service a c profile could not be found
	 */
	@Override
	public ServiceACProfile findByCompanyId_First(long companyId,
		OrderByComparator<ServiceACProfile> orderByComparator)
		throws NoSuchServiceACProfileException {
		ServiceACProfile serviceACProfile = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (serviceACProfile != null) {
			return serviceACProfile;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchServiceACProfileException(msg.toString());
	}

	/**
	 * Returns the first service a c profile in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching service a c profile, or <code>null</code> if a matching service a c profile could not be found
	 */
	@Override
	public ServiceACProfile fetchByCompanyId_First(long companyId,
		OrderByComparator<ServiceACProfile> orderByComparator) {
		List<ServiceACProfile> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last service a c profile in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching service a c profile
	 * @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a matching service a c profile could not be found
	 */
	@Override
	public ServiceACProfile findByCompanyId_Last(long companyId,
		OrderByComparator<ServiceACProfile> orderByComparator)
		throws NoSuchServiceACProfileException {
		ServiceACProfile serviceACProfile = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (serviceACProfile != null) {
			return serviceACProfile;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchServiceACProfileException(msg.toString());
	}

	/**
	 * Returns the last service a c profile in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching service a c profile, or <code>null</code> if a matching service a c profile could not be found
	 */
	@Override
	public ServiceACProfile fetchByCompanyId_Last(long companyId,
		OrderByComparator<ServiceACProfile> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<ServiceACProfile> list = findByCompanyId(companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the service a c profiles before and after the current service a c profile in the ordered set where companyId = &#63;.
	 *
	 * @param serviceACProfileId the primary key of the current service a c profile
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next service a c profile
	 * @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a service a c profile with the primary key could not be found
	 */
	@Override
	public ServiceACProfile[] findByCompanyId_PrevAndNext(
		long serviceACProfileId, long companyId,
		OrderByComparator<ServiceACProfile> orderByComparator)
		throws NoSuchServiceACProfileException {
		ServiceACProfile serviceACProfile = findByPrimaryKey(serviceACProfileId);

		Session session = null;

		try {
			session = openSession();

			ServiceACProfile[] array = new ServiceACProfileImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, serviceACProfile,
					companyId, orderByComparator, true);

			array[1] = serviceACProfile;

			array[2] = getByCompanyId_PrevAndNext(session, serviceACProfile,
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

	protected ServiceACProfile getByCompanyId_PrevAndNext(Session session,
		ServiceACProfile serviceACProfile, long companyId,
		OrderByComparator<ServiceACProfile> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SERVICEACPROFILE_WHERE);

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
			query.append(ServiceACProfileModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(serviceACProfile);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ServiceACProfile> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the service a c profiles where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (ServiceACProfile serviceACProfile : findByCompanyId(companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(serviceACProfile);
		}
	}

	/**
	 * Returns the number of service a c profiles where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching service a c profiles
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SERVICEACPROFILE_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "serviceACProfile.companyId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_N = new FinderPath(ServiceACProfileModelImpl.ENTITY_CACHE_ENABLED,
			ServiceACProfileModelImpl.FINDER_CACHE_ENABLED,
			ServiceACProfileImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByC_N",
			new String[] { Long.class.getName(), String.class.getName() },
			ServiceACProfileModelImpl.COMPANYID_COLUMN_BITMASK |
			ServiceACProfileModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_N = new FinderPath(ServiceACProfileModelImpl.ENTITY_CACHE_ENABLED,
			ServiceACProfileModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the service a c profile where companyId = &#63; and name = &#63; or throws a {@link com.liferay.portal.ac.profile.NoSuchServiceACProfileException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching service a c profile
	 * @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a matching service a c profile could not be found
	 */
	@Override
	public ServiceACProfile findByC_N(long companyId, String name)
		throws NoSuchServiceACProfileException {
		ServiceACProfile serviceACProfile = fetchByC_N(companyId, name);

		if (serviceACProfile == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchServiceACProfileException(msg.toString());
		}

		return serviceACProfile;
	}

	/**
	 * Returns the service a c profile where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching service a c profile, or <code>null</code> if a matching service a c profile could not be found
	 */
	@Override
	public ServiceACProfile fetchByC_N(long companyId, String name) {
		return fetchByC_N(companyId, name, true);
	}

	/**
	 * Returns the service a c profile where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching service a c profile, or <code>null</code> if a matching service a c profile could not be found
	 */
	@Override
	public ServiceACProfile fetchByC_N(long companyId, String name,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { companyId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_N,
					finderArgs, this);
		}

		if (result instanceof ServiceACProfile) {
			ServiceACProfile serviceACProfile = (ServiceACProfile)result;

			if ((companyId != serviceACProfile.getCompanyId()) ||
					!Validator.equals(name, serviceACProfile.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SERVICEACPROFILE_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindName) {
					qPos.add(name);
				}

				List<ServiceACProfile> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"ServiceACProfilePersistenceImpl.fetchByC_N(long, String, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					ServiceACProfile serviceACProfile = list.get(0);

					result = serviceACProfile;

					cacheResult(serviceACProfile);

					if ((serviceACProfile.getCompanyId() != companyId) ||
							(serviceACProfile.getName() == null) ||
							!serviceACProfile.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
							finderArgs, serviceACProfile);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N,
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
			return (ServiceACProfile)result;
		}
	}

	/**
	 * Removes the service a c profile where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the service a c profile that was removed
	 */
	@Override
	public ServiceACProfile removeByC_N(long companyId, String name)
		throws NoSuchServiceACProfileException {
		ServiceACProfile serviceACProfile = findByC_N(companyId, name);

		return remove(serviceACProfile);
	}

	/**
	 * Returns the number of service a c profiles where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching service a c profiles
	 */
	@Override
	public int countByC_N(long companyId, String name) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_N;

		Object[] finderArgs = new Object[] { companyId, name };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SERVICEACPROFILE_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindName) {
					qPos.add(name);
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

	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 = "serviceACProfile.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_N_NAME_1 = "serviceACProfile.name IS NULL";
	private static final String _FINDER_COLUMN_C_N_NAME_2 = "serviceACProfile.name = ?";
	private static final String _FINDER_COLUMN_C_N_NAME_3 = "(serviceACProfile.name IS NULL OR serviceACProfile.name = '')";

	public ServiceACProfilePersistenceImpl() {
		setModelClass(ServiceACProfile.class);
	}

	/**
	 * Caches the service a c profile in the entity cache if it is enabled.
	 *
	 * @param serviceACProfile the service a c profile
	 */
	@Override
	public void cacheResult(ServiceACProfile serviceACProfile) {
		EntityCacheUtil.putResult(ServiceACProfileModelImpl.ENTITY_CACHE_ENABLED,
			ServiceACProfileImpl.class, serviceACProfile.getPrimaryKey(),
			serviceACProfile);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
			new Object[] {
				serviceACProfile.getCompanyId(), serviceACProfile.getName()
			}, serviceACProfile);

		serviceACProfile.resetOriginalValues();
	}

	/**
	 * Caches the service a c profiles in the entity cache if it is enabled.
	 *
	 * @param serviceACProfiles the service a c profiles
	 */
	@Override
	public void cacheResult(List<ServiceACProfile> serviceACProfiles) {
		for (ServiceACProfile serviceACProfile : serviceACProfiles) {
			if (EntityCacheUtil.getResult(
						ServiceACProfileModelImpl.ENTITY_CACHE_ENABLED,
						ServiceACProfileImpl.class,
						serviceACProfile.getPrimaryKey()) == null) {
				cacheResult(serviceACProfile);
			}
			else {
				serviceACProfile.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all service a c profiles.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(ServiceACProfileImpl.class.getName());
		}

		EntityCacheUtil.clearCache(ServiceACProfileImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the service a c profile.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ServiceACProfile serviceACProfile) {
		EntityCacheUtil.removeResult(ServiceACProfileModelImpl.ENTITY_CACHE_ENABLED,
			ServiceACProfileImpl.class, serviceACProfile.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(serviceACProfile);
	}

	@Override
	public void clearCache(List<ServiceACProfile> serviceACProfiles) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ServiceACProfile serviceACProfile : serviceACProfiles) {
			EntityCacheUtil.removeResult(ServiceACProfileModelImpl.ENTITY_CACHE_ENABLED,
				ServiceACProfileImpl.class, serviceACProfile.getPrimaryKey());

			clearUniqueFindersCache(serviceACProfile);
		}
	}

	protected void cacheUniqueFindersCache(ServiceACProfile serviceACProfile) {
		if (serviceACProfile.isNew()) {
			Object[] args = new Object[] {
					serviceACProfile.getCompanyId(), serviceACProfile.getName()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_N, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N, args,
				serviceACProfile);
		}
		else {
			ServiceACProfileModelImpl serviceACProfileModelImpl = (ServiceACProfileModelImpl)serviceACProfile;

			if ((serviceACProfileModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						serviceACProfile.getCompanyId(),
						serviceACProfile.getName()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_N, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N, args,
					serviceACProfile);
			}
		}
	}

	protected void clearUniqueFindersCache(ServiceACProfile serviceACProfile) {
		ServiceACProfileModelImpl serviceACProfileModelImpl = (ServiceACProfileModelImpl)serviceACProfile;

		Object[] args = new Object[] {
				serviceACProfile.getCompanyId(), serviceACProfile.getName()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_N, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N, args);

		if ((serviceACProfileModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_N.getColumnBitmask()) != 0) {
			args = new Object[] {
					serviceACProfileModelImpl.getOriginalCompanyId(),
					serviceACProfileModelImpl.getOriginalName()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_N, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N, args);
		}
	}

	/**
	 * Creates a new service a c profile with the primary key. Does not add the service a c profile to the database.
	 *
	 * @param serviceACProfileId the primary key for the new service a c profile
	 * @return the new service a c profile
	 */
	@Override
	public ServiceACProfile create(long serviceACProfileId) {
		ServiceACProfile serviceACProfile = new ServiceACProfileImpl();

		serviceACProfile.setNew(true);
		serviceACProfile.setPrimaryKey(serviceACProfileId);

		String uuid = PortalUUIDUtil.generate();

		serviceACProfile.setUuid(uuid);

		return serviceACProfile;
	}

	/**
	 * Removes the service a c profile with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param serviceACProfileId the primary key of the service a c profile
	 * @return the service a c profile that was removed
	 * @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a service a c profile with the primary key could not be found
	 */
	@Override
	public ServiceACProfile remove(long serviceACProfileId)
		throws NoSuchServiceACProfileException {
		return remove((Serializable)serviceACProfileId);
	}

	/**
	 * Removes the service a c profile with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the service a c profile
	 * @return the service a c profile that was removed
	 * @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a service a c profile with the primary key could not be found
	 */
	@Override
	public ServiceACProfile remove(Serializable primaryKey)
		throws NoSuchServiceACProfileException {
		Session session = null;

		try {
			session = openSession();

			ServiceACProfile serviceACProfile = (ServiceACProfile)session.get(ServiceACProfileImpl.class,
					primaryKey);

			if (serviceACProfile == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchServiceACProfileException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(serviceACProfile);
		}
		catch (NoSuchServiceACProfileException nsee) {
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
	protected ServiceACProfile removeImpl(ServiceACProfile serviceACProfile) {
		serviceACProfile = toUnwrappedModel(serviceACProfile);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(serviceACProfile)) {
				serviceACProfile = (ServiceACProfile)session.get(ServiceACProfileImpl.class,
						serviceACProfile.getPrimaryKeyObj());
			}

			if (serviceACProfile != null) {
				session.delete(serviceACProfile);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (serviceACProfile != null) {
			clearCache(serviceACProfile);
		}

		return serviceACProfile;
	}

	@Override
	public ServiceACProfile updateImpl(
		com.liferay.portal.ac.profile.model.ServiceACProfile serviceACProfile) {
		serviceACProfile = toUnwrappedModel(serviceACProfile);

		boolean isNew = serviceACProfile.isNew();

		ServiceACProfileModelImpl serviceACProfileModelImpl = (ServiceACProfileModelImpl)serviceACProfile;

		if (Validator.isNull(serviceACProfile.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			serviceACProfile.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (serviceACProfile.isNew()) {
				session.save(serviceACProfile);

				serviceACProfile.setNew(false);
			}
			else {
				session.merge(serviceACProfile);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !ServiceACProfileModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((serviceACProfileModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						serviceACProfileModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { serviceACProfileModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((serviceACProfileModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						serviceACProfileModelImpl.getOriginalUuid(),
						serviceACProfileModelImpl.getOriginalCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						serviceACProfileModelImpl.getUuid(),
						serviceACProfileModelImpl.getCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((serviceACProfileModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						serviceACProfileModelImpl.getOriginalCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { serviceACProfileModelImpl.getCompanyId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}
		}

		EntityCacheUtil.putResult(ServiceACProfileModelImpl.ENTITY_CACHE_ENABLED,
			ServiceACProfileImpl.class, serviceACProfile.getPrimaryKey(),
			serviceACProfile, false);

		clearUniqueFindersCache(serviceACProfile);
		cacheUniqueFindersCache(serviceACProfile);

		serviceACProfile.resetOriginalValues();

		return serviceACProfile;
	}

	protected ServiceACProfile toUnwrappedModel(
		ServiceACProfile serviceACProfile) {
		if (serviceACProfile instanceof ServiceACProfileImpl) {
			return serviceACProfile;
		}

		ServiceACProfileImpl serviceACProfileImpl = new ServiceACProfileImpl();

		serviceACProfileImpl.setNew(serviceACProfile.isNew());
		serviceACProfileImpl.setPrimaryKey(serviceACProfile.getPrimaryKey());

		serviceACProfileImpl.setUuid(serviceACProfile.getUuid());
		serviceACProfileImpl.setServiceACProfileId(serviceACProfile.getServiceACProfileId());
		serviceACProfileImpl.setCompanyId(serviceACProfile.getCompanyId());
		serviceACProfileImpl.setUserId(serviceACProfile.getUserId());
		serviceACProfileImpl.setUserName(serviceACProfile.getUserName());
		serviceACProfileImpl.setCreateDate(serviceACProfile.getCreateDate());
		serviceACProfileImpl.setModifiedDate(serviceACProfile.getModifiedDate());
		serviceACProfileImpl.setAllowedServices(serviceACProfile.getAllowedServices());
		serviceACProfileImpl.setName(serviceACProfile.getName());
		serviceACProfileImpl.setTitle(serviceACProfile.getTitle());

		return serviceACProfileImpl;
	}

	/**
	 * Returns the service a c profile with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the service a c profile
	 * @return the service a c profile
	 * @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a service a c profile with the primary key could not be found
	 */
	@Override
	public ServiceACProfile findByPrimaryKey(Serializable primaryKey)
		throws NoSuchServiceACProfileException {
		ServiceACProfile serviceACProfile = fetchByPrimaryKey(primaryKey);

		if (serviceACProfile == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchServiceACProfileException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return serviceACProfile;
	}

	/**
	 * Returns the service a c profile with the primary key or throws a {@link com.liferay.portal.ac.profile.NoSuchServiceACProfileException} if it could not be found.
	 *
	 * @param serviceACProfileId the primary key of the service a c profile
	 * @return the service a c profile
	 * @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a service a c profile with the primary key could not be found
	 */
	@Override
	public ServiceACProfile findByPrimaryKey(long serviceACProfileId)
		throws NoSuchServiceACProfileException {
		return findByPrimaryKey((Serializable)serviceACProfileId);
	}

	/**
	 * Returns the service a c profile with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the service a c profile
	 * @return the service a c profile, or <code>null</code> if a service a c profile with the primary key could not be found
	 */
	@Override
	public ServiceACProfile fetchByPrimaryKey(Serializable primaryKey) {
		ServiceACProfile serviceACProfile = (ServiceACProfile)EntityCacheUtil.getResult(ServiceACProfileModelImpl.ENTITY_CACHE_ENABLED,
				ServiceACProfileImpl.class, primaryKey);

		if (serviceACProfile == _nullServiceACProfile) {
			return null;
		}

		if (serviceACProfile == null) {
			Session session = null;

			try {
				session = openSession();

				serviceACProfile = (ServiceACProfile)session.get(ServiceACProfileImpl.class,
						primaryKey);

				if (serviceACProfile != null) {
					cacheResult(serviceACProfile);
				}
				else {
					EntityCacheUtil.putResult(ServiceACProfileModelImpl.ENTITY_CACHE_ENABLED,
						ServiceACProfileImpl.class, primaryKey,
						_nullServiceACProfile);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(ServiceACProfileModelImpl.ENTITY_CACHE_ENABLED,
					ServiceACProfileImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return serviceACProfile;
	}

	/**
	 * Returns the service a c profile with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param serviceACProfileId the primary key of the service a c profile
	 * @return the service a c profile, or <code>null</code> if a service a c profile with the primary key could not be found
	 */
	@Override
	public ServiceACProfile fetchByPrimaryKey(long serviceACProfileId) {
		return fetchByPrimaryKey((Serializable)serviceACProfileId);
	}

	@Override
	public Map<Serializable, ServiceACProfile> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ServiceACProfile> map = new HashMap<Serializable, ServiceACProfile>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ServiceACProfile serviceACProfile = fetchByPrimaryKey(primaryKey);

			if (serviceACProfile != null) {
				map.put(primaryKey, serviceACProfile);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			ServiceACProfile serviceACProfile = (ServiceACProfile)EntityCacheUtil.getResult(ServiceACProfileModelImpl.ENTITY_CACHE_ENABLED,
					ServiceACProfileImpl.class, primaryKey);

			if (serviceACProfile == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, serviceACProfile);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_SERVICEACPROFILE_WHERE_PKS_IN);

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

			for (ServiceACProfile serviceACProfile : (List<ServiceACProfile>)q.list()) {
				map.put(serviceACProfile.getPrimaryKeyObj(), serviceACProfile);

				cacheResult(serviceACProfile);

				uncachedPrimaryKeys.remove(serviceACProfile.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				EntityCacheUtil.putResult(ServiceACProfileModelImpl.ENTITY_CACHE_ENABLED,
					ServiceACProfileImpl.class, primaryKey,
					_nullServiceACProfile);
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
	 * Returns all the service a c profiles.
	 *
	 * @return the service a c profiles
	 */
	@Override
	public List<ServiceACProfile> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the service a c profiles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of service a c profiles
	 * @param end the upper bound of the range of service a c profiles (not inclusive)
	 * @return the range of service a c profiles
	 */
	@Override
	public List<ServiceACProfile> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the service a c profiles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of service a c profiles
	 * @param end the upper bound of the range of service a c profiles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of service a c profiles
	 */
	@Override
	public List<ServiceACProfile> findAll(int start, int end,
		OrderByComparator<ServiceACProfile> orderByComparator) {
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

		List<ServiceACProfile> list = (List<ServiceACProfile>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SERVICEACPROFILE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SERVICEACPROFILE;

				if (pagination) {
					sql = sql.concat(ServiceACProfileModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<ServiceACProfile>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ServiceACProfile>)QueryUtil.list(q,
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
	 * Removes all the service a c profiles from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ServiceACProfile serviceACProfile : findAll()) {
			remove(serviceACProfile);
		}
	}

	/**
	 * Returns the number of service a c profiles.
	 *
	 * @return the number of service a c profiles
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SERVICEACPROFILE);

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
	 * Initializes the service a c profile persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ServiceACProfileImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_SERVICEACPROFILE = "SELECT serviceACProfile FROM ServiceACProfile serviceACProfile";
	private static final String _SQL_SELECT_SERVICEACPROFILE_WHERE_PKS_IN = "SELECT serviceACProfile FROM ServiceACProfile serviceACProfile WHERE serviceACProfileId IN (";
	private static final String _SQL_SELECT_SERVICEACPROFILE_WHERE = "SELECT serviceACProfile FROM ServiceACProfile serviceACProfile WHERE ";
	private static final String _SQL_COUNT_SERVICEACPROFILE = "SELECT COUNT(serviceACProfile) FROM ServiceACProfile serviceACProfile";
	private static final String _SQL_COUNT_SERVICEACPROFILE_WHERE = "SELECT COUNT(serviceACProfile) FROM ServiceACProfile serviceACProfile WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "serviceACProfile.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ServiceACProfile exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ServiceACProfile exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static final Log _log = LogFactoryUtil.getLog(ServiceACProfilePersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
	private static final ServiceACProfile _nullServiceACProfile = new ServiceACProfileImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<ServiceACProfile> toCacheModel() {
				return _nullServiceACProfileCacheModel;
			}
		};

	private static final CacheModel<ServiceACProfile> _nullServiceACProfileCacheModel =
		new CacheModel<ServiceACProfile>() {
			@Override
			public ServiceACProfile toEntityModel() {
				return _nullServiceACProfile;
			}
		};
}