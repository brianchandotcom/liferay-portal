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

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Image in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ImageCacheModel
	implements CacheModel<Image>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ImageCacheModel)) {
			return false;
		}

		ImageCacheModel imageCacheModel = (ImageCacheModel)obj;

		if ((imageId == imageCacheModel.imageId) &&
			(mvccVersion == imageCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, imageId);

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
		StringBundler sb = new StringBundler(17);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", imageId=");
		sb.append(imageId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", type=");
		sb.append(type);
		sb.append(", height=");
		sb.append(height);
		sb.append(", width=");
		sb.append(width);
		sb.append(", size=");
		sb.append(size);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Image toEntityModel() {
		ImageImpl imageImpl = new ImageImpl();

		imageImpl.setMvccVersion(mvccVersion);
		imageImpl.setImageId(imageId);
		imageImpl.setCompanyId(companyId);

		if (modifiedDate == Long.MIN_VALUE) {
			imageImpl.setModifiedDate(null);
		}
		else {
			imageImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (type == null) {
			imageImpl.setType("");
		}
		else {
			imageImpl.setType(type);
		}

		imageImpl.setHeight(height);
		imageImpl.setWidth(width);
		imageImpl.setSize(size);

		imageImpl.resetOriginalValues();

		return imageImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		imageId = objectInput.readLong();

		companyId = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		type = objectInput.readUTF();

		height = objectInput.readInt();

		width = objectInput.readInt();

		size = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(imageId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(modifiedDate);

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		objectOutput.writeInt(height);

		objectOutput.writeInt(width);

		objectOutput.writeInt(size);
	}

	public long mvccVersion;
	public long imageId;
	public long companyId;
	public long modifiedDate;
	public String type;
	public int height;
	public int width;
	public int size;

}