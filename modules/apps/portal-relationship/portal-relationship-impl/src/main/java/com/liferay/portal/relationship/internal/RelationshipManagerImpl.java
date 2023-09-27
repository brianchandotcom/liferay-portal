/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Máté Thurzó
 *
 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
 */
@Component(service = RelationshipManager.class)
@Deprecated
public class RelationshipManagerImpl implements RelationshipManager {

	@Override
	public <T extends ClassedModel> Collection<? extends ClassedModel>
		getInboundRelatedModels(Class<T> modelClass, long primKey) {

		List<ClassedModel> inboundRelatedModels = new ArrayList<>();

		for (Relationship<T> relationship : _getRelationships(modelClass)) {
			inboundRelatedModels.addAll(
				relationship.getInboundRelatedModels(primKey));
		}

		return inboundRelatedModels;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends ClassedModel> Collection<? extends ClassedModel>
		getInboundRelatedModels(
			Class<T> modelClass, long primKey, Degree degree) {

		List<ClassedModel> inboundRelatedClassedModels = new ArrayList<>();

		for (Relationship<T> relationship : _getRelationships(modelClass)) {
			for (ClassedModel inboundRelatedClassedModel :
					relationship.getInboundRelatedModels(primKey)) {

				inboundRelatedClassedModels.add(inboundRelatedClassedModel);

				Degree minusOneDegree = Degree.minusOne(degree);

				if (minusOneDegree.getDegree() <= 0) {
					continue;
				}

				inboundRelatedClassedModels.addAll(
					getInboundRelatedModels(
						(Class)inboundRelatedClassedModel.getModelClass(),
						(long)inboundRelatedClassedModel.getPrimaryKeyObj(),
						minusOneDegree));
			}
		}

		return inboundRelatedClassedModels;
	}

	@Override
	public <T extends ClassedModel> Collection<? extends ClassedModel>
		getOutboundRelatedModels(Class<T> modelClass, long primKey) {

		List<ClassedModel> outboundRelatedModels = new ArrayList<>();

		for (Relationship<T> relationship : _getRelationships(modelClass)) {
			outboundRelatedModels.addAll(
				relationship.getOutboundRelatedModels(primKey));
		}

		return outboundRelatedModels;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends ClassedModel> Collection<? extends ClassedModel>
		getOutboundRelatedModels(
			Class<T> modelClass, long primKey, Degree degree) {

		List<ClassedModel> outBoundRelatedClassedModels = new ArrayList<>();

		for (Relationship<T> relationship : _getRelationships(modelClass)) {
			for (ClassedModel outboundRelatedClassedModel :
					relationship.getOutboundRelatedModels(primKey)) {

				outBoundRelatedClassedModels.add(outboundRelatedClassedModel);

				Degree minusOneDegree = Degree.minusOne(degree);

				if (minusOneDegree.getDegree() <= 0) {
					continue;
				}

				outBoundRelatedClassedModels.addAll(
					getInboundRelatedModels(
						(Class)outboundRelatedClassedModel.getModelClass(),
						(long)outboundRelatedClassedModel.getPrimaryKeyObj(),
						minusOneDegree));
			}
		}

		return outBoundRelatedClassedModels;
	}

	@Override
	public <T extends ClassedModel> Collection<? extends ClassedModel>
		getRelatedModels(Class<T> modelClass, long primKey) {

		List<ClassedModel> relatedModels = new ArrayList<>();

		for (Relationship<T> relationship : _getRelationships(modelClass)) {
			relatedModels.addAll(relationship.getRelatedModels(primKey));
		}

		return relatedModels;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends ClassedModel> Collection<? extends ClassedModel>
		getRelatedModels(Class<T> modelClass, long primKey, Degree degree) {

		List<ClassedModel> relatedClassedModels = new ArrayList<>();

		for (Relationship<T> relationship : _getRelationships(modelClass)) {
			for (ClassedModel relatedClassedModel :
					relationship.getRelatedModels(primKey)) {

				relatedClassedModels.add(relatedClassedModel);

				Degree minusOneDegree = Degree.minusOne(degree);

				if (minusOneDegree.getDegree() <= 0) {
					continue;
				}

				relatedClassedModels.addAll(
					getInboundRelatedModels(
						(Class)relatedClassedModel.getModelClass(),
						(long)relatedClassedModel.getPrimaryKeyObj(),
						minusOneDegree));
			}
		}

		return relatedClassedModels;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext,
			(Class<RelationshipResource<?>>)
				(Class<?>)RelationshipResource.class,
			null,
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

		List<RelationshipResource<?>> relationshipResources =
			_serviceTrackerMap.getService(modelClass.getName());

		for (RelationshipResource<?> relationshipResource :
				relationshipResources) {

			RelationshipResource<T> typeCastRelationshipResource =
				(RelationshipResource<T>)relationshipResource;

			Relationship.Builder<T> builder = new Relationship.Builder<>();

			Relationship<T> relationship =
				typeCastRelationshipResource.relationship(builder);

			relationships.add(relationship);
		}

		return relationships;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RelationshipManagerImpl.class);

	private ServiceTrackerMap<String, List<RelationshipResource<?>>>
		_serviceTrackerMap;

}