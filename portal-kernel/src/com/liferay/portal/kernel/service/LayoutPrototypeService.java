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

package com.liferay.portal.kernel.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service interface for LayoutPrototype. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPrototypeServiceUtil
 * @see com.liferay.portal.service.base.LayoutPrototypeServiceBaseImpl
 * @see com.liferay.portal.service.impl.LayoutPrototypeServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface LayoutPrototypeService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutPrototypeServiceUtil} to access the layout prototype remote service. Add custom service methods to {@link com.liferay.portal.service.impl.LayoutPrototypeServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* @deprecated As of 7.0.0, replaced by {@link #addLayoutPrototype(Map, Map,
	boolean, ServiceContext)}
	*/
	@java.lang.Deprecated
	public LayoutPrototype addLayoutPrototype(
		Map<Locale, java.lang.String> nameMap, java.lang.String description,
		boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException;

	public LayoutPrototype addLayoutPrototype(
		Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException;

	public void deleteLayoutPrototype(long layoutPrototypeId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutPrototype fetchLayoutPrototype(long layoutPrototypeId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutPrototype getLayoutPrototype(long layoutPrototypeId)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPrototype> search(long companyId,
		java.lang.Boolean active, OrderByComparator<LayoutPrototype> obc)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link #updateLayoutPrototype(long,
	Map, Map, boolean, ServiceContext)}
	*/
	@java.lang.Deprecated
	public LayoutPrototype updateLayoutPrototype(long layoutPrototypeId,
		Map<Locale, java.lang.String> nameMap, java.lang.String description,
		boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException;

	public LayoutPrototype updateLayoutPrototype(long layoutPrototypeId,
		Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException;
}