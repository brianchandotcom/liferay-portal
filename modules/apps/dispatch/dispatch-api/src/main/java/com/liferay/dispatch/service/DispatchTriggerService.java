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

package com.liferay.dispatch.service;

import com.liferay.dispatch.executor.DispatchTaskClusterMode;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for DispatchTrigger. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Matija Petanjek
 * @see DispatchTriggerServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface DispatchTriggerService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.dispatch.service.impl.DispatchTriggerServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the dispatch trigger remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link DispatchTriggerServiceUtil} if injection and service tracking are not available.
	 */
	public DispatchTrigger addDispatchTrigger(
			String externalReferenceCode, long userId,
			String dispatchTaskExecutorType,
			UnicodeProperties dispatchTaskSettingsUnicodeProperties,
			String name)
		throws PortalException;

	public void deleteDispatchTrigger(long dispatchTriggerId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DispatchTrigger getDispatchTrigger(long dispatchTriggerId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DispatchTrigger> getDispatchTriggers(int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDispatchTriggersCount() throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public DispatchTrigger updateDispatchTrigger(
			long dispatchTriggerId, boolean active, String cronExpression,
			DispatchTaskClusterMode dispatchTaskClusterMode, int endDateMonth,
			int endDateDay, int endDateYear, int endDateHour, int endDateMinute,
			boolean neverEnd, boolean overlapAllowed, int startDateMonth,
			int startDateDay, int startDateYear, int startDateHour,
			int startDateMinute, String timeZoneId)
		throws PortalException;

	public DispatchTrigger updateDispatchTrigger(
			long dispatchTriggerId,
			UnicodeProperties dispatchTaskSettingsUnicodeProperties,
			String name)
		throws PortalException;

}