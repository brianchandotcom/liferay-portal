/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.data.engine.internal.expando.util;

import com.liferay.data.engine.internal.configuration.DataEngineConfiguration;
import com.liferay.data.engine.internal.expando.model.DataEngineExpandoBridgeImpl;
import com.liferay.data.engine.nativeobject.DataEngineNativeObject;
import com.liferay.data.engine.nativeobject.tracker.DataEngineNativeObjectTracker;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactory;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	configurationPid = "com.liferay.data.engine.internal.configuration.DataEngineConfiguration",
	immediate = true, service = ExpandoBridgeFactory.class
)
public class DataEngineExpandoBridgeFactoryImpl
	implements ExpandoBridgeFactory {

	@Override
	public ExpandoBridge getExpandoBridge(long companyId, String className) {
		DataEngineNativeObject dataEngineNativeObject =
			_dataEngineNativeObjectTracker.getDataEngineNativeObject(className);

		if ((dataEngineNativeObject != null) &&
			ArrayUtil.contains(
				_dataEngineConfiguration.dataEngineNativeObjectClassNames(),
				dataEngineNativeObject.getClassName())) {

			try {
				return new DataEngineExpandoBridgeImpl(
					className, 0, companyId, _groupLocalService);
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		return new ExpandoBridgeImpl(companyId, className);
	}

	@Override
	public ExpandoBridge getExpandoBridge(
		long companyId, String className, long classPK) {

		DataEngineNativeObject dataEngineNativeObject =
			_dataEngineNativeObjectTracker.getDataEngineNativeObject(className);

		if ((dataEngineNativeObject != null) &&
			ArrayUtil.contains(
				_dataEngineConfiguration.dataEngineNativeObjectClassNames(),
				dataEngineNativeObject.getClassName())) {

			try {
				return new DataEngineExpandoBridgeImpl(
					className, classPK, companyId, _groupLocalService);
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		return new ExpandoBridgeImpl(companyId, className, classPK);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_dataEngineConfiguration = ConfigurableUtil.createConfigurable(
			DataEngineConfiguration.class, properties);
	}

	private volatile DataEngineConfiguration _dataEngineConfiguration;

	@Reference
	private DataEngineNativeObjectTracker _dataEngineNativeObjectTracker;

	@Reference
	private GroupLocalService _groupLocalService;

}