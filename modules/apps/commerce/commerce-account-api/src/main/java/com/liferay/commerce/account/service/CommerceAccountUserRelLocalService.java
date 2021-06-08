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

import com.liferay.commerce.account.model.CommerceAccountUserRel;
import com.liferay.commerce.account.service.persistence.CommerceAccountUserRelPK;
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
 * Provides the local service interface for CommerceAccountUserRel. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Marco Leo
 * @see CommerceAccountUserRelLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CommerceAccountUserRelLocalService extends BaseLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.account.service.impl.CommerceAccountUserRelLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the commerce account user rel local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CommerceAccountUserRelLocalServiceUtil} if injection and service tracking are not available.
	 */
	public CommerceAccountUserRel addCommerceAccountUserRel(
		CommerceAccountUserRel commerceAccountUserRel);

	public CommerceAccountUserRel addCommerceAccountUserRel(
			long commerceAccountId, long commerceAccountUserId, long[] roleIds,
			ServiceContext serviceContext)
		throws PortalException;

	public CommerceAccountUserRel addCommerceAccountUserRel(
			long commerceAccountId, long commerceAccountUserId,
			ServiceContext serviceContext)
		throws PortalException;

	public void addCommerceAccountUserRels(
			long commerceAccountId, long[] userIds, String[] emailAddresses,
			long[] roleIds, ServiceContext serviceContext)
		throws PortalException;

	public void addDefaultRoles(long userId) throws PortalException;

	public CommerceAccountUserRel createCommerceAccountUserRel(
		CommerceAccountUserRelPK commerceAccountUserRelPK);

	public CommerceAccountUserRel deleteCommerceAccountUserRel(
		CommerceAccountUserRel commerceAccountUserRel);

	public CommerceAccountUserRel deleteCommerceAccountUserRel(
			CommerceAccountUserRelPK commerceAccountUserRelPK)
		throws PortalException;

	public void deleteCommerceAccountUserRels(
			long commerceAccountId, long[] userIds)
		throws PortalException;

	public void deleteCommerceAccountUserRelsByCommerceAccountId(
		long commerceAccountId);

	public void deleteCommerceAccountUserRelsByCommerceAccountUserId(
		long userId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceAccountUserRel fetchCommerceAccountUserRel(
		CommerceAccountUserRelPK commerceAccountUserRelPK);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceAccountUserRel getCommerceAccountUserRel(
			CommerceAccountUserRelPK commerceAccountUserRelPK)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceAccountUserRel> getCommerceAccountUserRels(
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceAccountUserRel> getCommerceAccountUserRels(
		long commerceAccountId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceAccountUserRel> getCommerceAccountUserRels(
		long commerceAccountId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceAccountUserRel>
		getCommerceAccountUserRelsByCommerceAccountUserId(
			long commerceAccountUserId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceAccountUserRelsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceAccountUserRelsCount(long commerceAccountId);

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public CommerceAccountUserRel inviteUser(
			long commerceAccountId, String emailAddress, long[] roleIds,
			String userExternalReferenceCode, ServiceContext serviceContext)
		throws PortalException;

	public CommerceAccountUserRel updateCommerceAccountUserRel(
		CommerceAccountUserRel commerceAccountUserRel);

}