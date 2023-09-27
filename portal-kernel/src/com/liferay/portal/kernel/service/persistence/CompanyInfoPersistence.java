/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.exception.NoSuchCompanyInfoException;
import com.liferay.portal.kernel.model.CompanyInfo;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the company info service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CompanyInfoUtil
 * @generated
 */
@ProviderType
public interface CompanyInfoPersistence extends BasePersistence<CompanyInfo> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CompanyInfoUtil} to access the company info persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the company info where companyId = &#63; or throws a <code>NoSuchCompanyInfoException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @return the matching company info
	 * @throws NoSuchCompanyInfoException if a matching company info could not be found
	 */
	public CompanyInfo findByCompanyId(long companyId)
		throws NoSuchCompanyInfoException;

	/**
	 * Returns the company info where companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @return the matching company info, or <code>null</code> if a matching company info could not be found
	 */
	public CompanyInfo fetchByCompanyId(long companyId);

	/**
	 * Returns the company info where companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching company info, or <code>null</code> if a matching company info could not be found
	 */
	public CompanyInfo fetchByCompanyId(long companyId, boolean useFinderCache);

	/**
	 * Removes the company info where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @return the company info that was removed
	 */
	public CompanyInfo removeByCompanyId(long companyId)
		throws NoSuchCompanyInfoException;

	/**
	 * Returns the number of company infos where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching company infos
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Caches the company info in the entity cache if it is enabled.
	 *
	 * @param companyInfo the company info
	 */
	public void cacheResult(CompanyInfo companyInfo);

	/**
	 * Caches the company infos in the entity cache if it is enabled.
	 *
	 * @param companyInfos the company infos
	 */
	public void cacheResult(java.util.List<CompanyInfo> companyInfos);

	/**
	 * Creates a new company info with the primary key. Does not add the company info to the database.
	 *
	 * @param companyInfoId the primary key for the new company info
	 * @return the new company info
	 */
	public CompanyInfo create(long companyInfoId);

	/**
	 * Removes the company info with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param companyInfoId the primary key of the company info
	 * @return the company info that was removed
	 * @throws NoSuchCompanyInfoException if a company info with the primary key could not be found
	 */
	public CompanyInfo remove(long companyInfoId)
		throws NoSuchCompanyInfoException;

	public CompanyInfo updateImpl(CompanyInfo companyInfo);

	/**
	 * Returns the company info with the primary key or throws a <code>NoSuchCompanyInfoException</code> if it could not be found.
	 *
	 * @param companyInfoId the primary key of the company info
	 * @return the company info
	 * @throws NoSuchCompanyInfoException if a company info with the primary key could not be found
	 */
	public CompanyInfo findByPrimaryKey(long companyInfoId)
		throws NoSuchCompanyInfoException;

	/**
	 * Returns the company info with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param companyInfoId the primary key of the company info
	 * @return the company info, or <code>null</code> if a company info with the primary key could not be found
	 */
	public CompanyInfo fetchByPrimaryKey(long companyInfoId);

	/**
	 * Returns all the company infos.
	 *
	 * @return the company infos
	 */
	public java.util.List<CompanyInfo> findAll();

	/**
	 * Returns a range of all the company infos.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CompanyInfoModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of company infos
	 * @param end the upper bound of the range of company infos (not inclusive)
	 * @return the range of company infos
	 */
	public java.util.List<CompanyInfo> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the company infos.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CompanyInfoModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of company infos
	 * @param end the upper bound of the range of company infos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of company infos
	 */
	public java.util.List<CompanyInfo> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CompanyInfo>
			orderByComparator);

	/**
	 * Returns an ordered range of all the company infos.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CompanyInfoModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of company infos
	 * @param end the upper bound of the range of company infos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of company infos
	 */
	public java.util.List<CompanyInfo> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CompanyInfo>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the company infos from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of company infos.
	 *
	 * @return the number of company infos
	 */
	public int countAll();

}