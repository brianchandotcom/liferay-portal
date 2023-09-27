/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceDiscountOrderTypeRel}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceDiscountOrderTypeRel
 * @generated
 */
public class CommerceDiscountOrderTypeRelWrapper
	extends BaseModelWrapper<CommerceDiscountOrderTypeRel>
	implements CommerceDiscountOrderTypeRel,
			   ModelWrapper<CommerceDiscountOrderTypeRel> {

	public CommerceDiscountOrderTypeRelWrapper(
		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel) {

		super(commerceDiscountOrderTypeRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put(
			"commerceDiscountOrderTypeRelId",
			getCommerceDiscountOrderTypeRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceDiscountId", getCommerceDiscountId());
		attributes.put("commerceOrderTypeId", getCommerceOrderTypeId());
		attributes.put("priority", getPriority());
		attributes.put("lastPublishDate", getLastPublishDate());

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

		Long commerceDiscountOrderTypeRelId = (Long)attributes.get(
			"commerceDiscountOrderTypeRelId");

		if (commerceDiscountOrderTypeRelId != null) {
			setCommerceDiscountOrderTypeRelId(commerceDiscountOrderTypeRelId);
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

		Long commerceDiscountId = (Long)attributes.get("commerceDiscountId");

		if (commerceDiscountId != null) {
			setCommerceDiscountId(commerceDiscountId);
		}

		Long commerceOrderTypeId = (Long)attributes.get("commerceOrderTypeId");

		if (commerceOrderTypeId != null) {
			setCommerceOrderTypeId(commerceOrderTypeId);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public CommerceDiscountOrderTypeRel cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public CommerceDiscount getCommerceDiscount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceDiscount();
	}

	/**
	 * Returns the commerce discount ID of this commerce discount order type rel.
	 *
	 * @return the commerce discount ID of this commerce discount order type rel
	 */
	@Override
	public long getCommerceDiscountId() {
		return model.getCommerceDiscountId();
	}

	/**
	 * Returns the commerce discount order type rel ID of this commerce discount order type rel.
	 *
	 * @return the commerce discount order type rel ID of this commerce discount order type rel
	 */
	@Override
	public long getCommerceDiscountOrderTypeRelId() {
		return model.getCommerceDiscountOrderTypeRelId();
	}

	/**
	 * Returns the commerce order type ID of this commerce discount order type rel.
	 *
	 * @return the commerce order type ID of this commerce discount order type rel
	 */
	@Override
	public long getCommerceOrderTypeId() {
		return model.getCommerceOrderTypeId();
	}

	/**
	 * Returns the company ID of this commerce discount order type rel.
	 *
	 * @return the company ID of this commerce discount order type rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce discount order type rel.
	 *
	 * @return the create date of this commerce discount order type rel
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the last publish date of this commerce discount order type rel.
	 *
	 * @return the last publish date of this commerce discount order type rel
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this commerce discount order type rel.
	 *
	 * @return the modified date of this commerce discount order type rel
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce discount order type rel.
	 *
	 * @return the mvcc version of this commerce discount order type rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce discount order type rel.
	 *
	 * @return the primary key of this commerce discount order type rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this commerce discount order type rel.
	 *
	 * @return the priority of this commerce discount order type rel
	 */
	@Override
	public int getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the user ID of this commerce discount order type rel.
	 *
	 * @return the user ID of this commerce discount order type rel
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce discount order type rel.
	 *
	 * @return the user name of this commerce discount order type rel
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce discount order type rel.
	 *
	 * @return the user uuid of this commerce discount order type rel
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce discount order type rel.
	 *
	 * @return the uuid of this commerce discount order type rel
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
	 * Sets the commerce discount ID of this commerce discount order type rel.
	 *
	 * @param commerceDiscountId the commerce discount ID of this commerce discount order type rel
	 */
	@Override
	public void setCommerceDiscountId(long commerceDiscountId) {
		model.setCommerceDiscountId(commerceDiscountId);
	}

	/**
	 * Sets the commerce discount order type rel ID of this commerce discount order type rel.
	 *
	 * @param commerceDiscountOrderTypeRelId the commerce discount order type rel ID of this commerce discount order type rel
	 */
	@Override
	public void setCommerceDiscountOrderTypeRelId(
		long commerceDiscountOrderTypeRelId) {

		model.setCommerceDiscountOrderTypeRelId(commerceDiscountOrderTypeRelId);
	}

	/**
	 * Sets the commerce order type ID of this commerce discount order type rel.
	 *
	 * @param commerceOrderTypeId the commerce order type ID of this commerce discount order type rel
	 */
	@Override
	public void setCommerceOrderTypeId(long commerceOrderTypeId) {
		model.setCommerceOrderTypeId(commerceOrderTypeId);
	}

	/**
	 * Sets the company ID of this commerce discount order type rel.
	 *
	 * @param companyId the company ID of this commerce discount order type rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce discount order type rel.
	 *
	 * @param createDate the create date of this commerce discount order type rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the last publish date of this commerce discount order type rel.
	 *
	 * @param lastPublishDate the last publish date of this commerce discount order type rel
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this commerce discount order type rel.
	 *
	 * @param modifiedDate the modified date of this commerce discount order type rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce discount order type rel.
	 *
	 * @param mvccVersion the mvcc version of this commerce discount order type rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce discount order type rel.
	 *
	 * @param primaryKey the primary key of this commerce discount order type rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this commerce discount order type rel.
	 *
	 * @param priority the priority of this commerce discount order type rel
	 */
	@Override
	public void setPriority(int priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets the user ID of this commerce discount order type rel.
	 *
	 * @param userId the user ID of this commerce discount order type rel
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce discount order type rel.
	 *
	 * @param userName the user name of this commerce discount order type rel
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce discount order type rel.
	 *
	 * @param userUuid the user uuid of this commerce discount order type rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce discount order type rel.
	 *
	 * @param uuid the uuid of this commerce discount order type rel
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
	protected CommerceDiscountOrderTypeRelWrapper wrap(
		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel) {

		return new CommerceDiscountOrderTypeRelWrapper(
			commerceDiscountOrderTypeRel);
	}

}