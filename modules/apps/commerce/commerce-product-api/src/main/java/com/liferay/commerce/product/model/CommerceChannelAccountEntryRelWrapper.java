/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link CommerceChannelAccountEntryRel}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceChannelAccountEntryRel
 * @generated
 */
public class CommerceChannelAccountEntryRelWrapper
	extends BaseModelWrapper<CommerceChannelAccountEntryRel>
	implements CommerceChannelAccountEntryRel,
			   ModelWrapper<CommerceChannelAccountEntryRel> {

	public CommerceChannelAccountEntryRelWrapper(
		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel) {

		super(commerceChannelAccountEntryRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put(
			"commerceChannelAccountEntryRelId",
			getCommerceChannelAccountEntryRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("accountEntryId", getAccountEntryId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("commerceChannelId", getCommerceChannelId());
		attributes.put("overrideEligibility", isOverrideEligibility());
		attributes.put("priority", getPriority());
		attributes.put("type", getType());

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

		Long commerceChannelAccountEntryRelId = (Long)attributes.get(
			"commerceChannelAccountEntryRelId");

		if (commerceChannelAccountEntryRelId != null) {
			setCommerceChannelAccountEntryRelId(
				commerceChannelAccountEntryRelId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long commerceChannelId = (Long)attributes.get("commerceChannelId");

		if (commerceChannelId != null) {
			setCommerceChannelId(commerceChannelId);
		}

		Boolean overrideEligibility = (Boolean)attributes.get(
			"overrideEligibility");

		if (overrideEligibility != null) {
			setOverrideEligibility(overrideEligibility);
		}

		Double priority = (Double)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}
	}

	@Override
	public CommerceChannelAccountEntryRel cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the account entry ID of this commerce channel account entry rel.
	 *
	 * @return the account entry ID of this commerce channel account entry rel
	 */
	@Override
	public long getAccountEntryId() {
		return model.getAccountEntryId();
	}

	/**
	 * Returns the fully qualified class name of this commerce channel account entry rel.
	 *
	 * @return the fully qualified class name of this commerce channel account entry rel
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this commerce channel account entry rel.
	 *
	 * @return the class name ID of this commerce channel account entry rel
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this commerce channel account entry rel.
	 *
	 * @return the class pk of this commerce channel account entry rel
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the commerce channel account entry rel ID of this commerce channel account entry rel.
	 *
	 * @return the commerce channel account entry rel ID of this commerce channel account entry rel
	 */
	@Override
	public long getCommerceChannelAccountEntryRelId() {
		return model.getCommerceChannelAccountEntryRelId();
	}

	/**
	 * Returns the commerce channel ID of this commerce channel account entry rel.
	 *
	 * @return the commerce channel ID of this commerce channel account entry rel
	 */
	@Override
	public long getCommerceChannelId() {
		return model.getCommerceChannelId();
	}

	/**
	 * Returns the company ID of this commerce channel account entry rel.
	 *
	 * @return the company ID of this commerce channel account entry rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce channel account entry rel.
	 *
	 * @return the create date of this commerce channel account entry rel
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this commerce channel account entry rel.
	 *
	 * @return the ct collection ID of this commerce channel account entry rel
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the modified date of this commerce channel account entry rel.
	 *
	 * @return the modified date of this commerce channel account entry rel
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce channel account entry rel.
	 *
	 * @return the mvcc version of this commerce channel account entry rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the override eligibility of this commerce channel account entry rel.
	 *
	 * @return the override eligibility of this commerce channel account entry rel
	 */
	@Override
	public boolean getOverrideEligibility() {
		return model.getOverrideEligibility();
	}

	/**
	 * Returns the primary key of this commerce channel account entry rel.
	 *
	 * @return the primary key of this commerce channel account entry rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this commerce channel account entry rel.
	 *
	 * @return the priority of this commerce channel account entry rel
	 */
	@Override
	public double getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the type of this commerce channel account entry rel.
	 *
	 * @return the type of this commerce channel account entry rel
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this commerce channel account entry rel.
	 *
	 * @return the user ID of this commerce channel account entry rel
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce channel account entry rel.
	 *
	 * @return the user name of this commerce channel account entry rel
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce channel account entry rel.
	 *
	 * @return the user uuid of this commerce channel account entry rel
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns <code>true</code> if this commerce channel account entry rel is override eligibility.
	 *
	 * @return <code>true</code> if this commerce channel account entry rel is override eligibility; <code>false</code> otherwise
	 */
	@Override
	public boolean isOverrideEligibility() {
		return model.isOverrideEligibility();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the account entry ID of this commerce channel account entry rel.
	 *
	 * @param accountEntryId the account entry ID of this commerce channel account entry rel
	 */
	@Override
	public void setAccountEntryId(long accountEntryId) {
		model.setAccountEntryId(accountEntryId);
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this commerce channel account entry rel.
	 *
	 * @param classNameId the class name ID of this commerce channel account entry rel
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this commerce channel account entry rel.
	 *
	 * @param classPK the class pk of this commerce channel account entry rel
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the commerce channel account entry rel ID of this commerce channel account entry rel.
	 *
	 * @param commerceChannelAccountEntryRelId the commerce channel account entry rel ID of this commerce channel account entry rel
	 */
	@Override
	public void setCommerceChannelAccountEntryRelId(
		long commerceChannelAccountEntryRelId) {

		model.setCommerceChannelAccountEntryRelId(
			commerceChannelAccountEntryRelId);
	}

	/**
	 * Sets the commerce channel ID of this commerce channel account entry rel.
	 *
	 * @param commerceChannelId the commerce channel ID of this commerce channel account entry rel
	 */
	@Override
	public void setCommerceChannelId(long commerceChannelId) {
		model.setCommerceChannelId(commerceChannelId);
	}

	/**
	 * Sets the company ID of this commerce channel account entry rel.
	 *
	 * @param companyId the company ID of this commerce channel account entry rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce channel account entry rel.
	 *
	 * @param createDate the create date of this commerce channel account entry rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this commerce channel account entry rel.
	 *
	 * @param ctCollectionId the ct collection ID of this commerce channel account entry rel
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the modified date of this commerce channel account entry rel.
	 *
	 * @param modifiedDate the modified date of this commerce channel account entry rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce channel account entry rel.
	 *
	 * @param mvccVersion the mvcc version of this commerce channel account entry rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets whether this commerce channel account entry rel is override eligibility.
	 *
	 * @param overrideEligibility the override eligibility of this commerce channel account entry rel
	 */
	@Override
	public void setOverrideEligibility(boolean overrideEligibility) {
		model.setOverrideEligibility(overrideEligibility);
	}

	/**
	 * Sets the primary key of this commerce channel account entry rel.
	 *
	 * @param primaryKey the primary key of this commerce channel account entry rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this commerce channel account entry rel.
	 *
	 * @param priority the priority of this commerce channel account entry rel
	 */
	@Override
	public void setPriority(double priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets the type of this commerce channel account entry rel.
	 *
	 * @param type the type of this commerce channel account entry rel
	 */
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this commerce channel account entry rel.
	 *
	 * @param userId the user ID of this commerce channel account entry rel
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce channel account entry rel.
	 *
	 * @param userName the user name of this commerce channel account entry rel
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce channel account entry rel.
	 *
	 * @param userUuid the user uuid of this commerce channel account entry rel
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
	public Map<String, Function<CommerceChannelAccountEntryRel, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<CommerceChannelAccountEntryRel, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected CommerceChannelAccountEntryRelWrapper wrap(
		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel) {

		return new CommerceChannelAccountEntryRelWrapper(
			commerceChannelAccountEntryRel);
	}

}