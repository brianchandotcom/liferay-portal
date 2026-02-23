/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.contacts.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.service.ContactLocalService;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;

/**
 * @author Lucas Marques de Paula
 */
public class ContactModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<Contact> {

	public ContactModelIndexerWriterContributor(
		ContactLocalService contactLocalService) {

		_contactLocalService = contactLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _contactLocalService.getIndexableActionableDynamicQuery();
	}

	private final ContactLocalService _contactLocalService;

}