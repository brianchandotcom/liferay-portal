/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.engine.fixed.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceTaxFixedRate}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxFixedRate
 * @generated
 */
public class CommerceTaxFixedRateWrapper
	extends BaseModelWrapper<CommerceTaxFixedRate>
	implements CommerceTaxFixedRate, ModelWrapper<CommerceTaxFixedRate> {

	public CommerceTaxFixedRateWrapper(
		CommerceTaxFixedRate commerceTaxFixedRate) {

		super(commerceTaxFixedRate);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("commerceTaxFixedRateId", getCommerceTaxFixedRateId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CPTaxCategoryId", getCPTaxCategoryId());
		attributes.put("commerceTaxMethodId", getCommerceTaxMethodId());
		attributes.put("rate", getRate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long commerceTaxFixedRateId = (Long)attributes.get(
			"commerceTaxFixedRateId");

		if (commerceTaxFixedRateId != null) {
			setCommerceTaxFixedRateId(commerceTaxFixedRateId);
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

		Long CPTaxCategoryId = (Long)attributes.get("CPTaxCategoryId");

		if (CPTaxCategoryId != null) {
			setCPTaxCategoryId(CPTaxCategoryId);
		}

		Long commerceTaxMethodId = (Long)attributes.get("commerceTaxMethodId");

		if (commerceTaxMethodId != null) {
			setCommerceTaxMethodId(commerceTaxMethodId);
		}

		Double rate = (Double)attributes.get("rate");

		if (rate != null) {
			setRate(rate);
		}
	}

	@Override
	public CommerceTaxFixedRate cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the commerce tax fixed rate ID of this commerce tax fixed rate.
	 *
	 * @return the commerce tax fixed rate ID of this commerce tax fixed rate
	 */
	@Override
	public long getCommerceTaxFixedRateId() {
		return model.getCommerceTaxFixedRateId();
	}

	/**
	 * Returns the commerce tax method ID of this commerce tax fixed rate.
	 *
	 * @return the commerce tax method ID of this commerce tax fixed rate
	 */
	@Override
	public long getCommerceTaxMethodId() {
		return model.getCommerceTaxMethodId();
	}

	/**
	 * Returns the company ID of this commerce tax fixed rate.
	 *
	 * @return the company ID of this commerce tax fixed rate
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	@Override
	public com.liferay.commerce.product.model.CPTaxCategory getCPTaxCategory()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCPTaxCategory();
	}

	/**
	 * Returns the cp tax category ID of this commerce tax fixed rate.
	 *
	 * @return the cp tax category ID of this commerce tax fixed rate
	 */
	@Override
	public long getCPTaxCategoryId() {
		return model.getCPTaxCategoryId();
	}

	/**
	 * Returns the create date of this commerce tax fixed rate.
	 *
	 * @return the create date of this commerce tax fixed rate
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this commerce tax fixed rate.
	 *
	 * @return the group ID of this commerce tax fixed rate
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this commerce tax fixed rate.
	 *
	 * @return the modified date of this commerce tax fixed rate
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce tax fixed rate.
	 *
	 * @return the mvcc version of this commerce tax fixed rate
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce tax fixed rate.
	 *
	 * @return the primary key of this commerce tax fixed rate
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the rate of this commerce tax fixed rate.
	 *
	 * @return the rate of this commerce tax fixed rate
	 */
	@Override
	public double getRate() {
		return model.getRate();
	}

	/**
	 * Returns the user ID of this commerce tax fixed rate.
	 *
	 * @return the user ID of this commerce tax fixed rate
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce tax fixed rate.
	 *
	 * @return the user name of this commerce tax fixed rate
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce tax fixed rate.
	 *
	 * @return the user uuid of this commerce tax fixed rate
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
	 * Sets the commerce tax fixed rate ID of this commerce tax fixed rate.
	 *
	 * @param commerceTaxFixedRateId the commerce tax fixed rate ID of this commerce tax fixed rate
	 */
	@Override
	public void setCommerceTaxFixedRateId(long commerceTaxFixedRateId) {
		model.setCommerceTaxFixedRateId(commerceTaxFixedRateId);
	}

	/**
	 * Sets the commerce tax method ID of this commerce tax fixed rate.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID of this commerce tax fixed rate
	 */
	@Override
	public void setCommerceTaxMethodId(long commerceTaxMethodId) {
		model.setCommerceTaxMethodId(commerceTaxMethodId);
	}

	/**
	 * Sets the company ID of this commerce tax fixed rate.
	 *
	 * @param companyId the company ID of this commerce tax fixed rate
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cp tax category ID of this commerce tax fixed rate.
	 *
	 * @param CPTaxCategoryId the cp tax category ID of this commerce tax fixed rate
	 */
	@Override
	public void setCPTaxCategoryId(long CPTaxCategoryId) {
		model.setCPTaxCategoryId(CPTaxCategoryId);
	}

	/**
	 * Sets the create date of this commerce tax fixed rate.
	 *
	 * @param createDate the create date of this commerce tax fixed rate
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this commerce tax fixed rate.
	 *
	 * @param groupId the group ID of this commerce tax fixed rate
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this commerce tax fixed rate.
	 *
	 * @param modifiedDate the modified date of this commerce tax fixed rate
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce tax fixed rate.
	 *
	 * @param mvccVersion the mvcc version of this commerce tax fixed rate
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce tax fixed rate.
	 *
	 * @param primaryKey the primary key of this commerce tax fixed rate
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the rate of this commerce tax fixed rate.
	 *
	 * @param rate the rate of this commerce tax fixed rate
	 */
	@Override
	public void setRate(double rate) {
		model.setRate(rate);
	}

	/**
	 * Sets the user ID of this commerce tax fixed rate.
	 *
	 * @param userId the user ID of this commerce tax fixed rate
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce tax fixed rate.
	 *
	 * @param userName the user name of this commerce tax fixed rate
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce tax fixed rate.
	 *
	 * @param userUuid the user uuid of this commerce tax fixed rate
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
	protected CommerceTaxFixedRateWrapper wrap(
		CommerceTaxFixedRate commerceTaxFixedRate) {

		return new CommerceTaxFixedRateWrapper(commerceTaxFixedRate);
	}

}