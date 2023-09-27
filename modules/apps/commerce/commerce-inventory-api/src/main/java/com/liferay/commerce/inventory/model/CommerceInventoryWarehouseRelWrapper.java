/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceInventoryWarehouseRel}.
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryWarehouseRel
 * @generated
 */
public class CommerceInventoryWarehouseRelWrapper
	extends BaseModelWrapper<CommerceInventoryWarehouseRel>
	implements CommerceInventoryWarehouseRel,
			   ModelWrapper<CommerceInventoryWarehouseRel> {

	public CommerceInventoryWarehouseRelWrapper(
		CommerceInventoryWarehouseRel commerceInventoryWarehouseRel) {

		super(commerceInventoryWarehouseRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"commerceInventoryWarehouseRelId",
			getCommerceInventoryWarehouseRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put(
			"commerceInventoryWarehouseId", getCommerceInventoryWarehouseId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long commerceInventoryWarehouseRelId = (Long)attributes.get(
			"commerceInventoryWarehouseRelId");

		if (commerceInventoryWarehouseRelId != null) {
			setCommerceInventoryWarehouseRelId(commerceInventoryWarehouseRelId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long commerceInventoryWarehouseId = (Long)attributes.get(
			"commerceInventoryWarehouseId");

		if (commerceInventoryWarehouseId != null) {
			setCommerceInventoryWarehouseId(commerceInventoryWarehouseId);
		}
	}

	@Override
	public CommerceInventoryWarehouseRel cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the fully qualified class name of this commerce inventory warehouse rel.
	 *
	 * @return the fully qualified class name of this commerce inventory warehouse rel
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this commerce inventory warehouse rel.
	 *
	 * @return the class name ID of this commerce inventory warehouse rel
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this commerce inventory warehouse rel.
	 *
	 * @return the class pk of this commerce inventory warehouse rel
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the commerce inventory warehouse ID of this commerce inventory warehouse rel.
	 *
	 * @return the commerce inventory warehouse ID of this commerce inventory warehouse rel
	 */
	@Override
	public long getCommerceInventoryWarehouseId() {
		return model.getCommerceInventoryWarehouseId();
	}

	/**
	 * Returns the commerce inventory warehouse rel ID of this commerce inventory warehouse rel.
	 *
	 * @return the commerce inventory warehouse rel ID of this commerce inventory warehouse rel
	 */
	@Override
	public long getCommerceInventoryWarehouseRelId() {
		return model.getCommerceInventoryWarehouseRelId();
	}

	/**
	 * Returns the company ID of this commerce inventory warehouse rel.
	 *
	 * @return the company ID of this commerce inventory warehouse rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce inventory warehouse rel.
	 *
	 * @return the create date of this commerce inventory warehouse rel
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this commerce inventory warehouse rel.
	 *
	 * @return the modified date of this commerce inventory warehouse rel
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce inventory warehouse rel.
	 *
	 * @return the mvcc version of this commerce inventory warehouse rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce inventory warehouse rel.
	 *
	 * @return the primary key of this commerce inventory warehouse rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this commerce inventory warehouse rel.
	 *
	 * @return the user ID of this commerce inventory warehouse rel
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce inventory warehouse rel.
	 *
	 * @return the user name of this commerce inventory warehouse rel
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce inventory warehouse rel.
	 *
	 * @return the user uuid of this commerce inventory warehouse rel
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this commerce inventory warehouse rel.
	 *
	 * @param classNameId the class name ID of this commerce inventory warehouse rel
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this commerce inventory warehouse rel.
	 *
	 * @param classPK the class pk of this commerce inventory warehouse rel
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the commerce inventory warehouse ID of this commerce inventory warehouse rel.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID of this commerce inventory warehouse rel
	 */
	@Override
	public void setCommerceInventoryWarehouseId(
		long commerceInventoryWarehouseId) {

		model.setCommerceInventoryWarehouseId(commerceInventoryWarehouseId);
	}

	/**
	 * Sets the commerce inventory warehouse rel ID of this commerce inventory warehouse rel.
	 *
	 * @param commerceInventoryWarehouseRelId the commerce inventory warehouse rel ID of this commerce inventory warehouse rel
	 */
	@Override
	public void setCommerceInventoryWarehouseRelId(
		long commerceInventoryWarehouseRelId) {

		model.setCommerceInventoryWarehouseRelId(
			commerceInventoryWarehouseRelId);
	}

	/**
	 * Sets the company ID of this commerce inventory warehouse rel.
	 *
	 * @param companyId the company ID of this commerce inventory warehouse rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce inventory warehouse rel.
	 *
	 * @param createDate the create date of this commerce inventory warehouse rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this commerce inventory warehouse rel.
	 *
	 * @param modifiedDate the modified date of this commerce inventory warehouse rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce inventory warehouse rel.
	 *
	 * @param mvccVersion the mvcc version of this commerce inventory warehouse rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce inventory warehouse rel.
	 *
	 * @param primaryKey the primary key of this commerce inventory warehouse rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this commerce inventory warehouse rel.
	 *
	 * @param userId the user ID of this commerce inventory warehouse rel
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce inventory warehouse rel.
	 *
	 * @param userName the user name of this commerce inventory warehouse rel
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce inventory warehouse rel.
	 *
	 * @param userUuid the user uuid of this commerce inventory warehouse rel
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
	protected CommerceInventoryWarehouseRelWrapper wrap(
		CommerceInventoryWarehouseRel commerceInventoryWarehouseRel) {

		return new CommerceInventoryWarehouseRelWrapper(
			commerceInventoryWarehouseRel);
	}

}