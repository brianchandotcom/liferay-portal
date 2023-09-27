/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.term.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceTermEntryRelService}.
 *
 * @author Luca Pellizzon
 * @see CommerceTermEntryRelService
 * @generated
 */
public class CommerceTermEntryRelServiceWrapper
	implements CommerceTermEntryRelService,
			   ServiceWrapper<CommerceTermEntryRelService> {

	public CommerceTermEntryRelServiceWrapper() {
		this(null);
	}

	public CommerceTermEntryRelServiceWrapper(
		CommerceTermEntryRelService commerceTermEntryRelService) {

		_commerceTermEntryRelService = commerceTermEntryRelService;
	}

	@Override
	public com.liferay.commerce.term.model.CommerceTermEntryRel
			addCommerceTermEntryRel(
				String className, long classPK, long commerceTermEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTermEntryRelService.addCommerceTermEntryRel(
			className, classPK, commerceTermEntryId);
	}

	@Override
	public void deleteCommerceTermEntryRel(long commerceTermEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceTermEntryRelService.deleteCommerceTermEntryRel(
			commerceTermEntryRelId);
	}

	@Override
	public void deleteCommerceTermEntryRels(
			String className, long commerceTermEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceTermEntryRelService.deleteCommerceTermEntryRels(
			className, commerceTermEntryId);
	}

	@Override
	public void deleteCommerceTermEntryRelsByCommerceTermEntryId(
			long commerceTermEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceTermEntryRelService.
			deleteCommerceTermEntryRelsByCommerceTermEntryId(
				commerceTermEntryId);
	}

	@Override
	public com.liferay.commerce.term.model.CommerceTermEntryRel
			fetchCommerceTermEntryRel(
				String className, long classPK, long commerceTermEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTermEntryRelService.fetchCommerceTermEntryRel(
			className, classPK, commerceTermEntryId);
	}

	@Override
	public java.util.List<com.liferay.commerce.term.model.CommerceTermEntryRel>
			getCommerceOrderTypeCommerceTermEntryRels(
				long commerceTermEntryId, String keywords, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTermEntryRelService.
			getCommerceOrderTypeCommerceTermEntryRels(
				commerceTermEntryId, keywords, start, end);
	}

	@Override
	public int getCommerceOrderTypeCommerceTermEntryRelsCount(
			long commerceTermEntryId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTermEntryRelService.
			getCommerceOrderTypeCommerceTermEntryRelsCount(
				commerceTermEntryId, keywords);
	}

	@Override
	public com.liferay.commerce.term.model.CommerceTermEntryRel
			getCommerceTermEntryRel(long commerceTermEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTermEntryRelService.getCommerceTermEntryRel(
			commerceTermEntryRelId);
	}

	@Override
	public java.util.List<com.liferay.commerce.term.model.CommerceTermEntryRel>
			getCommerceTermEntryRels(long commerceTermEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTermEntryRelService.getCommerceTermEntryRels(
			commerceTermEntryId);
	}

	@Override
	public java.util.List<com.liferay.commerce.term.model.CommerceTermEntryRel>
			getCommerceTermEntryRels(
				long commerceTermEntryId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.term.model.CommerceTermEntryRel>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTermEntryRelService.getCommerceTermEntryRels(
			commerceTermEntryId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceTermEntryRelsCount(long commerceTermEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTermEntryRelService.getCommerceTermEntryRelsCount(
			commerceTermEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceTermEntryRelService.getOSGiServiceIdentifier();
	}

	@Override
	public CommerceTermEntryRelService getWrappedService() {
		return _commerceTermEntryRelService;
	}

	@Override
	public void setWrappedService(
		CommerceTermEntryRelService commerceTermEntryRelService) {

		_commerceTermEntryRelService = commerceTermEntryRelService;
	}

	private CommerceTermEntryRelService _commerceTermEntryRelService;

}