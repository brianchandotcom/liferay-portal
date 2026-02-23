/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.roles.admin.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;

/**
 * @author Stefano Motta
 */
public class RoleModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<Role> {

	public RoleModelIndexerWriterContributor(
		RoleLocalService roleLocalService) {

		_roleLocalService = roleLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _roleLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(Role role) {
		return IndexerWriterMode.UPDATE;
	}

	private final RoleLocalService _roleLocalService;

}