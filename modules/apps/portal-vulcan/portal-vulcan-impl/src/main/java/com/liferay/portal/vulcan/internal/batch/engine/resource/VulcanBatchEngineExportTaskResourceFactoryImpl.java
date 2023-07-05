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

package com.liferay.portal.vulcan.internal.batch.engine.resource;

import com.liferay.headless.batch.engine.resource.v1_0.ExportTaskResource;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineExportTaskResource;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineExportTaskResourceFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 */
@Component(service = VulcanBatchEngineExportTaskResourceFactory.class)
public class VulcanBatchEngineExportTaskResourceFactoryImpl
	implements VulcanBatchEngineExportTaskResourceFactory {

	@Override
	public VulcanBatchEngineExportTaskResource create() {
		return new VulcanBatchEngineExportTaskResourceImpl(_factory);
	}

	@Reference
	private ExportTaskResource.Factory _factory;

}