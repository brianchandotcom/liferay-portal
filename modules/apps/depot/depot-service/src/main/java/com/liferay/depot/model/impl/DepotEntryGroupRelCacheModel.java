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

package com.liferay.depot.model.impl;

import com.liferay.depot.model.DepotEntryGroupRel;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing DepotEntryGroupRel in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DepotEntryGroupRelCacheModel
	implements CacheModel<DepotEntryGroupRel>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DepotEntryGroupRelCacheModel)) {
			return false;
		}

		DepotEntryGroupRelCacheModel depotEntryGroupRelCacheModel =
			(DepotEntryGroupRelCacheModel)obj;

		if ((depotEntryGroupRelId ==
				depotEntryGroupRelCacheModel.depotEntryGroupRelId) &&
			(mvccVersion == depotEntryGroupRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, depotEntryGroupRelId);

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
		sb.append(", depotEntryGroupRelId=");
		sb.append(depotEntryGroupRelId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", depotEntryId=");
		sb.append(depotEntryId);
		sb.append(", toGroupId=");
		sb.append(toGroupId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DepotEntryGroupRel toEntityModel() {
		DepotEntryGroupRelImpl depotEntryGroupRelImpl =
			new DepotEntryGroupRelImpl();

		depotEntryGroupRelImpl.setMvccVersion(mvccVersion);
		depotEntryGroupRelImpl.setDepotEntryGroupRelId(depotEntryGroupRelId);
		depotEntryGroupRelImpl.setCompanyId(companyId);
		depotEntryGroupRelImpl.setDepotEntryId(depotEntryId);
		depotEntryGroupRelImpl.setToGroupId(toGroupId);

		depotEntryGroupRelImpl.resetOriginalValues();

		return depotEntryGroupRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		depotEntryGroupRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		depotEntryId = objectInput.readLong();

		toGroupId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(depotEntryGroupRelId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(depotEntryId);

		objectOutput.writeLong(toGroupId);
	}

	public long mvccVersion;
	public long depotEntryGroupRelId;
	public long companyId;
	public long depotEntryId;
	public long toGroupId;

}