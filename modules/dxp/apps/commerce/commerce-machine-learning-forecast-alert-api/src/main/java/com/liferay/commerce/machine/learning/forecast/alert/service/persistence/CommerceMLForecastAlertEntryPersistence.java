/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.forecast.alert.service.persistence;

import com.liferay.commerce.machine.learning.forecast.alert.exception.NoSuchMLForecastAlertEntryException;
import com.liferay.commerce.machine.learning.forecast.alert.model.CommerceMLForecastAlertEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce ml forecast alert entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Riccardo Ferrari
 * @see CommerceMLForecastAlertEntryUtil
 * @generated
 */
@ProviderType
public interface CommerceMLForecastAlertEntryPersistence
	extends BasePersistence<CommerceMLForecastAlertEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceMLForecastAlertEntryUtil} to access the commerce ml forecast alert entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce ml forecast alert entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the commerce ml forecast alert entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @return the range of matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceMLForecastAlertEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceMLForecastAlertEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce ml forecast alert entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a matching commerce ml forecast alert entry could not be found
	 */
	public CommerceMLForecastAlertEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException;

	/**
	 * Returns the first commerce ml forecast alert entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	public CommerceMLForecastAlertEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceMLForecastAlertEntry> orderByComparator);

	/**
	 * Returns the last commerce ml forecast alert entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a matching commerce ml forecast alert entry could not be found
	 */
	public CommerceMLForecastAlertEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException;

	/**
	 * Returns the last commerce ml forecast alert entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	public CommerceMLForecastAlertEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceMLForecastAlertEntry> orderByComparator);

	/**
	 * Returns the commerce ml forecast alert entries before and after the current commerce ml forecast alert entry in the ordered set where uuid = &#63;.
	 *
	 * @param commerceMLForecastAlertEntryId the primary key of the current commerce ml forecast alert entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a commerce ml forecast alert entry with the primary key could not be found
	 */
	public CommerceMLForecastAlertEntry[] findByUuid_PrevAndNext(
			long commerceMLForecastAlertEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException;

	/**
	 * Removes all the commerce ml forecast alert entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of commerce ml forecast alert entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce ml forecast alert entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the commerce ml forecast alert entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the commerce ml forecast alert entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @return the range of matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceMLForecastAlertEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceMLForecastAlertEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce ml forecast alert entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a matching commerce ml forecast alert entry could not be found
	 */
	public CommerceMLForecastAlertEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException;

	/**
	 * Returns the first commerce ml forecast alert entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	public CommerceMLForecastAlertEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceMLForecastAlertEntry> orderByComparator);

	/**
	 * Returns the last commerce ml forecast alert entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a matching commerce ml forecast alert entry could not be found
	 */
	public CommerceMLForecastAlertEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException;

	/**
	 * Returns the last commerce ml forecast alert entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	public CommerceMLForecastAlertEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceMLForecastAlertEntry> orderByComparator);

	/**
	 * Returns the commerce ml forecast alert entries before and after the current commerce ml forecast alert entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceMLForecastAlertEntryId the primary key of the current commerce ml forecast alert entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a commerce ml forecast alert entry with the primary key could not be found
	 */
	public CommerceMLForecastAlertEntry[] findByUuid_C_PrevAndNext(
			long commerceMLForecastAlertEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException;

	/**
	 * Removes all the commerce ml forecast alert entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce ml forecast alert entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce ml forecast alert entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the commerce ml forecast alert entry where companyId = &#63; and commerceAccountId = &#63; and timestamp = &#63; or throws a <code>NoSuchMLForecastAlertEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param timestamp the timestamp
	 * @return the matching commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a matching commerce ml forecast alert entry could not be found
	 */
	public CommerceMLForecastAlertEntry findByC_C_T(
			long companyId, long commerceAccountId, Date timestamp)
		throws NoSuchMLForecastAlertEntryException;

	/**
	 * Returns the commerce ml forecast alert entry where companyId = &#63; and commerceAccountId = &#63; and timestamp = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param timestamp the timestamp
	 * @return the matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	public CommerceMLForecastAlertEntry fetchByC_C_T(
		long companyId, long commerceAccountId, Date timestamp);

	/**
	 * Returns the commerce ml forecast alert entry where companyId = &#63; and commerceAccountId = &#63; and timestamp = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param timestamp the timestamp
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	public CommerceMLForecastAlertEntry fetchByC_C_T(
		long companyId, long commerceAccountId, Date timestamp,
		boolean useFinderCache);

	/**
	 * Removes the commerce ml forecast alert entry where companyId = &#63; and commerceAccountId = &#63; and timestamp = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param timestamp the timestamp
	 * @return the commerce ml forecast alert entry that was removed
	 */
	public CommerceMLForecastAlertEntry removeByC_C_T(
			long companyId, long commerceAccountId, Date timestamp)
		throws NoSuchMLForecastAlertEntryException;

	/**
	 * Returns the number of commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and timestamp = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param timestamp the timestamp
	 * @return the number of matching commerce ml forecast alert entries
	 */
	public int countByC_C_T(
		long companyId, long commerceAccountId, Date timestamp);

	/**
	 * Returns all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 * @return the matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByC_C_S(
		long companyId, long commerceAccountId, int status);

	/**
	 * Returns a range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @return the range of matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByC_C_S(
		long companyId, long commerceAccountId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByC_C_S(
		long companyId, long commerceAccountId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceMLForecastAlertEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByC_C_S(
		long companyId, long commerceAccountId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceMLForecastAlertEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a matching commerce ml forecast alert entry could not be found
	 */
	public CommerceMLForecastAlertEntry findByC_C_S_First(
			long companyId, long commerceAccountId, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException;

	/**
	 * Returns the first commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	public CommerceMLForecastAlertEntry fetchByC_C_S_First(
		long companyId, long commerceAccountId, int status,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceMLForecastAlertEntry> orderByComparator);

	/**
	 * Returns the last commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a matching commerce ml forecast alert entry could not be found
	 */
	public CommerceMLForecastAlertEntry findByC_C_S_Last(
			long companyId, long commerceAccountId, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException;

	/**
	 * Returns the last commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	public CommerceMLForecastAlertEntry fetchByC_C_S_Last(
		long companyId, long commerceAccountId, int status,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceMLForecastAlertEntry> orderByComparator);

	/**
	 * Returns the commerce ml forecast alert entries before and after the current commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and status = &#63;.
	 *
	 * @param commerceMLForecastAlertEntryId the primary key of the current commerce ml forecast alert entry
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a commerce ml forecast alert entry with the primary key could not be found
	 */
	public CommerceMLForecastAlertEntry[] findByC_C_S_PrevAndNext(
			long commerceMLForecastAlertEntryId, long companyId,
			long commerceAccountId, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException;

	/**
	 * Returns all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param status the status
	 * @return the matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByC_C_S(
		long companyId, long[] commerceAccountIds, int status);

	/**
	 * Returns a range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @return the range of matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByC_C_S(
		long companyId, long[] commerceAccountIds, int status, int start,
		int end);

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByC_C_S(
		long companyId, long[] commerceAccountIds, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceMLForecastAlertEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByC_C_S(
		long companyId, long[] commerceAccountIds, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceMLForecastAlertEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 */
	public void removeByC_C_S(
		long companyId, long commerceAccountId, int status);

	/**
	 * Returns the number of commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 * @return the number of matching commerce ml forecast alert entries
	 */
	public int countByC_C_S(long companyId, long commerceAccountId, int status);

	/**
	 * Returns the number of commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param status the status
	 * @return the number of matching commerce ml forecast alert entries
	 */
	public int countByC_C_S(
		long companyId, long[] commerceAccountIds, int status);

	/**
	 * Returns all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @return the matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByC_C_GtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status);

	/**
	 * Returns a range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @return the range of matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByC_C_GtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByC_C_GtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceMLForecastAlertEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByC_C_GtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceMLForecastAlertEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a matching commerce ml forecast alert entry could not be found
	 */
	public CommerceMLForecastAlertEntry findByC_C_GtRc_S_First(
			long companyId, long commerceAccountId, double relativeChange,
			int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException;

	/**
	 * Returns the first commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	public CommerceMLForecastAlertEntry fetchByC_C_GtRc_S_First(
		long companyId, long commerceAccountId, double relativeChange,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceMLForecastAlertEntry> orderByComparator);

	/**
	 * Returns the last commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a matching commerce ml forecast alert entry could not be found
	 */
	public CommerceMLForecastAlertEntry findByC_C_GtRc_S_Last(
			long companyId, long commerceAccountId, double relativeChange,
			int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException;

	/**
	 * Returns the last commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	public CommerceMLForecastAlertEntry fetchByC_C_GtRc_S_Last(
		long companyId, long commerceAccountId, double relativeChange,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceMLForecastAlertEntry> orderByComparator);

	/**
	 * Returns the commerce ml forecast alert entries before and after the current commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * @param commerceMLForecastAlertEntryId the primary key of the current commerce ml forecast alert entry
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a commerce ml forecast alert entry with the primary key could not be found
	 */
	public CommerceMLForecastAlertEntry[] findByC_C_GtRc_S_PrevAndNext(
			long commerceMLForecastAlertEntryId, long companyId,
			long commerceAccountId, double relativeChange, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException;

	/**
	 * Returns all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param relativeChange the relative change
	 * @param status the status
	 * @return the matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByC_C_GtRc_S(
		long companyId, long[] commerceAccountIds, double relativeChange,
		int status);

	/**
	 * Returns a range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @return the range of matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByC_C_GtRc_S(
		long companyId, long[] commerceAccountIds, double relativeChange,
		int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByC_C_GtRc_S(
		long companyId, long[] commerceAccountIds, double relativeChange,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceMLForecastAlertEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByC_C_GtRc_S(
		long companyId, long[] commerceAccountIds, double relativeChange,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceMLForecastAlertEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 */
	public void removeByC_C_GtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status);

	/**
	 * Returns the number of commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @return the number of matching commerce ml forecast alert entries
	 */
	public int countByC_C_GtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status);

	/**
	 * Returns the number of commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param relativeChange the relative change
	 * @param status the status
	 * @return the number of matching commerce ml forecast alert entries
	 */
	public int countByC_C_GtRc_S(
		long companyId, long[] commerceAccountIds, double relativeChange,
		int status);

	/**
	 * Returns all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @return the matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByC_C_LtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status);

	/**
	 * Returns a range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @return the range of matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByC_C_LtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByC_C_LtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceMLForecastAlertEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByC_C_LtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceMLForecastAlertEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a matching commerce ml forecast alert entry could not be found
	 */
	public CommerceMLForecastAlertEntry findByC_C_LtRc_S_First(
			long companyId, long commerceAccountId, double relativeChange,
			int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException;

	/**
	 * Returns the first commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	public CommerceMLForecastAlertEntry fetchByC_C_LtRc_S_First(
		long companyId, long commerceAccountId, double relativeChange,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceMLForecastAlertEntry> orderByComparator);

	/**
	 * Returns the last commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a matching commerce ml forecast alert entry could not be found
	 */
	public CommerceMLForecastAlertEntry findByC_C_LtRc_S_Last(
			long companyId, long commerceAccountId, double relativeChange,
			int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException;

	/**
	 * Returns the last commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	public CommerceMLForecastAlertEntry fetchByC_C_LtRc_S_Last(
		long companyId, long commerceAccountId, double relativeChange,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceMLForecastAlertEntry> orderByComparator);

	/**
	 * Returns the commerce ml forecast alert entries before and after the current commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * @param commerceMLForecastAlertEntryId the primary key of the current commerce ml forecast alert entry
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a commerce ml forecast alert entry with the primary key could not be found
	 */
	public CommerceMLForecastAlertEntry[] findByC_C_LtRc_S_PrevAndNext(
			long commerceMLForecastAlertEntryId, long companyId,
			long commerceAccountId, double relativeChange, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException;

	/**
	 * Returns all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param relativeChange the relative change
	 * @param status the status
	 * @return the matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByC_C_LtRc_S(
		long companyId, long[] commerceAccountIds, double relativeChange,
		int status);

	/**
	 * Returns a range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @return the range of matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByC_C_LtRc_S(
		long companyId, long[] commerceAccountIds, double relativeChange,
		int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByC_C_LtRc_S(
		long companyId, long[] commerceAccountIds, double relativeChange,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceMLForecastAlertEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findByC_C_LtRc_S(
		long companyId, long[] commerceAccountIds, double relativeChange,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceMLForecastAlertEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 */
	public void removeByC_C_LtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status);

	/**
	 * Returns the number of commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @return the number of matching commerce ml forecast alert entries
	 */
	public int countByC_C_LtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status);

	/**
	 * Returns the number of commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param relativeChange the relative change
	 * @param status the status
	 * @return the number of matching commerce ml forecast alert entries
	 */
	public int countByC_C_LtRc_S(
		long companyId, long[] commerceAccountIds, double relativeChange,
		int status);

	/**
	 * Caches the commerce ml forecast alert entry in the entity cache if it is enabled.
	 *
	 * @param commerceMLForecastAlertEntry the commerce ml forecast alert entry
	 */
	public void cacheResult(
		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry);

	/**
	 * Caches the commerce ml forecast alert entries in the entity cache if it is enabled.
	 *
	 * @param commerceMLForecastAlertEntries the commerce ml forecast alert entries
	 */
	public void cacheResult(
		java.util.List<CommerceMLForecastAlertEntry>
			commerceMLForecastAlertEntries);

	/**
	 * Creates a new commerce ml forecast alert entry with the primary key. Does not add the commerce ml forecast alert entry to the database.
	 *
	 * @param commerceMLForecastAlertEntryId the primary key for the new commerce ml forecast alert entry
	 * @return the new commerce ml forecast alert entry
	 */
	public CommerceMLForecastAlertEntry create(
		long commerceMLForecastAlertEntryId);

	/**
	 * Removes the commerce ml forecast alert entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceMLForecastAlertEntryId the primary key of the commerce ml forecast alert entry
	 * @return the commerce ml forecast alert entry that was removed
	 * @throws NoSuchMLForecastAlertEntryException if a commerce ml forecast alert entry with the primary key could not be found
	 */
	public CommerceMLForecastAlertEntry remove(
			long commerceMLForecastAlertEntryId)
		throws NoSuchMLForecastAlertEntryException;

	public CommerceMLForecastAlertEntry updateImpl(
		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry);

	/**
	 * Returns the commerce ml forecast alert entry with the primary key or throws a <code>NoSuchMLForecastAlertEntryException</code> if it could not be found.
	 *
	 * @param commerceMLForecastAlertEntryId the primary key of the commerce ml forecast alert entry
	 * @return the commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a commerce ml forecast alert entry with the primary key could not be found
	 */
	public CommerceMLForecastAlertEntry findByPrimaryKey(
			long commerceMLForecastAlertEntryId)
		throws NoSuchMLForecastAlertEntryException;

	/**
	 * Returns the commerce ml forecast alert entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceMLForecastAlertEntryId the primary key of the commerce ml forecast alert entry
	 * @return the commerce ml forecast alert entry, or <code>null</code> if a commerce ml forecast alert entry with the primary key could not be found
	 */
	public CommerceMLForecastAlertEntry fetchByPrimaryKey(
		long commerceMLForecastAlertEntryId);

	/**
	 * Returns all the commerce ml forecast alert entries.
	 *
	 * @return the commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findAll();

	/**
	 * Returns a range of all the commerce ml forecast alert entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @return the range of commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceMLForecastAlertEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce ml forecast alert entries
	 */
	public java.util.List<CommerceMLForecastAlertEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceMLForecastAlertEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce ml forecast alert entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce ml forecast alert entries.
	 *
	 * @return the number of commerce ml forecast alert entries
	 */
	public int countAll();

}