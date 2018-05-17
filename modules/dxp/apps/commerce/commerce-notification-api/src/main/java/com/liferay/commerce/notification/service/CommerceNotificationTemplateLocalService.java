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

package com.liferay.commerce.notification.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.notification.model.CommerceNotificationTemplate;

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
 * Provides the local service interface for CommerceNotificationTemplate. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplateLocalServiceUtil
 * @see com.liferay.commerce.notification.service.base.CommerceNotificationTemplateLocalServiceBaseImpl
 * @see com.liferay.commerce.notification.service.impl.CommerceNotificationTemplateLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceNotificationTemplateLocalService
	extends BaseLocalService, PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceNotificationTemplateLocalServiceUtil} to access the commerce notification template local service. Add custom service methods to {@link com.liferay.commerce.notification.service.impl.CommerceNotificationTemplateLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the commerce notification template to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceNotificationTemplate the commerce notification template
	* @return the commerce notification template that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CommerceNotificationTemplate addCommerceNotificationTemplate(
		CommerceNotificationTemplate commerceNotificationTemplate);

	public CommerceNotificationTemplate addCommerceNotificationTemplate(
		String name, String description, String cc, String ccn, String type,
		boolean enabled, Map<Locale, String> subjectMap,
		Map<Locale, String> bodyMap, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Creates a new commerce notification template with the primary key. Does not add the commerce notification template to the database.
	*
	* @param commerceNotificationTemplateId the primary key for the new commerce notification template
	* @return the new commerce notification template
	*/
	@Transactional(enabled = false)
	public CommerceNotificationTemplate createCommerceNotificationTemplate(
		long commerceNotificationTemplateId);

	/**
	* Deletes the commerce notification template from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceNotificationTemplate the commerce notification template
	* @return the commerce notification template that was removed
	* @throws PortalException
	*/
	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceNotificationTemplate deleteCommerceNotificationTemplate(
		CommerceNotificationTemplate commerceNotificationTemplate)
		throws PortalException;

	/**
	* Deletes the commerce notification template with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceNotificationTemplateId the primary key of the commerce notification template
	* @return the commerce notification template that was removed
	* @throws PortalException if a commerce notification template with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public CommerceNotificationTemplate deleteCommerceNotificationTemplate(
		long commerceNotificationTemplateId) throws PortalException;

	public void deleteCommerceNotificationTemplates(long groupId)
		throws PortalException;

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.notification.model.impl.CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.notification.model.impl.CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public CommerceNotificationTemplate fetchCommerceNotificationTemplate(
		long commerceNotificationTemplateId);

	/**
	* Returns the commerce notification template matching the UUID and group.
	*
	* @param uuid the commerce notification template's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceNotificationTemplate fetchCommerceNotificationTemplateByUuidAndGroupId(
		String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the commerce notification template with the primary key.
	*
	* @param commerceNotificationTemplateId the primary key of the commerce notification template
	* @return the commerce notification template
	* @throws PortalException if a commerce notification template with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceNotificationTemplate getCommerceNotificationTemplate(
		long commerceNotificationTemplateId) throws PortalException;

	/**
	* Returns the commerce notification template matching the UUID and group.
	*
	* @param uuid the commerce notification template's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce notification template
	* @throws PortalException if a matching commerce notification template could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceNotificationTemplate getCommerceNotificationTemplateByUuidAndGroupId(
		String uuid, long groupId) throws PortalException;

	/**
	* Returns a range of all the commerce notification templates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.notification.model.impl.CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce notification templates
	* @param end the upper bound of the range of commerce notification templates (not inclusive)
	* @return the range of commerce notification templates
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceNotificationTemplate> getCommerceNotificationTemplates(
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceNotificationTemplate> getCommerceNotificationTemplates(
		long groupId, int start, int end,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator);

	/**
	* Returns all the commerce notification templates matching the UUID and company.
	*
	* @param uuid the UUID of the commerce notification templates
	* @param companyId the primary key of the company
	* @return the matching commerce notification templates, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceNotificationTemplate> getCommerceNotificationTemplatesByUuidAndCompanyId(
		String uuid, long companyId);

	/**
	* Returns a range of commerce notification templates matching the UUID and company.
	*
	* @param uuid the UUID of the commerce notification templates
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce notification templates
	* @param end the upper bound of the range of commerce notification templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce notification templates, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceNotificationTemplate> getCommerceNotificationTemplatesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator);

	/**
	* Returns the number of commerce notification templates.
	*
	* @return the number of commerce notification templates
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceNotificationTemplatesCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceNotificationTemplatesCount(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	* Updates the commerce notification template in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceNotificationTemplate the commerce notification template
	* @return the commerce notification template that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CommerceNotificationTemplate updateCommerceNotificationTemplate(
		CommerceNotificationTemplate commerceNotificationTemplate);

	public CommerceNotificationTemplate updateCommerceNotificationTemplate(
		long commerceNotificationTemplateId, String name, String description,
		String cc, String ccn, String type, boolean enabled,
		Map<Locale, String> subjectMap, Map<Locale, String> bodyMap,
		ServiceContext serviceContext) throws PortalException;
}