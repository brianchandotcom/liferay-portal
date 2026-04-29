/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.service.persistence.impl;

import com.liferay.commerce.pricing.exception.NoSuchPricingClassCPDefinitionRelException;
import com.liferay.commerce.pricing.model.CommercePricingClassCPDefinitionRel;
import com.liferay.commerce.pricing.model.CommercePricingClassCPDefinitionRelTable;
import com.liferay.commerce.pricing.model.impl.CommercePricingClassCPDefinitionRelImpl;
import com.liferay.commerce.pricing.model.impl.CommercePricingClassCPDefinitionRelModelImpl;
import com.liferay.commerce.pricing.service.persistence.CommercePricingClassCPDefinitionRelPersistence;
import com.liferay.commerce.pricing.service.persistence.CommercePricingClassCPDefinitionRelUtil;
import com.liferay.commerce.pricing.service.persistence.impl.constants.CommercePersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.impl.UniquePersistenceFinder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the commerce pricing class cp definition rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Riccardo Alberti
 * @generated
 */
@Component(service = CommercePricingClassCPDefinitionRelPersistence.class)
public class CommercePricingClassCPDefinitionRelPersistenceImpl
	extends BasePersistenceImpl
		<CommercePricingClassCPDefinitionRel,
		 NoSuchPricingClassCPDefinitionRelException>
	implements CommercePricingClassCPDefinitionRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommercePricingClassCPDefinitionRelUtil</code> to access the commerce pricing class cp definition rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommercePricingClassCPDefinitionRelImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCommercePricingClassId;
	private FinderPath _finderPathWithoutPaginationFindByCommercePricingClassId;
	private FinderPath _finderPathCountByCommercePricingClassId;
	private CollectionPersistenceFinder<CommercePricingClassCPDefinitionRel>
		_collectionPersistenceFinderByCommercePricingClassId;

	/**
	 * Returns all the commerce pricing class cp definition rels where commercePricingClassId = &#63;.
	 *
	 * @param commercePricingClassId the commerce pricing class ID
	 * @return the matching commerce pricing class cp definition rels
	 */
	@Override
	public List<CommercePricingClassCPDefinitionRel>
		findByCommercePricingClassId(long commercePricingClassId) {

		return findByCommercePricingClassId(
			commercePricingClassId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce pricing class cp definition rels where commercePricingClassId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassCPDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePricingClassId the commerce pricing class ID
	 * @param start the lower bound of the range of commerce pricing class cp definition rels
	 * @param end the upper bound of the range of commerce pricing class cp definition rels (not inclusive)
	 * @return the range of matching commerce pricing class cp definition rels
	 */
	@Override
	public List<CommercePricingClassCPDefinitionRel>
		findByCommercePricingClassId(
			long commercePricingClassId, int start, int end) {

		return findByCommercePricingClassId(
			commercePricingClassId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce pricing class cp definition rels where commercePricingClassId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassCPDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePricingClassId the commerce pricing class ID
	 * @param start the lower bound of the range of commerce pricing class cp definition rels
	 * @param end the upper bound of the range of commerce pricing class cp definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce pricing class cp definition rels
	 */
	@Override
	public List<CommercePricingClassCPDefinitionRel>
		findByCommercePricingClassId(
			long commercePricingClassId, int start, int end,
			OrderByComparator<CommercePricingClassCPDefinitionRel>
				orderByComparator) {

		return findByCommercePricingClassId(
			commercePricingClassId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce pricing class cp definition rels where commercePricingClassId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassCPDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePricingClassId the commerce pricing class ID
	 * @param start the lower bound of the range of commerce pricing class cp definition rels
	 * @param end the upper bound of the range of commerce pricing class cp definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce pricing class cp definition rels
	 */
	@Override
	public List<CommercePricingClassCPDefinitionRel>
		findByCommercePricingClassId(
			long commercePricingClassId, int start, int end,
			OrderByComparator<CommercePricingClassCPDefinitionRel>
				orderByComparator,
			boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CommercePricingClassCPDefinitionRel.class)) {

			return _collectionPersistenceFinderByCommercePricingClassId.find(
				finderCache, new Object[] {commercePricingClassId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first commerce pricing class cp definition rel in the ordered set where commercePricingClassId = &#63;.
	 *
	 * @param commercePricingClassId the commerce pricing class ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce pricing class cp definition rel
	 * @throws NoSuchPricingClassCPDefinitionRelException if a matching commerce pricing class cp definition rel could not be found
	 */
	@Override
	public CommercePricingClassCPDefinitionRel
			findByCommercePricingClassId_First(
				long commercePricingClassId,
				OrderByComparator<CommercePricingClassCPDefinitionRel>
					orderByComparator)
		throws NoSuchPricingClassCPDefinitionRelException {

		CommercePricingClassCPDefinitionRel
			commercePricingClassCPDefinitionRel =
				fetchByCommercePricingClassId_First(
					commercePricingClassId, orderByComparator);

		if (commercePricingClassCPDefinitionRel != null) {
			return commercePricingClassCPDefinitionRel;
		}

		throw new NoSuchPricingClassCPDefinitionRelException(
			_collectionPersistenceFinderByCommercePricingClassId.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {commercePricingClassId}));
	}

	/**
	 * Returns the first commerce pricing class cp definition rel in the ordered set where commercePricingClassId = &#63;.
	 *
	 * @param commercePricingClassId the commerce pricing class ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce pricing class cp definition rel, or <code>null</code> if a matching commerce pricing class cp definition rel could not be found
	 */
	@Override
	public CommercePricingClassCPDefinitionRel
		fetchByCommercePricingClassId_First(
			long commercePricingClassId,
			OrderByComparator<CommercePricingClassCPDefinitionRel>
				orderByComparator) {

		return _collectionPersistenceFinderByCommercePricingClassId.fetchFirst(
			finderCache, new Object[] {commercePricingClassId},
			orderByComparator);
	}

	/**
	 * Removes all the commerce pricing class cp definition rels where commercePricingClassId = &#63; from the database.
	 *
	 * @param commercePricingClassId the commerce pricing class ID
	 */
	@Override
	public void removeByCommercePricingClassId(long commercePricingClassId) {
		_collectionPersistenceFinderByCommercePricingClassId.remove(
			finderCache, new Object[] {commercePricingClassId});
	}

	/**
	 * Returns the number of commerce pricing class cp definition rels where commercePricingClassId = &#63;.
	 *
	 * @param commercePricingClassId the commerce pricing class ID
	 * @return the number of matching commerce pricing class cp definition rels
	 */
	@Override
	public int countByCommercePricingClassId(long commercePricingClassId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CommercePricingClassCPDefinitionRel.class)) {

			return _collectionPersistenceFinderByCommercePricingClassId.count(
				finderCache, new Object[] {commercePricingClassId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByCPDefinitionId;
	private FinderPath _finderPathWithoutPaginationFindByCPDefinitionId;
	private FinderPath _finderPathCountByCPDefinitionId;
	private CollectionPersistenceFinder<CommercePricingClassCPDefinitionRel>
		_collectionPersistenceFinderByCPDefinitionId;

	/**
	 * Returns all the commerce pricing class cp definition rels where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching commerce pricing class cp definition rels
	 */
	@Override
	public List<CommercePricingClassCPDefinitionRel> findByCPDefinitionId(
		long CPDefinitionId) {

		return findByCPDefinitionId(
			CPDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce pricing class cp definition rels where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassCPDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of commerce pricing class cp definition rels
	 * @param end the upper bound of the range of commerce pricing class cp definition rels (not inclusive)
	 * @return the range of matching commerce pricing class cp definition rels
	 */
	@Override
	public List<CommercePricingClassCPDefinitionRel> findByCPDefinitionId(
		long CPDefinitionId, int start, int end) {

		return findByCPDefinitionId(CPDefinitionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce pricing class cp definition rels where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassCPDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of commerce pricing class cp definition rels
	 * @param end the upper bound of the range of commerce pricing class cp definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce pricing class cp definition rels
	 */
	@Override
	public List<CommercePricingClassCPDefinitionRel> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CommercePricingClassCPDefinitionRel>
			orderByComparator) {

		return findByCPDefinitionId(
			CPDefinitionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce pricing class cp definition rels where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassCPDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of commerce pricing class cp definition rels
	 * @param end the upper bound of the range of commerce pricing class cp definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce pricing class cp definition rels
	 */
	@Override
	public List<CommercePricingClassCPDefinitionRel> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CommercePricingClassCPDefinitionRel>
			orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CommercePricingClassCPDefinitionRel.class)) {

			return _collectionPersistenceFinderByCPDefinitionId.find(
				finderCache, new Object[] {CPDefinitionId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first commerce pricing class cp definition rel in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce pricing class cp definition rel
	 * @throws NoSuchPricingClassCPDefinitionRelException if a matching commerce pricing class cp definition rel could not be found
	 */
	@Override
	public CommercePricingClassCPDefinitionRel findByCPDefinitionId_First(
			long CPDefinitionId,
			OrderByComparator<CommercePricingClassCPDefinitionRel>
				orderByComparator)
		throws NoSuchPricingClassCPDefinitionRelException {

		CommercePricingClassCPDefinitionRel
			commercePricingClassCPDefinitionRel = fetchByCPDefinitionId_First(
				CPDefinitionId, orderByComparator);

		if (commercePricingClassCPDefinitionRel != null) {
			return commercePricingClassCPDefinitionRel;
		}

		throw new NoSuchPricingClassCPDefinitionRelException(
			_collectionPersistenceFinderByCPDefinitionId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {CPDefinitionId}));
	}

	/**
	 * Returns the first commerce pricing class cp definition rel in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce pricing class cp definition rel, or <code>null</code> if a matching commerce pricing class cp definition rel could not be found
	 */
	@Override
	public CommercePricingClassCPDefinitionRel fetchByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CommercePricingClassCPDefinitionRel>
			orderByComparator) {

		return _collectionPersistenceFinderByCPDefinitionId.fetchFirst(
			finderCache, new Object[] {CPDefinitionId}, orderByComparator);
	}

	/**
	 * Removes all the commerce pricing class cp definition rels where CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 */
	@Override
	public void removeByCPDefinitionId(long CPDefinitionId) {
		_collectionPersistenceFinderByCPDefinitionId.remove(
			finderCache, new Object[] {CPDefinitionId});
	}

	/**
	 * Returns the number of commerce pricing class cp definition rels where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching commerce pricing class cp definition rels
	 */
	@Override
	public int countByCPDefinitionId(long CPDefinitionId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CommercePricingClassCPDefinitionRel.class)) {

			return _collectionPersistenceFinderByCPDefinitionId.count(
				finderCache, new Object[] {CPDefinitionId});
		}
	}

	private FinderPath _finderPathFetchByC_C;
	private UniquePersistenceFinder<CommercePricingClassCPDefinitionRel>
		_uniquePersistenceFinderByC_C;

	/**
	 * Returns the commerce pricing class cp definition rel where commercePricingClassId = &#63; and CPDefinitionId = &#63; or throws a <code>NoSuchPricingClassCPDefinitionRelException</code> if it could not be found.
	 *
	 * @param commercePricingClassId the commerce pricing class ID
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching commerce pricing class cp definition rel
	 * @throws NoSuchPricingClassCPDefinitionRelException if a matching commerce pricing class cp definition rel could not be found
	 */
	@Override
	public CommercePricingClassCPDefinitionRel findByC_C(
			long commercePricingClassId, long CPDefinitionId)
		throws NoSuchPricingClassCPDefinitionRelException {

		CommercePricingClassCPDefinitionRel
			commercePricingClassCPDefinitionRel = fetchByC_C(
				commercePricingClassId, CPDefinitionId);

		if (commercePricingClassCPDefinitionRel == null) {
			String message =
				_uniquePersistenceFinderByC_C.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {commercePricingClassId, CPDefinitionId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchPricingClassCPDefinitionRelException(message);
		}

		return commercePricingClassCPDefinitionRel;
	}

	/**
	 * Returns the commerce pricing class cp definition rel where commercePricingClassId = &#63; and CPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commercePricingClassId the commerce pricing class ID
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching commerce pricing class cp definition rel, or <code>null</code> if a matching commerce pricing class cp definition rel could not be found
	 */
	@Override
	public CommercePricingClassCPDefinitionRel fetchByC_C(
		long commercePricingClassId, long CPDefinitionId) {

		return fetchByC_C(commercePricingClassId, CPDefinitionId, true);
	}

	/**
	 * Returns the commerce pricing class cp definition rel where commercePricingClassId = &#63; and CPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commercePricingClassId the commerce pricing class ID
	 * @param CPDefinitionId the cp definition ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce pricing class cp definition rel, or <code>null</code> if a matching commerce pricing class cp definition rel could not be found
	 */
	@Override
	public CommercePricingClassCPDefinitionRel fetchByC_C(
		long commercePricingClassId, long CPDefinitionId,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CommercePricingClassCPDefinitionRel.class)) {

			return _uniquePersistenceFinderByC_C.fetch(
				finderCache,
				new Object[] {commercePricingClassId, CPDefinitionId},
				useFinderCache);
		}
	}

	/**
	 * Removes the commerce pricing class cp definition rel where commercePricingClassId = &#63; and CPDefinitionId = &#63; from the database.
	 *
	 * @param commercePricingClassId the commerce pricing class ID
	 * @param CPDefinitionId the cp definition ID
	 * @return the commerce pricing class cp definition rel that was removed
	 */
	@Override
	public CommercePricingClassCPDefinitionRel removeByC_C(
			long commercePricingClassId, long CPDefinitionId)
		throws NoSuchPricingClassCPDefinitionRelException {

		CommercePricingClassCPDefinitionRel
			commercePricingClassCPDefinitionRel = findByC_C(
				commercePricingClassId, CPDefinitionId);

		return remove(commercePricingClassCPDefinitionRel);
	}

	/**
	 * Returns the number of commerce pricing class cp definition rels where commercePricingClassId = &#63; and CPDefinitionId = &#63;.
	 *
	 * @param commercePricingClassId the commerce pricing class ID
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching commerce pricing class cp definition rels
	 */
	@Override
	public int countByC_C(long commercePricingClassId, long CPDefinitionId) {
		return _uniquePersistenceFinderByC_C.count(
			finderCache, new Object[] {commercePricingClassId, CPDefinitionId});
	}

	public CommercePricingClassCPDefinitionRelPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"CommercePricingClassCPDefinitionRelId",
			"CPricingClassCPDefinitionRelId");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommercePricingClassCPDefinitionRel.class);

		setModelImplClass(CommercePricingClassCPDefinitionRelImpl.class);
		setModelPKClass(long.class);

		setTable(CommercePricingClassCPDefinitionRelTable.INSTANCE);
	}

	/**
	 * Caches the commerce pricing class cp definition rel in the entity cache if it is enabled.
	 *
	 * @param commercePricingClassCPDefinitionRel the commerce pricing class cp definition rel
	 */
	@Override
	public void cacheResult(
		CommercePricingClassCPDefinitionRel
			commercePricingClassCPDefinitionRel) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					commercePricingClassCPDefinitionRel.getCtCollectionId())) {

			entityCache.putResult(
				CommercePricingClassCPDefinitionRelImpl.class,
				commercePricingClassCPDefinitionRel.getPrimaryKey(),
				commercePricingClassCPDefinitionRel);

			finderCache.putResult(
				_finderPathFetchByC_C,
				new Object[] {
					commercePricingClassCPDefinitionRel.
						getCommercePricingClassId(),
					commercePricingClassCPDefinitionRel.getCPDefinitionId()
				},
				commercePricingClassCPDefinitionRel);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce pricing class cp definition rels in the entity cache if it is enabled.
	 *
	 * @param commercePricingClassCPDefinitionRels the commerce pricing class cp definition rels
	 */
	@Override
	public void cacheResult(
		List<CommercePricingClassCPDefinitionRel>
			commercePricingClassCPDefinitionRels) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commercePricingClassCPDefinitionRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommercePricingClassCPDefinitionRel
				commercePricingClassCPDefinitionRel :
					commercePricingClassCPDefinitionRels) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						commercePricingClassCPDefinitionRel.
							getCtCollectionId())) {

				if (entityCache.getResult(
						CommercePricingClassCPDefinitionRelImpl.class,
						commercePricingClassCPDefinitionRel.getPrimaryKey()) ==
							null) {

					cacheResult(commercePricingClassCPDefinitionRel);
				}
			}
		}
	}

	protected void cacheUniqueFindersCache(
		CommercePricingClassCPDefinitionRelModelImpl
			commercePricingClassCPDefinitionRelModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					commercePricingClassCPDefinitionRelModelImpl.
						getCtCollectionId())) {

			Object[] args = new Object[] {
				commercePricingClassCPDefinitionRelModelImpl.
					getCommercePricingClassId(),
				commercePricingClassCPDefinitionRelModelImpl.getCPDefinitionId()
			};

			finderCache.putResult(
				_finderPathFetchByC_C, args,
				commercePricingClassCPDefinitionRelModelImpl);
		}
	}

	/**
	 * Creates a new commerce pricing class cp definition rel with the primary key. Does not add the commerce pricing class cp definition rel to the database.
	 *
	 * @param CommercePricingClassCPDefinitionRelId the primary key for the new commerce pricing class cp definition rel
	 * @return the new commerce pricing class cp definition rel
	 */
	@Override
	public CommercePricingClassCPDefinitionRel create(
		long CommercePricingClassCPDefinitionRelId) {

		CommercePricingClassCPDefinitionRel
			commercePricingClassCPDefinitionRel =
				new CommercePricingClassCPDefinitionRelImpl();

		commercePricingClassCPDefinitionRel.setNew(true);
		commercePricingClassCPDefinitionRel.setPrimaryKey(
			CommercePricingClassCPDefinitionRelId);

		commercePricingClassCPDefinitionRel.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commercePricingClassCPDefinitionRel;
	}

	/**
	 * Removes the commerce pricing class cp definition rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CommercePricingClassCPDefinitionRelId the primary key of the commerce pricing class cp definition rel
	 * @return the commerce pricing class cp definition rel that was removed
	 * @throws NoSuchPricingClassCPDefinitionRelException if a commerce pricing class cp definition rel with the primary key could not be found
	 */
	@Override
	public CommercePricingClassCPDefinitionRel remove(
			long CommercePricingClassCPDefinitionRelId)
		throws NoSuchPricingClassCPDefinitionRelException {

		return remove((Serializable)CommercePricingClassCPDefinitionRelId);
	}

	@Override
	protected CommercePricingClassCPDefinitionRel removeImpl(
		CommercePricingClassCPDefinitionRel
			commercePricingClassCPDefinitionRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commercePricingClassCPDefinitionRel)) {
				commercePricingClassCPDefinitionRel =
					(CommercePricingClassCPDefinitionRel)session.get(
						CommercePricingClassCPDefinitionRelImpl.class,
						commercePricingClassCPDefinitionRel.getPrimaryKeyObj());
			}

			if ((commercePricingClassCPDefinitionRel != null) &&
				ctPersistenceHelper.isRemove(
					commercePricingClassCPDefinitionRel)) {

				session.delete(commercePricingClassCPDefinitionRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commercePricingClassCPDefinitionRel != null) {
			clearCache(commercePricingClassCPDefinitionRel);
		}

		return commercePricingClassCPDefinitionRel;
	}

	@Override
	public CommercePricingClassCPDefinitionRel updateImpl(
		CommercePricingClassCPDefinitionRel
			commercePricingClassCPDefinitionRel) {

		boolean isNew = commercePricingClassCPDefinitionRel.isNew();

		if (!(commercePricingClassCPDefinitionRel instanceof
				CommercePricingClassCPDefinitionRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commercePricingClassCPDefinitionRel.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commercePricingClassCPDefinitionRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commercePricingClassCPDefinitionRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommercePricingClassCPDefinitionRel implementation " +
					commercePricingClassCPDefinitionRel.getClass());
		}

		CommercePricingClassCPDefinitionRelModelImpl
			commercePricingClassCPDefinitionRelModelImpl =
				(CommercePricingClassCPDefinitionRelModelImpl)
					commercePricingClassCPDefinitionRel;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew &&
			(commercePricingClassCPDefinitionRel.getCreateDate() == null)) {

			if (serviceContext == null) {
				commercePricingClassCPDefinitionRel.setCreateDate(date);
			}
			else {
				commercePricingClassCPDefinitionRel.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commercePricingClassCPDefinitionRelModelImpl.
				hasSetModifiedDate()) {

			if (serviceContext == null) {
				commercePricingClassCPDefinitionRel.setModifiedDate(date);
			}
			else {
				commercePricingClassCPDefinitionRel.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(
					commercePricingClassCPDefinitionRel)) {

				if (!isNew) {
					session.evict(
						CommercePricingClassCPDefinitionRelImpl.class,
						commercePricingClassCPDefinitionRel.getPrimaryKeyObj());
				}

				session.save(commercePricingClassCPDefinitionRel);
			}
			else {
				commercePricingClassCPDefinitionRel =
					(CommercePricingClassCPDefinitionRel)session.merge(
						commercePricingClassCPDefinitionRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommercePricingClassCPDefinitionRelImpl.class,
			commercePricingClassCPDefinitionRelModelImpl, false, true);

		cacheUniqueFindersCache(commercePricingClassCPDefinitionRelModelImpl);

		if (isNew) {
			commercePricingClassCPDefinitionRel.setNew(false);
		}

		commercePricingClassCPDefinitionRel.resetOriginalValues();

		return commercePricingClassCPDefinitionRel;
	}

	/**
	 * Returns the commerce pricing class cp definition rel with the primary key or throws a <code>NoSuchPricingClassCPDefinitionRelException</code> if it could not be found.
	 *
	 * @param CommercePricingClassCPDefinitionRelId the primary key of the commerce pricing class cp definition rel
	 * @return the commerce pricing class cp definition rel
	 * @throws NoSuchPricingClassCPDefinitionRelException if a commerce pricing class cp definition rel with the primary key could not be found
	 */
	@Override
	public CommercePricingClassCPDefinitionRel findByPrimaryKey(
			long CommercePricingClassCPDefinitionRelId)
		throws NoSuchPricingClassCPDefinitionRelException {

		return findByPrimaryKey(
			(Serializable)CommercePricingClassCPDefinitionRelId);
	}

	/**
	 * Returns the commerce pricing class cp definition rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce pricing class cp definition rel
	 * @return the commerce pricing class cp definition rel, or <code>null</code> if a commerce pricing class cp definition rel with the primary key could not be found
	 */
	@Override
	public CommercePricingClassCPDefinitionRel fetchByPrimaryKey(
		Serializable primaryKey) {

		if (ctPersistenceHelper.isProductionMode(
				CommercePricingClassCPDefinitionRel.class, primaryKey)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKey(primaryKey);
			}
		}

		CommercePricingClassCPDefinitionRel
			commercePricingClassCPDefinitionRel =
				(CommercePricingClassCPDefinitionRel)entityCache.getResult(
					CommercePricingClassCPDefinitionRelImpl.class, primaryKey);

		if (commercePricingClassCPDefinitionRel != null) {
			return commercePricingClassCPDefinitionRel;
		}

		Session session = null;

		try {
			session = openSession();

			commercePricingClassCPDefinitionRel =
				(CommercePricingClassCPDefinitionRel)session.get(
					CommercePricingClassCPDefinitionRelImpl.class, primaryKey);

			if (commercePricingClassCPDefinitionRel != null) {
				cacheResult(commercePricingClassCPDefinitionRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return commercePricingClassCPDefinitionRel;
	}

	/**
	 * Returns the commerce pricing class cp definition rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CommercePricingClassCPDefinitionRelId the primary key of the commerce pricing class cp definition rel
	 * @return the commerce pricing class cp definition rel, or <code>null</code> if a commerce pricing class cp definition rel with the primary key could not be found
	 */
	@Override
	public CommercePricingClassCPDefinitionRel fetchByPrimaryKey(
		long CommercePricingClassCPDefinitionRelId) {

		return fetchByPrimaryKey(
			(Serializable)CommercePricingClassCPDefinitionRelId);
	}

	@Override
	public Map<Serializable, CommercePricingClassCPDefinitionRel>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(
				CommercePricingClassCPDefinitionRel.class)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKeys(primaryKeys);
			}
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommercePricingClassCPDefinitionRel> map =
			new HashMap<Serializable, CommercePricingClassCPDefinitionRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommercePricingClassCPDefinitionRel
				commercePricingClassCPDefinitionRel = fetchByPrimaryKey(
					primaryKey);

			if (commercePricingClassCPDefinitionRel != null) {
				map.put(primaryKey, commercePricingClassCPDefinitionRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			try (SafeCloseable safeCloseable =
					ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
						CommercePricingClassCPDefinitionRel.class,
						primaryKey)) {

				CommercePricingClassCPDefinitionRel
					commercePricingClassCPDefinitionRel =
						(CommercePricingClassCPDefinitionRel)
							entityCache.getResult(
								CommercePricingClassCPDefinitionRelImpl.class,
								primaryKey);

				if (commercePricingClassCPDefinitionRel == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, commercePricingClassCPDefinitionRel);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		if ((databaseInMaxParameters > 0) &&
			(primaryKeys.size() > databaseInMaxParameters)) {

			Iterator<Serializable> iterator = primaryKeys.iterator();

			while (iterator.hasNext()) {
				Set<Serializable> page = new HashSet<>();

				for (int i = 0;
					 (i < databaseInMaxParameters) && iterator.hasNext(); i++) {

					page.add(iterator.next());
				}

				map.putAll(fetchByPrimaryKeys(page));
			}

			return map;
		}

		StringBundler sb = new StringBundler((primaryKeys.size() * 2) + 1);

		sb.append(getSelectSQL());
		sb.append(" WHERE ");
		sb.append(getPKDBName());
		sb.append(" IN (");

		for (Serializable primaryKey : primaryKeys) {
			sb.append((long)primaryKey);

			sb.append(",");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(")");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query query = session.createQuery(sql);

			for (CommercePricingClassCPDefinitionRel
					commercePricingClassCPDefinitionRel :
						(List<CommercePricingClassCPDefinitionRel>)
							query.list()) {

				map.put(
					commercePricingClassCPDefinitionRel.getPrimaryKeyObj(),
					commercePricingClassCPDefinitionRel);

				cacheResult(commercePricingClassCPDefinitionRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the commerce pricing class cp definition rels.
	 *
	 * @return the commerce pricing class cp definition rels
	 */
	@Override
	public List<CommercePricingClassCPDefinitionRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce pricing class cp definition rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassCPDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce pricing class cp definition rels
	 * @param end the upper bound of the range of commerce pricing class cp definition rels (not inclusive)
	 * @return the range of commerce pricing class cp definition rels
	 */
	@Override
	public List<CommercePricingClassCPDefinitionRel> findAll(
		int start, int end) {

		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce pricing class cp definition rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassCPDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce pricing class cp definition rels
	 * @param end the upper bound of the range of commerce pricing class cp definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce pricing class cp definition rels
	 */
	@Override
	public List<CommercePricingClassCPDefinitionRel> findAll(
		int start, int end,
		OrderByComparator<CommercePricingClassCPDefinitionRel>
			orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce pricing class cp definition rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassCPDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce pricing class cp definition rels
	 * @param end the upper bound of the range of commerce pricing class cp definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce pricing class cp definition rels
	 */
	@Override
	public List<CommercePricingClassCPDefinitionRel> findAll(
		int start, int end,
		OrderByComparator<CommercePricingClassCPDefinitionRel>
			orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CommercePricingClassCPDefinitionRel.class)) {

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

			List<CommercePricingClassCPDefinitionRel> list = null;

			if (useFinderCache) {
				list =
					(List<CommercePricingClassCPDefinitionRel>)
						finderCache.getResult(finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_COMMERCEPRICINGCLASSCPDEFINITIONREL);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_COMMERCEPRICINGCLASSCPDEFINITIONREL;

					sql = sql.concat(
						CommercePricingClassCPDefinitionRelModelImpl.
							ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list =
						(List<CommercePricingClassCPDefinitionRel>)
							QueryUtil.list(query, getDialect(), start, end);

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
	 * Removes all the commerce pricing class cp definition rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommercePricingClassCPDefinitionRel
				commercePricingClassCPDefinitionRel : findAll()) {

			remove(commercePricingClassCPDefinitionRel);
		}
	}

	/**
	 * Returns the number of commerce pricing class cp definition rels.
	 *
	 * @return the number of commerce pricing class cp definition rels
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CommercePricingClassCPDefinitionRel.class)) {

			Long count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(
						_SQL_COUNT_COMMERCEPRICINGCLASSCPDEFINITIONREL);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "CPricingClassCPDefinitionRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEPRICINGCLASSCPDEFINITIONREL;
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
		return CommercePricingClassCPDefinitionRelModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "CPricingClassCPDefinitionRel";
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
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctMergeColumnNames.add("commercePricingClassId");
		ctMergeColumnNames.add("CPDefinitionId");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("CPricingClassCPDefinitionRelId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {"commercePricingClassId", "CPDefinitionId"});
	}

	/**
	 * Initializes the commerce pricing class cp definition rel persistence.
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

		_finderPathWithPaginationFindByCommercePricingClassId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommercePricingClassId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commercePricingClassId"}, true);

		_finderPathWithoutPaginationFindByCommercePricingClassId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCommercePricingClassId",
				new String[] {Long.class.getName()},
				new String[] {"commercePricingClassId"}, true);

		_finderPathCountByCommercePricingClassId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommercePricingClassId",
			new String[] {Long.class.getName()},
			new String[] {"commercePricingClassId"}, false);

		_collectionPersistenceFinderByCommercePricingClassId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByCommercePricingClassId,
				_finderPathWithoutPaginationFindByCommercePricingClassId,
				_finderPathCountByCommercePricingClassId,
				_SQL_SELECT_COMMERCEPRICINGCLASSCPDEFINITIONREL_WHERE,
				_SQL_COUNT_COMMERCEPRICINGCLASSCPDEFINITIONREL_WHERE,
				CommercePricingClassCPDefinitionRelModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"commercePricingClassCPDefinitionRel.",
					"commercePricingClassId", FinderColumn.Type.LONG, "=", true,
					true,
					CommercePricingClassCPDefinitionRel::
						getCommercePricingClassId));

		_finderPathWithPaginationFindByCPDefinitionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCPDefinitionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"CPDefinitionId"}, true);

		_finderPathWithoutPaginationFindByCPDefinitionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCPDefinitionId",
			new String[] {Long.class.getName()},
			new String[] {"CPDefinitionId"}, true);

		_finderPathCountByCPDefinitionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPDefinitionId",
			new String[] {Long.class.getName()},
			new String[] {"CPDefinitionId"}, false);

		_collectionPersistenceFinderByCPDefinitionId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByCPDefinitionId,
				_finderPathWithoutPaginationFindByCPDefinitionId,
				_finderPathCountByCPDefinitionId,
				_SQL_SELECT_COMMERCEPRICINGCLASSCPDEFINITIONREL_WHERE,
				_SQL_COUNT_COMMERCEPRICINGCLASSCPDEFINITIONREL_WHERE,
				CommercePricingClassCPDefinitionRelModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"commercePricingClassCPDefinitionRel.", "CPDefinitionId",
					FinderColumn.Type.LONG, "=", true, true,
					CommercePricingClassCPDefinitionRel::getCPDefinitionId));

		_finderPathFetchByC_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commercePricingClassId", "CPDefinitionId"}, true);

		_uniquePersistenceFinderByC_C = new UniquePersistenceFinder<>(
			this, _finderPathFetchByC_C,
			_SQL_SELECT_COMMERCEPRICINGCLASSCPDEFINITIONREL_WHERE,
			new FinderColumn<>(
				"commercePricingClassCPDefinitionRel.",
				"commercePricingClassId", FinderColumn.Type.LONG, "=", true,
				false,
				CommercePricingClassCPDefinitionRel::getCommercePricingClassId),
			new FinderColumn<>(
				"commercePricingClassCPDefinitionRel.", "CPDefinitionId",
				FinderColumn.Type.LONG, "=", true, true,
				CommercePricingClassCPDefinitionRel::getCPDefinitionId));

		CommercePricingClassCPDefinitionRelUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CommercePricingClassCPDefinitionRelUtil.setPersistence(null);

		entityCache.removeCache(
			CommercePricingClassCPDefinitionRelImpl.class.getName());
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
	protected CTPersistenceHelper ctPersistenceHelper;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String
		_SQL_SELECT_COMMERCEPRICINGCLASSCPDEFINITIONREL =
			"SELECT commercePricingClassCPDefinitionRel FROM CommercePricingClassCPDefinitionRel commercePricingClassCPDefinitionRel";

	private static final String
		_SQL_SELECT_COMMERCEPRICINGCLASSCPDEFINITIONREL_WHERE =
			"SELECT commercePricingClassCPDefinitionRel FROM CommercePricingClassCPDefinitionRel commercePricingClassCPDefinitionRel WHERE ";

	private static final String _SQL_COUNT_COMMERCEPRICINGCLASSCPDEFINITIONREL =
		"SELECT COUNT(commercePricingClassCPDefinitionRel) FROM CommercePricingClassCPDefinitionRel commercePricingClassCPDefinitionRel";

	private static final String
		_SQL_COUNT_COMMERCEPRICINGCLASSCPDEFINITIONREL_WHERE =
			"SELECT COUNT(commercePricingClassCPDefinitionRel) FROM CommercePricingClassCPDefinitionRel commercePricingClassCPDefinitionRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commercePricingClassCPDefinitionRel.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommercePricingClassCPDefinitionRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePricingClassCPDefinitionRelPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"CommercePricingClassCPDefinitionRelId"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1413330907