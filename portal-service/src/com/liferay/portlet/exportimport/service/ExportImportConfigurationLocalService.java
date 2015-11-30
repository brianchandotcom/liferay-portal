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

package com.liferay.portlet.exportimport.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.service.BaseLocalService;
import com.liferay.portal.service.PersistedModelLocalService;

/**
 * Provides the local service interface for ExportImportConfiguration. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see ExportImportConfigurationLocalServiceUtil
 * @see com.liferay.portlet.exportimport.service.base.ExportImportConfigurationLocalServiceBaseImpl
 * @see com.liferay.portlet.exportimport.service.impl.ExportImportConfigurationLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface ExportImportConfigurationLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ExportImportConfigurationLocalServiceUtil} to access the export import configuration local service. Add custom service methods to {@link com.liferay.portlet.exportimport.service.impl.ExportImportConfigurationLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public com.liferay.portlet.exportimport.model.ExportImportConfiguration addDraftExportImportConfiguration(
		long userId, java.lang.String name, int type,
		java.util.Map<java.lang.String, java.io.Serializable> settingsMap)
		throws PortalException;

	public com.liferay.portlet.exportimport.model.ExportImportConfiguration addDraftExportImportConfiguration(
		long userId, int type,
		java.util.Map<java.lang.String, java.io.Serializable> settingsMap)
		throws PortalException;

	/**
	* Adds the export import configuration to the database. Also notifies the appropriate model listeners.
	*
	* @param exportImportConfiguration the export import configuration
	* @return the export import configuration that was added
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.exportimport.model.ExportImportConfiguration addExportImportConfiguration(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration);

	public com.liferay.portlet.exportimport.model.ExportImportConfiguration addExportImportConfiguration(
		long userId, long groupId, java.lang.String name,
		java.lang.String description, int type,
		java.util.Map<java.lang.String, java.io.Serializable> settingsMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.exportimport.model.ExportImportConfiguration addExportImportConfiguration(
		long userId, long groupId, java.lang.String name,
		java.lang.String description, int type,
		java.util.Map<java.lang.String, java.io.Serializable> settingsMap,
		int status, com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	/**
	* Creates a new export import configuration with the primary key. Does not add the export import configuration to the database.
	*
	* @param exportImportConfigurationId the primary key for the new export import configuration
	* @return the new export import configuration
	*/
	public com.liferay.portlet.exportimport.model.ExportImportConfiguration createExportImportConfiguration(
		long exportImportConfigurationId);

	/**
	* Deletes the export import configuration from the database. Also notifies the appropriate model listeners.
	*
	* @param exportImportConfiguration the export import configuration
	* @return the export import configuration that was removed
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	@com.liferay.portal.kernel.systemevent.SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public com.liferay.portlet.exportimport.model.ExportImportConfiguration deleteExportImportConfiguration(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration);

	/**
	* Deletes the export import configuration with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param exportImportConfigurationId the primary key of the export import configuration
	* @return the export import configuration that was removed
	* @throws PortalException if a export import configuration with the primary key could not be found
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.exportimport.model.ExportImportConfiguration deleteExportImportConfiguration(
		long exportImportConfigurationId) throws PortalException;

	public void deleteExportImportConfigurations(long groupId);

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.exportimport.model.impl.ExportImportConfigurationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.exportimport.model.impl.ExportImportConfigurationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public com.liferay.portlet.exportimport.model.ExportImportConfiguration fetchExportImportConfiguration(
		long exportImportConfigurationId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the export import configuration with the primary key.
	*
	* @param exportImportConfigurationId the primary key of the export import configuration
	* @return the export import configuration
	* @throws PortalException if a export import configuration with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.exportimport.model.ExportImportConfiguration getExportImportConfiguration(
		long exportImportConfigurationId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.exportimport.model.ExportImportConfiguration> getExportImportConfigurations(
		long groupId, int type);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.exportimport.model.ExportImportConfiguration> getExportImportConfigurations(
		long groupId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.exportimport.model.ExportImportConfiguration> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.exportimport.model.ExportImportConfiguration> getExportImportConfigurations(
		com.liferay.portal.kernel.search.Hits hits) throws PortalException;

	/**
	* Returns a range of all the export import configurations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.exportimport.model.impl.ExportImportConfigurationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of export import configurations
	* @param end the upper bound of the range of export import configurations (not inclusive)
	* @return the range of export import configurations
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.exportimport.model.ExportImportConfiguration> getExportImportConfigurations(
		int start, int end);

	/**
	* Returns the number of export import configurations.
	*
	* @return the number of export import configurations
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getExportImportConfigurationsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getExportImportConfigurationsCount(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getExportImportConfigurationsCount(long groupId, int type);

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

	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.exportimport.model.ExportImportConfiguration moveExportImportConfigurationToTrash(
		long userId, long exportImportConfigurationId)
		throws PortalException;

	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.exportimport.model.ExportImportConfiguration restoreExportImportConfigurationFromTrash(
		long userId, long exportImportConfigurationId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.portlet.exportimport.model.ExportImportConfiguration> searchExportImportConfigurations(
		long companyId, long groupId, int type, java.lang.String keywords,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.portlet.exportimport.model.ExportImportConfiguration> searchExportImportConfigurations(
		long companyId, long groupId, int type, java.lang.String name,
		java.lang.String description, boolean andSearch, int start, int end,
		com.liferay.portal.kernel.search.Sort sort) throws PortalException;

	/**
	* Updates the export import configuration in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param exportImportConfiguration the export import configuration
	* @return the export import configuration that was updated
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.exportimport.model.ExportImportConfiguration updateExportImportConfiguration(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration);

	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.exportimport.model.ExportImportConfiguration updateExportImportConfiguration(
		long userId, long exportImportConfigurationId, java.lang.String name,
		java.lang.String description,
		java.util.Map<java.lang.String, java.io.Serializable> settingsMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.exportimport.model.ExportImportConfiguration updateStatus(
		long userId, long exportImportConfigurationId, int status)
		throws PortalException;
}