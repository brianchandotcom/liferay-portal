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

import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.service.base.CPInstanceUnitOfMeasureServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.math.BigDecimal;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CPInstanceUnitOfMeasure"
	},
	service = AopService.class
)
public class CPInstanceUnitOfMeasureServiceImpl
	extends CPInstanceUnitOfMeasureServiceBaseImpl {

	public CPInstanceUnitOfMeasure addCPInstanceUnitOfMeasure(
			long cpInstanceId, boolean active,
			BigDecimal incrementalOrderQuantity, String key,
			Map<Locale, String> nameMap, int precision, boolean primary,
			double priority, BigDecimal rate, String sku)
		throws PortalException {

		_checkCommerceCatalog(cpInstanceId, ActionKeys.UPDATE);

		return cpInstanceUnitOfMeasureLocalService.addCPInstanceUnitOfMeasure(
			getUserId(), cpInstanceId, active, incrementalOrderQuantity, key,
			nameMap, precision, primary, priority, rate, sku);
	}

	@Override
	public CPInstanceUnitOfMeasure fetchCPInstanceUnitOfMeasure(
			long cpInstanceId, String key)
		throws PortalException {

		_checkCommerceCatalog(cpInstanceId, ActionKeys.VIEW);

		return cpInstanceUnitOfMeasureLocalService.fetchCPInstanceUnitOfMeasure(
			cpInstanceId, key);
	}

	@Override
	public CPInstanceUnitOfMeasure getCPInstanceUnitOfMeasure(
			long cpInstanceId, String key)
		throws PortalException {

		_checkCommerceCatalog(cpInstanceId, ActionKeys.VIEW);

		return cpInstanceUnitOfMeasureLocalService.getCPInstanceUnitOfMeasure(
			cpInstanceId, key);
	}

	public List<CPInstanceUnitOfMeasure> getCPInstanceUnitOfMeasures(
			long cpInstanceId, int start, int end,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws PortalException {

		_checkCommerceCatalog(cpInstanceId, ActionKeys.VIEW);

		return cpInstanceUnitOfMeasureLocalService.getCPInstanceUnitOfMeasures(
			cpInstanceId, start, end, orderByComparator);
	}

	public CPInstanceUnitOfMeasure updateCPInstanceUnitOfMeasure(
			long cpInstanceUnitOfMeasureId, long cpInstanceId, boolean active,
			BigDecimal incrementalOrderQuantity, String key,
			Map<Locale, String> nameMap, int precision, boolean primary,
			double priority, BigDecimal rate, String sku)
		throws PortalException {

		_checkCommerceCatalog(cpInstanceId, ActionKeys.UPDATE);

		return cpInstanceUnitOfMeasureLocalService.
			updateCPInstanceUnitOfMeasure(
				cpInstanceUnitOfMeasureId, cpInstanceId, active,
				incrementalOrderQuantity, key, nameMap, precision, primary,
				priority, rate, sku);
	}

	private void _checkCommerceCatalog(long cpInstanceId, String actionId)
		throws PortalException {

		CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		CommerceCatalog commerceCatalog =
			_commerceCatalogLocalService.fetchCommerceCatalogByGroupId(
				cpInstance.getGroupId());

		if (commerceCatalog == null) {
			throw new PrincipalException();
		}

		_commerceCatalogModelResourcePermission.check(
			getPermissionChecker(), commerceCatalog, actionId);
	}

	@Reference
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CommerceCatalog)"
	)
	private ModelResourcePermission<CommerceCatalog>
		_commerceCatalogModelResourcePermission;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

}