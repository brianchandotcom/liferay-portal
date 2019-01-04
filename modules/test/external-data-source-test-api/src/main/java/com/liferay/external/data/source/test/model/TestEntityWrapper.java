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

package com.liferay.external.data.source.test.model;

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
 * This class is a wrapper for {@link TestEntity}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TestEntity
 * @generated
 */
@ProviderType
public class TestEntityWrapper implements TestEntity, ModelWrapper<TestEntity> {
	public TestEntityWrapper(TestEntity testEntity) {
		_testEntity = testEntity;
	}

	@Override
	public Class<?> getModelClass() {
		return TestEntity.class;
	}

	@Override
	public String getModelClassName() {
		return TestEntity.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<TestEntity, Object>> attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<TestEntity, Object>> entry : attributeGetterFunctions.entrySet()) {
			String attributeName = entry.getKey();
			Function<TestEntity, Object> attributeGetterFunction = entry.getValue();

			attributes.put(attributeName, attributeGetterFunction.apply(this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<TestEntity, Object>> attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, BiConsumer<TestEntity, Object>> entry : attributeSetterBiConsumers.entrySet()) {
			String attributeName = entry.getKey();
			BiConsumer<TestEntity, Object> attributeBiConsumer = entry.getValue();

			attributeBiConsumer.accept(this,
				attributeSetterBiConsumers.get(attributeName));
		}
	}

	@Override
	public Object clone() {
		return new TestEntityWrapper((TestEntity)_testEntity.clone());
	}

	@Override
	public int compareTo(TestEntity testEntity) {
		return _testEntity.compareTo(testEntity);
	}

	@Override
	public Map<String, Function<TestEntity, Object>> getAttributeGetters() {
		return _testEntity.getAttributeGetters();
	}

	@Override
	public Map<String, BiConsumer<TestEntity, Object>> getAttributeSetters() {
		return _testEntity.getAttributeSetters();
	}

	/**
	* Returns the data of this test entity.
	*
	* @return the data of this test entity
	*/
	@Override
	public String getData() {
		return _testEntity.getData();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _testEntity.getExpandoBridge();
	}

	/**
	* Returns the ID of this test entity.
	*
	* @return the ID of this test entity
	*/
	@Override
	public long getId() {
		return _testEntity.getId();
	}

	/**
	* Returns the primary key of this test entity.
	*
	* @return the primary key of this test entity
	*/
	@Override
	public long getPrimaryKey() {
		return _testEntity.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _testEntity.getPrimaryKeyObj();
	}

	@Override
	public int hashCode() {
		return _testEntity.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _testEntity.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _testEntity.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _testEntity.isNew();
	}

	@Override
	public void persist() {
		_testEntity.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_testEntity.setCachedModel(cachedModel);
	}

	/**
	* Sets the data of this test entity.
	*
	* @param data the data of this test entity
	*/
	@Override
	public void setData(String data) {
		_testEntity.setData(data);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_testEntity.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_testEntity.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_testEntity.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the ID of this test entity.
	*
	* @param id the ID of this test entity
	*/
	@Override
	public void setId(long id) {
		_testEntity.setId(id);
	}

	@Override
	public void setNew(boolean n) {
		_testEntity.setNew(n);
	}

	/**
	* Sets the primary key of this test entity.
	*
	* @param primaryKey the primary key of this test entity
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_testEntity.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_testEntity.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<TestEntity> toCacheModel() {
		return _testEntity.toCacheModel();
	}

	@Override
	public TestEntity toEscapedModel() {
		return new TestEntityWrapper(_testEntity.toEscapedModel());
	}

	@Override
	public String toString() {
		return _testEntity.toString();
	}

	@Override
	public TestEntity toUnescapedModel() {
		return new TestEntityWrapper(_testEntity.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _testEntity.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof TestEntityWrapper)) {
			return false;
		}

		TestEntityWrapper testEntityWrapper = (TestEntityWrapper)obj;

		if (Objects.equals(_testEntity, testEntityWrapper._testEntity)) {
			return true;
		}

		return false;
	}

	@Override
	public TestEntity getWrappedModel() {
		return _testEntity;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _testEntity.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _testEntity.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_testEntity.resetOriginalValues();
	}

	private final TestEntity _testEntity;
}