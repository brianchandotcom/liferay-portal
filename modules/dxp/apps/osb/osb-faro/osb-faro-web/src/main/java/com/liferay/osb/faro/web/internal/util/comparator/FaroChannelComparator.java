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
import com.liferay.osb.faro.model.FaroChannel;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Geyson Silva
 */
public class FaroChannelComparator extends OrderByComparator<FaroChannel> {

	public FaroChannelComparator(List<OrderByField> orderByFields) {
		_orderByFields = orderByFields;
	}

	@Override
	public int compare(FaroChannel faroChannel1, FaroChannel faroChannel2) {
		return 0;
	}

	@Override
	public String getOrderBy() {
		Stream<OrderByField> stream = _orderByFields.stream();

		return stream.map(
			orderByField -> {
				String format = null;

				if (StringUtil.equals(
						orderByField.getFieldName(), "createTime")) {

					format = "%s %s";
				}
				else {
					format = "lower(%s) %s";
				}

				return String.format(
					format, _fieldNames.get(orderByField.getFieldName()),
					orderByField.getOrderBy());
			}
		).collect(
			Collectors.joining(StringPool.COMMA)
		);
	}

	private static final Map<String, String> _fieldNames =
		new HashMap<String, String>() {
			{
				put("createTime", "OSBFaro_FaroChannel.createTime");
				put("name", "OSBFaro_FaroChannel.name");
			}
		};

	private final List<OrderByField> _orderByFields;

}