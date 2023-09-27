/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.order.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceVirtualOrderItem}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVirtualOrderItem
 * @generated
 */
public class CommerceVirtualOrderItemWrapper
	extends BaseModelWrapper<CommerceVirtualOrderItem>
	implements CommerceVirtualOrderItem,
			   ModelWrapper<CommerceVirtualOrderItem> {

	public CommerceVirtualOrderItemWrapper(
		CommerceVirtualOrderItem commerceVirtualOrderItem) {

		super(commerceVirtualOrderItem);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put(
			"commerceVirtualOrderItemId", getCommerceVirtualOrderItemId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceOrderItemId", getCommerceOrderItemId());
		attributes.put("fileEntryId", getFileEntryId());
		attributes.put("url", getUrl());
		attributes.put("activationStatus", getActivationStatus());
		attributes.put("duration", getDuration());
		attributes.put("usages", getUsages());
		attributes.put("maxUsages", getMaxUsages());
		attributes.put("active", isActive());
		attributes.put("startDate", getStartDate());
		attributes.put("endDate", getEndDate());

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

		Long commerceVirtualOrderItemId = (Long)attributes.get(
			"commerceVirtualOrderItemId");

		if (commerceVirtualOrderItemId != null) {
			setCommerceVirtualOrderItemId(commerceVirtualOrderItemId);
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

		Long commerceOrderItemId = (Long)attributes.get("commerceOrderItemId");

		if (commerceOrderItemId != null) {
			setCommerceOrderItemId(commerceOrderItemId);
		}

		Long fileEntryId = (Long)attributes.get("fileEntryId");

		if (fileEntryId != null) {
			setFileEntryId(fileEntryId);
		}

		String url = (String)attributes.get("url");

		if (url != null) {
			setUrl(url);
		}

		Integer activationStatus = (Integer)attributes.get("activationStatus");

		if (activationStatus != null) {
			setActivationStatus(activationStatus);
		}

		Long duration = (Long)attributes.get("duration");

		if (duration != null) {
			setDuration(duration);
		}

		Integer usages = (Integer)attributes.get("usages");

		if (usages != null) {
			setUsages(usages);
		}

		Integer maxUsages = (Integer)attributes.get("maxUsages");

		if (maxUsages != null) {
			setMaxUsages(maxUsages);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		Date startDate = (Date)attributes.get("startDate");

		if (startDate != null) {
			setStartDate(startDate);
		}

		Date endDate = (Date)attributes.get("endDate");

		if (endDate != null) {
			setEndDate(endDate);
		}
	}

	@Override
	public CommerceVirtualOrderItem cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the activation status of this commerce virtual order item.
	 *
	 * @return the activation status of this commerce virtual order item
	 */
	@Override
	public int getActivationStatus() {
		return model.getActivationStatus();
	}

	/**
	 * Returns the active of this commerce virtual order item.
	 *
	 * @return the active of this commerce virtual order item
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderItem getCommerceOrderItem()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceOrderItem();
	}

	/**
	 * Returns the commerce order item ID of this commerce virtual order item.
	 *
	 * @return the commerce order item ID of this commerce virtual order item
	 */
	@Override
	public long getCommerceOrderItemId() {
		return model.getCommerceOrderItemId();
	}

	/**
	 * Returns the commerce virtual order item ID of this commerce virtual order item.
	 *
	 * @return the commerce virtual order item ID of this commerce virtual order item
	 */
	@Override
	public long getCommerceVirtualOrderItemId() {
		return model.getCommerceVirtualOrderItemId();
	}

	/**
	 * Returns the company ID of this commerce virtual order item.
	 *
	 * @return the company ID of this commerce virtual order item
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce virtual order item.
	 *
	 * @return the create date of this commerce virtual order item
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the duration of this commerce virtual order item.
	 *
	 * @return the duration of this commerce virtual order item
	 */
	@Override
	public long getDuration() {
		return model.getDuration();
	}

	/**
	 * Returns the end date of this commerce virtual order item.
	 *
	 * @return the end date of this commerce virtual order item
	 */
	@Override
	public Date getEndDate() {
		return model.getEndDate();
	}

	@Override
	public com.liferay.portal.kernel.repository.model.FileEntry getFileEntry()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getFileEntry();
	}

	/**
	 * Returns the file entry ID of this commerce virtual order item.
	 *
	 * @return the file entry ID of this commerce virtual order item
	 */
	@Override
	public long getFileEntryId() {
		return model.getFileEntryId();
	}

	/**
	 * Returns the group ID of this commerce virtual order item.
	 *
	 * @return the group ID of this commerce virtual order item
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the max usages of this commerce virtual order item.
	 *
	 * @return the max usages of this commerce virtual order item
	 */
	@Override
	public int getMaxUsages() {
		return model.getMaxUsages();
	}

	/**
	 * Returns the modified date of this commerce virtual order item.
	 *
	 * @return the modified date of this commerce virtual order item
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce virtual order item.
	 *
	 * @return the mvcc version of this commerce virtual order item
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce virtual order item.
	 *
	 * @return the primary key of this commerce virtual order item
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the start date of this commerce virtual order item.
	 *
	 * @return the start date of this commerce virtual order item
	 */
	@Override
	public Date getStartDate() {
		return model.getStartDate();
	}

	/**
	 * Returns the url of this commerce virtual order item.
	 *
	 * @return the url of this commerce virtual order item
	 */
	@Override
	public String getUrl() {
		return model.getUrl();
	}

	/**
	 * Returns the usages of this commerce virtual order item.
	 *
	 * @return the usages of this commerce virtual order item
	 */
	@Override
	public int getUsages() {
		return model.getUsages();
	}

	/**
	 * Returns the user ID of this commerce virtual order item.
	 *
	 * @return the user ID of this commerce virtual order item
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce virtual order item.
	 *
	 * @return the user name of this commerce virtual order item
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce virtual order item.
	 *
	 * @return the user uuid of this commerce virtual order item
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce virtual order item.
	 *
	 * @return the uuid of this commerce virtual order item
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this commerce virtual order item is active.
	 *
	 * @return <code>true</code> if this commerce virtual order item is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the activation status of this commerce virtual order item.
	 *
	 * @param activationStatus the activation status of this commerce virtual order item
	 */
	@Override
	public void setActivationStatus(int activationStatus) {
		model.setActivationStatus(activationStatus);
	}

	/**
	 * Sets whether this commerce virtual order item is active.
	 *
	 * @param active the active of this commerce virtual order item
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets the commerce order item ID of this commerce virtual order item.
	 *
	 * @param commerceOrderItemId the commerce order item ID of this commerce virtual order item
	 */
	@Override
	public void setCommerceOrderItemId(long commerceOrderItemId) {
		model.setCommerceOrderItemId(commerceOrderItemId);
	}

	/**
	 * Sets the commerce virtual order item ID of this commerce virtual order item.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID of this commerce virtual order item
	 */
	@Override
	public void setCommerceVirtualOrderItemId(long commerceVirtualOrderItemId) {
		model.setCommerceVirtualOrderItemId(commerceVirtualOrderItemId);
	}

	/**
	 * Sets the company ID of this commerce virtual order item.
	 *
	 * @param companyId the company ID of this commerce virtual order item
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce virtual order item.
	 *
	 * @param createDate the create date of this commerce virtual order item
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the duration of this commerce virtual order item.
	 *
	 * @param duration the duration of this commerce virtual order item
	 */
	@Override
	public void setDuration(long duration) {
		model.setDuration(duration);
	}

	/**
	 * Sets the end date of this commerce virtual order item.
	 *
	 * @param endDate the end date of this commerce virtual order item
	 */
	@Override
	public void setEndDate(Date endDate) {
		model.setEndDate(endDate);
	}

	/**
	 * Sets the file entry ID of this commerce virtual order item.
	 *
	 * @param fileEntryId the file entry ID of this commerce virtual order item
	 */
	@Override
	public void setFileEntryId(long fileEntryId) {
		model.setFileEntryId(fileEntryId);
	}

	/**
	 * Sets the group ID of this commerce virtual order item.
	 *
	 * @param groupId the group ID of this commerce virtual order item
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the max usages of this commerce virtual order item.
	 *
	 * @param maxUsages the max usages of this commerce virtual order item
	 */
	@Override
	public void setMaxUsages(int maxUsages) {
		model.setMaxUsages(maxUsages);
	}

	/**
	 * Sets the modified date of this commerce virtual order item.
	 *
	 * @param modifiedDate the modified date of this commerce virtual order item
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce virtual order item.
	 *
	 * @param mvccVersion the mvcc version of this commerce virtual order item
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce virtual order item.
	 *
	 * @param primaryKey the primary key of this commerce virtual order item
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the start date of this commerce virtual order item.
	 *
	 * @param startDate the start date of this commerce virtual order item
	 */
	@Override
	public void setStartDate(Date startDate) {
		model.setStartDate(startDate);
	}

	/**
	 * Sets the url of this commerce virtual order item.
	 *
	 * @param url the url of this commerce virtual order item
	 */
	@Override
	public void setUrl(String url) {
		model.setUrl(url);
	}

	/**
	 * Sets the usages of this commerce virtual order item.
	 *
	 * @param usages the usages of this commerce virtual order item
	 */
	@Override
	public void setUsages(int usages) {
		model.setUsages(usages);
	}

	/**
	 * Sets the user ID of this commerce virtual order item.
	 *
	 * @param userId the user ID of this commerce virtual order item
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce virtual order item.
	 *
	 * @param userName the user name of this commerce virtual order item
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce virtual order item.
	 *
	 * @param userUuid the user uuid of this commerce virtual order item
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce virtual order item.
	 *
	 * @param uuid the uuid of this commerce virtual order item
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
	protected CommerceVirtualOrderItemWrapper wrap(
		CommerceVirtualOrderItem commerceVirtualOrderItem) {

		return new CommerceVirtualOrderItemWrapper(commerceVirtualOrderItem);
	}

}