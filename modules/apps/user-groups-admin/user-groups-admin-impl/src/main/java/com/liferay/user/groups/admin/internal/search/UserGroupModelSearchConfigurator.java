/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.groups.admin.internal.search;

import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;
import com.liferay.user.groups.admin.internal.search.spi.model.index.contributor.UserGroupModelIndexerWriterContributor;
import com.liferay.user.groups.admin.internal.search.spi.model.result.contributor.UserGroupModelSummaryContributor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luan Maoski
 */
@Component(service = ModelSearchConfigurator.class)
public class UserGroupModelSearchConfigurator
	implements ModelSearchConfigurator<UserGroup> {

	@Override
	public String getClassName() {
		return UserGroup.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.UID, Field.USER_GROUP_ID
		};
	}

	@Override
	public ModelIndexerWriterContributor<UserGroup>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Override
	public ModelSummaryContributor getModelSummaryContributor() {
		return _modelSummaryContributor;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor =
			new UserGroupModelIndexerWriterContributor(_userGroupLocalService);
	}

	private ModelIndexerWriterContributor<UserGroup>
		_modelIndexWriterContributor;
	private final ModelSummaryContributor _modelSummaryContributor =
		new UserGroupModelSummaryContributor();

	@Reference
	private UserGroupLocalService _userGroupLocalService;

}