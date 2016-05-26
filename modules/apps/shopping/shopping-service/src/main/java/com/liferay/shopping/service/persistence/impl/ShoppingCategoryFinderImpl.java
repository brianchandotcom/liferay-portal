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

package com.liferay.shopping.service.persistence.impl;

import com.liferay.portal.dao.orm.custom.sql.CustomSQLUtil;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.shopping.model.ShoppingCategory;
import com.liferay.shopping.model.ShoppingItem;
import com.liferay.shopping.service.ShoppingCategoryLocalServiceUtil;
import com.liferay.shopping.service.ShoppingItemLocalServiceUtil;
import com.liferay.shopping.service.persistence.ShoppingCategoryFinder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class ShoppingCategoryFinderImpl
	extends ShoppingCategoryFinderBaseImpl implements ShoppingCategoryFinder {

	public static final String COUNT_C_BY_G_C =
		ShoppingCategoryFinder.class.getName() + ".countC_ByG_C";

	public static final String COUNT_I_BY_G_C =
		ShoppingCategoryFinder.class.getName() + ".countI_ByG_C";

	public static final String FIND_C_BY_G_C =
		ShoppingCategoryFinder.class.getName() + ".findC_ByG_C";

	public static final String FIND_I_BY_G_C =
		ShoppingCategoryFinder.class.getName() + ".findI_ByG_C";

	@Override
	public int countC_I_ByG_C(long groupId, long categoryId) {
		return doCountC_I_ByG_C(groupId, categoryId, false);
	}

	@Override
	public int filterCountC_I_ByG_C(long groupId, long categoryId) {
		return doCountC_I_ByG_C(groupId, categoryId, true);
	}

	@Override
	public List<Object> filterFindC_I_ByG_C(
		long groupId, long categoryId, QueryDefinition<?> queryDefinition) {

		return doFindC_I_ByG_C(groupId, categoryId, queryDefinition, true);
	}

	@Override
	public List<Object> findC_I_ByG_C(
		long groupId, long categoryId, QueryDefinition<?> queryDefinition) {

		return doFindC_I_ByG_C(groupId, categoryId, queryDefinition, false);
	}

	protected int doCountC_I_ByG_C(
		long groupId, long categoryId, boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			StringBundler sb = new StringBundler(5);

			sb.append(StringPool.OPEN_PARENTHESIS);

			String sql = CustomSQLUtil.get(getClass(), COUNT_C_BY_G_C);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, ShoppingCategory.class.getName(),
					"ShoppingCategory.categoryId", groupId);
			}

			sb.append(sql);
			sb.append(") UNION ALL (");

			sql = CustomSQLUtil.get(getClass(), COUNT_I_BY_G_C);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, ShoppingItem.class.getName(), "ShoppingItem.itemId",
					groupId);
			}

			sb.append(sql);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			SQLQuery q = session.createSynchronizedSQLQuery(sb.toString());

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(categoryId);
			qPos.add(groupId);
			qPos.add(categoryId);

			int count = 0;

			Iterator<Long> itr = q.iterate();

			while (itr.hasNext()) {
				Long l = itr.next();

				if (l != null) {
					count += l.intValue();
				}
			}

			return count;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<Object> doFindC_I_ByG_C(
		long groupId, long categoryId, QueryDefinition<?> queryDefinition,
		boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			StringBundler sb = new StringBundler(5);

			sb.append(StringPool.OPEN_PARENTHESIS);

			String sql = CustomSQLUtil.get(getClass(), FIND_C_BY_G_C);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, ShoppingCategory.class.getName(),
					"ShoppingCategory.categoryId", groupId);
			}

			sb.append(sql);
			sb.append(") UNION ALL (");

			sql = CustomSQLUtil.get(getClass(), FIND_I_BY_G_C);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, ShoppingItem.class.getName(), "ShoppingItem.itemId",
					groupId);
			}

			sb.append(sql);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			sql = sb.toString();

			sql = CustomSQLUtil.replaceOrderBy(
				sql, queryDefinition.getOrderByComparator());

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar("modelCategoryId", Type.LONG);
			q.addScalar("modelCategory", Type.LONG);
			q.addScalar("modelItemId", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(categoryId);
			qPos.add(groupId);
			qPos.add(categoryId);

			List<Object> models = new ArrayList<>();

			Iterator<Object[]> itr = (Iterator<Object[]>)QueryUtil.iterate(
				q, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());

			while (itr.hasNext()) {
				Object[] array = itr.next();

				long modelCategoryId = (Long)array[0];
				long modelCategory = (Long)array[1];

				Object obj = null;

				if (modelCategory == 1) {
					obj =
						ShoppingCategoryLocalServiceUtil.fetchShoppingCategory(
							modelCategoryId);
				}
				else {
					long itemId = (Long)array[2];

					obj = ShoppingItemLocalServiceUtil.fetchShoppingItem(
						itemId);
				}

				models.add(obj);
			}

			return models;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}