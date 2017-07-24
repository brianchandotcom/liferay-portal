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

import com.liferay.friendly.url.model.FriendlyURLEntryMapping;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryMappingPK;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing FriendlyURLEntryMapping in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLEntryMapping
 * @generated
 */
@ProviderType
public class FriendlyURLEntryMappingCacheModel implements CacheModel<FriendlyURLEntryMapping>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof FriendlyURLEntryMappingCacheModel)) {
			return false;
		}

		FriendlyURLEntryMappingCacheModel friendlyURLEntryMappingCacheModel = (FriendlyURLEntryMappingCacheModel)obj;

		if (friendlyURLEntryMappingPK.equals(
					friendlyURLEntryMappingCacheModel.friendlyURLEntryMappingPK)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, friendlyURLEntryMappingPK);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", friendlyURLEntryId=");
		sb.append(friendlyURLEntryId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public FriendlyURLEntryMapping toEntityModel() {
		FriendlyURLEntryMappingImpl friendlyURLEntryMappingImpl = new FriendlyURLEntryMappingImpl();

		friendlyURLEntryMappingImpl.setClassNameId(classNameId);
		friendlyURLEntryMappingImpl.setClassPK(classPK);
		friendlyURLEntryMappingImpl.setFriendlyURLEntryId(friendlyURLEntryId);

		friendlyURLEntryMappingImpl.resetOriginalValues();

		return friendlyURLEntryMappingImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		friendlyURLEntryId = objectInput.readLong();

		friendlyURLEntryMappingPK = new FriendlyURLEntryMappingPK(classNameId,
				classPK);
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeLong(friendlyURLEntryId);
	}

	public long classNameId;
	public long classPK;
	public long friendlyURLEntryId;
	public transient FriendlyURLEntryMappingPK friendlyURLEntryMappingPK;
}