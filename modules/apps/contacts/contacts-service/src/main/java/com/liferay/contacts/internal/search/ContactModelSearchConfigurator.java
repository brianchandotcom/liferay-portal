/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.contacts.internal.search;

import com.liferay.contacts.internal.search.spi.model.index.contributor.ContactModelIndexerWriterContributor;
import com.liferay.contacts.internal.search.spi.model.result.contributor.ContactModelSummaryContributor;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.service.ContactLocalService;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lucas Marques de Paula
 */
@Component(service = ModelSearchConfigurator.class)
public class ContactModelSearchConfigurator
	implements ModelSearchConfigurator<Contact> {

	@Override
	public String getClassName() {
		return Contact.class.getName();
	}

	@Override
	public ModelIndexerWriterContributor<Contact>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Override
	public ModelSummaryContributor getModelSummaryContributor() {
		return _modelSummaryContributor;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor = new ContactModelIndexerWriterContributor(
			_contactLocalService);
	}

	@Reference
	private ContactLocalService _contactLocalService;

	private ModelIndexerWriterContributor<Contact> _modelIndexWriterContributor;
	private final ModelSummaryContributor _modelSummaryContributor =
		new ContactModelSummaryContributor();

}