/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model;

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
 * This class is a wrapper for {@link CPConfigurationList}.
 * </p>
 *
 * @author Marco Leo
 * @see CPConfigurationList
 * @generated
 */
public class CPConfigurationListWrapper
	extends BaseModelWrapper<CPConfigurationList>
	implements CPConfigurationList, ModelWrapper<CPConfigurationList> {

	public CPConfigurationListWrapper(CPConfigurationList cpConfigurationList) {
		super(cpConfigurationList);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("CPConfigurationListId", getCPConfigurationListId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put(
			"parentCPConfigurationListId", getParentCPConfigurationListId());
		attributes.put(
			"masterCPConfigurationList", isMasterCPConfigurationList());
		attributes.put("name", getName());
		attributes.put("priority", getPriority());
		attributes.put("displayDate", getDisplayDate());
		attributes.put("expirationDate", getExpirationDate());
		attributes.put("lastPublishDate", getLastPublishDate());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

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

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long CPConfigurationListId = (Long)attributes.get(
			"CPConfigurationListId");

		if (CPConfigurationListId != null) {
			setCPConfigurationListId(CPConfigurationListId);
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

		Long parentCPConfigurationListId = (Long)attributes.get(
			"parentCPConfigurationListId");

		if (parentCPConfigurationListId != null) {
			setParentCPConfigurationListId(parentCPConfigurationListId);
		}

		Boolean masterCPConfigurationList = (Boolean)attributes.get(
			"masterCPConfigurationList");

		if (masterCPConfigurationList != null) {
			setMasterCPConfigurationList(masterCPConfigurationList);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Double priority = (Double)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Date displayDate = (Date)attributes.get("displayDate");

		if (displayDate != null) {
			setDisplayDate(displayDate);
		}

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Long statusByUserId = (Long)attributes.get("statusByUserId");

		if (statusByUserId != null) {
			setStatusByUserId(statusByUserId);
		}

		String statusByUserName = (String)attributes.get("statusByUserName");

		if (statusByUserName != null) {
			setStatusByUserName(statusByUserName);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}
	}

	@Override
	public CPConfigurationList cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this cp configuration list.
	 *
	 * @return the company ID of this cp configuration list
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the cp configuration list ID of this cp configuration list.
	 *
	 * @return the cp configuration list ID of this cp configuration list
	 */
	@Override
	public long getCPConfigurationListId() {
		return model.getCPConfigurationListId();
	}

	/**
	 * Returns the create date of this cp configuration list.
	 *
	 * @return the create date of this cp configuration list
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this cp configuration list.
	 *
	 * @return the ct collection ID of this cp configuration list
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the display date of this cp configuration list.
	 *
	 * @return the display date of this cp configuration list
	 */
	@Override
	public Date getDisplayDate() {
		return model.getDisplayDate();
	}

	/**
	 * Returns the expiration date of this cp configuration list.
	 *
	 * @return the expiration date of this cp configuration list
	 */
	@Override
	public Date getExpirationDate() {
		return model.getExpirationDate();
	}

	/**
	 * Returns the external reference code of this cp configuration list.
	 *
	 * @return the external reference code of this cp configuration list
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the group ID of this cp configuration list.
	 *
	 * @return the group ID of this cp configuration list
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last publish date of this cp configuration list.
	 *
	 * @return the last publish date of this cp configuration list
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the master cp configuration list of this cp configuration list.
	 *
	 * @return the master cp configuration list of this cp configuration list
	 */
	@Override
	public boolean getMasterCPConfigurationList() {
		return model.getMasterCPConfigurationList();
	}

	/**
	 * Returns the modified date of this cp configuration list.
	 *
	 * @return the modified date of this cp configuration list
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this cp configuration list.
	 *
	 * @return the mvcc version of this cp configuration list
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this cp configuration list.
	 *
	 * @return the name of this cp configuration list
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the parent cp configuration list ID of this cp configuration list.
	 *
	 * @return the parent cp configuration list ID of this cp configuration list
	 */
	@Override
	public long getParentCPConfigurationListId() {
		return model.getParentCPConfigurationListId();
	}

	/**
	 * Returns the primary key of this cp configuration list.
	 *
	 * @return the primary key of this cp configuration list
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this cp configuration list.
	 *
	 * @return the priority of this cp configuration list
	 */
	@Override
	public double getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the status of this cp configuration list.
	 *
	 * @return the status of this cp configuration list
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this cp configuration list.
	 *
	 * @return the status by user ID of this cp configuration list
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this cp configuration list.
	 *
	 * @return the status by user name of this cp configuration list
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this cp configuration list.
	 *
	 * @return the status by user uuid of this cp configuration list
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this cp configuration list.
	 *
	 * @return the status date of this cp configuration list
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	/**
	 * Returns the user ID of this cp configuration list.
	 *
	 * @return the user ID of this cp configuration list
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this cp configuration list.
	 *
	 * @return the user name of this cp configuration list
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this cp configuration list.
	 *
	 * @return the user uuid of this cp configuration list
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this cp configuration list.
	 *
	 * @return the uuid of this cp configuration list
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this cp configuration list is approved.
	 *
	 * @return <code>true</code> if this cp configuration list is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	/**
	 * Returns <code>true</code> if this cp configuration list is denied.
	 *
	 * @return <code>true</code> if this cp configuration list is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	 * Returns <code>true</code> if this cp configuration list is a draft.
	 *
	 * @return <code>true</code> if this cp configuration list is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	 * Returns <code>true</code> if this cp configuration list is expired.
	 *
	 * @return <code>true</code> if this cp configuration list is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	 * Returns <code>true</code> if this cp configuration list is inactive.
	 *
	 * @return <code>true</code> if this cp configuration list is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this cp configuration list is incomplete.
	 *
	 * @return <code>true</code> if this cp configuration list is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	/**
	 * Returns <code>true</code> if this cp configuration list is master cp configuration list.
	 *
	 * @return <code>true</code> if this cp configuration list is master cp configuration list; <code>false</code> otherwise
	 */
	@Override
	public boolean isMasterCPConfigurationList() {
		return model.isMasterCPConfigurationList();
	}

	/**
	 * Returns <code>true</code> if this cp configuration list is pending.
	 *
	 * @return <code>true</code> if this cp configuration list is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	/**
	 * Returns <code>true</code> if this cp configuration list is scheduled.
	 *
	 * @return <code>true</code> if this cp configuration list is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled() {
		return model.isScheduled();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this cp configuration list.
	 *
	 * @param companyId the company ID of this cp configuration list
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cp configuration list ID of this cp configuration list.
	 *
	 * @param CPConfigurationListId the cp configuration list ID of this cp configuration list
	 */
	@Override
	public void setCPConfigurationListId(long CPConfigurationListId) {
		model.setCPConfigurationListId(CPConfigurationListId);
	}

	/**
	 * Sets the create date of this cp configuration list.
	 *
	 * @param createDate the create date of this cp configuration list
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this cp configuration list.
	 *
	 * @param ctCollectionId the ct collection ID of this cp configuration list
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the display date of this cp configuration list.
	 *
	 * @param displayDate the display date of this cp configuration list
	 */
	@Override
	public void setDisplayDate(Date displayDate) {
		model.setDisplayDate(displayDate);
	}

	/**
	 * Sets the expiration date of this cp configuration list.
	 *
	 * @param expirationDate the expiration date of this cp configuration list
	 */
	@Override
	public void setExpirationDate(Date expirationDate) {
		model.setExpirationDate(expirationDate);
	}

	/**
	 * Sets the external reference code of this cp configuration list.
	 *
	 * @param externalReferenceCode the external reference code of this cp configuration list
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the group ID of this cp configuration list.
	 *
	 * @param groupId the group ID of this cp configuration list
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this cp configuration list.
	 *
	 * @param lastPublishDate the last publish date of this cp configuration list
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets whether this cp configuration list is master cp configuration list.
	 *
	 * @param masterCPConfigurationList the master cp configuration list of this cp configuration list
	 */
	@Override
	public void setMasterCPConfigurationList(
		boolean masterCPConfigurationList) {

		model.setMasterCPConfigurationList(masterCPConfigurationList);
	}

	/**
	 * Sets the modified date of this cp configuration list.
	 *
	 * @param modifiedDate the modified date of this cp configuration list
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this cp configuration list.
	 *
	 * @param mvccVersion the mvcc version of this cp configuration list
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this cp configuration list.
	 *
	 * @param name the name of this cp configuration list
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the parent cp configuration list ID of this cp configuration list.
	 *
	 * @param parentCPConfigurationListId the parent cp configuration list ID of this cp configuration list
	 */
	@Override
	public void setParentCPConfigurationListId(
		long parentCPConfigurationListId) {

		model.setParentCPConfigurationListId(parentCPConfigurationListId);
	}

	/**
	 * Sets the primary key of this cp configuration list.
	 *
	 * @param primaryKey the primary key of this cp configuration list
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this cp configuration list.
	 *
	 * @param priority the priority of this cp configuration list
	 */
	@Override
	public void setPriority(double priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets the status of this cp configuration list.
	 *
	 * @param status the status of this cp configuration list
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this cp configuration list.
	 *
	 * @param statusByUserId the status by user ID of this cp configuration list
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this cp configuration list.
	 *
	 * @param statusByUserName the status by user name of this cp configuration list
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this cp configuration list.
	 *
	 * @param statusByUserUuid the status by user uuid of this cp configuration list
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this cp configuration list.
	 *
	 * @param statusDate the status date of this cp configuration list
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * Sets the user ID of this cp configuration list.
	 *
	 * @param userId the user ID of this cp configuration list
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this cp configuration list.
	 *
	 * @param userName the user name of this cp configuration list
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this cp configuration list.
	 *
	 * @param userUuid the user uuid of this cp configuration list
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this cp configuration list.
	 *
	 * @param uuid the uuid of this cp configuration list
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
	public Map<String, Function<CPConfigurationList, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<CPConfigurationList, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CPConfigurationListWrapper wrap(
		CPConfigurationList cpConfigurationList) {

		return new CPConfigurationListWrapper(cpConfigurationList);
	}

}