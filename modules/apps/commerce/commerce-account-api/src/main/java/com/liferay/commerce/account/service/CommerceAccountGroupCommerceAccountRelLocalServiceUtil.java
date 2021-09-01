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

import com.liferay.commerce.account.model.CommerceAccountGroupCommerceAccountRel;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the local service utility for CommerceAccountGroupCommerceAccountRel. This utility wraps
 * <code>com.liferay.commerce.account.service.impl.CommerceAccountGroupCommerceAccountRelLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CommerceAccountGroupCommerceAccountRelLocalService
 * @generated
 */
public class CommerceAccountGroupCommerceAccountRelLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.account.service.impl.CommerceAccountGroupCommerceAccountRelLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceAccountGroupCommerceAccountRel
			addCommerceAccountGroupCommerceAccountRel(
				long commerceAccountGroupId, long commerceAccountId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceAccountGroupCommerceAccountRel(
			commerceAccountGroupId, commerceAccountId, serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	public static CommerceAccountGroupCommerceAccountRel
			addCommerceAccountGroupCommerceAccountRel(
				long commerceAccountGroupId, long commerceAccountId,
				String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceAccountGroupCommerceAccountRel(
			commerceAccountGroupId, commerceAccountId, externalReferenceCode,
			serviceContext);
	}

	public static CommerceAccountGroupCommerceAccountRel
			deleteCommerceAccountGroupCommerceAccountRel(
				CommerceAccountGroupCommerceAccountRel
					commerceAccountGroupCommerceAccountRel)
		throws PortalException {

		return getService().deleteCommerceAccountGroupCommerceAccountRel(
			commerceAccountGroupCommerceAccountRel);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	public static void
		deleteCommerceAccountGroupCommerceAccountRelByCAccountGroupId(
			long commerceAccountGroupId) {

		getService().
			deleteCommerceAccountGroupCommerceAccountRelByCAccountGroupId(
				commerceAccountGroupId);
	}

	public static CommerceAccountGroupCommerceAccountRel
		fetchCommerceAccountGroupCommerceAccountRel(
			long commerceAccountGroupId, long commerceAccountId) {

		return getService().fetchCommerceAccountGroupCommerceAccountRel(
			commerceAccountGroupId, commerceAccountId);
	}

	public static CommerceAccountGroupCommerceAccountRel
			getCommerceAccountGroupCommerceAccountRel(
				long commerceAccountGroupCommerceAccountRelId)
		throws PortalException {

		return getService().getCommerceAccountGroupCommerceAccountRel(
			commerceAccountGroupCommerceAccountRelId);
	}

	public static CommerceAccountGroupCommerceAccountRel
			getCommerceAccountGroupCommerceAccountRel(
				long commerceAccountGroupId, long commerceAccountId)
		throws PortalException {

		return getService().getCommerceAccountGroupCommerceAccountRel(
			commerceAccountGroupId, commerceAccountId);
	}

	public static List<CommerceAccountGroupCommerceAccountRel>
		getCommerceAccountGroupCommerceAccountRels(long commerceAccountId) {

		return getService().getCommerceAccountGroupCommerceAccountRels(
			commerceAccountId);
	}

	public static List<CommerceAccountGroupCommerceAccountRel>
		getCommerceAccountGroupCommerceAccountRels(
			long commerceAccountGroupId, int start, int end) {

		return getService().getCommerceAccountGroupCommerceAccountRels(
			commerceAccountGroupId, start, end);
	}

	public static int getCommerceAccountGroupCommerceAccountRelsCount(
		long commerceAccountGroupId) {

		return getService().getCommerceAccountGroupCommerceAccountRelsCount(
			commerceAccountGroupId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceAccountGroupCommerceAccountRelLocalService
		getService() {

		return _service;
	}

	private static volatile CommerceAccountGroupCommerceAccountRelLocalService
		_service;

}