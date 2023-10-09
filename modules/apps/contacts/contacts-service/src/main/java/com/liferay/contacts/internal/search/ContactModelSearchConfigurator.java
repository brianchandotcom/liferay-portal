/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.contacts.internal.search;

import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

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

	@Reference(
		target = "(indexer.class.name=com.liferay.portal.kernel.model.Contact)"
	)
	private ModelIndexerWriterContributor<Contact> _modelIndexWriterContributor;

	@Reference(
		target = "(indexer.class.name=com.liferay.portal.kernel.model.Contact)"
	)
	private ModelSummaryContributor _modelSummaryContributor;

}