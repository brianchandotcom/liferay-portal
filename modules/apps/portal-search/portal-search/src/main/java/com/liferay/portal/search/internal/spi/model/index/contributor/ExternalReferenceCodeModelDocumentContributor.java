/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.spi.model.index.contributor;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ExternalReferenceCodeModel;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Joshua Cords
 */
@Component(service = DocumentContributor.class)
public class ExternalReferenceCodeModelDocumentContributor
	implements DocumentContributor<ExternalReferenceCodeModel> {

	@Override
	public void contribute(
		Document document, BaseModel<ExternalReferenceCodeModel> baseModel) {

		if (!(baseModel instanceof ExternalReferenceCodeModel)) {
			return;
		}

		ExternalReferenceCodeModel externalReferenceCodeModel =
			(ExternalReferenceCodeModel)baseModel;

		document.addKeyword(
			"externalReferenceCode",
			externalReferenceCodeModel.getExternalReferenceCode());
	}

}