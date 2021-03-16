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

package com.liferay.portal.search.tuning.blueprints.facets.internal.request.handler;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.tuning.blueprints.engine.component.ServiceComponentReference;
import com.liferay.portal.search.tuning.blueprints.facets.spi.request.FacetRequestHandler;

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
@Component(immediate = true, service = FacetRequestHandlerFactory.class)
public class FacetRequestHandlerFactoryImpl
	implements FacetRequestHandlerFactory {

	@Override
	public FacetRequestHandler getHandler(String name)
		throws IllegalArgumentException {

		ServiceComponentReference<FacetRequestHandler>
			serviceComponentReference = _facetRequestHandlers.get(name);

		if (serviceComponentReference == null) {
			serviceComponentReference = _facetRequestHandlers.get("default");

			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(3);

				sb.append("No registered facet request handler for ");
				sb.append(name);
				sb.append(". Falling back to default");

				_log.warn(sb.toString());
			}
		}

		return serviceComponentReference.getServiceComponent();
	}

	@Override
	public String[] getHandlerNames() {
		Set<String> set = _facetRequestHandlers.keySet();

		return set.toArray(new String[0]);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void registerFacetRequestHandler(
		FacetRequestHandler facetRequestHandler,
		Map<String, Object> properties) {

		String name = (String)properties.get("name");

		if (Validator.isBlank(name)) {
			if (_log.isWarnEnabled()) {
				Class<?> clazz = facetRequestHandler.getClass();

				_log.warn(
					"Unable to add facet request handler " + clazz.getName() +
						". Name property empty.");
			}

			return;
		}

		int serviceRanking = GetterUtil.get(
			properties.get("service.ranking"), 0);

		ServiceComponentReference<FacetRequestHandler>
			serviceComponentReference = new ServiceComponentReference<>(
				facetRequestHandler, serviceRanking);

		if (_facetRequestHandlers.containsKey(name)) {
			ServiceComponentReference<FacetRequestHandler> previousReference =
				_facetRequestHandlers.get(name);

			if (previousReference.compareTo(serviceComponentReference) < 0) {
				_facetRequestHandlers.put(name, serviceComponentReference);
			}
		}
		else {
			_facetRequestHandlers.put(name, serviceComponentReference);
		}
	}

	protected void unregisterFacetRequestHandler(
		FacetRequestHandler facetRequestHandler,
		Map<String, Object> properties) {

		String name = (String)properties.get("name");

		if (Validator.isBlank(name)) {
			return;
		}

		_facetRequestHandlers.remove(name);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FacetRequestHandlerFactoryImpl.class);

	private volatile Map<String, ServiceComponentReference<FacetRequestHandler>>
		_facetRequestHandlers = new ConcurrentHashMap<>();

}