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

package com.liferay.object.service.persistence;

import com.liferay.object.exception.NoSuchObjectViewException;
import com.liferay.object.model.ObjectView;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the object view service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectViewUtil
 * @generated
 */
@ProviderType
public interface ObjectViewPersistence extends BasePersistence<ObjectView> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ObjectViewUtil} to access the object view persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the object views where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object views
	 */
	public java.util.List<ObjectView> findByUuid(String uuid);

	/**
	 * Returns a range of all the object views where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object views
	 * @param end the upper bound of the range of object views (not inclusive)
	 * @return the range of matching object views
	 */
	public java.util.List<ObjectView> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the object views where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object views
	 * @param end the upper bound of the range of object views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object views
	 */
	public java.util.List<ObjectView> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectView>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object views where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object views
	 * @param end the upper bound of the range of object views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object views
	 */
	public java.util.List<ObjectView> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectView>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view
	 * @throws NoSuchObjectViewException if a matching object view could not be found
	 */
	public ObjectView findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectView>
				orderByComparator)
		throws NoSuchObjectViewException;

	/**
	 * Returns the first object view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view, or <code>null</code> if a matching object view could not be found
	 */
	public ObjectView fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectView>
			orderByComparator);

	/**
	 * Returns the last object view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view
	 * @throws NoSuchObjectViewException if a matching object view could not be found
	 */
	public ObjectView findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectView>
				orderByComparator)
		throws NoSuchObjectViewException;

	/**
	 * Returns the last object view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view, or <code>null</code> if a matching object view could not be found
	 */
	public ObjectView fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectView>
			orderByComparator);

	/**
	 * Returns the object views before and after the current object view in the ordered set where uuid = &#63;.
	 *
	 * @param objectViewId the primary key of the current object view
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view
	 * @throws NoSuchObjectViewException if a object view with the primary key could not be found
	 */
	public ObjectView[] findByUuid_PrevAndNext(
			long objectViewId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectView>
				orderByComparator)
		throws NoSuchObjectViewException;

	/**
	 * Removes all the object views where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of object views where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object views
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the object views where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object views
	 */
	public java.util.List<ObjectView> findByUuid_C(String uuid, long companyId);

	/**
	 * Returns a range of all the object views where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object views
	 * @param end the upper bound of the range of object views (not inclusive)
	 * @return the range of matching object views
	 */
	public java.util.List<ObjectView> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the object views where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object views
	 * @param end the upper bound of the range of object views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object views
	 */
	public java.util.List<ObjectView> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectView>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object views where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object views
	 * @param end the upper bound of the range of object views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object views
	 */
	public java.util.List<ObjectView> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectView>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view
	 * @throws NoSuchObjectViewException if a matching object view could not be found
	 */
	public ObjectView findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectView>
				orderByComparator)
		throws NoSuchObjectViewException;

	/**
	 * Returns the first object view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view, or <code>null</code> if a matching object view could not be found
	 */
	public ObjectView fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectView>
			orderByComparator);

	/**
	 * Returns the last object view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view
	 * @throws NoSuchObjectViewException if a matching object view could not be found
	 */
	public ObjectView findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectView>
				orderByComparator)
		throws NoSuchObjectViewException;

	/**
	 * Returns the last object view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view, or <code>null</code> if a matching object view could not be found
	 */
	public ObjectView fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectView>
			orderByComparator);

	/**
	 * Returns the object views before and after the current object view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectViewId the primary key of the current object view
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view
	 * @throws NoSuchObjectViewException if a object view with the primary key could not be found
	 */
	public ObjectView[] findByUuid_C_PrevAndNext(
			long objectViewId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectView>
				orderByComparator)
		throws NoSuchObjectViewException;

	/**
	 * Removes all the object views where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of object views where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object views
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the object views where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @return the matching object views
	 */
	public java.util.List<ObjectView> findByObjectDefinitionId(
		long objectDefinitionId);

	/**
	 * Returns a range of all the object views where objectDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param start the lower bound of the range of object views
	 * @param end the upper bound of the range of object views (not inclusive)
	 * @return the range of matching object views
	 */
	public java.util.List<ObjectView> findByObjectDefinitionId(
		long objectDefinitionId, int start, int end);

	/**
	 * Returns an ordered range of all the object views where objectDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param start the lower bound of the range of object views
	 * @param end the upper bound of the range of object views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object views
	 */
	public java.util.List<ObjectView> findByObjectDefinitionId(
		long objectDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectView>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object views where objectDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param start the lower bound of the range of object views
	 * @param end the upper bound of the range of object views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object views
	 */
	public java.util.List<ObjectView> findByObjectDefinitionId(
		long objectDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectView>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object view in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view
	 * @throws NoSuchObjectViewException if a matching object view could not be found
	 */
	public ObjectView findByObjectDefinitionId_First(
			long objectDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectView>
				orderByComparator)
		throws NoSuchObjectViewException;

	/**
	 * Returns the first object view in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view, or <code>null</code> if a matching object view could not be found
	 */
	public ObjectView fetchByObjectDefinitionId_First(
		long objectDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectView>
			orderByComparator);

	/**
	 * Returns the last object view in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view
	 * @throws NoSuchObjectViewException if a matching object view could not be found
	 */
	public ObjectView findByObjectDefinitionId_Last(
			long objectDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectView>
				orderByComparator)
		throws NoSuchObjectViewException;

	/**
	 * Returns the last object view in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view, or <code>null</code> if a matching object view could not be found
	 */
	public ObjectView fetchByObjectDefinitionId_Last(
		long objectDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectView>
			orderByComparator);

	/**
	 * Returns the object views before and after the current object view in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectViewId the primary key of the current object view
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view
	 * @throws NoSuchObjectViewException if a object view with the primary key could not be found
	 */
	public ObjectView[] findByObjectDefinitionId_PrevAndNext(
			long objectViewId, long objectDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectView>
				orderByComparator)
		throws NoSuchObjectViewException;

	/**
	 * Removes all the object views where objectDefinitionId = &#63; from the database.
	 *
	 * @param objectDefinitionId the object definition ID
	 */
	public void removeByObjectDefinitionId(long objectDefinitionId);

	/**
	 * Returns the number of object views where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @return the number of matching object views
	 */
	public int countByObjectDefinitionId(long objectDefinitionId);

	/**
	 * Returns all the object views where objectDefinitionId = &#63; and defaultObjectView = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param defaultObjectView the default object view
	 * @return the matching object views
	 */
	public java.util.List<ObjectView> findByODI_DOV(
		long objectDefinitionId, boolean defaultObjectView);

	/**
	 * Returns a range of all the object views where objectDefinitionId = &#63; and defaultObjectView = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param defaultObjectView the default object view
	 * @param start the lower bound of the range of object views
	 * @param end the upper bound of the range of object views (not inclusive)
	 * @return the range of matching object views
	 */
	public java.util.List<ObjectView> findByODI_DOV(
		long objectDefinitionId, boolean defaultObjectView, int start, int end);

	/**
	 * Returns an ordered range of all the object views where objectDefinitionId = &#63; and defaultObjectView = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param defaultObjectView the default object view
	 * @param start the lower bound of the range of object views
	 * @param end the upper bound of the range of object views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object views
	 */
	public java.util.List<ObjectView> findByODI_DOV(
		long objectDefinitionId, boolean defaultObjectView, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectView>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object views where objectDefinitionId = &#63; and defaultObjectView = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param defaultObjectView the default object view
	 * @param start the lower bound of the range of object views
	 * @param end the upper bound of the range of object views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object views
	 */
	public java.util.List<ObjectView> findByODI_DOV(
		long objectDefinitionId, boolean defaultObjectView, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectView>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object view in the ordered set where objectDefinitionId = &#63; and defaultObjectView = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param defaultObjectView the default object view
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view
	 * @throws NoSuchObjectViewException if a matching object view could not be found
	 */
	public ObjectView findByODI_DOV_First(
			long objectDefinitionId, boolean defaultObjectView,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectView>
				orderByComparator)
		throws NoSuchObjectViewException;

	/**
	 * Returns the first object view in the ordered set where objectDefinitionId = &#63; and defaultObjectView = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param defaultObjectView the default object view
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view, or <code>null</code> if a matching object view could not be found
	 */
	public ObjectView fetchByODI_DOV_First(
		long objectDefinitionId, boolean defaultObjectView,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectView>
			orderByComparator);

	/**
	 * Returns the last object view in the ordered set where objectDefinitionId = &#63; and defaultObjectView = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param defaultObjectView the default object view
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view
	 * @throws NoSuchObjectViewException if a matching object view could not be found
	 */
	public ObjectView findByODI_DOV_Last(
			long objectDefinitionId, boolean defaultObjectView,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectView>
				orderByComparator)
		throws NoSuchObjectViewException;

	/**
	 * Returns the last object view in the ordered set where objectDefinitionId = &#63; and defaultObjectView = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param defaultObjectView the default object view
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view, or <code>null</code> if a matching object view could not be found
	 */
	public ObjectView fetchByODI_DOV_Last(
		long objectDefinitionId, boolean defaultObjectView,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectView>
			orderByComparator);

	/**
	 * Returns the object views before and after the current object view in the ordered set where objectDefinitionId = &#63; and defaultObjectView = &#63;.
	 *
	 * @param objectViewId the primary key of the current object view
	 * @param objectDefinitionId the object definition ID
	 * @param defaultObjectView the default object view
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view
	 * @throws NoSuchObjectViewException if a object view with the primary key could not be found
	 */
	public ObjectView[] findByODI_DOV_PrevAndNext(
			long objectViewId, long objectDefinitionId,
			boolean defaultObjectView,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectView>
				orderByComparator)
		throws NoSuchObjectViewException;

	/**
	 * Removes all the object views where objectDefinitionId = &#63; and defaultObjectView = &#63; from the database.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param defaultObjectView the default object view
	 */
	public void removeByODI_DOV(
		long objectDefinitionId, boolean defaultObjectView);

	/**
	 * Returns the number of object views where objectDefinitionId = &#63; and defaultObjectView = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param defaultObjectView the default object view
	 * @return the number of matching object views
	 */
	public int countByODI_DOV(
		long objectDefinitionId, boolean defaultObjectView);

	/**
	 * Caches the object view in the entity cache if it is enabled.
	 *
	 * @param objectView the object view
	 */
	public void cacheResult(ObjectView objectView);

	/**
	 * Caches the object views in the entity cache if it is enabled.
	 *
	 * @param objectViews the object views
	 */
	public void cacheResult(java.util.List<ObjectView> objectViews);

	/**
	 * Creates a new object view with the primary key. Does not add the object view to the database.
	 *
	 * @param objectViewId the primary key for the new object view
	 * @return the new object view
	 */
	public ObjectView create(long objectViewId);

	/**
	 * Removes the object view with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectViewId the primary key of the object view
	 * @return the object view that was removed
	 * @throws NoSuchObjectViewException if a object view with the primary key could not be found
	 */
	public ObjectView remove(long objectViewId)
		throws NoSuchObjectViewException;

	public ObjectView updateImpl(ObjectView objectView);

	/**
	 * Returns the object view with the primary key or throws a <code>NoSuchObjectViewException</code> if it could not be found.
	 *
	 * @param objectViewId the primary key of the object view
	 * @return the object view
	 * @throws NoSuchObjectViewException if a object view with the primary key could not be found
	 */
	public ObjectView findByPrimaryKey(long objectViewId)
		throws NoSuchObjectViewException;

	/**
	 * Returns the object view with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectViewId the primary key of the object view
	 * @return the object view, or <code>null</code> if a object view with the primary key could not be found
	 */
	public ObjectView fetchByPrimaryKey(long objectViewId);

	/**
	 * Returns all the object views.
	 *
	 * @return the object views
	 */
	public java.util.List<ObjectView> findAll();

	/**
	 * Returns a range of all the object views.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object views
	 * @param end the upper bound of the range of object views (not inclusive)
	 * @return the range of object views
	 */
	public java.util.List<ObjectView> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the object views.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object views
	 * @param end the upper bound of the range of object views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object views
	 */
	public java.util.List<ObjectView> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectView>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object views.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object views
	 * @param end the upper bound of the range of object views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object views
	 */
	public java.util.List<ObjectView> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectView>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the object views from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of object views.
	 *
	 * @return the number of object views
	 */
	public int countAll();

}