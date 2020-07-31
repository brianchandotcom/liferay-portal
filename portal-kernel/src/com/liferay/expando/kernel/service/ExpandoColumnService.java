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

package com.liferay.expando.kernel.service;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for ExpandoColumn. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoColumnServiceUtil
 * @generated
 */
@AccessControlled
@CTAware
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface ExpandoColumnService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.portlet.expando.service.impl.ExpandoColumnServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the expando column remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link ExpandoColumnServiceUtil} if injection and service tracking are not available.
	 */
	public ExpandoColumn addColumn(long tableId, String name, int type)
		throws PortalException;

	public ExpandoColumn addColumn(
			long tableId, String name, int type, Object defaultData)
		throws PortalException;

	public void deleteColumn(long columnId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExpandoColumn fetchExpandoColumn(long columnId)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public ExpandoColumn updateColumn(long columnId, String name, int type)
		throws PortalException;

	public ExpandoColumn updateColumn(
			long columnId, String name, int type, Object defaultData)
		throws PortalException;

	public ExpandoColumn updateTypeSettings(long columnId, String typeSettings)
		throws PortalException;

}