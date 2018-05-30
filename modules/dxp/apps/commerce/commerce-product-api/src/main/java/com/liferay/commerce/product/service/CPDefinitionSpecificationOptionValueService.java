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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;

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
 * Provides the remote service interface for CPDefinitionSpecificationOptionValue. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Marco Leo
 * @see CPDefinitionSpecificationOptionValueServiceUtil
 * @see com.liferay.commerce.product.service.base.CPDefinitionSpecificationOptionValueServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPDefinitionSpecificationOptionValueServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CPDefinitionSpecificationOptionValue"}, service = CPDefinitionSpecificationOptionValueService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CPDefinitionSpecificationOptionValueService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPDefinitionSpecificationOptionValueServiceUtil} to access the cp definition specification option value remote service. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPDefinitionSpecificationOptionValueServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CPDefinitionSpecificationOptionValue addCPDefinitionSpecificationOptionValue(
		long cpDefinitionId, long cpSpecificationOptionId,
		long cpOptionCategoryId, Map<Locale, String> valueMap, double priority,
		ServiceContext serviceContext) throws PortalException;

	public void deleteCPDefinitionSpecificationOptionValue(
		long cpDefinitionSpecificationOptionValueId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionSpecificationOptionValue fetchCPDefinitionSpecificationOptionValue(
		long cpDefinitionSpecificationOptionValueId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionSpecificationOptionValue fetchCPDefinitionSpecificationOptionValue(
		long cpDefinitionId, long cpSpecificationOptionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionSpecificationOptionValue getCPDefinitionSpecificationOptionValue(
		long cpDefinitionSpecificationOptionValueId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionSpecificationOptionValue> getCPDefinitionSpecificationOptionValues(
		long cpDefinitionId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionSpecificationOptionValue> getCPDefinitionSpecificationOptionValues(
		long cpDefinitionId, long cpOptionCategoryId);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	public CPDefinitionSpecificationOptionValue updateCPDefinitionSpecificationOptionValue(
		long cpDefinitionSpecificationOptionValueId, long cpOptionCategoryId,
		Map<Locale, String> valueMap, double priority,
		ServiceContext serviceContext) throws PortalException;
}