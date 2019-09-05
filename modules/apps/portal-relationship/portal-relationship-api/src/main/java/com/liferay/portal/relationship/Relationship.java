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

import com.liferay.portal.kernel.model.ClassedModel;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author Máté Thurzó
 */
public class Relationship<T extends ClassedModel> {

	public Stream<? extends ClassedModel> getInboundRelatedModelStream(
		long primKey) {

		T model = _modelSupplier.supply(primKey);

		return _getInboundRelatedModelStream(model);
	}

	public Stream<? extends ClassedModel> getOutboundRelatedModelStream(
		long primKey) {

		T model = _modelSupplier.supply(primKey);

		return _getOutboundRelatedModelStream(model);
	}

	public Stream<? extends ClassedModel> getRelatedModelStream(long primKey) {
		T model = _modelSupplier.supply(primKey);

		return Stream.concat(
			_getInboundRelatedModelStream(model),
			_getOutboundRelatedModelStream(model));
	}

	public static class Builder<T extends ClassedModel> {

		public Builder() {
			_relationship = new Relationship<>();
		}

		public Builder(Relationship<T> relationship) {
			_relationship = relationship;
		}

		public RelationshipStep modelSupplier(
			ModelSupplier<Long, T> modelSupplier) {

			_relationship._modelSupplier = modelSupplier;

			return new RelationshipStep();
		}

		public class RelationshipStep {

			public Relationship<T> build() {
				return _relationship;
			}

			public <U extends ClassedModel> RelationshipStep
				inboundMultiRelationship(
					MultiRelationshipFunction<T, U> multiRelationshipFunction) {

				Objects.requireNonNull(multiRelationshipFunction);

				_relationship._inboundMultiRelationshipFunctions.add(
					multiRelationshipFunction);

				return this;
			}

			public <U extends ClassedModel> RelationshipStep
				inboundSingleRelationship(Function<T, U> function) {

				Objects.requireNonNull(function);

				_relationship._inboundSingleRelationshipFunctions.add(function);

				return this;
			}

			public <U extends ClassedModel> RelationshipStep
				outboundMultiRelationship(
					MultiRelationshipFunction<T, U> multiRelationshipFunction) {

				Objects.requireNonNull(multiRelationshipFunction);

				_relationship._outboundMultiRelationshipFunctions.add(
					multiRelationshipFunction);

				return this;
			}

			public <U extends ClassedModel> RelationshipStep
				outboundSingleRelationship(Function<T, U> function) {

				Objects.requireNonNull(function);

				_relationship._outboundSingleRelationshipFunctions.add(
					function);

				return this;
			}

		}

		private final Relationship<T> _relationship;

	}

	private Relationship() {
	}

	private Stream<? extends ClassedModel> _getInboundMultiRelatedModelStream(
		T model) {

		Stream<MultiRelationshipFunction<T, ? extends ClassedModel>> stream =
			_inboundMultiRelationshipFunctions.stream();

		return stream.map(
			multiRelationshipFunction -> multiRelationshipFunction.apply(model)
		).flatMap(
			Collection::stream
		);
	}

	private Stream<? extends ClassedModel> _getInboundRelatedModelStream(
		T model) {

		return Stream.concat(
			_getInboundMultiRelatedModelStream(model),
			_getSingleInboundRelatedModelStream(model));
	}

	private Stream<? extends ClassedModel> _getOutboundMultiRelatedModelStream(
		T model) {

		Stream<MultiRelationshipFunction<T, ? extends ClassedModel>> stream =
			_outboundMultiRelationshipFunctions.stream();

		return stream.map(
			multiRelationshipFunction -> multiRelationshipFunction.apply(model)
		).flatMap(
			Collection::stream
		);
	}

	private Stream<? extends ClassedModel> _getOutboundRelatedModelStream(
		T model) {

		return Stream.concat(
			_getOutboundMultiRelatedModelStream(model),
			_getSingleOutboudRelatedModelStream(model));
	}

	private Stream<? extends ClassedModel> _getSingleInboundRelatedModelStream(
		T model) {

		Stream<Function<T, ? extends ClassedModel>> stream =
			_inboundSingleRelationshipFunctions.stream();

		return stream.map(function -> function.apply(model));
	}

	private Stream<? extends ClassedModel> _getSingleOutboudRelatedModelStream(
		T model) {

		Stream<Function<T, ? extends ClassedModel>> stream =
			_outboundSingleRelationshipFunctions.stream();

		return stream.map(function -> function.apply(model));
	}

	private final Set<MultiRelationshipFunction<T, ? extends ClassedModel>>
		_inboundMultiRelationshipFunctions = new HashSet<>();
	private final Set<Function<T, ? extends ClassedModel>>
		_inboundSingleRelationshipFunctions = new HashSet<>();
	private ModelSupplier<Long, T> _modelSupplier;
	private final Set<MultiRelationshipFunction<T, ? extends ClassedModel>>
		_outboundMultiRelationshipFunctions = new HashSet<>();
	private final Set<Function<T, ? extends ClassedModel>>
		_outboundSingleRelationshipFunctions = new HashSet<>();

}