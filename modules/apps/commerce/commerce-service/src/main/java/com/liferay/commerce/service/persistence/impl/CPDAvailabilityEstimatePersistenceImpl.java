/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service.persistence.impl;

import com.liferay.commerce.exception.NoSuchCPDAvailabilityEstimateException;
import com.liferay.commerce.model.CPDAvailabilityEstimate;
import com.liferay.commerce.model.CPDAvailabilityEstimateTable;
import com.liferay.commerce.model.impl.CPDAvailabilityEstimateImpl;
import com.liferay.commerce.model.impl.CPDAvailabilityEstimateModelImpl;
import com.liferay.commerce.service.persistence.CPDAvailabilityEstimatePersistence;
import com.liferay.commerce.service.persistence.CPDAvailabilityEstimateUtil;
import com.liferay.commerce.service.persistence.impl.constants.CommercePersistenceConstants;
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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the cpd availability estimate service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
@Component(service = CPDAvailabilityEstimatePersistence.class)
public class CPDAvailabilityEstimatePersistenceImpl
	extends BasePersistenceImpl
		<CPDAvailabilityEstimate, NoSuchCPDAvailabilityEstimateException>
	implements CPDAvailabilityEstimatePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CPDAvailabilityEstimateUtil</code> to access the cpd availability estimate persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CPDAvailabilityEstimateImpl.class.getName();

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
	private CollectionPersistenceFinder<CPDAvailabilityEstimate>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the cpd availability estimates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cpd availability estimates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cpd availability estimates
	 * @param end the upper bound of the range of cpd availability estimates (not inclusive)
	 * @return the range of matching cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cpd availability estimates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cpd availability estimates
	 * @param end the upper bound of the range of cpd availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cpd availability estimates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cpd availability estimates
	 * @param end the upper bound of the range of cpd availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid.find(
			finderCache, new Object[] {uuid}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first cpd availability estimate in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpd availability estimate
	 * @throws NoSuchCPDAvailabilityEstimateException if a matching cpd availability estimate could not be found
	 */
	@Override
	public CPDAvailabilityEstimate findByUuid_First(
			String uuid,
			OrderByComparator<CPDAvailabilityEstimate> orderByComparator)
		throws NoSuchCPDAvailabilityEstimateException {

		CPDAvailabilityEstimate cpdAvailabilityEstimate = fetchByUuid_First(
			uuid, orderByComparator);

		if (cpdAvailabilityEstimate != null) {
			return cpdAvailabilityEstimate;
		}

		throw new NoSuchCPDAvailabilityEstimateException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first cpd availability estimate in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	 */
	@Override
	public CPDAvailabilityEstimate fetchByUuid_First(
		String uuid,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Removes all the cpd availability estimates where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of cpd availability estimates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cpd availability estimates
	 */
	@Override
	public int countByUuid(String uuid) {
		return _collectionPersistenceFinderByUuid.count(
			finderCache, new Object[] {uuid});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<CPDAvailabilityEstimate>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the cpd availability estimates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cpd availability estimates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cpd availability estimates
	 * @param end the upper bound of the range of cpd availability estimates (not inclusive)
	 * @return the range of matching cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cpd availability estimates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cpd availability estimates
	 * @param end the upper bound of the range of cpd availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cpd availability estimates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cpd availability estimates
	 * @param end the upper bound of the range of cpd availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid_C.find(
			finderCache, new Object[] {uuid, companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cpd availability estimate in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpd availability estimate
	 * @throws NoSuchCPDAvailabilityEstimateException if a matching cpd availability estimate could not be found
	 */
	@Override
	public CPDAvailabilityEstimate findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CPDAvailabilityEstimate> orderByComparator)
		throws NoSuchCPDAvailabilityEstimateException {

		CPDAvailabilityEstimate cpdAvailabilityEstimate = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (cpdAvailabilityEstimate != null) {
			return cpdAvailabilityEstimate;
		}

		throw new NoSuchCPDAvailabilityEstimateException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first cpd availability estimate in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	 */
	@Override
	public CPDAvailabilityEstimate fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Removes all the cpd availability estimates where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of cpd availability estimates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cpd availability estimates
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		return _collectionPersistenceFinderByUuid_C.count(
			finderCache, new Object[] {uuid, companyId});
	}

	private FinderPath
		_finderPathWithPaginationFindByCommerceAvailabilityEstimateId;
	private FinderPath
		_finderPathWithoutPaginationFindByCommerceAvailabilityEstimateId;
	private FinderPath _finderPathCountByCommerceAvailabilityEstimateId;
	private CollectionPersistenceFinder<CPDAvailabilityEstimate>
		_collectionPersistenceFinderByCommerceAvailabilityEstimateId;

	/**
	 * Returns all the cpd availability estimates where commerceAvailabilityEstimateId = &#63;.
	 *
	 * @param commerceAvailabilityEstimateId the commerce availability estimate ID
	 * @return the matching cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findByCommerceAvailabilityEstimateId(
		long commerceAvailabilityEstimateId) {

		return findByCommerceAvailabilityEstimateId(
			commerceAvailabilityEstimateId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cpd availability estimates where commerceAvailabilityEstimateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAvailabilityEstimateId the commerce availability estimate ID
	 * @param start the lower bound of the range of cpd availability estimates
	 * @param end the upper bound of the range of cpd availability estimates (not inclusive)
	 * @return the range of matching cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findByCommerceAvailabilityEstimateId(
		long commerceAvailabilityEstimateId, int start, int end) {

		return findByCommerceAvailabilityEstimateId(
			commerceAvailabilityEstimateId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cpd availability estimates where commerceAvailabilityEstimateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAvailabilityEstimateId the commerce availability estimate ID
	 * @param start the lower bound of the range of cpd availability estimates
	 * @param end the upper bound of the range of cpd availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findByCommerceAvailabilityEstimateId(
		long commerceAvailabilityEstimateId, int start, int end,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator) {

		return findByCommerceAvailabilityEstimateId(
			commerceAvailabilityEstimateId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the cpd availability estimates where commerceAvailabilityEstimateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAvailabilityEstimateId the commerce availability estimate ID
	 * @param start the lower bound of the range of cpd availability estimates
	 * @param end the upper bound of the range of cpd availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findByCommerceAvailabilityEstimateId(
		long commerceAvailabilityEstimateId, int start, int end,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByCommerceAvailabilityEstimateId.
			find(
				finderCache, new Object[] {commerceAvailabilityEstimateId},
				start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cpd availability estimate in the ordered set where commerceAvailabilityEstimateId = &#63;.
	 *
	 * @param commerceAvailabilityEstimateId the commerce availability estimate ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpd availability estimate
	 * @throws NoSuchCPDAvailabilityEstimateException if a matching cpd availability estimate could not be found
	 */
	@Override
	public CPDAvailabilityEstimate findByCommerceAvailabilityEstimateId_First(
			long commerceAvailabilityEstimateId,
			OrderByComparator<CPDAvailabilityEstimate> orderByComparator)
		throws NoSuchCPDAvailabilityEstimateException {

		CPDAvailabilityEstimate cpdAvailabilityEstimate =
			fetchByCommerceAvailabilityEstimateId_First(
				commerceAvailabilityEstimateId, orderByComparator);

		if (cpdAvailabilityEstimate != null) {
			return cpdAvailabilityEstimate;
		}

		throw new NoSuchCPDAvailabilityEstimateException(
			_collectionPersistenceFinderByCommerceAvailabilityEstimateId.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {commerceAvailabilityEstimateId}));
	}

	/**
	 * Returns the first cpd availability estimate in the ordered set where commerceAvailabilityEstimateId = &#63;.
	 *
	 * @param commerceAvailabilityEstimateId the commerce availability estimate ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	 */
	@Override
	public CPDAvailabilityEstimate fetchByCommerceAvailabilityEstimateId_First(
		long commerceAvailabilityEstimateId,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator) {

		return _collectionPersistenceFinderByCommerceAvailabilityEstimateId.
			fetchFirst(
				finderCache, new Object[] {commerceAvailabilityEstimateId},
				orderByComparator);
	}

	/**
	 * Removes all the cpd availability estimates where commerceAvailabilityEstimateId = &#63; from the database.
	 *
	 * @param commerceAvailabilityEstimateId the commerce availability estimate ID
	 */
	@Override
	public void removeByCommerceAvailabilityEstimateId(
		long commerceAvailabilityEstimateId) {

		_collectionPersistenceFinderByCommerceAvailabilityEstimateId.remove(
			finderCache, new Object[] {commerceAvailabilityEstimateId});
	}

	/**
	 * Returns the number of cpd availability estimates where commerceAvailabilityEstimateId = &#63;.
	 *
	 * @param commerceAvailabilityEstimateId the commerce availability estimate ID
	 * @return the number of matching cpd availability estimates
	 */
	@Override
	public int countByCommerceAvailabilityEstimateId(
		long commerceAvailabilityEstimateId) {

		return _collectionPersistenceFinderByCommerceAvailabilityEstimateId.
			count(finderCache, new Object[] {commerceAvailabilityEstimateId});
	}

	private FinderPath _finderPathFetchByCProductId;
	private UniquePersistenceFinder<CPDAvailabilityEstimate>
		_uniquePersistenceFinderByCProductId;

	/**
	 * Returns the cpd availability estimate where CProductId = &#63; or throws a <code>NoSuchCPDAvailabilityEstimateException</code> if it could not be found.
	 *
	 * @param CProductId the c product ID
	 * @return the matching cpd availability estimate
	 * @throws NoSuchCPDAvailabilityEstimateException if a matching cpd availability estimate could not be found
	 */
	@Override
	public CPDAvailabilityEstimate findByCProductId(long CProductId)
		throws NoSuchCPDAvailabilityEstimateException {

		CPDAvailabilityEstimate cpdAvailabilityEstimate = fetchByCProductId(
			CProductId);

		if (cpdAvailabilityEstimate == null) {
			String message =
				_uniquePersistenceFinderByCProductId.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {CProductId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchCPDAvailabilityEstimateException(message);
		}

		return cpdAvailabilityEstimate;
	}

	/**
	 * Returns the cpd availability estimate where CProductId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CProductId the c product ID
	 * @return the matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	 */
	@Override
	public CPDAvailabilityEstimate fetchByCProductId(long CProductId) {
		return fetchByCProductId(CProductId, true);
	}

	/**
	 * Returns the cpd availability estimate where CProductId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CProductId the c product ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	 */
	@Override
	public CPDAvailabilityEstimate fetchByCProductId(
		long CProductId, boolean useFinderCache) {

		return _uniquePersistenceFinderByCProductId.fetch(
			finderCache, new Object[] {CProductId}, useFinderCache);
	}

	/**
	 * Removes the cpd availability estimate where CProductId = &#63; from the database.
	 *
	 * @param CProductId the c product ID
	 * @return the cpd availability estimate that was removed
	 */
	@Override
	public CPDAvailabilityEstimate removeByCProductId(long CProductId)
		throws NoSuchCPDAvailabilityEstimateException {

		CPDAvailabilityEstimate cpdAvailabilityEstimate = findByCProductId(
			CProductId);

		return remove(cpdAvailabilityEstimate);
	}

	/**
	 * Returns the number of cpd availability estimates where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @return the number of matching cpd availability estimates
	 */
	@Override
	public int countByCProductId(long CProductId) {
		return _uniquePersistenceFinderByCProductId.count(
			finderCache, new Object[] {CProductId});
	}

	public CPDAvailabilityEstimatePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CPDAvailabilityEstimate.class);

		setModelImplClass(CPDAvailabilityEstimateImpl.class);
		setModelPKClass(long.class);

		setTable(CPDAvailabilityEstimateTable.INSTANCE);
	}

	/**
	 * Caches the cpd availability estimate in the entity cache if it is enabled.
	 *
	 * @param cpdAvailabilityEstimate the cpd availability estimate
	 */
	@Override
	public void cacheResult(CPDAvailabilityEstimate cpdAvailabilityEstimate) {
		entityCache.putResult(
			CPDAvailabilityEstimateImpl.class,
			cpdAvailabilityEstimate.getPrimaryKey(), cpdAvailabilityEstimate);

		finderCache.putResult(
			_finderPathFetchByCProductId,
			new Object[] {cpdAvailabilityEstimate.getCProductId()},
			cpdAvailabilityEstimate);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the cpd availability estimates in the entity cache if it is enabled.
	 *
	 * @param cpdAvailabilityEstimates the cpd availability estimates
	 */
	@Override
	public void cacheResult(
		List<CPDAvailabilityEstimate> cpdAvailabilityEstimates) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (cpdAvailabilityEstimates.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CPDAvailabilityEstimate cpdAvailabilityEstimate :
				cpdAvailabilityEstimates) {

			if (entityCache.getResult(
					CPDAvailabilityEstimateImpl.class,
					cpdAvailabilityEstimate.getPrimaryKey()) == null) {

				cacheResult(cpdAvailabilityEstimate);
			}
		}
	}

	protected void cacheUniqueFindersCache(
		CPDAvailabilityEstimateModelImpl cpdAvailabilityEstimateModelImpl) {

		Object[] args = new Object[] {
			cpdAvailabilityEstimateModelImpl.getCProductId()
		};

		finderCache.putResult(
			_finderPathFetchByCProductId, args,
			cpdAvailabilityEstimateModelImpl);
	}

	/**
	 * Creates a new cpd availability estimate with the primary key. Does not add the cpd availability estimate to the database.
	 *
	 * @param CPDAvailabilityEstimateId the primary key for the new cpd availability estimate
	 * @return the new cpd availability estimate
	 */
	@Override
	public CPDAvailabilityEstimate create(long CPDAvailabilityEstimateId) {
		CPDAvailabilityEstimate cpdAvailabilityEstimate =
			new CPDAvailabilityEstimateImpl();

		cpdAvailabilityEstimate.setNew(true);
		cpdAvailabilityEstimate.setPrimaryKey(CPDAvailabilityEstimateId);

		String uuid = PortalUUIDUtil.generate();

		cpdAvailabilityEstimate.setUuid(uuid);

		cpdAvailabilityEstimate.setCompanyId(CompanyThreadLocal.getCompanyId());

		return cpdAvailabilityEstimate;
	}

	/**
	 * Removes the cpd availability estimate with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDAvailabilityEstimateId the primary key of the cpd availability estimate
	 * @return the cpd availability estimate that was removed
	 * @throws NoSuchCPDAvailabilityEstimateException if a cpd availability estimate with the primary key could not be found
	 */
	@Override
	public CPDAvailabilityEstimate remove(long CPDAvailabilityEstimateId)
		throws NoSuchCPDAvailabilityEstimateException {

		return remove((Serializable)CPDAvailabilityEstimateId);
	}

	@Override
	protected CPDAvailabilityEstimate removeImpl(
		CPDAvailabilityEstimate cpdAvailabilityEstimate) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpdAvailabilityEstimate)) {
				cpdAvailabilityEstimate = (CPDAvailabilityEstimate)session.get(
					CPDAvailabilityEstimateImpl.class,
					cpdAvailabilityEstimate.getPrimaryKeyObj());
			}

			if (cpdAvailabilityEstimate != null) {
				session.delete(cpdAvailabilityEstimate);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cpdAvailabilityEstimate != null) {
			clearCache(cpdAvailabilityEstimate);
		}

		return cpdAvailabilityEstimate;
	}

	@Override
	public CPDAvailabilityEstimate updateImpl(
		CPDAvailabilityEstimate cpdAvailabilityEstimate) {

		boolean isNew = cpdAvailabilityEstimate.isNew();

		if (!(cpdAvailabilityEstimate instanceof
				CPDAvailabilityEstimateModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cpdAvailabilityEstimate.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					cpdAvailabilityEstimate);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpdAvailabilityEstimate proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPDAvailabilityEstimate implementation " +
					cpdAvailabilityEstimate.getClass());
		}

		CPDAvailabilityEstimateModelImpl cpdAvailabilityEstimateModelImpl =
			(CPDAvailabilityEstimateModelImpl)cpdAvailabilityEstimate;

		if (Validator.isNull(cpdAvailabilityEstimate.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpdAvailabilityEstimate.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (cpdAvailabilityEstimate.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpdAvailabilityEstimate.setCreateDate(date);
			}
			else {
				cpdAvailabilityEstimate.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!cpdAvailabilityEstimateModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpdAvailabilityEstimate.setModifiedDate(date);
			}
			else {
				cpdAvailabilityEstimate.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(cpdAvailabilityEstimate);
			}
			else {
				cpdAvailabilityEstimate =
					(CPDAvailabilityEstimate)session.merge(
						cpdAvailabilityEstimate);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CPDAvailabilityEstimateImpl.class, cpdAvailabilityEstimateModelImpl,
			false, true);

		cacheUniqueFindersCache(cpdAvailabilityEstimateModelImpl);

		if (isNew) {
			cpdAvailabilityEstimate.setNew(false);
		}

		cpdAvailabilityEstimate.resetOriginalValues();

		return cpdAvailabilityEstimate;
	}

	/**
	 * Returns the cpd availability estimate with the primary key or throws a <code>NoSuchCPDAvailabilityEstimateException</code> if it could not be found.
	 *
	 * @param CPDAvailabilityEstimateId the primary key of the cpd availability estimate
	 * @return the cpd availability estimate
	 * @throws NoSuchCPDAvailabilityEstimateException if a cpd availability estimate with the primary key could not be found
	 */
	@Override
	public CPDAvailabilityEstimate findByPrimaryKey(
			long CPDAvailabilityEstimateId)
		throws NoSuchCPDAvailabilityEstimateException {

		return findByPrimaryKey((Serializable)CPDAvailabilityEstimateId);
	}

	/**
	 * Returns the cpd availability estimate with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDAvailabilityEstimateId the primary key of the cpd availability estimate
	 * @return the cpd availability estimate, or <code>null</code> if a cpd availability estimate with the primary key could not be found
	 */
	@Override
	public CPDAvailabilityEstimate fetchByPrimaryKey(
		long CPDAvailabilityEstimateId) {

		return fetchByPrimaryKey((Serializable)CPDAvailabilityEstimateId);
	}

	/**
	 * Returns all the cpd availability estimates.
	 *
	 * @return the cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cpd availability estimates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cpd availability estimates
	 * @param end the upper bound of the range of cpd availability estimates (not inclusive)
	 * @return the range of cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cpd availability estimates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cpd availability estimates
	 * @param end the upper bound of the range of cpd availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findAll(
		int start, int end,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cpd availability estimates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cpd availability estimates
	 * @param end the upper bound of the range of cpd availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findAll(
		int start, int end,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator,
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

		List<CPDAvailabilityEstimate> list = null;

		if (useFinderCache) {
			list = (List<CPDAvailabilityEstimate>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CPDAVAILABILITYESTIMATE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CPDAVAILABILITYESTIMATE;

				sql = sql.concat(
					CPDAvailabilityEstimateModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CPDAvailabilityEstimate>)QueryUtil.list(
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
	 * Removes all the cpd availability estimates from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPDAvailabilityEstimate cpdAvailabilityEstimate : findAll()) {
			remove(cpdAvailabilityEstimate);
		}
	}

	/**
	 * Returns the number of cpd availability estimates.
	 *
	 * @return the number of cpd availability estimates
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
					_SQL_COUNT_CPDAVAILABILITYESTIMATE);

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
		return "CPDAvailabilityEstimateId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CPDAVAILABILITYESTIMATE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CPDAvailabilityEstimateModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cpd availability estimate persistence.
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
			_SQL_SELECT_CPDAVAILABILITYESTIMATE_WHERE,
			_SQL_COUNT_CPDAVAILABILITYESTIMATE_WHERE,
			CPDAvailabilityEstimateModelImpl.ORDER_BY_JPQL,
			_ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"cpdAvailabilityEstimate.", "uuid", FinderColumn.Type.STRING,
				"=", true, true, CPDAvailabilityEstimate::getUuid));

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
				_SQL_SELECT_CPDAVAILABILITYESTIMATE_WHERE,
				_SQL_COUNT_CPDAVAILABILITYESTIMATE_WHERE,
				CPDAvailabilityEstimateModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"cpdAvailabilityEstimate.", "uuid",
					FinderColumn.Type.STRING, "=", true, false,
					CPDAvailabilityEstimate::getUuid),
				new FinderColumn<>(
					"cpdAvailabilityEstimate.", "companyId",
					FinderColumn.Type.LONG, "=", true, true,
					CPDAvailabilityEstimate::getCompanyId));

		_finderPathWithPaginationFindByCommerceAvailabilityEstimateId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByCommerceAvailabilityEstimateId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"commerceAvailabilityEstimateId"}, true);

		_finderPathWithoutPaginationFindByCommerceAvailabilityEstimateId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCommerceAvailabilityEstimateId",
				new String[] {Long.class.getName()},
				new String[] {"commerceAvailabilityEstimateId"}, true);

		_finderPathCountByCommerceAvailabilityEstimateId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceAvailabilityEstimateId",
			new String[] {Long.class.getName()},
			new String[] {"commerceAvailabilityEstimateId"}, false);

		_collectionPersistenceFinderByCommerceAvailabilityEstimateId =
			new CollectionPersistenceFinder<>(
				this,
				_finderPathWithPaginationFindByCommerceAvailabilityEstimateId,
				_finderPathWithoutPaginationFindByCommerceAvailabilityEstimateId,
				_finderPathCountByCommerceAvailabilityEstimateId,
				_SQL_SELECT_CPDAVAILABILITYESTIMATE_WHERE,
				_SQL_COUNT_CPDAVAILABILITYESTIMATE_WHERE,
				CPDAvailabilityEstimateModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"cpdAvailabilityEstimate.",
					"commerceAvailabilityEstimateId", FinderColumn.Type.LONG,
					"=", true, true,
					CPDAvailabilityEstimate::
						getCommerceAvailabilityEstimateId));

		_finderPathFetchByCProductId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCProductId",
			new String[] {Long.class.getName()}, new String[] {"CProductId"},
			true);

		_uniquePersistenceFinderByCProductId = new UniquePersistenceFinder<>(
			this, _finderPathFetchByCProductId,
			_SQL_SELECT_CPDAVAILABILITYESTIMATE_WHERE,
			new FinderColumn<>(
				"cpdAvailabilityEstimate.", "CProductId",
				FinderColumn.Type.LONG, "=", true, true,
				CPDAvailabilityEstimate::getCProductId));

		CPDAvailabilityEstimateUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CPDAvailabilityEstimateUtil.setPersistence(null);

		entityCache.removeCache(CPDAvailabilityEstimateImpl.class.getName());
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

	private static final String _SQL_SELECT_CPDAVAILABILITYESTIMATE =
		"SELECT cpdAvailabilityEstimate FROM CPDAvailabilityEstimate cpdAvailabilityEstimate";

	private static final String _SQL_SELECT_CPDAVAILABILITYESTIMATE_WHERE =
		"SELECT cpdAvailabilityEstimate FROM CPDAvailabilityEstimate cpdAvailabilityEstimate WHERE ";

	private static final String _SQL_COUNT_CPDAVAILABILITYESTIMATE =
		"SELECT COUNT(cpdAvailabilityEstimate) FROM CPDAvailabilityEstimate cpdAvailabilityEstimate";

	private static final String _SQL_COUNT_CPDAVAILABILITYESTIMATE_WHERE =
		"SELECT COUNT(cpdAvailabilityEstimate) FROM CPDAvailabilityEstimate cpdAvailabilityEstimate WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"cpdAvailabilityEstimate.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CPDAvailabilityEstimate exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CPDAvailabilityEstimatePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-2051341673