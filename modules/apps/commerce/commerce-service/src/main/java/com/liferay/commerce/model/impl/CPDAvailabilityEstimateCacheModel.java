/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model.impl;

import com.liferay.commerce.model.CPDAvailabilityEstimate;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CPDAvailabilityEstimate in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CPDAvailabilityEstimateCacheModel
	implements CacheModel<CPDAvailabilityEstimate>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CPDAvailabilityEstimateCacheModel)) {
			return false;
		}

		CPDAvailabilityEstimateCacheModel cpdAvailabilityEstimateCacheModel =
			(CPDAvailabilityEstimateCacheModel)object;

		if ((CPDAvailabilityEstimateId ==
				cpdAvailabilityEstimateCacheModel.CPDAvailabilityEstimateId) &&
			(mvccVersion == cpdAvailabilityEstimateCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, CPDAvailabilityEstimateId);

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
		StringBundler sb = new StringBundler(23);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", CPDAvailabilityEstimateId=");
		sb.append(CPDAvailabilityEstimateId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", commerceAvailabilityEstimateId=");
		sb.append(commerceAvailabilityEstimateId);
		sb.append(", CProductId=");
		sb.append(CProductId);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPDAvailabilityEstimate toEntityModel() {
		CPDAvailabilityEstimateImpl cpdAvailabilityEstimateImpl =
			new CPDAvailabilityEstimateImpl();

		cpdAvailabilityEstimateImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			cpdAvailabilityEstimateImpl.setUuid("");
		}
		else {
			cpdAvailabilityEstimateImpl.setUuid(uuid);
		}

		cpdAvailabilityEstimateImpl.setCPDAvailabilityEstimateId(
			CPDAvailabilityEstimateId);
		cpdAvailabilityEstimateImpl.setCompanyId(companyId);
		cpdAvailabilityEstimateImpl.setUserId(userId);

		if (userName == null) {
			cpdAvailabilityEstimateImpl.setUserName("");
		}
		else {
			cpdAvailabilityEstimateImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpdAvailabilityEstimateImpl.setCreateDate(null);
		}
		else {
			cpdAvailabilityEstimateImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpdAvailabilityEstimateImpl.setModifiedDate(null);
		}
		else {
			cpdAvailabilityEstimateImpl.setModifiedDate(new Date(modifiedDate));
		}

		cpdAvailabilityEstimateImpl.setCommerceAvailabilityEstimateId(
			commerceAvailabilityEstimateId);
		cpdAvailabilityEstimateImpl.setCProductId(CProductId);

		if (lastPublishDate == Long.MIN_VALUE) {
			cpdAvailabilityEstimateImpl.setLastPublishDate(null);
		}
		else {
			cpdAvailabilityEstimateImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		cpdAvailabilityEstimateImpl.resetOriginalValues();

		return cpdAvailabilityEstimateImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		CPDAvailabilityEstimateId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceAvailabilityEstimateId = objectInput.readLong();

		CProductId = objectInput.readLong();
		lastPublishDate = objectInput.readLong();
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

		objectOutput.writeLong(CPDAvailabilityEstimateId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(commerceAvailabilityEstimateId);

		objectOutput.writeLong(CProductId);
		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public String uuid;
	public long CPDAvailabilityEstimateId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceAvailabilityEstimateId;
	public long CProductId;
	public long lastPublishDate;

}