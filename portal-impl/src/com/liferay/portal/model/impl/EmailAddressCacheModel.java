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
import com.liferay.portal.model.EmailAddress;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing EmailAddress in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see EmailAddress
 * @generated
 */
public class EmailAddressCacheModel implements CacheModel<EmailAddress>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{emailAddressId=");
		sb.append(emailAddressId);
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
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", address=");
		sb.append(address);
		sb.append(", typeId=");
		sb.append(typeId);
		sb.append(", primary=");
		sb.append(primary);
		sb.append("}");

		return sb.toString();
	}

	public EmailAddress toEntityModel() {
		EmailAddressImpl emailAddressImpl = new EmailAddressImpl();

		emailAddressImpl.setEmailAddressId(emailAddressId);
		emailAddressImpl.setCompanyId(companyId);
		emailAddressImpl.setUserId(userId);

		if (userName == null) {
			emailAddressImpl.setUserName(StringPool.BLANK);
		}
		else {
			emailAddressImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			emailAddressImpl.setCreateDate(null);
		}
		else {
			emailAddressImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			emailAddressImpl.setModifiedDate(null);
		}
		else {
			emailAddressImpl.setModifiedDate(new Date(modifiedDate));
		}

		emailAddressImpl.setClassNameId(classNameId);
		emailAddressImpl.setClassPK(classPK);

		if (address == null) {
			emailAddressImpl.setAddress(StringPool.BLANK);
		}
		else {
			emailAddressImpl.setAddress(address);
		}

		emailAddressImpl.setTypeId(typeId);
		emailAddressImpl.setPrimary(primary);

		emailAddressImpl.resetOriginalValues();

		return emailAddressImpl;
	}

	public long emailAddressId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public String address;
	public int typeId;
	public boolean primary;
}