/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.search.spi.model.index.contributor;

import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = "indexer.class.name=com.liferay.portal.kernel.model.Organization",
	service = ModelDocumentContributor.class
)
public class OrganizationModelDocumentContributor
	implements ModelDocumentContributor<Organization> {

	@Override
	public void contribute(Document document, Organization organization) {
		document.addKeyword(
			"accountEntryIds",
			ListUtil.toLongArray(
				accountEntryOrganizationRelLocalService.
					getAccountEntryOrganizationRelsByOrganizationId(
						organization.getOrganizationId()),
				AccountEntryOrganizationRel::getAccountEntryId));
	}

	@Reference
	protected AccountEntryOrganizationRelLocalService
		accountEntryOrganizationRelLocalService;

}