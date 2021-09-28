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

package com.liferay.object.service;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for ObjectDefinition. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Marco Leo
 * @see ObjectDefinitionServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface ObjectDefinitionService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.object.service.impl.ObjectDefinitionServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the object definition remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link ObjectDefinitionServiceUtil} if injection and service tracking are not available.
	 */
	public ObjectDefinition addCustomObjectDefinition(
			Map<Locale, String> labelMap, String name, String panelAppOrder,
			String panelCategoryKey, Map<Locale, String> pluralLabelMap,
			String scope, List<ObjectField> objectFields)
		throws PortalException;

	public ObjectDefinition deleteObjectDefinition(long objectDefinitionId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ObjectDefinition getObjectDefinition(long objectDefinitionId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ObjectDefinition> getObjectDefinitions(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ObjectDefinition> getObjectDefinitions(
		long companyId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getObjectDefinitionsCount() throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getObjectDefinitionsCount(long companyId) throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public ObjectDefinition publishCustomObjectDefinition(
			long objectDefinitionId)
		throws PortalException;

	public ObjectDefinition updateCustomObjectDefinition(
			Long objectDefinitionId, long descriptionObjectFieldId,
			long titleObjectFieldId, boolean active,
			Map<Locale, String> labelMap, String name, String panelAppOrder,
			String panelCategoryKey, Map<Locale, String> pluralLabelMap,
			String scope)
		throws PortalException;

}