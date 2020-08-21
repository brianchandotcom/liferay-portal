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
 * This class is a wrapper for {@link CommerceApplicationBrand}.
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceApplicationBrand
 * @generated
 */
public class CommerceApplicationBrandWrapper
	extends BaseModelWrapper<CommerceApplicationBrand>
	implements CommerceApplicationBrand,
			   ModelWrapper<CommerceApplicationBrand> {

	public CommerceApplicationBrandWrapper(
		CommerceApplicationBrand commerceApplicationBrand) {

		super(commerceApplicationBrand);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"commerceApplicationBrandId", getCommerceApplicationBrandId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("logoId", getLogoId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceApplicationBrandId = (Long)attributes.get(
			"commerceApplicationBrandId");

		if (commerceApplicationBrandId != null) {
			setCommerceApplicationBrandId(commerceApplicationBrandId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Long logoId = (Long)attributes.get("logoId");

		if (logoId != null) {
			setLogoId(logoId);
		}
	}

	/**
	 * Returns the commerce application brand ID of this commerce application brand.
	 *
	 * @return the commerce application brand ID of this commerce application brand
	 */
	@Override
	public long getCommerceApplicationBrandId() {
		return model.getCommerceApplicationBrandId();
	}

	/**
	 * Returns the company ID of this commerce application brand.
	 *
	 * @return the company ID of this commerce application brand
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce application brand.
	 *
	 * @return the create date of this commerce application brand
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the logo ID of this commerce application brand.
	 *
	 * @return the logo ID of this commerce application brand
	 */
	@Override
	public long getLogoId() {
		return model.getLogoId();
	}

	/**
	 * Returns the modified date of this commerce application brand.
	 *
	 * @return the modified date of this commerce application brand
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this commerce application brand.
	 *
	 * @return the name of this commerce application brand
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this commerce application brand.
	 *
	 * @return the primary key of this commerce application brand
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this commerce application brand.
	 *
	 * @return the user ID of this commerce application brand
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce application brand.
	 *
	 * @return the user name of this commerce application brand
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce application brand.
	 *
	 * @return the user uuid of this commerce application brand
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
	 * Sets the commerce application brand ID of this commerce application brand.
	 *
	 * @param commerceApplicationBrandId the commerce application brand ID of this commerce application brand
	 */
	@Override
	public void setCommerceApplicationBrandId(long commerceApplicationBrandId) {
		model.setCommerceApplicationBrandId(commerceApplicationBrandId);
	}

	/**
	 * Sets the company ID of this commerce application brand.
	 *
	 * @param companyId the company ID of this commerce application brand
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce application brand.
	 *
	 * @param createDate the create date of this commerce application brand
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the logo ID of this commerce application brand.
	 *
	 * @param logoId the logo ID of this commerce application brand
	 */
	@Override
	public void setLogoId(long logoId) {
		model.setLogoId(logoId);
	}

	/**
	 * Sets the modified date of this commerce application brand.
	 *
	 * @param modifiedDate the modified date of this commerce application brand
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this commerce application brand.
	 *
	 * @param name the name of this commerce application brand
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this commerce application brand.
	 *
	 * @param primaryKey the primary key of this commerce application brand
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this commerce application brand.
	 *
	 * @param userId the user ID of this commerce application brand
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce application brand.
	 *
	 * @param userName the user name of this commerce application brand
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce application brand.
	 *
	 * @param userUuid the user uuid of this commerce application brand
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected CommerceApplicationBrandWrapper wrap(
		CommerceApplicationBrand commerceApplicationBrand) {

		return new CommerceApplicationBrandWrapper(commerceApplicationBrand);
	}

}