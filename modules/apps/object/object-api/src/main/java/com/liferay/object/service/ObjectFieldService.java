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

import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.model.ObjectStateFlow;
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
 * Provides the remote service interface for ObjectField. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Marco Leo
 * @see ObjectFieldServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface ObjectFieldService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.object.service.impl.ObjectFieldServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the object field remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link ObjectFieldServiceUtil} if injection and service tracking are not available.
	 */
	public ObjectField addCustomObjectField(
			long listTypeDefinitionId, long objectDefinitionId,
			String businessType, String dbType, String defaultValue,
			boolean indexed, boolean indexedAsKeyword, String indexedLanguageId,
			Map<Locale, String> labelMap, String name, boolean required,
			boolean state, List<ObjectFieldSetting> objectFieldSettings)
		throws PortalException;

	public ObjectField deleteObjectField(long objectFieldId) throws Exception;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ObjectField getObjectField(long objectFieldId)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public ObjectField updateObjectField(
			long objectFieldId, String externalReferenceCode,
			long listTypeDefinitionId, String businessType, String dbType,
			String defaultValue, boolean indexed, boolean indexedAsKeyword,
			String indexedLanguageId, Map<Locale, String> labelMap, String name,
			boolean required, boolean state,
			List<ObjectFieldSetting> objectFieldSettings,
			ObjectStateFlow objectStateFlow)
		throws PortalException;

}