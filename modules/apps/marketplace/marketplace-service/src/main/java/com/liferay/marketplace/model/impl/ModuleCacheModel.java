/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.model.impl;

import com.liferay.marketplace.model.Module;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing Module in entity cache.
 *
 * @author Ryan Park
 * @generated
 */
public class ModuleCacheModel implements CacheModel<Module>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ModuleCacheModel)) {
			return false;
		}

		ModuleCacheModel moduleCacheModel = (ModuleCacheModel)object;

		if (moduleId == moduleCacheModel.moduleId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, moduleId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", moduleId=");
		sb.append(moduleId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", appId=");
		sb.append(appId);
		sb.append(", bundleSymbolicName=");
		sb.append(bundleSymbolicName);
		sb.append(", bundleVersion=");
		sb.append(bundleVersion);
		sb.append(", contextName=");
		sb.append(contextName);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Module toEntityModel() {
		ModuleImpl moduleImpl = new ModuleImpl();

		if (uuid == null) {
			moduleImpl.setUuid("");
		}
		else {
			moduleImpl.setUuid(uuid);
		}

		moduleImpl.setModuleId(moduleId);
		moduleImpl.setCompanyId(companyId);
		moduleImpl.setAppId(appId);

		if (bundleSymbolicName == null) {
			moduleImpl.setBundleSymbolicName("");
		}
		else {
			moduleImpl.setBundleSymbolicName(bundleSymbolicName);
		}

		if (bundleVersion == null) {
			moduleImpl.setBundleVersion("");
		}
		else {
			moduleImpl.setBundleVersion(bundleVersion);
		}

		if (contextName == null) {
			moduleImpl.setContextName("");
		}
		else {
			moduleImpl.setContextName(contextName);
		}

		moduleImpl.resetOriginalValues();

		return moduleImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		moduleId = objectInput.readLong();

		companyId = objectInput.readLong();

		appId = objectInput.readLong();
		bundleSymbolicName = objectInput.readUTF();
		bundleVersion = objectInput.readUTF();
		contextName = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(moduleId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(appId);

		if (bundleSymbolicName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(bundleSymbolicName);
		}

		if (bundleVersion == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(bundleVersion);
		}

		if (contextName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(contextName);
		}
	}

	public String uuid;
	public long moduleId;
	public long companyId;
	public long appId;
	public String bundleSymbolicName;
	public String bundleVersion;
	public String contextName;

}