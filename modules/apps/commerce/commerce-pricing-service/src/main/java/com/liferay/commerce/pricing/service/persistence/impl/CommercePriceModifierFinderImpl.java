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

package com.liferay.commerce.pricing.service.persistence.impl;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.commerce.pricing.constants.CommercePriceModifierConstants;
import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.model.impl.CommercePriceModifierImpl;
import com.liferay.commerce.pricing.service.persistence.CommercePriceModifierFinder;
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
public class CommercePriceModifierFinderImpl
	extends CommercePriceModifierFinderBaseImpl
	implements CommercePriceModifierFinder {

	public static final String FIND_BY_C_C_C_P =
		CommercePriceModifierFinder.class.getName() + ".findByC_C_C_P";

	@Override
	public List<CommercePriceModifier> findByC_C_C_P(
		long commercePriceListId, long cpDefinitionId,
		long[] assetCategoriesIds, long[] commercePricingClassesIds) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_C_C_C_P);

			if ((assetCategoriesIds != null) &&
				(assetCategoriesIds.length > 0)) {

				sql = replaceQueryClassPKs(
					sql, "[$CLASS_PK_CATEGORIES$]", assetCategoriesIds);
			}
			else {
				sql = replaceQueryClassPKs(
					sql, "[$CLASS_PK_CATEGORIES$]", new long[] {0});
			}

			if ((commercePricingClassesIds != null) &&
				(commercePricingClassesIds.length > 0)) {

				sql = replaceQueryClassPKs(
					sql, "[$CLASS_PK_PRICING_CLASSES$]",
					commercePricingClassesIds);
			}
			else {
				sql = replaceQueryClassPKs(
					sql, "[$CLASS_PK_PRICING_CLASSES$]", new long[] {0});
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				CommercePriceModifierImpl.TABLE_NAME,
				CommercePriceModifierImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(commercePriceListId);
			queryPos.add(cpDefinitionId);
			queryPos.add(
				PortalUtil.getClassNameId(CPDefinition.class.getName()));
			queryPos.add(
				PortalUtil.getClassNameId(AssetCategory.class.getName()));
			queryPos.add(
				PortalUtil.getClassNameId(
					CommercePricingClass.class.getName()));
			queryPos.add(CommercePriceModifierConstants.TARGET_CATALOG);

			return (List<CommercePriceModifier>)QueryUtil.list(
				sqlQuery, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
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

	@ServiceReference(type = CustomSQL.class)
	private CustomSQL _customSQL;

}