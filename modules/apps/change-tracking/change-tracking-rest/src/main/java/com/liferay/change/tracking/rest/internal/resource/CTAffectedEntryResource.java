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

import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.rest.internal.model.entry.CTAffectedEntryModel;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Gergely Mathe
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Change.Tracking.REST)",
		"osgi.jaxrs.resource=true"
	},
	scope = ServiceScope.PROTOTYPE, service = CTAffectedEntryResource.class
)
@Path("/collections/{collectionId}/entries/{entryId}/affecteds")
public class CTAffectedEntryResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<CTAffectedEntryModel> getCTAffectedEntryModels(
		@PathParam("collectionId") long ctCollectionId,
		@PathParam("entryId") long ctEntryId, @Context Pagination pagination) {

		List<CTEntry> affectedCTEntries =
			_ctEntryLocalService.getRelatedOwnerCTEntries(ctEntryId);

		int totalCount = _ctEntryLocalService.getRelatedOwnerCTEntriesCount(
			ctEntryId);

		return _getPage(affectedCTEntries, totalCount, pagination);
	}

	private Page<CTAffectedEntryModel> _getPage(
		List<CTEntry> affectedCTEntries, int totalCount,
		Pagination pagination) {

		if (pagination == null) {
			pagination = Pagination.of(totalCount, 1);
		}

		return Page.of(
			TransformUtil.transform(
				affectedCTEntries, CTAffectedEntryModel::forCTEntry),
			pagination, totalCount);
	}

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

}