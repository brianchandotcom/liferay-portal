/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.service.persistence;

import com.liferay.commerce.currency.exception.NoSuchCurrencyException;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce currency service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CommerceCurrencyUtil
 * @generated
 */
@ProviderType
public interface CommerceCurrencyPersistence
	extends BasePersistence<CommerceCurrency> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceCurrencyUtil} to access the commerce currency persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce currencies where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce currencies
	 */
	public java.util.List<CommerceCurrency> findByUuid(String uuid);

	/**
	 * Returns a range of all the commerce currencies where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @return the range of matching commerce currencies
	 */
	public java.util.List<CommerceCurrency> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the commerce currencies where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce currencies
	 */
	public java.util.List<CommerceCurrency> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce currencies where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce currencies
	 */
	public java.util.List<CommerceCurrency> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce currency in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce currency
	 * @throws NoSuchCurrencyException if a matching commerce currency could not be found
	 */
	public CommerceCurrency findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
				orderByComparator)
		throws NoSuchCurrencyException;

	/**
	 * Returns the first commerce currency in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	 */
	public CommerceCurrency fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
			orderByComparator);

	/**
	 * Returns the last commerce currency in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce currency
	 * @throws NoSuchCurrencyException if a matching commerce currency could not be found
	 */
	public CommerceCurrency findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
				orderByComparator)
		throws NoSuchCurrencyException;

	/**
	 * Returns the last commerce currency in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	 */
	public CommerceCurrency fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
			orderByComparator);

	/**
	 * Returns the commerce currencies before and after the current commerce currency in the ordered set where uuid = &#63;.
	 *
	 * @param commerceCurrencyId the primary key of the current commerce currency
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce currency
	 * @throws NoSuchCurrencyException if a commerce currency with the primary key could not be found
	 */
	public CommerceCurrency[] findByUuid_PrevAndNext(
			long commerceCurrencyId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
				orderByComparator)
		throws NoSuchCurrencyException;

	/**
	 * Removes all the commerce currencies where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of commerce currencies where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce currencies
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the commerce currencies where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce currencies
	 */
	public java.util.List<CommerceCurrency> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the commerce currencies where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @return the range of matching commerce currencies
	 */
	public java.util.List<CommerceCurrency> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce currencies where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce currencies
	 */
	public java.util.List<CommerceCurrency> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce currencies where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce currencies
	 */
	public java.util.List<CommerceCurrency> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce currency in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce currency
	 * @throws NoSuchCurrencyException if a matching commerce currency could not be found
	 */
	public CommerceCurrency findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
				orderByComparator)
		throws NoSuchCurrencyException;

	/**
	 * Returns the first commerce currency in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	 */
	public CommerceCurrency fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
			orderByComparator);

	/**
	 * Returns the last commerce currency in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce currency
	 * @throws NoSuchCurrencyException if a matching commerce currency could not be found
	 */
	public CommerceCurrency findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
				orderByComparator)
		throws NoSuchCurrencyException;

	/**
	 * Returns the last commerce currency in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	 */
	public CommerceCurrency fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
			orderByComparator);

	/**
	 * Returns the commerce currencies before and after the current commerce currency in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceCurrencyId the primary key of the current commerce currency
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce currency
	 * @throws NoSuchCurrencyException if a commerce currency with the primary key could not be found
	 */
	public CommerceCurrency[] findByUuid_C_PrevAndNext(
			long commerceCurrencyId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
				orderByComparator)
		throws NoSuchCurrencyException;

	/**
	 * Removes all the commerce currencies where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce currencies where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce currencies
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the commerce currencies where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce currencies
	 */
	public java.util.List<CommerceCurrency> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the commerce currencies where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @return the range of matching commerce currencies
	 */
	public java.util.List<CommerceCurrency> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce currencies where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce currencies
	 */
	public java.util.List<CommerceCurrency> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce currencies where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce currencies
	 */
	public java.util.List<CommerceCurrency> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce currency in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce currency
	 * @throws NoSuchCurrencyException if a matching commerce currency could not be found
	 */
	public CommerceCurrency findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
				orderByComparator)
		throws NoSuchCurrencyException;

	/**
	 * Returns the first commerce currency in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	 */
	public CommerceCurrency fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
			orderByComparator);

	/**
	 * Returns the last commerce currency in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce currency
	 * @throws NoSuchCurrencyException if a matching commerce currency could not be found
	 */
	public CommerceCurrency findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
				orderByComparator)
		throws NoSuchCurrencyException;

	/**
	 * Returns the last commerce currency in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	 */
	public CommerceCurrency fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
			orderByComparator);

	/**
	 * Returns the commerce currencies before and after the current commerce currency in the ordered set where companyId = &#63;.
	 *
	 * @param commerceCurrencyId the primary key of the current commerce currency
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce currency
	 * @throws NoSuchCurrencyException if a commerce currency with the primary key could not be found
	 */
	public CommerceCurrency[] findByCompanyId_PrevAndNext(
			long commerceCurrencyId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
				orderByComparator)
		throws NoSuchCurrencyException;

	/**
	 * Removes all the commerce currencies where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of commerce currencies where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce currencies
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the commerce currency where companyId = &#63; and code = &#63; or throws a <code>NoSuchCurrencyException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param code the code
	 * @return the matching commerce currency
	 * @throws NoSuchCurrencyException if a matching commerce currency could not be found
	 */
	public CommerceCurrency findByC_C(long companyId, String code)
		throws NoSuchCurrencyException;

	/**
	 * Returns the commerce currency where companyId = &#63; and code = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param code the code
	 * @return the matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	 */
	public CommerceCurrency fetchByC_C(long companyId, String code);

	/**
	 * Returns the commerce currency where companyId = &#63; and code = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param code the code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	 */
	public CommerceCurrency fetchByC_C(
		long companyId, String code, boolean useFinderCache);

	/**
	 * Removes the commerce currency where companyId = &#63; and code = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param code the code
	 * @return the commerce currency that was removed
	 */
	public CommerceCurrency removeByC_C(long companyId, String code)
		throws NoSuchCurrencyException;

	/**
	 * Returns the number of commerce currencies where companyId = &#63; and code = &#63;.
	 *
	 * @param companyId the company ID
	 * @param code the code
	 * @return the number of matching commerce currencies
	 */
	public int countByC_C(long companyId, String code);

	/**
	 * Returns all the commerce currencies where companyId = &#63; and primary = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @return the matching commerce currencies
	 */
	public java.util.List<CommerceCurrency> findByC_P(
		long companyId, boolean primary);

	/**
	 * Returns a range of all the commerce currencies where companyId = &#63; and primary = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @return the range of matching commerce currencies
	 */
	public java.util.List<CommerceCurrency> findByC_P(
		long companyId, boolean primary, int start, int end);

	/**
	 * Returns an ordered range of all the commerce currencies where companyId = &#63; and primary = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce currencies
	 */
	public java.util.List<CommerceCurrency> findByC_P(
		long companyId, boolean primary, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce currencies where companyId = &#63; and primary = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce currencies
	 */
	public java.util.List<CommerceCurrency> findByC_P(
		long companyId, boolean primary, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce currency in the ordered set where companyId = &#63; and primary = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce currency
	 * @throws NoSuchCurrencyException if a matching commerce currency could not be found
	 */
	public CommerceCurrency findByC_P_First(
			long companyId, boolean primary,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
				orderByComparator)
		throws NoSuchCurrencyException;

	/**
	 * Returns the first commerce currency in the ordered set where companyId = &#63; and primary = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	 */
	public CommerceCurrency fetchByC_P_First(
		long companyId, boolean primary,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
			orderByComparator);

	/**
	 * Returns the last commerce currency in the ordered set where companyId = &#63; and primary = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce currency
	 * @throws NoSuchCurrencyException if a matching commerce currency could not be found
	 */
	public CommerceCurrency findByC_P_Last(
			long companyId, boolean primary,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
				orderByComparator)
		throws NoSuchCurrencyException;

	/**
	 * Returns the last commerce currency in the ordered set where companyId = &#63; and primary = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	 */
	public CommerceCurrency fetchByC_P_Last(
		long companyId, boolean primary,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
			orderByComparator);

	/**
	 * Returns the commerce currencies before and after the current commerce currency in the ordered set where companyId = &#63; and primary = &#63;.
	 *
	 * @param commerceCurrencyId the primary key of the current commerce currency
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce currency
	 * @throws NoSuchCurrencyException if a commerce currency with the primary key could not be found
	 */
	public CommerceCurrency[] findByC_P_PrevAndNext(
			long commerceCurrencyId, long companyId, boolean primary,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
				orderByComparator)
		throws NoSuchCurrencyException;

	/**
	 * Removes all the commerce currencies where companyId = &#63; and primary = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 */
	public void removeByC_P(long companyId, boolean primary);

	/**
	 * Returns the number of commerce currencies where companyId = &#63; and primary = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @return the number of matching commerce currencies
	 */
	public int countByC_P(long companyId, boolean primary);

	/**
	 * Returns all the commerce currencies where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching commerce currencies
	 */
	public java.util.List<CommerceCurrency> findByC_A(
		long companyId, boolean active);

	/**
	 * Returns a range of all the commerce currencies where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @return the range of matching commerce currencies
	 */
	public java.util.List<CommerceCurrency> findByC_A(
		long companyId, boolean active, int start, int end);

	/**
	 * Returns an ordered range of all the commerce currencies where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce currencies
	 */
	public java.util.List<CommerceCurrency> findByC_A(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce currencies where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce currencies
	 */
	public java.util.List<CommerceCurrency> findByC_A(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce currency in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce currency
	 * @throws NoSuchCurrencyException if a matching commerce currency could not be found
	 */
	public CommerceCurrency findByC_A_First(
			long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
				orderByComparator)
		throws NoSuchCurrencyException;

	/**
	 * Returns the first commerce currency in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	 */
	public CommerceCurrency fetchByC_A_First(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
			orderByComparator);

	/**
	 * Returns the last commerce currency in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce currency
	 * @throws NoSuchCurrencyException if a matching commerce currency could not be found
	 */
	public CommerceCurrency findByC_A_Last(
			long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
				orderByComparator)
		throws NoSuchCurrencyException;

	/**
	 * Returns the last commerce currency in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	 */
	public CommerceCurrency fetchByC_A_Last(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
			orderByComparator);

	/**
	 * Returns the commerce currencies before and after the current commerce currency in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param commerceCurrencyId the primary key of the current commerce currency
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce currency
	 * @throws NoSuchCurrencyException if a commerce currency with the primary key could not be found
	 */
	public CommerceCurrency[] findByC_A_PrevAndNext(
			long commerceCurrencyId, long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
				orderByComparator)
		throws NoSuchCurrencyException;

	/**
	 * Removes all the commerce currencies where companyId = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 */
	public void removeByC_A(long companyId, boolean active);

	/**
	 * Returns the number of commerce currencies where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching commerce currencies
	 */
	public int countByC_A(long companyId, boolean active);

	/**
	 * Returns all the commerce currencies where companyId = &#63; and primary = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param active the active
	 * @return the matching commerce currencies
	 */
	public java.util.List<CommerceCurrency> findByC_P_A(
		long companyId, boolean primary, boolean active);

	/**
	 * Returns a range of all the commerce currencies where companyId = &#63; and primary = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param active the active
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @return the range of matching commerce currencies
	 */
	public java.util.List<CommerceCurrency> findByC_P_A(
		long companyId, boolean primary, boolean active, int start, int end);

	/**
	 * Returns an ordered range of all the commerce currencies where companyId = &#63; and primary = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param active the active
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce currencies
	 */
	public java.util.List<CommerceCurrency> findByC_P_A(
		long companyId, boolean primary, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce currencies where companyId = &#63; and primary = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param active the active
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce currencies
	 */
	public java.util.List<CommerceCurrency> findByC_P_A(
		long companyId, boolean primary, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce currency in the ordered set where companyId = &#63; and primary = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce currency
	 * @throws NoSuchCurrencyException if a matching commerce currency could not be found
	 */
	public CommerceCurrency findByC_P_A_First(
			long companyId, boolean primary, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
				orderByComparator)
		throws NoSuchCurrencyException;

	/**
	 * Returns the first commerce currency in the ordered set where companyId = &#63; and primary = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	 */
	public CommerceCurrency fetchByC_P_A_First(
		long companyId, boolean primary, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
			orderByComparator);

	/**
	 * Returns the last commerce currency in the ordered set where companyId = &#63; and primary = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce currency
	 * @throws NoSuchCurrencyException if a matching commerce currency could not be found
	 */
	public CommerceCurrency findByC_P_A_Last(
			long companyId, boolean primary, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
				orderByComparator)
		throws NoSuchCurrencyException;

	/**
	 * Returns the last commerce currency in the ordered set where companyId = &#63; and primary = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	 */
	public CommerceCurrency fetchByC_P_A_Last(
		long companyId, boolean primary, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
			orderByComparator);

	/**
	 * Returns the commerce currencies before and after the current commerce currency in the ordered set where companyId = &#63; and primary = &#63; and active = &#63;.
	 *
	 * @param commerceCurrencyId the primary key of the current commerce currency
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce currency
	 * @throws NoSuchCurrencyException if a commerce currency with the primary key could not be found
	 */
	public CommerceCurrency[] findByC_P_A_PrevAndNext(
			long commerceCurrencyId, long companyId, boolean primary,
			boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
				orderByComparator)
		throws NoSuchCurrencyException;

	/**
	 * Removes all the commerce currencies where companyId = &#63; and primary = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param active the active
	 */
	public void removeByC_P_A(long companyId, boolean primary, boolean active);

	/**
	 * Returns the number of commerce currencies where companyId = &#63; and primary = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param active the active
	 * @return the number of matching commerce currencies
	 */
	public int countByC_P_A(long companyId, boolean primary, boolean active);

	/**
	 * Caches the commerce currency in the entity cache if it is enabled.
	 *
	 * @param commerceCurrency the commerce currency
	 */
	public void cacheResult(CommerceCurrency commerceCurrency);

	/**
	 * Caches the commerce currencies in the entity cache if it is enabled.
	 *
	 * @param commerceCurrencies the commerce currencies
	 */
	public void cacheResult(
		java.util.List<CommerceCurrency> commerceCurrencies);

	/**
	 * Creates a new commerce currency with the primary key. Does not add the commerce currency to the database.
	 *
	 * @param commerceCurrencyId the primary key for the new commerce currency
	 * @return the new commerce currency
	 */
	public CommerceCurrency create(long commerceCurrencyId);

	/**
	 * Removes the commerce currency with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceCurrencyId the primary key of the commerce currency
	 * @return the commerce currency that was removed
	 * @throws NoSuchCurrencyException if a commerce currency with the primary key could not be found
	 */
	public CommerceCurrency remove(long commerceCurrencyId)
		throws NoSuchCurrencyException;

	public CommerceCurrency updateImpl(CommerceCurrency commerceCurrency);

	/**
	 * Returns the commerce currency with the primary key or throws a <code>NoSuchCurrencyException</code> if it could not be found.
	 *
	 * @param commerceCurrencyId the primary key of the commerce currency
	 * @return the commerce currency
	 * @throws NoSuchCurrencyException if a commerce currency with the primary key could not be found
	 */
	public CommerceCurrency findByPrimaryKey(long commerceCurrencyId)
		throws NoSuchCurrencyException;

	/**
	 * Returns the commerce currency with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceCurrencyId the primary key of the commerce currency
	 * @return the commerce currency, or <code>null</code> if a commerce currency with the primary key could not be found
	 */
	public CommerceCurrency fetchByPrimaryKey(long commerceCurrencyId);

	/**
	 * Returns all the commerce currencies.
	 *
	 * @return the commerce currencies
	 */
	public java.util.List<CommerceCurrency> findAll();

	/**
	 * Returns a range of all the commerce currencies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @return the range of commerce currencies
	 */
	public java.util.List<CommerceCurrency> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the commerce currencies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce currencies
	 */
	public java.util.List<CommerceCurrency> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce currencies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce currencies
	 */
	public java.util.List<CommerceCurrency> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCurrency>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce currencies from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce currencies.
	 *
	 * @return the number of commerce currencies
	 */
	public int countAll();

}