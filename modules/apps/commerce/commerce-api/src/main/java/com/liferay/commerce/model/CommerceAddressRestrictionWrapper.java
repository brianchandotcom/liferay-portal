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
 * This class is a wrapper for {@link CommerceAddressRestriction}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAddressRestriction
 * @generated
 */
public class CommerceAddressRestrictionWrapper
	extends BaseModelWrapper<CommerceAddressRestriction>
	implements CommerceAddressRestriction,
			   ModelWrapper<CommerceAddressRestriction> {

	public CommerceAddressRestrictionWrapper(
		CommerceAddressRestriction commerceAddressRestriction) {

		super(commerceAddressRestriction);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"commerceAddressRestrictionId", getCommerceAddressRestrictionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("countryId", getCountryId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long commerceAddressRestrictionId = (Long)attributes.get(
			"commerceAddressRestrictionId");

		if (commerceAddressRestrictionId != null) {
			setCommerceAddressRestrictionId(commerceAddressRestrictionId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long countryId = (Long)attributes.get("countryId");

		if (countryId != null) {
			setCountryId(countryId);
		}
	}

	@Override
	public CommerceAddressRestriction cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the fully qualified class name of this commerce address restriction.
	 *
	 * @return the fully qualified class name of this commerce address restriction
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this commerce address restriction.
	 *
	 * @return the class name ID of this commerce address restriction
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this commerce address restriction.
	 *
	 * @return the class pk of this commerce address restriction
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the commerce address restriction ID of this commerce address restriction.
	 *
	 * @return the commerce address restriction ID of this commerce address restriction
	 */
	@Override
	public long getCommerceAddressRestrictionId() {
		return model.getCommerceAddressRestrictionId();
	}

	/**
	 * Returns the company ID of this commerce address restriction.
	 *
	 * @return the company ID of this commerce address restriction
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	@Override
	public com.liferay.portal.kernel.model.Country getCountry()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCountry();
	}

	/**
	 * Returns the country ID of this commerce address restriction.
	 *
	 * @return the country ID of this commerce address restriction
	 */
	@Override
	public long getCountryId() {
		return model.getCountryId();
	}

	/**
	 * Returns the create date of this commerce address restriction.
	 *
	 * @return the create date of this commerce address restriction
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this commerce address restriction.
	 *
	 * @return the group ID of this commerce address restriction
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this commerce address restriction.
	 *
	 * @return the modified date of this commerce address restriction
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce address restriction.
	 *
	 * @return the mvcc version of this commerce address restriction
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce address restriction.
	 *
	 * @return the primary key of this commerce address restriction
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this commerce address restriction.
	 *
	 * @return the user ID of this commerce address restriction
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce address restriction.
	 *
	 * @return the user name of this commerce address restriction
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce address restriction.
	 *
	 * @return the user uuid of this commerce address restriction
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
	 * Sets the class name ID of this commerce address restriction.
	 *
	 * @param classNameId the class name ID of this commerce address restriction
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this commerce address restriction.
	 *
	 * @param classPK the class pk of this commerce address restriction
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the commerce address restriction ID of this commerce address restriction.
	 *
	 * @param commerceAddressRestrictionId the commerce address restriction ID of this commerce address restriction
	 */
	@Override
	public void setCommerceAddressRestrictionId(
		long commerceAddressRestrictionId) {

		model.setCommerceAddressRestrictionId(commerceAddressRestrictionId);
	}

	/**
	 * Sets the company ID of this commerce address restriction.
	 *
	 * @param companyId the company ID of this commerce address restriction
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the country ID of this commerce address restriction.
	 *
	 * @param countryId the country ID of this commerce address restriction
	 */
	@Override
	public void setCountryId(long countryId) {
		model.setCountryId(countryId);
	}

	/**
	 * Sets the create date of this commerce address restriction.
	 *
	 * @param createDate the create date of this commerce address restriction
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this commerce address restriction.
	 *
	 * @param groupId the group ID of this commerce address restriction
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this commerce address restriction.
	 *
	 * @param modifiedDate the modified date of this commerce address restriction
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce address restriction.
	 *
	 * @param mvccVersion the mvcc version of this commerce address restriction
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce address restriction.
	 *
	 * @param primaryKey the primary key of this commerce address restriction
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this commerce address restriction.
	 *
	 * @param userId the user ID of this commerce address restriction
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce address restriction.
	 *
	 * @param userName the user name of this commerce address restriction
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce address restriction.
	 *
	 * @param userUuid the user uuid of this commerce address restriction
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
	protected CommerceAddressRestrictionWrapper wrap(
		CommerceAddressRestriction commerceAddressRestriction) {

		return new CommerceAddressRestrictionWrapper(
			commerceAddressRestriction);
	}

}