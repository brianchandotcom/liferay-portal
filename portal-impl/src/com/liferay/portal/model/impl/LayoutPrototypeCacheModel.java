/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.model.LayoutPrototype;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing LayoutPrototype in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPrototype
 * @generated
 */
public class LayoutPrototypeCacheModel implements CacheModel<LayoutPrototype>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", layoutPrototypeId=");
		sb.append(layoutPrototypeId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", settings=");
		sb.append(settings);
		sb.append(", active=");
		sb.append(active);
		sb.append("}");

		return sb.toString();
	}

	public LayoutPrototype toEntityModel() {
		LayoutPrototypeImpl layoutPrototypeImpl = new LayoutPrototypeImpl();

		if (uuid == null) {
			layoutPrototypeImpl.setUuid(StringPool.BLANK);
		}
		else {
			layoutPrototypeImpl.setUuid(uuid);
		}

		layoutPrototypeImpl.setLayoutPrototypeId(layoutPrototypeId);
		layoutPrototypeImpl.setCompanyId(companyId);

		if (name == null) {
			layoutPrototypeImpl.setName(StringPool.BLANK);
		}
		else {
			layoutPrototypeImpl.setName(name);
		}

		if (description == null) {
			layoutPrototypeImpl.setDescription(StringPool.BLANK);
		}
		else {
			layoutPrototypeImpl.setDescription(description);
		}

		if (settings == null) {
			layoutPrototypeImpl.setSettings(StringPool.BLANK);
		}
		else {
			layoutPrototypeImpl.setSettings(settings);
		}

		layoutPrototypeImpl.setActive(active);

		layoutPrototypeImpl.resetOriginalValues();

		return layoutPrototypeImpl;
	}

	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {
		uuid = objectInput.readUTF();

		layoutPrototypeId = objectInput.readLong();

		companyId = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		settings = objectInput.readUTF();

		active = objectInput.readBoolean();
	}

	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(layoutPrototypeId);

		objectOutput.writeLong(companyId);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (settings == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(settings);
		}

		objectOutput.writeBoolean(active);
	}

	public String uuid;
	public long layoutPrototypeId;
	public long companyId;
	public String name;
	public String description;
	public String settings;
	public boolean active;
}