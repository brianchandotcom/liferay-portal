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

package com.liferay.commerce.price.list.service.impl;

import com.liferay.commerce.price.list.constants.CommercePriceListActionKeys;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.exception.NoSuchPriceListException;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.base.CommercePriceListServiceBaseImpl;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Alessio Antonio Rendina
 * @author Zoltán Takács
 */
public class CommercePriceListServiceImpl
	extends CommercePriceListServiceBaseImpl {

	@Override
	public CommercePriceList addCommercePriceList(
			long groupId, long userId, long commerceCurrencyId,
			boolean netPrice, long parentCommercePriceListId, String name,
			double priority, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		return addCommercePriceList(
			groupId, userId, commerceCurrencyId, netPrice,
			parentCommercePriceListId, name, priority, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, null, neverExpire,
			serviceContext);
	}

	@Override
	public CommercePriceList addCommercePriceList(
			long groupId, long userId, long commerceCurrencyId,
			boolean netPrice, long parentCommercePriceListId, String name,
			double priority, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, String externalReferenceCode,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		_checkPortletResourcePermission(
			groupId, CommercePriceListActionKeys.ADD_COMMERCE_PRICE_LIST);

		return commercePriceListLocalService.addCommercePriceList(
			groupId, userId, commerceCurrencyId, netPrice,
			parentCommercePriceListId, name, priority, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, externalReferenceCode,
			neverExpire, serviceContext);
	}

	@Override
	public CommercePriceList addCommercePriceList(
			long groupId, long userId, long commerceCurrencyId,
			boolean netPrice, String type, long parentCommercePriceListId,
			boolean catalogBasePriceList, String name, double priority,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			String externalReferenceCode, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		_checkPortletResourcePermission(
			groupId, CommercePriceListActionKeys.ADD_COMMERCE_PRICE_LIST);

		return commercePriceListLocalService.addCommercePriceList(
			groupId, userId, commerceCurrencyId, netPrice, type,
			parentCommercePriceListId, catalogBasePriceList, name, priority,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			externalReferenceCode, neverExpire, serviceContext);
	}

	@Override
	public CommercePriceList addCommercePriceList(
			long groupId, long userId, long commerceCurrencyId,
			long parentCommercePriceListId, String name, double priority,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		return addCommercePriceList(
			groupId, userId, commerceCurrencyId, parentCommercePriceListId,
			name, priority, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, null, neverExpire, serviceContext);
	}

	@Override
	public CommercePriceList addCommercePriceList(
			long groupId, long userId, long commerceCurrencyId,
			long parentCommercePriceListId, String name, double priority,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			String externalReferenceCode, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		_checkPortletResourcePermission(
			groupId, CommercePriceListActionKeys.ADD_COMMERCE_PRICE_LIST);

		return commercePriceListLocalService.addCommercePriceList(
			groupId, userId, commerceCurrencyId, parentCommercePriceListId,
			name, priority, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, externalReferenceCode, neverExpire,
			serviceContext);
	}

	@Override
	public CommercePriceList addCommercePriceList(
			long groupId, long userId, long commerceCurrencyId, String name,
			double priority, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		return addCommercePriceList(
			groupId, userId, commerceCurrencyId, 0, name, priority,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute, null,
			neverExpire, serviceContext);
	}

	@Override
	public CommercePriceList addCommercePriceList(
			long groupId, long userId, long commerceCurrencyId, String name,
			double priority, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, String externalReferenceCode,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		return addCommercePriceList(
			groupId, userId, commerceCurrencyId, 0, name, priority,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			externalReferenceCode, neverExpire, serviceContext);
	}

	@Override
	public void deleteCommercePriceList(long commercePriceListId)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.DELETE);

		commercePriceListLocalService.deleteCommercePriceList(
			commercePriceListId);
	}

	@Override
	public CommercePriceList fetchByExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws PortalException {

		CommercePriceList commercePriceList =
			commercePriceListLocalService.fetchByExternalReferenceCode(
				companyId, externalReferenceCode);

		if (commercePriceList != null) {
			_commercePriceListModelResourcePermission.check(
				getPermissionChecker(), commercePriceList, ActionKeys.VIEW);
		}

		return commercePriceList;
	}

	@Override
	public CommercePriceList fetchCatalogBaseCommercePriceListByType(
			long groupId, String type)
		throws PortalException {

		CommercePriceList commercePriceList =
			commercePriceListLocalService.
				fetchCatalogBaseCommercePriceListByType(groupId, type);

		if (commercePriceList != null) {
			_commercePriceListModelResourcePermission.check(
				getPermissionChecker(), commercePriceList, ActionKeys.VIEW);
		}

		return commercePriceList;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public CommercePriceList fetchCommerceCatalogBasePriceListByType(
			long groupId, String type)
		throws PortalException {

		return commercePriceListService.fetchCatalogBaseCommercePriceListByType(
			groupId, type);
	}

	@Override
	public CommercePriceList fetchCommercePriceList(long commercePriceListId)
		throws PortalException {

		CommercePriceList commercePriceList =
			commercePriceListLocalService.fetchCommercePriceList(
				commercePriceListId);

		if (commercePriceList != null) {
			_commercePriceListModelResourcePermission.check(
				getPermissionChecker(), commercePriceListId, ActionKeys.VIEW);
		}

		return commercePriceList;
	}

	@Override
	public CommercePriceList getCommercePriceList(long commercePriceListId)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.VIEW);

		return commercePriceListLocalService.getCommercePriceList(
			commercePriceListId);
	}

	@Override
	public List<CommercePriceList> getCommercePriceLists(
			long companyId, int status, int start, int end,
			OrderByComparator<CommercePriceList> orderByComparator)
		throws PortalException {

		List<CommerceCatalog> commerceCatalogs =
			_commerceCatalogService.searchCommerceCatalogs(
				companyId, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Stream<CommerceCatalog> stream = commerceCatalogs.stream();

		long[] groupIds = stream.mapToLong(
			CommerceCatalog::getGroupId
		).toArray();

		if (status == WorkflowConstants.STATUS_ANY) {
			return commercePriceListPersistence.filterFindByG_C_NotS(
				groupIds, companyId, WorkflowConstants.STATUS_IN_TRASH, start,
				end, orderByComparator);
		}

		return commercePriceListPersistence.filterFindByG_C_S(
			groupIds, companyId, status, start, end, orderByComparator);
	}

	@Override
	public int getCommercePriceListsCount(long companyId, int status)
		throws PortalException {

		List<CommerceCatalog> commerceCatalogs =
			_commerceCatalogService.searchCommerceCatalogs(
				companyId, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Stream<CommerceCatalog> stream = commerceCatalogs.stream();

		long[] groupIds = stream.mapToLong(
			CommerceCatalog::getGroupId
		).toArray();

		if (status == WorkflowConstants.STATUS_ANY) {
			return commercePriceListPersistence.filterCountByG_C_NotS(
				groupIds, companyId, WorkflowConstants.STATUS_IN_TRASH);
		}

		return commercePriceListPersistence.filterCountByG_C_S(
			groupIds, companyId, status);
	}

	@Override
	public int getCommercePriceListsCount(
			long commercePricingClassId, String title)
		throws PrincipalException {

		return commercePriceListFinder.countByCommercePricingClassId(
			commercePricingClassId, title, true);
	}

	@Override
	public List<CommercePriceList> searchByCommercePricingClassId(
			long commercePricingClassId, String name, int start, int end)
		throws PrincipalException {

		return commercePriceListFinder.findByCommercePricingClassId(
			commercePricingClassId, name, start, end, true);
	}

	@Override
	public BaseModelSearchResult<CommercePriceList> searchCommercePriceLists(
			long companyId, String keywords, int status, int start, int end,
			Sort sort)
		throws PortalException {

		List<CommerceCatalog> commerceCatalogs =
			_commerceCatalogService.searchCommerceCatalogs(
				companyId, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Stream<CommerceCatalog> stream = commerceCatalogs.stream();

		long[] groupIds = stream.mapToLong(
			CommerceCatalog::getGroupId
		).toArray();

		return commercePriceListLocalService.searchCommercePriceLists(
			companyId, groupIds, keywords, status, start, end, sort);
	}

	@Override
	public int searchCommercePriceListsCount(
			long companyId, String keywords, int status)
		throws PortalException {

		List<CommerceCatalog> commerceCatalogs =
			_commerceCatalogService.searchCommerceCatalogs(
				companyId, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Stream<CommerceCatalog> stream = commerceCatalogs.stream();

		long[] groupIds = stream.mapToLong(
			CommerceCatalog::getGroupId
		).toArray();

		return commercePriceListLocalService.searchCommercePriceListsCount(
			companyId, groupIds, keywords, status);
	}

	@Override
	public void setCatalogBasePriceList(
			long groupId, long commercePriceListId, String type)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.UPDATE);

		commercePriceListLocalService.setCatalogBasePriceList(
			groupId, commercePriceListId, type);
	}

	@Override
	public CommercePriceList updateCommercePriceList(
			long commercePriceListId, long commerceCurrencyId, boolean netPrice,
			long parentCommercePriceListId, String name, double priority,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.UPDATE);

		return commercePriceListLocalService.updateCommercePriceList(
			commercePriceListId, commerceCurrencyId, netPrice,
			parentCommercePriceListId, name, priority, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public CommercePriceList updateCommercePriceList(
			long commercePriceListId, long commerceCurrencyId, boolean netPrice,
			String type, long parentCommercePriceListId,
			boolean catalogBasePriceList, String name, double priority,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.UPDATE);

		return commercePriceListLocalService.updateCommercePriceList(
			commercePriceListId, commerceCurrencyId, netPrice, type,
			parentCommercePriceListId, catalogBasePriceList, name, priority,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	@Override
	public CommercePriceList updateCommercePriceList(
			long commercePriceListId, long commerceCurrencyId,
			long parentCommercePriceListId, String name, double priority,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.UPDATE);

		return commercePriceListLocalService.updateCommercePriceList(
			commercePriceListId, commerceCurrencyId, parentCommercePriceListId,
			name, priority, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	@Override
	public CommercePriceList updateCommercePriceList(
			long commercePriceListId, long commerceCurrencyId, String name,
			double priority, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		return updateCommercePriceList(
			commercePriceListId, commerceCurrencyId, 0, name, priority,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	@Override
	public CommercePriceList updateExternalReferenceCode(
			CommercePriceList commercePriceList, long companyId,
			String externalReferenceCode)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceList, ActionKeys.UPDATE);

		return commercePriceListLocalService.updateExternalReferenceCode(
			commercePriceList, externalReferenceCode);
	}

	@Override
	public CommercePriceList upsertCommercePriceList(
			long groupId, long userId, long commercePriceListId,
			long commerceCurrencyId, boolean netPrice, String type,
			long parentCommercePriceListId, boolean catalogBasePriceList,
			String name, double priority, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			String externalReferenceCode, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		// Update

		if (commercePriceListId > 0) {
			try {
				return updateCommercePriceList(
					commercePriceListId, commerceCurrencyId, netPrice, type,
					parentCommercePriceListId, catalogBasePriceList, name,
					priority, displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, expirationDateMonth,
					expirationDateDay, expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);
			}
			catch (NoSuchPriceListException noSuchPriceListException) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to find price list with ID: " +
							commercePriceListId);
				}
			}
		}

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		if (Validator.isNotNull(externalReferenceCode)) {
			CommercePriceList commercePriceList =
				commercePriceListPersistence.fetchByC_ERC(
					serviceContext.getCompanyId(), externalReferenceCode);

			if (commercePriceList != null) {
				return updateCommercePriceList(
					commercePriceList.getCommercePriceListId(),
					commerceCurrencyId, netPrice, type,
					parentCommercePriceListId, catalogBasePriceList, name,
					priority, displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, expirationDateMonth,
					expirationDateDay, expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);
			}
		}

		// Add

		return addCommercePriceList(
			groupId, userId, commerceCurrencyId, netPrice, type,
			parentCommercePriceListId, catalogBasePriceList, name, priority,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			externalReferenceCode, neverExpire, serviceContext);
	}

	@Override
	public CommercePriceList upsertCommercePriceList(
			long groupId, long userId, long commercePriceListId,
			long commerceCurrencyId, long parentCommercePriceListId,
			String name, double priority, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			String externalReferenceCode, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		return upsertCommercePriceList(
			groupId, userId, commercePriceListId, commerceCurrencyId, true,
			CommercePriceListConstants.TYPE_PRICE_LIST,
			parentCommercePriceListId, false, name, priority, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, externalReferenceCode,
			neverExpire, serviceContext);
	}

	@Override
	public CommercePriceList upsertCommercePriceList(
			long groupId, long userId, long commercePriceListId,
			long commerceCurrencyId, String name, double priority,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			String externalReferenceCode, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		return upsertCommercePriceList(
			groupId, userId, commercePriceListId, commerceCurrencyId, 0, name,
			priority, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, externalReferenceCode, neverExpire,
			serviceContext);
	}

	private void _checkPortletResourcePermission(long groupId, String actionId)
		throws PrincipalException {

		PortletResourcePermission portletResourcePermission =
			_commercePriceListModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), groupId, actionId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceListServiceImpl.class);

	private static volatile ModelResourcePermission<CommercePriceList>
		_commercePriceListModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommercePriceListServiceImpl.class,
				"_commercePriceListModelResourcePermission",
				CommercePriceList.class);

	@ServiceReference(type = CommerceCatalogService.class)
	private CommerceCatalogService _commerceCatalogService;

}