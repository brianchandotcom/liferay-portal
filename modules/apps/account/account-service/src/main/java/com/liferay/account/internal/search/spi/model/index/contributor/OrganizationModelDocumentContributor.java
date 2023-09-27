/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.search.spi.model.index.contributor;

import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.util.ArrayUtil;
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
		try {
			long[] accountEntryIds = getAccountEntryIds(organization);

			if (ArrayUtil.isNotEmpty(accountEntryIds)) {
				document.addKeyword("accountEntryIds", accountEntryIds);
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to index organization " +
						organization.getOrganizationId(),
					exception);
			}
		}
	}

	protected long[] getAccountEntryIds(Organization organization) {
		DynamicQuery dynamicQuery =
			accountEntryOrganizationRelLocalService.dynamicQuery();

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"organizationId", organization.getOrganizationId()));
		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("accountEntryId"));

		return ArrayUtil.toLongArray(
			accountEntryOrganizationRelLocalService.dynamicQuery(dynamicQuery));
	}

	@Reference
	protected AccountEntryOrganizationRelLocalService
		accountEntryOrganizationRelLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		OrganizationModelDocumentContributor.class);

}