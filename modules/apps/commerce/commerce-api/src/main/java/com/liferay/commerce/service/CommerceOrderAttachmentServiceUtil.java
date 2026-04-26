/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service;

import com.liferay.commerce.model.CommerceOrderAttachment;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.InputStream;

import java.util.List;

/**
 * Provides the remote service utility for CommerceOrderAttachment. This utility wraps
 * <code>com.liferay.commerce.service.impl.CommerceOrderAttachmentServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderAttachmentService
 * @generated
 */
public class CommerceOrderAttachmentServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.service.impl.CommerceOrderAttachmentServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceOrderAttachment addCommerceOrderAttachment(
			long commerceOrderId, double priority, boolean restricted,
			String title, String type, String fileName, InputStream inputStream)
		throws PortalException {

		return getService().addCommerceOrderAttachment(
			commerceOrderId, priority, restricted, title, type, fileName,
			inputStream);
	}

	public static void deleteCommerceOrderAttachment(
			long commerceOrderAttachmentId)
		throws PortalException {

		getService().deleteCommerceOrderAttachment(commerceOrderAttachmentId);
	}

	public static CommerceOrderAttachment fetchCommerceOrderAttachment(
			long commerceOrderAttachmentId)
		throws PortalException {

		return getService().fetchCommerceOrderAttachment(
			commerceOrderAttachmentId);
	}

	public static CommerceOrderAttachment
			fetchCommerceOrderAttachmentByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws PortalException {

		return getService().fetchCommerceOrderAttachmentByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	public static CommerceOrderAttachment getCommerceOrderAttachment(
			long commerceOrderAttachmentId)
		throws PortalException {

		return getService().getCommerceOrderAttachment(
			commerceOrderAttachmentId);
	}

	public static List<CommerceOrderAttachment> getCommerceOrderAttachments(
			long commerceOrderId, boolean restricted, int start, int end,
			OrderByComparator<CommerceOrderAttachment> orderByComparator)
		throws PortalException {

		return getService().getCommerceOrderAttachments(
			commerceOrderId, restricted, start, end, orderByComparator);
	}

	public static List<CommerceOrderAttachment> getCommerceOrderAttachments(
			long commerceOrderId, int start, int end,
			OrderByComparator<CommerceOrderAttachment> orderByComparator)
		throws PortalException {

		return getService().getCommerceOrderAttachments(
			commerceOrderId, start, end, orderByComparator);
	}

	public static int getCommerceOrderAttachmentsCount(long commerceOrderId)
		throws PortalException {

		return getService().getCommerceOrderAttachmentsCount(commerceOrderId);
	}

	public static int getCommerceOrderAttachmentsCount(
			long commerceOrderId, boolean restricted)
		throws PortalException {

		return getService().getCommerceOrderAttachmentsCount(
			commerceOrderId, restricted);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceOrderAttachment updateCommerceOrderAttachment(
			long commerceOrderAttachmentId, double priority, boolean restricted,
			String title, String type)
		throws PortalException {

		return getService().updateCommerceOrderAttachment(
			commerceOrderAttachmentId, priority, restricted, title, type);
	}

	public static CommerceOrderAttachmentService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<CommerceOrderAttachmentService>
		_serviceSnapshot = new Snapshot<>(
			CommerceOrderAttachmentServiceUtil.class,
			CommerceOrderAttachmentService.class);

}
// LIFERAY-SERVICE-BUILDER-HASH:2127178852