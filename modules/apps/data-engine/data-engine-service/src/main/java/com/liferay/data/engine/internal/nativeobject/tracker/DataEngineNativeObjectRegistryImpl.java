/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.internal.nativeobject.tracker;

import com.liferay.data.engine.nativeobject.DataEngineNativeObject;
import com.liferay.data.engine.nativeobject.tracker.DataEngineNativeObjectRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collection;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Jeyvison Nascimento
 */
@Component(service = DataEngineNativeObjectRegistry.class)
public class DataEngineNativeObjectRegistryImpl
	implements DataEngineNativeObjectRegistry {

	@Override
	public DataEngineNativeObject getDataEngineNativeObject(String className) {
		return _serviceTrackerMap.getService(StringUtil.toUpperCase(className));
	}

	@Override
	public Collection<DataEngineNativeObject> getDataEngineNativeObjects() {
		return _serviceTrackerMap.values();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, DataEngineNativeObject.class, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(dataEngineNativeObject, emitter) -> emitter.emit(
					StringUtil.toUpperCase(
						dataEngineNativeObject.getClassName()))));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, DataEngineNativeObject>
		_serviceTrackerMap;

}