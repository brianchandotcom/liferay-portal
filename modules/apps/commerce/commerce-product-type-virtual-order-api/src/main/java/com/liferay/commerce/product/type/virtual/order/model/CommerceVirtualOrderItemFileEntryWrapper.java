/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
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
 * This class is a wrapper for {@link CommerceVirtualOrderItemFileEntry}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVirtualOrderItemFileEntry
 * @generated
 */
public class CommerceVirtualOrderItemFileEntryWrapper
	extends BaseModelWrapper<CommerceVirtualOrderItemFileEntry>
	implements CommerceVirtualOrderItemFileEntry,
			   ModelWrapper<CommerceVirtualOrderItemFileEntry> {

	public CommerceVirtualOrderItemFileEntryWrapper(
		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry) {

		super(commerceVirtualOrderItemFileEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put(
			"commerceVirtualOrderItemFileEntryId",
			getCommerceVirtualOrderItemFileEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put(
			"commerceVirtualOrderItemId", getCommerceVirtualOrderItemId());
		attributes.put("fileEntryId", getFileEntryId());
		attributes.put("url", getUrl());
		attributes.put("usages", getUsages());
		attributes.put("version", getVersion());

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

		Long commerceVirtualOrderItemFileEntryId = (Long)attributes.get(
			"commerceVirtualOrderItemFileEntryId");

		if (commerceVirtualOrderItemFileEntryId != null) {
			setCommerceVirtualOrderItemFileEntryId(
				commerceVirtualOrderItemFileEntryId);
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

		Long commerceVirtualOrderItemId = (Long)attributes.get(
			"commerceVirtualOrderItemId");

		if (commerceVirtualOrderItemId != null) {
			setCommerceVirtualOrderItemId(commerceVirtualOrderItemId);
		}

		Long fileEntryId = (Long)attributes.get("fileEntryId");

		if (fileEntryId != null) {
			setFileEntryId(fileEntryId);
		}

		String url = (String)attributes.get("url");

		if (url != null) {
			setUrl(url);
		}

		Integer usages = (Integer)attributes.get("usages");

		if (usages != null) {
			setUsages(usages);
		}

		String version = (String)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}
	}

	@Override
	public CommerceVirtualOrderItemFileEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public CommerceVirtualOrderItem getCommerceVirtualOrderItem()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceVirtualOrderItem();
	}

	/**
	 * Returns the commerce virtual order item file entry ID of this commerce virtual order item file entry.
	 *
	 * @return the commerce virtual order item file entry ID of this commerce virtual order item file entry
	 */
	@Override
	public long getCommerceVirtualOrderItemFileEntryId() {
		return model.getCommerceVirtualOrderItemFileEntryId();
	}

	/**
	 * Returns the commerce virtual order item ID of this commerce virtual order item file entry.
	 *
	 * @return the commerce virtual order item ID of this commerce virtual order item file entry
	 */
	@Override
	public long getCommerceVirtualOrderItemId() {
		return model.getCommerceVirtualOrderItemId();
	}

	/**
	 * Returns the company ID of this commerce virtual order item file entry.
	 *
	 * @return the company ID of this commerce virtual order item file entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce virtual order item file entry.
	 *
	 * @return the create date of this commerce virtual order item file entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	@Override
	public com.liferay.portal.kernel.repository.model.FileEntry getFileEntry()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getFileEntry();
	}

	/**
	 * Returns the file entry ID of this commerce virtual order item file entry.
	 *
	 * @return the file entry ID of this commerce virtual order item file entry
	 */
	@Override
	public long getFileEntryId() {
		return model.getFileEntryId();
	}

	/**
	 * Returns the group ID of this commerce virtual order item file entry.
	 *
	 * @return the group ID of this commerce virtual order item file entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this commerce virtual order item file entry.
	 *
	 * @return the modified date of this commerce virtual order item file entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce virtual order item file entry.
	 *
	 * @return the mvcc version of this commerce virtual order item file entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce virtual order item file entry.
	 *
	 * @return the primary key of this commerce virtual order item file entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the url of this commerce virtual order item file entry.
	 *
	 * @return the url of this commerce virtual order item file entry
	 */
	@Override
	public String getUrl() {
		return model.getUrl();
	}

	/**
	 * Returns the usages of this commerce virtual order item file entry.
	 *
	 * @return the usages of this commerce virtual order item file entry
	 */
	@Override
	public int getUsages() {
		return model.getUsages();
	}

	/**
	 * Returns the user ID of this commerce virtual order item file entry.
	 *
	 * @return the user ID of this commerce virtual order item file entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce virtual order item file entry.
	 *
	 * @return the user name of this commerce virtual order item file entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce virtual order item file entry.
	 *
	 * @return the user uuid of this commerce virtual order item file entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce virtual order item file entry.
	 *
	 * @return the uuid of this commerce virtual order item file entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the version of this commerce virtual order item file entry.
	 *
	 * @return the version of this commerce virtual order item file entry
	 */
	@Override
	public String getVersion() {
		return model.getVersion();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the commerce virtual order item file entry ID of this commerce virtual order item file entry.
	 *
	 * @param commerceVirtualOrderItemFileEntryId the commerce virtual order item file entry ID of this commerce virtual order item file entry
	 */
	@Override
	public void setCommerceVirtualOrderItemFileEntryId(
		long commerceVirtualOrderItemFileEntryId) {

		model.setCommerceVirtualOrderItemFileEntryId(
			commerceVirtualOrderItemFileEntryId);
	}

	/**
	 * Sets the commerce virtual order item ID of this commerce virtual order item file entry.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID of this commerce virtual order item file entry
	 */
	@Override
	public void setCommerceVirtualOrderItemId(long commerceVirtualOrderItemId) {
		model.setCommerceVirtualOrderItemId(commerceVirtualOrderItemId);
	}

	/**
	 * Sets the company ID of this commerce virtual order item file entry.
	 *
	 * @param companyId the company ID of this commerce virtual order item file entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce virtual order item file entry.
	 *
	 * @param createDate the create date of this commerce virtual order item file entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the file entry ID of this commerce virtual order item file entry.
	 *
	 * @param fileEntryId the file entry ID of this commerce virtual order item file entry
	 */
	@Override
	public void setFileEntryId(long fileEntryId) {
		model.setFileEntryId(fileEntryId);
	}

	/**
	 * Sets the group ID of this commerce virtual order item file entry.
	 *
	 * @param groupId the group ID of this commerce virtual order item file entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this commerce virtual order item file entry.
	 *
	 * @param modifiedDate the modified date of this commerce virtual order item file entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce virtual order item file entry.
	 *
	 * @param mvccVersion the mvcc version of this commerce virtual order item file entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce virtual order item file entry.
	 *
	 * @param primaryKey the primary key of this commerce virtual order item file entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the url of this commerce virtual order item file entry.
	 *
	 * @param url the url of this commerce virtual order item file entry
	 */
	@Override
	public void setUrl(String url) {
		model.setUrl(url);
	}

	/**
	 * Sets the usages of this commerce virtual order item file entry.
	 *
	 * @param usages the usages of this commerce virtual order item file entry
	 */
	@Override
	public void setUsages(int usages) {
		model.setUsages(usages);
	}

	/**
	 * Sets the user ID of this commerce virtual order item file entry.
	 *
	 * @param userId the user ID of this commerce virtual order item file entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce virtual order item file entry.
	 *
	 * @param userName the user name of this commerce virtual order item file entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce virtual order item file entry.
	 *
	 * @param userUuid the user uuid of this commerce virtual order item file entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce virtual order item file entry.
	 *
	 * @param uuid the uuid of this commerce virtual order item file entry
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the version of this commerce virtual order item file entry.
	 *
	 * @param version the version of this commerce virtual order item file entry
	 */
	@Override
	public void setVersion(String version) {
		model.setVersion(version);
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
	protected CommerceVirtualOrderItemFileEntryWrapper wrap(
		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry) {

		return new CommerceVirtualOrderItemFileEntryWrapper(
			commerceVirtualOrderItemFileEntry);
	}

}