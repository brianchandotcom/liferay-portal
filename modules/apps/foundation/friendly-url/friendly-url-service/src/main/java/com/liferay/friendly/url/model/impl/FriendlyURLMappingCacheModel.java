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

package com.liferay.friendly.url.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.friendly.url.model.FriendlyURLMapping;
import com.liferay.friendly.url.service.persistence.FriendlyURLMappingPK;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing FriendlyURLMapping in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLMapping
 * @generated
 */
@ProviderType
public class FriendlyURLMappingCacheModel implements CacheModel<FriendlyURLMapping>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof FriendlyURLMappingCacheModel)) {
			return false;
		}

		FriendlyURLMappingCacheModel friendlyURLMappingCacheModel = (FriendlyURLMappingCacheModel)obj;

		if (friendlyURLMappingPK.equals(
					friendlyURLMappingCacheModel.friendlyURLMappingPK)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, friendlyURLMappingPK);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", friendlyURLId=");
		sb.append(friendlyURLId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public FriendlyURLMapping toEntityModel() {
		FriendlyURLMappingImpl friendlyURLMappingImpl = new FriendlyURLMappingImpl();

		friendlyURLMappingImpl.setClassNameId(classNameId);
		friendlyURLMappingImpl.setClassPK(classPK);
		friendlyURLMappingImpl.setFriendlyURLId(friendlyURLId);

		friendlyURLMappingImpl.resetOriginalValues();

		return friendlyURLMappingImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		friendlyURLId = objectInput.readLong();

		friendlyURLMappingPK = new FriendlyURLMappingPK(classNameId, classPK);
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeLong(friendlyURLId);
	}

	public long classNameId;
	public long classPK;
	public long friendlyURLId;
	public transient FriendlyURLMappingPK friendlyURLMappingPK;
}