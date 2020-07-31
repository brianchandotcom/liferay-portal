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

import com.liferay.mobile.device.rules.model.MDRRuleGroup;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for MDRRuleGroup. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Edward C. Han
 * @see MDRRuleGroupServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface MDRRuleGroupService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.mobile.device.rules.service.impl.MDRRuleGroupServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the mdr rule group remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link MDRRuleGroupServiceUtil} if injection and service tracking are not available.
	 */
	public MDRRuleGroup addRuleGroup(
			long groupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, ServiceContext serviceContext)
		throws PortalException;

	public MDRRuleGroup copyRuleGroup(
			long ruleGroupId, long groupId, ServiceContext serviceContext)
		throws PortalException;

	public void deleteRuleGroup(long ruleGroupId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MDRRuleGroup fetchRuleGroup(long ruleGroupId) throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MDRRuleGroup getRuleGroup(long ruleGroupId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MDRRuleGroup> getRuleGroups(
		long[] groupIds, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRuleGroupsCount(long[] groupIds);

	public MDRRuleGroup updateRuleGroup(
			long ruleGroupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, ServiceContext serviceContext)
		throws PortalException;

}