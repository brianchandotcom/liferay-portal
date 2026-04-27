/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.engine.fixed.service.persistence.impl;

import com.liferay.commerce.tax.engine.fixed.exception.NoSuchTaxFixedRateException;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateTable;
import com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateImpl;
import com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateModelImpl;
import com.liferay.commerce.tax.engine.fixed.service.persistence.CommerceTaxFixedRatePersistence;
import com.liferay.commerce.tax.engine.fixed.service.persistence.CommerceTaxFixedRateUtil;
import com.liferay.commerce.tax.engine.fixed.service.persistence.impl.constants.CommercePersistenceConstants;
import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.CollectionPersistenceFinder;
import com.liferay.portal.kernel.service.persistence.impl.FinderColumn;
import com.liferay.portal.kernel.service.persistence.impl.UniquePersistenceFinder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the commerce tax fixed rate service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
@Component(service = CommerceTaxFixedRatePersistence.class)
public class CommerceTaxFixedRatePersistenceImpl
	extends BasePersistenceImpl<CommerceTaxFixedRate>
	implements CommerceTaxFixedRatePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceTaxFixedRateUtil</code> to access the commerce tax fixed rate persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceTaxFixedRateImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCPTaxCategoryId;
	private FinderPath _finderPathWithoutPaginationFindByCPTaxCategoryId;
	private FinderPath _finderPathCountByCPTaxCategoryId;
	private CollectionPersistenceFinder<CommerceTaxFixedRate>
		_collectionPersistenceFinderByCPTaxCategoryId;

	/**
	 * Returns all the commerce tax fixed rates where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @return the matching commerce tax fixed rates
	 */
	@Override
	public List<CommerceTaxFixedRate> findByCPTaxCategoryId(
		long CPTaxCategoryId) {

		return findByCPTaxCategoryId(
			CPTaxCategoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tax fixed rates where CPTaxCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxFixedRateModelImpl</code>.
	 * </p>
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param start the lower bound of the range of commerce tax fixed rates
	 * @param end the upper bound of the range of commerce tax fixed rates (not inclusive)
	 * @return the range of matching commerce tax fixed rates
	 */
	@Override
	public List<CommerceTaxFixedRate> findByCPTaxCategoryId(
		long CPTaxCategoryId, int start, int end) {

		return findByCPTaxCategoryId(CPTaxCategoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tax fixed rates where CPTaxCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxFixedRateModelImpl</code>.
	 * </p>
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param start the lower bound of the range of commerce tax fixed rates
	 * @param end the upper bound of the range of commerce tax fixed rates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tax fixed rates
	 */
	@Override
	public List<CommerceTaxFixedRate> findByCPTaxCategoryId(
		long CPTaxCategoryId, int start, int end,
		OrderByComparator<CommerceTaxFixedRate> orderByComparator) {

		return findByCPTaxCategoryId(
			CPTaxCategoryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tax fixed rates where CPTaxCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxFixedRateModelImpl</code>.
	 * </p>
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param start the lower bound of the range of commerce tax fixed rates
	 * @param end the upper bound of the range of commerce tax fixed rates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tax fixed rates
	 */
	@Override
	public List<CommerceTaxFixedRate> findByCPTaxCategoryId(
		long CPTaxCategoryId, int start, int end,
		OrderByComparator<CommerceTaxFixedRate> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByCPTaxCategoryId.find(
			finderCache, new Object[] {CPTaxCategoryId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce tax fixed rate in the ordered set where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax fixed rate
	 * @throws NoSuchTaxFixedRateException if a matching commerce tax fixed rate could not be found
	 */
	@Override
	public CommerceTaxFixedRate findByCPTaxCategoryId_First(
			long CPTaxCategoryId,
			OrderByComparator<CommerceTaxFixedRate> orderByComparator)
		throws NoSuchTaxFixedRateException {

		CommerceTaxFixedRate commerceTaxFixedRate =
			fetchByCPTaxCategoryId_First(CPTaxCategoryId, orderByComparator);

		if (commerceTaxFixedRate != null) {
			return commerceTaxFixedRate;
		}

		throw new NoSuchTaxFixedRateException(
			_collectionPersistenceFinderByCPTaxCategoryId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {CPTaxCategoryId}));
	}

	/**
	 * Returns the first commerce tax fixed rate in the ordered set where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax fixed rate, or <code>null</code> if a matching commerce tax fixed rate could not be found
	 */
	@Override
	public CommerceTaxFixedRate fetchByCPTaxCategoryId_First(
		long CPTaxCategoryId,
		OrderByComparator<CommerceTaxFixedRate> orderByComparator) {

		return _collectionPersistenceFinderByCPTaxCategoryId.fetchFirst(
			finderCache, new Object[] {CPTaxCategoryId}, orderByComparator);
	}

	/**
	 * Removes all the commerce tax fixed rates where CPTaxCategoryId = &#63; from the database.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 */
	@Override
	public void removeByCPTaxCategoryId(long CPTaxCategoryId) {
		_collectionPersistenceFinderByCPTaxCategoryId.remove(
			finderCache, new Object[] {CPTaxCategoryId});
	}

	/**
	 * Returns the number of commerce tax fixed rates where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @return the number of matching commerce tax fixed rates
	 */
	@Override
	public int countByCPTaxCategoryId(long CPTaxCategoryId) {
		return _collectionPersistenceFinderByCPTaxCategoryId.count(
			finderCache, new Object[] {CPTaxCategoryId});
	}

	private FinderPath _finderPathWithPaginationFindByCommerceTaxMethodId;
	private FinderPath _finderPathWithoutPaginationFindByCommerceTaxMethodId;
	private FinderPath _finderPathCountByCommerceTaxMethodId;
	private CollectionPersistenceFinder<CommerceTaxFixedRate>
		_collectionPersistenceFinderByCommerceTaxMethodId;

	/**
	 * Returns all the commerce tax fixed rates where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @return the matching commerce tax fixed rates
	 */
	@Override
	public List<CommerceTaxFixedRate> findByCommerceTaxMethodId(
		long commerceTaxMethodId) {

		return findByCommerceTaxMethodId(
			commerceTaxMethodId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tax fixed rates where commerceTaxMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxFixedRateModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param start the lower bound of the range of commerce tax fixed rates
	 * @param end the upper bound of the range of commerce tax fixed rates (not inclusive)
	 * @return the range of matching commerce tax fixed rates
	 */
	@Override
	public List<CommerceTaxFixedRate> findByCommerceTaxMethodId(
		long commerceTaxMethodId, int start, int end) {

		return findByCommerceTaxMethodId(commerceTaxMethodId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tax fixed rates where commerceTaxMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxFixedRateModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param start the lower bound of the range of commerce tax fixed rates
	 * @param end the upper bound of the range of commerce tax fixed rates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tax fixed rates
	 */
	@Override
	public List<CommerceTaxFixedRate> findByCommerceTaxMethodId(
		long commerceTaxMethodId, int start, int end,
		OrderByComparator<CommerceTaxFixedRate> orderByComparator) {

		return findByCommerceTaxMethodId(
			commerceTaxMethodId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tax fixed rates where commerceTaxMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxFixedRateModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param start the lower bound of the range of commerce tax fixed rates
	 * @param end the upper bound of the range of commerce tax fixed rates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tax fixed rates
	 */
	@Override
	public List<CommerceTaxFixedRate> findByCommerceTaxMethodId(
		long commerceTaxMethodId, int start, int end,
		OrderByComparator<CommerceTaxFixedRate> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByCommerceTaxMethodId.find(
			finderCache, new Object[] {commerceTaxMethodId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce tax fixed rate in the ordered set where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax fixed rate
	 * @throws NoSuchTaxFixedRateException if a matching commerce tax fixed rate could not be found
	 */
	@Override
	public CommerceTaxFixedRate findByCommerceTaxMethodId_First(
			long commerceTaxMethodId,
			OrderByComparator<CommerceTaxFixedRate> orderByComparator)
		throws NoSuchTaxFixedRateException {

		CommerceTaxFixedRate commerceTaxFixedRate =
			fetchByCommerceTaxMethodId_First(
				commerceTaxMethodId, orderByComparator);

		if (commerceTaxFixedRate != null) {
			return commerceTaxFixedRate;
		}

		throw new NoSuchTaxFixedRateException(
			_collectionPersistenceFinderByCommerceTaxMethodId.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {commerceTaxMethodId}));
	}

	/**
	 * Returns the first commerce tax fixed rate in the ordered set where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax fixed rate, or <code>null</code> if a matching commerce tax fixed rate could not be found
	 */
	@Override
	public CommerceTaxFixedRate fetchByCommerceTaxMethodId_First(
		long commerceTaxMethodId,
		OrderByComparator<CommerceTaxFixedRate> orderByComparator) {

		return _collectionPersistenceFinderByCommerceTaxMethodId.fetchFirst(
			finderCache, new Object[] {commerceTaxMethodId}, orderByComparator);
	}

	/**
	 * Removes all the commerce tax fixed rates where commerceTaxMethodId = &#63; from the database.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 */
	@Override
	public void removeByCommerceTaxMethodId(long commerceTaxMethodId) {
		_collectionPersistenceFinderByCommerceTaxMethodId.remove(
			finderCache, new Object[] {commerceTaxMethodId});
	}

	/**
	 * Returns the number of commerce tax fixed rates where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @return the number of matching commerce tax fixed rates
	 */
	@Override
	public int countByCommerceTaxMethodId(long commerceTaxMethodId) {
		return _collectionPersistenceFinderByCommerceTaxMethodId.count(
			finderCache, new Object[] {commerceTaxMethodId});
	}

	private FinderPath _finderPathFetchByC_C;
	private UniquePersistenceFinder<CommerceTaxFixedRate>
		_uniquePersistenceFinderByC_C;

	/**
	 * Returns the commerce tax fixed rate where CPTaxCategoryId = &#63; and commerceTaxMethodId = &#63; or throws a <code>NoSuchTaxFixedRateException</code> if it could not be found.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @return the matching commerce tax fixed rate
	 * @throws NoSuchTaxFixedRateException if a matching commerce tax fixed rate could not be found
	 */
	@Override
	public CommerceTaxFixedRate findByC_C(
			long CPTaxCategoryId, long commerceTaxMethodId)
		throws NoSuchTaxFixedRateException {

		CommerceTaxFixedRate commerceTaxFixedRate = fetchByC_C(
			CPTaxCategoryId, commerceTaxMethodId);

		if (commerceTaxFixedRate == null) {
			String message =
				_uniquePersistenceFinderByC_C.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {CPTaxCategoryId, commerceTaxMethodId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchTaxFixedRateException(message);
		}

		return commerceTaxFixedRate;
	}

	/**
	 * Returns the commerce tax fixed rate where CPTaxCategoryId = &#63; and commerceTaxMethodId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @return the matching commerce tax fixed rate, or <code>null</code> if a matching commerce tax fixed rate could not be found
	 */
	@Override
	public CommerceTaxFixedRate fetchByC_C(
		long CPTaxCategoryId, long commerceTaxMethodId) {

		return fetchByC_C(CPTaxCategoryId, commerceTaxMethodId, true);
	}

	/**
	 * Returns the commerce tax fixed rate where CPTaxCategoryId = &#63; and commerceTaxMethodId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce tax fixed rate, or <code>null</code> if a matching commerce tax fixed rate could not be found
	 */
	@Override
	public CommerceTaxFixedRate fetchByC_C(
		long CPTaxCategoryId, long commerceTaxMethodId,
		boolean useFinderCache) {

		return _uniquePersistenceFinderByC_C.fetch(
			finderCache, new Object[] {CPTaxCategoryId, commerceTaxMethodId},
			useFinderCache);
	}

	/**
	 * Removes the commerce tax fixed rate where CPTaxCategoryId = &#63; and commerceTaxMethodId = &#63; from the database.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @return the commerce tax fixed rate that was removed
	 */
	@Override
	public CommerceTaxFixedRate removeByC_C(
			long CPTaxCategoryId, long commerceTaxMethodId)
		throws NoSuchTaxFixedRateException {

		CommerceTaxFixedRate commerceTaxFixedRate = findByC_C(
			CPTaxCategoryId, commerceTaxMethodId);

		return remove(commerceTaxFixedRate);
	}

	/**
	 * Returns the number of commerce tax fixed rates where CPTaxCategoryId = &#63; and commerceTaxMethodId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @return the number of matching commerce tax fixed rates
	 */
	@Override
	public int countByC_C(long CPTaxCategoryId, long commerceTaxMethodId) {
		return _uniquePersistenceFinderByC_C.count(
			finderCache, new Object[] {CPTaxCategoryId, commerceTaxMethodId});
	}

	public CommerceTaxFixedRatePersistenceImpl() {
		setModelClass(CommerceTaxFixedRate.class);

		setModelImplClass(CommerceTaxFixedRateImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceTaxFixedRateTable.INSTANCE);
	}

	/**
	 * Caches the commerce tax fixed rate in the entity cache if it is enabled.
	 *
	 * @param commerceTaxFixedRate the commerce tax fixed rate
	 */
	@Override
	public void cacheResult(CommerceTaxFixedRate commerceTaxFixedRate) {
		entityCache.putResult(
			CommerceTaxFixedRateImpl.class,
			commerceTaxFixedRate.getPrimaryKey(), commerceTaxFixedRate);

		finderCache.putResult(
			_finderPathFetchByC_C,
			new Object[] {
				commerceTaxFixedRate.getCPTaxCategoryId(),
				commerceTaxFixedRate.getCommerceTaxMethodId()
			},
			commerceTaxFixedRate);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce tax fixed rates in the entity cache if it is enabled.
	 *
	 * @param commerceTaxFixedRates the commerce tax fixed rates
	 */
	@Override
	public void cacheResult(List<CommerceTaxFixedRate> commerceTaxFixedRates) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceTaxFixedRates.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceTaxFixedRate commerceTaxFixedRate :
				commerceTaxFixedRates) {

			if (entityCache.getResult(
					CommerceTaxFixedRateImpl.class,
					commerceTaxFixedRate.getPrimaryKey()) == null) {

				cacheResult(commerceTaxFixedRate);
			}
		}
	}

	/**
	 * Clears the cache for all commerce tax fixed rates.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceTaxFixedRateImpl.class);

		finderCache.clearCache(CommerceTaxFixedRateImpl.class);
	}

	/**
	 * Clears the cache for the commerce tax fixed rate.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceTaxFixedRate commerceTaxFixedRate) {
		entityCache.removeResult(
			CommerceTaxFixedRateImpl.class, commerceTaxFixedRate);
	}

	@Override
	public void clearCache(List<CommerceTaxFixedRate> commerceTaxFixedRates) {
		for (CommerceTaxFixedRate commerceTaxFixedRate :
				commerceTaxFixedRates) {

			entityCache.removeResult(
				CommerceTaxFixedRateImpl.class, commerceTaxFixedRate);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceTaxFixedRateImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceTaxFixedRateImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceTaxFixedRateModelImpl commerceTaxFixedRateModelImpl) {

		Object[] args = new Object[] {
			commerceTaxFixedRateModelImpl.getCPTaxCategoryId(),
			commerceTaxFixedRateModelImpl.getCommerceTaxMethodId()
		};

		finderCache.putResult(
			_finderPathFetchByC_C, args, commerceTaxFixedRateModelImpl);
	}

	/**
	 * Creates a new commerce tax fixed rate with the primary key. Does not add the commerce tax fixed rate to the database.
	 *
	 * @param commerceTaxFixedRateId the primary key for the new commerce tax fixed rate
	 * @return the new commerce tax fixed rate
	 */
	@Override
	public CommerceTaxFixedRate create(long commerceTaxFixedRateId) {
		CommerceTaxFixedRate commerceTaxFixedRate =
			new CommerceTaxFixedRateImpl();

		commerceTaxFixedRate.setNew(true);
		commerceTaxFixedRate.setPrimaryKey(commerceTaxFixedRateId);

		commerceTaxFixedRate.setCompanyId(CompanyThreadLocal.getCompanyId());

		return commerceTaxFixedRate;
	}

	/**
	 * Removes the commerce tax fixed rate with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceTaxFixedRateId the primary key of the commerce tax fixed rate
	 * @return the commerce tax fixed rate that was removed
	 * @throws NoSuchTaxFixedRateException if a commerce tax fixed rate with the primary key could not be found
	 */
	@Override
	public CommerceTaxFixedRate remove(long commerceTaxFixedRateId)
		throws NoSuchTaxFixedRateException {

		return remove((Serializable)commerceTaxFixedRateId);
	}

	/**
	 * Removes the commerce tax fixed rate with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce tax fixed rate
	 * @return the commerce tax fixed rate that was removed
	 * @throws NoSuchTaxFixedRateException if a commerce tax fixed rate with the primary key could not be found
	 */
	@Override
	public CommerceTaxFixedRate remove(Serializable primaryKey)
		throws NoSuchTaxFixedRateException {

		Session session = null;

		try {
			session = openSession();

			CommerceTaxFixedRate commerceTaxFixedRate =
				(CommerceTaxFixedRate)session.get(
					CommerceTaxFixedRateImpl.class, primaryKey);

			if (commerceTaxFixedRate == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTaxFixedRateException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceTaxFixedRate);
		}
		catch (NoSuchTaxFixedRateException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected CommerceTaxFixedRate removeImpl(
		CommerceTaxFixedRate commerceTaxFixedRate) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceTaxFixedRate)) {
				commerceTaxFixedRate = (CommerceTaxFixedRate)session.get(
					CommerceTaxFixedRateImpl.class,
					commerceTaxFixedRate.getPrimaryKeyObj());
			}

			if (commerceTaxFixedRate != null) {
				session.delete(commerceTaxFixedRate);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceTaxFixedRate != null) {
			clearCache(commerceTaxFixedRate);
		}

		return commerceTaxFixedRate;
	}

	@Override
	public CommerceTaxFixedRate updateImpl(
		CommerceTaxFixedRate commerceTaxFixedRate) {

		boolean isNew = commerceTaxFixedRate.isNew();

		if (!(commerceTaxFixedRate instanceof CommerceTaxFixedRateModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceTaxFixedRate.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceTaxFixedRate);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceTaxFixedRate proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceTaxFixedRate implementation " +
					commerceTaxFixedRate.getClass());
		}

		CommerceTaxFixedRateModelImpl commerceTaxFixedRateModelImpl =
			(CommerceTaxFixedRateModelImpl)commerceTaxFixedRate;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceTaxFixedRate.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceTaxFixedRate.setCreateDate(date);
			}
			else {
				commerceTaxFixedRate.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceTaxFixedRateModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceTaxFixedRate.setModifiedDate(date);
			}
			else {
				commerceTaxFixedRate.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceTaxFixedRate);
			}
			else {
				commerceTaxFixedRate = (CommerceTaxFixedRate)session.merge(
					commerceTaxFixedRate);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceTaxFixedRateImpl.class, commerceTaxFixedRateModelImpl,
			false, true);

		cacheUniqueFindersCache(commerceTaxFixedRateModelImpl);

		if (isNew) {
			commerceTaxFixedRate.setNew(false);
		}

		commerceTaxFixedRate.resetOriginalValues();

		return commerceTaxFixedRate;
	}

	/**
	 * Returns the commerce tax fixed rate with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce tax fixed rate
	 * @return the commerce tax fixed rate
	 * @throws NoSuchTaxFixedRateException if a commerce tax fixed rate with the primary key could not be found
	 */
	@Override
	public CommerceTaxFixedRate findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTaxFixedRateException {

		CommerceTaxFixedRate commerceTaxFixedRate = fetchByPrimaryKey(
			primaryKey);

		if (commerceTaxFixedRate == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTaxFixedRateException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceTaxFixedRate;
	}

	/**
	 * Returns the commerce tax fixed rate with the primary key or throws a <code>NoSuchTaxFixedRateException</code> if it could not be found.
	 *
	 * @param commerceTaxFixedRateId the primary key of the commerce tax fixed rate
	 * @return the commerce tax fixed rate
	 * @throws NoSuchTaxFixedRateException if a commerce tax fixed rate with the primary key could not be found
	 */
	@Override
	public CommerceTaxFixedRate findByPrimaryKey(long commerceTaxFixedRateId)
		throws NoSuchTaxFixedRateException {

		return findByPrimaryKey((Serializable)commerceTaxFixedRateId);
	}

	/**
	 * Returns the commerce tax fixed rate with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceTaxFixedRateId the primary key of the commerce tax fixed rate
	 * @return the commerce tax fixed rate, or <code>null</code> if a commerce tax fixed rate with the primary key could not be found
	 */
	@Override
	public CommerceTaxFixedRate fetchByPrimaryKey(long commerceTaxFixedRateId) {
		return fetchByPrimaryKey((Serializable)commerceTaxFixedRateId);
	}

	/**
	 * Returns all the commerce tax fixed rates.
	 *
	 * @return the commerce tax fixed rates
	 */
	@Override
	public List<CommerceTaxFixedRate> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tax fixed rates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxFixedRateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax fixed rates
	 * @param end the upper bound of the range of commerce tax fixed rates (not inclusive)
	 * @return the range of commerce tax fixed rates
	 */
	@Override
	public List<CommerceTaxFixedRate> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tax fixed rates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxFixedRateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax fixed rates
	 * @param end the upper bound of the range of commerce tax fixed rates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce tax fixed rates
	 */
	@Override
	public List<CommerceTaxFixedRate> findAll(
		int start, int end,
		OrderByComparator<CommerceTaxFixedRate> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tax fixed rates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxFixedRateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax fixed rates
	 * @param end the upper bound of the range of commerce tax fixed rates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce tax fixed rates
	 */
	@Override
	public List<CommerceTaxFixedRate> findAll(
		int start, int end,
		OrderByComparator<CommerceTaxFixedRate> orderByComparator,
		boolean useFinderCache) {

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

		List<CommerceTaxFixedRate> list = null;

		if (useFinderCache) {
			list = (List<CommerceTaxFixedRate>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCETAXFIXEDRATE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCETAXFIXEDRATE;

				sql = sql.concat(CommerceTaxFixedRateModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceTaxFixedRate>)QueryUtil.list(
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

	/**
	 * Removes all the commerce tax fixed rates from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceTaxFixedRate commerceTaxFixedRate : findAll()) {
			remove(commerceTaxFixedRate);
		}
	}

	/**
	 * Returns the number of commerce tax fixed rates.
	 *
	 * @return the number of commerce tax fixed rates
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_COMMERCETAXFIXEDRATE);

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

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "commerceTaxFixedRateId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCETAXFIXEDRATE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceTaxFixedRateModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce tax fixed rate persistence.
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

		_finderPathWithPaginationFindByCPTaxCategoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCPTaxCategoryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"CPTaxCategoryId"}, true);

		_finderPathWithoutPaginationFindByCPTaxCategoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCPTaxCategoryId",
			new String[] {Long.class.getName()},
			new String[] {"CPTaxCategoryId"}, true);

		_finderPathCountByCPTaxCategoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPTaxCategoryId",
			new String[] {Long.class.getName()},
			new String[] {"CPTaxCategoryId"}, false);

		_collectionPersistenceFinderByCPTaxCategoryId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByCPTaxCategoryId,
				_finderPathWithoutPaginationFindByCPTaxCategoryId,
				_finderPathCountByCPTaxCategoryId,
				_SQL_SELECT_COMMERCETAXFIXEDRATE_WHERE,
				_SQL_COUNT_COMMERCETAXFIXEDRATE_WHERE,
				CommerceTaxFixedRateModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"commerceTaxFixedRate.", "CPTaxCategoryId",
					FinderColumn.Type.LONG, "=", true, true,
					CommerceTaxFixedRate::getCPTaxCategoryId));

		_finderPathWithPaginationFindByCommerceTaxMethodId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommerceTaxMethodId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commerceTaxMethodId"}, true);

		_finderPathWithoutPaginationFindByCommerceTaxMethodId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceTaxMethodId", new String[] {Long.class.getName()},
			new String[] {"commerceTaxMethodId"}, true);

		_finderPathCountByCommerceTaxMethodId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceTaxMethodId", new String[] {Long.class.getName()},
			new String[] {"commerceTaxMethodId"}, false);

		_collectionPersistenceFinderByCommerceTaxMethodId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByCommerceTaxMethodId,
				_finderPathWithoutPaginationFindByCommerceTaxMethodId,
				_finderPathCountByCommerceTaxMethodId,
				_SQL_SELECT_COMMERCETAXFIXEDRATE_WHERE,
				_SQL_COUNT_COMMERCETAXFIXEDRATE_WHERE,
				CommerceTaxFixedRateModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"commerceTaxFixedRate.", "commerceTaxMethodId",
					FinderColumn.Type.LONG, "=", true, true,
					CommerceTaxFixedRate::getCommerceTaxMethodId));

		_finderPathFetchByC_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"CPTaxCategoryId", "commerceTaxMethodId"}, true);

		_uniquePersistenceFinderByC_C = new UniquePersistenceFinder<>(
			this, _finderPathFetchByC_C, _SQL_SELECT_COMMERCETAXFIXEDRATE_WHERE,
			new FinderColumn<>(
				"commerceTaxFixedRate.", "CPTaxCategoryId",
				FinderColumn.Type.LONG, "=", true, false,
				CommerceTaxFixedRate::getCPTaxCategoryId),
			new FinderColumn<>(
				"commerceTaxFixedRate.", "commerceTaxMethodId",
				FinderColumn.Type.LONG, "=", true, true,
				CommerceTaxFixedRate::getCommerceTaxMethodId));

		CommerceTaxFixedRateUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CommerceTaxFixedRateUtil.setPersistence(null);

		entityCache.removeCache(CommerceTaxFixedRateImpl.class.getName());
	}

	@Override
	@Reference(
		target = CommercePersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = CommercePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CommercePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCETAXFIXEDRATE =
		"SELECT commerceTaxFixedRate FROM CommerceTaxFixedRate commerceTaxFixedRate";

	private static final String _SQL_SELECT_COMMERCETAXFIXEDRATE_WHERE =
		"SELECT commerceTaxFixedRate FROM CommerceTaxFixedRate commerceTaxFixedRate WHERE ";

	private static final String _SQL_COUNT_COMMERCETAXFIXEDRATE =
		"SELECT COUNT(commerceTaxFixedRate) FROM CommerceTaxFixedRate commerceTaxFixedRate";

	private static final String _SQL_COUNT_COMMERCETAXFIXEDRATE_WHERE =
		"SELECT COUNT(commerceTaxFixedRate) FROM CommerceTaxFixedRate commerceTaxFixedRate WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceTaxFixedRate.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceTaxFixedRate exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceTaxFixedRate exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceTaxFixedRatePersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1209370205