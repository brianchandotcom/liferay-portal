/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.spi.model.index.contributor;

import com.liferay.portal.kernel.model.AuditedModel;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentContributor;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = DocumentContributor.class)
public class AuditedModelDocumentContributor
	implements DocumentContributor<AuditedModel> {

	@Override
	public void contribute(
		Document document, BaseModel<AuditedModel> baseModel) {

		if (!(baseModel instanceof AuditedModel)) {
			return;
		}

		AuditedModel auditedModel = (AuditedModel)baseModel;

		document.addKeyword(Field.COMPANY_ID, auditedModel.getCompanyId());
		document.addDate(Field.CREATE_DATE, auditedModel.getCreateDate());
		document.addDate(Field.MODIFIED_DATE, auditedModel.getModifiedDate());
		document.addKeyword(Field.USER_ID, auditedModel.getUserId());
		document.addKeyword(
			Field.USER_NAME,
			portal.getUserName(
				auditedModel.getUserId(), auditedModel.getUserName()),
			true);
	}

	@Reference
	protected Portal portal;

}