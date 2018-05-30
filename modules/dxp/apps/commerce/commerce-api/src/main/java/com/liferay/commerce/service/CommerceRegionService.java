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

import com.liferay.commerce.model.CommerceRegion;

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

/**
 * Provides the remote service interface for CommerceRegion. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceRegionServiceUtil
 * @see com.liferay.commerce.service.base.CommerceRegionServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceRegionServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CommerceRegion"}, service = CommerceRegionService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceRegionService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceRegionServiceUtil} to access the commerce region remote service. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceRegionServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CommerceRegion addCommerceRegion(long commerceCountryId,
		String name, String code, double priority, boolean active,
		ServiceContext serviceContext) throws PortalException;

	public void deleteCommerceRegion(long commerceRegionId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceRegion getCommerceRegion(long commerceRegionId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceRegion> getCommerceRegions(long commerceCountryId,
		boolean active);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceRegion> getCommerceRegions(long commerceCountryId,
		boolean active, int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceRegion> getCommerceRegions(long commerceCountryId,
		int start, int end, OrderByComparator<CommerceRegion> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceRegionsCount(long commerceCountryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceRegionsCount(long commerceCountryId, boolean active);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	public CommerceRegion updateCommerceRegion(long commerceRegionId,
		String name, String code, double priority, boolean active,
		ServiceContext serviceContext) throws PortalException;
}