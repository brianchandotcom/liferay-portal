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

package com.liferay.portlet.softwarecatalog.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.softwarecatalog.model.SCLicense;

/**
 * The persistence interface for the s c license service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.softwarecatalog.service.persistence.impl.SCLicensePersistenceImpl
 * @see SCLicenseUtil
 * @generated
 */
@ProviderType
public interface SCLicensePersistence extends BasePersistence<SCLicense> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SCLicenseUtil} to access the s c license persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the s c licenses where active = &#63;.
	*
	* @param active the active
	* @return the matching s c licenses
	*/
	public java.util.List<SCLicense> findByActive(boolean active);

	/**
	* Returns a range of all the s c licenses where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param start the lower bound of the range of s c licenses
	* @param end the upper bound of the range of s c licenses (not inclusive)
	* @return the range of matching s c licenses
	*/
	public java.util.List<SCLicense> findByActive(boolean active, int start,
		int end);

	/**
	* Returns an ordered range of all the s c licenses where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param start the lower bound of the range of s c licenses
	* @param end the upper bound of the range of s c licenses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c licenses
	*/
	public java.util.List<SCLicense> findByActive(boolean active, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SCLicense> orderByComparator);

	/**
	* Returns the first s c license in the ordered set where active = &#63;.
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c license
	* @throws NoSuchLicenseException if a matching s c license could not be found
	*/
	public SCLicense findByActive_First(boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<SCLicense> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException;

	/**
	* Returns the first s c license in the ordered set where active = &#63;.
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c license, or <code>null</code> if a matching s c license could not be found
	*/
	public SCLicense fetchByActive_First(boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<SCLicense> orderByComparator);

	/**
	* Returns the last s c license in the ordered set where active = &#63;.
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c license
	* @throws NoSuchLicenseException if a matching s c license could not be found
	*/
	public SCLicense findByActive_Last(boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<SCLicense> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException;

	/**
	* Returns the last s c license in the ordered set where active = &#63;.
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c license, or <code>null</code> if a matching s c license could not be found
	*/
	public SCLicense fetchByActive_Last(boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<SCLicense> orderByComparator);

	/**
	* Returns the s c licenses before and after the current s c license in the ordered set where active = &#63;.
	*
	* @param licenseId the primary key of the current s c license
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c license
	* @throws NoSuchLicenseException if a s c license with the primary key could not be found
	*/
	public SCLicense[] findByActive_PrevAndNext(long licenseId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<SCLicense> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException;

	/**
	* Returns all the s c licenses that the user has permission to view where active = &#63;.
	*
	* @param active the active
	* @return the matching s c licenses that the user has permission to view
	*/
	public java.util.List<SCLicense> filterFindByActive(boolean active);

	/**
	* Returns a range of all the s c licenses that the user has permission to view where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param start the lower bound of the range of s c licenses
	* @param end the upper bound of the range of s c licenses (not inclusive)
	* @return the range of matching s c licenses that the user has permission to view
	*/
	public java.util.List<SCLicense> filterFindByActive(boolean active,
		int start, int end);

	/**
	* Returns an ordered range of all the s c licenses that the user has permissions to view where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param start the lower bound of the range of s c licenses
	* @param end the upper bound of the range of s c licenses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c licenses that the user has permission to view
	*/
	public java.util.List<SCLicense> filterFindByActive(boolean active,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SCLicense> orderByComparator);

	/**
	* Returns the s c licenses before and after the current s c license in the ordered set of s c licenses that the user has permission to view where active = &#63;.
	*
	* @param licenseId the primary key of the current s c license
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c license
	* @throws NoSuchLicenseException if a s c license with the primary key could not be found
	*/
	public SCLicense[] filterFindByActive_PrevAndNext(long licenseId,
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<SCLicense> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException;

	/**
	* Removes all the s c licenses where active = &#63; from the database.
	*
	* @param active the active
	*/
	public void removeByActive(boolean active);

	/**
	* Returns the number of s c licenses where active = &#63;.
	*
	* @param active the active
	* @return the number of matching s c licenses
	*/
	public int countByActive(boolean active);

	/**
	* Returns the number of s c licenses that the user has permission to view where active = &#63;.
	*
	* @param active the active
	* @return the number of matching s c licenses that the user has permission to view
	*/
	public int filterCountByActive(boolean active);

	/**
	* Returns all the s c licenses where active = &#63; and recommended = &#63;.
	*
	* @param active the active
	* @param recommended the recommended
	* @return the matching s c licenses
	*/
	public java.util.List<SCLicense> findByA_R(boolean active,
		boolean recommended);

	/**
	* Returns a range of all the s c licenses where active = &#63; and recommended = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param recommended the recommended
	* @param start the lower bound of the range of s c licenses
	* @param end the upper bound of the range of s c licenses (not inclusive)
	* @return the range of matching s c licenses
	*/
	public java.util.List<SCLicense> findByA_R(boolean active,
		boolean recommended, int start, int end);

	/**
	* Returns an ordered range of all the s c licenses where active = &#63; and recommended = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param recommended the recommended
	* @param start the lower bound of the range of s c licenses
	* @param end the upper bound of the range of s c licenses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c licenses
	*/
	public java.util.List<SCLicense> findByA_R(boolean active,
		boolean recommended, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SCLicense> orderByComparator);

	/**
	* Returns the first s c license in the ordered set where active = &#63; and recommended = &#63;.
	*
	* @param active the active
	* @param recommended the recommended
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c license
	* @throws NoSuchLicenseException if a matching s c license could not be found
	*/
	public SCLicense findByA_R_First(boolean active, boolean recommended,
		com.liferay.portal.kernel.util.OrderByComparator<SCLicense> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException;

	/**
	* Returns the first s c license in the ordered set where active = &#63; and recommended = &#63;.
	*
	* @param active the active
	* @param recommended the recommended
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c license, or <code>null</code> if a matching s c license could not be found
	*/
	public SCLicense fetchByA_R_First(boolean active, boolean recommended,
		com.liferay.portal.kernel.util.OrderByComparator<SCLicense> orderByComparator);

	/**
	* Returns the last s c license in the ordered set where active = &#63; and recommended = &#63;.
	*
	* @param active the active
	* @param recommended the recommended
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c license
	* @throws NoSuchLicenseException if a matching s c license could not be found
	*/
	public SCLicense findByA_R_Last(boolean active, boolean recommended,
		com.liferay.portal.kernel.util.OrderByComparator<SCLicense> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException;

	/**
	* Returns the last s c license in the ordered set where active = &#63; and recommended = &#63;.
	*
	* @param active the active
	* @param recommended the recommended
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c license, or <code>null</code> if a matching s c license could not be found
	*/
	public SCLicense fetchByA_R_Last(boolean active, boolean recommended,
		com.liferay.portal.kernel.util.OrderByComparator<SCLicense> orderByComparator);

	/**
	* Returns the s c licenses before and after the current s c license in the ordered set where active = &#63; and recommended = &#63;.
	*
	* @param licenseId the primary key of the current s c license
	* @param active the active
	* @param recommended the recommended
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c license
	* @throws NoSuchLicenseException if a s c license with the primary key could not be found
	*/
	public SCLicense[] findByA_R_PrevAndNext(long licenseId, boolean active,
		boolean recommended,
		com.liferay.portal.kernel.util.OrderByComparator<SCLicense> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException;

	/**
	* Returns all the s c licenses that the user has permission to view where active = &#63; and recommended = &#63;.
	*
	* @param active the active
	* @param recommended the recommended
	* @return the matching s c licenses that the user has permission to view
	*/
	public java.util.List<SCLicense> filterFindByA_R(boolean active,
		boolean recommended);

	/**
	* Returns a range of all the s c licenses that the user has permission to view where active = &#63; and recommended = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param recommended the recommended
	* @param start the lower bound of the range of s c licenses
	* @param end the upper bound of the range of s c licenses (not inclusive)
	* @return the range of matching s c licenses that the user has permission to view
	*/
	public java.util.List<SCLicense> filterFindByA_R(boolean active,
		boolean recommended, int start, int end);

	/**
	* Returns an ordered range of all the s c licenses that the user has permissions to view where active = &#63; and recommended = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param recommended the recommended
	* @param start the lower bound of the range of s c licenses
	* @param end the upper bound of the range of s c licenses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c licenses that the user has permission to view
	*/
	public java.util.List<SCLicense> filterFindByA_R(boolean active,
		boolean recommended, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SCLicense> orderByComparator);

	/**
	* Returns the s c licenses before and after the current s c license in the ordered set of s c licenses that the user has permission to view where active = &#63; and recommended = &#63;.
	*
	* @param licenseId the primary key of the current s c license
	* @param active the active
	* @param recommended the recommended
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c license
	* @throws NoSuchLicenseException if a s c license with the primary key could not be found
	*/
	public SCLicense[] filterFindByA_R_PrevAndNext(long licenseId,
		boolean active, boolean recommended,
		com.liferay.portal.kernel.util.OrderByComparator<SCLicense> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException;

	/**
	* Removes all the s c licenses where active = &#63; and recommended = &#63; from the database.
	*
	* @param active the active
	* @param recommended the recommended
	*/
	public void removeByA_R(boolean active, boolean recommended);

	/**
	* Returns the number of s c licenses where active = &#63; and recommended = &#63;.
	*
	* @param active the active
	* @param recommended the recommended
	* @return the number of matching s c licenses
	*/
	public int countByA_R(boolean active, boolean recommended);

	/**
	* Returns the number of s c licenses that the user has permission to view where active = &#63; and recommended = &#63;.
	*
	* @param active the active
	* @param recommended the recommended
	* @return the number of matching s c licenses that the user has permission to view
	*/
	public int filterCountByA_R(boolean active, boolean recommended);

	/**
	* Caches the s c license in the entity cache if it is enabled.
	*
	* @param scLicense the s c license
	*/
	public void cacheResult(SCLicense scLicense);

	/**
	* Caches the s c licenses in the entity cache if it is enabled.
	*
	* @param scLicenses the s c licenses
	*/
	public void cacheResult(java.util.List<SCLicense> scLicenses);

	/**
	* Creates a new s c license with the primary key. Does not add the s c license to the database.
	*
	* @param licenseId the primary key for the new s c license
	* @return the new s c license
	*/
	public SCLicense create(long licenseId);

	/**
	* Removes the s c license with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param licenseId the primary key of the s c license
	* @return the s c license that was removed
	* @throws NoSuchLicenseException if a s c license with the primary key could not be found
	*/
	public SCLicense remove(long licenseId)
		throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException;

	public SCLicense updateImpl(SCLicense scLicense);

	/**
	* Returns the s c license with the primary key or throws a {@link NoSuchLicenseException} if it could not be found.
	*
	* @param licenseId the primary key of the s c license
	* @return the s c license
	* @throws NoSuchLicenseException if a s c license with the primary key could not be found
	*/
	public SCLicense findByPrimaryKey(long licenseId)
		throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException;

	/**
	* Returns the s c license with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param licenseId the primary key of the s c license
	* @return the s c license, or <code>null</code> if a s c license with the primary key could not be found
	*/
	public SCLicense fetchByPrimaryKey(long licenseId);

	@Override
	public java.util.Map<java.io.Serializable, SCLicense> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the s c licenses.
	*
	* @return the s c licenses
	*/
	public java.util.List<SCLicense> findAll();

	/**
	* Returns a range of all the s c licenses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c licenses
	* @param end the upper bound of the range of s c licenses (not inclusive)
	* @return the range of s c licenses
	*/
	public java.util.List<SCLicense> findAll(int start, int end);

	/**
	* Returns an ordered range of all the s c licenses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c licenses
	* @param end the upper bound of the range of s c licenses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of s c licenses
	*/
	public java.util.List<SCLicense> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SCLicense> orderByComparator);

	/**
	* Removes all the s c licenses from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of s c licenses.
	*
	* @return the number of s c licenses
	*/
	public int countAll();

	/**
	* Returns the primaryKeys of s c product entries associated with the s c license.
	*
	* @param pk the primary key of the s c license
	* @return long[] of the primaryKeys of s c product entries associated with the s c license
	*/
	public long[] getSCProductEntryPrimaryKeys(long pk);

	/**
	* Returns all the s c product entries associated with the s c license.
	*
	* @param pk the primary key of the s c license
	* @return the s c product entries associated with the s c license
	*/
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCProductEntries(
		long pk);

	/**
	* Returns a range of all the s c product entries associated with the s c license.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the s c license
	* @param start the lower bound of the range of s c licenses
	* @param end the upper bound of the range of s c licenses (not inclusive)
	* @return the range of s c product entries associated with the s c license
	*/
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCProductEntries(
		long pk, int start, int end);

	/**
	* Returns an ordered range of all the s c product entries associated with the s c license.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the s c license
	* @param start the lower bound of the range of s c licenses
	* @param end the upper bound of the range of s c licenses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of s c product entries associated with the s c license
	*/
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCProductEntries(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.softwarecatalog.model.SCProductEntry> orderByComparator);

	/**
	* Returns the number of s c product entries associated with the s c license.
	*
	* @param pk the primary key of the s c license
	* @return the number of s c product entries associated with the s c license
	*/
	public int getSCProductEntriesSize(long pk);

	/**
	* Returns <code>true</code> if the s c product entry is associated with the s c license.
	*
	* @param pk the primary key of the s c license
	* @param scProductEntryPK the primary key of the s c product entry
	* @return <code>true</code> if the s c product entry is associated with the s c license; <code>false</code> otherwise
	*/
	public boolean containsSCProductEntry(long pk, long scProductEntryPK);

	/**
	* Returns <code>true</code> if the s c license has any s c product entries associated with it.
	*
	* @param pk the primary key of the s c license to check for associations with s c product entries
	* @return <code>true</code> if the s c license has any s c product entries associated with it; <code>false</code> otherwise
	*/
	public boolean containsSCProductEntries(long pk);

	/**
	* Adds an association between the s c license and the s c product entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c license
	* @param scProductEntryPK the primary key of the s c product entry
	*/
	public void addSCProductEntry(long pk, long scProductEntryPK);

	/**
	* Adds an association between the s c license and the s c product entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c license
	* @param scProductEntry the s c product entry
	*/
	public void addSCProductEntry(long pk,
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry);

	/**
	* Adds an association between the s c license and the s c product entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c license
	* @param scProductEntryPKs the primary keys of the s c product entries
	*/
	public void addSCProductEntries(long pk, long[] scProductEntryPKs);

	/**
	* Adds an association between the s c license and the s c product entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c license
	* @param scProductEntries the s c product entries
	*/
	public void addSCProductEntries(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> scProductEntries);

	/**
	* Clears all associations between the s c license and its s c product entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c license to clear the associated s c product entries from
	*/
	public void clearSCProductEntries(long pk);

	/**
	* Removes the association between the s c license and the s c product entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c license
	* @param scProductEntryPK the primary key of the s c product entry
	*/
	public void removeSCProductEntry(long pk, long scProductEntryPK);

	/**
	* Removes the association between the s c license and the s c product entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c license
	* @param scProductEntry the s c product entry
	*/
	public void removeSCProductEntry(long pk,
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry);

	/**
	* Removes the association between the s c license and the s c product entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c license
	* @param scProductEntryPKs the primary keys of the s c product entries
	*/
	public void removeSCProductEntries(long pk, long[] scProductEntryPKs);

	/**
	* Removes the association between the s c license and the s c product entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c license
	* @param scProductEntries the s c product entries
	*/
	public void removeSCProductEntries(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> scProductEntries);

	/**
	* Sets the s c product entries associated with the s c license, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c license
	* @param scProductEntryPKs the primary keys of the s c product entries to be associated with the s c license
	*/
	public void setSCProductEntries(long pk, long[] scProductEntryPKs);

	/**
	* Sets the s c product entries associated with the s c license, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c license
	* @param scProductEntries the s c product entries to be associated with the s c license
	*/
	public void setSCProductEntries(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> scProductEntries);
}