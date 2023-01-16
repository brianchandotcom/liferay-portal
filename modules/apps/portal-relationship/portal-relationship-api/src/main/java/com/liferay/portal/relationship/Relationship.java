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

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.ClassedModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/**
 * @author Máté Thurzó
 */
public class Relationship<T extends ClassedModel> {

	public List<? extends ClassedModel> getInboundRelatedModels(long primKey) {
		return _getInboundRelatedModels(_modelSupplier.supply(primKey));
	}

	public List<? extends ClassedModel> getOutboundRelatedModels(long primKey) {
		return _getOutboundRelatedModels(_modelSupplier.supply(primKey));
	}

	public List<? extends ClassedModel> getRelatedModels(long primKey) {
		T model = _modelSupplier.supply(primKey);

		List<ClassedModel> relatedModels = new ArrayList<>();

		relatedModels.addAll(_getInboundRelatedModels(model));
		relatedModels.addAll(_getOutboundRelatedModels(model));

		return relatedModels;
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

	private List<? extends ClassedModel> _getInboundMultiRelatedModels(
		T model) {

		List<ClassedModel> inboundMultiRelatedModels = new ArrayList<>();

		_inboundMultiRelationshipFunctions.forEach(
			multiRelationshipFunction -> inboundMultiRelatedModels.addAll(
				multiRelationshipFunction.apply(model)));

		return inboundMultiRelatedModels;
	}

	private List<? extends ClassedModel> _getInboundRelatedModels(T model) {
		List<ClassedModel> inboundRelatedModels = new ArrayList<>();

		inboundRelatedModels.addAll(_getInboundMultiRelatedModels(model));
		inboundRelatedModels.addAll(_getSingleInboundRelatedModels(model));

		return inboundRelatedModels;
	}

	private List<? extends ClassedModel> _getOutboundMultiRelatedModels(
		T model) {

		List<ClassedModel> outboundMultiRelatedModels = new ArrayList<>();

		_outboundMultiRelationshipFunctions.forEach(
			multiRelationshipFunction -> outboundMultiRelatedModels.addAll(
				multiRelationshipFunction.apply(model)));

		return outboundMultiRelatedModels;
	}

	private List<? extends ClassedModel> _getOutboundRelatedModels(T model) {
		List<ClassedModel> outboundRelatedModels = new ArrayList<>();

		outboundRelatedModels.addAll(_getOutboundMultiRelatedModels(model));
		outboundRelatedModels.addAll(_getSingleOutboudRelatedModels(model));

		return outboundRelatedModels;
	}

	private List<? extends ClassedModel> _getSingleInboundRelatedModels(
		T model) {

		return TransformUtil.transform(
			_inboundSingleRelationshipFunctions,
			function -> function.apply(model));
	}

	private List<? extends ClassedModel> _getSingleOutboudRelatedModels(
		T model) {

		return TransformUtil.transform(
			_outboundSingleRelationshipFunctions,
			function -> function.apply(model));
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