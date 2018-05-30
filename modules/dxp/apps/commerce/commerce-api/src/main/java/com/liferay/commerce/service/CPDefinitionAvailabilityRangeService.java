/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CPDefinitionAvailabilityRange;

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

/**
 * Provides the remote service interface for CPDefinitionAvailabilityRange. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionAvailabilityRangeServiceUtil
 * @see com.liferay.commerce.service.base.CPDefinitionAvailabilityRangeServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CPDefinitionAvailabilityRangeServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CPDefinitionAvailabilityRange"}, service = CPDefinitionAvailabilityRangeService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CPDefinitionAvailabilityRangeService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPDefinitionAvailabilityRangeServiceUtil} to access the cp definition availability range remote service. Add custom service methods to {@link com.liferay.commerce.service.impl.CPDefinitionAvailabilityRangeServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public void deleteCPDefinitionAvailabilityRange(
		long cpDefinitionAvailabilityRangeId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionAvailabilityRange fetchCPDefinitionAvailabilityRangeByCPDefinitionId(
		long cpDefinitionId);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	public CPDefinitionAvailabilityRange updateCPDefinitionAvailabilityRange(
		long cpDefinitionAvailabilityRangeId, long cpDefinitionId,
		long commerceAvailabilityRangeId, ServiceContext serviceContext)
		throws PortalException;
}