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

package com.liferay.user.associated.data.web.internal.display;

import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.dao.search.ResultRowSplitter;
import com.liferay.portal.kernel.dao.search.SearchContainer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Drew Brokke
 */
public class ViewUADEntitiesDisplay {

	public String getApplicationKey() {
		return _applicationKey;
	}

	public long[] getGroupIds() {
		return _groupIds;
	}

	public ResultRowSplitter getResultRowSplitter() {
		return _resultRowSplitter;
	}

	public String getScope() {
		return _scope;
	}

	public SearchContainer<UADEntity> getSearchContainer() {
		return _searchContainer;
	}

	public String getSearchContainerID(
		HttpServletRequest httpServletRequest, String namespace) {

		if (_searchContainer == null) {
			return StringPool.BLANK;
		}

		return _searchContainer.getId(httpServletRequest, namespace);
	}

	public Class<?>[] getTypeClasses() {
		return _typeClasses;
	}

	public String getTypeName() {
		return _typeName;
	}

	public String getUADRegistryKey() {
		return _uadRegistryKey;
	}

	public String getUserOwnedEntityPKsString() {
		if (_searchContainer == null) {
			return StringPool.BLANK;
		}

		List<String> userOwnedPKs = new ArrayList<>();

		List<UADEntity> entities = _searchContainer.getResults();

		for (UADEntity entity : entities) {
			if (entity.isUserOwned()) {
				userOwnedPKs.add(String.valueOf(entity.getPrimaryKey()));
			}
		}

		return StringUtil.merge(
			userOwnedPKs.toArray(new String[0]), StringPool.COMMA);
	}

	public boolean isHierarchy() {
		return _hierarchy;
	}

	public void setApplicationKey(String applicationKey) {
		_applicationKey = applicationKey;
	}

	public void setGroupIds(long[] groupIds) {
		_groupIds = groupIds;
	}

	public void setHierarchy(boolean hierarchy) {
		_hierarchy = hierarchy;
	}

	public void setResultRowSplitter(ResultRowSplitter resultRowSplitter) {
		_resultRowSplitter = resultRowSplitter;
	}

	public void setScope(String scope) {
		_scope = scope;
	}

	public void setSearchContainer(SearchContainer<UADEntity> searchContainer) {
		_searchContainer = searchContainer;
	}

	public void setTypeClasses(Class<?>[] typeClasses) {
		_typeClasses = typeClasses;
	}

	public void setTypeName(String typeName) {
		_typeName = typeName;
	}

	public void setUADRegistryKey(String uadRegistryKey) {
		_uadRegistryKey = uadRegistryKey;
	}

	private String _applicationKey;
	private long[] _groupIds;
	private boolean _hierarchy;
	private ResultRowSplitter _resultRowSplitter;
	private String _scope;
	private SearchContainer<UADEntity> _searchContainer;
	private Class<?>[] _typeClasses;
	private String _typeName;
	private String _uadRegistryKey = StringPool.BLANK;

}