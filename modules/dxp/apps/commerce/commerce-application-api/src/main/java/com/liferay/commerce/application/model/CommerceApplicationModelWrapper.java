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
 * This class is a wrapper for {@link CommerceApplicationModel}.
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceApplicationModel
 * @generated
 */
public class CommerceApplicationModelWrapper
	extends BaseModelWrapper<CommerceApplicationModel>
	implements CommerceApplicationModel,
			   ModelWrapper<CommerceApplicationModel> {

	public CommerceApplicationModelWrapper(
		CommerceApplicationModel commerceApplicationModel) {

		super(commerceApplicationModel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"commerceApplicationModelId", getCommerceApplicationModelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put(
			"commerceApplicationBrandId", getCommerceApplicationBrandId());
		attributes.put("name", getName());
		attributes.put("year", getYear());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceApplicationModelId = (Long)attributes.get(
			"commerceApplicationModelId");

		if (commerceApplicationModelId != null) {
			setCommerceApplicationModelId(commerceApplicationModelId);
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

		Long commerceApplicationBrandId = (Long)attributes.get(
			"commerceApplicationBrandId");

		if (commerceApplicationBrandId != null) {
			setCommerceApplicationBrandId(commerceApplicationBrandId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String year = (String)attributes.get("year");

		if (year != null) {
			setYear(year);
		}
	}

	/**
	 * Returns the commerce application brand ID of this commerce application model.
	 *
	 * @return the commerce application brand ID of this commerce application model
	 */
	@Override
	public long getCommerceApplicationBrandId() {
		return model.getCommerceApplicationBrandId();
	}

	/**
	 * Returns the commerce application model ID of this commerce application model.
	 *
	 * @return the commerce application model ID of this commerce application model
	 */
	@Override
	public long getCommerceApplicationModelId() {
		return model.getCommerceApplicationModelId();
	}

	/**
	 * Returns the company ID of this commerce application model.
	 *
	 * @return the company ID of this commerce application model
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce application model.
	 *
	 * @return the create date of this commerce application model
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this commerce application model.
	 *
	 * @return the modified date of this commerce application model
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this commerce application model.
	 *
	 * @return the name of this commerce application model
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this commerce application model.
	 *
	 * @return the primary key of this commerce application model
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this commerce application model.
	 *
	 * @return the user ID of this commerce application model
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce application model.
	 *
	 * @return the user name of this commerce application model
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce application model.
	 *
	 * @return the user uuid of this commerce application model
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the year of this commerce application model.
	 *
	 * @return the year of this commerce application model
	 */
	@Override
	public String getYear() {
		return model.getYear();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the commerce application brand ID of this commerce application model.
	 *
	 * @param commerceApplicationBrandId the commerce application brand ID of this commerce application model
	 */
	@Override
	public void setCommerceApplicationBrandId(long commerceApplicationBrandId) {
		model.setCommerceApplicationBrandId(commerceApplicationBrandId);
	}

	/**
	 * Sets the commerce application model ID of this commerce application model.
	 *
	 * @param commerceApplicationModelId the commerce application model ID of this commerce application model
	 */
	@Override
	public void setCommerceApplicationModelId(long commerceApplicationModelId) {
		model.setCommerceApplicationModelId(commerceApplicationModelId);
	}

	/**
	 * Sets the company ID of this commerce application model.
	 *
	 * @param companyId the company ID of this commerce application model
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce application model.
	 *
	 * @param createDate the create date of this commerce application model
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this commerce application model.
	 *
	 * @param modifiedDate the modified date of this commerce application model
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this commerce application model.
	 *
	 * @param name the name of this commerce application model
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this commerce application model.
	 *
	 * @param primaryKey the primary key of this commerce application model
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this commerce application model.
	 *
	 * @param userId the user ID of this commerce application model
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce application model.
	 *
	 * @param userName the user name of this commerce application model
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce application model.
	 *
	 * @param userUuid the user uuid of this commerce application model
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the year of this commerce application model.
	 *
	 * @param year the year of this commerce application model
	 */
	@Override
	public void setYear(String year) {
		model.setYear(year);
	}

	@Override
	protected CommerceApplicationModelWrapper wrap(
		CommerceApplicationModel commerceApplicationModel) {

		return new CommerceApplicationModelWrapper(commerceApplicationModel);
	}

}