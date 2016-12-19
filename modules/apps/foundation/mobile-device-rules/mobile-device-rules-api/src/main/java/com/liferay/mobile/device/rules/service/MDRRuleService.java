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

import com.liferay.mobile.device.rules.model.MDRRule;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service interface for MDRRule. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Edward C. Han
 * @see MDRRuleServiceUtil
 * @see com.liferay.mobile.device.rules.service.base.MDRRuleServiceBaseImpl
 * @see com.liferay.mobile.device.rules.service.impl.MDRRuleServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=mdr", "json.web.service.context.path=MDRRule"}, service = MDRRuleService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface MDRRuleService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MDRRuleServiceUtil} to access the mdr rule remote service. Add custom service methods to {@link com.liferay.mobile.device.rules.service.impl.MDRRuleServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@JSONWebService(mode = JSONWebServiceMode.IGNORE)
	public MDRRule addRule(long ruleGroupId,
		Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap, java.lang.String type,
		UnicodeProperties typeSettings, ServiceContext serviceContext)
		throws PortalException;

	public MDRRule addRule(long ruleGroupId,
		Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap, java.lang.String type,
		java.lang.String typeSettings, ServiceContext serviceContext)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MDRRule fetchRule(long ruleId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MDRRule getRule(long ruleId) throws PortalException;

	public MDRRule updateRule(long ruleId,
		Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap, java.lang.String type,
		UnicodeProperties typeSettingsProperties, ServiceContext serviceContext)
		throws PortalException;

	public MDRRule updateRule(long ruleId,
		Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap, java.lang.String type,
		java.lang.String typeSettings, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	public void deleteRule(long ruleId) throws PortalException;
}