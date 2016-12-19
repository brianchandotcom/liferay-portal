/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link PasswordPolicyRel}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PasswordPolicyRel
 * @generated
 */
@ProviderType
public class PasswordPolicyRelWrapper implements PasswordPolicyRel,
	ModelWrapper<PasswordPolicyRel> {
	public PasswordPolicyRelWrapper(PasswordPolicyRel passwordPolicyRel) {
		_passwordPolicyRel = passwordPolicyRel;
	}

	@Override
	public Class<?> getModelClass() {
		return PasswordPolicyRel.class;
	}

	@Override
	public String getModelClassName() {
		return PasswordPolicyRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("passwordPolicyRelId", getPasswordPolicyRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("passwordPolicyId", getPasswordPolicyId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long passwordPolicyRelId = (Long)attributes.get("passwordPolicyRelId");

		if (passwordPolicyRelId != null) {
			setPasswordPolicyRelId(passwordPolicyRelId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long passwordPolicyId = (Long)attributes.get("passwordPolicyId");

		if (passwordPolicyId != null) {
			setPasswordPolicyId(passwordPolicyId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}
	}

	@Override
	public CacheModel<PasswordPolicyRel> toCacheModel() {
		return _passwordPolicyRel.toCacheModel();
	}

	@Override
	public PasswordPolicyRel toEscapedModel() {
		return new PasswordPolicyRelWrapper(_passwordPolicyRel.toEscapedModel());
	}

	@Override
	public PasswordPolicyRel toUnescapedModel() {
		return new PasswordPolicyRelWrapper(_passwordPolicyRel.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _passwordPolicyRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _passwordPolicyRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _passwordPolicyRel.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _passwordPolicyRel.getExpandoBridge();
	}

	@Override
	public int compareTo(PasswordPolicyRel passwordPolicyRel) {
		return _passwordPolicyRel.compareTo(passwordPolicyRel);
	}

	@Override
	public int hashCode() {
		return _passwordPolicyRel.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _passwordPolicyRel.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new PasswordPolicyRelWrapper((PasswordPolicyRel)_passwordPolicyRel.clone());
	}

	/**
	* Returns the fully qualified class name of this password policy rel.
	*
	* @return the fully qualified class name of this password policy rel
	*/
	@Override
	public java.lang.String getClassName() {
		return _passwordPolicyRel.getClassName();
	}

	@Override
	public java.lang.String toString() {
		return _passwordPolicyRel.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _passwordPolicyRel.toXmlString();
	}

	/**
	* Returns the class name ID of this password policy rel.
	*
	* @return the class name ID of this password policy rel
	*/
	@Override
	public long getClassNameId() {
		return _passwordPolicyRel.getClassNameId();
	}

	/**
	* Returns the class pk of this password policy rel.
	*
	* @return the class pk of this password policy rel
	*/
	@Override
	public long getClassPK() {
		return _passwordPolicyRel.getClassPK();
	}

	/**
	* Returns the company ID of this password policy rel.
	*
	* @return the company ID of this password policy rel
	*/
	@Override
	public long getCompanyId() {
		return _passwordPolicyRel.getCompanyId();
	}

	/**
	* Returns the mvcc version of this password policy rel.
	*
	* @return the mvcc version of this password policy rel
	*/
	@Override
	public long getMvccVersion() {
		return _passwordPolicyRel.getMvccVersion();
	}

	/**
	* Returns the password policy ID of this password policy rel.
	*
	* @return the password policy ID of this password policy rel
	*/
	@Override
	public long getPasswordPolicyId() {
		return _passwordPolicyRel.getPasswordPolicyId();
	}

	/**
	* Returns the password policy rel ID of this password policy rel.
	*
	* @return the password policy rel ID of this password policy rel
	*/
	@Override
	public long getPasswordPolicyRelId() {
		return _passwordPolicyRel.getPasswordPolicyRelId();
	}

	/**
	* Returns the primary key of this password policy rel.
	*
	* @return the primary key of this password policy rel
	*/
	@Override
	public long getPrimaryKey() {
		return _passwordPolicyRel.getPrimaryKey();
	}

	@Override
	public void persist() {
		_passwordPolicyRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_passwordPolicyRel.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_passwordPolicyRel.setClassName(className);
	}

	/**
	* Sets the class name ID of this password policy rel.
	*
	* @param classNameId the class name ID of this password policy rel
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_passwordPolicyRel.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this password policy rel.
	*
	* @param classPK the class pk of this password policy rel
	*/
	@Override
	public void setClassPK(long classPK) {
		_passwordPolicyRel.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this password policy rel.
	*
	* @param companyId the company ID of this password policy rel
	*/
	@Override
	public void setCompanyId(long companyId) {
		_passwordPolicyRel.setCompanyId(companyId);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_passwordPolicyRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_passwordPolicyRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_passwordPolicyRel.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the mvcc version of this password policy rel.
	*
	* @param mvccVersion the mvcc version of this password policy rel
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_passwordPolicyRel.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_passwordPolicyRel.setNew(n);
	}

	/**
	* Sets the password policy ID of this password policy rel.
	*
	* @param passwordPolicyId the password policy ID of this password policy rel
	*/
	@Override
	public void setPasswordPolicyId(long passwordPolicyId) {
		_passwordPolicyRel.setPasswordPolicyId(passwordPolicyId);
	}

	/**
	* Sets the password policy rel ID of this password policy rel.
	*
	* @param passwordPolicyRelId the password policy rel ID of this password policy rel
	*/
	@Override
	public void setPasswordPolicyRelId(long passwordPolicyRelId) {
		_passwordPolicyRel.setPasswordPolicyRelId(passwordPolicyRelId);
	}

	/**
	* Sets the primary key of this password policy rel.
	*
	* @param primaryKey the primary key of this password policy rel
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_passwordPolicyRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_passwordPolicyRel.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PasswordPolicyRelWrapper)) {
			return false;
		}

		PasswordPolicyRelWrapper passwordPolicyRelWrapper = (PasswordPolicyRelWrapper)obj;

		if (Objects.equals(_passwordPolicyRel,
					passwordPolicyRelWrapper._passwordPolicyRel)) {
			return true;
		}

		return false;
	}

	@Override
	public PasswordPolicyRel getWrappedModel() {
		return _passwordPolicyRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _passwordPolicyRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _passwordPolicyRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_passwordPolicyRel.resetOriginalValues();
	}

	private final PasswordPolicyRel _passwordPolicyRel;
}