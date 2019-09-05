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

package com.liferay.dynamic.data.mapping.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DDMStructureLink}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLink
 * @generated
 */
public class DDMStructureLinkWrapper
	extends BaseModelWrapper<DDMStructureLink>
	implements DDMStructureLink, ModelWrapper<DDMStructureLink> {

	public DDMStructureLinkWrapper(DDMStructureLink ddmStructureLink) {
		super(ddmStructureLink);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("structureLinkId", getStructureLinkId());
		attributes.put("companyId", getCompanyId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("structureId", getStructureId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long structureLinkId = (Long)attributes.get("structureLinkId");

		if (structureLinkId != null) {
			setStructureLinkId(structureLinkId);
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

		Long structureId = (Long)attributes.get("structureId");

		if (structureId != null) {
			setStructureId(structureId);
		}
	}

	/**
	 * Returns the fully qualified class name of this ddm structure link.
	 *
	 * @return the fully qualified class name of this ddm structure link
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this ddm structure link.
	 *
	 * @return the class name ID of this ddm structure link
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this ddm structure link.
	 *
	 * @return the class pk of this ddm structure link
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this ddm structure link.
	 *
	 * @return the company ID of this ddm structure link
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the mvcc version of this ddm structure link.
	 *
	 * @return the mvcc version of this ddm structure link
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this ddm structure link.
	 *
	 * @return the primary key of this ddm structure link
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public DDMStructure getStructure()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getStructure();
	}

	/**
	 * Returns the structure ID of this ddm structure link.
	 *
	 * @return the structure ID of this ddm structure link
	 */
	@Override
	public long getStructureId() {
		return model.getStructureId();
	}

	/**
	 * Returns the structure link ID of this ddm structure link.
	 *
	 * @return the structure link ID of this ddm structure link
	 */
	@Override
	public long getStructureLinkId() {
		return model.getStructureLinkId();
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
	 * Sets the class name ID of this ddm structure link.
	 *
	 * @param classNameId the class name ID of this ddm structure link
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this ddm structure link.
	 *
	 * @param classPK the class pk of this ddm structure link
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this ddm structure link.
	 *
	 * @param companyId the company ID of this ddm structure link
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the mvcc version of this ddm structure link.
	 *
	 * @param mvccVersion the mvcc version of this ddm structure link
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this ddm structure link.
	 *
	 * @param primaryKey the primary key of this ddm structure link
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the structure ID of this ddm structure link.
	 *
	 * @param structureId the structure ID of this ddm structure link
	 */
	@Override
	public void setStructureId(long structureId) {
		model.setStructureId(structureId);
	}

	/**
	 * Sets the structure link ID of this ddm structure link.
	 *
	 * @param structureLinkId the structure link ID of this ddm structure link
	 */
	@Override
	public void setStructureLinkId(long structureLinkId) {
		model.setStructureLinkId(structureLinkId);
	}

	@Override
	protected DDMStructureLinkWrapper wrap(DDMStructureLink ddmStructureLink) {
		return new DDMStructureLinkWrapper(ddmStructureLink);
	}

}