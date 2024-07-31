/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.spi.model.index.contributor;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
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
		document.addKeyword(
			"statusByUserExternalReferenceCode",
			_getUserExternalReferenceCode(workflowedModel.getStatusByUserId()));
		document.addKeyword(
			"statusByUserId", workflowedModel.getStatusByUserId());
	}

	@Reference
	protected UserLocalService userLocalService;

	private String _getUserExternalReferenceCode(long userId) {
		String userExternalReferenceCode = StringPool.BLANK;

		try {
			User user = userLocalService.getUser(userId);

			userExternalReferenceCode = user.getExternalReferenceCode();
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get user " + userId + " while indexing document",
					portalException);
			}
		}

		return userExternalReferenceCode;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WorkflowedModelDocumentContributor.class);

}