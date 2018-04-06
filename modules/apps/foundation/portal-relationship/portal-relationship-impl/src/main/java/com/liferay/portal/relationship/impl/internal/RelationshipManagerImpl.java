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

package com.liferay.portal.relationship.impl.internal;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.relationship.Relationship;
import com.liferay.portal.relationship.RelationshipManager;
import com.liferay.portal.relationship.RelationshipResource;

import java.util.Collection;
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
		getInboundRelationships(Class<T> relationshipBaseClass, long primKey) {

		Relationship<T> relationship = _getRelationship(
			relationshipBaseClass, primKey);

		Stream<? extends ClassedModel> stream =
			relationship.getInboundRelatedModels(primKey);

		return stream.collect(Collectors.toList());
	}

	@Override
	public <T extends ClassedModel> Collection<? extends ClassedModel>
		getOutboundRelationships(Class<T> relationshipBaseClass, long primKey) {

		Relationship<T> relationship = _getRelationship(
			relationshipBaseClass, primKey);

		Stream<? extends ClassedModel> stream =
			relationship.getOutboundRelatedModels(primKey);

		return stream.collect(Collectors.toList());
	}

	@Override
	public <T extends ClassedModel> Collection<? extends ClassedModel>
		getRelationships(Class<T> relationshipBaseClass, long primKey) {

		Relationship<T> relationship = _getRelationship(
			relationshipBaseClass, primKey);

		Stream<? extends ClassedModel> stream = relationship.getRelatedModels(
			primKey);

		return stream.collect(Collectors.toList());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
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
	private <T extends ClassedModel> Relationship<T> _getRelationship(
		Class<T> relationshipBaseClass, long primKey) {

		RelationshipResource<T> relationshipResource =
			_serviceTrackerMap.getService(relationshipBaseClass.getName());

		Relationship.Builder<T> builder = new Relationship.Builder<>();

		return relationshipResource.relationship(builder);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RelationshipManagerImpl.class);

	private ServiceTrackerMap<String, RelationshipResource> _serviceTrackerMap;

}