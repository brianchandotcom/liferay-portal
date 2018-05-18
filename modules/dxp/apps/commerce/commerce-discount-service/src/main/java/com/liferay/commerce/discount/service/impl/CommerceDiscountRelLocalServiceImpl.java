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

package com.liferay.commerce.discount.service.impl;

import com.liferay.commerce.discount.model.CommerceDiscountRel;
import com.liferay.commerce.discount.service.base.CommerceDiscountRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceDiscountRelLocalServiceImpl
	extends CommerceDiscountRelLocalServiceBaseImpl {

	@Override
	public CommerceDiscountRel addCommerceDiscountRel(
			long commerceDiscountId, String className, long classPK,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commerceDiscountRelId = counterLocalService.increment();

		CommerceDiscountRel commerceDiscountRel =
			commerceDiscountRelPersistence.create(commerceDiscountRelId);

		commerceDiscountRel.setGroupId(groupId);
		commerceDiscountRel.setCompanyId(user.getCompanyId());
		commerceDiscountRel.setUserId(user.getUserId());
		commerceDiscountRel.setUserName(user.getFullName());
		commerceDiscountRel.setCommerceDiscountId(commerceDiscountId);
		commerceDiscountRel.setClassName(className);
		commerceDiscountRel.setClassPK(classPK);

		commerceDiscountRelPersistence.update(commerceDiscountRel);

		return commerceDiscountRel;
	}

	@Override
	public void deleteCommerceDiscountRels(long commerceDiscountId)
		throws PortalException {

		commerceDiscountRelPersistence.removeByCommerceDiscountId(
			commerceDiscountId);
	}

	@Override
	public void deleteCommerceDiscountRels(String className, long classPK)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(className);

		commerceDiscountRelPersistence.removeByCN_CPK(classNameId, classPK);
	}

	@Override
	public long[] getClassPKs(long commerceDiscountId, String className) {
		long classNameId = classNameLocalService.getClassNameId(className);

		return ListUtil.toLongArray(
			commerceDiscountRelPersistence.findByCD_CN(
				commerceDiscountId, classNameId),
			CommerceDiscountRel::getClassPK);
	}

	@Override
	public List<CommerceDiscountRel> getCommerceDiscountRels(
		long commerceDiscountId, String className) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return commerceDiscountRelPersistence.findByCD_CN(
			commerceDiscountId, classNameId);
	}

	@Override
	public List<CommerceDiscountRel> getCommerceDiscountRels(
		long commerceDiscountId, String className, int start, int end,
		OrderByComparator<CommerceDiscountRel> orderByComparator) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return commerceDiscountRelPersistence.findByCD_CN(
			commerceDiscountId, classNameId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceDiscountRelsCount(
		long commerceDiscountId, String className) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return commerceDiscountRelPersistence.countByCD_CN(
			commerceDiscountId, classNameId);
	}

}