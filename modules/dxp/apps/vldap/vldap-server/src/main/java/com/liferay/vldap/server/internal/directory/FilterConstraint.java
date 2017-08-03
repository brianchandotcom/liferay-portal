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

package com.liferay.vldap.server.internal.directory;

import com.liferay.portal.kernel.util.StringPool;

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
		else {
			return filterConstraint;
		}
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