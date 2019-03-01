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

package com.liferay.change.tracking.service.test.model;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public class TestResourceModelClass
	implements BaseModel<TestResourceModelClass> {

	@Override
	public Object clone() {
		return null;
	}

	@Override
	public int compareTo(TestResourceModelClass testVersionClass) {
		return 0;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return null;
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		return null;
	}

	@Override
	public Class<?> getModelClass() {
		return null;
	}

	@Override
	public String getModelClassName() {
		return null;
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return null;
	}

	@Override
	public boolean isCachedModel() {
		return false;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return false;
	}

	@Override
	public boolean isEscapedModel() {
		return false;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return false;
	}

	@Override
	public boolean isNew() {
		return false;
	}

	@Override
	public void resetOriginalValues() {
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel baseModel) {
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
	}

	@Override
	public void setModelAttributes(Map attributes) {
	}

	@Override
	public void setNew(boolean n) {
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
	}

	@Override
	public CacheModel<TestResourceModelClass> toCacheModel() {
		return null;
	}

	@Override
	public TestResourceModelClass toEscapedModel() {
		return null;
	}

	@Override
	public TestResourceModelClass toUnescapedModel() {
		return null;
	}

	@Override
	public String toXmlString() {
		return null;
	}

}