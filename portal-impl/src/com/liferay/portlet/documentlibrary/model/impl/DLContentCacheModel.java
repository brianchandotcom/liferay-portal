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

package com.liferay.portlet.documentlibrary.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.documentlibrary.model.DLContent;

import java.io.Serializable;

/**
 * The cache model class for representing DLContent in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see DLContent
 * @generated
 */
public class DLContentCacheModel implements CacheModel<DLContent>, Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{contentId=");
		sb.append(contentId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", repositoryId=");
		sb.append(repositoryId);
		sb.append(", path=");
		sb.append(path);
		sb.append(", version=");
		sb.append(version);
		sb.append(", size=");
		sb.append(size);
		sb.append("}");

		return sb.toString();
	}

	public DLContent toEntityModel() {
		DLContentImpl dlContentImpl = new DLContentImpl();

		dlContentImpl.setContentId(contentId);
		dlContentImpl.setGroupId(groupId);
		dlContentImpl.setCompanyId(companyId);
		dlContentImpl.setRepositoryId(repositoryId);

		if (path == null) {
			dlContentImpl.setPath(StringPool.BLANK);
		}
		else {
			dlContentImpl.setPath(path);
		}

		if (version == null) {
			dlContentImpl.setVersion(StringPool.BLANK);
		}
		else {
			dlContentImpl.setVersion(version);
		}

		dlContentImpl.setSize(size);

		dlContentImpl.resetOriginalValues();

		return dlContentImpl;
	}

	public long contentId;
	public long groupId;
	public long companyId;
	public long repositoryId;
	public String path;
	public String version;
	public long size;
}