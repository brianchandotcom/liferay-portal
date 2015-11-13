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

package com.liferay.dynamic.data.mapping.service;

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
 * Provides the local service interface for DDMDataProvider. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProviderLocalServiceUtil
 * @see com.liferay.dynamic.data.mapping.service.base.DDMDataProviderLocalServiceBaseImpl
 * @see com.liferay.dynamic.data.mapping.service.impl.DDMDataProviderLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DDMDataProviderLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMDataProviderLocalServiceUtil} to access the d d m data provider local service. Add custom service methods to {@link com.liferay.dynamic.data.mapping.service.impl.DDMDataProviderLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the d d m data provider to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmDataProvider the d d m data provider
	* @return the d d m data provider that was added
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.dynamic.data.mapping.model.DDMDataProvider addDDMDataProvider(
		com.liferay.dynamic.data.mapping.model.DDMDataProvider ddmDataProvider);

	/**
	* Creates a new d d m data provider with the primary key. Does not add the d d m data provider to the database.
	*
	* @param dataProviderId the primary key for the new d d m data provider
	* @return the new d d m data provider
	*/
	public com.liferay.dynamic.data.mapping.model.DDMDataProvider createDDMDataProvider(
		long dataProviderId);

	/**
	* Deletes the d d m data provider with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param dataProviderId the primary key of the d d m data provider
	* @return the d d m data provider that was removed
	* @throws PortalException if a d d m data provider with the primary key could not be found
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.dynamic.data.mapping.model.DDMDataProvider deleteDDMDataProvider(
		long dataProviderId) throws PortalException;

	/**
	* Deletes the d d m data provider from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmDataProvider the d d m data provider
	* @return the d d m data provider that was removed
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.dynamic.data.mapping.model.DDMDataProvider deleteDDMDataProvider(
		com.liferay.dynamic.data.mapping.model.DDMDataProvider ddmDataProvider);

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws PortalException;

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public com.liferay.dynamic.data.mapping.model.DDMDataProvider fetchDDMDataProvider(
		long dataProviderId);

	/**
	* Returns the d d m data provider matching the UUID and group.
	*
	* @param uuid the d d m data provider's UUID
	* @param groupId the primary key of the group
	* @return the matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.dynamic.data.mapping.model.DDMDataProvider fetchDDMDataProviderByUuidAndGroupId(
		java.lang.String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the d d m data provider with the primary key.
	*
	* @param dataProviderId the primary key of the d d m data provider
	* @return the d d m data provider
	* @throws PortalException if a d d m data provider with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.dynamic.data.mapping.model.DDMDataProvider getDDMDataProvider(
		long dataProviderId) throws PortalException;

	/**
	* Returns the d d m data provider matching the UUID and group.
	*
	* @param uuid the d d m data provider's UUID
	* @param groupId the primary key of the group
	* @return the matching d d m data provider
	* @throws PortalException if a matching d d m data provider could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.dynamic.data.mapping.model.DDMDataProvider getDDMDataProviderByUuidAndGroupId(
		java.lang.String uuid, long groupId) throws PortalException;

	/**
	* Returns a range of all the d d m data providers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @return the range of d d m data providers
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProvider> getDDMDataProviders(
		int start, int end);

	/**
	* Returns all the d d m data providers matching the UUID and company.
	*
	* @param uuid the UUID of the d d m data providers
	* @param companyId the primary key of the company
	* @return the matching d d m data providers, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProvider> getDDMDataProvidersByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of d d m data providers matching the UUID and company.
	*
	* @param uuid the UUID of the d d m data providers
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching d d m data providers, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProvider> getDDMDataProvidersByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMDataProvider> orderByComparator);

	/**
	* Returns the number of d d m data providers.
	*
	* @return the number of d d m data providers
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDDMDataProvidersCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext);

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

	/**
	* Updates the d d m data provider in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddmDataProvider the d d m data provider
	* @return the d d m data provider that was updated
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.dynamic.data.mapping.model.DDMDataProvider updateDDMDataProvider(
		com.liferay.dynamic.data.mapping.model.DDMDataProvider ddmDataProvider);
}