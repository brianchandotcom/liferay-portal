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

package com.liferay.portlet.softwarecatalog.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.softwarecatalog.model.SCProductScreenshot;

import java.io.Serializable;

/**
 * The cache model class for representing SCProductScreenshot in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see SCProductScreenshot
 * @generated
 */
public class SCProductScreenshotCacheModel implements CacheModel<SCProductScreenshot>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{productScreenshotId=");
		sb.append(productScreenshotId);
		sb.append(", companyId=");
		sb.append(companyId);
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
		sb.append("}");

		return sb.toString();
	}

	public SCProductScreenshot toEntityModel() {
		SCProductScreenshotImpl scProductScreenshotImpl = new SCProductScreenshotImpl();

		scProductScreenshotImpl.setProductScreenshotId(productScreenshotId);
		scProductScreenshotImpl.setCompanyId(companyId);
		scProductScreenshotImpl.setGroupId(groupId);
		scProductScreenshotImpl.setProductEntryId(productEntryId);
		scProductScreenshotImpl.setThumbnailId(thumbnailId);
		scProductScreenshotImpl.setFullImageId(fullImageId);
		scProductScreenshotImpl.setPriority(priority);

		scProductScreenshotImpl.resetOriginalValues();

		return scProductScreenshotImpl;
	}

	public long productScreenshotId;
	public long companyId;
	public long groupId;
	public long productEntryId;
	public long thumbnailId;
	public long fullImageId;
	public int priority;
}