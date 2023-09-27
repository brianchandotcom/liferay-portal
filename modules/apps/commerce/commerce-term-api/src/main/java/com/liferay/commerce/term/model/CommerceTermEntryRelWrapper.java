/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.term.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceTermEntryRel}.
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceTermEntryRel
 * @generated
 */
public class CommerceTermEntryRelWrapper
	extends BaseModelWrapper<CommerceTermEntryRel>
	implements CommerceTermEntryRel, ModelWrapper<CommerceTermEntryRel> {

	public CommerceTermEntryRelWrapper(
		CommerceTermEntryRel commerceTermEntryRel) {

		super(commerceTermEntryRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("commerceTermEntryRelId", getCommerceTermEntryRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("commerceTermEntryId", getCommerceTermEntryId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long commerceTermEntryRelId = (Long)attributes.get(
			"commerceTermEntryRelId");

		if (commerceTermEntryRelId != null) {
			setCommerceTermEntryRelId(commerceTermEntryRelId);
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

		Long commerceTermEntryId = (Long)attributes.get("commerceTermEntryId");

		if (commerceTermEntryId != null) {
			setCommerceTermEntryId(commerceTermEntryId);
		}
	}

	@Override
	public CommerceTermEntryRel cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the fully qualified class name of this commerce term entry rel.
	 *
	 * @return the fully qualified class name of this commerce term entry rel
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this commerce term entry rel.
	 *
	 * @return the class name ID of this commerce term entry rel
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this commerce term entry rel.
	 *
	 * @return the class pk of this commerce term entry rel
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the commerce term entry ID of this commerce term entry rel.
	 *
	 * @return the commerce term entry ID of this commerce term entry rel
	 */
	@Override
	public long getCommerceTermEntryId() {
		return model.getCommerceTermEntryId();
	}

	/**
	 * Returns the commerce term entry rel ID of this commerce term entry rel.
	 *
	 * @return the commerce term entry rel ID of this commerce term entry rel
	 */
	@Override
	public long getCommerceTermEntryRelId() {
		return model.getCommerceTermEntryRelId();
	}

	/**
	 * Returns the company ID of this commerce term entry rel.
	 *
	 * @return the company ID of this commerce term entry rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce term entry rel.
	 *
	 * @return the create date of this commerce term entry rel
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this commerce term entry rel.
	 *
	 * @return the modified date of this commerce term entry rel
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce term entry rel.
	 *
	 * @return the mvcc version of this commerce term entry rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce term entry rel.
	 *
	 * @return the primary key of this commerce term entry rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this commerce term entry rel.
	 *
	 * @return the user ID of this commerce term entry rel
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce term entry rel.
	 *
	 * @return the user name of this commerce term entry rel
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce term entry rel.
	 *
	 * @return the user uuid of this commerce term entry rel
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
	 * Sets the class name ID of this commerce term entry rel.
	 *
	 * @param classNameId the class name ID of this commerce term entry rel
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this commerce term entry rel.
	 *
	 * @param classPK the class pk of this commerce term entry rel
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the commerce term entry ID of this commerce term entry rel.
	 *
	 * @param commerceTermEntryId the commerce term entry ID of this commerce term entry rel
	 */
	@Override
	public void setCommerceTermEntryId(long commerceTermEntryId) {
		model.setCommerceTermEntryId(commerceTermEntryId);
	}

	/**
	 * Sets the commerce term entry rel ID of this commerce term entry rel.
	 *
	 * @param commerceTermEntryRelId the commerce term entry rel ID of this commerce term entry rel
	 */
	@Override
	public void setCommerceTermEntryRelId(long commerceTermEntryRelId) {
		model.setCommerceTermEntryRelId(commerceTermEntryRelId);
	}

	/**
	 * Sets the company ID of this commerce term entry rel.
	 *
	 * @param companyId the company ID of this commerce term entry rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce term entry rel.
	 *
	 * @param createDate the create date of this commerce term entry rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this commerce term entry rel.
	 *
	 * @param modifiedDate the modified date of this commerce term entry rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce term entry rel.
	 *
	 * @param mvccVersion the mvcc version of this commerce term entry rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce term entry rel.
	 *
	 * @param primaryKey the primary key of this commerce term entry rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this commerce term entry rel.
	 *
	 * @param userId the user ID of this commerce term entry rel
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce term entry rel.
	 *
	 * @param userName the user name of this commerce term entry rel
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce term entry rel.
	 *
	 * @param userUuid the user uuid of this commerce term entry rel
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
	protected CommerceTermEntryRelWrapper wrap(
		CommerceTermEntryRel commerceTermEntryRel) {

		return new CommerceTermEntryRelWrapper(commerceTermEntryRel);
	}

}