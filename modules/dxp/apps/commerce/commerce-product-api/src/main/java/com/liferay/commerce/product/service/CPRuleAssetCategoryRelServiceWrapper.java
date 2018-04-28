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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPRuleAssetCategoryRelService}.
 *
 * @author Marco Leo
 * @see CPRuleAssetCategoryRelService
 * @generated
 */
@ProviderType
public class CPRuleAssetCategoryRelServiceWrapper
	implements CPRuleAssetCategoryRelService,
		ServiceWrapper<CPRuleAssetCategoryRelService> {
	public CPRuleAssetCategoryRelServiceWrapper(
		CPRuleAssetCategoryRelService cpRuleAssetCategoryRelService) {
		_cpRuleAssetCategoryRelService = cpRuleAssetCategoryRelService;
	}

	@Override
	public com.liferay.commerce.product.model.CPRuleAssetCategoryRel addCPRuleAssetCategoryRel(
		long cpRuleId, long assetCategoryId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleAssetCategoryRelService.addCPRuleAssetCategoryRel(cpRuleId,
			assetCategoryId, serviceContext);
	}

	@Override
	public void deleteCPRuleAssetCategoryRel(long cpRuleAssetCategoryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpRuleAssetCategoryRelService.deleteCPRuleAssetCategoryRel(cpRuleAssetCategoryRelId);
	}

	@Override
	public long[] getAssetCategoryIds(long cpRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleAssetCategoryRelService.getAssetCategoryIds(cpRuleId);
	}

	@Override
	public com.liferay.commerce.product.model.CPRuleAssetCategoryRel getCPRuleAssetCategoryRel(
		long cpRuleAssetCategoryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleAssetCategoryRelService.getCPRuleAssetCategoryRel(cpRuleAssetCategoryRelId);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPRuleAssetCategoryRel> getCPRuleAssetCategoryRels(
		long cpRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleAssetCategoryRelService.getCPRuleAssetCategoryRels(cpRuleId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpRuleAssetCategoryRelService.getOSGiServiceIdentifier();
	}

	@Override
	public CPRuleAssetCategoryRelService getWrappedService() {
		return _cpRuleAssetCategoryRelService;
	}

	@Override
	public void setWrappedService(
		CPRuleAssetCategoryRelService cpRuleAssetCategoryRelService) {
		_cpRuleAssetCategoryRelService = cpRuleAssetCategoryRelService;
	}

	private CPRuleAssetCategoryRelService _cpRuleAssetCategoryRelService;
}