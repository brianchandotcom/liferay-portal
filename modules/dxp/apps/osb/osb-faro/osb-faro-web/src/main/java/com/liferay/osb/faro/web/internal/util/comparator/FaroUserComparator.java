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

package com.liferay.osb.faro.web.internal.util.comparator;

import com.liferay.osb.faro.engine.client.util.OrderByField;
import com.liferay.osb.faro.model.FaroUser;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.Map;

/**
 * @author Matthew Kong
 */
public class FaroUserComparator extends OrderByComparator<FaroUser> {

	public FaroUserComparator(List<OrderByField> orderByFields) {
		_orderByFields = orderByFields;
	}

	@Override
	public int compare(FaroUser faroUser1, FaroUser faroUser2) {
		return 0;
	}

	@Override
	public String getOrderBy() {
		return StringUtil.merge(
			TransformUtil.transform(
				_orderByFields,
				orderByField -> {
					String format = null;

					if (StringUtil.equals(
							orderByField.getFieldName(), "status")) {

						format = "%s %s";
					}
					else {
						format = "lower(%s) %s";
					}

					return String.format(
						format, _fieldNames.get(orderByField.getFieldName()),
						orderByField.getOrderBy());
				}),
			", ");
	}

	private static final Map<String, String> _fieldNames = HashMapBuilder.put(
		"emailAddress", "OSBFaro_FaroUser.emailAddress"
	).put(
		"firstName", "User_.firstName"
	).put(
		"lastLoginDate", "User_.lastLoginDate"
	).put(
		"lastName", "User_.lastName"
	).put(
		"roleName", "Role_.name"
	).put(
		"status", "OSBFaro_FaroUser.status"
	).build();

	private final List<OrderByField> _orderByFields;

}