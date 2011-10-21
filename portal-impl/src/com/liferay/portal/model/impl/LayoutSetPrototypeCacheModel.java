/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.model.LayoutSetPrototype;

import java.io.Serializable;

/**
 * The cache model class for representing LayoutSetPrototype in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetPrototype
 * @generated
 */
public class LayoutSetPrototypeCacheModel implements CacheModel<LayoutSetPrototype>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", layoutSetPrototypeId=");
		sb.append(layoutSetPrototypeId);
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

	public LayoutSetPrototype toEntityModel() {
		LayoutSetPrototypeImpl layoutSetPrototypeImpl = new LayoutSetPrototypeImpl();

		if (uuid == null) {
			layoutSetPrototypeImpl.setUuid(StringPool.BLANK);
		}
		else {
			layoutSetPrototypeImpl.setUuid(uuid);
		}

		layoutSetPrototypeImpl.setLayoutSetPrototypeId(layoutSetPrototypeId);
		layoutSetPrototypeImpl.setCompanyId(companyId);

		if (name == null) {
			layoutSetPrototypeImpl.setName(StringPool.BLANK);
		}
		else {
			layoutSetPrototypeImpl.setName(name);
		}

		if (description == null) {
			layoutSetPrototypeImpl.setDescription(StringPool.BLANK);
		}
		else {
			layoutSetPrototypeImpl.setDescription(description);
		}

		if (settings == null) {
			layoutSetPrototypeImpl.setSettings(StringPool.BLANK);
		}
		else {
			layoutSetPrototypeImpl.setSettings(settings);
		}

		layoutSetPrototypeImpl.setActive(active);

		layoutSetPrototypeImpl.resetOriginalValues();

		return layoutSetPrototypeImpl;
	}

	public String uuid;
	public long layoutSetPrototypeId;
	public long companyId;
	public String name;
	public String description;
	public String settings;
	public boolean active;
}