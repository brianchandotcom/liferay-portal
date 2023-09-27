/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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