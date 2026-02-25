/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.organizations.internal.search;

import com.liferay.organizations.internal.search.spi.model.result.contributor.OrganizationModelSummaryContributor;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Fabiano Nazar
 * @author Luan Maoski
 */
@Component(service = ModelSearchConfigurator.class)
public class OrganizationModelSearchConfigurator
	implements ModelSearchConfigurator<Organization> {

	@Override
	public String getClassName() {
		return Organization.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.ORGANIZATION_ID, Field.UID
		};
	}

	@Override
	public ModelIndexerWriterContributor<Organization>
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
		_modelIndexWriterContributor = new ModelIndexerWriterContributor<>(
			_organizationLocalService::getIndexableActionableDynamicQuery);
	}

	private ModelIndexerWriterContributor<Organization>
		_modelIndexWriterContributor;
	private final ModelSummaryContributor _modelSummaryContributor =
		new OrganizationModelSummaryContributor();

	@Reference
	private OrganizationLocalService _organizationLocalService;

}