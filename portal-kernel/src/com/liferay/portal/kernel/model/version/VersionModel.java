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

package com.liferay.portal.kernel.model.version;

/**
 * @author Preston Crary
 */
public interface VersionModel<E extends VersionedModel> {

	public long getPrimaryKey();

	public int getVersion();

	public long getVersionedModelId();

	public void populateVersionedModel(E versionedModel);

	public void setPrimaryKey(long primaryKey);

	public void setVersion(int version);

	public void setVersionedModelId(long id);

	public E toVersionedModel();

}