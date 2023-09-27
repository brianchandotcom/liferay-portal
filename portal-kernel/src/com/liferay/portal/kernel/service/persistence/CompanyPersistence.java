/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.exception.NoSuchCompanyException;
import com.liferay.portal.kernel.model.Company;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the company service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CompanyUtil
 * @generated
 */
@ProviderType
public interface CompanyPersistence extends BasePersistence<Company> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CompanyUtil} to access the company persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the company where webId = &#63; or throws a <code>NoSuchCompanyException</code> if it could not be found.
	 *
	 * @param webId the web ID
	 * @return the matching company
	 * @throws NoSuchCompanyException if a matching company could not be found
	 */
	public Company findByWebId(String webId) throws NoSuchCompanyException;

	/**
	 * Returns the company where webId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param webId the web ID
	 * @return the matching company, or <code>null</code> if a matching company could not be found
	 */
	public Company fetchByWebId(String webId);

	/**
	 * Returns the company where webId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param webId the web ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching company, or <code>null</code> if a matching company could not be found
	 */
	public Company fetchByWebId(String webId, boolean useFinderCache);

	/**
	 * Removes the company where webId = &#63; from the database.
	 *
	 * @param webId the web ID
	 * @return the company that was removed
	 */
	public Company removeByWebId(String webId) throws NoSuchCompanyException;

	/**
	 * Returns the number of companies where webId = &#63;.
	 *
	 * @param webId the web ID
	 * @return the number of matching companies
	 */
	public int countByWebId(String webId);

	/**
	 * Returns the company where mx = &#63; or throws a <code>NoSuchCompanyException</code> if it could not be found.
	 *
	 * @param mx the mx
	 * @return the matching company
	 * @throws NoSuchCompanyException if a matching company could not be found
	 */
	public Company findByMx(String mx) throws NoSuchCompanyException;

	/**
	 * Returns the company where mx = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param mx the mx
	 * @return the matching company, or <code>null</code> if a matching company could not be found
	 */
	public Company fetchByMx(String mx);

	/**
	 * Returns the company where mx = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param mx the mx
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching company, or <code>null</code> if a matching company could not be found
	 */
	public Company fetchByMx(String mx, boolean useFinderCache);

	/**
	 * Removes the company where mx = &#63; from the database.
	 *
	 * @param mx the mx
	 * @return the company that was removed
	 */
	public Company removeByMx(String mx) throws NoSuchCompanyException;

	/**
	 * Returns the number of companies where mx = &#63;.
	 *
	 * @param mx the mx
	 * @return the number of matching companies
	 */
	public int countByMx(String mx);

	/**
	 * Returns the company where logoId = &#63; or throws a <code>NoSuchCompanyException</code> if it could not be found.
	 *
	 * @param logoId the logo ID
	 * @return the matching company
	 * @throws NoSuchCompanyException if a matching company could not be found
	 */
	public Company findByLogoId(long logoId) throws NoSuchCompanyException;

	/**
	 * Returns the company where logoId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param logoId the logo ID
	 * @return the matching company, or <code>null</code> if a matching company could not be found
	 */
	public Company fetchByLogoId(long logoId);

	/**
	 * Returns the company where logoId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param logoId the logo ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching company, or <code>null</code> if a matching company could not be found
	 */
	public Company fetchByLogoId(long logoId, boolean useFinderCache);

	/**
	 * Removes the company where logoId = &#63; from the database.
	 *
	 * @param logoId the logo ID
	 * @return the company that was removed
	 */
	public Company removeByLogoId(long logoId) throws NoSuchCompanyException;

	/**
	 * Returns the number of companies where logoId = &#63;.
	 *
	 * @param logoId the logo ID
	 * @return the number of matching companies
	 */
	public int countByLogoId(long logoId);

	/**
	 * Caches the company in the entity cache if it is enabled.
	 *
	 * @param company the company
	 */
	public void cacheResult(Company company);

	/**
	 * Caches the companies in the entity cache if it is enabled.
	 *
	 * @param companies the companies
	 */
	public void cacheResult(java.util.List<Company> companies);

	/**
	 * Creates a new company with the primary key. Does not add the company to the database.
	 *
	 * @param companyId the primary key for the new company
	 * @return the new company
	 */
	public Company create(long companyId);

	/**
	 * Removes the company with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param companyId the primary key of the company
	 * @return the company that was removed
	 * @throws NoSuchCompanyException if a company with the primary key could not be found
	 */
	public Company remove(long companyId) throws NoSuchCompanyException;

	public Company updateImpl(Company company);

	/**
	 * Returns the company with the primary key or throws a <code>NoSuchCompanyException</code> if it could not be found.
	 *
	 * @param companyId the primary key of the company
	 * @return the company
	 * @throws NoSuchCompanyException if a company with the primary key could not be found
	 */
	public Company findByPrimaryKey(long companyId)
		throws NoSuchCompanyException;

	/**
	 * Returns the company with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param companyId the primary key of the company
	 * @return the company, or <code>null</code> if a company with the primary key could not be found
	 */
	public Company fetchByPrimaryKey(long companyId);

	/**
	 * Returns all the companies.
	 *
	 * @return the companies
	 */
	public java.util.List<Company> findAll();

	/**
	 * Returns a range of all the companies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CompanyModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of companies
	 * @param end the upper bound of the range of companies (not inclusive)
	 * @return the range of companies
	 */
	public java.util.List<Company> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the companies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CompanyModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of companies
	 * @param end the upper bound of the range of companies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of companies
	 */
	public java.util.List<Company> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Company>
			orderByComparator);

	/**
	 * Returns an ordered range of all the companies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CompanyModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of companies
	 * @param end the upper bound of the range of companies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of companies
	 */
	public java.util.List<Company> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Company>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the companies from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of companies.
	 *
	 * @return the number of companies
	 */
	public int countAll();

}