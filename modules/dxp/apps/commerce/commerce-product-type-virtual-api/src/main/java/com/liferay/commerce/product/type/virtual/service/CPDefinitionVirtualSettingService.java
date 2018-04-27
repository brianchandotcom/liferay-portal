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

package com.liferay.commerce.product.type.virtual.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;

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

import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service interface for CPDefinitionVirtualSetting. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Marco Leo
 * @see CPDefinitionVirtualSettingServiceUtil
 * @see com.liferay.commerce.product.type.virtual.service.base.CPDefinitionVirtualSettingServiceBaseImpl
 * @see com.liferay.commerce.product.type.virtual.service.impl.CPDefinitionVirtualSettingServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CPDefinitionVirtualSetting"}, service = CPDefinitionVirtualSettingService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CPDefinitionVirtualSettingService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPDefinitionVirtualSettingServiceUtil} to access the cp definition virtual setting remote service. Add custom service methods to {@link com.liferay.commerce.product.type.virtual.service.impl.CPDefinitionVirtualSettingServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CPDefinitionVirtualSetting addCPDefinitionVirtualSetting(
		long cpDefinitionId, long fileEntryId, String url,
		String activationStatus, long duration, int maxUsages,
		boolean useSample, long sampleFileEntryId, String sampleUrl,
		boolean termsOfUseRequired, Map<Locale, String> termsOfUseContentMap,
		long termsOfUseJournalArticleResourcePrimKey,
		ServiceContext serviceContext) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionVirtualSetting fetchCPDefinitionVirtualSettingByCPDefinitionId(
		long cpDefinitionId) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	public CPDefinitionVirtualSetting updateCPDefinitionVirtualSetting(
		long cpDefinitionVirtualSettingId, long fileEntryId, String url,
		String activationStatus, long duration, int maxUsages,
		boolean useSample, long sampleFileEntryId, String sampleUrl,
		boolean termsOfUseRequired, Map<Locale, String> termsOfUseContentMap,
		long termsOfUseJournalArticleResourcePrimKey,
		ServiceContext serviceContext) throws PortalException;
}