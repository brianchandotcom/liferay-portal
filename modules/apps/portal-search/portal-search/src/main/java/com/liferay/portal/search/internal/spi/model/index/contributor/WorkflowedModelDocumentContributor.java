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
import com.liferay.portal.kernel.service.UserLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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

		long userId = workflowedModel.getStatusByUserId();

		document.addKeyword("statusByUserId", userId);

		if (userId != 0) {
			String[] userData = UserDataUtil.getUserData(
				baseModel.getClass(), userLocalService, userId);

			if (userData != null) {
				document.addKeyword(
					"statusByUserExternalReferenceCode", userData[0]);
			}
		}
	}

	@Reference
	protected UserLocalService userLocalService;

}