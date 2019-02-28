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

import com.liferay.portal.kernel.dao.search.ResultRowSplitter;
import com.liferay.portal.kernel.dao.search.SearchContainer;

import java.util.Arrays;
import java.util.List;

/**
 * @author Drew Brokke
 */
public class ViewUADEntitiesDisplay {

	public String getApplicationKey() {
		return _applicationKey;
	}

	public ResultRowSplitter getResultRowSplitter() {
		return _resultRowSplitter;
	}

	public SearchContainer<UADEntity> getSearchContainer() {
		return _searchContainer;
	}

	public List<Class<?>> getTypeClasses() {
		return _typeClasses;
	}

	public String getTypeName() {
		return _typeName;
	}

	public String getUadRegistryKey() {
		return _uadRegistryKey;
	}

	public boolean isHierarchy() {
		return _hierarchy;
	}

	public void setApplicationKey(String applicationKey) {
		_applicationKey = applicationKey;
	}

	public void setHierarchy(boolean hierarchy) {
		_hierarchy = hierarchy;
	}

	public void setResultRowSplitter(ResultRowSplitter resultRowSplitter) {
		_resultRowSplitter = resultRowSplitter;
	}

	public void setSearchContainer(SearchContainer<UADEntity> searchContainer) {
		_searchContainer = searchContainer;
	}

	public void setTypeClasses(Class<?>[] typeClasses) {
		_typeClasses = Arrays.asList(typeClasses);
	}

	public void setTypeName(String typeName) {
		_typeName = typeName;
	}

	public void setUadRegistryKey(String uadRegistryKey) {
		_uadRegistryKey = uadRegistryKey;
	}

	private String _applicationKey;
	private boolean _hierarchy;
	private ResultRowSplitter _resultRowSplitter;
	private SearchContainer<UADEntity> _searchContainer;
	private List<Class<?>> _typeClasses;
	private String _typeName;
	private String _uadRegistryKey = "";

}