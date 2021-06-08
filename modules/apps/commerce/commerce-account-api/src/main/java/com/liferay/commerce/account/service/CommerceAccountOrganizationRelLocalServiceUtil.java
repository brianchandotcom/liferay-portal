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
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the local service utility for CommerceAccountOrganizationRel. This utility wraps
 * <code>com.liferay.commerce.account.service.impl.CommerceAccountOrganizationRelLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CommerceAccountOrganizationRelLocalService
 * @generated
 */
public class CommerceAccountOrganizationRelLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.account.service.impl.CommerceAccountOrganizationRelLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceAccountOrganizationRel
		addCommerceAccountOrganizationRel(
			CommerceAccountOrganizationRel commerceAccountOrganizationRel) {

		return getService().addCommerceAccountOrganizationRel(
			commerceAccountOrganizationRel);
	}

	public static CommerceAccountOrganizationRel
			addCommerceAccountOrganizationRel(
				long commerceAccountId, long organizationId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceAccountOrganizationRel(
			commerceAccountId, organizationId, serviceContext);
	}

	public static void addCommerceAccountOrganizationRels(
			long commerceAccountId, long[] organizationIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		getService().addCommerceAccountOrganizationRels(
			commerceAccountId, organizationIds, serviceContext);
	}

	public static CommerceAccountOrganizationRel
		createCommerceAccountOrganizationRel(
			com.liferay.commerce.account.service.persistence.
				CommerceAccountOrganizationRelPK
					commerceAccountOrganizationRelPK) {

		return getService().createCommerceAccountOrganizationRel(
			commerceAccountOrganizationRelPK);
	}

	public static CommerceAccountOrganizationRel
		deleteCommerceAccountOrganizationRel(
			CommerceAccountOrganizationRel commerceAccountOrganizationRel) {

		return getService().deleteCommerceAccountOrganizationRel(
			commerceAccountOrganizationRel);
	}

	public static CommerceAccountOrganizationRel
			deleteCommerceAccountOrganizationRel(
				com.liferay.commerce.account.service.persistence.
					CommerceAccountOrganizationRelPK
						commerceAccountOrganizationRelPK)
		throws PortalException {

		return getService().deleteCommerceAccountOrganizationRel(
			commerceAccountOrganizationRelPK);
	}

	public static void deleteCommerceAccountOrganizationRels(
			long commerceAccountId, long[] organizationIds)
		throws PortalException {

		getService().deleteCommerceAccountOrganizationRels(
			commerceAccountId, organizationIds);
	}

	public static void deleteCommerceAccountOrganizationRelsByCommerceAccountId(
		long commerceAccountId) {

		getService().deleteCommerceAccountOrganizationRelsByCommerceAccountId(
			commerceAccountId);
	}

	public static void deleteCommerceAccountOrganizationRelsByOrganizationId(
		long organizationId) {

		getService().deleteCommerceAccountOrganizationRelsByOrganizationId(
			organizationId);
	}

	public static CommerceAccountOrganizationRel
		fetchCommerceAccountOrganizationRel(
			com.liferay.commerce.account.service.persistence.
				CommerceAccountOrganizationRelPK
					commerceAccountOrganizationRelPK) {

		return getService().fetchCommerceAccountOrganizationRel(
			commerceAccountOrganizationRelPK);
	}

	public static CommerceAccountOrganizationRel
			getCommerceAccountOrganizationRel(
				com.liferay.commerce.account.service.persistence.
					CommerceAccountOrganizationRelPK
						commerceAccountOrganizationRelPK)
		throws PortalException {

		return getService().getCommerceAccountOrganizationRel(
			commerceAccountOrganizationRelPK);
	}

	public static List<CommerceAccountOrganizationRel>
		getCommerceAccountOrganizationRels(int start, int end) {

		return getService().getCommerceAccountOrganizationRels(start, end);
	}

	public static List<CommerceAccountOrganizationRel>
		getCommerceAccountOrganizationRels(long commerceAccountId) {

		return getService().getCommerceAccountOrganizationRels(
			commerceAccountId);
	}

	public static List<CommerceAccountOrganizationRel>
		getCommerceAccountOrganizationRels(
			long commerceAccountId, int start, int end) {

		return getService().getCommerceAccountOrganizationRels(
			commerceAccountId, start, end);
	}

	public static List<CommerceAccountOrganizationRel>
		getCommerceAccountOrganizationRelsByOrganizationId(
			long organizationId, int start, int end) {

		return getService().getCommerceAccountOrganizationRelsByOrganizationId(
			organizationId, start, end);
	}

	public static int getCommerceAccountOrganizationRelsByOrganizationIdCount(
		long organizationId) {

		return getService().
			getCommerceAccountOrganizationRelsByOrganizationIdCount(
				organizationId);
	}

	public static int getCommerceAccountOrganizationRelsCount() {
		return getService().getCommerceAccountOrganizationRelsCount();
	}

	public static int getCommerceAccountOrganizationRelsCount(
		long commerceAccountId) {

		return getService().getCommerceAccountOrganizationRelsCount(
			commerceAccountId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceAccountOrganizationRel
		updateCommerceAccountOrganizationRel(
			CommerceAccountOrganizationRel commerceAccountOrganizationRel) {

		return getService().updateCommerceAccountOrganizationRel(
			commerceAccountOrganizationRel);
	}

	public static CommerceAccountOrganizationRelLocalService getService() {
		return _service;
	}

	private static volatile CommerceAccountOrganizationRelLocalService _service;

}