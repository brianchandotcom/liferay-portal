/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.external.data.source.test.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link TestEntity}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TestEntity
 * @generated
 */
public class TestEntityWrapper
	extends BaseModelWrapper<TestEntity>
	implements ModelWrapper<TestEntity>, TestEntity {

	public TestEntityWrapper(TestEntity testEntity) {
		super(testEntity);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("id", getId());
		attributes.put("data", getData());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long id = (Long)attributes.get("id");

		if (id != null) {
			setId(id);
		}

		String data = (String)attributes.get("data");

		if (data != null) {
			setData(data);
		}
	}

	@Override
	public TestEntity cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the data of this test entity.
	 *
	 * @return the data of this test entity
	 */
	@Override
	public String getData() {
		return model.getData();
	}

	/**
	 * Returns the ID of this test entity.
	 *
	 * @return the ID of this test entity
	 */
	@Override
	public long getId() {
		return model.getId();
	}

	/**
	 * Returns the primary key of this test entity.
	 *
	 * @return the primary key of this test entity
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the data of this test entity.
	 *
	 * @param data the data of this test entity
	 */
	@Override
	public void setData(String data) {
		model.setData(data);
	}

	/**
	 * Sets the ID of this test entity.
	 *
	 * @param id the ID of this test entity
	 */
	@Override
	public void setId(long id) {
		model.setId(id);
	}

	/**
	 * Sets the primary key of this test entity.
	 *
	 * @param primaryKey the primary key of this test entity
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected TestEntityWrapper wrap(TestEntity testEntity) {
		return new TestEntityWrapper(testEntity);
	}

}