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

package com.liferay.search.experiences.internal.blueprint.parameter;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.search.experiences.internal.blueprint.parameter.builder.SXPParameterBuilder;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = SXPParameterBuilderFactory.class)
public class SXPParameterBuilderFactoryImpl
	implements SXPParameterBuilderFactory {

	@Override
	public SXPParameterBuilder getBuilder(String name)
		throws IllegalArgumentException {

		SXPParameterBuilder sxpParameterBuilder =
			_sxpParameterBuilderServiceTrackerMap.getService(name);

		if (sxpParameterBuilder == null) {
			throw new IllegalArgumentException(
				"No registered parameter builder " + name);
		}

		return sxpParameterBuilder;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_sxpParameterBuilderServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, SXPParameterBuilder.class, "name");
	}

	@Deactivate
	protected void deactivate() {
		_sxpParameterBuilderServiceTrackerMap.close();
	}

	private ServiceTrackerMap<String, SXPParameterBuilder>
		_sxpParameterBuilderServiceTrackerMap;

}