/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.shopping.CouponCodeException;
import com.liferay.portlet.shopping.CouponDateException;
import com.liferay.portlet.shopping.CouponDescriptionException;
import com.liferay.portlet.shopping.CouponDiscountException;
import com.liferay.portlet.shopping.CouponEndDateException;
import com.liferay.portlet.shopping.CouponLimitCategoriesException;
import com.liferay.portlet.shopping.CouponLimitSKUsException;
import com.liferay.portlet.shopping.CouponMinimumOrderException;
import com.liferay.portlet.shopping.CouponNameException;
import com.liferay.portlet.shopping.CouponStartDateException;
import com.liferay.portlet.shopping.DuplicateCouponCodeException;
import com.liferay.portlet.shopping.model.ShoppingCategory;
import com.liferay.portlet.shopping.model.ShoppingCoupon;
import com.liferay.portlet.shopping.model.ShoppingItem;
import com.liferay.portlet.shopping.service.base.ShoppingCouponLocalServiceBaseImpl;
import com.liferay.util.PwdGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Huang Jie
 */
public class ShoppingCouponLocalServiceImpl
	extends ShoppingCouponLocalServiceBaseImpl {

	@Override
	public ShoppingCoupon addCoupon(
			long userId, String code, boolean autoCode, String name,
			String description, int startDateMonth, int startDateDay,
			int startDateYear, int startDateHour, int startDateMinute,
			int endDateMonth, int endDateDay, int endDateYear, int endDateHour,
			int endDateMinute, boolean neverExpire, boolean active,
			String limitCategories, String limitSkus, double minOrder,
			double discount, String discountType, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();

		code = code.trim().toUpperCase();

		if (autoCode) {
			code = getCode();
		}

		Date startDate = PortalUtil.getDate(
			startDateMonth, startDateDay, startDateYear, startDateHour,
			startDateMinute, user.getTimeZone(),
			CouponStartDateException.class);

		Date endDate = null;

		if (!neverExpire) {
			endDate = PortalUtil.getDate(
				endDateMonth, endDateDay, endDateYear, endDateHour,
				endDateMinute, user.getTimeZone(),
				CouponEndDateException.class);
		}

		if ((endDate != null) && startDate.after(endDate)) {
			throw new CouponDateException();
		}

		Date now = new Date();

		validate(
			user.getCompanyId(), groupId, code, autoCode, name, description,
			limitCategories, limitSkus, minOrder, discount);

		long couponId = counterLocalService.increment();

		ShoppingCoupon coupon = shoppingCouponPersistence.create(couponId);

		coupon.setGroupId(groupId);
		coupon.setCompanyId(user.getCompanyId());
		coupon.setUserId(user.getUserId());
		coupon.setUserName(user.getFullName());
		coupon.setCreateDate(now);
		coupon.setModifiedDate(now);
		coupon.setCode(code);
		coupon.setName(name);
		coupon.setDescription(description);
		coupon.setStartDate(startDate);
		coupon.setEndDate(endDate);
		coupon.setActive(active);
		coupon.setLimitCategories(limitCategories);
		coupon.setLimitSkus(limitSkus);
		coupon.setMinOrder(minOrder);
		coupon.setDiscount(discount);
		coupon.setDiscountType(discountType);

		shoppingCouponPersistence.update(coupon);

		return coupon;
	}

	@Override
	public void deleteCoupon(long couponId)
		throws PortalException, SystemException {

		ShoppingCoupon coupon = shoppingCouponPersistence.findByPrimaryKey(
			couponId);

		deleteCoupon(coupon);
	}

	@Override
	public void deleteCoupon(ShoppingCoupon coupon) throws SystemException {
		shoppingCouponPersistence.remove(coupon);
	}

	@Override
	public void deleteCoupons(long groupId) throws SystemException {
		List<ShoppingCoupon> coupons = shoppingCouponPersistence.findByGroupId(
			groupId);

		for (ShoppingCoupon coupon : coupons) {
			deleteCoupon(coupon);
		}
	}

	@Override
	public ShoppingCoupon getCoupon(long couponId)
		throws PortalException, SystemException {

		return shoppingCouponPersistence.findByPrimaryKey(couponId);
	}

	@Override
	public ShoppingCoupon getCoupon(String code)
		throws PortalException, SystemException {

		code = code.trim().toUpperCase();

		return shoppingCouponPersistence.findByCode(code);
	}

	@Override
	public List<ShoppingCoupon> search(
			long groupId, long companyId, String code, boolean active,
			String discountType, boolean andOperator, int start, int end)
		throws SystemException {

		return shoppingCouponFinder.findByG_C_C_A_DT(
			groupId, companyId, code, active, discountType, andOperator, start,
			end);
	}

	@Override
	public int searchCount(
			long groupId, long companyId, String code, boolean active,
			String discountType, boolean andOperator)
		throws SystemException {

		return shoppingCouponFinder.countByG_C_C_A_DT(
			groupId, companyId, code, active, discountType, andOperator);
	}

	@Override
	public ShoppingCoupon updateCoupon(
			long userId, long couponId, String name, String description,
			int startDateMonth, int startDateDay, int startDateYear,
			int startDateHour, int startDateMinute, int endDateMonth,
			int endDateDay, int endDateYear, int endDateHour, int endDateMinute,
			boolean neverExpire, boolean active, String limitCategories,
			String limitSkus, double minOrder, double discount,
			String discountType, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		ShoppingCoupon coupon = shoppingCouponPersistence.findByPrimaryKey(
			couponId);

		Date startDate = PortalUtil.getDate(
			startDateMonth, startDateDay, startDateYear, startDateHour,
			startDateMinute, user.getTimeZone(),
			CouponStartDateException.class);

		Date endDate = null;

		if (!neverExpire) {
			endDate = PortalUtil.getDate(
				endDateMonth, endDateDay, endDateYear, endDateHour,
				endDateMinute, user.getTimeZone(),
				CouponEndDateException.class);
		}

		if ((endDate != null) && startDate.after(endDate)) {
			throw new CouponDateException();
		}

		validate(
			coupon.getCompanyId(), coupon.getGroupId(), name, description,
			limitCategories, limitSkus, minOrder, discount);

		coupon.setModifiedDate(new Date());
		coupon.setName(name);
		coupon.setDescription(description);
		coupon.setStartDate(startDate);
		coupon.setEndDate(endDate);
		coupon.setActive(active);
		coupon.setLimitCategories(limitCategories);
		coupon.setLimitSkus(limitSkus);
		coupon.setMinOrder(minOrder);
		coupon.setDiscount(discount);
		coupon.setDiscountType(discountType);

		shoppingCouponPersistence.update(coupon);

		return coupon;
	}

	protected String getCode() throws SystemException {
		String code = PwdGenerator.getPassword(
			PwdGenerator.KEY1 + PwdGenerator.KEY2, 8);

		ShoppingCoupon coupon = shoppingCouponPersistence.fetchByCode(code);

		if (coupon != null) {
			return coupon.getCode();
		}

		return code;
	}

	protected void validate(
			long companyId, long groupId, String code, boolean autoCode,
			String name, String description, String limitCategories,
			String limitSkus, double minOrder, double discount)
		throws PortalException, SystemException {

		if (!autoCode) {
			if (Validator.isNull(code) || Validator.isNumber(code) ||
				(code.indexOf(CharPool.SPACE) != -1)) {

				throw new CouponCodeException();
			}

			if (shoppingCouponPersistence.fetchByCode(code) != null) {
				throw new DuplicateCouponCodeException();
			}
		}

		validate(
			companyId, groupId, name, description, limitCategories, limitSkus,
			minOrder, discount);
	}

	protected void validate(
			long companyId, long groupId, String name, String description,
			String limitCategories, String limitSkus, double minOrder,
			double discount)
		throws PortalException, SystemException {

		if (Validator.isNull(name)) {
			throw new CouponNameException();
		}
		else if (Validator.isNull(description)) {
			throw new CouponDescriptionException();
		}

		// Category IDs

		long[] categoryIds = StringUtil.split(limitCategories, 0L);

		List<Long> invalidCategoryIds = new ArrayList<Long>();

		for (long categoryId : categoryIds) {
			ShoppingCategory category =
				shoppingCategoryPersistence.fetchByPrimaryKey(categoryId);

			if ((category == null) || (category.getGroupId() != groupId)) {
				invalidCategoryIds.add(categoryId);
			}
		}

		if (invalidCategoryIds.size() > 0) {
			CouponLimitCategoriesException clce =
				new CouponLimitCategoriesException();

			clce.setCategoryIds(invalidCategoryIds);

			throw clce;
		}

		// SKUs

		String[] skus = StringUtil.split(limitSkus);

		List<String> invalidSkus = new ArrayList<String>();

		for (String sku : skus) {
			ShoppingItem item = shoppingItemPersistence.fetchByC_S(
				companyId, sku);

			if (item != null) {
				ShoppingCategory category = item.getCategory();

				if (category.getGroupId() != groupId) {
					invalidSkus.add(sku);
				}
			}
			else {
				invalidSkus.add(sku);
			}
		}

		if (invalidSkus.size() > 0) {
			CouponLimitSKUsException clskue = new CouponLimitSKUsException();

			clskue.setSkus(invalidSkus);

			throw clskue;
		}

		if (minOrder < 0) {
			throw new CouponMinimumOrderException();
		}

		if (discount < 0) {
			throw new CouponDiscountException();
		}
	}

}