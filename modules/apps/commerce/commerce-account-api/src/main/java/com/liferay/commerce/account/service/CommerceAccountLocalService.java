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

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Group;
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

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for CommerceAccount. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Marco Leo
 * @see CommerceAccountLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CommerceAccountLocalService extends BaseLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.account.service.impl.CommerceAccountLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the commerce account local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CommerceAccountLocalServiceUtil} if injection and service tracking are not available.
	 */
	public CommerceAccount addBusinessCommerceAccount(
			String name, long parentCommerceAccountId, String email,
			String taxId, boolean active, String externalReferenceCode,
			long[] userIds, String[] emailAddresses,
			ServiceContext serviceContext)
		throws PortalException;

	public CommerceAccount addCommerceAccount(CommerceAccount commerceAccount);

	@Indexable(type = IndexableType.REINDEX)
	public CommerceAccount addCommerceAccount(
			String name, long parentCommerceAccountId, String email,
			String taxId, int type, boolean active,
			String externalReferenceCode, ServiceContext serviceContext)
		throws PortalException;

	public CommerceAccount addOrUpdateCommerceAccount(
			String name, long parentCommerceAccountId, boolean logo,
			byte[] logoBytes, String email, String taxId, int type,
			boolean active, String externalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException;

	public CommerceAccount addPersonalCommerceAccount(
			long userId, String taxId, String externalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException;

	public CommerceAccount createCommerceAccount(long commerceAccountId);

	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceAccount deleteCommerceAccount(
			CommerceAccount commerceAccount)
		throws PortalException;

	public CommerceAccount deleteCommerceAccount(long commerceAccountId)
		throws PortalException;

	public void deleteCommerceAccounts(long companyId) throws PortalException;

	public void deleteLogo(long commerceAccountId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> T dslQuery(DSLQuery dslQuery);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceAccount fetchByExternalReferenceCode(
		long companyId, String externalReferenceCode);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceAccount fetchCommerceAccount(long commerceAccountId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceAccount fetchCommerceAccountByReferenceCode(
		long companyId, String externalReferenceCode);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceAccount getCommerceAccount(long commerceAccountId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceAccount getCommerceAccount(
			long userId, long commerceAccountId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Group getCommerceAccountGroup(long commerceAccountId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceAccount> getCommerceAccounts(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceAccountsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceAccount getGuestCommerceAccount(long companyId)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceAccount getPersonalCommerceAccount(long userId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceAccount> getUserCommerceAccounts(
			long userId, Long parentCommerceAccountId, int commerceSiteType,
			String keywords, Boolean active, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceAccount> getUserCommerceAccounts(
			long userId, Long parentCommerceAccountId, int commerceSiteType,
			String keywords, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getUserCommerceAccountsCount(
			long userId, Long parentCommerceAccountId, int commerceSiteType,
			String keywords)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getUserCommerceAccountsCount(
			long userId, Long parentCommerceAccountId, int commerceSiteType,
			String keywords, Boolean active)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceAccount> searchCommerceAccounts(
			long companyId, long parentCommerceAccountId, String keywords,
			int type, Boolean active, int start, int end, Sort sort)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCommerceAccountsCount(
			long companyId, long parentCommerceAccountId, String keywords,
			int type, Boolean active)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CommerceAccount setActive(long commerceAccountId, boolean active)
		throws PortalException;

	public CommerceAccount updateCommerceAccount(
		CommerceAccount commerceAccount);

	public CommerceAccount updateCommerceAccount(
			long commerceAccountId, String name, boolean logo, byte[] logoBytes,
			String email, String taxId, boolean active,
			long defaultBillingAddressId, long defaultShippingAddressId,
			ServiceContext serviceContext)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CommerceAccount updateCommerceAccount(
			long commerceAccountId, String name, boolean logo, byte[] logoBytes,
			String email, String taxId, boolean active,
			long defaultBillingAddressId, long defaultShippingAddressId,
			String externalReferenceCode, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of Mueller (7.2.x), pass Default Billing/Shipping Ids
	 */
	@Deprecated
	public CommerceAccount updateCommerceAccount(
			long commerceAccountId, String name, boolean logo, byte[] logoBytes,
			String email, String taxId, boolean active,
			ServiceContext serviceContext)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CommerceAccount updateDefaultBillingAddress(
			long commerceAccountId, long commerceAddressId)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CommerceAccount updateDefaultShippingAddress(
			long commerceAccountId, long commerceAddressId)
		throws PortalException;

	/**
	 * @bridged
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Indexable(type = IndexableType.REINDEX)
	public CommerceAccount updateStatus(
			long userId, long commerceAccountId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException;

}