/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.application.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceApplicationModelCProductRel}.
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceApplicationModelCProductRel
 * @generated
 */
public class CommerceApplicationModelCProductRelWrapper
	extends BaseModelWrapper<CommerceApplicationModelCProductRel>
	implements CommerceApplicationModelCProductRel,
			   ModelWrapper<CommerceApplicationModelCProductRel> {

	public CommerceApplicationModelCProductRelWrapper(
		CommerceApplicationModelCProductRel
			commerceApplicationModelCProductRel) {

		super(commerceApplicationModelCProductRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"commerceApplicationModelCProductRelId",
			getCommerceApplicationModelCProductRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put(
			"commerceApplicationModelId", getCommerceApplicationModelId());
		attributes.put("CProductId", getCProductId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceApplicationModelCProductRelId = (Long)attributes.get(
			"commerceApplicationModelCProductRelId");

		if (commerceApplicationModelCProductRelId != null) {
			setCommerceApplicationModelCProductRelId(
				commerceApplicationModelCProductRelId);
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

		Long commerceApplicationModelId = (Long)attributes.get(
			"commerceApplicationModelId");

		if (commerceApplicationModelId != null) {
			setCommerceApplicationModelId(commerceApplicationModelId);
		}

		Long CProductId = (Long)attributes.get("CProductId");

		if (CProductId != null) {
			setCProductId(CProductId);
		}
	}

	/**
	 * Returns the commerce application model c product rel ID of this commerce application model c product rel.
	 *
	 * @return the commerce application model c product rel ID of this commerce application model c product rel
	 */
	@Override
	public long getCommerceApplicationModelCProductRelId() {
		return model.getCommerceApplicationModelCProductRelId();
	}

	/**
	 * Returns the commerce application model ID of this commerce application model c product rel.
	 *
	 * @return the commerce application model ID of this commerce application model c product rel
	 */
	@Override
	public long getCommerceApplicationModelId() {
		return model.getCommerceApplicationModelId();
	}

	/**
	 * Returns the company ID of this commerce application model c product rel.
	 *
	 * @return the company ID of this commerce application model c product rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the c product ID of this commerce application model c product rel.
	 *
	 * @return the c product ID of this commerce application model c product rel
	 */
	@Override
	public long getCProductId() {
		return model.getCProductId();
	}

	/**
	 * Returns the create date of this commerce application model c product rel.
	 *
	 * @return the create date of this commerce application model c product rel
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this commerce application model c product rel.
	 *
	 * @return the modified date of this commerce application model c product rel
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this commerce application model c product rel.
	 *
	 * @return the primary key of this commerce application model c product rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this commerce application model c product rel.
	 *
	 * @return the user ID of this commerce application model c product rel
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce application model c product rel.
	 *
	 * @return the user name of this commerce application model c product rel
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce application model c product rel.
	 *
	 * @return the user uuid of this commerce application model c product rel
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
	 * Sets the commerce application model c product rel ID of this commerce application model c product rel.
	 *
	 * @param commerceApplicationModelCProductRelId the commerce application model c product rel ID of this commerce application model c product rel
	 */
	@Override
	public void setCommerceApplicationModelCProductRelId(
		long commerceApplicationModelCProductRelId) {

		model.setCommerceApplicationModelCProductRelId(
			commerceApplicationModelCProductRelId);
	}

	/**
	 * Sets the commerce application model ID of this commerce application model c product rel.
	 *
	 * @param commerceApplicationModelId the commerce application model ID of this commerce application model c product rel
	 */
	@Override
	public void setCommerceApplicationModelId(long commerceApplicationModelId) {
		model.setCommerceApplicationModelId(commerceApplicationModelId);
	}

	/**
	 * Sets the company ID of this commerce application model c product rel.
	 *
	 * @param companyId the company ID of this commerce application model c product rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the c product ID of this commerce application model c product rel.
	 *
	 * @param CProductId the c product ID of this commerce application model c product rel
	 */
	@Override
	public void setCProductId(long CProductId) {
		model.setCProductId(CProductId);
	}

	/**
	 * Sets the create date of this commerce application model c product rel.
	 *
	 * @param createDate the create date of this commerce application model c product rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this commerce application model c product rel.
	 *
	 * @param modifiedDate the modified date of this commerce application model c product rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this commerce application model c product rel.
	 *
	 * @param primaryKey the primary key of this commerce application model c product rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this commerce application model c product rel.
	 *
	 * @param userId the user ID of this commerce application model c product rel
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce application model c product rel.
	 *
	 * @param userName the user name of this commerce application model c product rel
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce application model c product rel.
	 *
	 * @param userUuid the user uuid of this commerce application model c product rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected CommerceApplicationModelCProductRelWrapper wrap(
		CommerceApplicationModelCProductRel
			commerceApplicationModelCProductRel) {

		return new CommerceApplicationModelCProductRelWrapper(
			commerceApplicationModelCProductRel);
	}

}