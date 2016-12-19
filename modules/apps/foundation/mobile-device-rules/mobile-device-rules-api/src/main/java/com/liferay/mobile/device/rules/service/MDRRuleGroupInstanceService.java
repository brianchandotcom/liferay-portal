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

package com.liferay.mobile.device.rules.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.mobile.device.rules.model.MDRRuleGroupInstance;

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
 * Provides the remote service interface for MDRRuleGroupInstance. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Edward C. Han
 * @see MDRRuleGroupInstanceServiceUtil
 * @see com.liferay.mobile.device.rules.service.base.MDRRuleGroupInstanceServiceBaseImpl
 * @see com.liferay.mobile.device.rules.service.impl.MDRRuleGroupInstanceServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=mdr", "json.web.service.context.path=MDRRuleGroupInstance"}, service = MDRRuleGroupInstanceService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface MDRRuleGroupInstanceService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MDRRuleGroupInstanceServiceUtil} to access the mdr rule group instance remote service. Add custom service methods to {@link com.liferay.mobile.device.rules.service.impl.MDRRuleGroupInstanceServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public MDRRuleGroupInstance addRuleGroupInstance(long groupId,
		java.lang.String className, long classPK, long ruleGroupId,
		ServiceContext serviceContext) throws PortalException;

	public MDRRuleGroupInstance addRuleGroupInstance(long groupId,
		java.lang.String className, long classPK, long ruleGroupId,
		int priority, ServiceContext serviceContext) throws PortalException;

	public MDRRuleGroupInstance updateRuleGroupInstance(
		long ruleGroupInstanceId, int priority) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRuleGroupInstancesCount(java.lang.String className,
		long classPK);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MDRRuleGroupInstance> getRuleGroupInstances(
		java.lang.String className, long classPK, int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator);

	public void deleteRuleGroupInstance(long ruleGroupInstanceId)
		throws PortalException;
}