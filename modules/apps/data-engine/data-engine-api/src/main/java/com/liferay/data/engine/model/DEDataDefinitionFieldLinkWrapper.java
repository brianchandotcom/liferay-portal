/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

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

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put(
			"deDataDefinitionFieldLinkId", getDeDataDefinitionFieldLinkId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("ddmStructureId", getDdmStructureId());
		attributes.put("fieldName", getFieldName());
		attributes.put("lastPublishDate", getLastPublishDate());

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

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
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

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public DEDataDefinitionFieldLink cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
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
	 * Returns the create date of this de data definition field link.
	 *
	 * @return the create date of this de data definition field link
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this de data definition field link.
	 *
	 * @return the ct collection ID of this de data definition field link
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
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
	 * Returns the last publish date of this de data definition field link.
	 *
	 * @return the last publish date of this de data definition field link
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this de data definition field link.
	 *
	 * @return the modified date of this de data definition field link
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this de data definition field link.
	 *
	 * @return the mvcc version of this de data definition field link
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
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
	 * Sets the create date of this de data definition field link.
	 *
	 * @param createDate the create date of this de data definition field link
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this de data definition field link.
	 *
	 * @param ctCollectionId the ct collection ID of this de data definition field link
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
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
	 * Sets the last publish date of this de data definition field link.
	 *
	 * @param lastPublishDate the last publish date of this de data definition field link
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this de data definition field link.
	 *
	 * @param modifiedDate the modified date of this de data definition field link
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this de data definition field link.
	 *
	 * @param mvccVersion the mvcc version of this de data definition field link
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
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
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<DEDataDefinitionFieldLink, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<DEDataDefinitionFieldLink, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected DEDataDefinitionFieldLinkWrapper wrap(
		DEDataDefinitionFieldLink deDataDefinitionFieldLink) {

		return new DEDataDefinitionFieldLinkWrapper(deDataDefinitionFieldLink);
	}

}