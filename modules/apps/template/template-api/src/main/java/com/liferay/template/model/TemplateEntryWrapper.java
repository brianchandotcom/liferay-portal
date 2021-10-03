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

package com.liferay.template.model;

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
 * This class is a wrapper for {@link TemplateEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TemplateEntry
 * @generated
 */
public class TemplateEntryWrapper
	extends BaseModelWrapper<TemplateEntry>
	implements ModelWrapper<TemplateEntry>, TemplateEntry {

	public TemplateEntryWrapper(TemplateEntry templateEntry) {
		super(templateEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("templateEntryId", getTemplateEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("ddmTemplateId", getDDMTemplateId());
		attributes.put("infoItemClassName", getInfoItemClassName());
		attributes.put(
			"infoItemFormVariationKey", getInfoItemFormVariationKey());
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

		Long templateEntryId = (Long)attributes.get("templateEntryId");

		if (templateEntryId != null) {
			setTemplateEntryId(templateEntryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long ddmTemplateId = (Long)attributes.get("ddmTemplateId");

		if (ddmTemplateId != null) {
			setDDMTemplateId(ddmTemplateId);
		}

		String infoItemClassName = (String)attributes.get("infoItemClassName");

		if (infoItemClassName != null) {
			setInfoItemClassName(infoItemClassName);
		}

		String infoItemFormVariationKey = (String)attributes.get(
			"infoItemFormVariationKey");

		if (infoItemFormVariationKey != null) {
			setInfoItemFormVariationKey(infoItemFormVariationKey);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public TemplateEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this template entry.
	 *
	 * @return the company ID of this template entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this template entry.
	 *
	 * @return the create date of this template entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this template entry.
	 *
	 * @return the ct collection ID of this template entry
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the ddm template ID of this template entry.
	 *
	 * @return the ddm template ID of this template entry
	 */
	@Override
	public long getDDMTemplateId() {
		return model.getDDMTemplateId();
	}

	/**
	 * Returns the group ID of this template entry.
	 *
	 * @return the group ID of this template entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the info item class name of this template entry.
	 *
	 * @return the info item class name of this template entry
	 */
	@Override
	public String getInfoItemClassName() {
		return model.getInfoItemClassName();
	}

	/**
	 * Returns the info item form variation key of this template entry.
	 *
	 * @return the info item form variation key of this template entry
	 */
	@Override
	public String getInfoItemFormVariationKey() {
		return model.getInfoItemFormVariationKey();
	}

	/**
	 * Returns the last publish date of this template entry.
	 *
	 * @return the last publish date of this template entry
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this template entry.
	 *
	 * @return the modified date of this template entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this template entry.
	 *
	 * @return the mvcc version of this template entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this template entry.
	 *
	 * @return the primary key of this template entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the template entry ID of this template entry.
	 *
	 * @return the template entry ID of this template entry
	 */
	@Override
	public long getTemplateEntryId() {
		return model.getTemplateEntryId();
	}

	/**
	 * Returns the user ID of this template entry.
	 *
	 * @return the user ID of this template entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this template entry.
	 *
	 * @return the user name of this template entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this template entry.
	 *
	 * @return the user uuid of this template entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this template entry.
	 *
	 * @return the uuid of this template entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this template entry.
	 *
	 * @param companyId the company ID of this template entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this template entry.
	 *
	 * @param createDate the create date of this template entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this template entry.
	 *
	 * @param ctCollectionId the ct collection ID of this template entry
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the ddm template ID of this template entry.
	 *
	 * @param ddmTemplateId the ddm template ID of this template entry
	 */
	@Override
	public void setDDMTemplateId(long ddmTemplateId) {
		model.setDDMTemplateId(ddmTemplateId);
	}

	/**
	 * Sets the group ID of this template entry.
	 *
	 * @param groupId the group ID of this template entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the info item class name of this template entry.
	 *
	 * @param infoItemClassName the info item class name of this template entry
	 */
	@Override
	public void setInfoItemClassName(String infoItemClassName) {
		model.setInfoItemClassName(infoItemClassName);
	}

	/**
	 * Sets the info item form variation key of this template entry.
	 *
	 * @param infoItemFormVariationKey the info item form variation key of this template entry
	 */
	@Override
	public void setInfoItemFormVariationKey(String infoItemFormVariationKey) {
		model.setInfoItemFormVariationKey(infoItemFormVariationKey);
	}

	/**
	 * Sets the last publish date of this template entry.
	 *
	 * @param lastPublishDate the last publish date of this template entry
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this template entry.
	 *
	 * @param modifiedDate the modified date of this template entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this template entry.
	 *
	 * @param mvccVersion the mvcc version of this template entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this template entry.
	 *
	 * @param primaryKey the primary key of this template entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the template entry ID of this template entry.
	 *
	 * @param templateEntryId the template entry ID of this template entry
	 */
	@Override
	public void setTemplateEntryId(long templateEntryId) {
		model.setTemplateEntryId(templateEntryId);
	}

	/**
	 * Sets the user ID of this template entry.
	 *
	 * @param userId the user ID of this template entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this template entry.
	 *
	 * @param userName the user name of this template entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this template entry.
	 *
	 * @param userUuid the user uuid of this template entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this template entry.
	 *
	 * @param uuid the uuid of this template entry
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public Map<String, Function<TemplateEntry, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<TemplateEntry, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected TemplateEntryWrapper wrap(TemplateEntry templateEntry) {
		return new TemplateEntryWrapper(templateEntry);
	}

}