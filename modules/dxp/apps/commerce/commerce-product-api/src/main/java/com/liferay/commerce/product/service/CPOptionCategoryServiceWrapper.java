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
 * Provides a wrapper for {@link CPOptionCategoryService}.
 *
 * @author Marco Leo
 * @see CPOptionCategoryService
 * @generated
 */
@ProviderType
public class CPOptionCategoryServiceWrapper implements CPOptionCategoryService,
	ServiceWrapper<CPOptionCategoryService> {
	public CPOptionCategoryServiceWrapper(
		CPOptionCategoryService cpOptionCategoryService) {
		_cpOptionCategoryService = cpOptionCategoryService;
	}

	@Override
	public com.liferay.commerce.product.model.CPOptionCategory addCPOptionCategory(
		java.util.Map<java.util.Locale, String> titleMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		double priority, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionCategoryService.addCPOptionCategory(titleMap,
			descriptionMap, priority, key, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPOptionCategory deleteCPOptionCategory(
		long cpOptionCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionCategoryService.deleteCPOptionCategory(cpOptionCategoryId);
	}

	@Override
	public com.liferay.commerce.product.model.CPOptionCategory fetchCPOptionCategory(
		long cpOptionCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionCategoryService.fetchCPOptionCategory(cpOptionCategoryId);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPOptionCategory> getCPOptionCategories(
		long groupId, int start, int end) {
		return _cpOptionCategoryService.getCPOptionCategories(groupId, start,
			end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPOptionCategory> getCPOptionCategories(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPOptionCategory> orderByComparator) {
		return _cpOptionCategoryService.getCPOptionCategories(groupId, start,
			end, orderByComparator);
	}

	@Override
	public int getCPOptionCategoriesCount(long groupId) {
		return _cpOptionCategoryService.getCPOptionCategoriesCount(groupId);
	}

	@Override
	public com.liferay.commerce.product.model.CPOptionCategory getCPOptionCategory(
		long cpOptionCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionCategoryService.getCPOptionCategory(cpOptionCategoryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpOptionCategoryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.product.model.CPOptionCategory updateCPOptionCategory(
		long cpOptionCategoryId,
		java.util.Map<java.util.Locale, String> titleMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		double priority, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionCategoryService.updateCPOptionCategory(cpOptionCategoryId,
			titleMap, descriptionMap, priority, key, serviceContext);
	}

	@Override
	public CPOptionCategoryService getWrappedService() {
		return _cpOptionCategoryService;
	}

	@Override
	public void setWrappedService(
		CPOptionCategoryService cpOptionCategoryService) {
		_cpOptionCategoryService = cpOptionCategoryService;
	}

	private CPOptionCategoryService _cpOptionCategoryService;
}