/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.softwarecatalog.model.SCProductVersion;

/**
 * The persistence interface for the s c product version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SCProductVersionPersistenceImpl
 * @see SCProductVersionUtil
 * @generated
 */
public interface SCProductVersionPersistence extends BasePersistence<SCProductVersion> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SCProductVersionUtil} to access the s c product version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the s c product version in the entity cache if it is enabled.
	*
	* @param scProductVersion the s c product version
	*/
	public void cacheResult(
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion);

	/**
	* Caches the s c product versions in the entity cache if it is enabled.
	*
	* @param scProductVersions the s c product versions
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> scProductVersions);

	/**
	* Creates a new s c product version with the primary key. Does not add the s c product version to the database.
	*
	* @param productVersionId the primary key for the new s c product version
	* @return the new s c product version
	*/
	public com.liferay.portlet.softwarecatalog.model.SCProductVersion create(
		long productVersionId);

	/**
	* Removes the s c product version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param productVersionId the primary key of the s c product version
	* @return the s c product version that was removed
	* @throws com.liferay.portlet.softwarecatalog.NoSuchProductVersionException if a s c product version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.softwarecatalog.model.SCProductVersion remove(
		long productVersionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductVersionException;

	public com.liferay.portlet.softwarecatalog.model.SCProductVersion updateImpl(
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the s c product version with the primary key or throws a {@link com.liferay.portlet.softwarecatalog.NoSuchProductVersionException} if it could not be found.
	*
	* @param productVersionId the primary key of the s c product version
	* @return the s c product version
	* @throws com.liferay.portlet.softwarecatalog.NoSuchProductVersionException if a s c product version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.softwarecatalog.model.SCProductVersion findByPrimaryKey(
		long productVersionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductVersionException;

	/**
	* Returns the s c product version with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param productVersionId the primary key of the s c product version
	* @return the s c product version, or <code>null</code> if a s c product version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.softwarecatalog.model.SCProductVersion fetchByPrimaryKey(
		long productVersionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the s c product versions where productEntryId = &#63;.
	*
	* @param productEntryId the product entry ID
	* @return the matching s c product versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> findByProductEntryId(
		long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the s c product versions where productEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param productEntryId the product entry ID
	* @param start the lower bound of the range of s c product versions
	* @param end the upper bound of the range of s c product versions (not inclusive)
	* @return the range of matching s c product versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> findByProductEntryId(
		long productEntryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the s c product versions where productEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param productEntryId the product entry ID
	* @param start the lower bound of the range of s c product versions
	* @param end the upper bound of the range of s c product versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c product versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> findByProductEntryId(
		long productEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first s c product version in the ordered set where productEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param productEntryId the product entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c product version
	* @throws com.liferay.portlet.softwarecatalog.NoSuchProductVersionException if a matching s c product version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.softwarecatalog.model.SCProductVersion findByProductEntryId_First(
		long productEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductVersionException;

	/**
	* Returns the last s c product version in the ordered set where productEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param productEntryId the product entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c product version
	* @throws com.liferay.portlet.softwarecatalog.NoSuchProductVersionException if a matching s c product version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.softwarecatalog.model.SCProductVersion findByProductEntryId_Last(
		long productEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductVersionException;

	/**
	* Returns the s c product versions before and after the current s c product version in the ordered set where productEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param productVersionId the primary key of the current s c product version
	* @param productEntryId the product entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c product version
	* @throws com.liferay.portlet.softwarecatalog.NoSuchProductVersionException if a s c product version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.softwarecatalog.model.SCProductVersion[] findByProductEntryId_PrevAndNext(
		long productVersionId, long productEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductVersionException;

	/**
	* Returns the s c product version where directDownloadURL = &#63; or throws a {@link com.liferay.portlet.softwarecatalog.NoSuchProductVersionException} if it could not be found.
	*
	* @param directDownloadURL the direct download u r l
	* @return the matching s c product version
	* @throws com.liferay.portlet.softwarecatalog.NoSuchProductVersionException if a matching s c product version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.softwarecatalog.model.SCProductVersion findByDirectDownloadURL(
		java.lang.String directDownloadURL)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductVersionException;

	/**
	* Returns the s c product version where directDownloadURL = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param directDownloadURL the direct download u r l
	* @return the matching s c product version, or <code>null</code> if a matching s c product version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.softwarecatalog.model.SCProductVersion fetchByDirectDownloadURL(
		java.lang.String directDownloadURL)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the s c product version where directDownloadURL = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param directDownloadURL the direct download u r l
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching s c product version, or <code>null</code> if a matching s c product version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.softwarecatalog.model.SCProductVersion fetchByDirectDownloadURL(
		java.lang.String directDownloadURL, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the s c product versions.
	*
	* @return the s c product versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the s c product versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of s c product versions
	* @param end the upper bound of the range of s c product versions (not inclusive)
	* @return the range of s c product versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the s c product versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of s c product versions
	* @param end the upper bound of the range of s c product versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of s c product versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the s c product versions where productEntryId = &#63; from the database.
	*
	* @param productEntryId the product entry ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByProductEntryId(long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the s c product version where directDownloadURL = &#63; from the database.
	*
	* @param directDownloadURL the direct download u r l
	* @return the s c product version that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.softwarecatalog.model.SCProductVersion removeByDirectDownloadURL(
		java.lang.String directDownloadURL)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductVersionException;

	/**
	* Removes all the s c product versions from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of s c product versions where productEntryId = &#63;.
	*
	* @param productEntryId the product entry ID
	* @return the number of matching s c product versions
	* @throws SystemException if a system exception occurred
	*/
	public int countByProductEntryId(long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of s c product versions where directDownloadURL = &#63;.
	*
	* @param directDownloadURL the direct download u r l
	* @return the number of matching s c product versions
	* @throws SystemException if a system exception occurred
	*/
	public int countByDirectDownloadURL(java.lang.String directDownloadURL)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of s c product versions.
	*
	* @return the number of s c product versions
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the s c framework versions associated with the s c product version.
	*
	* @param pk the primary key of the s c product version
	* @return the s c framework versions associated with the s c product version
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		long pk) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the s c framework versions associated with the s c product version.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the s c product version
	* @param start the lower bound of the range of s c product versions
	* @param end the upper bound of the range of s c product versions (not inclusive)
	* @return the range of s c framework versions associated with the s c product version
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the s c framework versions associated with the s c product version.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the s c product version
	* @param start the lower bound of the range of s c product versions
	* @param end the upper bound of the range of s c product versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of s c framework versions associated with the s c product version
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of s c framework versions associated with the s c product version.
	*
	* @param pk the primary key of the s c product version
	* @return the number of s c framework versions associated with the s c product version
	* @throws SystemException if a system exception occurred
	*/
	public int getSCFrameworkVersionsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns <code>true</code> if the s c framework version is associated with the s c product version.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersionPK the primary key of the s c framework version
	* @return <code>true</code> if the s c framework version is associated with the s c product version; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsSCFrameworkVersion(long pk, long scFrameworkVersionPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns <code>true</code> if the s c product version has any s c framework versions associated with it.
	*
	* @param pk the primary key of the s c product version to check for associations with s c framework versions
	* @return <code>true</code> if the s c product version has any s c framework versions associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsSCFrameworkVersions(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the s c product version and the s c framework version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersionPK the primary key of the s c framework version
	* @throws SystemException if a system exception occurred
	*/
	public void addSCFrameworkVersion(long pk, long scFrameworkVersionPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the s c product version and the s c framework version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersion the s c framework version
	* @throws SystemException if a system exception occurred
	*/
	public void addSCFrameworkVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the s c product version and the s c framework versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersionPKs the primary keys of the s c framework versions
	* @throws SystemException if a system exception occurred
	*/
	public void addSCFrameworkVersions(long pk, long[] scFrameworkVersionPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the s c product version and the s c framework versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersions the s c framework versions
	* @throws SystemException if a system exception occurred
	*/
	public void addSCFrameworkVersions(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Clears all associations between the s c product version and its s c framework versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version to clear the associated s c framework versions from
	* @throws SystemException if a system exception occurred
	*/
	public void clearSCFrameworkVersions(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the s c product version and the s c framework version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersionPK the primary key of the s c framework version
	* @throws SystemException if a system exception occurred
	*/
	public void removeSCFrameworkVersion(long pk, long scFrameworkVersionPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the s c product version and the s c framework version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersion the s c framework version
	* @throws SystemException if a system exception occurred
	*/
	public void removeSCFrameworkVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the s c product version and the s c framework versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersionPKs the primary keys of the s c framework versions
	* @throws SystemException if a system exception occurred
	*/
	public void removeSCFrameworkVersions(long pk, long[] scFrameworkVersionPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the s c product version and the s c framework versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersions the s c framework versions
	* @throws SystemException if a system exception occurred
	*/
	public void removeSCFrameworkVersions(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the s c framework versions associated with the s c product version, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersionPKs the primary keys of the s c framework versions to be associated with the s c product version
	* @throws SystemException if a system exception occurred
	*/
	public void setSCFrameworkVersions(long pk, long[] scFrameworkVersionPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the s c framework versions associated with the s c product version, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersions the s c framework versions to be associated with the s c product version
	* @throws SystemException if a system exception occurred
	*/
	public void setSCFrameworkVersions(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions)
		throws com.liferay.portal.kernel.exception.SystemException;
}