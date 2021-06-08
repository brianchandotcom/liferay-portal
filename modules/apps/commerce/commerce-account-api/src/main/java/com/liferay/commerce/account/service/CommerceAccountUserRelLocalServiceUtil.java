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
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the local service utility for CommerceAccountUserRel. This utility wraps
 * <code>com.liferay.commerce.account.service.impl.CommerceAccountUserRelLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CommerceAccountUserRelLocalService
 * @generated
 */
public class CommerceAccountUserRelLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.account.service.impl.CommerceAccountUserRelLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceAccountUserRel addCommerceAccountUserRel(
		CommerceAccountUserRel commerceAccountUserRel) {

		return getService().addCommerceAccountUserRel(commerceAccountUserRel);
	}

	public static CommerceAccountUserRel addCommerceAccountUserRel(
			long commerceAccountId, long commerceAccountUserId, long[] roleIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceAccountUserRel(
			commerceAccountId, commerceAccountUserId, roleIds, serviceContext);
	}

	public static CommerceAccountUserRel addCommerceAccountUserRel(
			long commerceAccountId, long commerceAccountUserId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceAccountUserRel(
			commerceAccountId, commerceAccountUserId, serviceContext);
	}

	public static void addCommerceAccountUserRels(
			long commerceAccountId, long[] userIds, String[] emailAddresses,
			long[] roleIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		getService().addCommerceAccountUserRels(
			commerceAccountId, userIds, emailAddresses, roleIds,
			serviceContext);
	}

	public static void addDefaultRoles(long userId) throws PortalException {
		getService().addDefaultRoles(userId);
	}

	public static CommerceAccountUserRel createCommerceAccountUserRel(
		com.liferay.commerce.account.service.persistence.
			CommerceAccountUserRelPK commerceAccountUserRelPK) {

		return getService().createCommerceAccountUserRel(
			commerceAccountUserRelPK);
	}

	public static CommerceAccountUserRel deleteCommerceAccountUserRel(
		CommerceAccountUserRel commerceAccountUserRel) {

		return getService().deleteCommerceAccountUserRel(
			commerceAccountUserRel);
	}

	public static CommerceAccountUserRel deleteCommerceAccountUserRel(
			com.liferay.commerce.account.service.persistence.
				CommerceAccountUserRelPK commerceAccountUserRelPK)
		throws PortalException {

		return getService().deleteCommerceAccountUserRel(
			commerceAccountUserRelPK);
	}

	public static void deleteCommerceAccountUserRels(
			long commerceAccountId, long[] userIds)
		throws PortalException {

		getService().deleteCommerceAccountUserRels(commerceAccountId, userIds);
	}

	public static void deleteCommerceAccountUserRelsByCommerceAccountId(
		long commerceAccountId) {

		getService().deleteCommerceAccountUserRelsByCommerceAccountId(
			commerceAccountId);
	}

	public static void deleteCommerceAccountUserRelsByCommerceAccountUserId(
		long userId) {

		getService().deleteCommerceAccountUserRelsByCommerceAccountUserId(
			userId);
	}

	public static CommerceAccountUserRel fetchCommerceAccountUserRel(
		com.liferay.commerce.account.service.persistence.
			CommerceAccountUserRelPK commerceAccountUserRelPK) {

		return getService().fetchCommerceAccountUserRel(
			commerceAccountUserRelPK);
	}

	public static CommerceAccountUserRel getCommerceAccountUserRel(
			com.liferay.commerce.account.service.persistence.
				CommerceAccountUserRelPK commerceAccountUserRelPK)
		throws PortalException {

		return getService().getCommerceAccountUserRel(commerceAccountUserRelPK);
	}

	public static List<CommerceAccountUserRel> getCommerceAccountUserRels(
		int start, int end) {

		return getService().getCommerceAccountUserRels(start, end);
	}

	public static List<CommerceAccountUserRel> getCommerceAccountUserRels(
		long commerceAccountId) {

		return getService().getCommerceAccountUserRels(commerceAccountId);
	}

	public static List<CommerceAccountUserRel> getCommerceAccountUserRels(
		long commerceAccountId, int start, int end) {

		return getService().getCommerceAccountUserRels(
			commerceAccountId, start, end);
	}

	public static List<CommerceAccountUserRel>
		getCommerceAccountUserRelsByCommerceAccountUserId(
			long commerceAccountUserId) {

		return getService().getCommerceAccountUserRelsByCommerceAccountUserId(
			commerceAccountUserId);
	}

	public static int getCommerceAccountUserRelsCount() {
		return getService().getCommerceAccountUserRelsCount();
	}

	public static int getCommerceAccountUserRelsCount(long commerceAccountId) {
		return getService().getCommerceAccountUserRelsCount(commerceAccountId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceAccountUserRel inviteUser(
			long commerceAccountId, String emailAddress, long[] roleIds,
			String userExternalReferenceCode,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().inviteUser(
			commerceAccountId, emailAddress, roleIds, userExternalReferenceCode,
			serviceContext);
	}

	public static CommerceAccountUserRel updateCommerceAccountUserRel(
		CommerceAccountUserRel commerceAccountUserRel) {

		return getService().updateCommerceAccountUserRel(
			commerceAccountUserRel);
	}

	public static CommerceAccountUserRelLocalService getService() {
		return _service;
	}

	private static volatile CommerceAccountUserRelLocalService _service;

}