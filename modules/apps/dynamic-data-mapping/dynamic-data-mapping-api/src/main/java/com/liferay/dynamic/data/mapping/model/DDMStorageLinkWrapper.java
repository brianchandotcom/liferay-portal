/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link DDMStorageLink}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStorageLink
 * @generated
 */
public class DDMStorageLinkWrapper
	extends BaseModelWrapper<DDMStorageLink>
	implements DDMStorageLink, ModelWrapper<DDMStorageLink> {

	public DDMStorageLinkWrapper(DDMStorageLink ddmStorageLink) {
		super(ddmStorageLink);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("storageLinkId", getStorageLinkId());
		attributes.put("companyId", getCompanyId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("structureId", getStructureId());
		attributes.put("structureVersionId", getStructureVersionId());

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

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long storageLinkId = (Long)attributes.get("storageLinkId");

		if (storageLinkId != null) {
			setStorageLinkId(storageLinkId);
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

		Long structureVersionId = (Long)attributes.get("structureVersionId");

		if (structureVersionId != null) {
			setStructureVersionId(structureVersionId);
		}
	}

	@Override
	public DDMStorageLink cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
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
	 * Returns the ct collection ID of this ddm storage link.
	 *
	 * @return the ct collection ID of this ddm storage link
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the mvcc version of this ddm storage link.
	 *
	 * @return the mvcc version of this ddm storage link
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
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
	 * Sets the ct collection ID of this ddm storage link.
	 *
	 * @param ctCollectionId the ct collection ID of this ddm storage link
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the mvcc version of this ddm storage link.
	 *
	 * @param mvccVersion the mvcc version of this ddm storage link
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
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
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<DDMStorageLink, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<DDMStorageLink, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected DDMStorageLinkWrapper wrap(DDMStorageLink ddmStorageLink) {
		return new DDMStorageLinkWrapper(ddmStorageLink);
	}

}