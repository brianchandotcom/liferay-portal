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

package com.liferay.vulcan.sample.liferay.portal.resource.internal.mapper;

import com.liferay.vulcan.error.VulcanDeveloperError;
import com.liferay.vulcan.resource.identifier.mapper.PathIdentifierMapper;
import com.liferay.vulcan.result.Try;
import com.liferay.vulcan.sample.liferay.portal.resource.identifier.AggregateRatingIdentifier;
import com.liferay.vulcan.uri.Path;
import com.liferay.vulcan.wiring.osgi.manager.CollectionResourceManager;

import java.util.Optional;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Converts a {@link Path} to a {@link
 * AggregateRatingIdentifier},
 * and vice versa.
 *
 * <p>
 * The {@code AggregateRatingPathIdentifierMapper} can then be provided as a
 * parameter in the methods of {@link
 * com.liferay.vulcan.resource.builder.RoutesBuilder}.
 * </p>
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class AggregateRatingPathIdentifierMapper
	implements PathIdentifierMapper<AggregateRatingIdentifier> {

	@Override
	public <U> Path map(
		AggregateRatingIdentifier aggregateRatingIdentifier,
		Class<U> modelClass) {

		String name = _getName(modelClass.getName());

		String ratedName = _getName(aggregateRatingIdentifier.getClassName());

		String id = ratedName + ":" + aggregateRatingIdentifier.getClassPK();

		return new Path(name, id);
	}

	@Override
	public AggregateRatingIdentifier map(Path path) {
		String id = path.getId();

		String[] components = id.split(":");

		if (components.length != 2) {
			throw new BadRequestException(
				id + " should be a string with the form \"name:classPK\"");
		}

		Optional<Class<Object>> optional =
			_collectionResourceManager.getModelClassOptional(components[0]);

		Class<Object> modelClass = optional.orElseThrow(
			() -> new NotFoundException(
				"No resource found for path " + components[0]));

		Try<Long> longTry = Try.fromFallible(
			() -> Long.parseLong(components[1]));

		Long classPK = longTry.orElseThrow(
			() -> new BadRequestException(
				"Unable to convert " + id + " to a long class PK"));

		return AggregateRatingIdentifier.create(modelClass.getName(), classPK);
	}

	private String _getName(String className) {
		Optional<String> optional = _collectionResourceManager.getNameOptional(
			className);

		return optional.orElseThrow(
			() -> new VulcanDeveloperError.UnresolvableURI(className));
	}

	@Reference
	private CollectionResourceManager _collectionResourceManager;

}