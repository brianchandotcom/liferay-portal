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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceTaxMethod;

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

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service interface for CommerceTaxMethod. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxMethodServiceUtil
 * @see com.liferay.commerce.service.base.CommerceTaxMethodServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceTaxMethodServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CommerceTaxMethod"}, service = CommerceTaxMethodService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceTaxMethodService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceTaxMethodServiceUtil} to access the commerce tax method remote service. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceTaxMethodServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CommerceTaxMethod addCommerceTaxMethod(Map<Locale, String> nameMap,
		Map<Locale, String> descriptionMap, String engineKey,
		boolean percentage, boolean active, ServiceContext serviceContext)
		throws PortalException;

	public CommerceTaxMethod createCommerceTaxMethod(long commerceTaxMethodId);

	public void deleteCommerceTaxMethod(long commerceTaxMethodId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceTaxMethod fetchCommerceTaxMethod(long groupId,
		String engineKey);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceTaxMethod getCommerceTaxMethod(long commerceTaxMethodId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceTaxMethod> getCommerceTaxMethods(long groupId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceTaxMethod> getCommerceTaxMethods(long groupId,
		boolean active);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceTaxMethodsCount(long groupId, boolean active);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	public CommerceTaxMethod setActive(long commerceTaxMethodId, boolean active)
		throws PortalException;

	public CommerceTaxMethod updateCommerceTaxMethod(long commerceTaxMethodId,
		Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
		boolean percentage, boolean active) throws PortalException;
}