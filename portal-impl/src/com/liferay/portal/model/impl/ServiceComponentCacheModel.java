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

package com.liferay.portal.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.ServiceComponent;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing ServiceComponent in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see ServiceComponent
 * @generated
 */
@ProviderType
public class ServiceComponentCacheModel implements CacheModel<ServiceComponent>,
	Externalizable, MVCCModel {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ServiceComponentCacheModel)) {
			return false;
		}

		ServiceComponentCacheModel serviceComponentCacheModel = (ServiceComponentCacheModel)obj;

		if ((serviceComponentId == serviceComponentCacheModel.serviceComponentId) &&
				(mvccVersion == serviceComponentCacheModel.mvccVersion)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, serviceComponentId);

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
		StringBundler sb = new StringBundler(15);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", serviceComponentId=");
		sb.append(serviceComponentId);
		sb.append(", buildNamespace=");
		sb.append(buildNamespace);
		sb.append(", buildNumber=");
		sb.append(buildNumber);
		sb.append(", buildDate=");
		sb.append(buildDate);
		sb.append(", data=");
		sb.append(data);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ServiceComponent toEntityModel() {
		ServiceComponentImpl serviceComponentImpl = new ServiceComponentImpl();

		serviceComponentImpl.setMvccVersion(mvccVersion);
		serviceComponentImpl.setServiceComponentId(serviceComponentId);

		if (buildNamespace == null) {
			serviceComponentImpl.setBuildNamespace(StringPool.BLANK);
		}
		else {
			serviceComponentImpl.setBuildNamespace(buildNamespace);
		}

		serviceComponentImpl.setBuildNumber(buildNumber);
		serviceComponentImpl.setBuildDate(buildDate);

		if (data == null) {
			serviceComponentImpl.setData(StringPool.BLANK);
		}
		else {
			serviceComponentImpl.setData(data);
		}

		serviceComponentImpl.setCompanyId(companyId);

		serviceComponentImpl.resetOriginalValues();

		return serviceComponentImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		serviceComponentId = objectInput.readLong();
		buildNamespace = objectInput.readUTF();
		buildNumber = objectInput.readLong();
		buildDate = objectInput.readLong();
		data = objectInput.readUTF();
		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(mvccVersion);
		objectOutput.writeLong(serviceComponentId);

		if (buildNamespace == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(buildNamespace);
		}

		objectOutput.writeLong(buildNumber);
		objectOutput.writeLong(buildDate);

		if (data == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(data);
		}

		objectOutput.writeLong(companyId);
	}

	public long mvccVersion;
	public long serviceComponentId;
	public String buildNamespace;
	public long buildNumber;
	public long buildDate;
	public String data;
	public long companyId;
}