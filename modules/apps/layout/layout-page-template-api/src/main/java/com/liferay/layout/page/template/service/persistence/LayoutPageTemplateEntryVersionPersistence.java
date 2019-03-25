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

package com.liferay.layout.page.template.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.layout.page.template.exception.NoSuchPageTemplateEntryVersionException;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntryVersion;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the layout page template entry version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateEntryVersionUtil
 * @generated
 */
@ProviderType
public interface LayoutPageTemplateEntryVersionPersistence
	extends BasePersistence<LayoutPageTemplateEntryVersion> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutPageTemplateEntryVersionUtil} to access the layout page template entry version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the layout page template entry versions where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @return the matching layout page template entry versions
	 */
	public java.util.List<LayoutPageTemplateEntryVersion>
		findBylayoutPageTemplateEntryId(long layoutPageTemplateEntryId);

	/**
	 * Returns a range of all the layout page template entry versions where layoutPageTemplateEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param start the lower bound of the range of layout page template entry versions
	 * @param end the upper bound of the range of layout page template entry versions (not inclusive)
	 * @return the range of matching layout page template entry versions
	 */
	public java.util.List<LayoutPageTemplateEntryVersion>
		findBylayoutPageTemplateEntryId(
			long layoutPageTemplateEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the layout page template entry versions where layoutPageTemplateEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param start the lower bound of the range of layout page template entry versions
	 * @param end the upper bound of the range of layout page template entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entry versions
	 */
	public java.util.List<LayoutPageTemplateEntryVersion>
		findBylayoutPageTemplateEntryId(
			long layoutPageTemplateEntryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateEntryVersion> orderByComparator);

	/**
	 * Returns an ordered range of all the layout page template entry versions where layoutPageTemplateEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param start the lower bound of the range of layout page template entry versions
	 * @param end the upper bound of the range of layout page template entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout page template entry versions
	 */
	public java.util.List<LayoutPageTemplateEntryVersion>
		findBylayoutPageTemplateEntryId(
			long layoutPageTemplateEntryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateEntryVersion> orderByComparator,
			boolean retrieveFromCache);

	/**
	 * Returns the first layout page template entry version in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry version
	 * @throws NoSuchPageTemplateEntryVersionException if a matching layout page template entry version could not be found
	 */
	public LayoutPageTemplateEntryVersion findBylayoutPageTemplateEntryId_First(
			long layoutPageTemplateEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateEntryVersion> orderByComparator)
		throws NoSuchPageTemplateEntryVersionException;

	/**
	 * Returns the first layout page template entry version in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry version, or <code>null</code> if a matching layout page template entry version could not be found
	 */
	public LayoutPageTemplateEntryVersion
		fetchBylayoutPageTemplateEntryId_First(
			long layoutPageTemplateEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateEntryVersion> orderByComparator);

	/**
	 * Returns the last layout page template entry version in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry version
	 * @throws NoSuchPageTemplateEntryVersionException if a matching layout page template entry version could not be found
	 */
	public LayoutPageTemplateEntryVersion findBylayoutPageTemplateEntryId_Last(
			long layoutPageTemplateEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateEntryVersion> orderByComparator)
		throws NoSuchPageTemplateEntryVersionException;

	/**
	 * Returns the last layout page template entry version in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry version, or <code>null</code> if a matching layout page template entry version could not be found
	 */
	public LayoutPageTemplateEntryVersion fetchBylayoutPageTemplateEntryId_Last(
		long layoutPageTemplateEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateEntryVersion> orderByComparator);

	/**
	 * Returns the layout page template entry versions before and after the current layout page template entry version in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryVersionId the primary key of the current layout page template entry version
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry version
	 * @throws NoSuchPageTemplateEntryVersionException if a layout page template entry version with the primary key could not be found
	 */
	public LayoutPageTemplateEntryVersion[]
			findBylayoutPageTemplateEntryId_PrevAndNext(
				long layoutPageTemplateEntryVersionId,
				long layoutPageTemplateEntryId,
				com.liferay.portal.kernel.util.OrderByComparator
					<LayoutPageTemplateEntryVersion> orderByComparator)
		throws NoSuchPageTemplateEntryVersionException;

	/**
	 * Removes all the layout page template entry versions where layoutPageTemplateEntryId = &#63; from the database.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 */
	public void removeBylayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId);

	/**
	 * Returns the number of layout page template entry versions where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @return the number of matching layout page template entry versions
	 */
	public int countBylayoutPageTemplateEntryId(long layoutPageTemplateEntryId);

	/**
	 * Caches the layout page template entry version in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplateEntryVersion the layout page template entry version
	 */
	public void cacheResult(
		LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion);

	/**
	 * Caches the layout page template entry versions in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplateEntryVersions the layout page template entry versions
	 */
	public void cacheResult(
		java.util.List<LayoutPageTemplateEntryVersion>
			layoutPageTemplateEntryVersions);

	/**
	 * Creates a new layout page template entry version with the primary key. Does not add the layout page template entry version to the database.
	 *
	 * @param layoutPageTemplateEntryVersionId the primary key for the new layout page template entry version
	 * @return the new layout page template entry version
	 */
	public LayoutPageTemplateEntryVersion create(
		long layoutPageTemplateEntryVersionId);

	/**
	 * Removes the layout page template entry version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateEntryVersionId the primary key of the layout page template entry version
	 * @return the layout page template entry version that was removed
	 * @throws NoSuchPageTemplateEntryVersionException if a layout page template entry version with the primary key could not be found
	 */
	public LayoutPageTemplateEntryVersion remove(
			long layoutPageTemplateEntryVersionId)
		throws NoSuchPageTemplateEntryVersionException;

	public LayoutPageTemplateEntryVersion updateImpl(
		LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion);

	/**
	 * Returns the layout page template entry version with the primary key or throws a <code>NoSuchPageTemplateEntryVersionException</code> if it could not be found.
	 *
	 * @param layoutPageTemplateEntryVersionId the primary key of the layout page template entry version
	 * @return the layout page template entry version
	 * @throws NoSuchPageTemplateEntryVersionException if a layout page template entry version with the primary key could not be found
	 */
	public LayoutPageTemplateEntryVersion findByPrimaryKey(
			long layoutPageTemplateEntryVersionId)
		throws NoSuchPageTemplateEntryVersionException;

	/**
	 * Returns the layout page template entry version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutPageTemplateEntryVersionId the primary key of the layout page template entry version
	 * @return the layout page template entry version, or <code>null</code> if a layout page template entry version with the primary key could not be found
	 */
	public LayoutPageTemplateEntryVersion fetchByPrimaryKey(
		long layoutPageTemplateEntryVersionId);

	/**
	 * Returns all the layout page template entry versions.
	 *
	 * @return the layout page template entry versions
	 */
	public java.util.List<LayoutPageTemplateEntryVersion> findAll();

	/**
	 * Returns a range of all the layout page template entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template entry versions
	 * @param end the upper bound of the range of layout page template entry versions (not inclusive)
	 * @return the range of layout page template entry versions
	 */
	public java.util.List<LayoutPageTemplateEntryVersion> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the layout page template entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template entry versions
	 * @param end the upper bound of the range of layout page template entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout page template entry versions
	 */
	public java.util.List<LayoutPageTemplateEntryVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateEntryVersion> orderByComparator);

	/**
	 * Returns an ordered range of all the layout page template entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template entry versions
	 * @param end the upper bound of the range of layout page template entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of layout page template entry versions
	 */
	public java.util.List<LayoutPageTemplateEntryVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateEntryVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Removes all the layout page template entry versions from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of layout page template entry versions.
	 *
	 * @return the number of layout page template entry versions
	 */
	public int countAll();

}