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

package com.liferay.commerce.price.list.qualification.type.service.impl;

import com.liferay.commerce.model.CommercePriceList;
import com.liferay.commerce.model.CommercePriceListQualificationTypeRel;
import com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel;
import com.liferay.commerce.price.list.qualification.type.service.base.CommercePriceListUserRelLocalServiceBaseImpl;
import com.liferay.commerce.service.CommercePriceListLocalService;
import com.liferay.commerce.service.CommercePriceListQualificationTypeRelLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommercePriceListUserRelLocalServiceImpl
	extends CommercePriceListUserRelLocalServiceBaseImpl {

	@Override
	public CommercePriceListUserRel addCommercePriceListUserRel(
			long commercePriceListQualificationTypeRelId, String className,
			long classPK, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commercePriceListUserRelId = counterLocalService.increment();

		CommercePriceListUserRel commercePriceListUserRel =
			commercePriceListUserRelPersistence.create(
				commercePriceListUserRelId);

		commercePriceListUserRel.setUuid(serviceContext.getUuid());
		commercePriceListUserRel.setGroupId(groupId);
		commercePriceListUserRel.setCompanyId(user.getCompanyId());
		commercePriceListUserRel.setUserId(user.getUserId());
		commercePriceListUserRel.setUserName(user.getFullName());
		commercePriceListUserRel.setCommercePriceListQualificationTypeRelId(
			commercePriceListQualificationTypeRelId);
		commercePriceListUserRel.setClassName(className);
		commercePriceListUserRel.setClassPK(classPK);
		commercePriceListUserRel.setExpandoBridgeAttributes(serviceContext);

		commercePriceListUserRel = commercePriceListUserRelPersistence.update(
			commercePriceListUserRel);

		_commercePriceListLocalService.cleanPriceListCache(groupId);

		doPriceListReindex(commercePriceListUserRel);

		return commercePriceListUserRel;
	}

	@Override
	public void deleteCommercePriceListUserRels(
			long commercePriceListQualificationTypeRelId)
		throws PortalException {

		CommercePriceListQualificationTypeRel
			commercePriceListQualificationTypeRel =
				_commercePriceListQualificationTypeRelLocalService.
					getCommercePriceListQualificationTypeRel(
						commercePriceListQualificationTypeRelId);

		commercePriceListUserRelPersistence.
			removeByCommercePriceListQualificationTypeRelId(
				commercePriceListQualificationTypeRelId);

		_commercePriceListLocalService.cleanPriceListCache(
			commercePriceListQualificationTypeRel.getGroupId());

		doPriceListReindex(commercePriceListQualificationTypeRel);
	}

	@Override
	public void deleteCommercePriceListUserRels(
			long commercePriceListQualificationTypeRelId, String className)
		throws PortalException {

		CommercePriceListQualificationTypeRel
			commercePriceListQualificationTypeRel =
				_commercePriceListQualificationTypeRelLocalService.
					getCommercePriceListQualificationTypeRel(
						commercePriceListQualificationTypeRelId);

		long classNameId = classNameLocalService.getClassNameId(className);

		commercePriceListUserRelPersistence.removeByC_C(
			commercePriceListQualificationTypeRelId, classNameId);

		_commercePriceListLocalService.cleanPriceListCache(
			commercePriceListQualificationTypeRel.getGroupId());

		doPriceListReindex(commercePriceListQualificationTypeRel);
	}

	@Override
	public void deleteCommercePriceListUserRels(
			long commercePriceListQualificationTypeRelId, String className,
			long classPK)
		throws PortalException {

		CommercePriceListQualificationTypeRel
			commercePriceListQualificationTypeRel =
				_commercePriceListQualificationTypeRelLocalService.
					getCommercePriceListQualificationTypeRel(
						commercePriceListQualificationTypeRelId);

		long classNameId = classNameLocalService.getClassNameId(className);

		commercePriceListUserRelPersistence.removeByC_C_C(
			commercePriceListQualificationTypeRelId, classNameId, classPK);

		_commercePriceListLocalService.cleanPriceListCache(
			commercePriceListQualificationTypeRel.getGroupId());

		doPriceListReindex(commercePriceListQualificationTypeRel);
	}

	@Override
	public List<CommercePriceListUserRel> getCommercePriceListUserRels(
		long commercePriceListQualificationTypeRelId, String className) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return commercePriceListUserRelPersistence.findByC_C(
			commercePriceListQualificationTypeRelId, classNameId);
	}

	@Override
	public List<CommercePriceListUserRel> getCommercePriceListUserRels(
		long commercePriceListQualificationTypeRelId, String className,
		int start, int end,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return commercePriceListUserRelPersistence.findByC_C(
			commercePriceListQualificationTypeRelId, classNameId, start, end,
			orderByComparator);
	}

	@Override
	public int getCommercePriceListUserRelsCount(
		long commercePriceListQualificationTypeRelId, String className) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return commercePriceListUserRelPersistence.countByC_C(
			commercePriceListQualificationTypeRelId, classNameId);
	}

	@Override
	public int getCommercePriceListUserRelsCount(
		long commercePriceListQualificationTypeRelId, String className,
		long[] classPKs) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return commercePriceListUserRelPersistence.countByC_C_C(
			commercePriceListQualificationTypeRelId, classNameId, classPKs);
	}

	@Override
	public CommercePriceListUserRel updateCommercePriceListUserRel(
			long commercePriceListUserRelId,
			long commercePriceListQualificationTypeRelId,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePriceListUserRel commercePriceListUserRel =
			commercePriceListUserRelPersistence.findByPrimaryKey(
				commercePriceListUserRelId);

		commercePriceListUserRel.setCommercePriceListQualificationTypeRelId(
			commercePriceListQualificationTypeRelId);
		commercePriceListUserRel.setExpandoBridgeAttributes(serviceContext);

		commercePriceListUserRel = commercePriceListUserRelPersistence.update(
			commercePriceListUserRel);

		_commercePriceListLocalService.cleanPriceListCache(
			commercePriceListUserRel.getGroupId());

		doPriceListReindex(commercePriceListUserRel);

		return commercePriceListUserRel;
	}

	protected void doPriceListReindex(
			CommercePriceListQualificationTypeRel
				commercePriceListQualificationTypeRel)
		throws PortalException {

		Indexer<CommercePriceList> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CommercePriceList.class);

		indexer.reindex(
			CommercePriceList.class.getName(),
			commercePriceListQualificationTypeRel.getCommercePriceListId());
	}

	protected void doPriceListReindex(
			CommercePriceListUserRel commercePriceListUserRel)
		throws PortalException {

		CommercePriceListQualificationTypeRel
			commercePriceListQualificationTypeRel =
				_commercePriceListQualificationTypeRelLocalService.
					getCommercePriceListQualificationTypeRel(
						commercePriceListUserRel.
							getCommercePriceListQualificationTypeRelId());

		doPriceListReindex(commercePriceListQualificationTypeRel);
	}

	@ServiceReference(type = CommercePriceListLocalService.class)
	private CommercePriceListLocalService _commercePriceListLocalService;

	@ServiceReference(
		type = CommercePriceListQualificationTypeRelLocalService.class
	)
	private CommercePriceListQualificationTypeRelLocalService
		_commercePriceListQualificationTypeRelLocalService;

}