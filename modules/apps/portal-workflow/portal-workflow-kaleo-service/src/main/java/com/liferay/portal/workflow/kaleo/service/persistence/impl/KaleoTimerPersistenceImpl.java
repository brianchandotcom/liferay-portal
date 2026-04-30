/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.service.persistence.impl;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.CollectionPersistenceFinder;
import com.liferay.portal.kernel.service.persistence.impl.FinderColumn;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.workflow.kaleo.exception.NoSuchTimerException;
import com.liferay.portal.workflow.kaleo.model.KaleoTimer;
import com.liferay.portal.workflow.kaleo.model.KaleoTimerTable;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoTimerImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoTimerModelImpl;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoTimerPersistence;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoTimerUtil;
import com.liferay.portal.workflow.kaleo.service.persistence.impl.constants.KaleoPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the kaleo timer service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = KaleoTimerPersistence.class)
public class KaleoTimerPersistenceImpl
	extends BasePersistenceImpl<KaleoTimer, NoSuchTimerException>
	implements KaleoTimerPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>KaleoTimerUtil</code> to access the kaleo timer persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		KaleoTimerImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByKCN_KCPK;
	private FinderPath _finderPathWithoutPaginationFindByKCN_KCPK;
	private FinderPath _finderPathCountByKCN_KCPK;
	private CollectionPersistenceFinder<KaleoTimer>
		_collectionPersistenceFinderByKCN_KCPK;

	/**
	 * Returns all the kaleo timers where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @return the matching kaleo timers
	 */
	@Override
	public List<KaleoTimer> findByKCN_KCPK(
		String kaleoClassName, long kaleoClassPK) {

		return findByKCN_KCPK(
			kaleoClassName, kaleoClassPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the kaleo timers where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTimerModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param start the lower bound of the range of kaleo timers
	 * @param end the upper bound of the range of kaleo timers (not inclusive)
	 * @return the range of matching kaleo timers
	 */
	@Override
	public List<KaleoTimer> findByKCN_KCPK(
		String kaleoClassName, long kaleoClassPK, int start, int end) {

		return findByKCN_KCPK(kaleoClassName, kaleoClassPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo timers where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTimerModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param start the lower bound of the range of kaleo timers
	 * @param end the upper bound of the range of kaleo timers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo timers
	 */
	@Override
	public List<KaleoTimer> findByKCN_KCPK(
		String kaleoClassName, long kaleoClassPK, int start, int end,
		OrderByComparator<KaleoTimer> orderByComparator) {

		return findByKCN_KCPK(
			kaleoClassName, kaleoClassPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo timers where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTimerModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param start the lower bound of the range of kaleo timers
	 * @param end the upper bound of the range of kaleo timers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo timers
	 */
	@Override
	public List<KaleoTimer> findByKCN_KCPK(
		String kaleoClassName, long kaleoClassPK, int start, int end,
		OrderByComparator<KaleoTimer> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					KaleoTimer.class)) {

			return _collectionPersistenceFinderByKCN_KCPK.find(
				finderCache, new Object[] {kaleoClassName, kaleoClassPK}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first kaleo timer in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo timer
	 * @throws NoSuchTimerException if a matching kaleo timer could not be found
	 */
	@Override
	public KaleoTimer findByKCN_KCPK_First(
			String kaleoClassName, long kaleoClassPK,
			OrderByComparator<KaleoTimer> orderByComparator)
		throws NoSuchTimerException {

		KaleoTimer kaleoTimer = fetchByKCN_KCPK_First(
			kaleoClassName, kaleoClassPK, orderByComparator);

		if (kaleoTimer != null) {
			return kaleoTimer;
		}

		throw new NoSuchTimerException(
			_collectionPersistenceFinderByKCN_KCPK.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {kaleoClassName, kaleoClassPK}));
	}

	/**
	 * Returns the first kaleo timer in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo timer, or <code>null</code> if a matching kaleo timer could not be found
	 */
	@Override
	public KaleoTimer fetchByKCN_KCPK_First(
		String kaleoClassName, long kaleoClassPK,
		OrderByComparator<KaleoTimer> orderByComparator) {

		return _collectionPersistenceFinderByKCN_KCPK.fetchFirst(
			finderCache, new Object[] {kaleoClassName, kaleoClassPK},
			orderByComparator);
	}

	/**
	 * Removes all the kaleo timers where kaleoClassName = &#63; and kaleoClassPK = &#63; from the database.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 */
	@Override
	public void removeByKCN_KCPK(String kaleoClassName, long kaleoClassPK) {
		_collectionPersistenceFinderByKCN_KCPK.remove(
			finderCache, new Object[] {kaleoClassName, kaleoClassPK});
	}

	/**
	 * Returns the number of kaleo timers where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @return the number of matching kaleo timers
	 */
	@Override
	public int countByKCN_KCPK(String kaleoClassName, long kaleoClassPK) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					KaleoTimer.class)) {

			return _collectionPersistenceFinderByKCN_KCPK.count(
				finderCache, new Object[] {kaleoClassName, kaleoClassPK});
		}
	}

	private FinderPath _finderPathWithPaginationFindByKCN_KCPK_Blocking;
	private FinderPath _finderPathWithoutPaginationFindByKCN_KCPK_Blocking;
	private FinderPath _finderPathCountByKCN_KCPK_Blocking;
	private CollectionPersistenceFinder<KaleoTimer>
		_collectionPersistenceFinderByKCN_KCPK_Blocking;

	/**
	 * Returns all the kaleo timers where kaleoClassName = &#63; and kaleoClassPK = &#63; and blocking = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param blocking the blocking
	 * @return the matching kaleo timers
	 */
	@Override
	public List<KaleoTimer> findByKCN_KCPK_Blocking(
		String kaleoClassName, long kaleoClassPK, boolean blocking) {

		return findByKCN_KCPK_Blocking(
			kaleoClassName, kaleoClassPK, blocking, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo timers where kaleoClassName = &#63; and kaleoClassPK = &#63; and blocking = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTimerModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param blocking the blocking
	 * @param start the lower bound of the range of kaleo timers
	 * @param end the upper bound of the range of kaleo timers (not inclusive)
	 * @return the range of matching kaleo timers
	 */
	@Override
	public List<KaleoTimer> findByKCN_KCPK_Blocking(
		String kaleoClassName, long kaleoClassPK, boolean blocking, int start,
		int end) {

		return findByKCN_KCPK_Blocking(
			kaleoClassName, kaleoClassPK, blocking, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo timers where kaleoClassName = &#63; and kaleoClassPK = &#63; and blocking = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTimerModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param blocking the blocking
	 * @param start the lower bound of the range of kaleo timers
	 * @param end the upper bound of the range of kaleo timers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo timers
	 */
	@Override
	public List<KaleoTimer> findByKCN_KCPK_Blocking(
		String kaleoClassName, long kaleoClassPK, boolean blocking, int start,
		int end, OrderByComparator<KaleoTimer> orderByComparator) {

		return findByKCN_KCPK_Blocking(
			kaleoClassName, kaleoClassPK, blocking, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo timers where kaleoClassName = &#63; and kaleoClassPK = &#63; and blocking = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTimerModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param blocking the blocking
	 * @param start the lower bound of the range of kaleo timers
	 * @param end the upper bound of the range of kaleo timers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo timers
	 */
	@Override
	public List<KaleoTimer> findByKCN_KCPK_Blocking(
		String kaleoClassName, long kaleoClassPK, boolean blocking, int start,
		int end, OrderByComparator<KaleoTimer> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					KaleoTimer.class)) {

			return _collectionPersistenceFinderByKCN_KCPK_Blocking.find(
				finderCache,
				new Object[] {kaleoClassName, kaleoClassPK, blocking}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first kaleo timer in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63; and blocking = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param blocking the blocking
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo timer
	 * @throws NoSuchTimerException if a matching kaleo timer could not be found
	 */
	@Override
	public KaleoTimer findByKCN_KCPK_Blocking_First(
			String kaleoClassName, long kaleoClassPK, boolean blocking,
			OrderByComparator<KaleoTimer> orderByComparator)
		throws NoSuchTimerException {

		KaleoTimer kaleoTimer = fetchByKCN_KCPK_Blocking_First(
			kaleoClassName, kaleoClassPK, blocking, orderByComparator);

		if (kaleoTimer != null) {
			return kaleoTimer;
		}

		throw new NoSuchTimerException(
			_collectionPersistenceFinderByKCN_KCPK_Blocking.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {kaleoClassName, kaleoClassPK, blocking}));
	}

	/**
	 * Returns the first kaleo timer in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63; and blocking = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param blocking the blocking
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo timer, or <code>null</code> if a matching kaleo timer could not be found
	 */
	@Override
	public KaleoTimer fetchByKCN_KCPK_Blocking_First(
		String kaleoClassName, long kaleoClassPK, boolean blocking,
		OrderByComparator<KaleoTimer> orderByComparator) {

		return _collectionPersistenceFinderByKCN_KCPK_Blocking.fetchFirst(
			finderCache, new Object[] {kaleoClassName, kaleoClassPK, blocking},
			orderByComparator);
	}

	/**
	 * Removes all the kaleo timers where kaleoClassName = &#63; and kaleoClassPK = &#63; and blocking = &#63; from the database.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param blocking the blocking
	 */
	@Override
	public void removeByKCN_KCPK_Blocking(
		String kaleoClassName, long kaleoClassPK, boolean blocking) {

		_collectionPersistenceFinderByKCN_KCPK_Blocking.remove(
			finderCache, new Object[] {kaleoClassName, kaleoClassPK, blocking});
	}

	/**
	 * Returns the number of kaleo timers where kaleoClassName = &#63; and kaleoClassPK = &#63; and blocking = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param blocking the blocking
	 * @return the number of matching kaleo timers
	 */
	@Override
	public int countByKCN_KCPK_Blocking(
		String kaleoClassName, long kaleoClassPK, boolean blocking) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					KaleoTimer.class)) {

			return _collectionPersistenceFinderByKCN_KCPK_Blocking.count(
				finderCache,
				new Object[] {kaleoClassName, kaleoClassPK, blocking});
		}
	}

	public KaleoTimerPersistenceImpl() {
		setModelClass(KaleoTimer.class);

		setModelImplClass(KaleoTimerImpl.class);
		setModelPKClass(long.class);

		setTable(KaleoTimerTable.INSTANCE);
	}

	/**
	 * Caches the kaleo timer in the entity cache if it is enabled.
	 *
	 * @param kaleoTimer the kaleo timer
	 */
	@Override
	public void cacheResult(KaleoTimer kaleoTimer) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					kaleoTimer.getCtCollectionId())) {

			entityCache.putResult(
				KaleoTimerImpl.class, kaleoTimer.getPrimaryKey(), kaleoTimer);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the kaleo timers in the entity cache if it is enabled.
	 *
	 * @param kaleoTimers the kaleo timers
	 */
	@Override
	public void cacheResult(List<KaleoTimer> kaleoTimers) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (kaleoTimers.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (KaleoTimer kaleoTimer : kaleoTimers) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						kaleoTimer.getCtCollectionId())) {

				if (entityCache.getResult(
						KaleoTimerImpl.class, kaleoTimer.getPrimaryKey()) ==
							null) {

					cacheResult(kaleoTimer);
				}
			}
		}
	}

	/**
	 * Creates a new kaleo timer with the primary key. Does not add the kaleo timer to the database.
	 *
	 * @param kaleoTimerId the primary key for the new kaleo timer
	 * @return the new kaleo timer
	 */
	@Override
	public KaleoTimer create(long kaleoTimerId) {
		KaleoTimer kaleoTimer = new KaleoTimerImpl();

		kaleoTimer.setNew(true);
		kaleoTimer.setPrimaryKey(kaleoTimerId);

		kaleoTimer.setCompanyId(CompanyThreadLocal.getCompanyId());

		return kaleoTimer;
	}

	/**
	 * Removes the kaleo timer with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTimerId the primary key of the kaleo timer
	 * @return the kaleo timer that was removed
	 * @throws NoSuchTimerException if a kaleo timer with the primary key could not be found
	 */
	@Override
	public KaleoTimer remove(long kaleoTimerId) throws NoSuchTimerException {
		return remove((Serializable)kaleoTimerId);
	}

	@Override
	protected KaleoTimer removeImpl(KaleoTimer kaleoTimer) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(kaleoTimer)) {
				kaleoTimer = (KaleoTimer)session.get(
					KaleoTimerImpl.class, kaleoTimer.getPrimaryKeyObj());
			}

			if ((kaleoTimer != null) &&
				ctPersistenceHelper.isRemove(kaleoTimer)) {

				session.delete(kaleoTimer);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (kaleoTimer != null) {
			clearCache(kaleoTimer);
		}

		return kaleoTimer;
	}

	@Override
	public KaleoTimer updateImpl(KaleoTimer kaleoTimer) {
		boolean isNew = kaleoTimer.isNew();

		if (!(kaleoTimer instanceof KaleoTimerModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(kaleoTimer.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(kaleoTimer);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in kaleoTimer proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom KaleoTimer implementation " +
					kaleoTimer.getClass());
		}

		KaleoTimerModelImpl kaleoTimerModelImpl =
			(KaleoTimerModelImpl)kaleoTimer;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (kaleoTimer.getCreateDate() == null)) {
			if (serviceContext == null) {
				kaleoTimer.setCreateDate(date);
			}
			else {
				kaleoTimer.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!kaleoTimerModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				kaleoTimer.setModifiedDate(date);
			}
			else {
				kaleoTimer.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(kaleoTimer)) {
				if (!isNew) {
					session.evict(
						KaleoTimerImpl.class, kaleoTimer.getPrimaryKeyObj());
				}

				session.save(kaleoTimer);
			}
			else {
				kaleoTimer = (KaleoTimer)session.merge(kaleoTimer);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			KaleoTimerImpl.class, kaleoTimerModelImpl, false, true);

		if (isNew) {
			kaleoTimer.setNew(false);
		}

		kaleoTimer.resetOriginalValues();

		return kaleoTimer;
	}

	/**
	 * Returns the kaleo timer with the primary key or throws a <code>NoSuchTimerException</code> if it could not be found.
	 *
	 * @param kaleoTimerId the primary key of the kaleo timer
	 * @return the kaleo timer
	 * @throws NoSuchTimerException if a kaleo timer with the primary key could not be found
	 */
	@Override
	public KaleoTimer findByPrimaryKey(long kaleoTimerId)
		throws NoSuchTimerException {

		return findByPrimaryKey((Serializable)kaleoTimerId);
	}

	@Override
	protected CTPersistenceHelper getCTPersistenceHelper() {
		return ctPersistenceHelper;
	}

	/**
	 * Returns the kaleo timer with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoTimerId the primary key of the kaleo timer
	 * @return the kaleo timer, or <code>null</code> if a kaleo timer with the primary key could not be found
	 */
	@Override
	public KaleoTimer fetchByPrimaryKey(long kaleoTimerId) {
		return fetchByPrimaryKey((Serializable)kaleoTimerId);
	}

	/**
	 * Returns all the kaleo timers.
	 *
	 * @return the kaleo timers
	 */
	@Override
	public List<KaleoTimer> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo timers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTimerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo timers
	 * @param end the upper bound of the range of kaleo timers (not inclusive)
	 * @return the range of kaleo timers
	 */
	@Override
	public List<KaleoTimer> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo timers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTimerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo timers
	 * @param end the upper bound of the range of kaleo timers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo timers
	 */
	@Override
	public List<KaleoTimer> findAll(
		int start, int end, OrderByComparator<KaleoTimer> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo timers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTimerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo timers
	 * @param end the upper bound of the range of kaleo timers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of kaleo timers
	 */
	@Override
	public List<KaleoTimer> findAll(
		int start, int end, OrderByComparator<KaleoTimer> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					KaleoTimer.class)) {

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

			List<KaleoTimer> list = null;

			if (useFinderCache) {
				list = (List<KaleoTimer>)finderCache.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_KALEOTIMER);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_KALEOTIMER;

					sql = sql.concat(KaleoTimerModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<KaleoTimer>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						finderCache.putResult(finderPath, finderArgs, list);
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
	 * Removes all the kaleo timers from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (KaleoTimer kaleoTimer : findAll()) {
			remove(kaleoTimer);
		}
	}

	/**
	 * Returns the number of kaleo timers.
	 *
	 * @return the number of kaleo timers
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					KaleoTimer.class)) {

			Long count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(_SQL_COUNT_KALEOTIMER);

					count = (Long)query.uniqueResult();

					finderCache.putResult(
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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "kaleoTimerId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_KALEOTIMER;
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
		return KaleoTimerModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "KaleoTimer";
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
		Set<String> ctIgnoreColumnNames = new HashSet<String>();
		Set<String> ctMergeColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctMergeColumnNames.add("kaleoClassName");
		ctMergeColumnNames.add("kaleoClassPK");
		ctMergeColumnNames.add("kaleoDefinitionId");
		ctMergeColumnNames.add("kaleoDefinitionVersionId");
		ctMergeColumnNames.add("name");
		ctMergeColumnNames.add("blocking");
		ctMergeColumnNames.add("description");
		ctMergeColumnNames.add("duration");
		ctMergeColumnNames.add("scale");
		ctMergeColumnNames.add("recurrenceDuration");
		ctMergeColumnNames.add("recurrenceScale");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("kaleoTimerId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);
	}

	/**
	 * Initializes the kaleo timer persistence.
	 */
	@Activate
	public void activate() {
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

		_finderPathWithPaginationFindByKCN_KCPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByKCN_KCPK",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"kaleoClassName", "kaleoClassPK"}, true);

		_finderPathWithoutPaginationFindByKCN_KCPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByKCN_KCPK",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"kaleoClassName", "kaleoClassPK"}, true);

		_finderPathCountByKCN_KCPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByKCN_KCPK",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"kaleoClassName", "kaleoClassPK"}, false);

		_collectionPersistenceFinderByKCN_KCPK =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByKCN_KCPK,
				_finderPathWithoutPaginationFindByKCN_KCPK,
				_finderPathCountByKCN_KCPK, _SQL_SELECT_KALEOTIMER_WHERE,
				_SQL_COUNT_KALEOTIMER_WHERE, KaleoTimerModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"kaleoTimer.", "kaleoClassName", FinderColumn.Type.STRING,
					"=", true, false, KaleoTimer::getKaleoClassName),
				new FinderColumn<>(
					"kaleoTimer.", "kaleoClassPK", FinderColumn.Type.LONG, "=",
					true, true, KaleoTimer::getKaleoClassPK));

		_finderPathWithPaginationFindByKCN_KCPK_Blocking = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByKCN_KCPK_Blocking",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"kaleoClassName", "kaleoClassPK", "blocking"}, true);

		_finderPathWithoutPaginationFindByKCN_KCPK_Blocking = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByKCN_KCPK_Blocking",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"kaleoClassName", "kaleoClassPK", "blocking"}, true);

		_finderPathCountByKCN_KCPK_Blocking = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByKCN_KCPK_Blocking",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"kaleoClassName", "kaleoClassPK", "blocking"}, false);

		_collectionPersistenceFinderByKCN_KCPK_Blocking =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByKCN_KCPK_Blocking,
				_finderPathWithoutPaginationFindByKCN_KCPK_Blocking,
				_finderPathCountByKCN_KCPK_Blocking,
				_SQL_SELECT_KALEOTIMER_WHERE, _SQL_COUNT_KALEOTIMER_WHERE,
				KaleoTimerModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"kaleoTimer.", "kaleoClassName", FinderColumn.Type.STRING,
					"=", true, false, KaleoTimer::getKaleoClassName),
				new FinderColumn<>(
					"kaleoTimer.", "kaleoClassPK", FinderColumn.Type.LONG, "=",
					true, false, KaleoTimer::getKaleoClassPK),
				new FinderColumn<>(
					"kaleoTimer.", "blocking", FinderColumn.Type.BOOLEAN, "=",
					true, true, KaleoTimer::isBlocking));

		KaleoTimerUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		KaleoTimerUtil.setPersistence(null);

		entityCache.removeCache(KaleoTimerImpl.class.getName());
	}

	@Override
	@Reference(
		target = KaleoPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = KaleoPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = KaleoPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected CTPersistenceHelper ctPersistenceHelper;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_KALEOTIMER =
		"SELECT kaleoTimer FROM KaleoTimer kaleoTimer";

	private static final String _SQL_SELECT_KALEOTIMER_WHERE =
		"SELECT kaleoTimer FROM KaleoTimer kaleoTimer WHERE ";

	private static final String _SQL_COUNT_KALEOTIMER =
		"SELECT COUNT(kaleoTimer) FROM KaleoTimer kaleoTimer";

	private static final String _SQL_COUNT_KALEOTIMER_WHERE =
		"SELECT COUNT(kaleoTimer) FROM KaleoTimer kaleoTimer WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "kaleoTimer.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No KaleoTimer exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoTimerPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-156598230