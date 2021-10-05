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

package com.liferay.object.related.models;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.List;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public interface ObjectRelatedModelsProvider<T extends BaseModel<T>> {

	public void deleteRelatedModel(
			long userId, long groupId, long objectRelationshipId,
			long primaryKey)
		throws PortalException;

	public void disassociateRelatedModels(
			long userId, long objectRelationshipId, long primaryKey1,
			long primaryKey2)
		throws PortalException;

	public String getClassName();

	public String getObjectRelationshipType();

	public List<T> getRelatedModels(
			long groupId, long objectRelationshipId, long primaryKey, int start,
			int end)
		throws PortalException;

	public int getRelatedModelsCount(
			long groupId, long objectRelationshipId, long primaryKey)
		throws PortalException;

}