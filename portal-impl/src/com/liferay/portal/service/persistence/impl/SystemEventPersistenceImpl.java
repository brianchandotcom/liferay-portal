/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchSystemEventException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.SystemEvent;
import com.liferay.portal.kernel.model.SystemEventTable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.SystemEventPersistence;
import com.liferay.portal.kernel.service.persistence.SystemEventUtil;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelperUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.CollectionPersistenceFinder;
import com.liferay.portal.kernel.service.persistence.impl.FinderColumn;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.model.impl.SystemEventImpl;
import com.liferay.portal.model.impl.SystemEventModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the system event service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SystemEventPersistenceImpl
	extends BasePersistenceImpl<SystemEvent, NoSuchSystemEventException>
	implements SystemEventPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SystemEventUtil</code> to access the system event persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SystemEventImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;
	private CollectionPersistenceFinder<SystemEvent>
		_collectionPersistenceFinderByGroupId;

	/**
	 * Returns all the system events where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching system events
	 */
	@Override
	public List<SystemEvent> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the system events where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @return the range of matching system events
	 */
	@Override
	public List<SystemEvent> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the system events where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching system events
	 */
	@Override
	public List<SystemEvent> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SystemEvent> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the system events where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching system events
	 */
	@Override
	public List<SystemEvent> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SystemEvent> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					SystemEvent.class)) {

			return _collectionPersistenceFinderByGroupId.find(
				FinderCacheUtil.getFinderCache(), new Object[] {groupId}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first system event in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching system event
	 * @throws NoSuchSystemEventException if a matching system event could not be found
	 */
	@Override
	public SystemEvent findByGroupId_First(
			long groupId, OrderByComparator<SystemEvent> orderByComparator)
		throws NoSuchSystemEventException {

		SystemEvent systemEvent = fetchByGroupId_First(
			groupId, orderByComparator);

		if (systemEvent != null) {
			return systemEvent;
		}

		throw new NoSuchSystemEventException(
			_collectionPersistenceFinderByGroupId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId}));
	}

	/**
	 * Returns the first system event in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching system event, or <code>null</code> if a matching system event could not be found
	 */
	@Override
	public SystemEvent fetchByGroupId_First(
		long groupId, OrderByComparator<SystemEvent> orderByComparator) {

		return _collectionPersistenceFinderByGroupId.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {groupId},
			orderByComparator);
	}

	/**
	 * Removes all the system events where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		_collectionPersistenceFinderByGroupId.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {groupId});
	}

	/**
	 * Returns the number of system events where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching system events
	 */
	@Override
	public int countByGroupId(long groupId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					SystemEvent.class)) {

			return _collectionPersistenceFinderByGroupId.count(
				FinderCacheUtil.getFinderCache(), new Object[] {groupId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_S;
	private FinderPath _finderPathWithoutPaginationFindByG_S;
	private FinderPath _finderPathCountByG_S;
	private CollectionPersistenceFinder<SystemEvent>
		_collectionPersistenceFinderByG_S;

	/**
	 * Returns all the system events where groupId = &#63; and systemEventSetKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param systemEventSetKey the system event set key
	 * @return the matching system events
	 */
	@Override
	public List<SystemEvent> findByG_S(long groupId, long systemEventSetKey) {
		return findByG_S(
			groupId, systemEventSetKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the system events where groupId = &#63; and systemEventSetKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param systemEventSetKey the system event set key
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @return the range of matching system events
	 */
	@Override
	public List<SystemEvent> findByG_S(
		long groupId, long systemEventSetKey, int start, int end) {

		return findByG_S(groupId, systemEventSetKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the system events where groupId = &#63; and systemEventSetKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param systemEventSetKey the system event set key
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching system events
	 */
	@Override
	public List<SystemEvent> findByG_S(
		long groupId, long systemEventSetKey, int start, int end,
		OrderByComparator<SystemEvent> orderByComparator) {

		return findByG_S(
			groupId, systemEventSetKey, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the system events where groupId = &#63; and systemEventSetKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param systemEventSetKey the system event set key
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching system events
	 */
	@Override
	public List<SystemEvent> findByG_S(
		long groupId, long systemEventSetKey, int start, int end,
		OrderByComparator<SystemEvent> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					SystemEvent.class)) {

			return _collectionPersistenceFinderByG_S.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {groupId, systemEventSetKey}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first system event in the ordered set where groupId = &#63; and systemEventSetKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param systemEventSetKey the system event set key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching system event
	 * @throws NoSuchSystemEventException if a matching system event could not be found
	 */
	@Override
	public SystemEvent findByG_S_First(
			long groupId, long systemEventSetKey,
			OrderByComparator<SystemEvent> orderByComparator)
		throws NoSuchSystemEventException {

		SystemEvent systemEvent = fetchByG_S_First(
			groupId, systemEventSetKey, orderByComparator);

		if (systemEvent != null) {
			return systemEvent;
		}

		throw new NoSuchSystemEventException(
			_collectionPersistenceFinderByG_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, systemEventSetKey}));
	}

	/**
	 * Returns the first system event in the ordered set where groupId = &#63; and systemEventSetKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param systemEventSetKey the system event set key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching system event, or <code>null</code> if a matching system event could not be found
	 */
	@Override
	public SystemEvent fetchByG_S_First(
		long groupId, long systemEventSetKey,
		OrderByComparator<SystemEvent> orderByComparator) {

		return _collectionPersistenceFinderByG_S.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {groupId, systemEventSetKey}, orderByComparator);
	}

	/**
	 * Removes all the system events where groupId = &#63; and systemEventSetKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param systemEventSetKey the system event set key
	 */
	@Override
	public void removeByG_S(long groupId, long systemEventSetKey) {
		_collectionPersistenceFinderByG_S.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {groupId, systemEventSetKey});
	}

	/**
	 * Returns the number of system events where groupId = &#63; and systemEventSetKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param systemEventSetKey the system event set key
	 * @return the number of matching system events
	 */
	@Override
	public int countByG_S(long groupId, long systemEventSetKey) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					SystemEvent.class)) {

			return _collectionPersistenceFinderByG_S.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {groupId, systemEventSetKey});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_C_C;
	private FinderPath _finderPathWithoutPaginationFindByG_C_C;
	private FinderPath _finderPathCountByG_C_C;
	private CollectionPersistenceFinder<SystemEvent>
		_collectionPersistenceFinderByG_C_C;

	/**
	 * Returns all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching system events
	 */
	@Override
	public List<SystemEvent> findByG_C_C(
		long groupId, long classNameId, long classPK) {

		return findByG_C_C(
			groupId, classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @return the range of matching system events
	 */
	@Override
	public List<SystemEvent> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end) {

		return findByG_C_C(groupId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching system events
	 */
	@Override
	public List<SystemEvent> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		OrderByComparator<SystemEvent> orderByComparator) {

		return findByG_C_C(
			groupId, classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching system events
	 */
	@Override
	public List<SystemEvent> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		OrderByComparator<SystemEvent> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					SystemEvent.class)) {

			return _collectionPersistenceFinderByG_C_C.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {groupId, classNameId, classPK}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching system event
	 * @throws NoSuchSystemEventException if a matching system event could not be found
	 */
	@Override
	public SystemEvent findByG_C_C_First(
			long groupId, long classNameId, long classPK,
			OrderByComparator<SystemEvent> orderByComparator)
		throws NoSuchSystemEventException {

		SystemEvent systemEvent = fetchByG_C_C_First(
			groupId, classNameId, classPK, orderByComparator);

		if (systemEvent != null) {
			return systemEvent;
		}

		throw new NoSuchSystemEventException(
			_collectionPersistenceFinderByG_C_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, classNameId, classPK}));
	}

	/**
	 * Returns the first system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching system event, or <code>null</code> if a matching system event could not be found
	 */
	@Override
	public SystemEvent fetchByG_C_C_First(
		long groupId, long classNameId, long classPK,
		OrderByComparator<SystemEvent> orderByComparator) {

		return _collectionPersistenceFinderByG_C_C.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {groupId, classNameId, classPK}, orderByComparator);
	}

	/**
	 * Removes all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByG_C_C(long groupId, long classNameId, long classPK) {
		_collectionPersistenceFinderByG_C_C.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {groupId, classNameId, classPK});
	}

	/**
	 * Returns the number of system events where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching system events
	 */
	@Override
	public int countByG_C_C(long groupId, long classNameId, long classPK) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					SystemEvent.class)) {

			return _collectionPersistenceFinderByG_C_C.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {groupId, classNameId, classPK});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_C_C_T;
	private FinderPath _finderPathWithoutPaginationFindByG_C_C_T;
	private FinderPath _finderPathCountByG_C_C_T;
	private CollectionPersistenceFinder<SystemEvent>
		_collectionPersistenceFinderByG_C_C_T;

	/**
	 * Returns all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the matching system events
	 */
	@Override
	public List<SystemEvent> findByG_C_C_T(
		long groupId, long classNameId, long classPK, int type) {

		return findByG_C_C_T(
			groupId, classNameId, classPK, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @return the range of matching system events
	 */
	@Override
	public List<SystemEvent> findByG_C_C_T(
		long groupId, long classNameId, long classPK, int type, int start,
		int end) {

		return findByG_C_C_T(
			groupId, classNameId, classPK, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching system events
	 */
	@Override
	public List<SystemEvent> findByG_C_C_T(
		long groupId, long classNameId, long classPK, int type, int start,
		int end, OrderByComparator<SystemEvent> orderByComparator) {

		return findByG_C_C_T(
			groupId, classNameId, classPK, type, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching system events
	 */
	@Override
	public List<SystemEvent> findByG_C_C_T(
		long groupId, long classNameId, long classPK, int type, int start,
		int end, OrderByComparator<SystemEvent> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					SystemEvent.class)) {

			return _collectionPersistenceFinderByG_C_C_T.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {groupId, classNameId, classPK, type}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching system event
	 * @throws NoSuchSystemEventException if a matching system event could not be found
	 */
	@Override
	public SystemEvent findByG_C_C_T_First(
			long groupId, long classNameId, long classPK, int type,
			OrderByComparator<SystemEvent> orderByComparator)
		throws NoSuchSystemEventException {

		SystemEvent systemEvent = fetchByG_C_C_T_First(
			groupId, classNameId, classPK, type, orderByComparator);

		if (systemEvent != null) {
			return systemEvent;
		}

		throw new NoSuchSystemEventException(
			_collectionPersistenceFinderByG_C_C_T.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, classNameId, classPK, type}));
	}

	/**
	 * Returns the first system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching system event, or <code>null</code> if a matching system event could not be found
	 */
	@Override
	public SystemEvent fetchByG_C_C_T_First(
		long groupId, long classNameId, long classPK, int type,
		OrderByComparator<SystemEvent> orderByComparator) {

		return _collectionPersistenceFinderByG_C_C_T.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {groupId, classNameId, classPK, type},
			orderByComparator);
	}

	/**
	 * Removes all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 */
	@Override
	public void removeByG_C_C_T(
		long groupId, long classNameId, long classPK, int type) {

		_collectionPersistenceFinderByG_C_C_T.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {groupId, classNameId, classPK, type});
	}

	/**
	 * Returns the number of system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the number of matching system events
	 */
	@Override
	public int countByG_C_C_T(
		long groupId, long classNameId, long classPK, int type) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					SystemEvent.class)) {

			return _collectionPersistenceFinderByG_C_C_T.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {groupId, classNameId, classPK, type});
		}
	}

	public SystemEventPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(SystemEvent.class);

		setModelImplClass(SystemEventImpl.class);
		setModelPKClass(long.class);

		setTable(SystemEventTable.INSTANCE);
	}

	/**
	 * Caches the system event in the entity cache if it is enabled.
	 *
	 * @param systemEvent the system event
	 */
	@Override
	public void cacheResult(SystemEvent systemEvent) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					systemEvent.getCtCollectionId())) {

			EntityCacheUtil.putResult(
				SystemEventImpl.class, systemEvent.getPrimaryKey(),
				systemEvent);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the system events in the entity cache if it is enabled.
	 *
	 * @param systemEvents the system events
	 */
	@Override
	public void cacheResult(List<SystemEvent> systemEvents) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (systemEvents.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (SystemEvent systemEvent : systemEvents) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						systemEvent.getCtCollectionId())) {

				if (EntityCacheUtil.getResult(
						SystemEventImpl.class, systemEvent.getPrimaryKey()) ==
							null) {

					cacheResult(systemEvent);
				}
			}
		}
	}

	/**
	 * Creates a new system event with the primary key. Does not add the system event to the database.
	 *
	 * @param systemEventId the primary key for the new system event
	 * @return the new system event
	 */
	@Override
	public SystemEvent create(long systemEventId) {
		SystemEvent systemEvent = new SystemEventImpl();

		systemEvent.setNew(true);
		systemEvent.setPrimaryKey(systemEventId);

		systemEvent.setCompanyId(CompanyThreadLocal.getCompanyId());

		return systemEvent;
	}

	/**
	 * Removes the system event with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param systemEventId the primary key of the system event
	 * @return the system event that was removed
	 * @throws NoSuchSystemEventException if a system event with the primary key could not be found
	 */
	@Override
	public SystemEvent remove(long systemEventId)
		throws NoSuchSystemEventException {

		return remove((Serializable)systemEventId);
	}

	@Override
	protected SystemEvent removeImpl(SystemEvent systemEvent) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(systemEvent)) {
				systemEvent = (SystemEvent)session.get(
					SystemEventImpl.class, systemEvent.getPrimaryKeyObj());
			}

			if ((systemEvent != null) &&
				CTPersistenceHelperUtil.isRemove(systemEvent)) {

				session.delete(systemEvent);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (systemEvent != null) {
			clearCache(systemEvent);
		}

		return systemEvent;
	}

	@Override
	public SystemEvent updateImpl(SystemEvent systemEvent) {
		boolean isNew = systemEvent.isNew();

		if (!(systemEvent instanceof SystemEventModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(systemEvent.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(systemEvent);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in systemEvent proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SystemEvent implementation " +
					systemEvent.getClass());
		}

		SystemEventModelImpl systemEventModelImpl =
			(SystemEventModelImpl)systemEvent;

		if (isNew && (systemEvent.getCreateDate() == null)) {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			Date date = new Date();

			if (serviceContext == null) {
				systemEvent.setCreateDate(date);
			}
			else {
				systemEvent.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(systemEvent)) {
				if (!isNew) {
					session.evict(
						SystemEventImpl.class, systemEvent.getPrimaryKeyObj());
				}

				session.save(systemEvent);
			}
			else {
				systemEvent = (SystemEvent)session.merge(systemEvent);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		EntityCacheUtil.putResult(
			SystemEventImpl.class, systemEventModelImpl, false, true);

		if (isNew) {
			systemEvent.setNew(false);
		}

		systemEvent.resetOriginalValues();

		return systemEvent;
	}

	/**
	 * Returns the system event with the primary key or throws a <code>NoSuchSystemEventException</code> if it could not be found.
	 *
	 * @param systemEventId the primary key of the system event
	 * @return the system event
	 * @throws NoSuchSystemEventException if a system event with the primary key could not be found
	 */
	@Override
	public SystemEvent findByPrimaryKey(long systemEventId)
		throws NoSuchSystemEventException {

		return findByPrimaryKey((Serializable)systemEventId);
	}

	@Override
	protected CTPersistenceHelper getCTPersistenceHelper() {
		return CTPersistenceHelperUtil.getCTPersistenceHelper();
	}

	/**
	 * Returns the system event with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param systemEventId the primary key of the system event
	 * @return the system event, or <code>null</code> if a system event with the primary key could not be found
	 */
	@Override
	public SystemEvent fetchByPrimaryKey(long systemEventId) {
		return fetchByPrimaryKey((Serializable)systemEventId);
	}

	/**
	 * Returns all the system events.
	 *
	 * @return the system events
	 */
	@Override
	public List<SystemEvent> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the system events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @return the range of system events
	 */
	@Override
	public List<SystemEvent> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the system events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of system events
	 */
	@Override
	public List<SystemEvent> findAll(
		int start, int end, OrderByComparator<SystemEvent> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the system events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of system events
	 */
	@Override
	public List<SystemEvent> findAll(
		int start, int end, OrderByComparator<SystemEvent> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					SystemEvent.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindAll;
					finderArgs = FINDER_ARGS_EMPTY;
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindAll;
				finderArgs = new Object[] {start, end, orderByComparator};
			}

			List<SystemEvent> list = null;

			if (useFinderCache) {
				list = (List<SystemEvent>)FinderCacheUtil.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_SYSTEMEVENT);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_SYSTEMEVENT;

					sql = sql.concat(SystemEventModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<SystemEvent>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						FinderCacheUtil.putResult(finderPath, finderArgs, list);
					}
				}
				catch (Exception exception) {
					throw processException(exception);
				}
				finally {
					closeSession(session);
				}
			}

			return list;
		}
	}

	/**
	 * Removes all the system events from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SystemEvent systemEvent : findAll()) {
			remove(systemEvent);
		}
	}

	/**
	 * Returns the number of system events.
	 *
	 * @return the number of system events
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					SystemEvent.class)) {

			Long count = (Long)FinderCacheUtil.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(_SQL_COUNT_SYSTEMEVENT);

					count = (Long)query.uniqueResult();

					FinderCacheUtil.putResult(
						_finderPathCountAll, FINDER_ARGS_EMPTY, count);
				}
				catch (Exception exception) {
					throw processException(exception);
				}
				finally {
					closeSession(session);
				}
			}

			return count.intValue();
		}
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "systemEventId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SYSTEMEVENT;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.getOrDefault(
			ctColumnResolutionType, Collections.emptySet());
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return SystemEventModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "SystemEvent";
	}

	@Override
	public List<String[]> getUniqueIndexColumnNames() {
		return _uniqueIndexColumnNames;
	}

	private static final Map<CTColumnResolutionType, Set<String>>
		_ctColumnNamesMap = new EnumMap<CTColumnResolutionType, Set<String>>(
			CTColumnResolutionType.class);
	private static final List<String> _mappingTableNames =
		new ArrayList<String>();
	private static final List<String[]> _uniqueIndexColumnNames =
		new ArrayList<String[]>();

	static {
		Set<String> ctControlColumnNames = new HashSet<String>();
		Set<String> ctMergeColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctMergeColumnNames.add("classExternalReferenceCode");
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");
		ctMergeColumnNames.add("classUuid");
		ctMergeColumnNames.add("referrerClassNameId");
		ctMergeColumnNames.add("parentSystemEventId");
		ctMergeColumnNames.add("systemEventSetKey");
		ctMergeColumnNames.add("type_");
		ctMergeColumnNames.add("extraData");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("systemEventId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);
	}

	/**
	 * Initializes the system event persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId"}, true);

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			true);

		_finderPathCountByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			false);

		_collectionPersistenceFinderByGroupId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByGroupId,
				_finderPathWithoutPaginationFindByGroupId,
				_finderPathCountByGroupId, _SQL_SELECT_SYSTEMEVENT_WHERE,
				_SQL_COUNT_SYSTEMEVENT_WHERE,
				SystemEventModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"systemEvent.", "groupId", FinderColumn.Type.LONG, "=",
					true, true, SystemEvent::getGroupId));

		_finderPathWithPaginationFindByG_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "systemEventSetKey"}, true);

		_finderPathWithoutPaginationFindByG_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_S",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "systemEventSetKey"}, true);

		_finderPathCountByG_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "systemEventSetKey"}, false);

		_collectionPersistenceFinderByG_S = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_S,
			_finderPathWithoutPaginationFindByG_S, _finderPathCountByG_S,
			_SQL_SELECT_SYSTEMEVENT_WHERE, _SQL_COUNT_SYSTEMEVENT_WHERE,
			SystemEventModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"systemEvent.", "groupId", FinderColumn.Type.LONG, "=", true,
				false, SystemEvent::getGroupId),
			new FinderColumn<>(
				"systemEvent.", "systemEventSetKey", FinderColumn.Type.LONG,
				"=", true, true, SystemEvent::getSystemEventSetKey));

		_finderPathWithPaginationFindByG_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "classNameId", "classPK"}, true);

		_finderPathWithoutPaginationFindByG_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "classNameId", "classPK"}, true);

		_finderPathCountByG_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "classNameId", "classPK"}, false);

		_collectionPersistenceFinderByG_C_C = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_C_C,
			_finderPathWithoutPaginationFindByG_C_C, _finderPathCountByG_C_C,
			_SQL_SELECT_SYSTEMEVENT_WHERE, _SQL_COUNT_SYSTEMEVENT_WHERE,
			SystemEventModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"systemEvent.", "groupId", FinderColumn.Type.LONG, "=", true,
				false, SystemEvent::getGroupId),
			new FinderColumn<>(
				"systemEvent.", "classNameId", FinderColumn.Type.LONG, "=",
				true, false, SystemEvent::getClassNameId),
			new FinderColumn<>(
				"systemEvent.", "classPK", FinderColumn.Type.LONG, "=", true,
				true, SystemEvent::getClassPK));

		_finderPathWithPaginationFindByG_C_C_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "classNameId", "classPK", "type_"}, true);

		_finderPathWithoutPaginationFindByG_C_C_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			},
			new String[] {"groupId", "classNameId", "classPK", "type_"}, true);

		_finderPathCountByG_C_C_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			},
			new String[] {"groupId", "classNameId", "classPK", "type_"}, false);

		_collectionPersistenceFinderByG_C_C_T =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_C_C_T,
				_finderPathWithoutPaginationFindByG_C_C_T,
				_finderPathCountByG_C_C_T, _SQL_SELECT_SYSTEMEVENT_WHERE,
				_SQL_COUNT_SYSTEMEVENT_WHERE,
				SystemEventModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"systemEvent.", "groupId", FinderColumn.Type.LONG, "=",
					true, false, SystemEvent::getGroupId),
				new FinderColumn<>(
					"systemEvent.", "classNameId", FinderColumn.Type.LONG, "=",
					true, false, SystemEvent::getClassNameId),
				new FinderColumn<>(
					"systemEvent.", "classPK", FinderColumn.Type.LONG, "=",
					true, false, SystemEvent::getClassPK),
				new FinderColumn<>(
					"systemEvent.", "type", FinderColumn.Type.INTEGER, "=",
					true, true, SystemEvent::getType));

		SystemEventUtil.setPersistence(this);
	}

	public void destroy() {
		SystemEventUtil.setPersistence(null);

		EntityCacheUtil.removeCache(SystemEventImpl.class.getName());
	}

	private static final String _SQL_SELECT_SYSTEMEVENT =
		"SELECT systemEvent FROM SystemEvent systemEvent";

	private static final String _SQL_SELECT_SYSTEMEVENT_WHERE =
		"SELECT systemEvent FROM SystemEvent systemEvent WHERE ";

	private static final String _SQL_COUNT_SYSTEMEVENT =
		"SELECT COUNT(systemEvent) FROM SystemEvent systemEvent";

	private static final String _SQL_COUNT_SYSTEMEVENT_WHERE =
		"SELECT COUNT(systemEvent) FROM SystemEvent systemEvent WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "systemEvent.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No SystemEvent exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		SystemEventPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type"});

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1397039967