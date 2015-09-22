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

package com.liferay.portlet.softwarecatalog.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.softwarecatalog.model.SCProductScreenshot;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing SCProductScreenshot in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see SCProductScreenshot
 * @generated
 */
@ProviderType
public class SCProductScreenshotCacheModel implements CacheModel<SCProductScreenshot>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SCProductScreenshotCacheModel)) {
			return false;
		}

		SCProductScreenshotCacheModel scProductScreenshotCacheModel = (SCProductScreenshotCacheModel)obj;

		if (productScreenshotId == scProductScreenshotCacheModel.productScreenshotId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, productScreenshotId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{productScreenshotId=");
		sb.append(productScreenshotId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", productEntryId=");
		sb.append(productEntryId);
		sb.append(", thumbnailId=");
		sb.append(thumbnailId);
		sb.append(", fullImageId=");
		sb.append(fullImageId);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SCProductScreenshot toEntityModel() {
		SCProductScreenshotImpl scProductScreenshotImpl = new SCProductScreenshotImpl();

		scProductScreenshotImpl.setProductScreenshotId(productScreenshotId);
		scProductScreenshotImpl.setGroupId(groupId);
		scProductScreenshotImpl.setProductEntryId(productEntryId);
		scProductScreenshotImpl.setThumbnailId(thumbnailId);
		scProductScreenshotImpl.setFullImageId(fullImageId);
		scProductScreenshotImpl.setPriority(priority);
		scProductScreenshotImpl.setCompanyId(companyId);

		scProductScreenshotImpl.resetOriginalValues();

		return scProductScreenshotImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		productScreenshotId = objectInput.readLong();
		groupId = objectInput.readLong();
		productEntryId = objectInput.readLong();
		thumbnailId = objectInput.readLong();
		fullImageId = objectInput.readLong();
		priority = objectInput.readInt();
		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(productScreenshotId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(productEntryId);
		objectOutput.writeLong(thumbnailId);
		objectOutput.writeLong(fullImageId);
		objectOutput.writeInt(priority);
		objectOutput.writeLong(companyId);
	}

	public long productScreenshotId;
	public long groupId;
	public long productEntryId;
	public long thumbnailId;
	public long fullImageId;
	public int priority;
	public long companyId;
}