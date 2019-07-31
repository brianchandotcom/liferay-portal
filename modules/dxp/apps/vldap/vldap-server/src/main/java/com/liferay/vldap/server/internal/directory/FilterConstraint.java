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

package com.liferay.vldap.server.internal.directory;

import com.liferay.petra.string.StringPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jonathan Potter
 */
public class FilterConstraint {

	public static List<FilterConstraint> getCombinations(
		List<FilterConstraint> leftFilterConstraints,
		List<FilterConstraint> rightFilterConstraints) {

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		for (FilterConstraint leftFilterConstraint : leftFilterConstraints) {
			for (FilterConstraint rightFilterConstraint :
					rightFilterConstraints) {

				FilterConstraint filterConstraint = merge(
					leftFilterConstraint, rightFilterConstraint);

				if (filterConstraint == null) {
					continue;
				}

				if (filterConstraint.isEmpty()) {
					continue;
				}

				filterConstraints.add(filterConstraint);
			}
		}

		return filterConstraints;
	}

	public static FilterConstraint merge(
		FilterConstraint leftFilterConstraint,
		FilterConstraint rightFilterConstraint) {

		FilterConstraint filterConstraint = new FilterConstraint(
			leftFilterConstraint);

		boolean collision = filterConstraint.merge(rightFilterConstraint);

		if (collision) {
			return null;
		}

		return filterConstraint;
	}

	public FilterConstraint() {
		_map = new HashMap<>();
	}

	public FilterConstraint(FilterConstraint filterConstraint) {
		_map = new HashMap<>(filterConstraint.getMap());
	}

	public void addAttribute(String attributeId, String value) {
		if (attributeId == null) {
			return;
		}

		_map.put(attributeId, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		else if (!(obj instanceof FilterConstraint)) {
			return false;
		}

		FilterConstraint filterConstraint = (FilterConstraint)obj;

		return _map.equals(filterConstraint.getMap());
	}

	public Map<String, String> getMap() {
		return _map;
	}

	public String getValue(String attributeId) {
		return _map.get(attributeId);
	}

	@Override
	public int hashCode() {
		return _map.hashCode();
	}

	public boolean isEmpty() {
		return _map.isEmpty();
	}

	public boolean merge(FilterConstraint rightFilterConstraint) {
		boolean collision = false;

		Map<String, String> rightMap = rightFilterConstraint.getMap();

		for (Map.Entry<String, String> entry : rightMap.entrySet()) {
			String newValue = entry.getValue();

			if (newValue.equals(StringPool.STAR)) {
				continue;
			}

			String previousValue = _map.put(entry.getKey(), entry.getValue());

			if ((previousValue != null) && !previousValue.equals(newValue) &&
				!previousValue.equals(StringPool.STAR)) {

				collision = true;
			}
		}

		return collision;
	}

	private final Map<String, String> _map;

}