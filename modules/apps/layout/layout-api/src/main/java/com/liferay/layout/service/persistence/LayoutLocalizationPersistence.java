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

package com.liferay.layout.service.persistence;

import com.liferay.layout.exception.NoSuchLocalizationException;
import com.liferay.layout.model.LayoutLocalization;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the layout localization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutLocalizationUtil
 * @generated
 */
@ProviderType
public interface LayoutLocalizationPersistence
	extends BasePersistence<LayoutLocalization>,
			CTPersistence<LayoutLocalization> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutLocalizationUtil} to access the layout localization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the layout localizations where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout localizations
	 */
	public java.util.List<LayoutLocalization> findByUuid(String uuid);

	/**
	 * Returns a range of all the layout localizations where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @return the range of matching layout localizations
	 */
	public java.util.List<LayoutLocalization> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the layout localizations where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout localizations
	 */
	public java.util.List<LayoutLocalization> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutLocalization>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout localizations where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout localizations
	 */
	public java.util.List<LayoutLocalization> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutLocalization>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first layout localization in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout localization
	 * @throws NoSuchLocalizationException if a matching layout localization could not be found
	 */
	public LayoutLocalization findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutLocalization>
				orderByComparator)
		throws NoSuchLocalizationException;

	/**
	 * Returns the first layout localization in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	public LayoutLocalization fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutLocalization>
			orderByComparator);

	/**
	 * Returns the last layout localization in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout localization
	 * @throws NoSuchLocalizationException if a matching layout localization could not be found
	 */
	public LayoutLocalization findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutLocalization>
				orderByComparator)
		throws NoSuchLocalizationException;

	/**
	 * Returns the last layout localization in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	public LayoutLocalization fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutLocalization>
			orderByComparator);

	/**
	 * Returns the layout localizations before and after the current layout localization in the ordered set where uuid = &#63;.
	 *
	 * @param layoutLocalizationId the primary key of the current layout localization
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout localization
	 * @throws NoSuchLocalizationException if a layout localization with the primary key could not be found
	 */
	public LayoutLocalization[] findByUuid_PrevAndNext(
			long layoutLocalizationId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutLocalization>
				orderByComparator)
		throws NoSuchLocalizationException;

	/**
	 * Removes all the layout localizations where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of layout localizations where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout localizations
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the layout localizations where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout localizations
	 */
	public java.util.List<LayoutLocalization> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the layout localizations where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @return the range of matching layout localizations
	 */
	public java.util.List<LayoutLocalization> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the layout localizations where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout localizations
	 */
	public java.util.List<LayoutLocalization> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutLocalization>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout localizations where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout localizations
	 */
	public java.util.List<LayoutLocalization> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutLocalization>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first layout localization in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout localization
	 * @throws NoSuchLocalizationException if a matching layout localization could not be found
	 */
	public LayoutLocalization findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutLocalization>
				orderByComparator)
		throws NoSuchLocalizationException;

	/**
	 * Returns the first layout localization in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	public LayoutLocalization fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutLocalization>
			orderByComparator);

	/**
	 * Returns the last layout localization in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout localization
	 * @throws NoSuchLocalizationException if a matching layout localization could not be found
	 */
	public LayoutLocalization findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutLocalization>
				orderByComparator)
		throws NoSuchLocalizationException;

	/**
	 * Returns the last layout localization in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	public LayoutLocalization fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutLocalization>
			orderByComparator);

	/**
	 * Returns the layout localizations before and after the current layout localization in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutLocalizationId the primary key of the current layout localization
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout localization
	 * @throws NoSuchLocalizationException if a layout localization with the primary key could not be found
	 */
	public LayoutLocalization[] findByUuid_C_PrevAndNext(
			long layoutLocalizationId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutLocalization>
				orderByComparator)
		throws NoSuchLocalizationException;

	/**
	 * Removes all the layout localizations where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of layout localizations where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout localizations
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the layout localizations where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the matching layout localizations
	 */
	public java.util.List<LayoutLocalization> findByPlid(long plid);

	/**
	 * Returns a range of all the layout localizations where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @return the range of matching layout localizations
	 */
	public java.util.List<LayoutLocalization> findByPlid(
		long plid, int start, int end);

	/**
	 * Returns an ordered range of all the layout localizations where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout localizations
	 */
	public java.util.List<LayoutLocalization> findByPlid(
		long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutLocalization>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout localizations where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout localizations
	 */
	public java.util.List<LayoutLocalization> findByPlid(
		long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutLocalization>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first layout localization in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout localization
	 * @throws NoSuchLocalizationException if a matching layout localization could not be found
	 */
	public LayoutLocalization findByPlid_First(
			long plid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutLocalization>
				orderByComparator)
		throws NoSuchLocalizationException;

	/**
	 * Returns the first layout localization in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	public LayoutLocalization fetchByPlid_First(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutLocalization>
			orderByComparator);

	/**
	 * Returns the last layout localization in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout localization
	 * @throws NoSuchLocalizationException if a matching layout localization could not be found
	 */
	public LayoutLocalization findByPlid_Last(
			long plid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutLocalization>
				orderByComparator)
		throws NoSuchLocalizationException;

	/**
	 * Returns the last layout localization in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	public LayoutLocalization fetchByPlid_Last(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutLocalization>
			orderByComparator);

	/**
	 * Returns the layout localizations before and after the current layout localization in the ordered set where plid = &#63;.
	 *
	 * @param layoutLocalizationId the primary key of the current layout localization
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout localization
	 * @throws NoSuchLocalizationException if a layout localization with the primary key could not be found
	 */
	public LayoutLocalization[] findByPlid_PrevAndNext(
			long layoutLocalizationId, long plid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutLocalization>
				orderByComparator)
		throws NoSuchLocalizationException;

	/**
	 * Removes all the layout localizations where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 */
	public void removeByPlid(long plid);

	/**
	 * Returns the number of layout localizations where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching layout localizations
	 */
	public int countByPlid(long plid);

	/**
	 * Returns the layout localization where languageId = &#63; and plid = &#63; or throws a <code>NoSuchLocalizationException</code> if it could not be found.
	 *
	 * @param languageId the language ID
	 * @param plid the plid
	 * @return the matching layout localization
	 * @throws NoSuchLocalizationException if a matching layout localization could not be found
	 */
	public LayoutLocalization findByL_P(String languageId, long plid)
		throws NoSuchLocalizationException;

	/**
	 * Returns the layout localization where languageId = &#63; and plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param languageId the language ID
	 * @param plid the plid
	 * @return the matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	public LayoutLocalization fetchByL_P(String languageId, long plid);

	/**
	 * Returns the layout localization where languageId = &#63; and plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param languageId the language ID
	 * @param plid the plid
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	public LayoutLocalization fetchByL_P(
		String languageId, long plid, boolean useFinderCache);

	/**
	 * Removes the layout localization where languageId = &#63; and plid = &#63; from the database.
	 *
	 * @param languageId the language ID
	 * @param plid the plid
	 * @return the layout localization that was removed
	 */
	public LayoutLocalization removeByL_P(String languageId, long plid)
		throws NoSuchLocalizationException;

	/**
	 * Returns the number of layout localizations where languageId = &#63; and plid = &#63;.
	 *
	 * @param languageId the language ID
	 * @param plid the plid
	 * @return the number of matching layout localizations
	 */
	public int countByL_P(String languageId, long plid);

	/**
	 * Returns the layout localization where companyId = &#63; and languageId = &#63; and plid = &#63; or throws a <code>NoSuchLocalizationException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param plid the plid
	 * @return the matching layout localization
	 * @throws NoSuchLocalizationException if a matching layout localization could not be found
	 */
	public LayoutLocalization findByC_L_P(
			long companyId, String languageId, long plid)
		throws NoSuchLocalizationException;

	/**
	 * Returns the layout localization where companyId = &#63; and languageId = &#63; and plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param plid the plid
	 * @return the matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	public LayoutLocalization fetchByC_L_P(
		long companyId, String languageId, long plid);

	/**
	 * Returns the layout localization where companyId = &#63; and languageId = &#63; and plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param plid the plid
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	public LayoutLocalization fetchByC_L_P(
		long companyId, String languageId, long plid, boolean useFinderCache);

	/**
	 * Removes the layout localization where companyId = &#63; and languageId = &#63; and plid = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param plid the plid
	 * @return the layout localization that was removed
	 */
	public LayoutLocalization removeByC_L_P(
			long companyId, String languageId, long plid)
		throws NoSuchLocalizationException;

	/**
	 * Returns the number of layout localizations where companyId = &#63; and languageId = &#63; and plid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param plid the plid
	 * @return the number of matching layout localizations
	 */
	public int countByC_L_P(long companyId, String languageId, long plid);

	/**
	 * Caches the layout localization in the entity cache if it is enabled.
	 *
	 * @param layoutLocalization the layout localization
	 */
	public void cacheResult(LayoutLocalization layoutLocalization);

	/**
	 * Caches the layout localizations in the entity cache if it is enabled.
	 *
	 * @param layoutLocalizations the layout localizations
	 */
	public void cacheResult(
		java.util.List<LayoutLocalization> layoutLocalizations);

	/**
	 * Creates a new layout localization with the primary key. Does not add the layout localization to the database.
	 *
	 * @param layoutLocalizationId the primary key for the new layout localization
	 * @return the new layout localization
	 */
	public LayoutLocalization create(long layoutLocalizationId);

	/**
	 * Removes the layout localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutLocalizationId the primary key of the layout localization
	 * @return the layout localization that was removed
	 * @throws NoSuchLocalizationException if a layout localization with the primary key could not be found
	 */
	public LayoutLocalization remove(long layoutLocalizationId)
		throws NoSuchLocalizationException;

	public LayoutLocalization updateImpl(LayoutLocalization layoutLocalization);

	/**
	 * Returns the layout localization with the primary key or throws a <code>NoSuchLocalizationException</code> if it could not be found.
	 *
	 * @param layoutLocalizationId the primary key of the layout localization
	 * @return the layout localization
	 * @throws NoSuchLocalizationException if a layout localization with the primary key could not be found
	 */
	public LayoutLocalization findByPrimaryKey(long layoutLocalizationId)
		throws NoSuchLocalizationException;

	/**
	 * Returns the layout localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutLocalizationId the primary key of the layout localization
	 * @return the layout localization, or <code>null</code> if a layout localization with the primary key could not be found
	 */
	public LayoutLocalization fetchByPrimaryKey(long layoutLocalizationId);

	/**
	 * Returns all the layout localizations.
	 *
	 * @return the layout localizations
	 */
	public java.util.List<LayoutLocalization> findAll();

	/**
	 * Returns a range of all the layout localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @return the range of layout localizations
	 */
	public java.util.List<LayoutLocalization> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the layout localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout localizations
	 */
	public java.util.List<LayoutLocalization> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutLocalization>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of layout localizations
	 */
	public java.util.List<LayoutLocalization> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutLocalization>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the layout localizations from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of layout localizations.
	 *
	 * @return the number of layout localizations
	 */
	public int countAll();

}