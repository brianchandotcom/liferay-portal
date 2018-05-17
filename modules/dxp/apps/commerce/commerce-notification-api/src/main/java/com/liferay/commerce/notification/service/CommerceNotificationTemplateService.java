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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service interface for CommerceNotificationTemplate. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplateServiceUtil
 * @see com.liferay.commerce.notification.service.base.CommerceNotificationTemplateServiceBaseImpl
 * @see com.liferay.commerce.notification.service.impl.CommerceNotificationTemplateServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CommerceNotificationTemplate"}, service = CommerceNotificationTemplateService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceNotificationTemplateService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceNotificationTemplateServiceUtil} to access the commerce notification template remote service. Add custom service methods to {@link com.liferay.commerce.notification.service.impl.CommerceNotificationTemplateServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CommerceNotificationTemplate addCommerceNotificationTemplate(
		String name, String description, String cc, String ccn, String type,
		boolean enabled, Map<Locale, String> subjectMap,
		Map<Locale, String> bodyMap, ServiceContext serviceContext)
		throws PortalException;

	public void deleteCommerceNotificationTemplate(
		long commerceNotificationTemplateId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceNotificationTemplate getCommerceNotificationTemplate(
		long commerceNotificationTemplateId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceNotificationTemplate> getCommerceNotificationTemplates(
		long groupId, int start, int end,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceNotificationTemplatesCount(long groupId)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	public CommerceNotificationTemplate updateCommerceNotificationTemplate(
		long commerceNotificationTemplateId, String name, String description,
		String cc, String ccn, String type, boolean enabled,
		Map<Locale, String> subjectMap, Map<Locale, String> bodyMap,
		ServiceContext serviceContext) throws PortalException;
}