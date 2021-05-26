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

import com.liferay.commerce.account.model.CommerceAccountGroupRel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for CommerceAccountGroupRel. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Marco Leo
 * @see CommerceAccountGroupRelLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CommerceAccountGroupRelLocalService extends BaseLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.account.service.impl.CommerceAccountGroupRelLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the commerce account group rel local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CommerceAccountGroupRelLocalServiceUtil} if injection and service tracking are not available.
	 */
	public CommerceAccountGroupRel addCommerceAccountGroupRel(
		CommerceAccountGroupRel commerceAccountGroupRel);

	public CommerceAccountGroupRel addCommerceAccountGroupRel(
			String className, long classPK, long commerceAccountGroupId,
			ServiceContext serviceContext)
		throws PortalException;

	public CommerceAccountGroupRel deleteCommerceAccountGroupRel(
		CommerceAccountGroupRel commerceAccountGroupRel);

	public CommerceAccountGroupRel deleteCommerceAccountGroupRel(
			long commerceAccountGroupRelId)
		throws PortalException;

	public void deleteCommerceAccountGroupRels(long commerceAccountGroupId);

	public void deleteCommerceAccountGroupRels(String className, long classPK);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceAccountGroupRel fetchCommerceAccountGroupRel(
		long commerceAccountGroupRelId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceAccountGroupRel getCommerceAccountGroupRel(
			long commerceAccountGroupRelId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceAccountGroupRel> getCommerceAccountGroupRels(
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceAccountGroupRel> getCommerceAccountGroupRels(
		long commerceAccountGroupId, int start, int end,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceAccountGroupRel> getCommerceAccountGroupRels(
		String className, long classPK, int start, int end,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceAccountGroupRelsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceAccountGroupRelsCount(long commerceAccountGroupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceAccountGroupRelsCount(String className, long classPK);

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

}