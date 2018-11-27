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

package com.liferay.user.associated.data.display;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Order;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.user.associated.data.util.UADDynamicQueryUtil;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Pei-Jung Lan
 * @author Drew Brokke
 */
public abstract class BaseModelUADDisplay<T extends BaseModel>
	implements UADDisplay<T> {

	@Override
	public long count(long userId) {
		return doCount(getDynamicQuery(userId));
	}

	@Override
	public Map<String, Object> getFieldValues(T t, String[] fieldNames) {
		Map<String, Object> modelAttributes = t.getModelAttributes();

		Set<String> modelAttributesKeySet = modelAttributes.keySet();

		modelAttributesKeySet.retainAll(Arrays.asList(fieldNames));

		return modelAttributes;
	}

	@Override
	public Serializable getPrimaryKey(T baseModel) {
		return baseModel.getPrimaryKeyObj();
	}

	@Override
	public List<T> getRange(long userId, int start, int end) {
		return doGetRange(getDynamicQuery(userId), start, end);
	}

	@Override
	public String[] getSortingFieldNames() {
		return ArrayUtil.append(
			new String[] {"createDate", "modifiedDate"}, getColumnFieldNames());
	}

	@Override
	public String getTypeName(Locale locale) {
		return getTypeClass().getSimpleName();
	}

	@Override
	public List<T> search(
		long userId, long[] groupIds, String keywords, String orderByField,
		String orderByType, int start, int end) {

		return doGetRange(
			getSearchDynamicQuery(
				userId, groupIds, keywords, orderByField, orderByType),
			start, end);
	}

	@Override
	public long searchCount(long userId, long[] groupIds, String keywords) {
		return doCount(
			getSearchDynamicQuery(userId, groupIds, keywords, null, null));
	}

	protected abstract long doCount(DynamicQuery dynamicQuery);

	protected abstract DynamicQuery doGetDynamicQuery();

	protected abstract List<T> doGetRange(
		DynamicQuery dynamicQuery, int start, int end);

	protected abstract String[] doGetUserIdFieldNames();

	protected DynamicQuery getDynamicQuery(long userId) {
		return UADDynamicQueryUtil.addDynamicQueryCriteria(
			doGetDynamicQuery(), doGetUserIdFieldNames(), userId);
	}

	protected OrderByComparator<T> getOrderByComparator(
		String orderByField, String orderByType) {

		return null;
	}

	protected String[] getSearchableFields() {
		return getDisplayFieldNames();
	}

	protected DynamicQuery getSearchDynamicQuery(
		long userId, long[] groupIds, String keywords, String orderByField,
		String orderByType) {

		DynamicQuery dynamicQuery = getDynamicQuery(userId);

		if (ArrayUtil.isNotEmpty(groupIds)) {
			dynamicQuery.add(
				RestrictionsFactoryUtil.in(
					"groupId", ArrayUtil.toLongArray(groupIds)));
		}

		String[] searchableFields = getSearchableFields();

		if (Validator.isNotNull(keywords) && (searchableFields.length > 0)) {
			Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

			String quotedKeywords = StringUtil.quote(
				keywords, CharPool.PERCENT);

			Class<?> clazz = getTypeClass();

			for (String searchableField : searchableFields) {
				try {
					Method method = clazz.getMethod(
						"get" +
							TextFormatter.format(
								searchableField, TextFormatter.G));

					if (method.getReturnType() == String.class) {
						disjunction.add(
							RestrictionsFactoryUtil.ilike(
								searchableField, quotedKeywords));
					}
				}
				catch (NoSuchMethodException | SecurityException e) {
					if (_log.isDebugEnabled()) {
						_log.debug(e, e);
					}
				}
			}

			dynamicQuery.add(disjunction);
		}

		if (orderByField != null) {
			OrderByComparator<T> obc = getOrderByComparator(
				orderByField, orderByType);

			if (obc != null) {
				OrderFactoryUtil.addOrderByComparator(dynamicQuery, obc);
			}
			else {
				Order order = null;

				if (Objects.equals(orderByType, "desc")) {
					order = OrderFactoryUtil.desc(orderByField);
				}
				else {
					order = OrderFactoryUtil.asc(orderByField);
				}

				dynamicQuery.addOrder(order);
			}
		}

		return dynamicQuery;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseModelUADDisplay.class);

}