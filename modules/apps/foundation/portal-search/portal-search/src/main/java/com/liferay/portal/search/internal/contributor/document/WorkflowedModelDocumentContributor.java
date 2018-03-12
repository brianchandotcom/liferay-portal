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

package com.liferay.portal.search.internal.contributor.document;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.WorkflowedModel;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentContributor;
import com.liferay.portal.kernel.search.Field;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = DocumentContributor.class)
public class WorkflowedModelDocumentContributor implements DocumentContributor {

	@Override
	public void contribute(Document document, BaseModel baseModel) {
		if (!(baseModel instanceof WorkflowedModel)) {
			return;
		}

		WorkflowedModel workflowedModel = (WorkflowedModel)baseModel;

		document.addKeyword(Field.STATUS, workflowedModel.getStatus());
	}

}