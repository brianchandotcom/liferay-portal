/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
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
 * This class is a wrapper for {@link CPDefinitionLink}.
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionLink
 * @generated
 */
public class CPDefinitionLinkWrapper
	extends BaseModelWrapper<CPDefinitionLink>
	implements CPDefinitionLink, ModelWrapper<CPDefinitionLink> {

	public CPDefinitionLinkWrapper(CPDefinitionLink cpDefinitionLink) {
		super(cpDefinitionLink);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("CPDefinitionLinkId", getCPDefinitionLinkId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CPDefinitionId", getCPDefinitionId());
		attributes.put("CProductId", getCProductId());
		attributes.put("displayDate", getDisplayDate());
		attributes.put("expirationDate", getExpirationDate());
		attributes.put("priority", getPriority());
		attributes.put("type", getType());
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

		Long CPDefinitionLinkId = (Long)attributes.get("CPDefinitionLinkId");

		if (CPDefinitionLinkId != null) {
			setCPDefinitionLinkId(CPDefinitionLinkId);
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

		Long CPDefinitionId = (Long)attributes.get("CPDefinitionId");

		if (CPDefinitionId != null) {
			setCPDefinitionId(CPDefinitionId);
		}

		Long CProductId = (Long)attributes.get("CProductId");

		if (CProductId != null) {
			setCProductId(CProductId);
		}

		Date displayDate = (Date)attributes.get("displayDate");

		if (displayDate != null) {
			setDisplayDate(displayDate);
		}

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}

		Double priority = (Double)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
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
	public CPDefinitionLink cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this cp definition link.
	 *
	 * @return the company ID of this cp definition link
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	@Override
	public CPDefinition getCPDefinition() {
		return model.getCPDefinition();
	}

	/**
	 * Returns the cp definition ID of this cp definition link.
	 *
	 * @return the cp definition ID of this cp definition link
	 */
	@Override
	public long getCPDefinitionId() {
		return model.getCPDefinitionId();
	}

	/**
	 * Returns the cp definition link ID of this cp definition link.
	 *
	 * @return the cp definition link ID of this cp definition link
	 */
	@Override
	public long getCPDefinitionLinkId() {
		return model.getCPDefinitionLinkId();
	}

	@Override
	public CProduct getCProduct() {
		return model.getCProduct();
	}

	/**
	 * Returns the c product ID of this cp definition link.
	 *
	 * @return the c product ID of this cp definition link
	 */
	@Override
	public long getCProductId() {
		return model.getCProductId();
	}

	@Override
	public String getCProductName() {
		return model.getCProductName();
	}

	/**
	 * Returns the create date of this cp definition link.
	 *
	 * @return the create date of this cp definition link
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this cp definition link.
	 *
	 * @return the ct collection ID of this cp definition link
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the display date of this cp definition link.
	 *
	 * @return the display date of this cp definition link
	 */
	@Override
	public Date getDisplayDate() {
		return model.getDisplayDate();
	}

	/**
	 * Returns the expiration date of this cp definition link.
	 *
	 * @return the expiration date of this cp definition link
	 */
	@Override
	public Date getExpirationDate() {
		return model.getExpirationDate();
	}

	/**
	 * Returns the group ID of this cp definition link.
	 *
	 * @return the group ID of this cp definition link
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last publish date of this cp definition link.
	 *
	 * @return the last publish date of this cp definition link
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this cp definition link.
	 *
	 * @return the modified date of this cp definition link
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this cp definition link.
	 *
	 * @return the mvcc version of this cp definition link
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this cp definition link.
	 *
	 * @return the primary key of this cp definition link
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this cp definition link.
	 *
	 * @return the priority of this cp definition link
	 */
	@Override
	public double getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the status of this cp definition link.
	 *
	 * @return the status of this cp definition link
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this cp definition link.
	 *
	 * @return the status by user ID of this cp definition link
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this cp definition link.
	 *
	 * @return the status by user name of this cp definition link
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this cp definition link.
	 *
	 * @return the status by user uuid of this cp definition link
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this cp definition link.
	 *
	 * @return the status date of this cp definition link
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	/**
	 * Returns the type of this cp definition link.
	 *
	 * @return the type of this cp definition link
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this cp definition link.
	 *
	 * @return the user ID of this cp definition link
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this cp definition link.
	 *
	 * @return the user name of this cp definition link
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this cp definition link.
	 *
	 * @return the user uuid of this cp definition link
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this cp definition link.
	 *
	 * @return the uuid of this cp definition link
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this cp definition link is approved.
	 *
	 * @return <code>true</code> if this cp definition link is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	/**
	 * Returns <code>true</code> if this cp definition link is denied.
	 *
	 * @return <code>true</code> if this cp definition link is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	 * Returns <code>true</code> if this cp definition link is a draft.
	 *
	 * @return <code>true</code> if this cp definition link is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	 * Returns <code>true</code> if this cp definition link is expired.
	 *
	 * @return <code>true</code> if this cp definition link is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	 * Returns <code>true</code> if this cp definition link is inactive.
	 *
	 * @return <code>true</code> if this cp definition link is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this cp definition link is incomplete.
	 *
	 * @return <code>true</code> if this cp definition link is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	/**
	 * Returns <code>true</code> if this cp definition link is pending.
	 *
	 * @return <code>true</code> if this cp definition link is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	/**
	 * Returns <code>true</code> if this cp definition link is scheduled.
	 *
	 * @return <code>true</code> if this cp definition link is scheduled; <code>false</code> otherwise
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
	 * Sets the company ID of this cp definition link.
	 *
	 * @param companyId the company ID of this cp definition link
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cp definition ID of this cp definition link.
	 *
	 * @param CPDefinitionId the cp definition ID of this cp definition link
	 */
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		model.setCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Sets the cp definition link ID of this cp definition link.
	 *
	 * @param CPDefinitionLinkId the cp definition link ID of this cp definition link
	 */
	@Override
	public void setCPDefinitionLinkId(long CPDefinitionLinkId) {
		model.setCPDefinitionLinkId(CPDefinitionLinkId);
	}

	/**
	 * Sets the c product ID of this cp definition link.
	 *
	 * @param CProductId the c product ID of this cp definition link
	 */
	@Override
	public void setCProductId(long CProductId) {
		model.setCProductId(CProductId);
	}

	/**
	 * Sets the create date of this cp definition link.
	 *
	 * @param createDate the create date of this cp definition link
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this cp definition link.
	 *
	 * @param ctCollectionId the ct collection ID of this cp definition link
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the display date of this cp definition link.
	 *
	 * @param displayDate the display date of this cp definition link
	 */
	@Override
	public void setDisplayDate(Date displayDate) {
		model.setDisplayDate(displayDate);
	}

	/**
	 * Sets the expiration date of this cp definition link.
	 *
	 * @param expirationDate the expiration date of this cp definition link
	 */
	@Override
	public void setExpirationDate(Date expirationDate) {
		model.setExpirationDate(expirationDate);
	}

	/**
	 * Sets the group ID of this cp definition link.
	 *
	 * @param groupId the group ID of this cp definition link
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this cp definition link.
	 *
	 * @param lastPublishDate the last publish date of this cp definition link
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this cp definition link.
	 *
	 * @param modifiedDate the modified date of this cp definition link
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this cp definition link.
	 *
	 * @param mvccVersion the mvcc version of this cp definition link
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this cp definition link.
	 *
	 * @param primaryKey the primary key of this cp definition link
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this cp definition link.
	 *
	 * @param priority the priority of this cp definition link
	 */
	@Override
	public void setPriority(double priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets the status of this cp definition link.
	 *
	 * @param status the status of this cp definition link
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this cp definition link.
	 *
	 * @param statusByUserId the status by user ID of this cp definition link
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this cp definition link.
	 *
	 * @param statusByUserName the status by user name of this cp definition link
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this cp definition link.
	 *
	 * @param statusByUserUuid the status by user uuid of this cp definition link
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this cp definition link.
	 *
	 * @param statusDate the status date of this cp definition link
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * Sets the type of this cp definition link.
	 *
	 * @param type the type of this cp definition link
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this cp definition link.
	 *
	 * @param userId the user ID of this cp definition link
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this cp definition link.
	 *
	 * @param userName the user name of this cp definition link
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this cp definition link.
	 *
	 * @param userUuid the user uuid of this cp definition link
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this cp definition link.
	 *
	 * @param uuid the uuid of this cp definition link
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
	public Map<String, Function<CPDefinitionLink, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<CPDefinitionLink, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CPDefinitionLinkWrapper wrap(CPDefinitionLink cpDefinitionLink) {
		return new CPDefinitionLinkWrapper(cpDefinitionLink);
	}

}