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

package com.liferay.portlet.social.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.social.model.SocialRequest;

import java.io.Serializable;

/**
 * The cache model class for representing SocialRequest in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see SocialRequest
 * @generated
 */
public class SocialRequestCacheModel implements CacheModel<SocialRequest>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", requestId=");
		sb.append(requestId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", type=");
		sb.append(type);
		sb.append(", extraData=");
		sb.append(extraData);
		sb.append(", receiverUserId=");
		sb.append(receiverUserId);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	public SocialRequest toEntityModel() {
		SocialRequestImpl socialRequestImpl = new SocialRequestImpl();

		if (uuid == null) {
			socialRequestImpl.setUuid(StringPool.BLANK);
		}
		else {
			socialRequestImpl.setUuid(uuid);
		}

		socialRequestImpl.setRequestId(requestId);
		socialRequestImpl.setGroupId(groupId);
		socialRequestImpl.setCompanyId(companyId);
		socialRequestImpl.setUserId(userId);
		socialRequestImpl.setCreateDate(createDate);
		socialRequestImpl.setModifiedDate(modifiedDate);
		socialRequestImpl.setClassNameId(classNameId);
		socialRequestImpl.setClassPK(classPK);
		socialRequestImpl.setType(type);

		if (extraData == null) {
			socialRequestImpl.setExtraData(StringPool.BLANK);
		}
		else {
			socialRequestImpl.setExtraData(extraData);
		}

		socialRequestImpl.setReceiverUserId(receiverUserId);
		socialRequestImpl.setStatus(status);

		socialRequestImpl.resetOriginalValues();

		return socialRequestImpl;
	}

	public String uuid;
	public long requestId;
	public long groupId;
	public long companyId;
	public long userId;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public int type;
	public String extraData;
	public long receiverUserId;
	public int status;
}