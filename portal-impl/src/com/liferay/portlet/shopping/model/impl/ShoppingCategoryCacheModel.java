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

package com.liferay.portlet.shopping.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.shopping.model.ShoppingCategory;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing ShoppingCategory in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingCategory
 * @generated
 */
public class ShoppingCategoryCacheModel implements CacheModel<ShoppingCategory>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{categoryId=");
		sb.append(categoryId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", parentCategoryId=");
		sb.append(parentCategoryId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append("}");

		return sb.toString();
	}

	public ShoppingCategory toEntityModel() {
		ShoppingCategoryImpl shoppingCategoryImpl = new ShoppingCategoryImpl();

		shoppingCategoryImpl.setCategoryId(categoryId);
		shoppingCategoryImpl.setGroupId(groupId);
		shoppingCategoryImpl.setCompanyId(companyId);
		shoppingCategoryImpl.setUserId(userId);

		if (userName == null) {
			shoppingCategoryImpl.setUserName(StringPool.BLANK);
		}
		else {
			shoppingCategoryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			shoppingCategoryImpl.setCreateDate(null);
		}
		else {
			shoppingCategoryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			shoppingCategoryImpl.setModifiedDate(null);
		}
		else {
			shoppingCategoryImpl.setModifiedDate(new Date(modifiedDate));
		}

		shoppingCategoryImpl.setParentCategoryId(parentCategoryId);

		if (name == null) {
			shoppingCategoryImpl.setName(StringPool.BLANK);
		}
		else {
			shoppingCategoryImpl.setName(name);
		}

		if (description == null) {
			shoppingCategoryImpl.setDescription(StringPool.BLANK);
		}
		else {
			shoppingCategoryImpl.setDescription(description);
		}

		shoppingCategoryImpl.resetOriginalValues();

		return shoppingCategoryImpl;
	}

	public long categoryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long parentCategoryId;
	public String name;
	public String description;
}