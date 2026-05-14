/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	 * Returns the dl storage quota where companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @return the matching dl storage quota, or <code>null</code> if a matching dl storage quota could not be found
	 */
	public default DLStorageQuota fetchByCompanyId(long companyId) {
		return fetchByCompanyId(companyId, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-23439307