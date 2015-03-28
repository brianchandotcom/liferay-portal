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

package com.liferay.portal.ac.profile.service;

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
 * Provides the local service interface for ServiceACProfile. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see ServiceACProfileLocalServiceUtil
 * @see com.liferay.portal.ac.profile.service.base.ServiceACProfileLocalServiceBaseImpl
 * @see com.liferay.portal.ac.profile.service.impl.ServiceACProfileLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface ServiceACProfileLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ServiceACProfileLocalServiceUtil} to access the service a c profile local service. Add custom service methods to {@link com.liferay.portal.ac.profile.service.impl.ServiceACProfileLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public com.liferay.portal.ac.profile.model.ServiceACProfile addServiceACProfile(
		long companyId, long userId, java.lang.String allowedServices,
		java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Adds the service a c profile to the database. Also notifies the appropriate model listeners.
	*
	* @param serviceACProfile the service a c profile
	* @return the service a c profile that was added
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portal.ac.profile.model.ServiceACProfile addServiceACProfile(
		com.liferay.portal.ac.profile.model.ServiceACProfile serviceACProfile);

	public void addServiceACProfileResources(
		com.liferay.portal.ac.profile.model.ServiceACProfile serviceACProfile,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void addServiceACProfileResources(
		com.liferay.portal.ac.profile.model.ServiceACProfile serviceACProfile,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Creates a new service a c profile with the primary key. Does not add the service a c profile to the database.
	*
	* @param serviceACProfileId the primary key for the new service a c profile
	* @return the new service a c profile
	*/
	public com.liferay.portal.ac.profile.model.ServiceACProfile createServiceACProfile(
		long serviceACProfileId);

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Deletes the service a c profile from the database. Also notifies the appropriate model listeners.
	*
	* @param serviceACProfile the service a c profile
	* @return the service a c profile that was removed
	* @throws PortalException
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portal.ac.profile.model.ServiceACProfile deleteServiceACProfile(
		com.liferay.portal.ac.profile.model.ServiceACProfile serviceACProfile)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Deletes the service a c profile with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param serviceACProfileId the primary key of the service a c profile
	* @return the service a c profile that was removed
	* @throws PortalException if a service a c profile with the primary key could not be found
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portal.ac.profile.model.ServiceACProfile deleteServiceACProfile(
		long serviceACProfileId)
		throws com.liferay.portal.kernel.exception.PortalException;

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public com.liferay.portal.ac.profile.model.ServiceACProfile fetchServiceACProfile(
		long serviceACProfileId);

	/**
	* Returns the service a c profile with the matching UUID and company.
	*
	* @param uuid the service a c profile's UUID
	* @param companyId the primary key of the company
	* @return the matching service a c profile, or <code>null</code> if a matching service a c profile could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.ac.profile.model.ServiceACProfile fetchServiceACProfileByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> getCompanyServiceACProfiles(
		long companyId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> getCompanyServiceACProfiles(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCompanyServiceACProfilesCount(long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portal.kernel.lar.PortletDataContext portletDataContext);

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.ac.profile.model.ServiceACProfile getServiceACProfile(
		long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Returns the service a c profile with the primary key.
	*
	* @param serviceACProfileId the primary key of the service a c profile
	* @return the service a c profile
	* @throws PortalException if a service a c profile with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.ac.profile.model.ServiceACProfile getServiceACProfile(
		long serviceACProfileId)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Returns the service a c profile with the matching UUID and company.
	*
	* @param uuid the service a c profile's UUID
	* @param companyId the primary key of the company
	* @return the matching service a c profile
	* @throws PortalException if a matching service a c profile could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.ac.profile.model.ServiceACProfile getServiceACProfileByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Returns a range of all the service a c profiles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of service a c profiles
	* @param end the upper bound of the range of service a c profiles (not inclusive)
	* @return the range of service a c profiles
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> getServiceACProfiles(
		int start, int end);

	/**
	* Returns the number of service a c profiles.
	*
	* @return the number of service a c profiles
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getServiceACProfilesCount();

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	/**
	* Updates the service a c profile in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param serviceACProfile the service a c profile
	* @return the service a c profile that was updated
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portal.ac.profile.model.ServiceACProfile updateServiceACProfile(
		com.liferay.portal.ac.profile.model.ServiceACProfile serviceACProfile);

	public com.liferay.portal.ac.profile.model.ServiceACProfile updateServiceACProfile(
		long serviceACProfileId, java.lang.String allowedServices,
		java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void updateServiceACProfileResources(
		com.liferay.portal.ac.profile.model.ServiceACProfile serviceACProfile,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException;
}