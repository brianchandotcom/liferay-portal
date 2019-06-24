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

package com.liferay.change.tracking.rest.resource.v1_0;

import com.liferay.change.tracking.rest.constant.v1_0.CollectionType;
import com.liferay.change.tracking.rest.dto.v1_0.Collection;
import com.liferay.change.tracking.rest.dto.v1_0.CollectionUpdate;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import javax.annotation.Generated;

import javax.ws.rs.core.Response;

import org.osgi.annotation.versioning.ProviderType;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/change-tracking/v1.0
 *
 * @author Máté Thurzó
 * @generated
 */
@Generated("")
@ProviderType
public interface CollectionResource {

	public Page<Collection> getCollectionsPage(
			Long companyId, CollectionType type, Long userId,
			Pagination pagination, Sort[] sorts)
		throws Exception;

	public Collection postCollection(
			Long companyId, Long userId, CollectionUpdate collectionUpdate)
		throws Exception;

	public Response deleteCollection(Long companyId, Long collectionId)
		throws Exception;

	public Collection getCollection(Long companyId, Long collectionId)
		throws Exception;

	public Response postCollectionCheckout(Long collectionId, Long userId)
		throws Exception;

	public Response postCollectionPublish(
			Long collectionId, Boolean ignoreCollision, Long userId)
		throws Exception;

	public default void setContextAcceptLanguage(
		AcceptLanguage contextAcceptLanguage) {
	}

	public void setContextCompany(Company contextCompany);

}