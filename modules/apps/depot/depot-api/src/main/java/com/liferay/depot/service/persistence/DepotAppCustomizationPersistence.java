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

package com.liferay.depot.service.persistence;

import com.liferay.depot.exception.NoSuchAppCustomizationException;
import com.liferay.depot.model.DepotAppCustomization;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the depot app customization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DepotAppCustomizationUtil
 * @generated
 */
@ProviderType
public interface DepotAppCustomizationPersistence
	extends BasePersistence<DepotAppCustomization> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DepotAppCustomizationUtil} to access the depot app customization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the depot app customizations where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @return the matching depot app customizations
	 */
	public java.util.List<DepotAppCustomization> findByDepotEntryId(
		long depotEntryId);

	/**
	 * Returns a range of all the depot app customizations where depotEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotAppCustomizationModelImpl</code>.
	 * </p>
	 *
	 * @param depotEntryId the depot entry ID
	 * @param start the lower bound of the range of depot app customizations
	 * @param end the upper bound of the range of depot app customizations (not inclusive)
	 * @return the range of matching depot app customizations
	 */
	public java.util.List<DepotAppCustomization> findByDepotEntryId(
		long depotEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the depot app customizations where depotEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotAppCustomizationModelImpl</code>.
	 * </p>
	 *
	 * @param depotEntryId the depot entry ID
	 * @param start the lower bound of the range of depot app customizations
	 * @param end the upper bound of the range of depot app customizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching depot app customizations
	 */
	public java.util.List<DepotAppCustomization> findByDepotEntryId(
		long depotEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DepotAppCustomization>
			orderByComparator);

	/**
	 * Returns an ordered range of all the depot app customizations where depotEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotAppCustomizationModelImpl</code>.
	 * </p>
	 *
	 * @param depotEntryId the depot entry ID
	 * @param start the lower bound of the range of depot app customizations
	 * @param end the upper bound of the range of depot app customizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching depot app customizations
	 */
	public java.util.List<DepotAppCustomization> findByDepotEntryId(
		long depotEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DepotAppCustomization>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first depot app customization in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot app customization
	 * @throws NoSuchAppCustomizationException if a matching depot app customization could not be found
	 */
	public DepotAppCustomization findByDepotEntryId_First(
			long depotEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<DepotAppCustomization> orderByComparator)
		throws NoSuchAppCustomizationException;

	/**
	 * Returns the first depot app customization in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot app customization, or <code>null</code> if a matching depot app customization could not be found
	 */
	public DepotAppCustomization fetchByDepotEntryId_First(
		long depotEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<DepotAppCustomization>
			orderByComparator);

	/**
	 * Returns the last depot app customization in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching depot app customization
	 * @throws NoSuchAppCustomizationException if a matching depot app customization could not be found
	 */
	public DepotAppCustomization findByDepotEntryId_Last(
			long depotEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<DepotAppCustomization> orderByComparator)
		throws NoSuchAppCustomizationException;

	/**
	 * Returns the last depot app customization in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching depot app customization, or <code>null</code> if a matching depot app customization could not be found
	 */
	public DepotAppCustomization fetchByDepotEntryId_Last(
		long depotEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<DepotAppCustomization>
			orderByComparator);

	/**
	 * Returns the depot app customizations before and after the current depot app customization in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotAppCustomizationId the primary key of the current depot app customization
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next depot app customization
	 * @throws NoSuchAppCustomizationException if a depot app customization with the primary key could not be found
	 */
	public DepotAppCustomization[] findByDepotEntryId_PrevAndNext(
			long depotAppCustomizationId, long depotEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<DepotAppCustomization> orderByComparator)
		throws NoSuchAppCustomizationException;

	/**
	 * Removes all the depot app customizations where depotEntryId = &#63; from the database.
	 *
	 * @param depotEntryId the depot entry ID
	 */
	public void removeByDepotEntryId(long depotEntryId);

	/**
	 * Returns the number of depot app customizations where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @return the number of matching depot app customizations
	 */
	public int countByDepotEntryId(long depotEntryId);

	/**
	 * Returns the depot app customization where depotEntryId = &#63; and portletId = &#63; or throws a <code>NoSuchAppCustomizationException</code> if it could not be found.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param portletId the portlet ID
	 * @return the matching depot app customization
	 * @throws NoSuchAppCustomizationException if a matching depot app customization could not be found
	 */
	public DepotAppCustomization findByD_PI(long depotEntryId, String portletId)
		throws NoSuchAppCustomizationException;

	/**
	 * Returns the depot app customization where depotEntryId = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param portletId the portlet ID
	 * @return the matching depot app customization, or <code>null</code> if a matching depot app customization could not be found
	 */
	public DepotAppCustomization fetchByD_PI(
		long depotEntryId, String portletId);

	/**
	 * Returns the depot app customization where depotEntryId = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param portletId the portlet ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching depot app customization, or <code>null</code> if a matching depot app customization could not be found
	 */
	public DepotAppCustomization fetchByD_PI(
		long depotEntryId, String portletId, boolean useFinderCache);

	/**
	 * Removes the depot app customization where depotEntryId = &#63; and portletId = &#63; from the database.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param portletId the portlet ID
	 * @return the depot app customization that was removed
	 */
	public DepotAppCustomization removeByD_PI(
			long depotEntryId, String portletId)
		throws NoSuchAppCustomizationException;

	/**
	 * Returns the number of depot app customizations where depotEntryId = &#63; and portletId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param portletId the portlet ID
	 * @return the number of matching depot app customizations
	 */
	public int countByD_PI(long depotEntryId, String portletId);

	/**
	 * Caches the depot app customization in the entity cache if it is enabled.
	 *
	 * @param depotAppCustomization the depot app customization
	 */
	public void cacheResult(DepotAppCustomization depotAppCustomization);

	/**
	 * Caches the depot app customizations in the entity cache if it is enabled.
	 *
	 * @param depotAppCustomizations the depot app customizations
	 */
	public void cacheResult(
		java.util.List<DepotAppCustomization> depotAppCustomizations);

	/**
	 * Creates a new depot app customization with the primary key. Does not add the depot app customization to the database.
	 *
	 * @param depotAppCustomizationId the primary key for the new depot app customization
	 * @return the new depot app customization
	 */
	public DepotAppCustomization create(long depotAppCustomizationId);

	/**
	 * Removes the depot app customization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param depotAppCustomizationId the primary key of the depot app customization
	 * @return the depot app customization that was removed
	 * @throws NoSuchAppCustomizationException if a depot app customization with the primary key could not be found
	 */
	public DepotAppCustomization remove(long depotAppCustomizationId)
		throws NoSuchAppCustomizationException;

	public DepotAppCustomization updateImpl(
		DepotAppCustomization depotAppCustomization);

	/**
	 * Returns the depot app customization with the primary key or throws a <code>NoSuchAppCustomizationException</code> if it could not be found.
	 *
	 * @param depotAppCustomizationId the primary key of the depot app customization
	 * @return the depot app customization
	 * @throws NoSuchAppCustomizationException if a depot app customization with the primary key could not be found
	 */
	public DepotAppCustomization findByPrimaryKey(long depotAppCustomizationId)
		throws NoSuchAppCustomizationException;

	/**
	 * Returns the depot app customization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param depotAppCustomizationId the primary key of the depot app customization
	 * @return the depot app customization, or <code>null</code> if a depot app customization with the primary key could not be found
	 */
	public DepotAppCustomization fetchByPrimaryKey(
		long depotAppCustomizationId);

	/**
	 * Returns all the depot app customizations.
	 *
	 * @return the depot app customizations
	 */
	public java.util.List<DepotAppCustomization> findAll();

	/**
	 * Returns a range of all the depot app customizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotAppCustomizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of depot app customizations
	 * @param end the upper bound of the range of depot app customizations (not inclusive)
	 * @return the range of depot app customizations
	 */
	public java.util.List<DepotAppCustomization> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the depot app customizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotAppCustomizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of depot app customizations
	 * @param end the upper bound of the range of depot app customizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of depot app customizations
	 */
	public java.util.List<DepotAppCustomization> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DepotAppCustomization>
			orderByComparator);

	/**
	 * Returns an ordered range of all the depot app customizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotAppCustomizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of depot app customizations
	 * @param end the upper bound of the range of depot app customizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of depot app customizations
	 */
	public java.util.List<DepotAppCustomization> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DepotAppCustomization>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the depot app customizations from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of depot app customizations.
	 *
	 * @return the number of depot app customizations
	 */
	public int countAll();

}