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
public interface VersionedModel<V extends VersionModel> {

	public long getHeadId();

	public long getPrimaryKey();

	/**
	 * @deprecated As of Judson (7.1.x), replaced by the inverse of {@link
	 *             #isHead()}
	 */
	@Deprecated
	public default boolean isDraft() {
		return !isHead();
	}

	public default boolean isHead() {
		if (getHeadId() >= 0) {
			return false;
		}

		return true;
	}

	public void populateVersionModel(V versionModel);

	public void setHeadId(long headId);

	public void setPrimaryKey(long primaryKey);

}