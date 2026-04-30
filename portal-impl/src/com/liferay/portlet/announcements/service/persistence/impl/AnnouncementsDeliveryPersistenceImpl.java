/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.announcements.service.persistence.impl;

import com.liferay.announcements.kernel.exception.NoSuchDeliveryException;
import com.liferay.announcements.kernel.model.AnnouncementsDelivery;
import com.liferay.announcements.kernel.model.AnnouncementsDeliveryTable;
import com.liferay.announcements.kernel.service.persistence.AnnouncementsDeliveryPersistence;
import com.liferay.announcements.kernel.service.persistence.AnnouncementsDeliveryUtil;
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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelperUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.CollectionPersistenceFinder;
import com.liferay.portal.kernel.service.persistence.impl.FinderColumn;
import com.liferay.portal.kernel.service.persistence.impl.UniquePersistenceFinder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryImpl;
import com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the announcements delivery service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AnnouncementsDeliveryPersistenceImpl
	extends BasePersistenceImpl<AnnouncementsDelivery, NoSuchDeliveryException>
	implements AnnouncementsDeliveryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AnnouncementsDeliveryUtil</code> to access the announcements delivery persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AnnouncementsDeliveryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;
	private CollectionPersistenceFinder<AnnouncementsDelivery>
		_collectionPersistenceFinderByCompanyId;

	/**
	 * Returns all the announcements deliveries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching announcements deliveries
	 */
	@Override
	public List<AnnouncementsDelivery> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the announcements deliveries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnnouncementsDeliveryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of announcements deliveries
	 * @param end the upper bound of the range of announcements deliveries (not inclusive)
	 * @return the range of matching announcements deliveries
	 */
	@Override
	public List<AnnouncementsDelivery> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the announcements deliveries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnnouncementsDeliveryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of announcements deliveries
	 * @param end the upper bound of the range of announcements deliveries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching announcements deliveries
	 */
	@Override
	public List<AnnouncementsDelivery> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AnnouncementsDelivery> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the announcements deliveries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnnouncementsDeliveryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of announcements deliveries
	 * @param end the upper bound of the range of announcements deliveries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching announcements deliveries
	 */
	@Override
	public List<AnnouncementsDelivery> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AnnouncementsDelivery> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AnnouncementsDelivery.class)) {

			return _collectionPersistenceFinderByCompanyId.find(
				FinderCacheUtil.getFinderCache(), new Object[] {companyId},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first announcements delivery in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching announcements delivery
	 * @throws NoSuchDeliveryException if a matching announcements delivery could not be found
	 */
	@Override
	public AnnouncementsDelivery findByCompanyId_First(
			long companyId,
			OrderByComparator<AnnouncementsDelivery> orderByComparator)
		throws NoSuchDeliveryException {

		AnnouncementsDelivery announcementsDelivery = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (announcementsDelivery != null) {
			return announcementsDelivery;
		}

		throw new NoSuchDeliveryException(
			_collectionPersistenceFinderByCompanyId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId}));
	}

	/**
	 * Returns the first announcements delivery in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching announcements delivery, or <code>null</code> if a matching announcements delivery could not be found
	 */
	@Override
	public AnnouncementsDelivery fetchByCompanyId_First(
		long companyId,
		OrderByComparator<AnnouncementsDelivery> orderByComparator) {

		return _collectionPersistenceFinderByCompanyId.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {companyId},
			orderByComparator);
	}

	/**
	 * Removes all the announcements deliveries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		_collectionPersistenceFinderByCompanyId.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {companyId});
	}

	/**
	 * Returns the number of announcements deliveries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching announcements deliveries
	 */
	@Override
	public int countByCompanyId(long companyId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AnnouncementsDelivery.class)) {

			return _collectionPersistenceFinderByCompanyId.count(
				FinderCacheUtil.getFinderCache(), new Object[] {companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByUserId;
	private FinderPath _finderPathWithoutPaginationFindByUserId;
	private FinderPath _finderPathCountByUserId;
	private CollectionPersistenceFinder<AnnouncementsDelivery>
		_collectionPersistenceFinderByUserId;

	/**
	 * Returns all the announcements deliveries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching announcements deliveries
	 */
	@Override
	public List<AnnouncementsDelivery> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the announcements deliveries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnnouncementsDeliveryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of announcements deliveries
	 * @param end the upper bound of the range of announcements deliveries (not inclusive)
	 * @return the range of matching announcements deliveries
	 */
	@Override
	public List<AnnouncementsDelivery> findByUserId(
		long userId, int start, int end) {

		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the announcements deliveries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnnouncementsDeliveryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of announcements deliveries
	 * @param end the upper bound of the range of announcements deliveries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching announcements deliveries
	 */
	@Override
	public List<AnnouncementsDelivery> findByUserId(
		long userId, int start, int end,
		OrderByComparator<AnnouncementsDelivery> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the announcements deliveries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnnouncementsDeliveryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of announcements deliveries
	 * @param end the upper bound of the range of announcements deliveries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching announcements deliveries
	 */
	@Override
	public List<AnnouncementsDelivery> findByUserId(
		long userId, int start, int end,
		OrderByComparator<AnnouncementsDelivery> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AnnouncementsDelivery.class)) {

			return _collectionPersistenceFinderByUserId.find(
				FinderCacheUtil.getFinderCache(), new Object[] {userId}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first announcements delivery in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching announcements delivery
	 * @throws NoSuchDeliveryException if a matching announcements delivery could not be found
	 */
	@Override
	public AnnouncementsDelivery findByUserId_First(
			long userId,
			OrderByComparator<AnnouncementsDelivery> orderByComparator)
		throws NoSuchDeliveryException {

		AnnouncementsDelivery announcementsDelivery = fetchByUserId_First(
			userId, orderByComparator);

		if (announcementsDelivery != null) {
			return announcementsDelivery;
		}

		throw new NoSuchDeliveryException(
			_collectionPersistenceFinderByUserId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {userId}));
	}

	/**
	 * Returns the first announcements delivery in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching announcements delivery, or <code>null</code> if a matching announcements delivery could not be found
	 */
	@Override
	public AnnouncementsDelivery fetchByUserId_First(
		long userId,
		OrderByComparator<AnnouncementsDelivery> orderByComparator) {

		return _collectionPersistenceFinderByUserId.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {userId},
			orderByComparator);
	}

	/**
	 * Removes all the announcements deliveries where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		_collectionPersistenceFinderByUserId.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {userId});
	}

	/**
	 * Returns the number of announcements deliveries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching announcements deliveries
	 */
	@Override
	public int countByUserId(long userId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AnnouncementsDelivery.class)) {

			return _collectionPersistenceFinderByUserId.count(
				FinderCacheUtil.getFinderCache(), new Object[] {userId});
		}
	}

	private FinderPath _finderPathFetchByU_T;
	private UniquePersistenceFinder<AnnouncementsDelivery>
		_uniquePersistenceFinderByU_T;

	/**
	 * Returns the announcements delivery where userId = &#63; and type = &#63; or throws a <code>NoSuchDeliveryException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the matching announcements delivery
	 * @throws NoSuchDeliveryException if a matching announcements delivery could not be found
	 */
	@Override
	public AnnouncementsDelivery findByU_T(long userId, String type)
		throws NoSuchDeliveryException {

		AnnouncementsDelivery announcementsDelivery = fetchByU_T(userId, type);

		if (announcementsDelivery == null) {
			String message =
				_uniquePersistenceFinderByU_T.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {userId, type});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchDeliveryException(message);
		}

		return announcementsDelivery;
	}

	/**
	 * Returns the announcements delivery where userId = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the matching announcements delivery, or <code>null</code> if a matching announcements delivery could not be found
	 */
	@Override
	public AnnouncementsDelivery fetchByU_T(long userId, String type) {
		return fetchByU_T(userId, type, true);
	}

	/**
	 * Returns the announcements delivery where userId = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching announcements delivery, or <code>null</code> if a matching announcements delivery could not be found
	 */
	@Override
	public AnnouncementsDelivery fetchByU_T(
		long userId, String type, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AnnouncementsDelivery.class)) {

			return _uniquePersistenceFinderByU_T.fetch(
				FinderCacheUtil.getFinderCache(), new Object[] {userId, type},
				useFinderCache);
		}
	}

	/**
	 * Removes the announcements delivery where userId = &#63; and type = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the announcements delivery that was removed
	 */
	@Override
	public AnnouncementsDelivery removeByU_T(long userId, String type)
		throws NoSuchDeliveryException {

		AnnouncementsDelivery announcementsDelivery = findByU_T(userId, type);

		return remove(announcementsDelivery);
	}

	/**
	 * Returns the number of announcements deliveries where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the number of matching announcements deliveries
	 */
	@Override
	public int countByU_T(long userId, String type) {
		return _uniquePersistenceFinderByU_T.count(
			FinderCacheUtil.getFinderCache(), new Object[] {userId, type});
	}

	public AnnouncementsDeliveryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(AnnouncementsDelivery.class);

		setModelImplClass(AnnouncementsDeliveryImpl.class);
		setModelPKClass(long.class);

		setTable(AnnouncementsDeliveryTable.INSTANCE);
	}

	/**
	 * Caches the announcements delivery in the entity cache if it is enabled.
	 *
	 * @param announcementsDelivery the announcements delivery
	 */
	@Override
	public void cacheResult(AnnouncementsDelivery announcementsDelivery) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					announcementsDelivery.getCtCollectionId())) {

			EntityCacheUtil.putResult(
				AnnouncementsDeliveryImpl.class,
				announcementsDelivery.getPrimaryKey(), announcementsDelivery);

			FinderCacheUtil.putResult(
				_finderPathFetchByU_T,
				new Object[] {
					announcementsDelivery.getUserId(),
					announcementsDelivery.getType()
				},
				announcementsDelivery);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the announcements deliveries in the entity cache if it is enabled.
	 *
	 * @param announcementsDeliveries the announcements deliveries
	 */
	@Override
	public void cacheResult(
		List<AnnouncementsDelivery> announcementsDeliveries) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (announcementsDeliveries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (AnnouncementsDelivery announcementsDelivery :
				announcementsDeliveries) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						announcementsDelivery.getCtCollectionId())) {

				if (EntityCacheUtil.getResult(
						AnnouncementsDeliveryImpl.class,
						announcementsDelivery.getPrimaryKey()) == null) {

					cacheResult(announcementsDelivery);
				}
			}
		}
	}

	protected void cacheUniqueFindersCache(
		AnnouncementsDeliveryModelImpl announcementsDeliveryModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					announcementsDeliveryModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				announcementsDeliveryModelImpl.getUserId(),
				announcementsDeliveryModelImpl.getType()
			};

			FinderCacheUtil.putResult(
				_finderPathFetchByU_T, args, announcementsDeliveryModelImpl);
		}
	}

	/**
	 * Creates a new announcements delivery with the primary key. Does not add the announcements delivery to the database.
	 *
	 * @param deliveryId the primary key for the new announcements delivery
	 * @return the new announcements delivery
	 */
	@Override
	public AnnouncementsDelivery create(long deliveryId) {
		AnnouncementsDelivery announcementsDelivery =
			new AnnouncementsDeliveryImpl();

		announcementsDelivery.setNew(true);
		announcementsDelivery.setPrimaryKey(deliveryId);

		announcementsDelivery.setCompanyId(CompanyThreadLocal.getCompanyId());

		return announcementsDelivery;
	}

	/**
	 * Removes the announcements delivery with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param deliveryId the primary key of the announcements delivery
	 * @return the announcements delivery that was removed
	 * @throws NoSuchDeliveryException if a announcements delivery with the primary key could not be found
	 */
	@Override
	public AnnouncementsDelivery remove(long deliveryId)
		throws NoSuchDeliveryException {

		return remove((Serializable)deliveryId);
	}

	@Override
	protected AnnouncementsDelivery removeImpl(
		AnnouncementsDelivery announcementsDelivery) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(announcementsDelivery)) {
				announcementsDelivery = (AnnouncementsDelivery)session.get(
					AnnouncementsDeliveryImpl.class,
					announcementsDelivery.getPrimaryKeyObj());
			}

			if ((announcementsDelivery != null) &&
				CTPersistenceHelperUtil.isRemove(announcementsDelivery)) {

				session.delete(announcementsDelivery);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (announcementsDelivery != null) {
			clearCache(announcementsDelivery);
		}

		return announcementsDelivery;
	}

	@Override
	public AnnouncementsDelivery updateImpl(
		AnnouncementsDelivery announcementsDelivery) {

		boolean isNew = announcementsDelivery.isNew();

		if (!(announcementsDelivery instanceof
				AnnouncementsDeliveryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(announcementsDelivery.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					announcementsDelivery);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in announcementsDelivery proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AnnouncementsDelivery implementation " +
					announcementsDelivery.getClass());
		}

		AnnouncementsDeliveryModelImpl announcementsDeliveryModelImpl =
			(AnnouncementsDeliveryModelImpl)announcementsDelivery;

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(announcementsDelivery)) {
				if (!isNew) {
					session.evict(
						AnnouncementsDeliveryImpl.class,
						announcementsDelivery.getPrimaryKeyObj());
				}

				session.save(announcementsDelivery);
			}
			else {
				announcementsDelivery = (AnnouncementsDelivery)session.merge(
					announcementsDelivery);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		EntityCacheUtil.putResult(
			AnnouncementsDeliveryImpl.class, announcementsDeliveryModelImpl,
			false, true);

		cacheUniqueFindersCache(announcementsDeliveryModelImpl);

		if (isNew) {
			announcementsDelivery.setNew(false);
		}

		announcementsDelivery.resetOriginalValues();

		return announcementsDelivery;
	}

	/**
	 * Returns the announcements delivery with the primary key or throws a <code>NoSuchDeliveryException</code> if it could not be found.
	 *
	 * @param deliveryId the primary key of the announcements delivery
	 * @return the announcements delivery
	 * @throws NoSuchDeliveryException if a announcements delivery with the primary key could not be found
	 */
	@Override
	public AnnouncementsDelivery findByPrimaryKey(long deliveryId)
		throws NoSuchDeliveryException {

		return findByPrimaryKey((Serializable)deliveryId);
	}

	@Override
	protected CTPersistenceHelper getCTPersistenceHelper() {
		return CTPersistenceHelperUtil.getCTPersistenceHelper();
	}

	/**
	 * Returns the announcements delivery with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param deliveryId the primary key of the announcements delivery
	 * @return the announcements delivery, or <code>null</code> if a announcements delivery with the primary key could not be found
	 */
	@Override
	public AnnouncementsDelivery fetchByPrimaryKey(long deliveryId) {
		return fetchByPrimaryKey((Serializable)deliveryId);
	}

	/**
	 * Returns all the announcements deliveries.
	 *
	 * @return the announcements deliveries
	 */
	@Override
	public List<AnnouncementsDelivery> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the announcements deliveries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnnouncementsDeliveryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of announcements deliveries
	 * @param end the upper bound of the range of announcements deliveries (not inclusive)
	 * @return the range of announcements deliveries
	 */
	@Override
	public List<AnnouncementsDelivery> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the announcements deliveries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnnouncementsDeliveryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of announcements deliveries
	 * @param end the upper bound of the range of announcements deliveries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of announcements deliveries
	 */
	@Override
	public List<AnnouncementsDelivery> findAll(
		int start, int end,
		OrderByComparator<AnnouncementsDelivery> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the announcements deliveries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnnouncementsDeliveryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of announcements deliveries
	 * @param end the upper bound of the range of announcements deliveries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of announcements deliveries
	 */
	@Override
	public List<AnnouncementsDelivery> findAll(
		int start, int end,
		OrderByComparator<AnnouncementsDelivery> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AnnouncementsDelivery.class)) {

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

			List<AnnouncementsDelivery> list = null;

			if (useFinderCache) {
				list = (List<AnnouncementsDelivery>)FinderCacheUtil.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_ANNOUNCEMENTSDELIVERY);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_ANNOUNCEMENTSDELIVERY;

					sql = sql.concat(
						AnnouncementsDeliveryModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<AnnouncementsDelivery>)QueryUtil.list(
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
	 * Removes all the announcements deliveries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AnnouncementsDelivery announcementsDelivery : findAll()) {
			remove(announcementsDelivery);
		}
	}

	/**
	 * Returns the number of announcements deliveries.
	 *
	 * @return the number of announcements deliveries
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AnnouncementsDelivery.class)) {

			Long count = (Long)FinderCacheUtil.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(
						_SQL_COUNT_ANNOUNCEMENTSDELIVERY);

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
		return "deliveryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ANNOUNCEMENTSDELIVERY;
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
		return AnnouncementsDeliveryModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "AnnouncementsDelivery";
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
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctMergeColumnNames.add("type_");
		ctMergeColumnNames.add("email");
		ctMergeColumnNames.add("sms");
		ctMergeColumnNames.add("website");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("deliveryId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"userId", "type_"});
	}

	/**
	 * Initializes the announcements delivery persistence.
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

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId"}, true);

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			true);

		_finderPathCountByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			false);

		_collectionPersistenceFinderByCompanyId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByCompanyId,
				_finderPathWithoutPaginationFindByCompanyId,
				_finderPathCountByCompanyId,
				_SQL_SELECT_ANNOUNCEMENTSDELIVERY_WHERE,
				_SQL_COUNT_ANNOUNCEMENTSDELIVERY_WHERE,
				AnnouncementsDeliveryModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"announcementsDelivery.", "companyId",
					FinderColumn.Type.LONG, "=", true, true,
					AnnouncementsDelivery::getCompanyId));

		_finderPathWithPaginationFindByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"userId"}, true);

		_finderPathWithoutPaginationFindByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"}, true);

		_finderPathCountByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"},
			false);

		_collectionPersistenceFinderByUserId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByUserId,
				_finderPathWithoutPaginationFindByUserId,
				_finderPathCountByUserId,
				_SQL_SELECT_ANNOUNCEMENTSDELIVERY_WHERE,
				_SQL_COUNT_ANNOUNCEMENTSDELIVERY_WHERE,
				AnnouncementsDeliveryModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"announcementsDelivery.", "userId", FinderColumn.Type.LONG,
					"=", true, true, AnnouncementsDelivery::getUserId));

		_finderPathFetchByU_T = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByU_T",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"userId", "type_"}, true);

		_uniquePersistenceFinderByU_T = new UniquePersistenceFinder<>(
			this, _finderPathFetchByU_T,
			_SQL_SELECT_ANNOUNCEMENTSDELIVERY_WHERE,
			new FinderColumn<>(
				"announcementsDelivery.", "userId", FinderColumn.Type.LONG, "=",
				true, false, AnnouncementsDelivery::getUserId),
			new FinderColumn<>(
				"announcementsDelivery.", "type", FinderColumn.Type.STRING, "=",
				true, true, AnnouncementsDelivery::getType));

		AnnouncementsDeliveryUtil.setPersistence(this);
	}

	public void destroy() {
		AnnouncementsDeliveryUtil.setPersistence(null);

		EntityCacheUtil.removeCache(AnnouncementsDeliveryImpl.class.getName());
	}

	private static final String _SQL_SELECT_ANNOUNCEMENTSDELIVERY =
		"SELECT announcementsDelivery FROM AnnouncementsDelivery announcementsDelivery";

	private static final String _SQL_SELECT_ANNOUNCEMENTSDELIVERY_WHERE =
		"SELECT announcementsDelivery FROM AnnouncementsDelivery announcementsDelivery WHERE ";

	private static final String _SQL_COUNT_ANNOUNCEMENTSDELIVERY =
		"SELECT COUNT(announcementsDelivery) FROM AnnouncementsDelivery announcementsDelivery";

	private static final String _SQL_COUNT_ANNOUNCEMENTSDELIVERY_WHERE =
		"SELECT COUNT(announcementsDelivery) FROM AnnouncementsDelivery announcementsDelivery WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"announcementsDelivery.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AnnouncementsDelivery exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AnnouncementsDeliveryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type"});

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1983826142