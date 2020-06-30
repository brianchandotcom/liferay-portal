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

package com.liferay.commerce.discount.service.persistence.impl;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.impl.CommerceDiscountImpl;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountFinder;
import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Riccardo Alberti
 */
public class CommerceDiscountFinderImpl
	extends CommerceDiscountFinderBaseImpl implements CommerceDiscountFinder {

	public static final String FIND_BY_UNQUALIFIED_PRODUCT =
		CommerceDiscountFinder.class.getName() + ".findByUnqualifiedProduct";

	public static final String FIND_BY_UNQUALIFIED_ORDER =
		CommerceDiscountFinder.class.getName() + ".findByUnqualifiedOrder";

	public static final String FIND_BY_A_C_C_PRODUCT =
		CommerceDiscountFinder.class.getName() + ".findByA_C_C_Product";

	public static final String FIND_BY_A_C_C_ORDER =
		CommerceDiscountFinder.class.getName() + ".findByA_C_C_Order";

	public static final String FIND_BY_AG_C_C_PRODUCT =
		CommerceDiscountFinder.class.getName() + ".findByAG_C_C_Product";

	public static final String FIND_BY_AG_C_C_ORDER =
		CommerceDiscountFinder.class.getName() + ".findByAG_C_C_Order";

	public static final String FIND_BY_C_C_C_PRODUCT =
		CommerceDiscountFinder.class.getName() + ".findByC_C_C_Product";

	public static final String FIND_BY_C_C_C_ORDER =
		CommerceDiscountFinder.class.getName() + ".findByC_C_C_Order";

	public static final String FIND_PL_DISCOUNT_PRODUCT =
		CommerceDiscountFinder.class.getName() +
			".findPriceListDiscountProduct";

	@Override
	public List<CommerceDiscount> findByUnqualifiedProduct(
		long companyId, long cpDefinitionId, long[] assetCategoryIds,
		long[] commercePricingClassIds) {

		return _findProductDiscount(
			FIND_BY_UNQUALIFIED_PRODUCT, companyId, -1, null, -1,
			cpDefinitionId, assetCategoryIds, commercePricingClassIds);
	}

	@Override
	public List<CommerceDiscount> findByUnqualifiedOrder(
		long companyId, String commerceDiscountTargetType) {

		return _findOrderDiscounts(
			FIND_BY_UNQUALIFIED_ORDER, companyId, -1, null, -1,
			commerceDiscountTargetType);
	}

	@Override
	public List<CommerceDiscount> findByA_C_C_Product(
		long commerceAccountId, long cpDefinitionId, long[] assetCategoryIds,
		long[] commercePricingClassIds) {

		return _findProductDiscount(
			FIND_BY_A_C_C_PRODUCT, -1, commerceAccountId, null, -1,
			cpDefinitionId, assetCategoryIds, commercePricingClassIds);
	}

	@Override
	public List<CommerceDiscount> findByA_C_C_Order(
		long commerceAccountId, String commerceDiscountTargetType) {

		return _findOrderDiscounts(
			FIND_BY_A_C_C_ORDER, -1, commerceAccountId, null, -1,
			commerceDiscountTargetType);
	}

	@Override
	public List<CommerceDiscount> findByAG_C_C_Product(
		long[] commerceAccountGroupIds, long cpDefinitionId,
		long[] assetCategoryIds, long[] commercePricingClassIds) {

		return _findProductDiscount(
			FIND_BY_AG_C_C_PRODUCT, -1, -1, commerceAccountGroupIds, -1,
			cpDefinitionId, assetCategoryIds, commercePricingClassIds);
	}

	@Override
	public List<CommerceDiscount> findByAG_C_C_Order(
		long[] commerceAccountGroupIds, String commerceDiscountTargetType) {

		return _findOrderDiscounts(
			FIND_BY_AG_C_C_ORDER, -1, -1, commerceAccountGroupIds, -1,
			commerceDiscountTargetType);
	}

	@Override
	public List<CommerceDiscount> findByC_C_C_Product(
		long commerceChannelId, long cpDefinitionId, long[] assetCategoryIds,
		long[] commercePricingClassIds) {

		return _findProductDiscount(
			FIND_BY_C_C_C_PRODUCT, -1, -1, null, commerceChannelId,
			cpDefinitionId, assetCategoryIds, commercePricingClassIds);
	}

	@Override
	public List<CommerceDiscount> findByC_C_C_Order(
		long commerceChannelId, String commerceDiscountTargetType) {

		return _findOrderDiscounts(
			FIND_BY_C_C_C_ORDER, -1, -1, null, commerceChannelId,
			commerceDiscountTargetType);
	}

	@Override
	public List<CommerceDiscount> findPriceListDiscountProduct(
		long[] commerceDiscountIds, long cpDefinitionId,
		long[] assetCategoryIds, long[] commercePricingClassIds) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_PL_DISCOUNT_PRODUCT);

			if ((commerceDiscountIds != null) &&
				(commerceDiscountIds.length > 0)) {

				sql = replaceQueryClassPKs(
					sql, "[$DISCOUNT_IDS$]", commerceDiscountIds);
			}
			else {
				sql = replaceQueryClassPKs(
					sql, "[$DISCOUNT_IDS$]", new long[] {0});
			}

			if ((assetCategoryIds != null) && (assetCategoryIds.length > 0)) {
				sql = replaceQueryClassPKs(
					sql, "[$CLASS_PK_CATEGORIES$]", assetCategoryIds);
			}
			else {
				sql = replaceQueryClassPKs(
					sql, "[$CLASS_PK_CATEGORIES$]", new long[] {0});
			}

			if ((commercePricingClassIds != null) &&
				(commercePricingClassIds.length > 0)) {

				sql = replaceQueryClassPKs(
					sql, "[$CLASS_PK_PRICING_CLASSES$]",
					commercePricingClassIds);
			}
			else {
				sql = replaceQueryClassPKs(
					sql, "[$CLASS_PK_PRICING_CLASSES$]", new long[] {0});
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity(
				CommerceDiscountImpl.TABLE_NAME, CommerceDiscountImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(cpDefinitionId);
			qPos.add(PortalUtil.getClassNameId(CPDefinition.class.getName()));
			qPos.add(PortalUtil.getClassNameId(AssetCategory.class.getName()));
			qPos.add(
				PortalUtil.getClassNameId(
					CommercePricingClass.class.getName()));

			return (List<CommerceDiscount>)QueryUtil.list(
				q, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected String replaceQueryClassPKs(
		String sql, String queryPlaceholder, long[] classPKs) {

		StringBundler sb = new StringBundler(classPKs.length);

		for (int i = 0; i < classPKs.length; i++) {
			sb.append(classPKs[i]);

			if (i != (classPKs.length - 1)) {
				sb.append(", ");
			}
		}

		return StringUtil.replace(sql, queryPlaceholder, sb.toString());
	}

	private List<CommerceDiscount> _findOrderDiscounts(
		String queryString, long companyId, long commerceAccountId,
		long[] commerceAccountGroupIds, long commerceChannelId,
		String commerceDiscountTargetType) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), queryString);

			if ((commerceAccountGroupIds != null) &&
				(commerceAccountGroupIds.length > 0)) {

				sql = replaceQueryClassPKs(
					sql, "[$ACCOUNT_GROUP_IDS$]", commerceAccountGroupIds);
			}
			else {
				sql = replaceQueryClassPKs(
					sql, "[$ACCOUNT_GROUP_IDS$]", new long[] {0});
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity(
				CommerceDiscountImpl.TABLE_NAME, CommerceDiscountImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			if (companyId != -1) {
				qPos.add(companyId);
			}

			if (commerceAccountId != -1) {
				qPos.add(commerceAccountId);
			}

			if (commerceChannelId != -1) {
				qPos.add(commerceChannelId);
			}

			qPos.add(commerceDiscountTargetType);

			return (List<CommerceDiscount>)QueryUtil.list(
				q, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private List<CommerceDiscount> _findProductDiscount(
		String queryString, long companyId, long commerceAccountId,
		long[] commerceAccountGroupIds, long commerceChannelId,
		long cpDefinitionId, long[] assetCategoryIds,
		long[] commercePricingClassIds) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), queryString);

			if ((commerceAccountGroupIds != null) &&
				(commerceAccountGroupIds.length > 0)) {

				sql = replaceQueryClassPKs(
					sql, "[$ACCOUNT_GROUP_IDS$]", commerceAccountGroupIds);
			}
			else {
				sql = replaceQueryClassPKs(
					sql, "[$ACCOUNT_GROUP_IDS$]", new long[] {0});
			}

			if ((assetCategoryIds != null) && (assetCategoryIds.length > 0)) {
				sql = replaceQueryClassPKs(
					sql, "[$CLASS_PK_CATEGORIES$]", assetCategoryIds);
			}
			else {
				sql = replaceQueryClassPKs(
					sql, "[$CLASS_PK_CATEGORIES$]", new long[] {0});
			}

			if ((commercePricingClassIds != null) &&
				(commercePricingClassIds.length > 0)) {

				sql = replaceQueryClassPKs(
					sql, "[$CLASS_PK_PRICING_CLASSES$]",
					commercePricingClassIds);
			}
			else {
				sql = replaceQueryClassPKs(
					sql, "[$CLASS_PK_PRICING_CLASSES$]", new long[] {0});
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity(
				CommerceDiscountImpl.TABLE_NAME, CommerceDiscountImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			if (companyId != -1) {
				qPos.add(companyId);
			}

			if (commerceAccountId != -1) {
				qPos.add(commerceAccountId);
			}

			if (commerceChannelId != -1) {
				qPos.add(commerceChannelId);
			}

			qPos.add(cpDefinitionId);
			qPos.add(PortalUtil.getClassNameId(CPDefinition.class.getName()));
			qPos.add(PortalUtil.getClassNameId(AssetCategory.class.getName()));
			qPos.add(
				PortalUtil.getClassNameId(
					CommercePricingClass.class.getName()));

			return (List<CommerceDiscount>)QueryUtil.list(
				q, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@ServiceReference(type = CustomSQL.class)
	private CustomSQL _customSQL;

}