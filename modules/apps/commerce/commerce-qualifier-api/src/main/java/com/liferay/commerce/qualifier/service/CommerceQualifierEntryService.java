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

package com.liferay.commerce.qualifier.service;

import com.liferay.commerce.qualifier.model.CommerceQualifierEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for CommerceQualifierEntry. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Riccardo Alberti
 * @see CommerceQualifierEntryServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CommerceQualifierEntryService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.qualifier.service.impl.CommerceQualifierEntryServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the commerce qualifier entry remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CommerceQualifierEntryServiceUtil} if injection and service tracking are not available.
	 */
	public CommerceQualifierEntry addCommerceQualifierEntry(
			String sourceClassName, long sourceClassPK,
			String sourceCommerceQualifierMetadataKey, String targetClassName,
			long targetClassPK, String targetCommerceQualifierMetadataKey)
		throws PortalException;

	public CommerceQualifierEntry deleteCommerceQualifierEntry(
			long commerceQualifierEntryId)
		throws PortalException;

	public void deleteSourceCommerceQualifierEntries(
			String sourceClassName, long sourceClassPK)
		throws PortalException;

	public void deleteTargetCommerceQualifierEntries(
			String targetClassName, long targetClassPK)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceQualifierEntry fetchCommerceQualifierEntry(
			String sourceClassName, long sourceClassPK, String targetClassName,
			long targetClassPK)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceQualifierEntry getCommerceQualifierEntry(
			long commerceQualifierEntryId)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceQualifierEntry> getSourceCommerceQualifierEntries(
			long companyId, String sourceClassName, long sourceClassPK,
			String targetCommerceQualifierMetadataKey, String keywords,
			int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getSourceCommerceQualifierEntriesCount(
			long companyId, String sourceClassName, long sourceClassPK,
			String targetCommerceQualifierMetadataKey, String keywords)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceQualifierEntry> getTargetCommerceQualifierEntries(
			long companyId, String sourceCommerceQualifierMetadataKey,
			String targetClassName, long targetClassPK, String keywords,
			int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getTargetCommerceQualifierEntriesCount(
			long companyId, String sourceCommerceQualifierMetadataKey,
			String targetClassName, long targetClassPK, String keywords)
		throws PortalException;

}