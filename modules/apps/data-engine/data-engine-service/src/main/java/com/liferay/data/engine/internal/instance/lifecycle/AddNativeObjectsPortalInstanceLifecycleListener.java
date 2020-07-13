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

package com.liferay.data.engine.internal.instance.lifecycle;

import com.liferay.data.engine.internal.nativeobject.DataEngineNativeObjectObserver;
import com.liferay.data.engine.internal.nativeobject.DataEngineNativeObjectPortalExecutor;
import com.liferay.data.engine.nativeobject.DataEngineNativeObject;
import com.liferay.data.engine.nativeobject.tracker.DataEngineNativeObjectTracker;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.util.Portal;

import java.util.Collection;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class AddNativeObjectsPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) {
		Collection<DataEngineNativeObject> dataEngineNativeObjects =
			_dataEngineNativeObjectTracker.getDataEngineNativeObjects();

		dataEngineNativeObjects.forEach(
			dataEngineNativeObject ->
				_dataEngineNativeObjectPortalExecutor.execute(
					() ->
						_dataEngineNativeObjectObserver.
							createDataEngineNativeObject(
								company.getCompanyId(),
								dataEngineNativeObject)));
	}

	@Reference
	private DataEngineNativeObjectObserver _dataEngineNativeObjectObserver;

	@Reference
	private DataEngineNativeObjectPortalExecutor
		_dataEngineNativeObjectPortalExecutor;

	@Reference
	private DataEngineNativeObjectTracker _dataEngineNativeObjectTracker;

	@Reference
	private Portal _portal;

}