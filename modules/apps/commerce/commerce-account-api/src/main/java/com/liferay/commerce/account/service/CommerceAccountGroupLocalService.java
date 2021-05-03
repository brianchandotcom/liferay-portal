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

import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for CommerceAccountGroup. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Marco Leo
 * @see CommerceAccountGroupLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CommerceAccountGroupLocalService extends BaseLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.account.service.impl.CommerceAccountGroupLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the commerce account group local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CommerceAccountGroupLocalServiceUtil} if injection and service tracking are not available.
	 */
	@Indexable(type = IndexableType.REINDEX)
	public CommerceAccountGroup addCommerceAccountGroup(
			long companyId, String name, int type, boolean system,
			String externalReferenceCode, ServiceContext serviceContext)
		throws PortalException;

	public void checkGuestCommerceAccountGroup(long companyId)
		throws PortalException;

	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceAccountGroup deleteCommerceAccountGroup(
			CommerceAccountGroup commerceAccountGroup)
		throws PortalException;

	public CommerceAccountGroup deleteCommerceAccountGroup(
			long commerceAccountGroupId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceAccountGroup fetchByExternalReferenceCode(
		long companyId, String externalReferenceCode);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceAccountGroup fetchCommerceAccountGroup(
		long commerceAccountGroupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceAccountGroup getCommerceAccountGroup(
			long commerceAccountGroupId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceAccountGroup> getCommerceAccountGroups(
		long companyId, int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceAccountGroup>
		getCommerceAccountGroupsByCommerceAccountId(long commerceAccountId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceAccountGroupsCount(long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Long> getCommerceAccountUserIdsFromAccountGroupIds(
		long[] commerceAccountGroupIds, int start, int end);

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceAccountGroup> searchCommerceAccountGroups(
			long companyId, String keywords, int start, int end, Sort sort)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCommerceAccountsGroupCount(long companyId, String keywords)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CommerceAccountGroup updateCommerceAccountGroup(
			long commerceAccountGroupId, String name,
			ServiceContext serviceContext)
		throws PortalException;

}