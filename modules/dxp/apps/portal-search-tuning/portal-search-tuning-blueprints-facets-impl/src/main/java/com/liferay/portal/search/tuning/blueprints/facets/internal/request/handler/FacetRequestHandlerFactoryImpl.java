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
import com.liferay.portal.search.tuning.blueprints.facets.spi.request.FacetRequestHandler;
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
	service = FacetRequestHandlerFactory.class
)
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
		return ServiceComponentReferenceUtil.getComponentKeys(
			_facetRequestHandlers);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void registerFacetRequestHandler(
		FacetRequestHandler facetRequestHandler,
		Map<String, Object> properties) {

		ServiceComponentReferenceUtil.addToMapByName(
			_facetRequestHandlers, facetRequestHandler, properties);
	}

	protected void unregisterFacetRequestHandler(
		FacetRequestHandler facetRequestHandler,
		Map<String, Object> properties) {

		ServiceComponentReferenceUtil.removeFromMapByName(
			_facetRequestHandlers, facetRequestHandler, properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FacetRequestHandlerFactoryImpl.class);

	private volatile Map<String, ServiceComponentReference<FacetRequestHandler>>
		_facetRequestHandlers = new ConcurrentHashMap<>();

}