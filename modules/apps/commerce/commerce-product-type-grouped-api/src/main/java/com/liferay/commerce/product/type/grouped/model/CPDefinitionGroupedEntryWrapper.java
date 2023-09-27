/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.grouped.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CPDefinitionGroupedEntry}.
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CPDefinitionGroupedEntry
 * @generated
 */
public class CPDefinitionGroupedEntryWrapper
	extends BaseModelWrapper<CPDefinitionGroupedEntry>
	implements CPDefinitionGroupedEntry,
			   ModelWrapper<CPDefinitionGroupedEntry> {

	public CPDefinitionGroupedEntryWrapper(
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry) {

		super(cpDefinitionGroupedEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put(
			"CPDefinitionGroupedEntryId", getCPDefinitionGroupedEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CPDefinitionId", getCPDefinitionId());
		attributes.put("entryCProductId", getEntryCProductId());
		attributes.put("priority", getPriority());
		attributes.put("quantity", getQuantity());

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

		Long CPDefinitionGroupedEntryId = (Long)attributes.get(
			"CPDefinitionGroupedEntryId");

		if (CPDefinitionGroupedEntryId != null) {
			setCPDefinitionGroupedEntryId(CPDefinitionGroupedEntryId);
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

		Long entryCProductId = (Long)attributes.get("entryCProductId");

		if (entryCProductId != null) {
			setEntryCProductId(entryCProductId);
		}

		Double priority = (Double)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Integer quantity = (Integer)attributes.get("quantity");

		if (quantity != null) {
			setQuantity(quantity);
		}
	}

	@Override
	public CPDefinitionGroupedEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this cp definition grouped entry.
	 *
	 * @return the company ID of this cp definition grouped entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition getCPDefinition()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCPDefinition();
	}

	/**
	 * Returns the cp definition grouped entry ID of this cp definition grouped entry.
	 *
	 * @return the cp definition grouped entry ID of this cp definition grouped entry
	 */
	@Override
	public long getCPDefinitionGroupedEntryId() {
		return model.getCPDefinitionGroupedEntryId();
	}

	/**
	 * Returns the cp definition ID of this cp definition grouped entry.
	 *
	 * @return the cp definition ID of this cp definition grouped entry
	 */
	@Override
	public long getCPDefinitionId() {
		return model.getCPDefinitionId();
	}

	/**
	 * Returns the create date of this cp definition grouped entry.
	 *
	 * @return the create date of this cp definition grouped entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition
			getEntryCPDefinition()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getEntryCPDefinition();
	}

	@Override
	public long getEntryCPDefinitionId()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getEntryCPDefinitionId();
	}

	@Override
	public com.liferay.commerce.product.model.CProduct getEntryCProduct()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getEntryCProduct();
	}

	/**
	 * Returns the entry c product ID of this cp definition grouped entry.
	 *
	 * @return the entry c product ID of this cp definition grouped entry
	 */
	@Override
	public long getEntryCProductId() {
		return model.getEntryCProductId();
	}

	/**
	 * Returns the group ID of this cp definition grouped entry.
	 *
	 * @return the group ID of this cp definition grouped entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this cp definition grouped entry.
	 *
	 * @return the modified date of this cp definition grouped entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this cp definition grouped entry.
	 *
	 * @return the mvcc version of this cp definition grouped entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this cp definition grouped entry.
	 *
	 * @return the primary key of this cp definition grouped entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this cp definition grouped entry.
	 *
	 * @return the priority of this cp definition grouped entry
	 */
	@Override
	public double getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the quantity of this cp definition grouped entry.
	 *
	 * @return the quantity of this cp definition grouped entry
	 */
	@Override
	public int getQuantity() {
		return model.getQuantity();
	}

	/**
	 * Returns the user ID of this cp definition grouped entry.
	 *
	 * @return the user ID of this cp definition grouped entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this cp definition grouped entry.
	 *
	 * @return the user name of this cp definition grouped entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this cp definition grouped entry.
	 *
	 * @return the user uuid of this cp definition grouped entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this cp definition grouped entry.
	 *
	 * @return the uuid of this cp definition grouped entry
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
	 * Sets the company ID of this cp definition grouped entry.
	 *
	 * @param companyId the company ID of this cp definition grouped entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cp definition grouped entry ID of this cp definition grouped entry.
	 *
	 * @param CPDefinitionGroupedEntryId the cp definition grouped entry ID of this cp definition grouped entry
	 */
	@Override
	public void setCPDefinitionGroupedEntryId(long CPDefinitionGroupedEntryId) {
		model.setCPDefinitionGroupedEntryId(CPDefinitionGroupedEntryId);
	}

	/**
	 * Sets the cp definition ID of this cp definition grouped entry.
	 *
	 * @param CPDefinitionId the cp definition ID of this cp definition grouped entry
	 */
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		model.setCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Sets the create date of this cp definition grouped entry.
	 *
	 * @param createDate the create date of this cp definition grouped entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the entry c product ID of this cp definition grouped entry.
	 *
	 * @param entryCProductId the entry c product ID of this cp definition grouped entry
	 */
	@Override
	public void setEntryCProductId(long entryCProductId) {
		model.setEntryCProductId(entryCProductId);
	}

	/**
	 * Sets the group ID of this cp definition grouped entry.
	 *
	 * @param groupId the group ID of this cp definition grouped entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this cp definition grouped entry.
	 *
	 * @param modifiedDate the modified date of this cp definition grouped entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this cp definition grouped entry.
	 *
	 * @param mvccVersion the mvcc version of this cp definition grouped entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this cp definition grouped entry.
	 *
	 * @param primaryKey the primary key of this cp definition grouped entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this cp definition grouped entry.
	 *
	 * @param priority the priority of this cp definition grouped entry
	 */
	@Override
	public void setPriority(double priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets the quantity of this cp definition grouped entry.
	 *
	 * @param quantity the quantity of this cp definition grouped entry
	 */
	@Override
	public void setQuantity(int quantity) {
		model.setQuantity(quantity);
	}

	/**
	 * Sets the user ID of this cp definition grouped entry.
	 *
	 * @param userId the user ID of this cp definition grouped entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this cp definition grouped entry.
	 *
	 * @param userName the user name of this cp definition grouped entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this cp definition grouped entry.
	 *
	 * @param userUuid the user uuid of this cp definition grouped entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this cp definition grouped entry.
	 *
	 * @param uuid the uuid of this cp definition grouped entry
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
	protected CPDefinitionGroupedEntryWrapper wrap(
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry) {

		return new CPDefinitionGroupedEntryWrapper(cpDefinitionGroupedEntry);
	}

}