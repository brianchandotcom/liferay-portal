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

package com.liferay.portlet.messageboards.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.messageboards.model.MBMailingList;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing MBMailingList in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see MBMailingList
 * @generated
 */
public class MBMailingListCacheModel implements CacheModel<MBMailingList>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(53);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", mailingListId=");
		sb.append(mailingListId);
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
		sb.append(", categoryId=");
		sb.append(categoryId);
		sb.append(", emailAddress=");
		sb.append(emailAddress);
		sb.append(", inProtocol=");
		sb.append(inProtocol);
		sb.append(", inServerName=");
		sb.append(inServerName);
		sb.append(", inServerPort=");
		sb.append(inServerPort);
		sb.append(", inUseSSL=");
		sb.append(inUseSSL);
		sb.append(", inUserName=");
		sb.append(inUserName);
		sb.append(", inPassword=");
		sb.append(inPassword);
		sb.append(", inReadInterval=");
		sb.append(inReadInterval);
		sb.append(", outEmailAddress=");
		sb.append(outEmailAddress);
		sb.append(", outCustom=");
		sb.append(outCustom);
		sb.append(", outServerName=");
		sb.append(outServerName);
		sb.append(", outServerPort=");
		sb.append(outServerPort);
		sb.append(", outUseSSL=");
		sb.append(outUseSSL);
		sb.append(", outUserName=");
		sb.append(outUserName);
		sb.append(", outPassword=");
		sb.append(outPassword);
		sb.append(", allowAnonymous=");
		sb.append(allowAnonymous);
		sb.append(", active=");
		sb.append(active);
		sb.append("}");

		return sb.toString();
	}

	public MBMailingList toEntityModel() {
		MBMailingListImpl mbMailingListImpl = new MBMailingListImpl();

		if (uuid == null) {
			mbMailingListImpl.setUuid(StringPool.BLANK);
		}
		else {
			mbMailingListImpl.setUuid(uuid);
		}

		mbMailingListImpl.setMailingListId(mailingListId);
		mbMailingListImpl.setGroupId(groupId);
		mbMailingListImpl.setCompanyId(companyId);
		mbMailingListImpl.setUserId(userId);

		if (userName == null) {
			mbMailingListImpl.setUserName(StringPool.BLANK);
		}
		else {
			mbMailingListImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			mbMailingListImpl.setCreateDate(null);
		}
		else {
			mbMailingListImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			mbMailingListImpl.setModifiedDate(null);
		}
		else {
			mbMailingListImpl.setModifiedDate(new Date(modifiedDate));
		}

		mbMailingListImpl.setCategoryId(categoryId);

		if (emailAddress == null) {
			mbMailingListImpl.setEmailAddress(StringPool.BLANK);
		}
		else {
			mbMailingListImpl.setEmailAddress(emailAddress);
		}

		if (inProtocol == null) {
			mbMailingListImpl.setInProtocol(StringPool.BLANK);
		}
		else {
			mbMailingListImpl.setInProtocol(inProtocol);
		}

		if (inServerName == null) {
			mbMailingListImpl.setInServerName(StringPool.BLANK);
		}
		else {
			mbMailingListImpl.setInServerName(inServerName);
		}

		mbMailingListImpl.setInServerPort(inServerPort);
		mbMailingListImpl.setInUseSSL(inUseSSL);

		if (inUserName == null) {
			mbMailingListImpl.setInUserName(StringPool.BLANK);
		}
		else {
			mbMailingListImpl.setInUserName(inUserName);
		}

		if (inPassword == null) {
			mbMailingListImpl.setInPassword(StringPool.BLANK);
		}
		else {
			mbMailingListImpl.setInPassword(inPassword);
		}

		mbMailingListImpl.setInReadInterval(inReadInterval);

		if (outEmailAddress == null) {
			mbMailingListImpl.setOutEmailAddress(StringPool.BLANK);
		}
		else {
			mbMailingListImpl.setOutEmailAddress(outEmailAddress);
		}

		mbMailingListImpl.setOutCustom(outCustom);

		if (outServerName == null) {
			mbMailingListImpl.setOutServerName(StringPool.BLANK);
		}
		else {
			mbMailingListImpl.setOutServerName(outServerName);
		}

		mbMailingListImpl.setOutServerPort(outServerPort);
		mbMailingListImpl.setOutUseSSL(outUseSSL);

		if (outUserName == null) {
			mbMailingListImpl.setOutUserName(StringPool.BLANK);
		}
		else {
			mbMailingListImpl.setOutUserName(outUserName);
		}

		if (outPassword == null) {
			mbMailingListImpl.setOutPassword(StringPool.BLANK);
		}
		else {
			mbMailingListImpl.setOutPassword(outPassword);
		}

		mbMailingListImpl.setAllowAnonymous(allowAnonymous);
		mbMailingListImpl.setActive(active);

		mbMailingListImpl.resetOriginalValues();

		return mbMailingListImpl;
	}

	public String uuid;
	public long mailingListId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long categoryId;
	public String emailAddress;
	public String inProtocol;
	public String inServerName;
	public int inServerPort;
	public boolean inUseSSL;
	public String inUserName;
	public String inPassword;
	public int inReadInterval;
	public String outEmailAddress;
	public boolean outCustom;
	public String outServerName;
	public int outServerPort;
	public boolean outUseSSL;
	public String outUserName;
	public String outPassword;
	public boolean allowAnonymous;
	public boolean active;
}