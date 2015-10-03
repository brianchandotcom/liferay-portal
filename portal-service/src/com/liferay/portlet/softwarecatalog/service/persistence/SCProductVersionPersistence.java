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

import com.liferay.portlet.softwarecatalog.model.SCProductVersion;

/**
 * The persistence interface for the s c product version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.softwarecatalog.service.persistence.impl.SCProductVersionPersistenceImpl
 * @see SCProductVersionUtil
 * @generated
 */
@ProviderType
public interface SCProductVersionPersistence extends BasePersistence<SCProductVersion> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SCProductVersionUtil} to access the s c product version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the s c product versions where productEntryId = &#63;.
	*
	* @param productEntryId the product entry ID
	* @return the matching s c product versions
	*/
	public java.util.List<SCProductVersion> findByProductEntryId(
		long productEntryId);

	/**
	* Returns a range of all the s c product versions where productEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param productEntryId the product entry ID
	* @param start the lower bound of the range of s c product versions
	* @param end the upper bound of the range of s c product versions (not inclusive)
	* @return the range of matching s c product versions
	*/
	public java.util.List<SCProductVersion> findByProductEntryId(
		long productEntryId, int start, int end);

	/**
	* Returns an ordered range of all the s c product versions where productEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param productEntryId the product entry ID
	* @param start the lower bound of the range of s c product versions
	* @param end the upper bound of the range of s c product versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c product versions
	*/
	public java.util.List<SCProductVersion> findByProductEntryId(
		long productEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductVersion> orderByComparator);

	/**
	* Returns an ordered range of all the s c product versions where productEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param productEntryId the product entry ID
	* @param start the lower bound of the range of s c product versions
	* @param end the upper bound of the range of s c product versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to use the finder cache
	* @return the ordered range of matching s c product versions
	*/
	public java.util.List<SCProductVersion> findByProductEntryId(
		long productEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first s c product version in the ordered set where productEntryId = &#63;.
	*
	* @param productEntryId the product entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c product version
	* @throws NoSuchProductVersionException if a matching s c product version could not be found
	*/
	public SCProductVersion findByProductEntryId_First(long productEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductVersion> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductVersionException;

	/**
	* Returns the first s c product version in the ordered set where productEntryId = &#63;.
	*
	* @param productEntryId the product entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c product version, or <code>null</code> if a matching s c product version could not be found
	*/
	public SCProductVersion fetchByProductEntryId_First(long productEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductVersion> orderByComparator);

	/**
	* Returns the last s c product version in the ordered set where productEntryId = &#63;.
	*
	* @param productEntryId the product entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c product version
	* @throws NoSuchProductVersionException if a matching s c product version could not be found
	*/
	public SCProductVersion findByProductEntryId_Last(long productEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductVersion> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductVersionException;

	/**
	* Returns the last s c product version in the ordered set where productEntryId = &#63;.
	*
	* @param productEntryId the product entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c product version, or <code>null</code> if a matching s c product version could not be found
	*/
	public SCProductVersion fetchByProductEntryId_Last(long productEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductVersion> orderByComparator);

	/**
	* Returns the s c product versions before and after the current s c product version in the ordered set where productEntryId = &#63;.
	*
	* @param productVersionId the primary key of the current s c product version
	* @param productEntryId the product entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c product version
	* @throws NoSuchProductVersionException if a s c product version with the primary key could not be found
	*/
	public SCProductVersion[] findByProductEntryId_PrevAndNext(
		long productVersionId, long productEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductVersion> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductVersionException;

	/**
	* Removes all the s c product versions where productEntryId = &#63; from the database.
	*
	* @param productEntryId the product entry ID
	*/
	public void removeByProductEntryId(long productEntryId);

	/**
	* Returns the number of s c product versions where productEntryId = &#63;.
	*
	* @param productEntryId the product entry ID
	* @return the number of matching s c product versions
	*/
	public int countByProductEntryId(long productEntryId);

	/**
	* Returns the s c product version where directDownloadURL = &#63; or throws a {@link NoSuchProductVersionException} if it could not be found.
	*
	* @param directDownloadURL the direct download u r l
	* @return the matching s c product version
	* @throws NoSuchProductVersionException if a matching s c product version could not be found
	*/
	public SCProductVersion findByDirectDownloadURL(
		java.lang.String directDownloadURL)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductVersionException;

	/**
	* Returns the s c product version where directDownloadURL = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param directDownloadURL the direct download u r l
	* @return the matching s c product version, or <code>null</code> if a matching s c product version could not be found
	*/
	public SCProductVersion fetchByDirectDownloadURL(
		java.lang.String directDownloadURL);

	/**
	* Returns the s c product version where directDownloadURL = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param directDownloadURL the direct download u r l
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching s c product version, or <code>null</code> if a matching s c product version could not be found
	*/
	public SCProductVersion fetchByDirectDownloadURL(
		java.lang.String directDownloadURL, boolean retrieveFromCache);

	/**
	* Removes the s c product version where directDownloadURL = &#63; from the database.
	*
	* @param directDownloadURL the direct download u r l
	* @return the s c product version that was removed
	*/
	public SCProductVersion removeByDirectDownloadURL(
		java.lang.String directDownloadURL)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductVersionException;

	/**
	* Returns the number of s c product versions where directDownloadURL = &#63;.
	*
	* @param directDownloadURL the direct download u r l
	* @return the number of matching s c product versions
	*/
	public int countByDirectDownloadURL(java.lang.String directDownloadURL);

	/**
	* Caches the s c product version in the entity cache if it is enabled.
	*
	* @param scProductVersion the s c product version
	*/
	public void cacheResult(SCProductVersion scProductVersion);

	/**
	* Caches the s c product versions in the entity cache if it is enabled.
	*
	* @param scProductVersions the s c product versions
	*/
	public void cacheResult(java.util.List<SCProductVersion> scProductVersions);

	/**
	* Creates a new s c product version with the primary key. Does not add the s c product version to the database.
	*
	* @param productVersionId the primary key for the new s c product version
	* @return the new s c product version
	*/
	public SCProductVersion create(long productVersionId);

	/**
	* Removes the s c product version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param productVersionId the primary key of the s c product version
	* @return the s c product version that was removed
	* @throws NoSuchProductVersionException if a s c product version with the primary key could not be found
	*/
	public SCProductVersion remove(long productVersionId)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductVersionException;

	public SCProductVersion updateImpl(SCProductVersion scProductVersion);

	/**
	* Returns the s c product version with the primary key or throws a {@link NoSuchProductVersionException} if it could not be found.
	*
	* @param productVersionId the primary key of the s c product version
	* @return the s c product version
	* @throws NoSuchProductVersionException if a s c product version with the primary key could not be found
	*/
	public SCProductVersion findByPrimaryKey(long productVersionId)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductVersionException;

	/**
	* Returns the s c product version with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param productVersionId the primary key of the s c product version
	* @return the s c product version, or <code>null</code> if a s c product version with the primary key could not be found
	*/
	public SCProductVersion fetchByPrimaryKey(long productVersionId);

	@Override
	public java.util.Map<java.io.Serializable, SCProductVersion> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the s c product versions.
	*
	* @return the s c product versions
	*/
	public java.util.List<SCProductVersion> findAll();

	/**
	* Returns a range of all the s c product versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c product versions
	* @param end the upper bound of the range of s c product versions (not inclusive)
	* @return the range of s c product versions
	*/
	public java.util.List<SCProductVersion> findAll(int start, int end);

	/**
	* Returns an ordered range of all the s c product versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c product versions
	* @param end the upper bound of the range of s c product versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of s c product versions
	*/
	public java.util.List<SCProductVersion> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductVersion> orderByComparator);

	/**
	* Returns an ordered range of all the s c product versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c product versions
	* @param end the upper bound of the range of s c product versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to use the finder cache
	* @return the ordered range of s c product versions
	*/
	public java.util.List<SCProductVersion> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the s c product versions from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of s c product versions.
	*
	* @return the number of s c product versions
	*/
	public int countAll();

	/**
	* Returns the primaryKeys of s c framework versions associated with the s c product version.
	*
	* @param pk the primary key of the s c product version
	* @return long[] of the primaryKeys of s c framework versions associated with the s c product version
	*/
	public long[] getSCFrameworkVersionPrimaryKeys(long pk);

	/**
	* Returns all the s c framework versions associated with the s c product version.
	*
	* @param pk the primary key of the s c product version
	* @return the s c framework versions associated with the s c product version
	*/
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		long pk);

	/**
	* Returns a range of all the s c framework versions associated with the s c product version.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the s c product version
	* @param start the lower bound of the range of s c product versions
	* @param end the upper bound of the range of s c product versions (not inclusive)
	* @return the range of s c framework versions associated with the s c product version
	*/
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		long pk, int start, int end);

	/**
	* Returns an ordered range of all the s c framework versions associated with the s c product version.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the s c product version
	* @param start the lower bound of the range of s c product versions
	* @param end the upper bound of the range of s c product versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of s c framework versions associated with the s c product version
	*/
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> orderByComparator);

	/**
	* Returns the number of s c framework versions associated with the s c product version.
	*
	* @param pk the primary key of the s c product version
	* @return the number of s c framework versions associated with the s c product version
	*/
	public int getSCFrameworkVersionsSize(long pk);

	/**
	* Returns <code>true</code> if the s c framework version is associated with the s c product version.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersionPK the primary key of the s c framework version
	* @return <code>true</code> if the s c framework version is associated with the s c product version; <code>false</code> otherwise
	*/
	public boolean containsSCFrameworkVersion(long pk, long scFrameworkVersionPK);

	/**
	* Returns <code>true</code> if the s c product version has any s c framework versions associated with it.
	*
	* @param pk the primary key of the s c product version to check for associations with s c framework versions
	* @return <code>true</code> if the s c product version has any s c framework versions associated with it; <code>false</code> otherwise
	*/
	public boolean containsSCFrameworkVersions(long pk);

	/**
	* Adds an association between the s c product version and the s c framework version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersionPK the primary key of the s c framework version
	*/
	public void addSCFrameworkVersion(long pk, long scFrameworkVersionPK);

	/**
	* Adds an association between the s c product version and the s c framework version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersion the s c framework version
	*/
	public void addSCFrameworkVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion);

	/**
	* Adds an association between the s c product version and the s c framework versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersionPKs the primary keys of the s c framework versions
	*/
	public void addSCFrameworkVersions(long pk, long[] scFrameworkVersionPKs);

	/**
	* Adds an association between the s c product version and the s c framework versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersions the s c framework versions
	*/
	public void addSCFrameworkVersions(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions);

	/**
	* Clears all associations between the s c product version and its s c framework versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version to clear the associated s c framework versions from
	*/
	public void clearSCFrameworkVersions(long pk);

	/**
	* Removes the association between the s c product version and the s c framework version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersionPK the primary key of the s c framework version
	*/
	public void removeSCFrameworkVersion(long pk, long scFrameworkVersionPK);

	/**
	* Removes the association between the s c product version and the s c framework version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersion the s c framework version
	*/
	public void removeSCFrameworkVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion);

	/**
	* Removes the association between the s c product version and the s c framework versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersionPKs the primary keys of the s c framework versions
	*/
	public void removeSCFrameworkVersions(long pk, long[] scFrameworkVersionPKs);

	/**
	* Removes the association between the s c product version and the s c framework versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersions the s c framework versions
	*/
	public void removeSCFrameworkVersions(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions);

	/**
	* Sets the s c framework versions associated with the s c product version, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersionPKs the primary keys of the s c framework versions to be associated with the s c product version
	*/
	public void setSCFrameworkVersions(long pk, long[] scFrameworkVersionPKs);

	/**
	* Sets the s c framework versions associated with the s c product version, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersions the s c framework versions to be associated with the s c product version
	*/
	public void setSCFrameworkVersions(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions);
}