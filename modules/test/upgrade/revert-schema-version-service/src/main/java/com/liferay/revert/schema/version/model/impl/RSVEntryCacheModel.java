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

package com.liferay.revert.schema.version.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.revert.schema.version.model.RSVEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing RSVEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RSVEntryCacheModel
	implements CacheModel<RSVEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof RSVEntryCacheModel)) {
			return false;
		}

		RSVEntryCacheModel rsvEntryCacheModel = (RSVEntryCacheModel)obj;

		if ((rsvEntryId == rsvEntryCacheModel.rsvEntryId) &&
			(mvccVersion == rsvEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, rsvEntryId);

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
		StringBundler sb = new StringBundler(7);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", rsvEntryId=");
		sb.append(rsvEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public RSVEntry toEntityModel() {
		RSVEntryImpl rsvEntryImpl = new RSVEntryImpl();

		rsvEntryImpl.setMvccVersion(mvccVersion);
		rsvEntryImpl.setRsvEntryId(rsvEntryId);
		rsvEntryImpl.setCompanyId(companyId);

		rsvEntryImpl.resetOriginalValues();

		return rsvEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		rsvEntryId = objectInput.readLong();

		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(rsvEntryId);

		objectOutput.writeLong(companyId);
	}

	public long mvccVersion;
	public long rsvEntryId;
	public long companyId;

}