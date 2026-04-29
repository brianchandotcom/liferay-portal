/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.service.persistence.impl;

import com.liferay.commerce.price.list.exception.NoSuchPriceListCommerceAccountGroupRelException;
import com.liferay.commerce.price.list.model.CommercePriceListCommerceAccountGroupRel;
import com.liferay.commerce.price.list.model.CommercePriceListCommerceAccountGroupRelTable;
import com.liferay.commerce.price.list.model.impl.CommercePriceListCommerceAccountGroupRelImpl;
import com.liferay.commerce.price.list.model.impl.CommercePriceListCommerceAccountGroupRelModelImpl;
import com.liferay.commerce.price.list.service.persistence.CommercePriceListCommerceAccountGroupRelPersistence;
import com.liferay.commerce.price.list.service.persistence.CommercePriceListCommerceAccountGroupRelUtil;
import com.liferay.commerce.price.list.service.persistence.impl.constants.CommercePersistenceConstants;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

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
 * The persistence implementation for the commerce price list commerce account group rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
@Component(service = CommercePriceListCommerceAccountGroupRelPersistence.class)
public class CommercePriceListCommerceAccountGroupRelPersistenceImpl
	extends BasePersistenceImpl
		<CommercePriceListCommerceAccountGroupRel,
		 NoSuchPriceListCommerceAccountGroupRelException>
	implements CommercePriceListCommerceAccountGroupRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommercePriceListCommerceAccountGroupRelUtil</code> to access the commerce price list commerce account group rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommercePriceListCommerceAccountGroupRelImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;
	private CollectionPersistenceFinder
		<CommercePriceListCommerceAccountGroupRel>
			_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the commerce price list commerce account group rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel> findByUuid(
		String uuid) {

		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list commerce account group rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @return the range of matching commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list commerce account group rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommercePriceListCommerceAccountGroupRel>
			orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list commerce account group rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommercePriceListCommerceAccountGroupRel>
			orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CommercePriceListCommerceAccountGroupRel.class)) {

			return _collectionPersistenceFinderByUuid.find(
				finderCache, new Object[] {uuid}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first commerce price list commerce account group rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list commerce account group rel
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a matching commerce price list commerce account group rel could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel findByUuid_First(
			String uuid,
			OrderByComparator<CommercePriceListCommerceAccountGroupRel>
				orderByComparator)
		throws NoSuchPriceListCommerceAccountGroupRelException {

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel = fetchByUuid_First(
				uuid, orderByComparator);

		if (commercePriceListCommerceAccountGroupRel != null) {
			return commercePriceListCommerceAccountGroupRel;
		}

		throw new NoSuchPriceListCommerceAccountGroupRelException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first commerce price list commerce account group rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list commerce account group rel, or <code>null</code> if a matching commerce price list commerce account group rel could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel fetchByUuid_First(
		String uuid,
		OrderByComparator<CommercePriceListCommerceAccountGroupRel>
			orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Removes all the commerce price list commerce account group rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of commerce price list commerce account group rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce price list commerce account group rels
	 */
	@Override
	public int countByUuid(String uuid) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CommercePriceListCommerceAccountGroupRel.class)) {

			return _collectionPersistenceFinderByUuid.count(
				finderCache, new Object[] {uuid});
		}
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder
		<CommercePriceListCommerceAccountGroupRel>
			_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the commerce price list commerce account group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list commerce account group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @return the range of matching commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list commerce account group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceListCommerceAccountGroupRel>
			orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list commerce account group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceListCommerceAccountGroupRel>
			orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CommercePriceListCommerceAccountGroupRel.class)) {

			return _collectionPersistenceFinderByUuid_C.find(
				finderCache, new Object[] {uuid, companyId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first commerce price list commerce account group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list commerce account group rel
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a matching commerce price list commerce account group rel could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommercePriceListCommerceAccountGroupRel>
				orderByComparator)
		throws NoSuchPriceListCommerceAccountGroupRelException {

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel = fetchByUuid_C_First(
				uuid, companyId, orderByComparator);

		if (commercePriceListCommerceAccountGroupRel != null) {
			return commercePriceListCommerceAccountGroupRel;
		}

		throw new NoSuchPriceListCommerceAccountGroupRelException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first commerce price list commerce account group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list commerce account group rel, or <code>null</code> if a matching commerce price list commerce account group rel could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommercePriceListCommerceAccountGroupRel>
			orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Removes all the commerce price list commerce account group rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		_collectionPersistenceFinderByUuid_C.remove(
			finderCache, new Object[] {uuid, companyId});
	}

	/**
	 * Returns the number of commerce price list commerce account group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce price list commerce account group rels
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CommercePriceListCommerceAccountGroupRel.class)) {

			return _collectionPersistenceFinderByUuid_C.count(
				finderCache, new Object[] {uuid, companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByCommercePriceListId;
	private FinderPath _finderPathWithoutPaginationFindByCommercePriceListId;
	private FinderPath _finderPathCountByCommercePriceListId;
	private CollectionPersistenceFinder
		<CommercePriceListCommerceAccountGroupRel>
			_collectionPersistenceFinderByCommercePriceListId;

	/**
	 * Returns all the commerce price list commerce account group rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel>
		findByCommercePriceListId(long commercePriceListId) {

		return findByCommercePriceListId(
			commercePriceListId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list commerce account group rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @return the range of matching commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel>
		findByCommercePriceListId(
			long commercePriceListId, int start, int end) {

		return findByCommercePriceListId(commercePriceListId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list commerce account group rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel>
		findByCommercePriceListId(
			long commercePriceListId, int start, int end,
			OrderByComparator<CommercePriceListCommerceAccountGroupRel>
				orderByComparator) {

		return findByCommercePriceListId(
			commercePriceListId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list commerce account group rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel>
		findByCommercePriceListId(
			long commercePriceListId, int start, int end,
			OrderByComparator<CommercePriceListCommerceAccountGroupRel>
				orderByComparator,
			boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CommercePriceListCommerceAccountGroupRel.class)) {

			return _collectionPersistenceFinderByCommercePriceListId.find(
				finderCache, new Object[] {commercePriceListId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first commerce price list commerce account group rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list commerce account group rel
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a matching commerce price list commerce account group rel could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel
			findByCommercePriceListId_First(
				long commercePriceListId,
				OrderByComparator<CommercePriceListCommerceAccountGroupRel>
					orderByComparator)
		throws NoSuchPriceListCommerceAccountGroupRelException {

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel =
				fetchByCommercePriceListId_First(
					commercePriceListId, orderByComparator);

		if (commercePriceListCommerceAccountGroupRel != null) {
			return commercePriceListCommerceAccountGroupRel;
		}

		throw new NoSuchPriceListCommerceAccountGroupRelException(
			_collectionPersistenceFinderByCommercePriceListId.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {commercePriceListId}));
	}

	/**
	 * Returns the first commerce price list commerce account group rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list commerce account group rel, or <code>null</code> if a matching commerce price list commerce account group rel could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel
		fetchByCommercePriceListId_First(
			long commercePriceListId,
			OrderByComparator<CommercePriceListCommerceAccountGroupRel>
				orderByComparator) {

		return _collectionPersistenceFinderByCommercePriceListId.fetchFirst(
			finderCache, new Object[] {commercePriceListId}, orderByComparator);
	}

	/**
	 * Removes all the commerce price list commerce account group rels where commercePriceListId = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 */
	@Override
	public void removeByCommercePriceListId(long commercePriceListId) {
		_collectionPersistenceFinderByCommercePriceListId.remove(
			finderCache, new Object[] {commercePriceListId});
	}

	/**
	 * Returns the number of commerce price list commerce account group rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the number of matching commerce price list commerce account group rels
	 */
	@Override
	public int countByCommercePriceListId(long commercePriceListId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CommercePriceListCommerceAccountGroupRel.class)) {

			return _collectionPersistenceFinderByCommercePriceListId.count(
				finderCache, new Object[] {commercePriceListId});
		}
	}

	private FinderPath _finderPathFetchByCAGI_CPI;
	private UniquePersistenceFinder<CommercePriceListCommerceAccountGroupRel>
		_uniquePersistenceFinderByCAGI_CPI;

	/**
	 * Returns the commerce price list commerce account group rel where commercePriceListId = &#63; and commerceAccountGroupId = &#63; or throws a <code>NoSuchPriceListCommerceAccountGroupRelException</code> if it could not be found.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce price list commerce account group rel
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a matching commerce price list commerce account group rel could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel findByCAGI_CPI(
			long commercePriceListId, long commerceAccountGroupId)
		throws NoSuchPriceListCommerceAccountGroupRelException {

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel = fetchByCAGI_CPI(
				commercePriceListId, commerceAccountGroupId);

		if (commercePriceListCommerceAccountGroupRel == null) {
			String message =
				_uniquePersistenceFinderByCAGI_CPI.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {commercePriceListId, commerceAccountGroupId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchPriceListCommerceAccountGroupRelException(message);
		}

		return commercePriceListCommerceAccountGroupRel;
	}

	/**
	 * Returns the commerce price list commerce account group rel where commercePriceListId = &#63; and commerceAccountGroupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce price list commerce account group rel, or <code>null</code> if a matching commerce price list commerce account group rel could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel fetchByCAGI_CPI(
		long commercePriceListId, long commerceAccountGroupId) {

		return fetchByCAGI_CPI(
			commercePriceListId, commerceAccountGroupId, true);
	}

	/**
	 * Returns the commerce price list commerce account group rel where commercePriceListId = &#63; and commerceAccountGroupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce price list commerce account group rel, or <code>null</code> if a matching commerce price list commerce account group rel could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel fetchByCAGI_CPI(
		long commercePriceListId, long commerceAccountGroupId,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CommercePriceListCommerceAccountGroupRel.class)) {

			return _uniquePersistenceFinderByCAGI_CPI.fetch(
				finderCache,
				new Object[] {commercePriceListId, commerceAccountGroupId},
				useFinderCache);
		}
	}

	/**
	 * Removes the commerce price list commerce account group rel where commercePriceListId = &#63; and commerceAccountGroupId = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the commerce price list commerce account group rel that was removed
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel removeByCAGI_CPI(
			long commercePriceListId, long commerceAccountGroupId)
		throws NoSuchPriceListCommerceAccountGroupRelException {

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel = findByCAGI_CPI(
				commercePriceListId, commerceAccountGroupId);

		return remove(commercePriceListCommerceAccountGroupRel);
	}

	/**
	 * Returns the number of commerce price list commerce account group rels where commercePriceListId = &#63; and commerceAccountGroupId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the number of matching commerce price list commerce account group rels
	 */
	@Override
	public int countByCAGI_CPI(
		long commercePriceListId, long commerceAccountGroupId) {

		return _uniquePersistenceFinderByCAGI_CPI.count(
			finderCache,
			new Object[] {commercePriceListId, commerceAccountGroupId});
	}

	public CommercePriceListCommerceAccountGroupRelPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put(
			"commercePriceListCommerceAccountGroupRelId",
			"CPLCommerceAccountGroupRelId");
		dbColumnNames.put("order", "order_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommercePriceListCommerceAccountGroupRel.class);

		setModelImplClass(CommercePriceListCommerceAccountGroupRelImpl.class);
		setModelPKClass(long.class);

		setTable(CommercePriceListCommerceAccountGroupRelTable.INSTANCE);
	}

	/**
	 * Caches the commerce price list commerce account group rel in the entity cache if it is enabled.
	 *
	 * @param commercePriceListCommerceAccountGroupRel the commerce price list commerce account group rel
	 */
	@Override
	public void cacheResult(
		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					commercePriceListCommerceAccountGroupRel.
						getCtCollectionId())) {

			entityCache.putResult(
				CommercePriceListCommerceAccountGroupRelImpl.class,
				commercePriceListCommerceAccountGroupRel.getPrimaryKey(),
				commercePriceListCommerceAccountGroupRel);

			finderCache.putResult(
				_finderPathFetchByCAGI_CPI,
				new Object[] {
					commercePriceListCommerceAccountGroupRel.
						getCommercePriceListId(),
					commercePriceListCommerceAccountGroupRel.
						getCommerceAccountGroupId()
				},
				commercePriceListCommerceAccountGroupRel);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce price list commerce account group rels in the entity cache if it is enabled.
	 *
	 * @param commercePriceListCommerceAccountGroupRels the commerce price list commerce account group rels
	 */
	@Override
	public void cacheResult(
		List<CommercePriceListCommerceAccountGroupRel>
			commercePriceListCommerceAccountGroupRels) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commercePriceListCommerceAccountGroupRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommercePriceListCommerceAccountGroupRel
				commercePriceListCommerceAccountGroupRel :
					commercePriceListCommerceAccountGroupRels) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						commercePriceListCommerceAccountGroupRel.
							getCtCollectionId())) {

				if (entityCache.getResult(
						CommercePriceListCommerceAccountGroupRelImpl.class,
						commercePriceListCommerceAccountGroupRel.
							getPrimaryKey()) == null) {

					cacheResult(commercePriceListCommerceAccountGroupRel);
				}
			}
		}
	}

	protected void cacheUniqueFindersCache(
		CommercePriceListCommerceAccountGroupRelModelImpl
			commercePriceListCommerceAccountGroupRelModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					commercePriceListCommerceAccountGroupRelModelImpl.
						getCtCollectionId())) {

			Object[] args = new Object[] {
				commercePriceListCommerceAccountGroupRelModelImpl.
					getCommercePriceListId(),
				commercePriceListCommerceAccountGroupRelModelImpl.
					getCommerceAccountGroupId()
			};

			finderCache.putResult(
				_finderPathFetchByCAGI_CPI, args,
				commercePriceListCommerceAccountGroupRelModelImpl);
		}
	}

	/**
	 * Creates a new commerce price list commerce account group rel with the primary key. Does not add the commerce price list commerce account group rel to the database.
	 *
	 * @param commercePriceListCommerceAccountGroupRelId the primary key for the new commerce price list commerce account group rel
	 * @return the new commerce price list commerce account group rel
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel create(
		long commercePriceListCommerceAccountGroupRelId) {

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel =
				new CommercePriceListCommerceAccountGroupRelImpl();

		commercePriceListCommerceAccountGroupRel.setNew(true);
		commercePriceListCommerceAccountGroupRel.setPrimaryKey(
			commercePriceListCommerceAccountGroupRelId);

		String uuid = PortalUUIDUtil.generate();

		commercePriceListCommerceAccountGroupRel.setUuid(uuid);

		commercePriceListCommerceAccountGroupRel.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commercePriceListCommerceAccountGroupRel;
	}

	/**
	 * Removes the commerce price list commerce account group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePriceListCommerceAccountGroupRelId the primary key of the commerce price list commerce account group rel
	 * @return the commerce price list commerce account group rel that was removed
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a commerce price list commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel remove(
			long commercePriceListCommerceAccountGroupRelId)
		throws NoSuchPriceListCommerceAccountGroupRelException {

		return remove((Serializable)commercePriceListCommerceAccountGroupRelId);
	}

	@Override
	protected CommercePriceListCommerceAccountGroupRel removeImpl(
		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commercePriceListCommerceAccountGroupRel)) {
				commercePriceListCommerceAccountGroupRel =
					(CommercePriceListCommerceAccountGroupRel)session.get(
						CommercePriceListCommerceAccountGroupRelImpl.class,
						commercePriceListCommerceAccountGroupRel.
							getPrimaryKeyObj());
			}

			if ((commercePriceListCommerceAccountGroupRel != null) &&
				ctPersistenceHelper.isRemove(
					commercePriceListCommerceAccountGroupRel)) {

				session.delete(commercePriceListCommerceAccountGroupRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commercePriceListCommerceAccountGroupRel != null) {
			clearCache(commercePriceListCommerceAccountGroupRel);
		}

		return commercePriceListCommerceAccountGroupRel;
	}

	@Override
	public CommercePriceListCommerceAccountGroupRel updateImpl(
		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel) {

		boolean isNew = commercePriceListCommerceAccountGroupRel.isNew();

		if (!(commercePriceListCommerceAccountGroupRel instanceof
				CommercePriceListCommerceAccountGroupRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commercePriceListCommerceAccountGroupRel.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commercePriceListCommerceAccountGroupRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commercePriceListCommerceAccountGroupRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommercePriceListCommerceAccountGroupRel implementation " +
					commercePriceListCommerceAccountGroupRel.getClass());
		}

		CommercePriceListCommerceAccountGroupRelModelImpl
			commercePriceListCommerceAccountGroupRelModelImpl =
				(CommercePriceListCommerceAccountGroupRelModelImpl)
					commercePriceListCommerceAccountGroupRel;

		if (Validator.isNull(
				commercePriceListCommerceAccountGroupRel.getUuid())) {

			String uuid = PortalUUIDUtil.generate();

			commercePriceListCommerceAccountGroupRel.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew &&
			(commercePriceListCommerceAccountGroupRel.getCreateDate() ==
				null)) {

			if (serviceContext == null) {
				commercePriceListCommerceAccountGroupRel.setCreateDate(date);
			}
			else {
				commercePriceListCommerceAccountGroupRel.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commercePriceListCommerceAccountGroupRelModelImpl.
				hasSetModifiedDate()) {

			if (serviceContext == null) {
				commercePriceListCommerceAccountGroupRel.setModifiedDate(date);
			}
			else {
				commercePriceListCommerceAccountGroupRel.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(
					commercePriceListCommerceAccountGroupRel)) {

				if (!isNew) {
					session.evict(
						CommercePriceListCommerceAccountGroupRelImpl.class,
						commercePriceListCommerceAccountGroupRel.
							getPrimaryKeyObj());
				}

				session.save(commercePriceListCommerceAccountGroupRel);
			}
			else {
				commercePriceListCommerceAccountGroupRel =
					(CommercePriceListCommerceAccountGroupRel)session.merge(
						commercePriceListCommerceAccountGroupRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommercePriceListCommerceAccountGroupRelImpl.class,
			commercePriceListCommerceAccountGroupRelModelImpl, false, true);

		cacheUniqueFindersCache(
			commercePriceListCommerceAccountGroupRelModelImpl);

		if (isNew) {
			commercePriceListCommerceAccountGroupRel.setNew(false);
		}

		commercePriceListCommerceAccountGroupRel.resetOriginalValues();

		return commercePriceListCommerceAccountGroupRel;
	}

	/**
	 * Returns the commerce price list commerce account group rel with the primary key or throws a <code>NoSuchPriceListCommerceAccountGroupRelException</code> if it could not be found.
	 *
	 * @param commercePriceListCommerceAccountGroupRelId the primary key of the commerce price list commerce account group rel
	 * @return the commerce price list commerce account group rel
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a commerce price list commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel findByPrimaryKey(
			long commercePriceListCommerceAccountGroupRelId)
		throws NoSuchPriceListCommerceAccountGroupRelException {

		return findByPrimaryKey(
			(Serializable)commercePriceListCommerceAccountGroupRelId);
	}

	/**
	 * Returns the commerce price list commerce account group rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce price list commerce account group rel
	 * @return the commerce price list commerce account group rel, or <code>null</code> if a commerce price list commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel fetchByPrimaryKey(
		Serializable primaryKey) {

		if (ctPersistenceHelper.isProductionMode(
				CommercePriceListCommerceAccountGroupRel.class, primaryKey)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKey(primaryKey);
			}
		}

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel =
				(CommercePriceListCommerceAccountGroupRel)entityCache.getResult(
					CommercePriceListCommerceAccountGroupRelImpl.class,
					primaryKey);

		if (commercePriceListCommerceAccountGroupRel != null) {
			return commercePriceListCommerceAccountGroupRel;
		}

		Session session = null;

		try {
			session = openSession();

			commercePriceListCommerceAccountGroupRel =
				(CommercePriceListCommerceAccountGroupRel)session.get(
					CommercePriceListCommerceAccountGroupRelImpl.class,
					primaryKey);

			if (commercePriceListCommerceAccountGroupRel != null) {
				cacheResult(commercePriceListCommerceAccountGroupRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return commercePriceListCommerceAccountGroupRel;
	}

	/**
	 * Returns the commerce price list commerce account group rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commercePriceListCommerceAccountGroupRelId the primary key of the commerce price list commerce account group rel
	 * @return the commerce price list commerce account group rel, or <code>null</code> if a commerce price list commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel fetchByPrimaryKey(
		long commercePriceListCommerceAccountGroupRelId) {

		return fetchByPrimaryKey(
			(Serializable)commercePriceListCommerceAccountGroupRelId);
	}

	@Override
	public Map<Serializable, CommercePriceListCommerceAccountGroupRel>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(
				CommercePriceListCommerceAccountGroupRel.class)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKeys(primaryKeys);
			}
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommercePriceListCommerceAccountGroupRel> map =
			new HashMap
				<Serializable, CommercePriceListCommerceAccountGroupRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommercePriceListCommerceAccountGroupRel
				commercePriceListCommerceAccountGroupRel = fetchByPrimaryKey(
					primaryKey);

			if (commercePriceListCommerceAccountGroupRel != null) {
				map.put(primaryKey, commercePriceListCommerceAccountGroupRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			try (SafeCloseable safeCloseable =
					ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
						CommercePriceListCommerceAccountGroupRel.class,
						primaryKey)) {

				CommercePriceListCommerceAccountGroupRel
					commercePriceListCommerceAccountGroupRel =
						(CommercePriceListCommerceAccountGroupRel)
							entityCache.getResult(
								CommercePriceListCommerceAccountGroupRelImpl.
									class,
								primaryKey);

				if (commercePriceListCommerceAccountGroupRel == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(
						primaryKey, commercePriceListCommerceAccountGroupRel);
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

			for (CommercePriceListCommerceAccountGroupRel
					commercePriceListCommerceAccountGroupRel :
						(List<CommercePriceListCommerceAccountGroupRel>)
							query.list()) {

				map.put(
					commercePriceListCommerceAccountGroupRel.getPrimaryKeyObj(),
					commercePriceListCommerceAccountGroupRel);

				cacheResult(commercePriceListCommerceAccountGroupRel);
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
	 * Returns all the commerce price list commerce account group rels.
	 *
	 * @return the commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @return the range of commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel> findAll(
		int start, int end) {

		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel> findAll(
		int start, int end,
		OrderByComparator<CommercePriceListCommerceAccountGroupRel>
			orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel> findAll(
		int start, int end,
		OrderByComparator<CommercePriceListCommerceAccountGroupRel>
			orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CommercePriceListCommerceAccountGroupRel.class)) {

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

			List<CommercePriceListCommerceAccountGroupRel> list = null;

			if (useFinderCache) {
				list =
					(List<CommercePriceListCommerceAccountGroupRel>)
						finderCache.getResult(finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(
						_SQL_SELECT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL;

					sql = sql.concat(
						CommercePriceListCommerceAccountGroupRelModelImpl.
							ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list =
						(List<CommercePriceListCommerceAccountGroupRel>)
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
	 * Removes all the commerce price list commerce account group rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommercePriceListCommerceAccountGroupRel
				commercePriceListCommerceAccountGroupRel : findAll()) {

			remove(commercePriceListCommerceAccountGroupRel);
		}
	}

	/**
	 * Returns the number of commerce price list commerce account group rels.
	 *
	 * @return the number of commerce price list commerce account group rels
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CommercePriceListCommerceAccountGroupRel.class)) {

			Long count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(
						_SQL_COUNT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL);

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
		return "CPLCommerceAccountGroupRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL;
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
		return CommercePriceListCommerceAccountGroupRelModelImpl.
			TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "CPLCommerceGroupAccountRel";
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
		ctStrictColumnNames.add("uuid_");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctMergeColumnNames.add("commercePriceListId");
		ctMergeColumnNames.add("commerceAccountGroupId");
		ctMergeColumnNames.add("order_");
		ctMergeColumnNames.add("lastPublishDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("CPLCommerceAccountGroupRelId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {"commercePriceListId", "commerceAccountGroupId"});
	}

	/**
	 * Initializes the commerce price list commerce account group rel persistence.
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

		_finderPathWithPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_collectionPersistenceFinderByUuid = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByUuid,
			_finderPathWithoutPaginationFindByUuid, _finderPathCountByUuid,
			_SQL_SELECT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL_WHERE,
			_SQL_COUNT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL_WHERE,
			CommercePriceListCommerceAccountGroupRelModelImpl.ORDER_BY_JPQL,
			_ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"commercePriceListCommerceAccountGroupRel.", "uuid",
				FinderColumn.Type.STRING, "=", true, true,
				CommercePriceListCommerceAccountGroupRel::getUuid));

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathCountByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, false);

		_collectionPersistenceFinderByUuid_C =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByUuid_C,
				_finderPathWithoutPaginationFindByUuid_C,
				_finderPathCountByUuid_C,
				_SQL_SELECT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL_WHERE,
				_SQL_COUNT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL_WHERE,
				CommercePriceListCommerceAccountGroupRelModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"commercePriceListCommerceAccountGroupRel.", "uuid",
					FinderColumn.Type.STRING, "=", true, false,
					CommercePriceListCommerceAccountGroupRel::getUuid),
				new FinderColumn<>(
					"commercePriceListCommerceAccountGroupRel.", "companyId",
					FinderColumn.Type.LONG, "=", true, true,
					CommercePriceListCommerceAccountGroupRel::getCompanyId));

		_finderPathWithPaginationFindByCommercePriceListId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommercePriceListId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commercePriceListId"}, true);

		_finderPathWithoutPaginationFindByCommercePriceListId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommercePriceListId", new String[] {Long.class.getName()},
			new String[] {"commercePriceListId"}, true);

		_finderPathCountByCommercePriceListId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommercePriceListId", new String[] {Long.class.getName()},
			new String[] {"commercePriceListId"}, false);

		_collectionPersistenceFinderByCommercePriceListId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByCommercePriceListId,
				_finderPathWithoutPaginationFindByCommercePriceListId,
				_finderPathCountByCommercePriceListId,
				_SQL_SELECT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL_WHERE,
				_SQL_COUNT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL_WHERE,
				CommercePriceListCommerceAccountGroupRelModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"commercePriceListCommerceAccountGroupRel.",
					"commercePriceListId", FinderColumn.Type.LONG, "=", true,
					true,
					CommercePriceListCommerceAccountGroupRel::
						getCommercePriceListId));

		_finderPathFetchByCAGI_CPI = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCAGI_CPI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commercePriceListId", "commerceAccountGroupId"},
			true);

		_uniquePersistenceFinderByCAGI_CPI = new UniquePersistenceFinder<>(
			this, _finderPathFetchByCAGI_CPI,
			_SQL_SELECT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL_WHERE,
			new FinderColumn<>(
				"commercePriceListCommerceAccountGroupRel.",
				"commercePriceListId", FinderColumn.Type.LONG, "=", true, false,
				CommercePriceListCommerceAccountGroupRel::
					getCommercePriceListId),
			new FinderColumn<>(
				"commercePriceListCommerceAccountGroupRel.",
				"commerceAccountGroupId", FinderColumn.Type.LONG, "=", true,
				true,
				CommercePriceListCommerceAccountGroupRel::
					getCommerceAccountGroupId));

		CommercePriceListCommerceAccountGroupRelUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CommercePriceListCommerceAccountGroupRelUtil.setPersistence(null);

		entityCache.removeCache(
			CommercePriceListCommerceAccountGroupRelImpl.class.getName());
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
		_SQL_SELECT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL =
			"SELECT commercePriceListCommerceAccountGroupRel FROM CommercePriceListCommerceAccountGroupRel commercePriceListCommerceAccountGroupRel";

	private static final String
		_SQL_SELECT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL_WHERE =
			"SELECT commercePriceListCommerceAccountGroupRel FROM CommercePriceListCommerceAccountGroupRel commercePriceListCommerceAccountGroupRel WHERE ";

	private static final String
		_SQL_COUNT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL =
			"SELECT COUNT(commercePriceListCommerceAccountGroupRel) FROM CommercePriceListCommerceAccountGroupRel commercePriceListCommerceAccountGroupRel";

	private static final String
		_SQL_COUNT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL_WHERE =
			"SELECT COUNT(commercePriceListCommerceAccountGroupRel) FROM CommercePriceListCommerceAccountGroupRel commercePriceListCommerceAccountGroupRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commercePriceListCommerceAccountGroupRel.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommercePriceListCommerceAccountGroupRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceListCommerceAccountGroupRelPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {
			"uuid", "commercePriceListCommerceAccountGroupRelId", "order"
		});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-960486068