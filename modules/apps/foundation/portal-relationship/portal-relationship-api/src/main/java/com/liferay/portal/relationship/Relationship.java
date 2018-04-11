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

package com.liferay.portal.relationship;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.ClassedModel;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author Mate Thurzo
 */
@ProviderType
public class Relationship<T extends ClassedModel> {

	public Stream<? extends ClassedModel> getInboundRelatedModelStream(
		long primKey) {

		T relationshipBase = _relationshipBaseSupplier.supply(primKey);

		return _getInboundRelatedModelStream(relationshipBase);
	}

	public Stream<? extends ClassedModel> getOutboundRelatedModelStream(
		long primKey) {

		T relationshipBase = _relationshipBaseSupplier.supply(primKey);

		return _getOutboundRelatedModelStream(relationshipBase);
	}

	public Stream<? extends ClassedModel> getRelatedModelStream(long primKey) {
		T relationshipBase = _relationshipBaseSupplier.supply(primKey);

		return Stream.concat(
			_getInboundRelatedModelStream(relationshipBase),
			_getOutboundRelatedModelStream(relationshipBase));
	}

	public static class Builder<T extends ClassedModel> {

		public Builder() {
			_relationship = new Relationship<>();
		}

		public Builder(Relationship<T> relationship) {
			_relationship = relationship;
		}

		public RelationshipStep relationshipBaseSupplier(
			RelationshipBaseSupplier<Long, T> relationshipBaseSupplier) {

			_relationship._relationshipBaseSupplier = relationshipBaseSupplier;

			return new RelationshipStep();
		}

		public class RelationshipStep {

			public Relationship<T> build() {
				return _relationship;
			}

			public <U extends ClassedModel> RelationshipStep
				inboundRelationship(Function<T, U> relationshipFunction) {

				Objects.requireNonNull(relationshipFunction);

				_relationship._singleInboundRelationshipFunctions.add(
					relationshipFunction);

				return this;
			}

			public <U extends ClassedModel> RelationshipStep
				inboundRelationship(
					MultiRelationshipFunction<T, U> relationshipFunction) {

				Objects.requireNonNull(relationshipFunction);

				_relationship._multiInboundRelationshipFunctions.add(
					relationshipFunction);

				return this;
			}

			public <U extends ClassedModel> RelationshipStep
				outboundRelationship(Function<T, U> relationshipFunction) {

				Objects.requireNonNull(relationshipFunction);

				_relationship._singleOutboundRelationshipFunctions.add(
					relationshipFunction);

				return this;
			}

			public <U extends ClassedModel> RelationshipStep
				outboundRelationship(
					MultiRelationshipFunction<T, U> relationshipFunction) {

				Objects.requireNonNull(relationshipFunction);

				_relationship._multiOutboundRelationshipFunctions.add(
					relationshipFunction);

				return this;
			}

		}

		private final Relationship<T> _relationship;

	}

	private Relationship() {
		_multiInboundRelationshipFunctions = new HashSet<>();
		_multiOutboundRelationshipFunctions = new HashSet<>();
		_singleInboundRelationshipFunctions = new HashSet<>();
		_singleOutboundRelationshipFunctions = new HashSet<>();
	}

	private Stream<? extends ClassedModel> _getInboundRelatedModelStream(
		T relationBaseModel) {

		return Stream.concat(
			_getMultiInboundRelatedModelStream(relationBaseModel),
			_getSingleInboundRelatedModelStream(relationBaseModel));
	}

	private Stream<? extends ClassedModel> _getMultiInboundRelatedModelStream(
		T relationshipBaseModel) {

		Stream<MultiRelationshipFunction<T, ? extends ClassedModel>> stream =
			_multiInboundRelationshipFunctions.stream();

		return stream.map(
			relationshipFunction -> relationshipFunction.apply(
				relationshipBaseModel)
		).flatMap(
			Collection::stream
		);
	}

	private Stream<? extends ClassedModel> _getMultiOutboundRelatedModelStream(
		T relationshipBaseModel) {

		Stream<MultiRelationshipFunction<T, ? extends ClassedModel>> stream =
			_multiOutboundRelationshipFunctions.stream();

		return stream.map(
			relationshipFunction -> relationshipFunction.apply(
				relationshipBaseModel)
		).flatMap(
			Collection::stream
		);
	}

	private Stream<? extends ClassedModel> _getOutboundRelatedModelStream(
		T relationBaseModel) {

		return Stream.concat(
			_getMultiOutboundRelatedModelStream(relationBaseModel),
			_getSingleOutboudRelatedModelStream(relationBaseModel));
	}

	private Stream<? extends ClassedModel> _getSingleInboundRelatedModelStream(
		T relationshipBaseModel) {

		Stream<Function<T, ? extends ClassedModel>> stream =
			_singleInboundRelationshipFunctions.stream();

		return stream.map(
			relationshipFunction -> relationshipFunction.apply(
				relationshipBaseModel));
	}

	private Stream<? extends ClassedModel> _getSingleOutboudRelatedModelStream(
		T relationshipBaseModel) {

		Stream<Function<T, ? extends ClassedModel>> stream =
			_singleOutboundRelationshipFunctions.stream();

		return stream.map(
			relationshipFunction -> relationshipFunction.apply(
				relationshipBaseModel));
	}

	private final Set<MultiRelationshipFunction<T, ? extends ClassedModel>>
		_multiInboundRelationshipFunctions;
	private final Set<MultiRelationshipFunction<T, ? extends ClassedModel>>
		_multiOutboundRelationshipFunctions;
	private RelationshipBaseSupplier<Long, T> _relationshipBaseSupplier;
	private final Set<Function<T, ? extends ClassedModel>>
		_singleInboundRelationshipFunctions;
	private final Set<Function<T, ? extends ClassedModel>>
		_singleOutboundRelationshipFunctions;

}