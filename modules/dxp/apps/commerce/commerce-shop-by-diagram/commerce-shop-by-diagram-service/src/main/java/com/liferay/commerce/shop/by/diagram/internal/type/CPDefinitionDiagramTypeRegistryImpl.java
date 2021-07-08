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

package com.liferay.commerce.shop.by.diagram.internal.type;

import com.liferay.commerce.shop.by.diagram.internal.type.util.comparator.CPDefinitionDiagramTypeOrderComparator;
import com.liferay.commerce.shop.by.diagram.type.CPDefinitionDiagramType;
import com.liferay.commerce.shop.by.diagram.type.CPDefinitionDiagramTypeRegistry;
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
@Component(
	enabled = false, immediate = true,
	service = CPDefinitionDiagramTypeRegistry.class
)
public class CPDefinitionDiagramTypeRegistryImpl
	implements CPDefinitionDiagramTypeRegistry {

	@Override
	public CPDefinitionDiagramType getCPDefinitionDiagramType(String key) {
		ServiceWrapper<CPDefinitionDiagramType>
			cpDefinitionDiagramTypeServiceWrapper =
				_serviceTrackerMap.getService(key);

		if (cpDefinitionDiagramTypeServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No commerce product definition diagram type registered " +
						"with key " + key);
			}

			return null;
		}

		return cpDefinitionDiagramTypeServiceWrapper.getService();
	}

	@Override
	public List<CPDefinitionDiagramType> getCPDefinitionDiagramTypes() {
		List<CPDefinitionDiagramType> cpDefinitionDiagramTypes =
			new ArrayList<>();

		List<ServiceWrapper<CPDefinitionDiagramType>>
			cpDefinitionDiagramTypeServiceWrappers = ListUtil.fromCollection(
				_serviceTrackerMap.values());

		Collections.sort(
			cpDefinitionDiagramTypeServiceWrappers,
			_cpDefinitionDiagramTypeServiceWrapperOrderComparator);

		for (ServiceWrapper<CPDefinitionDiagramType>
				cpDefinitionDiagramTypeServiceWrapper :
					cpDefinitionDiagramTypeServiceWrappers) {

			cpDefinitionDiagramTypes.add(
				cpDefinitionDiagramTypeServiceWrapper.getService());
		}

		return Collections.unmodifiableList(cpDefinitionDiagramTypes);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CPDefinitionDiagramType.class,
			"commerce.product.definition.diagram.type.key",
			ServiceTrackerCustomizerFactory.
				<CPDefinitionDiagramType>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionDiagramTypeRegistryImpl.class);

	private final Comparator<ServiceWrapper<CPDefinitionDiagramType>>
		_cpDefinitionDiagramTypeServiceWrapperOrderComparator =
			new CPDefinitionDiagramTypeOrderComparator();
	private ServiceTrackerMap<String, ServiceWrapper<CPDefinitionDiagramType>>
		_serviceTrackerMap;

}