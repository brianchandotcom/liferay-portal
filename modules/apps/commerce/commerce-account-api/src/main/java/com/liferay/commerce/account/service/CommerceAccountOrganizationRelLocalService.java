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

package com.liferay.commerce.account.service;

import com.liferay.commerce.account.model.CommerceAccountOrganizationRel;
import com.liferay.commerce.account.service.persistence.CommerceAccountOrganizationRelPK;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for CommerceAccountOrganizationRel. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Marco Leo
 * @see CommerceAccountOrganizationRelLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CommerceAccountOrganizationRelLocalService
	extends BaseLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.account.service.impl.CommerceAccountOrganizationRelLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the commerce account organization rel local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CommerceAccountOrganizationRelLocalServiceUtil} if injection and service tracking are not available.
	 */
	public CommerceAccountOrganizationRel addCommerceAccountOrganizationRel(
		CommerceAccountOrganizationRel commerceAccountOrganizationRel);

	public CommerceAccountOrganizationRel addCommerceAccountOrganizationRel(
			long commerceAccountId, long organizationId,
			ServiceContext serviceContext)
		throws PortalException;

	public void addCommerceAccountOrganizationRels(
			long commerceAccountId, long[] organizationIds,
			ServiceContext serviceContext)
		throws PortalException;

	public CommerceAccountOrganizationRel createCommerceAccountOrganizationRel(
		CommerceAccountOrganizationRelPK commerceAccountOrganizationRelPK);

	public CommerceAccountOrganizationRel deleteCommerceAccountOrganizationRel(
		CommerceAccountOrganizationRel commerceAccountOrganizationRel);

	public CommerceAccountOrganizationRel deleteCommerceAccountOrganizationRel(
			CommerceAccountOrganizationRelPK commerceAccountOrganizationRelPK)
		throws PortalException;

	public void deleteCommerceAccountOrganizationRels(
			long commerceAccountId, long[] organizationIds)
		throws PortalException;

	public void deleteCommerceAccountOrganizationRelsByCommerceAccountId(
		long commerceAccountId);

	public void deleteCommerceAccountOrganizationRelsByOrganizationId(
		long organizationId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceAccountOrganizationRel fetchCommerceAccountOrganizationRel(
		CommerceAccountOrganizationRelPK commerceAccountOrganizationRelPK);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceAccountOrganizationRel getCommerceAccountOrganizationRel(
			CommerceAccountOrganizationRelPK commerceAccountOrganizationRelPK)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceAccountOrganizationRel>
		getCommerceAccountOrganizationRels(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceAccountOrganizationRel>
		getCommerceAccountOrganizationRels(long commerceAccountId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceAccountOrganizationRel>
		getCommerceAccountOrganizationRels(
			long commerceAccountId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceAccountOrganizationRel>
		getCommerceAccountOrganizationRelsByOrganizationId(
			long organizationId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceAccountOrganizationRelsByOrganizationIdCount(
		long organizationId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceAccountOrganizationRelsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceAccountOrganizationRelsCount(long commerceAccountId);

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public CommerceAccountOrganizationRel updateCommerceAccountOrganizationRel(
		CommerceAccountOrganizationRel commerceAccountOrganizationRel);

}