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

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.PluginSetting;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing PluginSetting in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PluginSettingCacheModel
	implements CacheModel<PluginSetting>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PluginSettingCacheModel)) {
			return false;
		}

		PluginSettingCacheModel pluginSettingCacheModel =
			(PluginSettingCacheModel)obj;

		if ((pluginSettingId == pluginSettingCacheModel.pluginSettingId) &&
			(mvccVersion == pluginSettingCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, pluginSettingId);

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
		sb.append(", pluginSettingId=");
		sb.append(pluginSettingId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", pluginId=");
		sb.append(pluginId);
		sb.append(", pluginType=");
		sb.append(pluginType);
		sb.append(", roles=");
		sb.append(roles);
		sb.append(", active=");
		sb.append(active);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public PluginSetting toEntityModel() {
		PluginSettingImpl pluginSettingImpl = new PluginSettingImpl();

		pluginSettingImpl.setMvccVersion(mvccVersion);
		pluginSettingImpl.setPluginSettingId(pluginSettingId);
		pluginSettingImpl.setCompanyId(companyId);

		if (pluginId == null) {
			pluginSettingImpl.setPluginId("");
		}
		else {
			pluginSettingImpl.setPluginId(pluginId);
		}

		if (pluginType == null) {
			pluginSettingImpl.setPluginType("");
		}
		else {
			pluginSettingImpl.setPluginType(pluginType);
		}

		if (roles == null) {
			pluginSettingImpl.setRoles("");
		}
		else {
			pluginSettingImpl.setRoles(roles);
		}

		pluginSettingImpl.setActive(active);

		pluginSettingImpl.resetOriginalValues();

		return pluginSettingImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		pluginSettingId = objectInput.readLong();

		companyId = objectInput.readLong();
		pluginId = objectInput.readUTF();
		pluginType = objectInput.readUTF();
		roles = objectInput.readUTF();

		active = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(pluginSettingId);

		objectOutput.writeLong(companyId);

		if (pluginId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(pluginId);
		}

		if (pluginType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(pluginType);
		}

		if (roles == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(roles);
		}

		objectOutput.writeBoolean(active);
	}

	public long mvccVersion;
	public long pluginSettingId;
	public long companyId;
	public String pluginId;
	public String pluginType;
	public String roles;
	public boolean active;

}