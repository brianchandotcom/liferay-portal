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