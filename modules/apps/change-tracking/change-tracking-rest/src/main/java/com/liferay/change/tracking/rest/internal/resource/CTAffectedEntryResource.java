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
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.List;

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
		@PathParam("entryId") long ctEntryId,
		@QueryParam("keywords") String keywords,
		@Context Pagination pagination) {

		try {
			List<CTEntry> affectedCTEntries;
			int totalCount;

			QueryDefinition<CTEntry> queryDefinition = _getQueryDefinition(
				pagination);

			if (Validator.isNotNull(keywords)) {
				CTEntry ownerCTEntry = _ctEntryLocalService.getCTEntry(
					ctEntryId);

				affectedCTEntries =
					_ctEntryLocalService.getRelatedOwnerCTEntries(
						ownerCTEntry.getCompanyId(), ctCollectionId, ctEntryId,
						keywords, queryDefinition);

				totalCount =
					(int)_ctEntryLocalService.getRelatedOwnerCTEntriesCount(
						ownerCTEntry.getCompanyId(), ctCollectionId, ctEntryId,
						keywords, queryDefinition);
			}
			else {
				OrderByComparator<CTEntry> orderByComparator =
					OrderByComparatorFactoryUtil.create(
						"CTEntry", "createDate", true);

				queryDefinition.setOrderByComparator(orderByComparator);

				affectedCTEntries =
					_ctEntryLocalService.getRelatedOwnerCTEntries(
						ctEntryId, queryDefinition);

				totalCount = _ctEntryLocalService.getRelatedOwnerCTEntriesCount(
					ctEntryId, queryDefinition);
			}

			return _getPage(affectedCTEntries, totalCount, pagination);
		}
		catch (PortalException pe) {
			throw new IllegalArgumentException(pe.getMessage(), pe);
		}
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

	private QueryDefinition<CTEntry> _getQueryDefinition(
		Pagination pagination) {

		QueryDefinition<CTEntry> queryDefinition = new QueryDefinition<>();

		if (pagination != null) {
			queryDefinition.setEnd(pagination.getEndPosition());
			queryDefinition.setStart(pagination.getStartPosition());
		}

		queryDefinition.setStatus(WorkflowConstants.STATUS_DRAFT);

		OrderByComparator<CTEntry> orderByComparator =
			OrderByComparatorFactoryUtil.create("CTEntry", Field.TITLE, "asc");

		queryDefinition.setOrderByComparator(orderByComparator);

		return queryDefinition;
	}

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

}