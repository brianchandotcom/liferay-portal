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

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.service.base.CommerceChannelRelServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceChannelRelServiceImpl
	extends CommerceChannelRelServiceBaseImpl {

	@Override
	public CommerceChannelRel addCommerceChannelRel(
			String className, long classPK, long commerceChannelId,
			ServiceContext serviceContext)
		throws PortalException {

		return commerceChannelRelLocalService.addCommerceChannelRel(
			className, classPK, commerceChannelId, serviceContext);
	}

	@Override
	public void deleteCommerceChannelRel(long commerceChannelRelId)
		throws PortalException {

		commerceChannelRelLocalService.deleteCommerceChannelRel(
			commerceChannelRelId);
	}

	@Override
	public void deleteCommerceChannelRels(String className, long classPK) {
		commerceChannelRelLocalService.deleteCommerceChannelRels(
			className, classPK);
	}

	@Override
	public CommerceChannelRel fetchCommerceChannelRel(
		String className, long classPK, long commerceChannelId) {

		return commerceChannelRelLocalService.fetchCommerceChannelRel(
			className, classPK, commerceChannelId);
	}

	@Override
	public CommerceChannelRel getCommerceChannelRel(long commerceChannelRelId)
		throws PortalException {

		return commerceChannelRelLocalService.getCommerceChannelRel(
			commerceChannelRelId);
	}

	@Override
	public List<CommerceChannelRel> getCommerceChannelRels(
		long commerceChannelId, int start, int end,
		OrderByComparator<CommerceChannelRel> orderByComparator) {

		return commerceChannelRelLocalService.getCommerceChannelRels(
			commerceChannelId, start, end, orderByComparator);
	}

	@Override
	public List<CommerceChannelRel> getCommerceChannelRels(
		String className, long classPK, int start, int end,
		OrderByComparator<CommerceChannelRel> orderByComparator) {

		return commerceChannelRelLocalService.getCommerceChannelRels(
			className, classPK, start, end, orderByComparator);
	}

	@Override
	public List<CommerceChannelRel> getCommerceChannelRels(
		String className, long classPK, String classPKField, String name,
		int start, int end) {

		return commerceChannelRelFinder.findByC_C(
			className, classPK, classPKField, name, start, end, true);
	}

	@Override
	public int getCommerceChannelRelsCount(long commerceChannelId) {
		return commerceChannelRelLocalService.getCommerceChannelRelsCount(
			commerceChannelId);
	}

	@Override
	public int getCommerceChannelRelsCount(String className, long classPK) {
		return commerceChannelRelLocalService.getCommerceChannelRelsCount(
			className, classPK);
	}

	@Override
	public int getCommerceChannelRelsCount(
		String className, long classPK, String classPKField, String name) {

		return commerceChannelRelFinder.countByC_C(
			className, classPK, classPKField, name, true);
	}

}