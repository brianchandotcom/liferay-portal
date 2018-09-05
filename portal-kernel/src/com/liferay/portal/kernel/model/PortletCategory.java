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

package com.liferay.portal.kernel.model;

import com.liferay.petra.string.StringPool;

import java.io.Serializable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Dennis Ju
 */
public class PortletCategory implements Serializable {

	public PortletCategory() {
		this("root");
	}

	public PortletCategory(String name) {
		this(name, new HashSet<String>());
	}

	public PortletCategory(String name, Set<String> portletIds) {
		_portletCategories = new HashMap<>();
		_portletIds = portletIds;

		if (name.contains(_DELIMITER)) {
			int index = name.lastIndexOf(_DELIMITER);

			_name = name.substring(index + _DELIMITER.length());

			String parentName = name.substring(0, index);

			PortletCategory parentPortletCategory = new PortletCategory(
				parentName);

			parentPortletCategory.addCategory(this);
		}
		else {
			_name = name;
			_parentPortletCategory = null;
			_path = name;
		}
	}

	public void addCategory(PortletCategory portletCategory) {
		portletCategory.setParentCategory(this);

		String path = _path.concat(_DELIMITER).concat(
			portletCategory.getName());

		portletCategory.setPath(path);

		_portletCategories.put(portletCategory.getName(), portletCategory);
	}

	public Collection<PortletCategory> getCategories() {
		return Collections.unmodifiableCollection(_portletCategories.values());
	}

	public PortletCategory getCategory(String name) {
		return _portletCategories.get(name);
	}

	public String getName() {
		return _name;
	}

	public PortletCategory getParentCategory() {
		return _parentPortletCategory;
	}

	public String getPath() {
		return _path;
	}

	public Set<String> getPortletIds() {
		return _portletIds;
	}

	public PortletCategory getRootCategory() {
		if (_parentPortletCategory == null) {
			return this;
		}

		return _parentPortletCategory.getRootCategory();
	}

	public boolean isHidden() {
		if (_name.equals(PortletCategoryConstants.NAME_HIDDEN)) {
			return true;
		}

		return false;
	}

	public void merge(PortletCategory newPortletCategory) {
		merge(this, newPortletCategory);
	}

	public void separate(Set<String> portletIds) {
		for (PortletCategory portletCategory : _portletCategories.values()) {
			portletCategory.separate(portletIds);
		}

		Iterator<String> itr = _portletIds.iterator();

		while (itr.hasNext()) {
			String portletId = itr.next();

			if (portletIds.contains(portletId)) {
				itr.remove();
			}
		}
	}

	public void separate(String portletId) {
		Set<String> portletIds = new HashSet<>(1);

		portletIds.add(portletId);

		separate(portletIds);
	}

	public void setPortletIds(Set<String> portletIds) {
		_portletIds = portletIds;
	}

	protected void merge(
		PortletCategory portletCategory1, PortletCategory portletCategory2) {

		Collection<PortletCategory> portletCategories =
			portletCategory2.getCategories();

		for (PortletCategory curPortletCategory2 : portletCategories) {
			PortletCategory curPortletCategory1 = portletCategory1.getCategory(
				curPortletCategory2.getName());

			if (curPortletCategory1 != null) {
				merge(curPortletCategory1, curPortletCategory2);
			}
			else {
				portletCategory1.addCategory(curPortletCategory2);
			}
		}

		Set<String> portletIds1 = portletCategory1.getPortletIds();
		Set<String> portletIds2 = portletCategory2.getPortletIds();

		portletIds1.addAll(portletIds2);
	}

	protected void setParentCategory(PortletCategory portletCategory) {
		_parentPortletCategory = portletCategory;
	}

	protected void setPath(String path) {
		_path = path;
	}

	private static final String _DELIMITER = StringPool.DOUBLE_SLASH;

	private final String _name;
	private PortletCategory _parentPortletCategory;
	private String _path;
	private final Map<String, PortletCategory> _portletCategories;
	private Set<String> _portletIds;

}