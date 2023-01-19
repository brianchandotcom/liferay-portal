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

import com.liferay.fragment.model.FragmentEntryContributed;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing FragmentEntryContributed in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class FragmentEntryContributedCacheModel
	implements CacheModel<FragmentEntryContributed>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentEntryContributedCacheModel)) {
			return false;
		}

		FragmentEntryContributedCacheModel fragmentEntryContributedCacheModel =
			(FragmentEntryContributedCacheModel)object;

		if ((fragmentEntryContributedId ==
				fragmentEntryContributedCacheModel.
					fragmentEntryContributedId) &&
			(mvccVersion == fragmentEntryContributedCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, fragmentEntryContributedId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", fragmentEntryContributedId=");
		sb.append(fragmentEntryContributedId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", fragmentEntryKey=");
		sb.append(fragmentEntryKey);
		sb.append(", css=");
		sb.append(css);
		sb.append(", html=");
		sb.append(html);
		sb.append(", js=");
		sb.append(js);
		sb.append(", configuration=");
		sb.append(configuration);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public FragmentEntryContributed toEntityModel() {
		FragmentEntryContributedImpl fragmentEntryContributedImpl =
			new FragmentEntryContributedImpl();

		fragmentEntryContributedImpl.setMvccVersion(mvccVersion);
		fragmentEntryContributedImpl.setCtCollectionId(ctCollectionId);
		fragmentEntryContributedImpl.setFragmentEntryContributedId(
			fragmentEntryContributedId);

		if (createDate == Long.MIN_VALUE) {
			fragmentEntryContributedImpl.setCreateDate(null);
		}
		else {
			fragmentEntryContributedImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			fragmentEntryContributedImpl.setModifiedDate(null);
		}
		else {
			fragmentEntryContributedImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		if (fragmentEntryKey == null) {
			fragmentEntryContributedImpl.setFragmentEntryKey("");
		}
		else {
			fragmentEntryContributedImpl.setFragmentEntryKey(fragmentEntryKey);
		}

		if (css == null) {
			fragmentEntryContributedImpl.setCss("");
		}
		else {
			fragmentEntryContributedImpl.setCss(css);
		}

		if (html == null) {
			fragmentEntryContributedImpl.setHtml("");
		}
		else {
			fragmentEntryContributedImpl.setHtml(html);
		}

		if (js == null) {
			fragmentEntryContributedImpl.setJs("");
		}
		else {
			fragmentEntryContributedImpl.setJs(js);
		}

		if (configuration == null) {
			fragmentEntryContributedImpl.setConfiguration("");
		}
		else {
			fragmentEntryContributedImpl.setConfiguration(configuration);
		}

		fragmentEntryContributedImpl.setType(type);

		fragmentEntryContributedImpl.resetOriginalValues();

		return fragmentEntryContributedImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		fragmentEntryContributedId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		fragmentEntryKey = objectInput.readUTF();
		css = (String)objectInput.readObject();
		html = (String)objectInput.readObject();
		js = (String)objectInput.readObject();
		configuration = (String)objectInput.readObject();

		type = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(fragmentEntryContributedId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (fragmentEntryKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(fragmentEntryKey);
		}

		if (css == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(css);
		}

		if (html == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(html);
		}

		if (js == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(js);
		}

		if (configuration == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(configuration);
		}

		objectOutput.writeInt(type);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long fragmentEntryContributedId;
	public long createDate;
	public long modifiedDate;
	public String fragmentEntryKey;
	public String css;
	public String html;
	public String js;
	public String configuration;
	public int type;

}