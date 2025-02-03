/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.roles.admin.internal.search;

import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;
import com.liferay.roles.admin.internal.search.spi.model.index.contributor.RoleModelIndexerWriterContributor;
import com.liferay.roles.admin.internal.search.spi.model.result.contributor.RoleModelSummaryContributor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(service = ModelSearchConfigurator.class)
public class RoleModelSearchConfigurator
	implements ModelSearchConfigurator<Role> {

	@Override
	public String getClassName() {
		return Role.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.MODIFIED_DATE, Field.NAME
		};
	}

	@Override
	public ModelIndexerWriterContributor<Role>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Override
	public ModelSummaryContributor getModelSummaryContributor() {
		return _modelSummaryContributor;
	}

	@Override
	public boolean isSearchResultPermissionFilterSuppressed() {
		return true;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor = new RoleModelIndexerWriterContributor(
			_dynamicQueryBatchIndexingActionableFactory, _roleLocalService);
	}

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	private ModelIndexerWriterContributor<Role> _modelIndexWriterContributor;
	private final ModelSummaryContributor _modelSummaryContributor =
		new RoleModelSummaryContributor();

	@Reference
	private RoleLocalService _roleLocalService;

}