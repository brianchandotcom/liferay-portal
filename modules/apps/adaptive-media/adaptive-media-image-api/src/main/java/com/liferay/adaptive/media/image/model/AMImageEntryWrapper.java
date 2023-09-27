/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link AMImageEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AMImageEntry
 * @generated
 */
public class AMImageEntryWrapper
	extends BaseModelWrapper<AMImageEntry>
	implements AMImageEntry, ModelWrapper<AMImageEntry> {

	public AMImageEntryWrapper(AMImageEntry amImageEntry) {
		super(amImageEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("amImageEntryId", getAmImageEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("configurationUuid", getConfigurationUuid());
		attributes.put("fileVersionId", getFileVersionId());
		attributes.put("mimeType", getMimeType());
		attributes.put("height", getHeight());
		attributes.put("width", getWidth());
		attributes.put("size", getSize());

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

		Long amImageEntryId = (Long)attributes.get("amImageEntryId");

		if (amImageEntryId != null) {
			setAmImageEntryId(amImageEntryId);
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

		String configurationUuid = (String)attributes.get("configurationUuid");

		if (configurationUuid != null) {
			setConfigurationUuid(configurationUuid);
		}

		Long fileVersionId = (Long)attributes.get("fileVersionId");

		if (fileVersionId != null) {
			setFileVersionId(fileVersionId);
		}

		String mimeType = (String)attributes.get("mimeType");

		if (mimeType != null) {
			setMimeType(mimeType);
		}

		Integer height = (Integer)attributes.get("height");

		if (height != null) {
			setHeight(height);
		}

		Integer width = (Integer)attributes.get("width");

		if (width != null) {
			setWidth(width);
		}

		Long size = (Long)attributes.get("size");

		if (size != null) {
			setSize(size);
		}
	}

	@Override
	public AMImageEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the am image entry ID of this am image entry.
	 *
	 * @return the am image entry ID of this am image entry
	 */
	@Override
	public long getAmImageEntryId() {
		return model.getAmImageEntryId();
	}

	/**
	 * Returns the company ID of this am image entry.
	 *
	 * @return the company ID of this am image entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the configuration uuid of this am image entry.
	 *
	 * @return the configuration uuid of this am image entry
	 */
	@Override
	public String getConfigurationUuid() {
		return model.getConfigurationUuid();
	}

	/**
	 * Returns the create date of this am image entry.
	 *
	 * @return the create date of this am image entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this am image entry.
	 *
	 * @return the ct collection ID of this am image entry
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the file version ID of this am image entry.
	 *
	 * @return the file version ID of this am image entry
	 */
	@Override
	public long getFileVersionId() {
		return model.getFileVersionId();
	}

	/**
	 * Returns the group ID of this am image entry.
	 *
	 * @return the group ID of this am image entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the height of this am image entry.
	 *
	 * @return the height of this am image entry
	 */
	@Override
	public int getHeight() {
		return model.getHeight();
	}

	/**
	 * Returns the mime type of this am image entry.
	 *
	 * @return the mime type of this am image entry
	 */
	@Override
	public String getMimeType() {
		return model.getMimeType();
	}

	/**
	 * Returns the mvcc version of this am image entry.
	 *
	 * @return the mvcc version of this am image entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this am image entry.
	 *
	 * @return the primary key of this am image entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the size of this am image entry.
	 *
	 * @return the size of this am image entry
	 */
	@Override
	public long getSize() {
		return model.getSize();
	}

	/**
	 * Returns the uuid of this am image entry.
	 *
	 * @return the uuid of this am image entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the width of this am image entry.
	 *
	 * @return the width of this am image entry
	 */
	@Override
	public int getWidth() {
		return model.getWidth();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the am image entry ID of this am image entry.
	 *
	 * @param amImageEntryId the am image entry ID of this am image entry
	 */
	@Override
	public void setAmImageEntryId(long amImageEntryId) {
		model.setAmImageEntryId(amImageEntryId);
	}

	/**
	 * Sets the company ID of this am image entry.
	 *
	 * @param companyId the company ID of this am image entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the configuration uuid of this am image entry.
	 *
	 * @param configurationUuid the configuration uuid of this am image entry
	 */
	@Override
	public void setConfigurationUuid(String configurationUuid) {
		model.setConfigurationUuid(configurationUuid);
	}

	/**
	 * Sets the create date of this am image entry.
	 *
	 * @param createDate the create date of this am image entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this am image entry.
	 *
	 * @param ctCollectionId the ct collection ID of this am image entry
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the file version ID of this am image entry.
	 *
	 * @param fileVersionId the file version ID of this am image entry
	 */
	@Override
	public void setFileVersionId(long fileVersionId) {
		model.setFileVersionId(fileVersionId);
	}

	/**
	 * Sets the group ID of this am image entry.
	 *
	 * @param groupId the group ID of this am image entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the height of this am image entry.
	 *
	 * @param height the height of this am image entry
	 */
	@Override
	public void setHeight(int height) {
		model.setHeight(height);
	}

	/**
	 * Sets the mime type of this am image entry.
	 *
	 * @param mimeType the mime type of this am image entry
	 */
	@Override
	public void setMimeType(String mimeType) {
		model.setMimeType(mimeType);
	}

	/**
	 * Sets the mvcc version of this am image entry.
	 *
	 * @param mvccVersion the mvcc version of this am image entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this am image entry.
	 *
	 * @param primaryKey the primary key of this am image entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the size of this am image entry.
	 *
	 * @param size the size of this am image entry
	 */
	@Override
	public void setSize(long size) {
		model.setSize(size);
	}

	/**
	 * Sets the uuid of this am image entry.
	 *
	 * @param uuid the uuid of this am image entry
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the width of this am image entry.
	 *
	 * @param width the width of this am image entry
	 */
	@Override
	public void setWidth(int width) {
		model.setWidth(width);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<AMImageEntry, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<AMImageEntry, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected AMImageEntryWrapper wrap(AMImageEntry amImageEntry) {
		return new AMImageEntryWrapper(amImageEntry);
	}

}