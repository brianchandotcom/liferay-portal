/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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