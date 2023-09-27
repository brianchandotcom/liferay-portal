/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.reading.time.model;

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
 * This class is a wrapper for {@link ReadingTimeEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ReadingTimeEntry
 * @generated
 */
public class ReadingTimeEntryWrapper
	extends BaseModelWrapper<ReadingTimeEntry>
	implements ModelWrapper<ReadingTimeEntry>, ReadingTimeEntry {

	public ReadingTimeEntryWrapper(ReadingTimeEntry readingTimeEntry) {
		super(readingTimeEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("readingTimeEntryId", getReadingTimeEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("readingTime", getReadingTime());

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

		Long readingTimeEntryId = (Long)attributes.get("readingTimeEntryId");

		if (readingTimeEntryId != null) {
			setReadingTimeEntryId(readingTimeEntryId);
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

		Long readingTime = (Long)attributes.get("readingTime");

		if (readingTime != null) {
			setReadingTime(readingTime);
		}
	}

	@Override
	public ReadingTimeEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the fully qualified class name of this reading time entry.
	 *
	 * @return the fully qualified class name of this reading time entry
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this reading time entry.
	 *
	 * @return the class name ID of this reading time entry
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this reading time entry.
	 *
	 * @return the class pk of this reading time entry
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this reading time entry.
	 *
	 * @return the company ID of this reading time entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this reading time entry.
	 *
	 * @return the create date of this reading time entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this reading time entry.
	 *
	 * @return the ct collection ID of this reading time entry
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the group ID of this reading time entry.
	 *
	 * @return the group ID of this reading time entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this reading time entry.
	 *
	 * @return the modified date of this reading time entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this reading time entry.
	 *
	 * @return the mvcc version of this reading time entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this reading time entry.
	 *
	 * @return the primary key of this reading time entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the reading time of this reading time entry.
	 *
	 * @return the reading time of this reading time entry
	 */
	@Override
	public long getReadingTime() {
		return model.getReadingTime();
	}

	/**
	 * Returns the reading time entry ID of this reading time entry.
	 *
	 * @return the reading time entry ID of this reading time entry
	 */
	@Override
	public long getReadingTimeEntryId() {
		return model.getReadingTimeEntryId();
	}

	/**
	 * Returns the status of this reading time entry.
	 *
	 * @return the status of this reading time entry
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the class primary key of the trash entry for this reading time entry.
	 *
	 * @return the class primary key of the trash entry for this reading time entry
	 */
	@Override
	public long getTrashEntryClassPK() {
		return model.getTrashEntryClassPK();
	}

	/**
	 * Returns the uuid of this reading time entry.
	 *
	 * @return the uuid of this reading time entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this reading time entry is in the Recycle Bin.
	 *
	 * @return <code>true</code> if this reading time entry is in the Recycle Bin; <code>false</code> otherwise
	 */
	@Override
	public boolean isInTrash() {
		return model.isInTrash();
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
	 * Sets the class name ID of this reading time entry.
	 *
	 * @param classNameId the class name ID of this reading time entry
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this reading time entry.
	 *
	 * @param classPK the class pk of this reading time entry
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this reading time entry.
	 *
	 * @param companyId the company ID of this reading time entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this reading time entry.
	 *
	 * @param createDate the create date of this reading time entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this reading time entry.
	 *
	 * @param ctCollectionId the ct collection ID of this reading time entry
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the group ID of this reading time entry.
	 *
	 * @param groupId the group ID of this reading time entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this reading time entry.
	 *
	 * @param modifiedDate the modified date of this reading time entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this reading time entry.
	 *
	 * @param mvccVersion the mvcc version of this reading time entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this reading time entry.
	 *
	 * @param primaryKey the primary key of this reading time entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the reading time of this reading time entry.
	 *
	 * @param readingTime the reading time of this reading time entry
	 */
	@Override
	public void setReadingTime(long readingTime) {
		model.setReadingTime(readingTime);
	}

	/**
	 * Sets the reading time entry ID of this reading time entry.
	 *
	 * @param readingTimeEntryId the reading time entry ID of this reading time entry
	 */
	@Override
	public void setReadingTimeEntryId(long readingTimeEntryId) {
		model.setReadingTimeEntryId(readingTimeEntryId);
	}

	/**
	 * Sets the uuid of this reading time entry.
	 *
	 * @param uuid the uuid of this reading time entry
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
	public Map<String, Function<ReadingTimeEntry, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<ReadingTimeEntry, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected ReadingTimeEntryWrapper wrap(ReadingTimeEntry readingTimeEntry) {
		return new ReadingTimeEntryWrapper(readingTimeEntry);
	}

}