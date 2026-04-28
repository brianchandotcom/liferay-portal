/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service;

import com.liferay.commerce.model.CommerceOrderAttachment;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.InputStream;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for CommerceOrderAttachment. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderAttachmentServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CommerceOrderAttachmentService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.service.impl.CommerceOrderAttachmentServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the commerce order attachment remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CommerceOrderAttachmentServiceUtil} if injection and service tracking are not available.
	 */
	public CommerceOrderAttachment addCommerceOrderAttachment(
			long commerceOrderId, double priority, boolean restricted,
			String title, String type, String fileName, InputStream inputStream)
		throws PortalException;

	public void deleteCommerceOrderAttachment(long commerceOrderAttachmentId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceOrderAttachment fetchCommerceOrderAttachment(
			long commerceOrderAttachmentId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceOrderAttachment
			fetchCommerceOrderAttachmentByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceOrderAttachment getCommerceOrderAttachment(
			long commerceOrderAttachmentId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrderAttachment> getCommerceOrderAttachments(
			long commerceOrderId, boolean restricted, int start, int end,
			OrderByComparator<CommerceOrderAttachment> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrderAttachment> getCommerceOrderAttachments(
			long commerceOrderId, int start, int end,
			OrderByComparator<CommerceOrderAttachment> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceOrderAttachmentsCount(long commerceOrderId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceOrderAttachmentsCount(
			long commerceOrderId, boolean restricted)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public CommerceOrderAttachment updateCommerceOrderAttachment(
			long commerceOrderAttachmentId, double priority, boolean restricted,
			String title, String type)
		throws PortalException;

}
// LIFERAY-SERVICE-BUILDER-HASH:-2090663514