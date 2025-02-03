/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.roles.admin.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Stefano Motta
 */
@Component(
	property = {
		"indexer.class.name=com.liferay.portal.kernel.model.Role",
		"service.ranking:Integer=200"
	},
	service = ModelDocumentContributor.class
)
public class RoleModelDocumentContributor
	implements ModelDocumentContributor<Role> {

	@Override
	public void contribute(Document document, Role role) {
		try {
			document.addKeyword("accountEntryId", 0);
			document.addKeyword(Field.COMPANY_ID, role.getCompanyId());
			document.addDate(Field.CREATE_DATE, role.getCreateDate());
			document.addText(Field.DESCRIPTION, role.getDescription());
			document.addKeyword(
				"externalReferenceCode", role.getExternalReferenceCode());
			document.addLocalizedText(
				"localized_description", role.getDescriptionMap());
			document.addLocalizedText("localized_title", role.getTitleMap());
			document.addDate(Field.MODIFIED_DATE, role.getModifiedDate());
			document.addKeyword(Field.NAME, role.getName());
			document.addKeyword("subtype", role.getSubtype());
			document.addText(Field.TITLE, role.getTitle());
			document.addNumber(Field.TYPE, role.getType());
			document.addKeyword(Field.USER_ID, role.getUserId());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to index role " + role.getRoleId(), exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RoleModelDocumentContributor.class);

}