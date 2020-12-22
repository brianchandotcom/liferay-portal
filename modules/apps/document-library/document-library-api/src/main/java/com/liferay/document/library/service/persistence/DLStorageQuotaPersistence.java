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

package com.liferay.document.library.service.persistence;

import com.liferay.document.library.exception.NoSuchStorageQuotaException;
import com.liferay.document.library.model.DLStorageQuota;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the dl storage quota service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLStorageQuotaUtil
 * @generated
 */
@ProviderType
public interface DLStorageQuotaPersistence
	extends BasePersistence<DLStorageQuota> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLStorageQuotaUtil} to access the dl storage quota persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the dl storage quota where companyId = &#63; or throws a <code>NoSuchStorageQuotaException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @return the matching dl storage quota
	 * @throws NoSuchStorageQuotaException if a matching dl storage quota could not be found
	 */
	public DLStorageQuota findByCompanyId(long companyId)
		throws NoSuchStorageQuotaException;

	/**
	 * Returns the dl storage quota where companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @return the matching dl storage quota, or <code>null</code> if a matching dl storage quota could not be found
	 */
	public DLStorageQuota fetchByCompanyId(long companyId);

	/**
	 * Returns the dl storage quota where companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching dl storage quota, or <code>null</code> if a matching dl storage quota could not be found
	 */
	public DLStorageQuota fetchByCompanyId(
		long companyId, boolean useFinderCache);

	/**
	 * Removes the dl storage quota where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @return the dl storage quota that was removed
	 */
	public DLStorageQuota removeByCompanyId(long companyId)
		throws NoSuchStorageQuotaException;

	/**
	 * Returns the number of dl storage quotas where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching dl storage quotas
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Caches the dl storage quota in the entity cache if it is enabled.
	 *
	 * @param dlStorageQuota the dl storage quota
	 */
	public void cacheResult(DLStorageQuota dlStorageQuota);

	/**
	 * Caches the dl storage quotas in the entity cache if it is enabled.
	 *
	 * @param dlStorageQuotas the dl storage quotas
	 */
	public void cacheResult(java.util.List<DLStorageQuota> dlStorageQuotas);

	/**
	 * Creates a new dl storage quota with the primary key. Does not add the dl storage quota to the database.
	 *
	 * @param dlStorageQuotaId the primary key for the new dl storage quota
	 * @return the new dl storage quota
	 */
	public DLStorageQuota create(long dlStorageQuotaId);

	/**
	 * Removes the dl storage quota with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlStorageQuotaId the primary key of the dl storage quota
	 * @return the dl storage quota that was removed
	 * @throws NoSuchStorageQuotaException if a dl storage quota with the primary key could not be found
	 */
	public DLStorageQuota remove(long dlStorageQuotaId)
		throws NoSuchStorageQuotaException;

	public DLStorageQuota updateImpl(DLStorageQuota dlStorageQuota);

	/**
	 * Returns the dl storage quota with the primary key or throws a <code>NoSuchStorageQuotaException</code> if it could not be found.
	 *
	 * @param dlStorageQuotaId the primary key of the dl storage quota
	 * @return the dl storage quota
	 * @throws NoSuchStorageQuotaException if a dl storage quota with the primary key could not be found
	 */
	public DLStorageQuota findByPrimaryKey(long dlStorageQuotaId)
		throws NoSuchStorageQuotaException;

	/**
	 * Returns the dl storage quota with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param dlStorageQuotaId the primary key of the dl storage quota
	 * @return the dl storage quota, or <code>null</code> if a dl storage quota with the primary key could not be found
	 */
	public DLStorageQuota fetchByPrimaryKey(long dlStorageQuotaId);

	/**
	 * Returns all the dl storage quotas.
	 *
	 * @return the dl storage quotas
	 */
	public java.util.List<DLStorageQuota> findAll();

	/**
	 * Returns a range of all the dl storage quotas.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLStorageQuotaModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl storage quotas
	 * @param end the upper bound of the range of dl storage quotas (not inclusive)
	 * @return the range of dl storage quotas
	 */
	public java.util.List<DLStorageQuota> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the dl storage quotas.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLStorageQuotaModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl storage quotas
	 * @param end the upper bound of the range of dl storage quotas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of dl storage quotas
	 */
	public java.util.List<DLStorageQuota> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLStorageQuota>
			orderByComparator);

	/**
	 * Returns an ordered range of all the dl storage quotas.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLStorageQuotaModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl storage quotas
	 * @param end the upper bound of the range of dl storage quotas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of dl storage quotas
	 */
	public java.util.List<DLStorageQuota> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLStorageQuota>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the dl storage quotas from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of dl storage quotas.
	 *
	 * @return the number of dl storage quotas
	 */
	public int countAll();

}