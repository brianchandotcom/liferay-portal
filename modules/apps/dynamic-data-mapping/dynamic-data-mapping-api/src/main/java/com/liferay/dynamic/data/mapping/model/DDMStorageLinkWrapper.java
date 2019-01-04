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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

/**
 * <p>
 * This class is a wrapper for {@link DDMStorageLink}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStorageLink
 * @generated
 */
@ProviderType
public class DDMStorageLinkWrapper extends BaseModelWrapper<DDMStorageLink>
	implements DDMStorageLink, ModelWrapper<DDMStorageLink> {
	public DDMStorageLinkWrapper(DDMStorageLink ddmStorageLink) {
		super(ddmStorageLink);
	}

	/**
	* Returns the fully qualified class name of this ddm storage link.
	*
	* @return the fully qualified class name of this ddm storage link
	*/
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	* Returns the class name ID of this ddm storage link.
	*
	* @return the class name ID of this ddm storage link
	*/
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	* Returns the class pk of this ddm storage link.
	*
	* @return the class pk of this ddm storage link
	*/
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	* Returns the company ID of this ddm storage link.
	*
	* @return the company ID of this ddm storage link
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the primary key of this ddm storage link.
	*
	* @return the primary key of this ddm storage link
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the storage link ID of this ddm storage link.
	*
	* @return the storage link ID of this ddm storage link
	*/
	@Override
	public long getStorageLinkId() {
		return model.getStorageLinkId();
	}

	@Override
	public String getStorageType()
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getStorageType();
	}

	@Override
	public DDMStructure getStructure()
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getStructure();
	}

	/**
	* Returns the structure ID of this ddm storage link.
	*
	* @return the structure ID of this ddm storage link
	*/
	@Override
	public long getStructureId() {
		return model.getStructureId();
	}

	/**
	* Returns the structure version ID of this ddm storage link.
	*
	* @return the structure version ID of this ddm storage link
	*/
	@Override
	public long getStructureVersionId() {
		return model.getStructureVersionId();
	}

	/**
	* Returns the uuid of this ddm storage link.
	*
	* @return the uuid of this ddm storage link
	*/
	@Override
	public String getUuid() {
		return model.getUuid();
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
	* Sets the class name ID of this ddm storage link.
	*
	* @param classNameId the class name ID of this ddm storage link
	*/
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this ddm storage link.
	*
	* @param classPK the class pk of this ddm storage link
	*/
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this ddm storage link.
	*
	* @param companyId the company ID of this ddm storage link
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the primary key of this ddm storage link.
	*
	* @param primaryKey the primary key of this ddm storage link
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the storage link ID of this ddm storage link.
	*
	* @param storageLinkId the storage link ID of this ddm storage link
	*/
	@Override
	public void setStorageLinkId(long storageLinkId) {
		model.setStorageLinkId(storageLinkId);
	}

	/**
	* Sets the structure ID of this ddm storage link.
	*
	* @param structureId the structure ID of this ddm storage link
	*/
	@Override
	public void setStructureId(long structureId) {
		model.setStructureId(structureId);
	}

	/**
	* Sets the structure version ID of this ddm storage link.
	*
	* @param structureVersionId the structure version ID of this ddm storage link
	*/
	@Override
	public void setStructureVersionId(long structureVersionId) {
		model.setStructureVersionId(structureVersionId);
	}

	/**
	* Sets the uuid of this ddm storage link.
	*
	* @param uuid the uuid of this ddm storage link
	*/
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	protected DDMStorageLinkWrapper wrap(DDMStorageLink ddmStorageLink) {
		return new DDMStorageLinkWrapper(ddmStorageLink);
	}
}