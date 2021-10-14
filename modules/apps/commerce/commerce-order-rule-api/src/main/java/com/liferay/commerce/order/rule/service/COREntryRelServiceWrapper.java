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

package com.liferay.commerce.order.rule.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link COREntryRelService}.
 *
 * @author Luca Pellizzon
 * @see COREntryRelService
 * @generated
 */
public class COREntryRelServiceWrapper
	implements COREntryRelService, ServiceWrapper<COREntryRelService> {

	public COREntryRelServiceWrapper(COREntryRelService corEntryRelService) {
		_corEntryRelService = corEntryRelService;
	}

	@Override
	public com.liferay.commerce.order.rule.model.COREntryRel addCOREntryRel(
			String className, long classPK, long corEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryRelService.addCOREntryRel(
			className, classPK, corEntryId);
	}

	@Override
	public void deleteCOREntryRel(long corEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_corEntryRelService.deleteCOREntryRel(corEntryRelId);
	}

	@Override
	public void deleteCOREntryRels(String className, long corEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_corEntryRelService.deleteCOREntryRels(className, corEntryId);
	}

	@Override
	public void deleteCOREntryRelsByCOREntryId(long corEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_corEntryRelService.deleteCOREntryRelsByCOREntryId(corEntryId);
	}

	@Override
	public com.liferay.commerce.order.rule.model.COREntryRel fetchCOREntryRel(
			String className, long classPK, long corEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryRelService.fetchCOREntryRel(
			className, classPK, corEntryId);
	}

	@Override
	public java.util.List<com.liferay.commerce.order.rule.model.COREntryRel>
			getAccountEntryCOREntryRels(
				long corEntryId, String keywords, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryRelService.getAccountEntryCOREntryRels(
			corEntryId, keywords, start, end);
	}

	@Override
	public int getAccountEntryCOREntryRelsCount(
			long corEntryId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryRelService.getAccountEntryCOREntryRelsCount(
			corEntryId, keywords);
	}

	@Override
	public java.util.List<com.liferay.commerce.order.rule.model.COREntryRel>
			getAccountGroupCOREntryRels(
				long corEntryId, String keywords, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryRelService.getAccountGroupCOREntryRels(
			corEntryId, keywords, start, end);
	}

	@Override
	public int getAccountGroupCOREntryRelsCount(
			long corEntryId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryRelService.getAccountGroupCOREntryRelsCount(
			corEntryId, keywords);
	}

	@Override
	public java.util.List<com.liferay.commerce.order.rule.model.COREntryRel>
			getCommerceChannelCOREntryRels(
				long corEntryId, String keywords, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryRelService.getCommerceChannelCOREntryRels(
			corEntryId, keywords, start, end);
	}

	@Override
	public int getCommerceChannelCOREntryRelsCount(
			long corEntryId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryRelService.getCommerceChannelCOREntryRelsCount(
			corEntryId, keywords);
	}

	@Override
	public java.util.List<com.liferay.commerce.order.rule.model.COREntryRel>
			getCommerceOrderTypeCOREntryRels(
				long corEntryId, String keywords, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryRelService.getCommerceOrderTypeCOREntryRels(
			corEntryId, keywords, start, end);
	}

	@Override
	public int getCommerceOrderTypeCOREntryRelsCount(
			long corEntryId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryRelService.getCommerceOrderTypeCOREntryRelsCount(
			corEntryId, keywords);
	}

	@Override
	public com.liferay.commerce.order.rule.model.COREntryRel getCOREntryRel(
			long corEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryRelService.getCOREntryRel(corEntryRelId);
	}

	@Override
	public java.util.List<com.liferay.commerce.order.rule.model.COREntryRel>
			getCOREntryRels(long corEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryRelService.getCOREntryRels(corEntryId);
	}

	@Override
	public java.util.List<com.liferay.commerce.order.rule.model.COREntryRel>
			getCOREntryRels(
				long corEntryId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.order.rule.model.COREntryRel>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryRelService.getCOREntryRels(
			corEntryId, start, end, orderByComparator);
	}

	@Override
	public int getCOREntryRelsCount(long corEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryRelService.getCOREntryRelsCount(corEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _corEntryRelService.getOSGiServiceIdentifier();
	}

	@Override
	public COREntryRelService getWrappedService() {
		return _corEntryRelService;
	}

	@Override
	public void setWrappedService(COREntryRelService corEntryRelService) {
		_corEntryRelService = corEntryRelService;
	}

	private COREntryRelService _corEntryRelService;

}