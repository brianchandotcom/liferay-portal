/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.engine.fixed.service.persistence;

import com.liferay.commerce.tax.engine.fixed.exception.NoSuchTaxFixedRateException;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce tax fixed rate service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxFixedRateUtil
 * @generated
 */
@ProviderType
public interface CommerceTaxFixedRatePersistence
	extends BasePersistence<CommerceTaxFixedRate> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceTaxFixedRateUtil} to access the commerce tax fixed rate persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce tax fixed rates where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @return the matching commerce tax fixed rates
	 */
	public java.util.List<CommerceTaxFixedRate> findByCPTaxCategoryId(
		long CPTaxCategoryId);

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
	public java.util.List<CommerceTaxFixedRate> findByCPTaxCategoryId(
		long CPTaxCategoryId, int start, int end);

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
	public java.util.List<CommerceTaxFixedRate> findByCPTaxCategoryId(
		long CPTaxCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxFixedRate>
			orderByComparator);

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
	public java.util.List<CommerceTaxFixedRate> findByCPTaxCategoryId(
		long CPTaxCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxFixedRate>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce tax fixed rate in the ordered set where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax fixed rate
	 * @throws NoSuchTaxFixedRateException if a matching commerce tax fixed rate could not be found
	 */
	public CommerceTaxFixedRate findByCPTaxCategoryId_First(
			long CPTaxCategoryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceTaxFixedRate> orderByComparator)
		throws NoSuchTaxFixedRateException;

	/**
	 * Returns the first commerce tax fixed rate in the ordered set where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax fixed rate, or <code>null</code> if a matching commerce tax fixed rate could not be found
	 */
	public CommerceTaxFixedRate fetchByCPTaxCategoryId_First(
		long CPTaxCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxFixedRate>
			orderByComparator);

	/**
	 * Returns the last commerce tax fixed rate in the ordered set where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax fixed rate
	 * @throws NoSuchTaxFixedRateException if a matching commerce tax fixed rate could not be found
	 */
	public CommerceTaxFixedRate findByCPTaxCategoryId_Last(
			long CPTaxCategoryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceTaxFixedRate> orderByComparator)
		throws NoSuchTaxFixedRateException;

	/**
	 * Returns the last commerce tax fixed rate in the ordered set where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax fixed rate, or <code>null</code> if a matching commerce tax fixed rate could not be found
	 */
	public CommerceTaxFixedRate fetchByCPTaxCategoryId_Last(
		long CPTaxCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxFixedRate>
			orderByComparator);

	/**
	 * Returns the commerce tax fixed rates before and after the current commerce tax fixed rate in the ordered set where CPTaxCategoryId = &#63;.
	 *
	 * @param commerceTaxFixedRateId the primary key of the current commerce tax fixed rate
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tax fixed rate
	 * @throws NoSuchTaxFixedRateException if a commerce tax fixed rate with the primary key could not be found
	 */
	public CommerceTaxFixedRate[] findByCPTaxCategoryId_PrevAndNext(
			long commerceTaxFixedRateId, long CPTaxCategoryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceTaxFixedRate> orderByComparator)
		throws NoSuchTaxFixedRateException;

	/**
	 * Removes all the commerce tax fixed rates where CPTaxCategoryId = &#63; from the database.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 */
	public void removeByCPTaxCategoryId(long CPTaxCategoryId);

	/**
	 * Returns the number of commerce tax fixed rates where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @return the number of matching commerce tax fixed rates
	 */
	public int countByCPTaxCategoryId(long CPTaxCategoryId);

	/**
	 * Returns all the commerce tax fixed rates where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @return the matching commerce tax fixed rates
	 */
	public java.util.List<CommerceTaxFixedRate> findByCommerceTaxMethodId(
		long commerceTaxMethodId);

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
	public java.util.List<CommerceTaxFixedRate> findByCommerceTaxMethodId(
		long commerceTaxMethodId, int start, int end);

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
	public java.util.List<CommerceTaxFixedRate> findByCommerceTaxMethodId(
		long commerceTaxMethodId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxFixedRate>
			orderByComparator);

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
	public java.util.List<CommerceTaxFixedRate> findByCommerceTaxMethodId(
		long commerceTaxMethodId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxFixedRate>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce tax fixed rate in the ordered set where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax fixed rate
	 * @throws NoSuchTaxFixedRateException if a matching commerce tax fixed rate could not be found
	 */
	public CommerceTaxFixedRate findByCommerceTaxMethodId_First(
			long commerceTaxMethodId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceTaxFixedRate> orderByComparator)
		throws NoSuchTaxFixedRateException;

	/**
	 * Returns the first commerce tax fixed rate in the ordered set where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax fixed rate, or <code>null</code> if a matching commerce tax fixed rate could not be found
	 */
	public CommerceTaxFixedRate fetchByCommerceTaxMethodId_First(
		long commerceTaxMethodId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxFixedRate>
			orderByComparator);

	/**
	 * Returns the last commerce tax fixed rate in the ordered set where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax fixed rate
	 * @throws NoSuchTaxFixedRateException if a matching commerce tax fixed rate could not be found
	 */
	public CommerceTaxFixedRate findByCommerceTaxMethodId_Last(
			long commerceTaxMethodId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceTaxFixedRate> orderByComparator)
		throws NoSuchTaxFixedRateException;

	/**
	 * Returns the last commerce tax fixed rate in the ordered set where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax fixed rate, or <code>null</code> if a matching commerce tax fixed rate could not be found
	 */
	public CommerceTaxFixedRate fetchByCommerceTaxMethodId_Last(
		long commerceTaxMethodId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxFixedRate>
			orderByComparator);

	/**
	 * Returns the commerce tax fixed rates before and after the current commerce tax fixed rate in the ordered set where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxFixedRateId the primary key of the current commerce tax fixed rate
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tax fixed rate
	 * @throws NoSuchTaxFixedRateException if a commerce tax fixed rate with the primary key could not be found
	 */
	public CommerceTaxFixedRate[] findByCommerceTaxMethodId_PrevAndNext(
			long commerceTaxFixedRateId, long commerceTaxMethodId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceTaxFixedRate> orderByComparator)
		throws NoSuchTaxFixedRateException;

	/**
	 * Removes all the commerce tax fixed rates where commerceTaxMethodId = &#63; from the database.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 */
	public void removeByCommerceTaxMethodId(long commerceTaxMethodId);

	/**
	 * Returns the number of commerce tax fixed rates where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @return the number of matching commerce tax fixed rates
	 */
	public int countByCommerceTaxMethodId(long commerceTaxMethodId);

	/**
	 * Returns the commerce tax fixed rate where CPTaxCategoryId = &#63; and commerceTaxMethodId = &#63; or throws a <code>NoSuchTaxFixedRateException</code> if it could not be found.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @return the matching commerce tax fixed rate
	 * @throws NoSuchTaxFixedRateException if a matching commerce tax fixed rate could not be found
	 */
	public CommerceTaxFixedRate findByC_C(
			long CPTaxCategoryId, long commerceTaxMethodId)
		throws NoSuchTaxFixedRateException;

	/**
	 * Returns the commerce tax fixed rate where CPTaxCategoryId = &#63; and commerceTaxMethodId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @return the matching commerce tax fixed rate, or <code>null</code> if a matching commerce tax fixed rate could not be found
	 */
	public CommerceTaxFixedRate fetchByC_C(
		long CPTaxCategoryId, long commerceTaxMethodId);

	/**
	 * Returns the commerce tax fixed rate where CPTaxCategoryId = &#63; and commerceTaxMethodId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce tax fixed rate, or <code>null</code> if a matching commerce tax fixed rate could not be found
	 */
	public CommerceTaxFixedRate fetchByC_C(
		long CPTaxCategoryId, long commerceTaxMethodId, boolean useFinderCache);

	/**
	 * Removes the commerce tax fixed rate where CPTaxCategoryId = &#63; and commerceTaxMethodId = &#63; from the database.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @return the commerce tax fixed rate that was removed
	 */
	public CommerceTaxFixedRate removeByC_C(
			long CPTaxCategoryId, long commerceTaxMethodId)
		throws NoSuchTaxFixedRateException;

	/**
	 * Returns the number of commerce tax fixed rates where CPTaxCategoryId = &#63; and commerceTaxMethodId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @return the number of matching commerce tax fixed rates
	 */
	public int countByC_C(long CPTaxCategoryId, long commerceTaxMethodId);

	/**
	 * Caches the commerce tax fixed rate in the entity cache if it is enabled.
	 *
	 * @param commerceTaxFixedRate the commerce tax fixed rate
	 */
	public void cacheResult(CommerceTaxFixedRate commerceTaxFixedRate);

	/**
	 * Caches the commerce tax fixed rates in the entity cache if it is enabled.
	 *
	 * @param commerceTaxFixedRates the commerce tax fixed rates
	 */
	public void cacheResult(
		java.util.List<CommerceTaxFixedRate> commerceTaxFixedRates);

	/**
	 * Creates a new commerce tax fixed rate with the primary key. Does not add the commerce tax fixed rate to the database.
	 *
	 * @param commerceTaxFixedRateId the primary key for the new commerce tax fixed rate
	 * @return the new commerce tax fixed rate
	 */
	public CommerceTaxFixedRate create(long commerceTaxFixedRateId);

	/**
	 * Removes the commerce tax fixed rate with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceTaxFixedRateId the primary key of the commerce tax fixed rate
	 * @return the commerce tax fixed rate that was removed
	 * @throws NoSuchTaxFixedRateException if a commerce tax fixed rate with the primary key could not be found
	 */
	public CommerceTaxFixedRate remove(long commerceTaxFixedRateId)
		throws NoSuchTaxFixedRateException;

	public CommerceTaxFixedRate updateImpl(
		CommerceTaxFixedRate commerceTaxFixedRate);

	/**
	 * Returns the commerce tax fixed rate with the primary key or throws a <code>NoSuchTaxFixedRateException</code> if it could not be found.
	 *
	 * @param commerceTaxFixedRateId the primary key of the commerce tax fixed rate
	 * @return the commerce tax fixed rate
	 * @throws NoSuchTaxFixedRateException if a commerce tax fixed rate with the primary key could not be found
	 */
	public CommerceTaxFixedRate findByPrimaryKey(long commerceTaxFixedRateId)
		throws NoSuchTaxFixedRateException;

	/**
	 * Returns the commerce tax fixed rate with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceTaxFixedRateId the primary key of the commerce tax fixed rate
	 * @return the commerce tax fixed rate, or <code>null</code> if a commerce tax fixed rate with the primary key could not be found
	 */
	public CommerceTaxFixedRate fetchByPrimaryKey(long commerceTaxFixedRateId);

	/**
	 * Returns all the commerce tax fixed rates.
	 *
	 * @return the commerce tax fixed rates
	 */
	public java.util.List<CommerceTaxFixedRate> findAll();

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
	public java.util.List<CommerceTaxFixedRate> findAll(int start, int end);

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
	public java.util.List<CommerceTaxFixedRate> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxFixedRate>
			orderByComparator);

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
	public java.util.List<CommerceTaxFixedRate> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxFixedRate>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce tax fixed rates from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce tax fixed rates.
	 *
	 * @return the number of commerce tax fixed rates
	 */
	public int countAll();

}