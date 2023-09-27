/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceShippingOptionAccountEntryRel}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingOptionAccountEntryRel
 * @generated
 */
public class CommerceShippingOptionAccountEntryRelWrapper
	extends BaseModelWrapper<CommerceShippingOptionAccountEntryRel>
	implements CommerceShippingOptionAccountEntryRel,
			   ModelWrapper<CommerceShippingOptionAccountEntryRel> {

	public CommerceShippingOptionAccountEntryRelWrapper(
		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel) {

		super(commerceShippingOptionAccountEntryRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"CommerceShippingOptionAccountEntryRelId",
			getCommerceShippingOptionAccountEntryRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("accountEntryId", getAccountEntryId());
		attributes.put("commerceChannelId", getCommerceChannelId());
		attributes.put(
			"commerceShippingMethodKey", getCommerceShippingMethodKey());
		attributes.put(
			"commerceShippingOptionKey", getCommerceShippingOptionKey());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long CommerceShippingOptionAccountEntryRelId = (Long)attributes.get(
			"CommerceShippingOptionAccountEntryRelId");

		if (CommerceShippingOptionAccountEntryRelId != null) {
			setCommerceShippingOptionAccountEntryRelId(
				CommerceShippingOptionAccountEntryRelId);
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

		Long accountEntryId = (Long)attributes.get("accountEntryId");

		if (accountEntryId != null) {
			setAccountEntryId(accountEntryId);
		}

		Long commerceChannelId = (Long)attributes.get("commerceChannelId");

		if (commerceChannelId != null) {
			setCommerceChannelId(commerceChannelId);
		}

		String commerceShippingMethodKey = (String)attributes.get(
			"commerceShippingMethodKey");

		if (commerceShippingMethodKey != null) {
			setCommerceShippingMethodKey(commerceShippingMethodKey);
		}

		String commerceShippingOptionKey = (String)attributes.get(
			"commerceShippingOptionKey");

		if (commerceShippingOptionKey != null) {
			setCommerceShippingOptionKey(commerceShippingOptionKey);
		}
	}

	@Override
	public CommerceShippingOptionAccountEntryRel cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the account entry ID of this commerce shipping option account entry rel.
	 *
	 * @return the account entry ID of this commerce shipping option account entry rel
	 */
	@Override
	public long getAccountEntryId() {
		return model.getAccountEntryId();
	}

	/**
	 * Returns the commerce channel ID of this commerce shipping option account entry rel.
	 *
	 * @return the commerce channel ID of this commerce shipping option account entry rel
	 */
	@Override
	public long getCommerceChannelId() {
		return model.getCommerceChannelId();
	}

	/**
	 * Returns the commerce shipping method key of this commerce shipping option account entry rel.
	 *
	 * @return the commerce shipping method key of this commerce shipping option account entry rel
	 */
	@Override
	public String getCommerceShippingMethodKey() {
		return model.getCommerceShippingMethodKey();
	}

	/**
	 * Returns the commerce shipping option account entry rel ID of this commerce shipping option account entry rel.
	 *
	 * @return the commerce shipping option account entry rel ID of this commerce shipping option account entry rel
	 */
	@Override
	public long getCommerceShippingOptionAccountEntryRelId() {
		return model.getCommerceShippingOptionAccountEntryRelId();
	}

	/**
	 * Returns the commerce shipping option key of this commerce shipping option account entry rel.
	 *
	 * @return the commerce shipping option key of this commerce shipping option account entry rel
	 */
	@Override
	public String getCommerceShippingOptionKey() {
		return model.getCommerceShippingOptionKey();
	}

	/**
	 * Returns the company ID of this commerce shipping option account entry rel.
	 *
	 * @return the company ID of this commerce shipping option account entry rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce shipping option account entry rel.
	 *
	 * @return the create date of this commerce shipping option account entry rel
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this commerce shipping option account entry rel.
	 *
	 * @return the modified date of this commerce shipping option account entry rel
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce shipping option account entry rel.
	 *
	 * @return the mvcc version of this commerce shipping option account entry rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce shipping option account entry rel.
	 *
	 * @return the primary key of this commerce shipping option account entry rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this commerce shipping option account entry rel.
	 *
	 * @return the user ID of this commerce shipping option account entry rel
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce shipping option account entry rel.
	 *
	 * @return the user name of this commerce shipping option account entry rel
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce shipping option account entry rel.
	 *
	 * @return the user uuid of this commerce shipping option account entry rel
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the account entry ID of this commerce shipping option account entry rel.
	 *
	 * @param accountEntryId the account entry ID of this commerce shipping option account entry rel
	 */
	@Override
	public void setAccountEntryId(long accountEntryId) {
		model.setAccountEntryId(accountEntryId);
	}

	/**
	 * Sets the commerce channel ID of this commerce shipping option account entry rel.
	 *
	 * @param commerceChannelId the commerce channel ID of this commerce shipping option account entry rel
	 */
	@Override
	public void setCommerceChannelId(long commerceChannelId) {
		model.setCommerceChannelId(commerceChannelId);
	}

	/**
	 * Sets the commerce shipping method key of this commerce shipping option account entry rel.
	 *
	 * @param commerceShippingMethodKey the commerce shipping method key of this commerce shipping option account entry rel
	 */
	@Override
	public void setCommerceShippingMethodKey(String commerceShippingMethodKey) {
		model.setCommerceShippingMethodKey(commerceShippingMethodKey);
	}

	/**
	 * Sets the commerce shipping option account entry rel ID of this commerce shipping option account entry rel.
	 *
	 * @param CommerceShippingOptionAccountEntryRelId the commerce shipping option account entry rel ID of this commerce shipping option account entry rel
	 */
	@Override
	public void setCommerceShippingOptionAccountEntryRelId(
		long CommerceShippingOptionAccountEntryRelId) {

		model.setCommerceShippingOptionAccountEntryRelId(
			CommerceShippingOptionAccountEntryRelId);
	}

	/**
	 * Sets the commerce shipping option key of this commerce shipping option account entry rel.
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key of this commerce shipping option account entry rel
	 */
	@Override
	public void setCommerceShippingOptionKey(String commerceShippingOptionKey) {
		model.setCommerceShippingOptionKey(commerceShippingOptionKey);
	}

	/**
	 * Sets the company ID of this commerce shipping option account entry rel.
	 *
	 * @param companyId the company ID of this commerce shipping option account entry rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce shipping option account entry rel.
	 *
	 * @param createDate the create date of this commerce shipping option account entry rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this commerce shipping option account entry rel.
	 *
	 * @param modifiedDate the modified date of this commerce shipping option account entry rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce shipping option account entry rel.
	 *
	 * @param mvccVersion the mvcc version of this commerce shipping option account entry rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce shipping option account entry rel.
	 *
	 * @param primaryKey the primary key of this commerce shipping option account entry rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this commerce shipping option account entry rel.
	 *
	 * @param userId the user ID of this commerce shipping option account entry rel
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce shipping option account entry rel.
	 *
	 * @param userName the user name of this commerce shipping option account entry rel
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce shipping option account entry rel.
	 *
	 * @param userUuid the user uuid of this commerce shipping option account entry rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected CommerceShippingOptionAccountEntryRelWrapper wrap(
		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel) {

		return new CommerceShippingOptionAccountEntryRelWrapper(
			commerceShippingOptionAccountEntryRel);
	}

}