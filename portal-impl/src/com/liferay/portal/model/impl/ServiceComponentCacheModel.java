/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
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
public class ServiceComponentCacheModel implements CacheModel<ServiceComponent>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{serviceComponentId=");
		sb.append(serviceComponentId);
		sb.append(", buildNamespace=");
		sb.append(buildNamespace);
		sb.append(", buildNumber=");
		sb.append(buildNumber);
		sb.append(", buildDate=");
		sb.append(buildDate);
		sb.append(", data=");
		sb.append(data);
		sb.append(", mvccVersion=");
		sb.append(mvccVersion);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ServiceComponent toEntityModel() {
		ServiceComponentImpl serviceComponentImpl = new ServiceComponentImpl();

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

		serviceComponentImpl.setMvccVersion(mvccVersion);

		serviceComponentImpl.resetOriginalValues();

		return serviceComponentImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		serviceComponentId = objectInput.readLong();
		buildNamespace = objectInput.readUTF();
		buildNumber = objectInput.readLong();
		buildDate = objectInput.readLong();
		data = objectInput.readUTF();
		mvccVersion = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
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

		objectOutput.writeLong(mvccVersion);
	}

	public long serviceComponentId;
	public String buildNamespace;
	public long buildNumber;
	public long buildDate;
	public String data;
	public long mvccVersion;
}