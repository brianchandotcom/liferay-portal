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
import com.liferay.portal.model.ResourceCode;

import java.io.Serializable;

/**
 * The cache model class for representing ResourceCode in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see ResourceCode
 * @generated
 */
public class ResourceCodeCacheModel implements CacheModel<ResourceCode>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{codeId=");
		sb.append(codeId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", scope=");
		sb.append(scope);
		sb.append("}");

		return sb.toString();
	}

	public ResourceCode toEntityModel() {
		ResourceCodeImpl resourceCodeImpl = new ResourceCodeImpl();

		resourceCodeImpl.setCodeId(codeId);
		resourceCodeImpl.setCompanyId(companyId);

		if (name == null) {
			resourceCodeImpl.setName(StringPool.BLANK);
		}
		else {
			resourceCodeImpl.setName(name);
		}

		resourceCodeImpl.setScope(scope);

		resourceCodeImpl.resetOriginalValues();

		return resourceCodeImpl;
	}

	public long codeId;
	public long companyId;
	public String name;
	public int scope;
}