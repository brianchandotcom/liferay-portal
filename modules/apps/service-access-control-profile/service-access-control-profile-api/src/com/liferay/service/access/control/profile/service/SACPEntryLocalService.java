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

package com.liferay.service.access.control.profile.service;

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
 * Provides the local service interface for SACPEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see SACPEntryLocalServiceUtil
 * @see com.liferay.service.access.control.profile.service.base.SACPEntryLocalServiceBaseImpl
 * @see com.liferay.service.access.control.profile.service.impl.SACPEntryLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface SACPEntryLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SACPEntryLocalServiceUtil} to access the s a c p entry local service. Add custom service methods to {@link com.liferay.service.access.control.profile.service.impl.SACPEntryLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the s a c p entry to the database. Also notifies the appropriate model listeners.
	*
	* @param sacpEntry the s a c p entry
	* @return the s a c p entry that was added
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.service.access.control.profile.model.SACPEntry addSACPEntry(
		com.liferay.service.access.control.profile.model.SACPEntry sacpEntry);

	public com.liferay.service.access.control.profile.model.SACPEntry addSACPEntry(
		long userId, java.lang.String allowedServiceSignatures,
		boolean defaultSACPEntry, java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public void checkDefaultSACPEntry(long companyId) throws PortalException;

	/**
	* Creates a new s a c p entry with the primary key. Does not add the s a c p entry to the database.
	*
	* @param sacpEntryId the primary key for the new s a c p entry
	* @return the new s a c p entry
	*/
	public com.liferay.service.access.control.profile.model.SACPEntry createSACPEntry(
		long sacpEntryId);

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws PortalException;

	/**
	* Deletes the s a c p entry from the database. Also notifies the appropriate model listeners.
	*
	* @param sacpEntry the s a c p entry
	* @return the s a c p entry that was removed
	* @throws PortalException
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.service.access.control.profile.model.SACPEntry deleteSACPEntry(
		com.liferay.service.access.control.profile.model.SACPEntry sacpEntry)
		throws PortalException;

	/**
	* Deletes the s a c p entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param sacpEntryId the primary key of the s a c p entry
	* @return the s a c p entry that was removed
	* @throws PortalException if a s a c p entry with the primary key could not be found
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.service.access.control.profile.model.SACPEntry deleteSACPEntry(
		long sacpEntryId) throws PortalException;

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.control.profile.model.impl.SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.control.profile.model.impl.SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public com.liferay.service.access.control.profile.model.SACPEntry fetchSACPEntry(
		long sacpEntryId);

	/**
	* Returns the s a c p entry with the matching UUID and company.
	*
	* @param uuid the s a c p entry's UUID
	* @param companyId the primary key of the company
	* @return the matching s a c p entry, or <code>null</code> if a matching s a c p entry could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.service.access.control.profile.model.SACPEntry fetchSACPEntryByUuidAndCompanyId(
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
	public java.util.List<com.liferay.service.access.control.profile.model.SACPEntry> getCompanySACPEntries(
		long companyId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.service.access.control.profile.model.SACPEntry> getCompanySACPEntries(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.control.profile.model.SACPEntry> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCompanySACPEntriesCount(long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext);

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj) throws PortalException;

	/**
	* Returns a range of all the s a c p entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.control.profile.model.impl.SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @return the range of s a c p entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.service.access.control.profile.model.SACPEntry> getSACPEntries(
		int start, int end);

	/**
	* Returns the number of s a c p entries.
	*
	* @return the number of s a c p entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getSACPEntriesCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.service.access.control.profile.model.SACPEntry getSACPEntry(
		long companyId, java.lang.String name) throws PortalException;

	/**
	* Returns the s a c p entry with the primary key.
	*
	* @param sacpEntryId the primary key of the s a c p entry
	* @return the s a c p entry
	* @throws PortalException if a s a c p entry with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.service.access.control.profile.model.SACPEntry getSACPEntry(
		long sacpEntryId) throws PortalException;

	/**
	* Returns the s a c p entry with the matching UUID and company.
	*
	* @param uuid the s a c p entry's UUID
	* @param companyId the primary key of the company
	* @return the matching s a c p entry
	* @throws PortalException if a matching s a c p entry could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.service.access.control.profile.model.SACPEntry getSACPEntryByUuidAndCompanyId(
		java.lang.String uuid, long companyId) throws PortalException;

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	/**
	* Updates the s a c p entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param sacpEntry the s a c p entry
	* @return the s a c p entry that was updated
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.service.access.control.profile.model.SACPEntry updateSACPEntry(
		com.liferay.service.access.control.profile.model.SACPEntry sacpEntry);

	public com.liferay.service.access.control.profile.model.SACPEntry updateSACPEntry(
		long sacpEntryId, java.lang.String allowedServiceSignatures,
		java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;
}