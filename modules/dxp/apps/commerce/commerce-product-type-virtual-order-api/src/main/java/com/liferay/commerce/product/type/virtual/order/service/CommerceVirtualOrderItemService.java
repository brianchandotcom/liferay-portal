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

package com.liferay.commerce.product.type.virtual.order.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.io.File;

/**
 * Provides the remote service interface for CommerceVirtualOrderItem. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVirtualOrderItemServiceUtil
 * @see com.liferay.commerce.product.type.virtual.order.service.base.CommerceVirtualOrderItemServiceBaseImpl
 * @see com.liferay.commerce.product.type.virtual.order.service.impl.CommerceVirtualOrderItemServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CommerceVirtualOrderItem"}, service = CommerceVirtualOrderItemService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceVirtualOrderItemService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceVirtualOrderItemServiceUtil} to access the commerce virtual order item remote service. Add custom service methods to {@link com.liferay.commerce.product.type.virtual.order.service.impl.CommerceVirtualOrderItemServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public File getFile(long commerceVirtualOrderItemId)
		throws Exception;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	public CommerceVirtualOrderItem updateCommerceVirtualOrderItem(
		long commerceVirtualOrderItemId, long fileEntryId, String url,
		int activationStatus, long duration, int usages, int maxUsages,
		boolean active) throws PortalException;
}