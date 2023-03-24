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

package com.liferay.osb.faro.web.internal.model.preferences;

import com.liferay.osb.faro.web.internal.exception.FaroException;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class DistributionCardTabsPreferences {

	public void addDistributionTab(
		String id,
		DistributionCardTabPreferences distributionCardTabPreferences) {

		if (_order.contains(id)) {
			throw new FaroException(
				"Distribution tab title already exists: " +
					StringUtil.quote(id, StringPool.QUOTE));
		}

		_distributionCardTabPreferencesMap.put(
			id, distributionCardTabPreferences);

		_order.add(id);
	}

	public Map<String, DistributionCardTabPreferences>
		getDistributionCardTabPreferencesMap() {

		return _distributionCardTabPreferencesMap;
	}

	public List<String> getOrder() {
		return _order;
	}

	public void removeDistributionTab(String id) {
		_distributionCardTabPreferencesMap.remove(id);
		_order.remove(id);
	}

	public void setDistributionCardTabPreferencesMap(
		Map<String, DistributionCardTabPreferences>
			distributionCardTabPreferencesMap) {

		_distributionCardTabPreferencesMap = distributionCardTabPreferencesMap;
	}

	public void setOrder(List<String> order) {
		_order = order;
	}

	private Map<String, DistributionCardTabPreferences>
		_distributionCardTabPreferencesMap = new HashMap<>();
	private List<String> _order = new ArrayList<>();

}