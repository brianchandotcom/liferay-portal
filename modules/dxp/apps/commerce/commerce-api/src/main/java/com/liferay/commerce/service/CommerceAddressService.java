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

import com.liferay.commerce.model.CommerceAddress;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service interface for CommerceAddress. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAddressServiceUtil
 * @see com.liferay.commerce.service.base.CommerceAddressServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceAddressServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CommerceAddress"}, service = CommerceAddressService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceAddressService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceAddressServiceUtil} to access the commerce address remote service. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceAddressServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CommerceAddress addCommerceAddress(String className, long classPK,
		String name, String description, String street1, String street2,
		String street3, String city, String zip, long commerceRegionId,
		long commerceCountryId, String phoneNumber, boolean defaultBilling,
		boolean defaultShipping, ServiceContext serviceContext)
		throws PortalException;

	public void deleteCommerceAddress(long commerceAddressId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceAddress fetchCommerceAddress(long commerceAddressId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceAddress getCommerceAddress(long commerceAddressId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceAddress> getCommerceAddresses(long groupId,
		String className, long classPK) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceAddress> getCommerceAddresses(long groupId,
		String className, long classPK, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceAddressesCount(long groupId, String className,
		long classPK) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<CommerceAddress> searchCommerceAddresses(
		long companyId, long groupId, String className, long classPK,
		String keywords, int start, int end, Sort sort)
		throws PortalException;

	public CommerceAddress updateCommerceAddress(long commerceAddressId,
		String name, String description, String street1, String street2,
		String street3, String city, String zip, long commerceRegionId,
		long commerceCountryId, String phoneNumber, boolean defaultBilling,
		boolean defaultShipping, ServiceContext serviceContext)
		throws PortalException;
}