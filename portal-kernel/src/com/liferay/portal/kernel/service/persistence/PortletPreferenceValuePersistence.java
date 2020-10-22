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

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.exception.NoSuchPortletPreferenceValueException;
import com.liferay.portal.kernel.model.PortletPreferenceValue;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the portlet preference value service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortletPreferenceValueUtil
 * @generated
 */
@ProviderType
public interface PortletPreferenceValuePersistence
	extends BasePersistence<PortletPreferenceValue>,
			CTPersistence<PortletPreferenceValue> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PortletPreferenceValueUtil} to access the portlet preference value persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the portlet preference values where portletPreferencesId = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @return the matching portlet preference values
	 */
	public java.util.List<PortletPreferenceValue> findByPortletPreferencesId(
		long portletPreferencesId);

	/**
	 * Returns a range of all the portlet preference values where portletPreferencesId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @return the range of matching portlet preference values
	 */
	public java.util.List<PortletPreferenceValue> findByPortletPreferencesId(
		long portletPreferencesId, int start, int end);

	/**
	 * Returns an ordered range of all the portlet preference values where portletPreferencesId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet preference values
	 */
	public java.util.List<PortletPreferenceValue> findByPortletPreferencesId(
		long portletPreferencesId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferenceValue>
			orderByComparator);

	/**
	 * Returns an ordered range of all the portlet preference values where portletPreferencesId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching portlet preference values
	 */
	public java.util.List<PortletPreferenceValue> findByPortletPreferencesId(
		long portletPreferencesId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferenceValue>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first portlet preference value in the ordered set where portletPreferencesId = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a matching portlet preference value could not be found
	 */
	public PortletPreferenceValue findByPortletPreferencesId_First(
			long portletPreferencesId,
			com.liferay.portal.kernel.util.OrderByComparator
				<PortletPreferenceValue> orderByComparator)
		throws NoSuchPortletPreferenceValueException;

	/**
	 * Returns the first portlet preference value in the ordered set where portletPreferencesId = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preference value, or <code>null</code> if a matching portlet preference value could not be found
	 */
	public PortletPreferenceValue fetchByPortletPreferencesId_First(
		long portletPreferencesId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferenceValue>
			orderByComparator);

	/**
	 * Returns the last portlet preference value in the ordered set where portletPreferencesId = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a matching portlet preference value could not be found
	 */
	public PortletPreferenceValue findByPortletPreferencesId_Last(
			long portletPreferencesId,
			com.liferay.portal.kernel.util.OrderByComparator
				<PortletPreferenceValue> orderByComparator)
		throws NoSuchPortletPreferenceValueException;

	/**
	 * Returns the last portlet preference value in the ordered set where portletPreferencesId = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preference value, or <code>null</code> if a matching portlet preference value could not be found
	 */
	public PortletPreferenceValue fetchByPortletPreferencesId_Last(
		long portletPreferencesId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferenceValue>
			orderByComparator);

	/**
	 * Returns the portlet preference values before and after the current portlet preference value in the ordered set where portletPreferencesId = &#63;.
	 *
	 * @param portletPreferenceValueId the primary key of the current portlet preference value
	 * @param portletPreferencesId the portlet preferences ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a portlet preference value with the primary key could not be found
	 */
	public PortletPreferenceValue[] findByPortletPreferencesId_PrevAndNext(
			long portletPreferenceValueId, long portletPreferencesId,
			com.liferay.portal.kernel.util.OrderByComparator
				<PortletPreferenceValue> orderByComparator)
		throws NoSuchPortletPreferenceValueException;

	/**
	 * Removes all the portlet preference values where portletPreferencesId = &#63; from the database.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 */
	public void removeByPortletPreferencesId(long portletPreferencesId);

	/**
	 * Returns the number of portlet preference values where portletPreferencesId = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @return the number of matching portlet preference values
	 */
	public int countByPortletPreferencesId(long portletPreferencesId);

	/**
	 * Returns all the portlet preference values where portletPreferencesId = &#63; and name = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @return the matching portlet preference values
	 */
	public java.util.List<PortletPreferenceValue> findByP_N(
		long portletPreferencesId, String name);

	/**
	 * Returns a range of all the portlet preference values where portletPreferencesId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @return the range of matching portlet preference values
	 */
	public java.util.List<PortletPreferenceValue> findByP_N(
		long portletPreferencesId, String name, int start, int end);

	/**
	 * Returns an ordered range of all the portlet preference values where portletPreferencesId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet preference values
	 */
	public java.util.List<PortletPreferenceValue> findByP_N(
		long portletPreferencesId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferenceValue>
			orderByComparator);

	/**
	 * Returns an ordered range of all the portlet preference values where portletPreferencesId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching portlet preference values
	 */
	public java.util.List<PortletPreferenceValue> findByP_N(
		long portletPreferencesId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferenceValue>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first portlet preference value in the ordered set where portletPreferencesId = &#63; and name = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a matching portlet preference value could not be found
	 */
	public PortletPreferenceValue findByP_N_First(
			long portletPreferencesId, String name,
			com.liferay.portal.kernel.util.OrderByComparator
				<PortletPreferenceValue> orderByComparator)
		throws NoSuchPortletPreferenceValueException;

	/**
	 * Returns the first portlet preference value in the ordered set where portletPreferencesId = &#63; and name = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preference value, or <code>null</code> if a matching portlet preference value could not be found
	 */
	public PortletPreferenceValue fetchByP_N_First(
		long portletPreferencesId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferenceValue>
			orderByComparator);

	/**
	 * Returns the last portlet preference value in the ordered set where portletPreferencesId = &#63; and name = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a matching portlet preference value could not be found
	 */
	public PortletPreferenceValue findByP_N_Last(
			long portletPreferencesId, String name,
			com.liferay.portal.kernel.util.OrderByComparator
				<PortletPreferenceValue> orderByComparator)
		throws NoSuchPortletPreferenceValueException;

	/**
	 * Returns the last portlet preference value in the ordered set where portletPreferencesId = &#63; and name = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preference value, or <code>null</code> if a matching portlet preference value could not be found
	 */
	public PortletPreferenceValue fetchByP_N_Last(
		long portletPreferencesId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferenceValue>
			orderByComparator);

	/**
	 * Returns the portlet preference values before and after the current portlet preference value in the ordered set where portletPreferencesId = &#63; and name = &#63;.
	 *
	 * @param portletPreferenceValueId the primary key of the current portlet preference value
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a portlet preference value with the primary key could not be found
	 */
	public PortletPreferenceValue[] findByP_N_PrevAndNext(
			long portletPreferenceValueId, long portletPreferencesId,
			String name,
			com.liferay.portal.kernel.util.OrderByComparator
				<PortletPreferenceValue> orderByComparator)
		throws NoSuchPortletPreferenceValueException;

	/**
	 * Removes all the portlet preference values where portletPreferencesId = &#63; and name = &#63; from the database.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 */
	public void removeByP_N(long portletPreferencesId, String name);

	/**
	 * Returns the number of portlet preference values where portletPreferencesId = &#63; and name = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @return the number of matching portlet preference values
	 */
	public int countByP_N(long portletPreferencesId, String name);

	/**
	 * Returns the portlet preference value where portletPreferencesId = &#63; and name = &#63; and index = &#63; or throws a <code>NoSuchPortletPreferenceValueException</code> if it could not be found.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param index the index
	 * @return the matching portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a matching portlet preference value could not be found
	 */
	public PortletPreferenceValue findByP_N_I(
			long portletPreferencesId, String name, int index)
		throws NoSuchPortletPreferenceValueException;

	/**
	 * Returns the portlet preference value where portletPreferencesId = &#63; and name = &#63; and index = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param index the index
	 * @return the matching portlet preference value, or <code>null</code> if a matching portlet preference value could not be found
	 */
	public PortletPreferenceValue fetchByP_N_I(
		long portletPreferencesId, String name, int index);

	/**
	 * Returns the portlet preference value where portletPreferencesId = &#63; and name = &#63; and index = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param index the index
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching portlet preference value, or <code>null</code> if a matching portlet preference value could not be found
	 */
	public PortletPreferenceValue fetchByP_N_I(
		long portletPreferencesId, String name, int index,
		boolean useFinderCache);

	/**
	 * Removes the portlet preference value where portletPreferencesId = &#63; and name = &#63; and index = &#63; from the database.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param index the index
	 * @return the portlet preference value that was removed
	 */
	public PortletPreferenceValue removeByP_N_I(
			long portletPreferencesId, String name, int index)
		throws NoSuchPortletPreferenceValueException;

	/**
	 * Returns the number of portlet preference values where portletPreferencesId = &#63; and name = &#63; and index = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param index the index
	 * @return the number of matching portlet preference values
	 */
	public int countByP_N_I(long portletPreferencesId, String name, int index);

	/**
	 * Returns all the portlet preference values where portletPreferencesId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param smallValue the small value
	 * @return the matching portlet preference values
	 */
	public java.util.List<PortletPreferenceValue> findByP_N_SV(
		long portletPreferencesId, String name, String smallValue);

	/**
	 * Returns a range of all the portlet preference values where portletPreferencesId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @return the range of matching portlet preference values
	 */
	public java.util.List<PortletPreferenceValue> findByP_N_SV(
		long portletPreferencesId, String name, String smallValue, int start,
		int end);

	/**
	 * Returns an ordered range of all the portlet preference values where portletPreferencesId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet preference values
	 */
	public java.util.List<PortletPreferenceValue> findByP_N_SV(
		long portletPreferencesId, String name, String smallValue, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferenceValue>
			orderByComparator);

	/**
	 * Returns an ordered range of all the portlet preference values where portletPreferencesId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching portlet preference values
	 */
	public java.util.List<PortletPreferenceValue> findByP_N_SV(
		long portletPreferencesId, String name, String smallValue, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferenceValue>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first portlet preference value in the ordered set where portletPreferencesId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a matching portlet preference value could not be found
	 */
	public PortletPreferenceValue findByP_N_SV_First(
			long portletPreferencesId, String name, String smallValue,
			com.liferay.portal.kernel.util.OrderByComparator
				<PortletPreferenceValue> orderByComparator)
		throws NoSuchPortletPreferenceValueException;

	/**
	 * Returns the first portlet preference value in the ordered set where portletPreferencesId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preference value, or <code>null</code> if a matching portlet preference value could not be found
	 */
	public PortletPreferenceValue fetchByP_N_SV_First(
		long portletPreferencesId, String name, String smallValue,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferenceValue>
			orderByComparator);

	/**
	 * Returns the last portlet preference value in the ordered set where portletPreferencesId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a matching portlet preference value could not be found
	 */
	public PortletPreferenceValue findByP_N_SV_Last(
			long portletPreferencesId, String name, String smallValue,
			com.liferay.portal.kernel.util.OrderByComparator
				<PortletPreferenceValue> orderByComparator)
		throws NoSuchPortletPreferenceValueException;

	/**
	 * Returns the last portlet preference value in the ordered set where portletPreferencesId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preference value, or <code>null</code> if a matching portlet preference value could not be found
	 */
	public PortletPreferenceValue fetchByP_N_SV_Last(
		long portletPreferencesId, String name, String smallValue,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferenceValue>
			orderByComparator);

	/**
	 * Returns the portlet preference values before and after the current portlet preference value in the ordered set where portletPreferencesId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * @param portletPreferenceValueId the primary key of the current portlet preference value
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a portlet preference value with the primary key could not be found
	 */
	public PortletPreferenceValue[] findByP_N_SV_PrevAndNext(
			long portletPreferenceValueId, long portletPreferencesId,
			String name, String smallValue,
			com.liferay.portal.kernel.util.OrderByComparator
				<PortletPreferenceValue> orderByComparator)
		throws NoSuchPortletPreferenceValueException;

	/**
	 * Removes all the portlet preference values where portletPreferencesId = &#63; and name = &#63; and smallValue = &#63; from the database.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param smallValue the small value
	 */
	public void removeByP_N_SV(
		long portletPreferencesId, String name, String smallValue);

	/**
	 * Returns the number of portlet preference values where portletPreferencesId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param smallValue the small value
	 * @return the number of matching portlet preference values
	 */
	public int countByP_N_SV(
		long portletPreferencesId, String name, String smallValue);

	/**
	 * Caches the portlet preference value in the entity cache if it is enabled.
	 *
	 * @param portletPreferenceValue the portlet preference value
	 */
	public void cacheResult(PortletPreferenceValue portletPreferenceValue);

	/**
	 * Caches the portlet preference values in the entity cache if it is enabled.
	 *
	 * @param portletPreferenceValues the portlet preference values
	 */
	public void cacheResult(
		java.util.List<PortletPreferenceValue> portletPreferenceValues);

	/**
	 * Creates a new portlet preference value with the primary key. Does not add the portlet preference value to the database.
	 *
	 * @param portletPreferenceValueId the primary key for the new portlet preference value
	 * @return the new portlet preference value
	 */
	public PortletPreferenceValue create(long portletPreferenceValueId);

	/**
	 * Removes the portlet preference value with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param portletPreferenceValueId the primary key of the portlet preference value
	 * @return the portlet preference value that was removed
	 * @throws NoSuchPortletPreferenceValueException if a portlet preference value with the primary key could not be found
	 */
	public PortletPreferenceValue remove(long portletPreferenceValueId)
		throws NoSuchPortletPreferenceValueException;

	public PortletPreferenceValue updateImpl(
		PortletPreferenceValue portletPreferenceValue);

	/**
	 * Returns the portlet preference value with the primary key or throws a <code>NoSuchPortletPreferenceValueException</code> if it could not be found.
	 *
	 * @param portletPreferenceValueId the primary key of the portlet preference value
	 * @return the portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a portlet preference value with the primary key could not be found
	 */
	public PortletPreferenceValue findByPrimaryKey(
			long portletPreferenceValueId)
		throws NoSuchPortletPreferenceValueException;

	/**
	 * Returns the portlet preference value with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param portletPreferenceValueId the primary key of the portlet preference value
	 * @return the portlet preference value, or <code>null</code> if a portlet preference value with the primary key could not be found
	 */
	public PortletPreferenceValue fetchByPrimaryKey(
		long portletPreferenceValueId);

	/**
	 * Returns all the portlet preference values.
	 *
	 * @return the portlet preference values
	 */
	public java.util.List<PortletPreferenceValue> findAll();

	/**
	 * Returns a range of all the portlet preference values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @return the range of portlet preference values
	 */
	public java.util.List<PortletPreferenceValue> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the portlet preference values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of portlet preference values
	 */
	public java.util.List<PortletPreferenceValue> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferenceValue>
			orderByComparator);

	/**
	 * Returns an ordered range of all the portlet preference values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of portlet preference values
	 */
	public java.util.List<PortletPreferenceValue> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferenceValue>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the portlet preference values from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of portlet preference values.
	 *
	 * @return the number of portlet preference values
	 */
	public int countAll();

}