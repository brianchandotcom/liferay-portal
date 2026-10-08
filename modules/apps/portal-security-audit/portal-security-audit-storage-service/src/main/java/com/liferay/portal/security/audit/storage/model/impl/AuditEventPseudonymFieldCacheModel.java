/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit.storage.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.security.audit.storage.model.AuditEventPseudonymField;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing AuditEventPseudonymField in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AuditEventPseudonymFieldCacheModel
	implements CacheModel<AuditEventPseudonymField>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AuditEventPseudonymFieldCacheModel)) {
			return false;
		}

		AuditEventPseudonymFieldCacheModel auditEventPseudonymFieldCacheModel =
			(AuditEventPseudonymFieldCacheModel)object;

		if (auditEventPseudonymFieldId ==
				auditEventPseudonymFieldCacheModel.auditEventPseudonymFieldId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, auditEventPseudonymFieldId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{auditEventPseudonymFieldId=");
		sb.append(auditEventPseudonymFieldId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", contextName=");
		sb.append(contextName);
		sb.append(", name=");
		sb.append(name);
		sb.append(", value=");
		sb.append(value);
		sb.append(", valueHash=");
		sb.append(valueHash);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AuditEventPseudonymField toEntityModel() {
		AuditEventPseudonymFieldImpl auditEventPseudonymFieldImpl =
			new AuditEventPseudonymFieldImpl();

		auditEventPseudonymFieldImpl.setAuditEventPseudonymFieldId(
			auditEventPseudonymFieldId);
		auditEventPseudonymFieldImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			auditEventPseudonymFieldImpl.setCreateDate(null);
		}
		else {
			auditEventPseudonymFieldImpl.setCreateDate(new Date(createDate));
		}

		if (contextName == null) {
			auditEventPseudonymFieldImpl.setContextName("");
		}
		else {
			auditEventPseudonymFieldImpl.setContextName(contextName);
		}

		if (name == null) {
			auditEventPseudonymFieldImpl.setName("");
		}
		else {
			auditEventPseudonymFieldImpl.setName(name);
		}

		if (value == null) {
			auditEventPseudonymFieldImpl.setValue("");
		}
		else {
			auditEventPseudonymFieldImpl.setValue(value);
		}

		if (valueHash == null) {
			auditEventPseudonymFieldImpl.setValueHash("");
		}
		else {
			auditEventPseudonymFieldImpl.setValueHash(valueHash);
		}

		auditEventPseudonymFieldImpl.resetOriginalValues();

		return auditEventPseudonymFieldImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		auditEventPseudonymFieldId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		contextName = objectInput.readUTF();
		name = objectInput.readUTF();
		value = objectInput.readUTF();
		valueHash = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(auditEventPseudonymFieldId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);

		if (contextName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(contextName);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (value == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(value);
		}

		if (valueHash == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(valueHash);
		}
	}

	public long auditEventPseudonymFieldId;
	public long companyId;
	public long createDate;
	public String contextName;
	public String name;
	public String value;
	public String valueHash;

}
// LIFERAY-SERVICE-BUILDER-HASH:107230318