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

package com.liferay.commerce.data.integration.internal.process.type;

import com.liferay.commerce.data.integration.internal.process.type.comparator.ProcessTypeOrderComparator;
import com.liferay.commerce.data.integration.process.type.ProcessType;
import com.liferay.commerce.data.integration.process.type.ProcessTypeRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = ProcessTypeRegistry.class)
public class ProcessTypeRegistryImpl implements ProcessTypeRegistry {

	@Override
	public ProcessType getProcessType(String key) {
		ServiceWrapper<ProcessType> processTypeServiceWrapper =
			_serviceTrackerMap.getService(key);

		if (processTypeServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No ProcessType registered with key " + key);
			}

			return null;
		}

		return processTypeServiceWrapper.getService();
	}

	@Override
	public List<ProcessType> getProcessTypes() {
		List<ProcessType> processTypes = new ArrayList<>();

		List<ServiceWrapper<ProcessType>> processTypeServiceWrappers =
			ListUtil.fromCollection(_serviceTrackerMap.values());

		Collections.sort(
			processTypeServiceWrappers,
			_processTypeServiceWrapperOrderComparator);

		for (ServiceWrapper<ProcessType> processTypeServiceWrapper :
				processTypeServiceWrappers) {

			processTypes.add(processTypeServiceWrapper.getService());
		}

		return Collections.unmodifiableList(processTypes);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ProcessType.class,
			"commerce.data.integration.process.type.key",
			ServiceTrackerCustomizerFactory.<ProcessType>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ProcessTypeRegistryImpl.class);

	private final Comparator<ServiceWrapper<ProcessType>>
		_processTypeServiceWrapperOrderComparator =
			new ProcessTypeOrderComparator();
	private ServiceTrackerMap<String, ServiceWrapper<ProcessType>>
		_serviceTrackerMap;

}