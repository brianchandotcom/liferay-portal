/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.service.persistence.impl;

import com.liferay.commerce.inventory.exception.DuplicateCommerceInventoryReplenishmentItemExternalReferenceCodeException;
import com.liferay.commerce.inventory.exception.NoSuchInventoryReplenishmentItemException;
import com.liferay.commerce.inventory.model.CommerceInventoryReplenishmentItem;
import com.liferay.commerce.inventory.model.CommerceInventoryReplenishmentItemTable;
import com.liferay.commerce.inventory.model.impl.CommerceInventoryReplenishmentItemImpl;
import com.liferay.commerce.inventory.model.impl.CommerceInventoryReplenishmentItemModelImpl;
import com.liferay.commerce.inventory.service.persistence.CommerceInventoryReplenishmentItemPersistence;
import com.liferay.commerce.inventory.service.persistence.CommerceInventoryReplenishmentItemUtil;
import com.liferay.commerce.inventory.service.persistence.impl.constants.CommercePersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.CollectionPersistenceFinder;
import com.liferay.portal.kernel.service.persistence.impl.FinderColumn;
import com.liferay.portal.kernel.service.persistence.impl.UniquePersistenceFinder;
import com.liferay.portal.kernel.util.ContentTypes;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the commerce inventory replenishment item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @generated
 */
