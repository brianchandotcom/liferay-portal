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

package com.liferay.portal.relationship.internal;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.relationship.Degree;
import com.liferay.portal.relationship.Relationship;
import com.liferay.portal.relationship.RelationshipManager;
import com.liferay.portal.relationship.RelationshipResource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Mate Thurzo
 */
@Component(immediate = true, service = RelationshipManager.class)
public class RelationshipManagerImpl implements RelationshipManager {

	@Override
	public <T extends ClassedModel> Collection<? extends ClassedModel>
		getInboundRelatedModels(Class<T> modelClass, long primKey) {

		List<Relationship<T>> relationships = _getRelationships(modelClass);

		Stream<Relationship<T>> stream = relationships.stream();

		return stream.flatMap(
			relationship -> relationship.getInboundRelatedModelStream(primKey)
		).collect(
			Collectors.toList()
		);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends ClassedModel> Collection<? extends ClassedModel>
		getInboundRelatedModels(
			Class<T> modelClass, long primKey, Degree degree) {

		List<Relationship<T>> relationships = _getRelationships(modelClass);

		Stream<Relationship<T>> stream = relationships.stream();

		List<ClassedModel> inboundRelatedModels = new ArrayList<>();

		stream.forEach(
			relationship -> {
				Stream<? extends ClassedModel> inboundRelatedModelStream =
					relationship.getInboundRelatedModelStream(primKey);

				inboundRelatedModelStream.map(
					irm -> (ClassedModel)irm
				).forEach(
					inboundRelatedModel -> {
						inboundRelatedModels.add(inboundRelatedModel);

						Degree minusOneDegree = Degree.minusOne(degree);

						if (minusOneDegree.getDegree() <= 0) {
							return;
						}

						Collection<? extends ClassedModel>
							minusOneDegreeInbondRelatedModels =
								getInboundRelatedModels(
									(Class)inboundRelatedModel.getModelClass(),
									(long)
										inboundRelatedModel.getPrimaryKeyObj(),
									minusOneDegree);

						inboundRelatedModels.addAll(
							minusOneDegreeInbondRelatedModels);
					}
				);
			});

		return inboundRelatedModels;
	}

	@Override
	public <T extends ClassedModel> Collection<? extends ClassedModel>
		getOutboundRelatedModels(Class<T> modelClass, long primKey) {

		List<Relationship<T>> relationships = _getRelationships(modelClass);

		Stream<Relationship<T>> stream = relationships.stream();

		return stream.flatMap(
			relationship -> relationship.getOutboundRelatedModelStream(primKey)
		).collect(
			Collectors.toList()
		);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends ClassedModel> Collection<? extends ClassedModel>
		getOutboundRelatedModels(
			Class<T> modelClass, long primKey, Degree degree) {

		List<Relationship<T>> relationships = _getRelationships(modelClass);

		Stream<Relationship<T>> stream = relationships.stream();

		List<ClassedModel> outBoundRelatedModels = new ArrayList<>();

		stream.forEach(
			relationship -> {
				Stream<? extends ClassedModel> outboundRelatedModelStream =
					relationship.getOutboundRelatedModelStream(primKey);

				outboundRelatedModelStream.map(
					orm -> (ClassedModel)orm
				).forEach(
					outboundRelatedModel -> {
						outBoundRelatedModels.add(outboundRelatedModel);

						Degree minusOneDegree = Degree.minusOne(degree);

						if (minusOneDegree.getDegree() <= 0) {
							return;
						}

						Collection<? extends ClassedModel>
							minusOneDegreeInbondRelatedModels =
								getInboundRelatedModels(
									(Class)outboundRelatedModel.getModelClass(),
									(long)
										outboundRelatedModel.getPrimaryKeyObj(),
									minusOneDegree);

						outBoundRelatedModels.addAll(
							minusOneDegreeInbondRelatedModels);
					}
				);
			});

		return outBoundRelatedModels;
	}

	@Override
	public <T extends ClassedModel> Collection<? extends ClassedModel>
		getRelatedModels(Class<T> modelClass, long primKey) {

		List<Relationship<T>> relationships = _getRelationships(modelClass);

		Stream<Relationship<T>> stream = relationships.stream();

		return stream.flatMap(
			relationship -> relationship.getRelatedModelStream(primKey)
		).collect(
			Collectors.toList()
		);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends ClassedModel> Collection<? extends ClassedModel>
		getRelatedModels(Class<T> modelClass, long primKey, Degree degree) {

		List<Relationship<T>> relationships = _getRelationships(modelClass);

		Stream<Relationship<T>> stream = relationships.stream();

		List<ClassedModel> relatedModels = new ArrayList<>();

		stream.forEach(
			relationship -> {
				Stream<? extends ClassedModel> relatedModelStream =
					relationship.getRelatedModelStream(primKey);

				relatedModelStream.map(
					rm -> (ClassedModel)rm
				).forEach(
					relatedModel -> {
						relatedModels.add(relatedModel);

						Degree minusOneDegree = Degree.minusOne(degree);

						if (minusOneDegree.getDegree() <= 0) {
							return;
						}

						Collection<? extends ClassedModel>
							minusOneDegreeInbondRelatedModels =
								getInboundRelatedModels(
									(Class)relatedModel.getModelClass(),
									(long)relatedModel.getPrimaryKeyObj(),
									minusOneDegree);

						relatedModels.addAll(minusOneDegreeInbondRelatedModels);
					}
				);
			});

		return relatedModels;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, RelationshipResource.class, null,
			(serviceReference, emitter) -> {
				String modelClassName = (String)serviceReference.getProperty(
					"model.class.name");

				if (Validator.isNull(modelClassName)) {
					_log.error(
						"Unable to register relationship resource because of " +
							"missing service property \"model.class.name\"");
				}

				emitter.emit(modelClassName);
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	@SuppressWarnings("unchecked")
	private <T extends ClassedModel> List<Relationship<T>> _getRelationships(
		Class<T> modelClass) {

		List<Relationship<T>> relationships = new ArrayList<>();

		List<RelationshipResource> relationshipResources =
			_serviceTrackerMap.getService(modelClass.getName());

		for (RelationshipResource relationshipResource :
				relationshipResources) {

			Relationship.Builder<T> builder = new Relationship.Builder<>();

			Relationship<T> relationship = relationshipResource.relationship(
				builder);

			relationships.add(relationship);
		}

		return relationships;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RelationshipManagerImpl.class);

	private ServiceTrackerMap<String, List<RelationshipResource>>
		_serviceTrackerMap;

}