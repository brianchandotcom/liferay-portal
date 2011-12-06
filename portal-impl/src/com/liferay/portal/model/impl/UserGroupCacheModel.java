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
import com.liferay.portal.model.UserGroup;

import java.io.Serializable;

/**
 * The cache model class for representing UserGroup in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see UserGroup
 * @generated
 */
public class UserGroupCacheModel implements CacheModel<UserGroup>, Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{userGroupId=");
		sb.append(userGroupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", parentUserGroupId=");
		sb.append(parentUserGroupId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", publicLayoutSetPrototypeId=");
		sb.append(publicLayoutSetPrototypeId);
		sb.append(", privateLayoutSetPrototypeId=");
		sb.append(privateLayoutSetPrototypeId);
		sb.append(", addedByLDAPImport=");
		sb.append(addedByLDAPImport);
		sb.append("}");

		return sb.toString();
	}

	public UserGroup toEntityModel() {
		UserGroupImpl userGroupImpl = new UserGroupImpl();

		userGroupImpl.setUserGroupId(userGroupId);
		userGroupImpl.setCompanyId(companyId);
		userGroupImpl.setParentUserGroupId(parentUserGroupId);

		if (name == null) {
			userGroupImpl.setName(StringPool.BLANK);
		}
		else {
			userGroupImpl.setName(name);
		}

		if (description == null) {
			userGroupImpl.setDescription(StringPool.BLANK);
		}
		else {
			userGroupImpl.setDescription(description);
		}

		userGroupImpl.setPublicLayoutSetPrototypeId(publicLayoutSetPrototypeId);
		userGroupImpl.setPrivateLayoutSetPrototypeId(privateLayoutSetPrototypeId);
		userGroupImpl.setAddedByLDAPImport(addedByLDAPImport);

		userGroupImpl.resetOriginalValues();

		return userGroupImpl;
	}

	public long userGroupId;
	public long companyId;
	public long parentUserGroupId;
	public String name;
	public String description;
	public long publicLayoutSetPrototypeId;
	public long privateLayoutSetPrototypeId;
	public boolean addedByLDAPImport;
}