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
	immediate = true,
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