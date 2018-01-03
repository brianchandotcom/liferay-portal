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

package com.liferay.fragment.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.fragment.model.FragmentEntryInstanceLink;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing FragmentEntryInstanceLink in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryInstanceLink
 * @generated
 */
@ProviderType
public class FragmentEntryInstanceLinkCacheModel implements CacheModel<FragmentEntryInstanceLink>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof FragmentEntryInstanceLinkCacheModel)) {
			return false;
		}

		FragmentEntryInstanceLinkCacheModel fragmentEntryInstanceLinkCacheModel = (FragmentEntryInstanceLinkCacheModel)obj;

		if (fragmentEntryInstanceLinkId == fragmentEntryInstanceLinkCacheModel.fragmentEntryInstanceLinkId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, fragmentEntryInstanceLinkId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{fragmentEntryInstanceLinkId=");
		sb.append(fragmentEntryInstanceLinkId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", fragmentEntryId=");
		sb.append(fragmentEntryId);
		sb.append(", layoutPageTemplateEntryId=");
		sb.append(layoutPageTemplateEntryId);
		sb.append(", position=");
		sb.append(position);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public FragmentEntryInstanceLink toEntityModel() {
		FragmentEntryInstanceLinkImpl fragmentEntryInstanceLinkImpl = new FragmentEntryInstanceLinkImpl();

		fragmentEntryInstanceLinkImpl.setFragmentEntryInstanceLinkId(fragmentEntryInstanceLinkId);
		fragmentEntryInstanceLinkImpl.setGroupId(groupId);
		fragmentEntryInstanceLinkImpl.setFragmentEntryId(fragmentEntryId);
		fragmentEntryInstanceLinkImpl.setLayoutPageTemplateEntryId(layoutPageTemplateEntryId);
		fragmentEntryInstanceLinkImpl.setPosition(position);

		fragmentEntryInstanceLinkImpl.resetOriginalValues();

		return fragmentEntryInstanceLinkImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		fragmentEntryInstanceLinkId = objectInput.readLong();

		groupId = objectInput.readLong();

		fragmentEntryId = objectInput.readLong();

		layoutPageTemplateEntryId = objectInput.readLong();

		position = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(fragmentEntryInstanceLinkId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(fragmentEntryId);

		objectOutput.writeLong(layoutPageTemplateEntryId);

		objectOutput.writeInt(position);
	}

	public long fragmentEntryInstanceLinkId;
	public long groupId;
	public long fragmentEntryId;
	public long layoutPageTemplateEntryId;
	public int position;
}