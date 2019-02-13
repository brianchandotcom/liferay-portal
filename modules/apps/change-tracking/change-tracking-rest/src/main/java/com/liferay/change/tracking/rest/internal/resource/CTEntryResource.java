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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
	public List<CTEntryModel> getCollectionCTEntryModels(
		@PathParam("collectionId") long ctCollectionId,
		@QueryParam("collision") boolean collision,
		@QueryParam("compareTo") long compareToCTCollectionId) {

		List<CTEntry> ctEntries = null;

		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.getCTCollectionOptional(ctCollectionId);

		if (!ctCollectionOptional.isPresent()) {
			throw new IllegalArgumentException(
				"Unable to find change tacking collection with id " +
					ctCollectionId);
		}

		if (collision) {
			Optional<CTCollection> compareToCTCollectionOptional =
				_ctEngineManager.getCTCollectionOptional(
					compareToCTCollectionId);

			if (!compareToCTCollectionOptional.isPresent()) {
				throw new IllegalArgumentException(
					"Unable to find change tacking collection with id " +
						compareToCTCollectionId);
			}

			ctEntries = _ctEngineManager.getCollidingCTEntries(
				ctCollectionId, compareToCTCollectionId);
		}
		else {
			ctEntries = _ctEngineManager.getCTEntries(ctCollectionId);
		}

		Stream<CTEntry> ctEntryStream = ctEntries.stream();

		return ctEntryStream.map(
			CTEntryModel::forCTEntry
		).collect(
			Collectors.toList()
		);
	}

	@Reference
	private CTEngineManager _ctEngineManager;

}