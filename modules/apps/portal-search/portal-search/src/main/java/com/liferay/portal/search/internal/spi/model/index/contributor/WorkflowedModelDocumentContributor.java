/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.spi.model.index.contributor;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.WorkflowedModel;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentContributor;
import com.liferay.portal.kernel.search.Field;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = DocumentContributor.class)
public class WorkflowedModelDocumentContributor
	implements DocumentContributor<WorkflowedModel> {

	@Override
	public void contribute(
		Document document, BaseModel<WorkflowedModel> baseModel) {

		if (!(baseModel instanceof WorkflowedModel)) {
			return;
		}

		WorkflowedModel workflowedModel = (WorkflowedModel)baseModel;

		document.addKeyword(Field.STATUS, workflowedModel.getStatus());
		document.addKeyword(
			"statusByUserId", workflowedModel.getStatusByUserId());
	}

}