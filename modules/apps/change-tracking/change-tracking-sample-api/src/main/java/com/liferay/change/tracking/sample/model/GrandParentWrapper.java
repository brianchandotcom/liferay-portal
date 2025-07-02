/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.sample.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link GrandParent}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see GrandParent
 * @generated
 */
public class GrandParentWrapper
	extends BaseModelWrapper<GrandParent>
	implements GrandParent, ModelWrapper<GrandParent> {

	public GrandParentWrapper(GrandParent grandParent) {
		super(grandParent);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("grandParentId", getGrandParentId());
		attributes.put("companyId", getCompanyId());
		attributes.put("name", getName());
		attributes.put("parentGrandParentId", getParentGrandParentId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long grandParentId = (Long)attributes.get("grandParentId");

		if (grandParentId != null) {
			setGrandParentId(grandParentId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Long parentGrandParentId = (Long)attributes.get("parentGrandParentId");

		if (parentGrandParentId != null) {
			setParentGrandParentId(parentGrandParentId);
		}
	}

	@Override
	public GrandParent cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this grand parent.
	 *
	 * @return the company ID of this grand parent
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the grand parent ID of this grand parent.
	 *
	 * @return the grand parent ID of this grand parent
	 */
	@Override
	public long getGrandParentId() {
		return model.getGrandParentId();
	}

	/**
	 * Returns the mvcc version of this grand parent.
	 *
	 * @return the mvcc version of this grand parent
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this grand parent.
	 *
	 * @return the name of this grand parent
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the parent grand parent ID of this grand parent.
	 *
	 * @return the parent grand parent ID of this grand parent
	 */
	@Override
	public long getParentGrandParentId() {
		return model.getParentGrandParentId();
	}

	/**
	 * Returns the primary key of this grand parent.
	 *
	 * @return the primary key of this grand parent
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
	 * Sets the company ID of this grand parent.
	 *
	 * @param companyId the company ID of this grand parent
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the grand parent ID of this grand parent.
	 *
	 * @param grandParentId the grand parent ID of this grand parent
	 */
	@Override
	public void setGrandParentId(long grandParentId) {
		model.setGrandParentId(grandParentId);
	}

	/**
	 * Sets the mvcc version of this grand parent.
	 *
	 * @param mvccVersion the mvcc version of this grand parent
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this grand parent.
	 *
	 * @param name the name of this grand parent
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the parent grand parent ID of this grand parent.
	 *
	 * @param parentGrandParentId the parent grand parent ID of this grand parent
	 */
	@Override
	public void setParentGrandParentId(long parentGrandParentId) {
		model.setParentGrandParentId(parentGrandParentId);
	}

	/**
	 * Sets the primary key of this grand parent.
	 *
	 * @param primaryKey the primary key of this grand parent
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
	protected GrandParentWrapper wrap(GrandParent grandParent) {
		return new GrandParentWrapper(grandParent);
	}

}