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

package com.liferay.data.engine.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DEDataDefinitionFieldLink}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DEDataDefinitionFieldLink
 * @generated
 */
public class DEDataDefinitionFieldLinkWrapper
	extends BaseModelWrapper<DEDataDefinitionFieldLink>
	implements DEDataDefinitionFieldLink,
			   ModelWrapper<DEDataDefinitionFieldLink> {

	public DEDataDefinitionFieldLinkWrapper(
		DEDataDefinitionFieldLink deDataDefinitionFieldLink) {

		super(deDataDefinitionFieldLink);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put(
			"deDataDefinitionFieldLinkId", getDeDataDefinitionFieldLinkId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("ddmStructureId", getDdmStructureId());
		attributes.put("fieldName", getFieldName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long deDataDefinitionFieldLinkId = (Long)attributes.get(
			"deDataDefinitionFieldLinkId");

		if (deDataDefinitionFieldLinkId != null) {
			setDeDataDefinitionFieldLinkId(deDataDefinitionFieldLinkId);
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

		Long ddmStructureId = (Long)attributes.get("ddmStructureId");

		if (ddmStructureId != null) {
			setDdmStructureId(ddmStructureId);
		}

		String fieldName = (String)attributes.get("fieldName");

		if (fieldName != null) {
			setFieldName(fieldName);
		}
	}

	/**
	 * Returns the fully qualified class name of this de data definition field link.
	 *
	 * @return the fully qualified class name of this de data definition field link
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this de data definition field link.
	 *
	 * @return the class name ID of this de data definition field link
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this de data definition field link.
	 *
	 * @return the class pk of this de data definition field link
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this de data definition field link.
	 *
	 * @return the company ID of this de data definition field link
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the ddm structure ID of this de data definition field link.
	 *
	 * @return the ddm structure ID of this de data definition field link
	 */
	@Override
	public long getDdmStructureId() {
		return model.getDdmStructureId();
	}

	/**
	 * Returns the de data definition field link ID of this de data definition field link.
	 *
	 * @return the de data definition field link ID of this de data definition field link
	 */
	@Override
	public long getDeDataDefinitionFieldLinkId() {
		return model.getDeDataDefinitionFieldLinkId();
	}

	/**
	 * Returns the field name of this de data definition field link.
	 *
	 * @return the field name of this de data definition field link
	 */
	@Override
	public String getFieldName() {
		return model.getFieldName();
	}

	/**
	 * Returns the group ID of this de data definition field link.
	 *
	 * @return the group ID of this de data definition field link
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the primary key of this de data definition field link.
	 *
	 * @return the primary key of this de data definition field link
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the uuid of this de data definition field link.
	 *
	 * @return the uuid of this de data definition field link
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a de data definition field link model instance should use the <code>DEDataDefinitionFieldLink</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this de data definition field link.
	 *
	 * @param classNameId the class name ID of this de data definition field link
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this de data definition field link.
	 *
	 * @param classPK the class pk of this de data definition field link
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this de data definition field link.
	 *
	 * @param companyId the company ID of this de data definition field link
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the ddm structure ID of this de data definition field link.
	 *
	 * @param ddmStructureId the ddm structure ID of this de data definition field link
	 */
	@Override
	public void setDdmStructureId(long ddmStructureId) {
		model.setDdmStructureId(ddmStructureId);
	}

	/**
	 * Sets the de data definition field link ID of this de data definition field link.
	 *
	 * @param deDataDefinitionFieldLinkId the de data definition field link ID of this de data definition field link
	 */
	@Override
	public void setDeDataDefinitionFieldLinkId(
		long deDataDefinitionFieldLinkId) {

		model.setDeDataDefinitionFieldLinkId(deDataDefinitionFieldLinkId);
	}

	/**
	 * Sets the field name of this de data definition field link.
	 *
	 * @param fieldName the field name of this de data definition field link
	 */
	@Override
	public void setFieldName(String fieldName) {
		model.setFieldName(fieldName);
	}

	/**
	 * Sets the group ID of this de data definition field link.
	 *
	 * @param groupId the group ID of this de data definition field link
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the primary key of this de data definition field link.
	 *
	 * @param primaryKey the primary key of this de data definition field link
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the uuid of this de data definition field link.
	 *
	 * @param uuid the uuid of this de data definition field link
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	protected DEDataDefinitionFieldLinkWrapper wrap(
		DEDataDefinitionFieldLink deDataDefinitionFieldLink) {

		return new DEDataDefinitionFieldLinkWrapper(deDataDefinitionFieldLink);
	}

}