/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.batch.engine.resource;

import com.liferay.headless.batch.engine.resource.v1_0.ImportTaskResource;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResourceFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 */
@Component(service = VulcanBatchEngineImportTaskResourceFactory.class)
public class VulcanBatchEngineImportTaskResourceFactoryImpl
	implements VulcanBatchEngineImportTaskResourceFactory {

	@Override
	public VulcanBatchEngineImportTaskResource create() {
		return new VulcanBatchEngineImportTaskResourceImpl(_factory);
	}

	@Reference
	private ImportTaskResource.Factory _factory;

}