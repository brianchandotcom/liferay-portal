/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.data.integration.model.impl;

import com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcessLog;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceDataIntegrationProcessLog in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceDataIntegrationProcessLogCacheModel
	implements CacheModel<CommerceDataIntegrationProcessLog>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceDataIntegrationProcessLogCacheModel)) {
			return false;
		}

		CommerceDataIntegrationProcessLogCacheModel
			commerceDataIntegrationProcessLogCacheModel =
				(CommerceDataIntegrationProcessLogCacheModel)object;

		if (commerceDataIntegrationProcessLogId ==
				commerceDataIntegrationProcessLogCacheModel.
					commerceDataIntegrationProcessLogId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceDataIntegrationProcessLogId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{commerceDataIntegrationProcessLogId=");
		sb.append(commerceDataIntegrationProcessLogId);
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
		sb.append(", CDataIntegrationProcessId=");
		sb.append(CDataIntegrationProcessId);
		sb.append(", error=");
		sb.append(error);
		sb.append(", output=");
		sb.append(output);
		sb.append(", startDate=");
		sb.append(startDate);
		sb.append(", endDate=");
		sb.append(endDate);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceDataIntegrationProcessLog toEntityModel() {
		CommerceDataIntegrationProcessLogImpl
			commerceDataIntegrationProcessLogImpl =
				new CommerceDataIntegrationProcessLogImpl();

		commerceDataIntegrationProcessLogImpl.
			setCommerceDataIntegrationProcessLogId(
				commerceDataIntegrationProcessLogId);
		commerceDataIntegrationProcessLogImpl.setCompanyId(companyId);
		commerceDataIntegrationProcessLogImpl.setUserId(userId);

		if (userName == null) {
			commerceDataIntegrationProcessLogImpl.setUserName("");
		}
		else {
			commerceDataIntegrationProcessLogImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceDataIntegrationProcessLogImpl.setCreateDate(null);
		}
		else {
			commerceDataIntegrationProcessLogImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceDataIntegrationProcessLogImpl.setModifiedDate(null);
		}
		else {
			commerceDataIntegrationProcessLogImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commerceDataIntegrationProcessLogImpl.setCDataIntegrationProcessId(
			CDataIntegrationProcessId);

		if (error == null) {
			commerceDataIntegrationProcessLogImpl.setError("");
		}
		else {
			commerceDataIntegrationProcessLogImpl.setError(error);
		}

		if (output == null) {
			commerceDataIntegrationProcessLogImpl.setOutput("");
		}
		else {
			commerceDataIntegrationProcessLogImpl.setOutput(output);
		}

		if (startDate == Long.MIN_VALUE) {
			commerceDataIntegrationProcessLogImpl.setStartDate(null);
		}
		else {
			commerceDataIntegrationProcessLogImpl.setStartDate(
				new Date(startDate));
		}

		if (endDate == Long.MIN_VALUE) {
			commerceDataIntegrationProcessLogImpl.setEndDate(null);
		}
		else {
			commerceDataIntegrationProcessLogImpl.setEndDate(new Date(endDate));
		}

		commerceDataIntegrationProcessLogImpl.setStatus(status);

		commerceDataIntegrationProcessLogImpl.resetOriginalValues();

		return commerceDataIntegrationProcessLogImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		commerceDataIntegrationProcessLogId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CDataIntegrationProcessId = objectInput.readLong();
		error = (String)objectInput.readObject();
		output = (String)objectInput.readObject();
		startDate = objectInput.readLong();
		endDate = objectInput.readLong();

		status = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(commerceDataIntegrationProcessLogId);

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

		objectOutput.writeLong(CDataIntegrationProcessId);

		if (error == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(error);
		}

		if (output == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(output);
		}

		objectOutput.writeLong(startDate);
		objectOutput.writeLong(endDate);

		objectOutput.writeInt(status);
	}

	public long commerceDataIntegrationProcessLogId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CDataIntegrationProcessId;
	public String error;
	public String output;
	public long startDate;
	public long endDate;
	public int status;

}