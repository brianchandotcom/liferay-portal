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

package com.liferay.portal.search.internal.indexer;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.registrar.ModelSearchDefinition;
import com.liferay.portal.search.spi.model.registrar.ModelSearchRegistrarHelper;
import com.liferay.portal.search.spi.model.registrar.contributor.ModelSearchDefinitionContributor;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;
import com.liferay.portal.search.spi.model.result.contributor.ModelVisibilityContributor;

import java.util.Collections;
import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 */
@Component(immediate = true, service = ModelSearchRegistrarHelper.class)
public class ModelSearchRegistrarHelperImpl
	implements ModelSearchRegistrarHelper {

	@Override
	public ServiceRegistration<?> register(
		Class<? extends BaseModel<?>> clazz, BundleContext bundleContext,
		ModelSearchDefinitionContributor modelSearchDefinitionContributor) {

		String className = clazz.getName();

		ModelSearchDefinitionImpl modelSearchDefinitionImpl =
			new ModelSearchDefinitionImpl(className);

		modelSearchDefinitionContributor.contribute(modelSearchDefinitionImpl);

		return bundleContext.registerService(
			ModelSearchConfigurator.class,
			new ModelSearchConfiguratorImpl<>(
				bundleContext,
				modelSearchDefinitionImpl._modelIndexWriterContributor,
				modelSearchDefinitionImpl._modelVisibilityContributor,
				modelSearchDefinitionImpl._modelSearchSettingsImpl,
				modelSearchDefinitionImpl._modelSummaryContributor),
			new Hashtable<>(
				Collections.singletonMap("indexer.class.name", className)));
	}

	private class ModelSearchDefinitionImpl implements ModelSearchDefinition {

		public ModelSearchDefinitionImpl(String className) {
			_modelSearchSettingsImpl = new ModelSearchSettingsImpl(className);
		}

		@Override
		public void setDefaultSelectedFieldNames(
			String... defaultSelectedFieldNames) {

			_modelSearchSettingsImpl.setDefaultSelectedFieldNames(
				defaultSelectedFieldNames);
		}

		@Override
		public void setDefaultSelectedLocalizedFieldNames(
			String... defaultSelectedLocalizedFieldNames) {

			_modelSearchSettingsImpl.setDefaultSelectedLocalizedFieldNames(
				defaultSelectedLocalizedFieldNames);
		}

		@Override
		public void setModelIndexWriteContributor(
			ModelIndexerWriterContributor<?> modelIndexWriterContributor) {

			_modelIndexWriterContributor = modelIndexWriterContributor;
		}

		@Override
		public void setModelSummaryContributor(
			ModelSummaryContributor modelSummaryContributor) {

			_modelSummaryContributor = modelSummaryContributor;
		}

		@Override
		public void setModelVisibilityContributor(
			ModelVisibilityContributor modelVisibilityContributor) {

			_modelVisibilityContributor = modelVisibilityContributor;
		}

		@Override
		public void setSearchResultPermissionFilterSuppressed(
			boolean searchResultPermissionFilterSuppressed) {

			_modelSearchSettingsImpl.setSearchResultPermissionFilterSuppressed(
				searchResultPermissionFilterSuppressed);
		}

		@Override
		public void setSelectAllLocales(boolean selectAllLocales) {
			_modelSearchSettingsImpl.setSelectAllLocales(selectAllLocales);
		}

		private ModelIndexerWriterContributor<?> _modelIndexWriterContributor;
		private final ModelSearchSettingsImpl _modelSearchSettingsImpl;
		private ModelSummaryContributor _modelSummaryContributor;
		private ModelVisibilityContributor _modelVisibilityContributor;

	}

}