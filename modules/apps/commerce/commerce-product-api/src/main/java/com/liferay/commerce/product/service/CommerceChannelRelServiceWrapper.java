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

package com.liferay.commerce.product.service;

import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceChannelRelService}.
 *
 * @author Marco Leo
 * @see CommerceChannelRelService
 * @generated
 */
public class CommerceChannelRelServiceWrapper
	implements CommerceChannelRelService,
			   ServiceWrapper<CommerceChannelRelService> {

	public CommerceChannelRelServiceWrapper() {
		this(null);
	}

	public CommerceChannelRelServiceWrapper(
		CommerceChannelRelService commerceChannelRelService) {

		_commerceChannelRelService = commerceChannelRelService;
	}

	@Override
	public CommerceChannelRel addCommerceChannelRel(
			String className, long classPK, long commerceChannelId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelRelService.addCommerceChannelRel(
			className, classPK, commerceChannelId, serviceContext);
	}

	@Override
	public void deleteCommerceChannelRel(long commerceChannelRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceChannelRelService.deleteCommerceChannelRel(
			commerceChannelRelId);
	}

	@Override
	public void deleteCommerceChannelRels(String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceChannelRelService.deleteCommerceChannelRels(
			className, classPK);
	}

	@Override
	public CommerceChannelRel fetchCommerceChannelRel(
			String className, long classPK, long commerceChannelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelRelService.fetchCommerceChannelRel(
			className, classPK, commerceChannelId);
	}

	@Override
	public CommerceChannelRel getCommerceChannelRel(long commerceChannelRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelRelService.getCommerceChannelRel(
			commerceChannelRelId);
	}

	@Override
	public java.util.List<CommerceChannelRel> getCommerceChannelRels(
			long commerceChannelId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceChannelRel>
				orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelRelService.getCommerceChannelRels(
			commerceChannelId, start, end, orderByComparator);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public java.util.List<CommerceChannelRel> getCommerceChannelRels(
		String className, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceChannelRel>
			orderByComparator) {

		return _commerceChannelRelService.getCommerceChannelRels(
			className, classPK, start, end, orderByComparator);
	}

	@Override
	public java.util.List<CommerceChannelRel> getCommerceChannelRels(
		String className, long classPK, String name, int start, int end) {

		return _commerceChannelRelService.getCommerceChannelRels(
			className, classPK, name, start, end);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public java.util.List<CommerceChannelRel> getCommerceChannelRels(
		String className, long classPK, String classPKField, String name,
		int start, int end) {

		return _commerceChannelRelService.getCommerceChannelRels(
			className, classPK, classPKField, name, start, end);
	}

	@Override
	public int getCommerceChannelRelsCount(long commerceChannelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelRelService.getCommerceChannelRelsCount(
			commerceChannelId);
	}

	@Override
	public int getCommerceChannelRelsCount(String className, long classPK) {
		return _commerceChannelRelService.getCommerceChannelRelsCount(
			className, classPK);
	}

	@Override
	public int getCommerceChannelRelsCount(
		String className, long classPK, String name) {

		return _commerceChannelRelService.getCommerceChannelRelsCount(
			className, classPK, name);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public int getCommerceChannelRelsCount(
		String className, long classPK, String classPKField, String name) {

		return _commerceChannelRelService.getCommerceChannelRelsCount(
			className, classPK, classPKField, name);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceChannelRelService.getOSGiServiceIdentifier();
	}

	@Override
	public CommerceChannelRelService getWrappedService() {
		return _commerceChannelRelService;
	}

	@Override
	public void setWrappedService(
		CommerceChannelRelService commerceChannelRelService) {

		_commerceChannelRelService = commerceChannelRelService;
	}

	private CommerceChannelRelService _commerceChannelRelService;

}