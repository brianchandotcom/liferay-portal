/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.reflect.GenericUtil;
import com.liferay.portal.vulcan.problem.Problem;
import com.liferay.portal.vulcan.problem.ProblemMapper;
import com.liferay.portal.vulcan.problem.ProblemProvider;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alejandro Tardín
 */
@Component(service = ProblemProvider.class)
public class ProblemProviderImpl implements ProblemProvider {

	@Override
	public Problem getProblem(Throwable throwable) {
		while (throwable != null) {
			Class<? extends Throwable> clazz = throwable.getClass();

			ProblemMapper<Throwable> problemMapper =
				(ProblemMapper<Throwable>)_serviceTrackerMap.getService(
					clazz.getName());

			if (problemMapper != null) {
				return problemMapper.getProblem(throwable);
			}

			throwable = throwable.getCause();
		}

		return null;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext,
			(Class<ProblemMapper<?>>)(Class<?>)ProblemMapper.class, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(problemMapper, emitter) -> emitter.emit(
					GenericUtil.getGenericClassName(problemMapper))));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, ProblemMapper<?>> _serviceTrackerMap;

}