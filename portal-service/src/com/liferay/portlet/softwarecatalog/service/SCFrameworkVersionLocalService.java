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

package com.liferay.portlet.softwarecatalog.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseLocalService;
import com.liferay.portal.service.PersistedModelLocalService;

/**
 * Provides the local service interface for SCFrameworkVersion. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see SCFrameworkVersionLocalServiceUtil
 * @see com.liferay.portlet.softwarecatalog.service.base.SCFrameworkVersionLocalServiceBaseImpl
 * @see com.liferay.portlet.softwarecatalog.service.impl.SCFrameworkVersionLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface SCFrameworkVersionLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SCFrameworkVersionLocalServiceUtil} to access the s c framework version local service. Add custom service methods to {@link com.liferay.portlet.softwarecatalog.service.impl.SCFrameworkVersionLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion addFrameworkVersion(
		long userId, java.lang.String name, java.lang.String url,
		boolean active, int priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public void addFrameworkVersionResources(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion frameworkVersion,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws PortalException;

	public void addFrameworkVersionResources(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion frameworkVersion,
		com.liferay.portal.service.permission.ModelPermissions modelPermissions)
		throws PortalException;

	public void addFrameworkVersionResources(long frameworkVersionId,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws PortalException;

	public void addFrameworkVersionResources(long frameworkVersionId,
		com.liferay.portal.service.permission.ModelPermissions modelPermissions)
		throws PortalException;

	/**
	* Adds the s c framework version to the database. Also notifies the appropriate model listeners.
	*
	* @param scFrameworkVersion the s c framework version
	* @return the s c framework version that was added
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion addSCFrameworkVersion(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion);

	public void addSCProductVersionSCFrameworkVersion(long productVersionId,
		long frameworkVersionId);

	public void addSCProductVersionSCFrameworkVersion(long productVersionId,
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion);

	public void addSCProductVersionSCFrameworkVersions(long productVersionId,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> SCFrameworkVersions);

	public void addSCProductVersionSCFrameworkVersions(long productVersionId,
		long[] frameworkVersionIds);

	public void clearSCProductVersionSCFrameworkVersions(long productVersionId);

	/**
	* Creates a new s c framework version with the primary key. Does not add the s c framework version to the database.
	*
	* @param frameworkVersionId the primary key for the new s c framework version
	* @return the new s c framework version
	*/
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion createSCFrameworkVersion(
		long frameworkVersionId);

	public void deleteFrameworkVersion(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion frameworkVersion);

	public void deleteFrameworkVersion(long frameworkVersionId)
		throws PortalException;

	public void deleteFrameworkVersions(long groupId);

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws PortalException;

	/**
	* Deletes the s c framework version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param frameworkVersionId the primary key of the s c framework version
	* @return the s c framework version that was removed
	* @throws PortalException if a s c framework version with the primary key could not be found
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion deleteSCFrameworkVersion(
		long frameworkVersionId) throws PortalException;

	/**
	* Deletes the s c framework version from the database. Also notifies the appropriate model listeners.
	*
	* @param scFrameworkVersion the s c framework version
	* @return the s c framework version that was removed
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion deleteSCFrameworkVersion(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion);

	public void deleteSCProductVersionSCFrameworkVersion(
		long productVersionId, long frameworkVersionId);

	public void deleteSCProductVersionSCFrameworkVersion(
		long productVersionId,
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion);

	public void deleteSCProductVersionSCFrameworkVersions(
		long productVersionId,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> SCFrameworkVersions);

	public void deleteSCProductVersionSCFrameworkVersions(
		long productVersionId, long[] frameworkVersionIds);

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion fetchSCFrameworkVersion(
		long frameworkVersionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion getFrameworkVersion(
		long frameworkVersionId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions(
		long groupId, boolean active);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions(
		long groupId, boolean active, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions(
		long groupId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFrameworkVersionsCount(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFrameworkVersionsCount(long groupId, boolean active);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getProductVersionFrameworkVersions(
		long productVersionId);

	/**
	* Returns the s c framework version with the primary key.
	*
	* @param frameworkVersionId the primary key of the s c framework version
	* @return the s c framework version
	* @throws PortalException if a s c framework version with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion getSCFrameworkVersion(
		long frameworkVersionId) throws PortalException;

	/**
	* Returns a range of all the s c framework versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @return the range of s c framework versions
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		int start, int end);

	/**
	* Returns the number of s c framework versions.
	*
	* @return the number of s c framework versions
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getSCFrameworkVersionsCount();

	/**
	* Returns the productVersionIds of the s c product versions associated with the s c framework version.
	*
	* @param frameworkVersionId the frameworkVersionId of the s c framework version
	* @return long[] the productVersionIds of s c product versions associated with the s c framework version
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getSCProductVersionPrimaryKeys(long frameworkVersionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCProductVersionSCFrameworkVersions(
		long productVersionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCProductVersionSCFrameworkVersions(
		long productVersionId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCProductVersionSCFrameworkVersions(
		long productVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getSCProductVersionSCFrameworkVersionsCount(
		long productVersionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasSCProductVersionSCFrameworkVersion(
		long productVersionId, long frameworkVersionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasSCProductVersionSCFrameworkVersions(long productVersionId);

	public void setSCProductVersionSCFrameworkVersions(long productVersionId,
		long[] frameworkVersionIds);

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion updateFrameworkVersion(
		long frameworkVersionId, java.lang.String name, java.lang.String url,
		boolean active, int priority) throws PortalException;

	/**
	* Updates the s c framework version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param scFrameworkVersion the s c framework version
	* @return the s c framework version that was updated
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion updateSCFrameworkVersion(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion);
}