/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit.storage.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AuditEventPseudonymField}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AuditEventPseudonymField
 * @generated
 */
public class AuditEventPseudonymFieldWrapper
	extends BaseModelWrapper<AuditEventPseudonymField>
	implements AuditEventPseudonymField,
			   ModelWrapper<AuditEventPseudonymField> {

	public AuditEventPseudonymFieldWrapper(
		AuditEventPseudonymField auditEventPseudonymField) {

		super(auditEventPseudonymField);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"auditEventPseudonymFieldId", getAuditEventPseudonymFieldId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("contextName", getContextName());
		attributes.put("name", getName());
		attributes.put("value", getValue());
		attributes.put("valueHash", getValueHash());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long auditEventPseudonymFieldId = (Long)attributes.get(
			"auditEventPseudonymFieldId");

		if (auditEventPseudonymFieldId != null) {
			setAuditEventPseudonymFieldId(auditEventPseudonymFieldId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		String contextName = (String)attributes.get("contextName");

		if (contextName != null) {
			setContextName(contextName);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String value = (String)attributes.get("value");

		if (value != null) {
			setValue(value);
		}

		String valueHash = (String)attributes.get("valueHash");

		if (valueHash != null) {
			setValueHash(valueHash);
		}
	}

	@Override
	public AuditEventPseudonymField cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the audit event pseudonym field ID of this audit event pseudonym field.
	 *
	 * @return the audit event pseudonym field ID of this audit event pseudonym field
	 */
	@Override
	public long getAuditEventPseudonymFieldId() {
		return model.getAuditEventPseudonymFieldId();
	}

	/**
	 * Returns the company ID of this audit event pseudonym field.
	 *
	 * @return the company ID of this audit event pseudonym field
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the context name of this audit event pseudonym field.
	 *
	 * @return the context name of this audit event pseudonym field
	 */
	@Override
	public String getContextName() {
		return model.getContextName();
	}

	/**
	 * Returns the create date of this audit event pseudonym field.
	 *
	 * @return the create date of this audit event pseudonym field
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the name of this audit event pseudonym field.
	 *
	 * @return the name of this audit event pseudonym field
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this audit event pseudonym field.
	 *
	 * @return the primary key of this audit event pseudonym field
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the value of this audit event pseudonym field.
	 *
	 * @return the value of this audit event pseudonym field
	 */
	@Override
	public String getValue() {
		return model.getValue();
	}

	/**
	 * Returns the value hash of this audit event pseudonym field.
	 *
	 * @return the value hash of this audit event pseudonym field
	 */
	@Override
	public String getValueHash() {
		return model.getValueHash();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the audit event pseudonym field ID of this audit event pseudonym field.
	 *
	 * @param auditEventPseudonymFieldId the audit event pseudonym field ID of this audit event pseudonym field
	 */
	@Override
	public void setAuditEventPseudonymFieldId(long auditEventPseudonymFieldId) {
		model.setAuditEventPseudonymFieldId(auditEventPseudonymFieldId);
	}

	/**
	 * Sets the company ID of this audit event pseudonym field.
	 *
	 * @param companyId the company ID of this audit event pseudonym field
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the context name of this audit event pseudonym field.
	 *
	 * @param contextName the context name of this audit event pseudonym field
	 */
	@Override
	public void setContextName(String contextName) {
		model.setContextName(contextName);
	}

	/**
	 * Sets the create date of this audit event pseudonym field.
	 *
	 * @param createDate the create date of this audit event pseudonym field
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the name of this audit event pseudonym field.
	 *
	 * @param name the name of this audit event pseudonym field
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this audit event pseudonym field.
	 *
	 * @param primaryKey the primary key of this audit event pseudonym field
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the value of this audit event pseudonym field.
	 *
	 * @param value the value of this audit event pseudonym field
	 */
	@Override
	public void setValue(String value) {
		model.setValue(value);
	}

	/**
	 * Sets the value hash of this audit event pseudonym field.
	 *
	 * @param valueHash the value hash of this audit event pseudonym field
	 */
	@Override
	public void setValueHash(String valueHash) {
		model.setValueHash(valueHash);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected AuditEventPseudonymFieldWrapper wrap(
		AuditEventPseudonymField auditEventPseudonymField) {

		return new AuditEventPseudonymFieldWrapper(auditEventPseudonymField);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-558012593