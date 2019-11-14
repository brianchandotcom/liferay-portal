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

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link LVEntryVersion}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LVEntryVersion
 * @generated
 */
public class LVEntryVersionWrapper
	extends BaseModelWrapper<LVEntryVersion>
	implements LVEntryVersion, ModelWrapper<LVEntryVersion> {

	public LVEntryVersionWrapper(LVEntryVersion lvEntryVersion) {
		super(lvEntryVersion);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("lvEntryVersionId", getLvEntryVersionId());
		attributes.put("version", getVersion());
		attributes.put("uuid", getUuid());
		attributes.put("defaultLanguageId", getDefaultLanguageId());
		attributes.put("lvEntryId", getLvEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("groupId", getGroupId());
		attributes.put("uniqueGroupKey", getUniqueGroupKey());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long lvEntryVersionId = (Long)attributes.get("lvEntryVersionId");

		if (lvEntryVersionId != null) {
			setLvEntryVersionId(lvEntryVersionId);
		}

		Integer version = (Integer)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		String defaultLanguageId = (String)attributes.get("defaultLanguageId");

		if (defaultLanguageId != null) {
			setDefaultLanguageId(defaultLanguageId);
		}

		Long lvEntryId = (Long)attributes.get("lvEntryId");

		if (lvEntryId != null) {
			setLvEntryId(lvEntryId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		String uniqueGroupKey = (String)attributes.get("uniqueGroupKey");

		if (uniqueGroupKey != null) {
			setUniqueGroupKey(uniqueGroupKey);
		}
	}

	/**
	 * Returns the company ID of this lv entry version.
	 *
	 * @return the company ID of this lv entry version
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the default language ID of this lv entry version.
	 *
	 * @return the default language ID of this lv entry version
	 */
	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the group ID of this lv entry version.
	 *
	 * @return the group ID of this lv entry version
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the lv entry ID of this lv entry version.
	 *
	 * @return the lv entry ID of this lv entry version
	 */
	@Override
	public long getLvEntryId() {
		return model.getLvEntryId();
	}

	/**
	 * Returns the lv entry version ID of this lv entry version.
	 *
	 * @return the lv entry version ID of this lv entry version
	 */
	@Override
	public long getLvEntryVersionId() {
		return model.getLvEntryVersionId();
	}

	/**
	 * Returns the primary key of this lv entry version.
	 *
	 * @return the primary key of this lv entry version
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the unique group key of this lv entry version.
	 *
	 * @return the unique group key of this lv entry version
	 */
	@Override
	public String getUniqueGroupKey() {
		return model.getUniqueGroupKey();
	}

	/**
	 * Returns the uuid of this lv entry version.
	 *
	 * @return the uuid of this lv entry version
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the version of this lv entry version.
	 *
	 * @return the version of this lv entry version
	 */
	@Override
	public int getVersion() {
		return model.getVersion();
	}

	/**
	 * Sets the company ID of this lv entry version.
	 *
	 * @param companyId the company ID of this lv entry version
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the default language ID of this lv entry version.
	 *
	 * @param defaultLanguageId the default language ID of this lv entry version
	 */
	@Override
	public void setDefaultLanguageId(String defaultLanguageId) {
		model.setDefaultLanguageId(defaultLanguageId);
	}

	/**
	 * Sets the group ID of this lv entry version.
	 *
	 * @param groupId the group ID of this lv entry version
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the lv entry ID of this lv entry version.
	 *
	 * @param lvEntryId the lv entry ID of this lv entry version
	 */
	@Override
	public void setLvEntryId(long lvEntryId) {
		model.setLvEntryId(lvEntryId);
	}

	/**
	 * Sets the lv entry version ID of this lv entry version.
	 *
	 * @param lvEntryVersionId the lv entry version ID of this lv entry version
	 */
	@Override
	public void setLvEntryVersionId(long lvEntryVersionId) {
		model.setLvEntryVersionId(lvEntryVersionId);
	}

	/**
	 * Sets the primary key of this lv entry version.
	 *
	 * @param primaryKey the primary key of this lv entry version
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the unique group key of this lv entry version.
	 *
	 * @param uniqueGroupKey the unique group key of this lv entry version
	 */
	@Override
	public void setUniqueGroupKey(String uniqueGroupKey) {
		model.setUniqueGroupKey(uniqueGroupKey);
	}

	/**
	 * Sets the uuid of this lv entry version.
	 *
	 * @param uuid the uuid of this lv entry version
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the version of this lv entry version.
	 *
	 * @param version the version of this lv entry version
	 */
	@Override
	public void setVersion(int version) {
		model.setVersion(version);
	}

	@Override
	public long getVersionedModelId() {
		return model.getVersionedModelId();
	}

	@Override
	public void setVersionedModelId(long id) {
		model.setVersionedModelId(id);
	}

	@Override
	public void populateVersionedModel(LVEntry lvEntry) {
		model.populateVersionedModel(lvEntry);
	}

	@Override
	public LVEntry toVersionedModel() {
		return model.toVersionedModel();
	}

	@Override
	protected LVEntryVersionWrapper wrap(LVEntryVersion lvEntryVersion) {
		return new LVEntryVersionWrapper(lvEntryVersion);
	}

}