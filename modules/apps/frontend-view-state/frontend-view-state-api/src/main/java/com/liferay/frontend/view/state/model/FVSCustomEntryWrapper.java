/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.view.state.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link FVSCustomEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FVSCustomEntry
 * @generated
 */
public class FVSCustomEntryWrapper
	extends BaseModelWrapper<FVSCustomEntry>
	implements FVSCustomEntry, ModelWrapper<FVSCustomEntry> {

	public FVSCustomEntryWrapper(FVSCustomEntry fvsCustomEntry) {
		super(fvsCustomEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("fvsCustomEntryId", getFvsCustomEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("fvsEntryId", getFvsEntryId());
		attributes.put("name", getName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long fvsCustomEntryId = (Long)attributes.get("fvsCustomEntryId");

		if (fvsCustomEntryId != null) {
			setFvsCustomEntryId(fvsCustomEntryId);
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

		Long fvsEntryId = (Long)attributes.get("fvsEntryId");

		if (fvsEntryId != null) {
			setFvsEntryId(fvsEntryId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}
	}

	@Override
	public FVSCustomEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this fvs custom entry.
	 *
	 * @return the company ID of this fvs custom entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the container model ID of this fvs custom entry.
	 *
	 * @return the container model ID of this fvs custom entry
	 */
	@Override
	public long getContainerModelId() {
		return model.getContainerModelId();
	}

	/**
	 * Returns the container name of this fvs custom entry.
	 *
	 * @return the container name of this fvs custom entry
	 */
	@Override
	public String getContainerModelName() {
		return model.getContainerModelName();
	}

	/**
	 * Returns the create date of this fvs custom entry.
	 *
	 * @return the create date of this fvs custom entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the fvs custom entry ID of this fvs custom entry.
	 *
	 * @return the fvs custom entry ID of this fvs custom entry
	 */
	@Override
	public long getFvsCustomEntryId() {
		return model.getFvsCustomEntryId();
	}

	/**
	 * Returns the fvs entry ID of this fvs custom entry.
	 *
	 * @return the fvs entry ID of this fvs custom entry
	 */
	@Override
	public long getFvsEntryId() {
		return model.getFvsEntryId();
	}

	/**
	 * Returns the modified date of this fvs custom entry.
	 *
	 * @return the modified date of this fvs custom entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this fvs custom entry.
	 *
	 * @return the mvcc version of this fvs custom entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this fvs custom entry.
	 *
	 * @return the name of this fvs custom entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the parent container model ID of this fvs custom entry.
	 *
	 * @return the parent container model ID of this fvs custom entry
	 */
	@Override
	public long getParentContainerModelId() {
		return model.getParentContainerModelId();
	}

	/**
	 * Returns the primary key of this fvs custom entry.
	 *
	 * @return the primary key of this fvs custom entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this fvs custom entry.
	 *
	 * @return the user ID of this fvs custom entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this fvs custom entry.
	 *
	 * @return the user name of this fvs custom entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this fvs custom entry.
	 *
	 * @return the user uuid of this fvs custom entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this fvs custom entry.
	 *
	 * @return the uuid of this fvs custom entry
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
	 * Sets the company ID of this fvs custom entry.
	 *
	 * @param companyId the company ID of this fvs custom entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the container model ID of this fvs custom entry.
	 *
	 * @param containerModelId the container model ID of this fvs custom entry
	 */
	@Override
	public void setContainerModelId(long containerModelId) {
		model.setContainerModelId(containerModelId);
	}

	/**
	 * Sets the create date of this fvs custom entry.
	 *
	 * @param createDate the create date of this fvs custom entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the fvs custom entry ID of this fvs custom entry.
	 *
	 * @param fvsCustomEntryId the fvs custom entry ID of this fvs custom entry
	 */
	@Override
	public void setFvsCustomEntryId(long fvsCustomEntryId) {
		model.setFvsCustomEntryId(fvsCustomEntryId);
	}

	/**
	 * Sets the fvs entry ID of this fvs custom entry.
	 *
	 * @param fvsEntryId the fvs entry ID of this fvs custom entry
	 */
	@Override
	public void setFvsEntryId(long fvsEntryId) {
		model.setFvsEntryId(fvsEntryId);
	}

	/**
	 * Sets the modified date of this fvs custom entry.
	 *
	 * @param modifiedDate the modified date of this fvs custom entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this fvs custom entry.
	 *
	 * @param mvccVersion the mvcc version of this fvs custom entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this fvs custom entry.
	 *
	 * @param name the name of this fvs custom entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the parent container model ID of this fvs custom entry.
	 *
	 * @param parentContainerModelId the parent container model ID of this fvs custom entry
	 */
	@Override
	public void setParentContainerModelId(long parentContainerModelId) {
		model.setParentContainerModelId(parentContainerModelId);
	}

	/**
	 * Sets the primary key of this fvs custom entry.
	 *
	 * @param primaryKey the primary key of this fvs custom entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this fvs custom entry.
	 *
	 * @param userId the user ID of this fvs custom entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this fvs custom entry.
	 *
	 * @param userName the user name of this fvs custom entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this fvs custom entry.
	 *
	 * @param userUuid the user uuid of this fvs custom entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this fvs custom entry.
	 *
	 * @param uuid the uuid of this fvs custom entry
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
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected FVSCustomEntryWrapper wrap(FVSCustomEntry fvsCustomEntry) {
		return new FVSCustomEntryWrapper(fvsCustomEntry);
	}

}