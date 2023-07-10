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
 * This class is a wrapper for {@link PermissionPropagationEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PermissionPropagationEntry
 * @generated
 */
public class PermissionPropagationEntryWrapper
	extends BaseModelWrapper<PermissionPropagationEntry>
	implements ModelWrapper<PermissionPropagationEntry>,
			   PermissionPropagationEntry {

	public PermissionPropagationEntryWrapper(
		PermissionPropagationEntry permissionPropagationEntry) {

		super(permissionPropagationEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put(
			"permissionPropagationEntryId", getPermissionPropagationEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("propagation", isPropagation());

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

		Long permissionPropagationEntryId = (Long)attributes.get(
			"permissionPropagationEntryId");

		if (permissionPropagationEntryId != null) {
			setPermissionPropagationEntryId(permissionPropagationEntryId);
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

		Boolean propagation = (Boolean)attributes.get("propagation");

		if (propagation != null) {
			setPropagation(propagation);
		}
	}

	@Override
	public PermissionPropagationEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the fully qualified class name of this permission propagation entry.
	 *
	 * @return the fully qualified class name of this permission propagation entry
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this permission propagation entry.
	 *
	 * @return the class name ID of this permission propagation entry
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this permission propagation entry.
	 *
	 * @return the class pk of this permission propagation entry
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this permission propagation entry.
	 *
	 * @return the company ID of this permission propagation entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the ct collection ID of this permission propagation entry.
	 *
	 * @return the ct collection ID of this permission propagation entry
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the group ID of this permission propagation entry.
	 *
	 * @return the group ID of this permission propagation entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the mvcc version of this permission propagation entry.
	 *
	 * @return the mvcc version of this permission propagation entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the permission propagation entry ID of this permission propagation entry.
	 *
	 * @return the permission propagation entry ID of this permission propagation entry
	 */
	@Override
	public long getPermissionPropagationEntryId() {
		return model.getPermissionPropagationEntryId();
	}

	/**
	 * Returns the primary key of this permission propagation entry.
	 *
	 * @return the primary key of this permission propagation entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the propagation of this permission propagation entry.
	 *
	 * @return the propagation of this permission propagation entry
	 */
	@Override
	public boolean getPropagation() {
		return model.getPropagation();
	}

	/**
	 * Returns <code>true</code> if this permission propagation entry is propagation.
	 *
	 * @return <code>true</code> if this permission propagation entry is propagation; <code>false</code> otherwise
	 */
	@Override
	public boolean isPropagation() {
		return model.isPropagation();
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
	 * Sets the class name ID of this permission propagation entry.
	 *
	 * @param classNameId the class name ID of this permission propagation entry
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this permission propagation entry.
	 *
	 * @param classPK the class pk of this permission propagation entry
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this permission propagation entry.
	 *
	 * @param companyId the company ID of this permission propagation entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the ct collection ID of this permission propagation entry.
	 *
	 * @param ctCollectionId the ct collection ID of this permission propagation entry
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the group ID of this permission propagation entry.
	 *
	 * @param groupId the group ID of this permission propagation entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the mvcc version of this permission propagation entry.
	 *
	 * @param mvccVersion the mvcc version of this permission propagation entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the permission propagation entry ID of this permission propagation entry.
	 *
	 * @param permissionPropagationEntryId the permission propagation entry ID of this permission propagation entry
	 */
	@Override
	public void setPermissionPropagationEntryId(
		long permissionPropagationEntryId) {

		model.setPermissionPropagationEntryId(permissionPropagationEntryId);
	}

	/**
	 * Sets the primary key of this permission propagation entry.
	 *
	 * @param primaryKey the primary key of this permission propagation entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets whether this permission propagation entry is propagation.
	 *
	 * @param propagation the propagation of this permission propagation entry
	 */
	@Override
	public void setPropagation(boolean propagation) {
		model.setPropagation(propagation);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<PermissionPropagationEntry, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<PermissionPropagationEntry, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected PermissionPropagationEntryWrapper wrap(
		PermissionPropagationEntry permissionPropagationEntry) {

		return new PermissionPropagationEntryWrapper(
			permissionPropagationEntry);
	}

}