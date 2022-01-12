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

package com.liferay.commerce.pricing.service;

import com.liferay.commerce.pricing.model.CommercePriceModifierRel;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommercePriceModifierRelService}.
 *
 * @author Riccardo Alberti
 * @see CommercePriceModifierRelService
 * @generated
 */
public class CommercePriceModifierRelServiceWrapper
	implements CommercePriceModifierRelService,
			   ServiceWrapper<CommercePriceModifierRelService> {

	public CommercePriceModifierRelServiceWrapper() {
		this(null);
	}

	public CommercePriceModifierRelServiceWrapper(
		CommercePriceModifierRelService commercePriceModifierRelService) {

		_commercePriceModifierRelService = commercePriceModifierRelService;
	}

	@Override
	public CommercePriceModifierRel addCommercePriceModifierRel(
			long commercePriceModifierId, String className, long classPK,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceModifierRelService.addCommercePriceModifierRel(
			commercePriceModifierId, className, classPK, serviceContext);
	}

	@Override
	public void deleteCommercePriceModifierRel(long commercePriceModifierRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commercePriceModifierRelService.deleteCommercePriceModifierRel(
			commercePriceModifierRelId);
	}

	@Override
	public CommercePriceModifierRel fetchCommercePriceModifierRel(
			long commercePriceModifierId, String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceModifierRelService.fetchCommercePriceModifierRel(
			commercePriceModifierId, className, classPK);
	}

	@Override
	public java.util.List<CommercePriceModifierRel>
		getCategoriesCommercePriceModifierRels(
			long commercePriceModifierId, String name, int start, int end) {

		return _commercePriceModifierRelService.
			getCategoriesCommercePriceModifierRels(
				commercePriceModifierId, name, start, end);
	}

	@Override
	public int getCategoriesCommercePriceModifierRelsCount(
		long commercePriceModifierId, String name) {

		return _commercePriceModifierRelService.
			getCategoriesCommercePriceModifierRelsCount(
				commercePriceModifierId, name);
	}

	@Override
	public long[] getClassPKs(long commercePriceModifierRelId, String className)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceModifierRelService.getClassPKs(
			commercePriceModifierRelId, className);
	}

	@Override
	public CommercePriceModifierRel getCommercePriceModifierRel(
			long commercePriceModifierRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceModifierRelService.getCommercePriceModifierRel(
			commercePriceModifierRelId);
	}

	@Override
	public java.util.List<CommercePriceModifierRel>
			getCommercePriceModifierRels(
				long commercePriceModifierRelId, String className)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceModifierRelService.getCommercePriceModifierRels(
			commercePriceModifierRelId, className);
	}

	@Override
	public java.util.List<CommercePriceModifierRel>
			getCommercePriceModifierRels(
				long commercePriceModifierRelId, String className, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommercePriceModifierRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceModifierRelService.getCommercePriceModifierRels(
			commercePriceModifierRelId, className, start, end,
			orderByComparator);
	}

	@Override
	public int getCommercePriceModifierRelsCount(
			long commercePriceModifierRelId, String className)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceModifierRelService.
			getCommercePriceModifierRelsCount(
				commercePriceModifierRelId, className);
	}

	@Override
	public java.util.List<CommercePriceModifierRel>
		getCommercePriceModifiersRels(String className, long classPK) {

		return _commercePriceModifierRelService.getCommercePriceModifiersRels(
			className, classPK);
	}

	@Override
	public java.util.List<CommercePriceModifierRel>
		getCommercePricingClassesCommercePriceModifierRels(
			long commercePriceModifierId, String title, int start, int end) {

		return _commercePriceModifierRelService.
			getCommercePricingClassesCommercePriceModifierRels(
				commercePriceModifierId, title, start, end);
	}

	@Override
	public int getCommercePricingClassesCommercePriceModifierRelsCount(
		long commercePriceModifierId, String title) {

		return _commercePriceModifierRelService.
			getCommercePricingClassesCommercePriceModifierRelsCount(
				commercePriceModifierId, title);
	}

	@Override
	public java.util.List<CommercePriceModifierRel>
		getCPDefinitionsCommercePriceModifierRels(
			long commercePriceModifierId, String name, String languageId,
			int start, int end) {

		return _commercePriceModifierRelService.
			getCPDefinitionsCommercePriceModifierRels(
				commercePriceModifierId, name, languageId, start, end);
	}

	@Override
	public int getCPDefinitionsCommercePriceModifierRelsCount(
		long commercePriceModifierId, String name, String languageId) {

		return _commercePriceModifierRelService.
			getCPDefinitionsCommercePriceModifierRelsCount(
				commercePriceModifierId, name, languageId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commercePriceModifierRelService.getOSGiServiceIdentifier();
	}

	@Override
	public CommercePriceModifierRelService getWrappedService() {
		return _commercePriceModifierRelService;
	}

	@Override
	public void setWrappedService(
		CommercePriceModifierRelService commercePriceModifierRelService) {

		_commercePriceModifierRelService = commercePriceModifierRelService;
	}

	private CommercePriceModifierRelService _commercePriceModifierRelService;

}