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

package com.liferay.portal.tools.service.builder.test.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link BigDecimalEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BigDecimalEntry
 * @generated
 */
@ProviderType
public class BigDecimalEntryWrapper implements BigDecimalEntry,
	ModelWrapper<BigDecimalEntry> {
	public BigDecimalEntryWrapper(BigDecimalEntry bigDecimalEntry) {
		_bigDecimalEntry = bigDecimalEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return BigDecimalEntry.class;
	}

	@Override
	public String getModelClassName() {
		return BigDecimalEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("bigDecimalEntryId", getBigDecimalEntryId());
		attributes.put("bigDecimalValue", getBigDecimalValue());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long bigDecimalEntryId = (Long)attributes.get("bigDecimalEntryId");

		if (bigDecimalEntryId != null) {
			setBigDecimalEntryId(bigDecimalEntryId);
		}

		BigDecimal bigDecimalValue = (BigDecimal)attributes.get(
				"bigDecimalValue");

		if (bigDecimalValue != null) {
			setBigDecimalValue(bigDecimalValue);
		}
	}

	@Override
	public Object clone() {
		return new BigDecimalEntryWrapper((BigDecimalEntry)_bigDecimalEntry.clone());
	}

	@Override
	public int compareTo(BigDecimalEntry bigDecimalEntry) {
		return _bigDecimalEntry.compareTo(bigDecimalEntry);
	}

	/**
	* Returns the big decimal entry ID of this big decimal entry.
	*
	* @return the big decimal entry ID of this big decimal entry
	*/
	@Override
	public long getBigDecimalEntryId() {
		return _bigDecimalEntry.getBigDecimalEntryId();
	}

	/**
	* Returns the big decimal value of this big decimal entry.
	*
	* @return the big decimal value of this big decimal entry
	*/
	@Override
	public BigDecimal getBigDecimalValue() {
		return _bigDecimalEntry.getBigDecimalValue();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _bigDecimalEntry.getExpandoBridge();
	}

	/**
	* Returns the primary key of this big decimal entry.
	*
	* @return the primary key of this big decimal entry
	*/
	@Override
	public long getPrimaryKey() {
		return _bigDecimalEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _bigDecimalEntry.getPrimaryKeyObj();
	}

	@Override
	public int hashCode() {
		return _bigDecimalEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _bigDecimalEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _bigDecimalEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _bigDecimalEntry.isNew();
	}

	/**
	* Sets the big decimal entry ID of this big decimal entry.
	*
	* @param bigDecimalEntryId the big decimal entry ID of this big decimal entry
	*/
	@Override
	public void setBigDecimalEntryId(long bigDecimalEntryId) {
		_bigDecimalEntry.setBigDecimalEntryId(bigDecimalEntryId);
	}

	/**
	* Sets the big decimal value of this big decimal entry.
	*
	* @param bigDecimalValue the big decimal value of this big decimal entry
	*/
	@Override
	public void setBigDecimalValue(BigDecimal bigDecimalValue) {
		_bigDecimalEntry.setBigDecimalValue(bigDecimalValue);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_bigDecimalEntry.setCachedModel(cachedModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_bigDecimalEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_bigDecimalEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_bigDecimalEntry.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void setNew(boolean n) {
		_bigDecimalEntry.setNew(n);
	}

	/**
	* Sets the primary key of this big decimal entry.
	*
	* @param primaryKey the primary key of this big decimal entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_bigDecimalEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_bigDecimalEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<BigDecimalEntry> toCacheModel() {
		return _bigDecimalEntry.toCacheModel();
	}

	@Override
	public BigDecimalEntry toEscapedModel() {
		return new BigDecimalEntryWrapper(_bigDecimalEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _bigDecimalEntry.toString();
	}

	@Override
	public BigDecimalEntry toUnescapedModel() {
		return new BigDecimalEntryWrapper(_bigDecimalEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _bigDecimalEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BigDecimalEntryWrapper)) {
			return false;
		}

		BigDecimalEntryWrapper bigDecimalEntryWrapper = (BigDecimalEntryWrapper)obj;

		if (Objects.equals(_bigDecimalEntry,
					bigDecimalEntryWrapper._bigDecimalEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public BigDecimalEntry getWrappedModel() {
		return _bigDecimalEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _bigDecimalEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _bigDecimalEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_bigDecimalEntry.resetOriginalValues();
	}

	private final BigDecimalEntry _bigDecimalEntry;
}