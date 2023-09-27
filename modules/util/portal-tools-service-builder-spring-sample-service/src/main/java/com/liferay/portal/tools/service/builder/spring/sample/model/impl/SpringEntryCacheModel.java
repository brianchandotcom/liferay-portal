/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.spring.sample.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.tools.service.builder.spring.sample.model.SpringEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SpringEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SpringEntryCacheModel
	implements CacheModel<SpringEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SpringEntryCacheModel)) {
			return false;
		}

		SpringEntryCacheModel springEntryCacheModel =
			(SpringEntryCacheModel)object;

		if ((springEntryId == springEntryCacheModel.springEntryId) &&
			(mvccVersion == springEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, springEntryId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", springEntryId=");
		sb.append(springEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SpringEntry toEntityModel() {
		SpringEntryImpl springEntryImpl = new SpringEntryImpl();

		springEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			springEntryImpl.setUuid("");
		}
		else {
			springEntryImpl.setUuid(uuid);
		}

		springEntryImpl.setSpringEntryId(springEntryId);
		springEntryImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			springEntryImpl.setCreateDate(null);
		}
		else {
			springEntryImpl.setCreateDate(new Date(createDate));
		}

		springEntryImpl.resetOriginalValues();

		return springEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		springEntryId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(springEntryId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);
	}

	public long mvccVersion;
	public String uuid;
	public long springEntryId;
	public long companyId;
	public long createDate;

}