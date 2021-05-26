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
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the local service utility for CommerceAccountGroupRel. This utility wraps
 * <code>com.liferay.commerce.account.service.impl.CommerceAccountGroupRelLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CommerceAccountGroupRelLocalService
 * @generated
 */
public class CommerceAccountGroupRelLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.account.service.impl.CommerceAccountGroupRelLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceAccountGroupRel addCommerceAccountGroupRel(
		CommerceAccountGroupRel commerceAccountGroupRel) {

		return getService().addCommerceAccountGroupRel(commerceAccountGroupRel);
	}

	public static CommerceAccountGroupRel addCommerceAccountGroupRel(
			String className, long classPK, long commerceAccountGroupId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceAccountGroupRel(
			className, classPK, commerceAccountGroupId, serviceContext);
	}

	public static CommerceAccountGroupRel deleteCommerceAccountGroupRel(
		CommerceAccountGroupRel commerceAccountGroupRel) {

		return getService().deleteCommerceAccountGroupRel(
			commerceAccountGroupRel);
	}

	public static CommerceAccountGroupRel deleteCommerceAccountGroupRel(
			long commerceAccountGroupRelId)
		throws PortalException {

		return getService().deleteCommerceAccountGroupRel(
			commerceAccountGroupRelId);
	}

	public static void deleteCommerceAccountGroupRels(
		long commerceAccountGroupId) {

		getService().deleteCommerceAccountGroupRels(commerceAccountGroupId);
	}

	public static void deleteCommerceAccountGroupRels(
		String className, long classPK) {

		getService().deleteCommerceAccountGroupRels(className, classPK);
	}

	public static CommerceAccountGroupRel fetchCommerceAccountGroupRel(
		long commerceAccountGroupRelId) {

		return getService().fetchCommerceAccountGroupRel(
			commerceAccountGroupRelId);
	}

	public static CommerceAccountGroupRel getCommerceAccountGroupRel(
			long commerceAccountGroupRelId)
		throws PortalException {

		return getService().getCommerceAccountGroupRel(
			commerceAccountGroupRelId);
	}

	public static List<CommerceAccountGroupRel> getCommerceAccountGroupRels(
		int start, int end) {

		return getService().getCommerceAccountGroupRels(start, end);
	}

	public static List<CommerceAccountGroupRel> getCommerceAccountGroupRels(
		long commerceAccountGroupId, int start, int end,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator) {

		return getService().getCommerceAccountGroupRels(
			commerceAccountGroupId, start, end, orderByComparator);
	}

	public static List<CommerceAccountGroupRel> getCommerceAccountGroupRels(
		String className, long classPK, int start, int end,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator) {

		return getService().getCommerceAccountGroupRels(
			className, classPK, start, end, orderByComparator);
	}

	public static int getCommerceAccountGroupRelsCount() {
		return getService().getCommerceAccountGroupRelsCount();
	}

	public static int getCommerceAccountGroupRelsCount(
		long commerceAccountGroupId) {

		return getService().getCommerceAccountGroupRelsCount(
			commerceAccountGroupId);
	}

	public static int getCommerceAccountGroupRelsCount(
		String className, long classPK) {

		return getService().getCommerceAccountGroupRelsCount(
			className, classPK);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceAccountGroupRelLocalService getService() {
		return _service;
	}

	private static volatile CommerceAccountGroupRelLocalService _service;

}