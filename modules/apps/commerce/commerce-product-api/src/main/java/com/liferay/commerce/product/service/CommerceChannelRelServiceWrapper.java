/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	@Override
	public java.util.List<CommerceChannelRel> getCommerceChannelRels(
			String className, long classPK, String name, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelRelService.getCommerceChannelRels(
			className, classPK, name, start, end);
	}

	@Override
	public int getCommerceChannelRelsCount(long commerceChannelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelRelService.getCommerceChannelRelsCount(
			commerceChannelId);
	}

	@Override
	public int getCommerceChannelRelsCount(String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelRelService.getCommerceChannelRelsCount(
			className, classPK);
	}

	@Override
	public int getCommerceChannelRelsCount(
			String className, long classPK, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelRelService.getCommerceChannelRelsCount(
			className, classPK, name);
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