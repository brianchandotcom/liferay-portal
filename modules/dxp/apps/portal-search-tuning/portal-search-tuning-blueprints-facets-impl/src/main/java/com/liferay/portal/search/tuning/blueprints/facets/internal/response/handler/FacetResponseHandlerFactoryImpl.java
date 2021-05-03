/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.blueprints.facets.internal.response.handler;

import com.liferay.portal.search.tuning.blueprints.facets.spi.response.FacetResponseHandler;
import com.liferay.portal.search.tuning.blueprints.util.component.ServiceComponentReference;
import com.liferay.portal.search.tuning.blueprints.util.component.ServiceComponentReferenceUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "type=internal",
	service = FacetResponseHandlerFactory.class
)
public class FacetResponseHandlerFactoryImpl
	implements FacetResponseHandlerFactory {

	@Override
	public FacetResponseHandler getHandler(String name)
		throws IllegalArgumentException {

		ServiceComponentReference<FacetResponseHandler>
			serviceComponentReference = _facetResponseHandlers.get(name);

		if (serviceComponentReference == null) {
			throw new IllegalArgumentException(
				"No registered facet response handler for " + name);
		}

		return serviceComponentReference.getServiceComponent();
	}

	@Override
	public String[] getHandlerNames() {
		return ServiceComponentReferenceUtil.getComponentKeys(
			_facetResponseHandlers);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void registerFacetResponseHandler(
		FacetResponseHandler facetResponseHandler,
		Map<String, Object> properties) {

		ServiceComponentReferenceUtil.addToMapByName(
			_facetResponseHandlers, facetResponseHandler, properties);
	}

	protected void unregisterFacetResponseHandler(
		FacetResponseHandler facetResponseHandler,
		Map<String, Object> properties) {

		ServiceComponentReferenceUtil.removeFromMapByName(
			_facetResponseHandlers, facetResponseHandler, properties);
	}

	private volatile Map
		<String, ServiceComponentReference<FacetResponseHandler>>
			_facetResponseHandlers = new ConcurrentHashMap<>();

}