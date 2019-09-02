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

package com.liferay.app.builder.model.impl;

import com.liferay.app.builder.model.AppBuilderAppDeployment;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The cache model class for representing AppBuilderAppDeployment in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class AppBuilderAppDeploymentCacheModel
	implements CacheModel<AppBuilderAppDeployment>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AppBuilderAppDeploymentCacheModel)) {
			return false;
		}

		AppBuilderAppDeploymentCacheModel appBuilderAppDeploymentCacheModel =
			(AppBuilderAppDeploymentCacheModel)obj;

		if (appBuilderAppDeploymentId ==
				appBuilderAppDeploymentCacheModel.appBuilderAppDeploymentId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, appBuilderAppDeploymentId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{appBuilderAppDeploymentId=");
		sb.append(appBuilderAppDeploymentId);
		sb.append(", appBuilderAppId=");
		sb.append(appBuilderAppId);
		sb.append(", deploymentType=");
		sb.append(deploymentType);
		sb.append(", settings=");
		sb.append(settings);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AppBuilderAppDeployment toEntityModel() {
		AppBuilderAppDeploymentImpl appBuilderAppDeploymentImpl =
			new AppBuilderAppDeploymentImpl();

		appBuilderAppDeploymentImpl.setAppBuilderAppDeploymentId(
			appBuilderAppDeploymentId);
		appBuilderAppDeploymentImpl.setAppBuilderAppId(appBuilderAppId);

		if (deploymentType == null) {
			appBuilderAppDeploymentImpl.setDeploymentType("");
		}
		else {
			appBuilderAppDeploymentImpl.setDeploymentType(deploymentType);
		}

		if (settings == null) {
			appBuilderAppDeploymentImpl.setSettings("");
		}
		else {
			appBuilderAppDeploymentImpl.setSettings(settings);
		}

		appBuilderAppDeploymentImpl.resetOriginalValues();

		return appBuilderAppDeploymentImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		appBuilderAppDeploymentId = objectInput.readLong();

		appBuilderAppId = objectInput.readLong();
		deploymentType = objectInput.readUTF();
		settings = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(appBuilderAppDeploymentId);

		objectOutput.writeLong(appBuilderAppId);

		if (deploymentType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(deploymentType);
		}

		if (settings == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(settings);
		}
	}

	public long appBuilderAppDeploymentId;
	public long appBuilderAppId;
	public String deploymentType;
	public String settings;

}