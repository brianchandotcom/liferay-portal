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

package com.liferay.change.tracking.rest.internal.resource;

import com.liferay.change.tracking.CTEngineManager;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.rest.internal.model.entry.CTEntryModel;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Daniel Kocsis
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=change-tracking-application)",
		"osgi.jaxrs.resource=true"
	},
	scope = ServiceScope.PROTOTYPE, service = CTEntryResource.class
)
@Path("/collections/{collectionId}/entries")
public class CTEntryResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<CTEntryModel> getCollectionCTEntryModels(
		@PathParam("collectionId") long ctCollectionId,
		@QueryParam("collision") String collisionFilter,
		@Context Pagination pagination) {

		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.getCTCollectionOptional(ctCollectionId);

		if (!ctCollectionOptional.isPresent()) {
			throw new IllegalArgumentException(
				"Unable to get change tacking collection " + ctCollectionId);
		}

		List<CTEntry> ctEntries;
		int totalCount;

		if (GetterUtil.getBoolean(collisionFilter)) {
			ctEntries = _ctEngineManager.getCollidingCTEntries(ctCollectionId);

			totalCount = ctEntries.size();
		}
		else {
			QueryDefinition<CTEntry> queryDefinition = new QueryDefinition<>();

			if (pagination != null) {
				queryDefinition.setStart(pagination.getStartPosition());
				queryDefinition.setEnd(pagination.getEndPosition());
			}

			ctEntries = _ctEngineManager.getCTEntries(
				ctCollectionId, queryDefinition);

			totalCount = _ctEngineManager.getCTEntriesCount(ctCollectionId);
		}

		return _getPage(ctEntries, totalCount, pagination);
	}

	private Page<CTEntryModel> _getPage(
		List<CTEntry> ctEntries, int totalCount, Pagination pagination) {

		if (pagination == null) {
			pagination = Pagination.of(totalCount, 1);
		}

		return Page.of(
			TransformUtil.transform(ctEntries, CTEntryModel::forCTEntry),
			pagination, totalCount);
	}

	@Reference
	private CTEngineManager _ctEngineManager;

}