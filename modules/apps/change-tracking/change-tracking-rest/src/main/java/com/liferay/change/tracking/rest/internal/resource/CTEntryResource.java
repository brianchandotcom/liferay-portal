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

import com.liferay.change.tracking.engine.CTEngineManager;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.rest.internal.model.entry.CTEntryModel;
import com.liferay.change.tracking.rest.internal.util.CTJaxRsUtil;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Change.Tracking.REST)",
		"osgi.jaxrs.resource=true"
	},
	scope = ServiceScope.PROTOTYPE, service = CTEntryResource.class
)
@Path("/collections/{collectionId}/entries")
public class CTEntryResource {

	@GET
	@Path("/{ctEntryId}")
	@Produces(MediaType.APPLICATION_JSON)
	public CTEntryModel getCTEntryModel(
		@PathParam("ctEntryId") long ctEntryId) {

		return _getCTEntryModel(_ctEntryLocalService.fetchCTEntry(ctEntryId));
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<CTEntryModel> searchCollectionCTEntryModels(
		@PathParam("collectionId") long ctCollectionId,
		@QueryParam("companyId") long companyId,
		@QueryParam("groupId") String groupIdFilter,
		@QueryParam("userId") String userIdFilter,
		@QueryParam("classNameId") String classNameIdFilter,
		@QueryParam("changeType") String changeTypeFilter,
		@QueryParam("collision") String collisionFilter,
		@QueryParam("status") String statusFilter,
		@QueryParam("sort") String sort, @Context Pagination pagination) {

		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.getCTCollectionOptional(companyId, ctCollectionId);

		if (!ctCollectionOptional.isPresent()) {
			throw new IllegalArgumentException(
				"Unable to get change tacking collection " + ctCollectionId);
		}

		Boolean collision = null;

		if (Validator.isNotNull(collisionFilter)) {
			collision = GetterUtil.getBoolean(collisionFilter);
		}

		QueryDefinition<CTEntry> queryDefinition = new QueryDefinition<>();

		if (Validator.isNotNull(statusFilter)) {
			queryDefinition.setStatus(GetterUtil.getInteger(statusFilter));
		}
		else {
			queryDefinition.setStatus(WorkflowConstants.STATUS_DRAFT);
		}

		if (pagination != null) {
			queryDefinition.setEnd(pagination.getEndPosition());
			queryDefinition.setStart(pagination.getStartPosition());
		}

		if (Validator.isNotNull(sort)) {
			OrderByComparator<CTEntry> orderByComparator =
				OrderByComparatorFactoryUtil.create(
					"CTEntry",
					CTJaxRsUtil.checkSortColumns(sort, _orderByColumnNames));

			queryDefinition.setOrderByComparator(orderByComparator);
		}

		List<CTEntry> ctEntries = _ctEngineManager.getCTEntries(
			ctCollectionOptional.get(),
			GetterUtil.getLongValues(_getFiltersArray(groupIdFilter)),
			GetterUtil.getLongValues(_getFiltersArray(userIdFilter)),
			GetterUtil.getLongValues(_getFiltersArray(classNameIdFilter)),
			GetterUtil.getIntegerValues(_getFiltersArray(changeTypeFilter)),
			collision, queryDefinition);

		int totalCount = _ctEngineManager.getCTEntriesCount(
			ctCollectionOptional.get(),
			GetterUtil.getLongValues(_getFiltersArray(groupIdFilter)),
			GetterUtil.getLongValues(_getFiltersArray(userIdFilter)),
			GetterUtil.getLongValues(_getFiltersArray(classNameIdFilter)),
			GetterUtil.getIntegerValues(_getFiltersArray(changeTypeFilter)),
			collision, queryDefinition);

		return _getPage(ctEntries, totalCount, pagination);
	}

	private CTEntryModel _getCTEntryModel(CTEntry ctEntry) {
		if (ctEntry == null) {
			return CTEntryModel.EMPTY_CT_ENTRY_MODEL;
		}

		return CTEntryModel.forCTEntry(ctEntry);
	}

	private String[] _getFiltersArray(String filterString) {
		if (Validator.isNull(filterString)) {
			return new String[0];
		}

		filterString = filterString.trim();

		return filterString.split(StringPool.COMMA);
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

	private static final Set<String> _orderByColumnNames = new HashSet<>(
		Arrays.asList("createDate", "modifiedDate", "title"));

	@Reference
	private CTEngineManager _ctEngineManager;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

}