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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link LVEntryVersion}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LVEntryVersion
 * @generated
 */
@ProviderType
public class LVEntryVersionWrapper implements LVEntryVersion,
	ModelWrapper<LVEntryVersion> {
	public LVEntryVersionWrapper(LVEntryVersion lvEntryVersion) {
		_lvEntryVersion = lvEntryVersion;
	}

	@Override
	public Class<?> getModelClass() {
		return LVEntryVersion.class;
	}

	@Override
	public String getModelClassName() {
		return LVEntryVersion.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<LVEntryVersion, Object>> attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<LVEntryVersion, Object>> entry : attributeGetterFunctions.entrySet()) {
			String attributeName = entry.getKey();
			Function<LVEntryVersion, Object> attributeGetterFunction = entry.getValue();

			attributes.put(attributeName, attributeGetterFunction.apply(this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<LVEntryVersion, Object>> attributeSetterBiConsumers =
			getAttributeSetterBiConsumers();

		for (Map.Entry<String, BiConsumer<LVEntryVersion, Object>> entry : attributeSetterBiConsumers.entrySet()) {
			String attributeName = entry.getKey();
			BiConsumer<LVEntryVersion, Object> attributeBiConsumer = entry.getValue();

			attributeBiConsumer.accept(this,
				attributeSetterBiConsumers.get(attributeName));
		}
	}

	@Override
	public Object clone() {
		return new LVEntryVersionWrapper((LVEntryVersion)_lvEntryVersion.clone());
	}

	@Override
	public int compareTo(LVEntryVersion lvEntryVersion) {
		return _lvEntryVersion.compareTo(lvEntryVersion);
	}

	@Override
	public Map<String, Function<LVEntryVersion, Object>> getAttributeGetters() {
		return _lvEntryVersion.getAttributeGetters();
	}

	@Override
	public Map<String, BiConsumer<LVEntryVersion, Object>> getAttributeSetters() {
		return _lvEntryVersion.getAttributeSetters();
	}

	/**
	* Returns the default language ID of this lv entry version.
	*
	* @return the default language ID of this lv entry version
	*/
	@Override
	public String getDefaultLanguageId() {
		return _lvEntryVersion.getDefaultLanguageId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _lvEntryVersion.getExpandoBridge();
	}

	/**
	* Returns the group ID of this lv entry version.
	*
	* @return the group ID of this lv entry version
	*/
	@Override
	public long getGroupId() {
		return _lvEntryVersion.getGroupId();
	}

	/**
	* Returns the lv entry ID of this lv entry version.
	*
	* @return the lv entry ID of this lv entry version
	*/
	@Override
	public long getLvEntryId() {
		return _lvEntryVersion.getLvEntryId();
	}

	/**
	* Returns the lv entry version ID of this lv entry version.
	*
	* @return the lv entry version ID of this lv entry version
	*/
	@Override
	public long getLvEntryVersionId() {
		return _lvEntryVersion.getLvEntryVersionId();
	}

	/**
	* Returns the primary key of this lv entry version.
	*
	* @return the primary key of this lv entry version
	*/
	@Override
	public long getPrimaryKey() {
		return _lvEntryVersion.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _lvEntryVersion.getPrimaryKeyObj();
	}

	/**
	* Returns the version of this lv entry version.
	*
	* @return the version of this lv entry version
	*/
	@Override
	public int getVersion() {
		return _lvEntryVersion.getVersion();
	}

	@Override
	public int hashCode() {
		return _lvEntryVersion.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _lvEntryVersion.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _lvEntryVersion.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _lvEntryVersion.isNew();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_lvEntryVersion.setCachedModel(cachedModel);
	}

	/**
	* Sets the default language ID of this lv entry version.
	*
	* @param defaultLanguageId the default language ID of this lv entry version
	*/
	@Override
	public void setDefaultLanguageId(String defaultLanguageId) {
		_lvEntryVersion.setDefaultLanguageId(defaultLanguageId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_lvEntryVersion.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_lvEntryVersion.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_lvEntryVersion.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this lv entry version.
	*
	* @param groupId the group ID of this lv entry version
	*/
	@Override
	public void setGroupId(long groupId) {
		_lvEntryVersion.setGroupId(groupId);
	}

	/**
	* Sets the lv entry ID of this lv entry version.
	*
	* @param lvEntryId the lv entry ID of this lv entry version
	*/
	@Override
	public void setLvEntryId(long lvEntryId) {
		_lvEntryVersion.setLvEntryId(lvEntryId);
	}

	/**
	* Sets the lv entry version ID of this lv entry version.
	*
	* @param lvEntryVersionId the lv entry version ID of this lv entry version
	*/
	@Override
	public void setLvEntryVersionId(long lvEntryVersionId) {
		_lvEntryVersion.setLvEntryVersionId(lvEntryVersionId);
	}

	@Override
	public void setNew(boolean n) {
		_lvEntryVersion.setNew(n);
	}

	/**
	* Sets the primary key of this lv entry version.
	*
	* @param primaryKey the primary key of this lv entry version
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_lvEntryVersion.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_lvEntryVersion.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the version of this lv entry version.
	*
	* @param version the version of this lv entry version
	*/
	@Override
	public void setVersion(int version) {
		_lvEntryVersion.setVersion(version);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<LVEntryVersion> toCacheModel() {
		return _lvEntryVersion.toCacheModel();
	}

	@Override
	public LVEntryVersion toEscapedModel() {
		return new LVEntryVersionWrapper(_lvEntryVersion.toEscapedModel());
	}

	@Override
	public String toString() {
		return _lvEntryVersion.toString();
	}

	@Override
	public LVEntryVersion toUnescapedModel() {
		return new LVEntryVersionWrapper(_lvEntryVersion.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _lvEntryVersion.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LVEntryVersionWrapper)) {
			return false;
		}

		LVEntryVersionWrapper lvEntryVersionWrapper = (LVEntryVersionWrapper)obj;

		if (Objects.equals(_lvEntryVersion,
					lvEntryVersionWrapper._lvEntryVersion)) {
			return true;
		}

		return false;
	}

	@Override
	public long getVersionedModelId() {
		return _lvEntryVersion.getVersionedModelId();
	}

	@Override
	public void setVersionedModelId(long id) {
		_lvEntryVersion.setVersionedModelId(id);
	}

	@Override
	public void populateVersionedModel(LVEntry lvEntry) {
		_lvEntryVersion.populateVersionedModel(lvEntry);
	}

	@Override
	public LVEntry toVersionedModel() {
		return _lvEntryVersion.toVersionedModel();
	}

	@Override
	public LVEntryVersion getWrappedModel() {
		return _lvEntryVersion;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _lvEntryVersion.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _lvEntryVersion.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_lvEntryVersion.resetOriginalValues();
	}

	private final LVEntryVersion _lvEntryVersion;
}