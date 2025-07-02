/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.sample.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link Parent}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Parent
 * @generated
 */
public class ParentWrapper
	extends BaseModelWrapper<Parent> implements ModelWrapper<Parent>, Parent {

	public ParentWrapper(Parent parent) {
		super(parent);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("parentId", getParentId());
		attributes.put("companyId", getCompanyId());
		attributes.put("name", getName());
		attributes.put("grandParentId", getGrandParentId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

		Long parentId = (Long)attributes.get("parentId");

		if (parentId != null) {
			setParentId(parentId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Long grandParentId = (Long)attributes.get("grandParentId");

		if (grandParentId != null) {
			setGrandParentId(grandParentId);
		}
	}

	@Override
	public Parent cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this parent.
	 *
	 * @return the company ID of this parent
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the ct collection ID of this parent.
	 *
	 * @return the ct collection ID of this parent
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the grand parent ID of this parent.
	 *
	 * @return the grand parent ID of this parent
	 */
	@Override
	public long getGrandParentId() {
		return model.getGrandParentId();
	}

	/**
	 * Returns the mvcc version of this parent.
	 *
	 * @return the mvcc version of this parent
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this parent.
	 *
	 * @return the name of this parent
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the parent ID of this parent.
	 *
	 * @return the parent ID of this parent
	 */
	@Override
	public long getParentId() {
		return model.getParentId();
	}

	/**
	 * Returns the primary key of this parent.
	 *
	 * @return the primary key of this parent
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
	 * Sets the company ID of this parent.
	 *
	 * @param companyId the company ID of this parent
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the ct collection ID of this parent.
	 *
	 * @param ctCollectionId the ct collection ID of this parent
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the grand parent ID of this parent.
	 *
	 * @param grandParentId the grand parent ID of this parent
	 */
	@Override
	public void setGrandParentId(long grandParentId) {
		model.setGrandParentId(grandParentId);
	}

	/**
	 * Sets the mvcc version of this parent.
	 *
	 * @param mvccVersion the mvcc version of this parent
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this parent.
	 *
	 * @param name the name of this parent
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the parent ID of this parent.
	 *
	 * @param parentId the parent ID of this parent
	 */
	@Override
	public void setParentId(long parentId) {
		model.setParentId(parentId);
	}

	/**
	 * Sets the primary key of this parent.
	 *
	 * @param primaryKey the primary key of this parent
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
	public Map<String, Function<Parent, Object>> getAttributeGetterFunctions() {
		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<Parent, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected ParentWrapper wrap(Parent parent) {
		return new ParentWrapper(parent);
	}

}