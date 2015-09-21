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
import com.liferay.portal.model.PluginSetting;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing PluginSetting in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see PluginSetting
 * @generated
 */
@ProviderType
public class PluginSettingCacheModel implements CacheModel<PluginSetting>,
	Externalizable, MVCCModel {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PluginSettingCacheModel)) {
			return false;
		}

		PluginSettingCacheModel pluginSettingCacheModel = (PluginSettingCacheModel)obj;

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
		sb.append(", pluginId=");
		sb.append(pluginId);
		sb.append(", pluginType=");
		sb.append(pluginType);
		sb.append(", roles=");
		sb.append(roles);
		sb.append(", active=");
		sb.append(active);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public PluginSetting toEntityModel() {
		PluginSettingImpl pluginSettingImpl = new PluginSettingImpl();

		pluginSettingImpl.setMvccVersion(mvccVersion);
		pluginSettingImpl.setPluginSettingId(pluginSettingId);

		if (pluginId == null) {
			pluginSettingImpl.setPluginId(StringPool.BLANK);
		}
		else {
			pluginSettingImpl.setPluginId(pluginId);
		}

		if (pluginType == null) {
			pluginSettingImpl.setPluginType(StringPool.BLANK);
		}
		else {
			pluginSettingImpl.setPluginType(pluginType);
		}

		if (roles == null) {
			pluginSettingImpl.setRoles(StringPool.BLANK);
		}
		else {
			pluginSettingImpl.setRoles(roles);
		}

		pluginSettingImpl.setActive(active);
		pluginSettingImpl.setCompanyId(companyId);

		pluginSettingImpl.resetOriginalValues();

		return pluginSettingImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		pluginSettingId = objectInput.readLong();
		pluginId = objectInput.readUTF();
		pluginType = objectInput.readUTF();
		roles = objectInput.readUTF();
		active = objectInput.readBoolean();
		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(mvccVersion);
		objectOutput.writeLong(pluginSettingId);

		if (pluginId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(pluginId);
		}

		if (pluginType == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(pluginType);
		}

		if (roles == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(roles);
		}

		objectOutput.writeBoolean(active);
		objectOutput.writeLong(companyId);
	}

	public long mvccVersion;
	public long pluginSettingId;
	public String pluginId;
	public String pluginType;
	public String roles;
	public boolean active;
	public long companyId;
}