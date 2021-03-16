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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.tuning.blueprints.engine.component.ServiceComponentReference;
import com.liferay.portal.search.tuning.blueprints.facets.spi.response.FacetResponseHandler;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = FacetResponseHandlerFactory.class)
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
		Set<String> set = _facetResponseHandlers.keySet();

		return set.toArray(new String[0]);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void registerFacetResponseHandler(
		FacetResponseHandler facetResponseHandler,
		Map<String, Object> properties) {

		String name = (String)properties.get("name");

		if (Validator.isBlank(name)) {
			if (_log.isWarnEnabled()) {
				Class<?> clazz = facetResponseHandler.getClass();

				_log.warn(
					"Unable to add facet response handler " + clazz.getName() +
						". Name property empty.");
			}

			return;
		}

		int serviceRanking = GetterUtil.get(
			properties.get("service.ranking"), 0);

		ServiceComponentReference<FacetResponseHandler>
			serviceComponentReference = new ServiceComponentReference<>(
				facetResponseHandler, serviceRanking);

		if (_facetResponseHandlers.containsKey(name)) {
			ServiceComponentReference<FacetResponseHandler> previousReference =
				_facetResponseHandlers.get(name);

			if (previousReference.compareTo(serviceComponentReference) < 0) {
				_facetResponseHandlers.put(name, serviceComponentReference);
			}
		}
		else {
			_facetResponseHandlers.put(name, serviceComponentReference);
		}
	}

	protected void unregisterFacetResponseHandler(
		FacetResponseHandler facetResponseHandler,
		Map<String, Object> properties) {

		String name = (String)properties.get("name");

		if (Validator.isBlank(name)) {
			return;
		}

		_facetResponseHandlers.remove(name);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FacetResponseHandlerFactoryImpl.class);

	private volatile Map
		<String, ServiceComponentReference<FacetResponseHandler>>
			_facetResponseHandlers = new ConcurrentHashMap<>();

}