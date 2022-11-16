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

package com.liferay.commerce.internal.search;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchRegistrarHelper;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Danny Situ
 */
@Component(service = {})
public class CommerceOrderSearchRegistrar {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceRegistration = _modelSearchRegistrarHelper.register(
			CommerceOrder.class, bundleContext,
			modelSearchDefinition -> {
				modelSearchDefinition.setModelIndexWriteContributor(
					_modelIndexWriterContributor);
				modelSearchDefinition.setModelSummaryContributor(
					_modelSummaryContributor);
				modelSearchDefinition.setSearchResultPermissionFilterSuppressed(
					true);
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	@Reference(
		target = "(indexer.class.name=com.liferay.commerce.model.CommerceOrder)"
	)
	private ModelIndexerWriterContributor<CommerceOrder>
		_modelIndexWriterContributor;

	@Reference
	private ModelSearchRegistrarHelper _modelSearchRegistrarHelper;

	@Reference(
		target = "(indexer.class.name=com.liferay.commerce.model.CommerceOrder)"
	)
	private ModelSummaryContributor _modelSummaryContributor;

	private ServiceRegistration<?> _serviceRegistration;

}