@Component(service = CommerceInventoryReplenishmentItemPersistence.class)
public class CommerceInventoryReplenishmentItemPersistenceImpl
	extends BasePersistenceImpl
		<CommerceInventoryReplenishmentItem,
		 NoSuchInventoryReplenishmentItemException>
	implements CommerceInventoryReplenishmentItemPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceInventoryReplenishmentItemUtil</code> to access the commerce inventory replenishment item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceInventoryReplenishmentItemImpl.class.getName();

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
	private CollectionPersistenceFinder<CommerceInventoryReplenishmentItem>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the commerce inventory replenishment items where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory replenishment items where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @return the range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory replenishment items where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceInventoryReplenishmentItem>
			orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory replenishment items where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceInventoryReplenishmentItem> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid.find(
			finderCache, new Object[] {uuid}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce inventory replenishment item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory replenishment item
	 * @throws NoSuchInventoryReplenishmentItemException if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem findByUuid_First(
			String uuid,
			OrderByComparator<CommerceInventoryReplenishmentItem>
				orderByComparator)
		throws NoSuchInventoryReplenishmentItemException {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			fetchByUuid_First(uuid, orderByComparator);

		if (commerceInventoryReplenishmentItem != null) {
			return commerceInventoryReplenishmentItem;
		}

		throw new NoSuchInventoryReplenishmentItemException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first commerce inventory replenishment item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory replenishment item, or <code>null</code> if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem fetchByUuid_First(
		String uuid,
		OrderByComparator<CommerceInventoryReplenishmentItem>
			orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Removes all the commerce inventory replenishment items where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of commerce inventory replenishment items where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce inventory replenishment items
	 */
	@Override
	public int countByUuid(String uuid) {
		return _collectionPersistenceFinderByUuid.count(
			finderCache, new Object[] {uuid});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<CommerceInventoryReplenishmentItem>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the commerce inventory replenishment items where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory replenishment items where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @return the range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory replenishment items where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceInventoryReplenishmentItem>
			orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory replenishment items where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceInventoryReplenishmentItem> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid_C.find(
			finderCache, new Object[] {uuid, companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce inventory replenishment item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory replenishment item
	 * @throws NoSuchInventoryReplenishmentItemException if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommerceInventoryReplenishmentItem>
				orderByComparator)
		throws NoSuchInventoryReplenishmentItemException {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (commerceInventoryReplenishmentItem != null) {
			return commerceInventoryReplenishmentItem;
		}

		throw new NoSuchInventoryReplenishmentItemException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first commerce inventory replenishment item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory replenishment item, or <code>null</code> if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceInventoryReplenishmentItem>
			orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Removes all the commerce inventory replenishment items where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of commerce inventory replenishment items where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce inventory replenishment items
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		return _collectionPersistenceFinderByUuid_C.count(
			finderCache, new Object[] {uuid, companyId});
	}

	private FinderPath
		_finderPathWithPaginationFindByCommerceInventoryWarehouseId;
	private FinderPath
		_finderPathWithoutPaginationFindByCommerceInventoryWarehouseId;
	private FinderPath _finderPathCountByCommerceInventoryWarehouseId;
	private CollectionPersistenceFinder<CommerceInventoryReplenishmentItem>
		_collectionPersistenceFinderByCommerceInventoryWarehouseId;

	/**
	 * Returns all the commerce inventory replenishment items where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem>
		findByCommerceInventoryWarehouseId(long commerceInventoryWarehouseId) {

		return findByCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce inventory replenishment items where commerceInventoryWarehouseId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @return the range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem>
		findByCommerceInventoryWarehouseId(
			long commerceInventoryWarehouseId, int start, int end) {

		return findByCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory replenishment items where commerceInventoryWarehouseId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem>
		findByCommerceInventoryWarehouseId(
			long commerceInventoryWarehouseId, int start, int end,
			OrderByComparator<CommerceInventoryReplenishmentItem>
				orderByComparator) {

		return findByCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory replenishment items where commerceInventoryWarehouseId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem>
		findByCommerceInventoryWarehouseId(
			long commerceInventoryWarehouseId, int start, int end,
			OrderByComparator<CommerceInventoryReplenishmentItem>
				orderByComparator,
			boolean useFinderCache) {

		return _collectionPersistenceFinderByCommerceInventoryWarehouseId.find(
			finderCache, new Object[] {commerceInventoryWarehouseId}, start,
			end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce inventory replenishment item in the ordered set where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory replenishment item
	 * @throws NoSuchInventoryReplenishmentItemException if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem
			findByCommerceInventoryWarehouseId_First(
				long commerceInventoryWarehouseId,
				OrderByComparator<CommerceInventoryReplenishmentItem>
					orderByComparator)
		throws NoSuchInventoryReplenishmentItemException {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			fetchByCommerceInventoryWarehouseId_First(
				commerceInventoryWarehouseId, orderByComparator);

		if (commerceInventoryReplenishmentItem != null) {
			return commerceInventoryReplenishmentItem;
		}

		throw new NoSuchInventoryReplenishmentItemException(
			_collectionPersistenceFinderByCommerceInventoryWarehouseId.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {commerceInventoryWarehouseId}));
	}

	/**
	 * Returns the first commerce inventory replenishment item in the ordered set where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory replenishment item, or <code>null</code> if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem
		fetchByCommerceInventoryWarehouseId_First(
			long commerceInventoryWarehouseId,
			OrderByComparator<CommerceInventoryReplenishmentItem>
				orderByComparator) {

		return _collectionPersistenceFinderByCommerceInventoryWarehouseId.
			fetchFirst(
				finderCache, new Object[] {commerceInventoryWarehouseId},
				orderByComparator);
	}

	/**
	 * Removes all the commerce inventory replenishment items where commerceInventoryWarehouseId = &#63; from the database.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 */
	@Override
	public void removeByCommerceInventoryWarehouseId(
		long commerceInventoryWarehouseId) {

		_collectionPersistenceFinderByCommerceInventoryWarehouseId.remove(
			finderCache, new Object[] {commerceInventoryWarehouseId});
	}

	/**
	 * Returns the number of commerce inventory replenishment items where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the number of matching commerce inventory replenishment items
	 */
	@Override
	public int countByCommerceInventoryWarehouseId(
		long commerceInventoryWarehouseId) {

		return _collectionPersistenceFinderByCommerceInventoryWarehouseId.count(
			finderCache, new Object[] {commerceInventoryWarehouseId});
	}

	private FinderPath _finderPathWithPaginationFindByAvailabilityDate;
	private FinderPath _finderPathWithoutPaginationFindByAvailabilityDate;
	private FinderPath _finderPathCountByAvailabilityDate;
	private CollectionPersistenceFinder<CommerceInventoryReplenishmentItem>
		_collectionPersistenceFinderByAvailabilityDate;

	/**
	 * Returns all the commerce inventory replenishment items where availabilityDate = &#63;.
	 *
	 * @param availabilityDate the availability date
	 * @return the matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByAvailabilityDate(
		Date availabilityDate) {

		return findByAvailabilityDate(
			availabilityDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory replenishment items where availabilityDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param availabilityDate the availability date
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @return the range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByAvailabilityDate(
		Date availabilityDate, int start, int end) {

		return findByAvailabilityDate(availabilityDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory replenishment items where availabilityDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param availabilityDate the availability date
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByAvailabilityDate(
		Date availabilityDate, int start, int end,
		OrderByComparator<CommerceInventoryReplenishmentItem>
			orderByComparator) {

		return findByAvailabilityDate(
			availabilityDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory replenishment items where availabilityDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param availabilityDate the availability date
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByAvailabilityDate(
		Date availabilityDate, int start, int end,
		OrderByComparator<CommerceInventoryReplenishmentItem> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByAvailabilityDate.find(
			finderCache, new Object[] {availabilityDate}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce inventory replenishment item in the ordered set where availabilityDate = &#63;.
	 *
	 * @param availabilityDate the availability date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory replenishment item
	 * @throws NoSuchInventoryReplenishmentItemException if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem findByAvailabilityDate_First(
			Date availabilityDate,
			OrderByComparator<CommerceInventoryReplenishmentItem>
				orderByComparator)
		throws NoSuchInventoryReplenishmentItemException {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			fetchByAvailabilityDate_First(availabilityDate, orderByComparator);

		if (commerceInventoryReplenishmentItem != null) {
			return commerceInventoryReplenishmentItem;
		}

		throw new NoSuchInventoryReplenishmentItemException(
			_collectionPersistenceFinderByAvailabilityDate.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {availabilityDate}));
	}

	/**
	 * Returns the first commerce inventory replenishment item in the ordered set where availabilityDate = &#63;.
	 *
	 * @param availabilityDate the availability date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory replenishment item, or <code>null</code> if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem fetchByAvailabilityDate_First(
		Date availabilityDate,
		OrderByComparator<CommerceInventoryReplenishmentItem>
			orderByComparator) {

		return _collectionPersistenceFinderByAvailabilityDate.fetchFirst(
			finderCache, new Object[] {availabilityDate}, orderByComparator);
	}

	/**
	 * Removes all the commerce inventory replenishment items where availabilityDate = &#63; from the database.
	 *
	 * @param availabilityDate the availability date
	 */
	@Override
	public void removeByAvailabilityDate(Date availabilityDate) {
		_collectionPersistenceFinderByAvailabilityDate.remove(
			finderCache, new Object[] {availabilityDate});
	}

	/**
	 * Returns the number of commerce inventory replenishment items where availabilityDate = &#63;.
	 *
	 * @param availabilityDate the availability date
	 * @return the number of matching commerce inventory replenishment items
	 */
	@Override
	public int countByAvailabilityDate(Date availabilityDate) {
		return _collectionPersistenceFinderByAvailabilityDate.count(
			finderCache, new Object[] {availabilityDate});
	}

	private FinderPath _finderPathWithPaginationFindBySku;
	private FinderPath _finderPathWithoutPaginationFindBySku;
	private FinderPath _finderPathCountBySku;
	private CollectionPersistenceFinder<CommerceInventoryReplenishmentItem>
		_collectionPersistenceFinderBySku;

	/**
	 * Returns all the commerce inventory replenishment items where sku = &#63;.
	 *
	 * @param sku the sku
	 * @return the matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findBySku(String sku) {
		return findBySku(sku, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory replenishment items where sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param sku the sku
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @return the range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findBySku(
		String sku, int start, int end) {

		return findBySku(sku, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory replenishment items where sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param sku the sku
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findBySku(
		String sku, int start, int end,
		OrderByComparator<CommerceInventoryReplenishmentItem>
			orderByComparator) {

		return findBySku(sku, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory replenishment items where sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param sku the sku
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findBySku(
		String sku, int start, int end,
		OrderByComparator<CommerceInventoryReplenishmentItem> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderBySku.find(
			finderCache, new Object[] {sku}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce inventory replenishment item in the ordered set where sku = &#63;.
	 *
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory replenishment item
	 * @throws NoSuchInventoryReplenishmentItemException if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem findBySku_First(
			String sku,
			OrderByComparator<CommerceInventoryReplenishmentItem>
				orderByComparator)
		throws NoSuchInventoryReplenishmentItemException {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			fetchBySku_First(sku, orderByComparator);

		if (commerceInventoryReplenishmentItem != null) {
			return commerceInventoryReplenishmentItem;
		}

		throw new NoSuchInventoryReplenishmentItemException(
			_collectionPersistenceFinderBySku.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {sku}));
	}

	/**
	 * Returns the first commerce inventory replenishment item in the ordered set where sku = &#63;.
	 *
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory replenishment item, or <code>null</code> if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem fetchBySku_First(
		String sku,
		OrderByComparator<CommerceInventoryReplenishmentItem>
			orderByComparator) {

		return _collectionPersistenceFinderBySku.fetchFirst(
			finderCache, new Object[] {sku}, orderByComparator);
	}

	/**
	 * Removes all the commerce inventory replenishment items where sku = &#63; from the database.
	 *
	 * @param sku the sku
	 */
	@Override
	public void removeBySku(String sku) {
		_collectionPersistenceFinderBySku.remove(
			finderCache, new Object[] {sku});
	}

	/**
	 * Returns the number of commerce inventory replenishment items where sku = &#63;.
	 *
	 * @param sku the sku
	 * @return the number of matching commerce inventory replenishment items
	 */
	@Override
	public int countBySku(String sku) {
		return _collectionPersistenceFinderBySku.count(
			finderCache, new Object[] {sku});
	}

	private FinderPath _finderPathWithPaginationFindByC_S_U;
	private FinderPath _finderPathWithoutPaginationFindByC_S_U;
	private FinderPath _finderPathCountByC_S_U;
	private CollectionPersistenceFinder<CommerceInventoryReplenishmentItem>
		_collectionPersistenceFinderByC_S_U;

	/**
	 * Returns all the commerce inventory replenishment items where companyId = &#63; and sku = &#63; and unitOfMeasureKey = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param unitOfMeasureKey the unit of measure key
	 * @return the matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByC_S_U(
		long companyId, String sku, String unitOfMeasureKey) {

		return findByC_S_U(
			companyId, sku, unitOfMeasureKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory replenishment items where companyId = &#63; and sku = &#63; and unitOfMeasureKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param unitOfMeasureKey the unit of measure key
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @return the range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByC_S_U(
		long companyId, String sku, String unitOfMeasureKey, int start,
		int end) {

		return findByC_S_U(companyId, sku, unitOfMeasureKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory replenishment items where companyId = &#63; and sku = &#63; and unitOfMeasureKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param unitOfMeasureKey the unit of measure key
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByC_S_U(
		long companyId, String sku, String unitOfMeasureKey, int start, int end,
		OrderByComparator<CommerceInventoryReplenishmentItem>
			orderByComparator) {

		return findByC_S_U(
			companyId, sku, unitOfMeasureKey, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory replenishment items where companyId = &#63; and sku = &#63; and unitOfMeasureKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param unitOfMeasureKey the unit of measure key
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByC_S_U(
		long companyId, String sku, String unitOfMeasureKey, int start, int end,
		OrderByComparator<CommerceInventoryReplenishmentItem> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByC_S_U.find(
			finderCache, new Object[] {companyId, sku, unitOfMeasureKey}, start,
			end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce inventory replenishment item in the ordered set where companyId = &#63; and sku = &#63; and unitOfMeasureKey = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param unitOfMeasureKey the unit of measure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory replenishment item
	 * @throws NoSuchInventoryReplenishmentItemException if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem findByC_S_U_First(
			long companyId, String sku, String unitOfMeasureKey,
			OrderByComparator<CommerceInventoryReplenishmentItem>
				orderByComparator)
		throws NoSuchInventoryReplenishmentItemException {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			fetchByC_S_U_First(
				companyId, sku, unitOfMeasureKey, orderByComparator);

		if (commerceInventoryReplenishmentItem != null) {
			return commerceInventoryReplenishmentItem;
		}

		throw new NoSuchInventoryReplenishmentItemException(
			_collectionPersistenceFinderByC_S_U.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {companyId, sku, unitOfMeasureKey}));
	}

	/**
	 * Returns the first commerce inventory replenishment item in the ordered set where companyId = &#63; and sku = &#63; and unitOfMeasureKey = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param unitOfMeasureKey the unit of measure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory replenishment item, or <code>null</code> if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem fetchByC_S_U_First(
		long companyId, String sku, String unitOfMeasureKey,
		OrderByComparator<CommerceInventoryReplenishmentItem>
			orderByComparator) {

		return _collectionPersistenceFinderByC_S_U.fetchFirst(
			finderCache, new Object[] {companyId, sku, unitOfMeasureKey},
			orderByComparator);
	}

	/**
	 * Removes all the commerce inventory replenishment items where companyId = &#63; and sku = &#63; and unitOfMeasureKey = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param unitOfMeasureKey the unit of measure key
	 */
	@Override
	public void removeByC_S_U(
		long companyId, String sku, String unitOfMeasureKey) {

		_collectionPersistenceFinderByC_S_U.remove(
			finderCache, new Object[] {companyId, sku, unitOfMeasureKey});
	}

	/**
	 * Returns the number of commerce inventory replenishment items where companyId = &#63; and sku = &#63; and unitOfMeasureKey = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param unitOfMeasureKey the unit of measure key
	 * @return the number of matching commerce inventory replenishment items
	 */
	@Override
	public int countByC_S_U(
		long companyId, String sku, String unitOfMeasureKey) {

		return _collectionPersistenceFinderByC_S_U.count(
			finderCache, new Object[] {companyId, sku, unitOfMeasureKey});
	}

	private FinderPath _finderPathWithPaginationFindByAD_S_U;
	private FinderPath _finderPathWithoutPaginationFindByAD_S_U;
	private FinderPath _finderPathCountByAD_S_U;
	private CollectionPersistenceFinder<CommerceInventoryReplenishmentItem>
		_collectionPersistenceFinderByAD_S_U;

	/**
	 * Returns all the commerce inventory replenishment items where availabilityDate = &#63; and sku = &#63; and unitOfMeasureKey = &#63;.
	 *
	 * @param availabilityDate the availability date
	 * @param sku the sku
	 * @param unitOfMeasureKey the unit of measure key
	 * @return the matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByAD_S_U(
		Date availabilityDate, String sku, String unitOfMeasureKey) {

		return findByAD_S_U(
			availabilityDate, sku, unitOfMeasureKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory replenishment items where availabilityDate = &#63; and sku = &#63; and unitOfMeasureKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param availabilityDate the availability date
	 * @param sku the sku
	 * @param unitOfMeasureKey the unit of measure key
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @return the range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByAD_S_U(
		Date availabilityDate, String sku, String unitOfMeasureKey, int start,
		int end) {

		return findByAD_S_U(
			availabilityDate, sku, unitOfMeasureKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory replenishment items where availabilityDate = &#63; and sku = &#63; and unitOfMeasureKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param availabilityDate the availability date
	 * @param sku the sku
	 * @param unitOfMeasureKey the unit of measure key
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByAD_S_U(
		Date availabilityDate, String sku, String unitOfMeasureKey, int start,
		int end,
		OrderByComparator<CommerceInventoryReplenishmentItem>
			orderByComparator) {

		return findByAD_S_U(
			availabilityDate, sku, unitOfMeasureKey, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory replenishment items where availabilityDate = &#63; and sku = &#63; and unitOfMeasureKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param availabilityDate the availability date
	 * @param sku the sku
	 * @param unitOfMeasureKey the unit of measure key
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByAD_S_U(
		Date availabilityDate, String sku, String unitOfMeasureKey, int start,
		int end,
		OrderByComparator<CommerceInventoryReplenishmentItem> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByAD_S_U.find(
			finderCache, new Object[] {availabilityDate, sku, unitOfMeasureKey},
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce inventory replenishment item in the ordered set where availabilityDate = &#63; and sku = &#63; and unitOfMeasureKey = &#63;.
	 *
	 * @param availabilityDate the availability date
	 * @param sku the sku
	 * @param unitOfMeasureKey the unit of measure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory replenishment item
	 * @throws NoSuchInventoryReplenishmentItemException if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem findByAD_S_U_First(
			Date availabilityDate, String sku, String unitOfMeasureKey,
			OrderByComparator<CommerceInventoryReplenishmentItem>
				orderByComparator)
		throws NoSuchInventoryReplenishmentItemException {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			fetchByAD_S_U_First(
				availabilityDate, sku, unitOfMeasureKey, orderByComparator);

		if (commerceInventoryReplenishmentItem != null) {
			return commerceInventoryReplenishmentItem;
		}

		throw new NoSuchInventoryReplenishmentItemException(
			_collectionPersistenceFinderByAD_S_U.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {availabilityDate, sku, unitOfMeasureKey}));
	}

	/**
	 * Returns the first commerce inventory replenishment item in the ordered set where availabilityDate = &#63; and sku = &#63; and unitOfMeasureKey = &#63;.
	 *
	 * @param availabilityDate the availability date
	 * @param sku the sku
	 * @param unitOfMeasureKey the unit of measure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory replenishment item, or <code>null</code> if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem fetchByAD_S_U_First(
		Date availabilityDate, String sku, String unitOfMeasureKey,
		OrderByComparator<CommerceInventoryReplenishmentItem>
			orderByComparator) {

		return _collectionPersistenceFinderByAD_S_U.fetchFirst(
			finderCache, new Object[] {availabilityDate, sku, unitOfMeasureKey},
			orderByComparator);
	}

	/**
	 * Removes all the commerce inventory replenishment items where availabilityDate = &#63; and sku = &#63; and unitOfMeasureKey = &#63; from the database.
	 *
	 * @param availabilityDate the availability date
	 * @param sku the sku
	 * @param unitOfMeasureKey the unit of measure key
	 */
	@Override
	public void removeByAD_S_U(
		Date availabilityDate, String sku, String unitOfMeasureKey) {

		_collectionPersistenceFinderByAD_S_U.remove(
			finderCache,
			new Object[] {availabilityDate, sku, unitOfMeasureKey});
	}

	/**
	 * Returns the number of commerce inventory replenishment items where availabilityDate = &#63; and sku = &#63; and unitOfMeasureKey = &#63;.
	 *
	 * @param availabilityDate the availability date
	 * @param sku the sku
	 * @param unitOfMeasureKey the unit of measure key
	 * @return the number of matching commerce inventory replenishment items
	 */
	@Override
	public int countByAD_S_U(
		Date availabilityDate, String sku, String unitOfMeasureKey) {

		return _collectionPersistenceFinderByAD_S_U.count(
			finderCache,
			new Object[] {availabilityDate, sku, unitOfMeasureKey});
	}

	private FinderPath _finderPathFetchByERC_C;
	private UniquePersistenceFinder<CommerceInventoryReplenishmentItem>
		_uniquePersistenceFinderByERC_C;

	/**
	 * Returns the commerce inventory replenishment item where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchInventoryReplenishmentItemException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce inventory replenishment item
	 * @throws NoSuchInventoryReplenishmentItemException if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem findByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchInventoryReplenishmentItemException {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			fetchByERC_C(externalReferenceCode, companyId);

		if (commerceInventoryReplenishmentItem == null) {
			String message =
				_uniquePersistenceFinderByERC_C.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {externalReferenceCode, companyId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchInventoryReplenishmentItemException(message);
		}

		return commerceInventoryReplenishmentItem;
	}

	/**
	 * Returns the commerce inventory replenishment item where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce inventory replenishment item, or <code>null</code> if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return fetchByERC_C(externalReferenceCode, companyId, true);
	}

	/**
	 * Returns the commerce inventory replenishment item where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce inventory replenishment item, or <code>null</code> if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		return _uniquePersistenceFinderByERC_C.fetch(
			finderCache, new Object[] {externalReferenceCode, companyId},
			useFinderCache);
	}

	/**
	 * Removes the commerce inventory replenishment item where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the commerce inventory replenishment item that was removed
	 */
	@Override
	public CommerceInventoryReplenishmentItem removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchInventoryReplenishmentItemException {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			findByERC_C(externalReferenceCode, companyId);

		return remove(commerceInventoryReplenishmentItem);
	}

	/**
	 * Returns the number of commerce inventory replenishment items where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching commerce inventory replenishment items
	 */
	@Override
	public int countByERC_C(String externalReferenceCode, long companyId) {
		return _uniquePersistenceFinderByERC_C.count(
			finderCache, new Object[] {externalReferenceCode, companyId});
	}

	public CommerceInventoryReplenishmentItemPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put(
			"commerceInventoryReplenishmentItemId", "CIReplenishmentItemId");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceInventoryReplenishmentItem.class);

		setModelImplClass(CommerceInventoryReplenishmentItemImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceInventoryReplenishmentItemTable.INSTANCE);
	}

	/**
	 * Caches the commerce inventory replenishment item in the entity cache if it is enabled.
	 *
	 * @param commerceInventoryReplenishmentItem the commerce inventory replenishment item
	 */
	@Override
	public void cacheResult(
		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem) {

		entityCache.putResult(
			CommerceInventoryReplenishmentItemImpl.class,
			commerceInventoryReplenishmentItem.getPrimaryKey(),
			commerceInventoryReplenishmentItem);

		finderCache.putResult(
			_finderPathFetchByERC_C,
			new Object[] {
				commerceInventoryReplenishmentItem.getExternalReferenceCode(),
				commerceInventoryReplenishmentItem.getCompanyId()
			},
			commerceInventoryReplenishmentItem);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce inventory replenishment items in the entity cache if it is enabled.
	 *
	 * @param commerceInventoryReplenishmentItems the commerce inventory replenishment items
	 */
	@Override
	public void cacheResult(
		List<CommerceInventoryReplenishmentItem>
			commerceInventoryReplenishmentItems) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceInventoryReplenishmentItems.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceInventoryReplenishmentItem
				commerceInventoryReplenishmentItem :
					commerceInventoryReplenishmentItems) {

			if (entityCache.getResult(
					CommerceInventoryReplenishmentItemImpl.class,
					commerceInventoryReplenishmentItem.getPrimaryKey()) ==
						null) {

				cacheResult(commerceInventoryReplenishmentItem);
			}
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceInventoryReplenishmentItemModelImpl
			commerceInventoryReplenishmentItemModelImpl) {

		Object[] args = new Object[] {
			commerceInventoryReplenishmentItemModelImpl.
				getExternalReferenceCode(),
			commerceInventoryReplenishmentItemModelImpl.getCompanyId()
		};

		finderCache.putResult(
			_finderPathFetchByERC_C, args,
			commerceInventoryReplenishmentItemModelImpl);
	}

	/**
	 * Creates a new commerce inventory replenishment item with the primary key. Does not add the commerce inventory replenishment item to the database.
	 *
	 * @param commerceInventoryReplenishmentItemId the primary key for the new commerce inventory replenishment item
	 * @return the new commerce inventory replenishment item
	 */
	@Override
	public CommerceInventoryReplenishmentItem create(
		long commerceInventoryReplenishmentItemId) {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			new CommerceInventoryReplenishmentItemImpl();

		commerceInventoryReplenishmentItem.setNew(true);
		commerceInventoryReplenishmentItem.setPrimaryKey(
			commerceInventoryReplenishmentItemId);

		String uuid = PortalUUIDUtil.generate();

		commerceInventoryReplenishmentItem.setUuid(uuid);

		commerceInventoryReplenishmentItem.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceInventoryReplenishmentItem;
	}

	/**
	 * Removes the commerce inventory replenishment item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceInventoryReplenishmentItemId the primary key of the commerce inventory replenishment item
	 * @return the commerce inventory replenishment item that was removed
	 * @throws NoSuchInventoryReplenishmentItemException if a commerce inventory replenishment item with the primary key could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem remove(
			long commerceInventoryReplenishmentItemId)
		throws NoSuchInventoryReplenishmentItemException {

		return remove((Serializable)commerceInventoryReplenishmentItemId);
	}

	@Override
	protected CommerceInventoryReplenishmentItem removeImpl(
		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceInventoryReplenishmentItem)) {
				commerceInventoryReplenishmentItem =
					(CommerceInventoryReplenishmentItem)session.get(
						CommerceInventoryReplenishmentItemImpl.class,
						commerceInventoryReplenishmentItem.getPrimaryKeyObj());
			}

			if (commerceInventoryReplenishmentItem != null) {
				session.delete(commerceInventoryReplenishmentItem);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceInventoryReplenishmentItem != null) {
			clearCache(commerceInventoryReplenishmentItem);
		}

		return commerceInventoryReplenishmentItem;
	}

	@Override
	public CommerceInventoryReplenishmentItem updateImpl(
		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem) {

		boolean isNew = commerceInventoryReplenishmentItem.isNew();

		if (!(commerceInventoryReplenishmentItem instanceof
				CommerceInventoryReplenishmentItemModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commerceInventoryReplenishmentItem.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceInventoryReplenishmentItem);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceInventoryReplenishmentItem proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceInventoryReplenishmentItem implementation " +
					commerceInventoryReplenishmentItem.getClass());
		}

		CommerceInventoryReplenishmentItemModelImpl
			commerceInventoryReplenishmentItemModelImpl =
				(CommerceInventoryReplenishmentItemModelImpl)
					commerceInventoryReplenishmentItem;

		if (Validator.isNull(commerceInventoryReplenishmentItem.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commerceInventoryReplenishmentItem.setUuid(uuid);
		}

		if (Validator.isNull(
				commerceInventoryReplenishmentItem.
					getExternalReferenceCode())) {

			commerceInventoryReplenishmentItem.setExternalReferenceCode(
				commerceInventoryReplenishmentItem.getUuid());
		}
		else {
			if (!Objects.equals(
					commerceInventoryReplenishmentItemModelImpl.
						getColumnOriginalValue("externalReferenceCode"),
					commerceInventoryReplenishmentItem.
						getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId =
						commerceInventoryReplenishmentItem.getCompanyId();

					long groupId = 0;

					long classPK = 0;

					if (!isNew) {
						classPK =
							commerceInventoryReplenishmentItem.getPrimaryKey();
					}

					try {
						commerceInventoryReplenishmentItem.
							setExternalReferenceCode(
								SanitizerUtil.sanitize(
									companyId, groupId, userId,
									CommerceInventoryReplenishmentItem.class.
										getName(),
									classPK, ContentTypes.TEXT_HTML,
									Sanitizer.MODE_ALL,
									commerceInventoryReplenishmentItem.
										getExternalReferenceCode(),
									null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}

			CommerceInventoryReplenishmentItem
				ercCommerceInventoryReplenishmentItem = fetchByERC_C(
					commerceInventoryReplenishmentItem.
						getExternalReferenceCode(),
					commerceInventoryReplenishmentItem.getCompanyId());

			if (isNew) {
				if (ercCommerceInventoryReplenishmentItem != null) {
					throw new DuplicateCommerceInventoryReplenishmentItemExternalReferenceCodeException(
						"Duplicate commerce inventory replenishment item with external reference code " +
							commerceInventoryReplenishmentItem.
								getExternalReferenceCode() + " and company " +
									commerceInventoryReplenishmentItem.
										getCompanyId());
				}
			}
			else {
				if ((ercCommerceInventoryReplenishmentItem != null) &&
					(commerceInventoryReplenishmentItem.
						getCommerceInventoryReplenishmentItemId() !=
							ercCommerceInventoryReplenishmentItem.
								getCommerceInventoryReplenishmentItemId())) {

					throw new DuplicateCommerceInventoryReplenishmentItemExternalReferenceCodeException(
						"Duplicate commerce inventory replenishment item with external reference code " +
							commerceInventoryReplenishmentItem.
								getExternalReferenceCode() + " and company " +
									commerceInventoryReplenishmentItem.
										getCompanyId());
				}
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew &&
			(commerceInventoryReplenishmentItem.getCreateDate() == null)) {

			if (serviceContext == null) {
				commerceInventoryReplenishmentItem.setCreateDate(date);
			}
			else {
				commerceInventoryReplenishmentItem.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceInventoryReplenishmentItemModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceInventoryReplenishmentItem.setModifiedDate(date);
			}
			else {
				commerceInventoryReplenishmentItem.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceInventoryReplenishmentItem);
			}
			else {
				commerceInventoryReplenishmentItem =
					(CommerceInventoryReplenishmentItem)session.merge(
						commerceInventoryReplenishmentItem);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceInventoryReplenishmentItemImpl.class,
			commerceInventoryReplenishmentItemModelImpl, false, true);

		cacheUniqueFindersCache(commerceInventoryReplenishmentItemModelImpl);

		if (isNew) {
			commerceInventoryReplenishmentItem.setNew(false);
		}

		commerceInventoryReplenishmentItem.resetOriginalValues();

		return commerceInventoryReplenishmentItem;
	}

	/**
	 * Returns the commerce inventory replenishment item with the primary key or throws a <code>NoSuchInventoryReplenishmentItemException</code> if it could not be found.
	 *
	 * @param commerceInventoryReplenishmentItemId the primary key of the commerce inventory replenishment item
	 * @return the commerce inventory replenishment item
	 * @throws NoSuchInventoryReplenishmentItemException if a commerce inventory replenishment item with the primary key could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem findByPrimaryKey(
			long commerceInventoryReplenishmentItemId)
		throws NoSuchInventoryReplenishmentItemException {

		return findByPrimaryKey(
			(Serializable)commerceInventoryReplenishmentItemId);
	}

	/**
	 * Returns the commerce inventory replenishment item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceInventoryReplenishmentItemId the primary key of the commerce inventory replenishment item
	 * @return the commerce inventory replenishment item, or <code>null</code> if a commerce inventory replenishment item with the primary key could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem fetchByPrimaryKey(
		long commerceInventoryReplenishmentItemId) {

		return fetchByPrimaryKey(
			(Serializable)commerceInventoryReplenishmentItemId);
	}

	/**
	 * Returns all the commerce inventory replenishment items.
	 *
	 * @return the commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory replenishment items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @return the range of commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findAll(
		int start, int end) {

		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory replenishment items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findAll(
		int start, int end,
		OrderByComparator<CommerceInventoryReplenishmentItem>
			orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory replenishment items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findAll(
		int start, int end,
		OrderByComparator<CommerceInventoryReplenishmentItem> orderByComparator,
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

		List<CommerceInventoryReplenishmentItem> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceInventoryReplenishmentItem>)finderCache.getResult(
					finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEINVENTORYREPLENISHMENTITEM);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEINVENTORYREPLENISHMENTITEM;

				sql = sql.concat(
					CommerceInventoryReplenishmentItemModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceInventoryReplenishmentItem>)QueryUtil.list(
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
	 * Removes all the commerce inventory replenishment items from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceInventoryReplenishmentItem
				commerceInventoryReplenishmentItem : findAll()) {

			remove(commerceInventoryReplenishmentItem);
		}
	}

	/**
	 * Returns the number of commerce inventory replenishment items.
	 *
	 * @return the number of commerce inventory replenishment items
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
					_SQL_COUNT_COMMERCEINVENTORYREPLENISHMENTITEM);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "CIReplenishmentItemId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEINVENTORYREPLENISHMENTITEM;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceInventoryReplenishmentItemModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce inventory replenishment item persistence.
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
			_SQL_SELECT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE,
			_SQL_COUNT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE,
			CommerceInventoryReplenishmentItemModelImpl.ORDER_BY_JPQL,
			_ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"commerceInventoryReplenishmentItem.", "uuid",
				FinderColumn.Type.STRING, "=", true, true,
				CommerceInventoryReplenishmentItem::getUuid));

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
				_SQL_SELECT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE,
				_SQL_COUNT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE,
				CommerceInventoryReplenishmentItemModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"commerceInventoryReplenishmentItem.", "uuid",
					FinderColumn.Type.STRING, "=", true, false,
					CommerceInventoryReplenishmentItem::getUuid),
				new FinderColumn<>(
					"commerceInventoryReplenishmentItem.", "companyId",
					FinderColumn.Type.LONG, "=", true, true,
					CommerceInventoryReplenishmentItem::getCompanyId));

		_finderPathWithPaginationFindByCommerceInventoryWarehouseId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByCommerceInventoryWarehouseId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"commerceInventoryWarehouseId"}, true);

		_finderPathWithoutPaginationFindByCommerceInventoryWarehouseId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCommerceInventoryWarehouseId",
				new String[] {Long.class.getName()},
				new String[] {"commerceInventoryWarehouseId"}, true);

		_finderPathCountByCommerceInventoryWarehouseId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceInventoryWarehouseId",
			new String[] {Long.class.getName()},
			new String[] {"commerceInventoryWarehouseId"}, false);

		_collectionPersistenceFinderByCommerceInventoryWarehouseId =
			new CollectionPersistenceFinder<>(
				this,
				_finderPathWithPaginationFindByCommerceInventoryWarehouseId,
				_finderPathWithoutPaginationFindByCommerceInventoryWarehouseId,
				_finderPathCountByCommerceInventoryWarehouseId,
				_SQL_SELECT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE,
				_SQL_COUNT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE,
				CommerceInventoryReplenishmentItemModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"commerceInventoryReplenishmentItem.",
					"commerceInventoryWarehouseId", FinderColumn.Type.LONG, "=",
					true, true,
					CommerceInventoryReplenishmentItem::
						getCommerceInventoryWarehouseId));

		_finderPathWithPaginationFindByAvailabilityDate = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAvailabilityDate",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"availabilityDate"}, true);

		_finderPathWithoutPaginationFindByAvailabilityDate = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAvailabilityDate",
			new String[] {Date.class.getName()},
			new String[] {"availabilityDate"}, true);

		_finderPathCountByAvailabilityDate = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByAvailabilityDate", new String[] {Date.class.getName()},
			new String[] {"availabilityDate"}, false);

		_collectionPersistenceFinderByAvailabilityDate =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByAvailabilityDate,
				_finderPathWithoutPaginationFindByAvailabilityDate,
				_finderPathCountByAvailabilityDate,
				_SQL_SELECT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE,
				_SQL_COUNT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE,
				CommerceInventoryReplenishmentItemModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"commerceInventoryReplenishmentItem.", "availabilityDate",
					FinderColumn.Type.DATE, "=", true, true,
					CommerceInventoryReplenishmentItem::getAvailabilityDate));

		_finderPathWithPaginationFindBySku = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySku",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"sku"}, true);

		_finderPathWithoutPaginationFindBySku = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySku",
			new String[] {String.class.getName()}, new String[] {"sku"}, true);

		_finderPathCountBySku = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySku",
			new String[] {String.class.getName()}, new String[] {"sku"}, false);

		_collectionPersistenceFinderBySku = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindBySku,
			_finderPathWithoutPaginationFindBySku, _finderPathCountBySku,
			_SQL_SELECT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE,
			_SQL_COUNT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE,
			CommerceInventoryReplenishmentItemModelImpl.ORDER_BY_JPQL,
			_ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"commerceInventoryReplenishmentItem.", "sku",
				FinderColumn.Type.STRING, "=", true, true,
				CommerceInventoryReplenishmentItem::getSku));

		_finderPathWithPaginationFindByC_S_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_S_U",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "sku", "unitOfMeasureKey"}, true);

		_finderPathWithoutPaginationFindByC_S_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_S_U",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"companyId", "sku", "unitOfMeasureKey"}, true);

		_finderPathCountByC_S_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S_U",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"companyId", "sku", "unitOfMeasureKey"}, false);

		_collectionPersistenceFinderByC_S_U = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_S_U,
			_finderPathWithoutPaginationFindByC_S_U, _finderPathCountByC_S_U,
			_SQL_SELECT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE,
			_SQL_COUNT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE,
			CommerceInventoryReplenishmentItemModelImpl.ORDER_BY_JPQL,
			_ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"commerceInventoryReplenishmentItem.", "companyId",
				FinderColumn.Type.LONG, "=", true, false,
				CommerceInventoryReplenishmentItem::getCompanyId),
			new FinderColumn<>(
				"commerceInventoryReplenishmentItem.", "sku",
				FinderColumn.Type.STRING, "=", true, false,
				CommerceInventoryReplenishmentItem::getSku),
			new FinderColumn<>(
				"commerceInventoryReplenishmentItem.", "unitOfMeasureKey",
				FinderColumn.Type.STRING, "=", true, true,
				CommerceInventoryReplenishmentItem::getUnitOfMeasureKey));

		_finderPathWithPaginationFindByAD_S_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAD_S_U",
			new String[] {
				Date.class.getName(), String.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"availabilityDate", "sku", "unitOfMeasureKey"}, true);

		_finderPathWithoutPaginationFindByAD_S_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAD_S_U",
			new String[] {
				Date.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"availabilityDate", "sku", "unitOfMeasureKey"}, true);

		_finderPathCountByAD_S_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAD_S_U",
			new String[] {
				Date.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"availabilityDate", "sku", "unitOfMeasureKey"},
			false);

		_collectionPersistenceFinderByAD_S_U =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByAD_S_U,
				_finderPathWithoutPaginationFindByAD_S_U,
				_finderPathCountByAD_S_U,
				_SQL_SELECT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE,
				_SQL_COUNT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE,
				CommerceInventoryReplenishmentItemModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"commerceInventoryReplenishmentItem.", "availabilityDate",
					FinderColumn.Type.DATE, "=", true, false,
					CommerceInventoryReplenishmentItem::getAvailabilityDate),
				new FinderColumn<>(
					"commerceInventoryReplenishmentItem.", "sku",
					FinderColumn.Type.STRING, "=", true, false,
					CommerceInventoryReplenishmentItem::getSku),
				new FinderColumn<>(
					"commerceInventoryReplenishmentItem.", "unitOfMeasureKey",
					FinderColumn.Type.STRING, "=", true, true,
					CommerceInventoryReplenishmentItem::getUnitOfMeasureKey));

		_finderPathFetchByERC_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByERC_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"externalReferenceCode", "companyId"}, true);

		_uniquePersistenceFinderByERC_C = new UniquePersistenceFinder<>(
			this, _finderPathFetchByERC_C,
			_SQL_SELECT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE,
			new FinderColumn<>(
				"commerceInventoryReplenishmentItem.", "externalReferenceCode",
				FinderColumn.Type.STRING, "=", true, false,
				CommerceInventoryReplenishmentItem::getExternalReferenceCode),
			new FinderColumn<>(
				"commerceInventoryReplenishmentItem.", "companyId",
				FinderColumn.Type.LONG, "=", true, true,
				CommerceInventoryReplenishmentItem::getCompanyId));

		CommerceInventoryReplenishmentItemUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CommerceInventoryReplenishmentItemUtil.setPersistence(null);

		entityCache.removeCache(
			CommerceInventoryReplenishmentItemImpl.class.getName());
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

	private static final String _SQL_SELECT_COMMERCEINVENTORYREPLENISHMENTITEM =
		"SELECT commerceInventoryReplenishmentItem FROM CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem";

	private static final String
		_SQL_SELECT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE =
			"SELECT commerceInventoryReplenishmentItem FROM CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem WHERE ";

	private static final String _SQL_COUNT_COMMERCEINVENTORYREPLENISHMENTITEM =
		"SELECT COUNT(commerceInventoryReplenishmentItem) FROM CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem";

	private static final String
		_SQL_COUNT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE =
			"SELECT COUNT(commerceInventoryReplenishmentItem) FROM CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceInventoryReplenishmentItem.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceInventoryReplenishmentItem exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceInventoryReplenishmentItemPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "commerceInventoryReplenishmentItemId"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:783943233