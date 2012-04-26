/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.trash.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.trash.model.TrashVersion;

import java.io.Serializable;

/**
 * The cache model class for representing TrashVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see TrashVersion
 * @generated
 */
public class TrashVersionCacheModel implements CacheModel<TrashVersion>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{versionId=");
		sb.append(versionId);
		sb.append(", entryId=");
		sb.append(entryId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	public TrashVersion toEntityModel() {
		TrashVersionImpl trashVersionImpl = new TrashVersionImpl();

		trashVersionImpl.setVersionId(versionId);
		trashVersionImpl.setEntryId(entryId);
		trashVersionImpl.setClassNameId(classNameId);
		trashVersionImpl.setClassPK(classPK);
		trashVersionImpl.setStatus(status);

		trashVersionImpl.resetOriginalValues();

		return trashVersionImpl;
	}

	public long versionId;
	public long entryId;
	public long classNameId;
	public long classPK;
	public int status;
}