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
import com.liferay.portal.model.Resource;

import java.io.Serializable;

/**
 * The cache model class for representing Resource in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Resource
 * @generated
 */
public class ResourceCacheModel implements CacheModel<Resource>, Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{resourceId=");
		sb.append(resourceId);
		sb.append(", codeId=");
		sb.append(codeId);
		sb.append(", primKey=");
		sb.append(primKey);
		sb.append("}");

		return sb.toString();
	}

	public Resource toEntityModel() {
		ResourceImpl resourceImpl = new ResourceImpl();

		resourceImpl.setResourceId(resourceId);
		resourceImpl.setCodeId(codeId);

		if (primKey == null) {
			resourceImpl.setPrimKey(StringPool.BLANK);
		}
		else {
			resourceImpl.setPrimKey(primKey);
		}

		resourceImpl.resetOriginalValues();

		return resourceImpl;
	}

	public long resourceId;
	public long codeId;
	public String primKey;
}