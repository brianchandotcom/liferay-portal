/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommercePaymentMethodGroupRelQualifier}.
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommercePaymentMethodGroupRelQualifier
 * @generated
 */
public class CommercePaymentMethodGroupRelQualifierWrapper
	extends BaseModelWrapper<CommercePaymentMethodGroupRelQualifier>
	implements CommercePaymentMethodGroupRelQualifier,
			   ModelWrapper<CommercePaymentMethodGroupRelQualifier> {

	public CommercePaymentMethodGroupRelQualifierWrapper(
		CommercePaymentMethodGroupRelQualifier
			commercePaymentMethodGroupRelQualifier) {

		super(commercePaymentMethodGroupRelQualifier);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"commercePaymentMethodGroupRelQualifierId",
			getCommercePaymentMethodGroupRelQualifierId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put(
			"commercePaymentMethodGroupRelId",
			getCommercePaymentMethodGroupRelId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long commercePaymentMethodGroupRelQualifierId = (Long)attributes.get(
			"commercePaymentMethodGroupRelQualifierId");

		if (commercePaymentMethodGroupRelQualifierId != null) {
			setCommercePaymentMethodGroupRelQualifierId(
				commercePaymentMethodGroupRelQualifierId);
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

		Long commercePaymentMethodGroupRelId = (Long)attributes.get(
			"commercePaymentMethodGroupRelId");

		if (commercePaymentMethodGroupRelId != null) {
			setCommercePaymentMethodGroupRelId(commercePaymentMethodGroupRelId);
		}
	}

	@Override
	public CommercePaymentMethodGroupRelQualifier cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the fully qualified class name of this commerce payment method group rel qualifier.
	 *
	 * @return the fully qualified class name of this commerce payment method group rel qualifier
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this commerce payment method group rel qualifier.
	 *
	 * @return the class name ID of this commerce payment method group rel qualifier
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this commerce payment method group rel qualifier.
	 *
	 * @return the class pk of this commerce payment method group rel qualifier
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the commerce payment method group rel ID of this commerce payment method group rel qualifier.
	 *
	 * @return the commerce payment method group rel ID of this commerce payment method group rel qualifier
	 */
	@Override
	public long getCommercePaymentMethodGroupRelId() {
		return model.getCommercePaymentMethodGroupRelId();
	}

	/**
	 * Returns the commerce payment method group rel qualifier ID of this commerce payment method group rel qualifier.
	 *
	 * @return the commerce payment method group rel qualifier ID of this commerce payment method group rel qualifier
	 */
	@Override
	public long getCommercePaymentMethodGroupRelQualifierId() {
		return model.getCommercePaymentMethodGroupRelQualifierId();
	}

	/**
	 * Returns the company ID of this commerce payment method group rel qualifier.
	 *
	 * @return the company ID of this commerce payment method group rel qualifier
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce payment method group rel qualifier.
	 *
	 * @return the create date of this commerce payment method group rel qualifier
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this commerce payment method group rel qualifier.
	 *
	 * @return the modified date of this commerce payment method group rel qualifier
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce payment method group rel qualifier.
	 *
	 * @return the mvcc version of this commerce payment method group rel qualifier
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce payment method group rel qualifier.
	 *
	 * @return the primary key of this commerce payment method group rel qualifier
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this commerce payment method group rel qualifier.
	 *
	 * @return the user ID of this commerce payment method group rel qualifier
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce payment method group rel qualifier.
	 *
	 * @return the user name of this commerce payment method group rel qualifier
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce payment method group rel qualifier.
	 *
	 * @return the user uuid of this commerce payment method group rel qualifier
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
	 * Sets the class name ID of this commerce payment method group rel qualifier.
	 *
	 * @param classNameId the class name ID of this commerce payment method group rel qualifier
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this commerce payment method group rel qualifier.
	 *
	 * @param classPK the class pk of this commerce payment method group rel qualifier
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the commerce payment method group rel ID of this commerce payment method group rel qualifier.
	 *
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID of this commerce payment method group rel qualifier
	 */
	@Override
	public void setCommercePaymentMethodGroupRelId(
		long commercePaymentMethodGroupRelId) {

		model.setCommercePaymentMethodGroupRelId(
			commercePaymentMethodGroupRelId);
	}

	/**
	 * Sets the commerce payment method group rel qualifier ID of this commerce payment method group rel qualifier.
	 *
	 * @param commercePaymentMethodGroupRelQualifierId the commerce payment method group rel qualifier ID of this commerce payment method group rel qualifier
	 */
	@Override
	public void setCommercePaymentMethodGroupRelQualifierId(
		long commercePaymentMethodGroupRelQualifierId) {

		model.setCommercePaymentMethodGroupRelQualifierId(
			commercePaymentMethodGroupRelQualifierId);
	}

	/**
	 * Sets the company ID of this commerce payment method group rel qualifier.
	 *
	 * @param companyId the company ID of this commerce payment method group rel qualifier
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce payment method group rel qualifier.
	 *
	 * @param createDate the create date of this commerce payment method group rel qualifier
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this commerce payment method group rel qualifier.
	 *
	 * @param modifiedDate the modified date of this commerce payment method group rel qualifier
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce payment method group rel qualifier.
	 *
	 * @param mvccVersion the mvcc version of this commerce payment method group rel qualifier
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce payment method group rel qualifier.
	 *
	 * @param primaryKey the primary key of this commerce payment method group rel qualifier
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this commerce payment method group rel qualifier.
	 *
	 * @param userId the user ID of this commerce payment method group rel qualifier
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce payment method group rel qualifier.
	 *
	 * @param userName the user name of this commerce payment method group rel qualifier
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce payment method group rel qualifier.
	 *
	 * @param userUuid the user uuid of this commerce payment method group rel qualifier
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
	protected CommercePaymentMethodGroupRelQualifierWrapper wrap(
		CommercePaymentMethodGroupRelQualifier
			commercePaymentMethodGroupRelQualifier) {

		return new CommercePaymentMethodGroupRelQualifierWrapper(
			commercePaymentMethodGroupRelQualifier);
	}

}