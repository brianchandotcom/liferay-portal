/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.search;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Preston Crary
 * @author André de Oliveira
 */
@Component(service = DocumentContributor.class)
public class CTModelDocumentContributor implements DocumentContributor<Object> {

	@Override
	public void contribute(Document document, BaseModel<Object> baseModel) {
		if (!(baseModel instanceof CTModel)) {
			return;
		}

		CTModel<?> ctModel = (CTModel<?>)baseModel;

		if (ctModel.getCtCollectionId() !=
				CTConstants.CT_COLLECTION_ID_PRODUCTION) {

			document.addKeyword(_CT_COLLECTION_ID, ctModel.getCtCollectionId());
		}
	}

	private static final String _CT_COLLECTION_ID = "ctCollectionId";

}