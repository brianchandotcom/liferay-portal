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

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;

import com.liferay.exportimport.kernel.lar.PortletDataContext;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the local service interface for DDMFormInstance. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceLocalServiceUtil
 * @see com.liferay.dynamic.data.mapping.service.base.DDMFormInstanceLocalServiceBaseImpl
 * @see com.liferay.dynamic.data.mapping.service.impl.DDMFormInstanceLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DDMFormInstanceLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMFormInstanceLocalServiceUtil} to access the ddm form instance local service. Add custom service methods to {@link com.liferay.dynamic.data.mapping.service.impl.DDMFormInstanceLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the ddm form instance to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmFormInstance the ddm form instance
	* @return the ddm form instance that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public DDMFormInstance addDDMFormInstance(DDMFormInstance ddmFormInstance);

	public DDMFormInstance addFormInstance(long userId, long groupId,
		long ddmStructureId, Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap,
		DDMFormValues settingsDDMFormValues, ServiceContext serviceContext)
		throws PortalException;

	public DDMFormInstance addFormInstance(long userId, long groupId,
		long ddmStructureId, Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap,
		java.lang.String serializedSettingsDDMFormValues,
		ServiceContext serviceContext) throws PortalException;

	public void addFormInstanceResources(DDMFormInstance ddmFormInstance,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws PortalException;

	public void addFormInstanceResources(DDMFormInstance ddmFormInstance,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws PortalException;

	/**
	* Creates a new ddm form instance with the primary key. Does not add the ddm form instance to the database.
	*
	* @param formInstanceId the primary key for the new ddm form instance
	* @return the new ddm form instance
	*/
	public DDMFormInstance createDDMFormInstance(long formInstanceId);

	/**
	* Deletes the ddm form instance from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmFormInstance the ddm form instance
	* @return the ddm form instance that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public DDMFormInstance deleteDDMFormInstance(
		DDMFormInstance ddmFormInstance);

	/**
	* Deletes the ddm form instance with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param formInstanceId the primary key of the ddm form instance
	* @return the ddm form instance that was removed
	* @throws PortalException if a ddm form instance with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public DDMFormInstance deleteDDMFormInstance(long formInstanceId)
		throws PortalException;

	@SystemEvent(action = SystemEventConstants.ACTION_SKIP, type = SystemEventConstants.TYPE_DELETE)
	public void deleteFormInstance(DDMFormInstance ddmFormInstance)
		throws PortalException;

	public void deleteFormInstance(long ddmFormInstanceId)
		throws PortalException;

	public void deleteFormInstances(long groupId) throws PortalException;

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	public DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMFormInstance fetchDDMFormInstance(long formInstanceId);

	/**
	* Returns the ddm form instance matching the UUID and group.
	*
	* @param uuid the ddm form instance's UUID
	* @param groupId the primary key of the group
	* @return the matching ddm form instance, or <code>null</code> if a matching ddm form instance could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMFormInstance fetchDDMFormInstanceByUuidAndGroupId(
		java.lang.String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMFormInstance fetchFormInstance(long ddmFormInstanceId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the ddm form instance with the primary key.
	*
	* @param formInstanceId the primary key of the ddm form instance
	* @return the ddm form instance
	* @throws PortalException if a ddm form instance with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMFormInstance getDDMFormInstance(long formInstanceId)
		throws PortalException;

	/**
	* Returns the ddm form instance matching the UUID and group.
	*
	* @param uuid the ddm form instance's UUID
	* @param groupId the primary key of the group
	* @return the matching ddm form instance
	* @throws PortalException if a matching ddm form instance could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMFormInstance getDDMFormInstanceByUuidAndGroupId(
		java.lang.String uuid, long groupId) throws PortalException;

	/**
	* Returns a range of all the ddm form instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ddm form instances
	* @param end the upper bound of the range of ddm form instances (not inclusive)
	* @return the range of ddm form instances
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDMFormInstance> getDDMFormInstances(int start, int end);

	/**
	* Returns all the ddm form instances matching the UUID and company.
	*
	* @param uuid the UUID of the ddm form instances
	* @param companyId the primary key of the company
	* @return the matching ddm form instances, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDMFormInstance> getDDMFormInstancesByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of ddm form instances matching the UUID and company.
	*
	* @param uuid the UUID of the ddm form instances
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of ddm form instances
	* @param end the upper bound of the range of ddm form instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching ddm form instances, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDMFormInstance> getDDMFormInstancesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<DDMFormInstance> orderByComparator);

	/**
	* Returns the number of ddm form instances.
	*
	* @return the number of ddm form instances
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDDMFormInstancesCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMFormInstance getFormInstance(long ddmFormInstanceId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMFormInstance getFormInstance(java.lang.String uuid,
		long ddmFormInstanceId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDMFormInstance> getFormInstances(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFormInstancesCount(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMFormValues getFormInstanceSettingsFormValues(
		DDMFormInstance formInstance) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMFormInstanceSettings getFormInstanceSettingsModel(
		DDMFormInstance formInstance) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDMFormInstance> search(long companyId, long groupId,
		java.lang.String keywords, int start, int end,
		OrderByComparator<DDMFormInstance> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDMFormInstance> search(long companyId, long groupId,
		java.lang.String[] names, java.lang.String[] descriptions,
		boolean andOperator, int start, int end,
		OrderByComparator<DDMFormInstance> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, long groupId,
		java.lang.String keywords);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, long groupId,
		java.lang.String[] names, java.lang.String[] descriptions,
		boolean andOperator);

	/**
	* Updates the ddm form instance in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddmFormInstance the ddm form instance
	* @return the ddm form instance that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public DDMFormInstance updateDDMFormInstance(
		DDMFormInstance ddmFormInstance);

	public DDMFormInstance updateFormInstance(long formInstanceId,
		DDMFormValues settingsDDMFormValues) throws PortalException;

	public DDMFormInstance updateFormInstance(long ddmFormInstanceId,
		long ddmStructureId, Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap,
		DDMFormValues settingsDDMFormValues, ServiceContext serviceContext)
		throws PortalException;

	public DDMFormInstance updateFormInstance(long ddmFormInstanceId,
		long ddmStructureId, Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap,
		java.lang.String serializedSettingsDDMFormValues,
		ServiceContext serviceContext) throws PortalException;
}