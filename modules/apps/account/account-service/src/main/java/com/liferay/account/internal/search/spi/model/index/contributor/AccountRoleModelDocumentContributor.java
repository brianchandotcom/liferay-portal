/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.search.spi.model.index.contributor;

import com.liferay.account.model.AccountRole;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Drew Brokke
 */
@Component(
	property = "indexer.class.name=com.liferay.account.model.AccountRole",
	service = ModelDocumentContributor.class
)
public class AccountRoleModelDocumentContributor
	implements ModelDocumentContributor<AccountRole> {

	@Override
	public void contribute(Document document, AccountRole accountRole) {
		Role role;

		try {
			role = accountRole.getRole();
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return;
		}

		document.addKeyword(Field.COMPANY_ID, accountRole.getCompanyId());
		document.addLocalizedText(Field.DESCRIPTION, role.getDescriptionMap());
		document.addText(Field.NAME, role.getName());
		document.addLocalizedText(Field.TITLE, role.getTitleMap());
		document.addKeyword("accountEntryId", accountRole.getAccountEntryId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountRoleModelDocumentContributor.class);

}