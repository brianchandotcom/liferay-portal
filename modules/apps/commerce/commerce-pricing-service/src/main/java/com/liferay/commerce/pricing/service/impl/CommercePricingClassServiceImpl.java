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

package com.liferay.commerce.pricing.service.impl;

import com.liferay.commerce.pricing.constants.CommercePricingClassActionKeys;
import com.liferay.commerce.pricing.exception.NoSuchPricingClassException;
import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.service.base.CommercePricingClassServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Riccardo Alberti
 */
public class CommercePricingClassServiceImpl
	extends CommercePricingClassServiceBaseImpl {

	@Override
	public CommercePricingClass addCommercePricingClass(
			String externalReferenceCode, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, ServiceContext serviceContext)
		throws PortalException {

		_checkPortletResourcePermission(
			null, CommercePricingClassActionKeys.ADD_COMMERCE_PRICING_CLASS);

		PermissionChecker permissionChecker = getPermissionChecker();

		return commercePricingClassLocalService.addCommercePricingClass(
			externalReferenceCode, permissionChecker.getUserId(), titleMap,
			descriptionMap, serviceContext);
	}

	@Override
	public CommercePricingClass addOrUpdateCommercePricingClass(
			String externalReferenceCode, long commercePricingClassId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			ServiceContext serviceContext)
		throws PortalException {

		if (commercePricingClassId > 0) {
			try {
				return updateCommercePricingClass(
					commercePricingClassId, titleMap, descriptionMap,
					serviceContext);
			}
			catch (NoSuchPricingClassException noSuchPricingClassException) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to find pricing class with ID: " +
							commercePricingClassId,
						noSuchPricingClassException);
				}
			}
		}

		if (!Validator.isBlank(externalReferenceCode)) {
			CommercePricingClass commercePricingClass =
				commercePricingClassPersistence.fetchByC_ERC(
					serviceContext.getCompanyId(), externalReferenceCode);

			if (commercePricingClass != null) {
				return commercePricingClassLocalService.
					updateCommercePricingClass(
						commercePricingClassId, getUserId(), titleMap,
						descriptionMap, serviceContext);
			}
		}

		return addCommercePricingClass(
			externalReferenceCode, titleMap, descriptionMap, serviceContext);
	}

	@Override
	public CommercePricingClass deleteCommercePricingClass(
			long commercePricingClassId)
		throws PortalException {

		_commercePricingClassResourcePermission.check(
			getPermissionChecker(), commercePricingClassId, ActionKeys.DELETE);

		return commercePricingClassLocalService.deleteCommercePricingClass(
			commercePricingClassId);
	}

	@Override
	public CommercePricingClass fetchByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		CommercePricingClass commercePricingClass =
			commercePricingClassLocalService.fetchByExternalReferenceCode(
				externalReferenceCode, companyId);

		if (commercePricingClass != null) {
			_commercePricingClassResourcePermission.check(
				getPermissionChecker(), commercePricingClass, ActionKeys.VIEW);
		}

		return commercePricingClass;
	}

	@Override
	public CommercePricingClass fetchCommercePricingClass(
			long commercePricingClassId)
		throws PortalException {

		CommercePricingClass commercePricingClass =
			commercePricingClassLocalService.fetchCommercePricingClass(
				commercePricingClassId);

		if (commercePricingClass != null) {
			_commercePricingClassResourcePermission.check(
				getPermissionChecker(), commercePricingClass, ActionKeys.VIEW);
		}

		return commercePricingClass;
	}

	@Override
	public CommercePricingClass getCommercePricingClass(
			long commercePricingClassId)
		throws PortalException {

		_commercePricingClassResourcePermission.check(
			getPermissionChecker(), commercePricingClassId, ActionKeys.VIEW);

		return commercePricingClassLocalService.getCommercePricingClass(
			commercePricingClassId);
	}

	@Override
	public int getCommercePricingClassCountByCPDefinitionId(
			long cpDefinitionId, String title)
		throws PrincipalException {

		return commercePricingClassFinder.countByCPDefinitionId(
			cpDefinitionId, title, true);
	}

	@Override
	public List<CommercePricingClass> getCommercePricingClasses(
			long companyId, int start, int end,
			OrderByComparator<CommercePricingClass> orderByComparator)
		throws PortalException {

		return commercePricingClassPersistence.filterFindByCompanyId(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getCommercePricingClassesCount(long companyId)
		throws PortalException {

		return commercePricingClassPersistence.filterCountByCompanyId(
			companyId);
	}

	@Override
	public int getCommercePricingClassesCount(long cpDefinitionId, String title)
		throws PrincipalException {

		return commercePricingClassFinder.countByCPDefinitionId(
			cpDefinitionId, title, true);
	}

	@Override
	public List<CommercePricingClass> searchByCPDefinitionId(
			long cpDefinitionId, String title, int start, int end)
		throws PrincipalException {

		return commercePricingClassFinder.findByCPDefinitionId(
			cpDefinitionId, title, start, end, true);
	}

	@Override
	public BaseModelSearchResult<CommercePricingClass>
			searchCommercePricingClasses(
				long companyId, String keywords, int start, int end, Sort sort)
		throws PortalException {

		return commercePricingClassLocalService.searchCommercePricingClasses(
			companyId, keywords, start, end, sort);
	}

	@Override
	public CommercePricingClass updateCommercePricingClass(
			long commercePricingClassId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, ServiceContext serviceContext)
		throws PortalException {

		_commercePricingClassResourcePermission.check(
			getPermissionChecker(), commercePricingClassId, ActionKeys.UPDATE);

		return commercePricingClassLocalService.updateCommercePricingClass(
			commercePricingClassId, getUserId(), titleMap, descriptionMap,
			serviceContext);
	}

	@Override
	public CommercePricingClass updateCommercePricingClassExternalReferenceCode(
			String externalReferenceCode, long commercePricingClassId)
		throws PortalException {

		_commercePricingClassResourcePermission.check(
			getPermissionChecker(), commercePricingClassId, ActionKeys.UPDATE);

		return commercePricingClassLocalService.
			updateCommercePricingClassExternalReferenceCode(
				externalReferenceCode, commercePricingClassId);
	}

	private void _checkPortletResourcePermission(Group group, String actionId)
		throws PrincipalException {

		PortletResourcePermission portletResourcePermission =
			_commercePricingClassResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), group, actionId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePricingClassServiceImpl.class);

	private static volatile ModelResourcePermission<CommercePricingClass>
		_commercePricingClassResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommercePricingClassServiceImpl.class,
				"_commercePricingClassResourcePermission",
				CommercePricingClass.class);

	@ServiceReference(type = CompanyService.class)
	private CompanyService _companyService;

}