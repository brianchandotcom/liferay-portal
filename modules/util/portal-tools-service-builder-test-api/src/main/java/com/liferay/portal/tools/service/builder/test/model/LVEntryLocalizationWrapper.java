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
 * This class is a wrapper for {@link LVEntryLocalization}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LVEntryLocalization
 * @generated
 */
@ProviderType
public class LVEntryLocalizationWrapper implements LVEntryLocalization,
	ModelWrapper<LVEntryLocalization> {
	public LVEntryLocalizationWrapper(LVEntryLocalization lvEntryLocalization) {
		_lvEntryLocalization = lvEntryLocalization;
	}

	@Override
	public Class<?> getModelClass() {
		return LVEntryLocalization.class;
	}

	@Override
	public String getModelClassName() {
		return LVEntryLocalization.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<LVEntryLocalization, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<LVEntryLocalization, Object>> entry : attributeGetterFunctions.entrySet()) {
			String attributeName = entry.getKey();
			Function<LVEntryLocalization, Object> attributeGetterFunction = entry.getValue();

			attributes.put(attributeName, attributeGetterFunction.apply(this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<LVEntryLocalization, Object>> attributeSetterBiConsumers =
			getAttributeSetterBiConsumers();

		for (Map.Entry<String, BiConsumer<LVEntryLocalization, Object>> entry : attributeSetterBiConsumers.entrySet()) {
			String attributeName = entry.getKey();
			BiConsumer<LVEntryLocalization, Object> attributeBiConsumer = entry.getValue();

			attributeBiConsumer.accept(this,
				attributeSetterBiConsumers.get(attributeName));
		}
	}

	@Override
	public Object clone() {
		return new LVEntryLocalizationWrapper((LVEntryLocalization)_lvEntryLocalization.clone());
	}

	@Override
	public int compareTo(LVEntryLocalization lvEntryLocalization) {
		return _lvEntryLocalization.compareTo(lvEntryLocalization);
	}

	@Override
	public Map<String, Function<LVEntryLocalization, Object>> getAttributeGetters() {
		return _lvEntryLocalization.getAttributeGetters();
	}

	@Override
	public Map<String, BiConsumer<LVEntryLocalization, Object>> getAttributeSetters() {
		return _lvEntryLocalization.getAttributeSetters();
	}

	/**
	* Returns the content of this lv entry localization.
	*
	* @return the content of this lv entry localization
	*/
	@Override
	public String getContent() {
		return _lvEntryLocalization.getContent();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _lvEntryLocalization.getExpandoBridge();
	}

	/**
	* Returns the head ID of this lv entry localization.
	*
	* @return the head ID of this lv entry localization
	*/
	@Override
	public long getHeadId() {
		return _lvEntryLocalization.getHeadId();
	}

	/**
	* Returns the language ID of this lv entry localization.
	*
	* @return the language ID of this lv entry localization
	*/
	@Override
	public String getLanguageId() {
		return _lvEntryLocalization.getLanguageId();
	}

	/**
	* Returns the lv entry ID of this lv entry localization.
	*
	* @return the lv entry ID of this lv entry localization
	*/
	@Override
	public long getLvEntryId() {
		return _lvEntryLocalization.getLvEntryId();
	}

	/**
	* Returns the lv entry localization ID of this lv entry localization.
	*
	* @return the lv entry localization ID of this lv entry localization
	*/
	@Override
	public long getLvEntryLocalizationId() {
		return _lvEntryLocalization.getLvEntryLocalizationId();
	}

	/**
	* Returns the mvcc version of this lv entry localization.
	*
	* @return the mvcc version of this lv entry localization
	*/
	@Override
	public long getMvccVersion() {
		return _lvEntryLocalization.getMvccVersion();
	}

	/**
	* Returns the primary key of this lv entry localization.
	*
	* @return the primary key of this lv entry localization
	*/
	@Override
	public long getPrimaryKey() {
		return _lvEntryLocalization.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _lvEntryLocalization.getPrimaryKeyObj();
	}

	/**
	* Returns the title of this lv entry localization.
	*
	* @return the title of this lv entry localization
	*/
	@Override
	public String getTitle() {
		return _lvEntryLocalization.getTitle();
	}

	@Override
	public int hashCode() {
		return _lvEntryLocalization.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _lvEntryLocalization.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _lvEntryLocalization.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _lvEntryLocalization.isNew();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_lvEntryLocalization.setCachedModel(cachedModel);
	}

	/**
	* Sets the content of this lv entry localization.
	*
	* @param content the content of this lv entry localization
	*/
	@Override
	public void setContent(String content) {
		_lvEntryLocalization.setContent(content);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_lvEntryLocalization.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_lvEntryLocalization.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_lvEntryLocalization.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the head ID of this lv entry localization.
	*
	* @param headId the head ID of this lv entry localization
	*/
	@Override
	public void setHeadId(long headId) {
		_lvEntryLocalization.setHeadId(headId);
	}

	/**
	* Sets the language ID of this lv entry localization.
	*
	* @param languageId the language ID of this lv entry localization
	*/
	@Override
	public void setLanguageId(String languageId) {
		_lvEntryLocalization.setLanguageId(languageId);
	}

	/**
	* Sets the lv entry ID of this lv entry localization.
	*
	* @param lvEntryId the lv entry ID of this lv entry localization
	*/
	@Override
	public void setLvEntryId(long lvEntryId) {
		_lvEntryLocalization.setLvEntryId(lvEntryId);
	}

	/**
	* Sets the lv entry localization ID of this lv entry localization.
	*
	* @param lvEntryLocalizationId the lv entry localization ID of this lv entry localization
	*/
	@Override
	public void setLvEntryLocalizationId(long lvEntryLocalizationId) {
		_lvEntryLocalization.setLvEntryLocalizationId(lvEntryLocalizationId);
	}

	/**
	* Sets the mvcc version of this lv entry localization.
	*
	* @param mvccVersion the mvcc version of this lv entry localization
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_lvEntryLocalization.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_lvEntryLocalization.setNew(n);
	}

	/**
	* Sets the primary key of this lv entry localization.
	*
	* @param primaryKey the primary key of this lv entry localization
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_lvEntryLocalization.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_lvEntryLocalization.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the title of this lv entry localization.
	*
	* @param title the title of this lv entry localization
	*/
	@Override
	public void setTitle(String title) {
		_lvEntryLocalization.setTitle(title);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<LVEntryLocalization> toCacheModel() {
		return _lvEntryLocalization.toCacheModel();
	}

	@Override
	public LVEntryLocalization toEscapedModel() {
		return new LVEntryLocalizationWrapper(_lvEntryLocalization.toEscapedModel());
	}

	@Override
	public String toString() {
		return _lvEntryLocalization.toString();
	}

	@Override
	public LVEntryLocalization toUnescapedModel() {
		return new LVEntryLocalizationWrapper(_lvEntryLocalization.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _lvEntryLocalization.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LVEntryLocalizationWrapper)) {
			return false;
		}

		LVEntryLocalizationWrapper lvEntryLocalizationWrapper = (LVEntryLocalizationWrapper)obj;

		if (Objects.equals(_lvEntryLocalization,
					lvEntryLocalizationWrapper._lvEntryLocalization)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isHead() {
		return _lvEntryLocalization.isHead();
	}

	@Override
	public void populateVersionModel(
		LVEntryLocalizationVersion lvEntryLocalizationVersion) {
		_lvEntryLocalization.populateVersionModel(lvEntryLocalizationVersion);
	}

	@Override
	public LVEntryLocalization getWrappedModel() {
		return _lvEntryLocalization;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _lvEntryLocalization.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _lvEntryLocalization.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_lvEntryLocalization.resetOriginalValues();
	}

	private final LVEntryLocalization _lvEntryLocalization;
}