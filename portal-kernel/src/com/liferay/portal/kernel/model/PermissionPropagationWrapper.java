/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link PermissionPropagation}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PermissionPropagation
 * @generated
 */
public class PermissionPropagationWrapper
	extends BaseModelWrapper<PermissionPropagation>
	implements ModelWrapper<PermissionPropagation>, PermissionPropagation {

	public PermissionPropagationWrapper(
		PermissionPropagation permissionPropagation) {

		super(permissionPropagation);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("permissionPropagationId", getPermissionPropagationId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("propagate", isPropagate());

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

		Long permissionPropagationId = (Long)attributes.get(
			"permissionPropagationId");

		if (permissionPropagationId != null) {
			setPermissionPropagationId(permissionPropagationId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Boolean propagate = (Boolean)attributes.get("propagate");

		if (propagate != null) {
			setPropagate(propagate);
		}
	}

	@Override
	public PermissionPropagation cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the fully qualified class name of this permission propagation.
	 *
	 * @return the fully qualified class name of this permission propagation
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this permission propagation.
	 *
	 * @return the class name ID of this permission propagation
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this permission propagation.
	 *
	 * @return the class pk of this permission propagation
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this permission propagation.
	 *
	 * @return the company ID of this permission propagation
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the ct collection ID of this permission propagation.
	 *
	 * @return the ct collection ID of this permission propagation
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the group ID of this permission propagation.
	 *
	 * @return the group ID of this permission propagation
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the mvcc version of this permission propagation.
	 *
	 * @return the mvcc version of this permission propagation
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the permission propagation ID of this permission propagation.
	 *
	 * @return the permission propagation ID of this permission propagation
	 */
	@Override
	public long getPermissionPropagationId() {
		return model.getPermissionPropagationId();
	}

	/**
	 * Returns the primary key of this permission propagation.
	 *
	 * @return the primary key of this permission propagation
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the propagate of this permission propagation.
	 *
	 * @return the propagate of this permission propagation
	 */
	@Override
	public boolean getPropagate() {
		return model.getPropagate();
	}

	/**
	 * Returns <code>true</code> if this permission propagation is propagate.
	 *
	 * @return <code>true</code> if this permission propagation is propagate; <code>false</code> otherwise
	 */
	@Override
	public boolean isPropagate() {
		return model.isPropagate();
	}

	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this permission propagation.
	 *
	 * @param classNameId the class name ID of this permission propagation
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this permission propagation.
	 *
	 * @param classPK the class pk of this permission propagation
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this permission propagation.
	 *
	 * @param companyId the company ID of this permission propagation
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the ct collection ID of this permission propagation.
	 *
	 * @param ctCollectionId the ct collection ID of this permission propagation
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the group ID of this permission propagation.
	 *
	 * @param groupId the group ID of this permission propagation
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the mvcc version of this permission propagation.
	 *
	 * @param mvccVersion the mvcc version of this permission propagation
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the permission propagation ID of this permission propagation.
	 *
	 * @param permissionPropagationId the permission propagation ID of this permission propagation
	 */
	@Override
	public void setPermissionPropagationId(long permissionPropagationId) {
		model.setPermissionPropagationId(permissionPropagationId);
	}

	/**
	 * Sets the primary key of this permission propagation.
	 *
	 * @param primaryKey the primary key of this permission propagation
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets whether this permission propagation is propagate.
	 *
	 * @param propagate the propagate of this permission propagation
	 */
	@Override
	public void setPropagate(boolean propagate) {
		model.setPropagate(propagate);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<PermissionPropagation, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<PermissionPropagation, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected PermissionPropagationWrapper wrap(
		PermissionPropagation permissionPropagation) {

		return new PermissionPropagationWrapper(permissionPropagation);
	}

}