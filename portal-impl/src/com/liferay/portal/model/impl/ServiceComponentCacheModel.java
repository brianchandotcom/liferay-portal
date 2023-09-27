/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.ServiceComponent;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing ServiceComponent in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ServiceComponentCacheModel
	implements CacheModel<ServiceComponent>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ServiceComponentCacheModel)) {
			return false;
		}

		ServiceComponentCacheModel serviceComponentCacheModel =
			(ServiceComponentCacheModel)object;

		if ((serviceComponentId ==
				serviceComponentCacheModel.serviceComponentId) &&
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
		StringBundler sb = new StringBundler(13);

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
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ServiceComponent toEntityModel() {
		ServiceComponentImpl serviceComponentImpl = new ServiceComponentImpl();

		serviceComponentImpl.setMvccVersion(mvccVersion);
		serviceComponentImpl.setServiceComponentId(serviceComponentId);

		if (buildNamespace == null) {
			serviceComponentImpl.setBuildNamespace("");
		}
		else {
			serviceComponentImpl.setBuildNamespace(buildNamespace);
		}

		serviceComponentImpl.setBuildNumber(buildNumber);
		serviceComponentImpl.setBuildDate(buildDate);

		if (data == null) {
			serviceComponentImpl.setData("");
		}
		else {
			serviceComponentImpl.setData(data);
		}

		serviceComponentImpl.resetOriginalValues();

		return serviceComponentImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		serviceComponentId = objectInput.readLong();
		buildNamespace = objectInput.readUTF();

		buildNumber = objectInput.readLong();

		buildDate = objectInput.readLong();
		data = (String)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(serviceComponentId);

		if (buildNamespace == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(buildNamespace);
		}

		objectOutput.writeLong(buildNumber);

		objectOutput.writeLong(buildDate);

		if (data == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(data);
		}
	}

	public long mvccVersion;
	public long serviceComponentId;
	public String buildNamespace;
	public long buildNumber;
	public long buildDate;
	public String data;

}