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
 * This class is a wrapper for {@link Child}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Child
 * @generated
 */
public class ChildWrapper
	extends BaseModelWrapper<Child> implements Child, ModelWrapper<Child> {

	public ChildWrapper(Child child) {
		super(child);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("childId", getChildId());
		attributes.put("companyId", getCompanyId());
		attributes.put("name", getName());
		attributes.put("grandParentId", getGrandParentId());
		attributes.put("parentChildId", getParentChildId());
		attributes.put("parentName", getParentName());

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

		Long childId = (Long)attributes.get("childId");

		if (childId != null) {
			setChildId(childId);
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

		Long parentChildId = (Long)attributes.get("parentChildId");

		if (parentChildId != null) {
			setParentChildId(parentChildId);
		}

		String parentName = (String)attributes.get("parentName");

		if (parentName != null) {
			setParentName(parentName);
		}
	}

	@Override
	public Child cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the child ID of this child.
	 *
	 * @return the child ID of this child
	 */
	@Override
	public long getChildId() {
		return model.getChildId();
	}

	/**
	 * Returns the company ID of this child.
	 *
	 * @return the company ID of this child
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the ct collection ID of this child.
	 *
	 * @return the ct collection ID of this child
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the grand parent ID of this child.
	 *
	 * @return the grand parent ID of this child
	 */
	@Override
	public long getGrandParentId() {
		return model.getGrandParentId();
	}

	/**
	 * Returns the mvcc version of this child.
	 *
	 * @return the mvcc version of this child
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this child.
	 *
	 * @return the name of this child
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the parent child ID of this child.
	 *
	 * @return the parent child ID of this child
	 */
	@Override
	public long getParentChildId() {
		return model.getParentChildId();
	}

	/**
	 * Returns the parent name of this child.
	 *
	 * @return the parent name of this child
	 */
	@Override
	public String getParentName() {
		return model.getParentName();
	}

	/**
	 * Returns the primary key of this child.
	 *
	 * @return the primary key of this child
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
	 * Sets the child ID of this child.
	 *
	 * @param childId the child ID of this child
	 */
	@Override
	public void setChildId(long childId) {
		model.setChildId(childId);
	}

	/**
	 * Sets the company ID of this child.
	 *
	 * @param companyId the company ID of this child
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the ct collection ID of this child.
	 *
	 * @param ctCollectionId the ct collection ID of this child
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the grand parent ID of this child.
	 *
	 * @param grandParentId the grand parent ID of this child
	 */
	@Override
	public void setGrandParentId(long grandParentId) {
		model.setGrandParentId(grandParentId);
	}

	/**
	 * Sets the mvcc version of this child.
	 *
	 * @param mvccVersion the mvcc version of this child
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this child.
	 *
	 * @param name the name of this child
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the parent child ID of this child.
	 *
	 * @param parentChildId the parent child ID of this child
	 */
	@Override
	public void setParentChildId(long parentChildId) {
		model.setParentChildId(parentChildId);
	}

	/**
	 * Sets the parent name of this child.
	 *
	 * @param parentName the parent name of this child
	 */
	@Override
	public void setParentName(String parentName) {
		model.setParentName(parentName);
	}

	/**
	 * Sets the primary key of this child.
	 *
	 * @param primaryKey the primary key of this child
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
	public Map<String, Function<Child, Object>> getAttributeGetterFunctions() {
		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<Child, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected ChildWrapper wrap(Child child) {
		return new ChildWrapper(child);
	}

}