/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.search.spi.model.index.contributor;

import com.liferay.account.model.AccountRole;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Stefano Motta
 */
@Component(
	property = {
		"indexer.class.name=com.liferay.portal.kernel.model.Role",
		"service.ranking:Integer=100"
	},
	service = ModelDocumentContributor.class
)
public class RoleModelDocumentContributor
	implements ModelDocumentContributor<Role> {

	@Override
	public void contribute(Document document, Role role) {
		if (!Objects.equals(role.getClassName(), AccountRole.class.getName())) {
			return;
		}

		RoleAccountRoleDocumentContributorUtil.contribute(document, role);
	}

}