/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link PasswordPolicyRel}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PasswordPolicyRel
 * @generated
 */
public class PasswordPolicyRelWrapper
	extends BaseModelWrapper<PasswordPolicyRel>
	implements ModelWrapper<PasswordPolicyRel>, PasswordPolicyRel {

	public PasswordPolicyRelWrapper(PasswordPolicyRel passwordPolicyRel) {
		super(passwordPolicyRel);
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
	public PasswordPolicyRel cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the fully qualified class name of this password policy rel.
	 *
	 * @return the fully qualified class name of this password policy rel
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this password policy rel.
	 *
	 * @return the class name ID of this password policy rel
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this password policy rel.
	 *
	 * @return the class pk of this password policy rel
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this password policy rel.
	 *
	 * @return the company ID of this password policy rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the mvcc version of this password policy rel.
	 *
	 * @return the mvcc version of this password policy rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the password policy ID of this password policy rel.
	 *
	 * @return the password policy ID of this password policy rel
	 */
	@Override
	public long getPasswordPolicyId() {
		return model.getPasswordPolicyId();
	}

	/**
	 * Returns the password policy rel ID of this password policy rel.
	 *
	 * @return the password policy rel ID of this password policy rel
	 */
	@Override
	public long getPasswordPolicyRelId() {
		return model.getPasswordPolicyRelId();
	}

	/**
	 * Returns the primary key of this password policy rel.
	 *
	 * @return the primary key of this password policy rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
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
	 * Sets the class name ID of this password policy rel.
	 *
	 * @param classNameId the class name ID of this password policy rel
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this password policy rel.
	 *
	 * @param classPK the class pk of this password policy rel
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this password policy rel.
	 *
	 * @param companyId the company ID of this password policy rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the mvcc version of this password policy rel.
	 *
	 * @param mvccVersion the mvcc version of this password policy rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the password policy ID of this password policy rel.
	 *
	 * @param passwordPolicyId the password policy ID of this password policy rel
	 */
	@Override
	public void setPasswordPolicyId(long passwordPolicyId) {
		model.setPasswordPolicyId(passwordPolicyId);
	}

	/**
	 * Sets the password policy rel ID of this password policy rel.
	 *
	 * @param passwordPolicyRelId the password policy rel ID of this password policy rel
	 */
	@Override
	public void setPasswordPolicyRelId(long passwordPolicyRelId) {
		model.setPasswordPolicyRelId(passwordPolicyRelId);
	}

	/**
	 * Sets the primary key of this password policy rel.
	 *
	 * @param primaryKey the primary key of this password policy rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected PasswordPolicyRelWrapper wrap(
		PasswordPolicyRel passwordPolicyRel) {

		return new PasswordPolicyRelWrapper(passwordPolicyRel);
	}

}