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

package com.liferay.custom.elements.service.persistence;

import com.liferay.custom.elements.exception.NoSuchSourceException;
import com.liferay.custom.elements.model.CustomElementsSource;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the custom elements source service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CustomElementsSourceUtil
 * @generated
 */
@ProviderType
public interface CustomElementsSourcePersistence
	extends BasePersistence<CustomElementsSource> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CustomElementsSourceUtil} to access the custom elements source persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the custom elements sources where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching custom elements sources
	 */
	public java.util.List<CustomElementsSource> findByUuid(String uuid);

	/**
	 * Returns a range of all the custom elements sources where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsSourceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of custom elements sources
	 * @param end the upper bound of the range of custom elements sources (not inclusive)
	 * @return the range of matching custom elements sources
	 */
	public java.util.List<CustomElementsSource> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the custom elements sources where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsSourceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of custom elements sources
	 * @param end the upper bound of the range of custom elements sources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching custom elements sources
	 */
	public java.util.List<CustomElementsSource> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CustomElementsSource>
			orderByComparator);

	/**
	 * Returns an ordered range of all the custom elements sources where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsSourceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of custom elements sources
	 * @param end the upper bound of the range of custom elements sources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching custom elements sources
	 */
	public java.util.List<CustomElementsSource> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CustomElementsSource>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first custom elements source in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom elements source
	 * @throws NoSuchSourceException if a matching custom elements source could not be found
	 */
	public CustomElementsSource findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CustomElementsSource> orderByComparator)
		throws NoSuchSourceException;

	/**
	 * Returns the first custom elements source in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom elements source, or <code>null</code> if a matching custom elements source could not be found
	 */
	public CustomElementsSource fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CustomElementsSource>
			orderByComparator);

	/**
	 * Returns the last custom elements source in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching custom elements source
	 * @throws NoSuchSourceException if a matching custom elements source could not be found
	 */
	public CustomElementsSource findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CustomElementsSource> orderByComparator)
		throws NoSuchSourceException;

	/**
	 * Returns the last custom elements source in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching custom elements source, or <code>null</code> if a matching custom elements source could not be found
	 */
	public CustomElementsSource fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CustomElementsSource>
			orderByComparator);

	/**
	 * Returns the custom elements sources before and after the current custom elements source in the ordered set where uuid = &#63;.
	 *
	 * @param customElementsSourceId the primary key of the current custom elements source
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next custom elements source
	 * @throws NoSuchSourceException if a custom elements source with the primary key could not be found
	 */
	public CustomElementsSource[] findByUuid_PrevAndNext(
			long customElementsSourceId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CustomElementsSource> orderByComparator)
		throws NoSuchSourceException;

	/**
	 * Removes all the custom elements sources where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of custom elements sources where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching custom elements sources
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the custom elements sources where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching custom elements sources
	 */
	public java.util.List<CustomElementsSource> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the custom elements sources where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsSourceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of custom elements sources
	 * @param end the upper bound of the range of custom elements sources (not inclusive)
	 * @return the range of matching custom elements sources
	 */
	public java.util.List<CustomElementsSource> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the custom elements sources where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsSourceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of custom elements sources
	 * @param end the upper bound of the range of custom elements sources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching custom elements sources
	 */
	public java.util.List<CustomElementsSource> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CustomElementsSource>
			orderByComparator);

	/**
	 * Returns an ordered range of all the custom elements sources where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsSourceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of custom elements sources
	 * @param end the upper bound of the range of custom elements sources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching custom elements sources
	 */
	public java.util.List<CustomElementsSource> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CustomElementsSource>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first custom elements source in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom elements source
	 * @throws NoSuchSourceException if a matching custom elements source could not be found
	 */
	public CustomElementsSource findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CustomElementsSource> orderByComparator)
		throws NoSuchSourceException;

	/**
	 * Returns the first custom elements source in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom elements source, or <code>null</code> if a matching custom elements source could not be found
	 */
	public CustomElementsSource fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CustomElementsSource>
			orderByComparator);

	/**
	 * Returns the last custom elements source in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching custom elements source
	 * @throws NoSuchSourceException if a matching custom elements source could not be found
	 */
	public CustomElementsSource findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CustomElementsSource> orderByComparator)
		throws NoSuchSourceException;

	/**
	 * Returns the last custom elements source in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching custom elements source, or <code>null</code> if a matching custom elements source could not be found
	 */
	public CustomElementsSource fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CustomElementsSource>
			orderByComparator);

	/**
	 * Returns the custom elements sources before and after the current custom elements source in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param customElementsSourceId the primary key of the current custom elements source
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next custom elements source
	 * @throws NoSuchSourceException if a custom elements source with the primary key could not be found
	 */
	public CustomElementsSource[] findByUuid_C_PrevAndNext(
			long customElementsSourceId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CustomElementsSource> orderByComparator)
		throws NoSuchSourceException;

	/**
	 * Removes all the custom elements sources where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of custom elements sources where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching custom elements sources
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the custom elements sources where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching custom elements sources
	 */
	public java.util.List<CustomElementsSource> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the custom elements sources where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsSourceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of custom elements sources
	 * @param end the upper bound of the range of custom elements sources (not inclusive)
	 * @return the range of matching custom elements sources
	 */
	public java.util.List<CustomElementsSource> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the custom elements sources where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsSourceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of custom elements sources
	 * @param end the upper bound of the range of custom elements sources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching custom elements sources
	 */
	public java.util.List<CustomElementsSource> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CustomElementsSource>
			orderByComparator);

	/**
	 * Returns an ordered range of all the custom elements sources where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsSourceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of custom elements sources
	 * @param end the upper bound of the range of custom elements sources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching custom elements sources
	 */
	public java.util.List<CustomElementsSource> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CustomElementsSource>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first custom elements source in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom elements source
	 * @throws NoSuchSourceException if a matching custom elements source could not be found
	 */
	public CustomElementsSource findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CustomElementsSource> orderByComparator)
		throws NoSuchSourceException;

	/**
	 * Returns the first custom elements source in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom elements source, or <code>null</code> if a matching custom elements source could not be found
	 */
	public CustomElementsSource fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CustomElementsSource>
			orderByComparator);

	/**
	 * Returns the last custom elements source in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching custom elements source
	 * @throws NoSuchSourceException if a matching custom elements source could not be found
	 */
	public CustomElementsSource findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CustomElementsSource> orderByComparator)
		throws NoSuchSourceException;

	/**
	 * Returns the last custom elements source in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching custom elements source, or <code>null</code> if a matching custom elements source could not be found
	 */
	public CustomElementsSource fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CustomElementsSource>
			orderByComparator);

	/**
	 * Returns the custom elements sources before and after the current custom elements source in the ordered set where companyId = &#63;.
	 *
	 * @param customElementsSourceId the primary key of the current custom elements source
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next custom elements source
	 * @throws NoSuchSourceException if a custom elements source with the primary key could not be found
	 */
	public CustomElementsSource[] findByCompanyId_PrevAndNext(
			long customElementsSourceId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CustomElementsSource> orderByComparator)
		throws NoSuchSourceException;

	/**
	 * Removes all the custom elements sources where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of custom elements sources where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching custom elements sources
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Caches the custom elements source in the entity cache if it is enabled.
	 *
	 * @param customElementsSource the custom elements source
	 */
	public void cacheResult(CustomElementsSource customElementsSource);

	/**
	 * Caches the custom elements sources in the entity cache if it is enabled.
	 *
	 * @param customElementsSources the custom elements sources
	 */
	public void cacheResult(
		java.util.List<CustomElementsSource> customElementsSources);

	/**
	 * Creates a new custom elements source with the primary key. Does not add the custom elements source to the database.
	 *
	 * @param customElementsSourceId the primary key for the new custom elements source
	 * @return the new custom elements source
	 */
	public CustomElementsSource create(long customElementsSourceId);

	/**
	 * Removes the custom elements source with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param customElementsSourceId the primary key of the custom elements source
	 * @return the custom elements source that was removed
	 * @throws NoSuchSourceException if a custom elements source with the primary key could not be found
	 */
	public CustomElementsSource remove(long customElementsSourceId)
		throws NoSuchSourceException;

	public CustomElementsSource updateImpl(
		CustomElementsSource customElementsSource);

	/**
	 * Returns the custom elements source with the primary key or throws a <code>NoSuchSourceException</code> if it could not be found.
	 *
	 * @param customElementsSourceId the primary key of the custom elements source
	 * @return the custom elements source
	 * @throws NoSuchSourceException if a custom elements source with the primary key could not be found
	 */
	public CustomElementsSource findByPrimaryKey(long customElementsSourceId)
		throws NoSuchSourceException;

	/**
	 * Returns the custom elements source with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param customElementsSourceId the primary key of the custom elements source
	 * @return the custom elements source, or <code>null</code> if a custom elements source with the primary key could not be found
	 */
	public CustomElementsSource fetchByPrimaryKey(long customElementsSourceId);

	/**
	 * Returns all the custom elements sources.
	 *
	 * @return the custom elements sources
	 */
	public java.util.List<CustomElementsSource> findAll();

	/**
	 * Returns a range of all the custom elements sources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsSourceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of custom elements sources
	 * @param end the upper bound of the range of custom elements sources (not inclusive)
	 * @return the range of custom elements sources
	 */
	public java.util.List<CustomElementsSource> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the custom elements sources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsSourceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of custom elements sources
	 * @param end the upper bound of the range of custom elements sources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of custom elements sources
	 */
	public java.util.List<CustomElementsSource> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CustomElementsSource>
			orderByComparator);

	/**
	 * Returns an ordered range of all the custom elements sources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsSourceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of custom elements sources
	 * @param end the upper bound of the range of custom elements sources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of custom elements sources
	 */
	public java.util.List<CustomElementsSource> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CustomElementsSource>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the custom elements sources from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of custom elements sources.
	 *
	 * @return the number of custom elements sources
	 */
	public int countAll();

}